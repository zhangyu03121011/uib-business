/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.supplier.entity;

import org.hibernate.validator.constraints.Length;

import com.uib.ecmanager.common.persistence.DataEntity;

/**
 * 供应商管理Entity
 * @author luogc
 * @version 2016-08-22
 */
public class Supplier extends DataEntity<Supplier> {
	
	private static final long serialVersionUID = 1L;
	private String supplierNo;		// 供应商编号
	private String companyName;		// 公司名称
	private String companyAddr;		// 公司地址
	private String principalName;		// 负责人姓名
	private String principalPhone;		// 负责人手机号
	private String remark;		// 备注
	
	public Supplier() {
		super();
	}

	public Supplier(String id){
		super(id);
	}

	@Length(min=1, max=32, message="供应商编号长度必须介于 1 和 32 之间")
	public String getSupplierNo() {
		return supplierNo;
	}

	public void setSupplierNo(String supplierNo) {
		this.supplierNo = supplierNo;
	}
	
	@Length(min=0, max=64, message="公司名称长度必须介于 0 和 64 之间")
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	@Length(min=0, max=255, message="公司地址长度必须介于 0 和 255 之间")
	public String getCompanyAddr() {
		return companyAddr;
	}

	public void setCompanyAddr(String companyAddr) {
		this.companyAddr = companyAddr;
	}
	
	@Length(min=0, max=64, message="负责人姓名长度必须介于 0 和 64 之间")
	public String getPrincipalName() {
		return principalName;
	}

	public void setPrincipalName(String principalName) {
		this.principalName = principalName;
	}
	
	@Length(min=0, max=32, message="负责人手机号长度必须介于 0 和 32 之间")
	public String getPrincipalPhone() {
		return principalPhone;
	}

	public void setPrincipalPhone(String principalPhone) {
		this.principalPhone = principalPhone;
	}
	
	@Length(min=0, max=255, message="备注长度必须介于 0 和 255 之间")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}