package com.uib.union.payment.pojo;

import java.sql.Timestamp;

/**
 * 订单撤销信息
 * @author kevin
 *
 */
public class CancelOrder {
	
	private Integer 	id;
	
	/**
	 * 交易撤销订单流水
	 */
	private String  	cancelOrderNo;
	
	
	/**
	 * 支付流水号
	 */
	private String       paymentNo;
	
	/**
	 * 订单号
	 */
	private String       orderNo;
	
	/**
	 * 0:撤销中, 1: 撤销失败, 2:撤销成功
	 */
	private String       cancelState;
	
	/**
	 * 备注
	 */
	private String 		remark;
	
	/**
	 * 退款金额
	 */
	private Double  	cancelAmt;
	
	/**
	 * 返回通知URL
	 */
	private String  	notifyUrl;
	
	
	/**
	 * 支付流水号
	 */
	private String      threePaymentNo;
	
	/**
	 * 渠道类型　0:爱浓支付渠道 , 1: 银联支付渠道
	 */
	private String  	payType; 
	
	/**
	 * 请求时间
	 */
	private Timestamp	cancelDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCancelOrderNo() {
		return cancelOrderNo;
	}

	public void setCancelOrderNo(String cancelOrderNo) {
		this.cancelOrderNo = cancelOrderNo;
	}

	public String getPaymentNo() {
		return paymentNo;
	}

	public void setPaymentNo(String paymentNo) {
		this.paymentNo = paymentNo;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getCancelState() {
		return cancelState;
	}

	public void setCancelState(String cancelState) {
		this.cancelState = cancelState;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	

	

	public Double getCancelAmt() {
		return cancelAmt;
	}

	public void setCancelAmt(Double cancelAmt) {
		this.cancelAmt = cancelAmt;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getThreePaymentNo() {
		return threePaymentNo;
	}

	public void setThreePaymentNo(String threePaymentNo) {
		this.threePaymentNo = threePaymentNo;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public Timestamp getCancelDate() {
		return cancelDate;
	}

	public void setCancelDate(Timestamp cancelDate) {
		this.cancelDate = cancelDate;
	}
	
	
	
	
}
