package com.uib.product.service;

import java.util.List;
import java.util.Map;

import com.uib.common.bean.ParamBean;
import com.uib.common.enums.OperationType;
import com.uib.product.entity.Product;
import com.uib.product.entity.ProductComment;

public interface ProductService {

	/**
	 * 根据分类Id查询商品集合
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
	public Product queryProductByProductId(String productId,String bankId) throws Exception;

	public Product findById(String productId,String rankId);
	
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

	/**
	 * 查询对应品牌和分类的产品数量
	 * 
	 * @param id
	 * @param categoryId
	 * @return
	 * @throws Exception
	 */
	public Integer selectProductByBrandAndCategory(String id, String categoryId)
			throws Exception;

	public Integer getAllProductNum(String id) throws Exception;

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
	 *            商品id
	 * @param cartId
	 *            购物车Id
	 * @throws Exception
	 */
	public void releaseAllocatedStockByCartId(String id, String cartId)
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
	public void releaseAllocatedStockByCartItemId(String id, String cartItemId)
			throws Exception;

	/**
	 * 根据条件查询商品列表
	 * 
	 * @param prodocutCategoryId
	 * @param brandId
	 * @param specificationIds
	 * @param parameterId
	 * @param propertyId
	 * @param hasStock
	 * @param shippingType
	 * @param orderType
	 * @param pageSize
	 * @param pageIndex
	 * @return
	 * @throws Exception
	 */
	public List<Product> queryProducts(String prodocutCategoryId,
			String brandId, String specificationIdsStr, String parameterId,
			String propertyId, Boolean hasStock, String shippingType,
			String orderType, Integer pageSize, Integer pageIndex)
			throws Exception;

	public List<Product> getProductListByProductNo(Map<String, String> map);

	public List<Map<String, Object>> findListMobile();

	/**
	 * 查询商品参数
	 */
	public List<Map<String, Object>> getParameters(Map<String, Object> map);

	/**
	 * 根据ids数组查询正确的ids数组
	 * 
	 * @param ids
	 * @return
	 */
	public List<String> queryCorrectIdsByIds(String[] ids);

	void insertSelective(ProductComment record);// 保存评论

	/**
	 * 
	 * @Title: findByCategory
	 * @author sl 根据分类查询商品
	 */

	public List<Map<String, Object>> findByCategory4Mobile(Map<String, Object> map) throws Exception ;

	/**
	 * 根据商品id 减对应购买商品数量的库存
	 * 
	 * @param number
	 * @param id
	 * @throws Exception
	 */
	void updateSubtractProductStock(Integer number, String id) throws Exception;

	/**
	 * 根据商品ID 减已分配库存数量
	 * 
	 * @param map
	 * @throws Exception
	 */
	void updateSubtractAllocatedStock(Integer number, String id)
			throws Exception;

	/**
	 * 更新分配库存
	 * 
	 * @param params
	 * @throws Exception
	 */
	void updateAllocatedStockBatch(List<ParamBean> params,OperationType operationType) throws Exception;
	/**
	 * 
	* @Title: findProductCommentByProductId 
	* @author sl 
	   查询商品评论数
	 */
	List<Map<String, Object>> findProductCommentByProductId(Map<String, Object> params);
	
	/**
	 * 根据商品分类分页查询商品信息
	 * @param categoryId
	 * @param page
	 * @author chengw
	 * @return
	 */
	public List<Map<String, Object>> findByCategoryWechat(String categoryId,String page) throws Exception;
	
	public List<Map<String, Object>> findByOrderAndPage(String orderparam,String page,boolean down,String productCategoryName) throws Exception;
	
	public List<Map<String, Object>> findProductListCategoryAndPage(String orderparam,String page,boolean down,String categoryId,List<Object> list) throws Exception;
	
	public List<Map<String, Object>> findProdCategoryAndPageMoblie(String orderparam,String page,boolean down,String categoryId,List<Object> list,String proCategoryName) throws Exception;
	/**
	 * 分页查询商品信息
	 * @param page
	 * @author chengw
	 * @return
	 */
	public List<Map<String, Object>> findByPageWechat(String page) throws Exception;
	public List<Map<String, Object>> findHomeProduct(String page) throws Exception;
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
	 * 根据产品id更新产品的销量
	 * @param map
	 */
	void updateProductSales(String id,Integer sales)throws Exception;
	
	/**
	 * 根据商品id和规格数组，查询此规格对应的产品id
	 * @param productId
	 * @param specIds
	 * @return
	 * @throws Exception
	 */
	String queryProductIdByIdAndSpecIds(String productId,String[] specIds) throws Exception;
}
