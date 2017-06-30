package com.uib.order.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.uib.core.dao.MyBatisDao;
import com.uib.order.dao.OrderCommissionSettleLogDao;
import com.uib.order.entity.OrderCommissionSettleLog;
@Component
public class OrderCommissionSettleLogDaoImpl extends MyBatisDao<OrderCommissionSettleLog> implements OrderCommissionSettleLogDao{

	@Override
	public void insert(OrderCommissionSettleLog orderCommissionSettleLog)throws Exception {
		this.save("insert",orderCommissionSettleLog);
	}

	@Override
	public List<Map<String, Object>> queryOrderAndProductInfo()
			throws Exception {
		return this.getList("queryOrderAndProductInfo",null);
	}

	@Override
	public void batchUpdateIsRecorded(List<Map<String, Object>> mapList)
			throws Exception {
		this.update("batchUpdateIsRecorded", mapList);
		
	}

}
