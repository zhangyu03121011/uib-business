/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.coupon.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.uib.ecmanager.common.persistence.Page;
import com.uib.ecmanager.common.utils.StringUtils;
import com.uib.ecmanager.common.web.BaseController;
import com.uib.ecmanager.modules.coupon.dao.CouponCodeDao;
import com.uib.ecmanager.modules.coupon.entity.Coupon;
import com.uib.ecmanager.modules.coupon.entity.CouponCode;
import com.uib.ecmanager.modules.coupon.service.CouponCodeService;
import com.uib.ecmanager.modules.coupon.service.CouponService;
import com.uib.ecmanager.modules.sys.service.SystemService;

/**
 * 优惠码Controller
 * @author limy
 * @version 2016-06-2
 */
@Controller
@RequestMapping(value = "${adminPath}/coupon/couponCode")
public class CouponCodeController extends BaseController {

	@Autowired
	private CouponService couponService;
	@Autowired
	private CouponCodeService couponCodeService;
	@Autowired
	private CouponCodeDao couponCodeDao;
	@Autowired
	private SystemService systemService;
	
	@RequiresPermissions("coupon:coupon:view")
	@RequestMapping(value = {"couponCodelist", ""})
	public String couponCodelist(Coupon coupon, HttpServletRequest request, HttpServletResponse response, Model model) {
		CouponCode couponCode = new CouponCode();
		couponCode.setCoupon(coupon);
		Page<CouponCode> page = couponCodeService.findPage(new Page<CouponCode>(request, response), couponCode); 
		model.addAttribute("page", page);
		return "modules/coupon/couponCodeList";
	}


}