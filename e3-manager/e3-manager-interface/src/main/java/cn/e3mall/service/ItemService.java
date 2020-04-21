package cn.e3mall.service;

import java.util.List;

import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.common.util.E3Result;
import cn.e3mall.pojo.TbItem;
/**
 * 商品service
 * @author Dragon
 *
 */
public interface ItemService {
	/**
	 * 根据商品Id获取商品
	 * @param itemId
	 * @return
	 */
	TbItem getItemById(long itemId);
	/**
	 * 分页获取商品信息
	 * @param page 当前页码
	 * @param rows 每页需要的记录数
	 * @return
	 */
	EasyUIDataGridResult getItemList(int page, int rows);
	/**
	 * 添加商品
	 * @param item
	 * @param itemDesc
	 * @return
	 */
	E3Result addItem(TbItem item, String itemDesc);
	/**
	 * 修改商品
	 * @param item
	 * @param itemDesc
	 * @return
	 */
	E3Result editItem(TbItem item, String itemDesc);
	/**
	 * 根据商品ID删除商品
	 * @param itemIds
	 * @return
	 */
	E3Result deleteItemByIds(List<Long> itemIds);
	/**
	 * 下架商品，根据商品ID
	 * @param itemIds
	 * @return
	 */
	E3Result instockItem(List<Long> itemIds);
	/**
	 * 上架商品，根据商品ID
	 * @param itemIds
	 * @return
	 */
	E3Result reshelfItem(List<Long> itemIds);
}
