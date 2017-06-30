/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.mem.entity;

import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.uib.ecmanager.common.persistence.DataEntity;

/**
 * 提现管理表Entity
 * @author luogc
 * @version 2016-07-28
 */
public class WithdrawalApplyFor extends DataEntity<WithdrawalApplyFor> {
	
	private static final long serialVersionUID = 1L;
	private String applyUsername;		// 申请人用户名
	private String applyPhone;		// 申请人手机号
	private String cardPersonName;		// 持卡人姓名
	private String cardNo;		// 提现卡号
	private BigDecimal applyAmount;		// 提现金额
	private String applyStatus;		// 提现状态,0:未处理,1:处理失败,2:处理成功
	private Date applyDate;		// 申请提现时间
	private String bankName;    //提现银行
	private String branchBankName; //所属分行
	private String provinceCity;		// 所属城市
	private String bankTradeNo;		// 银行交易流水
	private Date disposeDate;		// 处理时间
	private String operator;		// 操作人
	private String remark;		// 备注
	
	private Date startDate;  //开始时间
	private Date endDate;	//结束时间
	
	private String memberId;  //申请人会员id
	private String isDefault;  //1.是  2.否 是否默认
	private String flag;       //0-有效 1-无效
	
	
	
	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public WithdrawalApplyFor() {
		super();
	}

	public WithdrawalApplyFor(String id){
		super(id);
	}

	@Length(min=0, max=64, message="申请人用户名长度必须介于 0 和 64 之间")
	public String getApplyUsername() {
		return applyUsername;
	}

	public void setApplyUsername(String applyUsername) {
		this.applyUsername = applyUsername;
	}
	
	@Length(min=0, max=11, message="申请人手机号长度必须介于 0 和 11 之间")
	public String getApplyPhone() {
		return applyPhone;
	}

	public void setApplyPhone(String applyPhone) {
		this.applyPhone = applyPhone;
	}
	
	@Length(min=0, max=32, message="持卡人姓名长度必须介于 0 和 32 之间")
	public String getCardPersonName() {
		return cardPersonName;
	}

	public void setCardPersonName(String cardPersonName) {
		this.cardPersonName = cardPersonName;
	}
	
	@Length(min=0, max=32, message="提现卡号长度必须介于 0 和 32 之间")
	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	
	public BigDecimal getApplyAmount() {
		return applyAmount;
	}

	public void setApplyAmount(BigDecimal applyAmount) {
		this.applyAmount = applyAmount;
	}
	
	@Length(min=0, max=1, message="提现状态,0:未处理,1:处理失败,2:处理成功长度必须介于 0 和 1 之间")
	public String getApplyStatus() {
		return applyStatus;
	}

	public void setApplyStatus(String applyStatus) {
		this.applyStatus = applyStatus;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}
	
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBranchBankName() {
		return branchBankName;
	}

	public void setBranchBankName(String branchBankName) {
		this.branchBankName = branchBankName;
	}

	@Length(min=0, max=64, message="所属城市长度必须介于 0 和 64 之间")
	public String getProvinceCity() {
		return provinceCity;
	}

	public void setProvinceCity(String provinceCity) {
		this.provinceCity = provinceCity;
	}
	
	@Length(min=0, max=64, message="银行交易流水长度必须介于 0 和 64 之间")
	public String getBankTradeNo() {
		return bankTradeNo;
	}

	public void setBankTradeNo(String bankTradeNo) {
		this.bankTradeNo = bankTradeNo;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getDisposeDate() {
		return disposeDate;
	}

	public void setDisposeDate(Date disposeDate) {
		this.disposeDate = disposeDate;
	}
	
	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	@Length(min=0, max=2000, message="remark长度必须介于 0 和 2000 之间")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
}