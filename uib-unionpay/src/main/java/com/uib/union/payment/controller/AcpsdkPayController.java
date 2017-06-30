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
import com.uib.common.utils.Base64Util;
import com.uib.common.utils.BeanAndMapUtil;
import com.uib.common.web.HttpCall;
import com.uib.common.web.HttpCallResult;
import com.uib.union.common.SystemConstant;
import com.uib.union.payment.dto.AcpsdkPayResponse;
import com.uib.union.payment.dto.AcpsdkReturnGoodsRepsonse;
import com.uib.union.payment.dto.MerchantNotifyDto;
import com.uib.union.payment.dto.PaymentOrderDto;
import com.uib.union.payment.dto.RefundGoodsDto;
import com.uib.union.payment.service.AcpsdkPayService;
import com.uib.union.utils.LogPrintUtil;
import com.unionpay.acp.sdk.SDKUtil;

/**
 * 全渠道支付
 * @author kevin
 *
 */
@Controller
@RequestMapping("/acpsdk")
public class AcpsdkPayController {
	
	private static final Logger logger = Logger.getLogger("rootLogger");

	
	@Value("${acpsdk.frontTransUrl}")
	private String ACPSDK_FRONTTRANSURL;
	
	
	@Value("/unionAllPay")
	private String unionAllPay;
	
	@Value("/unionAllPayCall")
	private String unionAllPayCallView;
	
	@Autowired
	private AcpsdkPayService acpsdkPayService;
	
	
	/**
	 * 全渠道支付
	 * @param pay
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/pay")
	public String acpsdkPay(@ModelAttribute PaymentOrderDto paymentOrderDto,
			HttpServletResponse response, HttpServletRequest request) {
		
		try {
			logger.info("==========begin 已进入银联全渠道支付  acpsdk pay ======================");
			String interfaceName = request.getParameter("interfaceName");
			String tranData = request.getParameter("tranData");
			String clientIp = request.getParameter("clientIp");
			String requestIp = request.getRemoteHost();
			String clientName = request.getParameter("clientName");
			
			String base64DecodeData = "";
			if (null != tranData && !"".equals(tranData)) {
				//base64DecodeData = new String(ProcessMessage.Base64Decode(tranData), "UTF-8");
				base64DecodeData = Base64Util.getFromBase64(tranData);
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
			paymentOrderDto.setBase64TranData(tranData);
			

			//logger.info("接收用户选择的  bankId 数据 =====" + bankId);
			logger.info("接收商户  xml 数据 =====" + paymentOrderDto.getTranData());
			logger.info("接收商户 签名数据===" + paymentOrderDto.getMerSignMsg());
			logger.info("接收商户 接口名称===" + paymentOrderDto.getInterfaceName());
			logger.info("接收商户 商户ID===" + paymentOrderDto.getMerchantId());
			logger.info("接收商户接口　 clientIp===" + clientIp);
			logger.info("接收商户接口　 requestIp===" + requestIp);
			logger.info("接收商户接口　 clientName===" + clientName);

			Map<String, String> requestMap = acpsdkPayService.saveAcpSdkPay(paymentOrderDto);
			if (null != requestMap){
				request.setAttribute("requestMap", requestMap);
				request.setAttribute("requestFrontUrl", ACPSDK_FRONTTRANSURL);
				logger.info("========================打印请求银联支付接口参数开始========================");
				LogPrintUtil.logParamMap(requestMap);
				logger.info("========================打印请求银联支付接口参数结束========================");
				logger.info("==========end 已进入银联全渠道支付  acpsdk pay ======================");
			}
			
		} catch (Exception ex){
			logger.error("银联全渠道支付  acpsdkPay   失败   : " ,ex);
		}
		return unionAllPay;
	}
	
	
	
	
	/**
	 * 全渠道后台返回通知						  
	 * @param acpsdkPayResponse
	 * @param response
	 * @param request
	 */
	@RequestMapping(value = "/acpsdkPayBackNotify")
	public void acpsdkPayBackNotify(@ModelAttribute AcpsdkPayResponse acpsdkPayResponse,
			HttpServletResponse response, HttpServletRequest request) {
		
		try {
			logger.info("==========begin 已进入银联全渠道支付后台返回通知  acpsdkPayBackNotify ======================");
			
			Map<String, String> map =	BeanAndMapUtil.transBean2MapString(acpsdkPayResponse);
			if (!SDKUtil.validate(map, SystemConstant.encoding)){
				MerchantNotifyDto notifyDto =	acpsdkPayService.unionAllPayNotify(acpsdkPayResponse);
				
				//String newTranDataEncode = ProcessMessage.Base64Encode(notifyDto.getTranData().getBytes());
				String newTranDataEncode =Base64Util.getBase64(notifyDto.getTranData());
				
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
				
				
				logger.info(" acpsdkPayBackNotify 返回二级商户访问地址  :" + notifyDto.getNotifyUrl());
				logger.info(" acpsdkPayBackNotify 返回二级商户数据interfaceName  :" + notifyDto.getInterfaceName());
				logger.info(" acpsdkPayBackNotify 返回二级商户数据signData  :" + notifyDto.getSignData());
				logger.info(" acpsdkPayBackNotify 返回二级商户数据tranData  :" + notifyDto.getTranData());
				logger.info(" acpsdkPayBackNotify 返回二级商户数据newTranDataEncode  :" + newTranDataEncode);
				logger.info(" acpsdkPayBackNotify 返回二级商户数据version  :" + notifyDto.getVersion());
				
				//HttpClient hc = new HttpClient(notifyDto.getNotifyUrl(), 30000, 30000);
				//int status = hc.send(postMap, SystemConstant.encoding);
				HttpCallResult callResult =	HttpCall.post(notifyDto.getNotifyUrl(),postMap );
				
				
				logger.info(" acpsdkPayBackNotify 返回二级商户状态  :" + callResult.getStatusCode());
			}
			
			logger.info("==========end 已进入银联全渠道支付返后台回通知  acpsdkPayBackNotify ======================");
		} catch (Exception ex){
			logger.error("银联全渠道支付  acpsdkPayBackNotify   失败   : " , ex);
		}
	}
	
	
	
