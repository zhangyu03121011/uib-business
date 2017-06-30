package com.uib.cart.dao;

import java.util.List;
import java.util.Map;

import com.uib.cart.entity.CartItemInfo;

public interface CartItemInfoDao {
	
	/**
	 * 批量修改购物车价格
	 * @param cartItemList
	 * @throws Exception
	 */
	public void batchUpdatePrice(List<CartItemInfo> cartItemInfoList) throws Exception;
	
	/**
	 * 通过产品id和cartId查询商品价格
	 * @param cartItemList
	 * @return
	 * @throws Exception
	 */
	public List<CartItemInfo> queryPriceByProductId(List<CartItemInfo> cartItemInfoList) throws Exception;
	
	/**
	 * 修改购物车价格
	 * @param cartItemInfo
	 * @throws Exception
	 */
	public void updatePrice(CartItemInfo cartItemInfo) throws Exception;
	
	/**
	 * 查询特定商品在购物车的数量
	 * @param parm
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> queryProductNumberByIdAndUserName(Map<String,Object> parm) throws Exception;
	
	/**
	 * 查询用户购物车所有商品的数量
	 * @param parm
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> queryProductNumberByUserName(Map<String,Object> parm) throws Exception;
}
