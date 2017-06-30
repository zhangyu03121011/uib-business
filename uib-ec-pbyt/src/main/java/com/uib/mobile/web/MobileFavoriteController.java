/**
 * Copyright &copy; 2014-2016  All rights reserved.
 *
 * Licensed under the 深圳嘉宝易汇通科技发展有限公司 License, Version 1.0 (the "License");
 * 
 */
package com.uib.mobile.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.uib.base.BaseController;
import com.uib.common.enums.ExceptionEnum;
import com.uib.member.entity.MemMember;
import com.uib.member.service.MemMemberService;
import com.uib.mobile.dto.ReturnMsg;
import com.uib.product.service.MemberFavoriteProductService;
import com.uib.serviceUtils.Utils;

/**
 * @ClassName: MobileFavoriteController
 * @Description:商品收藏
 * @author zfan
 * @date 2015年11月16日 上午10:02:02
 */
@Controller
@RequestMapping("/mobile/favorite")
public class MobileFavoriteController extends BaseController {
	
	@Autowired
	private MemMemberService memMemberService;
	
	@Autowired
	private MemberFavoriteProductService memberFavoriteProductService;
	
	/***
	 * 查询收藏商品列表
	 * @param sessionId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/favoriteProductList")
	public ReturnMsg<Object> favoriteProductList(String sessionId) {
		ReturnMsg<Object> msg = new ReturnMsg<Object>();
		// 非空判断
		if (Utils.isBlank(sessionId)) {
			msg.setCode(ExceptionEnum.param_not_null.getIndex());
			msg.setMsg(ExceptionEnum.param_not_null.getValue() + "sessionId:" + sessionId);
			msg.setStatus(false);
			return msg;
		}
		
		try {
			MemMember member = memMemberService.getMemMemberBySessionId(sessionId);
			if(member == null){
				msg.setStatus(false);
				msg.setCode(ExceptionEnum.member_not_exist.getIndex());
				msg.setMsg(ExceptionEnum.member_not_exist.getValue());
				return msg;
			}
			List<Map<String, Object>> listMap = memberFavoriteProductService.getMemberFavoriteProductsByMemberId(member.getId());
			for(Map<String,Object> map:listMap){
				Object appIsMarketable=map.get("appIsMarketable");
				if("".equals(appIsMarketable)){
					map.put("appIsMarketable", "0");
				}
			}
			msg.setData(listMap);                          
			msg.setStatus(true);
		} catch (Exception e) {
			msg.setStatus(false);
			msg.setCode(ExceptionEnum.system_error.getIndex());
			msg.setMsg(ExceptionEnum.system_error.getValue());
			
			logger.error("收藏商品出错", e);
		}
		return msg;
	}
	
	/***
	 * 删除商品收藏
	 * @param sessionId
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public ReturnMsg<Object> delete(String sessionId, String[] ids) {
		ReturnMsg<Object> msg = new ReturnMsg<Object>();
		// 非空判断
		if (Utils.isBlank(sessionId) || Utils.isBlank(ids)) {
			msg.setCode(ExceptionEnum.param_not_null.getIndex());
			msg.setMsg(ExceptionEnum.param_not_null.getValue() + "sessionId:" + sessionId);
			msg.setStatus(false);
			return msg;
		}
		try {
			MemMember member = memMemberService.getMemMemberBySessionId(sessionId);
			if(member == null){
				msg.setStatus(false);
				msg.setCode(ExceptionEnum.member_not_exist.getIndex());
				msg.setMsg(ExceptionEnum.member_not_exist.getValue());
				return msg;
			}
			for(String id : ids){
				memberFavoriteProductService.deleteFavorite(id);
			}
			msg.setStatus(true);
		} catch (Exception e) {
			msg.setStatus(false);
			msg.setCode(ExceptionEnum.system_error.getIndex());
			msg.setMsg(ExceptionEnum.system_error.getValue());
			
			logger.error("收藏商品出错", e);
		}
		return msg;
	}
	
}
