package com.uib.product.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.uib.core.dao.MyBatisDao;
import com.uib.product.dao.ParameterGroupDao;
import com.uib.product.entity.ParameterGroup;

@Component
public class ParameterGroupDaoImpl extends MyBatisDao<ParameterGroup> implements
		ParameterGroupDao {

	@Override
	public List<ParameterGroup> queryParameterGroupsByCategoryId(
			String categoryId) throws Exception {
		Map<String,String> map = new HashMap<String, String>();
		map.put("categoryId", categoryId);
		return this.get("queryParameterGroupsByCategoryId", map);
	}

}
