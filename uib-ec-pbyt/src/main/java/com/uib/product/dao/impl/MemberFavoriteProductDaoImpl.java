/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/easypay_ec">easypay_ec</a> All rights reserved.
 */
package com.uib.product.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.uib.common.web.Global;
import com.uib.core.dao.MyBatisDao;
import com.uib.product.dao.MemberFavoriteProductDao;
import com.uib.product.entity.MemberFavoriteProduct;
import com.uib.serviceUtils.Utils;

/**
 * 收藏夹DAO接口
 */
@Component
public class MemberFavoriteProductDaoImpl  extends MyBatisDao<MemberFavoriteProduct> implements MemberFavoriteProductDao{

	@Override
	public List<Map<String,Object>> getMemberFavoriteProductsByMemberId(String memberId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberId", memberId);
		map.put("imageUrl", Global.getConfig("upload.image.path"));
		return this.getList("getMemberFavoriteProductsByMemberId", map);
	}

	@Override
	public void saveFavorite(MemberFavoriteProduct memberFavoriteProduct) throws Exception {
		this.save("saveFavorite", memberFavoriteProduct);
	}

	@Override
	public void deleteFavorite(String id) throws Exception {
		this.update("delete", id);
	}

	@Override
	public MemberFavoriteProduct getMemberFavoriteProducts(String memberId,String productId) throws Exception {
		Map<String,String> map = new HashMap<String,String>();
		map.put("memberId", memberId);
		map.put("productId", productId);
		Object result = this.getObjectValue("getFavoriteProductByMap",map);
		return Utils.isBlank(result)?null:(MemberFavoriteProduct)result;
	}

	@Override
	public Integer getFavoriteCount(String memberId) throws Exception {
		Integer object = (Integer) super.getObjectValue("getFavoriteCount", memberId);
		return object;
	}
	
}