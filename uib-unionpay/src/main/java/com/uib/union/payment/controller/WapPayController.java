package com.uib.union.payment.controller;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinagpay.mer.bean.ProcessMessage;
import com.uib.common.utils.BeanAndMapUtil;
import com.uib.union.common.SystemConstant;
import com.uib.union.payment.dto.AcpsdkPayResponse;
import com.uib.union.payment.dto.MerchantNotifyDto;
import com.uib.union.payment.dto.PaymentOrderDto;
import com.uib.union.payment.dto.RefundGoodsDto;
import com.uib.union.payment.service.WapPayService;
import com.uib.union.utils.LogPrintUtil;
import com.unionpay.acp.sdk.HttpClient;
import com.unionpay.acp.sdk.SDKUtil;

/**
 * 银联全渠道手机wap支付
 * @author kevin
 *
 */
@Controller
@RequestMapping("/wapPay")
public class WapPayController {
	
	private static final Logger logger = Logger.getLogger("rootLogger");

	
	@Value("${acpsdk.frontTransUrl}")
	private String ACPSDK_FRONTTRANSURL;
	
	
	@Value("/wapPay")
	private String wapPay;
	
	@Value("/wapPayCall")
	private String wapPayCall;
	
	@Autowired
	private WapPayService wapPayService;
	
	
	/**
	 * 银联全渠道手机wap支付
	 * @param pay
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/pay")
	public String wapPay(@ModelAttribute PaymentOrderDto paymentOrderDto,
			HttpServletResponse response, HttpServletRequest request) {
		try {
			logger.info("==========begin 已进入银联全渠道手机 wap 支付  ======================");
			String interfaceName = request.getParameter("interfaceName");
			String tranData = request.getParameter("tranData");
			String clientIp = request.getParameter("clientIp");
			String requestIp = request.getRemoteHost();
			String clientName = request.getParameter("clientName");
			
			String base64DecodeData = "";
			if (null != tranData && !"".equals(tranData)) {
				base64DecodeData = new String(ProcessMessage.Base64Decode(tranData), "UTF-8");
			}
			String merSignMsg = request.getParameter("merSignMsg");
			String merchantId = request.getParameter("merchantId");
			//String bankId = request.getParameter("bankId");
			
			paymentOrderDto.setInterfaceName(interfaceName);
			paymentOrderDto.setTranData(base64DecodeData);
			paymentOrderDto.setMerSignMsg(merSignMsg);
			paymentOrderDto.setMerchantId(merchantId);
			//paymentOrderDto.setBankId(bankId);
			paymentOrderDto.setClientIp(clientIp);
			paymentOrderDto.setClientName(clientName);
			

			//logger.info("接收用户选择的  bankId 数据 =====" + bankId);
			logger.info("接收商户  xml 数据 =====" + paymentOrderDto.getTranData());
			logger.info("接收商户 签名数据===" + paymentOrderDto.getMerSignMsg());
			logger.info("接收商户 接口名称===" + paymentOrderDto.getInterfaceName());
			logger.info("接收商户 商户ID===" + paymentOrderDto.getMerchantId());
			logger.info("接收商户接口　 clientIp===" + clientIp);
			logger.info("接收商户接口　 requestIp===" + requestIp);
			logger.info("接收商户接口　 clientName===" + clientName);

			Map<String, String> requestMap = wapPayService.saveWapPay(paymentOrderDto);
			if (null != requestMap ){
				request.setAttribute("requestMap", requestMap);
				request.setAttribute("requestFrontUrl", ACPSDK_FRONTTRANSURL);
				logger.info("========================打印请求银联支付接口参数开始========================");
				LogPrintUtil.logParamMap(requestMap);
				logger.info("========================打印请求银联支付接口参数结束========================");
				logger.info("==========end 已进入银联全渠道支付  acpsdk pay ======================");
			}
			
			logger.info("========================打印请求银联手机 wap 支付 接口参数开始========================");
			LogPrintUtil.logParamMap(requestMap);
			logger.info("========================打印请求银联手机 wap 支付 接口参数结束========================");
			logger.info("==========end 已进入银联全渠道手机 wap 支付  ======================");
		} catch (Exception ex){
			logger.error("银联全渠道手机 wap 支付  失败   : " ,ex);
		}
		return wapPay;
	}
	
	
	
	
	/**
	 * 全渠道手机wap支付后台返回通知						  
	 * @param acpsdkPayResponse
	 * @param response
	 * @param request
	 */
	@RequestMapping(value = "/wapPayBackNotify")
	public void wapPayBackNotify(@ModelAttribute AcpsdkPayResponse acpsdkPayResponse,
			HttpServletResponse response, HttpServletRequest request) {
		
		try {
			logger.info("==========begin 已进入银联全渠道手机 wap 支付后台返回通知  wapPayBackNotify ======================");
			
			Map<String, String> map =	BeanAndMapUtil.transBean2MapString(acpsdkPayResponse);
			if (!SDKUtil.validate(map, SystemConstant.encoding)){
				MerchantNotifyDto notifyDto =	wapPayService.unionWapPayNotify(acpsdkPayResponse);
				
				String newTranDataEncode = ProcessMessage.Base64Encode(notifyDto.getTranData().getBytes());
				
				logger.info(" notifyMerchant 返回二级商户数据interfaceName  :" + notifyDto.getInterfaceName());
				logger.info(" notifyMerchant 返回二级商户数据signData  :" + notifyDto.getSignData());
				logger.info(" notifyMerchant 返回二级商户数据tranData  :" + notifyDto.getTranData());
				logger.info(" notifyMerchant 返回二级商户数据newTranDataEncode  :" + newTranDataEncode);
				logger.info(" notifyMerchant 返回二级商户数据version  :" + notifyDto.getVersion());
				
				request.setAttribute("interfaceName", notifyDto.getInterfaceName());
				request.setAttribute("signData", notifyDto.getSignData());
				request.setAttribute("tranData", newTranDataEncode);
				request.setAttribute("version", notifyDto.getVersion());
				request.setAttribute("pageURL", notifyDto.getReturnUrl());
				
				request.setAttribute("orderNo", notifyDto.getOrderNo());
				request.setAttribute("orderAmt", notifyDto.getOrderAmt());
				
				Map<String, String> postMap = new HashMap<String, String>();
				postMap.put("interfaceName", notifyDto.getInterfaceName());
				postMap.put("signData", notifyDto.getSignData());
				postMap.put("tranData", newTranDataEncode);
				postMap.put("version", notifyDto.getVersion());
				postMap.put("pageURL", notifyDto.getReturnUrl());
				postMap.put("orderNo", notifyDto.getOrderNo());
				postMap.put("orderAmt", notifyDto.getOrderAmt());
				
				HttpClient hc = new HttpClient(notifyDto.getNotifyUrl(), 30000, 30000);
				int status = hc.send(postMap, SystemConstant.encoding);
				
				logger.info(" wapPayBackNotify 返回二级商户状态  :" + status);
				logger.info(" wapPayBackNotify 返回二级商户数据interfaceName  :" + notifyDto.getInterfaceName());
				logger.info(" wapPayBackNotify 返回二级商户数据signData  :" + notifyDto.getSignData());
				logger.info(" wapPayBackNotify 返回二级商户数据tranData  :" + notifyDto.getTranData());
				logger.info(" wapPayBackNotify 返回二级商户数据newTranDataEncode  :" + newTranDataEncode);
				logger.info(" wapPayBackNotify 返回二级商户数据version  :" + notifyDto.getVersion());
			}
			
			logger.info("==========end 已进入银联全渠道手机 wap 支付返后台回通知  wapPayBackNotify ======================");
		} catch (Exception ex){
			logger.error("银联全渠道支付  wapPayBackNotify   失败   : " , ex);
		}
	}
	
	
	
