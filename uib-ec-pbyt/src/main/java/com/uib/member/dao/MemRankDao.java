/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/easypay_ec">easypay_ec</a> All rights reserved.
 */
package com.uib.member.dao;


/**
 * 会员等级(单表)DAO接口
 * @author kevin
 * @version 2015-05-29
 */
public interface MemRankDao  {
		
	 void queryMemRankByUserName(String userName) throws Exception;
}