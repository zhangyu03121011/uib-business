package com.uib.union.merchant.pojo;

public class MerchantInfo {
	
	/** 主键 */
	private Integer id;
	
	/** 审请商户号，邮客户自主去商户号 */
	private String merchantCode;
	
	/** 商户所对应的接口密码 */
	private String hmd5Password;
	
	/** 收款银行编号 */
	private String receiveBankId;
	
	/** 收款帐号 */
	private String bankCardNo;
	
	/** 备注 */
	private String remark;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getHmd5Password() {
		return hmd5Password;
	}

	public void setHmd5Password(String hmd5Password) {
		this.hmd5Password = hmd5Password;
	}

	public String getReceiveBankId() {
		return receiveBankId;
	}

	public void setReceiveBankId(String receiveBankId) {
		this.receiveBankId = receiveBankId;
	}

	public String getBankCardNo() {
		return bankCardNo;
	}

	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	
}
