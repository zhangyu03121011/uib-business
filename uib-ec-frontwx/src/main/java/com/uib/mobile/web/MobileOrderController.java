package com.uib.mobile.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.ListUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.RequestContext;

import com.uib.cart.entity.Cart;
import com.uib.cart.entity.CartItem;
import com.uib.cart.service.CartService;
import com.uib.common.enums.ExceptionEnum;
import com.uib.common.enums.IsUsedStates;
import com.uib.common.enums.OrderSource;
import com.uib.common.enums.OrderStatus;
import com.uib.common.mapper.JsonMapper;
import com.uib.common.utils.JacksonUtil;
import com.uib.common.utils.StringUtil;
import com.uib.common.utils.StringUtils;
import com.uib.common.utils.UUIDGenerator;
import com.uib.member.dao.CouponCodeDao;
import com.uib.member.dao.CouponDao;
import com.uib.member.entity.Coupon;
import com.uib.member.entity.CouponCode;
import com.uib.member.entity.MemMember;
import com.uib.member.service.MemMemberService;
import com.uib.mobile.dto.CouponDto;
import com.uib.mobile.dto.OrderItemDto;
import com.uib.mobile.dto.OrderItemPojo4Mobile;
import com.uib.mobile.dto.OrderPojo4Mobile;
import com.uib.mobile.dto.OrderTableDto;
import com.uib.mobile.dto.ReturnMsg;
import com.uib.order.dao.OrderShippingRefDao;
import com.uib.order.entity.OrderTable;
import com.uib.order.entity.OrderTableItem;
import com.uib.order.service.OrderService;
import com.uib.order.service.OrderTableShippingService;
import com.uib.product.entity.Product;
import com.uib.product.service.ProductService;
import com.uib.serviceUtils.OrderNoGenerateUtil;
import com.uib.serviceUtils.Utils;

/**
 * 手机
 * 
 * @author gaven
 * 
 */
@Controller
@RequestMapping("/mobile/member/order")
public class MobileOrderController {

	private Logger logger = LoggerFactory.getLogger(MobileOrderController.class);

	@Autowired
	private OrderService orderService;
	@Autowired
	private CartService cartService;
	@Autowired
	private ProductService productService;
	@Autowired
	private MemMemberService memMemberService;
	@Autowired
	private OrderTableShippingService orderShippingService;
	@Autowired
	private OrderShippingRefDao orderShippingRefDao;
	@Autowired
	private CouponDao couponDao;
	@Autowired
	private CouponCodeDao couponCodeDao;

