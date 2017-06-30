package com.uib.serviceUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.uib.core.web.TreeUtils;
import com.uib.product.entity.ProductCategory;
import com.uib.product.service.ProductCategoryService;
import com.uib.product.service.ProductService;
import com.uib.product.web.ProductController;

public class ProcessorUtil {
	private static Logger logger = LoggerFactory
			.getLogger(ProductController.class);

	/**
	 * 将菜单封装成树
	 * 
	 * @Title getMenuToTreeUtils
	 * @author WANGHUAN
	 * @param
	 * @return String
	 * @throws Exception
	 * @throws
	 * @date 2014年11月05日
	 * @time 上午9:35:57
	 * @Description
	 */
	public static List<TreeUtils> getProductCategoryToTreeUtils(
			ProductCategoryService productCategoryService,
			List<ProductCategory> productCategoryList) throws Exception {
		List<TreeUtils> treeUtilsList = new ArrayList<TreeUtils>();
		if (null != productCategoryList) {
			for (ProductCategory productCategory : productCategoryList) {
				TreeUtils treeUtils = new TreeUtils();
				treeUtils.setId(productCategory.getId());
				treeUtils.setText(productCategory.getName());
				List<ProductCategory> childrenProductCategory = productCategoryService
						.getCategoryByMeridAndParentId(productCategory.getId(),
								null);
				if (null != childrenProductCategory) {
					treeUtils.setChildren(getProductCategoryToTreeUtils(
							productCategoryService, childrenProductCategory));
				}
				treeUtilsList.add(treeUtils);
			}
		}
		return treeUtilsList;
	}

	public static List<TreeUtils> getProductCategorySBTreeByProductId(
			ProductCategoryService productCategoryService,
			ProductService productService, String productId) throws Exception {
		List<TreeUtils> treeUtilsList = new LinkedList<TreeUtils>();
		try {
			if (StringUtils.isBlank(productId)) {
				return treeUtilsList;
			}
			String idsString = productService
					.queryCategoryIdsByProductId(productId);
			if (StringUtils.isBlank(idsString)) {
				return treeUtilsList;
			}
			String[] ids = idsString.split(" \\| ");
			String[] ids_ = ids[1].split(",");
			List<String> idList = new ArrayList<String>();
			idList.addAll(Arrays.asList(ids));
			idList.addAll(Arrays.asList(ids_));
			List<ProductCategory> categoryList = productCategoryService
					.queryCategorysByCategoryIds(idList);
			for (int i = 0; i < ids_.length; i++) {
				String id = ids_[i];
				if (StringUtils.isBlank(id) || "0".equals(id)) {
					continue;
				}
				for (ProductCategory productCategory : categoryList) {
					if (id.equals(productCategory.getId())) {
						TreeUtils treeUtils = new TreeUtils();
						treeUtils.setId(productCategory.getId());
						treeUtils.setText(productCategory.getName());
						treeUtilsList.add(treeUtils);
					}
				}
			}
			for (String id : ids) {
				for (ProductCategory productCategory : categoryList) {
					if (id.equals(productCategory.getId())) {
						TreeUtils treeUtils = new TreeUtils();
						treeUtils.setId(productCategory.getId());
						treeUtils.setText(productCategory.getName());
						treeUtilsList.add(treeUtils);
					}
				}
			}
			return treeUtilsList;
		} catch (Exception e) {
			logger.error("根据商品id查询分类单树失败!", e);
			return treeUtilsList;
		}
	}
}
