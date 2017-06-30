package com.uib.union.payment.dto;

public class ReturnRefundTranData {
	
	// 商户代码
	private String merchantId;
	
	// 原订单号
	private String orderNo;
	
	//退款流水号	
	private String tranSerialNo;
	
	//退款金额
	private String refundAmt;
	
	//商户退款金额
	private String merRefundAmt;
	
	//退还手续费
	private String refundFeeAmt;

	//退款方式
	private String refundType;
	
	//交易状态 AMEROQ02  订单未完成支付  AMEROQ03  总退款金额超出订单金额  AMEROQ04无此交易权限
	private String tranStat;

	//备注
	private String remark;

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

	public String getTranSerialNo() {
		return tranSerialNo;
	}

	public void setTranSerialNo(String tranSerialNo) {
		this.tranSerialNo = tranSerialNo;
	}

	public String getRefundAmt() {
		return refundAmt;
	}

	public void setRefundAmt(String refundAmt) {
		this.refundAmt = refundAmt;
	}

	public String getMerRefundAmt() {
		return merRefundAmt;
	}

	public void setMerRefundAmt(String merRefundAmt) {
		this.merRefundAmt = merRefundAmt;
	}

	public String getRefundFeeAmt() {
		return refundFeeAmt;
	}

	public void setRefundFeeAmt(String refundFeeAmt) {
		this.refundFeeAmt = refundFeeAmt;
	}

	public String getRefundType() {
		return refundType;
	}

	public void setRefundType(String refundType) {
		this.refundType = refundType;
	}

	public String getTranStat() {
		return tranStat;
	}

	public void setTranStat(String tranStat) {
		this.tranStat = tranStat;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	

	
}
