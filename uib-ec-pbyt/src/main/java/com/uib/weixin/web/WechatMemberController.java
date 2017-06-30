package com.uib.weixin.web;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.RequestContext;

import com.easypay.common.utils.DateUtil;
import com.easypay.common.utils.DigestUtil;
import com.easypay.common.utils.JacksonUtil;
import com.easypay.common.utils.RandomUtil;
import com.easypay.common.utils.UUIDGenerator;
import com.uib.common.enums.ExceptionEnum;
import com.uib.common.enums.Payment_Method;
import com.uib.common.enums.Payment_Status;
import com.uib.common.enums.Payment_Type;
import com.uib.common.enums.WXBankTypeEnum;
import com.uib.common.mapper.JsonMapper;
import com.uib.common.utils.Des;
import com.uib.common.utils.StringUtils;
import com.uib.member.dto.MemberDto;
import com.uib.member.dto.ValidateCodeInfo;
import com.uib.member.entity.MemMember;
import com.uib.member.service.MemMemberService;
import com.uib.mobile.constants.Constants;
import com.uib.mobile.dto.ComplaintApplicationDto;
import com.uib.mobile.dto.MemberIdCartDto;
import com.uib.mobile.dto.ReturnMsg;
import com.uib.mobile.dto.TbAttachment;
import com.uib.mobile.dto.UserComplaintAttrMap;
import com.uib.mobile.service.ComplainApplicationService;
import com.uib.mobile.service.TbAttachmentService;
import com.uib.mobile.service.UserComplaintAttrMapService;
import com.uib.order.entity.BalancePayLog;
import com.uib.order.entity.OrderTable;
import com.uib.order.service.BalancePayLogService;
import com.uib.order.service.OrderService;
import com.uib.pay.dto.WeixinPayResDto;
import com.uib.pay.service.PayService;
import com.uib.ptyt.config.entity.WechatConfig;
import com.uib.serviceUtils.Utils;
import com.uib.weixin.constant.WxConstant;
import com.uib.weixin.dto.WechatPayConfig;
import com.uib.weixin.util.MD5Util;
import com.uib.weixin.util.MessageUtil;
import com.uib.weixin.util.UserSession;
import com.uib.weixin.util.WeixinUtil;
import com.uib.weixin.util.WxPhoneMessageUtil;

@Controller
@RequestMapping("/wechat/member/user")
public class WechatMemberController {

	private Logger logger = LoggerFactory.getLogger(WechatMemberController.class);

	@Autowired
	private MemMemberService memMemberService;
	
	@Autowired
	private ComplainApplicationService complainApplicationservice;
	
	@Autowired
	private UserComplaintAttrMapService userComplaintAttrMapService;
	
	@Autowired
	private TbAttachmentService tbAttachmentService;
	
	@Autowired
	private PayService payService;
	
	@Autowired
	private OrderService orderService;

	@Value("${upload.image.path}")
	private String UPLOAD_IMAGE_PATH;
	
	@Autowired
	private BalancePayLogService balancePayLogService;

	@RequestMapping("/getMemberByUserName")
	@ResponseBody
	public MemMember getMemMemberByUserName(HttpServletRequest request,HttpServletResponse response) {
		MemMember member = null;
		try {
			String userName = (String) UserSession.getSession(WxConstant.wx_user_name);
			if(StringUtils.isNotEmpty(userName)){
				member = memMemberService.getMemMemberByUsernameNew(userName);
				String phone =(String) UserSession.getSession(WxConstant.wx_user_phone);
				member.setPhone(phone.substring(0,3) + "****" + phone.substring(7, phone.length()));
			}
		} catch (Exception e) {
			logger.error("查找会员信息异常：" + e);
		}
		return member;
	}  

	/**
	 * 修改密码
	 * 
	 * @param userName
	 * @param password
	 * @param password1
	 * @param phone
	 * @param validateCode
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/updatePassword")
	@ResponseBody
	public ReturnMsg<Object> updatePassword(String password, String password1,
			String oldPassword, String userName, HttpServletRequest request) {
		ReturnMsg<Object> returnMsg = new ReturnMsg<Object>();
		try {
			// 从后台代码获取国际化信息
			RequestContext requestContext = new RequestContext(request);
			if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)
					|| StringUtils.isEmpty(password1)
					|| StringUtils.isEmpty(oldPassword)) {
				returnMsg.setCode(ExceptionEnum.param_not_null.getIndex());
				returnMsg.setStatus(false);
				returnMsg.setMsg(ExceptionEnum.param_not_null.getValue());
				return returnMsg;
			}
			MemMember member = memMemberService
					.getMemMemberByUsername(userName);
			if (null == member) {
				returnMsg.setCode(ExceptionEnum.member_not_exist.getIndex());
				returnMsg.setStatus(false);
				returnMsg.setMsg(ExceptionEnum.member_not_exist.getValue());
				return returnMsg;
			}
			if (!DigestUtil.MD5(oldPassword).equalsIgnoreCase(member.getPassword())) {
				returnMsg.setCode(ExceptionEnum.old_password_error.getIndex());
				returnMsg.setStatus(false);
				returnMsg.setMsg(ExceptionEnum.old_password_error.getValue());
				return returnMsg;
			}
			if (!DigestUtil.MD5(password).equalsIgnoreCase(DigestUtil.MD5(password1))) {
				returnMsg.setCode(ExceptionEnum.password_different.getIndex());
				returnMsg.setStatus(false);
				returnMsg.setMsg(ExceptionEnum.password_different.getValue());
				return returnMsg;
			}

			memMemberService.updatePassword(DigestUtil.MD5(password),
					member.getUsername());
			returnMsg.setStatus(true);
			returnMsg.setMsg(requestContext
					.getMessage("user.updatepassword.ok"));
		} catch (Exception ex) {
			returnMsg.setCode(ExceptionEnum.system_error.getIndex());
			returnMsg.setStatus(false);
			returnMsg.setMsg(ExceptionEnum.system_error.getValue());
			logger.error("手机修改密码方法updatePassword出错", ex);
			return returnMsg;
		}
		return returnMsg;
	}

	/**
	 * 
	 * @Title: identityAuthentication
	 * @author sl 提交身份验证
	 * @throws IOException
	 * @throws ParseException
	 */
	@ResponseBody
	@RequestMapping(value = "/identityAuthentication", method = RequestMethod.POST)
	public ReturnMsg<String> identityAuthentication(
			@RequestParam Map<String, String> param, HttpServletRequest request){
		logger.info("提交身份验证开始weixin" + param.toString());
		ReturnMsg<String> returnMsg = new ReturnMsg<String>();
		if (Utils.isObjectsBlank(param.get("idCardPositive"),
				param.get("idCardOpposite"), param.get("idCardHand"),
				param.get("realName"), param.get("idCard"))) {
			returnMsg.setStatus(false);
			returnMsg.setCode(ExceptionEnum.param_not_null.getIndex());
			returnMsg.setCode(ExceptionEnum.param_not_null.getValue());
			logger.info("输入参数有为空的，请检查");
			return returnMsg;
		}
		try {
			MemMember member = new MemMember();
			//获取当前用户userName
			String wxUserName = (String)UserSession.getSession(WxConstant.wx_user_name);
			logger.info("通过session获取userName=" + wxUserName);
			if (StringUtils.isNotEmpty(wxUserName)) {
				member = memMemberService.getMemMemberByUsername(wxUserName);
			}
			if (null == member) {
				logger.info("该会员不存在:" + wxUserName);
				returnMsg.setMsg(ExceptionEnum.member_not_exist.getValue() + ":"
						+ wxUserName);
				returnMsg.setCode(ExceptionEnum.member_not_exist.getIndex());
				returnMsg.setStatus(false);
				return returnMsg;
			}
			param.put("id", member.getId());
			param.put("idCardPositive", formatUrl(param.get("idCardPositive")));
			param.put("idCardOpposite", formatUrl(param.get("idCardOpposite")));
			param.put("idCardHand", formatUrl(param.get("idCardHand")));
			param.put("approveFlag", "0");
			param.put("realName", param.get("realName"));
			param.put("idCard", param.get("idCard"));
			param.put("indate", param.get("indate"));
//			memMemberService.updateById(param);
			memMemberService.updateMemberInfoById(param);
			returnMsg.setMsg(new RequestContext(request)
					.getMessage("submit.succeed"));
			logger.info("身份验证返回：" + JsonMapper.toJsonString(returnMsg));
		} catch (Exception e) {
			logger.info("提交身份验证异常:" + e);
			returnMsg.setStatus(false);
			returnMsg.setCode(ExceptionEnum.business_logic_exception.getIndex());
			returnMsg.setMsg(ExceptionEnum.business_logic_exception.getValue());
		}
		return returnMsg;
	}

