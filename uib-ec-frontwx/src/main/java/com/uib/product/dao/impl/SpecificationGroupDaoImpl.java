package com.uib.product.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.uib.core.dao.MyBatisDao;
import com.uib.product.dao.SpecificationGroupDao;
import com.uib.product.entity.SpecificationGroup;

@Component
public class SpecificationGroupDaoImpl extends MyBatisDao<SpecificationGroup>
		implements SpecificationGroupDao {

	@Override
	public List<SpecificationGroup> querySpecificationGroupsByCategoryId(
			String categoryId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("categoryId", categoryId);
		return this.get("querySpecificationGroupsByCategoryId", map);
	}

	@Override
	public List<SpecificationGroup> querySpecificationGroupsByProductId(
			String productId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("productId", productId);
		return this.get("querySpecificationGroupsByProductId", map);
	}

}
