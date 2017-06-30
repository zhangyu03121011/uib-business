package com.uib.union.payment.dao;

import java.util.Map;

import com.uib.union.payment.pojo.PaymentOrder;
import com.uib.core.exception.GenericException;

/**
 * 支付Dao
 * @author kevin
 *
 */
public interface PaymentOrderDao {
	
	/**
	 * 保存支付信息
	 * @param paymentOrder
	 * @throws GenericException
	 */
	void savePaymentOrder(PaymentOrder paymentOrder) throws GenericException;
	
	
	/**
	 * 根据返回支付结果修改支付信息
	 * @param map
	 * @throws GenericException
	 */
	void updatePaymentOrder(Map<String, Object> map) throws GenericException;
	
	/**
	 * 根据商品订单号获取
	 * @param orderNo
	 * @return
	 * @throws GenericException
	 */
	PaymentOrder getPaymentOrderByOrderNo(String orderNo, String merchantCode) throws GenericException;
	
	
	/**
	 * 根据uib系统支付流水获取支付信息
	 * @param paymentNo
	 * @return
	 * @throws GenericException
	 */
	PaymentOrder getPaymentOrderByPaymentNo(String paymentNo) throws GenericException;
}
