package com.uib.cart.service.impl;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.uib.cart.dao.CartDao;
import com.uib.cart.entity.Cart;
import com.uib.cart.entity.CartItem;
import com.uib.cart.service.CartItemService;
import com.uib.cart.service.CartService;
import com.uib.common.enums.ExceptionEnum;
import com.uib.common.utils.DateUtils;
import com.uib.common.utils.UUIDGenerator;
import com.uib.common.utils.WebUtil;
import com.uib.member.entity.MemMember;
import com.uib.member.service.MemMemberService;
import com.uib.mobile.dto.ReturnMsg;
import com.uib.product.entity.Product;
import com.uib.product.service.ProductService;
import com.uib.serviceUtils.Utils;

@Service
public class CartServiceImpl implements CartService {
	
	private Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);
	
	@Autowired
	private MemMemberService MemMemberService;
	@Autowired
	private CartDao cartDao;
	@Autowired
	private CartItemService cartItemService;
	
	@Autowired
	private ProductService productService;

	@Override
	public Cart getCurrentCart() throws Exception{
		MemMember member = MemMemberService.getCurrent();
		if (member != null) {
			Cart cart = cartDao.selectCartByMemberId(member.getId());
			if (cart != null) {
				if (!cart.hasExpired()) {
					if (!DateUtils.isSameDay(cart.getModifyDate(), new Date())) {
						cart.setModifyDate(new Date());
						cartDao.updateCart(cart);
					}
					return cart;
				} else {
					cartDao.removeCartById(cart.getId());
				}
			}
		} else {
			RequestAttributes requestAttributes = RequestContextHolder
					.currentRequestAttributes();
			if (requestAttributes != null) {
				HttpServletRequest request = ((ServletRequestAttributes) requestAttributes)
						.getRequest();
				String id = WebUtil.getCookie(request, Cart.ID_COOKIE_NAME);
				String key = WebUtil.getCookie(request, Cart.KEY_COOKIE_NAME);
				if (StringUtils.isNotEmpty(id) && StringUtils.isNotBlank(id)
						&& StringUtils.isNotEmpty(key)) {
					Cart cart = cartDao.selectCart(new Cart(id, null, null));
					if (cart != null && cart.getMemberId() == null
							&& StringUtils.equals(cart.getCartKey(), key)) {
						if (!cart.hasExpired()) {
							if (!DateUtils.isSameDay(cart.getModifyDate(),
									new Date())) {
								cart.setModifyDate(new Date());
								cartDao.updateCart(cart);
							}
							return cart;
						} else {
							cartDao.removeCartById(cart.getId());
						}
					}
				}
			}
		}
		return null;
	}

	@Override
	public void save(Cart cart) throws Exception {
		cartDao.saveCart(cart);
	}

	@Override
	public void removeById(String id,String pid) throws Exception {
		cartItemService.deleteByRelativeId(id,pid);
		//cartDao.removeCartById(id);
	}

	@Override
	public Cart selectCartById(String id) throws Exception {
		Cart cart = cartDao.selectCart(new Cart(id, null, null));
		return cart;
	}
	
	@Override
	public ReturnMsg<Cart> selectCartByUserName(String userName) throws Exception {
		ReturnMsg<Cart> msg = new ReturnMsg<Cart>();
		MemMember member = MemMemberService.getCurrent();
		if(Utils.isBlank(member)){
			member = MemMemberService.getCurrentUser(userName);
		}
		if(Utils.isBlank(member)){
			msg.setCode(ExceptionEnum.member_not_exist.getIndex());
			msg.setMsg(ExceptionEnum.member_not_exist.getValue());
			msg.setStatus(false);
			return msg;
		}
		Cart cart = cartDao.selectCartByMemberId(member.getId());
		msg.setData(cart);
		msg.setStatus(true);
		return msg;
	}

	@Override
	public void deleteCartById(String id) throws Exception {
		cartDao.removeCartById(id);
	}

	@Override
	public Map<String, Object> queryCartByUserName(String userName)
			throws Exception {
		return cartDao.queryCartByUserName(userName);
	}

	@Override
	public Map<String, Object> queryCartItemByUserNameAndProductId(
			String userName, String productId) throws Exception {
		return cartDao.queryCartItemByUserNameAndProductId(userName, productId);
	}

	@Override
	public ReturnMsg<String> addCart(String productId, Integer quantity,String userName,String terminalFlag) throws Exception {
		logger.info("加入购物车入参userName=" + userName + ",productId=" + productId + ",quantity=" + quantity + ",terminalFlag=" + terminalFlag);
		
		ReturnMsg<String> result = new ReturnMsg<String>();
		result.setStatus(true);
		
		Product product = productService.queryProductByProductId(productId);
		ReturnMsg<String> checkResult = new ReturnMsg<String>();
		checkResult = this.checkProduct(product,terminalFlag);
		if(!checkResult.isStatus()){
			return checkResult;
		}
		
		Map<String,Object> cartInfo = this.queryCartByUserName(userName);
		
		//用户存在
		if (cartInfo.containsKey("userId")) {
			String userId = cartInfo.get("userId").toString();
			String cartId = "";
			String scount = "";
			int count = 0;
			if(cartInfo.containsKey("cartId")){
				cartId = cartInfo.get("cartId").toString();
			}
			if(cartInfo.containsKey("count")){
				scount = cartInfo.get("count").toString();
				//该用户购物车所有商品总数
				count = Integer.parseInt(scount);
			}
			
			if (count >= Cart.MAX_PRODUCT_COUNT) {
				result.setCode(ExceptionEnum.cartItems_size_exceed.getIndex());
				result.setMsg(ExceptionEnum.cartItems_size_exceed.getValue());
				result.setStatus(false);
				return result;
			}
			//从来没有买过商品，第一次加入购物车
			if(StringUtils.isEmpty(cartId)){
				checkResult = this.addCartTable(userId, quantity, product);
				if(!checkResult.isStatus()){
					return checkResult;
				}
			}else{
				checkResult = this.addCartItemTable(userName, product, quantity, cartId);
				if(!checkResult.isStatus()){
					return checkResult;
				}
			}	
		}
		return result;
	}
	
	/**
	 * 检查商品是否存在、是否上架、是否赠品
	 * @param product
	 * @return
	 */
	private ReturnMsg<String> checkProduct(Product product,String terminalFlag){
		ReturnMsg<String> result = new ReturnMsg<String>();
		result.setStatus(true);
		//检查商品是否存在
		if (Utils.isBlank(product)) {
			result.setCode(ExceptionEnum.product_not_exist.getIndex());
			result.setMsg(ExceptionEnum.product_not_exist.getValue());
			result.setStatus(false);
		}
//		if("APP".equals(terminalFlag.toString())){
//			//检查APP商品是否上架
//			if (!"1".equals(product.getAppIsMarketable())) {
//				result.setCode(ExceptionEnum.product_undercarriage.getIndex());
//				result.setMsg(ExceptionEnum.product_undercarriage.getValue());
//				result.setStatus(false);
//			}
//		}else{
//			//检查微信商品是否上架
//			if (!"1".equals(product.getWxIsMarketable())) {
//				result.setCode(ExceptionEnum.product_undercarriage.getIndex());
//				result.setMsg(ExceptionEnum.product_undercarriage.getValue());
//				result.setStatus(false);
//			}
//		}
//		
//		//检查商品是否是赠品
//		if (Utils.booleanOf(product.getIsGift())) {
//			result.setCode(ExceptionEnum.product_is_gift.getIndex());
//			result.setMsg(ExceptionEnum.product_is_gift.getValue());
//			result.setStatus(false);
//		}
		return result;
	}
	
	/**
	 * 增加购物车表
	 * @param userId
	 * @param quantity
	 * @param product
	 * @return
	 * @throws Exception
	 */
	private ReturnMsg<String> addCartTable(String userId,int quantity,Product product) throws Exception{
		ReturnMsg<String> result = new ReturnMsg<String>();
		result.setStatus(true);
		Cart cart = new Cart();
		
		cart.setId(UUIDGenerator.getUUID());
		cart.setCartKey(UUIDGenerator.getUUID() + DigestUtils.md5Hex(RandomStringUtils.randomAlphabetic(30)));
		cart.setMemberId(userId);
		cart.setCreateDate(new Date());
		cart.setModifyDate(new Date());
		this.save(cart);
		
		if (CartItem.MAX_QUANTITY != null && quantity > CartItem.MAX_QUANTITY) {
			result.setCode(ExceptionEnum.cartItem_count_exceed.getIndex());
			result.setMsg(ExceptionEnum.cartItem_count_exceed.getValue());
			result.setStatus(false);
			return result;
		}
//		if (product.getStock() != null && quantity > product.getAvailableStock()) {
//			result.setCode(ExceptionEnum.product_Low_stock.getIndex());
//			result.setMsg(ExceptionEnum.product_Low_stock.getValue());
//			result.setStatus(false);
//			return result;
//		}
		CartItem cartItem = new CartItem(); 
		cartItem.setId(UUIDGenerator.getUUID());
		cartItem.setQuantity(quantity);
		cartItem.setProductId(product.getId());
		cartItem.setCartId(cart.getId());
		cartItem.setProductPrice(product.getPrice());
		cartItem.setCreateDate(new Date());
		cartItem.setModifyDate(new Date());
		cartItemService.insertCartItem(cartItem);
		return result;
	}
	
	/**
	 * 增加或修改购物车明细表
	 * @param userName
	 * @param product
	 * @param quantity
	 * @param cartId
	 * @return
	 * @throws Exception
	 */
	private ReturnMsg<String> addCartItemTable(String userName,Product product,int quantity,String cartId) throws Exception{
		ReturnMsg<String> result = new ReturnMsg<String>();
		result.setStatus(true);
		
		//查找该商品有是否有加入购物车
		Map<String,Object> cartItemInfo = this.queryCartItemByUserNameAndProductId(userName, product.getId());
		CartItem cartItem = new CartItem(); 
		
		//该商品不在购物车
		if(null == cartItemInfo){
			if (CartItem.MAX_QUANTITY != null && quantity > CartItem.MAX_QUANTITY) {
				result.setCode(ExceptionEnum.cartItem_count_exceed.getIndex());
				result.setMsg(ExceptionEnum.cartItem_count_exceed.getValue());
				result.setStatus(false);
				return result;
			}
//			if (product.getStock() != null && quantity > product.getAvailableStock()) {
//				result.setCode(ExceptionEnum.product_Low_stock.getIndex());
//				result.setMsg(ExceptionEnum.product_Low_stock.getValue());
//				result.setStatus(false);
//				return result;
//			}
			
			cartItem.setId(UUIDGenerator.getUUID());
			cartItem.setQuantity(quantity);
			cartItem.setProductId(product.getId());
			cartItem.setCartId(cartId);
			cartItem.setProductPrice(product.getPrice());
			cartItem.setCreateDate(new Date());
			cartItem.setModifyDate(new Date());
			cartItemService.insertCartItem(cartItem);
		//该商品在购物车存在
		}else{
			String cartItemId = "";
			int quant = 0;
			
			if (cartItemInfo.containsKey("quantity")) {
		        String squant = cartItemInfo.get("quantity").toString();
		        quant = Integer.parseInt(squant) + quantity;
		    }else{
		    	quant = quantity;
		    }
			
			if (CartItem.MAX_QUANTITY != null && quant > CartItem.MAX_QUANTITY) {
				result.setCode(ExceptionEnum.cartItem_count_exceed.getIndex());
				result.setMsg(ExceptionEnum.cartItem_count_exceed.getValue());
				result.setStatus(false);
				return result;
			}
//			if (product.getStock() != null && quant > product.getAvailableStock()) {
//				result.setCode(ExceptionEnum.product_Low_stock.getIndex());
//				result.setMsg(ExceptionEnum.product_Low_stock.getValue());
//				result.setStatus(false);
//				return result;
//			}
			
			if(cartItemInfo.containsKey("cartItemId")){
				cartItemId = cartItemInfo.get("cartItemId").toString();
			}else{
				logger.info("购物车明细不存在,cartId=" + cartId + ",productId=" + product);
				result.setCode(ExceptionEnum.cartItems_is_blank.getIndex());
				result.setMsg(ExceptionEnum.cartItems_is_blank.getValue());
				result.setStatus(false);
				return result;
			}
			
			cartItem.setId(cartItemId);
			cartItem.add(quant);
			cartItem.setProductPrice(product.getPrice());
			cartItemService.updateCartItem(cartItem);
		}
		
		return result;
	}
}
