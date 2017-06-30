package com.uib.order.dao;

import java.util.List;
import java.util.Map;

import com.uib.member.entity.MemMember;
import com.uib.order.entity.OrderTable;
import com.uib.order.entity.OrderTableItem;

public interface OrderDao {
    
	/**
	 * 根据id查询平台购买的佣金记录
	 * @param map
	 * @throws Exception
	 */
	List<Map<String,Object>> getOrderCommissionByMemberId(Map<String, Object> map) throws Exception;
	/**
	 * 根据ID修改订单状态
	 * 
	 * @param map
	 * @throws Exception
	 */
	void updateOrderStatus(Map<String, Object> map) throws Exception;

	/**
	 * 修改订单信息
	 * 
	 * @param id
	 * @throws Exception
	 */
	void delete(String id) throws Exception;

	/**
	 * 根据订单号伪删除订单
	 * 
	 * @param orderNo
	 * @throws Exception
	 */
	void deleteByOrderNo(String orderNo) throws Exception;

	/**
	 * 根据订单号获取订单信息
	 * 
	 * @param orderNo
	 * @return
	 * @throws Exception
	 */
	OrderTable getOrderByOrderNo(String orderNo) throws Exception;

	/**
	 * 根据订单号,会员名查询订单详情
	 * 
	 * @param orderNo
	 * @return
	 * @throws Exception
	 */
	OrderTable getOrderTableByOrderNo(String orderNo, String userId)
			throws Exception;

	/**
	 * 保存订单
	 * 
	 * @param order
	 * @throws Exception
	 */
	public void insert(OrderTable order) throws Exception;

	/**
	 * 根据订单号，更改状态
	 * 
	 * @param map
	 * @throws Exception
	 */

	public void updateByOrderNO(Map<String, Object> map) throws Exception;

	/**
	 * 
	 * @param order
	 * @return
	 * @throws Exception
	 */
	public List<OrderTable> selectOrderTables(OrderTable order)
			throws Exception;
	
	public int queryOrderDeliveryCount(String userName,String orderStatus);

	/**
	 * 根据条件查询订单
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<OrderTable> selectOrderTables(Map<String, Object> params)
			throws Exception;

	/**
	 * 根据条件统计状态数据
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> selectSatisticsByStatus(
			Map<String, Object> params) throws Exception;

	List<OrderTable> getOrderTableByUserName(MemMember member) throws Exception;

	List<OrderTable> findOrderByKeyword(MemMember member) throws Exception;

	/**
	 * 根据订单号修改订单状态及支付状态
	 * 
	 * @param orderNo
	 * @param orderStatus
	 * @param paymentStatus
	 * @throws Exception
	 */
	void updateOrderStatusAndPayStatus(Map<String, Object> map)
			throws Exception;

	List<Object> findIdByOrderStatus(String orderStatus);

	void updateOrderStatus(List<Object> list);

	/**
	 * 查询订单状态为等待付款并且超出30分钟的订单信息
	 * 
	 * @return
	 * @throws Exception
	 */
	List<OrderTable> getOrderWaitPayStatusTimeout() throws Exception;

	/**
	 * 根据订单状态以及ids修改订单状态信息
	 * 
	 * @param paramMap
	 * @throws Exception
	 */
	void updateOrderStatusByOrderStatusAndIds(Map<String, Object> paramMap)
			throws Exception;

	/**
	 * 根据订单状态以及ids修改订单状态信息(根据手机端特殊请求)
	 * 
	 * @param order
	 * @return
	 * @throws Exception
	 */
	List<OrderTable> selectOrderTables4Mobile(OrderTable order)
			throws Exception;
	
	/**
	 * 根据订单状态以及ids修改订单状态信息(根据手机端特殊请求)
	 * 
	 * @param order
	 * @return
	 * @throws Exception
	 */
	List<OrderTable> selectOrderTables4Mobile2(OrderTable order)
			throws Exception;
	
	/**
	 * 查询该笔订单用户购买商品数
	 * @param orderNo
	 * @return
	 * @throws Exception
	 */
	List<Object> queryProductSales(Map<String, Object> map)throws Exception;
	
	/**
	 * 通过用户名和订单号查询订单状态
	 * @param map
	 * @return
	 * @throws Exception
	 */
	Map<String,Object> queryOrderStatus(Map<String, Object> map)throws Exception;
	
	/***
	 * 查询订单数量
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Integer queryOrderCount(Map<String, Object> map) throws Exception;
	
	/**
	 * 根据购物车商品项编号查询出B端分享人当前等级的商品价格
	 * @param cartItemId
	 * @return
	 */
	public String queryRecommendMeberIdByCartItemId(String cartItemId) throws Exception;
	
	/**
	 * 根据订单号更新商品销量
	 */
	public void updateProductSalesByOrderNO(List<OrderTableItem> orderTableItems) throws Exception;
}
