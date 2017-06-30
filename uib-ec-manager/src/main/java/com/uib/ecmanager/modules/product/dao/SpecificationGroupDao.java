/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.product.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.uib.ecmanager.common.persistence.CrudDao;
import com.uib.ecmanager.common.persistence.annotation.MyBatisDao;
import com.uib.ecmanager.modules.product.entity.SpecificationGroup;

/**
 * 商品规格DAO接口
 * 
 * @author gaven
 * @version 2015-06-13
 */
@MyBatisDao
public interface SpecificationGroupDao extends CrudDao<SpecificationGroup> {
	/**
	 * 根据商品id查询规格组集合
	 * 
	 * @param productId
	 * @return
	 */
	public List<SpecificationGroup> querySpecificationGroupsByProductId(@Param("productId") String productId);

	/**
	 * 根据条件查询规格组集合
	 * 
	 * @param paramMap
	 * @return
	 */
	public List<SpecificationGroup> querySpecificationGroupByMap(Map<String, Object> paramMap);

	/**
	 * 根据条件删除规格
	 * 
	 * @param productCategoryId
	 */
	public void batchDeleteByProductCategoryId(@Param("productCategoryId") String productCategoryId);

}