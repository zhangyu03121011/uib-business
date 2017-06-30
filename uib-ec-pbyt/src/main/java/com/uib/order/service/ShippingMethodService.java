package com.uib.order.service;

import java.util.List;

import com.uib.order.entity.ShippingMethod;

public interface ShippingMethodService {
	/**
	 * 查询所有配送方式
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<ShippingMethod> quaryAllShippingMethods() throws Exception;

	/**
	 * 根据配送ID查询配送方式
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public ShippingMethod getShippingMethod(String id) throws Exception;
}
