package com.uib.member.entity;

import java.sql.Timestamp;
import java.util.Date;

/**
 * 收货地址
 * @author kevin
 *
 */
public class MemReceiver {
	// 主健
    private String	id;
	// 地址
	private String address;
	// 区域名称
	private String areaName;
	// 联系人
	private String consignee;
	// 是否默认
	private boolean isDefault ;
	// 手机号
	private String phone;
	// 邮编
	private String zipCode;
	// 地区
	private String area;
	// 会员编号
	private MemMember memMember;
	
	
	
	// 创建者
	private String createBy;
	// 创建时间
	private Timestamp createDate;
	// 更新者
	private String updateBy ;
	// 更新时间
	private Timestamp updateDate;
	// 备注信息
	private String remarks ;
	// 删除标记（0：正常；1：删除）
	private String delFlag;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getConsignee() {
		return consignee;
	}
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}
	public boolean getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Timestamp getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public Timestamp getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Timestamp updateDate) {
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

	public MemMember getMemMember() {
		return memMember;
	}
	public void setMemMember(MemMember memMember) {
		this.memMember = memMember;
	}

	
	
	
	
}
