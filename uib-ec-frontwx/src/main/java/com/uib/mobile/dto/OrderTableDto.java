package com.uib.mobile.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class OrderTableDto {
	private String orderNo; // 订单编号
	private String paymentMethod; // 支付方式
	private String orderStatus; // 订单状态
	private BigDecimal amount;	//订单金额
	private BigDecimal fee; // 支付手续费
	private String consignee; // 收货人
	private String phone; // 电话
	private String areaName; // 地区名称
	private String address; // 地址
	private String shippingMethod; // 配送方式
	private String shippingMethodName;//配送方式名称
	private BigDecimal freight; // 运费
	private Date createDate;		//下单时间
	private CouponDto couponDto;		//优惠券
	private BigDecimal productAmount; //商品总额
	private List<OrderItemDto> list_ordertable_item;
	private BigDecimal couponDiscount; // 优惠劵折扣
	
	private String orderSource;//订单的来源：PC,APP，微信
	
	/** 是否使用默认地址作为收货地址 */
	private String defaultAddressFlag;
	
	private String receiverId;
	
	/** 可用优惠券数量  不做持久化操作 */
	private int couponCount;
	
	/** 运单号，不做持久化操作 */
	private String trackingNo;
	
	private String deliveryCorp;//订单配送快递公司名称
	
	private String area;
	
	//余额支付标记 0-未开启，1-开启
	private String balanceFlag;
	
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getConsignee() {
		return consignee;
	}
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getShippingMethod() {
		return shippingMethod;
	}
	public void setShippingMethod(String shippingMethod) {
		this.shippingMethod = shippingMethod;
	}

	public BigDecimal getFee() {
		return fee;
	}
	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}
	public BigDecimal getFreight() {
		return freight;
	}
	public void setFreight(BigDecimal freight) {
		this.freight = freight;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")  
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")  
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public List<OrderItemDto> getList_ordertable_item() {
		return list_ordertable_item;
	}
	public void setList_ordertable_item(List<OrderItemDto> list_ordertable_item) {
		this.list_ordertable_item = list_ordertable_item;
	}
	public BigDecimal getProductAmount() {
		return productAmount;
	}
	public void setProductAmount(BigDecimal productAmount) {
		this.productAmount = productAmount;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public CouponDto getCouponDto() {
		return couponDto;
	}
	public void setCouponDto(CouponDto couponDto) {
		this.couponDto = couponDto;
	}
	public String getDefaultAddressFlag() {
		return defaultAddressFlag;
	}
	public void setDefaultAddressFlag(String defaultAddressFlag) {
		this.defaultAddressFlag = defaultAddressFlag;
	}
	public String getShippingMethodName() {
		return shippingMethodName;
	}
	public void setShippingMethodName(String shippingMethodName) {
		this.shippingMethodName = shippingMethodName;
	}
	public String getReceiverId() {
		return receiverId;
	}
	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}
	public BigDecimal getCouponDiscount() {
		return couponDiscount;
	}
	public void setCouponDiscount(BigDecimal couponDiscount) {
		this.couponDiscount = couponDiscount;
	}
	public String getOrderSource() {
		return orderSource;
	}
	public void setOrderSource(String orderSource) {
		this.orderSource = orderSource;
	}
	public int getCouponCount() {
		return couponCount;
	}
	public void setCouponCount(int couponCount) {
		this.couponCount = couponCount;
	}
	public String getTrackingNo() {
		return trackingNo;
	}
	public void setTrackingNo(String trackingNo) {
		this.trackingNo = trackingNo;
	}
	public String getDeliveryCorp() {
		return deliveryCorp;
	}
	public void setDeliveryCorp(String deliveryCorp) {
		this.deliveryCorp = deliveryCorp;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getBalanceFlag() {
		return balanceFlag;
	}
	public void setBalanceFlag(String balanceFlag) {
		this.balanceFlag = balanceFlag;
	}

}
