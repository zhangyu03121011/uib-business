/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.supplier.web;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.uib.common.utils.RandomUtil;
import com.uib.common.utils.UUIDGenerator;
import com.uib.ecmanager.common.config.Global;
import com.uib.ecmanager.common.persistence.Page;
import com.uib.ecmanager.common.utils.IdGen;
import com.uib.ecmanager.common.utils.StringUtils;
import com.uib.ecmanager.common.web.BaseController;
import com.uib.ecmanager.modules.product.entity.ProductCategory;
import com.uib.ecmanager.modules.supplier.entity.Supplier;
import com.uib.ecmanager.modules.supplier.service.SupplierService;

/**
 * 供应商管理Controller
 * @author luogc
 * @version 2016-08-22
 */
@Controller
@RequestMapping(value = "${adminPath}/supplier/supplier")
public class SupplierController extends BaseController {

	@Autowired
	private SupplierService supplierService;
	
	@ModelAttribute
	public Supplier get(@RequestParam(required=false) String id) {
		Supplier entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = supplierService.get(id);
		}
		if (entity == null){
			entity = new Supplier();
		}
		return entity;
	}
	
	@RequiresPermissions("supplier:supplier:view")
	@RequestMapping(value = {"list", ""})
	public String list(Supplier supplier, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Supplier> page = supplierService.findPage(new Page<Supplier>(request, response), supplier); 
		model.addAttribute("page", page);
		return "modules/supplier/supplierList";
	}

	@RequiresPermissions("supplier:supplier:view")
	@RequestMapping(value = "form")
	public String form(Supplier supplier, Model model) {
		model.addAttribute("supplier", supplier);
		return "modules/supplier/supplierForm";
	}
	
	@RequiresPermissions("supplier:supplier:view")
	@RequestMapping(value = "updateFormView")
	public String updateFormView(Supplier supplier, Model model) {
		model.addAttribute("supplier", supplier);
		return "modules/supplier/supplierupdateForm";
	}

	@RequiresPermissions("supplier:supplier:save")
	@RequestMapping(value = "save")
	public String save(Supplier supplier, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, supplier)){
			return form(supplier, model);
		} else if (supplier.getCompanyName() == null) {
			addMessage(model, "保存失败,公司名称不能为空!");
			return form(supplier, model);
		} else if (supplier.getPrincipalName() == null) {
			addMessage(model, "保存失败,负责人姓名不能为空!");
			return form(supplier, model);
		} else if (supplier.getPrincipalPhone() == null) {
			addMessage(model, "保存失败,负责人手机号不能为空!");
			return form(supplier, model);
		}
		supplier.setSupplierNo(RandomUtil.getRandom(8));
		supplier.setCreateDate(new Date());
		supplierService.save(supplier);
		addMessage(redirectAttributes, "保存供应商管理成功");
		return "redirect:"+Global.getAdminPath()+"/supplier/supplier/?repage";
	}
	
	@RequiresPermissions("supplier:supplier:edit")
	@RequestMapping(value = "update")
	public String update(Supplier supplier, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, supplier)){
			return form(supplier, model);
		} else if (supplier.getCompanyName() == null) {
			addMessage(model, "修改失败,公司名称不能为空!");
			return updateFormView(supplier, model);
		} else if (supplier.getPrincipalName() == null) {
			addMessage(model, "修改失败,负责人姓名不能为空!");
			return updateFormView(supplier, model);
		} else if (supplier.getPrincipalPhone() == null) {
			addMessage(model, "修改失败,负责人手机号不能为空!");
			return updateFormView(supplier, model);
		}
		supplierService.update(supplier);
		addMessage(redirectAttributes, "修改供应商管理成功");
		return "redirect:"+Global.getAdminPath()+"/supplier/supplier/?repage";
	}
	
	@RequiresPermissions("supplier:supplier:edit")
	@RequestMapping(value = "delete")
	public String delete(Supplier supplier, RedirectAttributes redirectAttributes) {
		supplierService.delete(supplier);
		addMessage(redirectAttributes, "删除供应商管理成功");
		return "redirect:"+Global.getAdminPath()+"/supplier/supplier/?repage";
	}
	
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required = false) String extId,
			HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		try {
			List<Supplier> list = supplierService.findList(new Supplier());
			for (int i = 0; i < list.size(); i++) {
				Supplier e = list.get(i);
				if (StringUtils.isBlank(extId) || (extId != null && !extId.equals(e.getId()))) {
					Map<String, Object> map = Maps.newHashMap();
					map.put("id", e.getId());
					map.put("name", e.getCompanyName());
					mapList.add(map);
				}
			}
		} catch (Exception e) {
			logger.error("分类树结构获取失败", e);
		}
		return mapList;
	}
}