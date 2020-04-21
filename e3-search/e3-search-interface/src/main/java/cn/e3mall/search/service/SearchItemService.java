package cn.e3mall.search.service;


import cn.e3mall.common.util.E3Result;

/**
 * 商品全文检索service
 * @author Dragon
 *
 */
public interface SearchItemService {
	
	/**
	 * 将商品数据导入到solr索引库中
	 * @return
	 */
	E3Result importItems();
}
