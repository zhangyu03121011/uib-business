package com.uib.mobile.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.uib.common.enums.ExceptionEnum;
import com.uib.mobile.dao.CommissionDao;
import com.uib.mobile.dto.Commission;
import com.uib.mobile.dto.RecommendProductLog;
import com.uib.mobile.service.CommissionService;
import com.uib.mobile.service.RecommendProductLogService;
import com.uib.mobile.web.CommissionController;
import com.uib.order.service.OrderService;
import com.uib.ptyt.constants.WechatConstant;
import com.uib.weixin.util.UserSession;

@Component
public class CommissionServiceImpl implements CommissionService {
	private Logger logger = LoggerFactory.getLogger(CommissionServiceImpl.class);
	
	@Autowired
	private CommissionDao commissionDao;
	
	@Autowired
	private RecommendProductLogService recommendProductLogService;
	
	@Autowired
	private OrderService orderService;

	public List<Commission> findByName(Map<String, Object> params) throws Exception{
		return commissionDao.findByName(params);
	}

	@Override
	public void deleteCommissionById(String[] ids) throws Exception {
		 commissionDao.deleteCommissionById(ids);
	}

	@Override
	public List<Map<String,Object>> countCommission(String page)
			throws Exception {
		try{
			List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
			String memberId=(String) UserSession.getSession(WechatConstant.USER_ID);
			//String memberId="1c366f084d704e27a3a6813d055df513";
			if(memberId==null || memberId==""){
				Map<String,Object> map3=new HashMap<String,Object>();
				map3.put("flag",true);
				list.add(map3);
				return list;
			}
			//查询某个用户的所用佣金记录(1.平台购买 )
			List<Map<String,Object>> order_List=new ArrayList<Map<String,Object>>();
			order_List=orderService.getOrderCommissionByMemberId(memberId);
			//推荐表中所有的记录(2.分享给别人  3.别人分享给自己购买)
			List<Map<String,Object>> recommend_List=new ArrayList<Map<String,Object>>();
			recommend_List=recommendProductLogService.queryRecommendDetail(memberId, page);
			//按照时间desc,并且包装成一个list
			if(!(order_List.size()==0) || !(recommend_List.size()==0)){
				for(Map<String,Object> map1:order_List){
					list.add(map1);
				}
				for(Map<String,Object> map2:recommend_List){
					list.add(map2);
				}
				//将list进行时间降序
				//给数组排序
				Comparator<Map<String,Object>> comparator = new Comparator<Map<String,Object>>(){  
					   public int  compare(Map<String,Object> r1, Map<String,Object> r2) {
						   try {
							   
						       Date dt1 =new SimpleDateFormat("yyyy-MM-dd").parse((String)r1.get("createTime"));
							   Date dt2 = new SimpleDateFormat("yyyy-MM-dd").parse((String)r2.get("createTime"));
							   if (dt1.getTime() > dt2.getTime()) {
							       return -1;
							   } else if (dt1.getTime() < dt2.getTime()) {
							       return 1;
							   } else {
							       return 0;
							   }
							   } catch (Exception e) {
							       e.printStackTrace();
							   }
							   return 0; 
					   }  
		        };  
		        Collections.sort(list,comparator);
			}else{
				Map<String,Object> map=new HashMap<String,Object>();
				map.put("flag", true);
				list.add(map);
			}
			return list;
		}catch (Exception e) {
			e.fillInStackTrace();
			logger.error("微信后台：查询某个用户的所用佣金记录失败!", e);
			return null;
		}
	}
}
