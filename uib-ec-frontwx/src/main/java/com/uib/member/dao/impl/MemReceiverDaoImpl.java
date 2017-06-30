package com.uib.member.dao.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.uib.core.dao.MyBatisDao;
import com.uib.core.exception.GenericException;
import com.uib.member.dao.MemReceiverDao;
import com.uib.member.entity.MemReceiver;

@Component
public class MemReceiverDaoImpl extends MyBatisDao<MemReceiver> implements MemReceiverDao{


	@Override
	public Integer getMemReceiverByAddrCount(String memberId) throws GenericException {
		return (Integer) this.getObjectValue("getMemReceiverByAddrCount", memberId);
	}

	@Override
	public List<MemReceiver> getMemReceiverByAddress(String memberId) {
		return this.get("getMemReceiverByAddress", memberId);
	}

	@Override
	public MemReceiver getMemReceiverById(String id) throws Exception {
		return this.getUnique("getMemReceiverById", id);
	}

	
	@Override
	public void update(MemReceiver memReceiver) {
		this.update("update",memReceiver);
		
	}

	@Override
	public void delete(String id) throws Exception {
		this.update("delete",id);
		
	}
	@Override
	public void save(MemReceiver memReceiver) throws Exception {
		this.save("save",memReceiver);
		
	}

	@Override
	public void updateIsDefault(MemReceiver memReceiver) throws Exception {
		this.update("updateIsDefault", memReceiver);
	}

	@Override
	public MemReceiver getDefaultMemReceiverByMemberId(String memberId) throws Exception {
		return this.getUnique("getDefaultMemReceiverByMemberId", memberId);
	}

	@Override
	public MemReceiver getLastOrderReceiverByMemberId(String userName)
			throws Exception {
		return this.getUnique("getLastOrderReceiverByMemberId", userName);
	}
}
