package com.uib.union.payment.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.chinagpay.mer.bean.HmacSHA256Sign;
import com.uib.common.utils.Base64Util;
import com.uib.common.utils.BeanAndMapUtil;
import com.uib.common.utils.DateUtil;
import com.uib.common.utils.HmacSHASign;
import com.uib.common.utils.XmlUtil;
import com.uib.common.web.B2CReq;
import com.uib.common.web.B2CRes;
import com.uib.common.web.HttpCall;
import com.uib.common.web.HttpCallResult;
import com.uib.union.common.SystemConstant;
import com.uib.union.enums.AccessTypeEnum;
import com.uib.union.enums.ChannelTypeEnum;
import com.uib.union.enums.UibTranStatEnum;
import com.uib.union.enums.UibCancelStateEnum;
import com.uib.union.enums.UibRefundStateEnum;
import com.uib.union.enums.PayMethodEnum;
import com.uib.union.enums.TxnSubTypeEnum;
import com.uib.union.enums.TxnTypeEnum;
import com.uib.union.merchant.dao.MerchantInfoDao;
import com.uib.union.merchant.pojo.MerchantInfo;
import com.uib.union.payment.dao.CancelOrderDao;
import com.uib.union.payment.dao.PaymentOrderDao;
import com.uib.union.payment.dao.PaymentOrderLogDao;
import com.uib.union.payment.dao.RefundOrderDao;
import com.uib.union.payment.dto.AcpsdkPayRequest;
import com.uib.union.payment.dto.AcpsdkPayResponse;
import com.uib.union.payment.dto.AcpsdkReturnGoodsRepsonse;
import com.uib.union.payment.dto.MerchantNotifyDto;
import com.uib.union.payment.dto.PaymentOrderDto;
import com.uib.union.payment.dto.RefundB2cReq;
import com.uib.union.payment.dto.RefundGoodsDto;
import com.uib.union.payment.dto.ReturnRefundTranData;
import com.uib.union.payment.pojo.CancelOrder;
import com.uib.union.payment.pojo.PaymentOrder;
import com.uib.union.payment.pojo.PaymentOrderLog;
import com.uib.union.payment.pojo.RefundOrder;
import com.uib.union.payment.service.AcpsdkPayService;
import com.uib.union.utils.AcpSdkUtil;
import com.uib.union.utils.OrderNoGenerateUtil;

/**
 * 全渠道支付
 * 
 * @author kevin
 */
@Component
public class AcpsdkPayServiceImpl implements AcpsdkPayService {

	private static final Logger logger = Logger.getLogger("rootLogger");

	@Value("${union_allpay_backurl}")
	private String UNION_ALLPAY_BACKURL;

	@Value("${union_allpay_fronturl}")
	private String UNION_ALLPAY_FRONTURL;

	@Value("${union_allpay_merid}")
	private String UNION_ALLPAY_MERID;

	@Value("${union_allpay_backTransReq}")
	private String UNION_ALLPAY_BACKTRANSREQ;

	// 退货后台通知
	@Value("union_allpay_returnGoodsNotify")
	private String UNION_ALLPAY_RETURNGOODSNOTIFY;

	@Autowired
	private PaymentOrderDao paymentOrderDao;

	@Autowired
	private PaymentOrderLogDao paymentOrderLogDao;

	@Autowired
	private MerchantInfoDao merchantInfoDao;

	@Autowired
	private RefundOrderDao refundOrderDao;

	@Autowired
	private CancelOrderDao cancelOrderDao;

