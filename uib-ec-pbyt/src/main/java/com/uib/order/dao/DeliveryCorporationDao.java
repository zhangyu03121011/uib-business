/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/easypay_ec">easypay_ec</a> All rights reserved.
 */
package com.uib.order.dao;

import com.uib.order.entity.DeliveryCorporation;

/**
 * 物流公司DAO接口
 * 
 * @author gaven
 * @version 2015-06-08
 */
public interface DeliveryCorporationDao {
	/**
	 * 根据id获取物流公司
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public DeliveryCorporation getDeliveryCorporation(String id) throws Exception;
}