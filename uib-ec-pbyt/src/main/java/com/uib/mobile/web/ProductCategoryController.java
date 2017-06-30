/**
 * Copyright &copy; 2014-2016  All rights reserved.
 *
 * Licensed under the 深圳嘉宝易汇通科技发展有限公司 License, Version 1.0 (the "License");
 * 
 */
package com.uib.mobile.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.uib.product.service.ProductService;
import com.uib.serviceUtils.Utils;

/**
 * @ClassName: ProductCategoryController
 * @Description:商品分类
 * @author sl
 * @date 2015年9月15日 上午10:52:22
 */
@Controller
@RequestMapping("/mobile/productCategory")
public class ProductCategoryController extends BaseController {
	@Autowired
	private ProductCategoryService productCategoryServiceimpl;
	
	@Autowired
	private ProductCategoryService productCategoryService;
	
	@Autowired
	private ProductService productServiceImpl;
	
	
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
			returnMsg.setStatus(false);
			returnMsg.setCode(ExceptionEnum.business_logic_exception.getIndex());
			returnMsg.setMsg(ExceptionEnum.business_logic_exception.getValue());
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
		ProductCategory ProductCategory = new ProductCategory();
		if (StringUtil.isNotBlank(id)) {
			returnMsg.setStatus(false);
			returnMsg.setCode(ExceptionEnum.param_not_null.getIndex());
			return returnMsg;
		}
		try {
			ProductCategory = productCategoryServiceimpl.getProductCategory(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		returnMsg.setData(ProductCategory);
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
	public ReturnMsg<Object> querySubCategory(String id) {
		logger.info("查询商品分类入参parentId=" + id);
		ReturnMsg<Object> result = new ReturnMsg<Object>();
		try {
			if (Utils.isBlank(id)) {
				result.setStatus(false);
				result.setCode(ExceptionEnum.param_not_null.getIndex());
				result.setMsg(ExceptionEnum.param_not_null.getValue());
				return result;
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("parentId", id);
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
	
	@RequestMapping("/findProdCategoryAndPageMoblie")
	@ResponseBody
	public ReturnMsg<Map<String,Object>> findProdCategoryAndPageMoblie(String orderparam,String page,boolean down,String categoryId,String level,String homeOrSearch,String proCategoryName,HttpServletRequest request,HttpServletResponse response){
		String productName="";
		String resultName="";
		logger.info("app后台：按时间、价格、销量查询商品列表入参orderparam=" + orderparam + ",page=" + page+",down="+down+",homeOrSearch="+homeOrSearch);
		ReturnMsg<Map<String,Object>> result = new ReturnMsg<Map<String,Object>>();
		Map<String,Object> reMap = new HashMap<String, Object>();
		List<Object> list = new ArrayList<Object>();
		if(Utils.isObjectsBlank(orderparam,page,down)){
			result.setStatus(false);
			result.setCode(ExceptionEnum.param_not_null.getIndex());
			result.setMsg(ExceptionEnum.param_not_null.getValue());
			return result;
		}
		try {
			if(homeOrSearch.equals("home")){
				//首页分类，一级，二级，三级
				productName="";
				if(categoryId.equals("null") || categoryId.equals(""))
				{
					categoryId="";
				} else {
					ProductCategory productCategory =productCategoryService.getProductCategory(categoryId);
					 resultName = productCategory.getName();
				}
				if(StringUtils.isNotEmpty(categoryId) && ("1".equals(level) || "2".equals(level)))
				{
					//查询一级下面所有的分类id
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("parentId", categoryId);
					String imageUrl = Global.getConfig("upload.image.path");
					map.put("imageUrl", imageUrl);
					List<Category4Mobile> categorys = productCategoryServiceimpl.querySubCategory4Mobile(map);
					if("1".equals(level)) {
						for (Category4Mobile category4Mobile : categorys) {
							List<Category4Mobile> subCategorys = category4Mobile.getSubCategorys();
							for (Category4Mobile category4Mobile2 : subCategorys) {
								list.add(category4Mobile2.getId());
							}
						}
					} else if("2".equals(level)) {
						for (Category4Mobile category4Mobile : categorys) {
								list.add(category4Mobile.getId());
						}
					}
				} else {
					list.add(categoryId);
				}
			}else if(homeOrSearch.equals("search")){
				//搜索页面
				 productName = java.net.URLDecoder.decode(proCategoryName,"utf-8");
				 categoryId = "";
				 list.add(categoryId);
				if(productName.equals("null"))
				{
					productName="";
				}
				resultName = productName;
			}
			logger.info("app后台：按产品分类categoryId="+categoryId+",productName="+productName);
			List<Map<String,Object>> products = productServiceImpl.findProdCategoryAndPageMoblie(orderparam,page,down,categoryId,list,productName);
			reMap.put("productName", resultName);
			reMap.put("products", products);
			result.setData(reMap);
		} catch (Exception e) {
			logger.error("app后台：按时间、价格、销量查询商品列表失败!", e);
			result.setCode(ExceptionEnum.business_logic_exception.getIndex());
			result.setMsg(ExceptionEnum.business_logic_exception.getValue());
		}
		return result;
	}

}
