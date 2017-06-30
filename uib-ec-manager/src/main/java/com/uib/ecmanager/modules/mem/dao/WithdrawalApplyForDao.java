/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.mem.dao;

import com.uib.ecmanager.common.persistence.CrudDao;
import com.uib.ecmanager.common.persistence.annotation.MyBatisDao;
import com.uib.ecmanager.modules.mem.entity.WithdrawalApplyFor;

/**
 * 提现管理表DAO接口
 * @author luogc
 * @version 2016-07-28
 */
@MyBatisDao
public interface WithdrawalApplyForDao extends CrudDao<WithdrawalApplyFor> {
	
}