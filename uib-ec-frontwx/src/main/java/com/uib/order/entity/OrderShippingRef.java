/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/easypay_ec">easypay_ec</a> All rights reserved.
 */
package com.uib.order.entity;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.constraints.Length;

/**
 * 发货单Entity
 * @author limy
 * @version 2015-06-08
 */
public class OrderShippingRef implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String id;
	private OrderTableShipping orderTableShipping;		// 发货主键ID 父类
	private String orderTabelId;		// 订单主键ID
	
	protected String remarks;	// 备注
	protected String createBy;	// 创建者
	protected Date createDate;	// 创建日期
	protected String updateBy;	// 更新者
	protected Date updateDate;	// 更新日期
	protected String delFlag; 	// 删除标记（0：正常；1：删除；2：审核）
	
	public OrderShippingRef() {
		super();
	}

	
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
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


	public OrderShippingRef(OrderTableShipping orderTableShipping){
		this.orderTableShipping = orderTableShipping;
	}

	@Length(min=1, max=64, message="发货主键ID长度必须介于 1 和 64 之间")

	public OrderTableShipping getOrderTableShipping() {
		return orderTableShipping;
	}

	public void setOrderTableShipping(OrderTableShipping orderTableShipping) {
		this.orderTableShipping = orderTableShipping;
	}
	
	@Length(min=1, max=64, message="订单主键ID长度必须介于 1 和 64 之间")
	public String getOrderTabelId() {
		return orderTabelId;
	}

	public void setOrderTabelId(String orderTabelId) {
		this.orderTabelId = orderTabelId;
	}

}