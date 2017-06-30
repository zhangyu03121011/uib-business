/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/easypay_ec">easypay_ec</a> All rights reserved.
 */
package com.uib.member.entity;

import org.hibernate.validator.constraints.Length;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 优惠券Entity
 * 
 * @author limy
 * @version 2015-06-15
 */
public class CouponCode {

	private String id; // id
	private Coupon coupon; // 优惠券主键 父类
	private String code; // 号码
	private Integer isUsed; // 是否已使用

	private Date usedDate; // 使用日期
	private String couponNo; // 优惠券
	private MemMember memMember; // 会员
	private String memberId; // 会员ID
	private String couponId;

	private Date createDate;
	private Date updateDate;
	private String delFlag;
	private String remarks;

	public CouponCode() {
		super();
	}

	public CouponCode(Coupon coupon) {
		this.coupon = coupon;
	}

	@Length(min = 1, max = 64, message = "优惠券主键长度必须介于 1 和 64 之间")
	public Coupon getCoupon() {
		return coupon;
	}

	public void setCoupon(Coupon coupon) {
		this.coupon = coupon;
	}

	@Length(min = 0, max = 100, message = "号码长度必须介于 0 和 100 之间")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Length(min = 0, max = 1, message = "是否已使用长度必须介于 0 和 1 之间")
	public Integer getIsUsed() {
		return isUsed;
	}

	public void setIsUsed(Integer isUsed) {
		this.isUsed = isUsed;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUsedDate() {
		return usedDate;
	}

	public void setUsedDate(Date usedDate) {
		this.usedDate = usedDate;
	}

	@Length(min = 0, max = 32, message = "优惠卷长度必须介于 0 和 32 之间")
	public String getCouponNo() {
		return couponNo;
	}

	public void setCouponNo(String couponNo) {
		this.couponNo = couponNo;
	}

	public MemMember getMemMember() {
		return memMember;
	}

	public void setMemMember(MemMember memMember) {
		this.memMember = memMember;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getCouponId() {
		return couponId;
	}

	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}
	
}