package com.uib.union.generation.pojo;

import java.sql.Timestamp;

/**
 * 代扣实体
 * @author kevin
 *
 */
public class Deductions {
	// 主键
	private Integer	id;  
	
	//代扣流水号
	private String  deductionsNo;
	
	//商户编号
	private String	merchantCode;
	
	//银行名称
	private String bankName;
	
	//银行编号
	private String bankCode;			  
	
	//户名
	private String accountName;
	
	//代扣卡号
	private String accountNo; 
	
	//代扣金额
	private Double deductionsAmt;
	
	// 商户请求时间
	private Timestamp requestTime;
	
	//代扣最新状态 0:未代扣  1:代扣中, 2:已代扣
	private String	deductionsState;
	
	//代扣时间
	private Timestamp deductionsTiem;
	
	//代扣成功时间
	private Timestamp successTime;
	
	//备注
	private String remark;
	
	
	
	

	public Double getDeductionsAmt() {
		return deductionsAmt;
	}

	public void setDeductionsAmt(Double deductionsAmt) {
		this.deductionsAmt = deductionsAmt;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDeductionsNo() {
		return deductionsNo;
	}

	public void setDeductionsNo(String deductionsNo) {
		this.deductionsNo = deductionsNo;
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public Timestamp getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(Timestamp requestTime) {
		this.requestTime = requestTime;
	}

	public String getDeductionsState() {
		return deductionsState;
	}

	public void setDeductionsState(String deductionsState) {
		this.deductionsState = deductionsState;
	}

	public Timestamp getDeductionsTiem() {
		return deductionsTiem;
	}

	public void setDeductionsTiem(Timestamp deductionsTiem) {
		this.deductionsTiem = deductionsTiem;
	}

	public Timestamp getSuccessTime() {
		return successTime;
	}

	public void setSuccessTime(Timestamp successTime) {
		this.successTime = successTime;
	}
	
	
	
}
