/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.order.web;

import java.util.List;

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
import com.uib.ecmanager.modules.mem.dao.MemMemberDao;
import com.uib.ecmanager.modules.mem.entity.MemMember;
import com.uib.ecmanager.modules.order.entity.OrderTablePayment;
import com.uib.ecmanager.modules.order.service.OrderTablePaymentService;

/**
 * 收款单Controller
 * @author limy
 * @version 2015-06-08
 */
@Controller
@RequestMapping(value = "${adminPath}/order/orderTablePayment")
public class OrderTablePaymentController extends BaseController {

	@Autowired
	private OrderTablePaymentService orderTablePaymentService;
	@Autowired
	private MemMemberDao memMemberDao;
	@ModelAttribute
	public OrderTablePayment get(@RequestParam(required=false) String id) {
		OrderTablePayment entity = null;
		try {
			if (StringUtils.isNotBlank(id)){
				entity = orderTablePaymentService.get(id);
			}
			if (entity == null){
				entity = new OrderTablePayment();
			}	
		} catch (Exception e) {
			logger.error("",e);
		}
		return entity;
	}
	
	@RequiresPermissions("order:orderTablePayment:view")
	@RequestMapping(value = {"list", ""})
	public String list(OrderTablePayment orderTablePayment, HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			Page<OrderTablePayment> page = orderTablePaymentService.findPage(new Page<OrderTablePayment>(request, response), orderTablePayment); 
			List<OrderTablePayment> list = page.getList();
			for (OrderTablePayment orderTablePayment2 : list) {
				System.out.println(orderTablePayment2.getMember());
				//会员
				MemMember member = new MemMember();
				if(orderTablePayment2.getMember()!=null){
					member = memMemberDao.get(orderTablePayment2.getMember());
					System.out.println(member);
					model.addAttribute("member", member);
				}
			}
			
			model.addAttribute("page", page);
		} catch (Exception e) {
			logger.error("",e);
		}
		return "modules/order/orderTablePaymentList";
	}

	@RequiresPermissions("order:orderTablePayment:view")
	@RequestMapping(value = "form")
	public String form(OrderTablePayment orderTablePayment, Model model) {
		try {
			model.addAttribute("orderTablePayment", orderTablePayment);

			//会员
			MemMember member = new MemMember();
			if(orderTablePayment.getMember()!=null){
				member = memMemberDao.get(orderTablePayment.getMember());
				model.addAttribute("member", member);
			}
		} catch (Exception e) {
			logger.error("",e);
		}
		return "modules/order/orderTablePaymentForm";
	}
	
	@RequiresPermissions("order:orderTablePayment:view")
	@RequestMapping(value = "updateFormView")
	public String updateFormView(OrderTablePayment orderTablePayment, Model model) {
		try {
			model.addAttribute("orderTablePayment", orderTablePayment);
		} catch (Exception e) {
			logger.error("",e);
		}
		return "modules/order/orderTablePaymentupdateForm";
	}

	@RequiresPermissions("order:orderTablePayment:save")
	@RequestMapping(value = "save")
	public String save(OrderTablePayment orderTablePayment, Model model, RedirectAttributes redirectAttributes) {
		try {
			/*if (!beanValidator(model, orderTablePayment)){
			return form(orderTablePayment, model);
			}*/
			orderTablePaymentService.save(orderTablePayment);
			addMessage(redirectAttributes, "保存收款单成功");
		} catch (Exception e) {
			logger.error("",e);
		}
		return "redirect:"+Global.getAdminPath()+"/order/orderTablePayment/?repage";
	}
	
	@RequiresPermissions("order:orderTablePayment:edit")
	@RequestMapping(value = "update")
	public String update(OrderTablePayment orderTablePayment, Model model, RedirectAttributes redirectAttributes) {
		try {
			/*if (!beanValidator(model, orderTablePayment)){
			return form(orderTablePayment, model);
			}*/
			orderTablePaymentService.update(orderTablePayment);
			addMessage(redirectAttributes, "修改收款单成功");
		} catch (Exception e) {
			logger.error("",e);
		}
		return "redirect:"+Global.getAdminPath()+"/order/orderTablePayment/?repage";
	}
	
	@RequiresPermissions("order:orderTablePayment:edit")
	@RequestMapping(value = "delete")
	public String delete(OrderTablePayment orderTablePayment, RedirectAttributes redirectAttributes) {
		try {
			orderTablePaymentService.delete(orderTablePayment);
			addMessage(redirectAttributes, "删除收款单成功");
		} catch (Exception e) {
			logger.error("",e);
		}
		return "redirect:"+Global.getAdminPath()+"/order/orderTablePayment/?repage";
	}

}