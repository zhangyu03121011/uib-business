package com.uib.ecmanager.modules.product.web;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.uib.ecmanager.modules.product.entity.ProductComment;
import com.uib.ecmanager.modules.product.service.ProductCommentService;
import com.uib.ecmanager.modules.sys.utils.Utils;

/**
 * 商品评论Controller
 * 
 * @author gaven
 * @version 2015-10-22
 */
@RequestMapping("${adminPath}/product/comment")
@Controller
public class ProductCommentController {
	/**
	 * 日志记录
	 */
	private Logger log = LoggerFactory.getLogger("rootLogger");
	@Autowired
	private ProductCommentService productCommentService;

	/**
	 * 根据条件查询商品评论
	 * 
	 * @param paramMap
	 * @return
	 */
	@RequestMapping("/query")
	public String queryComment(String productId, Model model) {
		try {
			if (Utils.isNotBlank(productId)) {
				List<ProductComment> comments = productCommentService
						.queryProductComment(productId);
				model.addAttribute("status", true);
				model.addAttribute("productComments", comments);
				model.addAttribute("productId", "productId");
			}
		} catch (Exception e) {
			log.error("查询商品评论报错", e);
			model.addAttribute("status", false);
		}
		return "modules/product/productComment";
	}

}
