/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/easypay_ec">easypay_ec</a> All rights reserved.
 */
package com.uib.order.entity;

import java.beans.Transient;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.uib.common.enums.OrderStatus;
import com.uib.serviceUtils.Utils;

/**
 * 订单Entity
 * 
 * @author limy
 * @version 2015-06-08
 */
public class OrderTable {

	private String id;
	private String orderNo; // 订单编号
	private String address; // 地址
	private String amountPaid; // 已付金额
	private String areaName; // 地区名称
	private String consignee; // 收货人
	private BigDecimal couponDiscount; // 优惠劵折扣
	private Date expire; // 到期时间
	private BigDecimal fee; // 支付手续费
	private String invoiceTitle; // 发票抬头
	private String isAllocatedStock; // 是否分配库存
	private String isInvoice; // 是否开据发票
	private Date lockExpire; // 锁定到期时间
	private String memo; // 附言
	private BigDecimal offsetAmount; // 调整金额
	private String orderStatus; // 订单状态
	private String paymentMethodName; // 支付方式名称
	private String paymentStatus; // 支付状态
	private String phone; // 电话
	private String point; // 赠送积分
	private String promotion; // 促销
	private BigDecimal promotionDiscount; // 促销折扣
	private String shippingMethodName; // 配送方式名称
	private String shippingStatus; // 配送状态
	private BigDecimal tax; // 税金
	private String zipCode; // 邮编
	private String area; // 地区
	private String couponCode; // 优惠码
	private String paymentMethod; // 支付方式
	private String shippingMethod; // 配送方式
	private String userName; // 用户名
	private String memberId; //会员编号
	private Date createDate;
	private BigDecimal freight; // 运费
	private String orderSource;//订单的来源：PC,APP，微信
	private String deliveryCorp;//订单配送物流公司
	private Integer pageSize;
	private Integer pageIndex;
	private Double commission;  //佣金
	private String userType;   //用户类型
	private String supplierId; //供应商编号
	
	// private List<OrderTableItem> orderTableItemList = Lists.newArrayList();
	// // 子表列表
	// private List<OrderTablePayment> orderTablePaymentList =
	// Lists.newArrayList(); // 子表列表
	// private List<OrderTableRefunds> orderTableRefundsList =
	// Lists.newArrayList(); // 子表列表

	private List<OrderTableItem> list_ordertable_item;

	private String remarks; // 备注
	private String createBy; // 创建者
	private String updateBy; // 更新者
	private Date updateDate; // 更新日期
	private int delFlag = 0;

	/** 是否使用默认地址作为收货地址，该字段不参与持久化，做查询时使用 */
	private String defaultAddressFlag;
	
	/** 运单号，不做持久化操作 */
	private String trackingNo;
	
	/** 订单的应付总价（商品总价+运费-优惠券），不参与持久化操作 */
	private BigDecimal totalPrice;
	
	public OrderTable() {
		// super();
	}

	// public OrderTable(String id){
	// super(id);
	// }

	@Length(min = 0, max = 32, message = "订单编号长度必须介于 0 和 32 之间")
	public String getOrderNo() {
		return orderNo;
	}

	public List<OrderTableItem> getList_ordertable_item() {
		return list_ordertable_item;
	}

