/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/easypay_ec">easypay_ec</a> All rights reserved.
 */
package com.uib.member.dao;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.uib.member.entity.Area;



/**
 * 地区表DAO接口
 */
public interface AreaDao  {

	public List<Area> findAreasByParentId(String parentId);
}