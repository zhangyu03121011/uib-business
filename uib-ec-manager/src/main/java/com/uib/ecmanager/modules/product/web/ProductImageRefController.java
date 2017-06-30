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
import com.uib.ecmanager.modules.product.entity.ProductImageRef;
import com.uib.ecmanager.modules.product.service.ProductImageRefService;

/**
 * 商品图片Controller
 * @author gaven
 * @version 2015-05-27
 */
@Controller
@RequestMapping(value = "${adminPath}/product/productImageRef")
public class ProductImageRefController extends BaseController {

	@Autowired
	private ProductImageRefService productImageRefService;
	
	@ModelAttribute
	public ProductImageRef get(@RequestParam(required=false) String id) {
		ProductImageRef entity = null;
		try {
			if (StringUtils.isNotBlank(id)) {
				entity = productImageRefService.get(id);
			}
			if (entity == null) {
				entity = new ProductImageRef();
			}
		} catch (Exception e) {
			logger.error("根据id获取图片关联失败", e);
		}
		return entity;
	}
	
	@RequiresPermissions("product:productImageRef:view")
	@RequestMapping(value = {"list", ""})
	public String list(ProductImageRef productImageRef, HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			Page<ProductImageRef> page = productImageRefService.findPage(
					new Page<ProductImageRef>(request, response),
					productImageRef);
			model.addAttribute("page", page);
		} catch (Exception e) {
			logger.error("获取图片关联列表失败", e);
		}
		return "modules/product/productImageRefList";
	}

	@RequiresPermissions("product:productImageRef:view")
	@RequestMapping(value = "form")
	public String form(ProductImageRef productImageRef, Model model) {
		try {
			model.addAttribute("productImageRef", productImageRef);
		} catch (Exception e) {
			logger.error("图片关联form表单信息获取失败", e);
		}
		return "modules/product/productImageRefForm";
	}

	@RequiresPermissions("product:productImageRef:edit")
	@RequestMapping(value = "save")
	public String save(ProductImageRef productImageRef, Model model, RedirectAttributes redirectAttributes) {
		try {
			if (!beanValidator(model, productImageRef)) {
				return form(productImageRef, model);
			}
			productImageRefService.save(productImageRef);
			addMessage(redirectAttributes, "保存商品图片成功");
		} catch (Exception e) {
			logger.error("保存商品-图片关联失败", e);
		}
		return "redirect:"+Global.getAdminPath()+"/product/productImageRef/?repage";
	}
	
	@RequiresPermissions("product:productImageRef:edit")
	@RequestMapping(value = "delete")
	public String delete(ProductImageRef productImageRef, RedirectAttributes redirectAttributes) {
		try {
			productImageRefService.delete(productImageRef);
			addMessage(redirectAttributes, "删除商品图片成功");
		} catch (Exception e) {
			logger.error("删除图片失败", e);
		}
		return "redirect:"+Global.getAdminPath()+"/product/productImageRef/?repage";
	}

}