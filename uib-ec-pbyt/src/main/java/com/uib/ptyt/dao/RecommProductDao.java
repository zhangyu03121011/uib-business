package com.uib.ptyt.dao;

import java.util.List;
import java.util.Map;

import com.uib.common.annotation.MyBatisDao;
import com.uib.ptyt.entity.RecommProduct;

@MyBatisDao
public interface RecommProductDao {
	
	public void insert(RecommProduct recommProduct);
	
	public RecommProduct getRecommProduct(RecommProduct recommProduct);
	
	public void delete(String id);
	
	public List<RecommProduct> queryRecommProduct(Map map);
	
	public void updateViewCount(RecommProduct recommProduct);

	public  List<Map<String,Object>>  querRecommProductNum(RecommProduct recommProduct);
	
	public Integer checkOnly(String memberId,String productId) throws Exception;
}
