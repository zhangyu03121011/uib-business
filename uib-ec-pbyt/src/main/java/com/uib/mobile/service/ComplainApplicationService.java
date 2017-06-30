package com.uib.mobile.service;

import java.util.List;
import java.util.Map;

import com.uib.mobile.dto.ComplaintApplicationDto;

/**
 * @Todo 投诉申请
 * @date 2015年11月14日
 * @author luoxf
 */
public interface ComplainApplicationService {

	/**
	 * 投诉申请
	 * @param sessionId
	 * @param feedbackType
	 * @param describe
	 * @param images
	 * @throws Exception
	 */
	public void save(ComplaintApplicationDto complaint)throws Exception;
	
	/**
	 * 查寻投诉记录
	 * @param memberId
	 * @return
	 * @throws Exception
	 */
	public List<ComplaintApplicationDto> complainRecords(String memberId) throws Exception;
	
	/**
	 * 删除投诉记录
	 * @param id
	 * @throws Exception
	 */
	public void deleteComplainRecord(String id)throws Exception;
	
	
	/**
	 * 根据id查找投诉记录
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public ComplaintApplicationDto getComplaintApplicationById (String id)throws Exception;
	
	/**
	 * 分页查询投诉记录
	 * @param page
	 * @author chengw
	 * @return
	 */
	public List<ComplaintApplicationDto> findcomplainRecords(String memberId) throws Exception;
}
