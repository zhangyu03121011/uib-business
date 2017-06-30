package com.uib.ptyt.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uib.ptyt.dao.DrawalsRecordDao;
import com.uib.ptyt.entity.DrawalsRecordDto;

@Service
public class DrawalsRecordService {
	
	@Autowired
	private DrawalsRecordDao drawalsRecordDao;
	
	/**
	 * 查询提现信息根据指定状态(可选择分页)
	 * @param applyUserName
	 * @return
	 * @throws Exception
	 */
	public List<DrawalsRecordDto> queryDrawalsRecord(String flag,String memberId,String applyStatus,String page) throws Exception{
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("memberId", memberId);
		if(null!=flag && ""!=flag){
			map.put("flag", flag);
		}
		if(null!=applyStatus && ""!=applyStatus){
			map.put("applyStatus", applyStatus);
		}
		if (null!=page && ""!=page) {
			int startSize = (Integer.valueOf(page) - 1) * 7;
			int pageSize = 7;
			map.put("startSize", startSize);
			map.put("pageSize", pageSize);
		}
		return drawalsRecordDao.queryDrawalsRecord(map);
	}
	
	/**
	 * 插入提现信息
	 * @param drawalsRecordDto
	 * @throws Exception
	 */
	public void insert(DrawalsRecordDto drawalsRecordDto) throws Exception{
		drawalsRecordDao.insert(drawalsRecordDto);
		return;
	}
	
	/**
	 * 根据提现卡号查询提现信息
	 * @param
	 * @throws Exception
	 */
	public List<DrawalsRecordDto> queryDraByCardNo(String cardNo,String flag) throws Exception{
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("cardNo", cardNo);
		map.put("flag", flag);
		return drawalsRecordDao.queryDrawalsRecord(map);
	}
	/**
	 * 查出现登录用户默认的提现记录
	 * @param 
	 * @throws Exception
	 */
	public List<DrawalsRecordDto> queryDraDefault(String memberId) throws Exception{
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("memberId", memberId);
		map.put("isDefault", "1");
		return drawalsRecordDao.queryDrawalsRecord(map);
	}
	/**
	 * 查询已申请提现的信息
	 * @param memberId
	 * @return
	 * @throws Exception
	 */
	public List<DrawalsRecordDto> queryAlerayDra(String memberId) throws Exception{
		return drawalsRecordDao.queryAlerayDra(memberId);
	}
	/**
	 * 查询用户已保存的银行卡信息
	 * @param memberId
	 * @param flag
	 * @return
	 * @throws Exception
	 */
	public List<DrawalsRecordDto> querySaveDra(String memberId,String flag) throws Exception{
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("memberId", memberId);
		map.put("flag", flag);
		return drawalsRecordDao.queryDrawalsRecord(map);
	}
	/**
	 * 更新提现信息
	 * @param memberId
	 * @param isDefault
	 * @param cardNo
	 * @return
	 * @throws Exception
	 */
	public void update(String memberId,String isDefault,String cardNo) throws Exception{
		Map<String,Object> map=new HashMap<String,Object>();
		if(null!=memberId && !"".equals(memberId)){
			map.put("memberId", memberId);
		}
		if(null!=isDefault && !"".equals(isDefault)){
			map.put("isDefault", isDefault);
		}
		if(null!=cardNo && !"".equals(cardNo)){
			map.put("cardNo", cardNo);
		}
		drawalsRecordDao.update(map);
		return;
	}
}
