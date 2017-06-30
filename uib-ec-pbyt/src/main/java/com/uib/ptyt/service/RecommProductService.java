package com.uib.ptyt.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uib.common.web.Global;
import com.uib.ptyt.dao.RecommProductDao;
import com.uib.ptyt.entity.RecommProduct;

/***
 * 推广记录service
 * @author zfan
 *
 */
@Service
public class RecommProductService {
	
	@Autowired
	private RecommProductDao recommProductDao;
	
	public void insert(RecommProduct recommProduct) throws Exception {
		recommProductDao.insert(recommProduct);
	}
	
	public RecommProduct getRecommProduct(RecommProduct recommProduct) throws Exception{
		return recommProductDao.getRecommProduct(recommProduct);
	}
	
	public void delete(String id) throws Exception {
		recommProductDao.delete(id);
	}
	
	public List<RecommProduct> queryRecommProduct(Map map){
		map.put("imageUrl", Global.getConfig("upload.image.path"));
		return recommProductDao.queryRecommProduct(map);
	}
	
	public void updateViewCount(RecommProduct recommProduct){
		recommProductDao.updateViewCount(recommProduct);;
	}
   
	public  List<Map<String,Object>>  querRecommProductNum(RecommProduct recommProduct) {
		 return recommProductDao.querRecommProductNum(recommProduct);
	}
	
	
	public Integer checkOnly(String memberId,String productId) throws Exception {
		 return recommProductDao.checkOnly(memberId,productId);
	}

}
