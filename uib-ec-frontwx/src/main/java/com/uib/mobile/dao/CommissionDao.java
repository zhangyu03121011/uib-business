package com.uib.mobile.dao;

import java.util.List;
import java.util.Map;

import com.uib.mobile.dto.Commission;




public interface CommissionDao {
	
	/**
	 * 查询所有
	 * @param commission
	 * @return
	 */
	public List<Commission> findByName(Map<String, Object> params) throws Exception;
	
	/**
	 * 删除记录
	 * @param id
	 */
	public void deleteCommissionById(String[] ids) throws Exception;
	
	
}
