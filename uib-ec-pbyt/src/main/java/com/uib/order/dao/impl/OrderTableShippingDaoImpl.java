package com.uib.order.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.uib.core.dao.MyBatisDao;
import com.uib.order.dao.OrderTableShippingDao;
import com.uib.order.entity.OrderTableShipping;

@Component
public class OrderTableShippingDaoImpl extends MyBatisDao<OrderTableShipping> implements
		OrderTableShippingDao {

	@Override
	public void insert(OrderTableShipping shipping) throws Exception {
		this.save("insert", shipping);
	}

	@Override
	public void delete(Map<String, String> params) throws Exception {
		this.remove("delete", params);
	}

	@Override
	public void updateDeleteFlag(Map<String, String> params) throws Exception {
		this.update("updateDeleteFlag", params);
	}

}