	@Override
	public Map<String, String> saveAcpSdkPay(PaymentOrderDto paymentOrderDto) throws Exception {
		Timestamp timestamp = new Timestamp(new Date().getTime());

		B2CReq b2cReq = (B2CReq) XmlUtil.xmlStrToBean(paymentOrderDto.getTranData(), B2CReq.class);

		Map<String, String> returnMap = new HashMap<String, String>();

		if (null == b2cReq) {
			returnMap.put("error", "参数错误!");
			return returnMap;
		} else {
			if (null == b2cReq.getMerId()) {
				returnMap.put("error", "商户号为空!");
				return returnMap;
			}
			if (StringUtils.isEmpty(b2cReq.getNotifyURL())) {
				returnMap.put("error", "返回通知URL不能为空!");
				return returnMap;
			}
			MerchantInfo merchantInfo = merchantInfoDao.getMerchantInfoByMerchantCode(b2cReq.getMerId());
			if (null == merchantInfo) {
				returnMap.put("error", "商户号不存在!");
				return returnMap;
			}

			logger.info("hmd5Password ====" + merchantInfo.getHmd5Password());
			String merSignMsg = new String(paymentOrderDto.getMerSignMsg());
			String hmacSign = HmacSHASign.hmacSHASign(paymentOrderDto.getTranData(), merchantInfo.getHmd5Password());
			//String hmacSign = HmacSHA256Sign.hmacSHA256(paymentOrderDto.getTranData(), merchantInfo.getHmd5Password());
			logger.info("paymentOrderDto.getMerSignMsg() ====" + merSignMsg);
			logger.info("hmacSign ====" + hmacSign);
			logger.info("paymentOrderDto.getTranData()" + paymentOrderDto.getTranData());
			if (!hmacSign.equals(paymentOrderDto.getMerSignMsg())) {
				returnMap.put("error", "签名不正确!");
				return returnMap;
			}
		}
		PaymentOrder repeatOrder = paymentOrderDao.getPaymentOrderByOrderNo(b2cReq.getOrderNo(),
				paymentOrderDto.getMerchantId());

		if (null == repeatOrder
				|| (null != repeatOrder && !repeatOrder.getTranStat().equals(UibTranStatEnum.ALREADY_PAY.getDesc()))) {
			String newDate = DateUtil.getYYYYMMDDHHMMSSDate(new Date());

			String paymentNo = OrderNoGenerateUtil.getOrderNo();

			PaymentOrder paymentOrder = new PaymentOrder();
			BeanUtils.copyProperties(paymentOrder, paymentOrderDto);
			paymentOrder.setBankId(paymentOrderDto.getBankId());
			paymentOrder.setOrderNo(b2cReq.getOrderNo());
			paymentOrder.setCurType(b2cReq.getCurType());
			paymentOrder.setGoodsName(b2cReq.getGoodsName());
			paymentOrder.setGoodsDesc(b2cReq.getGoodsDesc());
			BigDecimal orderamt = new BigDecimal(b2cReq.getOrderAmt());
			paymentOrder.setOrderAmt(orderamt);
			paymentOrder.setMailUserName(b2cReq.getMallUserName());
			paymentOrder.setPaymentNo(paymentNo);
			paymentOrder.setTranStat(UibTranStatEnum.NOT_PAY.getDesc());
			paymentOrder.setTranDate(timestamp);
			paymentOrder.setReturnUrl(b2cReq.getReturnURL());
			paymentOrder.setNotifyUrl(b2cReq.getNotifyURL());
			paymentOrder.setMerchantCode(b2cReq.getMerId());
			paymentOrder.setPayMethod(PayMethodEnum.UNION_ALL_PAY.getDesc());

			paymentOrderDao.savePaymentOrder(paymentOrder);

			PaymentOrderLog paymentOrderLog = new PaymentOrderLog();
			paymentOrderLog.setOrderNo(paymentOrder.getOrderNo());
			paymentOrderLog.setOrderAmt(orderamt);
			paymentOrderLog.setCreateTime(timestamp);
			paymentOrderLog.setPaymentNo(paymentNo);
			paymentOrderLog.setMessage("请求支付中");
			paymentOrderLog.setTranStat(UibTranStatEnum.NOT_PAY.getDesc());
			paymentOrderLog.setPayMethod(PayMethodEnum.UNION_ALL_PAY.getDesc());
			paymentOrderLogDao.savePaymentOrderLog(paymentOrderLog);

			Map<String, String> contentData = new HashMap<String, String>();
			// 版本号
			contentData.put("version", SystemConstant.version);
			// 字符集编码 默认"UTF-8"
			String encoding = "UTF-8";
			contentData.put("encoding", encoding);
			// 证书ID 调用 SDK 可自动获取并赋值
			contentData.put("certId", " ");
			// 签名 调用 SDK 可自动运算签名并赋值
			contentData.put("signature", " ");
			// 签名方法 01 RSA
			contentData.put("signMethod", SystemConstant.SIGN_METHOD_RSA);
			// 交易类型 01-消费
			contentData.put("txnType", TxnTypeEnum.CONSUMPTION.getDesc());
			// 交易子类型 01:自助消费 02:订购 03:分期付款
			contentData.put("txnSubType", TxnSubTypeEnum.TRADE_CHILD.getDesc());
			// 业务类型 000201 B2C网关支付
			contentData.put("bizType", SystemConstant.BIZTYPE_B2C);
			// 渠道类型 07-互联网渠道
			contentData.put("channelType", SystemConstant.CHANNEL_TYPE_NET);
			// 商户/收单前台接收地址 选送
			// 后台服务对应的写法参照 FrontRcvResponse.java
			contentData.put("frontUrl", UNION_ALLPAY_FRONTURL);
			// 商户/收单后台接收地址 必送
			// 后台服务对应的写法参照 BackRcvResponse.java
			contentData.put("backUrl", UNION_ALLPAY_BACKURL);
			// 接入类型:商户接入填0 0- 商户 ， 1： 收单， 2：平台商户
			contentData.put("accessType", AccessTypeEnum.MERCHANT.getDesc());
			// 商户号码
			contentData.put("merId", UNION_ALLPAY_MERID);
			// 订单号 商户根据自己规则定义生成，每订单日期内不重复
			contentData.put("orderId", paymentOrder.getPaymentNo());
			// 订单发送时间 格式： YYYYMMDDhhmmss 商户发送交易时间，根据自己系统或平台生成
			contentData.put("txnTime", newDate);
			
			/**
			 * 与银联交互参数单位为分
			 */
			BigDecimal tmepbig = new BigDecimal(100);
			int amt =paymentOrder.getOrderAmt().multiply(tmepbig).intValue();
			//int amt = (int) (paymentOrder.getOrderAmt().doubleValue() * 100);
			// 交易金额 分
			contentData.put("txnAmt", String.valueOf(amt));
			// 交易币种
			contentData.put("currencyCode", SystemConstant.CURRENCYCODE_RMB);

			returnMap = AcpSdkUtil.signData(contentData);
		} else {
			returnMap.put("error", "该商户所属订单号已存在或,该订单号已支付!");
			return returnMap;
		}

		// Map<String, Object> map = new HashMap<String, Object>();
		// map.put("repeatOrder", repeatOrder);
		return returnMap;
	}

