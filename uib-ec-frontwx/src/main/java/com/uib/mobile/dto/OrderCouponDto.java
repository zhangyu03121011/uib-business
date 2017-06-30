package com.uib.mobile.dto;

import java.util.List;

public class OrderCouponDto {
	
	/** 可用优惠券数量 */
	private Integer couponCount;
	
	/** 不可用优惠券数量 */
	private Integer unCouponCount;
	
	/** 可用优惠券列表 */
	private List<CouponDto> couponList;
	
	/** 不可用优惠券列表 */
	private List<CouponDto> unCouponList;

	public Integer getCouponCount() {
		return couponCount;
	}

	public void setCouponCount(Integer couponCount) {
		this.couponCount = couponCount;
	}

	public Integer getUnCouponCount() {
		return unCouponCount;
	}

	public void setUnCouponCount(Integer unCouponCount) {
		this.unCouponCount = unCouponCount;
	}

	public List<CouponDto> getCouponList() {
		return couponList;
	}

	public void setCouponList(List<CouponDto> couponList) {
		this.couponList = couponList;
	}

	public List<CouponDto> getUnCouponList() {
		return unCouponList;
	}

	public void setUnCouponList(List<CouponDto> unCouponList) {
		this.unCouponList = unCouponList;
	}

}
