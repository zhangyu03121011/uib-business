package com.uib.ptyt.dao;

import com.uib.common.annotation.MyBatisDao;
import com.uib.ptyt.entity.MemMerchantDto;

@MyBatisDao
public interface MemMerchantDao {
    
	/**
	 * 插入商户信息
	 * @param memMerchantDto
	 * @return
	 * @throws Exception
	 */
	public void insert(MemMerchantDto memMerchantDto) throws Exception;
	/**
	 * 更新商户信息
	 * @param memMerchantDto
	 * @return
	 * @throws Exception
	 */
	public void update(MemMerchantDto memMerchantDto) throws Exception;
	/**
	 * 查询商户信息
	 * @param memMerchantDto
	 * @return
	 * @throws Exception
	 */
	public MemMerchantDto queryMerchant(MemMerchantDto memMerchantDto) throws Exception;
}
