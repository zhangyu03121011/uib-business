package com.uib.ptyt.web;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.uib.common.enums.ExceptionEnum;
import com.uib.mobile.dto.ReturnMsg;
import com.uib.ptyt.constants.WechatConstant;
import com.uib.ptyt.entity.MemMerchantDto;
import com.uib.ptyt.service.MemMemberService;
import com.uib.ptyt.service.MemMerchantService;
import com.uib.weixin.util.UserSession;

@RequestMapping("/memMember")
@Controller
public class MemMemberController {
    
	private Logger logger = LoggerFactory.getLogger(MemMemberController.class);
	
	@Autowired
	private MemMemberService memMemberService;
	
	@Autowired
	private MemMerchantService memMerchantService;
	
	/**
	 * 根据openId查询会员信息
	 * @param openId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryMemMember")
	@ResponseBody
	public ReturnMsg<Map<String,Object>> queryMemMember(){
		ReturnMsg<Map<String,Object>> result = new ReturnMsg<Map<String,Object>>();
		try{
			//UserSession.setSession(WechatConstant.OPEN_ID, "ow2uIv1mkDslQANzRGNMyu-xSNTo");
			//UserSession.setSession(WechatConstant.USER_ID,"2b50c5f742dd44a9870d966b13cfbd87");
			String openId=(String) UserSession.getSession(WechatConstant.OPEN_ID);
			if(openId==null || openId.equals("")){
				result.setStatus(false);
				result.setCode(ExceptionEnum.openId_is_null.getIndex());
				result.setMsg(ExceptionEnum.openId_is_null.getValue());
				return result;
			}
			Map<String,Object> list=memMemberService.queryMemMember(openId,null);
			result.setStatus(true);
			result.setData(list); 
		}catch (Exception e) {
			logger.error("微信后台：根据openId查询会员信息失败!", e);
			result.setStatus(false);
			result.setCode(ExceptionEnum.business_logic_exception.getIndex());
			result.setMsg(ExceptionEnum.business_logic_exception.getValue());
		}
		return result;
	}
	
	/**
	 * 根据Id判断会员类型是否是分销商 
	 * @param openId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/FlagMemMember")
	@ResponseBody
	public ReturnMsg<Object> FlagMemMember(){
		ReturnMsg<Object> result = new ReturnMsg<Object>();
		try{
			String id=(String) UserSession.getSession(WechatConstant.USER_ID);
			result.setData(id);
			Map<String,Object> list=new HashMap<String,Object>();
			list=memMemberService.queryMemMember(null,id);
			String userType=(String) list.get("userType");
			String approveFlag=(String)list.get("approveFlag");
			if(WechatConstant.USER_TYPE_C.equals(userType) || !(WechatConstant.APPROVE_FLAG_1.equals(approveFlag))){
				result.setStatus(false);
				result.setCode(ExceptionEnum.member_consumer.getIndex());
				result.setMsg(ExceptionEnum.member_consumer.getValue());
				result.setData(list);
			}else if(WechatConstant.USER_TYPE_B.equals(userType) && WechatConstant.APPROVE_FLAG_1.equals(approveFlag)){
				result.setStatus(true);
				result.setData(list);
			}else if(WechatConstant.USER_TYPE_C2.equals(userType) || !(WechatConstant.APPROVE_FLAG_1.equals(approveFlag))){
				result.setStatus(false);
				result.setCode(ExceptionEnum.member_consumer.getIndex());
				result.setMsg(ExceptionEnum.member_consumer.getValue());
				result.setData(list);
			} else {
				result.setStatus(false);
			}
		}catch (Exception e) {
			logger.error("微信后台：根据openId查询会员信息失败!", e);
			result.setStatus(false);
			result.setCode(ExceptionEnum.business_logic_exception.getIndex());
			result.setMsg(ExceptionEnum.business_logic_exception.getValue());
		}
		return result;
	}
	/**
	 * 根据id更新会员信息
	 * @param openId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateMemMember")
	@ResponseBody
	public ReturnMsg<Object> updateMemMember(String username,String card,String phone,String approveFlag){
		logger.info("微信后台：根据id更新会员信息username=" + username+",card="+card+",phone="+phone);
		ReturnMsg<Object> result = new ReturnMsg<Object>();
		if(null==username || ""==username||null==card || ""==card||null==phone || ""==phone){
			result.setStatus(false);
			result.setCode(ExceptionEnum.param_not_null.getIndex());
			result.setMsg(ExceptionEnum.param_not_null.getValue());
			return result;
		}
		try{
			//String id="fcc57ff5843f4b25a021976f659f2665";
			String id=(String) UserSession.getSession(WechatConstant.USER_ID);
			if(null==id || ""==id){
				result.setStatus(false);
				result.setCode(ExceptionEnum.param_not_null.getIndex());
				result.setMsg("id"+ExceptionEnum.param_not_null.getValue());
				return result;
			}
			//根据手机号去查询数据是否已存在,若存在,给与提示
			Map<String,Object> map=memMemberService.queryMemMemberByPhone(phone);
			if(!(null==map)){
				String map_id=(String)map.get("id");
				if(!(id.equals(map_id))){
					//手机号已存在
					result.setStatus(false);
					result.setCode(ExceptionEnum.phone_is_exist.getIndex());
					result.setMsg(ExceptionEnum.phone_is_exist.getValue());
					return result;
				}
			}
			//从商户表查 该member Id是否已有数据 防止脏数据的产生
			if(!("2".equals(approveFlag))){
				MemMerchantDto memMerchantDto=new MemMerchantDto();
			    memMerchantDto.setMemberId(id);
			    MemMerchantDto result_Dto=memMerchantService.queryMerchant(memMerchantDto);
			    if(!(null==result_Dto)){
			    	result.setStatus(false);
			    	result.setCode(ExceptionEnum.merchat_is_exist.getIndex());
			    	result.setMsg(ExceptionEnum.merchat_is_exist.getValue());
			    	return result;
			    }	
			}
			memMemberService.updateMemMember(id, username, card, phone,approveFlag);
			result.setStatus(true);
		}catch (Exception e) {
			logger.error("微信后台：根据id更新会员信息失败!", e);
			result.setStatus(false);
			result.setCode(ExceptionEnum.business_logic_exception.getIndex());
			result.setMsg(ExceptionEnum.business_logic_exception.getValue());
		}
		return result;
	}
	/**
	 * 根据id更新会员手机
	 * @param phone
	 */
	@RequestMapping("/updatePhone")
	@ResponseBody
	public ReturnMsg<Object> updatePhone(String phone,String userType){
		logger.info("微信后台：根据id更新会员手机phone=" + phone);
		ReturnMsg<Object> result = new ReturnMsg<Object>();
		if(null==phone || ""==phone){
			result.setStatus(false);
			result.setCode(ExceptionEnum.param_not_null.getIndex());
			result.setMsg(ExceptionEnum.param_not_null.getValue());
			return result;
		}
		try{
			//根据手机号去查询数据是否已存在,若存在,给与提示
			Map<String,Object> map=memMemberService.queryMemMemberByPhone(phone);
			//String member_id="fcc57ff5843f4b25a021976f659f2665";
			String member_id=(String) UserSession.getSession(WechatConstant.USER_ID);
			if(!(null==map)){
				String map_id=(String)map.get("id");
				if(!(member_id.equals(map_id))){
					//手机号已存在
					result.setStatus(false);
					result.setCode(ExceptionEnum.phone_is_exist.getIndex());
					result.setMsg(ExceptionEnum.phone_is_exist.getValue());
					return result;
				}
			}
			memMemberService.updatePhone(phone, userType);
			result.setStatus(true);
		}catch (Exception e) {
			logger.error("微信后台：根据id更新会员手机失败!", e);
			result.setStatus(false);
			result.setCode(ExceptionEnum.business_logic_exception.getIndex());
			result.setMsg(ExceptionEnum.business_logic_exception.getValue());
		}
		return result;
	}
}

