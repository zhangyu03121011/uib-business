package com.uib.receiver.dao.impl;

import org.springframework.stereotype.Component;

import com.uib.core.dao.MyBatisDao;
import com.uib.member.entity.MemReceiver;
import com.uib.receiver.dao.ReceiverDao;

@Component
public class ReceiverDaoImpl extends MyBatisDao<MemReceiver> implements ReceiverDao {

	@Override
	public void update(String id) {
		this.update("update",id);
		
	}

	@Override
	public void delete(String id) throws Exception {
		this.update("delete",id);
		
	}
	@Override
	public void save(MemReceiver memReceiver) throws Exception {
		this.save("save",memReceiver);
		
	}

}
