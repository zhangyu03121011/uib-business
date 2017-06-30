package com.uib.mobile.dto;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;



public class CouponDto {
	
	private Date beginDate;		// 使用起始日期
	private Date endDate;		// 使用结束日期
	private String name;		// 名称
	private BigDecimal facePrice;	//面值
	private BigDecimal needConsumeBalance; //所需消费余额
	private Integer isUsed;		// 是否已使用
	private String presentType;		// 赠送类型
	private String couponCode;		// 优惠码编号
	
	@DateTimeFormat(pattern="yyyy-MM-dd")  
	@JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")  
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	
	@DateTimeFormat(pattern="yyyy-MM-dd")  
	@JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
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
	public BigDecimal getFacePrice() {
		return facePrice;
	}
	public void setFacePrice(BigDecimal facePrice) {
		this.facePrice = facePrice;
	}
	public BigDecimal getNeedConsumeBalance() {
		return needConsumeBalance;
	}
	public void setNeedConsumeBalance(BigDecimal needConsumeBalance) {
		this.needConsumeBalance = needConsumeBalance;
	}
	public Integer getIsUsed() {
		return isUsed;
	}
	public void setIsUsed(Integer isUsed) {
		this.isUsed = isUsed;
	}
	public String getPresentType() {
		return presentType;
	}
	public void setPresentType(String presentType) {
		this.presentType = presentType;
	}
	public String getCouponCode() {
		return couponCode;
	}
	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}
}
