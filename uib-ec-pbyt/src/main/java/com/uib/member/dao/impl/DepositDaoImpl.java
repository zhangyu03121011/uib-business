package com.uib.member.dao.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.uib.core.dao.MyBatisDao;
import com.uib.member.dao.DepositDao;
import com.uib.member.entity.Deposit;
import com.uib.member.entity.MemMember;

@Component
public class DepositDaoImpl extends MyBatisDao<Deposit> implements DepositDao{

	@Override
	public List<Deposit> getAllDepositByUserName(MemMember member)
			throws Exception {
		// TODO Auto-generated method stub
		return this.getObjectList("getAllDepositByUserName", member);
	}

}
