package com.uib.mobile.dto;

import java.math.BigDecimal;

import com.uib.common.utils.StringUtils;
import com.uib.common.web.Global;

public class OrderItemDto {
	 private final  String FILE_PATH =	Global.getConfig("upload.image.path");
	private String fullName;		// 商品全称
	private String name;	//商品名
	private BigDecimal price;		// 商品价格
	private String thumbnail;		// 缩略图
	private Integer quantity;		// 商品数量
	private Integer allocatedStock;  //库存
	private String isMarketable;	//是否上下架
	private String goodNo;//商品编号
//	private String orderNo;		// 订单编号
	
	private String isComment;//是否已评价，0：未评价，1：已评价

	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public String getThumbnail() {
		String thumbnailPath = "";
		if (!StringUtils.isEmpty(thumbnail)){
			thumbnailPath= FILE_PATH+thumbnail;
		}
		return  thumbnailPath;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
//	public String getOrderNo() {
//		return orderNo;
//	}
//	public void setOrderNo(String orderNo) {
//		this.orderNo = orderNo;
//	}
	public Integer getAllocatedStock() {
		return allocatedStock;
	}
	public void setAllocatedStock(Integer allocatedStock) {
		this.allocatedStock = allocatedStock;
	}
	public String getIsMarketable() {
		return isMarketable;
	}
	public void setIsMarketable(String isMarketable) {
		this.isMarketable = isMarketable;
	}
	public String getGoodNo() {
		return goodNo;
	}
	public void setGoodNo(String goodNo) {
		this.goodNo = goodNo;
	}
	public String getIsComment() {
		return isComment;
	}
	public void setIsComment(String isComment) {
		this.isComment = isComment;
	}
	

}
