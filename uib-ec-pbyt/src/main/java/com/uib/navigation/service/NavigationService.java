package com.uib.navigation.service;

import java.util.List;

import com.uib.navigation.entity.Navigation;

public interface NavigationService {
	
	/**
	 * 根据位置获取导航信息
	 * @param position
	 * @return
	 * @throws Exception
	 */
	List<Navigation> getByPosition(Integer position ) throws Exception;
}
