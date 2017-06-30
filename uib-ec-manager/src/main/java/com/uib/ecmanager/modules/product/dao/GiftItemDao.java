/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.product.dao;

import com.uib.ecmanager.common.persistence.CrudDao;
import com.uib.ecmanager.common.persistence.annotation.MyBatisDao;
import com.uib.ecmanager.modules.product.entity.GiftItem;

/**
 * 赠品DAO接口
 * @author gaven
 * @version 2015-06-09
 */
@MyBatisDao
public interface GiftItemDao extends CrudDao<GiftItem> {
	
}