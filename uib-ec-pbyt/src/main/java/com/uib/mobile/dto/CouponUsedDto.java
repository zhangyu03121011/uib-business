package com.uib.mobile.dto;

import java.util.Date;

public class CouponUsedDto {
	private Date beginDate;		// 使用起始日期
	private Date endDate;		// 使用结束日期
	private String name;		// 名称
	private String couponCodeId;		// 优惠码编号

	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCouponCodeId() {
		return couponCodeId;
	}
	public void setCouponCodeId(String couponCodeId) {
		this.couponCodeId = couponCodeId;
	}
}
