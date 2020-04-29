package cn.e3mall.cart.controller;


import cn.e3mall.common.util.CookieUtils;
import cn.e3mall.common.util.E3Result;
import cn.e3mall.common.util.JsonUtils;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.service.ItemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 购物车控制器
 */
@Controller
public class CartController {
    @Autowired
    private ItemService itemService;
    @Value("${cart.cookie.key}")
    private String cookieKey;
    @Value("${cart.cookie.expire}")
    private Integer cookieExpire;
    /**
     * 将指定商品加入购物车。
     * @param itemId 商品ID
     * @param num 需要加入购物车的商品的数量
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/cart/add/{itemId}")
    public String addCartItem(@PathVariable Long itemId, Integer num, HttpServletRequest request, HttpServletResponse response){
        List<TbItem> itemList = getCartList(request);
        boolean hasItem =false;
        if(itemList != null && !itemList.isEmpty()){
            for (TbItem tbItem : itemList) {
                if(tbItem.getId() == itemId.longValue()){
                    tbItem.setNum(tbItem.getNum() + num);
                    hasItem = true;
                    break;
                }
            }
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
        return "cartSuccess";
    }

    /**
     * 展示购物车内的商品
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("/cart/cart")
    public String showCartList(HttpServletRequest request, Model model){
        List<TbItem> cartList = getCartList(request);
        model.addAttribute("cartList",cartList);
        return "cart";
    }

    @RequestMapping("/cart/update/num/{itemId}/{num}")
    @ResponseBody
    public E3Result updateNum(@PathVariable Long itemId,@PathVariable Integer num,HttpServletRequest request,HttpServletResponse response){
        List<TbItem> itemList = getCartList(request);
        boolean hasItem =false;
        if(itemList != null && !itemList.isEmpty()){
            for (TbItem tbItem : itemList) {
                if(tbItem.getId() == itemId.longValue()){
                    tbItem.setNum(num);
                    break;
                }
            }
        }
        CookieUtils.setCookie(request,response,cookieKey,JsonUtils.objectToJson(itemList),cookieExpire,"utf-8");
        return E3Result.ok();
    }

    @RequestMapping("/cart/delete/{itemId}")
    public String deleteCartItem(@PathVariable Long itemId,HttpServletRequest request,HttpServletResponse response){
        List<TbItem> cartList = getCartList(request);
        for (TbItem tbItem : cartList) {
            if (tbItem.getId() == itemId.longValue()) {
                // 4、删除商品。
                cartList.remove(tbItem);
                break;
            }
        }
        CookieUtils.setCookie(request, response, cookieKey, JsonUtils.objectToJson(cartList), cookieExpire, "utf-8");
        return "redirect:/cart/cart.html";
    }

    private List<TbItem> getCartList(HttpServletRequest request) {
        String json = CookieUtils.getCookieValue(request, cookieKey,true);
        if(StringUtils.isNotBlank(json)){
            List<TbItem> itemList = JsonUtils.jsonToList(json, TbItem.class);
            return itemList;
        }
        return new ArrayList<>();
    }
}
