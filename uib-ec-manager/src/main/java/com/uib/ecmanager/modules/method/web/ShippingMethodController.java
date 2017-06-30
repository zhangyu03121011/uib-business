/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.method.web;

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
import com.uib.ecmanager.common.persistence.Page;
import com.uib.ecmanager.common.utils.StringUtils;
import com.uib.ecmanager.common.web.BaseController;
import com.uib.ecmanager.modules.delivery.entity.DeliveryCorporation;
import com.uib.ecmanager.modules.delivery.service.DeliveryCorporationService;
import com.uib.ecmanager.modules.method.entity.ShippingMethod;
import com.uib.ecmanager.modules.method.service.ShippingMethodService;

/**
 * 配送方式Controller
 * @author limy
 * @version 2015-07-15
 */
@Controller
@RequestMapping(value = "${adminPath}/method/shippingMethod")
public class ShippingMethodController extends BaseController {

	@Autowired
	private ShippingMethodService shippingMethodService;
	@Autowired
	private DeliveryCorporationService deliveryCorporationService;
	@ModelAttribute
	public ShippingMethod get(@RequestParam(required=false) String id) {
		ShippingMethod entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = shippingMethodService.get(id);
		}
		if (entity == null){
			entity = new ShippingMethod();
		}
		return entity;
	}
	
	@RequiresPermissions("method:shippingMethod:view")
	@RequestMapping(value = {"list", ""})
	public String list(ShippingMethod shippingMethod, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ShippingMethod> page = shippingMethodService.findPage(new Page<ShippingMethod>(request, response), shippingMethod); 
		model.addAttribute("page", page);
		return "modules/method/shippingMethodList";
	}

	@RequiresPermissions("method:shippingMethod:view")
	@RequestMapping(value = "form")
	public String form(ShippingMethod shippingMethod, Model model) {
		model.addAttribute("shippingMethod", shippingMethod);
		DeliveryCorporation deliverycorp = new DeliveryCorporation();
		model.addAttribute("deliverycorps", deliveryCorporationService.findList(deliverycorp));
		return "modules/method/shippingMethodForm";
	}
	
	@RequiresPermissions("method:shippingMethod:view")
	@RequestMapping(value = "updateFormView")
	public String updateFormView(ShippingMethod shippingMethod, Model model) {
		model.addAttribute("shippingMethod", shippingMethod);
		DeliveryCorporation deliverycorp = new DeliveryCorporation();
		model.addAttribute("deliverycorps", deliveryCorporationService.findList(deliverycorp));
		return "modules/method/shippingMethodupdateForm";
	}

	@RequiresPermissions("method:shippingMethod:save")
	@RequestMapping(value = "save")
	public String save(ShippingMethod shippingMethod, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, shippingMethod)){
			return form(shippingMethod, model);
		}
		shippingMethodService.save(shippingMethod);
		addMessage(redirectAttributes, "保存配送方式成功");
		return "redirect:"+Global.getAdminPath()+"/method/shippingMethod/?repage";
	}
	
	@RequiresPermissions("method:shippingMethod:edit")
	@RequestMapping(value = "update")
	public String update(ShippingMethod shippingMethod, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, shippingMethod)){
			return form(shippingMethod, model);
		}
		shippingMethodService.update(shippingMethod);
		addMessage(redirectAttributes, "修改配送方式成功");
		return "redirect:"+Global.getAdminPath()+"/method/shippingMethod/?repage";
	}
	
	@RequiresPermissions("method:shippingMethod:edit")
	@RequestMapping(value = "delete")
	public String delete(ShippingMethod shippingMethod, RedirectAttributes redirectAttributes) {
		shippingMethodService.delete(shippingMethod);
		addMessage(redirectAttributes, "删除配送方式成功");
		return "redirect:"+Global.getAdminPath()+"/method/shippingMethod/?repage";
	}

}