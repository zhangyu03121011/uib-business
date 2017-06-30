package com.uib.mobile.web;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.uib.cart.entity.Cart;
import com.uib.cart.entity.CartItem;
import com.uib.cart.service.CartItemService;
import com.uib.cart.service.CartService;
import com.uib.cart.service.impl.CartPriceService;
import com.uib.common.enums.ExceptionEnum;
import com.uib.member.entity.MemMember;
import com.uib.member.service.MemMemberService;
import com.uib.mobile.dto.CartPojo4Mobile;
import com.uib.mobile.dto.ReturnMsg;
import com.uib.mobile.service.Cart4MobileService;
import com.uib.serviceUtils.Utils;

@Controller
@RequestMapping("/mobile/cart")
public class MobileCartController {
	
	private Logger logger = LoggerFactory.getLogger(MobileCartController.class);
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private MemMemberService memMemberService;
	
	@Autowired
	private CartItemService cartItemService;
	
	@Autowired
	private CartPriceService cartPriceService;
	
	@Autowired
	private Cart4MobileService cart4MobileService;

	/**
	 * 添加购物车
	 * 
	 * @param productId 商品id
	 * @param quantity 数量
	 * @param sessionId 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/addCart")
	@ResponseBody
	public ReturnMsg<List<Map<String,Object>>> addCart(String productId,Integer quantity,String sessionId, HttpServletRequest request) {
		logger.info("加入购物车入参productId=" + productId + ",quantity=" + quantity + ",sessionId=" + sessionId);
		ReturnMsg<List<Map<String,Object>>> result = new ReturnMsg<List<Map<String,Object>>>();
		result.setStatus(false);
		try {
			//检查参数是否为空
			if (Utils.isObjectsBlank(productId, quantity, sessionId)) {
				result.setCode(ExceptionEnum.param_not_null.getIndex());
				result.setMsg(ExceptionEnum.param_not_null.getValue());
				return result;
			}
			
			//通过sessionId获取会员信息
			MemMember memMember = memMemberService.getMemMemberBySessionId(sessionId);
			//检查会员是否存在
			if (Utils.isBlank(memMember)) {
				result.setCode(ExceptionEnum.member_not_exist.getIndex());
				result.setMsg(ExceptionEnum.member_not_exist.getValue());
				return result;
			}
			
			ReturnMsg<String> addCartResult = new ReturnMsg<String>();
			addCartResult = cartService.addCart(productId, quantity, memMember.getUsername(),"APP");
			if(!addCartResult.isStatus()){	
				result.setCode(addCartResult.getCode());
				result.setMsg(addCartResult.getMsg());
			}else{
				result.setStatus(true);
				List<Map<String,Object>> cartList = cart4MobileService.queryCartByUserName(memMember.getUsername());
				result.setData(cartList);
			}
			
		} catch (Exception e) {
			logger.error("添加购物车失败!", e);
			result.setCode(ExceptionEnum.business_logic_exception.getIndex());
			result.setMsg(ExceptionEnum.business_logic_exception.getValue());
		}
		return result;
	}

	/**
	 * 根据sessionId查询购物车
	 * 
	 * @param sessionId
	 * @return
	 */
	@RequestMapping("/list_json")
	@ResponseBody
	public ReturnMsg<List<Map<String,Object>>> listCartJson(String sessionId, HttpServletRequest request) {
		logger.info("根据sessionId查询购物车入参sessionId=" + sessionId);
		ReturnMsg<List<Map<String,Object>>> result = new ReturnMsg<List<Map<String,Object>>>();
		try {
			if (Utils.isNotBlank(sessionId)) {
				//通过sessionId获取会员信息
				MemMember memMember = memMemberService.getMemMemberBySessionId(sessionId);
				if (Utils.isBlank(memMember)) {
					result.setCode(ExceptionEnum.member_not_exist.getIndex());
					result.setMsg(ExceptionEnum.member_not_exist.getValue());
					result.setStatus(false);
					return result;
				}
				
				String userName = memMember.getUsername();
				logger.info("通过sessionId取得的userName=" + userName);
				
				List<Map<String,Object>> cartList = cart4MobileService.queryCartByUserName(userName);
				result.setData(cartList);
				result.setCode("200");
				result.setStatus(true);
			} else {
				result.setStatus(false);
				result.setCode(ExceptionEnum.param_not_null.getIndex());
				result.setMsg(ExceptionEnum.param_not_null.getValue());
				return result;
			}
		} catch (Exception e) {
			logger.error("查询购物车出错!", e);
			result.setStatus(false);
			result.setCode(ExceptionEnum.business_logic_exception.getIndex());
			result.setMsg(ExceptionEnum.business_logic_exception.getValue());
		}
		return result;
	}

