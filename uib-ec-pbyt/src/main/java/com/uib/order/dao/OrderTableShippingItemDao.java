package com.uib.order.dao;

import com.uib.order.entity.OrderTableShippingItem;

public interface OrderTableShippingItemDao {
	/**
	 * 
	 * @param item
	 * @throws Exception
	 */
	public void insert(OrderTableShippingItem item) throws Exception;
}