	@Override
	public MerchantNotifyDto unionAllPayNotify(AcpsdkPayResponse acpsdkPayResponse) throws Exception {
		MerchantNotifyDto merchantNotifyDto = new MerchantNotifyDto();
		if (null != acpsdkPayResponse) {
			PaymentOrder payOrder = paymentOrderDao.getPaymentOrderByPaymentNo(acpsdkPayResponse.getOrderId());
			if (null != payOrder) {

				payOrder.setTranStat(UibTranStatEnum.ALREADY_PAY.getDesc());
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("threePaymentNo", acpsdkPayResponse.getQueryId());
				map.put("paymentNo", acpsdkPayResponse.getOrderId());
				map.put("tranStat", UibTranStatEnum.ALREADY_PAY.getDesc());
				map.put("queryId", acpsdkPayResponse.getQueryId());
				paymentOrderDao.updatePaymentOrder(map);
				Timestamp timestamp = new Timestamp(new Date().getTime());
				PaymentOrderLog payLog = new PaymentOrderLog();
				payLog.setCreateTime(timestamp);
				payLog.setPaymentNo(acpsdkPayResponse.getOrderId());
				// payLog.setMessage(upNotifyResponseDto.getRespMsg());
				payLog.setThreePaymentNo(acpsdkPayResponse.getTn());
				
				/**
				 * 获取
				 */
				double orderAmt = (Double.parseDouble(acpsdkPayResponse.getTxnAmt()) / 100);
				BigDecimal orderBigAmt = new BigDecimal(orderAmt);
				payLog.setOrderAmt(orderBigAmt);
				payLog.setTranStat(UibTranStatEnum.ALREADY_PAY.getDesc());

				paymentOrderLogDao.savePaymentOrderLog(payLog);

				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
				MerchantInfo merchantInfo = merchantInfoDao.getMerchantInfoByMerchantCode(payOrder.getMerchantCode());

				if (null != merchantInfo) {
					String orderNo = payOrder.getOrderNo();
					B2CRes mnRes = new B2CRes();
					mnRes.setMerchantId(payOrder.getMerchantCode());
					mnRes.setCurType(payOrder.getCurType());
					mnRes.setOrderNo(orderNo);
					mnRes.setOrderAmt(String.valueOf(payOrder.getOrderAmt()));
					mnRes.setRemark(payOrder.getRemark());

					if (null != payOrder.getTranDate()) {
						Date date = new Date(payOrder.getTranDate().getTime());
						mnRes.setTranTime(sdf.format(date));
					}

					mnRes.setTranSerialNo(payOrder.getPaymentNo());
					mnRes.setTranStat(payOrder.getTranStat());
					String tranDataXml = XmlUtil.parseBean4Xml(mnRes);

					// String hmacSign =
					// DigestUtil.hmacSign(tranDataXml,merchantInfo.getHmd5Password());
					//String hmacSign = HmacSHA256Sign.hmacSHA256(tranDataXml, merchantInfo.getHmd5Password());
					String hmacSign =HmacSHASign.hmacSHASign(tranDataXml, merchantInfo.getHmd5Password());
					merchantNotifyDto.setOrderAmt(String.valueOf(payOrder.getOrderAmt()));
					merchantNotifyDto.setOrderNo(orderNo);
					merchantNotifyDto.setInterfaceName("支付接口");
					merchantNotifyDto.setVersion("1.0");
					merchantNotifyDto.setTranData(tranDataXml);
					merchantNotifyDto.setSignData(hmacSign);
					merchantNotifyDto.setReturnUrl(payOrder.getReturnUrl());
					merchantNotifyDto.setNotifyUrl(payOrder.getNotifyUrl());
				}
			}
		}
		return merchantNotifyDto;
	}

