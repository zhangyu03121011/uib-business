/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.product.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.uib.ecmanager.common.persistence.CrudDao;
import com.uib.ecmanager.common.persistence.annotation.MyBatisDao;
import com.uib.ecmanager.modules.product.entity.ParamBean;
import com.uib.ecmanager.modules.product.entity.PropertyGroup;

/**
 * 商品属性DAO接口
 * 
 * @author gaven
 * @version 2015-06-04
 */
@MyBatisDao
public interface PropertyGroupDao extends CrudDao<PropertyGroup> {
	public void batchDeleteByProductCategoryId(@Param("productCategoryId") String productCategroyId);

	/**
	 * 根据分类查询继承自上级分类的附加属性
	 * 
	 * @param categoryId
	 * @param parentId
	 * @return
	 */
	public List<PropertyGroup> queryParentAddPropertyGroups(@Param("categoryId") String categoryId,
			@Param("parentId") String parentId);

	/**
	 * 根据分类Id查询属性组信息
	 * 
	 * @param categoryId
	 * @return
	 */
	public List<PropertyGroup> queryPropertyGroupsByCategoryId(@Param("categoryId") String categoryId);

	/**
	 * 保存分类的附加属性
	 * 
	 * @param categoryId
	 * @param propertyGroupIds
	 */
	public void saveCategoryPropertyGroupRef(@Param("categoryId") String categoryId,
			@Param("propertyGroupIdBeans") List<ParamBean<String[]>> propertyGroupIdBeans);

	/**
	 * 删除分类的继承属性组
	 * 
	 * @param categoryId
	 */
	public void deleteByCategoryId(@Param("categoryId") String categoryId);
}