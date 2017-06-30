package com.uib.mobile.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.uib.common.web.Global;
import com.uib.serviceUtils.Utils;

public class CartItemPojo4Mobile {
	private String id;
	private Integer quantity; // 数量
	// private List<String> specificationValue; //规格值
	private String productId; // 商品id
	private String productName; // 商品name
	private BigDecimal price; // 商品价格
	private BigDecimal marketPrice; // 市场价
	private Integer weight; // 商品重量
	private Long point; // 积分
	private String image; // 商品图片
	private BigDecimal subtotal; // 小计
	private String isMarketable; //是否上架 1:上架 0:下架

	private static final String baseUrl = Global.getConfig("upload.image.path");// 图片路径

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

	/*
	 * public List<String> getSpecificationValue() { return specificationValue;
	 * } public void setSpecificationValue(List<String> specificationValue) {
	 * this.specificationValue = specificationValue; }
	 */
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public BigDecimal getPrice() {
		return (Utils.isBlank(price) ? new BigDecimal(0) : price).setScale(2,
				RoundingMode.HALF_UP);
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getMarketPrice() {
		return (Utils.isBlank(marketPrice) ? new BigDecimal(0) : marketPrice)
				.setScale(2, RoundingMode.HALF_UP);
	}

	public void setMarketPrice(BigDecimal marketPrice) {
		this.marketPrice = marketPrice;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public Long getPoint() {
		return point;
	}

	public void setPoint(Long point) {
		this.point = point;
	}

	public String getImage() {
		return baseUrl + image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public BigDecimal getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}

	public String getIsMarketable() {
		return isMarketable;
	}

	public void setIsMarketable(String isMarketable) {
		this.isMarketable = isMarketable;
	}

}
