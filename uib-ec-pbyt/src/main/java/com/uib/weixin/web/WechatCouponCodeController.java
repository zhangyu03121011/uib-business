package com.uib.weixin.web;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.easypay.common.utils.UUIDGenerator;
import com.uib.common.enums.ExceptionEnum;
import com.uib.member.entity.Coupon;
import com.uib.member.entity.CouponCode;
import com.uib.member.entity.MemMember;
import com.uib.member.service.MemMemberService;
import com.uib.mobile.dto.ReturnMsg;
import com.uib.serviceUtils.Utils;
import com.uib.weixin.constant.WxConstant;
import com.uib.weixin.util.UserSession;

/**
 * 优惠券码表
 * @author kevin
 *
 */
@Controller
@RequestMapping("/wechat/member/couponCode")
public class WechatCouponCodeController {
	private Logger logger = LoggerFactory.getLogger("rootLogger");
	
	@Autowired
	private MemMemberService memMemberService;
	
	@RequestMapping("/insert")
	@ResponseBody
	public ReturnMsg<Object> insert(String couponId){	
		ReturnMsg<Object> returnMsg = new ReturnMsg<Object>();
		try {
			if(Utils.isBlank(couponId)){
				returnMsg.setMsg(ExceptionEnum.param_not_null.getValue());
				returnMsg.setCode(ExceptionEnum.param_not_null.getIndex());
				returnMsg.setStatus(false);
				return returnMsg;
			}
			CouponCode couponCode=new CouponCode();
			couponCode.setId(UUIDGenerator.getUUID());
			Coupon coupon=new Coupon();
			coupon.setId(couponId);
			couponCode.setCoupon(coupon);
			//获取当前用户id
			String memberId = (String)UserSession.getSession(WxConstant.wx_user_id);
			MemMember memMember=new MemMember();
			memMember.setId(memberId);
			couponCode.setMemMember(memMember);
			couponCode.setCreateDate(new Date());
			couponCode.setUpdateDate(new Date());
			couponCode.setDelFlag("0");
			memMemberService.insert(couponCode);
			returnMsg.setStatus(true);
		} catch (Exception e) {
			returnMsg.setMsg(ExceptionEnum.system_error.getValue() + e.getMessage());
			returnMsg.setCode(ExceptionEnum.system_error.getIndex());
			returnMsg.setStatus(false);
			logger.error("插入优惠劵码表报错！",e);
		}
		return returnMsg;
	}
}
