package cn.e3mall.common.exception;
/**
 * 找不到商品详情异常
 * @author Dragon
 *
 */
public class ItemDescNotFoundException extends Exception {
	String message;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ItemDescNotFoundException(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return this.message;
	}

}
