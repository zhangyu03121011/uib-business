package com.uib.ptyt.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uib.common.web.Global;
import com.uib.product.entity.Product;
import com.uib.ptyt.dao.StoreDao;
import com.uib.ptyt.entity.Special;
import com.uib.ptyt.entity.StoreDto;
import com.uib.ptyt.entity.StoreMerchantDto;

@Service
public class StoreService {

	@Autowired
	private StoreDao storeDao;

	public StoreDto queryStore(String merchantNo) {
		return storeDao.queryStore(merchantNo);
	}

	@Transactional(readOnly = false)
	public void saveStore(StoreDto storeDto, String merchantNo) throws Exception{
		if (StringUtils.isEmpty(storeDto.getId())) {
			String storeId = UUID.randomUUID().toString().replaceAll("-", "");
			storeDto.setId(storeId);
			storeDao.insertStore(storeDto);

			StoreMerchantDto storeMerchantDto = new StoreMerchantDto();
			storeMerchantDto.setId(UUID.randomUUID().toString()
					.replaceAll("-", ""));
			storeMerchantDto.setStoreId(storeId);
			storeMerchantDto.setMerchantId(merchantNo);
			storeDao.insertStoreMerchant(storeMerchantDto);
		} else {
			storeDao.updateStore(storeDto);
		}
	}

	public List<Product> queryProduct(Map<String, Object> map) {
		return storeDao.queryProduct(map);
	}

	public List<Special> querySpecial(String page,String merchantNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		int startSize = (Integer.parseInt(page) - 1) * 2;
		int pageSize = 2;
		map.put("startSize", startSize);
		map.put("pageSize", pageSize);
		map.put("merchantNo", merchantNo);
		map.put("imageUrl", Global.getConfig("upload.image.path"));
		return storeDao.querySpecial(map);
	}
	
	public List<Special> querySpecial1(Map<String, Object> map) {
		return storeDao.querySpecial1(map);
	}

	public void delProduct(Map<String, Object> map) {
		storeDao.delProduct(map);
	}

	public void redProduct(Map<String, Object> map) {
		storeDao.redProduct(map);
	}

	public void delSpecial(Map<String, Object> map) {
		storeDao.delSpecial(map);
	}
    
	public List<Product> queryProductList (Map<String, Object> map){
		return storeDao.queryProductList(map);
	}
}
