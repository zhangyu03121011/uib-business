package com.uib.order.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uib.common.enums.PaymentMethodCodeEnum;
import com.uib.order.dao.PaymentMethodDao;
import com.uib.order.entity.PaymentMethod;
import com.uib.order.service.PaymentMethodService;

@Service
public class PaymentMethodServiceImpl implements PaymentMethodService {
	@Autowired
	private PaymentMethodDao paymentMethodDao;

	@Override
	public List<PaymentMethod> queryAllPaymentMethods() throws Exception {
		return paymentMethodDao.selectAllPaymentMethods();
	}

	@Override
	public PaymentMethod queryPaymentMethod(String id) throws Exception {
		return paymentMethodDao.queryPaymentMethod(id);
	}

	@Override
	public PaymentMethod getByMethodCode(
			PaymentMethodCodeEnum paymentMethodCodeEnum) {
		return paymentMethodDao.getByMethodCode(paymentMethodCodeEnum.getIndex());
	}

}
