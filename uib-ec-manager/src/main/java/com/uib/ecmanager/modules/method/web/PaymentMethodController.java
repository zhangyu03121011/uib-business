/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.method.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.uib.ecmanager.common.config.Global;
import com.uib.ecmanager.common.enums.Pay_Method;
import com.uib.ecmanager.common.persistence.Page;
import com.uib.ecmanager.common.utils.StringUtils;
import com.uib.ecmanager.common.web.BaseController;
import com.uib.ecmanager.modules.method.entity.PaymentMethod;
import com.uib.ecmanager.modules.method.entity.ShippingMethod;
import com.uib.ecmanager.modules.method.service.PaymentMethodService;
import com.uib.ecmanager.modules.method.service.ShippingMethodService;

/**
 * 支付方式Controller
 * @author limy
 * @version 2015-07-15
 */
@Controller
@RequestMapping(value = "${adminPath}/method/paymentMethod")
public class PaymentMethodController extends BaseController {

	@Autowired
	private PaymentMethodService paymentMethodService;
	@Autowired
	private ShippingMethodService shippingMethodService;
	
	@ModelAttribute
	public PaymentMethod get(@RequestParam(required=false) String id) {
		PaymentMethod entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = paymentMethodService.get(id);
		}
		if (entity == null){
			entity = new PaymentMethod();
		}
		return entity;
	}
	
	@RequiresPermissions("method:paymentMethod:view")
	@RequestMapping(value = {"list", ""})
	public String list(PaymentMethod paymentMethod, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<PaymentMethod> page = paymentMethodService.findPage(new Page<PaymentMethod>(request, response), paymentMethod); 
		model.addAttribute("page", page);
		return "modules/method/paymentMethodList";
	}

	@RequiresPermissions("method:paymentMethod:view")
	@RequestMapping(value = "form")
	public String form(PaymentMethod paymentMethod, Model model) {
		model.addAttribute("paymentMethod", paymentMethod);
		ShippingMethod shippingMethod = new ShippingMethod();
		model.addAttribute("shippingMethods", shippingMethodService.findList(shippingMethod));
		//支付方式
		Map<Integer,String> list = new HashMap<Integer,String>();
		for(Pay_Method pm : Pay_Method.values()){
			list.put(pm.getIndex(), pm.getDescription());
		}
		model.addAttribute("list", list);
		return "modules/method/paymentMethodForm";
	}
	
	@RequiresPermissions("method:paymentMethod:view")
	@RequestMapping(value = "updateFormView")
	public String updateFormView(PaymentMethod paymentMethod, Model model) {
		model.addAttribute("paymentMethod", paymentMethod);
		ShippingMethod shippingMethod = new ShippingMethod();
		model.addAttribute("shippingMethods", shippingMethodService.findList(shippingMethod));
		//支付方式
		Map<Integer,String> list = new HashMap<Integer,String>();
		for(Pay_Method pm : Pay_Method.values()){
			list.put(pm.getIndex(), pm.getDescription());
		}
		model.addAttribute("list", list);
		return "modules/method/paymentMethodupdateForm";
	}

	@RequiresPermissions("method:paymentMethod:save")
	@RequestMapping(value = "save")
	public String save(PaymentMethod paymentMethod, Model model, RedirectAttributes redirectAttributes) {
//		if (!beanValidator(model, paymentMethod)){
//			return form(paymentMethod, model);
//		}
		paymentMethodService.save(paymentMethod);
		addMessage(redirectAttributes, "保存支付方式成功");
		return "redirect:"+Global.getAdminPath()+"/method/paymentMethod/?repage";
	}
	
	@RequiresPermissions("method:paymentMethod:edit")
	@RequestMapping(value = "update")
	public String update(PaymentMethod paymentMethod, Model model, RedirectAttributes redirectAttributes) {
//		if (!beanValidator(model, paymentMethod)){
//			return form(paymentMethod, model);
//		}
		paymentMethodService.update(paymentMethod);
		addMessage(redirectAttributes, "修改支付方式成功");
		return "redirect:"+Global.getAdminPath()+"/method/paymentMethod/?repage";
	}
	
	@RequiresPermissions("method:paymentMethod:edit")
	@RequestMapping(value = "delete")
	public String delete(PaymentMethod paymentMethod, RedirectAttributes redirectAttributes) {
		paymentMethodService.delete(paymentMethod);
		addMessage(redirectAttributes, "删除支付方式成功");
		return "redirect:"+Global.getAdminPath()+"/method/paymentMethod/?repage";
	}

}