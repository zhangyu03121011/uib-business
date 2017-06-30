package com.uib.member.dao;

import java.util.List;

import com.uib.member.entity.Deposit;
import com.uib.member.entity.MemMember;

/**
 * 预存额接口
 * @author hp
 */

public interface DepositDao {
	public List<Deposit> getAllDepositByUserName(MemMember member)throws Exception;
	
}
