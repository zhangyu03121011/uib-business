package com.uib.mobile.dao;

import java.util.List;
import java.util.Map;

/**
 * @todo   
 * @author chengw
 * @date   2015年12月10日
 * @time   下午4:49:51
 */
public interface Cart4MobileDao {
	
	/**
	 * 根据用户名查询购物车信息
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> queryCartInfoByUserName(Map<String,Object> parm) throws Exception;

}


