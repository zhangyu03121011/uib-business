package com.uib.union.payment.dto;

import java.math.BigDecimal;

/**
 * @author kevin
 *
 */
public class PaymentOrderDto {
	 
	 /** 主键 */
	 private Long id;
	 
	 /** 支付流水号 */
	 private String  paymentNo;               
    
	 /** 商户号 */
	 private String  merchantId;                       
	 
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
	 
	 /** 接口名称 */
	 private String interfaceName;
	 
	 /** XML 数据  */
	 private String tranData;
	 
	 private String base64TranData;
	 
	 /** 签名数据 */
	 private String merSignMsg;
	 
	 /**
	  * 客户IP地址
	  */
	 private String clientIp;
	 
	 /**
	  * 客户姓名
	  */
	 private String clientName;
	 
	 
	 
	 
	public String getBase64TranData() {
		return base64TranData;
	}

	public void setBase64TranData(String base64TranData) {
		this.base64TranData = base64TranData;
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public String getTranData() {
		return tranData;
	}

	public void setTranData(String tranData) {
		this.tranData = tranData;
	}

	public String getMerSignMsg() {
		return merSignMsg;
	}

	public void setMerSignMsg(String merSignMsg) {
		this.merSignMsg = merSignMsg;
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

	

	

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
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

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public BigDecimal getOrderAmt() {
		return orderAmt;
	}

	public void setOrderAmt(BigDecimal orderAmt) {
		this.orderAmt = orderAmt;
	}
	 
	
	
	 
}
