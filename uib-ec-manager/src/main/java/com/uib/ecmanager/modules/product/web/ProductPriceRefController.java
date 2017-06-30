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
import com.uib.ecmanager.modules.product.entity.ProductPriceRef;
import com.uib.ecmanager.modules.product.service.ProductPriceRefService;

/**
 * 商品会员价格关联表Controller
 * @author luogc
 * @version 2016-07-16
 */
@Controller
@RequestMapping(value = "${adminPath}/product/productPriceRef")
public class ProductPriceRefController extends BaseController {

	@Autowired
	private ProductPriceRefService productPriceRefService;
	
	@ModelAttribute
	public ProductPriceRef get(@RequestParam(required=false) String id) {
		ProductPriceRef entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = productPriceRefService.get(id);
		}
		if (entity == null){
			entity = new ProductPriceRef();
		}
		return entity;
	}
	
	@RequiresPermissions("product:productPriceRef:view")
	@RequestMapping(value = {"list", ""})
	public String list(ProductPriceRef productPriceRef, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ProductPriceRef> page = productPriceRefService.findPage(new Page<ProductPriceRef>(request, response), productPriceRef); 
		model.addAttribute("page", page);
		return "modules/product/productPriceRefList";
	}

	@RequiresPermissions("product:productPriceRef:view")
	@RequestMapping(value = "form")
	public String form(ProductPriceRef productPriceRef, Model model) {
		model.addAttribute("productPriceRef", productPriceRef);
		return "modules/product/productPriceRefForm";
	}
	
	@RequiresPermissions("product:productPriceRef:view")
	@RequestMapping(value = "updateFormView")
	public String updateFormView(ProductPriceRef productPriceRef, Model model) {
		model.addAttribute("productPriceRef", productPriceRef);
		return "modules/product/productPriceRefupdateForm";
	}

	@RequiresPermissions("product:productPriceRef:save")
	@RequestMapping(value = "save")
	public String save(ProductPriceRef productPriceRef, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, productPriceRef)){
			return form(productPriceRef, model);
		}
		productPriceRefService.save(productPriceRef);
		addMessage(redirectAttributes, "保存商品会员价格关联表成功");
		return "redirect:"+Global.getAdminPath()+"/product/productPriceRef/?repage";
	}
	
	@RequiresPermissions("product:productPriceRef:edit")
	@RequestMapping(value = "update")
	public String update(ProductPriceRef productPriceRef, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, productPriceRef)){
			return form(productPriceRef, model);
		}
		productPriceRefService.update(productPriceRef);
		addMessage(redirectAttributes, "修改商品会员价格关联表成功");
		return "redirect:"+Global.getAdminPath()+"/product/productPriceRef/?repage";
	}
	
	@RequiresPermissions("product:productPriceRef:edit")
	@RequestMapping(value = "delete")
	public String delete(ProductPriceRef productPriceRef, RedirectAttributes redirectAttributes) {
		productPriceRefService.delete(productPriceRef);
		addMessage(redirectAttributes, "删除商品会员价格关联表成功");
		return "redirect:"+Global.getAdminPath()+"/product/productPriceRef/?repage";
	}

}