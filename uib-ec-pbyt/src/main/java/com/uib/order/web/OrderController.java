package com.uib.order.web;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.RequestContext;

import com.uib.base.BaseController;
import com.uib.cart.service.CartService;
import com.uib.common.enums.ExceptionEnum;
import com.uib.common.enums.IsUsedStates;
import com.uib.common.enums.OrderStatus;
import com.uib.member.dao.CouponDao;
import com.uib.member.entity.Coupon;
import com.uib.member.entity.MemMember;
import com.uib.member.entity.MemReceiver;
import com.uib.member.service.MemMemberService;
import com.uib.mobile.dto.ReturnMsg;
import com.uib.order.dao.OrderShippingRefDao;
import com.uib.order.entity.OrderTable;
import com.uib.order.entity.OrderTableItem;
import com.uib.order.entity.PaymentMethod;
import com.uib.order.entity.ShippingMethod;
import com.uib.order.service.OrderCommissionSettleLogService;
import com.uib.order.service.OrderService;
import com.uib.order.service.OrderTableShippingService;
import com.uib.order.service.PaymentMethodService;
import com.uib.order.service.ShippingMethodService;
import com.uib.product.entity.ProductComment;
import com.uib.product.service.ProductService;
import com.uib.serviceUtils.OrderNoGenerateUtil;
import com.uib.serviceUtils.Utils;

@Controller
@RequestMapping("/order")
public class OrderController extends BaseController {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private OrderService orderService;
	@Autowired
	private MemMemberService memMemberService;
	@Autowired
	private PaymentMethodService paymentMethodService;
	@Autowired
	private ShippingMethodService shippingMethodService;
	@Autowired
	private CartService cartService;
	@Autowired
	private CouponDao couponDao;
	@Autowired
	private OrderTableShippingService shippingService;
	@Autowired
	private ProductService productService;
	@Autowired
	private OrderTableShippingService orderShippingService;
	@Autowired
	private OrderShippingRefDao orderShippingRefDao;
	
	@Autowired
	private  OrderCommissionSettleLogService orderCommissionSettleLogService;

	@Value("${easypayicpURL}")
	private String EASYPAYICP_URL;

	@RequestMapping("/insurance")
	private String gotoInsurance(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) {
		MemMember member = (MemMember) request.getSession().getAttribute(
				"member");
		String url = EASYPAYICP_URL + "/payInsure/xpPcForm?"
				+ "authorizationClient=" + member.getUsername();
		return "redirect:" + url;
	}

