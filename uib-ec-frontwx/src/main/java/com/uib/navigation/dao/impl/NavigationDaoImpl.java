package com.uib.navigation.dao.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.uib.core.dao.MyBatisDao;
import com.uib.navigation.dao.NavigationDao;
import com.uib.navigation.entity.Navigation;

@Component
public class NavigationDaoImpl extends MyBatisDao<Navigation> implements NavigationDao{

	@Override
	public List<Navigation> getByPosition(Integer position) throws Exception {
		return this.get("getByPosition", position);
	}


}
