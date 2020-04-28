package cn.e3mall.cart.service.impl;

import cn.e3mall.cart.service.CartService;
import cn.e3mall.common.util.CookieUtils;
import cn.e3mall.common.util.JsonUtils;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.service.ItemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
@Service
public class CartServiceImpl implements CartService {
    @Value("${cart.cookie.key}")
    private String cookieKey;
    @Value("${cart.cookie.expire}")
    private Integer cookieExpire;
    @Autowired
    private ItemService itemService;
    @Override
    public List<TbItem> getCartList(HttpServletRequest request) {
        String json = CookieUtils.getCookieValue(request, cookieKey,true);
        if(StringUtils.isNotBlank(json)){
            List<TbItem> itemList = JsonUtils.jsonToList(json, TbItem.class);
            return itemList;
        }
        return new ArrayList<>();
    }

    /**
     * 将商品加入购物车中，或者修改购物车汇总某件商品的数量
     * @param itemId 商品ID。如果购物车内没有的话就会加入
     * @param num 商品数量
     * @param request
     * @param response
     */
    @Override
    public void addCartItem(Long itemId, Integer num, HttpServletRequest request, HttpServletResponse response) {
        List<TbItem> itemList = getCartList(request);
        boolean hasItem = hasItem(itemId,itemList,num);
        if(!hasItem){
            TbItem item = itemService.getItemById(itemId);
            if(StringUtils.isNotBlank(item.getImage())){
                String[] images = item.getImage().split(",");
                item.setImage(images[0]);
            }
            item.setNum(num);
            itemList.add(item);
        }
        CookieUtils.setCookie(request,response,cookieKey,JsonUtils.objectToJson(itemList),cookieExpire,"utf-8");
    }

    /**
     * 删除购物车中的商品
     * @param itemId
     * @param request
     * @param response
     */
    public void deleteCartItem(Long itemId,HttpServletRequest request, HttpServletResponse response){
        List<TbItem> itemList = getCartList(request);
        hasItem(itemId,itemList,0);
        CookieUtils.setCookie(request,response,cookieKey,JsonUtils.objectToJson(itemList),cookieExpire,"utf-8");
    }

    /**
     * 判断<code>itemList<code/>是否有指定<code>itemId<code/>的商品。
     * 存在的返回true，不存在返回false。且如果<code>num<code/>大于0的话，
     * 将更新此商品的数量。否则将从<code>itemList<code/>移除此商品
     * @param itemId 商品的id
     * @param itemList 购物车内的商品列表
     * @param num 商品数量。大于0时更改商品数量，小于0的时候删除商品
     * @return
     */
    private boolean hasItem(Long itemId,List<TbItem> itemList,Integer num){
        if(itemList != null && !itemList.isEmpty()){
            for (TbItem tbItem : itemList) {
                if(tbItem.getId() == itemId.longValue()){
                    if ((num == null || num <= 0)) {
                        itemList.remove(tbItem);
                    } else {
                        tbItem.setNum(num);
                    }
                    return true;
                }
            }
        }
        return false;
    }
}
