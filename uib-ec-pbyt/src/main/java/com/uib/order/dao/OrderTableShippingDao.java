package com.uib.order.dao;

import java.util.Map;

import com.uib.order.entity.OrderTableShipping;

public interface OrderTableShippingDao {

	/**
	 * 插入发货单
	 * 
	 * @param shipping
	 * @throws Exception
	 */
	public void insert(OrderTableShipping shipping) throws Exception;

	/**
	 * 根据条件删除发货单
	 * 
	 * @param params
	 * @throws Exception
	 */
	public void delete(Map<String, String> params) throws Exception;
	
	/**
	 * 根据条件伪删除删除发货单
	 * @param params
	 * @throws Exception
	 */
	public void updateDeleteFlag(Map<String, String> params) throws Exception;

}
