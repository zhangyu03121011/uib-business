package com.uib.ptyt.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.uib.common.annotation.MyBatisDao;
import com.uib.ptyt.entity.StoreProductDto;

@MyBatisDao
public interface PbytProductDao {
	
	/**
	 * 分页查询商品信息
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> findProductList(Map<String, Object> map) throws Exception;
	
	/**
	 * 分页查询商品信息(过滤掉已经添加到店铺的商品)
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> findProductList1(Map<String, Object> map) throws Exception;
	
	/**
	 * 根据商品层级查询商品分类信息
	 * @param grade
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> findProductCategoryList(String grade)throws Exception;
	
	/**
	 * 批量添加商品到我的店铺
	 * @param mapList
	 */
	public void addStoreProduct(@Param("id") String id, @Param("productIds")String[] productIds ,@Param("storeId")String storeId,@Param("createTime")Date createTime);
    /**
     * 根据用户ID查询店铺Id
     * @param map
     * @return
     * @throws Exception
     */
	public Map queryStoreByUserId(Map map)throws Exception;
	
	
	/**
	 * 查询店铺与区域的关联
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Map queryProductAdress(Map map)throws Exception;
}
