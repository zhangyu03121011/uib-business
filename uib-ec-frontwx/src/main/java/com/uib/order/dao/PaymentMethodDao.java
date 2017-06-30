/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/easypay_ec">easypay_ec</a> All rights reserved.
 */
package com.uib.order.dao;

import java.util.List;

import com.uib.order.entity.PaymentMethod;


/**
 * 支付方式DAO接口
 * @author limy
 * @version 2015-06-16
 */
public interface PaymentMethodDao {
	/**
	 * 查询所有支付方式
	 * 
	 * @return
	 */
	public List<PaymentMethod> selectAllPaymentMethods()throws Exception;
	
	/**
	 * 根据id查询支付方式
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public PaymentMethod queryPaymentMethod(String id) throws Exception;
	
	/***
	 * 根据methodCode查询单个支付方式对象
	 * @param getByCode
	 * @return
	 */
	public PaymentMethod getByMethodCode(String methodCode);
}