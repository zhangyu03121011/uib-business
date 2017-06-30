/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.navigation.web;

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

import com.google.common.collect.Lists;
import com.uib.ecmanager.common.config.Global;
import com.uib.ecmanager.common.persistence.Page;
import com.uib.ecmanager.common.utils.StringUtils;
import com.uib.ecmanager.common.web.BaseController;
import com.uib.ecmanager.modules.cms.entity.Category;
import com.uib.ecmanager.modules.cms.service.CategoryService;
import com.uib.ecmanager.modules.navigation.entity.Navigation;
import com.uib.ecmanager.modules.navigation.entity.Navigation.Position;
import com.uib.ecmanager.modules.navigation.entity.Navigation.Tags;
import com.uib.ecmanager.modules.navigation.service.NavigationService;
import com.uib.ecmanager.modules.product.entity.ProductCategory;
import com.uib.ecmanager.modules.product.service.ProductCategoryService;

/**
 * 导航管理Controller
 * @author gaven
 * @version 2015-06-08
 */
@Controller
@RequestMapping(value = "${adminPath}/navigation")
public class NavigationController extends BaseController {

	@Autowired
	private NavigationService navigationService;
	@Autowired
	private ProductCategoryService productCategoryService;
	@Autowired
	private CategoryService categoryService;
	
	@ModelAttribute
	public Navigation get(@RequestParam(required=false) String id) {
		Navigation entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = navigationService.get(id);
		}
		if (entity == null){
			entity = new Navigation();
		}
		return entity;
	}
	
	@RequiresPermissions("navigation:navigation:view")
	@RequestMapping(value = {"list", ""})
	public String list(Navigation navigation, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Navigation> page = navigationService.findPage(new Page<Navigation>(request, response), navigation); 
		model.addAttribute("page", page);
		return "modules/navigation/navigationList";
	}

	@RequiresPermissions("navigation:navigation:view")
	@RequestMapping(value = "form")
	public String form(Navigation navigation, Model model) {
		model.addAttribute("navigation", navigation);
		model.addAttribute("positions", Position.values());
		model.addAttribute("tags", Tags.values());
		model.addAttribute("productCateGoryList", productCategoryService.findList(new ProductCategory()));
		List<Category> sourcelist = categoryService.findByUser(true, null);
		List<Category> list = Lists.newArrayList();
		Category.sortList(list, sourcelist, "1");
		model.addAttribute("cateGoryList", list);
		return "modules/navigation/navigationForm";
	}
	
	@RequiresPermissions("navigation:navigation:view")
	@RequestMapping(value = "updateFormView")
	public String updateFormView(Navigation navigation, Model model) {
		model.addAttribute("navigation", navigation);
		model.addAttribute("positions", Position.values());
		model.addAttribute("tags", Tags.values());
		model.addAttribute("productCateGoryList", productCategoryService.findList(new ProductCategory()));
		List<Category> sourcelist = categoryService.findByUser(true, null);
		List<Category> list = Lists.newArrayList();
		Category.sortList(list, sourcelist, "1");
		model.addAttribute("cateGoryList", list);
		return "modules/navigation/navigationupdateForm";
	}

	@RequiresPermissions("navigation:navigation:save")
	@RequestMapping(value = "save")
	public String save(Navigation navigation, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, navigation)){
			return form(navigation, model);
		}
		navigationService.save(navigation);
		addMessage(redirectAttributes, "保存导航管理成功");
		return "redirect:"+Global.getAdminPath()+"/navigation/?repage";
	}
	
	@RequiresPermissions("navigation:navigation:edit")
	@RequestMapping(value = "update")
	public String update(Navigation navigation, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, navigation)){
			return form(navigation, model);
		}
		navigationService.update(navigation);
		addMessage(redirectAttributes, "修改导航管理成功");
		return "redirect:"+Global.getAdminPath()+"/navigation/?repage";
	}
	
	@RequiresPermissions("navigation:navigation:edit")
	@RequestMapping(value = "delete")
	public String delete(Navigation navigation, RedirectAttributes redirectAttributes) {
		navigationService.delete(navigation);
		addMessage(redirectAttributes, "删除导航管理成功");
		return "redirect:"+Global.getAdminPath()+"/navigation/?repage";
	}

}