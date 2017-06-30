package com.uib.union.payment.dto;

/**
 * 退货返回通知
 * @author kevin
 *
 */
public class AcpsdkReturnGoodsRepsonse {
	
	/** 版本号 */
	private String version;
	
	/** 编码方式 */
	private String encoding;
	
	/** 证书ID  */
	private String certId;
	
	/** 签名 */
	private String  signature;
	
	/** 签名方法 */
	private String  signMethod;
	
	/** 交易类型  */
	private String  txnType;
	
	/** 交易子类 */
	private String  txnSubType;
	
	/** 产品类型 */
	private String bizType;
	
	/** 接入类型 */
	private String accessType;
	
	/** 商户代码  */
	private String merId;
	
	/** 商户订单号  */
	private String orderId;
	
	/** 原始消费交易的queryId */
	private String origQryId;
	
	/** 订单发送时间 */
	private String txnTime;
	
	/** 交易币种 */
	private String currencyCode;
	
	/** 交易金额 */
	private String txnAmt;
	
	/** 请求方保留域  */
	private String reqReserved;
	
	/** 保留域 */
	private String reserved;
	
	/** 退货交易的 */
	private String queryId;
	
	/** 响应码 */
	private String respCode;
	
	/** 响应信息 */
	private String respMsg;
	
	/** 清算金额 */
	private String settleAmt;
	
	/** 清算币种 */
	private String settleCurrencyCode;
	
	/** 清算日期 */
	private String settleDate;
	
	/** 系统跟踪号 */
	private String traceNo;
	
	/** 交易传输时间 */
	private String traceTime;
	
	/** 兑换日期 */
	private String exchangeDate;
	
	/** 汇率 */
	private String exchangeRate;
	
	/** */
	private String vpcTransData;

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

	public String getOrigQryId() {
		return origQryId;
	}

	public void setOrigQryId(String origQryId) {
		this.origQryId = origQryId;
	}

	public String getTxnTime() {
		return txnTime;
	}

	public void setTxnTime(String txnTime) {
		this.txnTime = txnTime;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getTxnAmt() {
		return txnAmt;
	}

	public void setTxnAmt(String txnAmt) {
		this.txnAmt = txnAmt;
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

	public String getSettleAmt() {
		return settleAmt;
	}

	public void setSettleAmt(String settleAmt) {
		this.settleAmt = settleAmt;
	}

	public String getSettleCurrencyCode() {
		return settleCurrencyCode;
	}

	public void setSettleCurrencyCode(String settleCurrencyCode) {
		this.settleCurrencyCode = settleCurrencyCode;
	}

	public String getSettleDate() {
		return settleDate;
	}

	public void setSettleDate(String settleDate) {
		this.settleDate = settleDate;
	}

	public String getTraceNo() {
		return traceNo;
	}

	public void setTraceNo(String traceNo) {
		this.traceNo = traceNo;
	}

	public String getTraceTime() {
		return traceTime;
	}

	public void setTraceTime(String traceTime) {
		this.traceTime = traceTime;
	}

	public String getExchangeDate() {
		return exchangeDate;
	}

	public void setExchangeDate(String exchangeDate) {
		this.exchangeDate = exchangeDate;
	}

	public String getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(String exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public String getVpcTransData() {
		return vpcTransData;
	}

	public void setVpcTransData(String vpcTransData) {
		this.vpcTransData = vpcTransData;
	}
	
	
	
}
