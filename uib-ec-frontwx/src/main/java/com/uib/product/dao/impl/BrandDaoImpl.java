package com.uib.product.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.uib.core.dao.MyBatisDao;
import com.uib.product.dao.BrandDao;
import com.uib.product.entity.Brand;

@Component
public class BrandDaoImpl extends MyBatisDao<Brand> implements BrandDao {

	@Override
	public List<Brand> queryBrandByCategoryId(String categoryId)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("categoryId", categoryId);
		return this.get("queryBrandByCategoryId", map);
	}

	public Brand getBrand(String id)throws Exception  {
		return this.getUnique("get", id);
	}
	

}
