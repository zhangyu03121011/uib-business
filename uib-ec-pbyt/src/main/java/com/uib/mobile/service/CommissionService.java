package com.uib.mobile.service;

import java.util.List;
import java.util.Map;

import com.uib.mobile.dto.Commission;
import com.uib.mobile.dto.RecommendProductLog;

public interface CommissionService {
	
	/**
	 * 根据用户查询所有佣金记录
	 * @param commission
	 * @return
	 * @throws Exception
	 */
	public List<Commission> findByName(Map<String, Object> params) throws Exception;
	
	/**
	 * 删除记录
	 * @param id
	 * @throws Exception
	 */
	public void deleteCommissionById(String[] ids)throws Exception;

	/**
	 * 查询某个用户的所用佣金记录(1.平台购买 2.分享给别人  3.别人分享给自己购买)
	 * @param page
	 * @throws Exception
	 */
	public List<Map<String,Object>> countCommission(String page)throws Exception;
}
