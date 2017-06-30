/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.mem.dao;

import com.uib.ecmanager.common.persistence.CrudDao;
import com.uib.ecmanager.common.persistence.annotation.MyBatisDao;
import com.uib.ecmanager.modules.mem.entity.Deposit;

/**
 * 预存款DAO接口
 * @author limy
 * @version 2015-06-15
 */
@MyBatisDao
public interface DepositDao extends CrudDao<Deposit> {
	
}