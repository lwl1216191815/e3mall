package cn.e3mall.cart.service;

import cn.e3mall.pojo.TbItem;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 购物车接口
 */
public interface CartService {

    List<TbItem> getCartList(HttpServletRequest request);

    void addCartItem(Long itemId, Integer num, HttpServletRequest request, HttpServletResponse response);
}
