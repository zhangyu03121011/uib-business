package com.uib.receiver.service;

import com.uib.member.entity.MemReceiver;

public interface ReceiverService {

	void update(String id) throws Exception;

	void delete(String id)throws Exception;

	void save(MemReceiver memReceiver) throws Exception;
}
