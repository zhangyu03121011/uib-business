package com.uib.order.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.easypay.core.dao.MyBatisDao;
import com.uib.member.entity.MemMember;
import com.uib.order.dao.OrderDao;
import com.uib.order.entity.OrderTable;
import com.uib.order.entity.OrderTableItem;

/**
 * 订单管理
 * 
 * @author kevin
 *
 */
@Component
public class OrderDaoImpl extends MyBatisDao<OrderTable> implements OrderDao {

	public List<Map<String,Object>> getOrderCommissionByMemberId(Map<String, Object> map)
			throws Exception {
		return this.getList("getOrderCommissionByMemberId",map);
	}
	
	public void delete(String id) throws Exception {
		this.update("delete", id);

	}

	@Override
	public void updateOrderStatus(Map<String, Object> map) throws Exception {
		this.update("updateOrderStatus", map);

	}

	public void updateByOrderNO(Map<String, Object> map) throws Exception {
		this.update("updateByOrderNO", map);
	}

	@Override
	public OrderTable getOrderByOrderNo(String orderNo) throws Exception {
		return this.getUnique("getOrderByOrderNo", orderNo);
	}

	@Override
	public void insert(OrderTable order) throws Exception {
		this.save("insert", order);
	}

	@Override
	public List<OrderTable> selectOrderTables(OrderTable order)
			throws Exception {
		return this.get("selectByOrderStatus", order);
	}
	
	public int queryOrderDeliveryCount(String userName,String orderStatus) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("userName", userName);
		map.put("orderStatus", orderStatus);
		return (int) this.getObjectValue("queryOrderDeliveryCount",map);
	}
	
	@Override
	public List<OrderTable> selectOrderTables4Mobile(OrderTable order) throws Exception{
		return this.get("select4MobileByOrderStatus",order);
	}
	
	@Override
	public List<OrderTable> selectOrderTables4Mobile2(OrderTable order) throws Exception{
		return this.get("select4MobileByOrderStatus2",order);
	}


	@Override
	public List<OrderTable> getOrderTableByUserName(MemMember member)
			throws Exception {

		return this.getObjectList("getOrderTableByUserName", member);
	}

	@Override
	public List<OrderTable> findOrderByKeyword(MemMember member)
			throws Exception {
		return this.getObjectList("findOrderByKeyword", member);
	}

	@Override
	public void deleteByOrderNo(String orderNo) throws Exception {
		this.update("deleteByOrderNo", orderNo);
	}

	@Override
	public void updateOrderStatusAndPayStatus(Map<String, Object> map)
			throws Exception {
		this.update("updateOrderStatusAndPayStatus", map);
	}

	@Override
	public OrderTable getOrderTableByOrderNo(String orderNo, String userId)
			throws Exception {
		// TODO Auto-generated method stub
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("orderNo", orderNo);
		map.put("userId", userId);
		return this.getUnique("getOrderTableByOrderNo", map);
	}

	public List<Object> findIdByOrderStatus(String orderStatus) {

		return super.getObjects("findIdByOrderStatus", orderStatus);
	}

	public void updateOrderStatus(List<Object> list) {
		super.update("updateOrderStatusList", list);
	}

	@Override
	public List<OrderTable> getOrderWaitPayStatusTimeout() throws Exception {
		return this.getObjectList("getOrderWaitPayStatusTimeout");
	}

	@Override
	public void updateOrderStatusByOrderStatusAndIds(
			Map<String, Object> paramMap) throws Exception {
		this.update("updateOrderStatusByOrderStatusAndIds", paramMap);
	}

	@Override
	public List<OrderTable> selectOrderTables(Map<String, Object> params)
			throws Exception {
		return this.get("queryOrderTablesByParams", params);
	}

	@Override
	public Map<String, Object> selectSatisticsByStatus(
			Map<String, Object> params) throws Exception {
		return this.getMap("selectSatisticsByStatus", params);
	}

	@Override
	public List<Object> queryProductSales(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return this.getObjects("queryProductSales",map);
	}

	@Override
	public Map<String, Object> queryOrderStatus(Map<String, Object> map)
			throws Exception {
		return this.getMap("queryOrderStatus", map);
	}

	@Override
	public Integer queryOrderCount(Map<String, Object> map) throws Exception {
		Integer object = (Integer) super.getObjectValue("queryOrderStatus", map);
		return object;
	}
	
	/**
	 * 根据购物车商品项编号查询出B端分享人当前等级的商品价格
	 * @param cartItemId
	 * @return
	 */
	public String queryRecommendMeberIdByCartItemId(String cartItemId) throws Exception {
		return (String) this.getObjectValue("queryRecommendMeberIdByCartItemId", cartItemId);
	}
	
	/**
	 * 根据订单号更新商品销量
	 */
	public void updateProductSalesByOrderNO(List<OrderTableItem> orderTableItemList) throws Exception {
		this.update("updateProductSalesByOrderNO",orderTableItemList);
	}
}
