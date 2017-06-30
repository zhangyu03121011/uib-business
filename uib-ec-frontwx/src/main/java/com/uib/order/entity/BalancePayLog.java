package com.uib.order.entity;

import java.math.BigDecimal;
import java.util.Date;

/***
 * 余额支付日志表
 * @author zfan
 *
 */
public class BalancePayLog {

	private String orderNo;
	
	private String userName;
	
	/** 余额扣款金额 */
	private BigDecimal amount;
	
	/** 支付状态  0-冻结、1-已支付、2-已退还   */
	private String status;
	
	private Date createDate;
	
	private Date updateDate;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	
}
