/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.product.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.uib.ecmanager.common.persistence.CrudDao;
import com.uib.ecmanager.common.persistence.annotation.MyBatisDao;
import com.uib.ecmanager.modules.product.entity.ProductPropertyRef;

/**
 * 属性、商品关联DAO接口
 * 
 * @author gaven
 * @version 2015-06-29
 */
@MyBatisDao
public interface ProductPropertyRefDao extends CrudDao<ProductPropertyRef> {
	/**
	 * 批量删除属性
	 * 
	 * @param productId
	 * @param parameterIds
	 */
	public void deleteFrom(@Param("productId") String productId,
			@Param("propertyIds") List<String> propertyIds);

	/**
	 * 根据商品id查询属性id集合
	 * 
	 * @param productId
	 * @return
	 */
	public List<String> queryPropertyIdsByProductId(
			@Param("productId") String productId);
}