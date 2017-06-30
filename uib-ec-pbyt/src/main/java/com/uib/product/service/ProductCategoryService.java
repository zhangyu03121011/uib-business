package com.uib.product.service;

import java.util.List;
import java.util.Map;

import com.uib.mobile.dto.Category4Mobile;
import com.uib.product.entity.ProductCategory;

/**
 * 商品分类service
 * 
 * @author kevin
 *
 */
public interface ProductCategoryService {

	/**
	 * 根据商户号以及上级ID获取分类信息
	 * 
	 * @param parentId
	 * @param merId
	 * @return
	 * @throws Exception
	 */
	List<ProductCategory> getCategoryByMeridAndParentId(String parentId,
			String merId) throws Exception;

	/**
	 * 根据分类id获取顶级分类
	 * 
	 * @param categoryId
	 * @return
	 * @throws Exception
	 */
	ProductCategory getMostParentCategorysByCategoryId(String categoryId)
			throws Exception;

	/**
	 * 根据ids集合查询分类集合
	 * 
	 * @param categoryIds
	 * @return
	 * @throws Exception
	 */
	List<ProductCategory> queryCategorysByCategoryIds(List categoryIds)
			throws Exception;

	/**
	 * 查询品牌下的产品分类
	 * 
	 * @param id
	 * @return
	 */
	List<ProductCategory> getCategoryByProduct(String id) throws Exception;

	ProductCategory getProductCategory(String id) throws Exception;

	/**
	 * 根据条件查询商品分类子类
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	List<Category4Mobile> querySubCategory4Mobile(Map<String, Object> map)
			throws Exception;
}
