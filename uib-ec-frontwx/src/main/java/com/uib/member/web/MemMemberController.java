package com.uib.member.web;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.uib.base.BaseController;
import com.uib.common.enums.OrderStatus;
import com.uib.common.utils.FileUploadUtil;
import com.uib.member.entity.Area;
import com.uib.member.entity.Coupon;
import com.uib.member.entity.CouponCode;
import com.uib.member.entity.MemMember;
import com.uib.member.entity.MemReceiver;
import com.uib.member.service.MemMemberService;
import com.uib.order.entity.OrderTable;
import com.uib.order.service.OrderService;
import com.uib.serviceUtils.Utils;

/**
 * 会员信息管理
 * 
 * @author kevin
 * 
 */
@Controller
@RequestMapping("/member")
public class MemMemberController extends BaseController {

	@Autowired
	private MemMemberService memMemberService;

	@Autowired
	private OrderService OrderService;

	private static final Pattern PRODUCTNO_PATTERN = Pattern
			.compile("^[0-9a-fA-F\\-]{32,36}$");

	private static final Pattern ORDERNO_PATTERN = Pattern
			.compile("^[1-9]\\d{21}$");

	@Value("/index")
	private String indexView;

	@Value("/member/my_order")
	private String myOrderView;

	@Value("/member/product_order")
	private String myProductView;

	@Value("/member/insurance_order")
	private String myInsuranceView;

	@Value("/member/my_coupon")
	private String myCouponView;

	@Value("${easypayicpURL}")
	private String EASYPAYICP_URL;

	@Value("/member/my_address")
	private String myAddressView;

	@RequestMapping("/getMemberByUserName")
	public String getMemMemberByUserName() {
		MemMember member = memMemberService.getMemMemberByUsername("kevin_001");
		System.out.println(member.getName());
		return "";
	}

