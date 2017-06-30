package com.uib.order.dao;

import java.util.List;
import java.util.Map;

import com.uib.order.entity.OrderCommissionSettleLog;
import com.uib.order.entity.OrderTable;

public interface OrderCommissionSettleLogDao {
	public void insert(OrderCommissionSettleLog orderCommissionSettleLog) throws Exception;
	public List<Map<String,Object>> queryOrderAndProductInfo() throws Exception;
	
	public List<Map<String,Object>> getOrderAndProdInfo() throws Exception;
	/**
	 * 批量设置订单佣金结算记录表数据状态为1（已记录、已处理）
	 * @return
	 * @throws Exception
	 */
	public void batchUpdateIsRecorded(List<Map<String,Object>> mapList) throws Exception;
	
	
	/**
	 * 批量更新用户订单表中未结算的佣金（C端客户从平台购买）
	 * @return
	 * @throws Exception
	 */
	public void batchUpdateOrderCommssion(List<OrderTable> orderList) throws Exception;
	
	
	/**
	 * 在更新订单项里面：未结算字段=结算
	 * @return
	 * @throws Exception
	 */
	public void batchUpdateOrderIsNotSettlement(List<OrderTable> orderList) throws Exception;
	
	
	/**
	 * 批量更新普通用户的贡献值
	 * @return
	 * @throws Exception
	 */
	public void batchUpdateMemberSumAmount(List<OrderTable> orderList) throws Exception;

}
