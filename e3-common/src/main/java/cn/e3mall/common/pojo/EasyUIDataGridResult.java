package cn.e3mall.common.pojo;

import java.io.Serializable;
import java.util.List;

public class EasyUIDataGridResult implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer total;
	
	private List<?> rows;
	/**
	 * 带有结果集和记录数的构造函数
	 * @param total 记录数 
	 * @param rows 结果集
	 */
	public EasyUIDataGridResult(Integer total, List<?> rows) {
		super();
		this.total = total;
		this.rows = rows;
	}
	/**
	 * 带有结果集和记录数的构造函数
	 * @param total 记录数 ,会转换为int类型
	 * @param rows 结果集
	 */
	public EasyUIDataGridResult(Long total,List<?> rows) {
		this(total.intValue(),rows);
	}
	
	public EasyUIDataGridResult() {
		
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public List<?> getRows() {
		return rows;
	}
	public void setRows(List<?> rows) {
		this.rows = rows;
	}
	
}
