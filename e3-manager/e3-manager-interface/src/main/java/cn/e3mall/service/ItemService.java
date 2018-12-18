package cn.e3mall.service;

import cn.e3mall.pojo.TbItem;

/**
 * 商品管理service接口
 */
public interface ItemService {
    /**
     * 根据商品ID获取商品信息
     * @param itemId
     * 商品ID
     * @return
     */
    TbItem getItemById(Long itemId);
}
