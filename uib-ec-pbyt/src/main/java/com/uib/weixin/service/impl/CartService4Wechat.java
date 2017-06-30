package com.uib.weixin.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uib.common.web.Global;
import com.uib.weixin.dao.CartInfoDao;
import com.uib.weixin.dto.CartInfo4Wechat;

/**
 * @todo   
 * @author chengw
 * @date   2015年12月29日
 * @time   下午2:17:24
 */
@Service
public class CartService4Wechat {
	
	private Logger logger = LoggerFactory.getLogger(CartService4Wechat.class);
	
	@Autowired
	private CartInfoDao cartInfoDao;
	
	public List<CartInfo4Wechat> queryCartByUserName(String userId) throws Exception{
		logger.info("微信端查询购物车入参userId=" + userId);
		Map<String,Object> parm = new HashMap<String, Object>();
		parm.put("userId", userId);
		parm.put("imageUrl", Global.getConfig("upload.image.path"));
		return cartInfoDao.queryCartByUserName(parm);
	}
	public void deleteByCartItemId(String[] cartItemIds) throws Exception{
		List<String> list = new ArrayList<String>();
		for (String cartId : cartItemIds) {
			list.add(cartId);
		}
		cartInfoDao.deleteByCartItemId(list);
	}

}


