package com.uib.mobile.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uib.mobile.dao.CommentDao;
import com.uib.mobile.dto.CommentDto;
import com.uib.mobile.service.CommentService;
import com.uib.product.dao.ProductCommentDao;

/**
 * @Todo 商品评论
 * @date 2015年11月10日
 * @author Ly
 */
@Service
public class CommentServiceImpl implements CommentService {
	@Autowired
	ProductCommentDao productCommentDao;
	
	@Autowired
	CommentDao commentDao;
	
	@Override
	public  Map<String,Object> findCountGroupByProductId(String productId){
		return productCommentDao.findCountGroupByProductId(productId);
	}
	
	@Override
	public  List<Map<String, Object>> findById(Map<String, Object> params){
		return productCommentDao.findById(params);
	}
	
	public void saveComment(CommentDto commentDto) {
		commentDao.saveComment(commentDto);
	}
	
	@Override
	public  Map<String,Object> queryComment(Map<String, Object> params){
		return productCommentDao.queryComment(params);
	}

	@Override
	public List<Map<String, Object>> queryLast5Comment(Map<String, Object> params) {
		return productCommentDao.queryLast5Comment(params);
	}
}