	/**
	 * 全渠道支付前台页面返回通知
	 * @param acpsdkPayResponse
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/acpsdkPayFrontNotify")
	public String acpsdkPayFrontNotify(@ModelAttribute AcpsdkPayResponse acpsdkPayResponse,
			HttpServletResponse response, HttpServletRequest request) {
		
		try {
			logger.info("==========begin 已进入银联全渠道支付前台返回通知  acpsdkPayFrontNofity ======================");
			Map<String, String> map =	BeanAndMapUtil.transBean2MapString(acpsdkPayResponse);
			if (!SDKUtil.validate(map, SystemConstant.encoding)){
				MerchantNotifyDto notifyDto =	acpsdkPayService.unionAllPayNotify(acpsdkPayResponse);
				
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
			logger.info("==========end 已进入银联全渠道支付前台返回通知  acpsdkPayFrontNofity ======================");
		} catch (Exception ex){
			logger.error("银联全渠道支付  acpsdkPayFrontNofity   失败   : " ,ex);
		}
		return unionAllPayCallView;
	}
	
	/**
	 * 全渠道退货接口
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/acpsdkReturnGoods")
	@ResponseBody
	public Map<String, String> acpsdkReturnGoods(HttpServletResponse response, HttpServletRequest request){
		Map<String, String> returnMap = null;
		try {
			logger.info("==========begin 已进入银联全渠道支付退货  acpsdkReturnGoods ======================");
			String interfaceName =	request.getParameter("interfaceName");
			String version = request.getParameter("version");
			String tranData = request.getParameter("tranData");
			String merSignMsg = request.getParameter("merSignMsg");
			String merchantId = request.getParameter("merchantId");
			
			logger.info("接收退货refundGoods参数接口名称 interfaceName==" + interfaceName);
			logger.info("接收退货refundGoods参数接口名称version====" + version);
			logger.info("接收退货refundGoods参数接口名称tranData====" + tranData);
			logger.info("接收退货refundGoods参数接口名称merSignMsg====" + merSignMsg);
			logger.info("接收退货refundGoods参数接口名称merchantId====" + merchantId);
			
			RefundGoodsDto refundGoodsDto = new RefundGoodsDto();
			refundGoodsDto.setInterfaceName(interfaceName);
			refundGoodsDto.setVersion(version);
			refundGoodsDto.setTranData(tranData);
			refundGoodsDto.setMerSignMsg(merSignMsg);
			refundGoodsDto.setMerchantId(merchantId);
			
			returnMap = 	acpsdkPayService.unionAllReturnGoods(refundGoodsDto);
			
			logger.info("==========end 已进入银联全渠道支付退货  acpsdkReturnGoods ======================");
		} catch (Exception ex) {
			logger.error("银联全渠道支付退货   acpsdkReturnGoods   失败   : " ,ex);
		}
		return returnMap;
	}
	
	
	
	/**
	 * 退货返回通知地址
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/returnGoodsNotify")
	public void returnGoodsNotify(HttpServletResponse response, HttpServletRequest request, AcpsdkReturnGoodsRepsonse returnGoods){
		try {
			logger.info("==========begin 已进入银联全渠道支付退货  returnGoodsNotify 结时返回通知 ======================");
			boolean flag  =	acpsdkPayService.unionAllReturnGoodsNotify(returnGoods);
			logger.info("==========end 已进入银联全渠道支付退货  returnGoodsNotify 结时返回通知======================");
		} catch (Exception ex) {
			logger.error("银联全渠道支付退货   acpsdkReturnGoods   失败   : " ,ex);
		}
	}
	
	
	/**
	 * 全渠道取消交易接口
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/cancelTrade")
	@ResponseBody
	public String cancelTrade(HttpServletResponse response, HttpServletRequest request){
		String returnStr = "failure";
		try {
			logger.info("==========begin 已进入银联全渠道支付撤销交易   cancelTrade 时返回通知 ======================");
			String interfaceName =	request.getParameter("interfaceName");
			String version = request.getParameter("version"); 
			String tranData = request.getParameter("tranData");
			String merSignMsg = request.getParameter("merSignMsg");
			String merchantId = request.getParameter("merchantId");
			
			logger.info("接收退货cancelTrade参数接口名称 interfaceName==" + interfaceName);
			logger.info("接收退货cancelTrade参数接口名称version====" + version);
			logger.info("接收退货cancelTrade参数接口名称tranData====" + tranData);
			logger.info("接收退货cancelTrade参数接口名称merSignMsg====" + merSignMsg);
			logger.info("接收退货cancelTrade参数接口名称merchantId====" + merchantId);
			
			RefundGoodsDto refundGoodsDto = new RefundGoodsDto();
			refundGoodsDto.setInterfaceName(interfaceName);
			refundGoodsDto.setVersion(version);
			refundGoodsDto.setTranData(tranData);
			refundGoodsDto.setMerSignMsg(merSignMsg);
			refundGoodsDto.setMerchantId(merchantId);
			
			acpsdkPayService.cancelTrade(refundGoodsDto);
			returnStr = "success";
			logger.info("==========end 已进入银联全渠道支付撤销交易   cancelTrade 时返回通知  ======================");
		} catch (Exception ex) {
			logger.error("银联全渠道支付退货   cancelTrade   失败   : " ,ex);
		}
		return returnStr;
	}
	
	
	
}
