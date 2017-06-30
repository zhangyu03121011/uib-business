package com.uib.mobile.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uib.mobile.dao.TbAttachmentDao;
import com.uib.mobile.dto.TbAttachment;
import com.uib.mobile.service.TbAttachmentService;
@Service
public class TbAttachmentServiceImpl implements TbAttachmentService{

	@Autowired
	private TbAttachmentDao tbAttachmentDao;
	
	@Override
	public void save(TbAttachment tbAttachment) throws Exception {
		this.tbAttachmentDao.save(tbAttachment);
	}

}
