/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.product.entity;

import org.hibernate.validator.constraints.Length;

import com.uib.ecmanager.common.persistence.DataEntity;

/**
 * 规格商品关联Entity
 * @author gaven
 * @version 2015-06-24
 */
public class ProductSpecificationRef extends DataEntity<ProductSpecificationRef> {
	
	private static final long serialVersionUID = 1L;
	private String specificationId;		// 规格主键ID
	private String productId;		// 商品主键ID
	
	public ProductSpecificationRef() {
		super();
	}

	public ProductSpecificationRef(String id){
		super(id);
	}

	@Length(min=1, max=64, message="规格主键ID长度必须介于 1 和 64 之间")
	public String getSpecificationId() {
		return specificationId;
	}

	public void setSpecificationId(String specificationId) {
		this.specificationId = specificationId;
	}
	
	@Length(min=1, max=64, message="商品主键ID长度必须介于 1 和 64 之间")
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
	
}