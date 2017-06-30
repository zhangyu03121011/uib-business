package com.uib.order.service;

import java.util.List;
import java.util.Map;


public interface OrderCommissionSettleLogService {
	public void insert(String orderNo) throws Exception;
	public List<Map<String,Object>> queryOrderAndProductInfo() throws Exception;
	/**
	 * 批量设置订单佣金结算记录表数据状态为1（已记录、已处理）
	 * @return
	 * @throws Exception
	 */
	public void batchUpdateIsRecorded(List<Map<String,Object>> mapList) throws Exception;


}
