package com.uib.union.payment.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.uib.core.dao.MyBatisDao;
import com.uib.core.exception.GenericException;
import com.uib.union.payment.dao.CancelOrderDao;
import com.uib.union.payment.pojo.CancelOrder;

@Component
public class CancelOrderDaoImpl extends MyBatisDao<CancelOrder> implements CancelOrderDao{

	@Override
	public void saveCancelOrder(CancelOrder cancelOrder) throws GenericException {
		this.save("saveCancelOrder", cancelOrder);
	}

	@Override
	public void updateCancelOrder(Map<String, Object> map) throws GenericException {
		this.update("updateCancelOrder", map);
	}

	@Override
	public CancelOrder getCancelOrderByPaymentNo(String paymentNo) throws GenericException {
		return this.getUnique("getCancelOrderByPaymentNo", paymentNo);
	}

}
