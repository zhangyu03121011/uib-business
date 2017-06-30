/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.product.web;

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
import com.uib.ecmanager.common.web.BaseController;
import com.uib.ecmanager.common.utils.StringUtils;
import com.uib.ecmanager.modules.product.entity.SpecialProductRef;
import com.uib.ecmanager.modules.product.service.SpecialProductRefService;

/**
 * 商品专题关联列表Controller
 * @author limy
 * @version 2016-07-14
 */
@Controller
@RequestMapping(value = "${adminPath}/product/specialProductRef")
public class SpecialProductRefController extends BaseController {

	@Autowired
	private SpecialProductRefService specialProductRefService;
	
	@ModelAttribute
	public SpecialProductRef get(@RequestParam(required=false) String id) {
		SpecialProductRef entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = specialProductRefService.get(id);
		}
		if (entity == null){
			entity = new SpecialProductRef();
		}
		return entity;
	}
	
	@RequiresPermissions("product:specialProductRef:view")
	@RequestMapping(value = {"list", ""})
	public String list(SpecialProductRef specialProductRef, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SpecialProductRef> page = specialProductRefService.findPage(new Page<SpecialProductRef>(request, response), specialProductRef); 
		model.addAttribute("page", page);
		return "modules/product/specialProductRefList";
	}

	@RequiresPermissions("product:specialProductRef:view")
	@RequestMapping(value = "form")
	public String form(SpecialProductRef specialProductRef, Model model) {
		model.addAttribute("specialProductRef", specialProductRef);
		return "modules/product/specialProductRefForm";
	}
	
	@RequiresPermissions("product:specialProductRef:view")
	@RequestMapping(value = "updateFormView")
	public String updateFormView(SpecialProductRef specialProductRef, Model model) {
		model.addAttribute("specialProductRef", specialProductRef);
		return "modules/product/specialProductRefupdateForm";
	}

	@RequiresPermissions("product:specialProductRef:save")
	@RequestMapping(value = "save")
	public String save(SpecialProductRef specialProductRef, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, specialProductRef)){
			return form(specialProductRef, model);
		}
		specialProductRefService.save(specialProductRef);
		addMessage(redirectAttributes, "保存商品专题关联列表成功");
		return "redirect:"+Global.getAdminPath()+"/product/specialProductRef/?repage";
	}
	
	@RequiresPermissions("product:specialProductRef:edit")
	@RequestMapping(value = "update")
	public String update(SpecialProductRef specialProductRef, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, specialProductRef)){
			return form(specialProductRef, model);
		}
		specialProductRefService.update(specialProductRef);
		addMessage(redirectAttributes, "修改商品专题关联列表成功");
		return "redirect:"+Global.getAdminPath()+"/product/specialProductRef/?repage";
	}
	
	@RequiresPermissions("product:specialProductRef:edit")
	@RequestMapping(value = "delete")
	public String delete(SpecialProductRef specialProductRef, RedirectAttributes redirectAttributes) {
		specialProductRefService.delete(specialProductRef);
		addMessage(redirectAttributes, "删除商品专题关联列表成功");
		return "redirect:"+Global.getAdminPath()+"/product/specialProductRef/?repage";
	}

}