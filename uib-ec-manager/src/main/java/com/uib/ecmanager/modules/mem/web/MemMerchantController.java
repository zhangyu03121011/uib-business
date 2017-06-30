/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.mem.web;

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
import com.uib.ecmanager.modules.mem.entity.MemMerchant;
import com.uib.ecmanager.modules.mem.service.MemMerchantService;

/**
 * 会员卖家信息Controller
 * @author kevin
 * @version 2015-05-28
 */
@Controller
@RequestMapping(value = "${adminPath}/mem/memMerchant")
public class MemMerchantController extends BaseController {

	@Autowired
	private MemMerchantService memMerchantService;
	
	@ModelAttribute
	public MemMerchant get(@RequestParam(required=false) String id) {
		MemMerchant entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = memMerchantService.get(id);
		}
		if (entity == null){
			entity = new MemMerchant();
		}
		return entity;
	}
	
	@RequiresPermissions("mem:memMerchant:view")
	@RequestMapping(value = {"list", ""})
	public String list(MemMerchant memMerchant, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<MemMerchant> page = memMerchantService.findPage(new Page<MemMerchant>(request, response), memMerchant); 
		model.addAttribute("page", page);
		return "modules/mem/memMerchantList";
	}

	@RequiresPermissions("mem:memMerchant:view")
	@RequestMapping(value = "form")
	public String form(MemMerchant memMerchant, Model model) {
		model.addAttribute("memMerchant", memMerchant);
		return "modules/mem/memMerchantForm";
	}

	@RequiresPermissions("mem:memMerchant:save")
	@RequestMapping(value = "save")
	public String save(MemMerchant memMerchant, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, memMerchant)){
			return form(memMerchant, model);
		}
		memMerchantService.save(memMerchant);
		addMessage(redirectAttributes, "保存会员卖家信息成功");
		return "redirect:"+Global.getAdminPath()+"/mem/memMerchant/?repage";
	}
	
	@RequiresPermissions("mem:memMerchant:edit")
	@RequestMapping(value = "update")
	public String te(MemMerchant memMerchant, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, memMerchant)){
			return form(memMerchant, model);
		}
		memMerchantService.update(memMerchant);
		addMessage(redirectAttributes, "保存会员卖家信息成功");
		return "redirect:"+Global.getAdminPath()+"/mem/memMerchant/?repage";
	}
	
	@RequiresPermissions("mem:memMerchant:edit")
	@RequestMapping(value = "delete")
	public String delete(MemMerchant memMerchant, RedirectAttributes redirectAttributes) {
		memMerchantService.delete(memMerchant);
		addMessage(redirectAttributes, "删除会员卖家信息成功");
		return "redirect:"+Global.getAdminPath()+"/mem/memMerchant/?repage";
	}

}