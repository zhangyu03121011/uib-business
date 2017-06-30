/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.product.entity;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

import com.uib.ecmanager.common.persistence.DataEntity;

/**
 * 商品规格Entity
 * 
 * @author gaven
 * @version 2015-06-13
 */
public class ProductSpecification extends DataEntity<ProductSpecification> {

	private static final long serialVersionUID = 1L;
	private String specificationNo; // 商品规格编号
	private String orders; // 排序
	private String name; // 规格名称
	private String filePath; // 规格文件路径
	private String specificationGroupNo; // 商品规格组编号
	private String merchantNo; // 商户号
	private SpecificationGroup SpecificationGroup; // 规格组主键ID 父类

	public ProductSpecification() {
		super();
	}

	public ProductSpecification(String id) {
		super(id);
	}

	public ProductSpecification(SpecificationGroup SpecificationGroup) {
		this.SpecificationGroup = SpecificationGroup;
	}

	@Length(min = 0, max = 32, message = "商品规格编号长度必须介于 0 和 32 之间")
	public String getSpecificationNo() {
		return specificationNo;
	}

	public void setSpecificationNo(String specificationNo) {
		this.specificationNo = specificationNo;
	}

	@Length(min = 0, max = 11, message = "排序长度必须介于 0 和 11 之间")
	public String getOrders() {
		return StringUtils.isBlank(orders)? "0" : orders;
	}

	public void setOrders(String orders) {
		this.orders = orders;
	}

	@Length(min = 0, max = 32, message = "规格名称长度必须介于 0 和 32 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Length(min = 0, max = 255, message = "规格文件路径长度必须介于 0 和 255 之间")
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	@Length(min = 0, max = 32, message = "商品规格组编号长度必须介于 0 和 32 之间")
	public String getSpecificationGroupNo() {
		return specificationGroupNo;
	}

	public void setSpecificationGroupNo(String specificationGroupNo) {
		this.specificationGroupNo = specificationGroupNo;
	}

	@Length(min = 0, max = 32, message = "商户号长度必须介于 0 和 32 之间")
	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	@Length(min = 1, max = 64, message = "规格组主键ID长度必须介于 1 和 64 之间")
	public SpecificationGroup getSpecificationGroup() {
		return SpecificationGroup;
	}

	public void setSpecificationGroup(SpecificationGroup SpecificationGroup) {
		this.SpecificationGroup = SpecificationGroup;
	}

	// 前端字段
	private boolean isSelected;

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
}