package com.uib.product.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uib.product.dao.ProductParameterRefDao;
import com.uib.product.entity.ProductParameterRef;
import com.uib.product.service.ProductParameterRefService;

@Service
public class ProductParameterRefServiceImpl implements
		ProductParameterRefService {
	@Autowired
	private ProductParameterRefDao productParameterDao;

	@Override
	public List<String> queryParameterIdsByProductId(String productId) {
		return productParameterDao.queryParameterIdsByProductId(productId);
	}

	@Override
	public List<ProductParameterRef> queryProductParameterRersByProductId(
			String productId) {
		return productParameterDao
				.queryProductParameterRersByProductId(productId);
	}

}
