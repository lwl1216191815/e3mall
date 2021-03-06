package cn.e3mall.cart.service.impl;

import cn.e3mall.cart.service.CartService;
import cn.e3mall.common.jedis.JedisClient;
import cn.e3mall.common.util.E3Result;
import cn.e3mall.common.util.JsonUtils;
import cn.e3mall.mapper.TbItemMapper;
import cn.e3mall.pojo.TbItem;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Value("${cart.redis.key.prefix}")
    private String cartRedisKeyPrefix;
    @Autowired
    private JedisClient jedisClient;
    @Autowired
    private TbItemMapper itemMapper;

    /**
     * 先判断redis缓存中是否有此用户的购物车
     * 如果有，修改购物车数量
     * 如果没有，就根据商品ID查询商品信息
     * 将商品写入购物车。
     * 在redis中，购物车采用hash数据结构，
     * key采用cartRedisKeyPrefix:userId，
     * filed使用itemId，
     * value为商品对象转换的json字符串
     * @param userId
     * @param itemId
     * @param num
     * @return
     */
    @Override
    public E3Result addCart(Long userId, Long itemId, Integer num) {
        //1 先判断此用户是否有购物车
        String redisKey = cartRedisKeyPrefix + ":" + String.valueOf(userId);
        String field = String.valueOf(itemId);
        String json = jedisClient.hget(redisKey, field);
        TbItem item;
        if(StringUtils.isNotBlank(json)){
            item = JsonUtils.jsonToObject(json, TbItem.class);
            num += item.getNum();
        }else{
            item = itemMapper.selectByPrimaryKey(itemId);
        }
        item.setNum(num);
        jedisClient.hset(redisKey,field,JsonUtils.objectToJson(item));
        return E3Result.ok();
    }

    @Override
    public E3Result mergeCart(Long userId, List<TbItem> itemList) {
        if(itemList != null && !itemList.isEmpty()){
            for (TbItem tbItem : itemList) {
                addCart(userId,tbItem.getId(),tbItem.getNum());
            }
        }
        return E3Result.ok();
    }

    @Override
    public List<TbItem> getCartList(Long userId) {
        String redisKey = cartRedisKeyPrefix + ":" + String.valueOf(userId);
        List<String> values = jedisClient.hvals(redisKey);
        List<TbItem> itemList = new ArrayList<>(values.size());
        for (String value : values) {
            TbItem tbItem = JsonUtils.jsonToObject(value, TbItem.class);
            itemList.add(tbItem);
        }
        return itemList;
    }

    @Override
    public E3Result updateCartNum(Long userId, Long itemId, Integer num) {
        //1 先判断此用户是否有购物车
        String redisKey = cartRedisKeyPrefix + ":" + String.valueOf(userId);
        String field = String.valueOf(itemId);
        String json = jedisClient.hget(redisKey, field);
        if(StringUtils.isNotBlank(json)){
            TbItem item = JsonUtils.jsonToObject(json, TbItem.class);
            item.setNum(num);
            jedisClient.hset(redisKey,field,JsonUtils.objectToJson(item));
            return E3Result.ok();
        }
        return  E3Result.build(500,"购物车中并没有这款商品");
    }

    @Override
    public E3Result deleteCart(Long userId, Long itemId) {
        String redisKey = cartRedisKeyPrefix + ":" + String.valueOf(userId);
        String field = String.valueOf(itemId);
        jedisClient.hdel(redisKey,field);
        return E3Result.ok();
    }
}
