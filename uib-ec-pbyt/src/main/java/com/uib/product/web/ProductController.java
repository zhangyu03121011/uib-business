package com.uib.product.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.easypay.core.web.TreeUtils;
import com.uib.product.entity.Brand;
import com.uib.product.entity.ParameterGroup;
import com.uib.product.entity.Product;
import com.uib.product.entity.ProductCategory;
import com.uib.product.entity.ProductParameter;
import com.uib.product.entity.ProductProperty;
import com.uib.product.entity.ProductSpecification;
import com.uib.product.entity.PropertyGroup;
import com.uib.product.entity.SpecificationGroup;
import com.uib.product.service.BrandService;
import com.uib.product.service.ParameterGroupService;
import com.uib.product.service.ProductCategoryService;
import com.uib.product.service.ProductService;
import com.uib.product.service.PropertyGroupService;
import com.uib.product.service.SpecificationGroupService;
import com.uib.serviceUtils.ProcessorUtil;
import com.uib.serviceUtils.Utils;

/**
 * 商品信息
 * 
 * @author kevin
 * 
 */
@Controller
@RequestMapping("/product")
public class ProductController {
	private Logger logger = LoggerFactory.getLogger(ProductController.class);
	@Autowired
	private ProductService productService;
	@Autowired
	private ProductCategoryService productCategoryService;
	@Autowired
	private BrandService brandService;
	@Autowired
	private SpecificationGroupService specificationGroupService;
	@Autowired
	private ParameterGroupService parameterGroupService;
	@Autowired
	private PropertyGroupService propertyGroupService;

	private static final String BRAND_ID = "brandId";
	private static final String SPECIFICATION_IDS = "specificationIds";
	private static final String PROPERTY_ID = "propertyId";
	private static final String PARAMETER_ID = "parameterId";

	//@Autowired
//	private LuceneUtil luceneUtil;

	@Value("/product/products_list")
	private String productListView;
	@Value("/product/products_details")
	private String productDetailView;

	@Value("/product/products_query")
	private String productQueryView;

