package com.uib.ptyt.dao;

import java.util.List;
import java.util.Map;

import com.uib.common.annotation.MyBatisDao;
import com.uib.ptyt.entity.DrawalsRecordDto;

@MyBatisDao
public interface DrawalsRecordDao {

	/**
	 * 查询所有的提现信息(可选择分页)
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<DrawalsRecordDto> queryDrawalsRecord(Map<String, Object> map) throws Exception;

	/**
	 * 插入提现信息
	 * @param drawalsRecordDto
	 * @throws Exception
	 */
	public void insert(DrawalsRecordDto drawalsRecordDto) throws Exception;
	/**
	 * 查询已申请提现的信息
	 * @param memberId
	 * @return
	 * @throws Exception
	 */
	public List<DrawalsRecordDto> queryAlerayDra(String memberId) throws Exception;
	/**
	 * 更新提现信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public void update(Map<String,Object> map) throws Exception;
}
