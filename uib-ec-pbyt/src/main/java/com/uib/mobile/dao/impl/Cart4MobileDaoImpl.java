package com.uib.mobile.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.easypay.core.dao.MyBatisDao;
import com.uib.mobile.dao.Cart4MobileDao;
import com.uib.mobile.dto.CartPojo4Mobile;

/**
 * @todo   
 * @author chengw
 * @date   2015年12月10日
 * @time   下午4:54:10
 */
@Component
public class Cart4MobileDaoImpl extends MyBatisDao<CartPojo4Mobile> implements Cart4MobileDao {

	@Override
	public List<Map<String, Object>> queryCartInfoByUserName(
			Map<String, Object> parm) throws Exception {
		return this.getList("queryCartInfoByUserName", parm);
	}



}


