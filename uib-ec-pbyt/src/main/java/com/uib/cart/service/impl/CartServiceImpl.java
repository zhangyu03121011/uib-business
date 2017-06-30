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
import com.uib.common.utils.StringUtil;
import com.uib.common.utils.UUIDGenerator;
import com.uib.common.utils.WebUtil;
import com.uib.member.entity.MemMember;
import com.uib.member.service.MemMemberService;
import com.uib.mobile.dto.ReturnMsg;
import com.uib.mobile.service.RecommendProductLogService;
import com.uib.product.entity.Product;
import com.uib.product.service.ProductService;
import com.uib.ptyt.constants.WechatConstant;
import com.uib.ptyt.entity.RecommProduct;
import com.uib.ptyt.service.RecommProductService;
import com.uib.serviceUtils.Utils;
import com.uib.weixin.util.UserSession;

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
	
	@Autowired
	private RecommendProductLogService recommendProductLogService;
	
	@Autowired
	private RecommProductService recommProductService;

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

	/**
	 * 根据购物车编号查询购物车信息
	 */
	public Cart selectCartById(String id) throws Exception {
		logger.info("根据购物车编号查询购物车信息开始===参数id"+id);
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

	public Map<String, Object> queryCartByUserId(String userId) throws Exception {
		return cartDao.queryCartByUserId(userId);
	}

	public Map<String, Object> queryCartItemByUserIdAndProductId(String userId, String productId) throws Exception {
		return cartDao.queryCartItemByUserIdAndProductId(userId, productId);
	}

	/**
	 * 添加商品到购物车
	 */
	public ReturnMsg<String> addCart(String productId, Integer quantity,String userId,String terminalFlag,String specificationIds,String recommendMemberId) throws Exception {
		logger.info("加入购物车入参userId=" + userId + ",productId=" + productId + ",quantity=" + quantity + ",terminalFlag=" + terminalFlag);
		ReturnMsg<String> result = new ReturnMsg<String>();
		result.setStatus(true);
		//会员等级，如果是普通商品则取当前的等级，是推广分享的商品则去推荐人的等级，分享的商品是根据分享人的等级显示商品价格
		String rankId = (String)UserSession.getSession(WechatConstant.RANK_ID);
		
		String productType = WechatConstant.product_type_1;
		//获取商品推荐人id
		if(StringUtils.isNotEmpty(recommendMemberId)) {
			MemMember memMember = MemMemberService.getMemMember(recommendMemberId);
			if (null != memMember) {
				rankId = memMember.getRankId();
				productType = WechatConstant.product_type_2;
			}
		}
		logger.info("rankId======="+rankId);
		Product product = productService.queryProductByProductId(productId,rankId);
		ReturnMsg<String> checkResult = new ReturnMsg<String>();
		//检查商品状态
		checkResult = this.checkProduct(product,terminalFlag,quantity);
		if(!checkResult.isStatus()){
			return checkResult;
		}
		//查询当前用户的购物车信息
		Map<String,Object> cartInfo = this.queryCartByUserId(userId);
			//用户存在
			if (StringUtil.isNotEmpty(userId)) {
				String cartId = "";
				String scount = "";
				int count = 0;
				if(null != cartInfo && cartInfo.containsKey("cartId")){
					cartId = cartInfo.get("cartId").toString();
				}
				if(null != cartInfo && cartInfo.containsKey("count")){
					scount = cartInfo.get("count").toString();
					//该用户购物车所有商品总数
					count = Integer.parseInt(scount);
				}
				//购物车商品数量限制99
				if (count >= Cart.MAX_PRODUCT_COUNT) {
					result.setCode(ExceptionEnum.cartItems_size_exceed.getIndex());
					result.setMsg(ExceptionEnum.cartItems_size_exceed.getValue());
					result.setStatus(false);
					return result;
				}
				//从来没有买过商品，第一次加入购物车
				if(StringUtils.isEmpty(cartId)){
					checkResult = this.addCartTable(userId, quantity, product,specificationIds,productType,recommendMemberId);
					if(!checkResult.isStatus()){
						return checkResult;
					}
				}else{
					checkResult = this.addCartItemTable(userId, product, quantity, cartId,specificationIds,productType,recommendMemberId);
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
	private ReturnMsg<String> checkProduct(Product product,String terminalFlag,Integer quantity){
		ReturnMsg<String> result = new ReturnMsg<String>();
		result.setStatus(true);
		//检查商品是否存在
		if (Utils.isBlank(product)) {
			result.setCode(ExceptionEnum.product_not_exist.getIndex());
			result.setMsg(ExceptionEnum.product_not_exist.getValue());
			result.setStatus(false);
		}
		//检查微信商品是否上架
		if (!"1".equals(product.getWxIsMarketable())) {
			result.setCode(ExceptionEnum.product_undercarriage.getIndex());
			result.setMsg(ExceptionEnum.product_undercarriage.getValue());
			result.setStatus(false);
		}
		
		//可用库存 = 库存 - 已分配库存
		int restStock = product.getStock() - product.getAllocatedStock();
		// 判断库存是否不足
		if (restStock <= 0 || quantity > restStock) {
			result.setCode(ExceptionEnum.product_Low_stock.getIndex());
			result.setStatus(false);
			result.setMsg(ExceptionEnum.product_Low_stock.getValue());
		}
		
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
	private ReturnMsg<String> addCartTable(String userId,int quantity,Product product,String specificationIds,String productType,String recommendMemberId) throws Exception{
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
		cartItem.setProductPrice(product.getSellPrice());
		cartItem.setProductType(productType);
		cartItem.setCreateDate(new Date());
		cartItem.setModifyDate(new Date());
		cartItem.setSpecificationId(specificationIds);
		cartItemService.insertCartItem(cartItem);
		//保存推荐记录信息
		addRecommendProductLogInfo(userId,recommendMemberId,product.getId(),cartItem.getId());
		return result;
	}
	
	/**
	 * 保存推荐记录信息
	 * @param userId
	 * @param recommendMemberId
	 * @param productId
	 * @param cartItemId
	 * @throws Exception 
	 */
	public void addRecommendProductLogInfo(String userId,String recommendMemberId,String productId,String cartItemId) throws Exception {
		//如果是推广分享的商品
		logger.info("新增推荐记录信息开始====recommendMemberId:"+recommendMemberId);
		logger.info("新增推荐记录信息开始====userId:"+userId);
		logger.info("新增推荐记录信息开始====productId:"+productId);
		logger.info("新增推荐记录信息开始====cartItemId:"+cartItemId);
		if(StringUtil.isNotEmpty(recommendMemberId)) {
			RecommProduct recommProduct = new RecommProduct();
			recommProduct.setRecommMemberId(recommendMemberId);
			recommProduct.setProductId(productId);
			RecommProduct recommProductresp = recommProductService.getRecommProduct(recommProduct);
			Integer checkOnlyCount = recommendProductLogService.checkOnly(userId, recommendMemberId, productId);
			//存在推广记录
			if (null != recommProductresp && checkOnlyCount.intValue() <= 0) {
				String usertype = recommProductresp.getUsertype();
				recommendProductLogService.addRecommendProductLog(userId, recommendMemberId, productId,recommProductresp.getId(),cartItemId,usertype);
			}
		}
		logger.info("新增推荐记录信息结束");
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
	private ReturnMsg<String> addCartItemTable(String userId,Product product,int quantity,String cartId,String specificationIds,String productType,String recommendMemberId) throws Exception{
		ReturnMsg<String> result = new ReturnMsg<String>();
		result.setStatus(true);
		
		//查找该商品有是否有加入购物车
		Map<String,Object> cartItemInfo = this.queryCartItemByUserIdAndProductId(userId, product.getId());
		CartItem cartItem = new CartItem(); 
		
		//该商品不在购物车
		if(null == cartItemInfo){
			if (CartItem.MAX_QUANTITY != null && quantity > CartItem.MAX_QUANTITY) {
				result.setCode(ExceptionEnum.cartItem_count_exceed.getIndex());
				result.setMsg(ExceptionEnum.cartItem_count_exceed.getValue());
				result.setStatus(false);
				return result;
			}
			
			cartItem.setId(UUIDGenerator.getUUID());
			cartItem.setQuantity(quantity);
			cartItem.setProductId(product.getId());
			cartItem.setCartId(cartId);
			cartItem.setProductPrice(product.getSellPrice());
			cartItem.setProductType(productType);
			cartItem.setCreateDate(new Date());
			cartItem.setModifyDate(new Date());
			cartItem.setSpecificationId(specificationIds);
			cartItemService.insertCartItem(cartItem);
			if(WechatConstant.product_type_2.equals(productType)) {
				//保存推荐记录信息
				addRecommendProductLogInfo(userId,recommendMemberId,product.getId(),cartItem.getId());
			}
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
			cartItem.setProductPrice(product.getSellPrice());
			cartItem.setSpecificationId(specificationIds);
			cartItemService.updateCartItem(cartItem);
		}
		
		return result;
	}
}
