package com.uib.product.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.easypay.core.dao.MyBatisDao;
import com.uib.common.web.Global;
import com.uib.product.dao.ProductImageRefDao;
import com.uib.product.entity.ProductImageRef;

@Component
public class ProductImageRefDaoImpl extends MyBatisDao<ProductImageRef> implements
		ProductImageRefDao {

	@Override
	public List<ProductImageRef> queryProductImageRefByProductId(
			String productId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("productId", productId);
		return this.get("queryProductImageRefByProductId", map);
	}

	@Override
	public List<ProductImageRef> queryLast3ImageById(String productId)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("productId", productId);
		map.put("imageUrl", Global.getConfig("upload.image.path"));
		return this.get("queryLast3ImageById", map);
	}

}
