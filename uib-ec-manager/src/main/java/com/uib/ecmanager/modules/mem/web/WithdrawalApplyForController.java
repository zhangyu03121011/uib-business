/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.mem.web;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
import com.uib.ecmanager.common.enums.WithdrawalApplyStatus;
import com.uib.ecmanager.common.persistence.Page;
import com.uib.ecmanager.common.utils.StringUtils;
import com.uib.ecmanager.common.web.BaseController;
import com.uib.ecmanager.modules.mem.entity.MemMember;
import com.uib.ecmanager.modules.mem.entity.WithdrawalApplyFor;
import com.uib.ecmanager.modules.mem.service.MemMemberService;
import com.uib.ecmanager.modules.mem.service.WithdrawalApplyForService;

/**
 * 提现管理表Controller
 * @author luogc
 * @version 2016-07-28
 */
@Controller
@RequestMapping(value = "${adminPath}/mem/withdrawalApplyFor")
public class WithdrawalApplyForController extends BaseController {

	@Autowired
	private WithdrawalApplyForService withdrawalApplyForService;
	
	@ModelAttribute
	public WithdrawalApplyFor get(@RequestParam(required=false) String id) {
		WithdrawalApplyFor entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = withdrawalApplyForService.get(id);
		}
		if (entity == null){
			entity = new WithdrawalApplyFor();
		}
		return entity;
	}
	
	@RequiresPermissions("mem:withdrawalApplyFor:view")
	@RequestMapping(value = {"list", ""})
	public String list(WithdrawalApplyFor withdrawalApplyFor, HttpServletRequest request, HttpServletResponse response, Model model) {
		withdrawalApplyFor.setFlag("0");
		Page<WithdrawalApplyFor> page = withdrawalApplyForService.findPage(new Page<WithdrawalApplyFor>(request, response), withdrawalApplyFor); 
		model.addAttribute("withdrawalapplyStatus", getWithdrawalApplyStatus());
		model.addAttribute("page", page);
		return "modules/mem/withdrawalApplyForList";
	}

	@RequiresPermissions("mem:withdrawalApplyFor:view")
	@RequestMapping(value = "form")
	public String form(WithdrawalApplyFor withdrawalApplyFor, Model model) {
		model.addAttribute("withdrawalApplyFor", withdrawalApplyFor);
		return "modules/mem/withdrawalApplyForForm";
	}
	
	@RequiresPermissions("mem:withdrawalApplyFor:view")
	@RequestMapping(value = "updateFormView")
	public String updateFormView(WithdrawalApplyFor withdrawalApplyFor, Model model) {
		model.addAttribute("withdrawalApplyFor", withdrawalApplyFor);
		return "modules/mem/withdrawalApplyForupdateForm";
	}

	@RequiresPermissions("mem:withdrawalApplyFor:save")
	@RequestMapping(value = "save")
	public String save(WithdrawalApplyFor withdrawalApplyFor, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, withdrawalApplyFor)){
			return form(withdrawalApplyFor, model);
		}
		withdrawalApplyForService.save(withdrawalApplyFor);
		addMessage(redirectAttributes, "保存提现管理表成功");
		return "redirect:"+Global.getAdminPath()+"/mem/withdrawalApplyFor/?repage";
	}
	
	@RequiresPermissions("mem:withdrawalApplyFor:edit")
	@RequestMapping(value = "update")
	public String update(WithdrawalApplyFor withdrawalApplyFor, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, withdrawalApplyFor)){
			return form(withdrawalApplyFor, model);
		}
		withdrawalApplyForService.update(withdrawalApplyFor);
	    if(withdrawalApplyFor.getApplyStatus().equals("1")){
			if(withdrawalApplyFor.getRemarks()==null || withdrawalApplyFor.getRemarks()==""){
				return "modules/mem/withdrawalApplyForupdateForm";
			}
		}
		addMessage(redirectAttributes, "修改提现管理表成功");
		return "redirect:"+Global.getAdminPath()+"/mem/withdrawalApplyFor/?repage";
	}
	
	/*@RequiresPermissions("mem:withdrawalApplyFor:edit")
	@RequestMapping(value = "delete")
	public String delete(WithdrawalApplyFor withdrawalApplyFor, RedirectAttributes redirectAttributes) {
		withdrawalApplyForService.delete(withdrawalApplyFor);
		addMessage(redirectAttributes, "删除提现管理表成功");
		return "redirect:"+Global.getAdminPath()+"/mem/withdrawalApplyFor/?repage";
	}*/

	/**
	 * 提现状态枚举类
	 * @return
	 */
	public static Map<Integer,String> getWithdrawalApplyStatus() {
		Map<Integer,String> withdrawalapplyStatus = new HashMap<Integer,String>();
		for(WithdrawalApplyStatus ct : WithdrawalApplyStatus.values()){
			withdrawalapplyStatus.put(ct.getIndex(), ct.getDescription());
		}
		return withdrawalapplyStatus;
	}
		
	/**
	 * 导出提现数据
	 * @param withdrawalApplyFor
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "export")
	@ResponseBody
	public void exportWithdrawalApplyFor(WithdrawalApplyFor withdrawalApplyFor, HttpServletRequest request,
			HttpServletResponse response) {
		OutputStream os = null;
		HSSFWorkbook wb = null;
		try {
			final String userAgent = request.getHeader("USER-AGENT");
			List<WithdrawalApplyFor> applyForList = new ArrayList<WithdrawalApplyFor>();
			Page<WithdrawalApplyFor> page = withdrawalApplyForService.findPage(
					new Page<WithdrawalApplyFor>(request, response), withdrawalApplyFor);
			long pageSize = page.getPageSize();
			long countNum = page.getCount();
			int loopAccount = (int) (countNum % pageSize == 0 ? (countNum
					/ pageSize - 1) : (countNum / pageSize));
			applyForList.addAll(page.getList());
			for (int i = 0; i < loopAccount; i++) {
				page = new Page<WithdrawalApplyFor>();
				page.setPageNo(i + 1);
				page.setPageSize((int) pageSize);
				page = withdrawalApplyForService.findPages(page, withdrawalApplyFor);
				applyForList.addAll(page.getList());
			}
			// 第一步，创建一个webbook，对应一个Excel文件
			wb = new HSSFWorkbook();
			// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
			HSSFSheet sheet = wb.createSheet("提现数据");
			// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
			HSSFRow row = sheet.createRow((int) 0);
			// 第四步，创建单元格，并设置值表头 设置表头居中
			HSSFCellStyle style = wb.createCellStyle();
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
			HSSFCell cell = row.createCell(0);
			cell.setCellValue("用户名");
			cell.setCellStyle(style);
			cell = row.createCell(1);
			cell.setCellValue("手机号");
			cell.setCellStyle(style);
			cell = row.createCell(2);
			cell.setCellValue("提现银行");
			cell.setCellStyle(style);
			cell = row.createCell(3);
			cell.setCellValue("所属省市");
			cell.setCellStyle(style);
			cell = row.createCell(4);
			cell.setCellValue("所属分行");
			cell.setCellStyle(style);
			cell = row.createCell(5);
			cell.setCellValue("银行卡号");
			cell.setCellStyle(style);
			cell = row.createCell(6);
			cell.setCellValue("持卡人姓名");
			cell.setCellStyle(style);
			cell = row.createCell(7);
			cell.setCellValue("提现金额(元)");
			cell.setCellStyle(style);
			cell = row.createCell(8);
			cell.setCellValue("申请时间");
			cell.setCellStyle(style);
			cell = row.createCell(9);
			cell.setCellValue("提现状态");
			cell.setCellStyle(style);
			int orderSize = applyForList.size();
			// 第五步，写入实体数据
			for (int i = 0; i < orderSize; i++) {
				row = sheet.createRow((int) i + 1);
				WithdrawalApplyFor af = applyForList.get(i);
				row.createCell(0).setCellValue(af.getApplyUsername());
				row.createCell(1).setCellValue(af.getApplyPhone());
				row.createCell(2).setCellValue(af.getBankName());
				row.createCell(3).setCellValue(af.getProvinceCity());
				row.createCell(4).setCellValue(af.getBranchBankName());
				row.createCell(5).setCellValue(af.getCardNo());
				row.createCell(6).setCellValue(af.getCardPersonName());				
				if (af.getApplyAmount() == null) {
					row.createCell(7).setCellValue("");
				} else {
					row.createCell(7).setCellValue(af.getApplyAmount().doubleValue());
				}
				row.createCell(8).setCellValue(af.getApplyDate() == null ? "" : new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(af.getApplyDate()));				
				row.createCell(9).setCellValue("0".equals(af.getApplyStatus()) ? "未处理":"1".equals(af.getApplyStatus()) 
						? "处理失败":"2".equals(af.getApplyStatus()) ? "处理成功" : "");
			}
			os = response.getOutputStream();
			response.reset();

			// 解决中文文件名在不同浏览器下乱码问题
			String fileName = "提现记录" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			String finalFileName = null;
			if (StringUtils.contains(userAgent, "MSIE")) {// IE浏览器
				finalFileName = URLEncoder.encode(fileName, "UTF8");
			} else if (StringUtils.contains(userAgent, "Mozilla")) {// google,火狐浏览器
				finalFileName = new String(fileName.getBytes(), "ISO8859-1");
			} else {
				finalFileName = URLEncoder.encode(fileName, "UTF8");// 其他浏览器
			}
			response.setHeader("Content-disposition", "attachment; filename=" + finalFileName + ".xls");
			response.setContentType("application/msexcel");
			wb.write(os);
		} catch (Exception e) {
			logger.error("导出信息报错!", e);
		} finally {
			try {
				if (os != null) {
					os.close();
				}
			} catch (IOException e) {
				logger.error("关闭流报错", e);
			}
		}
	}
}