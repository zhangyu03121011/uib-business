/**
 * Copyright &copy; 2014-2016  All rights reserved.
 *
 * Licensed under the 深圳嘉宝易汇通科技发展有限公司 License, Version 1.0 (the "License");
 * 
 */
package com.uib.mobile.web;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.RequestContext;

import com.easypay.common.utils.UUIDGenerator;
import com.uib.base.BaseController;
import com.uib.common.enums.ExceptionEnum;
import com.uib.common.utils.StringUtils;
import com.uib.mobile.dto.CommentDto;
import com.uib.mobile.dto.ReturnMsg;
import com.uib.mobile.service.CommentService;
import com.uib.order.entity.OrderTable;
import com.uib.order.service.OrderService;
import com.uib.serviceUtils.Utils;

/**
 * @Todo 商品评论
 * @date 2015年11月10日
 * @author Ly
 */
@Controller
@RequestMapping("/mobile/comment")
public class MobileCommentController extends BaseController {
	
	private static final Log logger = LogFactory.getLog(MobileCommentController.class);
	
	@Value("/mobile/estimate")
	private String mobileEstimateView;
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private OrderService orderService;
	
	/**
	 * 历史订单评价晒单保存
	 * @param commentDto
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public ReturnMsg<Object> saveMember(CommentDto commentDto, HttpServletRequest request, HttpServletResponse response) {
		logger.info("历史订单评价晒单保存开始");
		ReturnMsg<Object> returnMsg = new ReturnMsg<Object>();
		RequestContext requestContext = new RequestContext(request);
		try {
			String validateMesage = Utils.validate(commentDto);
			if (!StringUtils.isEmpty(validateMesage)) {
				returnMsg.setCode(ExceptionEnum.param_not_null.getIndex());
				returnMsg.setStatus(false);
				returnMsg.setMsg(ExceptionEnum.param_not_null.getValue());
				return returnMsg;
			}
			commentDto.setId(UUIDGenerator.getUUID());
			commentService.saveComment(commentDto);
			
			orderService.updateIsCommentByorderItemNo(commentDto.getOrderTableItemId());
			returnMsg.setCode("200");
			returnMsg.setStatus(true);
			returnMsg.setData(null);
		} catch (Exception ex) {
			logger.info("历史订单评价晒单保存异常"+ex);
			ex.printStackTrace();
			returnMsg.setStatus(false);
			returnMsg.setMsg(requestContext.getMessage("user.regist.failregist"));
			return returnMsg;
		}
		logger.info("历史订单评价晒单保存结束");
		return returnMsg;
	}
	
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
			this.getEstimateData(productId, modelMap);
		} catch (Exception e) {
			logger.info("初始化用户口碑页面数据异常：" + e);
		}
	
		return mobileEstimateView;
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
			this.getEstimateData(productId, modelMap);
		} catch (Exception e) {
			logger.info("初始化分享用户口碑页面数据异常：" + e);
		}
	
		return mobileEstimateView;
	}
	
	
	/**
	 * 获取口碑页面数据
	 * @param productId
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	private ModelMap getEstimateData(String productId,ModelMap modelMap){
		Map<String, Object> params = new HashMap<String,Object>();
		try {
			//查询评论总数，好评总数，中评总数，差评总数，平均分
			Map<String,Object> avgCore =commentService.findCountGroupByProductId(productId);
			if(null == avgCore){
				Map<String,Object> avg = new HashMap<String,Object>();
				avg.put("hp", "0");
				avg.put("cp", "0");
				avg.put("zp", "0");
				avg.put("scorePercent", "width:0%;");
				avg.put("hpRate", "0");
				avg.put("zongp", "0");
				modelMap.put("avgCore", avg);
				modelMap.put("hpList", null);
				modelMap.put("zpList", null);
				modelMap.put("cpList", null);
			}else{
				float fhpRate = Float.parseFloat(avgCore.get("hp").toString()) / Float.parseFloat(avgCore.get("all").toString()) * 100;
				BigDecimal bhpRate = new BigDecimal(fhpRate);
				//好评率采用四舍五入保留2位小数
				double hpRate = bhpRate.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(); 
				avgCore.put("hpRate", hpRate);
				
				//综评分数保留2位小数
				float fzongp = Float.parseFloat(avgCore.get("zongp").toString());
				BigDecimal bzongp = new BigDecimal(fzongp);
				double zongp = bzongp.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(); 
				avgCore.put("zongp", zongp);
				
				params.put("productId", productId);
				params.put("flag", 3);
				//查询好评
				List<Map<String, Object>> hpList = commentService.findById(params);
				params.put("flag", 2);
				//查询中评
				List<Map<String, Object>> zpList = commentService.findById(params);
				params.put("flag", 1);
				//查询差评
				List<Map<String, Object>> cpList = commentService.findById(params);
				modelMap.put("avgCore", avgCore);
				modelMap.put("hpList", hpList);
				modelMap.put("zpList", zpList);
				modelMap.put("cpList", cpList);
			}
			
		} catch (Exception e) {
			logger.info("初始化分享用户口碑页面数据异常：" + e);
		}
		return modelMap;
	}
	
}
