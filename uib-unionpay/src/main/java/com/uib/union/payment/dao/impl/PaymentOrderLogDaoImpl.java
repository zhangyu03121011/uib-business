package com.uib.union.payment.dao.impl;

import org.springframework.stereotype.Component;

import com.uib.core.dao.MyBatisDao;
import com.uib.core.exception.GenericException;
import com.uib.union.payment.dao.PaymentOrderLogDao;
import com.uib.union.payment.pojo.PaymentOrderLog;

@Component
public class PaymentOrderLogDaoImpl extends MyBatisDao<PaymentOrderLog> implements PaymentOrderLogDao{

	@Override
	public void savePaymentOrderLog(PaymentOrderLog paymentOrderLog)
			throws GenericException {
		this.save("savePaymentOrderLog", paymentOrderLog);
	}

}
