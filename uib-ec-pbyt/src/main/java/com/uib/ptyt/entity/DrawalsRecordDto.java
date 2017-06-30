package com.uib.ptyt.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DrawalsRecordDto implements Serializable{
	
	private static final long serialVersionUID = 8274519679006161774L;
	
	private String id;
	private String applyUserName;     //申请人用户名
	private String applyPhone;        //申请人手机号
	private String cardPersonName;    //持卡人姓名
	private String cardNo;            //提现卡号
	private Double applyAmount;       //提现金额
	private String applyStatus;       //提现状态,0:未处理,1:处理失败,2:处理成功
	private Date applyDate;           //申请提现时间
	private String provinceCity;      //所属城市
	private String bankName;          //提现银行
	private String branchBankName;    //分行名称
	private String bankTradeNo;       //银行交易流水
	private Date disposeDate;         //处理时间
	private String remark;            //备注
	private String operator;          //操作人
	private String memberId;       //申请人会员表Id
	private String isDefault;      //是否默认 1.是 2.否
	private String flag;           //是否有效 0.有效 1.无效
	
	
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getApplyUserName() {
		return applyUserName;
	}
	public void setApplyUserName(String applyUserName) {
		this.applyUserName = applyUserName;
	}
	public String getApplyPhone() {
		return applyPhone;
	}
	public void setApplyPhone(String applyPhone) {
		this.applyPhone = applyPhone;
	}
	public String getCardPersonName() {
		return cardPersonName;
	}
	public void setCardPersonName(String cardPersonName) {
		this.cardPersonName = cardPersonName;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public Double getApplyAmount() {
		return applyAmount;
	}
	public void setApplyAmount(Double applyAmount) {
		this.applyAmount = applyAmount;
	}
	public String getApplyStatus() {
		return applyStatus;
	}
	public void setApplyStatus(String applyStatus) {
		this.applyStatus = applyStatus;
	}
	public Date getApplyDate() {
		return applyDate;
	}
	
	/*用于页面展示*/
	public String getStrApplyDate(){
		if(null!=applyDate){
			return new SimpleDateFormat("YYYY-MM-dd").format(applyDate);
		}else{
			return "";
		}
	}
	
	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}
	public String getProvinceCity() {
		return provinceCity;
	}
	public void setProvinceCity(String provinceCity) {
		this.provinceCity = provinceCity;
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
	public String getBankTradeNo() {
		return bankTradeNo;
	}
	public void setBankTradeNo(String bankTradeNo) {
		this.bankTradeNo = bankTradeNo;
	}
	public Date getDisposeDate() {
		return disposeDate;
	}
	public void setDisposeDate(Date disposeDate) {
		this.disposeDate = disposeDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	
}
