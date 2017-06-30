package com.uib.common.web;

/**
 * 接收接口
 * @author kevin
 *
 */
public class B2CRes {
	
	/** 分配给 uib的商户代码 */
	private String merchantId;
	
	/** 商户订单号 */
	private String orderNo;
	
	/** 第三方支付流水号 */
	private String tranSerialNo;
	
	/** 订单金额 */
	private String orderAmt;
	
	/** 支付币种   原输入值，目前只支持使用人民币（CNY）支付。取值： CNY */
	private String curType="CNY";
	
	/** 返回交易成功日期时间  格式为：YYYYMMDDHHmmss */
	private String tranTime;
	
	/** 订单处理状态   订单状态(0:未支付; 1:已支付; 2:失败; 4:已关闭; 5:取消;) */
	private String tranStat;
	
	/** 备注字段 */
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

	public String getOrderAmt() {
		return orderAmt;
	}

	public void setOrderAmt(String orderAmt) {
		this.orderAmt = orderAmt;
	}

	public String getCurType() {
		return curType;
	}

	public void setCurType(String curType) {
		this.curType = curType;
	}

	

	public String getTranTime() {
		return tranTime;
	}

	public void setTranTime(String tranTime) {
		this.tranTime = tranTime;
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
