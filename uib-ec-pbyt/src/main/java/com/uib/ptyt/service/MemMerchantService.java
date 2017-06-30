package com.uib.ptyt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.uib.ptyt.dao.MemMerchantDao;
import com.uib.ptyt.entity.MemMerchantDto;

@Service
public class MemMerchantService {

	@Autowired
	private MemMerchantDao memMerchantDao;
	
	/**
	 * 插入商户信息
	 * @param memMerchantDto
	 * @return
	 * @throws Exception
	 */
	public void insertMemMerchant(MemMerchantDto memMerchantDto) throws Exception{
		memMerchantDao.insert(memMerchantDto);
		return;
	}
	/**
	 * 更新商户信息
	 * @param memMerchantDto
	 * @return
	 * @throws Exception
	 */
	public void updateMemMerchant(MemMerchantDto memMerchantDto) throws Exception{
		memMerchantDao.update(memMerchantDto);
	}
	/**
	 * 查询商户信息(可带条件)
	 * @param memMerchantDto
	 * @return
	 * @throws Exception
	 */
	public MemMerchantDto queryMerchant(MemMerchantDto memMerchantDto) throws Exception{
		return memMerchantDao.queryMerchant(memMerchantDto);
	}
}
