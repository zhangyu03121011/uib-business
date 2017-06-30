package com.uib.union.payment.pojo;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.uib.union.enums.UibTranStatEnum;
import com.uib.union.enums.PayMethodEnum;

public class PaymentOrderLog {

	/** 主键 */
	private Long id;
	
	/** 订 单号 */
	private String orderNo;
	
	/** 支付流水号 */
	private String paymentNo;
	
	/** 创建时间 */
	private Timestamp createTime;
	
	/** 订单状态(0:未支付; 1:已支付; 2:失败; 4:已关闭; 5:取消;) */
	private String tranStat;
	
	/** 记录信息 */
	private String message;
	
	
	/** 交易金额 */
	private BigDecimal orderAmt;
	
	
	/** 第三方 支付流水号  */
	private String threePaymentNo;
	
	/**
	 * 支付方式
	 */
	private String payMethod;
	
	
	

	public String getTranStat() {
		return tranStat;
	}

	public void setTranStat(String tranStat) {
		this.tranStat = tranStat;
	}

	public String getPayMethod() {
		return payMethod;
	}

	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}

	
	public BigDecimal getOrderAmt() {
		return orderAmt;
	}

	public void setOrderAmt(BigDecimal orderAmt) {
		this.orderAmt = orderAmt;
	}

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

	

	

	public String getThreePaymentNo() {
		return threePaymentNo;
	}

	public void setThreePaymentNo(String threePaymentNo) {
		this.threePaymentNo = threePaymentNo;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	
	
}
