package cn.e3mall.cart.controller;


import cn.e3mall.cart.service.CartService;
import cn.e3mall.common.util.CookieUtils;
import cn.e3mall.common.util.E3Result;
import cn.e3mall.common.util.JsonUtils;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbUser;
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
    @Autowired
    private CartService cartService;
    @Value("${user.key.in.request}")
    private String userInRequest;
    /**
     * 将指定商品加入购物车。
     * 先判断用户是否已经登录。
     * 如果已经登录就将购物车加入redis中。
     * 否则就将购物车写入Cookie
     * @param itemId 商品ID
     * @param num 需要加入购物车的商品的数量
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/cart/add/{itemId}")
    public String addCartItem(@PathVariable Long itemId, Integer num, HttpServletRequest request, HttpServletResponse response){
        Object user = request.getAttribute(userInRequest);
        if(user != null){
            TbUser u = (TbUser) user;
            cartService.addCart(u.getId(),itemId,num);
            return "cartSuccess";
        }
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
    public String showCartList(HttpServletRequest request, Model model,HttpServletResponse response){
        //1 先从cookie中拿到购物车列表
        List<TbItem> cartList = getCartList(request);
        //2 判断用户是否登录
        TbUser user = (TbUser) request.getAttribute(userInRequest);
        //3 如果用户已经登录了
        if(user != null){
            //4 合并购物车
            cartService.mergeCart(user.getId(),cartList);
            //5 删除cookie中的购物车
            CookieUtils.deleteCookie(request,response,cookieKey);
            cartList = cartService.getCartList(user.getId());
        }
        model.addAttribute("cartList",cartList);
        return "cart";
    }

    @RequestMapping("/cart/update/num/{itemId}/{num}")
    @ResponseBody
    public E3Result updateNum(@PathVariable Long itemId,@PathVariable Integer num,HttpServletRequest request,HttpServletResponse response){
        TbUser user = (TbUser) request.getAttribute(userInRequest);
        if(user != null){
            E3Result result = cartService.updateCartNum(user.getId(), itemId, num);
            return result;
        }
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
        TbUser user = (TbUser) request.getAttribute(userInRequest);
        if(user != null){
            cartService.deleteCart(user.getId(),itemId);
            return "redirect:/cart/cart.html";
        }
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