	public void setList_ordertable_item(
			List<OrderTableItem> list_ordertable_item) {
		this.list_ordertable_item = list_ordertable_item;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	@Length(min = 0, max = 32, message = "地址长度必须介于 0 和 32 之间")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAmountPaid() {
		return amountPaid;
	}

	public void setAmountPaid(String amountPaid) {
		this.amountPaid = amountPaid;
	}

	@Length(min = 0, max = 32, message = "地区名称长度必须介于 0 和 32 之间")
	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	@Length(min = 0, max = 32, message = "收货人长度必须介于 0 和 32 之间")
	public String getConsignee() {
		return consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	public BigDecimal getCouponDiscount() {
		return couponDiscount;
	}

	public void setCouponDiscount(BigDecimal couponDiscount) {
		this.couponDiscount = couponDiscount;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getExpire() {
		return expire;
	}

	public void setExpire(Date expire) {
		this.expire = expire;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	@Length(min = 0, max = 32, message = "发票抬头长度必须介于 0 和 32 之间")
	public String getInvoiceTitle() {
		return invoiceTitle;
	}

	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}

	@Length(min = 0, max = 32, message = "是否分配库存长度必须介于 0 和 32 之间")
	public String getIsAllocatedStock() {
		return isAllocatedStock;
	}

	public void setIsAllocatedStock(String isAllocatedStock) {
		this.isAllocatedStock = isAllocatedStock;
	}

	@Length(min = 0, max = 32, message = "是否开据发票长度必须介于 0 和 32 之间")
	public String getIsInvoice() {
		return isInvoice;
	}

	public void setIsInvoice(String isInvoice) {
		this.isInvoice = isInvoice;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getLockExpire() {
		return lockExpire;
	}

	public void setLockExpire(Date lockExpire) {
		this.lockExpire = lockExpire;
	}

	@Length(min = 0, max = 32, message = "附言长度必须介于 0 和 32 之间")
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public BigDecimal getOffsetAmount() {
		return offsetAmount;
	}

	public void setOffsetAmount(BigDecimal offsetAmount) {
		this.offsetAmount = offsetAmount;
	}

	@Length(min = 0, max = 32, message = "订单状态长度必须介于 0 和 32 之间")
	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	@Length(min = 0, max = 32, message = "支付方式名称长度必须介于 0 和 32 之间")
	public String getPaymentMethodName() {
		return paymentMethodName;
	}

	public void setPaymentMethodName(String paymentMethodName) {
		this.paymentMethodName = paymentMethodName;
	}

	@Length(min = 0, max = 32, message = "支付状态长度必须介于 0 和 32 之间")
	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	@Length(min = 0, max = 32, message = "电话长度必须介于 0 和 32 之间")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Length(min = 0, max = 32, message = "赠送积分长度必须介于 0 和 32 之间")
	public String getPoint() {
		return point;
	}

	public void setPoint(String point) {
		this.point = point;
	}

	@Length(min = 0, max = 32, message = "促销长度必须介于 0 和 32 之间")
	public String getPromotion() {
		return promotion;
	}

	public void setPromotion(String promotion) {
		this.promotion = promotion;
	}

	public BigDecimal getPromotionDiscount() {
		return promotionDiscount;
	}

	public void setPromotionDiscount(BigDecimal promotionDiscount) {
		this.promotionDiscount = promotionDiscount;
	}

	@Length(min = 0, max = 32, message = "配送方式名称长度必须介于 0 和 32 之间")
	public String getShippingMethodName() {
		return shippingMethodName;
	}

	public void setShippingMethodName(String shippingMethodName) {
		this.shippingMethodName = shippingMethodName;
	}

	@Length(min = 0, max = 32, message = "配送状态长度必须介于 0 和 32 之间")
	public String getShippingStatus() {
		return shippingStatus;
	}

	public void setShippingStatus(String shippingStatus) {
		this.shippingStatus = shippingStatus;
	}

	@Length(min = 0, max = 32, message = "税金长度必须介于 0 和 32 之间")
	public BigDecimal getTax() {
		return tax;
	}

	public void setTax(BigDecimal tax) {
		this.tax = tax;
	}

	@Length(min = 0, max = 32, message = "邮编长度必须介于 0 和 32 之间")
	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	@Length(min = 0, max = 32, message = "地区长度必须介于 0 和 32 之间")
	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	@Length(min = 0, max = 32, message = "优惠码长度必须介于 0 和 32 之间")
	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	@Length(min = 0, max = 32, message = "支付方式长度必须介于 0 和 32 之间")
	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	@Length(min = 0, max = 32, message = "配送方式长度必须介于 0 和 32 之间")
	public String getShippingMethod() {
		return shippingMethod;
	}

	public void setShippingMethod(String shippingMethod) {
		this.shippingMethod = shippingMethod;
	}

	@Length(min = 0, max = 32, message = "用户名长度必须介于 0 和 32 之间")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * 获取商品价格
	 * 
	 * @return 商品价格
	 */
	@Transient
	public BigDecimal getPrice() {
		BigDecimal price = new BigDecimal(0);
		if (getList_ordertable_item() != null) {
			for (OrderTableItem orderItem : getList_ordertable_item()) {
				if (orderItem != null && orderItem.getSubtotal() != null) {
					price = price.add(orderItem.getSubtotal());
				}
			}
		}
		return price;
	}

	/**
	 * 获取订单金额
	 * 
	 * @return 订单金额
	 */
	@Transient
	public BigDecimal getOrderAmount(BigDecimal price) {
		BigDecimal amount = price;
		if (getFee() != null) {
			amount = amount.add(getFee());
		}
		if (getFreight() != null) {
			amount = amount.add(getFreight());
		}
		if (getPromotionDiscount() != null) {
			amount = amount.subtract(getPromotionDiscount());
		}
		if (getCouponDiscount() != null) {
			amount = amount.subtract(getCouponDiscount());
		}
		if (getOffsetAmount() != null) {
			amount = amount.add(getOffsetAmount());
		}
		if (getTax() != null) {
			amount = amount.add(getTax());
		}
		return (amount.compareTo(new BigDecimal(0)) > 0 ? amount
				: new BigDecimal(0)).setScale(2, RoundingMode.HALF_UP);
	}

	/**
	 * 获取订单金额
	 * 
	 * @return 订单金额
	 */
	@Transient
	public BigDecimal getAmount() {
		BigDecimal amount = getPrice();
		if (getFee() != null) {
			amount = amount.add(getFee());
		}
		if (getFreight() != null) {
			amount = amount.add(getFreight());
		}
		if (getPromotionDiscount() != null) {
			amount = amount.subtract(getPromotionDiscount());
		}
		if (getCouponDiscount() != null) {
			amount = amount.subtract(getCouponDiscount());
		}
		if (getOffsetAmount() != null) {
			amount = amount.add(getOffsetAmount());
		}
		if (getTax() != null) {
			amount = amount.add(getTax());
		}
		return (amount.compareTo(new BigDecimal(0)) > 0 ? amount
				: new BigDecimal(0)).setScale(2, RoundingMode.HALF_UP);
	}

	public String getOrderStatusName() {
		return Utils.isBlank(orderStatus) ? OrderStatus.unconfirmed
				.getDescription() : OrderStatus.getDescription(orderStatus);
	}

	public BigDecimal getFreight() {
		return freight;
	}

	public void setFreight(BigDecimal freight) {
		this.freight = freight;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public int getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(int delFlag) {
		this.delFlag = delFlag;
	}

	public Integer getProductCount() {
		return Utils.isBlank(list_ordertable_item) ? 0 : list_ordertable_item
				.size();
	}

	public String getDefaultAddressFlag() {
		return defaultAddressFlag;
	}

	public void setDefaultAddressFlag(String defaultAddressFlag) {
		this.defaultAddressFlag = defaultAddressFlag;
	}
	
	

	public String getOrderSource() {
		return orderSource;
	}

	public void setOrderSource(String orderSource) {
		this.orderSource = orderSource;
	}



	/**
	 * 配送状态
	 */
	public enum ShippingStatus {

		/** 未发货 */
		unshipped,

		/** 部分发货 */
		partialShipment,

		/** 已发货 */
		shipped,

		/** 部分退货 */
		partialReturns,

		/** 已退货 */
		returned
	}

	/**
	 * 支付状态
	 */
	public enum PaymentStatus {

		/** 未支付 */
		unpaid,

		/** 部分支付 */
		partialPayment,

		/** 已支付 */
		paid,

		/** 部分退款 */
		partialRefunds,

		/** 已退款 */
		refunded
	}

	// public List<OrderTableItem> getOrderTableItemList() {
	// return orderTableItemList;
	// }
	//
	// public void setOrderTableItemList(List<OrderTableItem>
	// orderTableItemList) {
	// this.orderTableItemList = orderTableItemList;
	// }
	// public List<OrderTablePayment> getOrderTablePaymentList() {
	// return orderTablePaymentList;
	// }
	//
	// public void setOrderTablePaymentList(List<OrderTablePayment>
	// orderTablePaymentList) {
	// this.orderTablePaymentList = orderTablePaymentList;
	// }
	// public List<OrderTableRefunds> getOrderTableRefundsList() {
	// return orderTableRefundsList;
	// }
	//
	// public void setOrderTableRefundsList(List<OrderTableRefunds>
	// orderTableRefundsList) {
	// this.orderTableRefundsList = orderTableRefundsList;
	// }

	public final static String PAYMENT_METHOD_ON_LINE = "1";// 在线支付
	public final static String PAYMENT_METHOD_BANK_REMITTANCE = "2";// 银行汇款
	public final static String PAYMENT_METHOD_CASH_ON_DELIVERY = "3";// 货到付款

	public enum StatusType {
		TYPE_OF_PAY_STATUS, TYPE_OF_ORDER_STATUS
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

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public Double getCommission() {
		return commission;
	}

	public void setCommission(Double commission) {
		this.commission = commission;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	
	
}