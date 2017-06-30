/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/easypay_ec">easypay_ec</a> All rights reserved.
 */
package com.uib.product.entity;

import java.io.Serializable;


/**
 * 商品参数值Entity
 * @author gaven
 * @version 2015-06-26
 */
public class ProductParameterRef implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String id;
	private String parameterId;		// 参数主键ID
	private String productId;		// 商品主键ID
	private String parameterValue;		// 参数值
	
	private ProductParameter productParameter;
	
	public ProductParameterRef() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParameterId() {
		return parameterId;
	}

	public void setParameterId(String parameterId) {
		this.parameterId = parameterId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getParameterValue() {
		return parameterValue;
	}

	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
	}

	public ProductParameter getProductParameter() {
		return productParameter;
	}

	public void setProductParameter(ProductParameter productParameter) {
		this.productParameter = productParameter;
	}
	
}