package com.uib.mobile.web;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.uib.common.enums.ExceptionEnum;
import com.uib.common.enums.IsUsedStates;
import com.uib.common.utils.JacksonUtil;
import com.uib.member.entity.Coupon;
import com.uib.member.entity.CouponCode;
import com.uib.member.entity.MemMember;
import com.uib.member.service.MemMemberService;
import com.uib.mobile.dto.CouponDto;
import com.uib.mobile.dto.ReturnMsg;
import com.uib.serviceUtils.Utils;

/**
 * 优惠券
 * @author kevin
 *
 */
@Controller
@RequestMapping("/mobile/member/coupon")
public class MobileCouponController {
	private Logger logger = LoggerFactory.getLogger("rootLogger");
	
	@Autowired
	private MemMemberService memMemberService;
	
	/***
	 * 查询我的优惠券列表
	 * @param states 0：未使用，1：已使用，2：已过期
	 * @param sessionId
	 * @return
	 */
	@RequestMapping("/myCoupon")
	@ResponseBody
	public ReturnMsg<List<CouponDto>> myCoupon(Integer states, String sessionId){		
		ReturnMsg<List<CouponDto>> returnMsg = new ReturnMsg<List<CouponDto>>();
		try {
			logger.info("=======begin 手机APP调用myCoupon方法接口====================");
			logger.info("=======传参(会员名) sessionId=" + sessionId +"(状态)states=" + states+ "=============");
			MemMember member = memMemberService.getMemMemberBySessionId(sessionId);
			//检查会员是否存在
			if (Utils.isBlank(member)) {
				returnMsg.setCode(ExceptionEnum.member_not_exist.getIndex());
				returnMsg.setMsg(ExceptionEnum.member_not_exist.getValue());
				returnMsg.setStatus(false);
				return returnMsg;
			}
			if(Utils.isBlank(states)){
				states = 0;
			}
			//1.查询当前用户所有的优惠券
			List<CouponCode> listCouponCode  = memMemberService.getCouponByMemberId(member.getId());
			List<CouponDto> listCoupon = new ArrayList<CouponDto>();
			for (CouponCode couponCode : listCouponCode) {
				Coupon coupon = memMemberService.getCouponByCouponId(couponCode.getCoupon().getId());
				CouponDto couponDto = new CouponDto();
				BeanUtils.copyProperties(couponDto, coupon);
				couponDto.setCouponCode(couponCode.getCode());
				couponDto.setIsUsed(couponCode.getIsUsed());
				boolean endDateEnd = coupon.getEndDate()!= null && new Date().after(coupon.getEndDate());
				
				if(states ==0){
					//未使用，且未过期
					if(IsUsedStates.Unused.getIndex() == couponCode.getIsUsed() && !endDateEnd){
						listCoupon.add(couponDto);
					}
				}else if(states ==1){
					//已使用
					if(IsUsedStates.Used.getIndex() == couponCode.getIsUsed() || IsUsedStates.Lock.getIndex() == couponCode.getIsUsed()){
						listCoupon.add(couponDto);
					}
				}else if(states ==2){
					//已过期（未使用）
					if(IsUsedStates.Unused.getIndex() == couponCode.getIsUsed() && endDateEnd){
						listCoupon.add(couponDto);
					}
				}
			}
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
	
	public static void main(String[] args) throws ParseException {
		String beginTimeStr = "2016-01-20 12:00:00";
		
		Date beginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(beginTimeStr);
		System.out.println(beginTime.after(new Date()));
		System.out.println(new Date().after(beginTime));
	}
		
}
