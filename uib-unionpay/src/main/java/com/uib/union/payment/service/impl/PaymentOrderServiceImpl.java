package com.uib.union.payment.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.chinagpay.mer.bean.HmacSHA256Sign;
import com.chinagpay.mer.bean.ProcessMessage;
import com.uib.union.payment.dto.RefundB2cReq;
import com.uib.union.payment.pojo.RefundOrder;
import com.uib.common.utils.RandomUtil;
import com.uib.common.utils.XmlUtil;
import com.uib.common.web.B2CReq;
import com.uib.common.web.B2CRes;
import com.uib.common.web.ObjectConversion;
import com.uib.core.exception.GenericException;
import com.uib.union.common.SystemConstant;
import com.uib.union.enums.AccessTypeEnum;
import com.uib.union.enums.UibTranStatEnum;
import com.uib.union.enums.UibRefundStateEnum;
import com.uib.union.enums.PayMethodEnum;
import com.uib.union.enums.TxnSubTypeEnum;
import com.uib.union.enums.TxnTypeEnum;
import com.uib.union.enums.UnionPayResCodeEnum;
import com.uib.union.enums.UnionPayTransTypeEnum;
import com.uib.union.merchant.dao.MerchantInfoDao;
import com.uib.union.merchant.pojo.MerchantInfo;
import com.uib.union.payment.dao.PaymentOrderDao;
import com.uib.union.payment.dao.PaymentOrderLogDao;
import com.uib.union.payment.dao.RefundOrderDao;
import com.uib.union.payment.dto.AcpsdkPayResponse;
import com.uib.union.payment.dto.MerchantNotifyDto;
import com.uib.union.payment.dto.PaymentOrderDto;
import com.uib.union.payment.dto.RefundGoodsDto;
import com.uib.union.payment.dto.UnionPayNotifyResponseDto;
import com.uib.union.payment.dto.UnionPayRequestDto;
import com.uib.union.payment.pojo.PaymentOrder;
import com.uib.union.payment.pojo.PaymentOrderLog;
import com.uib.union.payment.service.PaymentOrderService;
import com.uib.union.utils.AcpSdkUtil;
import com.uib.union.utils.QuickPayConf;
import com.uib.union.utils.QuickPayUtils;

@Component
public class PaymentOrderServiceImpl implements PaymentOrderService {

	@Autowired
	private PaymentOrderDao paymentOrderDao;

	@Autowired
	private PaymentOrderLogDao paymentOrderLogDao;

	@Autowired
	private MerchantInfoDao merchantInfoDao;

	@Autowired
	private QuickPayUtils quickPayUtils;

	@Value("${front_return_url}")
	private String FRONT_RETURN_URL;

	@Value("${callback_return_url}")
	private String CALLBACK_RETURN_URL;

	@Value("${mer_id}")
	private String MER_ID;

	@Value("${mer_abbr}")
	private String MER_ABBR;
	
	
	

	@Autowired
	private RefundOrderDao refundOrderDao;

	/*
	 * @Value("${hmd5_password}") private String HMD5_PASSWORD;
	 */

