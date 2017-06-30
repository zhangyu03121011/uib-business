package com.uib.order.dao.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.uib.core.dao.MyBatisDao;
import com.uib.order.dao.PaymentMethodDao;
import com.uib.order.entity.PaymentMethod;

@Component
public class PaymentMethodDaoImpl extends MyBatisDao<PaymentMethod> implements
		PaymentMethodDao {

	@Override
	public List<PaymentMethod> selectAllPaymentMethods() throws Exception {
		return this.get("selectAllPaymentMethods");
	}

	@Override
	public PaymentMethod queryPaymentMethod(String id) throws Exception {
		return this.getUnique("queryPaymentMethod",id);
	}

	@Override
	public PaymentMethod getByMethodCode(String methodCode) {
		return this.getUnique("getByMethodCode", methodCode);
	}
	
	

}
