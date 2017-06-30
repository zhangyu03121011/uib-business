package com.uib.ptyt.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.uib.common.enums.ExceptionEnum;
import com.uib.common.mapper.JsonMapper;
import com.uib.common.web.Global;
import com.uib.mobile.dto.ReturnMsg;
import com.uib.product.entity.Product;
import com.uib.ptyt.constants.WechatConstant;
import com.uib.ptyt.entity.Special;
import com.uib.ptyt.entity.StoreDto;
import com.uib.ptyt.service.StoreService;
import com.uib.weixin.util.UserSession;

@RequestMapping("/store")
@Controller
public class StoreController {

	private static final Logger logger = Logger.getLogger(StoreController.class);

	@Autowired
	private StoreService storeService;
	@RequestMapping("/queryStore")
	@ResponseBody
	public StoreDto queryStore(String merchantNo) {
		logger.info("查询我的店铺……");
		//String merchantNo = (String) UserSession.getSession(WechatConstant.MERCHANT_ID);	   
	    if(StringUtils.isEmpty(merchantNo)){
	    	// merchantNo = "2cd77cd7ee4f4756a5517b14f37e39fa";
	    	merchantNo = (String) UserSession.getSession(WechatConstant.MERCHANT_ID);	
	    }
		logger.info("merchantNo"+merchantNo);
		try {
			return storeService.queryStore(merchantNo);
		} catch (Exception e) {
			logger.info(e.fillInStackTrace());
			e.printStackTrace();
			return null;
		}
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public int saveStore(StoreDto storeDto) {
		String merchantNo = (String) UserSession.getSession(WechatConstant.MERCHANT_ID);
		//String merchantNo = "1d19f56c12a7406696b7e47320f7cf97";
		logger.info("merchantNo"+merchantNo);
		logger.info("保存我的店铺：" + JsonMapper.toJsonString(storeDto));
		try {
			storeService.saveStore(storeDto, merchantNo);
			return 1;
		} catch (Exception e) {
			logger.info(e.fillInStackTrace());
			e.printStackTrace();
			return 0;
		}
	}

	@RequestMapping(value = "/queryProduct/pageIndex/{pageIndex}/pageSize/{pageSize}")
	@ResponseBody
	public List<Product> queryProduct(String storeId,
			@PathVariable Integer pageIndex, @PathVariable Integer pageSize,String rankId) {
		logger.info("查询热销商品：" + storeId);
		logger.info("pageIndex:" + pageIndex + ",pageSize:" + pageSize);
		 if(StringUtils.isEmpty(rankId)){
			 rankId = (String) UserSession.getSession(WechatConstant.RANK_ID);	
		    }
		//String rankId ="1";
		logger.info("rankId"+rankId);
		List<Product> list = new ArrayList<Product>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageIndex", pageIndex);
		map.put("pageSize", pageSize);
		map.put("storeId", storeId);
		map.put("rankId", rankId);
		map.put("imageUrl", Global.getConfig("upload.image.path"));
		try {
			list = storeService.queryProduct(map);
		} catch (Exception e) {
			logger.info(e.fillInStackTrace());
			e.printStackTrace();
			return list;
		}
		return list;
	}

	@RequestMapping(value = "/delProduct", method = RequestMethod.POST)
	@ResponseBody
	public int delProduct(String ids, String storeId) {
		logger.info("删除热销商品：" + ids + "/" + storeId);
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("ids", ids.split(","));
			map.put("storeId", storeId);
			storeService.delProduct(map);
			return 1;
		} catch (Exception e) {
			logger.info(e.fillInStackTrace());
			e.printStackTrace();
			return 0;
		}
	}

	@RequestMapping(value = "/redProduct", method = RequestMethod.POST)
	@ResponseBody
	public int redProduct(String ids, String storeId, String isRecommend) {
		logger.info("推荐热销商品：" + ids + "/" + storeId + "/" + isRecommend);
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("ids", ids.split(","));
			map.put("storeId", storeId);
			map.put("isRecommend", isRecommend);
			storeService.redProduct(map);
			return 1;
		} catch (Exception e) {
			logger.info(e.fillInStackTrace());
			e.printStackTrace();
			return 0;
		}
	}
	@RequestMapping(value = "/querySpecial1")
	@ResponseBody
	public List<Special> querySpecial1(String merchantNo) {
		logger.info("查询热门专题：");
		 if(StringUtils.isEmpty(merchantNo)){
			 merchantNo = (String) UserSession.getSession(WechatConstant.MERCHANT_ID);	
		    }
		//String merchantNo = "2cd77cd7ee4f4756a5517b14f37e39fa";
		logger.info("merchantNo"+merchantNo);
		List<Special> list = new ArrayList<Special>();
		Map<String, Object> map = new HashMap<String, Object>();		
		map.put("merchantNo", merchantNo);
		map.put("imageUrl", Global.getConfig("upload.image.path"));
		try {
			list = storeService.querySpecial1(map);
		} catch (Exception e) {
			logger.info(e.fillInStackTrace());
			e.printStackTrace();
			return list;
		}
		return list;
	}

	@RequestMapping(value = "/querySpecialList")
	@ResponseBody
	public ReturnMsg<Object> querySpecial(String page) {
		logger.info("分页查询热门专题：");
		ReturnMsg<Object> result = new ReturnMsg<Object>();
		List<Special> SpecialList = new ArrayList<Special>();
		String  merchantNo = (String) UserSession.getSession(WechatConstant.MERCHANT_ID);	
		//String  merchantNo = "d350eabf0f424ac39f8578562f562d78";	
		logger.info("merchantNo==========="+merchantNo);
		if (StringUtils.isEmpty(page)||StringUtils.isEmpty(merchantNo)) {
			result.setStatus(false);
			result.setCode(ExceptionEnum.param_not_null.getIndex());
			result.setMsg(ExceptionEnum.param_not_null.getValue());
			return result;
		}
		try {
			SpecialList = storeService.querySpecial(page,merchantNo);
			result.setStatus(true);
			result.setData(SpecialList);
		} catch (Exception e) {
			logger.error("分页查询热门专题失败!", e);
			result.setStatus(false);
			result.setCode(ExceptionEnum.business_logic_exception.getIndex());
			result.setMsg(ExceptionEnum.business_logic_exception.getValue());
		}
		return result;
	}

	@RequestMapping(value = "/delSpecial", method = RequestMethod.POST)
	@ResponseBody
	public ReturnMsg<Object> delSpecial(String id) {
		logger.info("单个专题删除开始=====");
		ReturnMsg<Object> result = new ReturnMsg<Object>();
		String	 merchantNo = (String) UserSession.getSession(WechatConstant.MERCHANT_ID);
		//String	 merchantNo="d350eabf0f424ac39f8578562f562d78";
		logger.info("merchantNo============="+merchantNo);
		if (StringUtils.isEmpty(id)||StringUtils.isEmpty(merchantNo)) {
			result.setStatus(false);
			result.setCode(ExceptionEnum.param_not_null.getIndex());
			result.setMsg(ExceptionEnum.param_not_null.getValue());
			return result;
		}
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", id);
			map.put("merchantNo", merchantNo);
			storeService.delSpecial(map);
			result.setStatus(true);
		} catch (Exception e) {
			logger.error("单个专题删除失败!", e);
			result.setStatus(false);
			result.setCode(ExceptionEnum.business_logic_exception.getIndex());
			result.setMsg(ExceptionEnum.business_logic_exception.getValue());
		}
		return result;
	}
}
