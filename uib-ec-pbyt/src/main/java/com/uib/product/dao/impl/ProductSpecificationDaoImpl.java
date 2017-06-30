package com.uib.product.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.easypay.core.dao.MyBatisDao;
import com.uib.product.dao.ProductSpecificationDao;
import com.uib.product.entity.ProductSpecification;

@Component
public class ProductSpecificationDaoImpl extends
		MyBatisDao<ProductSpecification> implements ProductSpecificationDao {
	
	/**
	 * 根据商品id查询规格集合
	 * 
	 * @param productId
	 * @return
	 */
	public List<ProductSpecification> querySpecificationsByProductId(
			String productId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("productId", productId);
		return this.get("querySpecificationsByProductId", map);
	}

	@Override
	public List<String> selectSpecificationNames(String productId) {
		List<ProductSpecification> psList = querySpecificationsByProductId(productId);
		List<String> nameList = new ArrayList<String>(psList.size());
		for (ProductSpecification productSpecification : psList) {
			nameList.add(productSpecification.getName());
		}
		return nameList;
	}
}
