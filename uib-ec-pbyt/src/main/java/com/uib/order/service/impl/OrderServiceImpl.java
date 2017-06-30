package com.uib.order.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.uib.cart.entity.Cart;
import com.uib.cart.entity.CartItem;
import com.uib.cart.service.CartService;
import com.uib.common.bean.ParamBean;
import com.uib.common.enums.ExceptionEnum;
import com.uib.common.enums.IsUsedStates;
import com.uib.common.enums.OperationType;
import com.uib.common.enums.OrderSource;
import com.uib.common.enums.OrderStatus;
import com.uib.common.enums.PaymentMethodCodeEnum;
import com.uib.common.mapper.JsonMapper;
import com.uib.common.utils.StringUtils;
import com.uib.common.utils.UUIDGenerator;
import com.uib.member.dao.CouponCodeDao;
import com.uib.member.dao.CouponDao;
import com.uib.member.dao.MemReceiverDao;
import com.uib.member.entity.Coupon;
import com.uib.member.entity.CouponCode;
import com.uib.member.entity.MemMember;
import com.uib.member.entity.MemReceiver;
import com.uib.member.service.MemMemberService;
import com.uib.mobile.dto.CouponDto;
import com.uib.mobile.dto.OrderCouponDto;
import com.uib.mobile.dto.OrderItemPojo4Mobile;
import com.uib.mobile.dto.OrderPojo4Mobile;
import com.uib.mobile.dto.OrderTableDto;
import com.uib.mobile.dto.ReturnMsg;
import com.uib.mobile.service.RecommendProductLogService;
import com.uib.order.dao.OrderDao;
import com.uib.order.dao.OrderTableItemDao;
import com.uib.order.entity.BalancePayLog;
import com.uib.order.entity.OrderTable;
import com.uib.order.entity.OrderTable.PaymentStatus;
import com.uib.order.entity.OrderTable.ShippingStatus;
import com.uib.order.entity.OrderTable.StatusType;
import com.uib.order.entity.OrderTableItem;
import com.uib.order.entity.PaymentMethod;
import com.uib.order.service.BalancePayLogService;
import com.uib.order.service.OrderService;
import com.uib.order.service.OrderTableShippingService;
import com.uib.order.service.PaymentMethodService;
import com.uib.order.service.ShippingMethodService;
import com.uib.pay.service.PayService;
import com.uib.product.entity.Product;
import com.uib.product.service.ProductService;
import com.uib.ptyt.constants.WechatConstant;
import com.uib.serviceUtils.OrderNoGenerateUtil;
import com.uib.serviceUtils.Utils;
import com.uib.weixin.constant.WxConstant;
import com.uib.weixin.util.UserSession;

@Component
public class OrderServiceImpl implements OrderService {
	private final Logger logger = Logger.getLogger(OrderServiceImpl.class);

	@Autowired
	private OrderDao orderDao;
	@Autowired
	private CouponCodeDao couponCodeDao;
	@Autowired
	private PaymentMethodService paymentMethodService;
	@Autowired
	private ShippingMethodService shippingMethodService;
	@Autowired
	private MemMemberService memberService;
	@Autowired
	private CartService cartService;
	@Autowired
	private MemReceiverDao receiverDao;
	@Autowired
	private OrderTableItemDao orderTableItemDao;
	@Autowired
	private ProductService productService;
	@Autowired
	private OrderTableShippingService shippingService;
	@Autowired
	private CouponDao couponDao;
	@Autowired
	private BalancePayLogService balancePayLogService;
	@Autowired
	private PayService payService;
	
	@Autowired
	private RecommendProductLogService recommendProductLogService;
	
	
	/**
	 * 根据id查询平台购买的佣金记录
	 * @param memberId
	 * @throws Exception
	 */
	public List<Map<String,Object>> getOrderCommissionByMemberId(String memberId) throws Exception{
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("memberId", memberId);
		return orderDao.getOrderCommissionByMemberId(map);
	}
	
	
	
