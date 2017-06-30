/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.product.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.uib.ecmanager.common.persistence.CrudDao;
import com.uib.ecmanager.common.persistence.annotation.MyBatisDao;
import com.uib.ecmanager.modules.product.entity.ProductParameterRef;

/**
 * 商品参数值DAO接口
 * 
 * @author gaven
 * @version 2015-06-26
 */
@MyBatisDao
public interface ProductParameterRefDao extends CrudDao<ProductParameterRef> {

	/**
	 * 根据商品Id查询参数Id集合
	 * 
	 * @param productId
	 * @return
	 */
	public List<String> queryParameterIdsByProductId(
			@Param("productId") String productId);

	/**
	 * 根据商品Id查询参数值集合
	 * 
	 * @param productId
	 * @return
	 */
	public List<ProductParameterRef> queryProductParameterRersByProductId(
			@Param("productId") String productId);

	/**
	 * 批量删除参数值
	 * 
	 * @param productId
	 * @param parameterIds
	 */
	public void deleteFrom(@Param("productId") String productId,
			@Param("parameterIds") List<String> parameterIds);

}