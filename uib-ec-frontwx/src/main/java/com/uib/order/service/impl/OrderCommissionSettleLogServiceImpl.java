package com.uib.order.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uib.order.dao.OrderCommissionSettleLogDao;
import com.uib.order.entity.OrderCommissionSettleLog;
import com.uib.order.service.OrderCommissionSettleLogService;

@Service
public class OrderCommissionSettleLogServiceImpl implements OrderCommissionSettleLogService{

	@Autowired
	private OrderCommissionSettleLogDao orderCommissionSettleLogDao;
	
	@Override
	public void insert(String orderNo)throws Exception {
		OrderCommissionSettleLog orderCommissionSettleLog =new OrderCommissionSettleLog();
		orderCommissionSettleLog.setOrderNo(orderNo);
		orderCommissionSettleLog.setStatus("0");
		orderCommissionSettleLog.setCreateTime(new Date());
		orderCommissionSettleLogDao.insert(orderCommissionSettleLog);
	}

	@Override
	public List<Map<String, Object>> queryOrderAndProductInfo()
			throws Exception {
		return orderCommissionSettleLogDao.queryOrderAndProductInfo();
	}

	@Override
	public void batchUpdateIsRecorded(List<Map<String, Object>> mapList)
			throws Exception {
		orderCommissionSettleLogDao.batchUpdateIsRecorded(mapList);
		
	}
	
	

}
