/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.product.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uib.common.utils.UUIDGenerator;
import com.uib.ecmanager.ServiceUtils.Utils;
import com.uib.ecmanager.common.persistence.Page;
import com.uib.ecmanager.common.service.CrudService;
import com.uib.ecmanager.common.utils.StringUtils;
import com.uib.ecmanager.modules.mem.entity.MemMerchant;
import com.uib.ecmanager.modules.mem.service.MemMerchantService;
import com.uib.ecmanager.modules.product.dao.GiftItemDao;
import com.uib.ecmanager.modules.product.dao.ProductAreaRefDao;
import com.uib.ecmanager.modules.product.dao.ProductDao;
import com.uib.ecmanager.modules.product.dao.ProductImageRefDao;
import com.uib.ecmanager.modules.product.dao.ProductParameterDao;
import com.uib.ecmanager.modules.product.dao.ProductParameterRefDao;
import com.uib.ecmanager.modules.product.dao.ProductPropertyRefDao;
import com.uib.ecmanager.modules.product.dao.ProductSpecificationDao;
import com.uib.ecmanager.modules.product.dao.ProductSpecificationRefDao;
import com.uib.ecmanager.modules.product.dao.SpecialDao;
import com.uib.ecmanager.modules.product.dao.SpecialProductRefDao;
import com.uib.ecmanager.modules.product.entity.GiftItem;
import com.uib.ecmanager.modules.product.entity.ParamBean;
import com.uib.ecmanager.modules.product.entity.ParameterGroup;
import com.uib.ecmanager.modules.product.entity.Product;
import com.uib.ecmanager.modules.product.entity.ProductAreaRef;
import com.uib.ecmanager.modules.product.entity.ProductImageRef;
import com.uib.ecmanager.modules.product.entity.ProductParameter;
import com.uib.ecmanager.modules.product.entity.ProductParameterRef;
import com.uib.ecmanager.modules.product.entity.ProductPropertyRef;
import com.uib.ecmanager.modules.product.entity.ProductSpecificationRef;
import com.uib.ecmanager.modules.sys.utils.UserUtils;

/**
 * 商品Service
 * 
 * @author gaven
 * @version 2015-06-01
 */
@Service
@Transactional(readOnly = true)
public class ProductService extends CrudService<ProductDao, Product> {

	@Autowired
	private ProductImageRefDao productImageRefDao;
	@Autowired
	private GiftItemDao giftItemDao;
	@Autowired
	private BrandService brandService;
	@Autowired
	private ProductCategoryService productCategoryService;
	@Autowired
	private MemMerchantService memMerchantService;
	@Autowired
	private ProductDao productDao;
	@Autowired
	private ProductSpecificationRefDao productSpecificationRefDao;
	@Autowired
	private SpecificationGroupService specificationGroupService;
	@Autowired
	private ParameterGroupService parameterGroupService;
	@Autowired
	private ProductParameterDao productParameterDao;
	@Autowired
	private ProductParameterRefDao productParameterRefDao;
	@Autowired
	private ProductPropertyRefDao productPropertyRefDao;
	@Autowired
	private ProductSpecificationGroupRefService productSpecificationGroupRefService;
	@Autowired
	private ProductSpecificationDao productSpecificationDao;
	@Autowired
	private ProductAreaRefService productAreaRefService;
	@Autowired
	private ProductAreaRefDao productAreaRefDao;
	
	@Autowired
	private SpecialProductRefDao specialProductRefDao;
	public Product get(String id) {
		Product product = super.get(id);
		product.setProductImageRefList(productImageRefDao.findList(new ProductImageRef(product)));
		// product.setProductReviewList(productReviewDao
		// .findList(new ProductReview(product)));
		product.setSpecificationGoups(specificationGroupService.querySpecificationGroupsByProductId(id));
		ParameterGroup parameterGroup = new ParameterGroup();
		parameterGroup.setProductCategoryId(product.getProductCategoryId());
		List<ParameterGroup> groups = parameterGroupService.findList(parameterGroup);
		for (ParameterGroup group : groups) {
			group.setProductParameterList(productParameterDao.findList(new ProductParameter(group)));
		}
		product.setParameterGroups(groups);
		return product;
	}

	public List<Product> findList(Product product) {
		return super.findList(product);
	}

	public Page<Product> findPage(Page<Product> page, Product product) {
		page = super.findPage(page, product);
		for (Product pd : page.getList()) {
			pd.setBrand(brandService.get(pd.getBrandId()));
			pd.setProductCategory(productCategoryService.get(pd.getProductCategoryId()));
			MemMerchant merchant = new MemMerchant();
			merchant.setMerchantNo(pd.getMerchantNo());
			pd.setMemMerchant(memMerchantService.get(merchant));
		}
		return page;
	}

