package com.uib.ptyt.service;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.easypay.common.utils.UUIDGenerator;
import com.uib.member.entity.MemMember;
import com.uib.mobile.dto.RecommendProductLog;
import com.uib.order.entity.OrderTable;
import com.uib.ptyt.constants.WechatConstant;
import com.uib.ptyt.dao.MemMemberDao;
import com.uib.ptyt.entity.MemMerchantDto;
import com.uib.ptyt.entity.StoreDto;
import com.uib.weixin.util.UserSession;

@Service
public class MemMemberService {
	private Logger logger = LoggerFactory.getLogger(MemMemberService.class);
	
	@Autowired
	private MemMemberDao memMemberDao;
	
	@Autowired
	private MemMerchantService memMerchantService;
	
	@Autowired
	private StoreService storeService;
	
	/**
	 * 根据openId查询会员信息
	 * @param openId
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> queryMemMember(String openId,String id) throws Exception{
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("openId", openId);
		map.put("id", id);
		return memMemberDao.queryMemMember(map);
	}
	/**
	 * 根据手机号查询会员信息
	 * @param phone
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> queryMemMemberByPhone(String phone) throws Exception{
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("phone", phone);
		return memMemberDao.queryMemMember(map);
	}
	
	/**
	 * 根据id更新会员信息
	 * @param openId
	 * @throws Exception
	 */
	@Transactional(readOnly = false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void updateMemMember(String id,String username,String card,String phone,String approveFlag) throws Exception{
			//更新会员表
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("id", id);
			map.put("username", username);
			map.put("card", card);
			map.put("phone", phone);
			memMemberDao.updateMemMember(map);
			if(!("2".equals(approveFlag))){
				//往商户表插入一条数据
				MemMerchantDto memMerchantDto=new MemMerchantDto();
				String memMerchant_id=UUIDGenerator.getUUID();
				memMerchantDto.setId(memMerchant_id);
				memMerchantDto.setMerchantNo(UUIDGenerator.getUUID());
				memMerchantDto.setRegisterDate(new Date());
				memMerchantDto.setMemberId(id);
				Map<String,Object> map2=queryMemMember(null,id);
				String name=(String)map2.get("name");
				if(null==name || ""==name){
					memMerchantDto.setMerchantName("");
					memMerchantDto.setCreateBy("");
					memMerchantDto.setUpdateBy("");
				}else{
					memMerchantDto.setMerchantName(name);
					memMerchantDto.setCreateBy(name);
					memMerchantDto.setUpdateBy(name);
				}
				memMerchantDto.setPhone(phone);
				memMerchantDto.setContactName(username);
				memMerchantDto.setCreateDate(new Date());
				memMerchantDto.setUpdateDate(new Date());
				memMerchantDto.setDelFlag("0");
				memMerchantService.insertMemMerchant(memMerchantDto);
				UserSession.setSession(WechatConstant.MERCHANT_ID,memMerchant_id);
				//往店铺表插入一条数据
				//往店铺商户关联表插入一条数据
				StoreDto storeDto=new StoreDto();
				storeService.saveStore(storeDto, memMerchant_id);
			}
	}
	/**
	 * 根据id更新会员手机
	 * @param map
	 * @throws Exception
	 */
	@Transactional(readOnly = false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void updatePhone(String phone,String userType) throws Exception{
		//String id="1c366f084d704e27a3a6813d055df513";
		//更新会员表的手机号
		String id=(String) UserSession.getSession(WechatConstant.USER_ID);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("id", id);
		map.put("phone", phone);
		map.put("userType", userType);
		memMemberDao.updatePhone(map);
		//查询会员的userType
		Map<String,Object> user_map=queryMemMember(null,id);
		//如果用户为分销商，则更新商户表的手机号
		if("2".equals((String)user_map.get("userType"))){
			MemMerchantDto memMerchantDto=new MemMerchantDto();
			memMerchantDto.setPhone(phone);
			memMerchantDto.setMemberId(id);
			memMerchantService.updateMemMerchant(memMerchantDto);	
		}
	}
	
	/*微信关注生产一个用户
	 * 
	 */
	@Transactional(readOnly = false)
	public void createUser(String openId) throws Exception {
		MemMember member = new MemMember();
		member.setId(UUIDGenerator.getUUID());
		member.setOpenId(openId);
		member.setUserType("1");//普通消费者
		member.setRankId("1"); //等级为普通
		//member.setApproveFlag("0");//身份证等级为待审核
		member.setSessionId(UUIDGenerator.getUUID());
		member.setUsername(openId);
		// 微信号
		//member.setWeixinName(memberDto.getWeixinName());
		//memMemberDao.saveMember(member);
		memMemberDao.createUser(member);
		/*UserSession.setSession("userId", member.getId());
		UserSession.setSession("wxOpenId", openId);*/
		UserSession.setSession(WechatConstant.USER_ID, member.getId());
		UserSession.setSession(WechatConstant.OPEN_ID, openId);
		UserSession.setSession(WechatConstant.RANK_ID, member.getRankId());
	}
	
	/**
	 * 根据openId查询会员ID
	 * @param openId
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> getuserIdByopenId(String openId) throws Exception{
		return memMemberDao.getuserIdByopenId(openId);
	}
	
	/**
	 * 根据userId查询商户ID
	 * @param openId
	 * @return
	 * @throws Exception
	 */
	public String getmerchartIdByUserId(String userId) throws Exception{
		return memMemberDao.getmerchartIdByUserId(userId);
	}
	
	
	/*
	 * 更新用户头像，性别，昵称
	 */
	@Transactional(readOnly = false)
	public void updateUserHead(Map<String,Object> map) throws Exception {
		MemMember member = new MemMember();
		member.setGender(map.get("sex").toString());
		/*member.setUsername(map.get("nickname").toString());
		member.setName(map.get("nickname").toString());*/
		member.setAvatar(map.get("headimgurl").toString());
		member.setOpenId(map.get("openId").toString());
		memMemberDao.updateUserHead(member);
	}
	
	/*
	 * 更新用户昵称
	 */
	@Transactional(readOnly = false)
	public void updateUserName(Map<String,Object> map) throws Exception {
		MemMember member = new MemMember();
		member.setOpenId(map.get("openId").toString());
		member.setUsername(map.get("nickname").toString());
		member.setName(map.get("nickname").toString());
		memMemberDao.updateUserName(member);
	}
	
	/*
	 * 微信获取用户名有特殊字符的，系统自动给分配一个用户名
	 */
	@Transactional(readOnly = false)
	public void updateUserName2(String openId,String userName) throws Exception {
		MemMember member = new MemMember();
		member.setOpenId(openId);
		member.setUsername(userName);
		member.setName(userName);
		memMemberDao.updateUserName(member);
	}
	
	
	/**
	 * 根据memberID批量查询出被推荐人用户信息
	 * @param memberID
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> getMemberByIds(List<RecommendProductLog> settleList) throws Exception{
		return memMemberDao.getMemberByIds(settleList);
	}
	
	/**
	 * 根据memberID批量查询出推荐人用户信息
	 * @param memberID
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> getRecommendMemberByIds(List<RecommendProductLog> settleList) throws Exception{
		return memMemberDao.getRecommendMemberByIds(settleList);
	}
	
	/**
	 * 根据username批量查询出用户信息
	 * @param memberID
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> getMemByUsernameList(List<OrderTable> orderList) throws Exception{
		return memMemberDao.getMemByUsernameList(orderList);
	}
	
	/**
	 * 获取编码
	 * @throws Exception
	 */
	public int getCode() throws Exception{
		return memMemberDao.getCode();
	}
	
	/**
	 * 更新编码
	 * @throws Exception
	 */
	public void updateCode(int code) throws Exception{
		 memMemberDao.updateCode(code);
	}
}
