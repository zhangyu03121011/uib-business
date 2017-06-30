package com.uib.product.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.uib.mobile.dto.Category4Mobile;
import com.uib.product.dao.ProductCategoryDao;
import com.uib.product.entity.ProductCategory;
import com.uib.product.service.ProductCategoryService;

/**
 * 商品分类 service
 * @author kevin
 *
 */
@Component
public class ProductCategoryServiceImpl implements ProductCategoryService{

	@Autowired
	private ProductCategoryDao productCategoryDao;
	
	
	@Override
	public List<ProductCategory> getCategoryByMeridAndParentId(String parentId, String merId) throws Exception {
		return productCategoryDao.getCategoryByMeridAndParentId(parentId, merId);
	}


	@Override
	public ProductCategory getMostParentCategorysByCategoryId(String categoryId)
			throws Exception {
		ProductCategory category = productCategoryDao.getMostParentByCategoryId(categoryId);
		return category;
	}


	@Override
	public List<ProductCategory> queryCategorysByCategoryIds(List categoryIds)
			throws Exception {
		return productCategoryDao.queryCategorysByCategoryIds(categoryIds);
	}


	@Override
	public List<ProductCategory> getCategoryByProduct(String id)throws Exception {
		return  productCategoryDao.getCategoryByProduct(id);
	}

	@Override
	public ProductCategory getProductCategory(String id) throws Exception{
			return productCategoryDao.getProductCategory(id);
	}


	@Override
	public List<Category4Mobile> querySubCategory4Mobile(Map<String, Object> map)
			throws Exception {
		return productCategoryDao.querySubCategory(map);
	}

}
