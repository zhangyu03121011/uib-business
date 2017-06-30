package com.uib.mobile.service;

import java.util.List;
import java.util.Map;

import com.easypay.core.exception.GenericException;
import com.uib.mobile.dto.CommentDto;

/**
 * @Todo 商品评论
 * @date 2015年11月10日
 * @author Ly
 */
public interface CommentService {

	public Map<String,Object> findCountGroupByProductId(String productId);
	public List<Map<String, Object>> findById(Map<String, Object> params);
	public void saveComment(CommentDto commentDto) throws GenericException;
	public Map<String,Object> queryComment(Map<String, Object> params);
	public  List<Map<String, Object>> queryLast5Comment(Map<String, Object> params);
}
