package com.uib.order.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.easypay.core.dao.MyBatisDao;
import com.uib.order.dao.OrderCommissionSettleLogDao;
import com.uib.order.entity.OrderCommissionSettleLog;
import com.uib.order.entity.OrderTable;
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
	public List<Map<String, Object>> getOrderAndProdInfo()
			throws Exception {
		return this.getList("getOrderAndProdInfo",null);
	}

	@Override
	public void batchUpdateIsRecorded(List<Map<String, Object>> mapList)
			throws Exception {
		this.update("batchUpdateIsRecorded", mapList);
		
	}
	
	@Override
	public void batchUpdateOrderCommssion(List<OrderTable> orderList)
			throws Exception {
		this.update("batchUpdateOrderCommssion", orderList);
		
	}
	
	@Override
	public void batchUpdateOrderIsNotSettlement(List<OrderTable> orderList)
			throws Exception {
		this.update("batchUpdateOrderIsNotSettlement", orderList);
		
	}
	
	@Override
	public void batchUpdateMemberSumAmount(List<OrderTable> orderList)
			throws Exception {
		this.update("batchUpdateMemberSumAmount", orderList);
		
	}

}
