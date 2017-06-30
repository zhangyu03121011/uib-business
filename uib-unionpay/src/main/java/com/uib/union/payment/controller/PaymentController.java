package com.uib.union.payment.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chinagpay.mer.bean.ProcessMessage;
import com.uib.common.web.HttpCall;
import com.uib.common.web.ObjectConversion;
import com.uib.union.payment.dto.MerchantNotifyDto;
import com.uib.union.payment.dto.PaymentOrderDto;
import com.uib.union.payment.dto.RefundGoodsDto;
import com.uib.union.payment.dto.UnionPayNotifyResponseDto;
import com.uib.union.payment.pojo.PaymentOrder;
import com.uib.union.payment.service.PaymentOrderService;
import com.uib.union.utils.QuickPayConf;
import com.uib.union.utils.QuickPayUtils;

@Controller
@RequestMapping("/unionpay")
public class PaymentController {
	
	private static final Logger logger = Logger.getLogger("rootLogger");

	@Value("/unionPay")
	private String unionPayUrl;

	@Value("${unionpay_gateway}")
	private String UNIONPAY_GATEWAY;
	
	
	@Value("${gateway_payurl}")
	private String GATEWAY_PAYURL;
	
	@Value("/callback")
	private String  successView ;
	
	@Value("${return_goods_api}")
	private String RETURN_GOODS_API;
	

	
	
	@Autowired
	private PaymentOrderService paymentOrderService;
	
	
	
	
	
	/**
	 * 调用银联支付接口 
	 * @param paymentOrderDto
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/savePay")
	public String savePayment(@ModelAttribute PaymentOrderDto paymentOrderDto,
			HttpServletResponse response, HttpServletRequest request) {
		try {
			//request.setCharacterEncoding("UTF-8");
			
			
			logger.info("================== begin 已进入保存支付订单方法savePayment ===================");
			String interfaceName = request.getParameter("interfaceName");
			String tranData = request.getParameter("tranData");
			String clientIp = request.getParameter("clientIp");
			String clientName = request.getParameter("clientName");
			
			String base64DecodeData = "";
			if (null != tranData && !"".equals(tranData)) {
				base64DecodeData = new String(ProcessMessage.Base64Decode(tranData));
			}
			String merSignMsg = request.getParameter("merSignMsg");
			String merchantId = request.getParameter("merchantId");
			String bankId = request.getParameter("bankId");

			paymentOrderDto.setInterfaceName(interfaceName);
			paymentOrderDto.setTranData(base64DecodeData);
			paymentOrderDto.setMerSignMsg(merSignMsg);
			paymentOrderDto.setMerchantId(merchantId);
			paymentOrderDto.setBankId(bankId);
			paymentOrderDto.setClientIp(clientIp);
			paymentOrderDto.setClientName(clientName);
			

			logger.info("接收用户选择的  bankId 数据 =====" + bankId);
			logger.info("接收商户  xml 数据 =====" + paymentOrderDto.getTranData());
			logger.info("接收商户 签名数据===" + paymentOrderDto.getMerSignMsg());
			logger.info("接收商户 接口名称===" + paymentOrderDto.getInterfaceName());
			logger.info("接收商户 商户ID===" + paymentOrderDto.getMerchantId());
			logger.info("接收商户接口　 clientIp===" + clientIp);
			logger.info("接收商户接口　 clientName===" + clientName);
			
			
			Map<String, Object> returnMap  = paymentOrderService.savePaymentOrder(paymentOrderDto);
			PaymentOrder repeatOrder = (PaymentOrder)returnMap.get("repeatOrder");
			if (null != repeatOrder){
				request.setAttribute("errorCode", "-1");
				request.setAttribute("errorMessage", "订单重复"); 
				return unionPayUrl;
			}
			request.setAttribute("unionPay", returnMap.get("upRequestDto"));
			request.setAttribute("signature", returnMap.get("signature"));
			request.setAttribute("signMethod", returnMap.get("signMethod"));
			request.setAttribute("gateWayPayUrl", UNIONPAY_GATEWAY+GATEWAY_PAYURL);
			
			logger.info("================== end 进入保存订单方法savePayment结束  ===================");
		} catch (Exception ex) {
			logger.error("保存支付订单信息  savePay   失败   : " + ex.getMessage());
			ex.printStackTrace();
		}
		return unionPayUrl;
	}
	
	
	
	/**
	 * 接收银联支付页面返回通知接口
	 * 
	 * @param paymentOrderDto
	 * @param response
	 * @param request
	 */
	@RequestMapping(value = "/payPageNotify")
	public String payPageNotify(HttpServletResponse response, HttpServletRequest request) {
		
		try {

			String[] resArr = new String[QuickPayConf.notifyVo.length]; 
			for(int i=0;i<QuickPayConf.notifyVo.length;i++){
				resArr[i] = request.getParameter(QuickPayConf.notifyVo[i]);
			}
			String signature = request.getParameter(QuickPayConf.signature);
			String signMethod = request.getParameter(QuickPayConf.signMethod);
			Map<String, String> map = new TreeMap<String, String>();
			
			for (int i = 0; i < QuickPayConf.notifyVo.length; i++) {
				map.put(QuickPayConf.notifyVo[i], resArr[i]);
			}
			
			boolean signatureCheck = new QuickPayUtils().checkSign(resArr, signMethod, signature,map);
			
			UnionPayNotifyResponseDto  unpay = (UnionPayNotifyResponseDto)ObjectConversion.convertMap(UnionPayNotifyResponseDto.class, map);
			
			
			if (signatureCheck){
				MerchantNotifyDto mnotifyDto =	paymentOrderService.payBackgroundNotify(unpay);
				
				String newTranDataEncode = ProcessMessage.Base64Encode(mnotifyDto.getTranData().getBytes());
				
				logger.info(" notifyMerchant 返回二级商户数据interfaceName  :" + mnotifyDto.getInterfaceName());
				logger.info(" notifyMerchant 返回二级商户数据signData  :" + mnotifyDto.getSignData());
				logger.info(" notifyMerchant 返回二级商户数据tranData  :" + mnotifyDto.getTranData());
				logger.info(" notifyMerchant 返回二级商户数据newTranDataEncode  :" + newTranDataEncode);
				logger.info(" notifyMerchant 返回二级商户数据version  :" + mnotifyDto.getVersion());
				
				request.setAttribute("interfaceName", mnotifyDto.getInterfaceName());
				request.setAttribute("signData", mnotifyDto.getSignData());
				request.setAttribute("tranData", newTranDataEncode);
				request.setAttribute("version", mnotifyDto.getVersion());
				request.setAttribute("pageURL", mnotifyDto.getReturnUrl());
				
				request.setAttribute("orderNo", mnotifyDto.getOrderNo());
				request.setAttribute("orderAmt", mnotifyDto.getOrderAmt());
			}
			logger.info("================== end 接收支付后台返回通知接口  payPageNotify ===================");
		} catch (Exception ex) {
			logger.error("接收支付后台返回通知接口 payPageNotify 失败: " + ex.getMessage());
			ex.printStackTrace();
		}
		return successView;
	}

	
	
