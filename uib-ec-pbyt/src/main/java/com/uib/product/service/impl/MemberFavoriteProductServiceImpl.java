package com.uib.product.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uib.product.dao.MemberFavoriteProductDao;
import com.uib.product.entity.MemberFavoriteProduct;
import com.uib.product.service.MemberFavoriteProductService;

@Service
public class MemberFavoriteProductServiceImpl implements MemberFavoriteProductService{
	
	@Autowired
	private MemberFavoriteProductDao memberFavoriteProductDao;
	
	@Override
	public List<Map<String,Object>> getMemberFavoriteProductsByMemberId(String memberId) throws Exception {
		return memberFavoriteProductDao.getMemberFavoriteProductsByMemberId(memberId);
	}
	@Override
	public void saveFavorite(MemberFavoriteProduct memberFavoriteProduct) throws Exception {
		memberFavoriteProductDao.saveFavorite(memberFavoriteProduct);
	}
	@Override
	public void deleteFavorite(String id) throws Exception {
		memberFavoriteProductDao.deleteFavorite(id);
	}
	@Override
	public MemberFavoriteProduct getMemberFavoriteProducts(String memberId, String productId)
			throws Exception {
		return memberFavoriteProductDao.getMemberFavoriteProducts(memberId, productId);
	}
	@Override
	public Integer getFavoriteCount(String memberId) throws Exception {
		return memberFavoriteProductDao.getFavoriteCount(memberId);
	}
}
