package com.uib.order.dao;

import java.util.List;
import java.util.Map;

import com.uib.order.entity.OrderCommissionSettleLog;

public interface OrderCommissionSettleLogDao {
	public void insert(OrderCommissionSettleLog orderCommissionSettleLog) throws Exception;
	public List<Map<String,Object>> queryOrderAndProductInfo() throws Exception;
	/**
	 * 批量设置订单佣金结算记录表数据状态为1（已记录、已处理）
	 * @return
	 * @throws Exception
	 */
	public void batchUpdateIsRecorded(List<Map<String,Object>> mapList) throws Exception;

}
