package com.uib.weixin.web;

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
import com.uib.weixin.util.UserSession;

@Controller
@RequestMapping("/wechat/Commission")
public class WeChatCommissionController extends BaseController {
	
	private Logger logger = LoggerFactory.getLogger(WeChatCommissionController.class);
	
	@Autowired	
	private CommissionService commissionService;
	
	@Autowired	
	private MemMemberService memMemberService;
	
	@RequestMapping(value = "/queryCommission")
	@ResponseBody
	public ReturnMsg<Map<String,Object>> findByName(int settleFlag,HttpServletRequest request) throws Exception{
		
		ReturnMsg<Map<String,Object>> result = new ReturnMsg<Map<String,Object>>();
		try {
			logger.info("是否已结算settleFlag=" + settleFlag);
			MemMember memMember = new MemMember();
			String userName = (String) UserSession.getSession("wxUserName");
			memMember = memMemberService.getMemMemberByUsernameNew(userName);
			//通过sessionId获取会员信息
			//MemMember memMember = memMemberService.getMemMemberBySessionId(sessionId);
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
			if(settleFlag==1){
				params.put("isSettle", "1");
			}
			else{
				params.put("isSettle", "2");
			}
			List<Commission> doList =commissionService.findByName(params);
			data.put("doList", doList);
			result.setStatus(true);
			result.setData(data);
		} catch (Exception e) {
			logger.error("查询佣金记录报错！", e);
			result.setCode(ExceptionEnum.business_logic_exception.getIndex());
			result.setMsg(ExceptionEnum.business_logic_exception.getValue());
			result.setStatus(false);
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
