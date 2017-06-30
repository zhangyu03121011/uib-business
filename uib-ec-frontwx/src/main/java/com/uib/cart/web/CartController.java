package com.uib.cart.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.uib.cart.entity.Cart;
import com.uib.cart.entity.CartItem;
import com.uib.cart.service.CartItemService;
import com.uib.cart.service.CartService;
import com.uib.common.bean.CartItemVo;
import com.uib.common.bean.ShoppingCartMsg;
import com.uib.common.utils.UUIDGenerator;
import com.uib.common.utils.WebUtil;
import com.uib.member.entity.MemMember;
import com.uib.member.service.MemMemberService;
import com.uib.product.entity.Product;
import com.uib.product.service.ProductService;
import com.uib.serviceUtils.Utils;
import com.uib.serviceUtils.Utils.RoundType;

@Controller
@RequestMapping("/cart")
public class CartController {
	private Logger logger = LoggerFactory.getLogger(CartController.class);
	@Autowired
	private CartService cartService;
	@Autowired
	private ProductService productService;
	@Autowired
	private MemMemberService memMemberService;
	@Autowired
	private CartItemService cartItemService;
	@Value("${currency.sign}")
	private String CURRENCY_SIGN;
	@Value("${currency.unit}")
	private String CURRENCY_UNIT;

	@Value("/cart/cart_list")
	private String CART_LIST_VIEW;
	
	


