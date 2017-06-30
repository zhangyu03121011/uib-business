package com.uib.union.payment.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.uib.core.dao.MyBatisDao;
import com.uib.core.exception.GenericException;
import com.uib.union.payment.dao.PaymentOrderDao;
import com.uib.union.payment.pojo.PaymentOrder;

@Component
public class PaymentOrderDaoImpl extends MyBatisDao<PaymentOrder> implements PaymentOrderDao{

	@Override
	public void savePaymentOrder(PaymentOrder paymentOrder)
			throws GenericException {
		this.save("savePaymentOrder", paymentOrder);
	}

	@Override
	public void updatePaymentOrder(Map<String, Object> map)
			throws GenericException {
		this.update("updatePaymentOrder", map);
	}

	@Override
	public PaymentOrder getPaymentOrderByOrderNo(String orderNo , String merchantCode)
			throws GenericException {
		Map<String, String> map = new HashMap<String, String>();
		map.put("orderNo", orderNo);
		map.put("merchantCode", merchantCode);
		return this.getUnique("getPaymentOrderByOrderNo", map);
	}

	@Override
	public PaymentOrder getPaymentOrderByPaymentNo(String paymentNo)
			throws GenericException {
		return this.getUnique("getPaymentOrderByPaymentNo", paymentNo);
	}

}