	@Override
	public Map<String, String> unionAllReturnGoods(RefundGoodsDto refundGoodsDto) throws Exception {
		Map<String, String> returnMap = new HashMap<String, String>();
		String base64TranDataDecode = "";
		String tranData = refundGoodsDto.getTranData();
		if (!StringUtils.isEmpty(tranData)) {
			base64TranDataDecode = Base64Util.getFromBase64(tranData);
		}
		RefundB2cReq refundB2cReq = (RefundB2cReq) XmlUtil.xmlStrToBean(base64TranDataDecode, RefundB2cReq.class);

		returnMap = validateReturnParam(returnMap,refundB2cReq,refundGoodsDto);
		if (null != returnMap ){
			return returnMap;
		}

		PaymentOrder payOrder = paymentOrderDao.getPaymentOrderByOrderNo(refundB2cReq.getOrderNo(),
				refundB2cReq.getMerchantId());

		if (null != payOrder) {
			RefundOrder refundOrder = refundOrderDao.getRefundOrderByPaymentNo(payOrder.getPaymentNo());
			String refundNo = "";
			if (null == refundOrder) {
				refundOrder = new RefundOrder();
				refundNo = "R" + OrderNoGenerateUtil.getOrderNo();
				refundOrder.setPaymentNo(payOrder.getPaymentNo());
				refundOrder.setRefundNo(refundNo);
				refundOrder.setRefundAmt(Double.parseDouble(refundB2cReq.getRefundAmt()));
				refundOrder.setRemark("");
				refundOrder.setRefundState(UibRefundStateEnum.RETURN_EXCE.getDesc());
				refundOrder.setNotifyUrl(refundB2cReq.getNotifyURL());
				refundOrder.setOrderNo(refundB2cReq.getOrderNo());
				refundOrder.setPayType(SystemConstant.PAY_TYPE_UNIONPAY);
				refundOrder.setMerchantCode(refundB2cReq.getMerchantId());
				Date date = new Date();
				refundOrder.setRefundDate(new Timestamp(date.getTime()));
				refundOrderDao.saveRefundOrder(refundOrder);
			} else {
				refundNo = refundOrder.getRefundNo();
			}

			String orderId = payOrder.getPaymentNo();// --商户订单号

			Map<String, String> contentData = packReturnGoods(payOrder, refundNo);

			// 待查询交易的流水号
			// contentData.put("queryId", payOrder.getQueryId());// C

			Map<String, String> resmap = AcpSdkUtil.submitDate(contentData, UNION_ALLPAY_BACKTRANSREQ);

			logger.info("unionAllReturnGoods 请求银联全渠道支付退货接口返回信息: " + resmap.toString());
			String respCode = resmap.get("respCode");
			logger.info("请求银联全渠道支付退货接口 响应状态:" + respCode);
			if (!StringUtils.isEmpty(respCode)) {
				if (!"00".equals(respCode)) {// 如果服务器返回不成功，结束流程，返回错误信息
					logger.info("请求银联全渠道支付退货接口处理不成功");
					returnMap.put("code", "-1");
					returnMap.put("errorMsg", "退货请求审请处理不成功");
					// System.out.println("处理不成功");
				} else {
					Map<String, String> paramMap = new HashMap<String, String>();
					paramMap.put("refundState", UibRefundStateEnum.RETURN_SUCCESS.getDesc());
					paramMap.put("paymentNo", orderId);
					paramMap.put("queryId", resmap.get("queryId").toString());
					refundOrderDao.updateRefundOrder(paramMap);
					returnMap.put("code", "1");
					returnMap.put("errorMsg", "处理成功!");
				}
			}
		}
		// 格式如下：{子域名1=值&子域名2=值&子域名3=值} 子域： origTxnType N2原交易类型余额查询时必送
		// contentData.put("reserved", "");// O
		return returnMap;
	}

