/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.product.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.uib.ecmanager.common.persistence.CrudDao;
import com.uib.ecmanager.common.persistence.annotation.MyBatisDao;
import com.uib.ecmanager.modules.product.entity.ParamBean;
import com.uib.ecmanager.modules.product.entity.ProductSpecificationRef;

/**
 * 规格商品关联DAO接口
 * 
 * @author gaven
 * @version 2015-06-24
 */
@MyBatisDao
public interface ProductSpecificationRefDao extends
		CrudDao<ProductSpecificationRef> {
	/**
	 * 根据商品Id查询规格id集合
	 * 
	 * @param productId
	 * @return
	 */
	public List<String> querySpecificationIdsByProductId(
			@Param("productId") String productId);

	/**
	 * 批量插入(方法有问题 )
	 * 
	 * @param productId
	 * @param specificationIds
	 */
	public void insertBatch(@Param("productId") String productId,
			@Param("specificationIds") List<String> specificationIds);

	/**
	 * 批量插入
	 * 
	 * @param productSpecificationRefs
	 */
	public void insertRefsBatch(
			@Param("productSpecificationRefs") List<ProductSpecificationRef> productSpecificationRefs);

	/**
	 * 批量删除
	 * 
	 * @param productId
	 * @param specificationIds
	 */
	public void deleteBatch(@Param("productId") String productId,
			@Param("specificationIds") List<String> specificationIds);

	/**
	 * 更新商品、规格关联关系
	 * 
	 * @param params
	 */
	public void updateSpecificationIdsRef(
			@Param("params") List<ParamBean<List<String>>> params);

	/**
	 * 根据商品id数组批量删除关联关系
	 * 
	 * @param pids
	 */
	public void batchDeleteByPids(@Param("pids") List<String> pids);
}