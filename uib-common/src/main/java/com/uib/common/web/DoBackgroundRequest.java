package com.uib.common.web;


import java.util.HashMap;
import java.util.Map;

import com.uib.common.utils.Base64Util;


public class DoBackgroundRequest {
	public String doRefundRequest(Map message) {

		String interFace = (String) message.get("interfaceName");
		String version = (String) message.get("version");
		String tranData = (String) message.get("tranData");
		String merSignMsg = (String) message.get("merSignMsg");
		String merchantId = (String) message.get("merchantId");

		Map<String, String> params = new HashMap<String, String>();
		params.put("interfaceName", interFace);
		params.put("version", version);
		params.put("tranData", tranData);
		params.put("merSignMsg", merSignMsg);
		params.put("merchantId", merchantId);

		try {
			HttpCallResult result = HttpCall.post((String) message.get("URL"), params);
			String content = result.getContent();
			int re_code = result.getStatusCode();
			if (re_code == 200) {
				System.out.println("-----------------退款接口返回数据--BEGIN--------------------");
				System.out.println("退款返回的数据：" + content);
				String s = content.split("&")[2];
				String tranData1 = s.substring(s.indexOf("=") + 1, s.length());
			//	String st = new String(ProcessMessage.Base64Decode(tranData1));
				//System.out.println(st);
				System.out.println("-----------------退款接口返回数据--END--------------------");
				//content = content + "<br/> 明文数据：<xmp>" + st + "</xmp>";
			}
			return content;// new
							// String(ProcessMessage.Base64Decode(mypost.getResponseBodyAsString()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String doQueryRequest(Map message) {

		String interFace = (String) message.get("interfaceName");
		String version = (String) message.get("version");
		String tranData = (String) message.get("tranData");
		String merSignMsg = (String) message.get("merSignMsg");
		String merchantId = (String) message.get("merchantId");

		
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("interfaceName", interFace);
		params.put("version", version);
		params.put("tranData", tranData);
		params.put("merSignMsg", merSignMsg);
		params.put("merchantId", merchantId);
		try {
			HttpCallResult result = HttpCall.post((String) message.get("URL"), params);
			String content = result.getContent();
			int re_code = result.getStatusCode();

			if (re_code == 200) {
				System.out.println("-----------------查证接口返回数据--BEGIN--------------------");
				// int end = mypost.getResponseBodyAsString().indexOf("&");
				// if (end > 0)
				// System.out.println(new
				// String(ProcessMessage.Base64Decode(mypost.getResponseBodyAsString().substring(0,
				// end))) + mypost.getResponseBodyAsString().substring(end));
				// else {
				System.out.println(content);
				// }
				System.out.println("-----------------查证接口返回数据--END--------------------");
			}
			// int end = mypost.getResponseBodyAsString().indexOf("&");
			// if (end > 0) {
			// return new
			// String(ProcessMessage.Base64Decode(mypost.getResponseBodyAsString()));
			// }
			return content;
		}  catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String doSendGoodsRequest(Map message) {

		String interFace = (String) message.get("interfaceName");
		String version = (String) message.get("version");
		String tranData = (String) message.get("tranData");
		String merSignMsg = (String) message.get("merSignMsg");
		String merchantId = (String) message.get("merchantId");

		Map<String, String> params = new HashMap<String, String>();
		params.put("interfaceName", interFace);
		params.put("version", version);
		params.put("tranData", tranData);
		params.put("merSignMsg", merSignMsg);
		params.put("merchantId", merchantId);
		try {
			HttpCallResult result = HttpCall.post((String) message.get("URL"), params);
			String content = result.getContent();
			int re_code = result.getStatusCode();

			if (re_code == 200) {
				System.out.println("-----------------发货接口返回数据--BEGIN--------------------");
				System.out.println(content);
				System.out.println("-----------------发货接口返回数据--END--------------------");
			}
			return new String(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		String res = new String(
				"PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iZ2IyMzEyIiA/PjxCMkNSZXM+PHRyYW5TZXJpYWxObz5aRjIwMTMxMTI1MTAwMDAyMjQzMDwvdHJhblNlcmlhbE5vPjxvcmRlckFtdD4yLjAwPC9vcmRlckFtdD48Y3VyVHlwZT5DTlk8L2N1clR5cGU+PG1lcmNoYW50SWQ+TTEwMDAwMDAyMDwvbWVyY2hhbnRJZD48b3JkZXJObz4wNjQ1MTc5NzM2PC9vcmRlck5vPjx0cmFuU3RhdD4xPC90cmFuU3RhdD48cmVtYXJrPjM1PC9yZW1hcms+PHRyYW5UaW1lPjIwMTMxMTI1MTgxOTEyPC90cmFuVGltZT48L0IyQ1Jlcz4=");
		System.out.println(Base64Util.getFromBase64(res));
	}
}