	/**
	 * 验证退货请求参数
	 */
	private Map<String,String> validateReturnParam(Map<String, String> returnMap, RefundB2cReq refundB2cReq,RefundGoodsDto refundGoodsDto){
		if (null == refundB2cReq) {
			returnMap.put("code", "-1");
			returnMap.put("errorMsg", "参数错误!");
			return returnMap;
		}
		if (null == refundB2cReq.getMerchantId()) {
			returnMap.put("code", "-1");
			returnMap.put("errorMsg", "商户号为空!");
			return returnMap;
		}
		if (StringUtils.isEmpty(refundB2cReq.getNotifyURL())) {
			returnMap.put("code", "-1");
			returnMap.put("errorMsg", "返回通知URL不能为空!");
			return returnMap;
		}
		MerchantInfo merchantInfo = merchantInfoDao.getMerchantInfoByMerchantCode(refundB2cReq.getMerchantId());
		if (null == merchantInfo) {
			returnMap.put("code", "-1");
			returnMap.put("errorMsg", "商户号不存在!");
			return returnMap;
		}

		logger.info("hmd5Password ====" + merchantInfo.getHmd5Password());
		String merSignMsg = refundGoodsDto.getMerSignMsg();
		String hmacSign = HmacSHA256Sign.hmacSHA256(refundGoodsDto.getTranData(), merchantInfo.getHmd5Password());
		logger.info("refundGoodsDto.getMerSignMsg() ====" + merSignMsg);
		logger.info("hmacSign ====" + hmacSign);
		logger.info("refundGoodsDto.getTranData()" + refundGoodsDto.getTranData());
		if (!hmacSign.equals(refundGoodsDto.getMerSignMsg())) {
			returnMap.put("code", "-1");
			returnMap.put("errorMsg", "签名不正确!");
			return returnMap;
		}
		return returnMap;
	}
	
	
	
	/**
	 * 封装退货请求信息
	 * 
	 * @param map
	 * @param payOrder
	 * @param refundNo
	 */
	private Map<String, String> packReturnGoods(PaymentOrder payOrder, String refundNo) {
		Map<String, String> contentData = new HashMap<String, String>();
		// 交易金额
		int orderAmt = (int) (payOrder.getOrderAmt().doubleValue() * 100);

		String txnTime = DateUtil.getYYYYMMDDHHMMSSDate(new Date());// --订单发送时间

		// 固定填写
		contentData.put("version", SystemConstant.version);// M
		// 默认取值：UTF-8
		contentData.put("encoding", SystemConstant.encoding);// M
		// //通过MPI插件获取
		// contentData.put("certId", certId);//M
		// 填写对报文摘要的签名
		// contentData.put("signature", signature);//M
		// 01RSA02 MD5 (暂不支持)
		contentData.put("signMethod", SystemConstant.SIGN_METHOD_RSA);// M
		// 交易类型 00
		contentData.put("txnType", TxnTypeEnum.RETURN_GOODS.getDesc());// M
		// 默认00
		contentData.put("txnSubType", TxnSubTypeEnum.DEFULT_TYPE.getDesc());// M
		// 默认:000000
		contentData.put("bizType", SystemConstant.BIZTYPE_B2C);// M
		// 0：普通商户直连接入2：平台类商户接入
		contentData.put("accessType", AccessTypeEnum.MERCHANT.getDesc());// M
		// 　
		contentData.put("merId", UNION_ALLPAY_MERID);// M
		// 被查询交易的交易时间
		contentData.put("txnTime", txnTime);// M
		// 被查询交易的订单号
		contentData.put("orderId", refundNo);// M

		// 订单原始交易流水号
		contentData.put("origQryId", payOrder.getQueryId());

		contentData.put("channelType", ChannelTypeEnum.INTERNET.getDesc());

		contentData.put("txnAmt", String.valueOf(orderAmt));

		contentData.put("backUrl", UNION_ALLPAY_RETURNGOODSNOTIFY);
		return contentData;
	}