	@Override
	public Map<String, Object> savePaymentOrder(PaymentOrderDto paymentOrderDto) throws Exception {

		Timestamp timestamp = new Timestamp(new Date().getTime());

		B2CReq b2cReq = (B2CReq) XmlUtil.xmlStrToBean(paymentOrderDto.getTranData(), B2CReq.class);
		Map<String, Object> map = new HashMap<String, Object>();
		if (null != b2cReq){
			MerchantInfo merchantInfo = merchantInfoDao.getMerchantInfoByMerchantCode(b2cReq.getMerId());
			if (null == merchantInfo){
				
			}
		}

		PaymentOrder repeatOrder = paymentOrderDao.getPaymentOrderByOrderNo(b2cReq.getOrderNo(),
				paymentOrderDto.getMerchantId());

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		String paymentNo = sdf.format(date) + RandomUtil.getRandom(5);

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
		paymentOrder.setPayMethod(PayMethodEnum.UNION_ONLINE_PAY.getDesc());

		paymentOrderDao.savePaymentOrder(paymentOrder);

		PaymentOrderLog paymentOrderLog = new PaymentOrderLog();
		paymentOrderLog.setOrderNo(paymentOrder.getOrderNo());
		paymentOrderLog.setOrderAmt(paymentOrder.getOrderAmt());
		paymentOrderLog.setCreateTime(timestamp);
		paymentOrderLog.setPaymentNo(paymentNo);
		paymentOrderLog.setMessage("请求支付中");
		paymentOrderLog.setTranStat(UibTranStatEnum.NOT_PAY.getDesc());
		paymentOrderLog.setPayMethod(PayMethodEnum.UNION_ONLINE_PAY.getDesc());
		paymentOrderLogDao.savePaymentOrderLog(paymentOrderLog);

		UnionPayRequestDto upRequestDto = new UnionPayRequestDto();
		upRequestDto.setVersion(QuickPayConf.version);
		upRequestDto.setCharset(QuickPayConf.charset);
		upRequestDto.setTransType(UnionPayTransTypeEnum.CONSUMPTION.getDesc());
		// upRequestDto.setOrigQid("");
		upRequestDto.setMerId(MER_ID);
		upRequestDto.setMerAbbr(QuickPayConf.MERCHANT_SHORT_NAME);
		// upRequestDto.setAcqCode("");
		// upRequestDto.setMerCode("");
		// upRequestDto.setCommodityUrl("");

		// String goodsName = new
		// String(paymentOrder.getGoodsDesc().getBytes("UTF-8"),"ISO8859-1");
		upRequestDto.setCommodityName(paymentOrder.getGoodsDesc());
		// upRequestDto.setCommodityUnitPrice("");
		// upRequestDto.setCommodityQuantity("");
		// upRequestDto.setCommodityDiscount("");
		// upRequestDto.setTransferFee("");
		upRequestDto.setOrderNumber(paymentNo);
		// 根据银联要求转换为分
		int amt = (int) (paymentOrder.getOrderAmt().doubleValue() * 100);
		upRequestDto.setOrderAmount(String.valueOf(amt));
		upRequestDto.setOrderCurrency("156");
		upRequestDto.setOrderTime(sdf.format(timestamp));
		// upRequestDto.setCustomerIp("127.0.0.1");
		// upRequestDto.setCustomerName("张三");
		// upRequestDto.setDefaultPayType("");
		// upRequestDto.setDefaultBankNumber("");
		upRequestDto.setTransTimeout("300000");
		upRequestDto.setFrontEndUrl(FRONT_RETURN_URL);
		upRequestDto.setBackEndUrl(CALLBACK_RETURN_URL);
		Map<String, String> upMap = ObjectConversion.objToHash(upRequestDto);
		String signature = quickPayUtils.signMap(upMap, "MD5");

		
		map.put("signature", signature);
		map.put("signMethod", "MD5");
		map.put("upRequestDto", upRequestDto);
		map.put("repeatOrder", repeatOrder);
		return map;
	}

	@Override
	public void savePaymentOrderLog(PaymentOrderLog paymentOrderLog) throws GenericException {

	}

