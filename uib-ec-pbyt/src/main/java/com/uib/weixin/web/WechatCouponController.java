package com.uib.weixin.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.easypay.common.utils.JacksonUtil;
import com.uib.common.enums.ExceptionEnum;
import com.uib.common.enums.IsUsedStates;
import com.uib.common.utils.StringUtils;
import com.uib.member.entity.Coupon;
import com.uib.member.entity.CouponCode;
import com.uib.member.entity.MemMember;
import com.uib.member.service.MemMemberService;
import com.uib.mobile.dto.CouponDto;
import com.uib.mobile.dto.ReturnMsg;
import com.uib.serviceUtils.Utils;
import com.uib.weixin.constant.WxConstant;
import com.uib.weixin.util.UserSession;
/**
 * 优惠券
 * @author kevin
 *
 */
@Controller
@RequestMapping("/wechat/member/coupon")
public class WechatCouponController {
	private Logger logger = LoggerFactory.getLogger("rootLogger");
	
	@Autowired
	private MemMemberService memMemberService;
	
	
	@RequestMapping("/myCoupon")
	@ResponseBody
	public ReturnMsg<List<CouponDto>> myCoupon(Integer states,HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap){	
		ReturnMsg<List<CouponDto>> returnMsg = new ReturnMsg<List<CouponDto>>();
		try {
			logger.info("=======begin 手机APP调用myCoupon方法接口====================");
			logger.info("=======begin 手机微信调用complainRecords方法接口====================");
			MemMember memMerber = null;
			//获取当前用户userName
			String userName = (String)UserSession.getSession(WxConstant.wx_user_name);
			if(StringUtils.isNotEmpty(userName)) {
				logger.info("code=======:"+userName);
				memMerber = memMemberService.getMemMemberByUsername(userName);
			}
			if (null == memMerber) {
				logger.info("该会员不存在:" + userName);
				returnMsg.setMsg(ExceptionEnum.member_not_exist.getValue() + ":" + userName);
				returnMsg.setCode(ExceptionEnum.member_not_exist.getIndex());
				returnMsg.setStatus(false);
				return returnMsg;
			}
			if(Utils.isBlank(states)){
				states = 0;
			}
			//1.查询当前用户所有的优惠券
			List<CouponCode> listCouponCode  = memMemberService.getCouponByMemberId(memMerber.getId());
			List<CouponDto> listCoupon = new ArrayList<CouponDto>();
			for (CouponCode couponCode : listCouponCode) {
				Coupon coupon = memMemberService.getCouponByCouponId(couponCode.getCoupon().getId());
				CouponDto couponDto = new CouponDto();
				BeanUtils.copyProperties(couponDto, coupon);
				couponDto.setCouponCode(couponCode.getCode());
				couponDto.setIsUsed(couponCode.getIsUsed());
				boolean endDateEnd = coupon.getEndDate()!= null && new Date().after(coupon.getEndDate());
//				System.out.println(couponCode.getIsUsed()+"====="+endDateEnd+"====="+coupon.getNeedConsumeBalance());
				if(states ==1){
					if(IsUsedStates.Used.getIndex() == couponCode.getIsUsed() || IsUsedStates.Lock.getIndex() == couponCode.getIsUsed()){
						listCoupon.add(couponDto);
					}
				}else if(states ==2){//已过期（未使用）
					if(IsUsedStates.Unused.getIndex() == couponCode.getIsUsed() && endDateEnd){
						listCoupon.add(couponDto);
					}
				}else if(states ==0){//未使用
					if(IsUsedStates.Unused.getIndex() == couponCode.getIsUsed() && !endDateEnd){
						listCoupon.add(couponDto);
					}
				}
				
			}
			System.out.println(listCoupon.size());
			//2.保存数据
			returnMsg.setData(listCoupon);
		} catch (Exception e) {
			returnMsg.setMsg(ExceptionEnum.business_logic_exception.getValue() + e.getMessage());
			returnMsg.setCode(ExceptionEnum.business_logic_exception.getIndex());
			returnMsg.setStatus(false);
			logger.error("查询优惠券报错！",e);
		}
	//	logger.info("返回参数:" + JacksonUtil.writeValueAsString(returnMsg));
		logger.info("=======end myCoupon 手机APP调用myCoupon方法接口====================");
		return returnMsg;
	}
	
