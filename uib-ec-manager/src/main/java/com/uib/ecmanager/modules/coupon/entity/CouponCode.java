/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.coupon.entity;

import org.hibernate.validator.constraints.Length;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.uib.ecmanager.common.persistence.DataEntity;
import com.uib.ecmanager.modules.mem.entity.MemMember;

/**
 * 优惠券Entity
 * @author limy
 * @version 2015-06-15
 */
public class CouponCode extends DataEntity<CouponCode> {
	
	private static final long serialVersionUID = 1L;
	private Coupon coupon;		// 优惠券主键 父类
	private String code;		// 号码
	private Integer isUsed;		// 是否已使用
	private Date usedDate;		// 使用日期
	private String couponNo;		// 优惠券
	private MemMember memMember;		// 会员
	
	public CouponCode() {
		super();
	}

	public CouponCode(String id){
		super(id);
	}

	public CouponCode(Coupon coupon){
		this.coupon = coupon;
	}

	@Length(min=1, max=64, message="优惠券主键长度必须介于 1 和 64 之间")
	public Coupon getCoupon() {
		return coupon;
	}

	public void setCoupon(Coupon coupon) {
		this.coupon = coupon;
	}
	
	@Length(min=0, max=100, message="号码长度必须介于 0 和 100 之间")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@Length(min=0, max=1, message="是否已使用长度必须介于 0 和 1 之间")
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
	
	@Length(min=0, max=32, message="优惠卷长度必须介于 0 和 32 之间")
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
	

	
}