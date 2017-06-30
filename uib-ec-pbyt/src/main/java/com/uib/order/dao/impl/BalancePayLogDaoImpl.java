package com.uib.order.dao.impl;

import org.springframework.stereotype.Component;

import com.easypay.core.dao.MyBatisDao;
import com.uib.order.dao.BalancePayLogDao;
import com.uib.order.entity.BalancePayLog;

@Component
public class BalancePayLogDaoImpl extends MyBatisDao<BalancePayLog> implements BalancePayLogDao {

	@Override
	public void insertPayLog(BalancePayLog balancePayLog) throws Exception {
		this.save("insert", balancePayLog);
	}

	@Override
	public void updatePayLog(BalancePayLog balancePayLog) throws Exception {
		this.update("updateBalancePayLog", balancePayLog);
	}

	@Override
	public BalancePayLog getPayLogByOrderNo(String orderNo) throws Exception {
		return this.getUnique("getPayLogByOrderNo", orderNo);
	}

}
