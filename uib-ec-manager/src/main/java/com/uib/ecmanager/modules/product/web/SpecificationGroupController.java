/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.product.web;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.uib.ecmanager.common.config.Global;
import com.uib.ecmanager.common.persistence.Page;
import com.uib.ecmanager.common.utils.StringUtils;
import com.uib.ecmanager.common.web.BaseController;
import com.uib.ecmanager.modules.product.entity.ProductSpecification;
import com.uib.ecmanager.modules.product.entity.SpecificationGroup;
import com.uib.ecmanager.modules.product.entity.SpecificationGroup.SpecificationContentType;
import com.uib.ecmanager.modules.product.service.ProductCategoryService;
import com.uib.ecmanager.modules.product.service.SpecificationGroupService;

/**
 * 商品规格Controller
 * 
 * @author gaven
 * @version 2015-06-13
 */
@Controller
@RequestMapping(value = "${adminPath}/product/specificationGroup")
public class SpecificationGroupController extends BaseController {

	@Autowired
	private SpecificationGroupService specificationGroupService;
	@Autowired
	private ProductCategoryService productCategoryService;

	@ModelAttribute
	public SpecificationGroup get(@RequestParam(required = false) String id) {
		SpecificationGroup entity = null;
		try {
			if (StringUtils.isNotBlank(id)) {
				entity = specificationGroupService.get(id);
			}
			if (entity == null) {
				entity = new SpecificationGroup();
			}
		} catch (Exception e) {
			logger.error("根据id获取规格组失败", e);
		}
		return entity;
	}

	@RequiresPermissions("product:specificationGroup:view")
	@RequestMapping(value = { "list", "" })
	public String list(SpecificationGroup specificationGroup, HttpServletRequest request, HttpServletResponse response,
			Model model) {
		try {
			Page<SpecificationGroup> page = specificationGroupService.findPage(new Page<SpecificationGroup>(request, response), specificationGroup);
			model.addAttribute("page", page);
			model.addAttribute("contentTypes", SpecificationContentType.values());
		} catch (Exception e) {
			logger.error("获取规格组列表失败", e);
		}
		return "modules/product/specificationGroupList";
	}

	@RequiresPermissions("product:specificationGroup:view")
	@RequestMapping(value = "form")
	public String form(SpecificationGroup specificationGroup, Model model) {
		try {
			model.addAttribute("specificationGroup", specificationGroup);
			model.addAttribute("contentTypes", SpecificationContentType.values());
		} catch (Exception e) {
			logger.error("获取规格组form表单信息失败", e);
		}
		return "modules/product/specificationGroupForm";
	}

	@RequiresPermissions("product:specificationGroup:view")
	@RequestMapping(value = "updateFormView")
	public String updateFormView(SpecificationGroup specificationGroup, Model model) {
		try {
			model.addAttribute("specificationGroup", specificationGroup);
			model.addAttribute("contentTypes", SpecificationContentType.values());
			model.addAttribute("productCategory",productCategoryService.get(specificationGroup.getProductCategoryId()));
		} catch (Exception e) {
			logger.error("获取规格组更新信息失败", e);
		}
		return "modules/product/specificationGroupupdateForm";
	}

	@RequiresPermissions("product:specificationGroup:save")
	@RequestMapping(value = "save")
	public String save(SpecificationGroup specificationGroup, Model model, RedirectAttributes redirectAttributes) {
		try {
			List<ProductSpecification> productSpecificationList = specificationGroup.getProductSpecificationList();
			if (!beanValidator(model, specificationGroup)) {
				return form(specificationGroup, model);
			}
			for(int i=0;i<productSpecificationList.size();i++){
				if(!productSpecificationList.get(i).getDelFlag().equals("1")){
					for(int j=i+1;j<productSpecificationList.size();j++){
						if(!productSpecificationList.get(j).getDelFlag().equals("1")){
							if(productSpecificationList.get(j).getName().equals(productSpecificationList.get(i).getName())){
								addMessage(redirectAttributes, "一个规格组中不允许添加相同的规格");
								redirectAttributes.addFlashAttribute("specificationGroup", specificationGroup);
								return "redirect:" + Global.getAdminPath() + "/product/specificationGroup/updateFormView";
							}
						}
						
					}
				}
				
			}
			specificationGroupService.save(specificationGroup);
			addMessage(redirectAttributes, "保存商品规格成功");
		} catch (Exception e) {
			logger.error("保存规格组信息失败", e);
		}
		return "redirect:" + Global.getAdminPath() + "/product/specificationGroup/?repage";
	}

	@RequiresPermissions("product:specificationGroup:edit")
	@RequestMapping(value = "update")
	public String update(SpecificationGroup specificationGroup, Model model, RedirectAttributes redirectAttributes) {
		try {
			List<ProductSpecification> productSpecificationList = specificationGroup.getProductSpecificationList();
			if (!beanValidator(model, specificationGroup)) {
				return form(specificationGroup, model);
			}
			for(int i=0;i<productSpecificationList.size();i++){
				if(!productSpecificationList.get(i).getDelFlag().equals("1")){
					for(int j=i+1;j<productSpecificationList.size();j++){
						if(!productSpecificationList.get(j).getDelFlag().equals("1")){
							if(productSpecificationList.get(j).getName().equals(productSpecificationList.get(i).getName())){
								addMessage(redirectAttributes, "一个规格组中不允许添加相同的规格");
								redirectAttributes.addFlashAttribute("specificationGroup", specificationGroup);
								return "redirect:" + Global.getAdminPath() + "/product/specificationGroup/updateFormView";
							}
						}
						
					}
				}
				
			}
			specificationGroupService.update(specificationGroup);
			addMessage(redirectAttributes, "修改商品规格成功");
		} catch (Exception e) {
			logger.error("修改商品规格组失败", e);
		}
		return "redirect:" + Global.getAdminPath() + "/product/specificationGroup/?repage";
	}

	@RequiresPermissions("product:specificationGroup:edit")
	@RequestMapping(value = "delete")
	public String delete(SpecificationGroup specificationGroup, RedirectAttributes redirectAttributes) {
		try {
			specificationGroupService.delete(specificationGroup);
			addMessage(redirectAttributes, "删除商品规格成功");
		} catch (Exception e) {
			logger.error("删除商品规格组失败", e);
		}
		return "redirect:" + Global.getAdminPath() + "/product/specificationGroup/?repage";
	}

}