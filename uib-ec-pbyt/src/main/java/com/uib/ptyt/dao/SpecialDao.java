package com.uib.ptyt.dao;

import java.util.List;
import java.util.Map;

import com.uib.common.annotation.MyBatisDao;
import com.uib.ptyt.entity.Special;
import com.uib.ptyt.entity.SpecialMerchantRef;



@MyBatisDao
public interface SpecialDao {
	
	/**
	 * 分页查询商品
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> findSpecialByPage(Map<String, Object> map) throws Exception;
	
	public List<Map<String, Object>> findSpecialProd(Map<String, Object> map) throws Exception;
	
	public List<Map<String, Object>> findProductById(Map<String, Object> map) throws Exception;
	
	public Map<String, Object> findSpecialById(Map<String, Object> map) throws Exception;
	
	public List<Special> findSpecialUserId(Map<String, Object> map) throws Exception;
	
	public void addMySpecials(List<SpecialMerchantRef> list) throws Exception;
	
	public void deleteMySpecials(Map<String, Object> map) throws Exception;
}
