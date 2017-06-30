/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.mem.web;

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
import com.uib.ecmanager.modules.mem.entity.Deposit;
import com.uib.ecmanager.modules.mem.entity.MemMember;
import com.uib.ecmanager.modules.mem.service.DepositService;
import com.uib.ecmanager.modules.mem.service.MemMemberService;
import com.uib.ecmanager.modules.order.dao.OrderTableDao;
import com.uib.ecmanager.modules.order.entity.OrderTable;
import com.uib.ecmanager.modules.order.entity.OrderTablePayment;
import com.uib.ecmanager.modules.order.service.OrderTablePaymentService;
import com.uib.ecmanager.modules.order.service.OrderTableService;

/**
 * 预存款Controller
 * @author limy
 * @version 2015-06-15
 */
@Controller
@RequestMapping(value = "${adminPath}/mem/deposit")
public class DepositController extends BaseController {

	@Autowired
	private DepositService depositService;
	@Autowired
	private OrderTableService orderTableService;
	@Autowired
	private MemMemberService memMemberService;
	@Autowired
	private OrderTablePaymentService orderTablePaymentService;
	
	@ModelAttribute
	public Deposit get(@RequestParam(required=false) String id) {
		Deposit entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = depositService.get(id);
		}
		if (entity == null){
			entity = new Deposit();
		}
		return entity;
	}
	
	@RequiresPermissions("mem:deposit:view")
	@RequestMapping(value = {"list", ""})
	public String list(Deposit deposit, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Deposit> page = depositService.findPage(new Page<Deposit>(request, response), deposit); 
		List<Deposit> list = page.getList();
		for (Deposit deposit2 : list) {
			OrderTable orderTable = new OrderTable();
			MemMember memMember = new MemMember();
			OrderTablePayment orderTablePayment = new OrderTablePayment();
			if(deposit2.getOrderTable()!=null){
				orderTable = orderTableService.get(deposit2.getOrderTable().getId());
				memMember = memMemberService.get(deposit2.getMemMember().getId());
				orderTablePayment = orderTablePaymentService.get(deposit2.getOrderTablePayment().getId());
				model.addAttribute("orderTable", orderTable);
				model.addAttribute("memMember", memMember);
				model.addAttribute("orderTablePayment", orderTablePayment);
			}
			System.out.println(deposit2.getOrderTable());
			System.out.println(deposit2.getMemMember());
			System.out.println(deposit2.getOrderTablePayment());
		}
		model.addAttribute("page", page);
		return "modules/mem/depositList";
	}

	@RequiresPermissions("mem:deposit:view")
	@RequestMapping(value = "form")
	public String form(Deposit deposit, Model model) {
		model.addAttribute("deposit", deposit);
		return "modules/mem/depositForm";
	}
	
	@RequiresPermissions("mem:deposit:view")
	@RequestMapping(value = "updateFormView")
	public String updateFormView(Deposit deposit, Model model) {
		model.addAttribute("deposit", deposit);
		return "modules/mem/depositupdateForm";
	}

	@RequiresPermissions("mem:deposit:save")
	@RequestMapping(value = "save")
	public String save(Deposit deposit, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, deposit)){
			return form(deposit, model);
		}
		depositService.save(deposit);
		addMessage(redirectAttributes, "保存预存款成功");
		return "redirect:"+Global.getAdminPath()+"/mem/deposit/?repage";
	}
	
	@RequiresPermissions("mem:deposit:edit")
	@RequestMapping(value = "update")
	public String update(Deposit deposit, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, deposit)){
			return form(deposit, model);
		}
		depositService.update(deposit);
		addMessage(redirectAttributes, "修改预存款成功");
		return "redirect:"+Global.getAdminPath()+"/mem/deposit/?repage";
	}
	
	@RequiresPermissions("mem:deposit:edit")
	@RequestMapping(value = "delete")
	public String delete(Deposit deposit, RedirectAttributes redirectAttributes) {
		depositService.delete(deposit);
		addMessage(redirectAttributes, "删除预存款成功");
		return "redirect:"+Global.getAdminPath()+"/mem/deposit/?repage";
	}

}