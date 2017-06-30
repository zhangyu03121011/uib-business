/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/easypay_ec">easypay_ec</a> All rights reserved.
 */
package com.uib.order.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 发货单Entity
 * @author limy
 * @version 2015-06-08
 */
public class OrderTableShippingItem implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String id;
	private OrderTableShipping orderTableShipping;		// 发货单ID 父类
	private String productNo;	//商品编号
	private String name;		// 商品名称
	private Integer quantity;		// 商品数量
	private String shippingNo;		// 发货单编号
	
	protected String remarks;	// 备注
	protected String createBy;	// 创建者
	protected Date createDate;	// 创建日期
	protected String updateBy;	// 更新者
	protected Date updateDate;	// 更新日期
	protected String delFlag; 	// 删除标记（0：正常；1：删除；2：审核）
	
	
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public OrderTableShippingItem() {
		super();
	}


	public OrderTableShipping getOrderTableShipping() {
		return orderTableShipping;
	}


	public void setOrderTableShipping(OrderTableShipping orderTableShipping) {
		this.orderTableShipping = orderTableShipping;
	}


	public String getProductNo() {
		return productNo;
	}


	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Integer getQuantity() {
		return quantity;
	}


	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}


	public String getShippingNo() {
		return shippingNo;
	}


	public void setShippingNo(String shippingNo) {
		this.shippingNo = shippingNo;
	}


	public String getRemarks() {
		return remarks;
	}


	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}


	public String getCreateBy() {
		return createBy;
	}


	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}


	public Date getCreateDate() {
		return createDate;
	}


	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}


	public String getUpdateBy() {
		return updateBy;
	}


	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}


	public Date getUpdateDate() {
		return updateDate;
	}


	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}


	public String getDelFlag() {
		return delFlag;
	}


	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

}