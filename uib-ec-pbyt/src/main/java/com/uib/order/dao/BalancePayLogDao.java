package com.uib.order.dao;

import com.uib.order.entity.BalancePayLog;

public interface BalancePayLogDao {

	public void insertPayLog(BalancePayLog balancePayLog) throws Exception;

	public void updatePayLog(BalancePayLog balancePayLog) throws Exception;
	
	/***
	 * 根据订单号查询余额支付记录
	 * @param orderNo
	 * @return
	 * @throws Exception
	 */
	public BalancePayLog getPayLogByOrderNo(String orderNo) throws Exception;

}