	@Transactional(readOnly = false)
	public void save(Product product) {// TODO：事物中，多表插入 外键问题
		List<String> specificationIds = product.getProductSpecificationIds();
		String suffixName = Utils.isBlank(specificationIds) ? null
				: productSpecificationDao.querySuffixNameByProductSpecificationIds(specificationIds);
		if (!Utils.isBlank(suffixName)) {
			product.setName(product.getName() + " " + suffixName);
			if (Utils.isNotBlank(product.getFullName())) {
				product.setFullName(product.getFullName() + " " + suffixName);
			}
		}
		product.setCreateDate(new Date());
		product.setIsNewRecord(true);
		super.save(product);
		//添加销售区域
		List<ProductAreaRef> parList = new ArrayList<ProductAreaRef>();
		if(product.getAreaId()!=null){
		for (int i = 0; i < product.getAreaId().length; i++) {
			String areaId = product.getAreaId()[i].substring(0,product.getAreaId()[i].length()-1);
				ProductAreaRef par = new ProductAreaRef();
				par.setId(UUIDGenerator.getUUID());
				par.setProductId(product.getId());
				par.setAreaId(areaId);
				par.setIsNewRecord(true);
				parList.add(par);
		}
		productAreaRefService.insertProductAreaRef(parList);
		}
		for (ProductImageRef productImageRef : product.getProductImageRefList()) {
			if (Utils.isBlank(productImageRef.getOrders())) {
				productImageRef.setOrders("0");
			}
			if (productImageRef.getProduct() != null && !productImageRef.getProduct().getId().equals(product.getId())) {
				productImageRef.setId(UUIDGenerator.getUUID());
				productImageRef.setProduct(product);
				productImageRef.preInsert();
				productImageRefDao.insert(productImageRef);
				continue;
			}
			if (GiftItem.IS_GIFT.equals(product.getIsGift())) {
				GiftItem entity = new GiftItem(UUID.randomUUID().toString());
				entity.setProductId(product.getId());
				entity.setQuantity(product.getStock());
				// entity.setPromotionId(product.getPromotion());
				giftItemDao.insert(entity);
			}
			if (ProductImageRef.DEL_FLAG_NORMAL.equals(productImageRef.getDelFlag())) {
				if (StringUtils.isBlank(productImageRef.getId())) {
					productImageRef.setProduct(product);
					productImageRef.preInsert();
					productImageRefDao.insert(productImageRef);
				}
			} else {
				productImageRefDao.delete(productImageRef);
			}
		}
		// for (ProductReview productReview : product.getProductReviewList()) {
		// if (productReview.getId() == null) {
		// continue;
		// }
		// if (ProductReview.DEL_FLAG_NORMAL
		// .equals(productReview.getDelFlag())) {
		// if (StringUtils.isBlank(productReview.getId())) {
		// productReview.setProduct(product);
		// productReview.preInsert();
		// productReviewDao.insert(productReview);
		// }
		// } else {
		// productReviewDao.delete(productReview);
		// }
		// }
		String productId = product.getId();
		if (CollectionUtils.isNotEmpty(specificationIds)) {
			List<String> oldIds = productSpecificationRefDao.querySpecificationIdsByProductId(productId);
			List<String> oldIds_ = new ArrayList<String>(oldIds);
			List<String> specificationIds_ = new ArrayList<String>(specificationIds);
			oldIds_.removeAll(specificationIds_);
			specificationIds_.removeAll(oldIds);
			List<ProductSpecificationRef> refs = new ArrayList<ProductSpecificationRef>();
			for (int i = 0; i < specificationIds_.size(); i++) {
				ProductSpecificationRef ref = new ProductSpecificationRef(UUIDGenerator.getUUID());
				ref.setProductId(productId);
				ref.setSpecificationId(specificationIds_.get(i));
				refs.add(ref);
			}
			productSpecificationRefDao.insertRefsBatch(refs);
			if (CollectionUtils.isNotEmpty(oldIds_)) {
				productSpecificationRefDao.deleteBatch(productId, oldIds_);
			}
		}
		Map<ProductParameter, String> parametersValue = product.getParameterValue();
		if (MapUtils.isNotEmpty(parametersValue)) {
			// 需要优化
			Set<Entry<ProductParameter, String>> entrySet = parametersValue.entrySet();
			List<String> parameterIds = productParameterRefDao.queryParameterIdsByProductId(productId);
			for (Entry<ProductParameter, String> entry : entrySet) {
				String parameterId = entry.getKey().getId();
				String parameterValue = entry.getValue();
				ProductParameterRef ref = new ProductParameterRef();
				ref.setProductId(productId);
				ref.setParameterValue(parameterValue);
				ref.setParameterId(parameterId);
				if (parameterIds.contains(parameterId)) {
					productParameterRefDao.update(ref);
					parameterIds.remove(parameterId);
				} else {
					ref.setId(UUIDGenerator.getUUID());
					productParameterRefDao.insert(ref);
				}
			}
			if (CollectionUtils.isNotEmpty(parameterIds)) {
				productParameterRefDao.deleteFrom(productId, parameterIds);
			}
		} else {
			productParameterRefDao.deleteFrom(productId, null);
		}

		Map<String, ProductPropertyRef> propertyRefMap = product.getPropertyRefMap();
		Set<Entry<String, ProductPropertyRef>> entrySet = propertyRefMap.entrySet();
		for (Entry<String, ProductPropertyRef> entry : entrySet) {
			ProductPropertyRef ref = entry.getValue();
			ref.setId(UUIDGenerator.getUUID());
			ref.setProductId(productId);
			productPropertyRefDao.insert(ref);
		}
	}

