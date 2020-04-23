package cn.e3mall.service.impl;

import java.util.List;

import cn.e3mall.common.jedis.JedisClient;
import cn.e3mall.common.util.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.e3mall.common.exception.ItemDescNotFoundException;
import cn.e3mall.common.util.E3Result;
import cn.e3mall.mapper.TbItemDescMapper;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.pojo.TbItemDescExample;
import cn.e3mall.pojo.TbItemDescExample.Criteria;
import cn.e3mall.service.ItemDescService;
/**
 * 商品详情service
 * @author Dragon
 *
 */
@Service
public class ItemDescServiceImpl implements ItemDescService {
	@Autowired
	private TbItemDescMapper itemDescMapper;
    @Value("${redis.item.pre}")
    private String redisItemPre;
    @Value("${item.cache.expire}")
    private Integer redisItemExpire;
    @Autowired
    private JedisClient jedisClient;
	@Override
	public E3Result getDescByItemId(long itemId) throws ItemDescNotFoundException {
        String json = jedisClient.get(redisItemPre + ":" + itemId + ":DESC");
        if(StringUtils.isNotBlank(json)){
            TbItemDesc itemDesc = JsonUtils.jsonToObject(json, TbItemDesc.class);
            return E3Result.ok(itemDesc);
        }
        TbItemDescExample example = new TbItemDescExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(itemId);
		List<TbItemDesc> list = itemDescMapper.selectByExampleWithBLOBs(example);
		if(!list.isEmpty() && list.size() == 1 && list.get(0).getItemId() == itemId) {
            TbItemDesc itemDesc = list.get(0);
            jedisClient.set(redisItemPre + ":" + itemId + ":DESC",JsonUtils.objectToJson(itemDesc));
            jedisClient.expire(redisItemPre + ":" + itemId + ":DESC",redisItemExpire);
            return E3Result.ok(itemDesc);
		}
		throw new ItemDescNotFoundException("根据商品ID未找到合法的商品详情记录");
	}

}
