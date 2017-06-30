package com.uib.ptyt.service;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.uib.common.web.Global;
import com.uib.ptyt.dao.CommDetailDao;
import com.uib.ptyt.entity.RecommOrderDto;

@Service
public class CommDetailService {

	@Autowired
	private CommDetailDao commDetailDao;

	
	 public List<RecommOrderDto> querRecommendList(Map<String, Object> map) throws Exception{
		   map.put("imageUrl", Global.getConfig("upload.image.path"));
		   return commDetailDao.querRecommendList(map);
		}
}