	@Override
	public Map<String, String> queryTrade(AcpsdkPayRequest queryRequest) throws Exception {
		String txnTime = DateUtil.getYYYYMMDDHHMMSSDate(new Date());// --订单发送时间

		Map<String, Object> contentData = new HashMap<String, Object>();
		// 固定填写
		contentData.put("version", queryRequest.getVersion());// M
		// 默认取值：UTF-8
		contentData.put("encoding", queryRequest.getEncoding());// M
		// //通过MPI插件获取
		// contentData.put("certId", certId);//M
		// 填写对报文摘要的签名
		// contentData.put("signature", signature);//M
		// 01RSA02 MD5 (暂不支持)
		contentData.put("signMethod", queryRequest.getSignMethod());// M
		// 交易类型 00
		contentData.put("txnType", queryRequest.getTxnType());// M
		// 默认00
		contentData.put("txnSubType", TxnSubTypeEnum.DEFULT_TYPE.getDesc());// M
		// 默认:000000
		contentData.put("bizType", SystemConstant.BIZTYPE_B2C);// M
		// 0：普通商户直连接入2：平台类商户接入
		contentData.put("accessType", AccessTypeEnum.MERCHANT.getDesc());// M
		// 　
		contentData.put("merId", UNION_ALLPAY_MERID);// M
		// 被查询交易的交易时间
		contentData.put("txnTime", txnTime);// M
		// 被查询交易的订单号
		contentData.put("orderId", queryRequest.getOrderId());// M

		// 待查询交易的流水号
		// contentData.put("queryId", resmap.get("origQryId"));//C
		// 格式如下：{子域名1=值&子域名2=值&子域名3=值} 子域： origTxnType N2原交易类型余额查询时必送
		// contentData.put("reserved", "");//O
		Map<String, String> queryResmap = AcpSdkUtil.submitDate(contentData, UNION_ALLPAY_BACKTRANSREQ);

		return queryResmap;
	}

