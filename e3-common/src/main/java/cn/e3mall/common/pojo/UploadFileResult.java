package cn.e3mall.common.pojo;

import java.io.Serializable;

/**
 *  文件上传结果返回
 * @author Dragon
 *
 */
public class UploadFileResult implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 错误提示码
	 */
	private Integer error;
	/**
	 * 文件预览路径
	 */
	private String url;
	/**
	 * 错误提示信息
	 */
	private String message;
	public Integer getError() {
		return error;
	}
	public void setError(Integer error) {
		this.error = error;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public UploadFileResult(Integer error, String url) {
		super();
		this.error = error;
		this.url = url;
	}	
}
