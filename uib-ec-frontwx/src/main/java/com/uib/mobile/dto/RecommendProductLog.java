package com.uib.mobile.dto;

import java.util.Date;

public class RecommendProductLog {
	private String id;	//推荐记录id
	private String memberId;	//被推荐会员id
	private String recommendMemberId;	//推荐会员id
	private String productId;	//商品id
	private String orderNo;//订单编号
	private Integer settlement;//是否结算(0：初始状态、1：未结算、2：结算)
	private Date createTime;	//推荐记录时间
	private Character delFlag;	//删除标志
	private Double commPercent; //佣金比例
	private Double price; // 销售价格
	private Double commission; // 佣金
	private String paymentDate; //支付完成日期
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
 
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getRecommendMemberId() {
		return recommendMemberId;
	}
	public void setRecommendMemberId(String recommendMemberId) {
		this.recommendMemberId = recommendMemberId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Character getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(Character delFlag) {
		this.delFlag = delFlag;
	}
	public Integer getSettlement() {
		return settlement;
	}
	public void setSettlement(Integer settlement) {
		this.settlement = settlement;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public Double getCommPercent() {
		return commPercent;
	}
	public void setCommPercent(Double commPercent) {
		this.commPercent = commPercent;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Double getCommission() {
		return commission;
	}
	public void setCommission(Double commission) {
		this.commission = commission;
	}
	public String getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}
}
