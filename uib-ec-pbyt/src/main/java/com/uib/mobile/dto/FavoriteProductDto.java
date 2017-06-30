package com.uib.mobile.dto;

import java.math.BigDecimal;

public class FavoriteProductDto {
	
	private String id; //id编号
	private String name; //会员编号
	private String allocatedStock; //商品编号
	private BigDecimal price;		// 商品价格
	private String pid;//商品ID
	private String image;//商品图片
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAllocatedStock() {
		return allocatedStock;
	}
	public void setAllocatedStock(String allocatedStock) {
		this.allocatedStock = allocatedStock;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
}
