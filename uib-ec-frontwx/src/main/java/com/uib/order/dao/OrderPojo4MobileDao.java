package com.uib.order.dao;

import java.util.List;

import com.uib.mobile.dto.OrderPojo4Mobile;
import com.uib.order.entity.OrderTable;

public interface OrderPojo4MobileDao{
	/**
	 * @param order
	 * @return
	 * @throws Exception
	 */
	public List<OrderPojo4Mobile> seleOrderPojo4Mobiles(OrderTable order)
			throws Exception;
}
