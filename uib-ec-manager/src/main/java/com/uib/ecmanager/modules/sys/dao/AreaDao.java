/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.uib.ecmanager.common.persistence.TreeDao;
import com.uib.ecmanager.common.persistence.annotation.MyBatisDao;
import com.uib.ecmanager.modules.sys.entity.Area;
import com.uib.ecmanager.modules.sys.entity.Dict;
import com.uib.ecmanager.modules.sys.entity.Menu;

/**
 * 区域DAO接口
 * @author ThinkGem
 * @version 2014-05-16
 */
@MyBatisDao
public interface AreaDao extends TreeDao<Area> {

	public List<Area> findAreasByParentId(@Param("parentId") String parentId);
	public List<Area> ProductfindAllList(Area area);
}
