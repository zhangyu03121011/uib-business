package com.uib.union.payment.dto;

public class RefundB2cReq {
	
	//商户代码
	private String merchantId;
	
	//原订单号
	private String orderNo;
	
	//退款金额
	private String refundAmt;
	
	//退款方式
	private String refundType;
	
	//退款通知的URL
	private String notifyURL;

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getRefundAmt() {
		return refundAmt;
	}

	public void setRefundAmt(String refundAmt) {
		this.refundAmt = refundAmt;
	}

	public String getRefundType() {
		return refundType;
	}

	public void setRefundType(String refundType) {
		this.refundType = refundType;
	}

	public String getNotifyURL() {
		return notifyURL;
	}

	public void setNotifyURL(String notifyURL) {
		this.notifyURL = notifyURL;
	}
	
	
	

}
