package com.uib.order.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.easypay.core.dao.MyBatisDao;
import com.uib.order.dao.OrderShippingRefDao;
import com.uib.order.entity.OrderShippingRef;

@Component
public class OrderShippingRefDaoImpl extends MyBatisDao<OrderShippingRef> implements
		OrderShippingRefDao {

	@Override
	public void insert(OrderShippingRef ref) throws Exception {
		this.save("insert", ref);
	}

	@Override
	public void delete(Map<String, String> params) throws Exception {
		this.remove("remove",params);
	}

}