	/*
	 * @Override public MerchantNotifyDto payNotify(PayNotifyDto payNotifyDto)
	 * throws Exception { MerchantNotifyDto merchantNotifyDto = new
	 * MerchantNotifyDto();
	 * 
	 * Timestamp timestamp = new Timestamp(new Date().getTime()); String
	 * base64TranDataDecode = ""; String tranData = payNotifyDto.getTranData();
	 * if (!StringUtils.isEmpty(tranData)) { base64TranDataDecode = new
	 * String(ProcessMessage.Base64Decode(tranData), "GBK"); } B2CRes b2cRes =
	 * (B2CRes) XmlUtil.xmlStrToBean(base64TranDataDecode, B2CRes.class);
	 * Map<String, Object> map = new HashMap<String, Object>();
	 * map.put("threePaymentNo", b2cRes.getTranSerialNo()); map.put("tranStat",
	 * b2cRes.getTranStat()); map.put("paymentNo", b2cRes.getOrderNo());
	 * paymentOrderDao.updatePaymentOrder(map);
	 * 
	 * PaymentOrderLog payLog = new PaymentOrderLog();
	 * payLog.setCreateTime(timestamp);
	 * payLog.setPaymentNo(b2cRes.getOrderNo());
	 * payLog.setMessage(b2cRes.getRemark());
	 * payLog.setThreePaymentNo(b2cRes.getTranSerialNo());
	 * 
	 * 
	 * String orderAmt = b2cRes.getOrderAmt().replace(",", "");
	 * payLog.setOrderAmt(Double.parseDouble(orderAmt));
	 * payLog.setTranStat(b2cRes.getTranStat());
	 * paymentOrderLogDao.savePaymentOrderLog(payLog);
	 * 
	 * SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	 * PaymentOrder payOrder =
	 * paymentOrderDao.getPaymentOrderByPaymentNo(b2cRes.getOrderNo());
	 * MerchantInfo merchantInfo =
	 * merchantInfoDao.getMerchantInfoByMerchantCode(
	 * payOrder.getMerchantCode()); if (null != merchantInfo) { String orderNo =
	 * payOrder.getOrderNo(); B2CRes mnRes = new B2CRes();
	 * mnRes.setMerchantId(payOrder.getMerchantCode());
	 * mnRes.setCurType(payOrder.getCurType()); mnRes.setOrderNo(orderNo);
	 * mnRes.setOrderAmt(String.valueOf(payOrder.getOrderAmt()));
	 * mnRes.setRemark(payOrder.getRemark());
	 * 
	 * if (null != payOrder.getTranDate()){ Date date = new
	 * Date(payOrder.getTranDate().getTime());
	 * mnRes.setTranTime(sdf.format(date)); }
	 * 
	 * mnRes.setTranSerialNo(payOrder.getPaymentNo());
	 * mnRes.setTranStat(payOrder.getTranStat()); String tranDataXml =
	 * XmlUtil.parseBean4Xml(mnRes);
	 * 
	 * //String hmacSign =
	 * DigestUtil.hmacSign(tranDataXml,merchantInfo.getHmd5Password()); String
	 * hmacSign =
	 * HmacSHA256Sign.hmacSHA256(tranDataXml,merchantInfo.getHmd5Password());
	 * 
	 * merchantNotifyDto.setOrderAmt(orderAmt);
	 * merchantNotifyDto.setOrderNo(orderNo);
	 * merchantNotifyDto.setInterfaceName("支付接口");
	 * merchantNotifyDto.setVersion("1.0");
	 * merchantNotifyDto.setTranData(tranDataXml);
	 * merchantNotifyDto.setSignData(hmacSign);
	 * merchantNotifyDto.setReturnUrl(payOrder.getReturnUrl());
	 * merchantNotifyDto.setNotifyUrl(payOrder.getNotifyUrl()); }
	 * 
	 * return merchantNotifyDto; }
	 */

