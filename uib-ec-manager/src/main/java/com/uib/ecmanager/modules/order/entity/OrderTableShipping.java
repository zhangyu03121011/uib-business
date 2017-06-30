/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.order.entity;

import org.hibernate.validator.constraints.Length;

import java.beans.Transient;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;
import com.uib.ecmanager.common.persistence.DataEntity;

/**
 * 发货单Entity
 * @author limy
 * @version 2015-06-08
 */
public class OrderTableShipping extends DataEntity<OrderTableShipping> {
	
	private static final long serialVersionUID = 1L;
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
	private Date shippingDate;		// 电话
	private String shippingMethod;		// 配送方式
	private String trackingNo;		// 运单号
	private String zipCode;		// 邮编
	private String orderNo;		// 订单编号
	private String isRemarks;		// 是否备注
	private String exceptionRemarks;		// 异常备注
	private String orderId;
	private List<OrderShippingRef> orderShippingRefList = Lists.newArrayList();		// 子表列表
	private List<OrderTableShippingItem> orderTableShippingItemList = Lists.newArrayList();		// 子表列表
	
	public OrderTableShipping() {
		super();
	}

	public OrderTableShipping(String id){
		super(id);
	}

	@Length(min=0, max=32, message="发货单编号长度必须介于 0 和 32 之间")
	public String getShippingNo() {
		return shippingNo;
	}

	public void setShippingNo(String shippingNo) {
		this.shippingNo = shippingNo;
	}
	
	@Length(min=0, max=32, message="地址长度必须介于 0 和 32 之间")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@Length(min=0, max=32, message="地区长度必须介于 0 和 32 之间")
	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}
	
	@Length(min=0, max=32, message="收货人长度必须介于 0 和 32 之间")
	public String getConsignee() {
		return consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}
	
	@Length(min=0, max=32, message="物流公司长度必须介于 0 和 32 之间")
	public String getDeliveryCorp() {
		return deliveryCorp;
	}

	public void setDeliveryCorp(String deliveryCorp) {
		this.deliveryCorp = deliveryCorp;
	}
	
	@Length(min=0, max=32, message="物流公司代码长度必须介于 0 和 32 之间")
	public String getDeliveryCorpCode() {
		return deliveryCorpCode;
	}

	public void setDeliveryCorpCode(String deliveryCorpCode) {
		this.deliveryCorpCode = deliveryCorpCode;
	}
	
	@Length(min=0, max=32, message="物流公司网址长度必须介于 0 和 32 之间")
	public String getDeliveryCorpUrl() {
		return deliveryCorpUrl;
	}

	public void setDeliveryCorpUrl(String deliveryCorpUrl) {
		this.deliveryCorpUrl = deliveryCorpUrl;
	}
	@Transient
	public BigDecimal getFreight() {
		return freight;
	}
	public void setFreight(BigDecimal freight) {
		this.freight = freight;
	}

	
	
	@Length(min=0, max=32, message="操作员长度必须介于 0 和 32 之间")
	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	@Length(min=0, max=32, message="电话长度必须介于 0 和 32 之间")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Length(min=0, max=32, message="配送方式长度必须介于 0 和 32 之间")
	public String getShippingMethod() {
		return shippingMethod;
	}

	public void setShippingMethod(String shippingMethod) {
		this.shippingMethod = shippingMethod;
	}
	
	@Length(min=0, max=32, message="运单号长度必须介于 0 和 32 之间")
	public String getTrackingNo() {
		return trackingNo;
	}

	public void setTrackingNo(String trackingNo) {
		this.trackingNo = trackingNo;
	}
	
	@Length(min=0, max=32, message="邮编长度必须介于 0 和 32 之间")
	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	
	@Length(min=0, max=32, message="订单编号长度必须介于 0 和 32 之间")
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

	public void setOrderTableShippingItemList(List<OrderTableShippingItem> orderTableShippingItemList) {
		this.orderTableShippingItemList = orderTableShippingItemList;
	}

	public Date getShippingDate() {
		return shippingDate;
	}

	public void setShippingDate(Date shippingDate) {
		this.shippingDate = shippingDate;
	}

	public String getIsRemarks() {
		return isRemarks;
	}

	public void setIsRemarks(String isRemarks) {
		this.isRemarks = isRemarks;
	}

	public String getExceptionRemarks() {
		return exceptionRemarks;
	}

	public void setExceptionRemarks(String exceptionRemarks) {
		this.exceptionRemarks = exceptionRemarks;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

}