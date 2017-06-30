/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.product.entity;

import com.uib.ecmanager.common.persistence.DataEntity;
import com.uib.ecmanager.modules.sys.entity.Area;

/**
 * 商品区域关联表Entity
 * @author limy
 * @version 2016-08-22
 */
public class ProductAreaRef extends DataEntity<ProductAreaRef> {
	
	private static final long serialVersionUID = 1L;
	private String productId;		// 商品id
	private String areaId;		// 区域编号
	private Area area;
	
	public ProductAreaRef() {
		super();
	}

	public ProductAreaRef(String id){
		super(id);
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}


}