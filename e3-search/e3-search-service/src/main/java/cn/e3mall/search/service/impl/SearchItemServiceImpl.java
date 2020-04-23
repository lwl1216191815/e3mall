package cn.e3mall.search.service.impl;

import java.util.List;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.e3mall.common.constant.SearchItemConstant;
import cn.e3mall.common.pojo.SearchItem;
import cn.e3mall.common.util.E3Result;
import cn.e3mall.search.mapper.ItemMapper;
import cn.e3mall.search.service.SearchItemService;
@Service
public class SearchItemServiceImpl implements SearchItemService {
	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private SolrClient solrClient;

	@Override
	public E3Result importItems() {
		try {
			List<SearchItem> itemList = itemMapper.getItemList();
			for(SearchItem item : itemList) {
				SolrInputDocument document = new SolrInputDocument();
				document.addField(SearchItemConstant.ID, item.getId());
				document.addField(SearchItemConstant.ITEM_TITLE, item.getTitle());
				document.addField(SearchItemConstant.ITEM_SELL_POINT, item.getSellPoint());
				document.addField(SearchItemConstant.ITEM_IMAGE, item.getImages());
				document.addField(SearchItemConstant.ITEM_PRICE, item.getPrice());
				document.addField(SearchItemConstant.ITEM_CATEGORY_NAME, item.getCategoryName());
				solrClient.add(document);
			}
			solrClient.commit();
			return E3Result.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return E3Result.build(500, "商品导入失败");
		}
	}

	public E3Result addDocument(Long itemId) throws Exception{
		if(itemId == null){
			throw new NullPointerException("itemId为null");
		}
		SearchItem item = itemMapper.getItemById(itemId);
		SolrInputDocument document = new SolrInputDocument();
		document.addField(SearchItemConstant.ID, item.getId());
		document.addField(SearchItemConstant.ITEM_TITLE, item.getTitle());
		document.addField(SearchItemConstant.ITEM_SELL_POINT, item.getSellPoint());
		document.addField(SearchItemConstant.ITEM_IMAGE, item.getImages());
		document.addField(SearchItemConstant.ITEM_PRICE, item.getPrice());
		document.addField(SearchItemConstant.ITEM_CATEGORY_NAME, item.getCategoryName());
		solrClient.add(document);
		solrClient.commit();
		return E3Result.ok();
	}

}