	/**
	 * 校方责任险
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/xzxInsurance")
	private String gotoXzxInsurance(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) {
		MemMember member = (MemMember) request.getSession().getAttribute(
				"member");
		String url = EASYPAYICP_URL + "/payInsure/xzxPcForm?"
				+ "authorizationClient=" + member.getUsername();
		return "redirect:" + url;
	}

	/**
	 * 团队旅游险
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/rsywInsurance")
	private String gotoRsywInsurance(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) {
		MemMember member = (MemMember) request.getSession().getAttribute(
				"member");
		String url = EASYPAYICP_URL + "/payInsure/wcnrgnlyPcForm?"
				+ "authorizationClient=" + member.getUsername();
		return "redirect:" + url;
	}

	/**
	 * 取消订单
	 * 
	 * @param id
	 */
	@RequestMapping("/cancelOrder")
	public String cancelOrder(String id, String userName,
			HttpServletRequest request) {
		try {
			// 登陆判断
			MemMember member = (MemMember) request.getSession().getAttribute(
					"member");
			if (Utils.isObjectsBlank(member, member.getUsername())
					|| !member.getUsername().equals(userName)) {// 需认证登陆用户和订单参数是否合法
				return "redirect:/f/login";
			}
			orderService.updateOrderStatus(id,
					String.valueOf(OrderStatus.cancelled.getIndex()), userName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/f/member/myOrder";
	}

	/**
	 * 删除订单
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	public String delete(String id) {
		try {
			orderService.delete(id);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "redirect:/f/member/myOrder";
	}

	/*
	 * @RequestMapping("/shouhou") private String myOrder(HttpServletRequest
	 * request, HttpServletResponse response, ModelMap modelMap) { MemMember
	 * member = (MemMember) request.getSession().getAttribute("member"); return
	 * "/member/apply_returned_table"; }
	 */

	@RequestMapping("/form")
	public String addOrder(String cartId, HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) {
		HttpSession session = request.getSession();
		try {
			// 1.查询当前用户所有的收货地址数
			MemMember member = (MemMember) session.getAttribute("member");
			Integer address_count = memMemberService
					.getMemReceiverByAddrCount(member.getId());
			// 2.查询当前用户所有的地址
			List<MemReceiver> list = memMemberService
					.getMemReceiverByAddress(member.getId());
			// 2.保存数据
			modelMap.addAttribute("memberId", member.getId());
			modelMap.addAttribute("address_count", address_count);
			modelMap.addAttribute("list", list);
			List<PaymentMethod> paymentMethods = paymentMethodService
					.queryAllPaymentMethods();
			modelMap.addAttribute("paymentMethods", paymentMethods);
			List<ShippingMethod> shippingMethods = shippingMethodService
					.quaryAllShippingMethods();
			modelMap.addAttribute("shippingMethods", shippingMethods);
			modelMap.addAttribute("cart", cartService.getCurrentCart());
			List<Coupon> coupons = couponDao.selectCouponsByMemberId(member
					.getId());
			modelMap.addAttribute("coupons", coupons);
		} catch (Exception e) {
			logger.error("订单form界面信息查询出错！", e);
		}
		return "/order/form";
	}

	@RequestMapping("/save")
	public String saveOrder(String receiverId, String cartId, String[] pids,
			OrderTable order, HttpServletRequest request,
			HttpServletResponse response) {
		String result = null;
		String orderNo = OrderNoGenerateUtil.getOrderNo(); // 生成订单号
		try {
			order.setOrderNo(orderNo);
			orderService.addOrder(order, receiverId, cartId, pids);
			BigDecimal amount = order.getAmount();
			request.setAttribute("orderNo", orderNo);
			request.setAttribute("amount", amount);
			request.setAttribute("goodsName", order.getRemarks());
			if (OrderTable.PAYMENT_METHOD_ON_LINE.equals(order
					.getPaymentMethod())) { // 在线支付
				result = "/order/order_payment";
			} else if (OrderTable.PAYMENT_METHOD_BANK_REMITTANCE.equals(order
					.getPaymentMethod())) { // 银行汇款
				result = "/order/order_bank_transfer";
			} else if (OrderTable.PAYMENT_METHOD_CASH_ON_DELIVERY.equals(order
					.getPaymentMethod())) {// 货到付款
				result = "/order/order_cod";
				// 生成发货单
				shippingService.addOrderTableShipping(order);
			}
		} catch (Exception e) {
			logger.error("保存订单出错!", e);
		}
		return result;
	}

	/**
	 * 
	 * @Title: toProductComment
	 * @author sl
	 * @Description: 进入商品评论
	 * @param @return 参数
	 * @return String 返回类型
	 * @throws
	 */
	@RequestMapping("/toProductComment")
	public String toProductComment(String orderTableId, Model model) {
		List<OrderTableItem> list = orderService
				.findByOrderTableId(orderTableId);
		model.addAttribute("list", list);
		return "/product/product_comment";
	}

	/**
	 * 
	 * @Title: toProductComment
	 * @author sl 保存评论
	 */
	@ResponseBody
	@RequestMapping("/saveComment")
	public boolean saveComment(HttpServletRequest request,
			ProductComment productComment) {
		productComment.setIp(request.getLocalAddr());
		productService.insertSelective(productComment);
		return true;

	}

	/**
	 * 跳转到支付页面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/toPayView")
	private String selectPayMethod(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) {
		String orderNo = request.getParameter("orderNo");
		String amount = request.getParameter("amount");
		modelMap.addAttribute("orderNo", orderNo);
		modelMap.addAttribute("amount", amount);
		return "/order/order_payment";
	}

	/**
	 * 订单详情
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/query/orderDetail")
	private String queryOrderDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) {
		try {
			String orderNo = request.getParameter("orderNo");
			String userName = request.getParameter("userName");
			modelMap.addAttribute("order",
					orderService.getOrderTableByOrderNo(orderNo, userName));
		} catch (Exception e) {
			logger.error("查询订单详情出错！", e);
		}
		return "/order/order_detail";
	}

	/**
	 * 根据订单号删除订单
	 * 
	 * @param orderNo
	 * @param request
	 * @return
	 */
	// TODO:接口安全隐患登陆用户可删除其他用户的订单信息，需调整
	@RequestMapping("/deleteByOrderNo")
	@ResponseBody
	public ReturnMsg<Object> delete(String orderNo,String userName, HttpServletRequest request) {
		ReturnMsg<Object> result = new ReturnMsg<Object>();
		RequestContext requestContext = new RequestContext(request);
		try {
			// 登陆判断
			MemMember member = (MemMember) request.getSession().getAttribute(
					"member");
			if (Utils.isObjectsBlank(member, member.getUsername())
					|| !member.getUsername().equals(userName)) {// 需认证登陆用户和订单参数是否合法
				result.setCode(ExceptionEnum.not_login.getIndex());
				result.setMsg(ExceptionEnum.not_login.getValue());
				result.setStatus(false);
				return result;
			}
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
			// 登陆判断
			MemMember member = (MemMember) request.getSession().getAttribute(
					"member");
			if (Utils.isObjectsBlank(member, member.getUsername())
					|| !member.getUsername().equals(order.getUserName())) {// 需认证登陆用户和订单参数是否合法
				result.setCode(ExceptionEnum.not_login.getIndex());
				result.setMsg(ExceptionEnum.not_login.getValue());
				result.setStatus(false);
				return result;
			}
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
					break;
				} else {
					orderService.updateByOrderNO(orderNo,
							OrderStatus.completed.getIndex());
					result.setStatus(true);
					//添加订单结算日志记录
					orderCommissionSettleLogService.insert(orderNo);				
					break;
				}
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
}
