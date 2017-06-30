/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.sys.dao;

import com.uib.ecmanager.common.persistence.CrudDao;
import com.uib.ecmanager.common.persistence.annotation.MyBatisDao;
import com.uib.ecmanager.modules.sys.entity.UserMerchantMap;

/**
 * 用户商户关联DAO接口
 * @author limy
 * @version 2015-08-25
 */
@MyBatisDao
public interface UserMerchantMapDao extends CrudDao<UserMerchantMap> {
	public UserMerchantMap getMapByMerchantNo(String merchantNo);
	public UserMerchantMap getMapByUserId(String userId);
}