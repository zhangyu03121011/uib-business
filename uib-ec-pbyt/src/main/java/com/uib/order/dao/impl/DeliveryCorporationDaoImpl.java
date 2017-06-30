package com.uib.order.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.uib.core.dao.MyBatisDao;
import com.uib.order.dao.DeliveryCorporationDao;
import com.uib.order.entity.DeliveryCorporation;

@Component
public class DeliveryCorporationDaoImpl extends MyBatisDao<DeliveryCorporation> implements
		DeliveryCorporationDao {

	@Override
	public DeliveryCorporation getDeliveryCorporation(String id)
			throws Exception {
		Map<String, String> map = new HashMap<String, String>(1);
		map.put("id", id);
		return this.getUnique("getDeliveryCorporation", map);
	}

}
