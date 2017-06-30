package com.uib.union.payment.pojo;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.uib.union.enums.UibTranStatEnum;
import com.uib.union.enums.PayMethodEnum;

public class PaymentOrder {
	 
	 /** 主键 */
	 private Long id;
	 
	 /** 支付流水号 */
	 private String  paymentNo;               
    
	 /** 商户号 */
	 private String  merchantCode;                       
	 
	 /** 订单号 */
	 private String  orderNo;
	 
	 /** -- 交易金额 */
     private BigDecimal  orderAmt;                                
     
     /** -- 商品名称 */
     private String  goodsName;                       
	 
     /** -- 商品说明 */
     private String  goodsDesc;
     
     /** 邮箱地址 */
	 private String  mailUserName;                     
	 
	 /** 币种类型 */
	 private String  curType;
	 
	 /** -- 银行编号 */
	 private String  bankId;  
	 
	 /** -- 支付返回商户数据的地址 */
	 private String  returnUrl;          
	
	 /** -- 支付成功返回消息的地址（后台返回） */
	 private String  notifyUrl;
	 
	 /** -- 备注 */
	 private String  remark;
	 
	 /** 爱农驿站支付流水号 */
	 private String threePaymentNo;
	 
	 /** 最新状态 订单状态(0:未支付; 1:已支付; 2:失败; 4:已关闭; 5:取消;) */
	 private String tranStat;
	 
	 
	/** 返回交易成功日期时间 */
	 private Timestamp tranDate;
	 
	 /**
	  * 支付方式
	  */
	 private String payMethod;
	 
	 
	 
	 /**
	  * 查询ID
	  */
	 private String queryId;

	
	
	 

	public String getQueryId() {
		return queryId;
	}

	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}

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

	public Timestamp getTranDate() {
		return tranDate;
	}

	public void setTranDate(Timestamp tranDate) {
		this.tranDate = tranDate;
	}

	public String getThreePaymentNo() {
		return threePaymentNo;
	}

	public void setThreePaymentNo(String threePaymentNo) {
		this.threePaymentNo = threePaymentNo;
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


	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	

	public BigDecimal getOrderAmt() {
		return orderAmt;
	}

	public void setOrderAmt(BigDecimal orderAmt) {
		this.orderAmt = orderAmt;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getGoodsDesc() {
		return goodsDesc;
	}

	public void setGoodsDesc(String goodsDesc) {
		this.goodsDesc = goodsDesc;
	}

	public String getMailUserName() {
		return mailUserName;
	}

	public void setMailUserName(String mailUserName) {
		this.mailUserName = mailUserName;
	}

	public String getCurType() {
		return curType;
	}

	public void setCurType(String curType) {
		this.curType = curType;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	 
	 
}
