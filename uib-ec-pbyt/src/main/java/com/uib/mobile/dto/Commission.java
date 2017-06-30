package com.uib.mobile.dto;

import java.util.Date;

public class Commission {
	private String id;			//ID
	private String username;	//被邀请人名字
	private String phone;		//被邀请人手机号
	private String price;		//商品金额
	private String commPercent;	//佣金百分比
	private String fromUserId;	//邀请人ID
	private String toUserId;	//被邀请人ID
	private String productId;	//商品ID
	private int settlement;		//是否结算(0：结算、1：未结算)
	private String  createDate;	//创建时间
	private String commission;	//佣金
	private String delFlag;		//删除标记（0：正常；1：删除）
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFromUserId() {
		return fromUserId;
	}
	public void setFromUserId(String fromUserId) {
		this.fromUserId = fromUserId;
	}
	public String getToUserId() {
		return toUserId;
	}
	public void setToUserId(String toUserId) {
		this.toUserId = toUserId;
	}
	public int getSettlement() {
		return settlement;
	}
	public void setSettlement(int settlement) {
		this.settlement = settlement;
	}

	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getCommission() {
		return commission;
	}
	public void setCommission(String commission) {
		this.commission = commission;
	}
	public String getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getCommPercent() {
		return commPercent;
	}
	public void setCommPercent(String commPercent) {
		this.commPercent = commPercent;
	}
}
