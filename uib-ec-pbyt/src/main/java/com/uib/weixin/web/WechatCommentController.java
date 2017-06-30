/**
 * Copyright &copy; 2014-2016  All rights reserved.
 *
 * Licensed under the 深圳嘉宝易汇通科技发展有限公司 License, Version 1.0 (the "License");
 * 
 */
package com.uib.weixin.web;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.uib.base.BaseController;
import com.uib.product.service.impl.ProductCommentService;
import com.uib.serviceUtils.Utils;

@Controller
@RequestMapping("/wechat/comment")
public class WechatCommentController extends BaseController {
	
	private static final Log logger = LogFactory.getLog(WechatCommentController.class);
	
	@Value("/weixin/estimate")
	private String wechatEstimateView;
	
	@Autowired
	private ProductCommentService commentService;
	
	/**
	 * 用户口碑
	 * @param productId
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/userEstimate", method = RequestMethod.GET)
	public String userEvaluate(String productId,ModelMap modelMap) {
		logger.info("用户口碑页面数据入参productId=" + productId);
		try {
			commentService.getEstimateData(productId, modelMap);
		} catch (Exception e) {
			logger.info("初始化用户口碑页面数据异常：" + e);
		}
	
		return wechatEstimateView;
	}
	
	/**
	 * 分享用户口碑
	 * @param productId
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/shareUserEstimate", method = RequestMethod.GET)
	public String shareUserEstimate(String productId,ModelMap modelMap) {
		logger.info("分享用户口碑页面数据入参productId=" + productId);
		try {
			modelMap.put("flag", "0");
			commentService.getEstimateData(productId, modelMap);
		} catch (Exception e) {
			logger.info("初始化分享用户口碑页面数据异常：" + e);
		}
	
		return wechatEstimateView;
	}
	
	/**
	 * 初始化用户口碑数据(分页)
	 * @param productId
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/userEstimateByPage")
	@ResponseBody
	public Map<String,Object> userEvaluateByPage(String productId) {
		logger.info("用户口碑页面数据入参productId=" + productId);
		return commentService.getEstimateDataByPage(productId, "1");
	}
	
	/**
	 * 分页查询商品评论数据
	 * @param productId 商品id
	 * @param page      页数
	 * @param flag      1-差评；2-中评；3-好评 
	 * @return
	 */
	@RequestMapping(value = "/queryCommentByPage")
	@ResponseBody
	public List<Map<String, Object>> queryCommentByPage(String productId,String page,String flag) {
		logger.info("微信端：分页查询商品评论入参productId=" + productId + ",page=" + page + ",flag=" + flag);
		if (Utils.isObjectsBlank(productId, page, flag)) {
			logger.error("输入参数有为空，请检查");
			return null;
		}
		
		try {
			List<Map<String, Object>> commentList = commentService.queryCommentByPage(productId, page, flag);
			return commentList;
		} catch (Exception e) {
			logger.error("分页查询商品评论数据异常",e);
			return null;
		}
		
	}
	
}
