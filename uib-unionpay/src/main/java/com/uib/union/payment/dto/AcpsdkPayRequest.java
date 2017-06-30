package com.uib.union.payment.dto;

/**
 * 银联全渠道支付请求参数
 * @author kevin
 *
 */
public class AcpsdkPayRequest {
	
	/**
	 * 版本号
	 */
	private String version = "5.0.0";
	
	
	/**
	 * 字符集编码 默认"UTF-8"
	 */
	private String encoding = "UTF-8";
	
	
	/**
	 * 签名方法 01 RSA
	 */
	private String signMethod = "01";
	
	
	/**
	 * 交易类型
	 */
	private String txnType;
	
	
	/**
	 * 业务类型 000201 B2C网关支付
	 */
	private String bizType;
	
	
	/**
	 * 渠道类型 07-互联网渠道
	 */
	private String channelType;
	
	
	/**
	 * 收单前台接收地址 选送
	 */
	private String frontUrl;
	
	
	/**
	 * 收单后台接收地址 必送
	 */
	private String backUrl;
	
	/**
	 * 接入类型:商户接入填0 0- 商户 ， 1： 收单， 2：平台商户
	 */
	private String accessType;
	
	/**
	 * 商户号
	 */
	private String merId;
	
	
	/**
	 * 订单号
	 */
	private String orderId;
	
	/**
	 * 订单发送时间
	 */
	private String txnTime;
	
	
	/**
	 * 交易金额
	 */
	private String txnAmt;
	
	
	/**
	 * 交易币种
	 */
	private String currencyCode;
	
	
	/**
	 * 发卡机构代码 根据需求选送 参考接口规范 当需要网银标志前移时，上送银行简称代码 如 工行 ICBC
	 */
	private String issInsCode;
	
	
	/**
	 * 持卡人ip 根据需求选送 参考接口规范 防钓鱼用
	 */
	private String customerIp;
	
	
	
	


	public String getCustomerIp() {
		return customerIp;
	}


	public void setCustomerIp(String customerIp) {
		this.customerIp = customerIp;
	}


	public String getIssInsCode() {
		return issInsCode;
	}


	public void setIssInsCode(String issInsCode) {
		this.issInsCode = issInsCode;
	}


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


	public String getBizType() {
		return bizType;
	}


	public void setBizType(String bizType) {
		this.bizType = bizType;
	}


	public String getChannelType() {
		return channelType;
	}


	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}


	public String getFrontUrl() {
		return frontUrl;
	}


	public void setFrontUrl(String frontUrl) {
		this.frontUrl = frontUrl;
	}


	public String getBackUrl() {
		return backUrl;
	}


	public void setBackUrl(String backUrl) {
		this.backUrl = backUrl;
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
	
	
	
}
