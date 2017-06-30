package com.uib.union.payment.dao;

import java.util.Map;

import com.uib.core.exception.GenericException;
import com.uib.union.payment.pojo.CancelOrder;

public interface CancelOrderDao {
	
	/**
	 * 保存撤销订单信息
	 * @param cancelOrder
	 * @throws GenericException
	 */
	void saveCancelOrder(CancelOrder cancelOrder) throws GenericException;
	
	
	
	/**
	 * 根据参数修改订单信息
	 * @param map
	 * @throws GenericException
	 */
	void updateCancelOrder(Map<String, Object> map) throws GenericException;
	
	
	/**
	 * 根据支付流水获取撤销支付信息
	 * @param paymentNo
	 * @return
	 * @throws GenericException
	 */
	CancelOrder getCancelOrderByPaymentNo(String paymentNo) throws GenericException;
}
