package com.uib.union.payment.dto;

public class PayNotifyDto {
	
	private String interfaceName;
	
	private String version;
	
	private String tranData;
	
	private String signData;

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
