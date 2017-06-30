package com.uib.mobile.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.uib.core.dao.MyBatisDao;
import com.uib.mobile.dao.ComplainApplicationDao;
import com.uib.mobile.dto.ComplaintApplicationDto;

/**
 * @Todo 投诉申请
 * @date 2015年11月10日
 * @author luoxf
 */
@Component
public class ComplainApplicationDaoImpl extends MyBatisDao<ComplaintApplicationDto> implements ComplainApplicationDao{

	@Override
	public void save(ComplaintApplicationDto complaint) throws Exception {
		this.save("insert", complaint);
		
	}

	@Override
	public List<ComplaintApplicationDto> complainRecords(String memberId) throws Exception {		
		return this.get("complainRecords", memberId);
	}

	@Override
	public void deleteComplainRecord(String id) throws Exception {
		this.update("deleteComplainRecord",id);
		
	}

	@Override
	public ComplaintApplicationDto getComplaintApplicationById(String id) throws Exception {
		return this.getUnique("getComplaintApplicationById", id);
	}

	@Override
	public List<ComplaintApplicationDto> findcomplainRecords(Map<String, Object> map)
			throws Exception {
		// TODO Auto-generated method stub
		return super.getObjectList("findcomplainRecords", map);
	}

}
