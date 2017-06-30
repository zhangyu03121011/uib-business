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
public class CouponService extends CrudService<CouponDao, Coupon> {

	@Autowired
	private CouponCodeDao couponCodeDao;
	@Autowired
	private CouponDao couponDao;
	
	public Coupon get(String id) {
		Coupon coupon = super.get(id);
		coupon.setCouponCodeList(couponCodeDao.findList(new CouponCode(coupon)));
		return coupon;
	}
	
	public List<Coupon> findList(Coupon coupon) {
		return super.findList(coupon);
	}
	
	public Page<Coupon> findPage(Page<Coupon> page, Coupon coupon) {
		return super.findPage(page, coupon);
	}
	
	@Transactional(readOnly = false)
	public void save(Coupon coupon) {
		super.save(coupon);
		for (CouponCode couponCode : coupon.getCouponCodeList()){
			if (couponCode.getId() == null){
				continue;
			}
			if (CouponCode.DEL_FLAG_NORMAL.equals(couponCode.getDelFlag())){
				if (StringUtils.isBlank(couponCode.getId())){
					couponCode.setCoupon(coupon);
					couponCode.preInsert();
					couponCodeDao.insert(couponCode);
				}
			}else{
				couponCodeDao.delete(couponCode);
			}
		}
	}
	
	
	@Transactional(readOnly = false)
	public void update(Coupon coupon) {
		super.update(coupon);
		for (CouponCode couponCode : coupon.getCouponCodeList()){
			if (couponCode.getId() == null){
				continue;
			}
			if (CouponCode.DEL_FLAG_NORMAL.equals(couponCode.getDelFlag())){
				
					couponCode.preUpdate();
					couponCodeDao.update(couponCode);
				
			}else{
				couponCodeDao.delete(couponCode);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(Coupon coupon) {
		super.delete(coupon);
		couponCodeDao.delete(new CouponCode(coupon));
	}
	
	@Transactional(readOnly = false)
	public CouponCode build(Coupon coupon) {
		Assert.notNull(coupon);
		CouponCode couponCode = new CouponCode();
		couponCode.setId(UUID.randomUUID().toString());
		String uuid = UUID.randomUUID().toString().toUpperCase();
		couponCode.setCode(uuid.substring(0, 8) + uuid.substring(9, 13) + uuid.substring(14, 18) + uuid.substring(19, 23) + uuid.substring(24));
		couponCode.setIsUsed(0);
		couponCode.setCoupon(coupon);
		couponCode.setMemMember(null);
		couponCode.setCreateDate(new Date());
		couponCode.setUpdateDate(new Date());
		couponCodeDao.update(couponCode);
		return couponCode;
	}
	@Transactional(readOnly = false)
	public List<CouponCode> builder(Coupon coupon) {
		List<CouponCode> couponCodes = new ArrayList<CouponCode>();
		for (int i = 0; i < coupon.getSum(); i++) {
			CouponCode couponCode = build(coupon);
			couponCodes.add(couponCode);
			couponCodeDao.insert(couponCode);
		}
		coupon.setSum(0);
		couponDao.update(coupon);
		return couponCodes;
	}
	
	@Transactional(readOnly = false)
	public Integer totalCount(Coupon coupon) {
		System.out.println(coupon+"\n----totalCount-------");
		Assert.notNull(coupon);
		System.out.println(coupon+"\n------totalCount-----");
		return couponCodeDao.totalCount(coupon);
	}
	
	@Transactional(readOnly = false)
	public Integer usedCount(Coupon coupon) {
		System.out.println(coupon+"\n----usedCount-------");
		Assert.notNull(coupon);
		System.out.println(coupon+"\n------usedCount-----");
		return couponCodeDao.usedCount(coupon);
	}


}