	@RequestMapping("/addCart")
	@ResponseBody
	public ShoppingCartMsg addCart(@RequestParam("productId") String productId,
			@RequestParam("quantity") Integer quantity,
			@RequestParam("specificationId") String specificationId,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			if (Utils.isObjectsBlank(productId, quantity, specificationId)
					&& quantity < 1) {
				return ShoppingCartMsg.error("shop.message.error");
			}
			Product product = productService.queryProductByProductId(productId);
			if (Utils.isBlank(product)) {
				return ShoppingCartMsg.warn("shop.cart.productNotExsit");
			}
			if (!Utils.booleanOf(product.getPcIsMarketable())) {
				return ShoppingCartMsg.warn("shop.cart.productNotMarketable");
			}
			if (Utils.booleanOf(product.getIsGift())) {
				return ShoppingCartMsg.warn("hop.cart.notForSale");
			}
			Cart cart = cartService.getCurrentCart();
			MemMember member = memMemberService.getCurrent();
			if (cart == null) {
				cart = new Cart();
				cart.setId(UUIDGenerator.getUUID());
				cart.setCartKey(UUIDGenerator.getUUID()
						+ DigestUtils.md5Hex(RandomStringUtils
								.randomAlphabetic(30)));
				if (member != null) {
					cart.setMemberId(member.getId());
				}
				cart.setCreateDate(new Date());
				cart.setModifyDate(new Date());
				cartService.save(cart);
			}
			if (Cart.MAX_PRODUCT_COUNT != null
					&& cart.getCartItems().size() >= Cart.MAX_PRODUCT_COUNT) {
				return ShoppingCartMsg.warn("shop.cart.addCountNotAllowed");
			}
			CartItem cartItem = cart.getCartItem(product);
			if (Utils.isNotBlank(cartItem)) {
				if (CartItem.MAX_QUANTITY != null
						&& cartItem.getQuantity() + quantity > CartItem.MAX_QUANTITY) {
					return ShoppingCartMsg
							.warn("shop.cart.maxCartItemQuantity");
				}
				if (product.getStock() != null
						&& cartItem.getQuantity() + quantity > product
								.getAvailableStock()) {
					return ShoppingCartMsg.warn("shop.cart.productLowStock");
				}
				cartItem.setSpecificationId(specificationId);
				cartItem.setProduct(product);
				cartItem.add(quantity);
//				product.setAllocatedStock(product.getAllocatedStock()
//						+ quantity);
//				productService.update(product);
				cartItemService.updateCartItem(cartItem);
			} else {
				if (CartItem.MAX_QUANTITY != null
						&& quantity > CartItem.MAX_QUANTITY) {
					return ShoppingCartMsg.warn("shop.cart.maxCartItemQuantity"
							+ CartItem.MAX_QUANTITY);
				}
				if (product.getStock() != null
						&& quantity > product.getAvailableStock()) {
					return ShoppingCartMsg.warn("shop.cart.productLowStock");
				}
				cartItem = new CartItem();
				cartItem.setId(UUIDGenerator.getUUID());
				cartItem.setQuantity(quantity);
				cartItem.setProductId(product.getId());
				cartItem.setCart(cart);
				cartItem.setSpecificationId(specificationId);
				cartItem.setCreateDate(new Date());
				cartItem.setModifyDate(new Date());
				cartItem.setProduct(product);
				cartItemService.insertCartItem(cartItem);
				cart.getCartItems().add(cartItem);
			}

			if (member == null) {
				WebUtil.addCookie(request, response, Cart.ID_COOKIE_NAME, cart
						.getId().toString(), Cart.TIMEOUT);
				WebUtil.addCookie(request, response, Cart.KEY_COOKIE_NAME,
						cart.getCartKey(), Cart.TIMEOUT);
			}
			List<CartItemVo> cartItemVoList = new ArrayList<CartItemVo>();
			List<CartItem> cartItemSet = cart.getCartItems();
			if (null != cartItemSet && cartItemSet.size() > 0) {
				for (CartItem Item : cartItemSet) {
					CartItemVo cartItemVo = new CartItemVo();
					cartItemVo.setId(Item.getId().toString());
					String name = Item.getProduct().getFullName();
					cartItemVo.setName(Utils.isBlank(name)?Item.getProduct().getName():name);
					cartItemVo.setQuantity(Item.getQuantity());
					cartItemVo.setThumbnail(Item.getProduct().getThumbnail());
					cartItemVo.setProductId(Item.getProductId());
					// cartItemVo.setProductPath(Item.getProduct().get());
					double price = Item.getPrice();
					if (member == null) {
						price = new BigDecimal(Item.getPrice()).setScale(2,
								BigDecimal.ROUND_HALF_UP).doubleValue();
					}
					cartItemVo.setPrice(Utils.currency(price,
							RoundType.roundHalfUp, 2, CURRENCY_SIGN,
							CURRENCY_UNIT).toString());
					cartItemVoList.add(cartItemVo);
				}
			}
			return ShoppingCartMsg.success("shop.cart.addSuccess", String
					.valueOf(cart.getQuantity()), cartItemVoList, Utils
					.currency(cart.getEffectivePrice(), RoundType.roundHalfUp,
							0, CURRENCY_SIGN, CURRENCY_UNIT));
		} catch (Exception e) {
			logger.error("添加购物车失败!", e);
			return ShoppingCartMsg.error("shop.message.error");
		}
	}

	@RequestMapping("/list")
	public String listCart(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) {
		try {
			Cart cart = cartService.getCurrentCart();
			modelMap.put("cart", cart);
		} catch (Exception e) {
			logger.error("查询购物车出错!", e);
		}
		return CART_LIST_VIEW;
	}

	@RequestMapping("/list_json")
	@ResponseBody
	public Cart listCartJson(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) {
		Cart cart = null;
		try {
			cart = cartService.getCurrentCart();
		} catch (Exception e) {
			logger.error("查询购物车出错!", e);
		}
		return cart;
	}

	@RequestMapping("/delete_by_Id")
	@ResponseBody
	public Map<String, Object> deleteById( String productId[], String []id) {
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			if(Utils.isAllBlank(productId,id)){
				data.put("status", "false");
				data.put("message", "参数为空!");
				return data;
			}
			Cart cart = cartService.getCurrentCart();
			cartItemService.deleteByRelativeId(id, productId, cart.getId());
			if (Utils.isNotBlank(cart)) {
				List<CartItem> cartItems = cart.getCartItems();
				List<CartItem> removeItems = new ArrayList<CartItem>();
				List<String> productIdList = Arrays.asList(productId);
				for (CartItem cartItem : cartItems) {
					if (productIdList.contains(cartItem.getProductId())) {
						removeItems.add(cartItem);
					}
				}
				cartItems.removeAll(removeItems);
			}
			data.put("status", "success");
			data.put("message", "删除商品成功!");
			data.put("cart", cart);
			return data;
		} catch (Exception e) {
			logger.error("根据商品id删除商品出错！", e);
			data.put("status", "false");
			data.put("message", "删除商品失败!");
			return data;
		}
	}

	@RequestMapping("/update_count")
	@ResponseBody
	public Map<String, Object> updateCount(HttpServletRequest request,
			HttpServletResponse response, String cartItemId, Integer quantity) {
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			CartItem item = cartItemService.queryById(cartItemId);
//			productService.updateAllocatedStock(item.getProductId(), item
//					.getProduct().getAllocatedStock()
//					- item.getQuantity()
//					+ quantity);
			item.setProductId(item.getProductId());
			item.setQuantity(quantity);
			cartItemService.updateCartItem(item);
			data.put("message", "成功修改!");
			return data;
		} catch (Exception e) {
			logger.error("根据商品id修改购物项数量出错！", e);
			data.put("message", "修改失败!");
			return data;
		}
	}
}