	@RequestMapping(value = "/balancepay", method = RequestMethod.POST)
	@ResponseBody
	public ReturnMsg<Object> balancepay(String sessionId, String password) {
		logger.info("sessionId:" + sessionId + ",password:" + password);
		ReturnMsg<Object> msg = new ReturnMsg<Object>();
		// 非空判断
		if (Utils.isBlank(sessionId) || Utils.isBlank(password)) {
			msg.setCode(ExceptionEnum.param_not_null.getIndex());
			msg.setMsg(ExceptionEnum.param_not_null.getValue() + "sessionId"
					+ sessionId + ",password:" + password);
			msg.setStatus(false);
			return msg;
		}
		// 判断用户名是否存在
		MemMember member = null;
		try {
			member = memMemberService.getMemMemberBySessionId(sessionId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (Utils.isBlank(member)) {
			msg.setCode(ExceptionEnum.member_not_exist.getIndex());
			msg.setMsg(ExceptionEnum.member_not_exist.getValue());
			msg.setStatus(false);
			return msg;
		}

		// 判断密码是否正确
		int count = memMemberService.verifyPassword(member.getUsername(), password);
		if (count == 0) {
			msg.setCode(ExceptionEnum.pass_error.getIndex());
			msg.setMsg(ExceptionEnum.pass_error.getValue() + ",password:"
					+ password);
			msg.setStatus(false);
			return msg;
		}

		msg.setMsg("验证成功");
		msg.setStatus(true);
		return msg;
	}

	/**
	 * 添加订单
	 * 
	 * @param receiverId
	 * @param cartId
	 * @param pid
	 * @param order
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public ReturnMsg<OrderPojo4Mobile> addOrder(String sessionId, String receiverId,
			String cartId, String[] pid, OrderTable order,
			HttpServletRequest request, HttpServletResponse response) {
		ReturnMsg<OrderPojo4Mobile> result = new ReturnMsg<OrderPojo4Mobile>();
		RequestContext requestContext = new RequestContext(request);
		try {
			logger.info("========================receiverId:" + receiverId
					+ ",cartId" + cartId + ",pid:"
					+ JsonMapper.toJsonString(pid) + ",order:"
					+ JsonMapper.toJsonString(order) + ", sessionId:"+sessionId);
			if (Utils.isObjectsBlank(order, cartId, pid,
					order.getPaymentMethod(), sessionId)) {
				result.setCode(ExceptionEnum.param_not_null.getIndex());
				result.setStatus(false);
				result.setMsg(requestContext.getMessage("business.param.notNull"));
				return result;
			}
			if(StringUtils.isEmpty(receiverId)){
				if(StringUtils.isEmpty(order.getConsignee()) || StringUtils.isEmpty(order.getAreaName()) 
						|| StringUtils.isEmpty(order.getAddress()) || StringUtils.isEmpty(order.getPhone()) || StringUtils.isEmpty(order.getArea())){
					result.setCode(ExceptionEnum.param_not_null.getIndex());
					result.setStatus(false);
					result.setMsg(requestContext.getMessage("business.param.notNull"));
					return result;
				}
			}
			
			MemMember member = memMemberService.getMemMemberBySessionId(sessionId);
			order.setUserName(member.getUsername());
			
			String orderNo = OrderNoGenerateUtil.getOrderNo(); // 生成订单号
			order.setOrderNo(orderNo);
			order.setOrderSource(OrderSource.App.getIndex());
			Cart cart = cartService.selectCartById(cartId);
			if (Utils.isBlank(cart)) {
				result.setCode(ExceptionEnum.param_illegal.getIndex());
				result.setStatus(false);
				result.setMsg(requestContext.getMessage("business.param.illegal")
						+ "cartId:" + cartId + ",购物车不存在.");
				return result;
			}
			List<CartItem> cartItems = cart.getCartItems();
			if (Utils.isBlank(cartItems)) {
				result.setCode(ExceptionEnum.cartItems_is_blank.getIndex());
				result.setStatus(false);
				result.setMsg("购物车为空.");
				return result;
			}
			List<String> correctIds = new ArrayList<String>();
			List<String> idList = Arrays.asList(pid);
			for (CartItem item : cartItems) {
				String productId = item.getProductId();
				if (idList.contains(productId)) {
					correctIds.add(productId);
				}
			}
			if (Utils.isBlank(correctIds) || correctIds.size() < pid.length) {
				result.setCode(ExceptionEnum.param_illegal.getIndex());
				result.setStatus(false);
				result.setMsg(requestContext.getMessage("business.param.illegal")
						+ ",购物车不存在的商品id列表:"
						+ ListUtils.subtract(idList, correctIds));
				return result;
			}
			result = checkProduct(pid, result, cartItems);
			if(result != null){
				logger.info("返回参数:" + JacksonUtil.writeValueAsString(result));
				return result;
			}
			result = new ReturnMsg<OrderPojo4Mobile>();
			
			orderService.addOrder(order, receiverId, cartId, pid);
			OrderPojo4Mobile orderPojo4Mobile = new OrderPojo4Mobile();
			BeanUtils.copyProperties(orderPojo4Mobile, order);
			List<OrderItemPojo4Mobile> itemList = new ArrayList<OrderItemPojo4Mobile>();
			List<OrderTableItem> orderItems = order.getList_ordertable_item();
			for (OrderTableItem orderTableItem : orderItems) {
				OrderItemPojo4Mobile it = new OrderItemPojo4Mobile();
				BeanUtils.copyProperties(it, orderTableItem);
				itemList.add(it);
			}
			orderPojo4Mobile.setList_ordertable_item(itemList);
			BigDecimal amount = order.getAmount();
			request.setAttribute("orderNo", orderNo);
			request.setAttribute("amount", amount);
			result.setStatus(true);
			result.setData(orderPojo4Mobile);
			// 生成发货单
			// shippingService.createOrderTableShipping(order);
		} catch (Exception e) {
			logger.error("保存订单出错！", e);
			result.setStatus(false);
			result.setCode(ExceptionEnum.business_logic_exception.getIndex());
			result.setMsg(requestContext.getMessage("business.logic.exception"));
		}
		logger.info("返回参数:" + JacksonUtil.writeValueAsString(result));
		return result;
	}

	private ReturnMsg<OrderPojo4Mobile> checkProduct(String[] pid, ReturnMsg<OrderPojo4Mobile> result,
			List<CartItem> cartItems) throws Exception{
		Product p = null;
		for (CartItem item : cartItems) {
			for(String productId : pid){
				if(!productId.equals(item.getProductId())){
					continue;
				}
				p = productService.findById(item.getProductId());
				// 判断商品是否为空
				if (Utils.isBlank(p)) {
					result.setCode(ExceptionEnum.product_not_exist.getIndex());
					result.setStatus(false);
					result.setMsg(ExceptionEnum.product_not_exist.getValue());
					return result;
				}
				// 判断商品是否下架
				if (p.getAppIsMarketable().equals("0")) {
					result.setCode(ExceptionEnum.product_undercarriage.getIndex());
					result.setStatus(false);
					result.setMsg(ExceptionEnum.product_undercarriage.getValue());
					return result;
				}
				//可用库存 = 库存 - 已分配库存
				int restStock = p.getStock() - p.getAllocatedStock();
				// 判断库存是否不足
				if (item.getQuantity() > restStock) {
					result.setCode(ExceptionEnum.product_Low_stock.getIndex());
					result.setStatus(false);
					result.setMsg(ExceptionEnum.product_Low_stock.getValue());
					return result;
				}
				
				BigDecimal pPrice = new BigDecimal(p.getPrice());
				int ct = pPrice.compareTo(new BigDecimal(item.getProductPrice()));
				
				logger.info("product.price={}...", p.getPrice());
				logger.info("cartItem.product.price={}...", item.getProductPrice());
				
				
				logger.info("ct={}...", ct);
				// 判断商品价格是否改动
				if (ct != 0) {
					result.setCode(ExceptionEnum.product_price_different
							.getIndex());
					result.setStatus(false);
					result.setMsg(ExceptionEnum.product_price_different.getValue());
					return result;
				}
			}
		}
		return null;
	}
	

	/**
	 * 查询订单
	 * 
	 * @param order
	 * @param request
	 * @return
	 */
	@RequestMapping("query")
	@ResponseBody
	public ReturnMsg<List<OrderPojo4Mobile>> queryOrders(String sessionId, OrderTable order,
			HttpServletRequest request) {
		ReturnMsg<List<OrderPojo4Mobile>> msg = new ReturnMsg<List<OrderPojo4Mobile>>();
		RequestContext requestContext = new RequestContext(request);
		try {
			// 非空判断
			if (Utils.isBlank(order)) {
				msg.setCode(ExceptionEnum.param_not_null.getIndex());
				msg.setStatus(false);
				msg.setMsg(requestContext.getMessage("business.param.notNull"));
				return msg;
			}
			MemMember member = memMemberService.getMemMemberBySessionId(sessionId);
			order.setUserName(member.getUsername());
			
			String userName = order.getUserName();
			// 用户名是否存在判断
			if (Utils.isBlank(member)) {
				msg.setCode(ExceptionEnum.param_illegal.getIndex());
				msg.setStatus(false);
				msg.setMsg(requestContext.getMessage("business.param.illegal")
						+ ",该用户不存在,userName:" + userName);
				return msg;
			}
			List<OrderPojo4Mobile> orders = orderService
					.queryOrderPojo4Mobiles2(order);
			msg.setStatus(true);
			msg.setData(orders);
		} catch (Exception e) {
			logger.error("查询订单报错！", e);
			msg.setStatus(false);
			msg.setCode(ExceptionEnum.business_logic_exception.getIndex());
			msg.setMsg(requestContext.getMessage("business.logic.exception"));
		}
		return msg;
	}

	/**
	 * 更新订单状态
	 * 
	 * @param order
	 * @param request
	 * @return
	 */
	@RequestMapping("/update/status")
	@ResponseBody
	public ReturnMsg<Object> updateOrderStatus(OrderTable order,
			HttpServletRequest request) {
		ReturnMsg<Object> result = new ReturnMsg<Object>();
		RequestContext requestContext = new RequestContext(request);
		try {
			// 非空判断
			if (Utils.isBlank(order)
					|| Utils.isObjectsBlank(order.getOrderNo(),
							order.getOrderStatus())) {
				result.setCode(ExceptionEnum.param_not_null.getIndex());
				result.setStatus(false);
				result.setMsg(requestContext
						.getMessage("business.param.notNull")
						+ ":orderNo,orderStatus");
				return result;
			}
			String orderNo = order.getOrderNo();
			String status = order.getOrderStatus();
			if (!status.matches("^\\d$")
					|| !OrderStatus.CheckOrderStatusCode(status)) {
				result.setCode(ExceptionEnum.param_illegal.getIndex());
				result.setStatus(false);
				result.setMsg(requestContext
						.getMessage("business.param.illegal") + ",订单状态非法");
				return result;
			}
			// 订单是否存在、可修改判断
			OrderTable orderTable = orderService.getOrderByOrderNo(orderNo);
			if (Utils.isBlank(orderTable)) {
				result.setCode(ExceptionEnum.order_not_exist.getIndex());
				result.setStatus(false);
				result.setMsg(requestContext.getMessage("order.notExist"));
				return result;
			}
			OrderStatus orderStatus = OrderStatus.getStatusByIndex(orderTable
					.getOrderStatus());
			OrderStatus updateStatus = OrderStatus.getStatusByIndex(status);
			switch (orderStatus) {
			// 可取消订单
			case unconfirmed:
			case wait_pay:
				if (!OrderStatus.cancelled.equals(updateStatus)) {
					result.setCode(ExceptionEnum.param_illegal.getIndex());
					result.setStatus(false);
					result.setMsg(requestContext
							.getMessage("business.param.illegal") + ",订单状态参数错误");
				} else {
					orderService.updateByOrderNO(orderNo,
							updateStatus.getIndex() + "");
					order = orderService.getOrderByOrderNo(orderNo);// TODO:需更新优化
					memMemberService.updateIsUsedByCode(order.getCouponCode(),
							IsUsedStates.Unused.getIndex());
					// 删除发货信息
					Map<String, String> params = new HashMap<String, String>();
					params.put("orderNo", orderNo);
					orderShippingRefDao.delete(params);
					orderShippingService.deleteOrderTableShipping(params);
				}
				break;
			// 可确认收货
			case paid_shipped:
			case shipped:
				if (!OrderStatus.confirmed.equals(updateStatus)) {
					result.setCode(ExceptionEnum.param_illegal.getIndex());
					result.setStatus(false);
					result.setMsg(requestContext
							.getMessage("business.param.illegal")
							+ ",订单不可修改为确认状态");
				} else {
					orderService.updateByOrderNO(orderNo,
							OrderStatus.completed.getIndex());
				}
				result.setStatus(true);
				break;
			// 可删除,修改del_flag
			// case completed:
			// case cancelled:
			// break;
			default:
				result.setCode(ExceptionEnum.param_illegal.getIndex());
				result.setStatus(false);
				result.setMsg(requestContext
						.getMessage("business.param.illegal")
						+ ",订单不可修改成该状态,orderNo:" + orderNo);
				break;
			}
			return result;
		} catch (Exception e) {
			logger.error("修改订单状态出错!", e);
			result.setStatus(false);
			result.setCode(ExceptionEnum.business_logic_exception.getIndex());
			result.setMsg(requestContext.getMessage("business.logic.exception"));
			return result;
		}

	}

	/**
	 * 根据订单号删除订单
	 * 
	 * @param orderNo
	 * @param request
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public ReturnMsg<Object> delete(String orderNo, HttpServletRequest request) {
		ReturnMsg<Object> result = new ReturnMsg<Object>();
		RequestContext requestContext = new RequestContext(request);
		try {
			if (Utils.isBlank(orderNo)) {
				result.setCode(ExceptionEnum.param_not_null.getIndex());
				result.setStatus(false);
				result.setMsg(requestContext
						.getMessage("business.param.notNull") + ":orderNo");
				return result;
			}
			// 订单是否存在、可修改判断
			OrderTable orderTable = orderService.getOrderByOrderNo(orderNo);
			if (Utils.isBlank(orderTable)) {
				result.setCode(ExceptionEnum.order_not_exist.getIndex());
				result.setStatus(false);
				result.setMsg(requestContext.getMessage("order.notExist"));
				return result;
			}
			OrderStatus orderStatus = OrderStatus.getStatusByIndex(orderTable
					.getOrderStatus());
			switch (orderStatus) {
			case completed:
			case cancelled:
				orderService.deleteOrderByOrderNo(orderNo);
				result.setStatus(true);
				break;
			default:
				result.setCode(ExceptionEnum.param_illegal.getIndex());
				result.setStatus(false);
				result.setMsg(requestContext
						.getMessage("business.param.illegal")
						+ ",当前订单状态下不可删除,orderNo:" + orderNo);
				break;
			}
			return result;
		} catch (Exception e) {
			logger.error("删除订单出错", e);
			result.setStatus(false);
			result.setCode(ExceptionEnum.business_logic_exception.getIndex());
			result.setMsg(requestContext.getMessage("business.logic.exception"));
			return result;
		}

	}

	/**
	 * 查询订单详情
	 * 
	 * @param orderNo
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryOrder")
	@ResponseBody
	public ReturnMsg<OrderTableDto> queryOrder(String orderNo, String sessionId) {
		ReturnMsg<OrderTableDto> msg = new ReturnMsg<OrderTableDto>();
		OrderTableDto orderDto = new OrderTableDto();
		Coupon coupon = new Coupon();
		CouponCode couponCode = new CouponCode();
		List<OrderItemDto> orderItemDtos = new ArrayList<OrderItemDto>();
		try {
			logger.info("=======begin 手机APP调用queryOrder方法接口====================");
			logger.info("=======传参 orderNo=" + orderNo + "===sessionId="
					+ sessionId + "=======");
			// 非空判断
			if (Utils.isBlank(orderNo) || Utils.isBlank(sessionId)) {
				msg.setCode(ExceptionEnum.param_not_null.getIndex());
				msg.setStatus(false);
				msg.setMsg(ExceptionEnum.param_not_null.getValue());
				return msg;
			}
			MemMember member = memMemberService.getMemMemberBySessionId(sessionId);
			// 用户名是否存在判断
			if (member == null) {
				msg.setCode(ExceptionEnum.member_not_exist.getIndex());
				msg.setStatus(false);
				msg.setMsg(ExceptionEnum.member_not_exist.getValue());
				return msg;
			}
			OrderTable order = orderService.getOrderTableByOrderNo(orderNo,
					member.getUsername());
			if (order == null) {
				msg.setCode(ExceptionEnum.order_not_exist.getIndex());
				msg.setStatus(false);
				msg.setMsg(ExceptionEnum.order_not_exist.getValue());
				return msg;
			}
			if (!StringUtil.isEmpty(order.getCouponCode())) {
				couponCode = couponCodeDao.getCouponCodeByCouponCode(order
						.getCouponCode());
				coupon = couponDao.getCouponByCouponId(couponCode.getCoupon()
						.getId());
			}
			CouponDto couponDto = new CouponDto();
			couponDto.setIsUsed(couponCode.getIsUsed());
			couponDto.setCouponCode(couponCode.getCode());
			BeanUtils.copyProperties(couponDto, coupon);
			BeanUtils.copyProperties(orderDto, order);
			Product p = null;
			BigDecimal productAmount = new BigDecimal(0);
			for (OrderTableItem oti : order.getList_ordertable_item()) {
				OrderItemDto orderItemDto = new OrderItemDto();
				p = productService.findById(oti.getGoodsNo());
				if (order.getOrderStatus().equals(
						OrderStatus.wait_pay.getIndex())) {
					if (p != null) {
						BigDecimal pPrice = new BigDecimal(p.getPrice()
								.toString());
						oti.setPrice(pPrice);
						orderService.updateOrderPriceByorderItemNo(oti);
					}
				}
				BeanUtils.copyProperties(orderItemDto, oti);
				orderItemDto.setAllocatedStock(p.getAllocatedStock());
				orderItemDto.setIsMarketable(p.getAppIsMarketable());
				orderItemDto.setGoodNo(p.getId());
				orderItemDtos.add(orderItemDto);
				// 计算商品总额
				BigDecimal quantity = new BigDecimal(oti.getQuantity()
						.toString());
				BigDecimal price = quantity.multiply(oti.getPrice());
				productAmount = productAmount.add(price);
				orderDto.setProductAmount(productAmount);
				// 订单总额
				BigDecimal priceAmount = order.getOrderAmount(productAmount);
				orderDto.setAmount(priceAmount);
				orderDto.setCouponDto(couponDto);
				orderDto.setList_ordertable_item(orderItemDtos);
			}
			msg.setData(orderDto);
		} catch (Exception e) {
			msg.setStatus(false);
			msg.setCode(ExceptionEnum.business_logic_exception.getIndex());
			msg.setMsg(ExceptionEnum.business_logic_exception.getValue());
			logger.error("查询订单详情出错", e);
		}
		logger.info("返回参数:" + JacksonUtil.writeValueAsString(msg));
		logger.info("=======end 手机APP调用queryOrder方法接口====================");
		return msg;
	}

	/**
	 * 支付提示
	 * 
	 * @param orderNo
	 * @param request
	 * @return
	 */
	@RequestMapping("/paymentCues")
	@ResponseBody
	public ReturnMsg<Object> paymentCues(String orderNo, String sessionId) {
		ReturnMsg<Object> msg = new ReturnMsg<Object>();
		try {
			logger.info("=======begin 手机APP调用paymentCues方法接口====================");
			logger.info("=======传参 orderNo=" + orderNo + "===sessionId="
					+ sessionId + "=======");
			// 非空判断
			if (Utils.isBlank(orderNo) || Utils.isBlank(sessionId)) {
				msg.setCode(ExceptionEnum.param_not_null.getIndex());
				msg.setStatus(false);
				msg.setMsg(ExceptionEnum.param_not_null.getValue()
						+ ":userName");
				return msg;
			}
			MemMember member = memMemberService.getMemMemberBySessionId(sessionId);
			// 用户名是否存在判断
			if (member == null) {
				msg.setCode(ExceptionEnum.member_not_exist.getIndex());
				msg.setStatus(false);
				msg.setMsg(ExceptionEnum.member_not_exist.getValue());
				return msg;
			}
			OrderTable order = orderService.getOrderTableByOrderNo(orderNo,
					member.getUsername());
			if (order == null) {
				msg.setCode(ExceptionEnum.order_not_exist.getIndex());
				msg.setStatus(false);
				msg.setMsg(ExceptionEnum.order_not_exist.getValue());
				return msg;
			}
			Product p = null;
			for (OrderTableItem oti : order.getList_ordertable_item()) {
				p = productService.findById(oti.getGoodsNo());
				BigDecimal pPrice = new BigDecimal(p.getPrice().toString());
				int ct = pPrice.compareTo(oti.getPrice());
				// 判断商品是否为空
				if (Utils.isBlank(p)) {
					msg.setCode(ExceptionEnum.product_not_exist.getIndex());
					msg.setStatus(false);
					msg.setMsg(ExceptionEnum.product_not_exist.getValue());
					return msg;
				}
				// 判断商品是否下架
				if (p.getAppIsMarketable().equals("0")) {
					msg.setCode(ExceptionEnum.product_undercarriage.getIndex());
					msg.setStatus(false);
					msg.setMsg(ExceptionEnum.product_undercarriage.getValue());
					return msg;
				}
				// 判断库存是否为空
				if (p.getAllocatedStock() <= 0) {
					msg.setCode(ExceptionEnum.product_Low_stock.getIndex());
					msg.setStatus(false);
					msg.setMsg(ExceptionEnum.product_Low_stock.getValue());
					return msg;
				}
				// 判断商品价格是否改动
				if (ct != 0) {
					msg.setCode(ExceptionEnum.product_price_different
							.getIndex());
					msg.setStatus(false);
					msg.setMsg(ExceptionEnum.product_price_different.getValue());
					return msg;
				}
			}
		} catch (Exception e) {
			msg.setStatus(false);
			msg.setCode(ExceptionEnum.business_logic_exception.getIndex());
			msg.setMsg(ExceptionEnum.business_logic_exception.getValue());
			logger.error("支付提示出错", e);
		}
		logger.info("返回参数:" + JacksonUtil.writeValueAsString(msg));
		logger.info("=======end 手机APP调用paymentCues方法接口====================");
		return msg;
	}

	@RequestMapping("/test/generator/couponCode")
	@ResponseBody
	public ReturnMsg<List<CouponCode>> generatorCouponCode(String sessionId,
			String type) {
		ReturnMsg<List<CouponCode>> result = new ReturnMsg<List<CouponCode>>();
		result.setStatus(false);
		try {
			if (Utils.isObjectsBlank(sessionId, type)) {
				result.setCode(ExceptionEnum.param_not_null.getIndex());
				result.setMsg(ExceptionEnum.param_not_null.getValue());
				return result;
			}
			MemMember member = memMemberService.getMemMemberBySessionId(sessionId);
			/*** 注册赠送优惠券 ***/
			List<Coupon> list = couponDao.findUsableCouponByPresentType(type);
			produceCouponCodes(member.getId(), list);
			result.setStatus(true);
		} catch (Exception e) {
			logger.error("生成优惠码报错!", e);
			result.setCode(ExceptionEnum.business_logic_exception.getIndex());
			result.setMsg(ExceptionEnum.business_logic_exception.getValue());
		}
		return result;
	}

	@RequestMapping("/test/query/couponCode")
	@ResponseBody
	public ReturnMsg<List<CouponCode>> queryCouponCode(String sessionId) {
		ReturnMsg<List<CouponCode>> result = new ReturnMsg<List<CouponCode>>();
		result.setStatus(false);
		try {
			if (Utils.isBlank(sessionId)) {
				result.setCode(ExceptionEnum.param_not_null.getIndex());
				result.setMsg(ExceptionEnum.param_not_null.getValue());
				return result;
			}
			MemMember member = memMemberService.getMemMemberBySessionId(sessionId);
			if (Utils.isBlank(member)) {
				result.setCode(ExceptionEnum.member_not_exist.getIndex());
				result.setMsg(ExceptionEnum.member_not_exist.getValue());
				return result;
			}
			result.setData(couponCodeDao.getCouponByMemberId(member.getId()));
			result.setStatus(true);
		} catch (Exception e) {
			logger.error("查询报错!", e);
			result.setCode(ExceptionEnum.business_logic_exception.getIndex());
			result.setMsg(ExceptionEnum.business_logic_exception.getValue());
		}
		return result;
	}

	// 根据优惠券批量生成优惠码
	public void produceCouponCodes(String memberId, List<Coupon> list) {
		if (list.size() > 0) {
			List<CouponCode> couponCodeList = new ArrayList<CouponCode>();
			for (int i = 0; i < list.size(); i++) {
				CouponCode cc = new CouponCode();
				cc.setId(UUIDGenerator.getUUID());
				cc.setCode(UUIDGenerator.getUUID());
				cc.setCouponId(list.get(i).getId());
				cc.setMemberId(memberId);
				couponCodeList.add(cc);
			}
			couponCodeDao.insertList(couponCodeList); // 批量写入优惠券
			couponDao.updateMinusSum(list); // 批量更新优惠券数量-1
		}
	}
	
	/**
	 * 查询订单状态
	 * @param orderNo
	 * @param sessionId
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryOrderStatus")
	@ResponseBody
	public ReturnMsg<String> queryOrderStatus(String orderNo, String sessionId, HttpServletRequest request) {
		logger.info("查询订单状态入参：orderNo=" + orderNo + ",sessionId=" + sessionId);
		ReturnMsg<String> msg = new ReturnMsg<String>();
		try {
			// 非空判断
			if (Utils.isBlank(orderNo) || Utils.isBlank(sessionId)) {
				msg.setStatus(false);
				msg.setCode(ExceptionEnum.param_not_null.getIndex());
				msg.setMsg(ExceptionEnum.param_not_null.getValue());
				return msg;
			}
			MemMember member = memMemberService.getMemMemberBySessionId(sessionId);
			// 用户名是否存在判断
			if (null == member) {
				msg.setStatus(false);
				msg.setCode(ExceptionEnum.member_not_exist.getIndex());
				msg.setMsg(ExceptionEnum.member_not_exist.getValue());
				return msg;
			}
			Map<String,Object> orderInfo = orderService.queryOrderStatus(member.getUsername(), orderNo);
			if(null != orderInfo){
				msg.setStatus(true);
				msg.setData(orderInfo.get("orderStatus").toString());
			}else{
				msg.setStatus(false);
				msg.setCode(ExceptionEnum.order_not_exist.getIndex());
				msg.setMsg(ExceptionEnum.order_not_exist.getValue());
			}
		} catch (Exception e) {
			msg.setStatus(false);
			msg.setCode(ExceptionEnum.business_logic_exception.getIndex());
			msg.setMsg(ExceptionEnum.business_logic_exception.getValue());
			logger.error("查询订单状态出错", e);
		}
		return msg;
	}
}
