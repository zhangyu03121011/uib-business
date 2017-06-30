package com.uib.ecmanager.modules.product.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.uib.ecmanager.ServiceUtils.Utils;
import com.uib.ecmanager.common.mapper.JsonMapper;
import com.uib.ecmanager.common.web.BaseController;
import com.uib.ecmanager.modules.product.dao.ProductSpecificationDao;
import com.uib.ecmanager.modules.product.entity.SpecificationGroup;

@Controller
@RequestMapping(value = "${adminPath}/product/specification")
public class ProductSpecificationController extends BaseController {
	@Autowired
	private ProductSpecificationDao productSpecificationDao;

	@RequestMapping(value = "/specificationJson")
	public @ResponseBody String getSpecificationJson(@RequestParam(value = "ids", required = false) String ids,
			@RequestParam(value = "productCategoryId", required = false) String productCategoryId,
			HttpServletRequest request) {
		// 权限控制
		// 查询
		List<SpecificationGroup> specifications = null;
		try {
			if (Utils.isNotBlank(productCategoryId)) {
				specifications = productSpecificationDao.querySpecificationByProductCategoryId(productCategoryId);
			} else {
				specifications = productSpecificationDao
						.getSpecificationByGroupIds(StringUtils.isBlank(ids) ? null : ids.split(","));
			}
		} catch (Exception e) {
			logger.error("根据id列表获取商品规格列表失败", e);
		}
		return JsonMapper.toJsonString(specifications);
	}
}
