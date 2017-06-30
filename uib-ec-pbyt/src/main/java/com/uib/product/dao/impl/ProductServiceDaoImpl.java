package com.uib.product.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.uib.common.bean.ParamBean;
import com.uib.common.enums.OperationType;
import com.uib.common.web.Global;
import com.uib.core.dao.MyBatisDao;
import com.uib.product.dao.ProductServiceDao;
import com.uib.product.entity.Product;
import com.uib.serviceUtils.Utils;

@Component
public class ProductServiceDaoImpl extends MyBatisDao<Product> implements
		ProductServiceDao {

	@Override
	public List<Product> queryProductByProductCategoryId(
			String productCategoryId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("productCategoryId", productCategoryId);
		return this.get("queryProductByProductCategoryId", map);
	}

	@Override
	public List<Product> selectProductByBrand(String id, String flag,
			String productcategoryid) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", id);
		map.put("flag", flag);
		map.put("productcategoryid", productcategoryid);
		return this.get("selectProductByBrand", map);
	}

	@Override
	public Product queryProductByProductId(String productId, String rankId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("productId", productId);
		map.put("rankId", rankId);
		return this.getUnique("queryProductByProductId", map);
	}
	//chengjian 2015-11-17
	@Override
	public Map<String, Object> queryProductIsMarketable4Mobile(String productId) throws Exception{
		return this.getMap("queryProductIsMarketable4Mobile", productId);
	}

	@Override
	public List<Product> queryHotProductByProductCategoryId(
			String productCategoryId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("productCategoryId", productCategoryId);
		return this.get("queryHotProductByProductCategoryId", map);
	}

	@Override
	public String queryCategoryIdsByProductId(String productId)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("productId", productId);
		return (String) this.getObjectValue("queryCategoryIdsByProductId", map);
	}

	@Override
	public List<Product> getProductByNotInIds(List<String> ids)
			throws Exception {
		return this.get("getProductByNotInIds", ids);
	}

	@Override
	public Integer selectProductByBrandAndCategory(String id, String categoryId)
			throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", id);
		map.put("categoryId", categoryId);
		return (Integer) this.getObjectValue("selectProductByBrandAndCategory",
				map);
	}

	@Override
	public Integer getAllProductNum(String id) throws Exception {
		return (Integer) this.getObjectValue("getAllProductNum", id);
	}

	@Override
	public void update(Product product) throws Exception {
		this.update("update", product);
	}

	@Override
	public void updateAllocatedStock(String id, Integer allocatedStock)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("allocatedStock", allocatedStock);
		this.update("updateAllocatedStock", map);
	}

	@Override
	public void updateAllocatedStockBatch(List<ParamBean> params,
			OperationType operationType) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("params", params);
		map.put("operationType", operationType.name());
		this.update("updateAllocatedStockBatch", map);
	}

	@Override
	public void releaseAllocatedStock(String id, Integer number)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("number", number);
		this.update("releaseAllocatedStock", map);
	}

	@Override
	public void releaseAllocatedStockByRelativeId(String id, String cartItemId)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		if (Utils.isNotBlank(cartItemId)) {
			map.put("cartItemId", cartItemId);
		}
		this.update("releaseAllocatedStockByRelativeId", map);
	}

	@Override
	public List<Product> queryProducts(String prodocutCategoryId,
			String brandId, String specificationIds, String[] parameterIds,
			String[] propertyIds, Boolean hasStock, String shippingType,
			String orderField, String sdx, Integer pageSize, Integer pageIndex)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("productCategoryId", prodocutCategoryId);
		map.put("brandId", brandId);
		map.put("specificationIds", specificationIds);
		map.put("parameterIds", parameterIds);
		map.put("propertyIds", propertyIds);
		map.put("hasStock", hasStock);
		map.put("shippingType", shippingType);
		map.put("orderField", orderField);
		map.put("sdx", sdx);
		map.put("start", (pageIndex - 1) * pageSize);
		map.put("size", pageSize);
		return this.get("queryProducts", map);
	}

	public List<Product> getProductListByProductNo(Map<String, String> map) {

		return this.get("getProductListByProductNo", map);
	}

	public List<Map<String, Object>> findListMobile() {
		return this.getList("findListMobile", new HashMap<String, Object>());
	}

	public List<Map<String, Object>> getParameters(Map<String, Object> map) {
		return this.getList("getParameters", map);
	}

	@Override
	public List<String> queryExistIdsByIds(String[] ids) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", ids);
		List<Object> objList = this.getObjects("selectExistIdsByIds", map);
		List<String> result = new ArrayList<String>();
		if (objList == null) {
			return result;
		}
		for (Object obj : objList) {
			result.add(obj.toString());
		}
		return result;
	}

	public List<Map<String, Object>> findByCategory4Mobile(Map<String, Object> map) {
		map.put("imageUrl", Global.getConfig("upload.image.path"));
		return super.getList("findByCategory4Mobile", map);
	}

	@Override
	public void updateSubtractProductStock(Map<String, Object> map)
			throws Exception {
		this.update("updateSubtractProductStock", map);
	}

	@Override
	public void updateSubtractAllocatedStock(Map<String, Object> map)
			throws Exception {
		this.update("updateSubtractAllocatedStock", map);
	}

	@Override
	public List<Map<String, Object>> findByCategoryWechat(
			Map<String, Object> map) throws Exception {
		return super.getList("findByCategoryWechat", map);
	}

	@Override
	public List<Map<String, Object>> findByPageWechat(Map<String, Object> map)
			throws Exception {
		return super.getList("findByPageWechat", map);
	}
	
	@Override
	public List<Map<String, Object>> findHomeProduct(Map<String, Object> map)
			throws Exception {
		return super.getList("findHomeProduct", map);
	}
	
	@Override
	public List<Map<String, Object>> findByOrderAndPage(Map<String, Object> map)
			throws Exception {
		return super.getList("findByOrderAndPage", map);
	}
	
	@Override
	public List<Map<String, Object>> findProductListCategoryAndPage(Map<String, Object> map)
			throws Exception {
		return super.getList("findProductListCategoryAndPage", map);
	}
	
	@Override
	public List<Map<String, Object>> findProdCategoryAndPageMoblie(Map<String, Object> map)
			throws Exception {
		return super.getList("findProdCategoryAndPageMoblie", map);
	}

	@Override
	public List<Map<String, Object>> querySpecAndGroupSpecInfo(String productId)
			throws Exception {
		return super.getList("querySpecAndGroupSpecInfo", productId);
	}

	@Override
	public List<Map<String, Object>> querySpecBySpecIdAndId(Map<String, Object> map) throws Exception {
		return super.getList("querySpecBySpecIdAndId", map);
	}

	@Override
	public List<Map<String, Object>> queryProductAndSpecInfoById(
			String productId) throws Exception {
		return super.getList("queryProductAndSpecInfoById", productId);
	}


	@Override
	public void updateProductSales(Map<String, Object> map) throws Exception {
		this.update("updateProductSales", map);
		
	}

}
