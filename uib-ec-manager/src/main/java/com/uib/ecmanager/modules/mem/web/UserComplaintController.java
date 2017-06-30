/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.mem.web;

import java.util.Date;
import java.util.HashMap;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.uib.ecmanager.common.config.Global;
import com.uib.ecmanager.common.enums.Complaint_Type;
import com.uib.ecmanager.common.persistence.Page;
import com.uib.ecmanager.common.utils.StringUtils;
import com.uib.ecmanager.common.web.BaseController;
import com.uib.ecmanager.modules.mem.entity.UserComplaint;
import com.uib.ecmanager.modules.mem.service.UserComplaintService;

/**
 * 会员投诉Controller
 * @author luogc
 * @version 2016-01-12
 */
@Controller
@RequestMapping(value = "${adminPath}/mem/userComplaint")
public class UserComplaintController extends BaseController {

	@Autowired
	private UserComplaintService userComplaintService;
	public static String frontWebImageBaseUrl = "";
	
	@RequestMapping(value = {"get"})
	@ModelAttribute
	public UserComplaint get(@RequestParam(required=false) String id) {
		UserComplaint entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = userComplaintService.get(id);
		}
		if (entity == null){
			entity = new UserComplaint();
		}
		return entity;
	}
	
	@RequiresPermissions("mem:userComplaint:view")
	@RequestMapping(value = {"list", ""})
	public String list(UserComplaint userComplaint, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<UserComplaint> page = userComplaintService.findPage(new Page<UserComplaint>(request, response), userComplaint);
		model.addAttribute("listComplaint_Type", getComplaintType());
		model.addAttribute("page", page);
		return "modules/mem/userComplaintList";
	}
	
	@RequiresPermissions("mem:userComplaint:view")
	@RequestMapping(value = "form")
	public String form(UserComplaint userComplaint, Model model) {
		model.addAttribute("userComplaint", userComplaint);
		return "modules/mem/userComplaintForm";
	}
	
	@RequiresPermissions("mem:userComplaint:view")
	@RequestMapping(value = "updateFormView")
	public String updateFormView(UserComplaint userComplaint, Model model) {
		userComplaint = get(userComplaint.getId());
		model.addAttribute("userComplaint", userComplaint);
		return "modules/mem/userComplaintupdateForm";
	}

	@RequiresPermissions("mem:userComplaint:save")
	@RequestMapping(value = "save")
	public String save(UserComplaint userComplaint, Model model, RedirectAttributes redirectAttributes, MultipartFile complaintImg) {
		if (!beanValidator(model, userComplaint)){
			return form(userComplaint, model);
		}
		userComplaintService.save(userComplaint);
		addMessage(redirectAttributes, "保存会员投诉成功");
		return "redirect:"+Global.getAdminPath()+"/mem/userComplaint/list/?repage";
	}
	
	@RequiresPermissions("mem:userComplaint:edit")
	@RequestMapping(value = "update")
	public String update(UserComplaint userComplaint, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, userComplaint)){
			return form(userComplaint, model);
		}
		userComplaintService.update(userComplaint);
		addMessage(redirectAttributes, "修改会员投诉成功");
		return "redirect:"+Global.getAdminPath()+"/mem/userComplaint/list/?repage";
	}
	
	@RequiresPermissions("mem:userComplaint:edit")
	@RequestMapping(value = "delete")
	public String delete(UserComplaint userComplaint, RedirectAttributes redirectAttributes) {
		userComplaintService.delete(userComplaint);
		addMessage(redirectAttributes, "删除会员投诉成功");
		return "redirect:"+Global.getAdminPath()+"/mem/userComplaint/list/?repage";
	}
	
	/**
	 * 查看投诉
	 * @param userComplaint
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("mem:userComplaint:view")
	@RequestMapping(value = "replyView")
	public String replyView(UserComplaint userComplaint,Model model,HttpServletRequest request, HttpServletResponse response) {	
		userComplaint = get(userComplaint.getId());
		model.addAttribute("userComplaint", userComplaint);
		model.addAttribute("frontWebImageBaseUrl",getFrontWebImageBaseUrl());
		return "modules/mem/userComplaintReply";
	}	
	
	/**
	 * 答复投诉
	 * @param userComplaint
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("mem:userComplaint:view")
	@RequestMapping(value = "saveReplyInfo")
	public String saveReplyInfo(UserComplaint userComplaint, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		String reply = userComplaint.getReply();
		userComplaint = get(userComplaint.getId());
		userComplaint.setReply(reply);
		userComplaint.setSolutionTime(new Date());
		userComplaint.setSolutionState("1");
		userComplaintService.update(userComplaint);
		userComplaint = new UserComplaint();
		Page<UserComplaint> page = userComplaintService.findPage(new Page<UserComplaint>(request, response), userComplaint);
		model.addAttribute("page", page);
		model.addAttribute("userComplaint", userComplaint);
		model.addAttribute("listComplaint_Type", getComplaintType());
		return "modules/mem/userComplaintList";
	}
	
	/**
	 * 获取服务器配置路径
	 * @return
	 */
	public static String getFrontWebImageBaseUrl() {
		if(StringUtils.isBlank(frontWebImageBaseUrl)){
			frontWebImageBaseUrl = Global.getConfig("frontWeb.image.baseUrl");
		}
		return frontWebImageBaseUrl;
	}
	
	/**
	 * 投诉类型枚举类
	 * @return
	 */
	public static Map<Integer,String> getComplaintType() {
		Map<Integer,String> listComplaint_Type = new HashMap<Integer,String>();
		for(Complaint_Type ct : Complaint_Type.values()){
			listComplaint_Type.put(ct.getIndex(), ct.getDescription());
		}
		return listComplaint_Type;
	}
}