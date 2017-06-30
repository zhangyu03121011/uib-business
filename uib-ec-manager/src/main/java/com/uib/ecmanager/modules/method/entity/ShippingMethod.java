/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.method.entity;

import java.beans.Transient;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.google.common.collect.Lists;
import com.uib.ecmanager.common.persistence.DataEntity;
import com.uib.ecmanager.modules.delivery.entity.DeliveryCorporation;
import com.uib.ecmanager.modules.order.entity.OrderTableItem;

/**
 * 配送方式Entity
 * @author limy
 * @version 2015-07-15
 */
public class ShippingMethod extends DataEntity<ShippingMethod> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 名称
	private Integer firstweight;		// 首重量
	private Integer continueweight;		// 续重量
	private Double firstprice;		// 首重价格
	private Double continueprice;		// 续重价格
	private String icon;		// 图标
	private String description;		// 介绍
	private DeliveryCorporation defaultdeliverycorp;		// 默认物流公司
	private String orders;		// 排序
	private List<PaymentMethod> paymentMethodList = Lists.newArrayList();		// 子表列表

	public ShippingMethod() {
		super();
	}

	public ShippingMethod(String id){
		super(id);
	}

	@Length(min=0, max=32, message="名称长度必须介于 0 和 32 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getFirstweight() {
		return firstweight;
	}

	public void setFirstweight(Integer firstweight) {
		this.firstweight = firstweight;
	}
	
	public Integer getContinueweight() {
		return continueweight;
	}

	public void setContinueweight(Integer continueweight) {
		this.continueweight = continueweight;
	}
	
	public Double getFirstprice() {
		return firstprice;
	}

	public void setFirstprice(Double firstprice) {
		this.firstprice = firstprice;
	}
	
	public Double getContinueprice() {
		return continueprice;
	}

	public void setContinueprice(Double continueprice) {
		this.continueprice = continueprice;
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
	
	@Transient
	public DeliveryCorporation getDefaultdeliverycorp() {
		return defaultdeliverycorp;
	}

	public void setDefaultdeliverycorp(DeliveryCorporation defaultdeliverycorp) {
		this.defaultdeliverycorp = defaultdeliverycorp;
	}
	
	@Length(min=0, max=64, message="排序长度必须介于 0 和 64 之间")
	public String getOrders() {
		return orders;
	}

	public void setOrders(String orders) {
		this.orders = orders;
	}

	public List<PaymentMethod> getPaymentMethodList() {
		return paymentMethodList;
	}

	public void setPaymentMethodList(List<PaymentMethod> paymentMethodList) {
		this.paymentMethodList = paymentMethodList;
	}
	
}