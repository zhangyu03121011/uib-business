package com.uib.mobile.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.easypay.common.utils.UUIDGenerator;
import com.uib.common.web.Global;
import com.uib.member.entity.MemMember;
import com.uib.member.service.MemMemberService;
import com.uib.mobile.dao.ComplainApplicationDao;
import com.uib.mobile.dao.RecommendProductLogDao;
import com.uib.mobile.dto.ComplaintApplicationDto;
import com.uib.mobile.dto.TbAttachment;
import com.uib.mobile.dto.UserComplaintAttrMap;
import com.uib.mobile.service.ComplainApplicationService;
import com.uib.serviceUtils.Utils;

/**
 * @Todo 投诉申请
 * @date 2015年11月14日
 * @author luoxf
 */
@Service
public class ComplainApplicationServiceImpl implements ComplainApplicationService{
	@Autowired
	private ComplainApplicationDao complainApplicationdao;
	
	@Autowired
	private MemMemberService memMemberService;

	/**
	 * 投诉申请
	 */
	@Override
	//@Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor=Exception.class)
	public void save(ComplaintApplicationDto complaint) throws Exception {	
		complainApplicationdao.save(complaint);
	}

	@Override
	public List<ComplaintApplicationDto> complainRecords(String memberId)
			throws Exception {
		return complainApplicationdao.complainRecords(memberId);
	}

	@Override
	public void deleteComplainRecord(String id) throws Exception {
		complainApplicationdao.deleteComplainRecord(id);	
	}

	@Override
	public ComplaintApplicationDto getComplaintApplicationById(String id)
			throws Exception {
		return complainApplicationdao.getComplaintApplicationById(id);
	}   

	@Override
	public List<ComplaintApplicationDto> findcomplainRecords(String memberId)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
//		int startSize = (Integer.parseInt(page) - 1) * 5;
//		int pageSize = 5;
//		map.put("startSize", startSize);
//		map.put("pageSize", pageSize);
//		map.put("imageUrl", Global.getConfig("upload.image.path"));
		map.put("memberId", memberId);
		return complainApplicationdao.findcomplainRecords(map);
	}
	


}
