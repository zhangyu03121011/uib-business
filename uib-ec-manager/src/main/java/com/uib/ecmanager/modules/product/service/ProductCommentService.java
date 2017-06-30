package com.uib.ecmanager.modules.product.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uib.ecmanager.modules.product.dao.ProductCommentDao;
import com.uib.ecmanager.modules.product.entity.ProductComment;

@Service
public class ProductCommentService {
	/**
	 * 日志记录器
	 */
	private Logger log = LoggerFactory.getLogger("rootLogger");
	@Autowired
	private ProductCommentDao productCommentDao;
	
	@Transactional()
	public List<ProductComment> queryProductComment(String productId){
		List<ProductComment> comments = productCommentDao.selectByProductId(productId);
		return comments;
	}
	
}
