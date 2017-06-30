package com.uib.product.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uib.product.dao.ProductImageRefDao;
import com.uib.product.entity.ProductImageRef;
import com.uib.product.service.ProductImageRefService;

@Service
public class ProductImageRefServiceImpl implements ProductImageRefService {
//	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private ProductImageRefDao productImageRefDao;

	@Override
	public List<ProductImageRef> queryProductImageRefByProductId(
			String productId) throws Exception {
		return productImageRefDao.queryProductImageRefByProductId(productId);
	}

	@Override
	public List<ProductImageRef> queryLast3ImageById(String productId)
			throws Exception {
		return productImageRefDao.queryLast3ImageById(productId);
	}

}
