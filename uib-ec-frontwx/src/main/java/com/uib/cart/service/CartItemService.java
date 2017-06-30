package com.uib.cart.service;

import java.util.Map;

import com.uib.cart.entity.CartItem;

public interface CartItemService {

	/**
	 * 添加购物车项
	 * 
	 * @param cartItem
	 * @throws Exception
	 */
	public void insertCartItem(CartItem cartItem) throws Exception;

	/**
	 * 更新购物车项
	 * 
	 * @param cartItem
	 * @throws Exception
	 */
	public void updateCartItem(CartItem cartItem) throws Exception;

	/**
	 * 根据商品id删除商品项
	 * 
	 * @param id
	 *            购物项ID
	 * @param productId
	 *            商品id
	 * @param cartId
	 *            购物车ID
	 * @throws Exception
	 */
	public void deleteByRelativeId(String[] id, String[] productId, String cartId) throws Exception;

	/**
	 * 根据商品id查询购物项
	 * 
	 * @param productId
	 * @return
	 * @throws Exception
	 */
	public CartItem queryById(String cartItemId) throws Exception;

	/** 
	* @Title: deleteByRelativeId 
	* @author sl 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param id
	* @param @param productId
	* @param @param cartId
	* @param @throws Exception   参数
	* @return void    返回类型 
	* @throws 
	*/
	void deleteByRelativeId(String cartId,String pid) throws Exception;
	
	/**
	 * 查询用户购物车特定商品的的数量
	 * @param productId
	 * @param userName
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> queryProductNumberByIdAndUserName(String productId,String userName) throws Exception;
	
	/**
	 * 查询用户购物车所有商品的数量
	 * @param parm
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> queryProductNumberByUserName(String userName) throws Exception;
}
