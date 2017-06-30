package com.uib.product.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uib.product.dao.PropertyGroupDao;
import com.uib.product.entity.PropertyGroup;
import com.uib.product.service.PropertyGroupService;

@Service
public class PropertyGroupServiceImpl implements PropertyGroupService {
//	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private PropertyGroupDao propertyGroupDao;

	@Override
	public List<PropertyGroup> queryPropertyGoupsByCategoryId(String categoryId)
			throws Exception {
		return propertyGroupDao.queryPropertyGoupsByCategoryId(categoryId);
	}

	@Override
	public List<PropertyGroup> queryPropertyGroupsByProductId(String productId)
			throws Exception {
		return propertyGroupDao.queryPropertyGroupsByProductId(productId);
	}

}
