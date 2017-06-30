/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.product.entity;

import org.hibernate.validator.constraints.Length;

import com.uib.ecmanager.common.persistence.DataEntity;

/**
 * 商品规格组关联Entity
 * @author gaven
 * @version 2015-11-17
 */
public class ProductSpecificationGroupRef extends DataEntity<ProductSpecificationGroupRef> {
	
	private static final long serialVersionUID = 1L;
	private String specificationGroupId;		// 规格组主键ID
	private String productId;		// 商品主键ID
	
	public ProductSpecificationGroupRef() {
		super();
	}

	public ProductSpecificationGroupRef(String id){
		super(id);
	}

	@Length(min=1, max=64, message="规格组主键ID长度必须介于 1 和 64 之间")
	public String getSpecificationGroupId() {
		return specificationGroupId;
	}

	public void setSpecificationGroupId(String specificationGroupId) {
		this.specificationGroupId = specificationGroupId;
	}
	
	@Length(min=1, max=64, message="商品主键ID长度必须介于 1 和 64 之间")
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
	
}