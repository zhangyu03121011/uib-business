/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/easypay_ec">easypay_ec</a> All rights reserved.
 */
package com.uib.order.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;

/**
 * 发货单Entity
 * @author limy
 * @version 2015-06-08
 */
public class OrderTableShipping implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String id;
	private String shippingNo;		// 发货单编号
	private String address;		// 地址
	private String area;		// 地区
	private String consignee;		// 收货人
	private String deliveryCorp;		// 物流公司
	private String deliveryCorpCode;		// 物流公司代码
	private String deliveryCorpUrl;		// 物流公司网址
	private BigDecimal freight;		// 物流费用
	private String operator;		// 操作员
	private String phone;		// 电话
	private String shippingMethod;		// 配送方式
	private String trackingNo;		// 运单号
	private String zipCode;		// 邮编
	private String orderNo;		// 订单编号
	private List<OrderShippingRef> orderShippingRefList = Lists.newArrayList();		// 子表列表
	private List<OrderTableShippingItem> orderTableShippingItemList = Lists.newArrayList();		// 子表列表
	
	protected String remarks;	// 备注
	protected String createBy;	// 创建者
	protected Date createDate;	// 创建日期
	protected String updateBy;	// 更新者
	protected Date updateDate;	// 更新日期
	protected String delFlag; 	// 删除标记（0：正常；1：删除；2：审核）
	
	public OrderTableShipping() {
		super();
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getShippingNo() {
		return shippingNo;
	}

	public void setShippingNo(String shippingNo) {
		this.shippingNo = shippingNo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getConsignee() {
		return consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	public String getDeliveryCorp() {
		return deliveryCorp;
	}

	public void setDeliveryCorp(String deliveryCorp) {
		this.deliveryCorp = deliveryCorp;
	}

	public String getDeliveryCorpCode() {
		return deliveryCorpCode;
	}

	public void setDeliveryCorpCode(String deliveryCorpCode) {
		this.deliveryCorpCode = deliveryCorpCode;
	}

	public String getDeliveryCorpUrl() {
		return deliveryCorpUrl;
	}

	public void setDeliveryCorpUrl(String deliveryCorpUrl) {
		this.deliveryCorpUrl = deliveryCorpUrl;
	}

	public BigDecimal getFreight() {
		return freight;
	}

	public void setFreight(BigDecimal freight) {
		this.freight = freight;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getShippingMethod() {
		return shippingMethod;
	}

	public void setShippingMethod(String shippingMethod) {
		this.shippingMethod = shippingMethod;
	}

	public String getTrackingNo() {
		return trackingNo;
	}

	public void setTrackingNo(String trackingNo) {
		this.trackingNo = trackingNo;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public List<OrderShippingRef> getOrderShippingRefList() {
		return orderShippingRefList;
	}

	public void setOrderShippingRefList(List<OrderShippingRef> orderShippingRefList) {
		this.orderShippingRefList = orderShippingRefList;
	}

	public List<OrderTableShippingItem> getOrderTableShippingItemList() {
		return orderTableShippingItemList;
	}

	public void setOrderTableShippingItemList(
			List<OrderTableShippingItem> orderTableShippingItemList) {
		this.orderTableShippingItemList = orderTableShippingItemList;
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