package com.uib.union.payment.dto;

/**
 * 银联支付请求Dto
 * @author kevin
 *
 */
public class UnionPayRequestDto {
	
	/**
	 * 协议版本
	 */
	private String  version;
    
	/**
	 * 字符编码
	 */
	private String 	charset;
	
	/**
	 * 交易类型
	 */
	private String  transType;
	
	/**
	 * 原始交易流水号
	 */
	private String  origQid;
	
	/**
	 * 商户代码
	 */
	private String  merId;
	
	/**
	 * 商户简称
	 */
	private String merAbbr;
	
	
	/**
	 * 收单机构代码（仅收单机构接入需要填写）
	 */
	private String acqCode;
	
	/**
	 * 商户类别（收单机构接入需要填写）
	 */
	private String merCode;
	
	/**
	 * 商品URL
	 */
	private String commodityUrl;
	
	
	/**
	 * 商品名称
	 */
	private String commodityName;
	
	
	/**
	 * 商品单价 单位：分
	 */
	private String commodityUnitPrice;
	
	
	/**
	 * 商品数量
	 */
	private String commodityQuantity;
	
	
	/**
	 * 折扣 单位：分
	 */
	private String commodityDiscount;
	
	/**
	 * 运费 单位：分
	 */
	private String transferFee;
	
	/**
	 * 订单号（需要商户自己生成）
	 */
	private String orderNumber;
    
	
	/**
	 * 交易金额 单位：分
	 */
	private String orderAmount;
	
    
	/**
	 * 交易币种
	 */
	private String orderCurrency;
	
	/**
	 * 交易时间
	 */
	private String orderTime;
    
	
	/**
	 * 用户IP
	 */
	private String customerIp;
	
	/**
	 * 用户真实姓名
	 */
	private String customerName;
	
	/**
	 * 默认支付方式
	 */
	private String defaultPayType;
	
	/**
	 * 默认银行编号
	 */
	private String defaultBankNumber;
	
	/**
	 * 交易超时时间
	 */
	private String transTimeout;
	
	/**
	 * 前台回调商户URL
	 */
	private String frontEndUrl;
	
	/**
	 * 后台回调商户URL
	 */
	private String backEndUrl;
	
	/**
	 * 商户保留域
	 */
	private String merReserved;
	
	


	public String getVersion() {
		return version;
	}


	public void setVersion(String version) {
		this.version = version;
	}


	public String getCharset() {
		return charset;
	}


	public void setCharset(String charset) {
		this.charset = charset;
	}


	public String getTransType() {
		return transType;
	}


	public void setTransType(String transType) {
		this.transType = transType;
	}


	public String getOrigQid() {
		return origQid;
	}


	public void setOrigQid(String origQid) {
		this.origQid = origQid;
	}


	public String getMerId() {
		return merId;
	}


	public void setMerId(String merId) {
		this.merId = merId;
	}


	public String getMerAbbr() {
		return merAbbr;
	}


	public void setMerAbbr(String merAbbr) {
		this.merAbbr = merAbbr;
	}


	public String getAcqCode() {
		return acqCode;
	}


	public void setAcqCode(String acqCode) {
		this.acqCode = acqCode;
	}


	public String getMerCode() {
		return merCode;
	}


	public void setMerCode(String merCode) {
		this.merCode = merCode;
	}


	public String getCommodityUrl() {
		return commodityUrl;
	}


	public void setCommodityUrl(String commodityUrl) {
		this.commodityUrl = commodityUrl;
	}


	public String getCommodityName() {
		return commodityName;
	}


	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName;
	}


	public String getCommodityUnitPrice() {
		return commodityUnitPrice;
	}


	public void setCommodityUnitPrice(String commodityUnitPrice) {
		this.commodityUnitPrice = commodityUnitPrice;
	}


	public String getCommodityQuantity() {
		return commodityQuantity;
	}


	public void setCommodityQuantity(String commodityQuantity) {
		this.commodityQuantity = commodityQuantity;
	}


	public String getCommodityDiscount() {
		return commodityDiscount;
	}


	public void setCommodityDiscount(String commodityDiscount) {
		this.commodityDiscount = commodityDiscount;
	}


	public String getTransferFee() {
		return transferFee;
	}


	public void setTransferFee(String transferFee) {
		this.transferFee = transferFee;
	}


	public String getOrderNumber() {
		return orderNumber;
	}


	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}


	public String getOrderAmount() {
		return orderAmount;
	}


	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}


	public String getOrderCurrency() {
		return orderCurrency;
	}


	public void setOrderCurrency(String orderCurrency) {
		this.orderCurrency = orderCurrency;
	}


	public String getOrderTime() {
		return orderTime;
	}


	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}


	public String getCustomerIp() {
		return customerIp;
	}


	public void setCustomerIp(String customerIp) {
		this.customerIp = customerIp;
	}


	public String getCustomerName() {
		return customerName;
	}


	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}


	public String getDefaultPayType() {
		return defaultPayType;
	}


	public void setDefaultPayType(String defaultPayType) {
		this.defaultPayType = defaultPayType;
	}


	public String getDefaultBankNumber() {
		return defaultBankNumber;
	}


	public void setDefaultBankNumber(String defaultBankNumber) {
		this.defaultBankNumber = defaultBankNumber;
	}


	public String getTransTimeout() {
		return transTimeout;
	}


	public void setTransTimeout(String transTimeout) {
		this.transTimeout = transTimeout;
	}


	public String getFrontEndUrl() {
		return frontEndUrl;
	}


	public void setFrontEndUrl(String frontEndUrl) {
		this.frontEndUrl = frontEndUrl;
	}


	public String getBackEndUrl() {
		return backEndUrl;
	}


	public void setBackEndUrl(String backEndUrl) {
		this.backEndUrl = backEndUrl;
	}


	public String getMerReserved() {
		return merReserved;
	}


	public void setMerReserved(String merReserved) {
		this.merReserved = merReserved;
	}
	
	
	
}
