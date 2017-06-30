package com.uib.navigation.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.uib.navigation.dao.NavigationDao;
import com.uib.navigation.entity.Navigation;
import com.uib.navigation.service.NavigationService;

@Component
public class NavigationServiceImpl implements NavigationService{

	@Autowired
	private NavigationDao navigationDao;
	
	@Override
	public List<Navigation> getByPosition(Integer position) throws Exception {
		return navigationDao.getByPosition(position);
	}

}
