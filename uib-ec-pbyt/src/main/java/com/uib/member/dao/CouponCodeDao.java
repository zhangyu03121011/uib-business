/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/easypay_ec">easypay_ec</a> All rights reserved.
 */
package com.uib.member.dao;

import java.util.List;
import java.util.Map;

import com.easypay.core.exception.GenericException;
import com.uib.member.entity.CouponCode;

/**
 * 优惠券表DAO接口
 */
public interface CouponCodeDao {
	// 通过当前会员在优惠码中查询出优惠券
	public List<CouponCode> getCouponByMemberId(String memberId) throws GenericException;

	/**
	 * 根据优惠码设置已使用
	 * 
	 * @param code
	 */
	public void setCouponCodeUsed(String code);

	/**
	 * 根据优惠券码查询
	 * 
	 * @param code
	 * @return
	 */
	public CouponCode getCouponCodeByCode(String code);
	/**
	 * 根据优惠码查询优惠码
	 * 
	 * @param code
	 * @return
	 */
	public CouponCode getCouponCodeByCouponCode(String code);
	
	void insertList(List<CouponCode> list);
	
	/**
	 * 根据优惠码修改优惠码所属状态
	 * @param paramMap
	 * @throws Exception
	 */
	void updateIsUsedByCode(Map<String,Object> paramMap) throws Exception;
	
	/**
	 * 插入单条数据
	 * @param couponCode
	 * @return 
	 * @throws Exception
	 */
	void insert(CouponCode couponCode) throws Exception;
}