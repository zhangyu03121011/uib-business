package com.uib.cart.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.uib.cart.dao.CartDao;
import com.uib.cart.entity.Cart;
import com.uib.core.dao.MyBatisDao;
import com.uib.core.exception.GenericException;

@Component
public class CartDaoImpl extends MyBatisDao<Cart> implements CartDao {

	@Override
	public Cart selectCartByMemberId(String memberId) throws Exception {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("memberId", memberId);
		return this.getUnique("selectCartByMemberId",map);
	}

	@Override
	public void updateCart(Cart cart) throws Exception {
		this.update("updateCart", cart);
	}

	@Override
	public void removeCartById(String cartId) throws Exception {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("cartId", cartId);
		this.remove("removeCartById", map);
	}

	@Override
	public Cart selectCart(Cart cart) throws Exception {
		return this.getUnique("selectCart", cart);
	}

	@Override
	public void saveCart(Cart cart) throws Exception {
		this.save("saveCart", cart);
	}

	@Override
	public Map<String, Object> queryCartByUserName(String userName) throws GenericException{
		return this.getMap("queryCartByUserName", userName);
	}

	@Override
	public Map<String, Object> queryCartItemByUserNameAndProductId(
			String userName, String productId) throws GenericException {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("userName", userName);
		map.put("productId", productId);
		return this.getMap("queryCartItemByUserNameAndProductId", map);
	}
}
