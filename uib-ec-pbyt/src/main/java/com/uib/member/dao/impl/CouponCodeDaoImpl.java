package com.uib.member.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.uib.core.dao.MyBatisDao;
import com.uib.core.exception.GenericException;
import com.uib.member.dao.CouponCodeDao;
import com.uib.member.entity.CouponCode;

@Component
public class CouponCodeDaoImpl extends MyBatisDao<CouponCode>implements CouponCodeDao {

	@Override
	public List<CouponCode> getCouponByMemberId(String memberId) throws GenericException {
		return this.get("getCouponByMemberId", memberId);
	}

	@Override
	public void setCouponCodeUsed(String code) {
		this.update("setCouponCodeUsed", code);
	}

	@Override
	public CouponCode getCouponCodeByCode(String code) {
		return this.getUnique("getCouponCodeByCode", code);
	}
	@Override
	public CouponCode getCouponCodeByCouponCode(String code){
		return this.getUnique("getCouponCodeByCouponCode", code);
	}
	public void insertList(List<CouponCode> list) {
		super.save("insertList", list);

	}

	@Override
	public void updateIsUsedByCode(Map<String, Object> paramMap) throws Exception {
		this.update("updateIsUsedByCode", paramMap);	
	}

	@Override
	public void insert(CouponCode couponCode) throws Exception {
	    super.save("insert", couponCode);	
	}

}
