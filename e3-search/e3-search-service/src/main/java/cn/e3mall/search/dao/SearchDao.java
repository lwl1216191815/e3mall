package cn.e3mall.search.dao;

import cn.e3mall.common.constant.SearchItemConstant;
import cn.e3mall.common.pojo.SearchItem;
import cn.e3mall.common.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class SearchDao {
    @Autowired
    private SolrClient solrClient;

    public SearchResult searchItem(SolrQuery query) throws IOException, SolrServerException {
        //根据查询条件查询索引库
        QueryResponse response = solrClient.query(query);
        SolrDocumentList queryResult = response.getResults();
        SearchResult result = new SearchResult();
        long num = queryResult.getNumFound();
        //获取总记录数
        result.setRecordCount(Long.valueOf(num).intValue());
        List<SearchItem> itemList = new ArrayList<>(result.getRecordCount());
        //高亮结果集
        Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
        for (SolrDocument document : queryResult) {
            SearchItem item = new SearchItem();
            String itemId = document.get(SearchItemConstant.ID).toString();
            item.setId(itemId);
            item.setCategoryName(document.get(SearchItemConstant.ITEM_CATEGORY_NAME).toString());
            item.setImages(document.get(SearchItemConstant.ITEM_IMAGE).toString());
            item.setPrice(Long.valueOf(document.get(SearchItemConstant.ITEM_PRICE).toString()));
            item.setSellPoint(document.get(SearchItemConstant.ITEM_SELL_POINT).toString());
            String itemTitle = document.get(SearchItemConstant.ITEM_TITLE).toString();
            //获取高亮的结果
            List<String> list = highlighting.get(itemId).get(itemTitle);
            if (list != null && !list.isEmpty()) {
                itemTitle = list.get(0);
            }
            item.setTitle(itemTitle);
            itemList.add(item);
        }
        result.setItemList(itemList);
        return result;
    }
}
