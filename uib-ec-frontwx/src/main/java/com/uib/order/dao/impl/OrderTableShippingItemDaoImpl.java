package com.uib.order.dao.impl;

import org.springframework.stereotype.Component;

import com.uib.core.dao.MyBatisDao;
import com.uib.order.dao.OrderTableShippingItemDao;
import com.uib.order.entity.OrderTableShippingItem;

@Component
public class OrderTableShippingItemDaoImpl extends MyBatisDao<OrderTableShippingItem> implements
		OrderTableShippingItemDao {

	@Override
	public void insert(OrderTableShippingItem item) throws Exception {
		this.save("insert", item);
	}

}
