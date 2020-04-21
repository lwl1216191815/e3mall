package cn.e3mall.common.pojo;

import java.io.Serializable;
/**
 * easyUI树形节点
 * @author Dragon
 *
 */
public class EasyUITreeNode implements Serializable {

	/**
	 * 默认的序列化
	 */
	private static final long serialVersionUID = 1L;
	
	private long id;
	
	private String text;
	
	private String state;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	

}
