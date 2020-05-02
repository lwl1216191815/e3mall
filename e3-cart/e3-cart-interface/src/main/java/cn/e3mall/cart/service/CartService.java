package cn.e3mall.cart.service;

import cn.e3mall.common.util.E3Result;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbUser;

import java.util.List;

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

    /**
     *  将cookie中的购物车合并到redis中
     * @param userId
     * @param itemList
     * @return
     */
    E3Result mergeCart(Long userId, List<TbItem> itemList);

    /**
     * 根据用户ID获取购物车列表
     * @param userId
     * @return
     */
    List<TbItem> getCartList(Long userId);

    /**
     * 更新购物车数量
     * @param userId
     * @param itemId
     * @param num
     * @return
     */
    E3Result updateCartNum(Long userId,Long itemId,Integer num);

    /**
     * 删除购物车
     * @param userId
     * @param itemId
     * @return
     */
    E3Result deleteCart(Long userId,Long itemId);
}
