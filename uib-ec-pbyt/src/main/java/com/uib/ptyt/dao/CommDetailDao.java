package com.uib.ptyt.dao;

import java.util.List;
import java.util.Map;

import com.uib.common.annotation.MyBatisDao;
import com.uib.ptyt.entity.RecommOrderDto;

@MyBatisDao
public interface CommDetailDao {
	
	/***
	 * 查询销售订单
	 */
	public List<RecommOrderDto> querRecommendList(Map<String, Object> map) throws Exception;
}
