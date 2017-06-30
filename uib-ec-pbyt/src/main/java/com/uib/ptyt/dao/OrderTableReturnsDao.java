package com.uib.ptyt.dao;

import java.util.List;

import com.uib.common.annotation.MyBatisDao;
import com.uib.ptyt.entity.OrderTableReturnsDto;
import com.uib.ptyt.entity.OrderTableReturnsItemDto;

@MyBatisDao
public interface OrderTableReturnsDao {
    
	/**
	 * 查询退货单信息(单条)
	 * @param orderTableReturnsDto
	 * @return
	 * @throws Exception
	 */
	public OrderTableReturnsDto queryReturns(OrderTableReturnsDto orderTableReturnsDto) throws Exception;
	/**
	 * 插入退货信息
	 * @param orderTableReturnsDto
	 * @throws Exception
	 */
	public void insert(OrderTableReturnsDto orderTableReturnsDto) throws Exception;
	/**
	 * 插入退货项表信息
	 * @param orderTableReturnsItemDto
	 * @throws Exception
	 */
	public void insertReturnsItem(List<OrderTableReturnsItemDto> list) throws Exception;
}
