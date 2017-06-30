/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.product.entity;

import org.hibernate.validator.constraints.Length;

import com.uib.ecmanager.common.persistence.DataEntity;

/**
 * 商品参数值Entity
 * @author gaven
 * @version 2015-06-26
 */
public class ProductParameterRef extends DataEntity<ProductParameterRef> {
	
	private static final long serialVersionUID = 1L;
	private String parameterId;		// 参数主键ID
	private String productId;		// 商品主键ID
	private String parameterValue;		// 参数值
	
	public ProductParameterRef() {
		super();
	}

	public ProductParameterRef(String id){
		super(id);
	}

	@Length(min=1, max=64, message="参数主键ID长度必须介于 1 和 64 之间")
	public String getParameterId() {
		return parameterId;
	}

	public void setParameterId(String parameterId) {
		this.parameterId = parameterId;
	}
	
	@Length(min=1, max=64, message="商品主键ID长度必须介于 1 和 64 之间")
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	@Length(min=0, max=32, message="参数值长度必须介于 0 和 32 之间")
	public String getParameterValue() {
		return parameterValue;
	}

	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
	}
	
}