package com.uib.mobile.service;

import java.util.List;
import java.util.Map;

import com.uib.mobile.dto.Commission;

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
	
}
