package com.uib.union.payment.dto;

/**
 * 退货Dto
 * @author kevin
 *
 */
public class RefundGoodsDto {
	
	private String interfaceName ;
	
	private String version ;
	
	private String tranData ;
	
	private	String merSignMsg ;
    
	private String merchantId ;

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

	public String getMerSignMsg() {
		return merSignMsg;
	}

	public void setMerSignMsg(String merSignMsg) {
		this.merSignMsg = merSignMsg;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	
	
	
	
	
}
