/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.product.entity;

import org.hibernate.validator.constraints.Length;

import com.uib.ecmanager.common.persistence.DataEntity;

/**
 * 商品专题关联列表Entity
 * @author limy
 * @version 2016-07-14
 */
public class SpecialProductRef extends DataEntity<SpecialProductRef> {
	
	private static final long serialVersionUID = 1L;
	private String specialId;		// 专题编号
	private Product product;		// 商品编号
	private String productId;
	private String updateFlag;
	
	public SpecialProductRef() {
		super();
	}

	public SpecialProductRef(String id){
		super(id);
	}


	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getSpecialId() {
		return specialId;
	}

	public void setSpecialId(String specialId) {
		this.specialId = specialId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getUpdateFlag() {
		return updateFlag;
	}

	public void setUpdateFlag(String updateFlag) {
		this.updateFlag = updateFlag;
	}

	
}