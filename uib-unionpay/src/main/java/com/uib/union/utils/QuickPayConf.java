package com.uib.union.utils;

import org.springframework.stereotype.Component;

@Component
public class QuickPayConf {
	// 版本号
		public final static String version = "1.0.0";

		// 编码方式
		public final static String charset = "UTF-8";
		
		public final static String MERCHANT_SHORT_NAME = "联保";

		// 基础网址（请按相应环境切换）

		/* 测试环境 */
		private final static String UPOP_BASE_URL = "http://202.101.25.184/UpopWeb/api/";

		/* PM环境（准生产环境） */
		//private final static String UPOP_BASE_URL = "https://www.epay.lxdns.com/UpopWeb/api/";

		/* 生产环境 */
		//private final static String UPOP_BASE_URL = "https://unionpaysecure.com/api/";

		// 支付网址
		public final static String gateWay = UPOP_BASE_URL + "Pay.action";

		// 后续交易网址
		public final static String backStagegateWay = UPOP_BASE_URL + "BSPay.action";

		// 查询网址
		public final static String queryUrl = UPOP_BASE_URL + "Query.action";

		// 商户代码
		public final static String merCode = "105550149170027";

		// 商户名称
		public final static String merName = "用户商城名称";

		public final static String merFrontEndUrl = "http://www.yourdomain.com/your_path/yourFrontEndUrl";

		public final static String merBackEndUrl = "http://www.yourdomain.com/your_path/yourBackEndUrl";

		// 加密方式
		public final static String signType = "MD5";
		public final static String signType_SHA1withRSA = "SHA1withRSA";

		// 商城密匙，需要和银联商户网站上配置的一样
		public final static String securityKey = "88888888";

		// 签名
		public final static String signature = "signature";
		public final static String signMethod = "signMethod";

		//组装消费请求包
		public final static String[] reqVo = new String[]{
				"version",
	            "charset",
	            "transType",
	            "origQid",
	            "merId",
	            "merAbbr",
	            "acqCode",
	            "merCode",
	            "commodityUrl",
	            "commodityName",
	            "commodityUnitPrice",
	            "commodityQuantity",
	            "commodityDiscount",
	            "transferFee",
	            "orderNumber",
	            "orderAmount",
	            "orderCurrency",
	            "orderTime",
	            "customerIp",
	            "customerName",
	            "defaultPayType",
	            "defaultBankNumber",
	            "transTimeout",
	            "frontEndUrl",
	            "backEndUrl",
	            "merReserved"
		};

		public final static String[] notifyVo = new String[]{
	            "charset",
	            "cupReserved",
	            "exchangeDate",
	            "exchangeRate",
	            "merAbbr",
	            "merId",
	            "orderAmount",
	            "orderCurrency",
	            "orderNumber",
	            "qid",
	            "respCode",
	            "respMsg",
	            "respTime",
	            "settleAmount",
	            "settleCurrency",
	            "settleDate",
	            "traceNumber",
	            "traceTime",
	            "transType",
	            "version"
		};

		public final static String[] queryVo = new String[]{
			"version",
			"charset",
			"transType",
			"merId",
			"orderNumber",
			"orderTime",
			"merReserved"
		};

}
