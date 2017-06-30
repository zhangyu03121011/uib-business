/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.product.entity;

import org.hibernate.validator.constraints.Length;

import com.uib.ecmanager.common.persistence.DataEntity;

/**
 * 商品参数组Entity
 * @author gaven
 * @version 2015-05-28
 */
public class ProductParameter extends DataEntity<ProductParameter> {
	
	private static final long serialVersionUID = 1L;
	private String parameterNo;		// 参数编号
	private Integer orders;		// 排序
	private String name;		// 参数名称
	private String groupNo;		// 参数组编号
	private String merchantNo;		// 商户号
	private ParameterGroup parameterGroup;		// 参数组主键Id 父类
	
	public ProductParameter() {
		super();
	}

	public ProductParameter(String id){
		super(id);
	}

	public ProductParameter(ParameterGroup parameterGroup){
		this.parameterGroup = parameterGroup;
	}

	@Length(min=1, max=32, message="参数编号长度必须介于 1 和 32 之间")
	public String getParameterNo() {
		return parameterNo;
	}

	public void setParameterNo(String parameterNo) {
		this.parameterNo = parameterNo;
	}
	
	public Integer getOrders() {
		return orders;
	}

	public void setOrders(Integer orders) {
		this.orders = orders;
	}
	
	@Length(min=0, max=32, message="参数名称长度必须介于 0 和 32 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=32, message="参数组编号长度必须介于 0 和 32 之间")
	public String getGroupNo() {
		return groupNo;
	}

	public void setGroupNo(String groupNo) {
		this.groupNo = groupNo;
	}
	
	@Length(min=0, max=64, message="商户号长度必须介于 0 和 64 之间")
	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}
	
	@Length(min=1, max=64, message="参数组主键Id长度必须介于 1 和 64 之间")
	public ParameterGroup getParameterGroup() {
		return parameterGroup;
	}

	public void setParameterGroup(ParameterGroup parameterGroup) {
		this.parameterGroup = parameterGroup;
	}
	
}