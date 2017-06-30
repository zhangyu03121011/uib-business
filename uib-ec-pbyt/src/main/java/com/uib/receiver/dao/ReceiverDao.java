package com.uib.receiver.dao;

import com.uib.member.entity.MemReceiver;

public interface ReceiverDao {
	void update(String id) throws Exception;

	void delete(String id)throws Exception;

	void save(MemReceiver memReceiver) throws Exception;
}
