/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.order.web;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.uib.ecmanager.common.config.Global;
import com.uib.ecmanager.common.enums.OrderSource;
import com.uib.ecmanager.common.enums.OrderStatus;
import com.uib.ecmanager.common.enums.PaymentStatus;
import com.uib.ecmanager.common.enums.Payment_Method;
import com.uib.ecmanager.common.enums.Refunds_Method;
import com.uib.ecmanager.common.enums.ShippingStatus;
import com.uib.ecmanager.common.persistence.Page;
import com.uib.ecmanager.common.utils.OrderNoGenerateUtil;
import com.uib.ecmanager.common.utils.ReturnMsg;
import com.uib.ecmanager.common.utils.StringUtils;
import com.uib.ecmanager.common.web.BaseController;
import com.uib.ecmanager.modules.delivery.entity.DeliveryCorporation;
import com.uib.ecmanager.modules.delivery.service.DeliveryCorporationService;
import com.uib.ecmanager.modules.mem.dao.MemMemberDao;
import com.uib.ecmanager.modules.mem.entity.MemMember;
import com.uib.ecmanager.modules.method.dao.PaymentMethodDao;
import com.uib.ecmanager.modules.method.dao.ShippingMethodDao;
import com.uib.ecmanager.modules.method.entity.PaymentMethod;
import com.uib.ecmanager.modules.method.entity.ShippingMethod;
import com.uib.ecmanager.modules.order.dao.OrderReturnsRefDao;
import com.uib.ecmanager.modules.order.dao.OrderShippingRefDao;
import com.uib.ecmanager.modules.order.dao.OrderTableItemDao;
import com.uib.ecmanager.modules.order.dao.OrderTableReturnsDao;
import com.uib.ecmanager.modules.order.dao.OrderTableShippingDao;
import com.uib.ecmanager.modules.order.dao.OrderTableShippingItemDao;
import com.uib.ecmanager.modules.order.entity.OrderReturnsRef;
import com.uib.ecmanager.modules.order.entity.OrderShippingRef;
import com.uib.ecmanager.modules.order.entity.OrderTable;
import com.uib.ecmanager.modules.order.entity.OrderTableItem;
import com.uib.ecmanager.modules.order.entity.OrderTablePayment;
import com.uib.ecmanager.modules.order.entity.OrderTableRefunds;
import com.uib.ecmanager.modules.order.entity.OrderTableReturns;
import com.uib.ecmanager.modules.order.entity.OrderTableReturnsItem;
import com.uib.ecmanager.modules.order.entity.OrderTableShipping;
import com.uib.ecmanager.modules.order.entity.OrderTableShippingItem;
import com.uib.ecmanager.modules.order.service.OrderReturnsRefService;
import com.uib.ecmanager.modules.order.service.OrderShippingRefService;
import com.uib.ecmanager.modules.order.service.OrderTablePaymentService;
import com.uib.ecmanager.modules.order.service.OrderTableRefundsService;
import com.uib.ecmanager.modules.order.service.OrderTableReturnsService;
import com.uib.ecmanager.modules.order.service.OrderTableService;
import com.uib.ecmanager.modules.order.service.OrderTableShippingService;
import com.uib.ecmanager.modules.product.entity.Product;
import com.uib.ecmanager.modules.product.service.ProductService;
import com.uib.ecmanager.modules.sys.entity.User;
import com.uib.ecmanager.modules.sys.service.SystemService;

/**
 * 订单Controller
 * 
 * @author limy
 * @version 2015-06-08
 */
@Controller
@RequestMapping(value = "${adminPath}/order/orderTable")
public class OrderTableController extends BaseController {
	@Autowired
	private OrderTableService orderTableService;
	// 收款
	@Autowired
	private OrderTablePaymentService orderTablePaymentService;
	// 退款
	@Autowired
	private OrderTableRefundsService orderTableRefundsService;
	// 收货
	@Autowired
	private OrderTableReturnsService orderTableReturnsService;
	// 收货关联
	@Autowired
	private OrderReturnsRefService orderReturnsRefService;
	// 发货
	@Autowired
	private OrderTableShippingService orderTableShippingService;
	// 发货关联
	@Autowired
	private OrderShippingRefService orderShippingRefService;
	// 发货关联
	@Autowired
	private OrderShippingRefDao orderShippingRefDao;
	// 订单项
	@Autowired
	private OrderTableItemDao orderTableItemDao;
	// 用户
	@Autowired
	private SystemService systemService;
	// 物流公司
	@Autowired
	private DeliveryCorporationService deliveryCorporationService;
	// 配送方式
	@Autowired
	private ShippingMethodDao shippingMethodDao;
	// 支付方式
	@Autowired
	private PaymentMethodDao paymentMethodDao;
	// 商品
	@Autowired
	private ProductService productService;
	// 会员
	@Autowired
	private MemMemberDao memMemberDao;
	// 收货
	@Autowired
	private OrderTableReturnsDao orderTableReturnsDao;
	// 发货
	@Autowired
	private OrderTableShippingDao orderTableShippingDao;
	// 收货关联
	@Autowired
	private OrderReturnsRefDao orderReturnsRefDao;
	// 发货关联
	@Autowired
	private OrderTableShippingItemDao orderTableShippingItemDao;

