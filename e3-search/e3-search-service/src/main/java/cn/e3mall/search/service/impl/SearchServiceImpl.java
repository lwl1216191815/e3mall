package cn.e3mall.search.service.impl;

import cn.e3mall.common.constant.SearchItemConstant;
import cn.e3mall.common.pojo.SearchResult;
import cn.e3mall.search.dao.SearchDao;
import cn.e3mall.search.service.SearchService;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 实现全文检索
 */
@Service
public class SearchServiceImpl implements SearchService {

    @Value("${solr.default.query.field}")
    private String defaultField;
    @Autowired
    private SearchDao searchDao;
    @Override
    public SearchResult search(String keyWords, Integer page, Integer rows) throws Exception {
        // 1 创建一个solrQuery对象
        SolrQuery query = new SolrQuery();
        // 2 设置查询条件
        query.setQuery(keyWords);
        // 3 设置分页条件
        if(page == null || page < 0){
            page =1;
        }
        if(rows == null || rows < 0){
            rows = 20;
        }
        query.setStart((page - 1) * rows);
        // 设置默认的查询域
        query.set("df",defaultField);
        // 设置高亮显示
        query.setHighlight(true);
        query.addHighlightField(SearchItemConstant.ITEM_TITLE);
        query.setHighlightSimplePre("<em style=\"color:red\">");
        query.setHighlightSimplePost("</em>");
        SearchResult result = searchDao.searchItem(query);
        int recordCount = result.getRecordCount();
        int pages = recordCount / rows;
        if(recordCount % rows > 0){
            pages++;
        }
        result.setTotalPage(pages);
        return result;
    }
}
