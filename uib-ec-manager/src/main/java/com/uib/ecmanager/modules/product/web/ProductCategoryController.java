/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.product.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.uib.ecmanager.ServiceUtils.Utils;
import com.uib.ecmanager.common.config.Global;
import com.uib.ecmanager.common.utils.ReturnMsg;
import com.uib.ecmanager.common.utils.StringUtils;
import com.uib.ecmanager.common.web.BaseController;
import com.uib.ecmanager.modules.product.dao.ProductDao;
import com.uib.ecmanager.modules.product.entity.ParamBean;
import com.uib.ecmanager.modules.product.entity.ProductCategory;
import com.uib.ecmanager.modules.product.entity.PropertyGroup;
import com.uib.ecmanager.modules.product.service.ProductCategoryService;
import com.uib.ecmanager.modules.product.service.PropertyGroupService;

/**
 * 商品分类Controller
 * 
 * @author kevin
 * @version 2015-06-12
 */
@Controller
@RequestMapping(value = "${adminPath}/product/productCategory")
public class ProductCategoryController extends BaseController {
	private static final String PROP_PARAM_PREFIX = "propertyId_group_";

	@Autowired
	private ProductCategoryService productCategoryService;
	@Autowired
	private ProductDao productDao;
	@Autowired
	private PropertyGroupService propertyGroupService;

	@ModelAttribute
	public ProductCategory get(@RequestParam(required = false) String id) {
		ProductCategory entity = null;
		try {
			if (StringUtils.isNotBlank(id)) {
				entity = productCategoryService.get(id);
			}
			if (entity == null) {
				entity = new ProductCategory();
			}
		} catch (Exception e) {
			logger.error("根据分类id获取分类信息失败", e);
		}
		return entity;
	}

	@RequiresPermissions("product:productCategory:view")
	@RequestMapping(value = { "list", "" })
	public String list(ProductCategory productCategory, HttpServletRequest request, HttpServletResponse response,
			Model model) {
		try {
			List<ProductCategory> list = productCategoryService.findList(productCategory);
			model.addAttribute("list", list);
		} catch (Exception e) {
			logger.error("获取分类列表失败", e);
		}
		return "modules/product/productCategoryList";
	}

	@RequiresPermissions("product:productCategory:view")
	@RequestMapping(value = "form")
	public String form(ProductCategory productCategory, Model model) {
		String returnPath = "modules/product/productCategoryUpdateForm";
		try {
			if (productCategory.getParent() != null && StringUtils.isNotBlank(productCategory.getParent().getId())) {
				String parentId = productCategory.getParent().getId();
				productCategory.setParent(productCategoryService.get(parentId));
				List<PropertyGroup> propertyGroups = propertyGroupService
						.findPropertyGroupsByParentCategoryId(productCategory.getId(), parentId, true);
				model.addAttribute("propertyGroups", propertyGroups);
				// 获取排序号，最末节点排序号+30
				if (StringUtils.isBlank(productCategory.getId())) {
					ProductCategory productCategoryChild = new ProductCategory();
					productCategoryChild.setParent(new ProductCategory(productCategory.getParent().getId()));
					List<ProductCategory> list = productCategoryService.findList(productCategory);
					if (list.size() > 0) {
						productCategory.setSort(list.get(list.size() - 1).getSort());
						if (productCategory.getSort() != null) {
							productCategory.setSort(productCategory.getSort() + 30);
						}
					}
				}
			}
			if (productCategory.getSort() == null) {
				productCategory.setSort(30);
			}
			if (StringUtils.isBlank(productCategory.getId())) {
				returnPath = "modules/product/productCategoryForm";
			}
			model.addAttribute("productCategory", productCategory);
		} catch (Exception e) {
			logger.error("获取商品分类form信息失败", e);
		}
		return returnPath;
	}

