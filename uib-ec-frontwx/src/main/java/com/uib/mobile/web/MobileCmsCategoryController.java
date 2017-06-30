package com.uib.mobile.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.uib.cms.entity.ArticleData;
import com.uib.cms.service.ArticleService;
import com.uib.common.enums.ExceptionEnum;
import com.uib.mobile.dto.ArticleDto;
import com.uib.mobile.dto.ReturnMsg;

@Controller
@RequestMapping("/mobile/CmsCategory")
public class MobileCmsCategoryController {
	private Logger logger = LoggerFactory.getLogger("rootLogger");

	@Autowired
	private ArticleService articleService;
	@Value("${systemPath}")
	private String systemPath;
	
	@Value("${articlePhonePath}")
	private String articlePhonePath;
	
	
	
	@RequestMapping("/listCmsHelp")
	@ResponseBody
	public ReturnMsg<List<ArticleDto>> listCmsHelp(String cmsCategoryNo ){
		ReturnMsg<List<ArticleDto>> returnMsg = new ReturnMsg<List<ArticleDto>>();
		try {
			logger.info("=======begin 手机APP调用listCmsHelp方法接口====================");
			logger.info("=======传参(栏目编号) cmsCategoryNo=" + cmsCategoryNo+ "=============");
			if (null == cmsCategoryNo) {
				logger.info("该栏目编号不存在:" + cmsCategoryNo);
				returnMsg.setMsg(ExceptionEnum.cmsCategoryNo_not_exist.getValue() + ":"
						+ cmsCategoryNo);
				returnMsg.setCode(ExceptionEnum.cmsCategoryNo_not_exist.getIndex());
				returnMsg.setStatus(false);
				return returnMsg;
			}
			List<Map<String,Object>> article = articleService.getCmsArticlesByCmsCategoryNo(cmsCategoryNo);
			if(article == null){
				logger.info("该栏目不存在:" + cmsCategoryNo);
				returnMsg.setMsg("该栏目不存在");
				returnMsg.setStatus(false);
				return returnMsg;
			}
			List<ArticleDto> articleDtos = new ArrayList<ArticleDto>();
			ArticleDto articleDto = new ArticleDto();
			for (Map<String, Object> map : article) {
				BeanUtils.copyProperties(articleDto, map);
				articleDto.setUrl(articlePhonePath+articleDto.getId());
				articleDtos.add(articleDto);
			}
			returnMsg.setData(articleDtos);
		} catch (Exception e) {
			returnMsg.setMsg(ExceptionEnum.business_logic_exception.getValue() + e.getMessage());
			returnMsg.setCode(ExceptionEnum.business_logic_exception.getIndex());
			returnMsg.setStatus(false);
			logger.error("查询帮助报错！", e);
		}
		logger.info("=======end listCmsHelp 手机APP调用listCmsHelp方法接口====================");
		return returnMsg;
	}
	
	@RequestMapping("/listCmsRegister")
	@ResponseBody
	public ReturnMsg<Object> listCmsRegister(String cmsCategoryNo ){
		ReturnMsg<Object> returnMsg = new ReturnMsg<Object>();
		try {
			logger.info("=======begin 手机APP调用listCmsRegister方法接口====================");
			logger.info("=======传参(栏目编号) cmsCategoryNo=" + cmsCategoryNo+ "=============");
			if (null == cmsCategoryNo) {
				logger.info("该栏目编号不存在:" + cmsCategoryNo);
				returnMsg.setMsg(ExceptionEnum.cmsCategoryNo_not_exist.getValue() + ":"
						+ cmsCategoryNo);
				returnMsg.setCode(ExceptionEnum.cmsCategoryNo_not_exist.getIndex());
				returnMsg.setStatus(false);
				return returnMsg;
			}
			Map<String,Object> article = articleService.getCmsArticleByCmsCategoryNo(cmsCategoryNo);
			ArticleDto articleDto = new ArticleDto();
			if(article == null){
				logger.info("该栏目不存在:" + cmsCategoryNo);
				returnMsg.setMsg("该栏目不存在");
				returnMsg.setStatus(false);
				return returnMsg;
			}
			BeanUtils.copyProperties(articleDto, article);
			articleDto.setUrl(articlePhonePath+articleDto.getId());
			returnMsg.setData(articleDto);
		} catch (Exception e) {
			returnMsg.setMsg(ExceptionEnum.business_logic_exception.getValue() + e.getMessage());
			returnMsg.setCode(ExceptionEnum.business_logic_exception.getIndex());
			returnMsg.setStatus(false);
			logger.error("查询注册协议报错！", e);
		}
		logger.info("=======end listCmsRegister 手机APP调用listCmsRegister方法接口====================");
		return returnMsg;
	}
	
	/**
	 * 显示帮助和注册协议
	 * 
	 * @param articleId
	 */
	@RequestMapping("/listProtocol")
	public String listProtocol(String articleId ,HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		try{
			ArticleData article = articleService.getArticleDataById(articleId);
			model.addAttribute("article", article);
		}catch(Exception e){
			e.printStackTrace();
			logger.info("显示协议出错");
		}
		return "/mobile/protocol";
	}

}
