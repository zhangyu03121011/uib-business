package com.uib.union.payment.service;

import java.util.Map;

import com.uib.core.exception.GenericException;
import com.uib.union.payment.dto.AcpsdkPayRequest;
import com.uib.union.payment.dto.AcpsdkPayResponse;
import com.uib.union.payment.dto.AcpsdkReturnGoodsRepsonse;
import com.uib.union.payment.dto.MerchantNotifyDto;
import com.uib.union.payment.dto.PaymentOrderDto;
import com.uib.union.payment.dto.RefundGoodsDto;

public interface AcpsdkPayService {
	

	/**
	 * 保存用户选择银联支付渠道数据
	 * @param paymentOrderDto
	 * @return
	 * @throws Exception
	 */
	Map<String, String> saveAcpSdkPay(PaymentOrderDto paymentOrderDto) throws Exception;
	
	
	
	
	/**
	 * 接收银联全渠道返回接口数据
	 * @param unionAllPayNotify
	 * @throws GenericException
	 */
	MerchantNotifyDto unionAllPayNotify(AcpsdkPayResponse acpsdkPayResponse) throws Exception;

	
	
	/**
	 * 请求银联全渠道退货
	 * @param refundGoodsDto
	 * @return
	 * @throws Exception
	 */
	Map<String, String> unionAllReturnGoods(RefundGoodsDto refundGoodsDto) throws Exception;
	
	
	/**
	 * 接收退货返回通知处理
	 * @param returnGoodsRsp
	 * @return
	 * @throws Exception
	 */
	boolean unionAllReturnGoodsNotify(AcpsdkReturnGoodsRepsonse returnGoodsRsp) throws Exception;
	
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
