/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.order.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.uib.ecmanager.common.persistence.CrudDao;
import com.uib.ecmanager.common.persistence.annotation.MyBatisDao;
import com.uib.ecmanager.modules.order.entity.OrderReturnsRef;
import com.uib.ecmanager.modules.sys.entity.Area;

/**
 * 退货单DAO接口
 * @author limy
 * @version 2015-06-08
 */
@MyBatisDao
public interface OrderReturnsRefDao extends CrudDao<OrderReturnsRef> {
	public List<OrderReturnsRef> findReturnsRefByOrderNo(String orderNo);
}