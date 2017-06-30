/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.mem.dao;

import com.uib.ecmanager.common.persistence.CrudDao;
import com.uib.ecmanager.common.persistence.annotation.MyBatisDao;
import com.uib.ecmanager.modules.mem.entity.MemMerchant;

/**
 * 会员卖家信息DAO接口
 * @author kevin
 * @version 2015-05-28
 */
@MyBatisDao
public interface MemMerchantDao extends CrudDao<MemMerchant> {
	
}