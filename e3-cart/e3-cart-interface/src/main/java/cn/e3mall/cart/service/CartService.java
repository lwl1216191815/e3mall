package cn.e3mall.cart.service;

import cn.e3mall.common.util.E3Result;

/**
 * 购物车接口
 */
public interface CartService {
    /**
     * 将商品加入购物车
     * @param userId
     * @param itemId
     * @param num
     * @return
     */
    E3Result addCart(Long userId,Long itemId,Integer num);
}
