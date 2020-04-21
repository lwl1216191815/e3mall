package cn.e3mall.search.mapper;

import java.util.List;

import cn.e3mall.common.pojo.SearchItem;

/**
 * 单独实现的ItemMapper
 * @author Dragon
 *
 */
public interface ItemMapper {
	
	/**
	 * 获取商品列表（搜索）
	 * @return
	 */
	List<SearchItem> getItemList();
}
