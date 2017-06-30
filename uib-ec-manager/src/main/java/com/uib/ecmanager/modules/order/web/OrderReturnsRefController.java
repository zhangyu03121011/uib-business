/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.order.web;

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
import com.uib.ecmanager.modules.order.entity.OrderReturnsRef;
import com.uib.ecmanager.modules.order.service.OrderReturnsRefService;

/**
 * 订单与退货关联Controller
 * @author limy
 * @version 2015-06-08
 */
@Controller
@RequestMapping(value = "${adminPath}/order/orderReturnsRef")
public class OrderReturnsRefController extends BaseController {

	@Autowired
	private OrderReturnsRefService orderReturnsRefService;
	
	@ModelAttribute
	public OrderReturnsRef get(@RequestParam(required=false) String id) {
		OrderReturnsRef entity = null;
		try {
			if (StringUtils.isNotBlank(id)){
				entity = orderReturnsRefService.get(id);
			}
			if (entity == null){
				entity = new OrderReturnsRef();
			}
		} catch (Exception e) {
			logger.error("",e);
		}
		
		return entity;
	}
	
	@RequiresPermissions("order:orderReturnsRef:view")
	@RequestMapping(value = {"list", ""})
	public String list(OrderReturnsRef orderReturnsRef, HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			Page<OrderReturnsRef> page = orderReturnsRefService.findPage(new Page<OrderReturnsRef>(request, response), orderReturnsRef); 
			model.addAttribute("page", page);	
		} catch (Exception e) {
			logger.error("",e);
		}
		return "modules/order/orderReturnsRefList";
	}

	@RequiresPermissions("order:orderReturnsRef:view")
	@RequestMapping(value = "form")
	public String form(OrderReturnsRef orderReturnsRef, Model model) {
		try {
			model.addAttribute("orderReturnsRef", orderReturnsRef);
		} catch (Exception e) {
			logger.error("",e);
		}
		return "modules/order/orderReturnsRefForm";
	}
	
	@RequiresPermissions("order:orderReturnsRef:view")
	@RequestMapping(value = "updateFormView")
	public String updateFormView(OrderReturnsRef orderReturnsRef, Model model) {
		try {
			model.addAttribute("orderReturnsRef", orderReturnsRef);
		} catch (Exception e) {
			logger.error("",e);
		}
		
		return "modules/order/orderReturnsRefupdateForm";
	}

	@RequiresPermissions("order:orderReturnsRef:save")
	@RequestMapping(value = "save")
	public String save(OrderReturnsRef orderReturnsRef, Model model, RedirectAttributes redirectAttributes) {
		try {
			if (!beanValidator(model, orderReturnsRef)){
				return form(orderReturnsRef, model);
			}
			orderReturnsRefService.save(orderReturnsRef);
			addMessage(redirectAttributes, "保存订单与退货关联成功");	
		} catch (Exception e) {
			logger.error("",e);
		}
		return "redirect:"+Global.getAdminPath()+"/order/orderReturnsRef/?repage";
	}
	
	@RequiresPermissions("order:orderReturnsRef:edit")
	@RequestMapping(value = "update")
	public String update(OrderReturnsRef orderReturnsRef, Model model, RedirectAttributes redirectAttributes) {
		try {
			if (!beanValidator(model, orderReturnsRef)){
				return form(orderReturnsRef, model);
			}
			orderReturnsRefService.update(orderReturnsRef);
			addMessage(redirectAttributes, "修改订单与退货关联成功");
		} catch (Exception e) {
			logger.error("",e);
		}
		return "redirect:"+Global.getAdminPath()+"/order/orderReturnsRef/?repage";
	}
	
	@RequiresPermissions("order:orderReturnsRef:edit")
	@RequestMapping(value = "delete")
	public String delete(OrderReturnsRef orderReturnsRef, RedirectAttributes redirectAttributes) {
		try {
			orderReturnsRefService.delete(orderReturnsRef);
			addMessage(redirectAttributes, "删除订单与退货关联成功");
		} catch (Exception e) {
			logger.error("",e);
		}
		return "redirect:"+Global.getAdminPath()+"/order/orderReturnsRef/?repage";
	}

}