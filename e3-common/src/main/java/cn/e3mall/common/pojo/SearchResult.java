package cn.e3mall.common.pojo;

import java.io.Serializable;
import java.util.List;

public class SearchResult implements Serializable {
    /**
     * 查询到的结果集
     */
    private List<SearchItem> itemList;
    /**
     * 总页数
     */
    private int totalPage;
    /**
     * 总记录数
     */
    private int recordCount;

    public List<SearchItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<SearchItem> itemList) {
        this.itemList = itemList;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(int recordCount) {
        this.recordCount = recordCount;
    }
}
