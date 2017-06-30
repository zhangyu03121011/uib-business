/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.delivery.entity;

import org.hibernate.validator.constraints.Length;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;
import com.uib.ecmanager.common.persistence.DataEntity;
import com.uib.ecmanager.modules.mem.entity.Deposit;
import com.uib.ecmanager.modules.method.entity.ShippingMethod;

/**
 * 物流公司Entity
 * @author gaven
 * @version 2015-06-08
 */
public class DeliveryCorporation extends DataEntity<DeliveryCorporation> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 名称
	private String url;		// 网址
	private String code;		// 代码
	private Integer orders;		// 排序
	private Date modifyDate;		// 修改日期
	private List<ShippingMethod> shippingMethodList = Lists.newArrayList();
	public DeliveryCorporation() {
		super();
	}

	public DeliveryCorporation(String id){
		super(id);
	}

	@Length(min=1, max=255, message="名称长度必须介于 1 和 255 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=255, message="网址长度必须介于 0 和 255 之间")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	@Length(min=0, max=255, message="代码长度必须介于 0 和 255 之间")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public Integer getOrders() {
		return orders;
	}

	public void setOrders(Integer orders) {
		this.orders = orders;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public List<ShippingMethod> getShippingMethodList() {
		return shippingMethodList;
	}

	public void setShippingMethodList(List<ShippingMethod> shippingMethodList) {
		this.shippingMethodList = shippingMethodList;
	}
	
}