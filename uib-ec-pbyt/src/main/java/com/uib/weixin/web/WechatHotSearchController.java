package com.uib.weixin.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.uib.cms.entity.HotSearch;
import com.uib.cms.service.HotSearchService;
import com.uib.common.enums.ExceptionEnum;
import com.uib.common.utils.Collections3;
import com.uib.common.utils.StringUtils;
import com.uib.common.utils.WebUtil;
import com.uib.mobile.dto.ReturnMsg;

/***
 * 热搜
 * @author 程健
 *
 */
@Controller
@RequestMapping("/wechat/hotSearch")
public class WechatHotSearchController {
	
	private Logger logger = LoggerFactory.getLogger(WechatHotSearchController.class);
	
	@Autowired
	private HotSearchService hotSearchService;
	
	/***
	 * 获取热搜关键词 列表
	 */
	@RequestMapping(value = "/getHotSearchList")
	@ResponseBody
	public ReturnMsg<Map<String,Object>> getHotSearchList(String count,HttpServletRequest request,HttpServletResponse response){
		ReturnMsg<Map<String,Object>> returnMsg = new ReturnMsg<Map<String,Object>>();
		try{
			Map<String,Object> result = new HashMap<String, Object>();
			//获取热门搜索
			List<HotSearch> hotSearchList = hotSearchService.getHotSearchList(StringUtils.isNotBlank(count) ? Integer.valueOf(count) : 5);
			result.put("hotSearchList", hotSearchList);
			//获取历史搜索
			String searchStr = WebUtil.getCookie(request, "searchStr");
			List<String> lastsearchList = new ArrayList<String>();
			
			//按追加时间降序排序
			if(StringUtils.isNotEmpty(searchStr)){
				String[] lastsearchArray = searchStr.split(",");
				for (String string : lastsearchArray) {
					lastsearchList.add(string);
				}
				Collections.sort(lastsearchList,Collections.reverseOrder());
				for (int i = 0; i < lastsearchList.size(); i++) {
					String lastsearchValue = lastsearchList.get(i);
					lastsearchList.set(i, lastsearchValue.substring(lastsearchValue.indexOf("#")+1, lastsearchValue.length()));
				}
				Collections3.removeDuplicateWithOrder(lastsearchList);
				if(lastsearchList.size() > 15) {
					lastsearchList = lastsearchList.subList(0, 15);
				}
				result.put("lastsearchArray", lastsearchList);
			}else{
				result.put("lastsearchArray", null);
			}
			returnMsg.setStatus(true);
			returnMsg.setData(result);
		}catch(Exception e){
			logger.error("获取热搜关键词 列表出错", e);
			
			returnMsg.setCode(ExceptionEnum.system_error.getIndex());
			returnMsg.setMsg(ExceptionEnum.system_error.getValue());
			returnMsg.setStatus(false);
		}
		return returnMsg;
	}
	
	
	
	/***
	 * cookie清除
	 */
	@RequestMapping(value = "/cookieRemove")
	@ResponseBody
	public void cookieRemove(HttpServletRequest request,HttpServletResponse response){
		WebUtil.removeCookie(request,response,"searchStr") ;
	}
	
}
