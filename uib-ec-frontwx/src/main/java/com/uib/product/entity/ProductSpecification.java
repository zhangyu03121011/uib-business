/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/easypay_ec">easypay_ec</a> All rights reserved.
 */
package com.uib.product.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 商品规格Entity
 * @author gaven
 * @version 2015-06-13
 */
public class ProductSpecification implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String id;
	private String specificationNo;		// 商品规格编号
	private String orders;		// 排序
	private String name;		// 规格名称
	private String filePath;		// 规格文件路径
	private String specificationGroupNo;		// 商品规格组编号
	private String merchantNo;		// 商户号
	private SpecificationGroup SpecificationGroup;		// 规格组主键ID 父类
	
	private String remarks; // 备注
	private String createBy; // 创建者
	private Date createDate; // 创建日期
	private String updateBy; // 更新者
	private Date updateDate; // 更新日期
	private String delFlag; // 删除标记（0：正常；1：删除；2：审核）
	
	public ProductSpecification() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSpecificationNo() {
		return specificationNo;
	}

	public void setSpecificationNo(String specificationNo) {
		this.specificationNo = specificationNo;
	}

	public String getOrders() {
		return orders;
	}

	public void setOrders(String orders) {
		this.orders = orders;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getSpecificationGroupNo() {
		return specificationGroupNo;
	}

	public void setSpecificationGroupNo(String specificationGroupNo) {
		this.specificationGroupNo = specificationGroupNo;
	}

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public SpecificationGroup getSpecificationGroup() {
		return SpecificationGroup;
	}

	public void setSpecificationGroup(SpecificationGroup specificationGroup) {
		SpecificationGroup = specificationGroup;
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

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	
}