package com.uib.order.service;

import java.util.List;

import com.uib.common.enums.PaymentMethodCodeEnum;
import com.uib.order.entity.PaymentMethod;

public interface PaymentMethodService {
	/**
	 * 查询所有支付方式
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<PaymentMethod> queryAllPaymentMethods() throws Exception;

	/**
	 * 根据id查询支付方式
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public PaymentMethod queryPaymentMethod(String id) throws Exception;
	
	public PaymentMethod getByMethodCode(PaymentMethodCodeEnum paymentMethodCodeEnum);
}
