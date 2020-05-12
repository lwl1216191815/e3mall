package cn.e3mall.order.controller;

import cn.e3mall.cart.service.CartService;
import cn.e3mall.common.util.E3Result;
import cn.e3mall.order.pojo.OrderInfo;
import cn.e3mall.order.service.OrderService;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbUser;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class OrderController {
    @Autowired
    private CartService cartService;
    @Value("${user.key.in.request}")
    private String keyOfUserInRequest;
    @Autowired
    private OrderService orderService;

    @RequestMapping("/order/order-cart")
    public String showOrderCart(HttpServletRequest request){
        TbUser user = (TbUser)request.getAttribute(keyOfUserInRequest);
        List<TbItem> cartList = cartService.getCartList(user.getId());
        request.setAttribute("cartList",cartList);
        return "order-cart";
    }

    @RequestMapping(value="/order/create",method = RequestMethod.POST)
    public String createOrder(OrderInfo orderInfo,HttpServletRequest request){
        TbUser user = (TbUser) request.getAttribute(keyOfUserInRequest);
        orderInfo.setUserId(user.getId());
        orderInfo.setBuyerNick(user.getUsername());
        E3Result result = orderService.createOrder(orderInfo);
        //需要返回订单号、当前日期加3天
        request.setAttribute("orderId",result.getData().toString());
        request.setAttribute("date",new DateTime().plusDays(3));
        return "success";
    }
}
