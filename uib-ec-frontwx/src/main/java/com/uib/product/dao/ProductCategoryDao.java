package com.uib.product.dao;

import java.util.List;
import java.util.Map;

import com.uib.mobile.dto.Category4Mobile;
import com.uib.product.entity.ProductCategory;

/**
 * 分类dao
 * 
 * @author kevin
 *
 */
public interface ProductCategoryDao {

	/**
	 * 根据商户号以及上级ID获取分类信息
	 * 
	 * @param parentId
	 * @param merId
	 * @return
	 * @throws Exception
	 */
	List<ProductCategory> getCategoryByMeridAndParentId(String parentId,
			String merchantNo) throws Exception;

	ProductCategory getCategoryById(String id);

	/**
	 * 根据分类id查询顶级分类
	 * 
	 * @param categoryId
	 *            分类id
	 * @return
	 */
	ProductCategory getMostParentByCategoryId(String categoryId);

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
	 * 根据商品查询分类
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	List<ProductCategory> getCategoryByProduct(String id) throws Exception;
	ProductCategory getProductCategory(String id) throws Exception;
	/**
	 * 根据集合条件查询子分类集合
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	List<Category4Mobile> querySubCategory(Map<String, Object> map)
			throws Exception;

}
