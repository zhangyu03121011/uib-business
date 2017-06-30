/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/easypay_ec">easypay_ec</a> All rights reserved.
 */
package com.uib.product.dao;

import java.util.List;
import java.util.Map;

/**
 * 商品属性DAO接口
 * @author gaven
 * @version 2015-06-04
 */
public interface ProductPropertyDao {
	
	public List<Map<String,Object>> queryProductPropertyById(String productId);
	
}