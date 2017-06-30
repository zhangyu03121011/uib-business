package com.uib.mobile.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.uib.cms.entity.HotSearch;
import com.uib.cms.service.HotSearchService;
import com.uib.common.enums.ExceptionEnum;
import com.uib.common.utils.StringUtils;
import com.uib.mobile.dto.ReturnMsg;

/***
 * 热搜
 * @author zfan
 *
 */
@Controller
@RequestMapping("/mobile/hotSearch")
public class MobileHotSearchController {
	
	private Logger logger = LoggerFactory.getLogger(MobileHotSearchController.class);
	
	@Autowired
	private HotSearchService hotSearchService;
	
	/***
	 * 获取热搜关键词 列表
	 */
	@RequestMapping(value = "/getHotSearchList")
	@ResponseBody
	public ReturnMsg<List<HotSearch>> getHotSearchList(String count){
		ReturnMsg<List<HotSearch>> returnMsg = new ReturnMsg<List<HotSearch>>();
		try{
			List<HotSearch> hotSearchList = hotSearchService.getHotSearchList(StringUtils.isNotBlank(count) ? Integer.valueOf(count) : 5);
			returnMsg.setStatus(true);
			returnMsg.setData(hotSearchList);
		}catch(Exception e){
			logger.error("获取热搜关键词 列表出错", e);
			
			returnMsg.setCode(ExceptionEnum.system_error.getIndex());
			returnMsg.setMsg(ExceptionEnum.system_error.getValue());
			returnMsg.setStatus(false);
		}
		return returnMsg;
	}
	
	
}
