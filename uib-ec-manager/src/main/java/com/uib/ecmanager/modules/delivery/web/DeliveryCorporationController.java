/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.delivery.web;

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

/**
 * 物流公司Controller
 * @author gaven
 * @version 2015-06-08
 */
@Controller
@RequestMapping(value = "${adminPath}/delivery/deliveryCorporation")
public class DeliveryCorporationController extends BaseController {

	@Autowired
	private DeliveryCorporationService deliveryCorporationService;
	
	@ModelAttribute
	public DeliveryCorporation get(@RequestParam(required=false) String id) {
		DeliveryCorporation entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = deliveryCorporationService.get(id);
		}
		if (entity == null){
			entity = new DeliveryCorporation();
		}
		return entity;
	}
	
	@RequiresPermissions("delivery:deliveryCorporation:view")
	@RequestMapping(value = {"list", ""})
	public String list(DeliveryCorporation deliveryCorporation, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<DeliveryCorporation> page = deliveryCorporationService.findPage(new Page<DeliveryCorporation>(request, response), deliveryCorporation); 
		model.addAttribute("page", page);
		return "modules/delivery/deliveryCorporationList";
	}

	@RequiresPermissions("delivery:deliveryCorporation:view")
	@RequestMapping(value = "form")
	public String form(DeliveryCorporation deliveryCorporation, Model model) {
		model.addAttribute("deliveryCorporation", deliveryCorporation);
		return "modules/delivery/deliveryCorporationForm";
	}
	
	@RequiresPermissions("delivery:deliveryCorporation:view")
	@RequestMapping(value = "updateFormView")
	public String updateFormView(DeliveryCorporation deliveryCorporation, Model model) {
		model.addAttribute("deliveryCorporation", deliveryCorporation);
		return "modules/delivery/deliveryCorporationupdateForm";
	}

	@RequiresPermissions("delivery:deliveryCorporation:save")
	@RequestMapping(value = "save")
	public String save(DeliveryCorporation deliveryCorporation, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, deliveryCorporation)){
			return form(deliveryCorporation, model);
		}
		deliveryCorporationService.save(deliveryCorporation);
		addMessage(redirectAttributes, "保存物流公司成功");
		return "redirect:"+Global.getAdminPath()+"/delivery/deliveryCorporation/?repage";
	}
	
	@RequiresPermissions("delivery:deliveryCorporation:edit")
	@RequestMapping(value = "update")
	public String update(DeliveryCorporation deliveryCorporation, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, deliveryCorporation)){
			return form(deliveryCorporation, model);
		}
		deliveryCorporationService.update(deliveryCorporation);
		addMessage(redirectAttributes, "修改物流公司成功");
		return "redirect:"+Global.getAdminPath()+"/delivery/deliveryCorporation/?repage";
	}
	
	@RequiresPermissions("delivery:deliveryCorporation:edit")
	@RequestMapping(value = "delete")
	public String delete(DeliveryCorporation deliveryCorporation, RedirectAttributes redirectAttributes) {
		deliveryCorporationService.delete(deliveryCorporation);
		addMessage(redirectAttributes, "删除物流公司成功");
		return "redirect:"+Global.getAdminPath()+"/delivery/deliveryCorporation/?repage";
	}

}