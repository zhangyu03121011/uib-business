/**
 * Copyright &copy; 2012-2014 <a href="http://www.easypay.com.hk/easypayicp">easypayicp</a> All rights reserved.
 */
package com.uib.member.dto;

import java.util.Date;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;



/**
 * 保单(订单)信息Entity
 * @author ChenYu
 * @version 2015-06-06
 */
public class TInsureInfo  {
	
	private static final long serialVersionUID = 1L;
	private String id;
	private Date startDate;		// 投保开始时间
	private Date endDate;		// 投保结束时间
	private String policyholderName;		// 投保人姓名
	private String policyholderCardtype;		// 投保人证件类型
	private String policyholderCardno;		// 投保人证件号码
	private String policyholderPhoneno;		// 投保人手机号码
	private String policyholderEmail;		// 投保人邮箱
	private String policyholderRelation;		// 与被保人关系
	private String insuredName;		// 被保人姓名
	private String insuredCardtype;		// 被保人证件类型
	private String insuredCardno;		// 被保人证件号码
	private String insuredAge;// 被保险人年龄
	private String insuredBirth;		// 被保险人出生日期
	private String insuredAddress;		// 被保险人地址
	private String insuredSchool;		// 被保人学校名称
	private String insuredSchooladdress;		// 被保人学校所在地
	private String insuredType;		// 学校类型
	private String insuredLearnyear;		// 学习年制
	private String beneficiary;		// 受益人
	private String productCode;		// 产品代码
	private String authorizationApp;		// 授权APP
	private String authorizationClient;		// 授权用户
	private String insureCode;		// 投保单编号
	private String policyCode;		// 保单编号
	private String status;		// 状态
	
	private String remarks;	// 备注
	private String createBy;	// 创建者
	private String createDate;	// 创建日期
	private String updateBy;	// 更新者
	private String updateDate;	// 更新日期
	private String delFlag; 	// 删除标记（0：正常；1：删除；2：审核）
	private String isNewRecord;
	
	
	
	public String getIsNewRecord() {
		return isNewRecord;
	}

	public void setIsNewRecord(String isNewRecord) {
		this.isNewRecord = isNewRecord;
	}

	// 扩展字段
	private String insuredSchooladdress2;// 学校详细地址
	
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	@Length(min=1, max=100, message="详细地址不能为空")
	public String getInsuredSchooladdress2() {
		return insuredSchooladdress2;
	}

	public void setInsuredSchooladdress2(String insuredSchooladdress2) {
		this.insuredSchooladdress2 = insuredSchooladdress2;
	}


	@DateTimeFormat(pattern="yyyy-MM-dd")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	@Length(min=1, max=32, message="请正确填写投保人姓名")
	public String getPolicyholderName() {
		return policyholderName;
	}

	public void setPolicyholderName(String policyholderName) {
		this.policyholderName = policyholderName;
	}
	
	@Length(min=1, max=2, message="投保人证件类型长度必须介于 0 和 2 之间")
	public String getPolicyholderCardtype() {
		return policyholderCardtype;
	}

	public void setPolicyholderCardtype(String policyholderCardtype) {
		this.policyholderCardtype = policyholderCardtype;
	}
	
	//@Length(min=0, max=32, message="投保人证件号码长度必须介于 0 和 32 之间")
	@Pattern(regexp="^(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])$",message="身份证号码格式不正确")
	public String getPolicyholderCardno() {
		return policyholderCardno;
	}

	public void setPolicyholderCardno(String policyholderCardno) {
		this.policyholderCardno = policyholderCardno;
	}
	
	//@Length(min=0, max=32, message="投保人手机号码长度必须介于 0 和 32 之间")
	@Pattern(regexp="^(13[4,5,6,7,8,9]|15[0,8,9,1,7]|188|187)\\d{8}$",message="电话号码格式不正确")
	public String getPolicyholderPhoneno() {
		return policyholderPhoneno;
	}

	public void setPolicyholderPhoneno(String policyholderPhoneno) {
		this.policyholderPhoneno = policyholderPhoneno;
	}
	
	//@Length(min=0, max=32, message="投保人邮箱长度必须介于 0 和 32 之间")
	@Pattern(regexp="^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$",message="邮箱地址为是不正确")
	public String getPolicyholderEmail() {
		return policyholderEmail;
	}

	public void setPolicyholderEmail(String policyholderEmail) {
		this.policyholderEmail = policyholderEmail;
	}
	