	/**
	 * 全渠道手机wap支付前台页面返回通知
	 * @param acpsdkPayResponse
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/wapPayFrontNotify")
	public String wapPayFrontNotify(@ModelAttribute AcpsdkPayResponse acpsdkPayResponse,
			HttpServletResponse response, HttpServletRequest request) {
		
		try {
			logger.info("==========begin 已进入银联全渠道支付前台返回通知  wapPayFrontNotify ======================");
			Map<String, String> map =	BeanAndMapUtil.transBean2MapString(acpsdkPayResponse);
			if (!SDKUtil.validate(map, SystemConstant.encoding)){
				MerchantNotifyDto notifyDto =	wapPayService.unionWapPayNotify(acpsdkPayResponse);
				
				String newTranDataEncode = ProcessMessage.Base64Encode(notifyDto.getTranData().getBytes());
				
				logger.info(" notifyMerchant 返回二级商户数据interfaceName  :" + notifyDto.getInterfaceName());
				logger.info(" notifyMerchant 返回二级商户数据signData  :" + notifyDto.getSignData());
				logger.info(" notifyMerchant 返回二级商户数据tranData  :" + notifyDto.getTranData());
				logger.info(" notifyMerchant 返回二级商户数据newTranDataEncode  :" + newTranDataEncode);
				logger.info(" notifyMerchant 返回二级商户数据version  :" + notifyDto.getVersion());
				
				request.setAttribute("interfaceName", notifyDto.getInterfaceName());
				request.setAttribute("signData", notifyDto.getSignData());
				request.setAttribute("tranData", newTranDataEncode);
				request.setAttribute("version", notifyDto.getVersion());
				request.setAttribute("pageURL", notifyDto.getReturnUrl());
				
				request.setAttribute("orderNo", notifyDto.getOrderNo());
				request.setAttribute("orderAmt", notifyDto.getOrderAmt());
			}
			logger.info("==========end 已进入银联全渠道支付前台返回通知  wapPayFrontNotify ======================");
		} catch (Exception ex){
			logger.error("银联全渠道手机wap支付  wapPayFrontNotify   失败   : " ,ex);
		}
		return wapPayCall;
	}
	
	/**
	 * 全渠道退货接口
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/wapReturnGoods")
	@ResponseBody
	public String wapReturnGoods(HttpServletResponse response, HttpServletRequest request){
		String returnStr = "failure";
		try {
			logger.info("==========begin 已进入银联全渠道支付退货  acpsdkReturnGoods ======================");
			String interfaceName =	request.getParameter("interfaceName");
			String version = request.getParameter("version");
			String tranData = request.getParameter("tranData");
			String merSignMsg = request.getParameter("merSignMsg");
			String merchantId = request.getParameter("merchantId");
			
			logger.info("接收退货wapReturnGoods参数接口名称 interfaceName==" + interfaceName);
			logger.info("接收退货wapReturnGoods参数接口名称version====" + version);
			logger.info("接收退货wapReturnGoods参数接口名称tranData====" + tranData);
			logger.info("接收退货wapReturnGoods参数接口名称merSignMsg====" + merSignMsg);
			logger.info("接收退货wapReturnGoods参数接口名称merchantId====" + merchantId);
			
			RefundGoodsDto refundGoodsDto = new RefundGoodsDto();
			refundGoodsDto.setInterfaceName(interfaceName);
			refundGoodsDto.setVersion(version);
			refundGoodsDto.setTranData(tranData);
			refundGoodsDto.setMerSignMsg(merSignMsg);
			refundGoodsDto.setMerchantId(merchantId);
			
			boolean flag = 	wapPayService.unionReturnGoods(refundGoodsDto);
			if (flag){
				returnStr = "success";
			}
			
			logger.info("==========end 已进入银联全渠道支付退货  wapReturnGoods ======================");
		} catch (Exception ex) {
			logger.error("银联全渠道支付退货   wapReturnGoods   失败   : " ,ex);
		}
		return returnStr;
	}
	
	
	
	/**
	 * 退货返回通知地址
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/returnGoodsNotify")
	@ResponseBody
	public String returnGoodsNotify(HttpServletResponse response, HttpServletRequest request){
		String returnStr = "failure";
		try {
			logger.info("==========begin 已进入银联全渠道支付退货  returnGoodsNotify 结时返回通知 ======================");
			//acpsdkPayService.unionAllReturnGoods(refundGoodsDto);
			returnStr = "success";
			logger.info("==========end 已进入银联全渠道支付退货  returnGoodsNotify 结时返回通知======================");
		} catch (Exception ex) {
			logger.error("银联全渠道支付退货   returnGoodsNotify   失败   : " ,ex);
		}
		return returnStr;
	}
	
	
	/**
	 * 全渠道手机wap取消交易接口
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/cancelTrade")
	@ResponseBody
	public String cancelTrade(HttpServletResponse response, HttpServletRequest request){
		String returnStr = "failure";
		try {
			logger.info("==========begin 已进入银联全渠道 手机wap 支付撤销交易   cancelTrade 时返回通知 ======================");
			String interfaceName =	request.getParameter("interfaceName");
			String version = request.getParameter("version"); 
			String tranData = request.getParameter("tranData");
			String merSignMsg = request.getParameter("merSignMsg");
			String merchantId = request.getParameter("merchantId");
			
			logger.info("接收手机 wap取消交易接口 cancelTrade 参数接口名称 interfaceName==" + interfaceName);
			logger.info("接收wap取消交易接口 cancelTrade  参数接口名称version====" + version);
			logger.info("接收wap取消交易接口 cancelTrade 参数接口名称tranData====" + tranData);
			logger.info("接收wap取消交易接口 cancelTrade 参数接口名称merSignMsg====" + merSignMsg);
			logger.info("接收wap取消交易接口 cancelTrade 参数接口名称merchantId====" + merchantId);
			
			RefundGoodsDto refundGoodsDto = new RefundGoodsDto();
			refundGoodsDto.setInterfaceName(interfaceName);
			refundGoodsDto.setVersion(version);
			refundGoodsDto.setTranData(tranData);
			refundGoodsDto.setMerSignMsg(merSignMsg);
			refundGoodsDto.setMerchantId(merchantId);
			
			wapPayService.cancelTrade(refundGoodsDto);
			returnStr = "success";
			logger.info("==========end 已进入银联全渠道  手机wap 支付 撤销交易   cancelTrade 时返回通知  ======================");
		} catch (Exception ex) {
			logger.error("银联全渠道手机wap支付退货   cancelTrade   失败   : " ,ex);
		}
		return returnStr;
	}
	
	
	
}
