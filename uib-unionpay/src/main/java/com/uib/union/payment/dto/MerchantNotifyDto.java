package com.uib.union.payment.dto;

public class MerchantNotifyDto {
	
	private String interfaceName;
	
	private String version;
	
	
	private String tranData;
	
	
	private String signData;
	
	
	private String notifyUrl;
	
	private String returnUrl;
	
	
	private String orderNo;
	
	
	private String orderAmt;
	
	
	


	public String getOrderNo() {
		return orderNo;
	}


	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}


	


	public String getOrderAmt() {
		return orderAmt;
	}


	public void setOrderAmt(String orderAmt) {
		this.orderAmt = orderAmt;
	}


	public String getReturnUrl() {
		return returnUrl;
	}


	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}


	public String getNotifyUrl() {
		return notifyUrl;
	}


	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}


	public String getInterfaceName() {
		return interfaceName;
	}


	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}


	public String getVersion() {
		return version;
	}


	public void setVersion(String version) {
		this.version = version;
	}


	public String getTranData() {
		return tranData;
	}


	public void setTranData(String tranData) {
		this.tranData = tranData;
	}


	public String getSignData() {
		return signData;
	}


	public void setSignData(String signData) {
		this.signData = signData;
	}
	
	
	
}
