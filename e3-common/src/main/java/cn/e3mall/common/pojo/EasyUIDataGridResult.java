package cn.e3mall.common.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * 用于返回前台的通用工具类
 */
public class EasyUIDataGridResult<T> implements Serializable {
    private  Long total;
    private List<T> rows;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
