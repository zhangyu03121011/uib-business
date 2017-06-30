package com.uib.union.payment.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.chinagpay.mer.bean.ProcessMessage;
import com.uib.common.utils.BeanAndMapUtil;
import com.uib.union.common.SystemConstant;
import com.uib.union.enums.MobileReturnTypeEnum;
import com.uib.union.enums.RespCodeEnum;
import com.uib.union.payment.dto.AcpsdkPayResponse;
import com.uib.union.payment.dto.MerchantNotifyDto;
import com.uib.union.payment.dto.MobilePayResponse;
import com.uib.union.payment.dto.PaymentOrderDto;
import com.uib.union.payment.service.MobilePayService;
import com.uib.union.utils.LogPrintUtil;
import com.unionpay.acp.sdk.HttpClient;
import com.unionpay.acp.sdk.SDKUtil;

/**
 * 
 * @author kevin
 * 
 */
@Controller
@RequestMapping("/mobile")
public class MobilePayController {

	private static final Logger logger = Logger.getLogger("rootLogger");

	@Autowired
	private MobilePayService mobilePayService;

	/**
	 * 全渠道手机支付支付
	 * 
	 * @param pay
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/pay")
	@ResponseBody
	public MobilePayResponse mobilePay(@ModelAttribute PaymentOrderDto paymentOrderDto, HttpServletResponse response,
			HttpServletRequest request) {
		MobilePayResponse mobileResponse = new MobilePayResponse();
		try {
			logger.info("==========begin 已进入银联全渠道 手机支付  mobilePay ======================");
			String interfaceName = request.getParameter("interfaceName");
			String tranData = request.getParameter("tranData");
			String clientIp = request.getParameter("clientIp");
			String requestIp = request.getRemoteHost();
			String clientName = request.getParameter("clientName");

			String base64DecodeData = "";
			if (null != tranData && !"".equals(tranData)) {
				base64DecodeData = new String(ProcessMessage.Base64Decode(tranData));
			}
			String merSignMsg = request.getParameter("merSignMsg");
			String merchantId = request.getParameter("merchantId");
			// String bankId = request.getParameter("bankId");

			paymentOrderDto.setInterfaceName(interfaceName);
			paymentOrderDto.setTranData(base64DecodeData);
			paymentOrderDto.setMerSignMsg(merSignMsg);
			paymentOrderDto.setMerchantId(merchantId);
			// paymentOrderDto.setBankId(bankId);
			paymentOrderDto.setClientIp(clientIp);
			paymentOrderDto.setClientName(clientName);

			// logger.info("接收用户选择的  bankId 数据 =====" + bankId);
			logger.info("接收手机商户  xml 数据 =====" + paymentOrderDto.getTranData());
			logger.info("接收手机商户 签名数据===" + paymentOrderDto.getMerSignMsg());
			logger.info("接收手机商户 接口名称===" + paymentOrderDto.getInterfaceName());
			logger.info("接收手机商户 商户ID===" + paymentOrderDto.getMerchantId());
			logger.info("接收手机商户接口　 clientIp===" + clientIp);
			logger.info("接收手机商户接口　 requestIp===" + requestIp);
			logger.info("接收手机商户接口　 clientName===" + clientName);

			Map<String, String> requestMap = mobilePayService.saveMobilePay(paymentOrderDto);
			mobileResponse = new MobilePayResponse();
			if (null != requestMap) {
				String mapCode = requestMap.get("code").toString();
				if ("0".equals(mapCode)) {
					String respCode = requestMap.get("respCode");
					if (!RespCodeEnum.TRADE_SUCCESS.getDesc().equals(respCode)) {
						mobileResponse.setCode(MobileReturnTypeEnum.FAILURE.getDesc());
						mobileResponse.setMsg("交易失败");
					} else {
						String tn = requestMap.get("tn");
						mobileResponse.setTn(tn);
						mobileResponse.setCode(MobileReturnTypeEnum.SUCCESS.getDesc());
						mobileResponse.setMsg("提交订单成功");
						logger.info("========================打印请求银联手机支付接口成功返 回参数开始========================");
						LogPrintUtil.logParamMap(requestMap);
						logger.info("========================打印请求银联手机支付接口成功 返回参数结束========================");
						logger.info("==========end 已进入银联全渠道 手机支付  mobilePay ======================");
					}
				} else if (MobileReturnTypeEnum.ORDER_REPEAT.getDesc().equals(mapCode)) {
					mobileResponse.setCode(MobileReturnTypeEnum.ORDER_REPEAT.getDesc());
					mobileResponse.setMsg(requestMap.get("orderNo").toString() + " 该订单号已存在!");
					logger.info("返回码: " + MobileReturnTypeEnum.ORDER_REPEAT.getDesc() + requestMap.get("orderNo").toString() + " 该订单号已存在!");
				} else {
					mobileResponse.setCode(MobileReturnTypeEnum.FAILURE.getDesc());
					mobileResponse.setMsg("提交订单失败!");
					logger.info("返回码: " + MobileReturnTypeEnum.FAILURE.getDesc() + "提交订单失败!");
				}
			}
			// request.setAttribute("requestMap", requestMap);

		} catch (Exception ex) {
			logger.error("银联全渠道支付  mobilePay   失败   : ", ex);
			mobileResponse.setCode("3");
			mobileResponse.setMsg("系统内存错误!");
			
		}
		return mobileResponse;
	}
	
	
	
	
	
	@RequestMapping(value = "/mobilePayBackNotify")
	public void mobilePayBackNotify(@ModelAttribute AcpsdkPayResponse acpsdkPayResponse,
			HttpServletResponse response, HttpServletRequest request) {
		
		try {
			logger.info("==========begin 已进入银联全渠道手机控件支付后台返回通知  mobilePayBackNotify ======================");
			
			Map<String, String> map =	BeanAndMapUtil.transBean2MapString(acpsdkPayResponse);
			if (!SDKUtil.validate(map, SystemConstant.encoding)){
				mobilePayService.unionAllPayNotify(acpsdkPayResponse);
				MerchantNotifyDto notifyDto =	mobilePayService.unionAllPayNotify(acpsdkPayResponse);
				
				String newTranDataEncode = ProcessMessage.Base64Encode(notifyDto.getTranData().getBytes());
				
				
				
				Map<String, String> postMap = new HashMap<String, String>();
				postMap.put("interfaceName", notifyDto.getInterfaceName());
				postMap.put("signData", notifyDto.getSignData());
				postMap.put("tranData", newTranDataEncode);
				postMap.put("version", notifyDto.getVersion());
				postMap.put("orderNo", notifyDto.getOrderNo());
				postMap.put("orderAmt", notifyDto.getOrderAmt());
				
				
				HttpClient hc = new HttpClient(notifyDto.getNotifyUrl(), 30000, 30000);
				
				int status = hc.send(postMap, SystemConstant.encoding);
				
				//HttpCallResult callResult =	HttpCall.post(notifyDto.getNotifyUrl(), postMap);
			    //int status = 	callResult.getStatusCode();
				logger.info(" mobilePayBackNotify 返回二级商户数据返回地址  :" + notifyDto.getNotifyUrl());
				logger.info(" mobilePayBackNotify 返回二级商户数据interfaceName  :" + notifyDto.getInterfaceName());
				logger.info(" mobilePayBackNotify 返回二级商户数据signData  :" + notifyDto.getSignData());
				logger.info(" mobilePayBackNotify 返回二级商户数据tranData  :" + notifyDto.getTranData());
				logger.info(" mobilePayBackNotify 返回二级商户数据newTranDataEncode  :" + newTranDataEncode);
				logger.info(" mobilePayBackNotify 返回二级商户数据version  :" + notifyDto.getVersion());
				logger.info(" mobilePayBackNotify 返回二级商户数据,返回状态  :" + status);
			}
			
			logger.info("==========end 已进入银联全渠道手机控件支付后台返回通知  mobilePayBackNotify ======================");
		} catch (Exception ex){
			logger.error("银联全渠道手机控件支付后台返回通知  mobilePayBackNotify   失败   : " , ex);
		}
	}
}
