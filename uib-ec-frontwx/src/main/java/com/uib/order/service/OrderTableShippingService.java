package com.uib.order.service;

import java.util.Map;

import com.uib.order.entity.OrderTable;

public interface OrderTableShippingService {
	/**
	 * 根据订单生成发货单
	 * 
	 * @param order
	 * @throws Exception
	 */
	public void addOrderTableShipping(OrderTable order) throws Exception;

	/**
	 * 根据条件删除发货单
	 * 
	 * @param params
	 * @throws Exception
	 */
	public void deleteOrderTableShipping(Map<String, String> params)
			throws Exception;

	/**
	 * 根据条件伪删除发货数据
	 * 
	 * @param params
	 * @throws Exception
	 */
	public void updateDeleteFlag(Map<String, String> params) throws Exception;
}
