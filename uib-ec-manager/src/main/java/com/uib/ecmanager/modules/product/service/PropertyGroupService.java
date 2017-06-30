/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.product.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uib.common.utils.UUIDGenerator;
import com.uib.ecmanager.common.mapper.JsonMapper;
import com.uib.ecmanager.common.persistence.Page;
import com.uib.ecmanager.common.service.CrudService;
import com.uib.ecmanager.common.utils.StringUtils;
import com.uib.ecmanager.modules.product.dao.ProductPropertyDao;
import com.uib.ecmanager.modules.product.dao.PropertyGroupDao;
import com.uib.ecmanager.modules.product.entity.ProductProperty;
import com.uib.ecmanager.modules.product.entity.PropertyGroup;
import com.uib.ecmanager.modules.sys.utils.Utils;

/**
 * 商品属性Service
 * 
 * @author gaven
 * @version 2015-06-04
 */
@Service
@Transactional(readOnly = true)
public class PropertyGroupService extends CrudService<PropertyGroupDao, PropertyGroup> {

	@Autowired
	private ProductPropertyDao productPropertyDao;

	public PropertyGroup get(String id) {
		PropertyGroup propertyGroup = super.get(id);
		propertyGroup.setProductPropertyList(productPropertyDao.findList(new ProductProperty(propertyGroup)));
		return propertyGroup;
	}

	public List<PropertyGroup> findList(PropertyGroup propertyGroup) {
		List<PropertyGroup> list = super.findList(propertyGroup);
		for (PropertyGroup group : list) {
			group.setProductPropertyList(get(group.getId()).getProductPropertyList());
		}
		return list;
	}

	public List<PropertyGroup> findPropertyGroupsByParentCategoryId(String categoryId,String parentId,Boolean attachDetail) {
		List<PropertyGroup> list = dao.queryParentAddPropertyGroups(categoryId,parentId);
//		if(Boolean.TRUE.equals(attachDetail)){
//			for (PropertyGroup group : list) {
//				group.setProductPropertyList(get(group.getId()).getProductPropertyList());
//			}
//		}
		System.out.println(JsonMapper.toJsonString(list));
		return list;
	}
	
	public List<PropertyGroup> findPropertyGroupsByCategoryId(String categoryId,Boolean attachDetail){
		List<PropertyGroup> list = dao.queryPropertyGroupsByCategoryId(categoryId);
//		if(Boolean.TRUE.equals(attachDetail)){
//			for (PropertyGroup group : list) {
//				group.setProductPropertyList(get(group.getId()).getProductPropertyList());
//			}
//		}
		return list;
	}

	public Page<PropertyGroup> findPage(Page<PropertyGroup> page, PropertyGroup propertyGroup) {
		return super.findPage(page, propertyGroup);
	}

	@Transactional(readOnly = false)
	public void save(PropertyGroup propertyGroup) {
		super.save(propertyGroup);
		for (ProductProperty productProperty : propertyGroup.getProductPropertyList()) {
			if (productProperty.getId() == null) {
				continue;
			}
			if (ProductProperty.DEL_FLAG_NORMAL.equals(productProperty.getDelFlag())) {
				if (StringUtils.isBlank(productProperty.getId())) {
					productProperty.setMerchantNo(propertyGroup.getMerchantNo());
					productProperty.setPropertyGroup(propertyGroup);
					productProperty.preInsert();
					productPropertyDao.insert(productProperty);
				}
			} else {
				productPropertyDao.delete(productProperty);
			}
		}
	}

	@Transactional(readOnly = false)
	public void update(PropertyGroup propertyGroup) {
		super.update(propertyGroup);
		for (ProductProperty productProperty : propertyGroup.getProductPropertyList()) {
			if (productProperty.getMerchantNo() == null) {
				productProperty.setMerchantNo(propertyGroup.getMerchantNo());
			}
			if (StringUtils.isBlank(productProperty.getId())) {
				productProperty.setId(UUIDGenerator.getUUID());
				productProperty.setPropertyGroup(propertyGroup);
				productProperty.preInsert();
				productPropertyDao.insert(productProperty);
				continue;
			}
			if (ProductProperty.DEL_FLAG_NORMAL.equals(productProperty.getDelFlag())) {

				productProperty.preUpdate();
				productPropertyDao.update(productProperty);

			} else {
				productPropertyDao.delete(productProperty);
			}
		}
	}

	@Transactional(readOnly = false)
	public void delete(PropertyGroup propertyGroup) {
		super.delete(propertyGroup);
		productPropertyDao.delete(new ProductProperty(propertyGroup));
	}

}