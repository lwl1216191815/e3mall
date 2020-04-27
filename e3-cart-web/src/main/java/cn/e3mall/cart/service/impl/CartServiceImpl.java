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
        return Collections.emptyList();
    }

    @Override
    public void addCartItem(Long itemId, Integer num, HttpServletRequest request, HttpServletResponse response) {
        List<TbItem> itemList = getCartList(request);
        boolean hasItem = false;
        if(itemList != null && !itemList.isEmpty()){
            for (TbItem tbItem : itemList) {
                if(tbItem.getId() == itemId.longValue()){
                    tbItem.setNum(num);
                    hasItem = true;
                    break;
                }
            }
        }else{
            itemList = new ArrayList<>();
        }
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
}
