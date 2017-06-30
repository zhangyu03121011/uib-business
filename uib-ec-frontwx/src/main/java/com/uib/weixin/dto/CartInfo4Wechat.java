package com.uib.weixin.dto;

import java.math.BigDecimal;

/**
 * @todo   微信购物车信息dto
 * @author chengw
 * @date   2015年12月29日
 * @time   下午1:22:49
 */
public class CartInfo4Wechat {
	
	private String cartId; //购物车id
	private String cartItemId; //购物车项id
	private String memberId; //会员id
	private String productId; // 商品id
	private String productName; // 商品name
	private String productFullName; // 商品副标题，带规格
	private String image; // 商品图片
	private Integer quantity; // 数量
	private Integer stock; //库存
	private BigDecimal price; // 商品价格
	private BigDecimal marketPrice; // 市场价
	private String wxIsMarketable; //商品微信端是否上架 0-下架；1-上架
	
	public String getCartId() {
		return cartId;
	}
	public void setCartId(String cartId) {
		this.cartId = cartId;
	}
	public String getCartItemId() {
		return cartItemId;
	}
	public void setCartItemId(String cartItemId) {
		this.cartItemId = cartItemId;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
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
	public String getProductFullName() {
		return productFullName;
	}
	public void setProductFullName(String productFullName) {
		this.productFullName = productFullName;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Integer getStock() {
		return stock;
	}
	public void setStock(Integer stock) {
		this.stock = stock;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public BigDecimal getMarketPrice() {
		return marketPrice;
	}
	public void setMarketPrice(BigDecimal marketPrice) {
		this.marketPrice = marketPrice;
	}
	public String getWxIsMarketable() {
		return wxIsMarketable;
	}
	public void setWxIsMarketable(String wxIsMarketable) {
		this.wxIsMarketable = wxIsMarketable;
	}

}


