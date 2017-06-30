/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.order.entity;

import java.beans.Transient;

import org.hibernate.validator.constraints.Length;

import com.uib.ecmanager.common.enums.Log_Type;
import com.uib.ecmanager.common.persistence.DataEntity;
import com.uib.ecmanager.modules.navigation.entity.Navigation.Tags;

/**
 * 订单日志Entity
 * @author limy
 * @version 2015-06-01
 */
public class OrderTableLog extends DataEntity<OrderTableLog> {
	
	private static final long serialVersionUID = 1L;
	private String content;		// 内容
	private String operator;		// 操作员
	private Integer type;		// 类型
	private String orderNo;		// 订单编号
	

	public OrderTableLog() {
		super();
	}

	public OrderTableLog(String id){
		super(id);
	}

	@Length(min=0, max=255, message="内容长度必须介于 0 和 255 之间")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Length(min=0, max=32, message="操作员长度必须介于 0 和 32 之间")
	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	@Length(min=1, max=32, message="类型长度必须介于 1 和 32 之间")
	@Transient
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	public String getTypeName(){
		return Log_Type.getDescription(type);
	}
	
	@Length(min=1, max=32, message="订单编号长度必须介于 1 和 32 之间")


	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
}