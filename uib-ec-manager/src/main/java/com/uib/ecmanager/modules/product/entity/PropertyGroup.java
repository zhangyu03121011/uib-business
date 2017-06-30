/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.product.entity;

import org.hibernate.validator.constraints.Length;

import java.util.List;

import com.google.common.collect.Lists;
import com.uib.ecmanager.common.persistence.DataEntity;

/**
 * 商品属性Entity
 * @author gaven
 * @version 2015-06-04
 */
public class PropertyGroup extends DataEntity<PropertyGroup> {
	
	private static final long serialVersionUID = 1L;
	private String groupNo;		// 属性组编号
	private Integer orders;		// 排序
	private String name;		// 商品属性组名称
	private String productCategoryNo;		// 绑定分类
	private String merchantNo;		// 商户号
	private String isFiltre; // 是否作为筛选条件 0.不作为 1 .作为'
	private String productCategoryId;		// 商品分类主键ID
	private String productCategoryName;
	private List<ProductProperty> productPropertyList = Lists.newArrayList();		// 子表列表
	
	public String getIsFiltre() {
		return isFiltre;
	}

	public void setIsFiltre(String isFiltre) {
		this.isFiltre = isFiltre;
	}

	public PropertyGroup() {
		super();
	}

	public PropertyGroup(String id){
		super(id);
	}
	

	public PropertyGroup(String id,
			String productCategoryId) {
		super(id);
		this.productCategoryId = productCategoryId;
	}

	@Length(min=0, max=32, message="属性组编号长度必须介于 0 和 32 之间")
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
	
	@Length(min=0, max=32, message="商品属性组名称长度必须介于 0 和 32 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=32, message="绑定分类长度必须介于 0 和 32 之间")
	public String getProductCategoryNo() {
		return productCategoryNo;
	}

	public void setProductCategoryNo(String productCategoryNo) {
		this.productCategoryNo = productCategoryNo;
	}
	
	@Length(min=0, max=64, message="商户号长度必须介于 0 和 64 之间")
	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}
	
	@Length(min=1, max=64, message="商品分类主键ID长度必须介于 1 和 64 之间")
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

	//针对显示选择
	private Boolean selected;
	public Boolean getSelected() {
		return selected;
	}
	public void setSelected(Boolean selected) {
		this.selected = selected;
	}

	public String getProductCategoryName() {
		return productCategoryName;
	}

	public void setProductCategoryName(String productCategoryName) {
		this.productCategoryName = productCategoryName;
	}
	
}