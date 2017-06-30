/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/easypay_ec">easypay_ec</a> All rights reserved.
 */
package com.uib.order.dao;

import java.util.List;

import com.uib.order.entity.ShippingMethod;


/**
 * 配送方式DAO接口
 * @author limy
 * @version 2015-06-16
 */
public interface ShippingMethodDao {
	/**
	 * 查询所有配送方式
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<ShippingMethod> selectAllShippingMethods() throws Exception;
	
	/**
	 * 根据配送ID查询配送方式
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public ShippingMethod getShippingMethod(String id) throws Exception;
}