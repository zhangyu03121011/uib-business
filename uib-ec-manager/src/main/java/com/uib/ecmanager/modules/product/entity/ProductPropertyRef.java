/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.product.entity;

import org.hibernate.validator.constraints.Length;

import com.uib.ecmanager.common.persistence.DataEntity;

/**
 * 属性、商品关联Entity
 * @author gaven
 * @version 2015-06-29
 */
public class ProductPropertyRef extends DataEntity<ProductPropertyRef> {
	
	private static final long serialVersionUID = 1L;
	private String propertyId;		// 商品属性主键ID
	private String productId;		// 商品主键ID
	private ProductProperty property;
	
	public ProductPropertyRef() {
		super();
	}

	public ProductPropertyRef(String id){
		super(id);
	}

	@Length(min=1, max=64, message="商品属性主键ID长度必须介于 1 和 64 之间")
	public String getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(String propertyId) {
		this.propertyId = propertyId;
	}
	
	@Length(min=1, max=64, message="商品主键ID长度必须介于 1 和 64 之间")
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public ProductProperty getProperty() {
		return property;
	}

	public void setProperty(ProductProperty property) {
		this.property = property;
	}
	
}