/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.product.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uib.ecmanager.common.service.TreeService;
import com.uib.ecmanager.common.utils.StringUtils;
import com.uib.ecmanager.modules.product.dao.BrandDao;
import com.uib.ecmanager.modules.product.dao.ParameterGroupDao;
import com.uib.ecmanager.modules.product.dao.ProductCategoryDao;
import com.uib.ecmanager.modules.product.dao.ProductDao;
import com.uib.ecmanager.modules.product.dao.PropertyGroupDao;
import com.uib.ecmanager.modules.product.dao.SpecificationGroupDao;
import com.uib.ecmanager.modules.product.entity.ParamBean;
import com.uib.ecmanager.modules.product.entity.ProductCategory;
import com.uib.ecmanager.modules.sys.utils.Utils;

/**
 * 商品分类Service
 * 
 * @author kevin
 * @version 2015-06-12
 */
@Service
@Transactional(readOnly = true)
public class ProductCategoryService extends TreeService<ProductCategoryDao, ProductCategory> {
	@Autowired
	private BrandDao brandDao;
	@Autowired
	private ParameterGroupDao parameterGroupDao;
	@Autowired
	private PropertyGroupDao propertyGroupDao;
	@Autowired
	private SpecificationGroupDao specificationGroupDao;

	public ProductCategory get(String id) {
		return super.get(id);
	}

	public List<ProductCategory> findList(ProductCategory productCategory) {
		if (StringUtils.isNotBlank(productCategory.getParentIds())) {
			productCategory.setParentIds("," + productCategory.getParentIds() + ",");
		}
		return super.findList(productCategory);
	}

	@Transactional(readOnly = false)
	public void save(ProductCategory productCategory) {
		super.save(productCategory);
	}

	@Transactional(readOnly = false)
	public void save(ProductCategory productCategory, List<ParamBean<String[]>> propertyGroupIdBeans) {
		super.save(productCategory);
		if (Utils.isNotBlank(propertyGroupIdBeans)) {
			propertyGroupDao.saveCategoryPropertyGroupRef(productCategory.getId(), propertyGroupIdBeans);
		}
	}
	
	@Transactional(readOnly = false)
	public void update(ProductCategory productCategory, List<ParamBean<String[]>> propertyGroupIdBeans){
		super.update(productCategory);
		String categoryId = productCategory.getId();
		propertyGroupDao.deleteByCategoryId(categoryId);
		if (Utils.isNotBlank(propertyGroupIdBeans)) {
			propertyGroupDao.saveCategoryPropertyGroupRef(categoryId, propertyGroupIdBeans);
		}
	}

	@Transactional(readOnly = false)
	public void delete(ProductCategory productCategory) {
		String productCategoryId = productCategory.getId();
		// 逻辑删除商品及分类相关参数、属性、规格
		// productDao.batchDeleteByProductCategoryId(productCategoryId);
		brandDao.batchDeleteByProductCategoryId(productCategoryId);
		propertyGroupDao.batchDeleteByProductCategoryId(productCategoryId);
		parameterGroupDao.batchDeleteByProductCategoryId(productCategoryId);
		specificationGroupDao.batchDeleteByProductCategoryId(productCategoryId);
		propertyGroupDao.deleteByCategoryId(productCategoryId);
		// 物理删除分类
		super.delete(productCategory);
	}

	@Transactional(readOnly = true)
	public boolean isLastStage(String productCategoryId) {
		return super.dao.isLastStage(productCategoryId);
	}

}