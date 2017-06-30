/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.product.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.uib.ecmanager.common.persistence.CrudDao;
import com.uib.ecmanager.common.persistence.annotation.MyBatisDao;
import com.uib.ecmanager.modules.product.entity.ProductSpecification;
import com.uib.ecmanager.modules.product.entity.SpecificationGroup;

/**
 * 商品规格DAO接口
 * 
 * @author gaven
 * @version 2015-06-13
 */
@MyBatisDao
public interface ProductSpecificationDao extends CrudDao<ProductSpecification> {

	/**
	 * 根据规格组id集合查询规格集合
	 * 
	 * @param ids
	 * @return
	 */
	public List<SpecificationGroup> getSpecificationByGroupIds(@Param("ids") String[] ids);

	/**
	 * 根据分类id查询规格组集合
	 * 
	 * @param productCategoryId
	 * @return
	 */
	public List<SpecificationGroup> querySpecificationByProductCategoryId(
			@Param("productCategoryId") String productCategoryId);

	/**
	 * 根据商品id查询规格集合
	 * 
	 * @param productId
	 * @return
	 */
	public List<ProductSpecification> querySpecificationsByProductId(@Param("productId") String productId);

	/**
	 * 根据规格id查询商品后缀名
	 * 
	 * @param ids
	 * @return
	 */
	public String querySuffixNameByProductSpecificationIds(@Param("ids") List<String> ids);

}