package com.uib.order.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uib.order.dao.ShippingMethodDao;
import com.uib.order.entity.ShippingMethod;
import com.uib.order.service.ShippingMethodService;

@Service
public class ShippingMethodServiceImpl implements ShippingMethodService {
	@Autowired
	private ShippingMethodDao shippingMethodDao;
	
	@Override
	public List<ShippingMethod> quaryAllShippingMethods() throws Exception {
		return shippingMethodDao.selectAllShippingMethods();
	}

	@Override
	public ShippingMethod getShippingMethod(String id) throws Exception {
		return shippingMethodDao.getShippingMethod(id);
	}

}
