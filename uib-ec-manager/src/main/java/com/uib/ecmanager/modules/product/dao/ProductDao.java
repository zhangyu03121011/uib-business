/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.product.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.uib.ecmanager.common.persistence.CrudDao;
import com.uib.ecmanager.common.persistence.annotation.MyBatisDao;
import com.uib.ecmanager.modules.product.entity.ParamBean;
import com.uib.ecmanager.modules.product.entity.Product;
import com.uib.ecmanager.modules.product.entity.ProductSpecificationRef;

/**
 * 商品DAO接口
 * 
 * @author gaven
 * @version 2015-06-01
 */
@MyBatisDao
public interface ProductDao extends CrudDao<Product> {
	/**
	 * 根据商品编号查找商品
	 * 
	 * @param productNo
	 *            商品编号(忽略大小写)
	 * @return 商品，若不存在则返回null
	 */
	Product findByProductNo(String productNo);

	/**
	 * 批量更新上架状态
	 */
	public void updateMarketable(Map<String, Object> map);

	/**
	 * 批量删除
	 */
	public void updateFlag(String[] ids);

	/**
	 * 查询商品表 不是 已完成 或者已取消的订单
	 */
	public String[] findNotFinishAndNotCancel(String[] ids);

	/**
	 * 查询上架商品id
	 */
	public String[] findMarketableProduct(String[] ids);

	/**
	 * 根据条件伪删除
	 * 
	 * @param map
	 */
	public void updateDelFlagByMap(Map<String, Object> map);

	/**
	 * 根据条件删除商品相关属性
	 * 
	 * @param map
	 */
	// public void deleteRelativeRef(Map<String, Object> map);

	/**
	 * 根据货物id查询
	 * 
	 * @param goodsId
	 * @return
	 */
	public List<Product> queryProductsByGoodsId(@Param("goodsId") String goodsId);

	/**
	 * 批量更新商品货物id
	 * 
	 * @param pIds
	 * @param goodsId
	 */
	public void batchUpdateGoods(@Param("pIds") List<String> pIds, @Param("goodsId") String goodsId);

	/**
	 * 根据条件批量更新商品全名
	 * 
	 * @param params
	 */
	public void batchUpdateProductFullName(@Param("params") List<ParamBean<List<String>>> params);

	/**
	 * 判断分类下是否有商品
	 * 
	 * @param prodcutCatetoryId
	 *            分类id
	 * @return
	 */
	public int countProductInProductCategory(@Param("productCategoryId") String prodcutCatetoryId);

	/**
	 * 根据分类删除商品
	 * 
	 * @param prodcutCatetoryId
	 */
	public void batchDeleteByProductCategoryId(@Param("productCategoryId") String prodcutCatetoryId);
	
	/**
	 * 根据商品id查询商品价格与会员等级
	 * @param productId
	 * @return
	 */
	public Product findProductpriceAndMemrank(@Param("productId") String productId);

}