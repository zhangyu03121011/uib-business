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
import com.uib.ecmanager.modules.order.entity.OrderTableLog;
import com.uib.ecmanager.modules.order.service.OrderTableLogService;

/**
 * 订单日志Controller
 * @author limy
 * @version 2015-06-01
 */
@Controller
@RequestMapping(value = "${adminPath}/order/orderTableLog")
public class OrderTableLogController extends BaseController {

	@Autowired
	private OrderTableLogService orderTableLogService;
	
	@ModelAttribute
	public OrderTableLog get(@RequestParam(required=false) String id) {
		OrderTableLog entity = null;
		try {
			if (StringUtils.isNotBlank(id)){
				entity = orderTableLogService.get(id);
			}
			if (entity == null){
				entity = new OrderTableLog();
			}
		} catch (Exception e) {
			logger.error("",e);
		}
		return entity;
	}
	
	@RequiresPermissions("order:orderTableLog:view")
	@RequestMapping(value = {"list", ""})
	public String list(OrderTableLog orderTableLog, HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			Page<OrderTableLog> page = orderTableLogService.findPage(new Page<OrderTableLog>(request, response), orderTableLog); 
			model.addAttribute("page", page);
		} catch (Exception e) {
			logger.error("",e);
		}
		return "modules/order/orderTableLogList";
	}

	@RequiresPermissions("order:orderTableLog:view")
	@RequestMapping(value = "form")
	public String form(OrderTableLog orderTableLog, Model model) {
		try {
			model.addAttribute("orderTableLog", orderTableLog);
		} catch (Exception e) {
			logger.error("",e);
		}
		return "modules/order/orderTableLogForm";
	}
	
	@RequiresPermissions("order:orderTableLog:view")
	@RequestMapping(value = "updateFormView")
	public String updateFormView(OrderTableLog orderTableLog, Model model) {
		
		try {
			model.addAttribute("orderTableLog", orderTableLog);
		} catch (Exception e) {
			logger.error("",e);
		}
		return "modules/order/orderTableLogupdateForm";
	}

	@RequiresPermissions("order:orderTableLog:save")
	@RequestMapping(value = "save")
	public String save(OrderTableLog orderTableLog, Model model, RedirectAttributes redirectAttributes) {
		try {
			if (!beanValidator(model, orderTableLog)){
				return form(orderTableLog, model);
			}
			orderTableLogService.save(orderTableLog);
			addMessage(redirectAttributes, "保存订单日志成功");
		} catch (Exception e) {
			logger.error("",e);
		}
		
		return "redirect:"+Global.getAdminPath()+"/order/orderTableLog/?repage";
	}
	
	@RequiresPermissions("order:orderTableLog:edit")
	@RequestMapping(value = "update")
	public String update(OrderTableLog orderTableLog, Model model, RedirectAttributes redirectAttributes) {
		try {
			if (!beanValidator(model, orderTableLog)){
				return form(orderTableLog, model);
			}
			orderTableLogService.update(orderTableLog);
			addMessage(redirectAttributes, "修改订单日志成功");
		} catch (Exception e) {
			logger.error("",e);
		}
		
		return "redirect:"+Global.getAdminPath()+"/order/orderTableLog/?repage";
	}
	
	@RequiresPermissions("order:orderTableLog:edit")
	@RequestMapping(value = "delete")
	public String delete(OrderTableLog orderTableLog, RedirectAttributes redirectAttributes) {
		try {
			orderTableLogService.delete(orderTableLog);
			addMessage(redirectAttributes, "删除订单日志成功");
		} catch (Exception e) {
			logger.error("",e);
		}
		
		return "redirect:"+Global.getAdminPath()+"/order/orderTableLog/?repage";
	}

}