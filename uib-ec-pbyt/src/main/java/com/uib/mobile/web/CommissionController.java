package com.uib.mobile.web;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
	
	/*
	 *功能:统计当月佣金 
	 */
	@RequestMapping(value = "/countCommission")
	@ResponseBody
	public ReturnMsg<Object> countCommission(String page) throws Exception{
		ReturnMsg<Object> returnMsg = new ReturnMsg<Object>();
		try {
			List<Map<String,Object>> list=commissionService.countCommission(page);
			if(null==list){
				//service查询时报错
				returnMsg.setStatus(false);
				returnMsg.setCode(ExceptionEnum.business_logic_exception.getIndex());
				returnMsg.setMsg("查询佣金记录service"+ExceptionEnum.business_logic_exception.getValue());
				return returnMsg;
			}
			if(list.size()==1){
				for(Map<String,Object> map1:list){
					if(!(null==map1.get("flag"))){
						returnMsg.setStatus(true);
						returnMsg.setData(0.00);
						return returnMsg;
					}
				}
			}
			Calendar calendar=Calendar.getInstance();
			int month=calendar.get(Calendar.MONTH)+1;
			BigDecimal amounts=new BigDecimal(0.00);
			for(Map<String,Object> map2:list){
				//将月份等于当前月的数据的贡献值相加
				Calendar cal = Calendar.getInstance();
				Date date =new SimpleDateFormat("yyyy-MM-dd").parse((String)map2.get("createTime"));
				cal.setTime(date);
				int month2 = cal.get(Calendar.MONTH)+1;
				if(month2==month){
					if(null!=map2.get("commission")){
					    amounts=amounts.add((BigDecimal)map2.get("commission"));	
					}
				}
			}
			returnMsg.setStatus(true);
			returnMsg.setData(amounts);
		} catch (Exception e) {
			logger.info("统计当月佣金 失败：" + e);
			returnMsg.setStatus(false);
			returnMsg.setCode(ExceptionEnum.business_logic_exception.getIndex());
			returnMsg.setMsg(ExceptionEnum.business_logic_exception.getValue());
			return returnMsg;
		}
		return returnMsg;
	}
	/*
	 * 功能:津贴(佣金)明细
	 */
	@RequestMapping("/queryCommDetail")
	@ResponseBody
	public ReturnMsg<List<Map<String,Object>>> queryDetail(String page) throws Exception{
		ReturnMsg<List<Map<String,Object>>> result = new ReturnMsg<List<Map<String,Object>>>();
		try{
			List<Map<String,Object>> list=commissionService.countCommission(page);
			if(null==list){
				//service查询时报错
				result.setStatus(false);
				result.setCode(ExceptionEnum.business_logic_exception.getIndex());
				result.setMsg("查询佣金记录service"+ExceptionEnum.business_logic_exception.getValue());
				return result;
			}
			if(list.size()==1){
				for(Map<String,Object> map1:list){
					if(!(null==map1.get("flag"))){
						//service查询记录为空
						List<Map<String,Object>> list1=new ArrayList<Map<String,Object>>();
						result.setStatus(true);
						result.setData(list1);
						return result;
					}
				}
			}
			result.setStatus(true);
			result.setData(list);
		}catch (Exception e) {
			logger.error("微信后台：根据会员id查询津贴明细失败!", e);
			result.setStatus(false);
			result.setCode(ExceptionEnum.business_logic_exception.getIndex());
			result.setMsg(ExceptionEnum.business_logic_exception.getValue());
		}
		return result;
	}
}
