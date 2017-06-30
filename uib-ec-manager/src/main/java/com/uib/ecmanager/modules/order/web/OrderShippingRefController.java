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
import com.uib.ecmanager.modules.order.entity.OrderShippingRef;
import com.uib.ecmanager.modules.order.service.OrderShippingRefService;

/**
 * 订单与发货关联Controller
 * @author limy
 * @version 2015-06-08
 */
@Controller
@RequestMapping(value = "${adminPath}/order/orderShippingRef")
public class OrderShippingRefController extends BaseController {

	@Autowired
	private OrderShippingRefService orderShippingRefService;
	
	@ModelAttribute
	public OrderShippingRef get(@RequestParam(required=false) String id) {
		OrderShippingRef entity = null;
		try {
			if (StringUtils.isNotBlank(id)){
				entity = orderShippingRefService.get(id);
			}
			if (entity == null){
				entity = new OrderShippingRef();
			}
		} catch (Exception e) {
			logger.error("",e);
		}
		return entity;
	}
	
	@RequiresPermissions("order:orderShippingRef:view")
	@RequestMapping(value = {"list", ""})
	public String list(OrderShippingRef orderShippingRef, HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			Page<OrderShippingRef> page = orderShippingRefService.findPage(new Page<OrderShippingRef>(request, response), orderShippingRef); 
			model.addAttribute("page", page);
		} catch (Exception e) {
			logger.error("",e);
		}
		return "modules/order/orderShippingRefList";
	}

	@RequiresPermissions("order:orderShippingRef:view")
	@RequestMapping(value = "form")
	public String form(OrderShippingRef orderShippingRef, Model model) {
		try {
			model.addAttribute("orderShippingRef", orderShippingRef);
		} catch (Exception e) {
			logger.error("",e);
		}
		return "modules/order/orderShippingRefForm";
	}
	
	@RequiresPermissions("order:orderShippingRef:view")
	@RequestMapping(value = "updateFormView")
	public String updateFormView(OrderShippingRef orderShippingRef, Model model) {
		try {
			model.addAttribute("orderShippingRef", orderShippingRef);
		} catch (Exception e) {
			logger.error("",e);
		}
		return "modules/order/orderShippingRefupdateForm";
	}

	@RequiresPermissions("order:orderShippingRef:save")
	@RequestMapping(value = "save")
	public String save(OrderShippingRef orderShippingRef, Model model, RedirectAttributes redirectAttributes) {
		try {
			if (!beanValidator(model, orderShippingRef)){
				return form(orderShippingRef, model);
			}
			orderShippingRefService.save(orderShippingRef);
			addMessage(redirectAttributes, "保存订单与发货关联成功");
		} catch (Exception e) {
			logger.error("",e);
		}
		return "redirect:"+Global.getAdminPath()+"/order/orderShippingRef/?repage";
	}
	
	@RequiresPermissions("order:orderShippingRef:edit")
	@RequestMapping(value = "update")
	public String update(OrderShippingRef orderShippingRef, Model model, RedirectAttributes redirectAttributes) {
		try {
			if (!beanValidator(model, orderShippingRef)){
				return form(orderShippingRef, model);
			}
			orderShippingRefService.update(orderShippingRef);
			addMessage(redirectAttributes, "修改订单与发货关联成功");
		} catch (Exception e) {
			logger.error("",e);
		}
		return "redirect:"+Global.getAdminPath()+"/order/orderShippingRef/?repage";
	}
	
	@RequiresPermissions("order:orderShippingRef:edit")
	@RequestMapping(value = "delete")
	public String delete(OrderShippingRef orderShippingRef, RedirectAttributes redirectAttributes) {
		try {
			orderShippingRefService.delete(orderShippingRef);
			addMessage(redirectAttributes, "删除订单与发货关联成功");
		} catch (Exception e) {
			logger.error("",e);
		}
		return "redirect:"+Global.getAdminPath()+"/order/orderShippingRef/?repage";
	}

}