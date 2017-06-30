package com.uib.order.service;

import java.util.List;
import java.util.Map;

import com.uib.cart.entity.Cart;
import com.uib.mobile.dto.OrderCouponDto;
import com.uib.mobile.dto.OrderPojo4Mobile;
import com.uib.mobile.dto.OrderTableDto;
import com.uib.mobile.dto.ReturnMsg;
import com.uib.order.entity.OrderTable;
import com.uib.order.entity.OrderTableItem;
import com.uib.order.entity.OrderTable.StatusType;

public interface OrderService {

	/**
	 * 修改订单信息
	 * 
	 * @param map
	 * @throws Exception
	 */
	void updateOrderStatus(String id, String orderStatus, String userName)
			throws Exception;

	void delete(String id) throws Exception;

	/**
	 * 根据订单号删除订单
	 * 
	 * @param orderNo
	 * @throws Exception
	 */
	void deleteOrderByOrderNo(String orderNo) throws Exception;

	/**
	 * 根据订单号获取订单信息
	 * 
	 * @param orderNo
	 * @return
	 * @throws Exception
	 */
	OrderTable getOrderByOrderNo(String orderNo) throws Exception;

	/**
	 * 保存订单
	 * 
	 * @param order
	 * @param receiverId
	 * @param cartId
	 * @throws Exception
	 */
	public void addOrder(OrderTable order, String receiverId, String cartId,
			String[] cid) throws Exception;

	/**
	 * 
	 * @param orderNo
	 * @param orderStatus
	 * @throws Exception
	 */
	public void updateByOrderNO(String orderNo, String orderStatus)
			throws Exception;

	/**
	 * 根据用户、订单状态查询订单
	 * 
	 * @param order
	 * @return
	 * @throws Exception
	 */
	public List<OrderPojo4Mobile> queryOrderPojo4Mobiles(OrderTable order)
			throws Exception;
	
	/**
	 * 根据用户、订单状态查询订单
	 * 
	 * @param order
	 * @return
	 * @throws Exception
	 */
	public List<OrderPojo4Mobile> queryOrderPojo4Mobiles2(OrderTable order)
			throws Exception;
	
	/**
	 * 统计待收货订单
	 * @return
	 */
	public int queryOrderDeliveryCount(String userName,String orderStatus) throws Exception;

	/**
	 * 查询用户订单
	 * 
	 * @param order
	 * @return
	 * @throws Exception
	 */
	public List<OrderTable> queryOrderTables(OrderTable order) throws Exception;

	/**
	 * 根据条件查询订单列表
	 * 
	 * @param productId
	 * @param productName
	 * @param orderNo
	 * @param orderStatus
	 * @param payStatus
	 * @param userName
	 * @return
	 * @throws Exception
	 */
	public List<OrderTable> queryOrderTables(String productId,
			String productName, String orderNo, String orderStatus,
			String payStatus, String userName) throws Exception;

	/**
	 * 根据条件统计支付状态数据
	 * 
	 * @param productId
	 * @param productName
	 * @param orderNo
	 * @param orderStatus
	 * @param userName
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> queryStatisticsByPayStatus(String productId,
			String productName, String orderNo, String orderStatus,
			String userName, StatusType type) throws Exception;

	/**
	 * 根据订单号查询关联订单项
	 * 
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public List<OrderTableItem> getAllOrderTableItemByOrderNo(String orderNo)
			throws Exception;

	/**
	 * 根据订单号修改订单状态及支付状态
	 * 
	 * @param orderNo
	 * @param orderStatus
	 * @param paymentStatus
	 * @throws Exception
	 */
	void updateOrderStatusAndPayStatus(String orderNo, String orderStatus,
			String paymentStatus) throws Exception;

	public List<OrderTableItem> findByOrderTableId(String orderTableId);

	/**
	 * 根据订单号修改商品价格
	 * 
	 * @param orderNo
	 * @param orderStatus
	 * @param paymentStatus
	 * @throws Exception
	 */
	void updateOrderPriceByorderItemNo(OrderTableItem oti) throws Exception;

	/**
	 * 根据订单号查询订单详情
	 * 
	 * @param orderNo
	 * @return
	 * @throws Exception
	 */
	OrderTable getOrderTableByOrderNo(String orderNo, String userName)
			throws Exception;

	/**
	 * 查询订单状态等待付款超出30分钟的订单记录
	 * 
	 * @return
	 * @throws Exception
	 */
	List<OrderTable> getOrderWaitPayStatusTimeout() throws Exception;

	ReturnMsg<OrderTableDto> installOrder(OrderTable order, String receiverId,
			String cartId, String[] pid) throws Exception;

	/**
	 * 更新商品评论状态
	 */
	void updateIsCommentByorderItemNo(String id) throws Exception;

	public ReturnMsg<OrderCouponDto> queryCurUserCouponList(String cartId,
			String[] pid, String memberId);

	public int getUseCouponCount(String cartId, String[] pid, String memberId)
			throws Exception;

	/**
	 * 查询该笔订单用户购买商品数
	 * 
	 * @param orderNo
	 * @return
	 * @throws Exception
	 */
	List<OrderTableItem> queryProductSales(String orderNo) throws Exception;

	public void updateOrderByProductSales(String orderNo) throws Exception;

	/**
	 * 通过用户名和订单号查询订单状态
	 * 
	 * @param userName
	 * @param orderNo
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> queryOrderStatus(String userName, String orderNo)
			throws Exception;

	public int addOrderByWechat(OrderTable order, Cart cart, String receiverId,
			String[] pid, String balanceFlag) throws Exception;
}
