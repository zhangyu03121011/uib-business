/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.product.dao;

import com.uib.ecmanager.common.persistence.CrudDao;
import com.uib.ecmanager.common.persistence.annotation.MyBatisDao;
import com.uib.ecmanager.modules.product.entity.Special;

/**
 * 专题信息DAO接口
 * @author limy
 * @version 2016-07-14
 */
@MyBatisDao
public interface SpecialDao extends CrudDao<Special> {
	public Integer getbysort(String sort);
	public void updateFlag(String id);
}