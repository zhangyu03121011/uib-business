package com.uib.common.bean;

import java.util.List;

public class ShoppingCartMsg {
	
	private String quantity;
	
	
	private List<CartItemVo> cartItemVoList;
	
	
	private String effectivePoint;
	
	
	private String effectivePrice;
	
	private String promotions;
	
	private String isLowStock;
	
	
	public ShoppingCartMsg(){
		
	}
	
	
	public String getEffectivePoint() {
		return effectivePoint;
	}

	public void setEffectivePoint(String effectivePoint) {
		this.effectivePoint = effectivePoint;
	}

	public String getEffectivePrice() {
		return effectivePrice;
	}

	public void setEffectivePrice(String effectivePrice) {
		this.effectivePrice = effectivePrice;
	}

	public String getPromotions() {
		return promotions;
	}

	public void setPromotions(String promotions) {
		this.promotions = promotions;
	}

	public String getIsLowStock() {
		return isLowStock;
	}

	public void setIsLowStock(String isLowStock) {
		this.isLowStock = isLowStock;
	}


	/**
	 * 类型
	 */
	public enum Type {

		/** 成功 */
		success,

		/** 警告 */
		warn,

		/** 错误 */
		error
	}

	/** 类型 */
	private Type type;

	/** 内容 */
	private String content;

	

	/**
	 * 初始化一个新创建的 Message 对象
	 * 
	 * @param type
	 *            类型
	 * @param content
	 *            内容
	 */
	public ShoppingCartMsg(Type type, String content) {
		this.type = type;
		this.content = content;
	}

	/**
	 * @param type
	 *            类型
	 * @param content
	 *            内容
	 * @param args
	 *            参数
	 */
	public ShoppingCartMsg(Type type, String content, Object... args) {
		this.type = type;
		this.content = SpringUtil.getMessage(content, args);
	}
	
	/**
	 * 根据自定义参数组织表现层 主要删除购物车时用
	 * @param type
	 * @param content
	 * @param quantity
	 * @param args
	 */
	public ShoppingCartMsg(Type type, String content,String quantity,String effectivePoint,String effectivePrice,String isLowStock,List<CartItemVo> cartItemVoList, Object... args) {
		this.type = type;
		this.content = SpringUtil.getMessage(content, args);
		this.quantity = quantity;
		this.cartItemVoList = cartItemVoList;
		this.effectivePoint = effectivePoint;
		this.effectivePrice = effectivePrice;
		this.isLowStock = isLowStock;
	}
	
	/**
	 * 根据自定义参数组织表现层
	 * @param type
	 * @param content
	 * @param quantity
	 * @param args
	 */
	public ShoppingCartMsg(Type type, String content,String quantity,List<CartItemVo> cartItemVoList, Object... args) {
		this.type = type;
		this.content = SpringUtil.getMessage(content, args);
		this.quantity = quantity;
		this.cartItemVoList = cartItemVoList;
	}
	

	/**
	 * 返回成功消息
	 * 
	 * @param content
	 *            内容
	 * @param args
	 *            参数
	 * @return 成功消息
	 */
	public static ShoppingCartMsg success(String content, Object... args) {
		return new ShoppingCartMsg(Type.success, content, args);
	}
	
	/**
	 * 返回成功消息
	 * 
	 * @param content
	 *            内容
	 * @param args
	 *            参数
	 * @return 成功消息
	 */
	public static ShoppingCartMsg success(String content,String quantity,List<CartItemVo> cartItemVoList, Object... args) {
		return new ShoppingCartMsg(Type.success, content,quantity,cartItemVoList, args);
	}
	
	/**
	 * 返回成功消息
	 * 
	 * @param content
	 *            内容
	 * @param args
	 *            参数
	 * @return 成功消息
	 */
	public static ShoppingCartMsg delSuccess(String content,String quantity,String effectivePoint,String effectivePrice,String isLowStock,List<CartItemVo> cartItemVoList, Object... args) {
		return new ShoppingCartMsg(Type.success, content,quantity,effectivePoint,effectivePrice,isLowStock,cartItemVoList, args);
	}
	

	/**
	 * 返回警告消息
	 * 
	 * @param content
	 *            内容
	 * @param args
	 *            参数
	 * @return 警告消息
	 */
	public static ShoppingCartMsg warn(String content, Object... args) {
		return new ShoppingCartMsg(Type.warn, content, args);
	}

	/**
	 * 返回错误消息
	 * 
	 * @param content
	 *            内容
	 * @param args
	 *            参数
	 * @return 错误消息
	 */
	public static ShoppingCartMsg error(String content, Object... args) {
		return new ShoppingCartMsg(Type.error, content, args);
	}

	
	

	public List<CartItemVo> getCartItemVoList() {
		return cartItemVoList;
	}

	public void setCartItemVoList(List<CartItemVo> cartItemVoList) {
		this.cartItemVoList = cartItemVoList;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Override
	public String toString() {
		return content;
	}
}
