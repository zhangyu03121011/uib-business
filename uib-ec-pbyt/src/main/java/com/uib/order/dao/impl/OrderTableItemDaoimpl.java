package com.uib.order.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.easypay.core.dao.MyBatisDao;
import com.uib.order.dao.OrderTableItemDao;
import com.uib.order.entity.OrderTableItem;

@Component
public class OrderTableItemDaoimpl extends MyBatisDao<OrderTableItem>implements OrderTableItemDao {

	@Override
	public List<OrderTableItem> getAllOrderTableItemByOrderNo(String orderNo) throws Exception {
		return this.getObjectList("getAllOrderTableItemByOrderNo", orderNo);
	}

	@Override
	public void insert(OrderTableItem item) throws Exception {
		this.save("insert", item);
	}

	public List<OrderTableItem> findByOrderTableId(String orderTableId) {

		return super.get("findByOrderTableId", orderTableId);
	}
	@Override
	public void updateOrderPriceByorderItemNo(OrderTableItem oti) throws Exception {
		this.update("updateOrderPriceByorderItemNo", oti);
	}
	
	/**
	 * 更新商品评论状态
	 */
	public void updateIsCommentByorderItemNo(String id) throws Exception {
		this.update("updateIsCommentByorderItemNo", id);
	}
	
	@Override
	public List<OrderTableItem> queryProductSales(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return this.get("queryProductSales",map);
	}
	
	/**
	 * 根据订单号和商品id查询订单项信息
	 * @param orderNo
	 * @return
	 * @throws Exception
	 */
	public OrderTableItem queryOrderTableItem(Map<String, Object> map)throws Exception{
		return this.getUnique("queryOrderTableItem", map);
	}
}
