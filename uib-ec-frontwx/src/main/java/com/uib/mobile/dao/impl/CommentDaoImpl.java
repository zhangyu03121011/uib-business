package com.uib.mobile.dao.impl;

import org.springframework.stereotype.Repository;

import com.uib.core.dao.MyBatisDao;
import com.uib.mobile.dao.CommentDao;
import com.uib.mobile.dto.CommentDto;

/**
 * @Todo 商品评论
 * @date 2015年11月10日
 * @author Ly
 */
@Repository
public class CommentDaoImpl extends MyBatisDao<CommentDto> implements CommentDao {
	
	public void saveComment(CommentDto commentDto) {
		this.save("saveComment",commentDto);
	}
}
