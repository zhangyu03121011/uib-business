package com.uib.receiver.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.uib.member.entity.MemReceiver;
import com.uib.receiver.dao.ReceiverDao;
import com.uib.receiver.service.ReceiverService;



@Component
public class ReceiverServiceImpl implements  ReceiverService{
	@Autowired
	private ReceiverDao memReceiverDao;

	@Override
	public void delete(String id) throws Exception{
		// TODO Auto-generated method stub
		
		System.out.println("-----------------"+id);
		memReceiverDao.delete(id);
		
	}
	@Override
	public void update(String id) throws Exception {
		// TODO Auto-generated method stub
		memReceiverDao.update(id);
	}
	@Override
	public void save(MemReceiver memReceiver) throws Exception {
		// TODO Auto-generated method stub
		memReceiverDao.save(memReceiver);
	}

	

	

	

}
