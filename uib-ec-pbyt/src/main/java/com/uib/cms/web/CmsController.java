package com.uib.cms.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.uib.cms.entity.ArticleData;
import com.uib.cms.service.ArticleService;


@Controller
@RequestMapping("/cms")
public class CmsController {
	
	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(CmsController.class);
	
	@Value("/news")
	private String newsView;
	
	@Autowired
	private ArticleService articleService;
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String newsView(@PathVariable String id, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		try{
			ArticleData articleData =	articleService.getArticleDataById(id);
			request.setAttribute("articleData", articleData);
		} catch (Exception ex) {
			logger.error("newsView 出错 :" , ex );
			ex.printStackTrace();
		}
		return newsView;
	}
}
