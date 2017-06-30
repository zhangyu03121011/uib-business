package com.uib.weixin.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.easypay.core.dao.MyBatisDao;
import com.uib.weixin.dao.CartInfoDao;
import com.uib.weixin.dto.CartInfo4Wechat;

/**
 * @todo   
 * @author chengw
 * @date   2015年12月29日
 * @time   下午1:41:00
 */
@Component
public class CartInfoDaoImpl  extends MyBatisDao<CartInfo4Wechat> implements CartInfoDao{

	@Override
	public List<CartInfo4Wechat> queryCartByUserName(Map<String,Object> parm)
			throws Exception {
		return this.getObjectList("queryCartByUserName", parm);
	}
	
	@Override
	public void deleteByCartItemId(List<String> list)
			throws Exception {
		 this.remove("deleteByCartItemId", list);
	}

}


