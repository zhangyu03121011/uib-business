/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.product.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uib.ecmanager.common.persistence.Page;
import com.uib.ecmanager.common.service.CrudService;
import com.uib.ecmanager.common.utils.StringUtils;
import com.uib.ecmanager.modules.product.dao.ParameterGroupDao;
import com.uib.ecmanager.modules.product.dao.ProductParameterDao;
import com.uib.ecmanager.modules.product.entity.ParameterGroup;
import com.uib.ecmanager.modules.product.entity.ProductParameter;

/**
 * 商品参数组Service
 * 
 * @author gaven
 * @version 2015-05-28
 */
@Service
@Transactional(readOnly = true)
public class ParameterGroupService extends
		CrudService<ParameterGroupDao, ParameterGroup> {

	@Autowired
	private ProductParameterDao productParameterDao;

	public ParameterGroup get(String id) {
		ParameterGroup parameterGroup = super.get(id);
		parameterGroup.setProductParameterList(productParameterDao
				.findList(new ProductParameter(parameterGroup)));
		return parameterGroup;
	}

	public List<ParameterGroup> findList(ParameterGroup parameterGroup) {
		List<ParameterGroup> groups = super.findList(parameterGroup);
		for (ParameterGroup group : groups) {
			group.setProductParameterList(productParameterDao
					.findList(new ProductParameter(group)));
		}
		return groups;
	}

	public Page<ParameterGroup> findPage(Page<ParameterGroup> page,
			ParameterGroup parameterGroup) {
		return super.findPage(page, parameterGroup);
	}

	@Transactional(readOnly = false)
	public void save(ParameterGroup parameterGroup) {
		super.save(parameterGroup);
		for (ProductParameter productParameter : parameterGroup
				.getProductParameterList()) {
			if (productParameter.getId() == null) {
				continue;
			}
			if (ProductParameter.DEL_FLAG_NORMAL.equals(productParameter
					.getDelFlag())) {
				if (StringUtils.isBlank(productParameter.getId())) {
					productParameter.setParameterGroup(parameterGroup);
					productParameter.preInsert();
					productParameterDao.insert(productParameter);
				} else {
					productParameter.preUpdate();
					productParameterDao.update(productParameter);
				}
			} else {
				productParameterDao.delete(productParameter);
			}
		}
	}

	@Transactional(readOnly = false)
	public void delete(ParameterGroup parameterGroup) {
		super.delete(parameterGroup);
		productParameterDao.delete(new ProductParameter(parameterGroup));
	}

}