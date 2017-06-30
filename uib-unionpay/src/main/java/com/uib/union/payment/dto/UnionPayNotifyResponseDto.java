package com.uib.union.payment.dto;

/**
 * 接收银联支付成功通知接口
 * @author kevin
 *
 */
public class UnionPayNotifyResponseDto {
	
	/**
	 * 字符编码
	 */
	private String charset;
	
	/**
	 * 系统保留域
	 */
	private String cupReserved;
	
	/**
	 * 兑换日期
	 */
	private String exchangeDate;
	
	
	/**
	 * 清算汇率
	 */
	private String exchangeRate;
	
	/**
	 * 商户简称
	 */
	private String merAbbr;
	
	/**
	 * 商户号
	 */
	private String merId;
	
	
	/**
	 * 交易金额
	 */
	private String orderAmount;
	
	/**
	 * 交易币种
	 */
	private String orderCurrency;
	
	/**
	 * 商户订单号（需要商户自己生成）
	 */
	private String orderNumber;
	
	/**
	 * 交易流水号
	 */
	private String qid;
	
	/**
	 * 响应码
	 */
	private String respCode;
	
	/**
	 * 响应信息
	 */
	private String respMsg;
	
	/**
	 * 交易时间
	 */
	private String respTime;
	
	
	/**
	 * 清算金额
	 */
	private String settleAmount;
	
	/**
	 * 清算日期
	 */
	private String settleDate;
	
	/**
	 * 清算币种
	 */
	private String settleCurrency;
	
	
	/**
	 * 系统跟踪号
	 */
	private String traceNumber;
	
	/**
	 * 系统跟踪时间
	 */
	private String traceTime;
	
	/**
	 * 交易类型
	 */
	private String transType;
	
	/**
	 * 版本号
	 */
	private String version;

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getCupReserved() {
		return cupReserved;
	}

	public void setCupReserved(String cupReserved) {
		this.cupReserved = cupReserved;
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

	public String getMerAbbr() {
		return merAbbr;
	}

	public void setMerAbbr(String merAbbr) {
		this.merAbbr = merAbbr;
	}

	public String getMerId() {
		return merId;
	}

	public void setMerId(String merId) {
		this.merId = merId;
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

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getQid() {
		return qid;
	}

	public void setQid(String qid) {
		this.qid = qid;
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

	public String getRespTime() {
		return respTime;
	}

	public void setRespTime(String respTime) {
		this.respTime = respTime;
	}

	public String getSettleAmount() {
		return settleAmount;
	}

	public void setSettleAmount(String settleAmount) {
		this.settleAmount = settleAmount;
	}

	public String getSettleCurrency() {
		return settleCurrency;
	}

	public void setSettleCurrency(String settleCurrency) {
		this.settleCurrency = settleCurrency;
	}

	public String getSettleDate() {
		return settleDate;
	}

	public void setSettleDate(String settleDate) {
		this.settleDate = settleDate;
	}

	public String getTraceNumber() {
		return traceNumber;
	}

	public void setTraceNumber(String traceNumber) {
		this.traceNumber = traceNumber;
	}

	public String getTraceTime() {
		return traceTime;
	}

	public void setTraceTime(String traceTime) {
		this.traceTime = traceTime;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
	
	
	
}
