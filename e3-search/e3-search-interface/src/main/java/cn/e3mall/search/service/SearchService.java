package cn.e3mall.search.service;

import cn.e3mall.common.pojo.SearchResult;

/**
 * �����ӿ�
 */
public interface SearchService {

    /**
     * ȫ�ļ���
     * @param keyWords �ؼ���
     * @param page ҳ��
     * @param rows ÿҳ�ļ�¼��
     * @return
     * @throws Exception
     */
    SearchResult search(String keyWords,Integer page,Integer rows) throws Exception;
}
