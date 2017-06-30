package com.uib.union.payment.service;

import java.util.Map;

import com.uib.core.exception.GenericException;
import com.uib.union.payment.dto.AcpsdkPayRequest;
import com.uib.union.payment.dto.AcpsdkPayResponse;
import com.uib.union.payment.dto.MerchantNotifyDto;
import com.uib.union.payment.dto.PaymentOrderDto;
import com.uib.union.payment.dto.RefundGoodsDto;

/**
 * 银联手机wap支付
 * @author kevin
 *
 */
public interface WapPayService {
	

	/**
	 * 保存用户选择银联wap支付渠道数据
	 * @param paymentOrderDto
	 * @return
	 * @throws Exception
	 */
	Map<String, String> saveWapPay(PaymentOrderDto paymentOrderDto) throws Exception;
	
	
	
	
	/**
	 * 接收银联全渠道返回接口数据
	 * @param unionAllPayNotify
	 * @throws GenericException
	 */
	MerchantNotifyDto unionWapPayNotify(AcpsdkPayResponse acpsdkPayResponse) throws Exception;

	
	
	/**
	 * 请求银联全渠道退货
	 * @param refundGoodsDto
	 * @return
	 * @throws Exception
	 */
	boolean unionReturnGoods(RefundGoodsDto refundGoodsDto) throws Exception;
	
	
	/**
	 * 查询交易信息
	 * @return
	 * @throws Exception
	 */
	Map<String, String>	queryTrade(AcpsdkPayRequest request) throws Exception;
	
	
	
	/**
	 * 撤消交易
	 * @param refundGoodsDto
	 * @return
	 * @throws Exception
	 */
	boolean cancelTrade(RefundGoodsDto refundGoodsDto) throws Exception;
}
