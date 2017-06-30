package com.uib.ptyt.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.uib.common.web.Global;
import com.uib.ptyt.dao.PbytProductDao;
import com.uib.ptyt.entity.StoreProductDto;
@Service
public class PbytProductService {

	@Autowired
	private PbytProductDao pbytProductDao;
	/**
	 * 分页查询商品信息
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> findProductList(String rankId,String productCategoryId ,String productCategoryName, Integer pageIndex,  Integer pageSize,String storeId) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		try {
//			int startSize = (Integer.parseInt(page) - 1) * 4;
//			int pageSize = 4;
			map.put("rankId", rankId);
			map.put("productCategoryId", productCategoryId);
			map.put("productCategoryName", productCategoryName);
			map.put("pageIndex", pageIndex);
			map.put("pageSize", pageSize);
			map.put("imageUrl", Global.getConfig("upload.image.path"));
			map.put("storeId", storeId);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pbytProductDao.findProductList(map);
	}
	
	/**
	 * 分页查询商品信息
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> findProductList1(String rankId,String productCategoryId ,String productCategoryName, Integer pageIndex,  Integer pageSize,String storeId) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		try {
//			int startSize = (Integer.parseInt(page) - 1) * 4;
//			int pageSize = 4;
			map.put("rankId", rankId);
			map.put("productCategoryId", productCategoryId);
			map.put("productCategoryName", productCategoryName);
			map.put("pageIndex", pageIndex);
			map.put("pageSize", pageSize);
			map.put("imageUrl", Global.getConfig("upload.image.path"));
			map.put("storeId", storeId);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pbytProductDao.findProductList1(map);
	}
	
	
	/**
	 * 根据商品层级查询商品分类信息
	 * @param grade
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> findProductCategoryList(String grade){
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		try {
			 mapList = pbytProductDao.findProductCategoryList(grade);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mapList;		
	}
	
	/**
	 * 批量添加商品到我的店铺
	 * @param mapList
	 */
	public void addStoreProduct(String[] productIds ,String storeId){
			String id = UUID.randomUUID().toString().replaceAll("-", "");	
			pbytProductDao.addStoreProduct(id,productIds,storeId,new Date());		
		
	}
   
	/**
     * 根据用户ID查询店铺Id
     * @param map
     * @return
     * @throws Exception
     */
	public Map queryStoreByUserId(Map map)throws Exception{
		return pbytProductDao.queryStoreByUserId(map);
	}
	
	/**
	 * 查询店铺与区域的关联
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Boolean queryProductAdress(String[] areaIds, String productId)throws Exception{
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("productId", productId);
		Boolean b=true;
		if (areaIds[1].equals("0")) {
			map.put("areaId", areaIds[0]);
			Map productAdress=pbytProductDao.queryProductAdress(map);
			if (null != productAdress) {
				if (null != productAdress.get("productId") && null != productAdress.get("areaId")) {
					b=true;
				}
			} else {
				b=false;
			}
		} else if(!areaIds[1].equals("0") && !areaIds[0].equals("0")){
			map.put("areaId", areaIds[1]);
			Map productAdress=pbytProductDao.queryProductAdress(map);
			if (null != productAdress) {
				if (null != productAdress.get("productId") && null != productAdress.get("areaId")) {
					b=true;
				}
			} else {
				b=false;
			}
		}else{
			b=false;
		}

		return b;
	}
}
