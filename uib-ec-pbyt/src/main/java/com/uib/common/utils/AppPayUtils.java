package com.uib.common.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.chinagpay.mer.bean.DigestUtil;
import com.chinagpay.mer.bean.ProcessMessage;
import com.uib.pay.web.PayController.NotifyMethod;


@Component
public class AppPayUtils {
	
	/*****************************************************************
	 * mMode参数解释： "00" - 启动银联正式环境 "01" - 连接银联测试环境
	 *****************************************************************/
	public static final String MODE = "00";

	private static String SIGN_TYPE = "MD5";
	private final static String curType = "CNY";
	@Value("${siteUrl}")
	private  String RETURN_URL;
	@Value("${siteUrl}")
	private  String NOTIFY_URL;

	private static String interfaceName = "PayOrder_NS";
	private static String merId = "M100000460";

	public static String getInterfaceName() {
		return interfaceName;
	}

	public static String getMerId() {
		return merId;
	}

	private final static String remark = "B2C";


	private static StringBuffer getTransactionData(String orderAmt, String orderId, String goodsName, String goodsDesc, String mallUserName) {

		StringBuffer sb = new StringBuffer();
		sb.append("<orderNo>");
		sb.append(orderId);
		sb.append("</orderNo><orderAmt>");
		sb.append(orderAmt);
		sb.append("</orderAmt><goodsName>");
		sb.append(goodsName);
		sb.append("</goodsName><goodsDesc>");
		sb.append(goodsDesc);
		sb.append("</goodsDesc><mallUserName>");
		sb.append(mallUserName);
		sb.append("</mallUserName><remark>");
		sb.append(remark);
		sb.append("</remark>");
		return sb;
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
			return RETURN_URL + "/f/pay/notify/" + NotifyMethod.sync + "/" + order;
		}
		if (notifyMethod.equals(NotifyMethod.async)) {
			return NOTIFY_URL + "/f/pay/callBackNotify/" + NotifyMethod.async + "/" + order;
		}
		return RETURN_URL + "/f/pay/notify/" + NotifyMethod.general + "/" + order;
	}

	private  String getTranData(String orderAmt, String orderId, String goodsName, String goodsDesc, String mallUserName) {

		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?><B2CReq><merId>");
		sb.append(merId);
		sb.append("</merId><curType>");
		sb.append(curType);
		sb.append("</curType><returnURL>");
		sb.append(getNotifyUrl(orderId, NotifyMethod.async));
		sb.append("</returnURL><notifyURL>");
		sb.append(getNotifyUrl(orderId, NotifyMethod.async));
		sb.append("</notifyURL>");
		sb.append(getTransactionData(orderAmt, orderId, goodsName, goodsDesc, mallUserName));
		sb.append("</B2CReq>");
		return sb.toString();

	}

	public Map<String, String> getUnionMap(String orderAmt, String orderId, String goodsName, String goodsDesc, String mallUserName) {
		Map<String, String> paramMap = new HashMap<String, String>();
		String tranData = getTranData(orderAmt, orderId, goodsName, goodsDesc, mallUserName);
		paramMap.put("interfaceName", getInterfaceName());
		paramMap.put("tranData", ProcessMessage.Base64Encode(tranData.getBytes()));

		String merSignMsg = "";
		if (SIGN_TYPE.equals("MD5")) {
			merSignMsg = DigestUtil.hmacSign(tranData, "123456");
		}
		
		paramMap.put("merSignMsg", merSignMsg);
		paramMap.put("merchantId", getMerId());
		return paramMap;
	}
}
