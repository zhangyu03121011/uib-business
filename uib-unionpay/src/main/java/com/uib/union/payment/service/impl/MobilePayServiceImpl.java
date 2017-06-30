package com.uib.union.payment.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.uib.common.utils.DateUtil;
import com.uib.common.utils.HmacSHASign;
import com.uib.common.utils.XmlUtil;
import com.uib.common.web.B2CReq;
import com.uib.common.web.B2CRes;
import com.uib.union.common.SystemConstant;
import com.uib.union.enums.AccessTypeEnum;
import com.uib.union.enums.ChannelTypeEnum;
import com.uib.union.enums.UibTranStatEnum;
import com.uib.union.enums.MobileReturnTypeEnum;
import com.uib.union.enums.PayMethodEnum;
import com.uib.union.enums.TxnSubTypeEnum;
import com.uib.union.enums.TxnTypeEnum;
import com.uib.union.merchant.dao.MerchantInfoDao;
import com.uib.union.merchant.pojo.MerchantInfo;
import com.uib.union.payment.dao.CancelOrderDao;
import com.uib.union.payment.dao.PaymentOrderDao;
import com.uib.union.payment.dao.PaymentOrderLogDao;
import com.uib.union.payment.dao.RefundOrderDao;
import com.uib.union.payment.dto.AcpsdkPayResponse;
import com.uib.union.payment.dto.MerchantNotifyDto;
import com.uib.union.payment.dto.PaymentOrderDto;
import com.uib.union.payment.pojo.PaymentOrder;
import com.uib.union.payment.pojo.PaymentOrderLog;
import com.uib.union.payment.service.MobilePayService;
import com.uib.union.utils.AcpSdkUtil;
import com.uib.union.utils.OrderNoGenerateUtil;
import com.unionpay.acp.sdk.SDKConfig;

@Component
public class MobilePayServiceImpl implements  MobilePayService {

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
	
	
	@Value("${union_allpay_mobileapp_backurl}")
	private String UNION_ALLPAY_MOBILEAPP_BACKURL;
	
	@Value("${union_allpay_merid}")
	private String UNION_ALLPAY_MERID;
	