	/**
	 * 根据id删除商品、购物车项
	 * 
	 * @param productIds 商品id数组传入时是以逗号分隔的字符串，后台接收自动转为字符数组
	 * @param cartItemIds 购物车项数组
	 * @param userName 用户名
	 * @return
	 */
	@RequestMapping("/delete_by_Id")
	@ResponseBody
	public ReturnMsg<Object> deleteById(String[] productIds,String[] cartItemIds, String sessionId , HttpServletRequest request) {
		logger.info("根据id删除商品、购物车项入参productIds=" + productIds.toString() + ",cartItemIds=" + cartItemIds.toString() + ",sessionId=" + sessionId);
		ReturnMsg<Object> result = new ReturnMsg<Object>();
		try {
			if (Utils.isObjectsBlank(productIds, cartItemIds, sessionId)) {
				result.setCode(ExceptionEnum.param_not_null.getIndex());
				result.setStatus(false);
				result.setMsg(ExceptionEnum.param_not_null.getValue());
				return result;
			}
			//通过sessionId获取会员信息
			MemMember memMember = memMemberService.getMemMemberBySessionId(sessionId);
			if (Utils.isBlank(memMember)) {
				result.setCode(ExceptionEnum.member_not_exist.getIndex());
				result.setMsg(ExceptionEnum.member_not_exist.getValue());
				result.setStatus(false);
				return result;
			}
			String userName = memMember.getUsername();
			logger.info("通过sessionId取得的userName=" + userName);
			ReturnMsg<Cart> msg = cartService.selectCartByUserName(userName);
			if (!msg.isStatus()) {
				result.setStatus(false);
				result.setCode(msg.getCode());
				result.setMsg(msg.getMsg());
				return result;
			}
			Cart cart = msg.getData();
			if (Utils.isBlank(cart)) {
				result.setStatus(false);
				result.setCode(ExceptionEnum.cart_is_null.getIndex());
				result.setMsg(ExceptionEnum.cart_is_null.getValue());
				return result;
			}
			cartItemService.deleteByRelativeId(cartItemIds, productIds,cart.getId());
					
			result.setStatus(true);
			return result;
		} catch (Exception e) {
			logger.error("根据商品id删除商品出错！", e);
			result.setStatus(false);
			result.setCode(ExceptionEnum.business_logic_exception.getIndex());
			result.setMsg(ExceptionEnum.business_logic_exception.getValue());
			return result;
		}
	}

	

	/**
	 * 修改购物项商品数量
	 * 
	 * @param cartItemId
	 * @param quantity
	 * @return
	 */
	@RequestMapping("/update_count")
	@ResponseBody
	public ReturnMsg<CartPojo4Mobile> updateCount(String cartItemId, Integer quantity , HttpServletRequest request) {
		logger.info("修改购物项商品数量入参cartItemId=" + cartItemId + ",quantity=" + quantity);
		ReturnMsg<CartPojo4Mobile> result = new ReturnMsg<CartPojo4Mobile>();
		try {
			if (Utils.isObjectsBlank(cartItemId, quantity) && quantity < 0) {
				result.setStatus(false);
				result.setCode(ExceptionEnum.param_not_null.getIndex());
				result.setMsg(ExceptionEnum.param_not_null.getValue());
			} else {
				CartItem item = cartItemService.queryById(cartItemId);
				if (Utils.isBlank(item)) {
					result.setStatus(false);
					result.setCode(ExceptionEnum.param_illegal.getIndex());
					result.setMsg(ExceptionEnum.param_illegal.getValue() + ",购物车项不存在，cartItemId：" + cartItemId);
					return result;
				}
				item.setProductId(item.getProductId());
				item.setQuantity(quantity);
				cartItemService.updateCartItem(item);
				result.setStatus(true);
			}
		} catch (Exception e) {
			logger.error("根据商品id修改购物项数量出错！", e);
			result.setStatus(false);
			result.setCode(ExceptionEnum.business_logic_exception.getIndex());
			result.setMsg(ExceptionEnum.business_logic_exception.getValue());
		}
		return result;
	}
	
	/***
	 * 保存购物车商品价格
	 * @param cartId
	 * @param pid
	 */
	@RequestMapping("/saveCartItemPrice")
	@ResponseBody
	public ReturnMsg<Object> saveCartItemPrice(String cartId, String[] pid , HttpServletRequest request) {
		logger.info("调用saveCartItemPrice接口,cartId=" + cartId + ",pid[]=" + Arrays.toString(pid));
		
		ReturnMsg<Object> msg = new ReturnMsg<Object>(); 
		if (!Utils.isObjectsBlank(cartId, pid)) {
			cartPriceService.batchUpdPrice(cartId, pid);
		}
		msg.setStatus(true);
		return msg;
	}
}
