/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.mem.entity;

import org.hibernate.validator.constraints.Length;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.uib.ecmanager.common.persistence.DataEntity;

import javax.validation.constraints.NotNull;

/**
 * 会员卖家信息Entity
 * @author kevin
 * @version 2015-05-28
 */
public class MemMerchant extends DataEntity<MemMerchant> {
	
	private static final long serialVersionUID = 1L;
	private String merchantNo;		// 商户编号
	private String merchantName;		// 商户名称
	private Date registerDate;		// 入驻时间
	private Date effectiveDate;		// 有效期时间
	private String templateNum;		// 模板名称
	private String merchantPage;		// 主页地址
	private String contactName;		// 联系人姓名
	private String email;		// 联系人邮箱
	private String phone;		// 手机号
	private String memberId;		// 引用member主健
	private String ext1;		// ext1
	private String ext2;		// ext2
	private String ext3;		// ext3
	
	public MemMerchant() {
		super();
	}

	public MemMerchant(String id){
		super(id);
	}

	@Length(min=1, max=20, message="商户编号长度必须介于 1 和 20 之间")
	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}
	
	@Length(min=1, max=20, message="商户名称长度必须介于 1 和 20 之间")
	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="入驻时间不能为空")
	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="有效期时间不能为空")
	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	
	@Length(min=0, max=32, message="模板名称长度必须介于 0 和 32 之间")
	public String getTemplateNum() {
		return templateNum;
	}

	public void setTemplateNum(String templateNum) {
		this.templateNum = templateNum;
	}
	
	@Length(min=0, max=32, message="主页地址长度必须介于 0 和 32 之间")
	public String getMerchantPage() {
		return merchantPage;
	}

	public void setMerchantPage(String merchantPage) {
		this.merchantPage = merchantPage;
	}
	
	@Length(min=0, max=20, message="联系人姓名长度必须介于 0 和 20 之间")
	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	
	@Length(min=0, max=32, message="联系人邮箱长度必须介于 0 和 32 之间")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@Length(min=0, max=11, message="手机号长度必须介于 0 和 11 之间")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Length(min=1, max=64, message="引用member主健长度必须介于 1 和 64 之间")
	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	
	@Length(min=0, max=32, message="ext1长度必须介于 0 和 32 之间")
	public String getExt1() {
		return ext1;
	}

	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}
	
	@Length(min=0, max=32, message="ext2长度必须介于 0 和 32 之间")
	public String getExt2() {
		return ext2;
	}

	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}
	
	@Length(min=0, max=32, message="ext3长度必须介于 0 和 32 之间")
	public String getExt3() {
		return ext3;
	}

	public void setExt3(String ext3) {
		this.ext3 = ext3;
	}
	
}