package com.uib.union.payment.dto;

/**
 * 退货返回通知
 * @author kevin
 *
 */
public class RetufnRefundDto {
	
	private String interfaceName;
	
	private String refundOrder;
	
	private String version;
	
	private String tranData;
	
	private String signData;

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public String getRefundOrder() {
		return refundOrder;
	}

	public void setRefundOrder(String refundOrder) {
		this.refundOrder = refundOrder;
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
