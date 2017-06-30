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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.uib.ecmanager.common.config.Global;
import com.uib.ecmanager.common.persistence.Page;
import com.uib.ecmanager.common.utils.StringUtils;
import com.uib.ecmanager.common.web.BaseController;
import com.uib.ecmanager.modules.product.entity.ParameterGroup;
import com.uib.ecmanager.modules.product.entity.ProductParameter;
import com.uib.ecmanager.modules.product.service.ParameterGroupService;
import com.uib.ecmanager.modules.product.service.ProductCategoryService;

/**
 * 商品参数组Controller
 * @author gaven
 * @version 2015-05-28
 */
@Controller
@RequestMapping(value = "${adminPath}/product/parameterGroup")
public class ParameterGroupController extends BaseController {

	@Autowired
	private ParameterGroupService parameterGroupService;
	@Autowired
	private ProductCategoryService productCategoryService;
	
	@ModelAttribute
	public ParameterGroup get(@RequestParam(required=false) String id) {
		ParameterGroup entity = null;
		try {
			if (StringUtils.isNotBlank(id)) {
				entity = parameterGroupService.get(id);
			}
			if (entity == null) {
				entity = new ParameterGroup();
			}
		} catch (Exception e) {
			logger.error("根据id获取参数组失败", e);
		}
		return entity;
	}
	
	@RequiresPermissions("product:parameterGroup:view")
	@RequestMapping(value = {"list", ""})
	public String list(ParameterGroup parameterGroup, HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			Page<ParameterGroup> page = parameterGroupService
					.findPage(new Page<ParameterGroup>(request, response),
							parameterGroup);
			model.addAttribute("page", page);
		} catch (Exception e) {
			logger.error("获取参数组列表失败", e);
		}
		return "modules/product/parameterGroupList";
	}

	@RequiresPermissions("product:parameterGroup:view")
	@RequestMapping(value = "form")
	public String form(ParameterGroup parameterGroup, Model model) {
		try {
			model.addAttribute("parameterGroup", parameterGroup);
			model.addAttribute("productCategory", productCategoryService
					.get(parameterGroup.getProductCategoryId()));
		} catch (Exception e) {
			logger.error("获取参数组form表单信息失败", e);
		}
		return "modules/product/parameterGroupForm";
	}

	@RequiresPermissions("product:parameterGroup:edit")
	@RequestMapping(value = "save")
	public String save(ParameterGroup parameterGroup, Model model, RedirectAttributes redirectAttributes) {
		try {
			if (!beanValidator(model, parameterGroup)) {
				return form(parameterGroup, model);
			}
			List<ProductParameter> parameters = parameterGroup
					.getProductParameterList();
			for (ProductParameter parameter : parameters) {
				parameter.setParameterGroup(parameterGroup);
				parameter.setMerchantNo(parameterGroup.getMerchantNo());
			}
			//更新
			if (StringUtils.isNotBlank(parameterGroup.getId())) {
				parameterGroupService.update(parameterGroup);
			}
			parameterGroupService.save(parameterGroup);
			addMessage(redirectAttributes, "保存商品参数组数据服务成功");
		} catch (Exception e) {
			logger.error("保存参数组信息失败", e);
		}
		return "redirect:"+Global.getAdminPath()+"/product/parameterGroup/?repage";
	}
	
	@RequiresPermissions("product:parameterGroup:edit")
	@RequestMapping(value = "delete")
	public String delete(ParameterGroup parameterGroup, RedirectAttributes redirectAttributes) {
		try {
			parameterGroupService.delete(parameterGroup);
			addMessage(redirectAttributes, "删除商品参数组数据服务成功");
		} catch (Exception e) {
			logger.error("删除参数组失败", e);
		}
		return "redirect:"+Global.getAdminPath()+"/product/parameterGroup/?repage";
	}

}