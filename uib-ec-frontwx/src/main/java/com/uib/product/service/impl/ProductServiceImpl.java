package com.uib.product.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uib.cart.dao.CartDao;
import com.uib.cart.entity.Cart;
import com.uib.cart.entity.CartItem;
import com.uib.common.bean.ParamBean;
import com.uib.common.enums.OperationType;
import com.uib.common.utils.StringUtil;
import com.uib.common.utils.UUIDGenerator;
import com.uib.common.web.Global;
import com.uib.mobile.dto.Page;
import com.uib.product.dao.ProductCommentDao;
import com.uib.product.dao.ProductServiceDao;
import com.uib.product.entity.ParameterGroup;
import com.uib.product.entity.Product;
import com.uib.product.entity.ProductComment;
import com.uib.product.entity.ProductImageRef;
import com.uib.product.entity.ProductParameter;
import com.uib.product.entity.ProductParameterRef;
import com.uib.product.entity.ProductProperty;
import com.uib.product.entity.PropertyGroup;
import com.uib.product.entity.SpecificationGroup;
import com.uib.product.service.ProductImageRefService;
import com.uib.product.service.ProductParameterRefService;
import com.uib.product.service.ProductService;
import com.uib.product.service.PropertyGroupService;
import com.uib.product.service.SpecificationGroupService;
import com.uib.serviceUtils.Utils;

@Service
public class ProductServiceImpl implements ProductService {
	
	private Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

	@Autowired
	private ProductServiceDao productServiceDao;
	@Autowired
	private SpecificationGroupService specificationGroupService;
	@Autowired
	private ProductImageRefService productImageRefService;
	@Autowired
	private ProductParameterRefService productParameterRefService;
	@Autowired
	private PropertyGroupService propertyGroupsService;
	@Autowired
	private ProductCommentDao productCommentDao;
	@Autowired
	private CartDao cartDao;

	@Override
	public List<Product> queryProductByProductCategoryId(String ProductCategoryId) throws Exception {
		List<Product> products = productServiceDao.queryProductByProductCategoryId(ProductCategoryId);
		for (Product product : products) {
			product.setSpecificationGroups(specificationGroupService.querySpecificationGroupsByProductId(product.getId()));
		}
		return products;
	}

	@Override
	public List<Product> selectProductByBrand(String id, String flag, String productcategoryid) throws Exception {
		return productServiceDao.selectProductByBrand(id, flag, productcategoryid);
	}

	@Override
	public Product queryProductByProductId(String productId) throws Exception {
		Product product = productServiceDao.queryProductByProductId(productId);
		if (Utils.isBlank(product)) {
			return null;
		}
		List<ProductImageRef> imageList = productImageRefService.queryProductImageRefByProductId(product.getId());
		product.setProductImageRefList(imageList);
		List<SpecificationGroup> specificationGroups = specificationGroupService.querySpecificationGroupsByProductId(productId);
		product.setSpecificationGroups(specificationGroups);
		String introduction = product.getIntroduction();
		Document doc = Jsoup.parse(introduction.replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("&quot;", "\""));
		Elements elements = doc.getElementsByTag("img");
		for (Element element : elements) {
			element.attr("src", "../showImage?filePath=" + element.attr("src"));
		}
		product.setIntroduction(doc.body().html());
		List<ProductParameterRef> refs = productParameterRefService.queryProductParameterRersByProductId(productId);
		Map<ParameterGroup, Map<ProductParameter, String>> parametersValue = new HashMap<ParameterGroup, Map<ProductParameter, String>>();
		for (ProductParameterRef ref : refs) {
			ProductParameter productParameter = ref.getProductParameter();
			String groupId = productParameter.getParameterGroup().getId();
			Set<Entry<ParameterGroup, Map<ProductParameter, String>>> set = parametersValue.entrySet();
			boolean existFlag = false;
			for (Entry<ParameterGroup, Map<ProductParameter, String>> entry : set) {
				if (entry.getKey().getId().equals(groupId)) {
					Map<ProductParameter, String> map = entry.getValue();
					map.put(ref.getProductParameter(), ref.getParameterValue());
					existFlag = true;
					break;
				}
			}
			if (!existFlag) {
				Map<ProductParameter, String> map = new HashMap<ProductParameter, String>();
				map.put(productParameter, ref.getParameterValue());
				parametersValue.put(ref.getProductParameter().getParameterGroup(), map);
			}
		}
		product.setParametersValue(parametersValue);
		List<PropertyGroup> propertyGroups = propertyGroupsService.queryPropertyGroupsByProductId(productId);
		Map<PropertyGroup, ProductProperty> propertysMap = new HashMap<PropertyGroup, ProductProperty>();
		for (PropertyGroup propertyGroup : propertyGroups) {
			List<ProductProperty> properties = propertyGroup.getProductPropertyList();
			if (CollectionUtils.isEmpty(properties)) {
				continue;
			}
			propertysMap.put(propertyGroup, properties.get(0));
		}
		product.setPropertyValuesMap(propertysMap);
		return product;
	}

