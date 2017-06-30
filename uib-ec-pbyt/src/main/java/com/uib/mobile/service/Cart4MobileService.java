package com.uib.mobile.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uib.common.web.Global;
import com.uib.mobile.dao.Cart4MobileDao;

@Service
public class Cart4MobileService {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private Cart4MobileDao cart4MobileDao;
	
	public List<Map<String,Object>> queryCartByUserName(String userName) throws Exception{
		logger.info("APP端查询购物车入参userName=" + userName);
		Map<String,Object> parm = new HashMap<String, Object>();
		parm.put("userName", userName);
		parm.put("imageUrl", Global.getConfig("upload.image.path"));
		List<Map<String,Object>> result = cart4MobileDao.queryCartInfoByUserName(parm);
		logger.info("APP端查询购物车返回记录数=" + result.size());
		return result;
	};

}