	public String formatUrl(String url) {
		if (url.indexOf("/upload") != -1) {
			return url.substring(url.indexOf("/upload"), url.length());
		}
		return url;
	}

	/**
	 * 从微信服务器下载,在上传到图片服务器
	 * 
	 * @param mediaId
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/uploadImgToService")
	public String uploadImgToService(String mediaId) throws Exception {
		logger.info("mediaId" + mediaId);
		String imgUrl = "";
		try {
			imgUrl = WeixinUtil.downloadFromWeChat(mediaId);
		} catch (Exception e) {
			logger.info("获取WechatConfig异常" + e.fillInStackTrace());
		}
		return imgUrl;
	}

	/**
	 * 获取微信签名
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/getWechatConfig")
	public WechatConfig getWechatConfig(String url) throws Exception {
		logger.info("获取微信签名开始……" + url);
		WechatConfig config = new WechatConfig();
		try {
			config = WeixinUtil.getWechatConfig(url);
		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.info("获取WechatConfig异常" + e.fillInStackTrace());
		}
		return config;
	}

	/**
	 * 获取微信支付签名
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/getWechatPayConfig")
	public WechatPayConfig getWechatPayConfig(String orderNo, HttpServletRequest request) throws Exception {
		logger.info("获取微信支付签名开始……orderNo:"+orderNo);
		WechatPayConfig config = new WechatPayConfig();
		try {
			String openId = (String) UserSession.getSession("wxOpenId");
			logger.info("获取微信支付……openId:"+openId);
			if (!StringUtils.isEmpty(orderNo) && !StringUtils.isEmpty(openId)) {
				OrderTable order = orderService.getOrderByOrderNo(orderNo);
				//String amount = orderByOrderNo.getAmount().toString();
				//应付款 = 订单总额 - 已付金额
				String amount = order.getAmount().subtract(new BigDecimal(order.getAmountPaid())).toString();
				logger.info("orderNo==="+orderNo);
				logger.info("openId==="+openId);
				logger.info("amount==="+amount);
				config = WeixinUtil.getWechatPayConfig(orderNo,openId,amount, request);
			}
		} catch (Exception e) {
			logger.info("获取WechatPayConfig异常" + e.fillInStackTrace());
		}
		return config;
	}

	/**
	 * 微信支付回调
	 * 
	 * @throws Exception
	 *             void
	 */
	@ResponseBody
	@RequestMapping("/payCallBack")
	public void payCallBack(HttpServletRequest request,HttpServletResponse response){
		try {
	    	logger.info("微信支付回调通知接口开始");
			InputStream inStream = request.getInputStream();
			ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = inStream.read(buffer)) != -1) {
			    outSteam.write(buffer, 0, len);
			}
			outSteam.close();
			inStream.close();
			String resultStr  = new String(outSteam.toByteArray(),"utf-8");
			Map<String, String> resultMap = MessageUtil.parseXml(resultStr);
			logger.info("微信支付异步回调返回结果"+resultStr);
			String return_code = resultMap.get("return_code");
			String result_code = resultMap.get("result_code");
			