	@Override
	public Map<String, String> refundGoods(RefundGoodsDto refundGoodsDto) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Timestamp timestamp = new Timestamp(new Date().getTime());
		String base64TranDataDecode = "";
		String tranData = refundGoodsDto.getTranData();
		if (!StringUtils.isEmpty(tranData)) {
			base64TranDataDecode = new String(ProcessMessage.Base64Decode(tranData), "UTF-8");
		}
		RefundB2cReq refundB2cReq = (RefundB2cReq) XmlUtil.xmlStrToBean(base64TranDataDecode, RefundB2cReq.class);
		Map<String, String> map = new HashMap<String, String>();
		PaymentOrder payOrder = paymentOrderDao.getPaymentOrderByOrderNo(refundB2cReq.getOrderNo(),
				refundB2cReq.getMerchantId());
		if (null != payOrder) {
			MerchantInfo merchantInfo = merchantInfoDao.getMerchantInfoByMerchantCode(refundB2cReq.getMerchantId());
			if (null != merchantInfo) {
				RefundOrder refundOrder = new RefundOrder();
				refundOrder.setPaymentNo(payOrder.getPaymentNo());
				refundOrder.setRefundAmt(Double.parseDouble(refundB2cReq.getRefundAmt()));
				refundOrder.setRemark("");
				refundOrder.setRefundState(UibRefundStateEnum.RETURN_EXCE.getDesc());
				refundOrder.setNotifyUrl(refundB2cReq.getNotifyURL());
				refundOrder.setOrderNo(refundB2cReq.getOrderNo());
				Date date = new Date();
				refundOrder.setRefundDate(new Timestamp(date.getTime()));
				// refundOrder.setThreePaymentNo(refundB2cReq.g);

				int amt = (int) (payOrder.getOrderAmt().doubleValue() * 100);
				UnionPayRequestDto requestDto = new UnionPayRequestDto();
				requestDto.setCharset(QuickPayConf.charset);
				requestDto.setVersion(QuickPayConf.version);
				requestDto.setTransType(UnionPayTransTypeEnum.RETURN_GOODS.getDesc());
				requestDto.setOrigQid(payOrder.getThreePaymentNo());
				requestDto.setMerId(MER_ID);
				requestDto.setMerAbbr(QuickPayConf.MERCHANT_SHORT_NAME);
				requestDto.setOrderNumber(payOrder.getPaymentNo());
				requestDto.setOrderAmount(String.valueOf(amt));
				requestDto.setOrderCurrency("156");
				requestDto.setOrderTime(sdf.format(timestamp));
				requestDto.setCommodityName(payOrder.getGoodsDesc());
				// upRequestDto.setCustomerIp("127.0.0.1");
				// upRequestDto.setCustomerName("张三");
				// upRequestDto.setDefaultPayType("");
				// upRequestDto.setDefaultBankNumber("");
				requestDto.setTransTimeout("300000");
				requestDto.setFrontEndUrl(FRONT_RETURN_URL);
				requestDto.setBackEndUrl(CALLBACK_RETURN_URL);

				map = ObjectConversion.objToHash(requestDto);

				String responseBady = quickPayUtils.doPostQueryCmd(QuickPayConf.backStagegateWay,
						new QuickPayUtils().createBackStrForBackTrans(map, QuickPayConf.reqVo));

				refundOrderDao.saveRefundOrder(refundOrder);
			}
		}
		return map;
	}

	@Override
	public String updateRefundGoods(String refundXml) throws Exception {
		return null;
	}

	@Override
	public MerchantNotifyDto payBackgroundNotify(UnionPayNotifyResponseDto upNotifyResponseDto) throws Exception {
		MerchantNotifyDto merchantNotifyDto = new MerchantNotifyDto();

		Timestamp timestamp = new Timestamp(new Date().getTime());

		String tranStat = "2";
		if (UnionPayResCodeEnum.SUCCESS.getDesc().equals(upNotifyResponseDto.getRespCode())) {
			tranStat = "0";
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("threePaymentNo", upNotifyResponseDto.getQid());
		map.put("tranStat", tranStat);
		map.put("paymentNo", upNotifyResponseDto.getOrderNumber());
		paymentOrderDao.updatePaymentOrder(map);

		PaymentOrderLog payLog = new PaymentOrderLog();
		payLog.setCreateTime(timestamp);
		payLog.setPaymentNo(upNotifyResponseDto.getOrderNumber());
		payLog.setMessage(upNotifyResponseDto.getRespMsg());
		payLog.setThreePaymentNo(upNotifyResponseDto.getQid());

		double orderAmt = (Double.parseDouble(upNotifyResponseDto.getOrderAmount()) / 100);
		payLog.setOrderAmt(new BigDecimal(orderAmt));
		payLog.setTranStat(UibTranStatEnum.ALREADY_PAY.getDesc());

		paymentOrderLogDao.savePaymentOrderLog(payLog);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		PaymentOrder payOrder = paymentOrderDao.getPaymentOrderByPaymentNo(upNotifyResponseDto.getOrderNumber());
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
			String hmacSign = HmacSHA256Sign.hmacSHA256(tranDataXml, merchantInfo.getHmd5Password());

			merchantNotifyDto.setOrderAmt(String.valueOf(payOrder.getOrderAmt()));
			merchantNotifyDto.setOrderNo(orderNo);
			merchantNotifyDto.setInterfaceName("支付接口");
			merchantNotifyDto.setVersion("1.0");
			merchantNotifyDto.setTranData(tranDataXml);
			merchantNotifyDto.setSignData(hmacSign);
			merchantNotifyDto.setReturnUrl(payOrder.getReturnUrl());
			merchantNotifyDto.setNotifyUrl(payOrder.getNotifyUrl());
		}
		return merchantNotifyDto;
	}

	@Override
	public PaymentOrder getPaymentOrderByOrderNo(String orderNo, String merchantCode) throws GenericException {
		return paymentOrderDao.getPaymentOrderByOrderNo(orderNo, merchantCode);
	}



}
