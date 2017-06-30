package com.uib.order.dao.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.uib.core.dao.MyBatisDao;
import com.uib.mobile.dto.OrderPojo4Mobile;
import com.uib.order.dao.OrderPojo4MobileDao;
import com.uib.order.entity.OrderTable;

@Component
public class OrderPojo4MobileDaoImpl extends MyBatisDao<OrderPojo4Mobile>
		implements OrderPojo4MobileDao {

	@Override
	public List<OrderPojo4Mobile> seleOrderPojo4Mobiles(OrderTable order)
			throws Exception {
		return this.get("seleOrderPojo4Mobiles", order);
	}

}
