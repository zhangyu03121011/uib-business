package com.uib.member.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.uib.core.dao.MyBatisDao;
import com.uib.member.dao.CouponDao;
import com.uib.member.entity.Coupon;

@Component
public class CouponDaoImpl extends MyBatisDao<Coupon> implements CouponDao {

	@Override
	public Coupon getCouponByCouponId(String couponId) throws Exception {
		return this.getUnique("getCouponByCouponId", couponId);
	}

	@Override
	public List<Coupon> selectCouponsByMemberId(String memberId) throws Exception {
		return this.get("selectCouponsByMemberId", memberId);
	}

	public void updateMinusSum(List<Coupon> list) {
		super.update("updateMinusSum", list);
	}

	public List<Coupon> findUsableCouponByPresentType(String presentType) {

		return super.get("findUsableCouponByPresentType", presentType);
	}

	public List<Coupon> queryGetCoupon(Map<String, Object> map)
			throws Exception {
		return this.get("queryGetCoupon", map);
	}

}
