package com.uib.mobile.dto;

import java.beans.Transient;
import java.math.BigDecimal;
import java.math.RoundingMode;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.uib.common.web.Global;

public class OrderItemPojo4Mobile {
	private String id;
	private String goodsNo; // 商品id
	private String fullName; // 商品全称
	private String name; // 商品名
	private BigDecimal price; // 商品价格
	private String thumbnail; // 缩略图
	private Integer quantity; // 商品数量
	private String orderNo; // 订单编号
	private static final String baseUrl = Global.getConfig("upload.image.path");// 图片路径
	private String isComment;//是否已评价，0：未评价，1：已评价
	private String wxIsMarketable;//微信是否已下架
	private Integer stock;//可用库存

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGoodsNo() {
		return goodsNo;
	}

	public void setGoodsNo(String goodsNo) {
		this.goodsNo = goodsNo;
	}

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
		return baseUrl + thumbnail;
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

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	/**
	 * 获取小计
	 * 
	 * @return 小计
	 */
	@JsonProperty
	@Transient
	public BigDecimal getSubtotal() {
		BigDecimal subTotal = new BigDecimal(0);
		if (getPrice() != null && getQuantity() != null) {
			subTotal = getPrice().multiply(new BigDecimal(getQuantity()));
		}
		return subTotal.setScale(2, RoundingMode.HALF_UP);
	}

	public String getIsComment() {
		return isComment;
	}

	public void setIsComment(String isComment) {
		this.isComment = isComment;
	}

	public String getWxIsMarketable() {
		return wxIsMarketable;
	}

	public void setWxIsMarketable(String wxIsMarketable) {
		this.wxIsMarketable = wxIsMarketable;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}
}
