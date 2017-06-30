package com.uib.common.bean;


public class CartItemVo {
	
	private String id;
	
	/** 数量 */
	private Integer quantity;
	
	/** 名称 */
	private String name;
	
	/** 销售价 */
	private String price;
	
	/**
	 * 图片路径
	 */
	private String thumbnail;
	
	
	/**
	 * 商品路径 
	 */
	private String productPath;
	
	/**
	 * 商品id
	 */
	private String productId;
	
	
	

	public String getProductPath() {
		return productPath;
	}

	public void setProductPath(String productPath) {
		this.productPath = productPath;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
	
}