	@RequestMapping("/ifUsedCoupon")
	@ResponseBody
	public ReturnMsg<List<CouponDto>> ifUsedCoupon(Integer selectFlag,String sessionId,BigDecimal orderAmount){		
		ReturnMsg<List<CouponDto>> returnMsg = new ReturnMsg<List<CouponDto>>();
		try {
			logger.info("=======begin 手机APP调用ifUsedCoupon方法接口====================");
			logger.info("=======传参(会员名) sessionId=" + sessionId +"(查询标识)selectFlag=" + selectFlag+ "=============");
			MemMember member = memMemberService.getMemMemberBySessionId(sessionId);
		
			if (null == orderAmount) {
				logger.info("该订单金额不存在:" + orderAmount);
				returnMsg.setMsg(ExceptionEnum.orderAmount_not_exist.getValue() + ":"
						+ orderAmount);
				returnMsg.setCode(ExceptionEnum.orderAmount_not_exist.getIndex());
				returnMsg.setStatus(false);
				return returnMsg;
			}
			if(Utils.isBlank(selectFlag)){
				selectFlag = 1;
			}
			//1.查询当前用户所有的优惠券
			List<CouponCode> listCouponCode  = memMemberService.getCouponByMemberId(member.getId());
			List<CouponDto> listCouponUsed = new ArrayList<CouponDto>();
			for (CouponCode couponCode : listCouponCode) {
				Coupon coupon = memMemberService.getCouponByCouponId(couponCode.getCoupon().getId());
				CouponDto couponDto = new CouponDto();
				BeanUtils.copyProperties(couponDto, coupon);
				couponDto.setCouponCode(couponCode.getCode());
				couponDto.setIsUsed(couponCode.getIsUsed());
				int is_orderAmount = orderAmount.compareTo(coupon.getNeedConsumeBalance());
				boolean endDateEnd = coupon.getEndDate()!= null && new Date().after(coupon.getEndDate());
//				System.out.println(couponCode.getIsUsed()+"====="+endDateEnd+"====="+coupon.getNeedConsumeBalance());
				if(selectFlag == 1){
					if((is_orderAmount==0 || is_orderAmount==1) && couponCode.getIsUsed()==IsUsedStates.Unused.getIndex() && !endDateEnd){
						listCouponUsed.add(couponDto);
					}
				}
				if(selectFlag == 2){
					if(is_orderAmount==-1 || couponCode.getIsUsed()==IsUsedStates.Used.getIndex() || endDateEnd){
						listCouponUsed.add(couponDto);
					}
				}
			}
			System.out.println(listCouponUsed.size()+"-------------");
			returnMsg.setData(listCouponUsed);
		} catch (Exception e) {
			returnMsg.setMsg(ExceptionEnum.business_logic_exception.getValue() + e.getMessage());
			returnMsg.setCode(ExceptionEnum.business_logic_exception.getIndex());
			returnMsg.setStatus(false);
			logger.error("查询可用优惠券报错！",e);
		}
		logger.info("返回参数:" + JacksonUtil.writeValueAsString(returnMsg));
		logger.info("=======end ifUsedCoupon 手机APP调用ifUsedCoupon方法接口====================");
		return returnMsg;
	}
		
	@RequestMapping("/queryGetCoupon")
	@ResponseBody
	public ReturnMsg<Object> queryGetCoupon(String page){	
		ReturnMsg<Object> returnMsg = new ReturnMsg<Object>();
		List<Coupon> list=new ArrayList<Coupon>();
		try {
			//获取当前用户id
			String memberId = (String)UserSession.getSession(WxConstant.wx_user_id);
			if(memberId==null){
				memberId="";
			}
			list=memMemberService.queryGetCoupon(memberId, page);
			returnMsg.setStatus(true);
			returnMsg.setData(list);
		} catch (Exception e) {
			returnMsg.setMsg(ExceptionEnum.system_error.getValue() + e.getMessage());
			returnMsg.setCode(ExceptionEnum.system_error.getIndex());
			returnMsg.setStatus(false);
			logger.error("领取和未领取的优惠劵报错！",e);
		}
		return returnMsg;
	}
}
