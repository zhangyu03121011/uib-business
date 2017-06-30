package com.uib.union.payment.dao;

import com.uib.union.payment.pojo.PaymentOrderLog;
import com.uib.core.exception.GenericException;

/**
 * 支付日志记录
 * @author kevin
 *
 */
public interface PaymentOrderLogDao {
	
	/**
	 * 保存支付日志记录
	 * @param paymentOrderLog
	 * @throws GenericException
	 */
	void savePaymentOrderLog(PaymentOrderLog paymentOrderLog) throws GenericException;
}
