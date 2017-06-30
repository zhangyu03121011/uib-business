package com.uib.ptyt.entity;

import java.io.Serializable;
import java.util.Date;

public class MemMerchantDto implements Serializable{

	private static final long serialVersionUID = 8274519679006161774L;
	
	private String id;                    //主健
	private String merchantNo;            //商户编号
	private String merchantName;          //商户名称
	private Date registerDate;            //入驻时间
	private Date effectiveDate;           //有效期时间
	private String templateNum;           //模板名称
	private String merchantPage;          //主页地址
	private String contactName;           //联系人姓名
	private String email;                 //联系人邮箱
	private String phone;                 //联系人手机号
	private String memberId;              //引用member主健
	private String createBy;              //创建者
	private Date   createDate;            //创建时间
	private String updateBy;              //更新者
	private Date   updateDate;            //更新时间
	private String remarks;               //备注信息
	private String delFlag;               //删除标记（0：正常；1：删除）
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMerchantNo() {
		return merchantNo;
	}
	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public Date getRegisterDate() {
		return registerDate;
	}
	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}
	public Date getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	public String getTemplateNum() {
		return templateNum;
	}
	public void setTemplateNum(String templateNum) {
		this.templateNum = templateNum;
	}
	public String getMerchantPage() {
		return merchantPage;
	}
	public void setMerchantPage(String merchantPage) {
		this.merchantPage = merchantPage;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
}
