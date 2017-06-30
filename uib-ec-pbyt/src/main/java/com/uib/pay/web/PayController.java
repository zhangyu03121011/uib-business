package com.uib.pay.web;

import java.math.BigDecimal;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.uib.base.BaseController;
import com.uib.common.enums.ExceptionEnum;
import com.uib.common.utils.AppPayUtils;
import com.uib.common.utils.Base64Util;
import com.uib.common.utils.HmacSHASign;
import com.uib.common.utils.JacksonUtil;
import com.uib.common.web.B2CReq;
import com.uib.common.web.HttpCall;
import com.uib.common.web.HttpCallResult;
import com.uib.member.entity.MemMember;
import com.uib.member.service.MemMemberService;
import com.uib.mobile.dto.ReturnMsg;
import com.uib.order.entity.OrderTable;
import com.uib.order.service.OrderService;
import com.uib.pay.dto.AppPayResDto;
import com.uib.pay.service.PayService;

@Controller
@RequestMapping("/pay")
public class PayController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(PayController.class);
	//private String tempUrl = "http://192.168.100.56/ec-frontweb";
	/**
	 * 手机控件支付请求URL
	 */
	@Value("${UPOP.APP_PAY_URL}")
	private String UPOP_APP_PAY_URL;
	
	/**
	 * 银联全渠道支付
	 */
	@Value("${UPOP.PAY_URL}")
	private String UPOP_PAY_URL;

	@Value("${ALIPAY.WEBPAY_URL}")
	private String alipay;

	@Value("${UPOP.HMD5_PASSWORD}")
	private String md5;

	@Value("${MER_ID}")
	private String MER_ID;

	@Value("${UPOP.SIGN_TYPE")
	private String SIGN_TYPE;
	@Value("${siteUrl}")
	private String siteUrl;

	@Value("/paySubmit")
	private String paySubmit;

	@Value("${WAP.PAY_URL}")
	private String WAP_PAY_URL;

	@Value("${MER_RETURN_URL}")
	private String MER_RETURN_URL;

	@Value("${MER_NOTIFY_URL}")
	private String notifyURL;

	@Value("${SIGN_TYPE}")
	private String WAP_SIGN_TYPE;

	@Value("${HMD5_PASSWORD}")
	private String HMD5_PASSWORD;

	@Value("${PAY_INTERFACE_NAME}")
	private String PAY_INTERFACE_NAME;

	@Value("${WAP_PAY_MER_ID}")
	private String WAP_PAY_MER_ID;
	
	@Value("/paySubmit")
	private String paySubmitView;
	
	@Autowired
	private AppPayUtils appPayUtils;

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private MemMemberService memMemberService;
	
	@Autowired
	private PayService payService;

	/*
	@RequestMapping(value = "/unionPay")
	public String pay(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		try {
			String orderNo = String.valueOf(new Date().getTime());
			String payMethod = request.getParameter("pay"); // 支付宝方式
			String orderAmt = "0.01";
			String goodsName = "ipad air2";
			String goodsDesc = "描述";
			String mallUserName = "admin";
			String remark = "白色";
			// 交易请求地址
			String URL;
			// 接口名称
			String interfaceName = "AlipayWebPay"; // 判断是什么用支付
			// 商户编号
			String merId = MER_ID;
			String bankId = "110";
			// 交易数据
			String tranData = "";
			// 币种
			String curType = "CNY";
			// 商户接收支付成功数据的地址
			String returnURL = getNotifyUrl(orderNo, NotifyMethod.sync);
			// 商户接收支付成功消息的地址（后台返回）
			String notifyURL = getNotifyUrl(orderNo, NotifyMethod.async);
			String refundResultNotifyURL = ""; // 退货通知 url
			URL = alipay;
			String transactionData = "<orderNo>" + orderNo + "</orderNo><orderAmt>" + orderAmt + "</orderAmt><goodsName>" + goodsName + "</goodsName><goodsDesc>" + goodsDesc
					+ "</goodsDesc><mallUserName>" + mallUserName + "</mallUserName>" + "<remark>" + remark + "</remark>";
			tranData = "<?xml version=\"1.0\" encoding=\"utf-8\"?><B2CReq><merId>" + merId + "</merId><curType>" + curType + "</curType>" + transactionData + "<returnURL>" + returnURL
					+ "</returnURL><notifyURL>" + notifyURL
					+ "</notifyURL><merchantAccountType>0002</merchantAccountType><serviceCode>2015081709354917979</serviceCode><showUrl>http://localhost:8080/aaa</showUrl><orderName>ipad Air2</orderName><reserved1></reserved1><reserved2></reserved2></B2CReq>";
			String merSignMsg = "12345678";
			System.out.println("HMD5_PASSWORD:" + md5);
			System.out.println("tranData:" + tranData);
			merSignMsg = HmacSHASign.hmacSHASign(tranData, md5);
			System.out.println("merSignMsg11111111 ====" + merSignMsg);
			String tranDataBase64 = Base64Util.getBase64(tranData);
			Map<String, String> parameterMap = new HashMap<String, String>();
			parameterMap.put("merId", merId);
			parameterMap.put("bankId", bankId);
			parameterMap.put("interfaceName", interfaceName);
			parameterMap.put("tranDataBase64", tranDataBase64);
			parameterMap.put("merSignMsg", merSignMsg);
			parameterMap.put("payUrl", URL);
			request.setAttribute("parameterMap", parameterMap);

		} catch (Exception ex) {
			logger.error("电商二期银联支付失败!", ex);
		}
		return paySubmit;
	}*/
	
	@RequestMapping(value = "/unionPay")
	public String pay(HttpServletRequest request, HttpServletResponse response,HttpSession session, ModelMap modelMap) {
		
		try{
			logger.info("================begin 调用银联支付接口========================");
			String orderNo =	request.getParameter("orderNo");
			String goodsName = request.getParameter("goodsName");
		  //  goodsName = new String(goodsName.getBytes("ISO-8859-1"),"utf-8");
		    System.out.println("goodsName=="+goodsName);
			String amount = request.getParameter("amount");
			MemMember member =	(MemMember) session.getAttribute("member");
			B2CReq b2cReq = new B2CReq();
			b2cReq.setOrderNo(orderNo);
			b2cReq.setGoodsName(goodsName);
			b2cReq.setMallUserName(member.getUsername());
			b2cReq.setOrderAmt(amount);
			b2cReq.setGoodsDesc(goodsName);
			b2cReq.setReturnURL(getNotifyUrl(orderNo,NotifyMethod.sync));
			b2cReq.setNotifyURL(getNotifyUrl(orderNo,NotifyMethod.async));
			Map<String, Object> paramMap =	payService.disposeWebPay(b2cReq);
			paramMap.put("payUrl", UPOP_PAY_URL);
			request.setAttribute("parameterMap", paramMap);
			logger.info("================end 调用银联支付接口========================");
		} catch (Exception ex){
			logger.error("电商二期银联支付失败!", ex);
		}
		return paySubmitView;
	}

	/**
	 * 同步通知
	 */
	@RequestMapping(value = "/notify/{notifyMethod}/{sn}")
	public String notify(@PathVariable NotifyMethod notifyMethod, @PathVariable String sn, HttpServletRequest request) {
		try {
			String signData = request.getParameter("signData");
			String tranData = request.getParameter("tranData");
			
			logger.info("请求支付接口callBackNotify返回通知signData:" + signData);
			logger.info("请求支付接口callBackNotify返回通知tranData:" + tranData);
			
			String base64DecodeData = "";
			if (null != tranData && !"".equals(tranData)) {
				base64DecodeData = Base64Util.getFromBase64(tranData);
				//base64DecodeData = new String(B.Base64Decode(tranData));
			}
			System.out.println(signData);
			System.out.println(tranData);
			String hmacSign = HmacSHASign.hmacSHASign(tranData, md5);
			if (!hmacSign.equals(signData)) {
				logger.info("签名不正确");
			}
			payService.disposeNotifyInfo(base64DecodeData);
			
			request.setAttribute("orderNo", sn);
			request.setAttribute("orderAmt", request.getParameter("orderAmt"));
		} catch (Exception e) {
			logger.error("更改订单状态失败", e);
		} // 改变订单状态
		
		return "payment/notify";
	}

	/**
	 * 手机APP支付回调
	 * @param sn
	 * @param request
	 * @param model
	 * @return
	 * @Desc 支付回调更改订单状态
	 */
	@RequestMapping(value = "/callBackNotify/{notifyMethod}/{sn}")
	@ResponseBody
	public String callBackNotify(@PathVariable NotifyMethod notifyMethod, @PathVariable String sn, HttpServletRequest request, ModelMap model) {
		try {
			String signData = request.getParameter("signData");
			String tranData = request.getParameter("tranData");
			
			logger.info("请求支付接口callBackNotify返回通知signData:" + signData);
			logger.info("请求支付接口callBackNotify返回通知tranData:" + tranData);
			
			String base64DecodeData = "";
			if (null != tranData && !"".equals(tranData)) {
				base64DecodeData = Base64Util.getFromBase64(tranData);
				//base64DecodeData = new String(B.Base64Decode(tranData));
			}
			String hmacSign = HmacSHASign.hmacSHASign(base64DecodeData, md5);
			if (!hmacSign.equals(signData)) {
				logger.info("手机控件支付签名不正确");
				return "签名不正确!";
			}
			payService.disposeNotifyInfo(base64DecodeData);
			logger.info("异步回调 订单号：" + sn + "支付成功");
		} catch (Exception e) {
			logger.error("更改订单状态失败", e);
		}
		return "success";
	}
	

	/***
	 * 获取通知URL**
	 * 
	 * @param sn
	 *            编号
	 * @param notifyMethod
	 *            通知方法
	 * @return 通知URL siteUrl
	 */

	protected String getNotifyUrl(String order, NotifyMethod notifyMethod) {
		if (notifyMethod.equals(NotifyMethod.sync)) {
			return siteUrl + "/f/pay/notify/" + NotifyMethod.sync + "/" + order;
		}
		if (notifyMethod.equals(NotifyMethod.async)) {
			return siteUrl + "/f/pay/callBackNotify/" + NotifyMethod.async + "/" + order;
		}
		return siteUrl + "/f/pay/notify/" + NotifyMethod.general + "/" + order;
	}

	/**
	 * 
	 * @param orderId
	 *            订单号
	 * @param orderAmt
	 *            订单金额
	 * @param goodsName
	 *            商品名称
	 * @param goodsDesc
	 *            商品描述
	 * @param mallUserName
	 *            商城用户名
	 * @param bankId
	 *            银行编号
	 * @return
	 */
	@RequestMapping("/api/wap")
	public String wapPay(String orderId, String orderAmt, String goodsName, String goodsDesc, String mallUserName, String bankId, HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) {
		try {
			String remark = "B2C";
			// 币种
			String curType = "CNY";
			String transactionData = "<orderNo>" + orderId + "</orderNo><orderAmt>" + orderAmt + "</orderAmt><goodsName>" + goodsName + "</goodsName><goodsDesc>" + goodsDesc
					+ "</goodsDesc><mallUserName>" + mallUserName + "</mallUserName>" + "<remark>" + remark + "</remark>";
			// WAP_PAY_URL
			String tranData = "<?xml version=\"1.0\" encoding=\"utf-8\"?><B2CReq><merId>" + WAP_PAY_MER_ID + "</merId><curType>" + curType + "</curType>" + transactionData + "<returnURL>"
					+ MER_RETURN_URL + "</returnURL><notifyURL>" + notifyURL
					+ "</notifyURL><merchantAccountType>0002</merchantAccountType><serviceCode>2015081709354917979</serviceCode><showUrl>http://localhost:8080/aaa</showUrl><orderName>商品名称</orderName><reserved1></reserved1><reserved2></reserved2></B2CReq>";
			String merSignMsg = "";
			if ("MD5".equals(WAP_SIGN_TYPE)) {
				merSignMsg = HmacSHASign.hmacSHASign(tranData, HMD5_PASSWORD);
			}
			String tranDataBase64 = Base64Util.getBase64(tranData);
			// HttpClient client = HttpClients.createDefault();
			// HttpPost httpPost = new HttpPost(WAP_PAY_URL);
			// httpPost.addHeader("Connection", "close");
			// List<NameValuePair> nvs = new ArrayList<NameValuePair>();
			// nvs.add(new BasicNameValuePair("interfaceName",
			// PAY_INTERFACE_NAME));
			// nvs.add(new BasicNameValuePair("bankId", bankId));
			// nvs.add(new BasicNameValuePair("tranData", tranDataBase64));
			// nvs.add(new BasicNameValuePair("merSignMsg", merSignMsg));
			// nvs.add(new BasicNameValuePair("merchantId", MER_ID));
			// httpPost.setEntity(new UrlEncodedFormEntity(nvs,"utf-8"));
			// responseBody = client.execute(httpPost);
			// result = IOUtils.toString(responseBody.getEntity().getContent());
			// System.out.println("entity:"+result);
			modelMap.put("interfaceName", PAY_INTERFACE_NAME);
			modelMap.put("bankId", bankId);
			modelMap.put("tranData", tranDataBase64);
			modelMap.put("merSignMsg", merSignMsg);
			modelMap.put("merchantId", MER_ID);
			modelMap.put("URL", WAP_PAY_URL);
			// modelMap.put("tranData_", tranData);
			// request.setAttribute("tranData_", tranData);
		} catch (Exception e) {
			logger.error("手机支付报错!", e);
		}
		return "payment/wapPay";
	}

	@RequestMapping("/api/notify")
	@ResponseBody
	public ReturnMsg<Object> wapNotify() {
		ReturnMsg<Object> msg = new ReturnMsg<Object>();
		return msg;
	}

	/**
	 * 手机APP 获取银联支付 tn 号
	 * 
	 * @param orderAmt
	 * @param orderId
	 * @param goodsName
	 * @param goodsDesc
	 * @param mallUserName
	 * @return
	 */
	@RequestMapping("/getMobilePayTn")
	@ResponseBody
	public ReturnMsg<AppPayResDto> getMobilePayTn(String orderAmt, String orderNo, String goodsName, String goodsDesc, String sessionId) {
		ReturnMsg<AppPayResDto> returnMsg = new ReturnMsg<AppPayResDto>();
		AppPayResDto appPayResDto = null;
		try {
			logger.info("==============begin 手机APP进入获取银联支付tn号 getMobilePayTn===============");

			logger.info("手机App请求订单号 orderId ====" + orderNo);
			logger.info("手机App请求订单金额 orderAmt ====" + orderAmt);
			logger.info("手机App请求订单名称 goodsName ====" + goodsName);
			logger.info("手机App请求订单说明 goodsDesc ====" + goodsDesc);
			logger.info("手机App请求sessionId ====" + sessionId);
			OrderTable orderTable =	orderService.getOrderByOrderNo(orderNo);
			BigDecimal appAmt =	new BigDecimal(orderAmt);
			if (appAmt.compareTo(orderTable.getAmount())!=0){
//			if (appAmt.equals(orderTable.getAmount())){
				returnMsg.setCode(ExceptionEnum.orderAmount_different.getIndex());
				returnMsg.setStatus(false);
				returnMsg.setMsg(ExceptionEnum.orderAmount_different.getValue());
				logger.info("手机App请求getMobilePayTn方法" +ExceptionEnum.orderAmount_different.getValue() + "appAmt="+appAmt.toString() + ",amount="+orderTable.getAmount().toString() );
				logger.info("==============end 手机APP进入获取银联支付tn号 getMobilePayTn===============");
				return returnMsg;
				
			}
			
			if (StringUtils.isEmpty(sessionId) || StringUtils.isEmpty(orderAmt) || StringUtils.isEmpty(goodsName) || StringUtils.isEmpty(orderNo)){
				returnMsg.setCode(ExceptionEnum.param_not_null.getIndex());
				returnMsg.setStatus(false);
				returnMsg.setMsg(ExceptionEnum.param_not_null.getValue());
				logger.info("手机App请求getMobilePayTn方法返回参数为空，不能获取TN ====" );
				logger.info("==============end 手机APP进入获取银联支付tn号 getMobilePayTn===============");
				return returnMsg;
			}
			MemMember member = memMemberService.getMemMemberBySessionId(sessionId);
			if (null == member){
				returnMsg.setCode(ExceptionEnum.member_not_exist.getIndex());
				returnMsg.setStatus(false);
				returnMsg.setMsg(ExceptionEnum.member_not_exist.getValue());
				logger.info("手机App请求getMobilePayTn方法用户不存在，不能获取TN ====" );
				logger.info("==============end 手机APP进入获取银联支付tn号 getMobilePayTn===============");
				return returnMsg;
			}
			
			Map<String, String> paramMap = appPayUtils.getUnionMap(orderAmt, orderNo, goodsName, goodsDesc, member.getUsername());
			HttpCallResult result = HttpCall.post(UPOP_APP_PAY_URL, paramMap);
			String content = "";
			if (null != result) {
				content = result.getContent();
			}
			appPayResDto = JacksonUtil.readValue(content, AppPayResDto.class);
			//boolean flag = false;
			if ("3".equals(appPayResDto.getCode())){
				returnMsg.setStatus(false);
				returnMsg.setData(appPayResDto);
				returnMsg.setMsg(appPayResDto.getMsg());
			}
			returnMsg.setStatus(true);
			returnMsg.setData(appPayResDto);
			returnMsg.setMsg("获取Tn成功");
			
			logger.info("手机App请求getMobilePayTn方法返回参数 ====" + content);
			logger.info("==============end 手机APP进入获取银联支付tn号 getMobilePayTn===============");
		} catch (Exception ex) {
			logger.error("手机控件支付获取Tn报错!", ex);
			ex.printStackTrace();
		}
		return returnMsg;
	}

	/**
	 * 通知方法
	 */
	public enum NotifyMethod {

		/** 通用 */
		general,

		/** 同步 */
		sync,

		/** 异步 */
		async
	}
}
