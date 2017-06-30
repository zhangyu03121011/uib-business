package com.uib.union.payment.dto;

import java.sql.Timestamp;

public class PaymentOrderLogDto {

	/** 主键 */
	private Long id;
	
	/** 支付流水号 */
	private String paymentNo;
	
	/** 创建时间 */
	private Timestamp createTime;
	
	/** 状态 0:失败, 1:成功 */
	private String state;
	
	/** 记录信息 */
	private String message;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPaymentNo() {
		return paymentNo;
	}

	public void setPaymentNo(String paymentNo) {
		this.paymentNo = paymentNo;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
}
