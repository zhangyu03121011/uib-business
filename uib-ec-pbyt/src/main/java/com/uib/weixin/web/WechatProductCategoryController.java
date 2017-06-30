/**
 * Copyright &copy; 2014-2016  All rights reserved.
 *
 * Licensed under the 深圳嘉宝易汇通科技发展有限公司 License, Version 1.0 (the "License");
 * 
 */
package com.uib.weixin.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.uib.base.BaseController;
import com.uib.common.enums.ExceptionEnum;
import com.uib.common.utils.StringUtil;
import com.uib.common.utils.StringUtils;
import com.uib.common.web.Global;
import com.uib.mobile.dto.Category4Mobile;
import com.uib.mobile.dto.ReturnMsg;
import com.uib.product.entity.ProductCategory;
import com.uib.product.service.ProductCategoryService;
import com.uib.serviceUtils.Utils;

/**
 * @ClassName: ProductCategoryController
 * @Description:商品分类
 * @author sl
 * @date 2015年9月15日 上午10:52:22
 */
@Controller
@RequestMapping("/wechat/productCategory")
public class WechatProductCategoryController extends BaseController {
	private Logger logger = LoggerFactory.getLogger(WechatProductCategoryController.class);
	
	@Autowired
	private ProductCategoryService productCategoryServiceimpl;
	
	/**
	 * 根据上级ID获取分类信息
	 * 
	 * @param parentId
	 * @param merId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getCategoryByMeridAndParentId")
	@ResponseBody
	public ReturnMsg<List<ProductCategory>> getCategoryByMeridAndParentId(String parentId) {
		logger.info("根据上级ID获取分类信息开始");
		ReturnMsg<List<ProductCategory>> returnMsg = new ReturnMsg<List<ProductCategory>>();
		List<ProductCategory> productCategoryList = new ArrayList<ProductCategory>();
		if (StringUtils.isEmpty(parentId)) {
			returnMsg.setStatus(false);
			returnMsg.setCode(ExceptionEnum.param_not_null.getIndex());
			returnMsg.setMsg(ExceptionEnum.param_not_null.getValue());
			return returnMsg;
		}
		try {
			String imageUrl = Global.getConfig("upload.image.path");
			productCategoryList = productCategoryServiceimpl.getCategoryByMeridAndParentId(parentId, null);
			for (ProductCategory productCategory : productCategoryList) {
				productCategory.setImagePath(imageUrl+productCategory.getImagePath());
			}
		} catch (Exception e) {
			returnMsg.setStatus(false);
			logger.info("根据上级ID获取分类信息异常"+e);
		}
		returnMsg.setStatus(true);
		returnMsg.setData(productCategoryList);
		return returnMsg;
	}

	/**
	 * 查询下级菜单
	 */
	@RequestMapping("/getProductCategory")
	@ResponseBody
	public ReturnMsg<ProductCategory> getProductCategory(String id) {
		ReturnMsg<ProductCategory> returnMsg = new ReturnMsg<ProductCategory>();
		ProductCategory productCategory = new ProductCategory();
		if (StringUtil.isNotBlank(id)) {
			returnMsg.setStatus(false);
			returnMsg.setCode(ExceptionEnum.param_not_null.getIndex());
			return returnMsg;
		}
		try {
			productCategory = productCategoryServiceimpl.getProductCategory(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		returnMsg.setData(productCategory);
		return returnMsg;
	}

	/**
	 * 根据分类id查询子分类
	 * 
	 * @param id
	 *            分类id
	 * @return
	 */
	@RequestMapping("/query/subcategory")
	@ResponseBody
	public ReturnMsg<Object> querySubCategory(String parentId) {
		logger.info("查询商品分类入参parentId=" + parentId);
		ReturnMsg<Object> result = new ReturnMsg<Object>();
		try {
			if (Utils.isBlank(parentId)) {
				result.setStatus(false);
				result.setCode(ExceptionEnum.param_not_null.getIndex());
				result.setMsg(ExceptionEnum.param_not_null.getValue());
				return result;
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("parentId", parentId);
			String imageUrl = Global.getConfig("upload.image.path");
			map.put("imageUrl", imageUrl);
			List<Category4Mobile> categorys = productCategoryServiceimpl.querySubCategory4Mobile(map);
			result.setStatus(true);
			result.setData(categorys);
		} catch (Exception e) {
			logger.info("查询商品分类异常："+ e);
			result.setStatus(false);
			result.setCode(ExceptionEnum.business_logic_exception.getIndex());
			result.setMsg(ExceptionEnum.business_logic_exception.getValue());
		}
		return result;
	}

}
