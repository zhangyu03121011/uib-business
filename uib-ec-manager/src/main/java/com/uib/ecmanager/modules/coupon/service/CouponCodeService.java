/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.coupon.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.uib.ecmanager.common.persistence.Page;
import com.uib.ecmanager.common.service.CrudService;
import com.uib.ecmanager.common.utils.StringUtils;
import com.uib.ecmanager.modules.coupon.dao.CouponCodeDao;
import com.uib.ecmanager.modules.coupon.dao.CouponDao;
import com.uib.ecmanager.modules.coupon.entity.Coupon;
import com.uib.ecmanager.modules.coupon.entity.CouponCode;
import com.uib.ecmanager.modules.mem.entity.MemMember;

/**
 * 优惠券Service
 * @author limy
 * @version 2015-06-15
 */
@Service
@Transactional(readOnly = true)
public class CouponCodeService extends CrudService<CouponCodeDao, CouponCode> {

	@Autowired
	private CouponCodeDao couponCodeDao;
	@Autowired
	private CouponDao couponDao;
	

	public List<CouponCode> findList(CouponCode couponCode) {
		return super.findList(couponCode);
	}

}