			if("SUCCESS".equals(return_code) && "SUCCESS".equals(result_code)){
				logger.info("支付回调成功");
				//更新订单状态 支付状态 商品库存
				payService.weixinPayNotifyInfo(resultMap.get("out_trade_no"));
				//根据订单号更新商品销量
				orderService.updateProductSalesByOrderNO(resultMap.get("out_trade_no"));
				
				//组装订单支付信息
				WeixinPayResDto weixinPayResDto = new WeixinPayResDto();
				weixinPayResDto.setId(UUIDGenerator.getUUID());
				weixinPayResDto.setAmount(resultMap.get("total_fee"));
				weixinPayResDto.setAccount(resultMap.get("appid"));
				weixinPayResDto.setPayer(resultMap.get("openid"));
				weixinPayResDto.setOrderNo(resultMap.get("out_trade_no"));
				weixinPayResDto.setPaymentDate(resultMap.get("time_end"));
				weixinPayResDto.setPaymentMethod(String.valueOf(Payment_Method.JSAPI.getIndex()));
				weixinPayResDto.setType(Payment_Type.payment.getIndex());
				weixinPayResDto.setStatus(Payment_Status.success.getIndex());
				weixinPayResDto.setPaymentBank(WXBankTypeEnum.getDescription(resultMap.get("bank_type")));
				logger.info("orderNo====:"+resultMap.get("out_trade_no"));
				OrderTable orderByOrderNo = orderService.getOrderByOrderNo(resultMap.get("out_trade_no"));
				if (null != orderByOrderNo) {
					String id = orderByOrderNo.getId();
					weixinPayResDto.setOrderTableId(id);
				}
				logger.info("保存微信付款信息开始....");
				payService.savePayInfo(weixinPayResDto);
				logger.info("保存微信付款信息结束....");
				
				BalancePayLog balancePayLog = balancePayLogService.getPayLogByOrderNo(resultMap.get("out_trade_no"));
				if(balancePayLog != null){
					balancePayLog.setUpdateDate(new Date());
					balancePayLog.setStatus("1");//已支付
					balancePayLogService.updatePayLog(balancePayLog);
				}
			
				request.setAttribute("out_trade_no", resultMap.get("out_trade_no"));
				//通知微信.异步确认成功.必写.不然会一直通知后台.八次之后就认为交易失败了.
				//response.getWriter().write(MessageUtil.setXML("SUCCESS", ""));
				response.setContentType("text/html;charset=GBK");
				request.setCharacterEncoding("GBK"); 
				
				PrintWriter out= response.getWriter(); 
				out.print(MessageUtil.setXML("SUCCESS", "OK"));
				logger.info("已返回成功结果...");
	        }
		} catch (Exception e) { 
			logger.info("微信支付回调通知接口异常"+e);
			try{
				PrintWriter out= response.getWriter(); 
				out.print(MessageUtil.setXML("FAIL", "ERROR"));
			}catch(Exception e2){
				
			}
		}
    	logger.info("微信支付回调通知接口结束");
	}
	
	
	
	
	/**
	 * 重置密码
	 * @param oldPwd
	 * @param newPwd
	 * @return
	 */
	@RequestMapping("/checkOldPwd")
	@ResponseBody
	public ReturnMsg<String> checkOldPwd(String oldPwd) {
		logger.info("=========验证原密码开始weixin=======");
		logger.info("======原密码："+oldPwd+"=======");
		ReturnMsg<String> returnMsg = new ReturnMsg<String>();		
		try {
			if (Utils.isObjectsBlank(oldPwd)) {
				returnMsg.setStatus(false);
				returnMsg.setCode(ExceptionEnum.param_not_null.getIndex());
				returnMsg.setCode(ExceptionEnum.param_not_null.getValue());
				return returnMsg;
			}
			String userName = (String) UserSession.getSession(WxConstant.wx_user_name);
			if (StringUtils.isEmpty(userName)) {
				returnMsg.setStatus(false);
				returnMsg.setCode(ExceptionEnum.param_not_null.getIndex());
				returnMsg.setMsg(ExceptionEnum.param_not_null.getValue());
				return returnMsg;
			}
			//des解密
			Des desObj = new Des();
			oldPwd = desObj.strDec(oldPwd, "1", "2", "3");
			int member = memMemberService.queryMemberInfo(userName, DigestUtil.MD5(oldPwd));
			if(member<=0){
				returnMsg.setStatus(false);
				return returnMsg;
			}
			returnMsg.setStatus(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnMsg;
		
	}
	
	
	/**
	 * 重置密码
	 * @param oldPwd
	 * @param newPwd
	 * @return
	 */
	@RequestMapping("/resetPwd")
	@ResponseBody
	public ReturnMsg<String> resetPwd(String password) {
		logger.info("=========重置密码开始weixin=======");
		logger.info("======密码："+password+"=======");
		ReturnMsg<String> returnMsg = new ReturnMsg<String>();		
		try {
			if (Utils.isObjectsBlank(password)) {
				returnMsg.setStatus(false);
				returnMsg.setCode(ExceptionEnum.param_not_null.getIndex());
				returnMsg.setCode(ExceptionEnum.param_not_null.getValue());
				return returnMsg;
			}
			String userName = (String) UserSession.getSession(WxConstant.wx_user_name);
			if (StringUtils.isEmpty(userName)) {
				returnMsg.setStatus(false);
				returnMsg.setCode(ExceptionEnum.param_not_null.getIndex());
				returnMsg.setMsg(ExceptionEnum.param_not_null.getValue());
				return returnMsg;
			}
			//des解密
			Des desObj = new Des();
			password = desObj.strDec(password, "1", "2", "3");
			memMemberService.updatePassword(DigestUtil.MD5(password), userName);
			returnMsg.setStatus(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnMsg;
		
	}

	/**
	 * 根据用户名查询身份认证信息
	 * 
	 * @param userName
	 * @return
	 */
	@RequestMapping("/getApproveByUserName")
	@ResponseBody
	public ReturnMsg<MemberIdCartDto> getApproveByUserName() {
		ReturnMsg<MemberIdCartDto> returnMsg = new ReturnMsg<MemberIdCartDto>();
		String userName = (String) UserSession.getSession(WxConstant.wx_user_name);
		try {
			logger.info("============ begin 获取身分验证信息接口 getApproveByUserName=================");
			logger.info("获取身分验证信息接口 getApproveByUserName--userName参数= "
					+ userName);
			if (StringUtils.isEmpty(userName)) {
				returnMsg.setStatus(false);
				returnMsg.setCode(ExceptionEnum.param_not_null.getIndex());
				returnMsg.setMsg(ExceptionEnum.param_not_null.getValue());
				return returnMsg;
			}
			MemMember member = memMemberService.getApproveByUserName(userName);
			if (null == member) {
				returnMsg.setStatus(false);
				returnMsg.setCode(ExceptionEnum.member_exist.getIndex());
				returnMsg.setMsg(ExceptionEnum.member_exist.getValue());
				return returnMsg;
			}
			MemberIdCartDto memberIdCartDto = new MemberIdCartDto();
			memberIdCartDto.setIdCard(member.getIdCard());
			memberIdCartDto.setApproveFlag(member.getApproveFlag());
			memberIdCartDto.setAuditFailureDescription(member
					.getAuditFailureDescription());
			memberIdCartDto.setIdCardPositive(UPLOAD_IMAGE_PATH
					+ member.getIdCardPositive());
			memberIdCartDto.setIdCardOpposite(UPLOAD_IMAGE_PATH
					+ member.getIdCardOpposite());
			memberIdCartDto.setIdCardHand(UPLOAD_IMAGE_PATH
					+ member.getIdCardHand());

			memberIdCartDto.setUserName(member.getUsername());
			memberIdCartDto.setName(member.getName());
			memberIdCartDto.setId(member.getId());
			memberIdCartDto.setApproveDate(member.getApproveDate());
			memberIdCartDto.setIdCardValid(member.getIdCardValid());
			returnMsg.setStatus(true);
			returnMsg.setData(memberIdCartDto);
			logger.info("============ end 获取身分验证信息接口 getApproveByUserName=================");
		} catch (Exception ex) {
			returnMsg.setCode(ExceptionEnum.system_error.getIndex());
			returnMsg.setStatus(false);
			returnMsg.setMsg(ExceptionEnum.system_error.getValue());
			logger.error("手机查看身份认证方法getApproveByUserName出错", ex);
			return returnMsg;
		}
		return returnMsg;
	}

	@RequestMapping("/getMember")
	@ResponseBody
	public ReturnMsg<String> getMember() {
		//UserSession.setSession(WxConstant.wx_user_name, "oawtet5Dqn_mEoBzLNkYyk1C57qw1788");
		String userName = (String) UserSession.getSession(WxConstant.wx_user_name);
		logger.info("============ begin 获取用户信息接口 getMember=================");
		logger.info("调用发送验证码接口 getMember , userName = " + userName);
     
		ReturnMsg<String> returnMsg = new ReturnMsg<String>();

		if (StringUtils.isBlank(userName)) {
			returnMsg.setStatus(false);
			return returnMsg;
		} else {
			MemMember member = memMemberService
					.getMemMemberByUsername(userName);
			if (member == null) {
				returnMsg.setStatus(false);
			} else {
				returnMsg.setStatus(true);
				returnMsg.setMsg(member.getPhone());
			}
			returnMsg.setData(member == null ? getWxName() : member
					.getWeixinName());
		}
		logger.info("============ end 获取用户信息接口 getMember=================userName="
				+ userName);
		return returnMsg;
	}

	/***
	 * 验证手机号是否已被绑定
	 * 
	 * @param phone
	 * @param request
	 * @return
	 */
	@RequestMapping("/checkPhone")
	@ResponseBody
	public String checkPhone(String phone, HttpServletRequest request) {
		logger.info("============ begin 验证手机号是否已被绑定接口 checkPhone=================");
		logger.info("调用验证手机号是否已被绑定接口 checkPhone--phone参数= " + phone);

		String flag = "false";

		try {
			MemMember member = memMemberService.getMemberByPhone(phone);
			if (member != null) {
				flag = "true";
			}
			logger.info("============ end 调用验证手机号是否已被绑定接口 checkPhone=================");
		} catch (Exception e) {
			logger.error(
					"============ end 调用验证手机号是否已被绑定接口出错 checkPhone=================",
					e);
			e.printStackTrace();
			flag = "false";
		}
		return flag;
	}

//	/***
//	 * 发送验证码
//	 * 
//	 * @param phone
//	 * @param request
//	 * @return
//	 */
//	@RequestMapping("/validateCode")
//	@ResponseBody
//	public String validateCode(String phone, HttpServletRequest request) {
//		logger.info("============ begin 发送验证码接口 validateCode=================");
//		logger.info("调用发送验证码接口 validateCode--phone参数= " + phone);
//
//		String flag = "false";
//		String validateCode = RandomUtil.getRandom(6);
//		try {
//			boolean tempflag = memMemberService.sendValidateCode(phone, null,
//					validateCode);
//			if (tempflag) {
//				flag = "true";
//				
//				ValidateCodeInfo validateCodeInfo = new ValidateCodeInfo();
//				validateCodeInfo.setCreateDate(DateUtil.dateToDateString(new Date()));
//				validateCodeInfo.setValidateCode(validateCode);
//				Constants.codeMap.put(phone, validateCodeInfo);
//			} else {
//				flag = "false";
//			}
//			logger.info("============ end 调用发送验证码接口 validateCode=================");
//		} catch (Exception e) {
//			logger.error(
//					"============ end 调用发送验证码接口出错 validateCode=================",
//					e);
//			e.printStackTrace();
//			flag = "false";
//		}
//		return flag;
//	}

	/***
	 * 绑定手机号
	 * 
	 * @param phone
	 * @param code
	 */
	@RequestMapping("/bindPhone")
	@ResponseBody
	public ReturnMsg<MemberDto> bindPhone(String phone, String code,
			String userName, String wxName, HttpServletRequest request) {
		logger.info("============ begin 调用绑定手机号接口 bindPhone=================");
		logger.info("调用绑定手机号接口 bindPhone--phone参数= " + phone + ", code = "
				+ code);
		ReturnMsg<MemberDto> returnMsg = new ReturnMsg<MemberDto>();
		
		ValidateCodeInfo codeInfo = Constants.codeMap.get(phone);
		
		if (null == codeInfo || null == codeInfo.getValidateCode() || null == code) {
			returnMsg.setCode(ExceptionEnum.validate_code_error.getIndex());
			returnMsg.setStatus(false);
			returnMsg.setMsg(ExceptionEnum.validate_code_error.getValue());
			return returnMsg;
		}
		
		long time = DateUtil.compareDateStr(codeInfo.getCreateDate(), DateUtil.dateToDateString(new Date()));
		if (time > 120000) {
			// 验证码超时请重新获取
			returnMsg.setCode(ExceptionEnum.validate_code_timeout.getIndex());
			returnMsg.setStatus(false);
			returnMsg.setMsg(ExceptionEnum.validate_code_timeout.getValue());
			return returnMsg;
		}
		if (!codeInfo.getValidateCode().equals(code)) {
			returnMsg.setCode(ExceptionEnum.validate_code_error.getIndex());
			returnMsg.setStatus(false);
			returnMsg.setMsg(ExceptionEnum.validate_code_error.getValue());
			return returnMsg;
		}
		if (StringUtils.isBlank(userName)) {
			userName = String.valueOf(UserSession.getSession(WxConstant.wx_user_name));
		}

		MemberDto memberDto = new MemberDto();
		memberDto.setWeixinName(wxName);
		memberDto.setUserName(userName);
		memberDto.setPassword("123456");
		memberDto.setPhone(phone);

		try {
			memMemberService.saveMember(memberDto);

			MemberDto merberDto = new MemberDto();
			merberDto.setUserName(userName);

			returnMsg.setStatus(true);
			returnMsg.setData(merberDto);

			logger.info("============ end 调用绑定手机号接口 bindPhone =================");
		} catch (Exception e) {
			logger.error(
					"============ end 调用绑定手机号接口出错 bindPhone =================",
					e);

			e.printStackTrace();
			returnMsg.setCode(ExceptionEnum.system_error.getIndex());
			returnMsg.setStatus(false);
			returnMsg.setMsg(ExceptionEnum.system_error.getValue());
		}
		return returnMsg;
	}
	
	
	

	/**
	 * 投诉申请
	 * 
	 * @param sessionId
	 * @param feedbackType
	 * @param describe
	 * @param images
	 * @return
	 */
	@RequestMapping("/complaintApplication")
	@ResponseBody
	public ReturnMsg<Object> complaintApplication(HttpServletRequest request, ComplaintApplicationDto complaintApplicationDto) {
		ReturnMsg<Object> returnMsg = new ReturnMsg<Object>();
		try {
			logger.info("============ begin 投诉申请接口complaintApplication=================");
			logger.info("============ complaintApplication--FeedbackType参数= "+ complaintApplicationDto.getFeedbackType() );
			logger.info("============ complaintApplication--DescribeInfo参数= "+ complaintApplicationDto.getDescribeInfo() );
			logger.info("============ complaintApplication--title参数= "+ complaintApplicationDto.getTitle() );
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
			Timestamp d = new Timestamp(System.currentTimeMillis());
			String complaintId=UUIDGenerator.getUUID();
			complaintApplicationDto.setId(complaintId);
			complaintApplicationDto.setMemberId(memMerber.getId());
			complaintApplicationDto.setCreateTime(d);
			complaintApplicationDto.setModifyDate(d);
			complaintApplicationDto.setDelFlag("0");	
			complainApplicationservice.save(complaintApplicationDto);	
			
			List<TbAttachment> tbAttachmentList = complaintApplicationDto.getTbAttachment();	
			
			String filePath0= request.getParameter("tbAttachment.filePath0");
			String filePath1= request.getParameter("tbAttachment.filePath1");
			String filePath2= request.getParameter("tbAttachment.filePath2");
			logger.info("============ complaintApplication--filePath0参数= "+ filePath0 );
			logger.info("============ complaintApplication--filePath1参数= "+ filePath1 );
			logger.info("============ complaintApplication--filePath2参数= "+filePath2 );
			if(null!=filePath0 && !("").equals(filePath0)){
				String attachmentId0 = UUIDGenerator.getUUID();
				String fileName = filePath0.substring(filePath0.lastIndexOf("/")+1, filePath0.lastIndexOf("."));
				String filePath = filePath0;
				this.addTbAttachment(attachmentId0, complaintId, fileName, filePath, tbAttachmentList);					
			}
			if(null!=filePath1 && !("").equals(filePath1)){
				String attachmentId0 = UUIDGenerator.getUUID();
				String fileName = filePath1.substring(filePath1.lastIndexOf("/")+1, filePath1.lastIndexOf("."));
				String filePath = filePath1;
				this.addTbAttachment(attachmentId0, complaintId, fileName, filePath, tbAttachmentList);	
			}
			if(null!=filePath2 && !("").equals(filePath2)){
				String attachmentId0 = UUIDGenerator.getUUID();
				String fileName = filePath2.substring(filePath2.lastIndexOf("/")+1, filePath2.lastIndexOf("."));
				String filePath = filePath2;
				this.addTbAttachment(attachmentId0, complaintId, fileName, filePath, tbAttachmentList);		
			}		
			returnMsg.setStatus(true);
		} catch (Exception ex) {
			returnMsg.setCode(ExceptionEnum.system_error.getIndex());
			returnMsg.setStatus(false);
			returnMsg.setMsg(ExceptionEnum.system_error.getValue());
			logger.error("添加投诉申请异常", ex);
			return returnMsg;
		}
		logger.info("=======end save 手机APP调用complaintApplication方法接口====================");
		return returnMsg;

	}
	
	
	private void addTbAttachment(String id,String complaintId,String fileName,String filePath,List<TbAttachment> tbAttachmentList){
		try {
			TbAttachment tbAttachment =new TbAttachment();
			tbAttachment.setId(id);
			tbAttachment.setFileName(fileName);
			tbAttachment.setFilePath(filePath);
			tbAttachmentService.save(tbAttachment);		
			tbAttachmentList.add(tbAttachment);
	
			UserComplaintAttrMap userComplaintAttrMap =new UserComplaintAttrMap();
			userComplaintAttrMap.setId(UUIDGenerator.getUUID());
			userComplaintAttrMap.setComplaintId(complaintId);
			userComplaintAttrMap.setAttachmentId(id);
			userComplaintAttrMapService.save(userComplaintAttrMap);	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * 分页 查询投诉记录
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping("/findcomplainRecordsByPage")
	public ReturnMsg<List<ComplaintApplicationDto>> findcomplainRecordsByPage(HttpServletRequest request,HttpServletResponse response,String page){
		//String page = request.getParameter("page");
	   	logger.info("微信后台：分页查询商品入参page=" + page);
		ReturnMsg<List<ComplaintApplicationDto>> result = new ReturnMsg<List<ComplaintApplicationDto>>();
		try {
//		if (StringUtils.isEmpty(page)) {
//			result.setStatus(false);
//			result.setCode(ExceptionEnum.param_not_null.getIndex());
//			result.setMsg(ExceptionEnum.param_not_null.getValue());
//			return result;
//		}
		MemMember memMerber = null;
		//获取当前用户userName
		String userName = (String)UserSession.getSession(WxConstant.wx_user_name);
		if(StringUtils.isNotEmpty(userName)) {
			logger.info("code=======:"+userName);
			memMerber = memMemberService.getMemMemberByUsername(userName);
		}
		if (null == memMerber) {
			logger.info("该会员不存在:" + userName);
			result.setMsg(ExceptionEnum.member_not_exist.getValue() + ":" + userName);
			result.setCode(ExceptionEnum.member_not_exist.getIndex());
			result.setStatus(false);
			return result;
		}
		//查询当前用户的投诉记录
		List<ComplaintApplicationDto> list = complainApplicationservice.findcomplainRecords(memMerber.getId());
		//List<ComplaintApplicationDto> ComplaintApplicationDtoList = new ArrayList<ComplaintApplicationDto>();
		if(list.size()==0){
			result.setCode(ExceptionEnum.list_complaint_null.getIndex());
			result.setMsg(ExceptionEnum.list_complaint_null.getValue());
			result.setData(null);
			result.setStatus(false);
			return result;
		}
		for(ComplaintApplicationDto complaintApplication :list){	
			if(null!=complaintApplication.getCreateTime()){
				complaintApplication.setCreateTimeStr(new SimpleDateFormat("yyyy-MM-dd").format(complaintApplication.getCreateTime()));
			}
			if(null!=complaintApplication.getSolutionState()){
				complaintApplication.setSolutionTimeStr(new SimpleDateFormat("yyyy-MM-dd").format(complaintApplication.getSolutionTime()));
			}				
		}
		result.setData(list);
		} catch (Exception e) {
			logger.error("微信后台：分页查询商品失败!", e);
			result.setCode(ExceptionEnum.business_logic_exception.getIndex());
			result.setMsg(ExceptionEnum.business_logic_exception.getValue());
		}
		return result;
	}
	
	/**
	 * 查询投诉记录
	 * @param sessionId
	 * @return
	 */
	@RequestMapping("/complainRecords")
	@ResponseBody
	public ReturnMsg<List<ComplaintApplicationDto>> complainRecords(HttpServletRequest request){
		ReturnMsg<List<ComplaintApplicationDto>> returnMsg = new ReturnMsg<List<ComplaintApplicationDto>>();
		try {
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
			//查询当前用户的投诉记录
			List<ComplaintApplicationDto> list = complainApplicationservice.complainRecords(memMerber.getId());
			List<ComplaintApplicationDto> ComplaintApplicationDtoList = new ArrayList<ComplaintApplicationDto>();
			if(list.size()==0){
				returnMsg.setCode(ExceptionEnum.list_complaint_null.getIndex());
				returnMsg.setMsg(ExceptionEnum.list_complaint_null.getValue());
				returnMsg.setData(null);
				returnMsg.setStatus(false);
				return returnMsg;
			}
			for(ComplaintApplicationDto complaintapplication:list){
				ComplaintApplicationDto complaintApplicationDto = new ComplaintApplicationDto();
				if(null!=complaintapplication.getCreateTime()){
					String createTimeStr= DateFormatUtils.format(complaintapplication.getCreateTime(), "yyyy-MM-dd");
					complaintapplication.setCreateTimeStr(createTimeStr);
				}
				if(null!=complaintapplication.getSolutionTime()){
					String solutionTimeStr= DateFormatUtils.format(complaintapplication.getSolutionTime(), "yyyy-MM-dd");
					complaintapplication.setSolutionTimeStr(solutionTimeStr);
				}	
				BeanUtils.copyProperties(complaintapplication, complaintApplicationDto);
				ComplaintApplicationDtoList.add(complaintApplicationDto);
			}
			returnMsg.setData(ComplaintApplicationDtoList);
			
		} catch (Exception e) {
			returnMsg.setMsg(ExceptionEnum.business_logic_exception.getValue() + e.getMessage());
			returnMsg.setCode(ExceptionEnum.business_logic_exception.getIndex());
			returnMsg.setStatus(false);
			logger.error("查询投诉记录报错！", e);
		}
		logger.info("返回参数:" + JacksonUtil.writeValueAsString(returnMsg));
		logger.info("=======end complainRecords 手机APP调用complainRecords方法接口====================");
		return returnMsg;
		
	}
	/**
	 * 查询一个投诉记录
	 * @param request
	 * @param sessionId
	 * @param id
	 * @return
	 */
	@RequestMapping("/queryOneComplaintApplication")
	@ResponseBody
	public ReturnMsg<ComplaintApplicationDto> queryOneComplaintApplication(HttpServletRequest request, String id){
		ReturnMsg<ComplaintApplicationDto> returnMsg = new ReturnMsg<ComplaintApplicationDto>();
		try {
			logger.info("=======begin 手机微信调用queryOneComplaintApplication方法接口====================");
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
			ComplaintApplicationDto complaintApplicationDto = complainApplicationservice.getComplaintApplicationById(id);
			returnMsg.setData(complaintApplicationDto);
		} catch (Exception e) {
			returnMsg.setMsg(ExceptionEnum.business_logic_exception.getValue() + e.getMessage());
			returnMsg.setCode(ExceptionEnum.business_logic_exception.getIndex());
			returnMsg.setStatus(false);
			logger.error("查询投诉记录报错！", e);
		}
		logger.info("返回参数:" + JacksonUtil.writeValueAsString(returnMsg));
		logger.info("=======end queryOneComplaintApplication 手机APP调用queryOneComplaintApplication方法接口====================");
		return returnMsg;
		
	}

	/**
	 * 删除投诉记录
	 * @param sessionId
	 * @return
	 */
	@RequestMapping("/deleteComplainRecord")
	@ResponseBody
	public ReturnMsg<Object> deleteComplainRecord(HttpServletRequest request, String id){
		logger.info("=======begin delete 手机APP调用deleteComplainRecord方法接口====================");
		logger.info("=======传参 id=" + id + "=============");
		ReturnMsg<Object> returnMsg = new ReturnMsg<Object>();
		try {
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
			ComplaintApplicationDto complaintApplicationDto = complainApplicationservice.getComplaintApplicationById(id);
			if (complaintApplicationDto == null) {
				logger.info("该id不存在:" + id);
				returnMsg.setMsg(ExceptionEnum.id_not_exist.getValue() + ":" + id);
				returnMsg.setCode(ExceptionEnum.id_not_exist.getIndex());
				returnMsg.setStatus(false);
				return returnMsg;
			}
			complainApplicationservice.deleteComplainRecord(id);
		}catch (Exception e) {
			returnMsg.setMsg(ExceptionEnum.business_logic_exception.getValue() + e.getMessage());
			returnMsg.setCode(ExceptionEnum.business_logic_exception.getIndex());
			returnMsg.setStatus(false);
			logger.error("根据id删除投诉记录失败!", e);
		}
		logger.info("=======end delete 手机APP调用updateDefaultAddress方法接口====================");
		return returnMsg;
		
	}
	
	
	/**
	 * 判断用户是否已经实名认证
	 * @return
	 */
	@RequestMapping("/isAuthentication")
	@ResponseBody
	public ReturnMsg<String> isAuthentication(HttpServletRequest request){		
		logger.info("============ begin 判断用户是否已经实名认证 isAuthentication=================");
		RequestContext requestContext = new RequestContext(request);
		String userName = (String) UserSession.getSession(WxConstant.wx_user_name);
		ReturnMsg<String> msg = new ReturnMsg<String>();
		try{
			// 非空判断
			if (Utils.isBlank(userName)) {
				msg.setCode(ExceptionEnum.param_not_null.getIndex());
				msg.setStatus(false);
				msg.setMsg(requestContext.getMessage("business.param.notNull")
							+ ":userName");
				return msg;
			}
			MemMember member = memMemberService.getCurrentUser(userName);
			// 用户名是否存在判断
			if (Utils.isBlank(member)) {
				msg.setCode(ExceptionEnum.param_illegal.getIndex());
				msg.setStatus(false);
				msg.setMsg(requestContext.getMessage("business.param.illegal")
							+ ",该用户不存在,userName:" + userName);
					return msg;
			}					
			if(StringUtils.isEmpty(member.getApproveFlag())) {
				//没有实名认证
				msg.setCode("500");
				msg.setStatus(false);
				return msg;
			} else if("2".equals(member.getApproveFlag())) {
					msg.setCode("2");
					msg.setStatus(false);
					return msg;
			} else if("0".equals(member.getApproveFlag())) {
					msg.setCode("0");
					msg.setStatus(false);
					return msg;
			}
			msg.setCode("1");
			msg.setStatus(true);
			
		} catch (Exception e) {
			msg.setStatus(false);
		}
		return msg;		
		
	}
	
	/**
	 * 从session中获取用户信息
	 * @return
	 */
	@RequestMapping("/getMemberInfo")
	@ResponseBody
	public ReturnMsg<Object> getMemberInfo() {
		MemMember memberDto = (MemMember) UserSession.getSession(WxConstant.wx_member_info);
		logger.info("============ begin 获取用户信息接口 getMember=================");
		ReturnMsg<Object> returnMsg = new ReturnMsg<Object>();
		if (null == memberDto) {
			returnMsg.setStatus(false);
			return returnMsg;
		} 
		returnMsg.setStatus(true);
		returnMsg.setData(memberDto);
		return returnMsg;
	}
	
	/**
	 * 设置/忘记支付密码
	 * @param memberDto
	 * @param request
	 * @param response
	 * @param modelMap
	 * @param session
	 * @return
	 */
	@RequestMapping("/savePayPasswordByUserName")
	@ResponseBody
	public ReturnMsg<Object> savePayPasswordByUserName(MemMember memberDto, HttpServletRequest request, HttpServletResponse response){
		ReturnMsg<Object> returnMsg = new ReturnMsg<Object>();
		RequestContext requestContext = new RequestContext(request);
		try {
			String fromPageValidateCode = request.getParameter("validateCode");
			if (StringUtils.isEmpty( memberDto.getPhone()) || StringUtils.isEmpty(memberDto.getPayPassword()) ||StringUtils.isEmpty(fromPageValidateCode)) {
				returnMsg.setCode(ExceptionEnum.param_not_null.getIndex());
				returnMsg.setStatus(false);
				returnMsg.setMsg(ExceptionEnum.param_not_null.getValue());
				return returnMsg;
			}
			
			MemMember member = (MemMember) UserSession.getSession(WxConstant.wx_member_info);
			
			if (null == member) {
				logger.info("该会员不存在");
				returnMsg.setMsg(ExceptionEnum.member_not_exist.getValue());
				returnMsg.setCode(ExceptionEnum.member_not_exist.getIndex());
				returnMsg.setStatus(false);
				return returnMsg;
			}
			ReturnMsg<Object> checkResult = WxPhoneMessageUtil.checkMessCode(fromPageValidateCode,WxConstant.wx_mess_code_1);
			if (!checkResult.isStatus()) {
				return checkResult;
			}
			//des解密
			Des desObj = new Des();
			memberDto.setPayPassword(MD5Util.MD5Encode(desObj.strDec(memberDto.getPayPassword(), "1", "2", "3"), "UTF-8"));
			
			memMemberService.updatePayPassword(member.getId(), memberDto.getPayPassword());
			UserSession.setSession(WxConstant.wx_member_info, memberDto);
			returnMsg.setMsg(requestContext.getMessage("user.updatepaypassword.ok"));
			returnMsg.setStatus(true);
			returnMsg.setData(member);	
		}catch (Exception ex) {	
			ex.printStackTrace();
			returnMsg.setStatus(false);
			returnMsg.setMsg(requestContext.getMessage("user.updatepaypassword.failregist"));
			return returnMsg;
		}
		return returnMsg;
	}
	
	
	private static String getWxName() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		String newDate = sdf.format(date);
		String orderNo = newDate + RandomUtil.getRandom(2);
		return orderNo;
	}

	/**
	 * 修改支付密码
	 * 
	 * @param id
	 * @param paypassword
	 * @param paypassword1
	 * @param oldpayPassword
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/updatePayPassword")
	@ResponseBody
	public ReturnMsg<Object> updatePayPassword(String payPassword,
			String paypassword1, String oldpayPassword, String id,
			HttpServletRequest request) {
		ReturnMsg<Object> returnMsg = new ReturnMsg<Object>();
		RequestContext requestContext = new RequestContext(request);
		try {
			if (StringUtils.isEmpty(payPassword)
					|| StringUtils.isEmpty(oldpayPassword)) {
				returnMsg.setCode(ExceptionEnum.param_not_null.getIndex());
				returnMsg.setStatus(false);
				returnMsg.setMsg(ExceptionEnum.param_not_null.getValue());
				return returnMsg;
			}
			MemMember member = memMemberService.getMemMember(id);
			if (null == member) {
				logger.info("该会员不存在");
				returnMsg.setMsg(ExceptionEnum.member_not_exist.getValue());
				returnMsg.setCode(ExceptionEnum.member_not_exist.getIndex());
				returnMsg.setStatus(false);
				return returnMsg;
			}
			if (!oldpayPassword.equals(member.getPayPassword())) {
				logger.info("与原始密码不一致");
				returnMsg.setMsg(ExceptionEnum.old_password_error.getValue());
				returnMsg.setCode(ExceptionEnum.old_password_error.getIndex());
				returnMsg.setStatus(false);
				return returnMsg;
			}
			memMemberService.updatePayPassword(member.getId(), payPassword);
			returnMsg.setMsg(requestContext
					.getMessage("user.updatepaypassword.ok"));
			returnMsg.setStatus(true);
			returnMsg.setData(member);

		} catch (Exception ex) {
			ex.printStackTrace();
			returnMsg.setStatus(false);
			returnMsg.setMsg(requestContext
					.getMessage("user.updatepaypassword.failregist"));
			return returnMsg;
		}
		return returnMsg;

	}

	/**
	 * 验证原密码 -- 用于修改支付密码页面
	 * 
	 * @param oldpaypassword
	 * @return
	 */
	@RequestMapping("/checkOldPayPwd")
	@ResponseBody
	public ReturnMsg<String> checkOldPayPwd(String oldpaypassword) {
		logger.info("=========验证原密码开始weixin=======");
		logger.info("======oldpaypassword:{}======", oldpaypassword);
		ReturnMsg<String> returnMsg = new ReturnMsg<String>();
		try {
			if (Utils.isObjectsBlank(oldpaypassword)) {
				returnMsg.setStatus(false);
				returnMsg.setCode(ExceptionEnum.param_not_null.getIndex());
				returnMsg.setMsg(ExceptionEnum.param_not_null.getValue());
				return returnMsg;
			}
			oldpaypassword = new Des().strDec(oldpaypassword, "1", "2", "3");
			MemMember memMember = memMemberService.getMemMember(String
					.valueOf(UserSession.getSession(WxConstant.wx_user_id)));
			returnMsg.setStatus(DigestUtil.MD5(oldpaypassword).equalsIgnoreCase(memMember.getPayPassword()));
		} catch (Exception e) {
			logger.error("验证原密码出错，原因：{}", e.getMessage());
			
			returnMsg.setCode(ExceptionEnum.system_error.getIndex());
			returnMsg.setMsg(ExceptionEnum.system_error.getValue());
			returnMsg.setStatus(false);
		}
		return returnMsg;
	}

	/**
	 * 重置支付密码
	 * 
	 * @param 
	 * @param 
	 * @return
	 */
	@RequestMapping("/resetPayPwd")
	@ResponseBody
	public ReturnMsg<String> resetPayPwd(String oldPassword, String password, String password2) {
		logger.info("=========重置密码开始weixin=======");
		logger.info("======oldPassword:"+oldPassword+", password:"+password+", password2:"+password2+"======");
		ReturnMsg<String> returnMsg = new ReturnMsg<String>();
		try {
			if (Utils.isObjectsBlank(oldPassword, password, password2)) {
				returnMsg.setStatus(false);
				returnMsg.setCode(ExceptionEnum.param_not_null.getIndex());
				returnMsg.setMsg(ExceptionEnum.param_not_null.getValue());
				return returnMsg;
			}
			
			//验证2次密码是否正确
			if(!password.equals(password2)){
				returnMsg.setStatus(false);
				returnMsg.setCode(ExceptionEnum.password_different.getIndex());
				returnMsg.setMsg(ExceptionEnum.password_different.getValue());
				return returnMsg;
			}
			String memBerId = String.valueOf(UserSession.getSession(WxConstant.wx_user_id));
			String OldPass = oldPassword;
			//验证原密码是否正确
			oldPassword = new Des().strDec(oldPassword, "1", "2", "3");
			MemMember memMember = memMemberService.getMemMember(memBerId);
			boolean isOK = DigestUtil.MD5(oldPassword).equalsIgnoreCase(memMember.getPayPassword());
			if(!isOK){
				returnMsg.setStatus(false);
				returnMsg.setCode(ExceptionEnum.old_password_error.getIndex());
				returnMsg.setMsg(ExceptionEnum.old_password_error.getValue());
				return returnMsg;
			}
			//验证原密码和新密码是否一致
			if(OldPass.equals(password)){
				returnMsg.setStatus(true);
				return returnMsg;
			}
			//修改密码
			memMemberService.updatePayPassword(memBerId, DigestUtil.MD5(new Des().strDec(password, "1", "2", "3")));
			returnMsg.setStatus(true);
		} catch (Exception e) {
			logger.error("修改支付密码出错，原因：{}", e.getMessage());
			
			returnMsg.setStatus(false);
			returnMsg.setCode(ExceptionEnum.system_error.getIndex());
			returnMsg.setMsg(ExceptionEnum.system_error.getValue());
		}
		return returnMsg;
	}

	/***
	 * 验证支付密码
	 * @param payPassword
	 * @return
	 */
	@RequestMapping("/checkPayPassword")
	@ResponseBody
	public ReturnMsg<String> checkPayPassword(String payPassword){
		ReturnMsg<String> returnMsg = new ReturnMsg<String>();
		
		if(StringUtils.isEmpty(payPassword)){
			returnMsg.setStatus(false);
			returnMsg.setCode(ExceptionEnum.param_not_null.getIndex());
			returnMsg.setMsg(ExceptionEnum.param_not_null.getValue());
			return returnMsg;
		}
		try {
			MemMember member = memMemberService.getMemMember(String.valueOf(UserSession.getSession(WxConstant.wx_user_id)));
			if(StringUtils.isEmpty(member.getPayPassword())){
				returnMsg.setStatus(false);
				returnMsg.setCode(ExceptionEnum.no_set_pay_password.getIndex());
				returnMsg.setMsg(ExceptionEnum.no_set_pay_password.getValue());
				return returnMsg;
			}
			payPassword = DigestUtil.MD5(new Des().strDec(payPassword, "1", "2", "3"));
			if (!payPassword.equals(member.getPayPassword())) {
				returnMsg.setStatus(false);
				returnMsg.setCode(ExceptionEnum.pass_error.getIndex());
				returnMsg.setMsg(ExceptionEnum.pass_error.getValue());
				return returnMsg;
			}
			returnMsg.setStatus(true);
		} catch (Exception e) {
			returnMsg.setStatus(false);
			returnMsg.setCode(ExceptionEnum.system_error.getIndex());
			returnMsg.setMsg(ExceptionEnum.system_error.getValue());
		}
		return returnMsg;
	}
}