	/**
	 * 我的订单
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/myOrder")
	private String myOrder(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) {
		MemMember member = (MemMember) request.getSession().getAttribute(
				"member");
		return myOrderView;
	}

	/**
	 * 我的积分
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/myIntegral")
	private String myIntegral(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) {
		// MemMember member = (MemMember)
		// request.getSession().getAttribute("member");
		// return myOrderView;
		try {
			response.getWriter().print("<script>alert('敬请期待');</script>");
			response.getWriter().close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	/**
	 * 商品咨询
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/myAdvisory")
	private String myAdvisory(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) {
		try {
			response.getWriter().print("<script>alert('敬请期待');</script>");
			response.getWriter().close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	/**
	 * 到货通知
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/myArrivalNotice")
	private String myArrivalNotice(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) {
		try {
			response.getWriter().print("<script>alert('敬请期待');</script>");
			response.getWriter().close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	/**
	 * 根据条件查询订单
	 * 
	 * @param queryParam
	 * @param orderStatus
	 * @param payStatus
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/myProduct")
	private String myProductView(String queryParam, String orderStatus,
			String payStatus, HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) {
		MemMember member = (MemMember) request.getSession().getAttribute(
				"member");
		member.setOrderStatusType("");
		member.setFindOrder("");
		try {
			int count_obligation = 0; // 待付款
			int count_received = 0; // 待收货
			int count_evaluate = 0; // 待评价
			List<OrderTable> list = new ArrayList<OrderTable>();
			// if (Utils.isAllBlank(queryParam, orderStatus, payStatus)) {
			// 1.查询所有订单
			// list = memMemberService.getOrderTableByUserName(member);
			String productName = null;
			String productId = null;
			String queryOrderNo = null;
			if (queryParam != null
					&& PRODUCTNO_PATTERN.matcher(queryParam).find()) {
				productId = queryParam;
			} else if (Utils.isNotBlank(queryParam)
					&& ORDERNO_PATTERN.matcher(queryParam).find()) {
				queryOrderNo = queryParam;
			} else {
				productName = queryParam;
			}
			list = OrderService.queryOrderTables(productId, productName,
					queryOrderNo, orderStatus, payStatus, member.getUsername());

			// 2.计算待付款，待评价个数
			for (OrderTable ot : list) {
				// 获取每个订单的订单编号，根据订单号去查询所有关联订单项
				String order_status = ot.getOrderStatus();
				if (OrderStatus.wait_pay.getIndex().equals(order_status)) {
					count_obligation++;
				} else if (OrderStatus.paid_shipped.getIndex().equals(
						order_status)
						|| OrderStatus.shipped.getIndex().equals(order_status)) {
					count_received++;
				} else if (OrderStatus.signfor.getIndex().equals(order_status)) {
					count_evaluate++;
				}
			}
			// 4.保存数据
			modelMap.addAttribute("list", list);
			modelMap.addAttribute("count_obligation", count_obligation);
			modelMap.addAttribute("count_received", count_received);
			modelMap.addAttribute("count_evaluate", count_evaluate);

		} catch (Exception e) {
			logger.error("查询订单出错", e);
		}
		return myProductView;
	}

	/**
	 * 我的保单
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/myInsurance")
	private String myInsurance(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) {
		// HttpSession session = request.getSession();
		// List<TInsureInfo> entityList = new ArrayList<TInsureInfo>();
		try {
			response.getWriter().print("<script>alert('敬请期待');</script>");
			response.getWriter().close();
			return null;
			/*
			 * MemMember member = (MemMember) session.getAttribute("member");
			 * HttpCallResult result = HttpCall.get(EASYPAYICP_URL +
			 * "/payInsure/queryPolicy?authorizationClient=" +
			 * member.getUsername()); // HttpCallResult result = HttpCall.get(
			 * "http://220.112.198.43/eapsypayicp/payInsure/queryPolicy?authorizationClient=23421343"
			 * ); if (result.getStatusCode() == 200) { String content =
			 * result.getContent(); System.out.println("content =" + content);
			 * JavaType javaType =
			 * JacksonUtil.getCollectionType(ArrayList.class,
			 * TInsureInfo.class); entityList = (List<TInsureInfo>)
			 * JacksonUtil.readValueList(content, javaType);
			 * request.setAttribute("insuranceList", entityList); }
			 */
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return myInsuranceView;
	}

	/**
	 * 地址管理
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/myAddress")
	public String myAddress(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) {
		HttpSession session = request.getSession();
		try {
			// 1.查询当前用户所有的收货地址数
			MemMember member = (MemMember) session.getAttribute("member");
			Map<String, Object> approveInfo = memMemberService
					.findApproveInfo(member.getId());
			Object appflag = approveInfo.get("approve_flag");
			if (appflag == null || !("1".equals(appflag.toString()))) {
				response.getWriter().print(
						"<script>alert('未通过身份认证');location='"
								+ request.getContextPath()
								+ "/f/member/toIdentityApprove'</script>");
				response.getWriter().close();
				return null;
			}
			Integer address_count = memMemberService
					.getMemReceiverByAddrCount(member.getId());
			// 2.查询当前用户所有的地址
			List<MemReceiver> list = memMemberService
					.getMemReceiverByAddress(member.getId());
			// 2.保存数据
			modelMap.addAttribute("memberId", member.getId());
			modelMap.addAttribute("address_count", address_count);
			modelMap.addAttribute("list", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return myAddressView;
	}

	// 查询所有地区的parentId
	@RequestMapping("/listByParentId")
	@ResponseBody
	public List<Area> listByParentId(String parentId) throws Exception {
		return memMemberService.findAreasByParentId(parentId);
	}

	@RequestMapping("/updateById")
	@ResponseBody
	public MemReceiver updateById(String id, ModelMap modelMap)
			throws Exception {
		MemReceiver memreceiver = memMemberService.getMemReceiverById(id);

		return memreceiver;
	}

	/**
	 * 修改收货地址
	 * 
	 * @param id
	 */
	@RequestMapping("/update")
	public String update(MemReceiver memReceiver, HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		try {
			MemMember member = (MemMember) session.getAttribute("member");
			memReceiver.setMemMember(member);
			Timestamp d = new Timestamp(System.currentTimeMillis());
			memReceiver.setCreateDate(d);
			memReceiver.setUpdateDate(d);
			memReceiver.setDelFlag("0");
			memMemberService.update(memReceiver);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "redirect:/f/member/myAddress";
	}

	/**
	 * 修改收货地址
	 * 
	 * @param id
	 */
	@RequestMapping("/update_json")
	@ResponseBody
	public Map<String, Object> update4Ajax(MemReceiver memReceiver,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			MemMember member = (MemMember) session.getAttribute("member");
			memReceiver.setMemMember(member);
			Timestamp d = new Timestamp(System.currentTimeMillis());
			memReceiver.setCreateDate(d);
			memReceiver.setUpdateDate(d);
			memReceiver.setDelFlag("0");
			memMemberService.update(memReceiver);
			data.put("message", "修改地址成功");
		} catch (Exception e) {
			logger.error("修改地址出错！", e);
			data.put("message", "修改地址失败");
		}
		return data;
	}

	/**
	 * 修改默认地址
	 * 
	 * @param id
	 */
	@RequestMapping("/update_default_address")
	@ResponseBody
	public Map<String, Object> updateDefaultAddress(MemReceiver memReceiver,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) {
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			MemReceiver getMemMemberId = memMemberService
					.getMemReceiverById(memReceiver.getId());
			List<MemReceiver> list = memMemberService
					.getMemReceiverByAddress(getMemMemberId.getMemMember()
							.getId());
			for (MemReceiver receiver : list) {
				if (receiver.getIsDefault()) {
					receiver.setIsDefault(false);
					memMemberService.update(receiver);
				}
			}
			memMemberService.updateIsDefault(memReceiver);
			data.put("message", "修改默认地址成功");
		} catch (Exception e) {
			logger.error("更新默认地址报错!", e);
			data.put("message", "修改默认地址失败");
		}
		return data;
	}

	/**
	 * 删除收货地址
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	public String delete(String id) {
		try {
			memMemberService.delete(id);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "redirect:/f/member/myAddress";
	}

	/**
	 * 删除收货地址
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete_by_id")
	@ResponseBody
	public Map<String, Object> delete4Ajax(String id) {
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			memMemberService.delete(id);
			data.put("status", "success");
		} catch (Exception e) {
			logger.error("根据id删除地址失败!", e);
			data.put("status", "error");
		}
		return data;
	}

	/**
	 * 添加收货地址
	 * 
	 * @param id
	 */
	@RequestMapping("/save")
	public String save(HttpServletRequest request,
			HttpServletResponse response, MemReceiver memReceiver) {
		HttpSession session = request.getSession();
		try {
			MemMember member = (MemMember) session.getAttribute("member");
			memReceiver.setId(UUID.randomUUID().toString());
			memReceiver.setMemMember(member);
			Timestamp d = new Timestamp(System.currentTimeMillis());
			memReceiver.setCreateDate(d);
			memReceiver.setUpdateDate(d);
			memReceiver.setDelFlag("0");

			List<MemReceiver> list = memMemberService
					.getMemReceiverByAddress(member.getId());
			for (MemReceiver receiver : list) {
				if (receiver.getIsDefault() && memReceiver.getIsDefault()) {
					receiver.setIsDefault(false);
					System.out.println(receiver.getIsDefault());
					memMemberService.update(receiver);
				}
			}
			memMemberService.save(memReceiver);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "redirect:/f/member/myAddress";
	}

	@RequestMapping("/save_json")
	@ResponseBody
	public Map<String, Object> save4Ajax(HttpServletRequest request,
			HttpServletResponse response, MemReceiver memReceiver) {
		HttpSession session = request.getSession();
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			MemMember member = (MemMember) session.getAttribute("member");
			memReceiver.setId(UUID.randomUUID().toString());
			memReceiver.setMemMember(member);
			Timestamp d = new Timestamp(System.currentTimeMillis());
			memReceiver.setCreateDate(d);
			memReceiver.setUpdateDate(d);
			memReceiver.setDelFlag("0");

			List<MemReceiver> list = memMemberService
					.getMemReceiverByAddress(member.getId());
			for (MemReceiver receiver : list) {
				if (receiver.getIsDefault() && memReceiver.getIsDefault()) {
					receiver.setIsDefault(false);
					System.out.println(receiver.getIsDefault());
					memMemberService.update(receiver);
				}
			}
			memMemberService.save(memReceiver);
			data.put("receiver", memReceiver);
		} catch (Exception e) {
			logger.error("保存地址出错！", e);
		}
		return data;
	}

	/**
	 * 我的优惠券
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/myCoupon")
	public String myCoupon(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) {
		HttpSession session = request.getSession();
		try {
			MemMember member = (MemMember) session.getAttribute("member");
			// 1.查询当前用户所有的优惠券
			List<CouponCode> listCouponCode = memMemberService
					.getCouponByMemberId(member.getId());
			List<Coupon> listUsedCoupon = new ArrayList<Coupon>();
			List<Coupon> listNotUsedCoupon = new ArrayList<Coupon>();
			List<Coupon> listEndDateCoupon = new ArrayList<Coupon>();
			for (CouponCode couponCode : listCouponCode) {
				Coupon coupon = memMemberService.getCouponByCouponId(couponCode
						.getCoupon().getId());
				boolean endDateEnd = coupon.getEndDate() != null
						&& new Date().after(coupon.getEndDate());
				if (!endDateEnd && 0 == couponCode.getIsUsed()) {
					listNotUsedCoupon.add(coupon);
				}
				if (1 == couponCode.getIsUsed()) {
					listUsedCoupon.add(coupon);
				}
				if (0 == couponCode.getIsUsed() && endDateEnd) {
					listEndDateCoupon.add(coupon);
				}
			}
			// 2.保存数据
			modelMap.addAttribute("listUsedCoupon", listUsedCoupon);
			modelMap.addAttribute("listNotUsedCoupon", listNotUsedCoupon);
			modelMap.addAttribute("listEndDateCoupon", listEndDateCoupon);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return myCouponView;
	}

	/**
	 * 账户余额
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/myBalance")
	private String myBalance(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) {
		// MemMember member = (MemMember)
		// request.getSession().getAttribute("member");
		// String ToUrl = "/member/my_balance";
		try {
			response.getWriter().print("<script>alert('敬请期待');</script>");
			response.getWriter().close();
			/*
			 * List<Deposit> list =
			 * memMemberService.getAllDepositByUserName(member); if (list.size()
			 * > 0) { Deposit deposit_Latest = list.get(0); long date = new
			 * Date().getTime(); BigDecimal balance =
			 * deposit_Latest.getBalance(); List<Deposit> list_ThreeMonth_before
			 * = new ArrayList<Deposit>(); List<Deposit> list_ThreeMonth_after =
			 * new ArrayList<Deposit>(); for (Deposit deposit : list) { if
			 * ((date - deposit.getCreateDate().getTime()) <= (1000 * 60 * 60 *
			 * 24 * 90L)) { list_ThreeMonth_before.add(deposit); } else {
			 * list_ThreeMonth_after.add(deposit); } }
			 * modelMap.addAttribute("balance", balance);
			 * modelMap.addAttribute("list_ThreeMonth_before",
			 * list_ThreeMonth_before);
			 * modelMap.addAttribute("list_ThreeMonth_after",
			 * list_ThreeMonth_after); }
			 */

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.info("预存款查询出错");
		}
		// return ToUrl;
		return null;
	}

	/**
	 * 
	 * @Title: identityAuthentication
	 * @author sl
	 * @Description: 提交个人身份验证
	 * @param @param idCardPositive
	 * @param @param idCardOpposite
	 * @param @param request
	 * @param @return 参数
	 * @return String 返回类型 手持身份证图片
	 * @throws
	 */
	@RequestMapping("/identityAuthentication")
	public String identityAuthentication(MultipartFile idCardPositive,
			MultipartFile idCardOpposite, MultipartFile idCardHand,
			HttpServletRequest request) {
		logger.info("提交身份验证开始member");
		Map<String, String> param = new HashMap<String, String>();
		MemMember member = (MemMember) request.getSession().getAttribute(
				"member");
		String ositive = FileUploadUtil.upload(idCardPositive);
		String opposite = FileUploadUtil.upload(idCardOpposite);
		String hand = FileUploadUtil.upload(idCardHand);
		param.put("idCardPositive", ositive);
		param.put("idCardOpposite", opposite);
		param.put("idCardHand", hand);
		param.put("realName", request.getParameter("realName"));
		param.put("idCard", request.getParameter("idCard"));
		param.put("approveFlag", "0");
		param.put("id", member.getId());
		logger.info(param.toString());
		memMemberService.updateById(param);
		return "redirect:toIdentityApprove";
	}

	/**
	 * 
	 * @Title: toIdentityApprove
	 * @author sl
	 * @Description:查询身份认证各项资料
	 * @param @param request
	 * @param @return 参数
	 * @return String 返回类型
	 * @throws
	 */
	@RequestMapping("/toIdentityApprove")
	public String toIdentityApprove(HttpServletRequest request) {
		MemMember member = (MemMember) request.getSession().getAttribute(
				"member");
		Map<String, Object> map = memMemberService.findApproveInfo(member
				.getId());
		if (map.get("id_card") != null
				&& map.get("id_card").toString().length() == 18) {
			map.put("id_card",
					map.get("id_card").toString().substring(0, 3)
							+ "***********"
							+ map.get("id_card").toString().substring(14, 18));
		}
		if (map.get("id_card") != null
				&& map.get("id_card").toString().length() == 15) {
			map.put("id_card",
					map.get("id_card").toString().substring(0, 3) + "********"
							+ map.get("id_card").toString().substring(11, 15));
		}
		request.setAttribute("map", map);
		return "/member/identity_authentication";
	}

	@RequestMapping("/toUpdatePassword")
	public String toUpdatePassword() {

		return "/member/update_password";
	}

	// 修改密码
	@ResponseBody
	@RequestMapping("updatePassword")
	public String toUpdatePassword(String password, HttpServletRequest request)
			throws Exception {
		MemMember member = (MemMember) request.getSession().getAttribute(
				"member");
		if (password.equals(member.getPassword())) {
			memMemberService.updatePassword(password,
					member.getUsername());
			member.setPassword(password);
			request.getSession().setAttribute("member", member);
			return "ok";
		}
		return "error";
	}

	// 进入个人信息
	@RequestMapping("/myInfo")
	public String myInfo(HttpServletRequest request) {
		MemMember member = (MemMember) request.getSession().getAttribute(
				"member");
		request.setAttribute("member",
				memMemberService.getMemMemberByUsername(member.getUsername()));
		return "member/my_information";
	}

	@ResponseBody
	@RequestMapping("/updateMember")
	public boolean updateMember(@RequestParam Map<String, String> param) {
		memMemberService.updateById(param);

		return true;
	}

}
