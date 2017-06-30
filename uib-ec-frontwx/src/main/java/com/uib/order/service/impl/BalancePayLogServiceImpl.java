package com.uib.order.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uib.order.dao.BalancePayLogDao;
import com.uib.order.entity.BalancePayLog;
import com.uib.order.service.BalancePayLogService;

@Service
public class BalancePayLogServiceImpl implements BalancePayLogService {

	@Autowired
	private BalancePayLogDao payLogDao;
	
	@Override
	public void insertPayLog(BalancePayLog balancePayLog) throws Exception {
		payLogDao.insertPayLog(balancePayLog);
	}

	@Override
	public void updatePayLog(BalancePayLog balancePayLog) throws Exception {
		payLogDao.updatePayLog(balancePayLog);
	}

	@Override
	public BalancePayLog getPayLogByOrderNo(String orderNo) throws Exception {
		return payLogDao.getPayLogByOrderNo(orderNo);
	}

}
