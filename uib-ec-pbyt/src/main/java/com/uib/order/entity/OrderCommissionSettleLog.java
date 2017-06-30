package com.uib.order.entity;

import java.util.Date;

/**
 * 订单佣金结算记录dto
 * @author chengw
 *
 */
public class OrderCommissionSettleLog {

	// 订单编号
	private String orderNo; 
	
	//订单商品是否记录到佣金记录表 0-未记录，1-已记录
	private String status; 
	
	private Date createTime;
	
	private Date updateTime;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
    
}
