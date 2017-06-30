package com.uib.mobile.dao;

import java.util.List;
import java.util.Map;

import com.uib.mobile.dto.ComplaintApplicationDto;


/**
 * @Todo 投诉申请
 * @date 2015年11月10日
 * @author luoxf
 * @param <complainApplicationDto>
 */
public interface  ComplainApplicationDao {

	/**
	 * 添加投诉申请
	 * @param log
	 * @throws Exception
	 */
	public void save(ComplaintApplicationDto complaint) throws Exception;
	/**
	 * 查询投诉记录
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
	 * @param map
	 * @return
	 * @throws Exception
	 */
	List<ComplaintApplicationDto> findcomplainRecords(Map<String, Object> map) throws Exception;
	
}