	/**
	 * 更新当前订单状态
	 */
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void updateOrderStatus(String id, String orderStatus, String userName)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("orderStatus", orderStatus);
		map.put("userName", userName);
		orderDao.updateOrderStatus(map);
	}

	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void updateByOrderNO(String orderNo, String orderStatus) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderNo", orderNo);
		map.put("orderStatus", orderStatus);
		//更新订单状态
		orderDao.updateByOrderNO(map);
		if (!OrderStatus.cancelled.getIndex().equals(orderStatus)) {
			return;
		}
		List<OrderTableItem> items = orderTableItemDao
				.getAllOrderTableItemByOrderNo(orderNo);
		List<ParamBean> params = new ArrayList<ParamBean>();
		for (OrderTableItem item : items) {
			params.add(new ParamBean(item.getGoodsNo(), item.getQuantity()));
		}
		if (Utils.isBlank(params)) {
			return;
		}
		//批量更新商品已分配库存
		productService.updateAllocatedStockBatch(params, OperationType.detract);
	}
	
	
	public void delete(String id) throws Exception {
		orderDao.delete(id);
	}

	@Override
	public OrderTable getOrderByOrderNo(String orderNo) throws Exception {
		OrderTable orders = orderDao.getOrderByOrderNo(orderNo);
		if (Utils.isNotBlank(orders)) {
			List<OrderTableItem> orderItems = orderTableItemDao
					.getAllOrderTableItemByOrderNo(orderNo);
			orders.setList_ordertable_item(orderItems);
		}
		return orders;
	}

	/***
	 * 保存订单信息，此处不关联配送方式，不计算运费
	 */
	public void addOrder(OrderTable order, String receiverId, String cartId,
			String[] pid) throws Exception {
		PaymentMethod paymentMethod = paymentMethodService.getByMethodCode(PaymentMethodCodeEnum.getEnum(order.getPaymentMethod()));
		Cart cart = cartService.getCurrentCart();
		if (Utils.isBlank(cart) && Utils.isNotBlank(cartId)) {
			cart = cartService.selectCartById(cartId);
			if (Utils.isBlank(cart)) {
				throw new Exception("购物车为空");
			}
		}
//		if ("null".equalsIgnoreCase(order.getCouponCode())) {
//			order.setCouponCode("");
//		}
//		CouponCode couponCode = couponCodeDao.getCouponCodeByCode(order
//				.getCouponCode());
		MemMember member = memberService.getCurrent();
		if (Utils.isBlank(member) && Utils.isNotBlank(order.getMemberId())) {
			member = memberService.getMemMember(order.getMemberId());
		}
		order.setId(UUIDGenerator.getUUID());
		order.setCreateDate(new Date());
		order.setUpdateDate(new Date());
		order.setOrderStatus(OrderStatus.wait_pay.ordinal() + "");
		order.setFee(new BigDecimal(0));
		order.setCouponDiscount(new BigDecimal(0));
		order.setOffsetAmount(new BigDecimal(0));
		order.setPoint(String.valueOf(cart.getEffectivePoint()));
		order.setAmountPaid("0");
		order.setUserName(member.getUsername());
		order.setCreateBy(member.getUsername());
		order.setUpdateBy(member.getUsername());
		if (StringUtils.isNotEmpty(receiverId)) {
			MemReceiver receiver = receiverDao.getMemReceiverById(receiverId);
			if (receiver != null) {
				order.setConsignee(receiver.getConsignee());
				order.setAreaName(receiver.getAreaName());
				order.setAddress(receiver.getAddress());
				order.setZipCode(receiver.getZipCode());
				order.setPhone(receiver.getPhone());
				order.setArea(receiver.getArea());
			}
		}
		order.setPaymentMethod(paymentMethod.getId());
		order.setPaymentMethodName(paymentMethod.getName());
		order.setPaymentStatus(PaymentStatus.unpaid.ordinal() + "");
		order.setShippingMethod(null);
		order.setFreight(new BigDecimal(0));
		order.setShippingStatus(ShippingStatus.unshipped.ordinal() + "");

//		if (couponCode != null) {
//			if (couponCode.getIsUsed() == 0) {
//				BigDecimal couponDiscount = new BigDecimal(
//						cart.getEffectivePrice()).subtract(couponCode
//						.getCoupon().calculatePrice(cart.getQuantity(),
//								new BigDecimal(cart.getEffectivePrice())));
//				couponDiscount = couponDiscount.compareTo(new BigDecimal(0)) > 0 ? couponDiscount
//						: new BigDecimal(0);
//				order.setCouponDiscount(couponDiscount);
//			}
//		}
		List<OrderTableItem> orderItems = order.getList_ordertable_item();
		orderItems = orderItems == null ? new ArrayList<OrderTableItem>() : orderItems;
		List<String> productIdList = Arrays.asList(pid);
		List<ParamBean> params = new ArrayList<ParamBean>();
		for (CartItem cartItem : cart.getCartItems()) {
			if (cartItem != null && cartItem.getProduct() != null && productIdList.contains(cartItem.getProductId())) {
				Product product = cartItem.getProduct();
				OrderTableItem orderItem = new OrderTableItem();
				orderItem.setId(UUIDGenerator.getUUID());
				orderItem.setOrderNo(order.getOrderNo());
				orderItem.setGoodsNo(product.getId());
				orderItem.setName(product.getName());
				orderItem.setFullName(product.getFullName());
				orderItem.setPrice(new BigDecimal(cartItem.getPrice()));
				orderItem.setWeight(product.getWeight() != null ? product
						.getWeight().intValue() : 0);
				orderItem.setThumbnail(product.getThumbnail());
				if (Utils.isBlank(product.getThumbnail())) {
					orderItem.setThumbnail(product.getImage());
				}
				orderItem.setIsGift("0");
				orderItem.setQuantity(cartItem.getQuantity());
				orderItem.setShippedQuantity(0);
				orderItem.setReturnQuantity(0);
				OrderTable orderTable = new OrderTable();
				orderTable.setId(order.getId());
				orderItem.setOrderTable(orderTable);
				orderItem.setCreateBy(member.getUsername());
				orderItem.setUpdateBy(member.getUsername());
				orderItem.setCreateDate(new Date());
				orderItem.setUpdateDate(new Date());
				orderItem.setDelFlag("0");
				orderItems.add(orderItem);
				params.add(new ParamBean(product.getId(), orderItem.getQuantity()));
			}
		}
		order.setList_ordertable_item(orderItems);
		//是否分配库存
		order.setIsAllocatedStock("1");
		
		orderDao.insert(order);
		
		handleCart(cart, null, orderItems, params);
	}
	
	/**
	 * 根据供应商编号分组商品
	 * @param orderTableItemList
	 * @return
	 */
	private Map<String, List<CartItem>> setSupplierProduct(List<CartItem> cartItemList,String[] pid) {
		Map<String, List<CartItem>> newCartItemMap = new HashMap<String,List<CartItem>>();
		if(null != cartItemList) {
			for (CartItem cartItem : cartItemList) {
				List<String> productIdList = Arrays.asList(pid);
				if (cartItem != null && cartItem.getProduct() != null && productIdList.contains(cartItem.getProductId())) {
					ArrayList<CartItem> newCartItemList = new ArrayList<CartItem>();
					String supplierId = cartItem.getSupplierId();
					if(StringUtils.isNotEmpty(supplierId)) {
						for (CartItem cartItem2 : cartItemList) {
							if(supplierId.equals(cartItem2.getSupplierId())) {
								newCartItemList.add(cartItem2);
							}
						}
						newCartItemMap.put(supplierId, newCartItemList);
					}
					
				}
			}
		}
		logger.info("根据供应商编号分组商品结束======:");
		return newCartItemMap;
	}
	
	
	/**
	 * 保存订单信息
	 * @param order 订单
	 * @param cart  购物车
	 * @param pid   商品
	 * @param balanceFlag 支付标识
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	private int saveOrderInfo(OrderTable order, Cart cart, String[] pid, String balanceFlag) throws Exception {
		int result = 0;
		if(null != order) {
			List<OrderTableItem> orderItems = order.getList_ordertable_item();
			orderItems = orderItems == null ? new ArrayList<OrderTableItem>()
					: orderItems;
			List<String> productIdList = Arrays.asList(pid);
			List<ParamBean> params = new ArrayList<ParamBean>();
			BigDecimal orderAmount = new BigDecimal(0);//订单应付总额
			for (CartItem cartItem : cart.getCartItems()) {
				if (cartItem != null && cartItem.getProduct() != null && productIdList.contains(cartItem.getProductId())) {
					Product product = cartItem.getProduct();
					//判断商品是否为不同供应商
					OrderTableItem orderItem = new OrderTableItem();
					orderItem.setId(UUIDGenerator.getUUID());
					orderItem.setOrderNo(order.getOrderNo());
					orderItem.setGoodsNo(product.getId());
					orderItem.setName(product.getName());
					orderItem.setFullName(product.getFullName());
					//购物车商品的价格
					orderItem.setPrice(new BigDecimal(cartItem.getProductPrice()));
					orderItem.setWeight(product.getWeight() != null ? product
							.getWeight().intValue() : 0);
					orderItem.setThumbnail(product.getThumbnail());
					if (Utils.isBlank(product.getThumbnail())) {
						orderItem.setThumbnail(product.getImage());
					}
					orderItem.setIsGift("0");
					orderItem.setQuantity(cartItem.getQuantity());
					orderItem.setShippedQuantity(0);
					orderItem.setReturnQuantity(0);
					OrderTable orderTable = new OrderTable();
					orderTable.setId(order.getId());
					orderItem.setOrderTable(orderTable);
					orderItem.setCreateBy(order.getUserName());
					orderItem.setUpdateBy(order.getUserName());
					orderItem.setCreateDate(new Date());
					orderItem.setUpdateDate(new Date());
					orderItem.setDelFlag("0");
					//判断商品来源（1 是平台 2是推广分享）
					String productType = cartItem.getProductType();
					if (WechatConstant.product_type_2.equals(productType)) {
						recommendProductLogService.updateIsSettlement(order.getOrderNo(),cartItem.getId());
						orderItem.setOrderType(WechatConstant.order_type_1);
					} else {
						orderItem.setOrderType(WechatConstant.order_type_0);
					}
					MemMember memMember = memberService.getMemMember(order.getMemberId());
					if(null != memMember) {
						orderItem.setUserType(memMember.getUserType());
					}
					orderItems.add(orderItem);
					params.add(new ParamBean(product.getId(), orderItem.getQuantity()));
					orderAmount = orderAmount.add(orderItem.getPrice().multiply(new BigDecimal(orderItem.getQuantity())));
				}
			}
			order.setList_ordertable_item(orderItems);
			orderAmount.add(order.getFreight());
			
			//优惠券
			/*CouponCode couponCode = couponCodeDao.getCouponCodeByCode(order
					.getCouponCode());
			if (couponCode != null) {
				if (couponCode.getIsUsed() == 0) {
					BigDecimal couponDiscount = new BigDecimal(
							cart.getEffectivePrice()).subtract(couponCode
							.getCoupon().calculatePrice(cart.getQuantity(),
									new BigDecimal(cart.getEffectivePrice())));
					couponDiscount = couponDiscount.compareTo(new BigDecimal(0)) > 0 ? couponDiscount
							: new BigDecimal(0);
					order.setCouponDiscount(couponDiscount);
					
					orderAmount.subtract(couponCode.getCoupon().getFacePrice());
				}
			}*/
			order.setTotalPrice(orderAmount);
			
			// 保存订单数据
			orderDao.insert(order);

			// 删除购物车商品、更新库存、优惠券状态
			handleCart(cart, null, orderItems, params);
		}
		return result;
	}

	/***
	 * 保存订单信息，此处不关联配送方式，不计算运费
	 */
	public int addOrderByWechat(OrderTable order, Cart cart, String receiverId,String[] pid, String balanceFlag) throws Exception {
		int orderCount = 0;
		OrderTable baseOrder = new OrderTable();
		BeanUtils.copyProperties(baseOrder, order);;
		Map<String, List<CartItem>> supplierProductList = setSupplierProduct(cart.getCartItems(),pid);
		if (null != supplierProductList) {
			for (Map.Entry<String, List<CartItem>> cartItemList : supplierProductList.entrySet()) {
				order.setSupplierId(cartItemList.getKey());
				// 设置订单基础信息
				setOrderInfo(order, receiverId, cart);
				// 保存处理订单信息
				cart.setCartItems(cartItemList.getValue());
				logger.info("购物车项明细"+JsonMapper.toJsonString(cartItemList.getValue()));
				orderCount = saveOrderInfo(order, cart, pid, balanceFlag);
				order = baseOrder;
			}
		}
		return orderCount;
	}

	/***
	 * 处理用户选择账户余额支付<br>
	 * 1.当余额不足的情况，先冻结用户余额，再跳转至微信支付<br>
	 * 2.当余额足够支付的情况，直接扣取用户余额
	 * @param order
	 * @return -1 系统错误，1余额不足，2余额充足
	 */
	@SuppressWarnings("unused")
	private int handleBalance(OrderTable order){
		int result = 1;
		//余额支付
		try {
			MemMember member = memberService.getMemMember(String.valueOf(UserSession.getSession(WxConstant.wx_user_id)));
			
			BigDecimal dbBalance = new BigDecimal(member.getBalance());
			if(dbBalance.doubleValue() > 0){
				String status = "0";
				String balance = "0";
				BigDecimal payBalance = new BigDecimal(0);//支付金额
				
				//如果账户余额小于订单总额,则先冻结余额部分，订单状态修改为未付款，页面再跳转至微信支付页面支付剩余金额
				if(dbBalance.compareTo(order.getTotalPrice()) == -1){
					payBalance = new BigDecimal(member.getBalance());
					order.setAmountPaid(member.getBalance());
				}else{
					//余额大于等于订单总额，全部扣取余额部分，订单状态修改为已付款，页面直接提示付款成功
					balance = String.valueOf(dbBalance.subtract(order.getTotalPrice()));
					payBalance = order.getTotalPrice();
					status = "1";
					result = 2;
				}
				//修改用户账户余额
				Map<String, String> param = new HashMap<String, String>();
				param.put("balance", balance);
				param.put("id", member.getId());
				memberService.updateMemberInfoById(param);
				
				//记录余额消费记录
				BalancePayLog balancePayLog = new BalancePayLog();
				balancePayLog.setOrderNo(order.getOrderNo());
				balancePayLog.setUserName(member.getUsername());
				balancePayLog.setAmount(payBalance);
				balancePayLog.setStatus(status);
				balancePayLog.setCreateDate(new Date());
				balancePayLogService.insertPayLog(balancePayLog);
			}
		} catch (Exception e) {
			result = -1;
			logger.error("余额支付出错，原因：" + e.getMessage());
		}
		return result;
	}
	
	
	
	/**
	 * 插入订单商品信息，删除购物车商品、更新库存、优惠券状态
	 * @param cart
	 * @param couponCode
	 * @param orderItems
	 * @param params
	 * @throws Exception
	 */
	private void handleCart(Cart cart, CouponCode couponCode,List<OrderTableItem> orderItems, List<ParamBean> params) throws Exception {
		for (OrderTableItem item : orderItems) {
			orderTableItemDao.insert(item);
			cartService.removeById(cart.getId(), item.getGoodsNo());
		}
		if (orderItems.size() == cart.getCartItems().size()) {
			cartService.deleteCartById(cart.getId());
		}
		if(CollectionUtils.isNotEmpty(params)) {
			// 更新库存
			productService.updateAllocatedStockBatch(params, OperationType.add);
		}
		
//		if (Utils.isNotBlank(couponCode)) {
//			// 更新优惠券状态
//			memberService.updateIsUsedByCode(couponCode.getCode(),
//					IsUsedStates.Lock.getIndex());
//		}
	}

	private void setOrderInfo(OrderTable order, String receiverId, Cart cart)
			throws Exception {
		order.setId(UUIDGenerator.getUUID());
		order.setOrderNo(OrderNoGenerateUtil.getOrderNo());
		order.setOrderSource(OrderSource.weixin.getIndex());
		order.setUserName(String.valueOf(UserSession.getSession(WechatConstant.USER_NAME)));
		order.setMemberId(String.valueOf(UserSession.getSession(WechatConstant.USER_ID)));
		order.setCreateDate(new Date());
		order.setUpdateDate(new Date());
		PaymentMethod paymentMethod = paymentMethodService
				.getByMethodCode(PaymentMethodCodeEnum.getEnum(order
						.getPaymentMethod()));

		if ("null".equalsIgnoreCase(order.getCouponCode())) {
			order.setCouponCode("");
		}
		order.setOrderStatus(OrderStatus.wait_pay.ordinal() + "");
		order.setFee(new BigDecimal(0));
		order.setCouponDiscount(new BigDecimal(0));
		order.setOffsetAmount(new BigDecimal(0));
		order.setPoint(String.valueOf(cart.getEffectivePoint()));
		order.setAmountPaid("0");
		order.setCreateBy(order.getUserName());
		order.setUpdateBy(order.getUserName());
		if (StringUtils.isNotEmpty(receiverId)) {
			MemReceiver receiver = receiverDao.getMemReceiverById(receiverId);
			if (receiver != null) {
				order.setConsignee(receiver.getConsignee());
				order.setAreaName(receiver.getAreaName());
				order.setAddress(receiver.getAddress());
				order.setZipCode(receiver.getZipCode());
				order.setPhone(receiver.getPhone());
				order.setArea(receiver.getArea());
			}
		}
		order.setPaymentMethod(paymentMethod.getId());
		order.setPaymentMethodName(paymentMethod.getName());
		order.setPaymentStatus(PaymentStatus.unpaid.ordinal() + "");
		order.setShippingMethod(null);
		order.setFreight(new BigDecimal(0));
		order.setShippingStatus(ShippingStatus.unshipped.ordinal() + "");
		order.setIsAllocatedStock("1");

	}

	public List<OrderPojo4Mobile> queryOrderPojo4Mobiles(OrderTable order)
			throws Exception {
		List<OrderTable> orders = orderDao.selectOrderTables4Mobile(order);
		List<OrderPojo4Mobile> orders_ = new ArrayList<OrderPojo4Mobile>();
		for (OrderTable orderTable : orders) {
			List<OrderTableItem> orderItems = orderTableItemDao.getAllOrderTableItemByOrderNo(orderTable.getOrderNo());
			orderTable.setList_ordertable_item(orderItems);
			OrderPojo4Mobile orderPojo4Mobile = new OrderPojo4Mobile();
			BeanUtils.copyProperties(orderPojo4Mobile, orderTable);
			List<OrderItemPojo4Mobile> itemList = new ArrayList<OrderItemPojo4Mobile>();
			for (OrderTableItem orderTableItem : orderItems) {
				OrderItemPojo4Mobile it = new OrderItemPojo4Mobile();
				BeanUtils.copyProperties(it, orderTableItem);
				itemList.add(it);
			}
			orderPojo4Mobile.setList_ordertable_item(itemList);
			orders_.add(orderPojo4Mobile);
		}
		return orders_;
	}
	
	public List<OrderPojo4Mobile> queryOrderPojo4Mobiles2(OrderTable order)
			throws Exception {
		List<OrderTable> orders = orderDao.selectOrderTables4Mobile2(order);
		List<OrderPojo4Mobile> orders_ = new ArrayList<OrderPojo4Mobile>();
		for (OrderTable orderTable : orders) {
			List<OrderTableItem> orderItems = orderTableItemDao
					.getAllOrderTableItemByOrderNo(orderTable.getOrderNo());
			orderTable.setList_ordertable_item(orderItems);
			OrderPojo4Mobile orderPojo4Mobile = new OrderPojo4Mobile();
			BeanUtils.copyProperties(orderPojo4Mobile, orderTable);
			List<OrderItemPojo4Mobile> itemList = new ArrayList<OrderItemPojo4Mobile>();
			for (OrderTableItem orderTableItem : orderItems) {
				OrderItemPojo4Mobile it = new OrderItemPojo4Mobile();
				BeanUtils.copyProperties(it, orderTableItem);
				itemList.add(it);
			}
			orderPojo4Mobile.setList_ordertable_item(itemList);
			orders_.add(orderPojo4Mobile);
		}
		return orders_;
	}
	
	
	public int queryOrderDeliveryCount(String userName,String orderStatus){
		return orderDao.queryOrderDeliveryCount(userName,orderStatus);
	}

	public List<OrderTable> queryOrderTables(OrderTable order) throws Exception {
		List<OrderTable> orders = orderDao.selectOrderTables(order);
		for (OrderTable orderTable : orders) {
			List<OrderTableItem> orderItems = orderTableItemDao
					.getAllOrderTableItemByOrderNo(orderTable.getOrderNo());
			orderTable.setList_ordertable_item(orderItems);
		}
		return orders;
	}

	public List<OrderTableItem> getAllOrderTableItemByOrderNo(String orderNo)
			throws Exception {
		return orderTableItemDao.getAllOrderTableItemByOrderNo(orderNo);
	}

	public void deleteOrderByOrderNo(String orderNo) throws Exception {
		orderDao.deleteByOrderNo(orderNo);
		Map<String, String> params = new HashMap<String, String>();
		params.put("orderNo", orderNo);
		shippingService.updateDeleteFlag(params);
	}

	public void updateOrderStatusAndPayStatus(String orderNo,
			String orderStatus, String paymentStatus) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("orderNo", orderNo);
		param.put("orderStatus", orderStatus);
		param.put("paymentStatus", paymentStatus);
		orderDao.updateOrderStatusAndPayStatus(param);
	}

	public List<OrderTableItem> findByOrderTableId(String orderTableId) {

		return orderTableItemDao.findByOrderTableId(orderTableId);
	}

	public void updateOrderPriceByorderItemNo(OrderTableItem oti)
			throws Exception {
		orderTableItemDao.updateOrderPriceByorderItemNo(oti);
	}

	public OrderTable getOrderTableByOrderNo(String orderNo, String userId)
			throws Exception {
		OrderTable orders = orderDao.getOrderTableByOrderNo(orderNo, userId);
		List<OrderTableItem> orderItems = orderTableItemDao.getAllOrderTableItemByOrderNo(orderNo);
		orders.setList_ordertable_item(orderItems);
		return orders;
	}

	public List<OrderTable> getOrderWaitPayStatusTimeout() throws Exception {
		List<OrderTable> orderList = orderDao.getOrderWaitPayStatusTimeout();
		if (null != orderList) {
			for (OrderTable order : orderList) {
				List<OrderTableItem> orderItems = orderTableItemDao
						.getAllOrderTableItemByOrderNo(order.getOrderNo());
				order.setList_ordertable_item(orderItems);
			}
		}
		return orderList;
	}

	public List<OrderTable> queryOrderTables(String productId,
			String productName, String orderNo, String orderStatus,
			String payStatus, String userName) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("productId", productId);
		params.put("productName", productName);
		params.put("orderNo", orderNo);
		params.put("orderStatus", orderStatus);
		params.put("payStatus", payStatus);
		params.put("userName", userName);
		return orderDao.selectOrderTables(params);
	}

	public Map<String, Object> queryStatisticsByPayStatus(String productId,
			String productName, String orderNo, String orderStatus,
			String userName, StatusType type) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		if (Utils.isBlank(type)) {
			return params;
		}
		params.put("productId", productId);
		params.put("productName", productName);
		params.put("orderNo", orderNo);
		params.put("orderStatus", orderStatus);
		params.put("userName", userName);
		params.put("statusType", type.name());
		return orderDao.selectSatisticsByStatus(params);
	}

	/****
	 * 去结算，封装订单信息，此处不计算运费，不关联配送方式
	 */
	public ReturnMsg<OrderTableDto> installOrder(OrderTable order,
			String receiverId, String cartId, String[] pid) throws Exception {
		logger.info("去结算，封装订单信息开始===order"+JsonMapper.toJsonString(order));
		logger.info("去结算，封装订单信息开始===cartId"+cartId);
		logger.info("去结算，封装订单信息开始===pid"+pid);
		logger.info("去结算，封装订单信息开始===receiverId"+receiverId);
		ReturnMsg<OrderTableDto> msg = new ReturnMsg<OrderTableDto>();
		PaymentMethod paymentMethod = paymentMethodService.getByMethodCode(PaymentMethodCodeEnum.getEnum(order.getPaymentMethod()));
		logger.info("paymentMethod:" + paymentMethod + "order:"+ order.getPaymentMethod());
		//查询当前购物车信息
		Cart cart = cartService.getCurrentCart();
		if (Utils.isBlank(cart) && Utils.isNotBlank(cartId)) {
			cart = cartService.selectCartById(cartId);
			if (Utils.isBlank(cart)) {
				msg.setCode(ExceptionEnum.cartItems_is_blank.getIndex());
				msg.setStatus(false);
				msg.setMsg(ExceptionEnum.cartItems_is_blank.getValue());
				return msg;
			}
		}
		
		MemReceiver receiver = null;
		String memberId = order.getMemberId();
		if (StringUtils.isNotBlank(receiverId) && !"null".equals(receiverId)) {
			receiver = receiverDao.getMemReceiverById(receiverId);
		} else {
			receiver = memberService.getDefaultMemReceiverByMemberId(memberId);
			if (receiver == null) {
				receiver = memberService.getLastOrderReceiverByMemberId(memberId);
			}
		}
		if (receiver != null) {
			order.setConsignee(receiver.getConsignee());
			order.setAreaName(receiver.getAreaName());
			order.setAddress(receiver.getAddress());
			order.setZipCode(receiver.getZipCode());
			order.setPhone(receiver.getPhone());
			order.setArea(receiver.getArea());
			order.setDefaultAddressFlag(receiver.getIsDefault() ? "1" : "0");
			msg.setMsg(receiver.getId());
		}

		order.setFee(new BigDecimal(0));
		order.setCouponDiscount(new BigDecimal(0));
		order.setOffsetAmount(new BigDecimal(0));
		order.setPoint(String.valueOf(cart.getEffectivePoint()));
		order.setAmountPaid("0");
		order.setPaymentMethod(paymentMethod.getId());
		order.setPaymentMethodName(paymentMethod.getName());
		order.setPaymentStatus(PaymentStatus.unpaid.ordinal() + "");
		order.setFreight(new BigDecimal(0));
		order.setShippingStatus(ShippingStatus.unshipped.ordinal() + "");

		List<OrderTableItem> orderItems = order.getList_ordertable_item();
		orderItems = orderItems == null ? new ArrayList<OrderTableItem>() : orderItems;
		//获取商品id数组
		List<String> productIdList = Arrays.asList(pid);
		for (CartItem cartItem : cart.getCartItems()) {
			if (null != productIdList && cartItem != null && cartItem.getProduct() != null && productIdList.contains(cartItem.getProductId())) {
				Product product = cartItem.getProduct();
				OrderTableItem orderItem = new OrderTableItem();
				
				
				orderItem.setId(UUIDGenerator.getUUID());
				orderItem.setOrderNo(order.getOrderNo());
				orderItem.setGoodsNo(product.getId());
				orderItem.setName(product.getName());
				orderItem.setFullName(product.getFullName());
				orderItem.setPrice(new BigDecimal(cartItem.getProductPrice()));
				orderItem.setWeight(product.getWeight() != null ? product.getWeight().intValue() : 0);
				orderItem.setThumbnail(product.getThumbnail());
				if (Utils.isBlank(product.getThumbnail())) {
					orderItem.setThumbnail(product.getImage());
				}
				orderItem.setIsGift("0");
				orderItem.setQuantity(cartItem.getQuantity());
				orderItem.setShippedQuantity(0);
				orderItem.setReturnQuantity(0);
				
				OrderTable orderTable = new OrderTable();
				orderTable.setId(order.getId());
				
				orderItem.setOrderTable(orderTable);
				//商品规格组
				orderItem.setProductSpecificationList(cartItem.getProductSpecificationList());
				orderItems.add(orderItem);
			}
		}
		order.setList_ordertable_item(orderItems);
		msg.setStatus(true);
		return msg;
	}

	/**
	 * 更新商品评论状态
	 */
	public void updateIsCommentByorderItemNo(String id) throws Exception {
		orderTableItemDao.updateIsCommentByorderItemNo(id);
	}

	public ReturnMsg<OrderCouponDto> queryCurUserCouponList(String cartId,
			String[] pid, String memberId) {
		ReturnMsg<OrderCouponDto> msg = new ReturnMsg<OrderCouponDto>();
		try {
			Cart cart = cartService.getCurrentCart();
			if (Utils.isBlank(cart) && Utils.isNotBlank(cartId)) {
				cart = cartService.selectCartById(cartId);
				if (Utils.isBlank(cart)) {
					msg.setCode(ExceptionEnum.cartItems_is_blank.getIndex());
					msg.setStatus(false);
					msg.setMsg(ExceptionEnum.cartItems_is_blank.getValue());
					return msg;
				}
			}
			// ShippingMethod shippingMethod = shippingMethodService
			// .getShippingMethod(shippingMethodId);

			BigDecimal tempFreight = new BigDecimal(0);
			/*
			 * if(shippingMethod != null){ BigDecimal freight =
			 * shippingMethod.calculateFreight(cart.getWeight()); tempFreight =
			 * tempFreight.add(freight); }
			 */
			double price = 0d;
			for (CartItem cartItem : cart.getCartItems()) {
				for (String productId : pid) {
					if (cartItem.getProductId().equals(productId)) {
						// tempFreight = tempFreight.add(new
						// BigDecimal(cartItem.getPrice()));
						price = price + cartItem.getPrice();
					}
				}
			}
			tempFreight = new BigDecimal(price);
			// 查询出用户所有的优惠券
			List<CouponCode> couponCodeList = memberService
					.getCouponByMemberId(memberId);
			List<CouponDto> couponList = new ArrayList<CouponDto>();
			List<CouponDto> unCouponList = new ArrayList<CouponDto>();
			for (CouponCode couponCode : couponCodeList) {
				Coupon coupon = memberService.getCouponByCouponId(couponCode
						.getCoupon().getId());
				CouponDto couponDto = new CouponDto();
				BeanUtils.copyProperties(couponDto, coupon);
				couponDto.setCouponCode(couponCode.getCode());
				couponDto.setIsUsed(couponCode.getIsUsed());
				// boolean endDateEnd = coupon.getEndDate()!= null && new
				// Date().after(coupon.getEndDate());
				boolean isOK = coupon.getEndDate() != null
						&& !new Date().after(coupon.getEndDate());
				// 过滤未过期且未使用状态的优惠券
				if (isOK
						&& IsUsedStates.Unused.getIndex() == couponCode
								.getIsUsed()) {
					// 可使用
					if (tempFreight.compareTo(coupon.getNeedConsumeBalance()) != -1
							&& new Date().after(coupon.getBeginDate())) {
						couponList.add(couponDto);
					} else {
						// 不可使用
						unCouponList.add(couponDto);
					}
				}
			}
			OrderCouponDto orderCouponDto = new OrderCouponDto();
			orderCouponDto.setUnCouponList(unCouponList);
			orderCouponDto.setCouponList(couponList);
			orderCouponDto.setCouponCount(couponList.size());
			orderCouponDto.setUnCouponCount(unCouponList.size());
			msg.setData(orderCouponDto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return msg;
	}

	/***
	 * 获取用户可用的优惠券数量
	 * 
	 * @param cartId
	 * @param pid
	 * @param memberId
	 * @return
	 * @throws Exception
	 */
	public int getUseCouponCount(String cartId, String[] pid, String memberId)
			throws Exception {
		Cart cart = cartService.getCurrentCart();
		if (Utils.isBlank(cart) && Utils.isNotBlank(cartId)) {
			cart = cartService.selectCartById(cartId);
			if (Utils.isBlank(cart)) {
				return 0;
			}
		}
		BigDecimal tempFreight = new BigDecimal(0);
		double price = 0d;
		for (CartItem cartItem : cart.getCartItems()) {
			for (String productId : pid) {
				if (cartItem.getProductId().equals(productId)) {
					// tempFreight = tempFreight.add(new
					// BigDecimal(cartItem.getPrice()));
					price = price + cartItem.getPrice();
				}
			}
		}
		tempFreight = new BigDecimal(price);
		// 查询出用户所有的优惠券
		List<CouponCode> couponCodeList = memberService
				.getCouponByMemberId(memberId);
		int count = 0;
		for (CouponCode couponCode : couponCodeList) {
			Coupon coupon = memberService.getCouponByCouponId(couponCode
					.getCoupon().getId());
			CouponDto couponDto = new CouponDto();
			BeanUtils.copyProperties(couponDto, coupon);
			couponDto.setCouponCode(couponCode.getCode());
			couponDto.setIsUsed(couponCode.getIsUsed());
			boolean isOK = coupon.getEndDate() != null
					&& !new Date().after(coupon.getEndDate())
					&& new Date().after(coupon.getBeginDate());

			// 过滤已开始、未过期且未使用状态的优惠券
			if (isOK
					&& IsUsedStates.Unused.getIndex() == couponCode.getIsUsed()
					&& tempFreight.compareTo(coupon.getNeedConsumeBalance()) != -1) {
				count++;
			}
		}
		return count;
	}

	/**
	 * 查询该笔订单用户购买商品数
	 * 
	 * @param orderNo
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<OrderTableItem> queryProductSales(String orderNo)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderNo", orderNo);
		return orderTableItemDao.queryProductSales(map);
	}

	@Override
	public void updateOrderByProductSales(String orderNo) throws Exception {
		List<OrderTableItem> orderTableItemList = this
				.queryProductSales(orderNo);
		for (OrderTableItem orderTableItem : orderTableItemList) {
			productService.updateProductSales(orderTableItem.getGoodsNo(),
					orderTableItem.getQuantity());
		}

	}

	@Override
	public Map<String, Object> queryOrderStatus(String userName, String orderNo)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userName", userName);
		map.put("orderNo", orderNo);
		return orderDao.queryOrderStatus(map);
	}
	
	/**
	 * 根据购物车商品项编号查询出B端分享人当前等级的商品价格
	 * @param cartItemId
	 * @return
	 * @throws Exception 
	 */
	public String queryRecommendMeberIdByCartItemId(String cartItemId) throws Exception {
		return orderDao.queryRecommendMeberIdByCartItemId(cartItemId);
	}
	
	/**
	 * 根据订单号更新商品销量
	 */
	@Async
	public void updateProductSalesByOrderNO(String orderNo) throws Exception {
		try {
			logger.info("根据订单号更新商品销量开始 === orderNo:"+orderNo);
			List<OrderTableItem> orderTableItemList = orderTableItemDao.getAllOrderTableItemByOrderNo(orderNo);
			
			orderDao.updateProductSalesByOrderNO(orderTableItemList);
		} catch (Exception e) {
			logger.warn("根据订单号更新商品销量异常"+e);
		}
	}
	/**
	 * 根据订单号和商品id查询订单项信息
	 * @param orderNo
	 * @return
	 * @throws Exception
	 */
	public OrderTableItem queryOrderTableItem(String orderNo,String goodsNo)throws Exception{
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("orderNo", orderNo);
		map.put("goodsNo",goodsNo);
		return orderTableItemDao.queryOrderTableItem(map);
	}
}
