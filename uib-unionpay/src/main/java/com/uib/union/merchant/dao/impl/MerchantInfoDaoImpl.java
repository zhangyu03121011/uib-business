package com.uib.union.merchant.dao.impl;

import org.springframework.stereotype.Component;

import com.uib.core.dao.MyBatisDao;
import com.uib.core.exception.GenericException;
import com.uib.union.merchant.dao.MerchantInfoDao;
import com.uib.union.merchant.pojo.MerchantInfo;

@Component
public class MerchantInfoDaoImpl extends MyBatisDao<MerchantInfo> implements MerchantInfoDao{

	@Override
	public MerchantInfo getMerchantInfoByMerchantCode(String mCode)
			throws GenericException {
		return this.getUnique("getMerchantInfoByMerchantCode", mCode);
	}

}
