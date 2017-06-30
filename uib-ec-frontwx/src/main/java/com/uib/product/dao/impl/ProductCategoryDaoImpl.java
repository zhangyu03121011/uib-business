package com.uib.product.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.uib.core.dao.MyBatisDao;
import com.uib.mobile.dto.Category4Mobile;
import com.uib.product.dao.ProductCategoryDao;
import com.uib.product.entity.ProductCategory;
import com.uib.serviceUtils.Utils;

/**
 * 分类信息
 * @author kevin
 *
 */
@Component
public class ProductCategoryDaoImpl  extends MyBatisDao<ProductCategory> implements ProductCategoryDao{

	@Override
	public List<ProductCategory> getCategoryByMeridAndParentId(String parentId, String merchantNo) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("parentId", parentId);
		map.put("merchantNo", merchantNo);
		return this.get("getCategoryByMeridAndParentId", map);
	}

	
	@Override
	public ProductCategory getCategoryById(String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		List<ProductCategory> categorys = this.get("getCategoryById", map);
		return categorys!=null?categorys.get(0):null;
	}

	@Override
	public ProductCategory getMostParentByCategoryId(String categoryId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", categoryId);
		return (ProductCategory) this.getObjectValue("getMostParentByCategoryId", map);
	}

	@Override
	public List<ProductCategory> queryCategorysByCategoryIds(List categoryIds)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("categoryIds", categoryIds);
		return this.get("queryCategorysByCategoryIds", map);
	}

	@Override
	public List<ProductCategory> getCategoryByProduct(String id)
			throws Exception {
		return this.getObjectList("getCategoryByProduct", id);
	}


	@Override
	public List<Category4Mobile> querySubCategory(Map<String, Object> map)
			throws Exception {
		List<Category4Mobile> result = new ArrayList<Category4Mobile>();
		Object object = this.getObjects("querySubCategoryByParentId",map);
		if(Utils.isBlank(object)){
			return result;
		}
		for (Object obj : (List)object) {
			result.add((Category4Mobile)obj);
		}
		return result;
	}
	
	@Override
	public ProductCategory getProductCategory(String id)
			throws Exception {
		return this.getUnique("getProductCategory", id);
	}

}
