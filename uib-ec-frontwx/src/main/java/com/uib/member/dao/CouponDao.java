/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/easypay_ec">easypay_ec</a> All rights reserved.
 */
package com.uib.member.dao;

import java.util.List;
import java.util.Map;

import com.uib.member.entity.Coupon;

/**
 * 优惠券表DAO接口
 */

public interface CouponDao {
	// 通过优惠码中查询出来的优惠券Id在优惠券中查询优惠券
	Coupon getCouponByCouponId(String couponId) throws Exception;

	/**
	 * 根据会员id查询优惠券
	 * 
	 * @param memberId
	 * @return
	 * @throws Exception
	 */
	List<Coupon> selectCouponsByMemberId(String memberId) throws Exception;

	/**
	 * 
	 * @Title: findUsableCouponByPresentType
	 * @author sl
	 * @Description: 查询可用优惠券 @throws
	 */
	List<Coupon> findUsableCouponByPresentType(String presentType);

	/**
	 * 优惠卷数量减1
	 */
	void updateMinusSum(List<Coupon> list);
	
	/**
	 * 查询已领取和未领取的优惠劵
	 */
	List<Coupon> queryGetCoupon(Map<String,Object> map) throws Exception;
}