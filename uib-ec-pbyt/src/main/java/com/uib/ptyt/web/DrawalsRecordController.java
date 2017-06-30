package com.uib.ptyt.web;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.uib.common.enums.ExceptionEnum;
import com.uib.common.utils.UUIDGenerator;
import com.uib.mobile.dto.ReturnMsg;
import com.uib.ptyt.constants.WechatConstant;
import com.uib.ptyt.entity.DrawalsRecordDto;
import com.uib.ptyt.service.DrawalsRecordService;
import com.uib.ptyt.service.MemMemberService;
import com.uib.serviceUtils.Utils;
import com.uib.weixin.util.UserSession;
/**
 * 提现管理
 * @author gyq
 *
 */

@RequestMapping("/draRecord")
@Controller
public class DrawalsRecordController {
    
	private Logger logger = LoggerFactory.getLogger(DrawalsRecordController.class);
	
	@Autowired
	private DrawalsRecordService drawalsRecordService;
	
	@Autowired
	private MemMemberService memMemberService;
	
	/**
	 * 查询提现信息根据指定处理状态(可分页)
	 * @param applyStatus
	 * @return
	 */
	@RequestMapping("/queryDraRecord")
	@ResponseBody
	public ReturnMsg<List<DrawalsRecordDto>> queryDraRecord(String applyStatus,String page){
		ReturnMsg<List<DrawalsRecordDto>> result = new ReturnMsg<List<DrawalsRecordDto>>();
		try{
			//String memberId="test6";
			//UserSession.setSession(WechatConstant.USER_ID,"1c366f084d704e27a3a6813d055df513");
			//UserSession.setSession(WechatConstant.USER_ID,"2b50c5f742dd44a9870d966b13cfbd87");
			String memberId=(String) UserSession.getSession(WechatConstant.USER_ID);
			List<DrawalsRecordDto> list=drawalsRecordService.queryDrawalsRecord("0",memberId,applyStatus,page);
			result.setStatus(true);
			result.setData(list);
		}catch (Exception e) {
			logger.error("微信后台：查询提现信息失败!", e);
			result.setStatus(false);
			result.setCode(ExceptionEnum.business_logic_exception.getIndex());
			result.setMsg(ExceptionEnum.business_logic_exception.getValue());
		}
		return result;
	}
	
	/**
	 * 判断是否是第一次提现
	 * @return
	 */
	@RequestMapping("/isFirst")
	@ResponseBody
	public ReturnMsg<Object> isFirst(){
		ReturnMsg<Object> result = new ReturnMsg<Object>();
		try{
			//String memberId="test6";
			String memberId=(String) UserSession.getSession(WechatConstant.USER_ID);
			List<DrawalsRecordDto> list=drawalsRecordService.queryDrawalsRecord(null,memberId,null,null);
			if(list.size()==0){
				//是第一次提现
				result.setData(true);
			}else{
				result.setData(false);
			}
		}catch (Exception e) {
			logger.error("微信后台：判断是否是第一次提现失败!", e);
			result.setStatus(false);
			result.setCode(ExceptionEnum.business_logic_exception.getIndex());
			result.setMsg(ExceptionEnum.business_logic_exception.getValue());
		}
		return result;
	}
	