	@Override
	public boolean cancelTrade(RefundGoodsDto refundGoodsDto) throws Exception {
		boolean flag = false;
		String base64TranDataDecode = "";
		String tranData = refundGoodsDto.getTranData();
		if (!StringUtils.isEmpty(tranData)) {
			base64TranDataDecode =Base64Util.getFromBase64(tranData);
			//base64TranDataDecode = new String(ProcessMessage.Base64Decode(tranData), "UTF-8");
		}
		RefundB2cReq refundB2cReq = (RefundB2cReq) XmlUtil.xmlStrToBean(base64TranDataDecode, RefundB2cReq.class);

		PaymentOrder payOrder = paymentOrderDao.getPaymentOrderByOrderNo(refundB2cReq.getOrderNo(),
				refundB2cReq.getMerchantId());

		if (null != payOrder) {
			String cancelOrderNo = "C" + OrderNoGenerateUtil.getOrderNo();
			CancelOrder cancelOrder = new CancelOrder();
			cancelOrder.setPaymentNo(payOrder.getPaymentNo());
			cancelOrder.setCancelOrderNo(cancelOrderNo);
			cancelOrder.setCancelAmt(Double.parseDouble(refundB2cReq.getRefundAmt()));
			cancelOrder.setRemark("");
			cancelOrder.setCancelState(UibCancelStateEnum.CANCEL_EXCE.getDesc());
			cancelOrder.setNotifyUrl(refundB2cReq.getNotifyURL());
			cancelOrder.setOrderNo(refundB2cReq.getOrderNo());
			cancelOrder.setPayType(SystemConstant.PAY_TYPE_UNIONPAY);
			Date date = new Date();
			cancelOrder.setCancelDate(new Timestamp(date.getTime()));
			cancelOrderDao.saveCancelOrder(cancelOrder);

			// 交易金额
			int orderAmt = (int) (payOrder.getOrderAmt().doubleValue() * 100);

			Map<String, String> contentData = new HashMap<String, String>();

			String txnTime = DateUtil.getYYYYMMDDHHMMSSDate(new Date());// --订单发送时间

			// String orderId = payOrder.getPaymentNo();// --商户订单号
			// 固定填写
			contentData.put("version", SystemConstant.version);// M
			// 默认取值：UTF-8
			contentData.put("encoding", SystemConstant.encoding);// M
			// //通过MPI插件获取
			// contentData.put("certId", certId);//M
			// 填写对报文摘要的签名
			// contentData.put("signature", signature);//M
			// 01RSA02 MD5 (暂不支持)
			contentData.put("signMethod", SystemConstant.SIGN_METHOD_RSA);// M
			// 交易类型 00
			contentData.put("txnType", TxnTypeEnum.CANCEL_ORDER_TRADE.getDesc());// M
			// 默认00
			contentData.put("txnSubType", TxnSubTypeEnum.DEFULT_TYPE.getDesc());// M
			// 默认:000000
			contentData.put("bizType", SystemConstant.BIZTYPE_B2C);// M
			// 0：普通商户直连接入2：平台类商户接入
			contentData.put("accessType", AccessTypeEnum.MERCHANT.getDesc());// M
			// 　
			contentData.put("merId", UNION_ALLPAY_MERID);// M
			// 被查询交易的交易时间
			contentData.put("txnTime", txnTime);// M
			// 被查询交易的订单号
			contentData.put("orderId", cancelOrderNo);// M

			// 订单原始交易流水号
			contentData.put("origQryId", payOrder.getQueryId());

			contentData.put("channelType", ChannelTypeEnum.INTERNET.getDesc());

			contentData.put("txnAmt", String.valueOf(orderAmt));

			contentData.put("backUrl", UNION_ALLPAY_RETURNGOODSNOTIFY);

			// 待查询交易的流水号
			// contentData.put("queryId", payOrder.getQueryId());// C

			Map<String, String> resmap = AcpSdkUtil.submitDate(contentData, UNION_ALLPAY_BACKTRANSREQ);

			logger.info("cancelTrade 请求银联全渠道支付  撤消交易接口返回信息: " + resmap.toString());
			String respCode = resmap.get("respCode");
			logger.info("请求银联全渠道支付   撤消交易接口 响应状态:" + respCode);
			if (!StringUtils.isEmpty(respCode)) {
				if (!"00".equals(respCode)) {// 如果服务器返回不成功，结束流程，返回错误信息
					logger.info("请求银联全渠道支付  撤消交易接口处理不成功");
					// System.out.println("处理不成功");
				} else {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("paymentNo", payOrder.getPaymentNo());
					map.put("cancelState", UibCancelStateEnum.CANCEL_SUCCESS.getDesc());
					cancelOrderDao.updateCancelOrder(map);
					flag = true;
				}
			}

		}
		return flag;
	}