	/**
	 * 查询商品列表信息
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/search")
	public String search(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		/*try {
			String productName = request.getParameter("productName");
			List<Product> productList = luceneUtil.searchByProductName(productName);
			modelMap.addAttribute("productList", productList);
			Map<String, TreeUtils> trueMap = new HashMap<String, TreeUtils>();
			List<TreeUtils> categoryList = new ArrayList<TreeUtils>();
			for (Product product : productList) {
				if (StringUtils.isNotBlank(product.getProductCategoryId())) {
					ProductCategory category = productCategoryService.getMostParentCategorysByCategoryId(product
							.getProductCategoryId());
					TreeUtils tree = new TreeUtils();
					tree.setId(category.getId());
					tree.setText(category.getName());
					tree.setChildren(ProcessorUtil.getProductCategoryToTreeUtils(productCategoryService,
							productCategoryService.getCategoryByMeridAndParentId(category.getId(), null)));

					// 获取顶级父类
					// categoryList.add(tree);
					trueMap.put(category.getId(), tree);
				}
			}
			for (Map.Entry<String, TreeUtils> entry : trueMap.entrySet()) {
				TreeUtils tree = entry.getValue();
				categoryList.add(tree);
			}

			modelMap.put("subCategoryList", categoryList);
		} catch (Exception ex) {
			logger.error("根据查询失败!", ex);
		}*/
		return productQueryView;
	}

	@RequestMapping("/list")
	private String productList(@RequestParam(value = "productCategoryId", required = false) String productCategoryId,
			HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		try {
			List<Product> products = productService.queryProductByProductCategoryId(productCategoryId);
			modelMap.addAttribute("productList", products);
			List<TreeUtils> categoryList = new ArrayList<TreeUtils>();
			if (StringUtils.isNotBlank(productCategoryId)) {
				// 获取顶级父类
				ProductCategory category = productCategoryService.getMostParentCategorysByCategoryId(productCategoryId);
				if (null != category) {
					TreeUtils tree = new TreeUtils();
					tree.setId(category.getId());
					tree.setText(category.getName());
					tree.setChildren(ProcessorUtil.getProductCategoryToTreeUtils(productCategoryService,
							productCategoryService.getCategoryByMeridAndParentId(category != null ? category.getId()
									: productCategoryId, null)));
					categoryList.add(tree);
				}

			}
			modelMap.put("subCategoryList", categoryList);
			modelMap.put("brandList", brandService.queryBrandByCategoryId(productCategoryId));
			modelMap.put("specificationGroups",
					specificationGroupService.querySpecificationGroupsByCategoryId(productCategoryId));
			modelMap.put("parameterGroups", parameterGroupService.queryParameterGroupsByCategoryId(productCategoryId));
			modelMap.put("propertyGroups", propertyGroupService.queryPropertyGoupsByCategoryId(productCategoryId));
			modelMap.put("hotPoroductList", productService.queryHotProductByProductCategoryId(productCategoryId));
		} catch (Exception e) {
			logger.error("根据分类查询商品集合失败!", e);
		}
		return productListView;
	}

	@RequestMapping("/details")
	private String queryProductById(@RequestParam(value = "productId", required = false) String productId,
			HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		try {
			modelMap.put("categoryCascadeList", ProcessorUtil.getProductCategorySBTreeByProductId(
					productCategoryService, productService, productId));
			//当前用户等级
			String rankId = "1";
			Product product = productService.queryProductByProductId(productId,rankId);
			modelMap.put("product", product);
			modelMap.put("hotPoroductList",
					productService.queryHotProductByProductCategoryId(product.getProductCategoryId()));

		} catch (Exception e) {
			logger.error("根据id查询商品信息出错!", e);
		}
		return productDetailView;
	}

	// 查询商品评论
	@ResponseBody
	@RequestMapping("/findComment")
	public List<Map<String, Object>> findComment(@RequestParam Map<String, Object> params) {
		List<Map<String, Object>> list = productService.findProductCommentByProductId(params);

		return list;
	}

	@RequestMapping("/query")
	private String query(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		String productCategoryId = request.getParameter("productCategoryId");
		String brandId = request.getParameter(BRAND_ID);
		String specificationIds = request.getParameter(SPECIFICATION_IDS);
		String propertyId = request.getParameter(PROPERTY_ID);
		String parameterId = request.getParameter(PARAMETER_ID);
		String hasStock_ = request.getParameter("hasStock");
		Boolean hasStock = Utils.isBlank(hasStock_) ? null : Boolean.valueOf(hasStock_);
		String shippingType = request.getParameter("shippingType");
		String orderType = request.getParameter("orderType");
		String sizeString = request.getParameter("pageSize");
		String indexString = request.getParameter("pageIndex");
		List<TreeUtils> categoryList = new ArrayList<TreeUtils>();
		try {
			int pageSize = Utils.isBlank(sizeString) ? 0 : Integer.parseInt(sizeString);
			int pageIndex = Utils.isBlank(indexString) ? 0 : Integer.parseInt(indexString);
			List<Product> products = productService.queryProducts(productCategoryId, brandId, specificationIds,
					parameterId, propertyId, hasStock, shippingType, orderType, pageSize, pageIndex);
			if (StringUtils.isNotBlank(productCategoryId)) {
				// 获取顶级父类
				ProductCategory category = productCategoryService.getMostParentCategorysByCategoryId(productCategoryId);
				TreeUtils tree = new TreeUtils();
				tree.setId(category.getId());
				tree.setText(category.getName());
				tree.setChildren(ProcessorUtil.getProductCategoryToTreeUtils(productCategoryService,
						productCategoryService.getCategoryByMeridAndParentId(category != null ? category.getId()
								: productCategoryId, null)));
				categoryList.add(tree);
			}
			List<Brand> brandList = brandService.queryBrandByCategoryId(productCategoryId);
			List<SpecificationGroup> specificationGroups = specificationGroupService
					.querySpecificationGroupsByCategoryId(productCategoryId);
			List<ParameterGroup> parameterGroups = parameterGroupService
					.queryParameterGroupsByCategoryId(productCategoryId);
			List<PropertyGroup> propertyGroups = propertyGroupService.queryPropertyGoupsByCategoryId(productCategoryId);
			Map<String, Boolean> extendMap = new HashMap<String, Boolean>();
			boolean hasSpecificationAll = false;
			if (Utils.isNotBlank(specificationIds) && Utils.isNotBlank(specificationGroups)) {
				hasSpecificationAll = (specificationIds.split(",").length) >= specificationGroups.size();
				for (SpecificationGroup group : specificationGroups) {
					for (ProductSpecification specification : group.getProductSpecificationList()) {
						if (specificationIds.contains(specification.getId())) {
							group.setSelected(true);
							break;
						}
					}
				}
			}
			boolean hasPropertyAll = false;
			if (Utils.isNotBlank(propertyId) && Utils.isNotBlank(propertyGroups)) {
				hasPropertyAll = (propertyId.split(",").length) >= propertyGroups.size();
				for (PropertyGroup propertyGroup : propertyGroups) {
					for (ProductProperty property : propertyGroup.getProductPropertyList()) {
						if (propertyId.contains(property.getId())) {
							propertyGroup.setSelected(true);
							break;
						}
					}
				}
			}
			boolean hasParameterAll = false;
			if (Utils.isNotBlank(parameterId) && Utils.isNotBlank(parameterGroups)) {
				hasParameterAll = (parameterId.split(",").length >= parameterGroups.size());
				for (ParameterGroup group : parameterGroups) {
					for (ProductParameter parameter : group.getProductParameterList()) {
						if (parameterId.contains(parameter.getId())) {
							group.setSelected(true);
							break;
						}
					}
				}
			}
			extendMap.put("hasParameterAll", hasParameterAll);
			extendMap.put("hasPropertyAll", hasPropertyAll);
			extendMap.put("hasSpecificationAll", hasSpecificationAll);
			modelMap.put("extendMap", extendMap);
			modelMap.put("productList", products);
			modelMap.put("subCategoryList", categoryList);
			modelMap.put("brandList", brandList);
			modelMap.put("specificationGroups", specificationGroups);
			modelMap.put("parameterGroups", parameterGroups);
			modelMap.put("propertyGroups", propertyGroups);
			modelMap.put("hotPoroductList", productService.queryHotProductByProductCategoryId(productCategoryId));
		} catch (Exception e) {
			logger.error("查询商品出错！", e);
		}
		return productListView;
	}

}
