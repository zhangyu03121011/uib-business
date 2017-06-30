package com.uib.cart.dao;

import java.util.Map;

import com.alibaba.dubbo.rpc.service.GenericException;
import com.uib.cart.entity.Cart;

public interface CartDao {
	/**
	 * 根据会员Id查询购物车
	 * 
	 * @param memberId
	 * @return
	 * @throws Exception
	 */
	public Cart selectCartByMemberId(String memberId) throws Exception;

	/**
	 * 更新购物车
	 * 
	 * @param cart
	 */
	public void updateCart(Cart cart) throws Exception;

	/**
	 * 根据购物车Id删除购物车
	 * 
	 * @param cartId
	 * @throws Exception
	 */
	public void removeCartById(String cartId) throws Exception;

	/**
	 * 查询购物车
	 * 
	 * @param cart
	 * @return
	 * @throws Exception
	 */
	public Cart selectCart(Cart cart) throws Exception;

	/**
	 * 保存购物车
	 * 
	 * @param cart
	 * @throws Exception
	 */
	public void saveCart(Cart cart) throws Exception;
	
	/**
	 * 通过会员id查询购物车信息
	 * @param memId
	 * @return
	 */
	public Map<String,Object> queryCartByUserId(String userId) throws GenericException;
	
	/**
	 * 通过会员id和产品id查询购物车明细信息
	 * @param userName
	 * @param productId
	 * @return
	 * @throws GenericException
	 */
	public Map<String,Object> queryCartItemByUserIdAndProductId(String userId,String productId) throws GenericException;

}
