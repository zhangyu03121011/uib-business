/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.method.entity;


import java.beans.Transient;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.google.common.collect.Lists;
import com.uib.ecmanager.common.enums.Pay_Method;
import com.uib.ecmanager.common.persistence.DataEntity;

/**
 * 支付方式Entity
 * @author limy
 * @version 2015-07-15
 */
public class PaymentMethod extends DataEntity<PaymentMethod> {
	
	private static final long serialVersionUID = 1L;
	private Integer method;		// 方式
	private String name;		// 名称
	private Integer timeout;		// 超时时间
	private String orderNo;		// 订单表
	private String icon;		// 图标
	private String description;		// 介绍
	private String content;		// 内容
	private Integer orders;		// 排序
	private List<ShippingMethod> shippingMethodList = Lists.newArrayList();		// 子表列表

	public PaymentMethod() {
		super();
	}

	public PaymentMethod(String id){
		super(id);
	}

	@Transient
	public void setMethod(Integer method) {
		this.method = method;
	}
	public Integer getMethod() {
		return method;
	}
	public String getMethodName(){
		return method==null?null:Pay_Method.getDescription(method);
	}
	@Length(min=0, max=32, message="名称长度必须介于 0 和 32 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Transient
	public Integer getTimeout() {
		return timeout;
	}

	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}
	
	@Length(min=0, max=32, message="订单表长度必须介于 0 和 32 之间")
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	@Length(min=0, max=32, message="图标长度必须介于 0 和 32 之间")
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	@Length(min=0, max=255, message="介绍长度必须介于 0 和 255 之间")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Length(min=0, max=255, message="内容长度必须介于 0 和 255 之间")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	@Transient
	public Integer getOrders() {
		return orders;
	}

	public void setOrders(Integer orders) {
		this.orders = orders;
	}

	public List<ShippingMethod> getShippingMethodList() {
		return shippingMethodList;
	}

	public void setShippingMethodList(List<ShippingMethod> shippingMethodList) {
		this.shippingMethodList = shippingMethodList;
	}
	
}