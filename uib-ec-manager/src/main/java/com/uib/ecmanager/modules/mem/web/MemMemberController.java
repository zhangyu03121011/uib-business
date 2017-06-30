/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.mem.web;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.formula.ptg.MemErrPtg;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.uib.common.utils.DigestUtil;
import com.uib.ecmanager.common.config.Global;
import com.uib.ecmanager.common.enums.ApproveFlag;
import com.uib.ecmanager.common.enums.UserType;
import com.uib.ecmanager.common.persistence.Page;
import com.uib.ecmanager.common.utils.StringUtils;
import com.uib.ecmanager.common.web.BaseController;
import com.uib.ecmanager.modules.mem.entity.MemMember;
import com.uib.ecmanager.modules.mem.entity.MemRank;
import com.uib.ecmanager.modules.mem.service.MemMemberService;
import com.uib.ecmanager.modules.mem.service.MemRankService;
import com.uib.ecmanager.modules.sys.entity.User;
import com.uib.ecmanager.modules.sys.service.SystemService;

/**
 * 会员表Controller
 * 
 * @author kevin
 * @version 2015-05-31
 */
@Controller
@RequestMapping(value = "${adminPath}/mem/memMember")
public class MemMemberController extends BaseController {

	@Autowired
	private MemMemberService memMemberService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private MemRankService memRankService;
	public static String frontWebImageBaseUrl = "";
	
