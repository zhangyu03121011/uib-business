package com.uib.cart.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.uib.cart.dao.CartItemInfoDao;
import com.uib.cart.entity.CartItemInfo;
import com.uib.common.enums.ExceptionEnum;
import com.uib.mobile.dto.ReturnMsg;
import com.uib.serviceUtils.Utils;

@Component
public class CartPriceService {
	
	private Logger logger = LoggerFactory.getLogger(CartPriceService.class);
	
	@Autowired
	private CartItemInfoDao cartItemDao;
	
	@Async
	public ReturnMsg<String> batchUpdPrice(String cartId,String[] productIds){
		logger.info("批量修改购物车价格入参cartId=" + cartId + ",productIds=" + productIds);
		ReturnMsg<String> result = new  ReturnMsg<String>();
		result.setStatus(true);
		
		//检查参数是否为空
		if (Utils.isObjectsBlank(cartId, productIds)) {
			result.setStatus(false);
			result.setCode(ExceptionEnum.param_not_null.getIndex());
			result.setMsg(ExceptionEnum.param_not_null.getValue());
			return result;
		}
		
		try {
			List<CartItemInfo> CartItemInfoList = new ArrayList<CartItemInfo>();
			for (int i=0;i<productIds.length;i++) {
				CartItemInfo CartItemInfo = new CartItemInfo();
				CartItemInfo.setCartId(cartId);
				CartItemInfo.setProductId(productIds[i]);
				CartItemInfoList.add(CartItemInfo);
			}
			List<CartItemInfo> cartPriceList = cartItemDao.queryPriceByProductId(CartItemInfoList);
//			cartItemDao.batchUpdatePrice(cartPriceList);
			for (CartItemInfo cartItemInfo : cartPriceList) {
				cartItemDao.updatePrice(cartItemInfo);
			}
		} catch (Exception e) {
			logger.error("批量修改购物车价格失败!", e);
			result.setStatus(false);
			result.setCode(ExceptionEnum.business_logic_exception.getIndex());
			result.setMsg(ExceptionEnum.business_logic_exception.getValue());
		}
		logger.info("批量修改购物车价格入参结束");
		return result;
	}

}
