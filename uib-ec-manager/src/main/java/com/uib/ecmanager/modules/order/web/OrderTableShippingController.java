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
import com.uib.ecmanager.modules.order.entity.OrderTable;
import com.uib.ecmanager.modules.order.entity.OrderTableShipping;
import com.uib.ecmanager.modules.order.service.OrderTableService;
import com.uib.ecmanager.modules.order.service.OrderTableShippingService;

/**
 * 发货单Controller
 * @author limy
 * @version 2015-06-08
 */
@Controller
@RequestMapping(value = "${adminPath}/order/orderTableShipping")
public class OrderTableShippingController extends BaseController {

	@Autowired
	private OrderTableShippingService orderTableShippingService;
	@Autowired
	private OrderTableService orderTableService;
	@ModelAttribute
	public OrderTableShipping get(@RequestParam(required=false) String id) {
		OrderTableShipping entity = null;
		try {
			if (StringUtils.isNotBlank(id)){
				entity = orderTableShippingService.get(id);
			}
			if (entity == null){
				entity = new OrderTableShipping();
			}
		} catch (Exception e) {
			logger.error("",e);
		}
		return entity;
	}
	
	@RequiresPermissions("order:orderTableShipping:view")
	@RequestMapping(value = {"list", ""})
	public String list(OrderTableShipping orderTableShipping, HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			Page<OrderTableShipping> page = orderTableShippingService.findPage(new Page<OrderTableShipping>(request, response), orderTableShipping); 
			for (OrderTableShipping ots : page.getList()) {
				OrderTable order = orderTableService.findOrderTableByOrderNo(ots.getOrderNo());
				ots.setOrderId(order.getId());
			}
			model.addAttribute("page", page);	
		} catch (Exception e) {
			logger.error("",e);
		}
		
		return "modules/order/orderTableShippingList";
	}

	@RequiresPermissions("order:orderTableShipping:view")
	@RequestMapping(value = "form")
	public String form(OrderTableShipping orderTableShipping, Model model) {
		try {
			model.addAttribute("orderTableShipping", orderTableShipping);
		} catch (Exception e) {
			logger.error("",e);
		}
		
		return "modules/order/orderTableShippingForm";
	}
	
	@RequiresPermissions("order:orderTableShipping:view")
	@RequestMapping(value = "updateFormView")
	public String updateFormView(OrderTableShipping orderTableShipping, Model model) {
		try {
			model.addAttribute("orderTableShipping", orderTableShipping);
		} catch (Exception e) {
			logger.error("",e);
		}
		
		return "modules/order/orderTableShippingupdateForm";
	}

	@RequiresPermissions("order:orderTableShipping:save")
	@RequestMapping(value = "save")
	public String save(OrderTableShipping orderTableShipping, Model model, RedirectAttributes redirectAttributes) {
		try {
			if (!beanValidator(model, orderTableShipping)){
				return form(orderTableShipping, model);
			}
			orderTableShippingService.save(orderTableShipping);
			addMessage(redirectAttributes, "保存发货单成功");
		} catch (Exception e) {
			logger.error("",e);
		}
		
		return "redirect:"+Global.getAdminPath()+"/order/orderTableShipping/?repage";
	}
	
	@RequiresPermissions("order:orderTableShipping:edit")
	@RequestMapping(value = "update")
	public String update(OrderTableShipping orderTableShipping, Model model, RedirectAttributes redirectAttributes) {
		try {
			if (!beanValidator(model, orderTableShipping)){
				return form(orderTableShipping, model);
			}
			orderTableShippingService.update(orderTableShipping);
			addMessage(redirectAttributes, "修改发货单成功");
		} catch (Exception e) {
			logger.error("",e);
		}
		
		return "redirect:"+Global.getAdminPath()+"/order/orderTableShipping/?repage";
	}
	
	@RequiresPermissions("order:orderTableShipping:edit")
	@RequestMapping(value = "delete")
	public String delete(OrderTableShipping orderTableShipping, RedirectAttributes redirectAttributes) {
		try {
			orderTableShippingService.delete(orderTableShipping);
			addMessage(redirectAttributes, "删除发货单成功");
		} catch (Exception e) {
			logger.error("",e);
		}
		
		return "redirect:"+Global.getAdminPath()+"/order/orderTableShipping/?repage";
	}

}