/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.coupon.web;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.uib.ecmanager.common.config.Global;
import com.uib.ecmanager.common.enums.PresentType;
import com.uib.ecmanager.common.enums.Refunds_Method;
import com.uib.ecmanager.common.persistence.ExcelView;
import com.uib.ecmanager.common.persistence.Page;
import com.uib.ecmanager.common.utils.FreeMarkers;
import com.uib.ecmanager.common.utils.StringUtils;
import com.uib.ecmanager.common.web.BaseController;
import com.uib.ecmanager.modules.coupon.dao.CouponCodeDao;
import com.uib.ecmanager.modules.coupon.entity.Coupon;
import com.uib.ecmanager.modules.coupon.entity.CouponCode;
import com.uib.ecmanager.modules.coupon.service.CouponCodeService;
import com.uib.ecmanager.modules.coupon.service.CouponService;
import com.uib.ecmanager.modules.sys.service.SystemService;

/**
 * 优惠券Controller
 * @author limy
 * @version 2015-06-15
 */
@Controller
@RequestMapping(value = "${adminPath}/coupon/coupon")
public class CouponController extends BaseController {

	@Autowired
	private CouponService couponService;
	@Autowired
	private CouponCodeService couponCodeService;
	@Autowired
	private CouponCodeDao couponCodeDao;
	@Autowired
	private SystemService systemService;
	
	@ModelAttribute
	public Coupon get(@RequestParam(required=false) String id) {
		Coupon entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = couponService.get(id);
		}
		if (entity == null){
			entity = new Coupon();
		}
		return entity;
	}
	
	@RequiresPermissions("coupon:coupon:view")
	@RequestMapping(value = {"list", ""})
	public String list(Coupon coupon, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Coupon> page = couponService.findPage(new Page<Coupon>(request, response), coupon); 
		model.addAttribute("page", page);
		return "modules/coupon/couponList";
	}
	
	@RequiresPermissions("coupon:coupon:view")
	@RequestMapping(value = "form")
	public String form(Coupon coupon, Model model) {
		model.addAttribute("coupon", coupon);
		//赠送类型
		Map<Integer,String> listPresentType = new HashMap<Integer,String>();
		for(PresentType pt : PresentType.values()){
			listPresentType.put(pt.getIndex(), pt.getDescription());
		}
		model.addAttribute("listPresentType", listPresentType);
		
		return "modules/coupon/couponForm";
	}
	
	@RequiresPermissions("coupon:coupon:view")
	@RequestMapping(value = "updateFormView")
	public String updateFormView(Coupon coupon, Model model) {
		model.addAttribute("coupon", coupon);
		//赠送类型
		Map<Integer,String> listPresentType = new HashMap<Integer,String>();
		for(PresentType pt : PresentType.values()){
			listPresentType.put(pt.getIndex(), pt.getDescription());
		}
		model.addAttribute("listPresentType", listPresentType);
		return "modules/coupon/couponupdateForm";
	}
	@RequiresPermissions("coupon:coupon:view")
	@RequestMapping(value = "couponBuild")
	public String couponBuild(String id, Model model) {
		Coupon coupon = couponService.get(id);
		model.addAttribute("coupon", coupon);
		model.addAttribute("totalCount", couponService.totalCount(coupon));
		model.addAttribute("usedCount", couponService.usedCount(coupon));
		return "modules/coupon/couponBuild";
	}

	@RequiresPermissions("coupon:coupon:save")
	@RequestMapping(value = "save")
	public String save(Coupon coupon, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, coupon)){
			return form(coupon, model);
		}
		couponService.save(coupon);
		addMessage(redirectAttributes, "保存优惠券成功");
		return "redirect:"+Global.getAdminPath()+"/coupon/coupon/?repage";
	}
	
	@RequiresPermissions("coupon:coupon:edit")
	@RequestMapping(value = "update")
	public String update(Coupon coupon, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, coupon)){
			return form(coupon, model);
		}
		couponService.update(coupon);
		addMessage(redirectAttributes, "修改优惠券成功");
		return "redirect:"+Global.getAdminPath()+"/coupon/coupon/?repage";
	}
	
	@RequiresPermissions("coupon:coupon:edit")
	@RequestMapping(value = "delete")
	public String delete(Coupon coupon, RedirectAttributes redirectAttributes) {
		couponService.delete(coupon);
		addMessage(redirectAttributes, "删除优惠券成功");
		return "redirect:"+Global.getAdminPath()+"/coupon/coupon/?repage";
	}
	/**
	 * 下载优惠码
	 */
	@RequiresPermissions("coupon:coupon:edit")
	@RequestMapping(value = "download")
	public ModelAndView download(String id ) {
		Coupon coupon = couponService.get(id);
		List<CouponCode> data = couponService.builder(coupon);
		String filename = coupon.getName() +"-"+ new SimpleDateFormat("yyyyMM").format(new Date()) + ".xls";
		String[] contents = new String[4];
		contents[0] = "优惠券: " + coupon.getName();
		contents[1] = "生成数量: " + coupon.getSum();
		contents[2] = "操作员: " + systemService.getCurrentUsername();
		contents[3] = "生成日期: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		return new ModelAndView(new ExcelView(filename, null, new String[] { "code" }, new String[] {"优惠券"}, null, null, data, contents));
	}

}