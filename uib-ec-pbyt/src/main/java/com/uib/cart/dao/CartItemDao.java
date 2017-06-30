package com.uib.cart.dao;

import java.util.List;

import com.uib.cart.entity.CartItem;

public interface CartItemDao {
	/**
	 * 添加购物车项
	 * 
	 * @param cartItem
	 * @throws Exception
	 */
	public void insert(CartItem cartItem) throws Exception;

	/**
	 * 更新购物车项
	 * 
	 * @param CartItem
	 * @throws Exception
	 */
	public void update(CartItem CartItem) throws Exception;

	/**
	 * 根据商品id删除商品项
	 * 
	 * @param productId
	 * @throws Exception
	 */
	public void deleteByRelativeId(String productId, String cartId)
			throws Exception;

	/**
	 * 根据id删除购物车项
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void deleteById(String id) throws Exception;

	/**
	 * 根据商品id查询购物项
	 * 
	 * @param productId
	 * @return
	 * @throws Exception
	 */
	public List<CartItem> getByProductId(String productId) throws Exception;

	/**
	 * 根据id查询购物车项
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public CartItem getById(String id) throws Exception;
	
}
