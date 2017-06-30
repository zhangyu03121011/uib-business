package com.uib.cart.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uib.cart.dao.CartItemDao;
import com.uib.cart.dao.CartItemInfoDao;
import com.uib.cart.entity.CartItem;
import com.uib.cart.service.CartItemService;
import com.uib.product.service.ProductService;
import com.uib.serviceUtils.Utils;

@Service
public class CartItemServiceImpl implements CartItemService {
	
	private Logger logger = LoggerFactory.getLogger(CartItemServiceImpl.class);
	
	@Autowired
	private CartItemDao cartItemDao;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private CartItemInfoDao cartItemInfoDao;

	@Override
	public void insertCartItem(CartItem cartItem) throws Exception {
		cartItemDao.insert(cartItem);
	}

	@Override
	public void updateCartItem(CartItem cartItem) throws Exception {
		cartItem.setModifyDate(new Date());
		cartItemDao.update(cartItem);
	}

	@Override
	public void deleteByRelativeId(String[] id, String[] productId, String cartId) throws Exception {
		if (Utils.isNotBlank(id)) {
			for (int i = 0; i < productId.length; i++) {
//				productService.releaseAllocatedStockByCartItemId(productId[i], id[i]);
				cartItemDao.deleteById(id[i]);
			}
		} else {
			for (int i = 0; i < productId.length; i++) {
//				productService.releaseAllocatedStockByCartId(productId[i], cartId);
				cartItemDao.deleteByRelativeId(productId[i], cartId);
			}
		}
	}

	public void deleteByRelativeId(String cartId,String pid) throws Exception {
		//productService.releaseAllocatedStockByCartId(null, cartId);
		cartItemDao.deleteByRelativeId(pid, cartId);
	}

	@Override
	public CartItem queryById(String cartItemId) throws Exception {
		return cartItemDao.getById(cartItemId);
	}

	@Override
	public Map<String, Object> queryProductNumberByIdAndUserName(
			String productId, String userName) throws Exception {
		logger.info("查询用户购物车特定商品的的数量入参productId=" + productId + ",userName=" + userName);
		Map<String, Object> parm = new HashMap<String, Object>();
		parm.put("productId", productId);
		parm.put("userName", userName);
		return cartItemInfoDao.queryProductNumberByIdAndUserName(parm);
	}

	@Override
	public Map<String, Object> queryProductNumberByUserName(String userName)
			throws Exception {
		logger.info("查询用户购物车特定商品的的数量入参userName=" + userName);
		Map<String, Object> parm = new HashMap<String, Object>();
		parm.put("userName", userName);
		return cartItemInfoDao.queryProductNumberByUserName(parm);
	}

}
