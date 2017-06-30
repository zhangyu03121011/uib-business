/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.ad.web;

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
import com.uib.ecmanager.modules.ad.entity.AdvertisementPosition;
import com.uib.ecmanager.modules.ad.service.AdvertisementPositionService;

/**
 * 广告位Controller
 * @author gaven
 * @version 2015-06-06
 */
@Controller
@RequestMapping(value = "${adminPath}/ad/advertisementPosition")
public class AdvertisementPositionController extends BaseController {

	@Autowired
	private AdvertisementPositionService advertisementPositionService;
	
	@ModelAttribute
	public AdvertisementPosition get(@RequestParam(required=false) String id) {
		AdvertisementPosition entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = advertisementPositionService.get(id);
		}
		if (entity == null){
			entity = new AdvertisementPosition();
		}
		return entity;
	}
	
	@RequiresPermissions("ad:advertisementPosition:view")
	@RequestMapping(value = {"list", ""})
	public String list(AdvertisementPosition advertisementPosition, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AdvertisementPosition> page = advertisementPositionService.findPage(new Page<AdvertisementPosition>(request, response), advertisementPosition); 
		model.addAttribute("page", page);
		return "modules/ad/advertisementPositionList";
	}

	@RequiresPermissions("ad:advertisementPosition:view")
	@RequestMapping(value = "form")
	public String form(AdvertisementPosition advertisementPosition, Model model) {
		model.addAttribute("advertisementPosition", advertisementPosition);
		return "modules/ad/advertisementPositionForm";
	}
	
	@RequiresPermissions("ad:advertisementPosition:view")
	@RequestMapping(value = "updateFormView")
	public String updateFormView(AdvertisementPosition advertisementPosition, Model model) {
		model.addAttribute("advertisementPosition", advertisementPosition);
		return "modules/ad/advertisementPositionupdateForm";
	}

	@RequiresPermissions("ad:advertisementPosition:save")
	@RequestMapping(value = "save")
	public String save(AdvertisementPosition advertisementPosition, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, advertisementPosition)){
			return form(advertisementPosition, model);
		}
		advertisementPositionService.save(advertisementPosition);
		addMessage(redirectAttributes, "保存广告位成功");
		return "redirect:"+Global.getAdminPath()+"/ad/advertisementPosition/?repage";
	}
	
	@RequiresPermissions("ad:advertisementPosition:edit")
	@RequestMapping(value = "update")
	public String update(AdvertisementPosition advertisementPosition, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, advertisementPosition)){
			return form(advertisementPosition, model);
		}
		advertisementPositionService.update(advertisementPosition);
		addMessage(redirectAttributes, "修改广告位成功");
		return "redirect:"+Global.getAdminPath()+"/ad/advertisementPosition/?repage";
	}
	
	@RequiresPermissions("ad:advertisementPosition:edit")
	@RequestMapping(value = "delete")
	public String delete(AdvertisementPosition advertisementPosition, RedirectAttributes redirectAttributes) {
		advertisementPositionService.delete(advertisementPosition);
		addMessage(redirectAttributes, "删除广告位成功");
		return "redirect:"+Global.getAdminPath()+"/ad/advertisementPosition/?repage";
	}

}