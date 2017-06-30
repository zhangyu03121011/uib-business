/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.product.dao;

import org.apache.ibatis.annotations.Param;

import com.uib.ecmanager.common.persistence.CrudDao;
import com.uib.ecmanager.common.persistence.annotation.MyBatisDao;
import com.uib.ecmanager.modules.product.entity.Brand;

/**
 * 商品品牌DAO接口
 * @author gaven
 * @version 2015-06-13
 */
@MyBatisDao
public interface BrandDao extends CrudDao<Brand> {
	/**
	 * 根据分类id删除品牌
	 * @param productCategoryId
	 */
	public void batchDeleteByProductCategoryId(@Param("productCategoryId") String productCategoryId);
}