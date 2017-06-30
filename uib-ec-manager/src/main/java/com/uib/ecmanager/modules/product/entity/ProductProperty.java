/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.product.entity;

import org.hibernate.validator.constraints.Length;

import com.uib.ecmanager.common.persistence.DataEntity;

/**
 * 商品属性Entity
 * @author gaven
 * @version 2015-06-04
 */
public class ProductProperty extends DataEntity<ProductProperty> {
	
	private static final long serialVersionUID = 1L;
	private String propertyNo;		// 属性编号
	private Integer orders;		// 排序
	private String name;		// 属性名称
	private String groupNo;		// 属性组编号，字段来自property_group表
	private String merchantNo;		// 商户号
	private String isFiltre; // 是否做为筛选条件
	private PropertyGroup PropertyGroup;		// 商品属性组ID 父类
	
	public String getIsFiltre() {
		return isFiltre;
	}

	public void setIsFiltre(String isFiltre) {
		this.isFiltre = isFiltre;
	}

	public ProductProperty() {
		super();
	}

	public ProductProperty(String id){
		super(id);
	}

	public ProductProperty(PropertyGroup PropertyGroup){
		this.PropertyGroup = PropertyGroup;
	}

	@Length(min=0, max=32, message="属性编号长度必须介于 0 和 32 之间")
	public String getPropertyNo() {
		return propertyNo;
	}

	public void setPropertyNo(String propertyNo) {
		this.propertyNo = propertyNo;
	}
	
	public Integer getOrders() {
		return orders;
	}

	public void setOrders(Integer orders) {
		this.orders = orders;
	}
	
	@Length(min=0, max=32, message="属性名称长度必须介于 0 和 32 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=32, message="属性组编号，字段来自property_group表长度必须介于 0 和 32 之间")
	public String getGroupNo() {
		return groupNo;
	}

	public void setGroupNo(String groupNo) {
		this.groupNo = groupNo;
	}
	
	@Length(min=0, max=32, message="商户号长度必须介于 0 和 32 之间")
	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}
	
	@Length(min=1, max=64, message="商品属性组ID长度必须介于 1 和 64 之间")
	public PropertyGroup getPropertyGroup() {
		return PropertyGroup;
	}

	public void setPropertyGroup(PropertyGroup PropertyGroup) {
		this.PropertyGroup = PropertyGroup;
	}
	
	private Boolean selected;//显示属性是否显示
	public Boolean getSelected() {
		return selected;
	}
	public void setSelected(Boolean selected) {
		this.selected = selected;
	}
}