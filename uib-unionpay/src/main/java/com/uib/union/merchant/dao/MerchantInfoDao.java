package com.uib.union.merchant.dao;

import com.uib.union.merchant.pojo.MerchantInfo;
import com.uib.core.exception.GenericException;

/**
   * 　商户Dao
 * @author kevin
 *
 */
public interface MerchantInfoDao {
	
	/**
	 * 根据商户CODE 获取商户基本信息
	 * @param mCode
	 * @return
	 * @throws GenericException
	 */
    MerchantInfo getMerchantInfoByMerchantCode(String mCode) throws GenericException;
}
