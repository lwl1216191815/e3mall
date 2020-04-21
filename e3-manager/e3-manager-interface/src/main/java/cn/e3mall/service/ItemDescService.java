package cn.e3mall.service;

import cn.e3mall.common.exception.ItemDescNotFoundException;
import cn.e3mall.common.util.E3Result;

/**
 * 商品详情service
 * @author Dragon
 *
 */
public interface ItemDescService {
	/**
	 * 根据商品编号获取商品详情
	 * @param itemId
	 * @return
	 * @throws ItemDescNotFoundException 
	 */
	E3Result getDescByItemId(long itemId) throws ItemDescNotFoundException;
}
