package com.uib.product.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uib.product.dao.ProductSpecificationDao;
import com.uib.product.entity.ProductSpecification;
import com.uib.product.service.ProductSpecificationService;

@Service
public class ProductSpecificationServiceImpl implements
		ProductSpecificationService {
	@Autowired
	private ProductSpecificationDao productSpecificationDao;

	@Override
	public List<ProductSpecification> querySpecificationsByProductId(
			String productId) {
		return productSpecificationDao.querySpecificationsByProductId(productId);
	}

	@Override
	public List<String> querySpecificationValues(String productId) {
		return productSpecificationDao.selectSpecificationNames(productId);
	}

}
