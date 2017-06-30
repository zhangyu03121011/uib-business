package com.uib.navigation.dao;

import java.util.List;

import com.uib.navigation.entity.Navigation;

/**
 * 导航管理
 * @author kevin
 *
 */
public interface NavigationDao {
	
	/**
	 * 根据位置获取导航信息
	 * @param position
	 * @return
	 * @throws Exception
	 */
	List<Navigation> getByPosition(Integer position ) throws Exception;
}
