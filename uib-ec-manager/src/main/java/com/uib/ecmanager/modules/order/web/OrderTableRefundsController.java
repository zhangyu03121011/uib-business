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
import com.uib.ecmanager.modules.order.entity.OrderTableRefunds;
import com.uib.ecmanager.modules.order.service.OrderTableRefundsService;

/**
 * 退款单Controller
 * @author limy
 * @version 2015-06-08
 */
@Controller
@RequestMapping(value = "${adminPath}/order/orderTableRefunds")
public class OrderTableRefundsController extends BaseController {

	@Autowired
	private OrderTableRefundsService orderTableRefundsService;
	
	@ModelAttribute
	public OrderTableRefunds get(@RequestParam(required=false) String id) {
		OrderTableRefunds entity = null;
		try {
			if (StringUtils.isNotBlank(id)){
				entity = orderTableRefundsService.get(id);
			}
			if (entity == null){
				entity = new OrderTableRefunds();
			}	
		} catch (Exception e) {
			logger.error("",e);
		}
		return entity;
	}
	
	@RequiresPermissions("order:orderTableRefunds:view")
	@RequestMapping(value = {"list", ""})
	public String list(OrderTableRefunds orderTableRefunds, HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			Page<OrderTableRefunds> page = orderTableRefundsService.findPage(new Page<OrderTableRefunds>(request, response), orderTableRefunds); 
			model.addAttribute("page", page);
		} catch (Exception e) {
			logger.error("",e);
		}
		return "modules/order/orderTableRefundsList";
	}

	@RequiresPermissions("order:orderTableRefunds:view")
	@RequestMapping(value = "form")
	public String form(OrderTableRefunds orderTableRefunds, Model model) {
		try {
			model.addAttribute("orderTableRefunds", orderTableRefunds);
		} catch (Exception e) {
			logger.error("",e);
		}
		return "modules/order/orderTableRefundsForm";
	}
	
	@RequiresPermissions("order:orderTableRefunds:view")
	@RequestMapping(value = "updateFormView")
	public String updateFormView(OrderTableRefunds orderTableRefunds, Model model) {
		try {
			model.addAttribute("orderTableRefunds", orderTableRefunds);	
		} catch (Exception e) {
			logger.error("",e);
		}
		return "modules/order/orderTableRefundsupdateForm";
	}

	@RequiresPermissions("order:orderTableRefunds:save")
	@RequestMapping(value = "save")
	public String save(OrderTableRefunds orderTableRefunds, Model model, RedirectAttributes redirectAttributes) {
		try {
			/*if (!beanValidator(model, orderTableRefunds)){
			return form(orderTableRefunds, model);
		}*/
		orderTableRefundsService.save(orderTableRefunds);
		addMessage(redirectAttributes, "保存退款单成功");
		} catch (Exception e) {
			logger.error("",e);
		}
		return "redirect:"+Global.getAdminPath()+"/order/orderTableRefunds/?repage";
	}
	
	@RequiresPermissions("order:orderTableRefunds:edit")
	@RequestMapping(value = "update")
	public String update(OrderTableRefunds orderTableRefunds, Model model, RedirectAttributes redirectAttributes) {
		try {
			/*if (!beanValidator(model, orderTableRefunds)){
			return form(orderTableRefunds, model);
		}*/
		orderTableRefundsService.update(orderTableRefunds);
		addMessage(redirectAttributes, "修改退款单成功");
		} catch (Exception e) {
			logger.error("",e);
		}
		return "redirect:"+Global.getAdminPath()+"/order/orderTableRefunds/?repage";
	}
	
	@RequiresPermissions("order:orderTableRefunds:edit")
	@RequestMapping(value = "delete")
	public String delete(OrderTableRefunds orderTableRefunds, RedirectAttributes redirectAttributes) {
		try {
			orderTableRefundsService.delete(orderTableRefunds);
			addMessage(redirectAttributes, "删除退款单成功");
		} catch (Exception e) {
			logger.error("",e);
		}
		return "redirect:"+Global.getAdminPath()+"/order/orderTableRefunds/?repage";
	}

}