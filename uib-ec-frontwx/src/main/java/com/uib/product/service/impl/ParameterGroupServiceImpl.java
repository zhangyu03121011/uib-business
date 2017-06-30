package com.uib.product.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uib.product.dao.ParameterGroupDao;
import com.uib.product.entity.ParameterGroup;
import com.uib.product.service.ParameterGroupService;

@Service
public class ParameterGroupServiceImpl implements ParameterGroupService {
//	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private ParameterGroupDao parameterGroupDao;

	@Override
	public List<ParameterGroup> queryParameterGroupsByCategoryId(
			String categoryId) throws Exception {
		return parameterGroupDao.queryParameterGroupsByCategoryId(categoryId);
	}

}