	@Transactional(readOnly = false)
	public void update(Product product) {
		super.update(product);
		for (ProductImageRef productImageRef : product.getProductImageRefList()) {
			if (Utils.isBlank(productImageRef.getOrders())) {
				productImageRef.setOrders("0");
			}
			if (productImageRef.getId() == null) {
				productImageRef.setId(UUIDGenerator.getUUID());
				productImageRef.setProduct(product);
				productImageRef.preInsert();
				productImageRefDao.insert(productImageRef);
				continue;
			}
			if (ProductImageRef.DEL_FLAG_NORMAL.equals(productImageRef.getDelFlag())) {

				productImageRef.preUpdate();
				productImageRefDao.update(productImageRef);

			} else {
				productImageRefDao.delete(productImageRef);
			}
		}
		// for (ProductReview productReview : product.getProductReviewList()) {
		// if (productReview.getId() == null) {
		// productReview.setId(UUIDGenerator.getUUID());
		// productReview.setProduct(product);
		// productReview.preInsert();
		// productReviewDao.insert(productReview);
		// continue;
		// }
		// if (ProductReview.DEL_FLAG_NORMAL
		// .equals(productReview.getDelFlag())) {
		// productReview.preUpdate();
		// productReviewDao.update(productReview);
		//
		// } else {
		// productReviewDao.delete(productReview);
		// }
		// }
		String productId = product.getId();
		List<String> specificationIds = product.getProductSpecificationIds();
		if (CollectionUtils.isNotEmpty(specificationIds)) {
			List<String> oldIds = productSpecificationRefDao.querySpecificationIdsByProductId(productId);
			List<String> oldIds_ = new ArrayList<String>(oldIds);
			List<String> specificationIds_ = new ArrayList<String>(specificationIds);
			oldIds_.removeAll(specificationIds_);
			specificationIds_.removeAll(oldIds);
			List<ProductSpecificationRef> refs = new ArrayList<ProductSpecificationRef>();
			for (int i = 0; i < specificationIds_.size(); i++) {
				ProductSpecificationRef ref = new ProductSpecificationRef(UUIDGenerator.getUUID());
				ref.setProductId(productId);
				ref.setSpecificationId(specificationIds_.get(i));
				refs.add(ref);
			}
			if (CollectionUtils.isNotEmpty(refs)) {
				productSpecificationRefDao.insertRefsBatch(refs);
			}
			if (CollectionUtils.isNotEmpty(oldIds_)) {
				productSpecificationRefDao.deleteBatch(productId, oldIds_);
			}
		}
		Map<ProductParameter, String> parametersValue = product.getParameterValue();
		if (MapUtils.isNotEmpty(parametersValue)) {
			// 需要优化
			Set<Entry<ProductParameter, String>> entrySet = parametersValue.entrySet();
			List<String> parameterIds = productParameterRefDao.queryParameterIdsByProductId(productId);
			for (Entry<ProductParameter, String> entry : entrySet) {
				String parameterId = entry.getKey().getId();
				String parameterValue = entry.getValue();
				ProductParameterRef ref = new ProductParameterRef();
				ref.setProductId(productId);
				ref.setParameterValue(parameterValue);
				ref.setParameterId(parameterId);
				if (parameterIds.contains(parameterId)) {
					productParameterRefDao.update(ref);
					parameterIds.remove(parameterId);
				} else {
					ref.setId(UUIDGenerator.getUUID());
					productParameterRefDao.insert(ref);
				}
			}
			if (CollectionUtils.isNotEmpty(parameterIds)) {
				productParameterRefDao.deleteFrom(productId, parameterIds);
			}
		} else {
			productParameterRefDao.deleteFrom(productId, null);
		}
		Map<String, ProductPropertyRef> propertyRefMap = product.getPropertyRefMap();
		if (MapUtils.isEmpty(propertyRefMap)) {
			productPropertyRefDao.deleteFrom(productId, null);
		} else {
			Set<Entry<String, ProductPropertyRef>> entrySet = propertyRefMap.entrySet();
			List<String> propertyIds = productPropertyRefDao.queryPropertyIdsByProductId(productId);
			for (Entry<String, ProductPropertyRef> entry : entrySet) {
				ProductPropertyRef ref = entry.getValue();
				String propertyId = ref.getPropertyId();
				if (propertyIds.contains(propertyId)) {
					propertyIds.remove(propertyId);
					continue;
				}
				ref.setId(UUIDGenerator.getUUID());
				productPropertyRefDao.insert(ref);
			}
			if (CollectionUtils.isNotEmpty(propertyIds)) {
				productPropertyRefDao.deleteFrom(productId, propertyIds);
			}
		}
	}

