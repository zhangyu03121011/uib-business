package com.uib.product.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.uib.core.dao.MyBatisDao;
import com.uib.product.dao.ProductPropertyDao;
import com.uib.product.entity.ProductProperty;

@Component
public class ProductPropertyDaoImpl extends MyBatisDao<ProductProperty> implements
		ProductPropertyDao {

	@Override
	public List<Map<String, Object>> queryProductPropertyById(String productId) {
		return this.getList("queryProductPropertyById", productId);
	}

}
