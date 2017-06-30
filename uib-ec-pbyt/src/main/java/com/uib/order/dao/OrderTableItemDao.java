package com.uib.order.dao;

import java.util.List;
import java.util.Map;

import com.uib.order.entity.OrderTableItem;

public interface OrderTableItemDao {
	List<OrderTableItem> getAllOrderTableItemByOrderNo(String orderNo) throws Exception;

	/**
	 * 插入订单项
	 * 
	 * @param item
	 * @throws Exceptioon
	 */
	public void insert(OrderTableItem item) throws Exception;

	/**
	 * 根据订单号修改商品价格
	 * 
	 * @param orderNo
	 * @param orderStatus
	 * @param paymentStatus
	 * @throws Exception
	 */
	void updateOrderPriceByorderItemNo(OrderTableItem oti) throws Exception;

	List<OrderTableItem> findByOrderTableId(String orderTableId);
	
	/**
	 * 更新商品评论状态
	 */
	void updateIsCommentByorderItemNo(String id) throws Exception;
	

	/**
	 * 查询该笔订单用户购买商品数
	 * @param orderNo
	 * @return
	 * @throws Exception
	 */
	List<OrderTableItem> queryProductSales(Map<String, Object> map)throws Exception;
	
	/**
	 * 根据订单号和商品id查询订单项信息
	 * @param orderNo
	 * @return
	 * @throws Exception
	 */
	OrderTableItem queryOrderTableItem(Map<String, Object> map)throws Exception;
}
