package com.uib.order.dao.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.uib.core.dao.MyBatisDao;
import com.uib.order.dao.ShippingMethodDao;
import com.uib.order.entity.ShippingMethod;

@Component
public class ShippingMethodDaoImpl extends MyBatisDao<ShippingMethod> implements
		ShippingMethodDao {

	@Override
	public List<ShippingMethod> selectAllShippingMethods() throws Exception {
		return this.get("selectAllShippingMethods");
	}

	@Override
	public ShippingMethod getShippingMethod(String id) throws Exception {
		return this.getUnique("getShippingMethod", id);
	}

}
