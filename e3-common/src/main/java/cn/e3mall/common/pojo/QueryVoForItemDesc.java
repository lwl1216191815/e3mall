package cn.e3mall.common.pojo;

import java.io.Serializable;

public class QueryVoForItemDesc implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Object itemDesc;
	
	public Object getItemDesc() {
		return itemDesc;
	}

	public void setItemDesc(Object itemDesc) {
		this.itemDesc = itemDesc;
	}

	public QueryVoForItemDesc(Object itemDesc) {
		this.itemDesc = itemDesc;
	}
}
