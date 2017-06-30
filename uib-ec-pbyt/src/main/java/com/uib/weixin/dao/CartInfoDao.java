package com.uib.weixin.dao;

import java.util.List;
import java.util.Map;

import com.uib.weixin.dto.CartInfo4Wechat;

/**
 * @todo   
 * @author chengw
 * @date   2015年12月29日
 * @time   下午1:37:54
 */
public interface CartInfoDao {

	/**
	 * 通过用户名称查询购物车信息
	 * @param parm
	 * @return
	 * @throws Exception
	 */
	public List<CartInfo4Wechat> queryCartByUserName(Map<String,Object> parm) throws Exception;
	
	/*
	 * 根据cartItemid删除选中的product
	 */
	public void deleteByCartItemId(List<String> list) throws Exception;
}


