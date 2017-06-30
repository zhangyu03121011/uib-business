package com.uib.product.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.uib.common.web.Global;
import com.uib.product.dao.ProductCommentDao;

@Service
public class ProductCommentService{
	
	private static final Log logger = LogFactory.getLog(ProductCommentService.class);
	
	@Autowired
	ProductCommentDao productCommentDao;
	
	public  Map<String,Object> findCountGroupByProductId(String productId) throws Exception{
		return productCommentDao.findCountGroupByProductId(productId);
	}
	
	public  List<Map<String, Object>> findById(Map<String, Object> params) throws Exception{
		return productCommentDao.findById(params);
	}
	
	public  Map<String,Object> queryComment(Map<String, Object> params) throws Exception{
		return productCommentDao.queryComment(params);
	}

	public List<Map<String, Object>> queryLast5Comment(Map<String, Object> params) throws Exception{
		return productCommentDao.queryLast5Comment(params);
	}
	
	/**
	 * 分页查询商品评论
	 * @param productId 商品id
	 * @param page      页数
	 * @param Flag 		1-差评；2-中评；3-好评 
	 * @return
	 */
	public List<Map<String, Object>> queryCommentByPage(String productId, String page, String flag) throws Exception{
		logger.info("分页查询商品评论入参productId=" + productId + ",page=" + page + ",flag=" + flag);
		Map<String, Object> params = new HashMap<String, Object>();
		int startSize = (Integer.parseInt(page) - 1) * 4;
		int pageSize = 4;
		params.put("startSize", startSize);
		params.put("pageSize", pageSize);
		params.put("productId", productId);
		params.put("flag", flag);
		params.put("imageUrl", Global.getConfig("upload.image.path"));
		return productCommentDao.queryCommentByPage(params);
	}
	
	/**
	 * 获取口碑页面数据(评论不分页)
	 * @param productId
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	public ModelMap getEstimateData(String productId,ModelMap modelMap){
		logger.info("获取口碑页面数据入参productId=" + productId);
		Map<String, Object> params = new HashMap<String,Object>();
		try {
			//查询评论总数，好评总数，中评总数，差评总数，平均分
			Map<String,Object> avgCore = productCommentDao.findCountGroupByProductId(productId);
			if(null == avgCore){
				Map<String,Object> avg = new HashMap<String,Object>();
				avg.put("hp", "0");
				avg.put("cp", "0");
				avg.put("zp", "0");
				avg.put("all","0");
				avg.put("scorePercent", "width:0%;");
				avg.put("hpRate", "0");
				avg.put("zongp", "0");
				modelMap.put("avgCore", avg);
				modelMap.put("hpList", null);
				modelMap.put("zpList", null);
				modelMap.put("cpList", null);
			}else{
				float fhpRate = Float.parseFloat(avgCore.get("hp").toString()) / Float.parseFloat(avgCore.get("all").toString()) * 100;
				BigDecimal bhpRate = new BigDecimal(fhpRate);
				//好评率采用四舍五入保留2位小数
				double hpRate = bhpRate.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(); 
				avgCore.put("hpRate", hpRate);
				
				//综评分数保留2位小数
				float fzongp = Float.parseFloat(avgCore.get("zongp").toString());
				BigDecimal bzongp = new BigDecimal(fzongp);
				double zongp = bzongp.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(); 
				avgCore.put("zongp", zongp);
				
				params.put("productId", productId);
				params.put("flag", 3);
				//查询好评
				List<Map<String, Object>> hpList = this.findById(params);
				params.put("flag", 2);
				//查询中评
				List<Map<String, Object>> zpList = this.findById(params);
				params.put("flag", 1);
				//查询差评
				List<Map<String, Object>> cpList = this.findById(params);
				modelMap.put("avgCore", avgCore);
				modelMap.put("hpList", hpList);
				modelMap.put("zpList", zpList);
				modelMap.put("cpList", cpList);
			}
			
		} catch (Exception e) {
			logger.error("初始化用户口碑页面数据异常",e);
		}
		return modelMap;
	}
	
	/**
	 * 获取口碑页面数据(评论分页)
	 * @param productId
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> getEstimateDataByPage(String productId,String page){
		logger.info("分页获取口碑页面数据入参productId=" + productId + ",page=" + page);
		Map<String,Object> modelMap = new HashMap<String,Object>();
		
		try {
			//查询评论总数，好评总数，中评总数，差评总数，平均分
			Map<String,Object> avgCore = productCommentDao.findCountGroupByProductId(productId);
			if(null == avgCore){
				Map<String,Object> avg = new HashMap<String,Object>();
				avg.put("hp", "0");
				avg.put("cp", "0");
				avg.put("zp", "0");
				avg.put("all","0");
				avg.put("scorePercent", "width:0%;");
				avg.put("hpRate", "0");
				avg.put("zongp", "0");
				modelMap.put("avgCore", avg);
				modelMap.put("hpList", null);
				modelMap.put("zpList", null);
				modelMap.put("cpList", null);
			}else{
				float fhpRate = Float.parseFloat(avgCore.get("hp").toString()) / Float.parseFloat(avgCore.get("all").toString()) * 100;
				BigDecimal bhpRate = new BigDecimal(fhpRate);
				//好评率采用四舍五入保留2位小数
				double hpRate = bhpRate.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(); 
				avgCore.put("hpRate", hpRate);
				
				//综评分数保留2位小数
				float fzongp = Float.parseFloat(avgCore.get("zongp").toString());
				BigDecimal bzongp = new BigDecimal(fzongp);
				double zongp = bzongp.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(); 
				avgCore.put("zongp", zongp);
				
				int startSize = (Integer.parseInt(page) - 1) * 4;
				int pageSize = 4;
				Map<String, Object> params = new HashMap<String,Object>();
				params.put("startSize", startSize);
				params.put("pageSize", pageSize);
				params.put("productId", productId);
				params.put("flag", 3);
				//查询好评
				List<Map<String, Object>> hpList = productCommentDao.queryCommentByPage(params);
				params.put("flag", 2);
				//查询中评
				List<Map<String, Object>> zpList = productCommentDao.queryCommentByPage(params);
				params.put("flag", 1);
				//查询差评
				List<Map<String, Object>> cpList = productCommentDao.queryCommentByPage(params);
				modelMap.put("avgCore", avgCore);
				modelMap.put("hpList", hpList);
				modelMap.put("zpList", zpList);
				modelMap.put("cpList", cpList);
				modelMap.put("productId", productId);
			}
			
		} catch (Exception e) {
			logger.error("分页初始化用户口碑页面数据异常",e);
		}
		return modelMap;
	}
}