	@Override
	public boolean unionAllReturnGoodsNotify(AcpsdkReturnGoodsRepsonse returnGoodsRsp) throws Exception {
		logger.info("===============接收银联退货处理通知AcpsdkPayService类unionAllReturnGoodsNotify方法开始==================");
		boolean flag = false;
		String signature = returnGoodsRsp.getSignature();
		Map<String, Object> map = BeanAndMapUtil.transBean2Map(returnGoodsRsp);
		Map<String, String> signMap = AcpSdkUtil.signData(map);
		String signStr = signMap.get("signature");
		if (signature.equals(signStr)) {
			Map<String, String> paramsMap = new HashMap<String, String>();
			paramsMap.put("refundNo", returnGoodsRsp.getOrderId());
			paramsMap.put("refundState", UibRefundStateEnum.RETURN_MONEY_SUCCESS.getDesc());
			refundOrderDao.updateRefundOrder(paramsMap);
			flag = true;
		}

		RefundOrder refundOrder = refundOrderDao.getRefundOrderByRefundNo(returnGoodsRsp.getOrderId());
		MerchantInfo merchantInfo = merchantInfoDao.getMerchantInfoByMerchantCode(refundOrder.getMerchantCode());

		ReturnRefundTranData returnRefundData = new ReturnRefundTranData();
		returnRefundData.setMerchantId(refundOrder.getMerchantCode());
		returnRefundData.setOrderNo(refundOrder.getOrderNo());
		returnRefundData.setMerRefundAmt(refundOrder.getRefundAmt().toString());
		returnRefundData.setRefundAmt(refundOrder.getRefundAmt().toString());
		returnRefundData.setRefundType(SystemConstant.PAY_TYPE_UNIONPAY);
		returnRefundData.setTranSerialNo(refundOrder.getRefundNo());
		returnRefundData.setTranStat(UibTranStatEnum.RETURN_MONEY_SUCCES.getDesc());
		String returnRefundXml = XmlUtil.parseBean4Xml(returnRefundData);
		String hmacSign = HmacSHA256Sign.hmacSHA256(returnRefundXml, merchantInfo.getHmd5Password());
		String newTranDataEncode = Base64Util.getBase64(returnRefundXml);

		Map<String, String> postMap = new HashMap<String, String>();
		postMap.put("interfaceName", "退货接口返回通知");
		postMap.put("signData", hmacSign);
		postMap.put("tranData", newTranDataEncode);
		postMap.put("version", SystemConstant.version);
		postMap.put("orderNo", refundOrder.getOrderNo());
		postMap.put("orderAmt", refundOrder.getRefundAmt().toString());

		logger.info(" unionAllReturnGoodsNotify 退货交易通知 返回二级商户访问地址  :" + refundOrder.getNotifyUrl());
		logger.info(" unionAllReturnGoodsNotify 返回二级商户数据interfaceName  : 退货接口返回通知");
		logger.info(" unionAllReturnGoodsNotify 返回二级商户数据signData  :" + hmacSign);
		logger.info(" unionAllReturnGoodsNotify 返回二级商户数据tranData  :" + returnRefundXml);
		logger.info(" unionAllReturnGoodsNotify 返回二级商户数据newTranDataEncode  :" + newTranDataEncode);
		logger.info(" unionAllReturnGoodsNotify 返回二级商户数据version  :" + SystemConstant.version);

		// HttpClient hc = new HttpClient(notifyDto.getNotifyUrl(), 30000,
		// 30000);
		// int status = hc.send(postMap, SystemConstant.encoding);
		HttpCallResult callResult = HttpCall.post(refundOrder.getNotifyUrl(), postMap);
		logger.info(" unionAllReturnGoodsNotify 返回二级商户通知状态  callResult.getStatusCode():" + callResult.getStatusCode());
		// PaymentOrder payOrder =
		// paymentOrderDao.getPaymentOrderByPaymentNo(returnGoodsRsp.getR);

		// merchantNotifyDto.setOrderAmt(String.valueOf(payOrder.getOrderAmt()));
		// merchantNotifyDto.setOrderNo(orderNo);
		// merchantNotifyDto.setInterfaceName("支付接口");
		// merchantNotifyDto.setVersion("1.0");
		// merchantNotifyDto.setTranData(tranDataXml);
		// merchantNotifyDto.setSignData(hmacSign);
		// merchantNotifyDto.setReturnUrl(payOrder.getReturnUrl());
		// merchantNotifyDto.setNotifyUrl(payOrder.getNotifyUrl());
		logger.info("===============接收银联退货处理通知AcpsdkPayService类unionAllReturnGoodsNotify方法结束==================");
		return flag;
	}

	public static void main(String args[]) {

		// String merSignMsg =
		// HmacSHA256.hmacSHA256("<?xml version=\"1.0\" encoding=\"utf-8\"?><B2CReq><merId>M100000460</merId><curType>CNY</curType><orderNo>5576553214</orderNo><orderAmt>2.00</orderAmt><goodsName>威尼熊</goodsName><goodsDesc>灰色</goodsDesc><mallUserName>test@tes.com</mallUserName><remark>B2C</remark><returnURL>http://localhost:8080/</returnURL><notifyURL>http://localhost:8080/</notifyURL><reserved1></reserved1><reserved2></reserved2></B2CReq>",
		// "123456");
	}
}
