package com.uib.mobile.dao.impl;

import org.springframework.stereotype.Component;

import com.uib.core.dao.MyBatisDao;
import com.uib.mobile.dao.TbAttachmentDao;
import com.uib.mobile.dto.TbAttachment;
@Component
public class TbAttrachmentDaoImpl extends MyBatisDao<TbAttachment> implements TbAttachmentDao{

	@Override
	public void save(TbAttachment tbAttachment) throws Exception {
		this.save("save",tbAttachment);
		
	}

}