	@Override
	public List<Product> queryHotProductByProductCategoryId(String productCategoryId) throws Exception {
		return productServiceDao.queryHotProductByProductCategoryId(productCategoryId);
	}

	@Override
	public String queryCategoryIdsByProductId(String productId) throws Exception {
		return productServiceDao.queryCategoryIdsByProductId(productId);
	}

	@Override
	public Integer selectProductByBrandAndCategory(String id, String categoryId) throws Exception {
		return productServiceDao.selectProductByBrandAndCategory(id, categoryId);
	}

	@Override
	public Integer getAllProductNum(String id) throws Exception {
		return productServiceDao.getAllProductNum(id);
	}

	@Override
	public void update(Product product) throws Exception {
		productServiceDao.update(product);
	}

	@Override
	public void updateAllocatedStock(String id, Integer allocatedStock) throws Exception {
		productServiceDao.updateAllocatedStock(id, allocatedStock);
	}

	@Override
	public void releaseAllocatedStockByCartId(String id, String cartId) throws Exception {
		Cart cart = cartDao.selectCart(new Cart(cartId, null, null));
		for (CartItem item : cart.getCartItems()) {
			productServiceDao.releaseAllocatedStockByRelativeId(id, item.getId());
		}
	}

	@Override
	public void releaseAllocatedStockByCartItemId(String id, String cartItemId) throws Exception {
		productServiceDao.releaseAllocatedStockByRelativeId(id, cartItemId);
	}

	@Override
	public List<Product> queryProducts(String prodocutCategoryId, String brandId, String specificationIdsStr, String parameterIdsStr, String propertyIdsStr, Boolean hasStock, String shippingType,
			String orderType, Integer pageSize, Integer pageIndex) throws Exception {
		if (Utils.isAllBlank(brandId, specificationIdsStr, parameterIdsStr, propertyIdsStr, hasStock, shippingType, orderType)) {
			return queryHotProductByProductCategoryId(prodocutCategoryId);
		}
		String[] parameterIds = Utils.isBlank(parameterIdsStr) ? null : parameterIdsStr.split(",");
		String[] propertyIds = Utils.isBlank(propertyIdsStr) ? null : propertyIdsStr.split(",");
		if (pageSize <= 0) {
			pageSize = 20;
		}
		if (pageIndex <= 0) {
			pageIndex = 1;
		}
		String orderField = "price";
		String sdx = "ASC";
		if (Utils.isNotBlank(orderType)) {
			String[] Arrs = orderType.split("_");
			orderField = Arrs[0];
			sdx = Arrs[1];
		}
		return productServiceDao.queryProducts(prodocutCategoryId, brandId, specificationIdsStr, parameterIds, propertyIds, hasStock, shippingType, orderField, sdx, pageSize, pageIndex);
	}

	public List<Product> getProductListByProductNo(Map<String, String> map) {

		return productServiceDao.getProductListByProductNo(map);
	}

	public List<Map<String, Object>> findListMobile() {
		List<Map<String, Object>> list = productServiceDao.findListMobile();
		for (int i = 0; i < list.size(); i++) {
			Object imageURl = list.get(i).get("image");
			if (null != imageURl && !StringUtil.isNullOrEmpty(imageURl.toString())) {
				list.get(i).put("image", Global.getConfig("upload.image.path") + list.get(i).get("image"));
			}
		}
		return list;
	}

	public Product findById(String productId) {
		return productServiceDao.queryProductByProductId(productId);
	}
	
	public Map<String, Object> queryProductIsMarketable4Mobile(String productId) throws Exception{
		return productServiceDao.queryProductIsMarketable4Mobile(productId);
	}

	public List<Map<String, Object>> getParameters(Map<String, Object> map) {

		return productServiceDao.getParameters(map);
	}

	@Override
	public List<String> queryCorrectIdsByIds(String[] ids) {
		if (Utils.isBlank(ids)) {
			return new ArrayList<String>();
		}
		return productServiceDao.queryExistIdsByIds(ids);
	}

	public void insertSelective(ProductComment record) {
		record.setId(UUIDGenerator.getUUID());
		record.setCreateDate(new Date());
		productCommentDao.insertSelective(record);
	}

	public List<Map<String, Object>> findByCategory4Mobile(Map<String, Object> param) throws Exception {
		Page.paging(param);
		return productServiceDao.findByCategory4Mobile(param);
	}

