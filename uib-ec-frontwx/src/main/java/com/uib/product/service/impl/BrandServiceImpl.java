package com.uib.product.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uib.product.dao.BrandDao;
import com.uib.product.entity.Brand;
import com.uib.product.service.BrandService;

@Service
public class BrandServiceImpl implements BrandService{
//	private Logger logger = LoggerFactory.getLogger(BrandServiceImpl.class);
	
	@Autowired
	private BrandDao brandDao;

	@Override
	public List<Brand> queryBrandByCategoryId(String categoryId)
			throws Exception {
		return brandDao.queryBrandByCategoryId(categoryId);
	}

	@Override
	public Brand get(String id) throws Exception {
		return brandDao.getBrand(id);
	}
	
	
}
