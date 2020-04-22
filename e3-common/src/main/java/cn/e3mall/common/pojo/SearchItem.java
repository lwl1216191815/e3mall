package cn.e3mall.common.pojo;

import java.io.Serializable;

/**
 * 商品搜索pojo
 * @author Dragon
 *
 */
public class SearchItem implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 商品ID
	 */
	private String id;
	/**
	 * 商品标题
	 */
	private String title;
	/**
	 * 商品卖点
	 */
	private String sellPoint;
	/**
	 * 商品价格
	 */
	private Long price;
	/**
	 * 商品图片
	 */
	private String images;
	/**
	 * 商品类型名称
	 */
	private String categoryName;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSellPoint() {
		return sellPoint;
	}
	public void setSellPoint(String sellPoint) {
		this.sellPoint = sellPoint;
	}
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
	public String getImages() {
		return images;
	}
	public void setImages(String images) {
		this.images = images;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	
}
