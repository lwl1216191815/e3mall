package cn.e3mall.order.controller;

import cn.e3mall.cart.service.CartService;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class OrderController {
    @Autowired
    private CartService cartService;
    @Value("${user.key.in.request}")
    private String keyOfUserInRequest;

    @RequestMapping("/order/order-cart")
    public String showOrderCart(HttpServletRequest request){
        TbUser user = (TbUser)request.getAttribute(keyOfUserInRequest);
        List<TbItem> cartList = cartService.getCartList(user.getId());
        request.setAttribute("cartList",cartList);
        return "order-cart";
    }
}
