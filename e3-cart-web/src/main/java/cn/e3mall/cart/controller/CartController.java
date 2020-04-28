package cn.e3mall.cart.controller;


import cn.e3mall.cart.service.CartService;
import cn.e3mall.common.util.E3Result;
import cn.e3mall.pojo.TbItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 购物车控制器
 */
@Controller
public class CartController {
    @Autowired
    private CartService cartService;

    /**
     * 将指定商品加入购物车
     * @param itemId 商品ID
     * @param num 需要加入购物车的商品的数量
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/cart/add/{itemId}")
    public String addCartItem(@PathVariable Long itemId, Integer num, HttpServletRequest request, HttpServletResponse response){
        cartService.addCartItem(itemId,num,request,response);
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
        List<TbItem> cartList = cartService.getCartList(request);
        model.addAttribute("cartList",cartList);
        return "cart";
    }

    @RequestMapping("/cart/update/num/{itemId}/{num}")
    public E3Result updateNum(@PathVariable Long itemId,@PathVariable Integer num,HttpServletRequest request,HttpServletResponse response){
        cartService.addCartItem(itemId,num,request,response);
        return E3Result.ok();
    }
}
