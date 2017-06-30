package com.uib.product.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.uib.core.dao.MyBatisDao;
import com.uib.product.dao.PropertyGroupDao;
import com.uib.product.entity.PropertyGroup;

@Component
public class PropertyGroupDaoImpl extends MyBatisDao<PropertyGroup> implements
		PropertyGroupDao {

	@Override
	public List<PropertyGroup> queryPropertyGoupsByCategoryId(String categoryId)
			throws Exception {
		Map<String,String> map = new HashMap<String,String>();
		map.put("categoryId", categoryId);
		return this.get("queryPropertyGoupsByCategoryId", map);
	}

	@Override
	public List<PropertyGroup> queryPropertyGroupsByProductId(String productId)
			throws Exception {
		Map<String,String> map = new HashMap<String,String>();
		map.put("productId", productId);
		return this.get("queryPropertyGroupsByProductId", map);
	}

}
