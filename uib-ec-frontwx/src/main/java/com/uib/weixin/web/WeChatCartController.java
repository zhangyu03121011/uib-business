package com.uib.weixin.web;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.uib.cart.entity.CartItem;
import com.uib.cart.service.CartItemService;
import com.uib.cart.service.CartService;
import com.uib.cart.service.impl.CartPriceService;
import com.uib.common.enums.ExceptionEnum;
import com.uib.member.service.MemMemberService;
import com.uib.mobile.dto.ReturnMsg;
import com.uib.product.service.ProductService;
import com.uib.serviceUtils.Utils;
import com.uib.weixin.constant.WxConstant;
import com.uib.weixin.dto.CartInfo4Wechat;
import com.uib.weixin.service.impl.CartService4Wechat;
import com.uib.weixin.util.UserSession;

@Controller
@RequestMapping("/wechat/cart")
public class WeChatCartController {
	private Logger logger = LoggerFactory.getLogger(WeChatCartController.class);
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private MemMemberService memMemberService;
	
	@Autowired
	private CartItemService cartItemService;
	
	@Autowired
	private CartPriceService cartPriceService;
	
	@Autowired
	private CartService4Wechat cartService4Wechat;

	/**
	 * 添加购物车
	 * 
	 * @param productId  商品id
	 * @param quantity   数量
	 * @param userName   用户名
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/addCart")
	@ResponseBody
	public ReturnMsg<String> addCart(@RequestParam("productId") String productId,
									 @RequestParam("quantity") Integer quantity) {
		logger.info("微信端加入购物车入参：productId=" + productId + ",quantity=" + quantity);
		ReturnMsg<String> result = new ReturnMsg<String>();
		result.setStatus(true);
		try {
			//获取当前用户userName
			String userName = (String)UserSession.getSession(WxConstant.wx_user_name);
			logger.info("通过session获取userName=" + userName);
			
			if (Utils.isObjectsBlank(productId, quantity, userName) && quantity < 1) {
				result.setCode(ExceptionEnum.param_not_null.getIndex());
				result.setMsg(ExceptionEnum.param_not_null.getValue());
				result.setStatus(false);
				return result;
			}
			
			ReturnMsg<String> addCartResult = new ReturnMsg<String>();
			addCartResult = cartService.addCart(productId, quantity, userName,"WX");
			
			result.setCode(addCartResult.getCode());
			result.setMsg(addCartResult.getMsg());
			result.setStatus(addCartResult.isStatus());
		} catch (Exception e) {
			logger.error("添加购物车失败!", e);
			result.setCode(ExceptionEnum.business_logic_exception.getIndex());
			result.setMsg(ExceptionEnum.business_logic_exception.getValue());
		}
		return result;
	}
	
	/**
	 * 根据用户名查询购物车
	 * 
	 * @return
	 */
	@RequestMapping("/queryCart")
	@ResponseBody
	public List<CartInfo4Wechat> queryCartByUserName(){
		List<CartInfo4Wechat> result = new ArrayList<CartInfo4Wechat>();
		try {
			//获取当前用户userName
			String userName = (String)UserSession.getSession(WxConstant.wx_user_name);
			logger.info("通过session获取userName=" + userName);
			if (Utils.isNotBlank(userName)) {
				result = cartService4Wechat.queryCartByUserName(userName);	
				
				//异步修改购物车商品价格
				if(!result.isEmpty()){
					String[] pid = new String[result.size()];
					for (int i = 0; i < result.size(); i++) {
						pid[i] = result.get(i).getProductId();
					}
					
					cartPriceService.batchUpdPrice(result.get(0).getCartId(), pid);
				}
			}else{
				logger.info("通过session没有找到userName");
			}
		} catch (Exception e) {
			logger.error("查询购物车出错!", e);
		}
		return result;
	}
	

	
	/**
	 * 根据id删除商品、购物车项
	 * @param cartItemIds 购物车项数组
	 * @return
	 */
	@RequestMapping("/deleteCartById")
	@ResponseBody
	public ReturnMsg<Object> deleteCartById(String[] cartItemIds) {
		logger.info("删除购物车入参cartItemIds=" + cartItemIds);	
		ReturnMsg<Object> result = new ReturnMsg<Object>();
		try {
			if (null == cartItemIds || cartItemIds.length == 0) {
				result.setStatus(false);
				result.setCode(ExceptionEnum.param_not_null.getIndex());
				result.setMsg(ExceptionEnum.param_not_null.getValue());
				return result;
			}
			cartService4Wechat.deleteByCartItemId(cartItemIds);
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
	public ReturnMsg<String> updateCount(String cartItemId, Integer quantity) {
		logger.error("微信端修改购物车数量入参：cartItemId" + cartItemId + ",quantity=" + quantity);
		ReturnMsg<String> result = new ReturnMsg<String>();
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
	
}
