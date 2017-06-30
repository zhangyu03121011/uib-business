package com.uib.product.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.uib.core.dao.MyBatisDao;
import com.uib.product.dao.ProductParameterRefDao;
import com.uib.product.entity.ProductParameterRef;

/**
 * 
 * @author Gaven
 *
 */
@Component
public class ProductParameterRefDaoImpl extends MyBatisDao<ProductParameterRef>
		implements ProductParameterRefDao {

	@Override
	public List<String> queryParameterIdsByProductId(String productId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("productId", productId);
		return this.getSqlSession().selectList("queryParameterIdsByProductId",
				map);
	}

	@Override
	public List<ProductParameterRef> queryProductParameterRersByProductId(
			String productId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("productId", productId);
		return this.get("queryProductParameterRersByProductId", map);
	}

}
