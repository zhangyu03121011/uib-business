package com.uib.ptyt.dao;

import java.util.List;
import java.util.Map;

import com.uib.common.annotation.MyBatisDao;
import com.uib.product.entity.Product;
import com.uib.ptyt.entity.Special;
import com.uib.ptyt.entity.StoreDto;
import com.uib.ptyt.entity.StoreMerchantDto;

@MyBatisDao
public interface StoreDao {

	StoreDto queryStore(String merchantNo);

	void insertStore(StoreDto storeDto);

	void insertStoreMerchant(StoreMerchantDto storeMerchantDto);

	void updateStore(StoreDto storeDto);

	List<Product> queryProduct(Map<String, Object> map);

	List<Special> querySpecial(Map<String, Object> map);
	
	List<Special> querySpecial1(Map<String, Object> map);

	void delProduct(Map<String, Object> map);

	void redProduct(Map<String, Object> map);

	void delSpecial(Map<String, Object> map);
    
	List<Product> queryProductList(Map<String, Object> map);
}
