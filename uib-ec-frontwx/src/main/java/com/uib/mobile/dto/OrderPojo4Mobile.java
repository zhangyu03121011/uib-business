package com.uib.mobile.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class OrderPojo4Mobile {
	// private String id;
	private String orderNo; // 订单编号
	private String orderStatus; // 订单状态
	private String paymentStatus; // 支付状态
	private BigDecimal amount; // 订单金额
	private BigDecimal freight; // 运费
	private BigDecimal fee; // 支付手续费
	private BigDecimal promotionDiscount; // 促销折扣
	private BigDecimal couponDiscount; // 优惠劵折扣
	private BigDecimal offsetAmount; // 调整金额
	private BigDecimal tax; // 税金
	List<OrderItemPojo4Mobile> list_ordertable_item;
	
	/** 微信需要支付的费用 */
//	private BigDecimal wxPayPrice;
	
	/** 是否需要发起微信支付  1-需要，2-不需要 */
	private int wxPayFlag;

	// public String getId() {
	// return id;
	// }
	// public void setId(String id) {
	// this.id = id;
	// }
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	/**
	 * 获取商品金额
	 * 
	 * @return 商品金额
	 */
	public BigDecimal getPrice() {
		BigDecimal price = new BigDecimal(0);
		if (getList_ordertable_item() != null) {
			for (OrderItemPojo4Mobile orderItem : getList_ordertable_item()) {
				if (orderItem != null && orderItem.getSubtotal() != null) {
					price = price.add(orderItem.getSubtotal());
				}
			}
		}
		return price.setScale(2, RoundingMode.HALF_UP);
	}

	public BigDecimal getFee() {
		return fee.setScale(2, RoundingMode.HALF_UP);
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public BigDecimal getFreight() {
		if(freight != null){
			return freight.setScale(2, RoundingMode.HALF_UP);
		}
		return new BigDecimal(0);
	}

	public void setFreight(BigDecimal freight) {
		this.freight = freight;
	}

	public void setPromotionDiscount(BigDecimal promotionDiscount) {
		this.promotionDiscount = promotionDiscount;
	}

	public BigDecimal getPromotionDiscount() {
		return promotionDiscount;
	}

	public BigDecimal getCouponDiscount() {
		return couponDiscount;
	}

	public void setCouponDiscount(BigDecimal couponDiscount) {
		this.couponDiscount = couponDiscount;
	}

	public void setOffsetAmount(BigDecimal offsetAmount) {
		this.offsetAmount = offsetAmount;
	}

	public BigDecimal getOffsetAmount() {
		return offsetAmount;
	}

	public void setTax(BigDecimal tax) {
		this.tax = tax;
	}

	public BigDecimal getTax() {
		return tax;
	}

	/**
	 * 获取订单金额
	 * 
	 * @return 订单金额
	 */
	public BigDecimal getAmount() {
		amount = getPrice();
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

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public List<OrderItemPojo4Mobile> getList_ordertable_item() {
		return list_ordertable_item == null ? new ArrayList<OrderItemPojo4Mobile>()
				: list_ordertable_item;
	}

	public void setList_ordertable_item(
			List<OrderItemPojo4Mobile> list_ordertable_item) {
		this.list_ordertable_item = list_ordertable_item;
	}

	public int getWxPayFlag() {
		return wxPayFlag;
	}

	public void setWxPayFlag(int wxPayFlag) {
		this.wxPayFlag = wxPayFlag;
	}
}
