package com.uib.ptyt.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.easypay.common.utils.UUIDGenerator;
import com.uib.common.web.Global;
import com.uib.ptyt.dao.SpecialDao;
import com.uib.ptyt.entity.Special;
import com.uib.ptyt.entity.SpecialMerchantRef;

@Service
public class SpecialService {
	
	@Autowired
	private SpecialDao specialDao;

	//@Override
	public List<Map<String, Object>> findSpecialByPage(String page) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int startSize = (Integer.parseInt(page) - 1) * 2;
		int pageSize = 2;
		map.put("startSize", startSize);
		map.put("pageSize", pageSize);
		map.put("imageUrl", Global.getConfig("upload.image.path"));
		return specialDao.findSpecialByPage(map);
	}
	
	public List<Map<String, Object>> findSpecialProd(String rankId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rankId", rankId);
		map.put("imageUrl", Global.getConfig("upload.image.path"));
		return specialDao.findSpecialProd(map);
	}
	/*根据专题ID查询专题产品列表
	 * 
	 */
	public List<Map<String, Object>> findProductById(String page,String specialId,String rankId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int startSize = (Integer.parseInt(page) - 1) * 4;
		int pageSize = 4;
		map.put("specialId", specialId);
		map.put("startSize", startSize);
		map.put("rankId", rankId);
		map.put("pageSize", pageSize);
		map.put("imageUrl", Global.getConfig("upload.image.path"));
		return specialDao.findProductById(map);
	}
	
	/*根据专题ID查询专题产品列表
	 * 
	 */
	public Map<String, Object> findSpecialById(String specialId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("specialId", specialId);
		map.put("imageUrl", Global.getConfig("upload.image.path"));
		return specialDao.findSpecialById(map);
	}
	
	/*根据userID查询我的专题信息
	 * 
	 */
	public List<Special> findSpecialUserId(String userId,String page) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int startSize = (Integer.parseInt(page) - 1) * 2;
		int pageSize = 2;
		map.put("startSize", startSize);
		map.put("pageSize", pageSize);
		map.put("userId", userId);
		map.put("imageUrl", Global.getConfig("upload.image.path"));
		return specialDao.findSpecialUserId(map);
	}
	
	/*批量添加我的专题
	 * 
	 */
	@Transactional(readOnly = false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void addMySpecials(String specialIds,String merchantId) throws Exception {
		List<SpecialMerchantRef> specialMerchantRefList = new ArrayList<SpecialMerchantRef>();
		String[] specialIdsArr = specialIds.split(",");
		for (String elementId : specialIdsArr) {
			SpecialMerchantRef specialMerchantRef = new SpecialMerchantRef();
			String id=UUIDGenerator.getUUID();
			specialMerchantRef.setId(id);
			specialMerchantRef.setSpecialId(elementId);
			specialMerchantRef.setMerchantId(merchantId);
			specialMerchantRef.setCreateDate(new Date());;
			specialMerchantRefList.add(specialMerchantRef);
		}
		//增加前把选择的数据选删除
		if(null!=specialIds){
			deleteMySpecials(specialIds,merchantId);
		}
		specialDao.addMySpecials(specialMerchantRefList);
	}
	
	/*批量删除我的专题
	 * 
	 */
	@Transactional(readOnly = false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void deleteMySpecials(String specialIds,String merchantId) throws Exception {
		/*List<SpecialMerchantRef> specialMerchantRefList = new ArrayList<SpecialMerchantRef>();
		String[] specialIdsArr = specialIds.split(",");
		for (String elementId : specialIdsArr) {
			SpecialMerchantRef specialMerchantRef = new SpecialMerchantRef();
			specialMerchantRef.setSpecialId(elementId);
			specialMerchantRef.setMerchantId(merchantId);
			specialMerchantRefList.add(specialMerchantRef);
		}*/
		
		Map<String, Object> map = new HashMap<String, Object>();
		String[] specialIdsArr = specialIds.split(",");
		List<String> specialIdList = new ArrayList<String>();
		for (String specialId : specialIdsArr) {
			specialIdList.add(specialId);
		}
		map.put("specialIdList", specialIdList);
		map.put("merchantId", merchantId);
		//specialDao.deleteMySpecials(specialMerchantRefList);
		specialDao.deleteMySpecials(map);
	}
}