	/**
	 * 接收银联支付后台返回支付应答类
	 * @param paymentOrderDto
	 * @param response
	 * @param request
	 */
	@RequestMapping(value = "/payBackGroundNotify")
	public void payBackgroundNotify(HttpServletResponse response, HttpServletRequest request) {
		try {
			String[] resArr = new String[QuickPayConf.notifyVo.length]; 
			for(int i=0;i<QuickPayConf.notifyVo.length;i++){
				resArr[i] = request.getParameter(QuickPayConf.notifyVo[i]);
			}
			String signature = request.getParameter(QuickPayConf.signature);
			String signMethod = request.getParameter(QuickPayConf.signMethod);
			Map<String, String> map = new TreeMap<String, String>();
			
			for (int i = 0; i < QuickPayConf.notifyVo.length; i++) {
				map.put(QuickPayConf.notifyVo[i], resArr[i]);
			}
			
			boolean signatureCheck = new QuickPayUtils().checkSign(resArr, signMethod, signature,map);
			
			UnionPayNotifyResponseDto  unpay = (UnionPayNotifyResponseDto)ObjectConversion.convertMap(UnionPayNotifyResponseDto.class, map);
			
			
			if (signatureCheck){
				MerchantNotifyDto mnotifyDto =	paymentOrderService.payBackgroundNotify(unpay);
			}
			
			logger.info("================== end 接收支付后台返回通知接口  payNotify ===================");
		} catch (Exception ex) {
			logger.error("接收支付后台返回通知接口 payBackGroundNotify 失败: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	
	
	/**
	 * 通知二级商户支付结果
	 */
	public void notifyMerchant(MerchantNotifyDto  mnDto){
		try {
			logger.info("============= begin 进入通知二级商户支付结果方法notifyMerchant ================");
			Map<String , String> params = new HashMap<String, String>();
			
			params.put("interfaceName", mnDto.getInterfaceName());
			params.put("signData", mnDto.getSignData());
			
			params.put("version", mnDto.getVersion());
			
			String newTranDataEncode = ProcessMessage.Base64Encode(mnDto.getTranData().getBytes());
			params.put("tranData", newTranDataEncode);
			
			HttpCall.post(mnDto.getNotifyUrl(), params);
			
			
			logger.info(" notifyMerchant 返回二级商户数据interfaceName  :" + mnDto.getInterfaceName());
			logger.info(" notifyMerchant 返回二级商户数据signData  :" + mnDto.getSignData());
			logger.info(" notifyMerchant 返回二级商户数据tranData  :" + mnDto.getTranData());
			logger.info(" notifyMerchant 返回二级商户数据newTranDataEncode  :" + newTranDataEncode);
			logger.info(" notifyMerchant 返回二级商户数据version  :" + mnDto.getVersion());
			
			logger.info("============= end 进入通知二级商户支付结果方法notifyMerchant ================");
		} catch (Exception ex){
			logger.error("通知二级商户支付结果出错 :", ex);
			ex.printStackTrace();
		}
	}
	
	
	/**
	 * 后台申请银联退货
	 * @param response
	 * @param request
	 */
	@RequestMapping(value = "/refundGoods")
	public void refundGoods(HttpServletResponse response, HttpServletRequest request){
		try{
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
			
			Map<String, String> map =paymentOrderService.refundGoods(refundGoodsDto);
			/*HttpCallResult callResult =	HttpCall.post(RETURN_GOODS_API, params);
			String content = "";
			if (callResult.getStatusCode() ==200){
				 content =	callResult.getContent();
				 System.out.println("-----------------退款接口返回数据--BEGIN--------------------");
		        System.out.println("退款返回的数据："+content);
		        String s=content.split("&")[2];
		        String tranData1=s.substring(s.indexOf("=")+1,s.length());
		        String st=new String(ProcessMessage.Base64Decode(tranData1));
		        System.out.println(st);
		        System.out.println("-----------------退款接口返回数据--END--------------------");
		        content=content+"<br/> 明文数据：<xmp>"+st+"</xmp>";
		        paymentOrderService.updateRefundGoods(st);
			}*/
			
			
			
		} catch(Exception ex){
			logger.error("退货 refundGoods 失败: " + ex.getMessage());
			ex.printStackTrace();
		}
		//return refundGoodsView;
	}
	
}
