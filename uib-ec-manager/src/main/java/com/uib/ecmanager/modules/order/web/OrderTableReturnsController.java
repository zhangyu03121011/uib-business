/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.order.web;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.Region;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.uib.ecmanager.common.config.Global;
import com.uib.ecmanager.common.persistence.Page;
import com.uib.ecmanager.common.utils.DateUtils;
import com.uib.ecmanager.common.utils.StringUtils;
import com.uib.ecmanager.common.utils.excel.ExportExcel;
import com.uib.ecmanager.common.web.BaseController;
import com.uib.ecmanager.modules.order.dao.OrderTableReturnsItemDao;
import com.uib.ecmanager.modules.order.entity.OrderTableReturns;
import com.uib.ecmanager.modules.order.entity.OrderTableReturnsItem;
import com.uib.ecmanager.modules.order.service.OrderTableReturnsService;
/**
 * 退货单Controller
 * 
 * @author limy
 * @version 2015-06-08
 */
@Controller
@RequestMapping(value = "${adminPath}/order/orderTableReturns")
public class OrderTableReturnsController extends BaseController {
	private Logger logger = Logger.getLogger(OrderTableReturnsController.class);
	
	@Autowired
	private OrderTableReturnsService orderTableReturnsService;
	@Autowired
	private OrderTableReturnsItemDao orderTableReturnsItemDao;
	@Value("${upload.image.path}")
	private String uploadImagePath;
	@Value("${frontWeb.image.baseUrl}")
	private String baseUrl;
	@Value("${frontweb.image.folder}")
	private String imageFolder;

