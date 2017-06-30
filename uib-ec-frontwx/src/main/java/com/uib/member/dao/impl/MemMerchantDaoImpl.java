package com.uib.member.dao.impl;

import org.springframework.stereotype.Component;

import com.uib.core.dao.MyBatisDao;
import com.uib.member.dao.MemMerchantDao;
import com.uib.member.entity.MemMerchant;

@Component
public class MemMerchantDaoImpl extends MyBatisDao<MemMerchant> implements MemMerchantDao{

}
