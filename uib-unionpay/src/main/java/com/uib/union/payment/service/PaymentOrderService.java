package com.uib.union.payment.service;

import java.util.Map;

import com.uib.core.exception.GenericException;
import com.uib.union.payment.dto.AcpsdkPayResponse;
import com.uib.union.payment.dto.MerchantNotifyDto;
import com.uib.union.payment.dto.PaymentOrderDto;
import com.uib.union.payment.dto.RefundGoodsDto;
import com.uib.union.payment.dto.UnionPayNotifyResponseDto;
import com.uib.union.payment.pojo.PaymentOrder;
import com.uib.union.payment.pojo.PaymentOrderLog;

public interface PaymentOrderService {
	
	/**
	 * 保存支付信息
	 * @param paymentOrder
	 * @throws GenericException
	 */
	Map<String, Object> savePaymentOrder(PaymentOrderDto paymentOrderDto) throws Exception;
	
	
	
	/**
	 * 根据商品订单号获取
	 * @param orderNo
	 * @return
	 * @throws GenericException
	 */
	PaymentOrder getPaymentOrderByOrderNo(String orderNo, String merchantCode) throws GenericException;

	
	
	/**
	 * 保存支付日志记录
	 * @param paymentOrderLog
	 * @throws GenericException
	 */
	void savePaymentOrderLog(PaymentOrderLog paymentOrderLog) throws GenericException;
	
	
	/**
	 * 接收银联返回接口数据
	 * @param payNotifyDto
	 * @throws GenericException
	 */
	MerchantNotifyDto payBackgroundNotify(UnionPayNotifyResponseDto upNotifyResponseDto) throws Exception;
	
	
	/**
	 * 退货
	 * @param refundGoodsDto
	 * @throws Exception
	 */
	Map<String, String> refundGoods(RefundGoodsDto refundGoodsDto) throws Exception;
	
	
	
	/**
	 * 根据退货是否成功修改退货状态
	 * @param refundXml
	 * @return
	 * @throws Exception
	 */
	String updateRefundGoods(String refundXml) throws Exception;
	
	
	
	
}
