package com.uib.cart.service;

import java.util.Map;

import com.uib.cart.entity.Cart;
import com.uib.mobile.dto.ReturnMsg;

public interface CartService {
	/**
	 * 获取当前购物车
	 * 
	 * @return
	 * @throws Exception
	 */

	public Cart getCurrentCart() throws Exception;

	/**
	 * 保存购物车
	 * 
	 * @param cart
	 * @throws Exception
	 */
	public void save(Cart cart) throws Exception;

	/**
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void removeById(String id, String pid) throws Exception;

	/**
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Cart selectCartById(String id) throws Exception;

	/**
	 * 
	 * @param memberId
	 * @return
	 * @throws Exception
	 */
	public ReturnMsg<Cart> selectCartByUserName(String userName)
			throws Exception;

	/**
	 * 根据购物车id删除购物车
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void deleteCartById(String id) throws Exception;
	
	/**
	 * 通过会员id查询购物车信息
	 * @param memId
	 * @return
	 */
	public Map<String,Object> queryCartByUserId(String userId) throws Exception;
	
	public Map<String,Object> queryCartItemByUserIdAndProductId(String userId,String productId) throws Exception;
	
	/**
	 * 商品加入购物车
	 * @param productId
	 * @param quantity
	 * @param userName
	 * @return
	 * @throws Exception
	 */
	public ReturnMsg<String> addCart(String productId,Integer quantity,String userId,String terminalFlag,String specificationIds,String recommendMemberId) throws Exception;

}
