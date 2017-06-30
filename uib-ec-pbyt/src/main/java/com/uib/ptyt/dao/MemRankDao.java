package com.uib.ptyt.dao;

import java.util.List;

import com.uib.common.annotation.MyBatisDao;
import com.uib.ptyt.entity.MemRankDto;

@MyBatisDao
public interface MemRankDao {
    
	List<MemRankDto> queryMemRank() throws Exception;
}
