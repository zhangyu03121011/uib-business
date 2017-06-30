package com.uib.ptyt.entity;

import java.io.Serializable;

public class GoodsDto implements Serializable{


	private static final long serialVersionUID = -2010663510989790680L;
    private  String orderNo;//订单编号
	private  String fullName;//商品名称
    private  String image;//商品图片
    private  String sellPice;//售价
    private  String quantity;//商品数量
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getSellPice() {
		return sellPice;
	}
	public void setSellPice(String sellPice) {
		this.sellPice = sellPice;
	}
    
}
