/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.product.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.uib.ecmanager.common.persistence.CrudDao;
import com.uib.ecmanager.common.persistence.annotation.MyBatisDao;
import com.uib.ecmanager.modules.product.entity.ProductSpecificationGroupRef;

/**
 * 商品规格组关联DAO接口
 * 
 * @author gaven
 * @version 2015-11-17
 */
@MyBatisDao
public interface ProductSpecificationGroupRefDao extends
		CrudDao<ProductSpecificationGroupRef> {
	/**
	 * 批量插入商品规格组关联记录
	 * 
	 * @param groupIds
	 * @param productIds
	 */
	void batchSave(@Param("groupIds") List<String> groupIds,
			@Param("productIds") List<String> productIds);

	/**
	 * 根据id批量删除商品-规格组关联关系
	 * 
	 * @param productIds
	 */
	void batchDeleteByProductIds(@Param("productIds") List<String> productIds);

	/**
	 * 根据货物id批量删除商品规格组关联记录
	 * 
	 * @param goodsId
	 */
	void batchDeleteByGoodsId(@Param("goodsId") String goodsId);

	/**
	 * 根据商品id删除商品规格关联记录
	 * 
	 * @param productId
	 */
	void deleteByProductId(@Param("productId") String productId);

}