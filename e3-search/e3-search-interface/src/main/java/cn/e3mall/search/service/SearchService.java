package cn.e3mall.search.service;

import cn.e3mall.common.pojo.SearchResult;

/**
 * 搜索接口
 */
public interface SearchService {

    /**
     * 全文检索
     * @param keyWords 关键字
     * @param page 页码
     * @param rows 每页的记录数
     * @return
     * @throws Exception
     */
    SearchResult search(String keyWords,Integer page,Integer rows) throws Exception;
}
