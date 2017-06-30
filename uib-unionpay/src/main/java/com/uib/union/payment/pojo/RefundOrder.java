package com.uib.union.payment.pojo;

import java.sql.Timestamp;


/**
 * 退货信息
 * @author kevin
 *
 */
public class RefundOrder {
	 //    -- 主键
	private Integer	id;  
	
	/**
	 * 退货订单流水
	 */
	private String refundNo;
	
	// -- 支付流水号
	private String  paymentNo; 
	
	//  -- 0:退货中, 1: 退货失败, 2:退货成功
	private String  refundState;  
	
	//	   -- 备注
	private String  remark;  
	
	//退款金额
	private Double refundAmt;
	
	// --支付流水
	private String  threePaymentNo;
	
	// 银联交易流水号   交易的交易流水号 供查询用
	private String queryId;
	
	private String  notifyUrl;
	
	private String orderNo;
	
	private Timestamp refundDate;
	
	private String payType;
	
	/** 商户号 */
	private String merchantCode;
	
	
	

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getQueryId() {
		return queryId;
	}

	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Timestamp getRefundDate() {
		return refundDate;
	}

	public void setRefundDate(Timestamp refundDate) {
		this.refundDate = refundDate;
	}

	
	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPaymentNo() {
		return paymentNo;
	}

	public void setPaymentNo(String paymentNo) {
		this.paymentNo = paymentNo;
	}

	public String getRefundState() {
		return refundState;
	}

	public void setRefundState(String refundState) {
		this.refundState = refundState;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Double getRefundAmt() {
		return refundAmt;
	}

	public void setRefundAmt(Double refundAmt) {
		this.refundAmt = refundAmt;
	}

	public String getThreePaymentNo() {
		return threePaymentNo;
	}

	public void setThreePaymentNo(String threePaymentNo) {
		this.threePaymentNo = threePaymentNo;
	}

	public String getRefundNo() {
		return refundNo;
	}

	public void setRefundNo(String refundNo) {
		this.refundNo = refundNo;
	} 
	
	
	
	
	
}