	@Override
	public void updateSubtractProductStock(Integer number, String id) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("stock", number);
		paramMap.put("id", id);
		productServiceDao.updateSubtractProductStock(paramMap);
	}

	@Override
	public void updateSubtractAllocatedStock(Integer number, String id) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("allocatedStock", number);
		paramMap.put("id", id);
		productServiceDao.updateSubtractAllocatedStock(paramMap);
	}

	@Override
	public void updateAllocatedStock(List<ParamBean> params, OperationType operationType) throws Exception {
		productServiceDao.updateAllocatedStock(params, operationType);
	}

	public List<Map<String, Object>> findProductCommentByProductId(Map<String, Object> params) {

		return productCommentDao.findById(params);
	}

	@Override
	public List<Map<String, Object>> findByCategoryWechat(String categoryId,String page) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int startSize = (Integer.parseInt(page) - 1) * 4;
		int pageSize = 4;
		map.put("startSize", startSize);
		map.put("pageSize", pageSize);
		map.put("categoryId", categoryId);
		map.put("imageUrl", Global.getConfig("upload.image.path"));
		return productServiceDao.findByPageWechat(map);
	}
	
	@Override
	public List<Map<String, Object>> findByOrderAndPage(String orderparam,String page,boolean down,String productCategoryName) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int startSize = (Integer.parseInt(page) - 1) * 4;
		int pageSize = 4;
		map.put("startSize", startSize);
		map.put("pageSize", pageSize);
		map.put("orderparam", orderparam);
		map.put("down", down);
		map.put("productCategoryName", productCategoryName);
		map.put("imageUrl", Global.getConfig("upload.image.path"));
		return productServiceDao.findByOrderAndPage(map);
	}
	
	@Override
	public List<Map<String, Object>> findProductListCategoryAndPage(String orderparam,String page,boolean down,String categoryId,List<Object> listId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int startSize = (Integer.parseInt(page) - 1) * 4;
		int pageSize = 4;
		map.put("startSize", startSize);
		map.put("pageSize", pageSize);
		map.put("orderparam", orderparam);
		map.put("down", down);
		map.put("categoryId", categoryId);
		map.put("listId", listId);
		map.put("imageUrl", Global.getConfig("upload.image.path"));
		return productServiceDao.findProductListCategoryAndPage(map);
	}
	
	@Override
	public List<Map<String, Object>> findProdCategoryAndPageMoblie(String orderparam,String page,boolean down,String categoryId,List<Object> listId,String proCategoryName) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int startSize = (Integer.parseInt(page) - 1) * 10;
		int pageSize = 10;
		map.put("startSize", startSize);
		map.put("pageSize", pageSize);
		map.put("orderparam", orderparam);
		map.put("down", down);
		map.put("categoryId", categoryId);
		map.put("listId", listId);
		map.put("productCategoryName", proCategoryName);
		map.put("imageUrl", Global.getConfig("upload.image.path"));
		return productServiceDao.findProdCategoryAndPageMoblie(map);
	}

	@Override
	public List<Map<String, Object>> findByPageWechat(String page)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int startSize = (Integer.parseInt(page) - 1) * 4;
		int pageSize = 4;
		map.put("startSize", startSize);
		map.put("pageSize", pageSize);
		map.put("imageUrl", Global.getConfig("upload.image.path"));
		return productServiceDao.findByPageWechat(map);
	}
	
	@Override
	public List<Map<String, Object>> findHomeProduct(String page)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int startSize = (Integer.parseInt(page) - 1) * 4;
		int pageSize = 4;
		map.put("startSize", startSize);
		map.put("pageSize", pageSize);
		map.put("imageUrl", Global.getConfig("upload.image.path"));
		return productServiceDao.findHomeProduct(map);
	}

	@Override
	public List<Map<String, Object>> querySpecAndGroupSpecInfo(String productId)
			throws Exception {
		return productServiceDao.querySpecAndGroupSpecInfo(productId);
	}

	@Override
	public List<Map<String, Object>> querySpecBySpecIdAndId(
			Map<String, Object> map) throws Exception {
		return productServiceDao.querySpecBySpecIdAndId(map);
	}

	@Override
	public void updateProductSales(String id,Integer sales) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("sales", sales);
		productServiceDao.updateProductSales(map);
	}

	@Override
	public String queryProductIdByIdAndSpecIds(String productId,
			String[] specIds) throws Exception {
		List<Map<String, Object>> productList = productServiceDao.queryProductAndSpecInfoById(productId);
		//按产品id分组
		Map<String,Set<String>> productIdMap = new HashMap<String, Set<String>>();
		Set<String> productIdSet = new HashSet<String>();
		//收集productid
		for (Map<String, Object> item : productList) {
			productIdSet.add(item.get("productId").toString());
		}
		
		for (String sPid : productIdSet) {
			Set<String> specIdSet = new HashSet<String>();
			
			for (Map<String, Object> it : productList) {
				String itId = it.get("productId").toString();
				String itSpecId =  it.get("specId").toString();
				if(sPid.equals(itId)){
					specIdSet.add(itSpecId);
				}
			} 
			productIdMap.put(sPid, specIdSet);
		}
		
		//把页面传来的规格数组转为set
		List<String> specIdList = Arrays.asList(specIds);
		Set<String> specSet = new HashSet<String>();
		specSet.addAll(specIdList);
		
		for (String rid : productIdSet) {
			Set<String> idSet = productIdMap.get(rid);
			boolean checkFlag = true;
			for (String sid : specSet) {
				if(!idSet.contains(sid)){
					checkFlag = false;
					
				}
			}
			if(checkFlag){
				logger.info("返回productId=" + rid);
				return rid;
			}
			
		}
		logger.info("没有找到匹配的productId");
		return null;
	}


}
