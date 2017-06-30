/**
 * Copyright &copy; 2014-2016  All rights reserved.
 *
 * Licensed under the 深圳嘉宝易汇通科技发展有限公司 License, Version 1.0 (the "License");
 * 
 */
package com.uib.member.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.easypay.core.dao.MyBatisDao;
import com.uib.member.dao.MemberLoginStatusDao;
import com.uib.member.entity.MemberLoginStatus;

/**
 * @ClassName: MemberLoginStatusDaoImpl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author sl
 * @date 2015年9月24日 下午5:26:10
 */
@Component
public class MemberLoginStatusDaoImpl extends MyBatisDao<MemberLoginStatus>implements MemberLoginStatusDao {

	public Integer findExistsBySessionId(String sessionId) {
		Integer object = (Integer) super.getObjectValue("findExistsBySessionId", sessionId);
		return object;
	}
	
	public	void saveMemberLoginStatus(MemberLoginStatus memberLoginStatus) throws Exception{
		this.save("saveMemberLoginStatus", memberLoginStatus);
	}

	@Override
	public MemberLoginStatus findByMemberIdAndSessionId(String memberId, String sessionId) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("memberId", memberId);
		paramMap.put("sessionId", sessionId);
		return this.getUnique("findByMemberIdAndSessionId", paramMap);
	}

	@Override
	public MemberLoginStatus findByMemberId(String memberId) throws Exception {
	   return this.getUnique("findByMemberId", memberId);
	}
}
