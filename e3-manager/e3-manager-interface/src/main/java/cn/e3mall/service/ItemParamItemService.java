package cn.e3mall.service;

import cn.e3mall.common.util.E3Result;

/**
 * 商品规格和商品的关系service
 * @author Dragon
 *
 */
public interface ItemParamItemService {
	/**
	 * 根据商品id获取商品规格和商品的关系
	 * @param itemId 商品id
	 * @return TbItemParamItem对象
	 */
	E3Result getItemParamItemByItemId(long itemId);
}
