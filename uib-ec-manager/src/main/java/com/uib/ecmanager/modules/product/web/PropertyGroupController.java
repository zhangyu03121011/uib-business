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
import com.uib.ecmanager.common.utils.StringUtils;
import com.uib.ecmanager.common.web.BaseController;
import com.uib.ecmanager.modules.product.entity.PropertyGroup;
import com.uib.ecmanager.modules.product.service.ProductCategoryService;
import com.uib.ecmanager.modules.product.service.PropertyGroupService;

/**
 * 商品属性Controller
 * 
 * @author gaven
 * @version 2015-06-04
 */
@Controller
@RequestMapping(value = "${adminPath}/product/propertyGroup")
public class PropertyGroupController extends BaseController {

	@Autowired
	private PropertyGroupService propertyGroupService;
	@Autowired
	private ProductCategoryService productCategoryService;

	@ModelAttribute
	public PropertyGroup get(@RequestParam(required = false) String id) {
		PropertyGroup entity = null;
		try {
			if (StringUtils.isNotBlank(id)) {
				entity = propertyGroupService.get(id);
			}
			if (entity == null) {
				entity = new PropertyGroup();
			}
		} catch (Exception e) {
			logger.error("根据属性组id获取属性组失败", e);
		}
		return entity;
	}

	@RequiresPermissions("product:propertyGroup:view")
	@RequestMapping(value = { "list", "" })
	public String list(PropertyGroup propertyGroup, HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			Page<PropertyGroup> page = propertyGroupService.findPage(new Page<PropertyGroup>(request, response), propertyGroup);
			model.addAttribute("page", page);
		} catch (Exception e) {
			logger.error("获取属性组列表失败", e);
		}
		return "modules/product/propertyGroupList";
	}

	@RequiresPermissions("product:propertyGroup:view")
	@RequestMapping(value = "form")
	public String form(PropertyGroup propertyGroup, Model model) {
		try {
			model.addAttribute("propertyGroup", propertyGroup);
			model.addAttribute("productCategory", productCategoryService.get(propertyGroup.getProductCategoryId()));
		} catch (Exception e) {
			logger.error("获取属性组form表单信息失败", e);
		}
		return "modules/product/propertyGroupForm";
	}

	@RequiresPermissions("product:propertyGroup:view")
	@RequestMapping(value = "updateFormView")
	public String updateFormView(PropertyGroup propertyGroup, Model model) {
		try {
			model.addAttribute("propertyGroup", propertyGroup);
			model.addAttribute("productCategory", productCategoryService.get(propertyGroup.getProductCategoryId()));
		} catch (Exception e) {
			logger.error("获取属性组更新信息失败", e);
		}
		return "modules/product/propertyGroupupdateForm";
	}

	@RequiresPermissions("product:propertyGroup:save")
	@RequestMapping(value = "save")
	public String save(PropertyGroup propertyGroup, Model model, RedirectAttributes redirectAttributes) {
		try {
			if (!beanValidator(model, propertyGroup)) {
				return form(propertyGroup, model);
			}
			propertyGroupService.save(propertyGroup);
			addMessage(redirectAttributes, "保存商品属性成功");
		} catch (Exception e) {
			logger.error("保存属性组失败", e);
		}
		return "redirect:" + Global.getAdminPath() + "/product/propertyGroup/?repage";
	}

	@RequiresPermissions("product:propertyGroup:edit")
	@RequestMapping(value = "update")
	public String update(PropertyGroup propertyGroup, Model model, RedirectAttributes redirectAttributes) {
		try {
			if (!beanValidator(model, propertyGroup)) {
				return form(propertyGroup, model);
			}
			propertyGroupService.update(propertyGroup);
			addMessage(redirectAttributes, "修改商品属性成功");
		} catch (Exception e) {
			logger.error("更新属性组失败", e);
		}
		return "redirect:" + Global.getAdminPath() + "/product/propertyGroup/?repage";
	}

	@RequiresPermissions("product:propertyGroup:edit")
	@RequestMapping(value = "delete")
	public String delete(PropertyGroup propertyGroup, RedirectAttributes redirectAttributes) {
		try {
			propertyGroupService.delete(propertyGroup);
			addMessage(redirectAttributes, "删除商品属性成功");
		} catch (Exception e) {
			logger.error("删除属性组失败", e);
		}
		return "redirect:" + Global.getAdminPath() + "/product/propertyGroup/?repage";
	}

}