package com.uib.ecmanager.modules.product.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.uib.ecmanager.common.persistence.Page;
import com.uib.ecmanager.common.web.BaseController;
import com.uib.ecmanager.modules.product.dao.ProductPropertyDao;
import com.uib.ecmanager.modules.product.entity.ProductProperty;
import com.uib.ecmanager.modules.product.entity.PropertyGroup;

/**
 * 商品属性Controller
 * 
 * @author gaven
 * @version 2015-06-04
 */
@Controller
@RequestMapping(value = "${adminPath}/product/product_property")
public class ProductPropertyController {
	private Logger logger = LoggerFactory.getLogger("");
	@Autowired
	private ProductPropertyDao productPropertyDao;

	@RequestMapping(value = "list", method = RequestMethod.GET)
	public @ResponseBody List<ProductProperty> list(@RequestParam("gourpId") String groupId) {
		List<ProductProperty> productProperties = null;
		try {
			PropertyGroup group = new PropertyGroup();
			group.setId(groupId);
			productProperties = productPropertyDao.findList(new ProductProperty(group));
			return productProperties;
		} catch (Exception e) {
			logger.error("获取商品属性列表失败", e);
		}
		return productProperties;
	}
}
