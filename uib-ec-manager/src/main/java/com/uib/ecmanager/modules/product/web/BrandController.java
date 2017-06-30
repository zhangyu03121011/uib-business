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
import com.uib.ecmanager.modules.product.entity.Brand;
import com.uib.ecmanager.modules.product.service.BrandService;
import com.uib.ecmanager.modules.product.service.ProductCategoryService;

/**
 * 商品品牌Controller
 * @author gaven
 * @version 2015-06-13
 */
@Controller
@RequestMapping(value = "${adminPath}/product/brand")
public class BrandController extends BaseController {

	@Autowired
	private BrandService brandService;
	@Autowired
	private ProductCategoryService productCategoryService;
	
	@ModelAttribute
	public Brand get(@RequestParam(required=false) String id) {
		Brand entity = null;
		try {
			if (StringUtils.isNotBlank(id)) {
				entity = brandService.get(id);
			}
			if (entity == null) {
				entity = new Brand();
			}
		} catch (Exception e) {
			logger.error("根据id获取品牌信息失败", e);
		}
		return entity;
	}
	
	@RequiresPermissions("product:brand:view")
	@RequestMapping(value = {"list", ""})
	public String list(Brand brand, HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			Page<Brand> page = brandService.findPage(new Page<Brand>(request,
					response), brand);
			model.addAttribute("page", page);
		} catch (Exception e) {
			logger.error("获取品牌列表失败", e);
		}
		return "modules/product/brandList";
	}

	@RequiresPermissions("product:brand:view")
	@RequestMapping(value = "form")
	public String form(Brand brand, Model model) {
		try {
			model.addAttribute("brand", brand);
		} catch (Exception e) {
			logger.error("品牌form表信息获取失败", e);
		}
		return "modules/product/brandForm";
	}
	
	@RequiresPermissions("product:brand:view")
	@RequestMapping(value = "updateFormView")
	public String updateFormView(Brand brand, Model model) {
		try {
			model.addAttribute("brand", brand);
			model.addAttribute("productCategory",
					productCategoryService.get(brand.getProductCategoryId()));
		} catch (Exception e) {
			logger.error("获取品牌更新信息失败", e);
		}
		return "modules/product/brandupdateForm";
	}

	@RequiresPermissions("product:brand:save")
	@RequestMapping(value = "save")
	public String save(Brand brand, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, brand)){
			return form(brand, model);
		}
		try {
			brandService.save(brand);
			addMessage(redirectAttributes, "保存商品品牌成功");
		} catch (Exception e) {
			logger.error("保存品牌信息失败", e);
		}
		return "redirect:"+Global.getAdminPath()+"/product/brand/?repage";
	}
	
	@RequiresPermissions("product:brand:edit")
	@RequestMapping(value = "update")
	public String update(Brand brand, Model model, RedirectAttributes redirectAttributes) {
		try {
			if (!beanValidator(model, brand)){
				return form(brand, model);
			}
			brandService.update(brand);
			addMessage(redirectAttributes, "修改商品品牌成功");
		} catch (Exception e) {
			logger.error("更新品牌信息失败!", e);
		}
		return "redirect:"+Global.getAdminPath()+"/product/brand/?repage";
	}
	
	@RequiresPermissions("product:brand:edit")
	@RequestMapping(value = "delete")
	public String delete(Brand brand, RedirectAttributes redirectAttributes) {
		try {
			brandService.delete(brand);
			addMessage(redirectAttributes, "删除商品品牌成功");
		} catch (Exception e) {
			logger.error("删除品牌信息失败！", e);
		}
		return "redirect:"+Global.getAdminPath()+"/product/brand/?repage";
	}

}