	@Transactional(readOnly = false)
	public int deleteProduct(Product product) {
		String orderProductId[] = dao.findNotFinishAndNotCancel(new String[] { product.getId() });
		String marketableProduct[] = dao.findMarketableProduct(new String[] { product.getId() });
		List<String> la = new ArrayList<String>(Arrays.asList(new String[] { product.getId() }));
		List<String> lb = new ArrayList<String>(Arrays.asList(orderProductId));
		List<String> lc = new ArrayList<String>(Arrays.asList(marketableProduct));
		la.removeAll(lb);
		la.removeAll(lc);
		int i = la.size();
		if (i > 0) {
			dao.updateFlag(la.toArray(new String[la.size()]));
			String productId = product.getId();
			productImageRefDao.delete(new ProductImageRef(product));
			productParameterRefDao.deleteFrom(productId, null);
			productSpecificationRefDao.deleteBatch(productId, null);
			productPropertyRefDao.deleteFrom(productId, null);
			specialProductRefDao.batchDelete(la.toArray(new String[la.size()]));
			productAreaRefDao.batchDeleteArea(la.toArray(new String[la.size()]));
		}
		return i;
	}

	@Transactional(readOnly = false)
	public void deleteByGoodsId(String goodsId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("goodsId", goodsId);
		map.put("updateUserId", UserUtils.getUser().getUpdateBy().getId());
		productDao.updateDelFlagByMap(map);
		// productDao.deleteRelativeRef(map);
		productSpecificationGroupRefService.batchDeleteByGoodsId(goodsId);
	}

