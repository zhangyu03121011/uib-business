package com.uib.ptyt.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;





import com.uib.base.BaseController;
import com.uib.common.enums.ExceptionEnum;
import com.uib.mobile.dto.ReturnMsg;
import com.uib.ptyt.constants.WechatConstant;
import com.uib.ptyt.entity.Special;
import com.uib.ptyt.service.SpecialService;
import com.uib.weixin.util.UserSession;
/**
 * 专题Controller
 * @author chengjian
 * 
 */
@Controller
@RequestMapping("/ptyt/special")
public class SpecialController extends BaseController {
	
	private Logger logger = LoggerFactory.getLogger(SpecialController.class);
	
	@Autowired
	private SpecialService specialService;
	
	
	/**
	 * 分页查询专题产品信息
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping("/findSpecialByPage")
	public ReturnMsg<Map<String, Object>> findSpecialByPage(@RequestParam String page,String rankId){
		logger.info("分页查询专题产品信息page=" + page);
		ReturnMsg<Map<String, Object>> result = new ReturnMsg<Map<String, Object>>();
		List<Map<String, Object>> specialList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> productList = new ArrayList<Map<String, Object>>();
		Map<String,Object> specialProduct = new HashMap<String,Object>();
		if (StringUtils.isEmpty(page)) {
			result.setStatus(false);
			result.setCode(ExceptionEnum.param_not_null.getIndex());
			result.setMsg(ExceptionEnum.param_not_null.getValue());
			return result;
		}
		try {
			if(StringUtils.isEmpty(rankId)){			    	
				rankId = (String) UserSession.getSession(WechatConstant.RANK_ID);	
			    }
			logger.info("分页查询专题产品信息rankId ====="+rankId);
			specialList =specialService.findSpecialByPage(page);
			productList =specialService.findSpecialProd(rankId);
			specialProduct.put("specialList", specialList);
			specialProduct.put("productList", productList);
			result.setData(specialProduct);
			result.setStatus(true);
		} catch (Exception e) {
			logger.error("分页查询专题产品信息失败!", e);
			result.setCode(ExceptionEnum.business_logic_exception.getIndex());
			result.setMsg(ExceptionEnum.business_logic_exception.getValue());
		}
		return result;
	}
	
	
	/**
	 * 分页查询专题产品详情信息
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping("/findProductById")
	public ReturnMsg<Object> findProductById(@RequestParam String page,@RequestParam String specialId,String rankId){
		logger.info("分页查询专题详情信息page=" + page+",specialId="+specialId);
		ReturnMsg<Object> result = new ReturnMsg<Object>();
		List<Map<String, Object>> specialProdList = new ArrayList<Map<String, Object>>();
		if (StringUtils.isEmpty(page)|| StringUtils.isEmpty(specialId)) {
			result.setStatus(false);
			result.setCode(ExceptionEnum.param_not_null.getIndex());
			result.setMsg(ExceptionEnum.param_not_null.getValue());
			return result;
		}
		try {
			 if(StringUtils.isEmpty(rankId)){			    	
				 rankId = (String) UserSession.getSession(WechatConstant.RANK_ID);	
			    }
			logger.info("分页查询专题详情信息rankId ====="+rankId);
			specialProdList =specialService.findProductById(page,specialId,rankId);
			result.setData(specialProdList);
			result.setStatus(true);
		} catch (Exception e) {
			logger.error("分页查询专题详情信息失败!", e);
			result.setCode(ExceptionEnum.business_logic_exception.getIndex());
			result.setMsg(ExceptionEnum.business_logic_exception.getValue());
		}
		return result;
	}
	
	/**
	 * 根据专题ID查询专题详情信息
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping("/findSpecialById")
	public ReturnMsg<Object> findSpecialById(@RequestParam String specialId){
		logger.info("根据专题ID查询专题详情信息specialId="+specialId);
		ReturnMsg<Object> result = new ReturnMsg<Object>();
		Map<String, Object> special = new HashMap<String, Object>();
		if (StringUtils.isEmpty(specialId)) {
			result.setStatus(false);
			result.setCode(ExceptionEnum.param_not_null.getIndex());
			result.setMsg(ExceptionEnum.param_not_null.getValue());
			return result;
		}
		try {
			special =specialService.findSpecialById(specialId);
			result.setData(special);
			result.setStatus(true);
		} catch (Exception e) {
			logger.error("根据专题ID查询专题详情信息失败!", e);
			result.setCode(ExceptionEnum.business_logic_exception.getIndex());
			result.setMsg(ExceptionEnum.business_logic_exception.getValue());
		}
		return result;
	}
	
	
	/**
	 * 根据userID查询我的专题信息
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping("/findSpecialUserId")
	public ReturnMsg<Object> findSpecialUserId(@RequestParam String page){
		logger.info("根据userID查询我的专题信息");
		ReturnMsg<Object> result = new ReturnMsg<Object>();
		List<Special> special = new ArrayList<Special>();
		//String userId="a6b29bf9da384b1aaf266d32fcadda57";
		String userId = (String) UserSession.getSession(WechatConstant.USER_ID);
		if (StringUtils.isEmpty(userId)||StringUtils.isEmpty(page)) {
			result.setStatus(false);
			result.setCode(ExceptionEnum.param_not_null.getIndex());
			result.setMsg(ExceptionEnum.param_not_null.getValue());
			return result;
		}
		try {
			special =specialService.findSpecialUserId(userId,page);
			result.setData(special);
			result.setStatus(true);
		} catch (Exception e) {
			logger.error("根据userID查询我的专题信息失败!", e);
			result.setCode(ExceptionEnum.business_logic_exception.getIndex());
			result.setMsg(ExceptionEnum.business_logic_exception.getValue());
		}
		return result;
	}
	
	
	/**
	 * 分页查询所有专题产品信息
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping("/findAllSpecial")
	public ReturnMsg<Object> findAllSpecial(@RequestParam String page){
		logger.info("分页查询所有专题产品信息page=" + page);
		ReturnMsg<Object> result = new ReturnMsg<Object>();
		List<Map<String, Object>> specialList = new ArrayList<Map<String, Object>>();
		if (StringUtils.isEmpty(page)) {
			result.setStatus(false);
			result.setCode(ExceptionEnum.param_not_null.getIndex());
			result.setMsg(ExceptionEnum.param_not_null.getValue());
			return result;
		}
		try {
			specialList =specialService.findSpecialByPage(page);
			result.setData(specialList);
			result.setStatus(true);
		} catch (Exception e) {
			logger.error("分页查询所有专题产品信息失败!", e);
			result.setCode(ExceptionEnum.business_logic_exception.getIndex());
			result.setMsg(ExceptionEnum.business_logic_exception.getValue());
		}
		return result;
	}
	
	/**
	 * 批量添加我的专题
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping("/addMySpecials")
	public ReturnMsg<Object> addMySpecials(@RequestParam String specialIds){
		logger.info("批量添加我的专题specialIds=" + specialIds);
		ReturnMsg<Object> result = new ReturnMsg<Object>();
		String 	 merchantId = (String) UserSession.getSession(WechatConstant.MERCHANT_ID);	
		if (StringUtils.isEmpty(merchantId)||StringUtils.isEmpty(specialIds)) {
			result.setStatus(false);
			result.setCode(ExceptionEnum.param_not_null.getIndex());
			result.setMsg(ExceptionEnum.param_not_null.getValue());
			return result;
		}
		try {
			specialService.addMySpecials(specialIds,merchantId);
			result.setStatus(true);
		} catch (Exception e) {
			logger.error("批量添加我的专题!", e);
			result.setCode(ExceptionEnum.business_logic_exception.getIndex());
			result.setMsg(ExceptionEnum.business_logic_exception.getValue());
		}
		return result;
	}
	
	
	/**
	 * 批量删除我的专题
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping("/deleteMySpecials")
	public ReturnMsg<Object> deleteMySpecials(@RequestParam String specialIds){
		logger.info("批量删除我的专题specialIds=" + specialIds);
		ReturnMsg<Object> result = new ReturnMsg<Object>();
			//String	 merchantId="d350eabf0f424ac39f8578562f562d78";
			String merchantId = (String) UserSession.getSession(WechatConstant.MERCHANT_ID);	
		if (StringUtils.isEmpty(merchantId)||StringUtils.isEmpty(specialIds)) {
			result.setStatus(false);
			result.setCode(ExceptionEnum.param_not_null.getIndex());
			result.setMsg(ExceptionEnum.param_not_null.getValue());
			return result;
		}
		try {
			specialService.deleteMySpecials(specialIds,merchantId);
			result.setStatus(true);
		} catch (Exception e) {
			logger.error("批量删除我的专题!", e);
			result.setCode(ExceptionEnum.business_logic_exception.getIndex());
			result.setMsg(ExceptionEnum.business_logic_exception.getValue());
		}
		return result;
	}
}
