package com.uib.cart.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.easypay.core.dao.MyBatisDao;
import com.uib.cart.dao.CartItemInfoDao;
import com.uib.cart.entity.CartItemInfo;

@Component
public class CartItemInfoDaoImpl extends MyBatisDao<CartItemInfo> implements CartItemInfoDao {


	@Override
	public void batchUpdatePrice(List<CartItemInfo> cartItemInfoList) throws Exception {
		this.update("batchUpdatePrice", cartItemInfoList);
	}

	@Override
	public List<CartItemInfo> queryPriceByProductId(List<CartItemInfo> cartItemList) throws Exception {
		return this.get("queryPriceByProductId", cartItemList);
	}

	@Override
	public void updatePrice(CartItemInfo cartItemInfo) throws Exception {
		this.update("updatePrice", cartItemInfo);
	}

	@Override
	public Map<String, Object> queryProductNumberByIdAndUserName(
			Map<String, Object> parm) throws Exception{
		return this.getMap("queryProductNumberByIdAndUserName", parm);
	}

	@Override
	public Map<String, Object> queryProductNumberByUserName(
			Map<String,Object> parm) throws Exception {
		return this.getMap("queryProductNumberByUserName", parm);
	}

}
