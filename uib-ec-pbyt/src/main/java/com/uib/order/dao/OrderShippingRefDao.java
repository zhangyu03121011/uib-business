package com.uib.order.dao;

import java.util.Map;

import com.uib.order.entity.OrderShippingRef;

public interface OrderShippingRefDao {
	/**
	 * 插入订单发货单关联记录
	 * 
	 * @param ref
	 * @throws Exception
	 */
	public void insert(OrderShippingRef ref) throws Exception;

	/**
	 * 根据条件删除发货单关系记录
	 * 
	 * @param params
	 * @throws Exception
	 */
	public void delete(Map<String, String> params) throws Exception;
}
