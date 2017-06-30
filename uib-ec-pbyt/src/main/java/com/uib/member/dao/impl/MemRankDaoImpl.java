package com.uib.member.dao.impl;

import org.springframework.stereotype.Component;

import com.uib.core.dao.MyBatisDao;
import com.uib.member.dao.MemRankDao;
import com.uib.member.entity.MemRank;

@Component
public class MemRankDaoImpl extends MyBatisDao<MemRank> implements MemRankDao{

	@Override
	public void queryMemRankByUserName(String userName) throws Exception {
	}

}
