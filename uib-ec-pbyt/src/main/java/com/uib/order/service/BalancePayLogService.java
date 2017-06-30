package com.uib.order.service;

import com.uib.order.entity.BalancePayLog;

public interface BalancePayLogService {
	
	public void insertPayLog(BalancePayLog balancePayLog) throws Exception;

	public void updatePayLog(BalancePayLog balancePayLog) throws Exception;
	
	public BalancePayLog getPayLogByOrderNo(String orderNo) throws Exception;

}
