package com.uib.ptyt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uib.ptyt.dao.MemRankDao;
import com.uib.ptyt.entity.MemRankDto;


@Service
public class MemRankService {

	@Autowired
	private MemRankDao memRankDao;
	
	public List<MemRankDto> queryMemRank() throws Exception{

		return memRankDao.queryMemRank();
	} 
}
