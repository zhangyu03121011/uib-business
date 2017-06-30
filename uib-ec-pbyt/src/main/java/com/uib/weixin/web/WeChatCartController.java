package com.uib.weixin.web;

import java.math.BigDecimal;
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
import com.uib.common.utils.StringUtils;
import com.uib.member.service.MemMemberService;
import com.uib.mobile.dto.ReturnMsg;
import com.uib.order.service.OrderService;
import com.uib.product.entity.Product;
import com.uib.product.service.ProductService;
import com.uib.product.service.ProductSpecificationService;
import com.uib.ptyt.constants.WechatConstant;
import com.uib.ptyt.service.PbytProductService;
import com.uib.serviceUtils.Utils;
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
	
	@Autowired
	private ProductSpecificationService productSpecificationService;
	
	@Autowired
	private PbytProductService pbytProductService;
	
	@Autowired
	private OrderService orderService;
	
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
									 @RequestParam("quantity") Integer quantity,
									 @RequestParam("specificationIds") String specificationIds,
									 @RequestParam("areaIds") String[] areaIds,
									 @RequestParam("recommendMemberId") String recommendMemberId) {
		logger.info("微信端加入购物车入参：productId=" + productId + ",quantity=" + quantity);
		ReturnMsg<String> result = new ReturnMsg<String>();
		result.setStatus(true);
		try {
			//获取当前用户userName
//			UserSession.setSession(WxConstant.wx_user_name, "test_fx1");
			String userId = (String)UserSession.getSession(WechatConstant.USER_ID);
			logger.info("通过session获取userName=" + userId);
			logger.info("通过session获取productId=" + productId);
			logger.info("通过session获取quantity=" + quantity);
			
			if (Utils.isObjectsBlank(productId, quantity, userId) || quantity < 1) {
				result.setCode(ExceptionEnum.param_not_null.getIndex());
				result.setMsg(ExceptionEnum.param_not_null.getValue());
				result.setStatus(false);
				return result;
			}
			
			ReturnMsg<String> addCartResult = new ReturnMsg<String>();
			Boolean productAdress = pbytProductService.queryProductAdress(areaIds,productId);
			if(!productAdress) {
				result.setStatus(false);
				result.setCode(ExceptionEnum.cart_area_product_null.getIndex());
				result.setMsg(ExceptionEnum.cart_area_product_null.getValue());
				return result;
			}
			addCartResult = cartService.addCart(productId, quantity, userId,"WX",specificationIds,recommendMemberId);
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
//			UserSession.setSession(WxConstant.wx_user_name, "test_fx1");
//			UserSession.setSession(WechatConstant.RANK_ID, "1");
//			UserSession.setSession(WechatConstant.USER_ID, "e72bdcd77cc44e62b582441c005b0940");
			String userId = (String) UserSession.getSession(WechatConstant.USER_ID);
			String rankId = (String) UserSession.getSession(WechatConstant.RANK_ID);
			Product p = null;
			logger.info("通过session获取userId=" + userId);
			if (Utils.isNotBlank(userId)) {
				result = cartService4Wechat.queryCartByUserName(userId);
				for (CartInfo4Wechat cartInfo4Wechat : result) {
					//商品价格售价有变动
					String cartItemId = cartInfo4Wechat.getCartItemId();
					BigDecimal price = cartInfo4Wechat.getPrice();
					String productType = cartInfo4Wechat.getProductType();
					String productId = cartInfo4Wechat.getProductId();
					//如果是B端分享出来的商品 根据分享人的rankId查询商品的价格
					if(WechatConstant.product_type_2.equals(productType)) {
						String recommendMeberId = orderService.queryRecommendMeberIdByCartItemId(cartItemId);
						if(StringUtils.isNotEmpty(recommendMeberId)) {
							rankId = recommendMeberId;
						}
					}
					p = productService.findById(productId,rankId);
					//商品价格售价有变动
					if(null != p.getSellPrice()) {
						BigDecimal pPrice = new BigDecimal(p.getSellPrice());
						int ct = pPrice.compareTo(price);
						// 判断商品价格是否改动
						if (ct != 0) {
							cartInfo4Wechat.setWxIsPriceChanged(true);
						}
					}
				}
			}else{
				logger.info("通过session没有找到userId");
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
				
				Product product = productService.findById(item.getProductId(), "1");
				
				//可用库存 = 库存 - 已分配库存
				int restStock = product.getStock() - product.getAllocatedStock();
				// 判断库存是否不足
				if (quantity > restStock) {
					result.setCode(ExceptionEnum.product_Low_stock.getIndex());
					result.setStatus(false);
					result.setMsg(ExceptionEnum.product_Low_stock.getValue());
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
