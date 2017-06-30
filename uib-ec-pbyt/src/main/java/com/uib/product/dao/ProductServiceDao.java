package com.uib.product.dao;

import java.util.List;
import java.util.Map;

import com.uib.common.bean.ParamBean;
import com.uib.common.enums.OperationType;
import com.uib.product.entity.Product;

public interface ProductServiceDao {
	/**
	 * 根据分类Id查询商品
	 * 
	 * @param ProductCategoryId
	 * @return
	 * @throws Exception
	 */
	public List<Product> queryProductByProductCategoryId(
			String ProductCategoryId) throws Exception;

	public List<Product> selectProductByBrand(String id, String flag,
			String productcategoryid) throws Exception;

	/**
	 * 根据商品id查询商品
	 * 
	 * @param productId
	 *            商品id
	 * @return
	 * @throws Exception
	 */
	public Product queryProductByProductId(String productId, String rankId);
	
	public Map<String, Object> queryProductIsMarketable4Mobile(String productId) throws Exception;

	/**
	 * 查询热销商品
	 * 
	 * @param ProductCategoryId
	 *            分类Id
	 * @return
	 * @throws Exception
	 */
	public List<Product> queryHotProductByProductCategoryId(
			String ProductCategoryId) throws Exception;

	/**
	 * 根据商品id查询单树分类id集合
	 * 
	 * @param productId
	 * @return
	 * @throws Exception
	 */
	public String queryCategoryIdsByProductId(String productId)
			throws Exception;

	public Integer selectProductByBrandAndCategory(String id, String categoryId)
			throws Exception;

	public Integer getAllProductNum(String id) throws Exception;

	public List<Product> getProductByNotInIds(List<String> ids)
			throws Exception;

	/**
	 * 更新商品
	 * 
	 * @param product
	 * @throws Exception
	 */
	public void update(Product product) throws Exception;

	/**
	 * 修改分配库存
	 * 
	 * @param id
	 * @param allocatedStock
	 * @throws Exception
	 */
	public void updateAllocatedStock(String id, Integer allocatedStock)
			throws Exception;

	/**
	 * 释放已分配内存
	 * 
	 * @param id
	 * @param number
	 * @throws Exception
	 */
	public void releaseAllocatedStock(String id, Integer number)
			throws Exception;

	/**
	 * 释放已分配内存
	 * 
	 * @param id
	 *            商品id
	 * @param cartItemId
	 *            购物车项Id
	 * @throws Exception
	 */
	public void releaseAllocatedStockByRelativeId(String id, String cartItemId)
			throws Exception;

	/**
	 * 根据条件查询
	 * 
	 * @param prodocutCategoryId
	 * @param brandId
	 * @param specificationIds
	 * @param parameterIds
	 * @param propertyIds
	 * @param hasStock
	 * @param shippingType
	 * @param orderField
	 * @param sdx
	 * @param pageSize
	 * @param pageIndex
	 * @return
	 * @throws Exception
	 */
	public List<Product> queryProducts(String prodocutCategoryId,
			String brandId, String specificationIds, String[] parameterIds,
			String[] propertyIds, Boolean hasStock, String shippingType,
			String orderField, String sdx, Integer pageSize, Integer pageIndex)
			throws Exception;

	/**
	 * 
	 * @Title: getProductListByProductNo @author sl @Description: 根据商品编号查询 @param @param
	 *         map @param @return 参数 @return List<Product> 返回类型 @throws
	 */
	public List<Product> getProductListByProductNo(Map<String, String> map);

	public List<Map<String, Object>> findListMobile();

	/**
	 * 查询商品参数
	 */
	public List<Map<String, Object>> getParameters(Map<String, Object> map);

	/**
	 * 根据商品ids数组查询存在的商品ids数组
	 * 
	 * @param ids
	 * @return
	 */
	public List<String> queryExistIdsByIds(String[] ids);

	List<Map<String, Object>> findByCategory4Mobile(Map<String, Object> map) throws Exception;

	/**
	 * 根据商品id 减库存数量
	 * 
	 * @param id
	 * @throws Exception
	 */
	void updateSubtractProductStock(Map<String, Object> map) throws Exception;

	/**
	 * 根据商品ID 减已分配库存数量
	 * 
	 * @param map
	 * @throws Exception
	 */
	void updateSubtractAllocatedStock(Map<String, Object> map) throws Exception;

	/**
	 * 更新分配库存
	 * 
	 * @param params
	 * @param operationType
	 * @throws Exception
	 */
	void updateAllocatedStockBatch(List<ParamBean> params,
			OperationType operationType) throws Exception;
	
	/**
	 * 根据商品分类查询商品
	 * @param map
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> findByCategoryWechat(Map<String, Object> map) throws Exception;
	
	/**
	 * 分页查询商品
	 * @param map
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> findByPageWechat(Map<String, Object> map) throws Exception;
	
	List<Map<String, Object>> findHomeProduct(Map<String, Object> map) throws Exception;
	
	List<Map<String, Object>> findByOrderAndPage(Map<String, Object> map) throws Exception;
	
	List<Map<String, Object>> findProductListCategoryAndPage(Map<String, Object> map) throws Exception;
	
	List<Map<String, Object>> findProdCategoryAndPageMoblie(Map<String, Object> map) throws Exception;
	
	/**
	 * 根据商品id查询商品规格组和规格信息
	 * @param productId
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> querySpecAndGroupSpecInfo(String productId) throws Exception;
	
	/**
	 * 根据产品id和规格id查询改该规格下能组合的规格和规格组信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> querySpecBySpecIdAndId(Map<String, Object> map) throws Exception;
	
	/**
	 * 根据产品id查询该产品同系列（通过goods识别）的全部产品及其规格
	 * @param productId
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> queryProductAndSpecInfoById(String productId) throws Exception;
	
	/**
	 * 根据产品id更新产品的销量
	 * @param map
	 */
	void updateProductSales(Map<String, Object> map)throws Exception;

}