	@Transactional(readOnly = false)
	public Product findByProductNo(String productNo) {
		if (productNo == null) {
			return null;
		}
		try {
			return dao.findByProductNo(productNo);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 批量更新上架状态
	 */
	@Transactional(readOnly = false)
	public void updateMarketable(String[] pcIds, String[] appIds, String[] wxIds, String isMarketable) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (pcIds != null && pcIds.length > 0) {
			map.put("ids", pcIds);
			map.put("pcIsMarketable", isMarketable);
			dao.updateMarketable(map);
		}
		if (appIds != null && appIds.length > 0) {
			map.put("ids", appIds);
			map.put("appIsMarketable", isMarketable);
			dao.updateMarketable(map);
		}
		if (wxIds != null && wxIds.length > 0) {
			map.put("ids", wxIds);
			map.put("wxIsMarketable", isMarketable);
			dao.updateMarketable(map);
		}
	}

	@Transactional(readOnly = false)
	public int del(String[] ids) {
		String orderProductId[] = dao.findNotFinishAndNotCancel(ids);
		String marketableProduct[] = dao.findMarketableProduct(ids);
		List<String> la = new ArrayList<String>(Arrays.asList(ids));
		List<String> lb = new ArrayList<String>(Arrays.asList(orderProductId));
		List<String> lc = new ArrayList<String>(Arrays.asList(marketableProduct));
		la.removeAll(lb);
		la.removeAll(lc);
		int i = la.size();
		if (i > 0) {
			dao.updateFlag(la.toArray(new String[la.size()]));
			specialProductRefDao.batchDelete(la.toArray(new String[la.size()]));
			productAreaRefDao.batchDeleteArea(la.toArray(new String[la.size()]));
		}
		return i;
	}

	/**
	 * 根据货物id查询
	 * 
	 * @param goodsId
	 * @return
	 */
	public List<Product> queryProductsByGoodsId(String goodsId) {
		return dao.queryProductsByGoodsId(goodsId);
	}

	/**
	 * 根据条件更新商品
	 * 
	 * @param idsList
	 *            商品规格id组集合
	 * @param product
	 *            商品
	 * @param specificationGroupIds
	 *            规格组id集合
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = false) 
	public void updateProduct(List<List<String>> idsList, Product product, String specificationGroupIds)
			throws Exception {
		String goodsId = product.getGoods();
		if (Utils.isBlank(goodsId)) {
			goodsId = Utils.generatorUUIDNumber();
			product.setGoods(goodsId);
		}
		List<Product> products = queryProductsByGoodsId(goodsId);
		List<String> delIdList = new ArrayList<String>();
		List<String> unDoIdList = new ArrayList<String>();
		boolean isBlank = Utils.isBlank(idsList);
		boolean productsFlag = true;// 商品是否有规格标志
		Iterator<Product> its = products.iterator();
		while (its.hasNext()) {
			productsFlag = false;
			Product p = its.next();
			String pId = p.getId();
			// 商品规格中，删除商品，新加商品分组
			if (isBlank) {
				delIdList.add(pId);
				its.remove();
				continue;
			}
			List<ProductSpecificationRef> refs = p.getProductSpecificationRefs();
			List<String> specificationIds = new ArrayList<String>();
			for (ProductSpecificationRef ref : refs) {
				specificationIds.add(ref.getSpecificationId());
			}
			Iterator<List<String>> iterator = idsList.iterator();
			boolean hasFlag = false;
			while (iterator.hasNext()) {
				List<String> list = iterator.next();
				if (list.size() == specificationIds.size() && list.containsAll(specificationIds)) {
					unDoIdList.add(pId);
					its.remove();
					hasFlag = true;
					iterator.remove();
					break;
				}
			}
			if (!hasFlag) {
				delIdList.add(pId);
			}
		}
		List<String> productIds = new ArrayList<String>();
		List<String> groupIds = null;
		if (Utils.isNotBlank(specificationGroupIds)) {
			groupIds = Arrays.asList(specificationGroupIds.split(","));
		}
		if (idsList.isEmpty()) {
			update(product);
		} else {
			Iterator<List<String>> iterator = idsList.iterator();
			List<ParamBean<List<String>>> params = new ArrayList<ParamBean<List<String>>>();
			List<String> updateIds = new ArrayList<String>();
			while (iterator.hasNext()) {
				List<String> list = (List<String>) iterator.next();
				if (Utils.isBlank(products)) {
					if (productsFlag) {
						product.setProductSpecificationIds(list);
						update(product);
						params.add(new ParamBean<List<String>>(product.getId(), list));
						productsFlag = false;
						continue;
					}
					Product product_ = new Product();
					BeanUtils.copyProperties(product_, product);
					product_.setId(UUIDGenerator.getUUID());
					product_.setProductSpecificationIds(list);
					product_.setPcIsMarketable("0");
					save(product_);
					productIds.add(product_.getId());
					continue;
				}
				Product p = products.get(0);
				String pid = p.getId();
				productIds.add(pid);
				params.add(new ParamBean<List<String>>(pid, list));
				updateIds.add(pid);
				delIdList.remove(pid);
				products.remove(0);
			}
			if (Utils.isNotBlank(params)) {
				updateSpecificationIdsRef(params, updateIds);
			}
		}
		if (Utils.isNotBlank(delIdList)) {
			updateGoods(delIdList, Utils.generatorUUIDNumber());// 修改goodsId，前端需做调整
		}
		if (Utils.isNotBlank(groupIds)) {
			if (Utils.isBlank(productIds)) {
				productIds.add(product.getId());
			}
			productSpecificationGroupRefService.batchUpdateProductSpecificationGroupRefs(groupIds, productIds);
		}
	}

	/**
	 * 批量更新商品货物id
	 * 
	 * @param pIds
	 * @param goodsId
	 */
	public void updateGoods(List<String> pIds, String goodsId) {
		productDao.batchUpdateGoods(pIds, goodsId);
	}

	/**
	 * 批量更新商品规格关联关系
	 * 
	 * @param params
	 * @param pids
	 */
	public void updateSpecificationIdsRef(List<ParamBean<List<String>>> params, List<String> pids) {
		productDao.batchUpdateProductFullName(params);
		productSpecificationRefDao.batchDeleteByPids(pids);
		productSpecificationRefDao.updateSpecificationIdsRef(params);
	}
	
}