	private static final Logger logger = Logger.getLogger("rootLogger");

	
	@Override
	public Map<String, String> saveMobilePay(PaymentOrderDto paymentOrderDto) throws Exception {
		Timestamp timestamp = new Timestamp(new Date().getTime());
		Map<String, String> returnMap = new HashMap<String, String>();
		B2CReq b2cReq = (B2CReq) XmlUtil.xmlStrToBean(paymentOrderDto.getTranData(), B2CReq.class);

		PaymentOrder repeatOrder = paymentOrderDao.getPaymentOrderByOrderNo(b2cReq.getOrderNo(),
				paymentOrderDto.getMerchantId());
		if (null == repeatOrder ) {
			String paymentNo = OrderNoGenerateUtil.getOrderNo();

			PaymentOrder paymentOrder = new PaymentOrder();
			BeanUtils.copyProperties(paymentOrder, paymentOrderDto);
			paymentOrder.setBankId(paymentOrderDto.getBankId());
			paymentOrder.setOrderNo(b2cReq.getOrderNo());
			paymentOrder.setCurType(b2cReq.getCurType());
			paymentOrder.setGoodsName(b2cReq.getGoodsName());
			paymentOrder.setGoodsDesc(b2cReq.getGoodsDesc());
			paymentOrder.setOrderAmt(new BigDecimal(b2cReq.getOrderAmt()));
			paymentOrder.setMailUserName(b2cReq.getMallUserName());
			paymentOrder.setPaymentNo(paymentNo);
			paymentOrder.setTranStat(UibTranStatEnum.NOT_PAY.getDesc());
			paymentOrder.setTranDate(timestamp);
			paymentOrder.setReturnUrl(b2cReq.getReturnURL());
			paymentOrder.setNotifyUrl(b2cReq.getNotifyURL());
			paymentOrder.setMerchantCode(paymentOrderDto.getMerchantId());
			paymentOrder.setPayMethod(PayMethodEnum.UNION_ALL_PAY.getDesc());

			paymentOrderDao.savePaymentOrder(paymentOrder);

			PaymentOrderLog paymentOrderLog = new PaymentOrderLog();
			paymentOrderLog.setOrderNo(paymentOrder.getOrderNo());
			paymentOrderLog.setOrderAmt(paymentOrder.getOrderAmt());
			paymentOrderLog.setCreateTime(timestamp);
			paymentOrderLog.setPaymentNo(paymentNo);
			paymentOrderLog.setMessage("请求支付中");
			paymentOrderLog.setTranStat(UibTranStatEnum.NOT_PAY.getDesc());
			paymentOrderLog.setPayMethod(PayMethodEnum.UNION_ALL_PAY.getDesc());
			paymentOrderLogDao.savePaymentOrderLog(paymentOrderLog);
			returnMap = postPay(paymentOrder);
			
		} else {
			if (repeatOrder.getTranStat().equals(UibTranStatEnum.NOT_PAY.getDesc())){
				returnMap =	postPay(repeatOrder);
			}else if (repeatOrder.getTranStat().equals(UibTranStatEnum.ALREADY_PAY.getDesc())){
				returnMap.put("code", MobileReturnTypeEnum.ORDER_REPEAT.getDesc());
				returnMap.put("msg", "订单重复!");
				returnMap.put("orderNo", b2cReq.getOrderNo());
			}
		} 

		// Map<String, Object> map = new HashMap<String, Object>();
		// map.put("repeatOrder", repeatOrder);
		return returnMap;
	}
	
	
	private Map<String, String> postPay(PaymentOrder paymentOrder ) throws Exception{
		Map<String, String> returnMap = new HashMap<String, String>();
		String newDate = DateUtil.getYYYYMMDDHHMMSSDate(new Date());
		
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
		contentData.put("channelType", ChannelTypeEnum.MOBILE.getDesc());
		// 商户/收单前台接收地址 选送
		// 后台服务对应的写法参照 FrontRcvResponse.java
		contentData.put("frontUrl", "");
		// 商户/收单后台接收地址 必送
		// 后台服务对应的写法参照 BackRcvResponse.java
		contentData.put("backUrl", UNION_ALLPAY_MOBILEAPP_BACKURL);
		// 接入类型:商户接入填0 0- 商户 ， 1： 收单， 2：平台商户
		contentData.put("accessType", AccessTypeEnum.MERCHANT.getDesc());
		// 商户号码
		contentData.put("merId", UNION_ALLPAY_MERID);
		// 订单号 商户根据自己规则定义生成，每订单日期内不重复
		contentData.put("orderId", paymentOrder.getPaymentNo());
		// 订单发送时间 格式： YYYYMMDDhhmmss 商户发送交易时间，根据自己系统或平台生成
		contentData.put("txnTime", newDate);

		int amt = (int) (paymentOrder.getOrderAmt().doubleValue() * 100);
		// 交易金额 分
		contentData.put("txnAmt", String.valueOf(amt));
		// 交易币种
		contentData.put("currencyCode", SystemConstant.CURRENCYCODE_RMB);
		
		
		// 交易请求url 从配置文件读取
		String requestAppUrl = SDKConfig.getConfig().getAppRequestUrl();
		//returnMap = AcpSdkUtil.signData(contentData);
		returnMap = AcpSdkUtil.submitDate(contentData, requestAppUrl);
		
		returnMap.put("code", MobileReturnTypeEnum.SUCCESS.getDesc());
		returnMap.put("msg", "请求成功!");
		logger.info("银联手机控件支付请求报文:" + contentData.toString());
		logger.info("银联手机控件支付应答报文:" + returnMap.toString());
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
				map.put("threePaymentNo", acpsdkPayResponse.getTn());
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

				double orderAmt = (Double.parseDouble(acpsdkPayResponse.getTxnAmt()) / 100);
				
				payLog.setOrderAmt(new BigDecimal(orderAmt));
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
					
				//	String hmacSign = HmacSHA256Sign.hmacSHA256(tranDataXml, merchantInfo.getHmd5Password());
					String hmacSign = HmacSHASign.hmacSHASign(tranDataXml, merchantInfo.getHmd5Password());

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

}
