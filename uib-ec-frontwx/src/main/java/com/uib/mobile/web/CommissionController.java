package com.uib.mobile.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.uib.base.BaseController;
import com.uib.common.enums.ExceptionEnum;
import com.uib.member.entity.MemMember;
import com.uib.member.service.MemMemberService;
import com.uib.mobile.dto.Commission;
import com.uib.mobile.dto.ReturnMsg;
import com.uib.mobile.service.CommissionService;
import com.uib.serviceUtils.Utils;

@Controller
@RequestMapping("/mobile/Commission")
public class CommissionController extends BaseController {
	
	private Logger logger = LoggerFactory.getLogger(CommissionController.class);
	
	@Autowired	
	private CommissionService commissionService;
	
	@Autowired	
	private MemMemberService memMemberService;
	
	@RequestMapping(value = "/queryCommission")
	@ResponseBody
	public ReturnMsg<Map<String,Object>> findByName(String sessionId,String settleFlag,HttpServletRequest request) throws Exception{
		
		ReturnMsg<Map<String,Object>> result = new ReturnMsg<Map<String,Object>>();
		result.setStatus(false);
		try {
			/*String sessionId = parm.get("sessionId");
			String settleFlag= parm.get("settleFlag");*/
			logger.info("查找我的佣金入参sessionId="+sessionId + ",settleFlag=" + settleFlag);
			if (Utils.isObjectsBlank(sessionId)) {
				result.setCode(ExceptionEnum.param_not_null.getIndex());
				result.setMsg(ExceptionEnum.param_not_null.getValue());
				return result;
			}
			
			//通过sessionId获取会员信息
			MemMember memMember = memMemberService.getMemMemberBySessionId(sessionId);
			//检查会员是否存在
			if (Utils.isBlank(memMember)) {
				result.setCode(ExceptionEnum.member_not_exist.getIndex());
				result.setMsg(ExceptionEnum.member_not_exist.getValue());
				return result;
			}
			Map<String, Object> data = new HashMap<String, Object>();
			Map<String, Object> params = new HashMap<String, Object>();
			String accountAmt = memMember.getBalance();
			String commAmt = Double.toString(memMember.getCommission());
			
			data.put("accountAmt", accountAmt);
			data.put("commAmt", commAmt);
			
			String fromUserId = memMember.getId();
			logger.info("会员id=" + fromUserId);
			params.put("fromUserId", fromUserId);
			//1-代表未结算，2-代表已结算
			if(settleFlag=="1"){
				params.put("isSettle", "1");
			}
			else{
				params.put("isSettle", "2");
			}
			List<Commission> doList =commissionService.findByName(params);
			/*params.put("fromUserId", fromUserId);
			params.put("isSettle", "0");
			List<Commission> doList =commissionService.findByName(params);
			
			params.put("isSettle", "1");
			List<Commission> doneList =commissionService.findByName(params);*/
			data.put("doList", doList);
			//data.put("doneList", doneList);
			result.setStatus(true);
			result.setData(data);
		} catch (Exception e) {
			logger.error("查询佣金记录报错！", e);
			result.setCode(ExceptionEnum.business_logic_exception.getIndex());
			result.setMsg(ExceptionEnum.business_logic_exception.getValue());
		}
		return result;
	}
	/*
	 * 功能：删除拥金
	 */
	@RequestMapping(value = "/deleteCommission")
	@ResponseBody
	public ReturnMsg<Object> deleteCommission(String[] ids,HttpServletRequest request) throws Exception{
		ReturnMsg<Object> returnMsg = new ReturnMsg<Object>();
		try {
			/*String strIds = parm.get("ids");
			String[] ids = strIds.split(",");*/
			commissionService.deleteCommissionById(ids);
			returnMsg.setCode("200");
			returnMsg.setMsg("删除成功！");
			returnMsg.setStatus(true);
		} catch (Exception e) {
			logger.info("删除佣金列表失败：" + e);
			returnMsg.setStatus(false);
			returnMsg.setCode(ExceptionEnum.commission_not_exist.getIndex());
			returnMsg.setMsg(ExceptionEnum.commission_not_exist.getValue());
			return returnMsg;
		}
		return returnMsg;
	}
}