	@ModelAttribute
	public OrderTable get(@RequestParam(required = false) String id) {
		OrderTable entity = null;
		try {
			if (StringUtils.isNotBlank(id)) {
				entity = orderTableService.get(id);
			}
			if (entity == null) {
				entity = new OrderTable();
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		return entity;
	}

	@RequiresPermissions("order:orderTable:view")
	@RequestMapping(value = { "list", "" })
	public String list(OrderTable orderTable, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		try {
			Page<OrderTable> page = orderTableService.findPages(new Page<OrderTable>(request, response), orderTable);
			List<OrderTable> list = page.getList();
			PaymentMethod paymentMethod = new PaymentMethod();
			ShippingMethod shippingMethod = new ShippingMethod();
			for (OrderTable orderTable2 : list) {
				String orderStatus = orderTable2.getOrderStatus();
				String paymentStatus = orderTable2.getPaymentStatus();
				Integer shippingStatus = orderTable2.getShippingStatus();
				String orderSource = orderTable2.getOrderSource();
				String orderStatusName = OrderStatus.getDescription(orderStatus);
				String paymentStatusName = PaymentStatus.getDescription(paymentStatus);
				String shippingStatusName = ShippingStatus.getDescription(shippingStatus);
				String orderSourceName = OrderSource.getDescription(orderSource);
				orderTable2.setOrderStatusName(orderStatusName);
				orderTable2.setPaymentStatusName(paymentStatusName);
				orderTable2.setShippingStatusName(shippingStatusName);
				orderTable2.setOrderSourceName(orderSourceName);
				paymentMethod = paymentMethodDao.get(orderTable2.getPaymentMethod().getId());
//				shippingMethod = shippingMethodDao.get(orderTable2.getShippingMethod().getId());
				orderTable2.setPaymentMethod(paymentMethod);
//				orderTable2.setShippingMethod(shippingMethod);
				
				model.addAttribute("page", page);
			}
		} catch (Exception e) {
			logger.error("查看所有订单信息", e);
		}
		return "modules/order/orderTableList";
	}

	@RequiresPermissions("order:orderTable:view")
	@RequestMapping(value = "form")
	public String form(OrderTable orderTable, Model model) {
		try {
			String orderSource = orderTable.getOrderSource();
			String orderSourceName = OrderSource.getDescription(orderSource);
			orderTable.setOrderSourceName(orderSourceName);
			model.addAttribute("orderTable", orderTable);
			// 收款
			if (orderTable.getOrderTablePaymentList().size() != 0) {
				OrderTablePayment orderTablePayment = new OrderTablePayment();
				orderTablePayment.setDelFlag("0");
				orderTablePayment = orderTablePaymentService.get(orderTable.getOrderTablePaymentList().get(0));
				model.addAttribute("orderTableRefund", orderTablePayment);
				model.addAttribute("orderTablePayments",orderTablePaymentService.findList(orderTable.getOrderTablePaymentList().get(0)));
			}

			// 退款
			if (orderTable.getOrderTableRefundsList().size() != 0) {
				OrderTableRefunds orderTableRefund = new OrderTableRefunds();
				orderTableRefund.setDelFlag("0");
				orderTableRefund = orderTableRefundsService.get(orderTable.getOrderTableRefundsList().get(0));
				model.addAttribute("orderTableRefund",orderTableRefundsService.get(orderTableRefund));
				model.addAttribute("orderTableRefunds",orderTableRefundsService.findList(orderTable.getOrderTableRefundsList().get(0)));
			}

			// 发货
			List<OrderTableShipping> orderTableShippings = new ArrayList<OrderTableShipping>();
			if (orderShippingRefService.findShippingRefList(orderTable.getId()).size() != 0) {
				OrderTableShipping orderTableShipping = new OrderTableShipping();
				List<OrderShippingRef> orderShippingRef = new ArrayList<OrderShippingRef>();
				orderShippingRef = orderShippingRefService.findShippingRefList(orderTable.getId());
				for (OrderShippingRef osf : orderShippingRef) {
					orderTableShipping = orderTableShippingService.get(osf.getOrderTableShipping().getId());
					orderTableShippings.add(orderTableShipping);
				}
				model.addAttribute("orderTableShippings", orderTableShippings);
			}

			// 退货
			List<OrderTableReturns> orderTableReturns = new ArrayList<OrderTableReturns>();
			if (orderReturnsRefService.findReturnsRefList(orderTable.getId()).size() != 0) {
				OrderTableReturns orderTableReturn = new OrderTableReturns();
				List<OrderReturnsRef> orderReturnsRef = new ArrayList<OrderReturnsRef>();
				orderReturnsRef = orderReturnsRefService.findReturnsRefList(orderTable.getId());
				for (OrderReturnsRef orf : orderReturnsRef) {
					orderTableReturn = orderTableReturnsService.get(orf.getOrderTableReturns().getId());
					orderTableReturns.add(orderTableReturn);
				}
				orderTableReturn.setDelFlag("0");
				model.addAttribute("orderTableReturns", orderTableReturns);
			}
			// 订单项
			OrderTableItem orderTableItem = new OrderTableItem();
			Product product = new Product();
			List<Product> products = new ArrayList<Product>();
			if (orderTable.getOrderTableItemList().size() != 0) {
				List<OrderTableItem> orderTableItems = new ArrayList<OrderTableItem>();
				for (OrderTableItem otm : orderTable.getOrderTableItemList()) {
					Product product2 = otm.getProduct();
					String productId = (null != product2)?product2.getId():"";
					String productType = otm.getProductType();
					product = productService.get(productId);
					//如果是B端分享出来的商品 根据分享人的rankId查询商品的价格
					if("1".equals(productType)) {
						//查询推荐人信息
						Map<String, Object> recommendMeber = orderTableService.queryRecommendMeberByOrderNoAndProductId(orderTable.getOrderNo(),productId);
						if(null != recommendMeber) {
							String recommendMemberId = (String)recommendMeber.get("recommendMemberId");
							String recommendMeberName = (String)recommendMeber.get("recommendMeberName");
							String phone = (String)recommendMeber.get("phone");
							product.setDistributorName(recommendMeberName);
							product.setDistributorPhone(phone);
						}
					}
					otm.setProduct(product);
					orderTableItems.add(otm);
					products.add(product);
				}
				orderTableItem.setDelFlag("0");
				model.addAttribute("orderTableItems", orderTableItems);
				model.addAttribute("product", product);
			}

			// 收款方式
			Map<String, String> listPayment = new HashMap<String, String>();
			for (Payment_Method pm : Payment_Method.values()) {
				listPayment.put(pm.getIndex(), pm.getDescription());
			}
			model.addAttribute("listPayment", listPayment);

			// 退款方式
			Map<Integer, String> listRefunds = new HashMap<Integer, String>();
			for (Refunds_Method pm : Refunds_Method.values()) {
				listRefunds.put(pm.getIndex(), pm.getDescription());
			}
			model.addAttribute("listRefunds", listRefunds);

			paymentMethodDao.get(orderTable.getPaymentMethod().getId());

			// 支付方式
			PaymentMethod paymentMethod = new PaymentMethod();
			paymentMethod = paymentMethodDao.get(orderTable.getPaymentMethod().getId());
			model.addAttribute("paymentMethod", paymentMethod);
			PaymentMethod paymentMethod1 = new PaymentMethod();
			model.addAttribute("paymentMethods",paymentMethodDao.findList(paymentMethod1));

			/*// 配送方式
			ShippingMethod shippingMethod = new ShippingMethod();
			if(orderTable.getShippingMethod().getId()!=null){
				shippingMethod = shippingMethodDao.get(orderTable.getShippingMethod().getId());
			}
			model.addAttribute("shippingMethod", shippingMethod);
			ShippingMethod shippingMethod1 = new ShippingMethod();
			model.addAttribute("shippingMethods",shippingMethodDao.findList(shippingMethod1));
			*/
			
			// 物流公司
			DeliveryCorporation deliveryCorporation = new DeliveryCorporation();
			model.addAttribute("deliveryCorps",deliveryCorporationService.findList(deliveryCorporation));
		} catch (Exception e) {
			logger.error("查看订单信息", e);
		}
		return "modules/order/orderTableForm";
	}


	@RequiresPermissions("order:orderTable:view")
	@RequestMapping(value = "formShipping")
	public String formShipping(OrderTable orderTable, Model model) {
		try {
			if(orderTable.getOrderSource()!=null || orderTable.getOrderSource()!=""){
				String orderSource = orderTable.getOrderSource();
				String orderSourceName = OrderSource.getDescription(orderSource);
				orderTable.setOrderSourceName(orderSourceName);
			}
			model.addAttribute("orderTable", orderTable);
			// 收款
			if (orderTable.getOrderTablePaymentList().size() != 0) {
				OrderTablePayment orderTablePayment = new OrderTablePayment();
				orderTablePayment.setDelFlag("0");
				orderTablePayment = orderTablePaymentService.get(orderTable.getOrderTablePaymentList().get(0));
				model.addAttribute("orderTableRefund", orderTablePayment);
				model.addAttribute("orderTablePayments",orderTablePaymentService.findList(orderTable.getOrderTablePaymentList().get(0)));
			}

			// 退款
			if (orderTable.getOrderTableRefundsList().size() != 0) {
				OrderTableRefunds orderTableRefund = new OrderTableRefunds();
				orderTableRefund.setDelFlag("0");
				orderTableRefund = orderTableRefundsService.get(orderTable.getOrderTableRefundsList().get(0));
				model.addAttribute("orderTableRefund",orderTableRefundsService.get(orderTableRefund));
				model.addAttribute("orderTableRefunds",orderTableRefundsService.findList(orderTable.getOrderTableRefundsList().get(0)));
			}

			// 发货
			List<OrderTableShipping> orderTableShippings = new ArrayList<OrderTableShipping>();
			if (orderShippingRefService.findShippingRefList(orderTable.getId()).size() != 0) {
				OrderTableShipping orderTableShipping = new OrderTableShipping();
				List<OrderShippingRef> orderShippingRef = new ArrayList<OrderShippingRef>();
				orderShippingRef = orderShippingRefService.findShippingRefList(orderTable.getId());
				for (OrderShippingRef osf : orderShippingRef) {
					orderTableShipping = orderTableShippingService.get(osf.getOrderTableShipping().getId());
					orderTableShippings.add(orderTableShipping);
				}
				model.addAttribute("orderTableShippings", orderTableShippings);
			}

			// 退货
			List<OrderTableReturns> orderTableReturns = new ArrayList<OrderTableReturns>();
			if (orderReturnsRefService.findReturnsRefList(orderTable.getId()).size() != 0) {
				OrderTableReturns orderTableReturn = new OrderTableReturns();
				List<OrderReturnsRef> orderReturnsRef = new ArrayList<OrderReturnsRef>();
				orderReturnsRef = orderReturnsRefService.findReturnsRefList(orderTable.getId());
				for (OrderReturnsRef orf : orderReturnsRef) {
					orderTableReturn = orderTableReturnsService.get(orf.getOrderTableReturns().getId());
					orderTableReturns.add(orderTableReturn);
				}
				orderTableReturn.setDelFlag("0");
				model.addAttribute("orderTableReturns", orderTableReturns);
			}
			// 订单项
			OrderTableItem orderTableItem = new OrderTableItem();
			Product product = new Product();
			List<Product> products = new ArrayList<Product>();
			if (orderTable.getOrderTableItemList().size() != 0) {
				List<OrderTableItem> orderTableItems = new ArrayList<OrderTableItem>();
				for (OrderTableItem otm : orderTable.getOrderTableItemList()) {
					product = productService.get(otm.getProduct().getId());
					otm.setProduct(product);
					orderTableItems.add(otm);
					products.add(product);
				}
				orderTableItem.setDelFlag("0");
				model.addAttribute("orderTableItems", orderTableItems);
				model.addAttribute("product", product);
			}

			// 收款方式
			Map<String, String> listPayment = new HashMap<String, String>();
			for (Payment_Method pm : Payment_Method.values()) {
				listPayment.put(pm.getIndex(), pm.getDescription());
			}
			model.addAttribute("listPayment", listPayment);

			// 退款方式
			Map<Integer, String> listRefunds = new HashMap<Integer, String>();
			for (Refunds_Method pm : Refunds_Method.values()) {
				listRefunds.put(pm.getIndex(), pm.getDescription());
			}
			model.addAttribute("listRefunds", listRefunds);

			paymentMethodDao.get(orderTable.getPaymentMethod().getId());

			// 支付方式
			PaymentMethod paymentMethod = new PaymentMethod();
			paymentMethod = paymentMethodDao.get(orderTable.getPaymentMethod().getId());
			model.addAttribute("paymentMethod", paymentMethod);
			PaymentMethod paymentMethod1 = new PaymentMethod();
			model.addAttribute("paymentMethods",paymentMethodDao.findList(paymentMethod1));

			// 配送方式
			ShippingMethod shippingMethod1 = new ShippingMethod();
			model.addAttribute("shippingMethods",shippingMethodDao.findList(shippingMethod1));

			
			// 物流公司
			DeliveryCorporation deliveryCorporation = new DeliveryCorporation();
			// for (DeliveryCorporation deliveryCorporations :
			// deliveryCorporationService.findList(deliveryCorporation)) {
			// }
			// model.addAttribute("OrderStatus", OrderStatus.values());
			model.addAttribute("deliveryCorps",deliveryCorporationService.findList(deliveryCorporation));
		} catch (Exception e) {
			logger.error("查看订单信息", e);
		}
		return "modules/order/orderTableFormShipping";
	}
	
	@RequiresPermissions("user")
	@RequestMapping(value = "getShipping")
	@ResponseBody
	public OrderTable getShipping(String id, Model model) {
		OrderTable orderTable = new OrderTable();
		try{
		orderTable = orderTableService.get(id); 
		}catch(Exception e){
			logger.debug("",e);;
		}
		return orderTable;
	}

	@RequiresPermissions("order:orderTable:view")
	@RequestMapping(value = "updateFormView")
	public String updateFormView(OrderTable orderTable, Model model) {
		try {
			String orderSource = orderTable.getOrderSource();
			String orderSourceName = OrderSource.getDescription(orderSource);
			orderTable.setOrderSourceName(orderSourceName);
			model.addAttribute("orderTable", orderTable);
			// 订单项
			OrderTableItem orderTableItem = new OrderTableItem();
			Product product = new Product();
			List<Product> products = new ArrayList<Product>();
			if (orderTable.getOrderTableItemList().size() != 0) {
				List<OrderTableItem> orderTableItems = new ArrayList<OrderTableItem>();
				for (OrderTableItem otm : orderTable.getOrderTableItemList()) {
					product = productService.get(otm.getProduct().getId());
					otm.setProduct(product);
					orderTableItems.add(otm);
					products.add(product);
				}
				orderTableItem.setDelFlag("0");
				model.addAttribute("orderTableItems", orderTableItems);
				model.addAttribute("product", product);
			}
			// 会员
			MemMember member = new MemMember();
			if (orderTable.getMemMember() != null) {
				member = memMemberDao.get(orderTable.getMemMember().getId());
				model.addAttribute("member", member);
			}
			// 支付方式
			PaymentMethod paymentMethod = new PaymentMethod();
			paymentMethod = paymentMethodDao.get(orderTable.getPaymentMethod()
					.getId());
			model.addAttribute("paymentMethod", paymentMethod);
			PaymentMethod paymentMethod1 = new PaymentMethod();
			model.addAttribute("paymentMethods",
					paymentMethodDao.findList(paymentMethod1));

			// 配送方式
			/*ShippingMethod shippingMethod = new ShippingMethod();
			shippingMethod = shippingMethodDao.get(orderTable
					.getShippingMethod().getId());
			model.addAttribute("shippingMethod", shippingMethod);*/
			ShippingMethod shippingMethod1 = new ShippingMethod();
			model.addAttribute("shippingMethods",
					shippingMethodDao.findList(shippingMethod1));
			
		} catch (Exception e) {
			logger.error("修改订单信息", e);
		}
		return "modules/order/orderTableupdateForm";
	}

	/**
	 * 确认
	 */
	@RequiresPermissions("order:orderTable:edit")
	@RequestMapping(value = "confirm")
	public String confirm(String id, RedirectAttributes redirectAttributes) {
		try {
			OrderTable order = orderTableService.get(id);
			User user = systemService.getCurrent();
			if (order != null
					&& !order.isExpired()
					&& order.getOrderStatus().equals(
							OrderStatus.unconfirmed.getIndex())
					&& !order.isLocked(user)) {
				orderTableService.confirm(order, user);
				addMessage(redirectAttributes, "操作成功");
			} else {
				addMessage(redirectAttributes, "参数错误");
			}
		} catch (Exception e) {
			logger.error("确定订单信息", e);
		}
		return "redirect:../../order/orderTable/form?id=" + id;
	}

	/**
	 * 完成
	 */
	@RequiresPermissions("order:orderTable:edit")
	@RequestMapping(value = "complete")
	public String complete(String id, RedirectAttributes redirectAttributes) {
		try {
			OrderTable order = orderTableService.get(id);
			User user = systemService.getCurrent();
			if (order != null
					&& !order.isExpired()
					&& order.getOrderStatus().equals(
							OrderStatus.confirmed.getIndex())
					&& !order.isLocked(user)) {
				orderTableService.complete(order, user);
				addMessage(redirectAttributes, "操作成功");
			} else {
				addMessage(redirectAttributes, "参数错误");
			}
		} catch (Exception e) {
			logger.error("完成订单信息", e);
		}
		return "redirect:../../order/orderTable/form?id=" + id;
	}

	/**
	 * 取消
	 */
	@RequestMapping(value = "/cancel", method = RequestMethod.POST)
	public String cancel(String id, RedirectAttributes redirectAttributes) {
		try {
			OrderTable order = orderTableService.get(id);
			User user = systemService.getCurrent();
			if (order != null
					&& !order.isExpired()
					&& order.getOrderStatus().equals(
							OrderStatus.unconfirmed.getIndex())
					&& !order.isLocked(user)) {

				orderTableService.cancel(order, user);
				addMessage(redirectAttributes, "操作成功");
			} else {
				addMessage(redirectAttributes, "参数错误");
			}
		} catch (Exception e) {
			logger.error("取消订单信息", e);
		}
		return "redirect:../../order/orderTable/form?id=" + id;
	}

	@RequiresPermissions("order:orderTable:save")
	@RequestMapping(value = "save")
	public String save(OrderTable orderTable, Model model,
			RedirectAttributes redirectAttributes) {
		try {
			if (!beanValidator(model, orderTable)) {
				return form(orderTable, model);
			}
			orderTableService.save(orderTable);
			addMessage(redirectAttributes, "保存订单成功");
		} catch (Exception e) {
			logger.error("保存订单信息", e);
		}
		return "redirect:" + Global.getAdminPath()
				+ "/order/orderTable/?repage";
	}

	@RequiresPermissions("order:orderTable:edit")
	@RequestMapping(value = "update")
	public String update(OrderTable orderTable, Model model,
			RedirectAttributes redirectAttributes) {
		try {
			if (!beanValidator(model, orderTable)) {
				return form(orderTable, model);
			}
			orderTableService.update(orderTable);
			addMessage(redirectAttributes, "修改订单成功");
		} catch (Exception e) {
			logger.error("", e);
		}
		return "redirect:" + Global.getAdminPath()
				+ "/order/orderTable/?repage";
	}

	@RequiresPermissions("order:orderTable:edit")
	@RequestMapping(value = "delete")
	public String delete(OrderTable orderTable,
			RedirectAttributes redirectAttributes) {
		try {
			orderTableService.delete(orderTable);
			addMessage(redirectAttributes, "删除订单成功");
		} catch (Exception e) {
			logger.error("删除订单信息", e);
		}
		return "redirect:" + Global.getAdminPath()
				+ "/order/orderTable/?repage";
	}

	@SuppressWarnings("deprecation")
	@RequestMapping(value = "export")
	@ResponseBody
	public void exportOrders(OrderTable orderTable, HttpServletRequest request,
			HttpServletResponse response) {
		OutputStream os = null;
		HSSFWorkbook wb = null;
		try {
			final String userAgent = request.getHeader("USER-AGENT");
			List<OrderTable> orders = new ArrayList<OrderTable>();
			Page<OrderTable> page = orderTableService.findPages(
					new Page<OrderTable>(request, response), orderTable);
			long pageSize = page.getPageSize();
			long countNum = page.getCount();
			int loopAccount = (int) (countNum % pageSize == 0 ? (countNum
					/ pageSize - 1) : (countNum / pageSize));
			orders.addAll(page.getList());
			for (int i = 0; i < loopAccount; i++) {
				page = new Page<OrderTable>();
				page.setPageNo(i + 1);
				page.setPageSize((int) pageSize);
				page = orderTableService.findPages(page, orderTable);
				orders.addAll(page.getList());
			}
			// 第一步，创建一个webbook，对应一个Excel文件
			wb = new HSSFWorkbook();
			// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
			HSSFSheet sheet = wb.createSheet("订单数据");
			// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
			HSSFRow row = sheet.createRow((int) 0);
			// 第四步，创建单元格，并设置值表头 设置表头居中
			HSSFCellStyle style = wb.createCellStyle();
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
			HSSFCell cell = row.createCell((short) 0);
			cell.setCellValue("订单编号");
			cell.setCellStyle(style);
			cell = row.createCell((short) 1);
			cell.setCellValue("订单金额");
			cell.setCellStyle(style);
			cell = row.createCell((short) 2);
			cell.setCellValue("收货人");
			cell.setCellStyle(style);
			cell = row.createCell((short) 3);
			cell.setCellValue("支付方式");
			cell.setCellStyle(style);
			cell = row.createCell((short) 4);
			cell.setCellValue("配送方式");
			cell.setCellStyle(style);
			cell = row.createCell((short) 5);
			cell.setCellValue("订单状态");
			cell.setCellStyle(style);
			cell = row.createCell((short) 6);
			cell.setCellValue("配送状态");
			cell.setCellStyle(style);
			cell = row.createCell((short) 7);
			cell.setCellValue("订单来源");
			cell.setCellStyle(style);
			cell = row.createCell((short) 8);
			cell.setCellValue("创建时间");
			cell.setCellStyle(style);
			int orderSize = orders.size();
			// 第五步，写入实体数据
			for (int i = 0; i < orderSize; i++) {
				row = sheet.createRow((int) i + 1);
				OrderTable ot = orders.get(i);
				row.createCell(0).setCellValue(ot.getOrderNo());
				row.createCell(1).setCellValue(ot.getAmount().doubleValue());
				row.createCell(2).setCellValue(ot.getConsignee());
				row.createCell(3).setCellValue(ot.getPaymentMethodName());
				row.createCell(4).setCellValue(ot.getShippingMethodName());
				row.createCell(5).setCellValue(ot.getPaymentStatusName());
				row.createCell(6).setCellValue(ot.getShippingStatusName());
				row.createCell(7).setCellValue(ot.getOrderSourceName());
				row.createCell(8).setCellValue(
						new SimpleDateFormat("yyyy-MM-dd").format(ot
								.getCreateDate()));
			}
			os = response.getOutputStream();
			response.reset();

			// 解决中文文件名在不同浏览器下乱码问题
			String fileName = "订单记录";
			String finalFileName = null;
			if (StringUtils.contains(userAgent, "MSIE")) {// IE浏览器
				finalFileName = URLEncoder.encode(fileName, "UTF8");
			} else if (StringUtils.contains(userAgent, "Mozilla")) {// google,火狐浏览器
				finalFileName = new String(fileName.getBytes(), "ISO8859-1");
			} else {
				finalFileName = URLEncoder.encode(fileName, "UTF8");// 其他浏览器
			}
			response.setHeader("Content-disposition", "attachment; filename="
					+ finalFileName + ".xls");
			response.setContentType("application/msexcel");
			wb.write(os);
		} catch (Exception e) {
			logger.error("导出信息报错!", e);
		} finally {
			try {
				if (os != null) {
					os.close();
				}
			} catch (IOException e) {
				logger.error("关闭流报错", e);
			}
		}
	}

	/**
	 * 退款
	 

	@RequiresPermissions("order:orderTable:edit")
	@RequestMapping(value = "refunds")
	public String refunds(String orderId, String paymentMethodId,
			OrderTableRefunds orderTableRefunds,
			RedirectAttributes redirectAttributes) {

		try {
			OrderTable order = orderTableService.get(orderId);
			orderTableRefunds.setOrderTable(order);
			PaymentMethod paymentMethod = paymentMethodDao.get(paymentMethodId);
			orderTableRefunds
					.setPaymentMethod(paymentMethod != null ? paymentMethod
							.getName() : null);
			if (order.isExpired()
					|| order.getOrderStatus().equals(
							OrderStatus.confirmed.getIndex())) {
				return ERROR_MESSAGE;
			}
			if (order.getPaymentStatus() != PaymentStatus.paid.getIndex()
					&& order.getPaymentStatus() != PaymentStatus.partialPayment
							.getIndex()
					&& order.getPaymentStatus() != PaymentStatus.partialRefunds
							.getIndex()) {
				return ERROR_MESSAGE;
			}
			if (orderTableRefunds.getAmount().compareTo(new BigDecimal(0)) <= 0
					|| orderTableRefunds.getAmount().compareTo(
							order.getAmountPaid()) > 0) {
				return ERROR_MESSAGE;
			}
			User user = systemService.getCurrent();
			if (order.isLocked(user)) {
				return ERROR_MESSAGE;
			}

			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
			String str = format.format(new Date());
			orderTableRefunds.setRefundNo(str);
			orderTableRefunds.setOperator(user.getName());
			orderTableRefundsService.save(orderTableRefunds);
			orderTableService.refunds(order, orderTableRefunds, user);
			addMessage(redirectAttributes, "操作成功");
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("退款订单信息", ex);
		}
		return "redirect:../../order/orderTable/form?id=" + orderId;
	}
*/
	/**
	 * 发货
	 */

	@RequiresPermissions("order:orderTable:edit")
	@RequestMapping(value = "shipping")
	public String shipping(String orderId, String shippingMethodId,
			String deliveryCorpId, String area, OrderTableShipping shipping,
			 RedirectAttributes redirectAttributes) {
		try {
			User user = systemService.getCurrent();
			String shippingId = UUID.randomUUID().toString().replaceAll("-", "");
			shipping.setShippingNo("E"+OrderNoGenerateUtil.getOrderNo());
			shipping.setId(shippingId);
			OrderTable order = orderTableService.get(orderId);
			if (order == null) {
				return ERROR_MESSAGE;
			}
			List<OrderShippingRef> orderShippingRefList = new ArrayList<OrderShippingRef>();
			List<OrderTableShippingItem> orderTableShippingItemList = new ArrayList<OrderTableShippingItem>();
			for (OrderTableItem oti : order.getOrderTableItemList()) {
				OrderTableShippingItem otsi = new OrderTableShippingItem();
				otsi.setId(UUID.randomUUID().toString().replaceAll("-", ""));
				otsi.setOrderTableShipping(shipping);
				otsi.setProductNo(oti.getProduct().getId());
				otsi.setName(oti.getName());
				otsi.setQuantity(oti.getQuantity());
				otsi.setShippingNo(shipping.getShippingNo());
				otsi.setCreateBy(user);
				otsi.setUpdateBy(user);
				otsi.setCreateDate(new Date());
				otsi.setUpdateDate(new Date());
				otsi.setDelFlag("0");
				orderTableShippingService.saveShippingItem(otsi);
				orderTableShippingItemList.add(otsi);
			}
			shipping.setOrderShippingRefList(orderShippingRefList);
			shipping.setOrderTableShippingItemList(orderTableShippingItemList);
			for (Iterator<OrderTableShippingItem> iterator = shipping.getOrderTableShippingItemList().iterator(); iterator.hasNext();) {
				OrderTableShippingItem shippingItem = iterator.next();
				if (shippingItem == null
						|| StringUtils.isEmpty(shippingItem.getShippingNo())
						|| shippingItem.getQuantity() == null
						|| shippingItem.getQuantity() <= 0) {
					iterator.remove();
					continue;
				}
				OrderTableItem orderTableItem = order.getOrderItem(shippingItem.getProductNo());
				if (orderTableItem == null || shippingItem.getQuantity() > orderTableItem.getQuantity()- orderTableItem.getShippedQuantity()) {
					return ERROR_MESSAGE;
				}
				if (orderTableItem.getProduct() != null && orderTableItem.getProduct().getStock() != null && shippingItem.getQuantity() > orderTableItem.getProduct().getStock()) {
					return ERROR_MESSAGE;
				}
				shippingItem.setName(orderTableItem.getFullName());
				shippingItem.setOrderTableShipping(shipping);
			}
			shipping.setOrderNo(order.getOrderNo());
			ShippingMethod shippingMethod = shippingMethodDao.get(shippingMethodId);
			shipping.setShippingMethod(shippingMethod != null ? shippingMethod.getName() : null);
			DeliveryCorporation deliveryCorp = deliveryCorporationService.get(deliveryCorpId);
			shipping.setDeliveryCorp(deliveryCorp != null ? deliveryCorp.getName() : null);
			shipping.setDeliveryCorpUrl(deliveryCorp != null ? deliveryCorp.getUrl() : null);
			shipping.setDeliveryCorpCode(deliveryCorp != null ? deliveryCorp.getCode() : null);
			shipping.setArea(area);
			if (order.getShippingStatus() != ShippingStatus.unshipped.getIndex()) {
				return ERROR_MESSAGE;
			}
			if (order.isLocked(user)) {
				return ERROR_MESSAGE;
			}
			shipping.setOperator(user.getName());
			shipping.setUpdateBy(user);
			shipping.setUpdateDate(new Date());
			shipping.setCreateBy(user);
			shipping.setCreateDate(new Date());
			orderTableShippingService.saveShipping(shipping);
			OrderShippingRef osf = new OrderShippingRef();
			osf.setId(UUID.randomUUID().toString().replaceAll("-", ""));
			osf.setOrderTabelId(orderId);
			osf.setOrderTableShipping(shipping);
			orderTableShippingService.saveShippingRef(osf);
			orderShippingRefList.add(osf);
			orderTableService.shipping(order, shipping, user);
			addMessage(redirectAttributes, "操作成功");
		} catch (Exception e) {
			logger.error("发货订单信息", e);
		}
		return "redirect:../../order/orderTable/formShipping?id=" + orderId;
	}

	/**
	 * 退货
	 */
	@RequiresPermissions("order:orderTable:edit")
	@RequestMapping(value = "returns")
	public String returns(String orderId, String shippingMethodId,
			String deliveryCorpId, String area, OrderTableReturns returns,
			RedirectAttributes redirectAttributes) {
		try {
			OrderTable order = orderTableService.get(orderId);
			if (order == null) {
				return ERROR_MESSAGE;
			}
			for (Iterator<OrderTableReturnsItem> iterator = returns
					.getOrderTableReturnsItemList().iterator(); iterator
					.hasNext();) {
				OrderTableReturnsItem returnsItem = iterator.next();
				if (returnsItem == null
						|| StringUtils.isEmpty(returnsItem.getReturnNo())
						|| returnsItem.getQuantity() == null
						|| returnsItem.getQuantity() <= 0) {
					iterator.remove();
					continue;
				}
				OrderTableItem orderTableItem = order.getOrderItem(returnsItem
						.getReturnNo());
				if (orderTableItem == null
						|| returnsItem.getQuantity() > orderTableItem
								.getShippedQuantity()
								- orderTableItem.getReturnQuantity()) {
					return ERROR_MESSAGE;
				}
				returnsItem.setName(orderTableItem.getFullName());
				returnsItem.setOrderTableReturns(returns);
			}
			returns.setOrderNo(order.getOrderNo());
			ShippingMethod shippingMethod = shippingMethodDao
					.get(shippingMethodId);
			returns.setShippingMethod(shippingMethod != null ? shippingMethod
					.getName() : null);
			DeliveryCorporation deliveryCorp = deliveryCorporationService
					.get(deliveryCorpId);
			returns.setDeliveryCorp(deliveryCorp != null ? deliveryCorp
					.getName() : null);
			returns.setArea(area);
			if (order.isExpired()
					|| order.getOrderStatus().equals(
							OrderStatus.confirmed.getIndex())) {
				return ERROR_MESSAGE;
			}
			if (order.getShippingStatus() != ShippingStatus.shipped.getIndex()
					&& order.getShippingStatus() != ShippingStatus.partialShipment
							.getIndex()
					&& order.getShippingStatus() != ShippingStatus.partialReturns
							.getIndex()) {
				return ERROR_MESSAGE;
			}
			User user = systemService.getCurrent();
			if (order.isLocked(user)) {
				return ERROR_MESSAGE;
			}
			OrderReturnsRef orderReturnsRef = new OrderReturnsRef();
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
			String str = format.format(new Date());
			returns.setReturnNo(str);
			returns.setOperator(user.getName());
			orderTableReturnsDao.insert(returns);
			orderReturnsRef.setId(UUID.randomUUID().toString());
			orderReturnsRef.setOrderTabelId(order.getId());
			orderReturnsRef.setOrderTableReturns(returns);
			orderReturnsRefDao.insert(orderReturnsRef);
			orderTableService.returns(order, returns, user);
			addMessage(redirectAttributes, "操作成功");
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("退货订单信息", ex);
		}
		return "redirect:../../order/orderTable/form?id=" + orderId;
	}

}