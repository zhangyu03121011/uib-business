package com.uib.cart.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.easypay.core.dao.MyBatisDao;
import com.uib.cart.dao.CartItemDao;
import com.uib.cart.entity.CartItem;

@Component
public class CartItemDaoImpl extends MyBatisDao<CartItem> implements CartItemDao {

	@Override
	public void insert(CartItem cartItem) throws Exception {
		this.save("insert", cartItem);
	}

	@Override
	public void update(CartItem cartItem) throws Exception {
		this.update("update", cartItem);
	}

	@Override
	public void deleteByRelativeId(String productId,String cartId) throws Exception {
		Map<String,Object> map = new HashMap<String, Object>(3);
		map.put("productId", productId);
		map.put("cartId", cartId);
		this.remove("deleteByRelativeId", map);
	}

	@Override
	public List<CartItem> getByProductId(String productId) throws Exception {
		return this.get("getByProductId", productId);
	}

	@Override
	public CartItem getById(String id) throws Exception {
		return this.getUnique("getById", id);
	}

	@Override
	public void deleteById(String id) throws Exception {
		this.remove("deleteById", id);
	}

}