	@RequiresPermissions("product:productCategory:edit")
	@RequestMapping(value = "save")
	public String save(ProductCategory productCategory, String[] propertyGroupIds, Model model,
			RedirectAttributes redirectAttributes, HttpServletRequest request) {
		try {
			if (!beanValidator(model, productCategory)) {
				return form(productCategory, model);
			}
			List<ParamBean<String[]>> paramBeans = new ArrayList<ParamBean<String[]>>();
			String[] propertyIds = null;
			ParamBean<String[]> paramBean = null;
			if (Utils.isNotBlank(propertyGroupIds)) {
				for (String propertyGroupId : propertyGroupIds) {
					propertyIds = request.getParameterValues(PROP_PARAM_PREFIX + propertyGroupId);
					paramBean = new ParamBean<String[]>(propertyGroupId, propertyIds);
					paramBeans.add(paramBean);
				}
			}
			productCategoryService.save(productCategory, paramBeans);
			addMessage(redirectAttributes, "保存商品分类成功");
		} catch (Exception e) {
			logger.error("保存商品分类信息失败", e);
		}
		return "redirect:" + Global.getAdminPath() + "/product/productCategory/?repage";
	}

	@RequiresPermissions("product:product:edit")
	@RequestMapping(value = "update")
	public String update(ProductCategory productCategory, String[] propertyGroupIds, Model model,
			RedirectAttributes redirectAttributes, HttpServletRequest request) {
		try {
			if (!beanValidator(model, productCategory)) {
				return form(productCategory, model);
			}
			if (StringUtils.isBlank(productCategory.getParent().getId())) {
				productCategory.getParent().setId("0");
			}
			List<ParamBean<String[]>> paramBeans = new ArrayList<ParamBean<String[]>>();
			String[] propertyIds = null;
			ParamBean<String[]> paramBean = null;
			if (Utils.isNotBlank(propertyGroupIds)) {
				for (String propertyGroupId : propertyGroupIds) {
					propertyIds = request.getParameterValues(PROP_PARAM_PREFIX + propertyGroupId);
					paramBean = new ParamBean<String[]>(propertyGroupId, propertyIds);
					paramBeans.add(paramBean);
				}
			}
			productCategoryService.update(productCategory, paramBeans);
			addMessage(redirectAttributes, "修改商品成功");
		} catch (Exception e) {
			logger.error("更新商品分类信息失败", e);
		}
		return "redirect:" + Global.getAdminPath() + "/product/productCategory/?repage";
	}

	@RequiresPermissions("product:productCategory:edit")
	@RequestMapping(value = "delete")
	public String delete(ProductCategory productCategory, RedirectAttributes redirectAttributes) {
		try {
			productCategoryService.delete(productCategory);
			addMessage(redirectAttributes, "删除商品分类成功");
		} catch (Exception e) {
			logger.error("删除商品分类失败", e);
		}
		return "redirect:" + Global.getAdminPath() + "/product/productCategory/?repage";
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required = false) String extId,
			HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		try {
			List<ProductCategory> list = productCategoryService.findList(new ProductCategory());
			for (int i = 0; i < list.size(); i++) {
				ProductCategory e = list.get(i);
				if (StringUtils.isBlank(extId) || (extId != null && !extId.equals(e.getId())
						&& e.getParentIds().indexOf("," + extId + ",") == -1)) {
					Map<String, Object> map = Maps.newHashMap();
					map.put("id", e.getId());
					map.put("pId", e.getParentId());
					map.put("name", e.getName());
					mapList.add(map);
				}
			}
		} catch (Exception e) {
			logger.error("分类树结构获取失败", e);
		}
		return mapList;
	}

	@RequestMapping(value = "checkProductCategory")
	@ResponseBody
	@RequiresPermissions("user")
	public ReturnMsg<Object> checkProductCategory(String productCategoryId) {
		ReturnMsg<Object> msg = new ReturnMsg<Object>();
		try {
			msg.setCode("0");
			Boolean isLastStage = productCategoryService.isLastStage(productCategoryId);
			if (isLastStage) {
				int count = productDao.countProductInProductCategory(productCategoryId);
				if (count > 0) {
					msg.setCode("1");
				} else {
					msg.setCode("2");
				}
			}
		} catch (Exception e) {
			logger.error("查询分类商品失败！", e);
			msg.setStatus(false);
		}
		return msg;
	}

	@RequestMapping(value = "queryPropertyGroups")
	@ResponseBody
	@RequiresPermissions("user")
	public List<PropertyGroup> queryPropertyGroups(String categoryId, Boolean hasDetail) {
		List<PropertyGroup> propertyGroups = null;
		try {
			propertyGroups = propertyGroupService.findPropertyGroupsByCategoryId(categoryId, hasDetail);
		} catch (Exception e) {
			logger.error("查询分类商品属性组失败！", e);
		}
		return propertyGroups;
	}

}