	@ModelAttribute
	public OrderTableReturns get(@RequestParam(required = false) String id) {
		OrderTableReturns entity = null;
		try {
			if (StringUtils.isNotBlank(id)) {
				entity = orderTableReturnsService.get(id);
			}
			if (entity == null) {
				entity = new OrderTableReturns();
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		return entity;
	}

	@RequiresPermissions("order:orderTableReturns:view")
	@RequestMapping(value = { "list", "" })
	public String list(OrderTableReturns orderTableReturns,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		try {
			Page<OrderTableReturns> page = orderTableReturnsService.findPage(
					new Page<OrderTableReturns>(request, response),
					orderTableReturns);
			model.addAttribute("page", page);
		} catch (Exception e) {
			logger.error("", e);
		}
		return "modules/order/orderTableReturnsList";
	}

	@RequiresPermissions("order:orderTableReturns:view")
	@RequestMapping(value = "form")
	public String form(OrderTableReturns orderTableReturns, Model model) {
		try {
			model.addAttribute("orderTableReturns", orderTableReturns);
		} catch (Exception e) {
			logger.error("", e);
		}

		return "modules/order/orderTableReturnsForm";
	}

	@RequiresPermissions("order:orderTableReturns:view")
	@RequestMapping(value = "updateFormView")
	public String updateFormView(OrderTableReturns orderTableReturns,
			Model model) {
		try {
			model.addAttribute("orderTableReturns", orderTableReturns);
		} catch (Exception e) {
			logger.error("", e);
		}

		return "modules/order/orderTableReturnsupdateForm";
	}

	@RequiresPermissions("order:orderTableReturns:save")
	@RequestMapping(value = "save")
	public String save(OrderTableReturns orderTableReturns, Model model,
			RedirectAttributes redirectAttributes) {
		try {
			if (!beanValidator(model, orderTableReturns)) {
				return form(orderTableReturns, model);
			}
			orderTableReturnsService.save(orderTableReturns);
			addMessage(redirectAttributes, "保存退货单成功");
		} catch (Exception e) {
			logger.error("", e);
		}

		return "redirect:" + Global.getAdminPath()
				+ "/order/orderTableReturns/?repage";
	}

	@RequiresPermissions("order:orderTableReturns:edit")
	@RequestMapping(value = "update")
	public String update(OrderTableReturns orderTableReturns, Model model,
			RedirectAttributes redirectAttributes) {
		try {
			if (!beanValidator(model, orderTableReturns)) {
				return form(orderTableReturns, model);
			}
			orderTableReturnsService.update(orderTableReturns);
			addMessage(redirectAttributes, "修改退货单成功");
		} catch (Exception e) {
			logger.error("", e);
		}
		return "redirect:" + Global.getAdminPath()
				+ "/order/orderTableReturns/?repage";
	}

	@RequiresPermissions("order:orderTableReturns:edit")
	@RequestMapping(value = "delete")
	public String delete(OrderTableReturns orderTableReturns,
			RedirectAttributes redirectAttributes) {
		try {
			orderTableReturnsService.delete(orderTableReturns);
			addMessage(redirectAttributes, "删除退货单成功");
		} catch (Exception e) {
			logger.error("", e);
		}
		return "redirect:" + Global.getAdminPath()
				+ "/order/orderTableReturns/?repage";
	}

	@RequiresPermissions("order:orderTableReturns:edit")
	@RequestMapping(value = "updateStatus")
	public String updateStatus(OrderTableReturns orderTableReturns) {
		orderTableReturnsService.updateStatus(orderTableReturns);
		return "redirect:" + Global.getAdminPath()
				+ "/order/orderTableReturns/list?repage";

	}

	@RequiresPermissions("order:orderTableReturns:view")
	@RequestMapping(value = "export")
	public String exportFile(OrderTableReturns orderTableReturns,
			HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes) {
		OutputStream osOutputStream = null;
		try {
			final String userAgent = request.getHeader("USER-AGENT");
			String fileName = "退货订单数据" + DateUtils.getDate("yyyyMMddHHmmss")
					+ ".xlsx";
			Page<OrderTableReturns> page = orderTableReturnsService.findPage(
					new Page<OrderTableReturns>(request, response),
					orderTableReturns);
			// new ExportExcel("退货订单数据", OrderTableReturns.class)
			// .setDataList(page.getList()).write(response, fileName)
			// .dispose();
			List<OrderTableReturns> tableReturns = page.getList();
			// 第一步，创建一个webbook，对应一个Excel文件
			HSSFWorkbook wb = new HSSFWorkbook();
			// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
			HSSFSheet sheet = wb.createSheet(fileName);
			// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
			HSSFRow row = sheet.createRow((int) 0);
			// 第四步，创建单元格，并设置值表头 设置表头居中
			HSSFCellStyle style = wb.createCellStyle();
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
			style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			HSSFCell cell = row.createCell((short) 0);
			cell.setCellValue("退货单编号");
			cell.setCellStyle(style);
			cell = row.createCell((short) 1);
			cell.setCellValue("订单编号");
			cell.setCellStyle(style);
			cell = row.createCell((short) 2);
			cell.setCellValue("商品编号");
			cell.setCellStyle(style);
			cell = row.createCell((short) 3);
			cell.setCellValue("供应商名称");
			cell.setCellStyle(style);
			cell = row.createCell((short) 4);
			cell.setCellValue("退货时间");
			cell.setCellStyle(style);
			cell = row.createCell((short) 5);
			cell.setCellValue("用户姓名");
			cell.setCellStyle(style);
			cell = row.createCell((short) 6);
			cell.setCellValue("手机号码");
			cell.setCellStyle(style);
			cell = row.createCell((short) 7);
			cell.setCellValue("退货类型");
			cell.setCellStyle(style);
			cell = row.createCell((short) 8);
			cell.setCellValue("退货原因");
			cell.setCellStyle(style);
			cell = row.createCell((short) 9);
			cell.setCellValue("退货金额");
			cell.setCellStyle(style);
			cell = row.createCell((short) 10);
			cell.setCellValue("退货说明");
			cell.setCellStyle(style);
			cell = row.createCell((short) 11);
			cell.setCellValue("退货状态");
			cell.setCellStyle(style);
			cell = row.createCell((short) 12);
			cell.setCellValue("图片凭证");
			cell.setCellStyle(style);
			int i = 0;
			BufferedImage bufferImg = null;
			HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
			sheet.addMergedRegion(new Region(0, (short) 12, 0, (short) 17));
			for (OrderTableReturns orderTableReturn : tableReturns) {
				row = sheet.createRow((int) i + 1);
				i++;
				row.createCell(0).setCellValue(orderTableReturn.getReturnNo());
				row.getCell(0).setCellStyle(style);
				row.createCell(1).setCellValue(orderTableReturn.getOrderNo());
				row.getCell(1).setCellStyle(style);
				row.createCell(2).setCellValue(orderTableReturn.getProductId());
				row.getCell(2).setCellStyle(style);
				row.createCell(3).setCellValue(orderTableReturn.getCompanyName());
				row.getCell(3).setCellStyle(style);
				row.createCell(4).setCellValue(
						DateUtils.formatDate(orderTableReturn.getApplyTime(),
								"yyyy/MM/dd"));
				row.getCell(4).setCellStyle(style);
				row.createCell(5).setCellValue(orderTableReturn.getUserName());
				row.getCell(5).setCellStyle(style);
				row.createCell(6).setCellValue(orderTableReturn.getPhone());
				row.getCell(6).setCellStyle(style);
				row.createCell(7).setCellValue(
						orderTableReturn.getReturnTypeStr());
				row.getCell(7).setCellStyle(style);
				row.createCell(8).setCellValue(
						orderTableReturn.getReturnReason());
				row.getCell(8).setCellStyle(style);
				row.createCell(9).setCellValue(orderTableReturn.getReturnSum());
				row.getCell(9).setCellStyle(style);
				row.createCell(10).setCellValue(
						orderTableReturn.getRetrunDesc());
				row.getCell(10).setCellStyle(style);
				row.createCell(11).setCellValue(
						orderTableReturn.getReturnStatusStr());
				row.getCell(11).setCellStyle(style);
				List<OrderTableReturnsItem> tableReturnsItems = orderTableReturnsItemDao
						.findList(new OrderTableReturnsItem(orderTableReturn));
				logger.info("开始去查图片了……-------------------------------------------" );
				if (tableReturnsItems != null && tableReturnsItems.size() > 0) {
					sheet.getRow(i).setHeight((short) 600);
					for (int j = 0; j < tableReturnsItems.size(); j++) {
						OrderTableReturnsItem tableReturnsItem = tableReturnsItems
								.get(j);
						//tableReturnsItem.setImgDiskUrl(uploadImagePath+ imageFolder + tableReturnsItem.getImage());
						tableReturnsItem.setImgDiskUrl(uploadImagePath+ tableReturnsItem.getImage());
						ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
						System.err.println("图片路径++++"
								+ tableReturnsItems.get(j).getImgDiskUrl());
						logger.info("2图片路径……-----------------------------------------"+tableReturnsItems.get(j).getImgDiskUrl());
						bufferImg = ImageIO.read(new File(tableReturnsItems
								.get(j).getImgDiskUrl()));
						ImageIO.write(bufferImg, "jpg", byteArrayOut);
						// anchor主要用于设置图片的属性
						HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0,
								255, 255, (short) (12 + j + j), i,
								(short) (12 + 2 * j + 1), i);
						anchor.setAnchorType(3);
						// 插入图片
						patriarch.createPicture(anchor, wb.addPicture(
								byteArrayOut.toByteArray(),
								HSSFWorkbook.PICTURE_TYPE_JPEG));
					}
				}
			}
			// 设置自动对齐
			sheet.autoSizeColumn(0);
			sheet.autoSizeColumn(1);
			sheet.autoSizeColumn(2);
			sheet.autoSizeColumn(3);
			sheet.autoSizeColumn(4);
			sheet.autoSizeColumn(5);
			sheet.autoSizeColumn(6);
			sheet.autoSizeColumn(7);
			sheet.autoSizeColumn(8);
			sheet.autoSizeColumn(9);
			sheet.autoSizeColumn(10);
			sheet.autoSizeColumn(11);
			sheet.autoSizeColumn(12);
			sheet.autoSizeColumn(13);
			osOutputStream = response.getOutputStream();
			response.reset();
			// 解决中文文件名在不同浏览器下乱码问题
			String finalFileName = null;
			if (StringUtils.contains(userAgent, "MSIE")) {// IE浏览器
				finalFileName = URLEncoder.encode(fileName, "UTF8");
			} else if (StringUtils.contains(userAgent, "Mozilla")) {// google,火狐浏览器
				finalFileName = new String(fileName.getBytes(), "ISO8859-1");
			} else {
				finalFileName = URLEncoder.encode(fileName, "UTF8");// 其他浏览器
			}
			response.setHeader("Content-disposition", "attachment; filename="
					+ finalFileName + ".xls");
			response.setContentType("application/msexcel");
			wb.write(osOutputStream);
			osOutputStream.close();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出退货订单失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + Global.getAdminPath()
				+ "/order/orderTableReturns/list?repage";
	}
}