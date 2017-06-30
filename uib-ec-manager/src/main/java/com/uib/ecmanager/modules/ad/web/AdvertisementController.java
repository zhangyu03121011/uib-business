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
import com.uib.ecmanager.modules.ad.entity.Advertisement;
import com.uib.ecmanager.modules.ad.entity.AdvertisementPosition;
import com.uib.ecmanager.modules.ad.service.AdvertisementPositionService;
import com.uib.ecmanager.modules.ad.service.AdvertisementService;

/**
 * 广告管理Controller
 * @author gaven
 * @version 2015-06-06
 */
@Controller
@RequestMapping(value = "${adminPath}/ad/advertisement")
public class AdvertisementController extends BaseController {

	@Autowired
	private AdvertisementService advertisementService;
	@Autowired
	private AdvertisementPositionService advertisementPositionService;
	
	@ModelAttribute
	public Advertisement get(@RequestParam(required=false) String id) {
		Advertisement entity = null;
		try {
			if (StringUtils.isNotBlank(id)) {
				entity = advertisementService.get(id);
			}
			if (entity == null) {
				entity = new Advertisement();
			}
		} catch (Exception e) {
			logger.error("根据id获取广告信息失败",e);
		}
		return entity;
	}
	
	@RequiresPermissions("ad:advertisement:view")
	@RequestMapping(value = {"list", ""})
	public String list(Advertisement advertisement, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Advertisement> page = advertisementService.findPage(new Page<Advertisement>(request, response), advertisement); 
		model.addAttribute("page", page);
		return "modules/ad/advertisementList";
	}

	@RequiresPermissions("ad:advertisement:view")
	@RequestMapping(value = "form")
	public String form(Advertisement advertisement, Model model) {
		model.addAttribute("advertisement", advertisement);
		model.addAttribute("positionList", advertisementPositionService.findList());
		return "modules/ad/advertisementForm";
	}
	
	@RequiresPermissions("ad:advertisement:view")
	@RequestMapping(value = "updateFormView")
	public String updateFormView(Advertisement advertisement, Model model) {
		model.addAttribute("advertisement", advertisement);
		model.addAttribute("positionList", advertisementPositionService.findList());
		return "modules/ad/advertisementupdateForm";
	}

	@RequiresPermissions("ad:advertisement:save")
	@RequestMapping(value = "save")
	public String save(Advertisement advertisement, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, advertisement)){
			return form(advertisement, model);
		}
		advertisementService.save(advertisement);
		addMessage(redirectAttributes, "保存广告管理成功");
		return "redirect:"+Global.getAdminPath()+"/ad/advertisement/?repage";
	}
	
	@RequiresPermissions("ad:advertisement:edit")
	@RequestMapping(value = "update")
	public String update(Advertisement advertisement, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, advertisement)){
			return form(advertisement, model);
		}
		advertisementService.update(advertisement);
		addMessage(redirectAttributes, "修改广告管理成功");
		return "redirect:"+Global.getAdminPath()+"/ad/advertisement/?repage";
	}
	
	@RequiresPermissions("ad:advertisement:edit")
	@RequestMapping(value = "delete")
	public String delete(Advertisement advertisement, RedirectAttributes redirectAttributes) {
		advertisementService.delete(advertisement);
		addMessage(redirectAttributes, "删除广告管理成功");
		return "redirect:"+Global.getAdminPath()+"/ad/advertisement/?repage";
	}

}