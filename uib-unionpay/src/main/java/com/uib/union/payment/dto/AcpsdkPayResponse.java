package com.uib.union.payment.dto;

/**
 * 
 * @author kevin
 *
 */
public class AcpsdkPayResponse {
	
	/**
	 *  版本号
	 */
	private String version;
	 
	/**
	 *  编码方式
	 */
	private String encoding;
	 
	/**
	 *  证书ID
	 */
	private String certId;
	 
	 /**
	  * 签名
	  */
	private String signature;

	/**
	 * 签名方法
	 */
	private String signMethod;
	  
	/**
	 *  交易类型
	 */
	private String txnType;
	 
	 /**
	  * 交易子类
	  */
	private String txnSubType;
	 
	 /**
	  * 产品类型
	  */
	private String bizType;
	 
	/**
	 *  接入类型
	 */
	private String accessType;
	 
	/**
	 *  商户代码
	 */
	private String merId;
	
	/**
	 * 商户订单号
	 */
	private String orderId;
	 
	/**
	 *  订单发送时间
	 */
	private String txnTime;
	 
	/**
	 *  帐号
	 */
	private String accNo;
	
	
	/**
	 *  交易金额
	 */
	private String txnAmt;
	 
	/**
	 *  交易币种 默认为156
	 */
	private String currencyCode;
	 
	 
	/**
	 *  请求方保留域
	 */
	private String reqReserved;

	/**
	 * 保留域
	 */
	private String reserved;
	 
	/**
	 *  交易查询流水号  消费交易的流水号，供后续查询用
	 */
	private String queryId;
	 
	 
	/**
	 *  响应码
	 */
	private String respCode;
	
	
	/**
	 * 应答信息
	 */
	private String respMsg;
	 
	/**
	 *  帐号  特殊商户需要时返回用户支付使用的卡号
	 *  private String accNo;
	 */
	
	 
	 
	/**
	 *  支付卡类型   特殊商户需要时返回，根据商户配置
	 */
    private String payCardType;
	 
	 
	 /**
	  * 支付方式    特殊商户需要时返回，根据商户配置
	  */
	private String payType;
	
	 
	/**
	 * 银联订单号 , 商户推送订单后银联移动支付系统返回该流水号，商户调用支付控件时使用
	 */
	private String tn;


	public String getVersion() {
		return version;
	}


	public void setVersion(String version) {
		this.version = version;
	}


	public String getEncoding() {
		return encoding;
	}


	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}


	public String getCertId() {
		return certId;
	}


	public void setCertId(String certId) {
		this.certId = certId;
	}


	public String getSignature() {
		return signature;
	}


	public void setSignature(String signature) {
		this.signature = signature;
	}


	public String getSignMethod() {
		return signMethod;
	}


	public void setSignMethod(String signMethod) {
		this.signMethod = signMethod;
	}


	public String getTxnType() {
		return txnType;
	}


	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}


	public String getTxnSubType() {
		return txnSubType;
	}


	public void setTxnSubType(String txnSubType) {
		this.txnSubType = txnSubType;
	}


	public String getBizType() {
		return bizType;
	}


	public void setBizType(String bizType) {
		this.bizType = bizType;
	}


	public String getAccessType() {
		return accessType;
	}


	public void setAccessType(String accessType) {
		this.accessType = accessType;
	}


	public String getMerId() {
		return merId;
	}


	public void setMerId(String merId) {
		this.merId = merId;
	}


	public String getOrderId() {
		return orderId;
	}


	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}


	public String getTxnTime() {
		return txnTime;
	}


	public void setTxnTime(String txnTime) {
		this.txnTime = txnTime;
	}


	public String getAccNo() {
		return accNo;
	}


	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}


	public String getTxnAmt() {
		return txnAmt;
	}


	public void setTxnAmt(String txnAmt) {
		this.txnAmt = txnAmt;
	}


	public String getCurrencyCode() {
		return currencyCode;
	}


	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}


	public String getReqReserved() {
		return reqReserved;
	}


	public void setReqReserved(String reqReserved) {
		this.reqReserved = reqReserved;
	}


	public String getReserved() {
		return reserved;
	}


	public void setReserved(String reserved) {
		this.reserved = reserved;
	}


	public String getQueryId() {
		return queryId;
	}


	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}


	public String getRespCode() {
		return respCode;
	}


	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}


	public String getRespMsg() {
		return respMsg;
	}


	public void setRespMsg(String respMsg) {
		this.respMsg = respMsg;
	}


	public String getPayCardType() {
		return payCardType;
	}


	public void setPayCardType(String payCardType) {
		this.payCardType = payCardType;
	}


	public String getPayType() {
		return payType;
	}


	public void setPayType(String payType) {
		this.payType = payType;
	}


	public String getTn() {
		return tn;
	}


	public void setTn(String tn) {
		this.tn = tn;
	} 
	
	 
	
}
