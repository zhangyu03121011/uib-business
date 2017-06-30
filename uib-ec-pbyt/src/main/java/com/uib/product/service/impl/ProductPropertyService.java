package com.uib.product.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uib.product.dao.ProductPropertyDao;

/**
 * @todo   
 * @author chengw
 * @date   2015年12月28日
 * @time   下午9:44:19
 */
@Service
public class ProductPropertyService {
	
	@Autowired
	private ProductPropertyDao productPropertyDao;
	
	public List<Map<String, Object>> queryProductPropertyById(String productId) throws Exception{
		return productPropertyDao.queryProductPropertyById(productId);
	}

}