	@Length(min=1, max=2, message="与被保人关系长度必须介于 0 和 2 之间")
	public String getPolicyholderRelation() {
		return policyholderRelation;
	}

	public void setPolicyholderRelation(String policyholderRelation) {
		this.policyholderRelation = policyholderRelation;
	}
	
	@Length(min=2, max=32, message="请填写被保人姓名")
	public String getInsuredName() {
		return insuredName;
	}

	public void setInsuredName(String insuredName) {
		this.insuredName = insuredName;
	}
	
	@Length(min=1, max=2, message="被保人证件类型长度必须介于 0 和 2 之间")
	public String getInsuredCardtype() {
		return insuredCardtype;
	}

	public void setInsuredCardtype(String insuredCardtype) {
		this.insuredCardtype = insuredCardtype;
	}
	
	@Pattern(regexp="^(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])$",message="身份证号码格式不正确")
	public String getInsuredCardno() {
		return insuredCardno;
	}

	public void setInsuredCardno(String insuredCardno) {
		this.insuredCardno = insuredCardno;
	}
	
	//@Length(min=1, max=3, message="被保险人年龄不正确")
	@DecimalMin(value="1")
	@DecimalMax(value="199")
	public String getInsuredAge() {
		return insuredAge;
	}

	public void setInsuredAge(String insuredAge) {
		this.insuredAge = insuredAge;
	}

	@Length(min=10, max=32, message="被保险人出生日期长度必须介于 0 和 32 之间")
	public String getInsuredBirth() {
		return insuredBirth;
	}

	public void setInsuredBirth(String insuredBirth) {
		this.insuredBirth = insuredBirth;
	}
	
	@Length(min=2, max=255, message="被保险人地址长度必须介于 2 和 255 之间")
	public String getInsuredAddress() {
		return insuredAddress;
	}

	public void setInsuredAddress(String insuredAddress) {
		this.insuredAddress = insuredAddress;
	}
	
	@Length(min=2, max=255, message="被保人学校名称长度必须介于 2 和 255 之间")
	public String getInsuredSchool() {
		return insuredSchool;
	}

	public void setInsuredSchool(String insuredSchool) {
		this.insuredSchool = insuredSchool;
	}
	
	@Length(min=2, max=255, message="被保人学校所在地长度必须介于 2 和 255 之间")
	public String getInsuredSchooladdress() {
		return insuredSchooladdress;
	}

	public void setInsuredSchooladdress(String insuredSchooladdress) {
		this.insuredSchooladdress = insuredSchooladdress;
	}
	
	@Length(min=1, max=2, message="学校类型长度必须介于 0 和 2 之间")
	public String getInsuredType() {
		return insuredType;
	}

	public void setInsuredType(String insuredType) {
		this.insuredType = insuredType;
	}
	
	@Length(min=0, max=4, message="学习年制长度必须介于 0 和 4 之间")
	public String getInsuredLearnyear() {
		return insuredLearnyear;
	}

	public void setInsuredLearnyear(String insuredLearnyear) {
		this.insuredLearnyear = insuredLearnyear;
	}
	
	@Length(min=0, max=20, message="受益人长度必须介于 0 和 20 之间")
	public String getBeneficiary() {
		return beneficiary;
	}

	public void setBeneficiary(String beneficiary) {
		this.beneficiary = beneficiary;
	}
	
	@Length(min=0, max=32, message="产品代码长度必须介于 0 和 32 之间")
	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	
	@Length(min=1, max=32, message="授权APP长度必须介于 1 和 32 之间")
	public String getAuthorizationApp() {
		return authorizationApp;
	}

	public void setAuthorizationApp(String authorizationApp) {
		this.authorizationApp = authorizationApp;
	}
	
	@Length(min=1, max=32, message="授权用户长度必须介于 1 和 32 之间")
	public String getAuthorizationClient() {
		return authorizationClient;
	}

	public void setAuthorizationClient(String authorizationClient) {
		this.authorizationClient = authorizationClient;
	}
	
	@Length(min=1, max=50, message="投保单编号长度必须介于 1 和 50 之间")
	public String getInsureCode() {
		return insureCode;
	}

	public void setInsureCode(String insureCode) {
		this.insureCode = insureCode;
	}
	
	@Length(min=1, max=50, message="保单编号长度必须介于 1 和 50 之间")
	public String getPolicyCode() {
		return policyCode;
	}

	public void setPolicyCode(String policyCode) {
		this.policyCode = policyCode;
	}
	
	@Length(min=1, max=1, message="状态长度必须介于 1 和 1 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}