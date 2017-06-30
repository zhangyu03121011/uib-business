package com.uib.mobile.dao;

import com.uib.mobile.dto.CommentDto;

/**
 * @Todo 商品评论
 * @date 2015年11月10日
 * @author Ly
 */
public interface CommentDao {
	public void saveComment(CommentDto commentDto);
}
