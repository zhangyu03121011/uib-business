/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.mem.web;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import com.uib.ecmanager.modules.mem.entity.MemRank;
import com.uib.ecmanager.modules.mem.service.MemRankService;

/**
 * 会员等级(单表)Controller
 * @author kevin
 * @version 2015-05-29
 */
@Controller
@RequestMapping(value = "${adminPath}/mem/memRank")
public class MemRankController extends BaseController {

	@Autowired
	private MemRankService memRankService;
	
	@ModelAttribute
	public MemRank get(@RequestParam(required=false) String id) {
		MemRank entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = memRankService.get(id);
		}
		if (entity == null){
			entity = new MemRank();
		}
		return entity;
	}
	
	@RequiresPermissions("mem:memRank:view")
	@RequestMapping(value = {"list", ""})
	public String list(MemRank memRank, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<MemRank> page = memRankService.findPage(new Page<MemRank>(request, response), memRank); 
		List<MemRank> rankList = page.getList();
		for (MemRank rank : rankList) {
			int count = memRankService.findMemberByMemRankId(rank.getId());
			rank.setCount(count);
		}
		page.setList(rankList);
		model.addAttribute("page", page);
		return "modules/mem/memRankList";
	}

	@RequiresPermissions("mem:memRank:view")
	@RequestMapping(value = "form")
	public String form(MemRank memRank, Model model) {
		model.addAttribute("memRank", memRank);
		return "modules/mem/memRankForm";
	}
	
	@RequiresPermissions("mem:memRank:view")
	@RequestMapping(value = "updateFormView")
	public String updateFormView(MemRank memRank, Model model) {
		int count = memRankService.findMemberByMemRankId(memRank.getId());
		memRank.setCount(count);
		model.addAttribute("memRank", memRank);
		return "modules/mem/memRankupdateForm";
	}

	@RequiresPermissions("mem:memRank:save")
	@RequestMapping(value = "save")
	public String save(MemRank memRank, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, memRank)){
			return form(memRank, model);
		} else if (memRank.getName() == null) {
			addMessage(model, "保存失败，名称不能为空！");
			return form(memRank, model);
		} else if (memRank.getAmount() == null) {
			addMessage(model, "保存失败，贡献值不能为空！");
			return form(memRank, model);
		} 
		memRank.setIsSpecial("0");
		memRankService.save(memRank);
		addMessage(redirectAttributes, "保存会员等级成功");
		return "redirect:"+Global.getAdminPath()+"/mem/memRank/?repage";
	}
	
	@RequiresPermissions("mem:memRank:edit")
	@RequestMapping(value = "update")
	public String update(MemRank memRank, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, memRank)){
			return form(memRank, model);
		} else if (memRank.getName() == null) {
			addMessage(model, "修改失败，名称不能为空！");
			return form(memRank, model);
		} else if (memRank.getAmount() == null) {
			addMessage(model, "修改失败，贡献值不能为空！");
			return form(memRank, model);
		} 
		memRankService.update(memRank);
		addMessage(redirectAttributes, "修改会员等级成功");
		return "redirect:"+Global.getAdminPath()+"/mem/memRank/?repage";
	}
	
	@RequiresPermissions("mem:memRank:edit")
	@RequestMapping(value = "delete")
	public String delete(MemRank memRank, RedirectAttributes redirectAttributes) {
		memRankService.delete(memRank);
		addMessage(redirectAttributes, "删除会员等级成功");
		return "redirect:"+Global.getAdminPath()+"/mem/memRank/?repage";
	}

}