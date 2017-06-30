/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/easypay_ec">easypay_ec</a> All rights reserved.
 */
package com.uib.product.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;

/**
 * 商品属性Entity
 * @author gaven
 * @version 2015-06-04
 */
public class PropertyGroup implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String id;
	private String groupNo;		// 属性组编号
	private Integer orders;		// 排序
	private String name;		// 商品属性组名称
	private String productCategoryNo;		// 绑定分类
	private String merchantNo;		// 商户号
	private String productCategoryId;		// 商品分类主键ID
	private List<ProductProperty> productPropertyList = Lists.newArrayList();		// 子表列表

	private String remarks; // 备注
	private String createBy; // 创建者
	private Date createDate; // 创建日期
	private String updateBy; // 更新者
	private Date updateDate; // 更新日期
	private String delFlag; // 删除标记（0：正常；1：删除；2：审核）
	
	public PropertyGroup() {
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGroupNo() {
		return groupNo;
	}

	public void setGroupNo(String groupNo) {
		this.groupNo = groupNo;
	}

	public Integer getOrders() {
		return orders;
	}

	public void setOrders(Integer orders) {
		this.orders = orders;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProductCategoryNo() {
		return productCategoryNo;
	}

	public void setProductCategoryNo(String productCategoryNo) {
		this.productCategoryNo = productCategoryNo;
	}

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public String getProductCategoryId() {
		return productCategoryId;
	}

	public void setProductCategoryId(String productCategoryId) {
		this.productCategoryId = productCategoryId;
	}

	public List<ProductProperty> getProductPropertyList() {
		return productPropertyList;
	}

	public void setProductPropertyList(List<ProductProperty> productPropertyList) {
		this.productPropertyList = productPropertyList;
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
	
	private boolean isSelected;

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
	
}