	/**
	 * 插入提现信息
	 * @param drawalsRecordDto
	 * @return
	 */
	@RequestMapping("/insert")
	@ResponseBody
	public ReturnMsg<Object> insert(DrawalsRecordDto drawalsRecordDto){
		ReturnMsg<Object> result=new ReturnMsg<Object>();
		try{
			//根据银行卡号判断该记录是否已存在
			List<DrawalsRecordDto> list=drawalsRecordService.queryDraByCardNo(drawalsRecordDto.getCardNo(),"1");
			if(list.size()!=0){
				result.setStatus(false);
				result.setCode(ExceptionEnum.cardNo_is_exist.getIndex());
				result.setMsg(ExceptionEnum.cardNo_is_exist.getValue());
				return result;
			}
			//插入memberId,用户名
			//String memberId="test6";
			String memberId=(String) UserSession.getSession(WechatConstant.USER_ID);
			Map<String,Object> map=memMemberService.queryMemMember(null, memberId);
			String name=(String) map.get("name");
			drawalsRecordDto.setApplyUserName(name);
			drawalsRecordDto.setMemberId(memberId);
			String id=UUIDGenerator.getUUID();
			drawalsRecordDto.setId(id);
			drawalsRecordDto.setFlag("1");
			drawalsRecordService.insert(drawalsRecordDto);
			result.setStatus(true);
		}catch (Exception e) {
			logger.error("微信后台：判断是否是第一次提现失败!", e);
			result.setStatus(false);
			result.setCode(ExceptionEnum.business_logic_exception.getIndex());
			result.setMsg(ExceptionEnum.business_logic_exception.getValue());
		}
		return result;
	}
	/**
	 * 查寻现登录用户默认的提现记录
	 * @return
	 */
	@RequestMapping("/queryDraDefault")
	@ResponseBody
	public ReturnMsg<DrawalsRecordDto> queryDraDefault(){
		ReturnMsg<DrawalsRecordDto> result=new ReturnMsg<DrawalsRecordDto>();
		try{
			//String memberId="2b50c5f742dd44a9870d966b13cfbd87";
			String memberId=(String) UserSession.getSession(WechatConstant.USER_ID);
			List<DrawalsRecordDto> list=drawalsRecordService.queryDraDefault(memberId);
			for(DrawalsRecordDto drawalsRecordDto:list){
				result.setStatus(true);
				result.setData(drawalsRecordDto);
				return result;
			}
		}catch (Exception e) {
			logger.error("微信后台：查寻现登录用户默认的提现记录失败!", e);
			result.setStatus(false);
			result.setCode(ExceptionEnum.business_logic_exception.getIndex());
			result.setMsg(ExceptionEnum.business_logic_exception.getValue());
		}
		return result;
	}
	/**
	 * 统计用户当月已提现的次数
	 * @return
	 */
	@RequestMapping("/countAlreadyDra")
	@ResponseBody
	public ReturnMsg<Object> countAlreadyDra(){
		ReturnMsg<Object> result=new ReturnMsg<Object>();
		try{
			String memberId=(String) UserSession.getSession(WechatConstant.USER_ID);
			List<DrawalsRecordDto> list=drawalsRecordService.queryAlerayDra(memberId);
			Integer count=0;
			if(list.size()!=0){
				Calendar calendar=Calendar.getInstance();
				int month=calendar.get(Calendar.MONTH)+1;
				for(DrawalsRecordDto drawalsRecordDto:list){
					Calendar cal = Calendar.getInstance();
					cal.setTime(drawalsRecordDto.getApplyDate());
					int month2 = cal.get(Calendar.MONTH)+1;
					if(month2==month){
						count++;
					}
				}
			}
			result.setStatus(true);
			result.setData(count);
		}catch (Exception e) {
			logger.error("微信后台：统计用户当月已提现的次数失败!", e);
			result.setStatus(false);
			result.setCode(ExceptionEnum.business_logic_exception.getIndex());
			result.setMsg(ExceptionEnum.business_logic_exception.getValue());
		}
		return result;
	}
	/**
	 * 更新提现信息
	 * @param drawalsRecordDto
	 */
	@RequestMapping("/update")
	@ResponseBody
	public ReturnMsg<Object> update(DrawalsRecordDto drawalsRecordDto){
		ReturnMsg<Object> result=new ReturnMsg<Object>();
		try{
			if(Utils.isObjectsBlank(drawalsRecordDto.getCardNo(),drawalsRecordDto.getApplyAmount())){
				result.setStatus(false);
				result.setCode(ExceptionEnum.param_not_null.getIndex());
				result.setMsg(ExceptionEnum.param_not_null.getValue());
				return result;
			}
			//获取之前绑定银行卡时的信息
			List<DrawalsRecordDto> list=drawalsRecordService.queryDraByCardNo(drawalsRecordDto.getCardNo(),"1");
			for(DrawalsRecordDto drawalsRecordDto1:list){
				drawalsRecordDto.setApplyUserName(drawalsRecordDto1.getApplyUserName());
				drawalsRecordDto.setApplyPhone(drawalsRecordDto1.getApplyPhone());
				drawalsRecordDto.setCardPersonName(drawalsRecordDto1.getCardPersonName());
				drawalsRecordDto.setProvinceCity(drawalsRecordDto1.getProvinceCity());
				drawalsRecordDto.setBankName(drawalsRecordDto1.getBankName());
				drawalsRecordDto.setBranchBankName(drawalsRecordDto1.getBranchBankName());
				drawalsRecordDto.setMemberId(drawalsRecordDto1.getMemberId());
			}
			drawalsRecordDto.setFlag("0");
			String id=UUIDGenerator.getUUID();
			drawalsRecordDto.setId(id);
			drawalsRecordDto.setApplyStatus("0");
			drawalsRecordDto.setApplyDate(new Date());
			drawalsRecordService.insert(drawalsRecordDto);
			result.setStatus(true);
		}catch (Exception e) {
			logger.error("微信后台：更新提现信息失败!", e);
			result.setStatus(false);
			result.setCode(ExceptionEnum.business_logic_exception.getIndex());
			result.setMsg(ExceptionEnum.business_logic_exception.getValue());
		}
		return result;
	}
	/**
	 * 查询用户已保存的银行卡信息
	 * @return
	 */
	@RequestMapping("/querySaveDra")
	@ResponseBody
	public ReturnMsg<List<DrawalsRecordDto>> querySaveDra(){
		ReturnMsg<List<DrawalsRecordDto>> result=new ReturnMsg<List<DrawalsRecordDto>>();
		try{
			//String memberId="test6";
			String memberId=(String) UserSession.getSession(WechatConstant.USER_ID);
			List<DrawalsRecordDto> list=drawalsRecordService.querySaveDra(memberId,"1");
			result.setStatus(true);
			result.setData(list);
		}catch (Exception e) {
			logger.error("微信后台：查询用户已保存的银行卡信息失败!", e);
			result.setStatus(false);
			result.setCode(ExceptionEnum.business_logic_exception.getIndex());
			result.setMsg(ExceptionEnum.business_logic_exception.getValue());
		}
		return result;
	}
	/**
	 * 更新默认的银行卡信息
	 * @return
	 */
	@RequestMapping("/updateSaveDra")
	@ResponseBody
	public ReturnMsg<Object> updateSaveDra(String cardNo,String oldCardNo){
		ReturnMsg<Object> result=new ReturnMsg<Object>();
		try{
			if (StringUtils.isEmpty(cardNo) || StringUtils.isEmpty(oldCardNo)) {
				result.setStatus(false);
				result.setCode(ExceptionEnum.param_not_null.getIndex());
				result.setMsg(ExceptionEnum.param_not_null.getValue());
				return result;
			}
			String memberId=(String) UserSession.getSession(WechatConstant.USER_ID);
			//1.将之前默认的改为2 2.将选中的改为默认
			drawalsRecordService.update(memberId,"2", oldCardNo);
			drawalsRecordService.update(memberId, "1", cardNo);
			result.setStatus(true);
		}catch (Exception e) {
			logger.error("微信后台：更新默认的银行卡信息失败!", e);
			result.setStatus(false);
			result.setCode(ExceptionEnum.business_logic_exception.getIndex());
			result.setMsg(ExceptionEnum.business_logic_exception.getValue());
		}
		return result;
	}
}