	@ModelAttribute
	public MemMember get(@RequestParam(required = false) String id) {
		MemMember entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = memMemberService.get(id);
		}
		if (entity == null) {
			entity = new MemMember();
		}
		return entity;
	}

	@RequiresPermissions("mem:memMember:view")
	@RequestMapping(value = { "list", "" })
	public String list(MemMember memMember, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		if ("3".equals(memMember.getApproveFlag())) {
			memMember.setApproveFlag(null);
		}
		Page<MemMember> page = memMemberService.findPage(new Page<MemMember>(
				request, response), memMember);	
		List<MemRank> memRankList = memRankService.findList(new MemRank());
		model.addAttribute("memRankList", memRankList);
		model.addAttribute("listUserType", getUserType());
		model.addAttribute("page", page);
		return "modules/mem/memMemberList";
	}

	@RequiresPermissions("mem:memMember:view")
	@RequestMapping(value = "form")
	public String form(MemMember memMember, Model model) {
		model.addAttribute("memMember", memMember);
		return "modules/mem/memMemberForm";
	}

	@RequiresPermissions("mem:memMember:view")
	@RequestMapping(value = "updateFormView")
	public String updateFormView(MemMember memMember, Model model) {
		model.addAttribute("memMember", memMember);
		return "modules/mem/memMemberupdateForm";
	}

	@RequiresPermissions("mem:memMember:view")
	@RequestMapping(value = "varifyView")
	public String varifyView(MemMember memMember, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		model.addAttribute("memMember", memMember);
		model.addAttribute("frontWebImageBaseUrl",getFrontWebImageBaseUrl());
		return "modules/mem/memMemberVarify";
	}

	/**
	 * 保存会员审核
	 * @param memMember
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("mem:memMember:edit")
	@RequestMapping(value = "saveVarifyInfo")
	public String saveVarifyInfo(MemMember memMember,
			RedirectAttributes redirectAttributes) {
		if ("1".equals(memMember.getApproveFlag())) {
			memMember.setUserType("2");
		}
		memMember.setApproveDate(new Date());
		memMemberService.update(memMember);
		addMessage(redirectAttributes, "保存会员审核成功");
		return "redirect:" + Global.getAdminPath() + "/mem/memMember/approveList/?repage";
	}
	
	@RequiresPermissions("mem:memMember:save")
	@RequestMapping(value = "save")
	public String save(MemMember memMember, Model model,
			RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, memMember)) {
			return form(memMember, model);
		}
		memMember.setId(UUID.randomUUID().toString().replaceAll("-", ""));
		memMember.setPassword(DigestUtil.MD5("123456"));
		memMember.setIsEnabled(0+"");
		memMember.setIsLocked(0+"");
		User user = systemService.getCurrent();
		memMember.setCreateBy(user);
		memMember.setCreateDate(new Date());
		memMember.setUpdateBy(user);
		memMember.setUpdateDate(new Date());
		memMember.setIsNewRecord(true);
		memMemberService.save(memMember);
		addMessage(redirectAttributes, "保存会员成功");
		return "redirect:" + Global.getAdminPath() + "/mem/memMember/?repage";
	}

	@RequiresPermissions("mem:memMember:edit")
	@RequestMapping(value = "update")
	public String update(MemMember memMember, Model model,
			RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, memMember)) {
			return form(memMember, model);
		}
		memMemberService.update(memMember);
		addMessage(redirectAttributes, "修改会员成功");
		return "redirect:" + Global.getAdminPath() + "/mem/memMember/?repage";
	}

	@RequiresPermissions("mem:memMember:edit")
	@RequestMapping(value = "delete")
	public String delete(MemMember memMember,
			RedirectAttributes redirectAttributes) {
		memMemberService.delete(memMember);
		addMessage(redirectAttributes, "删除会员成功");
		return "redirect:" + Global.getAdminPath() + "/mem/memMember/?repage";
	}
	
	public static String getFrontWebImageBaseUrl() {
		if(StringUtils.isBlank(frontWebImageBaseUrl)){
			frontWebImageBaseUrl = Global.getConfig("frontWeb.image.baseUrl");
		}
		return frontWebImageBaseUrl;
	}
	

	
	/**
	 * 会员审核列表
	 * @param memMember
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("mem:memMember:view")
	@RequestMapping(value = "approveList")
	public String approveList(MemMember memMember, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Page<MemMember> page = memMemberService.findMemApprovePage(new Page<MemMember>(
				request, response), memMember);
		List<MemRank> memRankList = memRankService.findList(new MemRank());
		model.addAttribute("memRankList", memRankList);
		model.addAttribute("listApproveFlag", getApproveFlag());
		model.addAttribute("page", page);
		return "modules/mem/memApproveList";
	}
	
	/**
	 * 会员审核查看
	 * @param memMember
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("mem:memMember:view")
	@RequestMapping(value = "approveForm")
	public String approveForm(MemMember memMember, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Page<MemMember> page = memMemberService.findPage(new Page<MemMember>(
				request, response), memMember);
		model.addAttribute("page", page);
		return "modules/mem/memApproveForm";
	}
	
	/**
	 * 会员审核删除
	 * @param memMember
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("mem:memMember:edit")
	@RequestMapping(value = "approveDelete")
	public String approveDelete(MemMember memMember,
			RedirectAttributes redirectAttributes) {
		if (memMember.getApproveFlag() != null) {
			memMember.setApproveFlag(null);
			memMemberService.update(memMember);
		}
		addMessage(redirectAttributes, "删除会员审核成功");
		return "redirect:" + Global.getAdminPath() + "/mem/memMember/approveList/?repage";
	}
	
	/**
	 * 会员审核枚举类
	 * @return
	 */
	public static Map<Integer,String> getApproveFlag() {
		Map<Integer,String> listApproveFlag = new HashMap<Integer,String>();
		for(ApproveFlag a : ApproveFlag.values()){
			listApproveFlag.put(a.getIndex(), a.getDescription());
		}
		return listApproveFlag;
	}
	
	/**
	 * 用户类型枚举类
	 * @return
	 */
	public static Map<Integer,String> getUserType() {
		Map<Integer,String> listUserType = new HashMap<Integer,String>();
		for(UserType ct : UserType.values()){
			listUserType.put(ct.getIndex(), ct.getDescription());
		}
		return listUserType;
	}
	
}