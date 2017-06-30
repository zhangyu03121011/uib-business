/**
 * Copyright &copy; 2014-2016  All rights reserved.
 *
 * Licensed under the 深圳嘉宝易汇通科技发展有限公司 License, Version 1.0 (the "License");
 * 
 */
package com.uib.weixin.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.uib.ad.service.AdvertisementService;
import com.uib.base.BaseController;
import com.uib.cart.service.CartItemService;
import com.uib.common.enums.ExceptionEnum;
import com.uib.common.utils.WebUtil;
import com.uib.common.web.Global;
import com.uib.mobile.dto.Category4Mobile;
import com.uib.mobile.dto.ReturnMsg;
import com.uib.product.entity.Product;
import com.uib.product.entity.ProductCategory;
import com.uib.product.entity.ProductImageRef;
import com.uib.product.service.ProductCategoryService;
import com.uib.product.service.ProductImageRefService;
import com.uib.product.service.ProductService;
import com.uib.product.service.impl.ProductCommentService;
import com.uib.product.service.impl.ProductPropertyService;
import com.uib.ptyt.constants.WechatConstant;
import com.uib.ptyt.service.MemMemberService;
import com.uib.serviceUtils.Utils;
import com.uib.weixin.constant.WxConstant;
import com.uib.weixin.util.UserSession;

/**
 * 微信端，商品后台
 * @author chengw
 * 
 */
@Controller
@RequestMapping("/wechat/product")
public class WechatProductController extends BaseController {
	
	private Logger logger = LoggerFactory.getLogger(WechatProductController.class);
	
	@Autowired
	private ProductService productServiceImpl;
	
	@Autowired
	private ProductImageRefService productImageRefService;
	
	@Autowired
	private ProductCommentService commentService;
	
	@Autowired
	private AdvertisementService advertisementService;
	
	@Autowired
	private CartItemService cartItemService;
	
	@Autowired
	private ProductCategoryService productCategoryService;
	
	@Autowired
	private ProductCategoryService productCategoryServiceimpl;
	
	@Autowired
	private ProductPropertyService productPropertyService;
	@Autowired
	private MemMemberService memMemberService;

	/**
	 * 根据分类分页查询商品
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping("/findByCategoryAndPage")
	public ReturnMsg<List<Map<String, Object>>> findByCategory(@RequestParam String categoryId, @RequestParam String page){
		logger.info("微信后台：根据分类分页查询商品入参categoryId=" + categoryId + ",page=" + page);
		ReturnMsg<List<Map<String, Object>>> result = new ReturnMsg<List<Map<String, Object>>>();
		if (StringUtils.isEmpty(categoryId)) {
			result.setStatus(false);
			result.setCode(ExceptionEnum.param_not_null.getIndex());
			result.setMsg(ExceptionEnum.param_not_null.getValue());
			return result;
		}
		if (StringUtils.isEmpty(page)) {
			result.setStatus(false);
			result.setCode(ExceptionEnum.param_not_null.getIndex());
			result.setMsg(ExceptionEnum.param_not_null.getValue());
			return result;
		}
		try {
			result.setData(productServiceImpl.findByCategoryWechat(categoryId,page));
		} catch (Exception e) {
			logger.error("微信后台：根据分类分页查询商品失败!", e);
			result.setCode(ExceptionEnum.business_logic_exception.getIndex());
			result.setMsg(ExceptionEnum.business_logic_exception.getValue());
		}
		return result;
	}
	
	/**
	 * 分页 查询商品
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping("/findListByPage")
	public ReturnMsg<List<Map<String, Object>>> findByPage(@RequestParam String page){
		logger.info("微信后台：分页查询商品入参page=" + page);
		ReturnMsg<List<Map<String, Object>>> result = new ReturnMsg<List<Map<String, Object>>>();
		if (StringUtils.isEmpty(page)) {
			result.setStatus(false);
			result.setCode(ExceptionEnum.param_not_null.getIndex());
			result.setMsg(ExceptionEnum.param_not_null.getValue());
			return result;
		}
		try {
			result.setData(productServiceImpl.findByPageWechat(page));
		} catch (Exception e) {
			logger.error("微信后台：分页查询商品失败!", e);
			result.setCode(ExceptionEnum.business_logic_exception.getIndex());
			result.setMsg(ExceptionEnum.business_logic_exception.getValue());
		}
		return result;
	}
	
	
	/**
	 * 分页 查询首页精品产品
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping("/findHomeProduct")
	public ReturnMsg<List<Map<String, Object>>> findHomeProduct(@RequestParam String page){
		logger.info("微信后台：分页查询商品入参page=" + page);
		ReturnMsg<List<Map<String, Object>>> result = new ReturnMsg<List<Map<String, Object>>>();
		if (StringUtils.isEmpty(page)) {
			result.setStatus(false);
			result.setCode(ExceptionEnum.param_not_null.getIndex());
			result.setMsg(ExceptionEnum.param_not_null.getValue());
			return result;
		}
		try {
			List<Map<String, Object>> homeProductList = productServiceImpl.findHomeProduct(page);
			for (Map<String, Object> map : homeProductList) {
				Object price = map.get("price");
				if(null==price){
					price = Double.valueOf("0.00");
				}
				map.remove("price");
				map.put("price",String.format("%.2f", com.uib.common.utils.StringUtils.numberFormat(price.toString())));
				
				Object marketPrice = map.get("marketPrice");
				if(null==marketPrice){
					marketPrice = Double.valueOf("0.00");
				}				
				map.remove("marketPrice");
				map.put("marketPrice", String.format("%.2f", com.uib.common.utils.StringUtils.numberFormat(marketPrice.toString())));
			}
			result.setData(homeProductList);
		} catch (Exception e) {
			logger.error("微信后台：分页查询商品失败!", e);
			result.setCode(ExceptionEnum.business_logic_exception.getIndex());
			result.setMsg(ExceptionEnum.business_logic_exception.getValue());
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/findProductListCategoryAndPage")
	public ReturnMsg<Map<String,Object>> findProductListCategoryAndPage(String orderparam,String page,boolean down,String categoryId,String level,HttpServletRequest request,HttpServletResponse response){
		String name="";
		logger.info("微信后台：按时间、价格、销量查询商品列表入参orderparam=" + orderparam + ",page=" + page+",down="+down);
		ReturnMsg<Map<String,Object>> result = new ReturnMsg<Map<String,Object>>();
		Map<String,Object> reMap = new HashMap<String, Object>();
		if(Utils.isObjectsBlank(orderparam,page,down)){
			result.setStatus(false);
			result.setCode(ExceptionEnum.param_not_null.getIndex());
			result.setMsg(ExceptionEnum.param_not_null.getValue());
			return result;
		}
		try {
			if(categoryId.equals("null"))
			{
				categoryId="";
			} else {
				ProductCategory productCategory =productCategoryService.getProductCategory(categoryId);
				name = productCategory.getName();
			}
			List<Object> list = new ArrayList<Object>();
			if(StringUtils.isNotEmpty(categoryId) && ("1".equals(level) || "2".equals(level)))
			{
				//查询一级下面所有的分类id
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("parentId", categoryId);
				String imageUrl = Global.getConfig("upload.image.path");
				map.put("imageUrl", imageUrl);
				List<Category4Mobile> categorys = productCategoryServiceimpl.querySubCategory4Mobile(map);
				if("1".equals(level)) {
					for (Category4Mobile category4Mobile : categorys) {
						List<Category4Mobile> subCategorys = category4Mobile.getSubCategorys();
						for (Category4Mobile category4Mobile2 : subCategorys) {
							list.add(category4Mobile2.getId());
						}
					}
				} else if("2".equals(level)) {
					for (Category4Mobile category4Mobile : categorys) {
							list.add(category4Mobile.getId());
					}
				}
			} else {
				list.add(categoryId);
			}
			logger.info("微信后台：按产品分类categoryId="+categoryId+",name="+name);
			List<Map<String,Object>> products = productServiceImpl.findProductListCategoryAndPage(orderparam,page,down,categoryId,list);
			reMap.put("name", name);
			reMap.put("products", products);
			result.setData(reMap);
		} catch (Exception e) {
			logger.error("微信后台：按时间、价格、销量查询商品列表失败!", e);
			result.setCode(ExceptionEnum.business_logic_exception.getIndex());
			result.setMsg(ExceptionEnum.business_logic_exception.getValue());
		}
		return result;
	}
	
	/**
	 * 按排（最新时间，价格，销量，人气）查询产品
	 * @throws Exception 
	 * @parameter orderparam （最新时间，价格，销量，人气）
	 * @parameter page （分页参数）
	 */
	
	@ResponseBody
	@RequestMapping("/findByOrderAndPage")
	public ReturnMsg<Map<String,Object>> findByOrderAndPage(String orderparam,String page,boolean down,String productCategoryName,HttpServletRequest request,HttpServletResponse response){
		
		logger.info("微信后台：按时间、价格、销量查询商品列表入参orderparam=" + orderparam + ",page=" + page+",down="+down);
		ReturnMsg<Map<String,Object>> result = new ReturnMsg<Map<String,Object>>();
		Map<String,Object> reMap = new HashMap<String, Object>();
		if(Utils.isObjectsBlank(orderparam,page,down)){
			result.setStatus(false);
			result.setCode(ExceptionEnum.param_not_null.getIndex());
			result.setMsg(ExceptionEnum.param_not_null.getValue());
			return result;
		}
		try {
			String productName = java.net.URLDecoder.decode(productCategoryName,"utf-8");
			if(productName.equals("null"))
			{
				productName="";
			}
			//设置热门搜索cookie
			String searchStr = "";
			if(StringUtils.isNotEmpty(productName)){
				String lastSearchStr = WebUtil.getCookie(request, "searchStr") ;
				StringBuffer currentSearchStr = new StringBuffer();
				if(StringUtils.isNotEmpty(lastSearchStr)){
					Set<String> searchSet = new HashSet<String>();
					String[] serchArray = lastSearchStr.split(",");
					for (String itemArrary : serchArray) {
						searchSet.add(itemArrary);
					}
					//追加时间戳用于查询的时候排序
					searchSet.add(System.currentTimeMillis()+"#"+productName);
					int i = 0;
					for (String itemSet : searchSet) {
						if(0 != i){
							currentSearchStr.append(",");
						}
						currentSearchStr.append(itemSet);
						i++;
					}
					searchStr = currentSearchStr.toString();
				}else{
					searchStr = System.currentTimeMillis()+"#"+productName;
				}
				WebUtil.addCookie(request, response, "searchStr", searchStr, 604800);
			}
			logger.info("微信后台：按产品分类productName="+productName);
			
			List<Map<String,Object>> products = productServiceImpl.findByOrderAndPage(orderparam,page,down,productName.replace("%", "/%"));
			reMap.put("productName", productName);
			reMap.put("products", products);
			result.setData(reMap);
		} catch (Exception e) {
			logger.error("微信后台：按时间、价格、销量查询商品列表失败!", e);
			result.setCode(ExceptionEnum.business_logic_exception.getIndex());
			result.setMsg(ExceptionEnum.business_logic_exception.getValue());
		}
		return result;
	}
	
	/**
	 * 查询详情
	 */
	@RequestMapping("/getProducts")
	@ResponseBody
	public Map<String,Object> getProducts(String id) {
		logger.info("查询商品详情入参productId=" + id);
		Map<String,Object> result = new HashMap<String,Object>();
		List<Map<String,Object>> commentList = null;
		Product entity = null;
		 String rankId = (String) UserSession.getSession(WechatConstant.RANK_ID);
		try {
			if(StringUtils.isEmpty(id)){
				return null;
			}
			
			entity = productServiceImpl.findById(id,rankId);
			List<ProductImageRef> productImageRefList =productImageRefService.queryLast3ImageById(id);
			if(null != productImageRefList && !productImageRefList.isEmpty()){
				entity.setProductImageRefList(productImageRefList);
			}
			Map<String,Object> parms = new HashMap<String,Object>();
			parms.put("productId", id);
			commentList = commentService.queryLast5Comment(parms);
		} catch (Exception e) {
			logger.error("查询商品最新5笔评论异常",e);
		}
		Map<String,Object> avgCore = this.getAvgCore(id);
		result.put("commentList", commentList);
		result.put("avgCore", avgCore);
		result.put("product", entity);
		return result;
	}
	
	/**
	 * 查询详情(不包含评论)
	 */
	@RequestMapping("/getProductDetail")
	@ResponseBody
	public Map<String,Object> getProductDetail(String productId) {
		logger.info("查询商品详情入参productId=" + productId);
		Map<String,Object> result = new HashMap<String,Object>();
		Map<String,Object> cartInfo = null;
		List<Map<String,Object>> specList = null;
		Product product = null;
		 String rankId = (String) UserSession.getSession(WechatConstant.RANK_ID);
		try {
			if(StringUtils.isEmpty(productId)){
				return null;
			}
			product = productServiceImpl.findById(productId,rankId);
			List<ProductImageRef> productImageRefList =productImageRefService.queryLast3ImageById(productId);
			if(null != productImageRefList && !productImageRefList.isEmpty()){
				product.setProductImageRefList(productImageRefList);
			}
			
			specList = productServiceImpl.querySpecAndGroupSpecInfo(productId);
			
			//获取当前用户userName
			String userName = (String)UserSession.getSession(WxConstant.wx_user_name);
			//查询用户购物车所有商品的数量
			if (Utils.isNotBlank(userName)) {
//				cartInfo = cartItemService.queryProductNumberByIdAndUserName(productId, userName);
				cartInfo = cartItemService.queryProductNumberByUserName(userName);
				
			}else{
				cartInfo = new HashMap<String, Object>();
				cartInfo.put("count", 0);
			}
			
		} catch (Exception e) {
			logger.error("查询商品详情异常",e);
		}
		result.put("product", product);
		result.put("cartInfo", cartInfo);
		result.put("specList", specList);
		result.put("imageDomain", Global.getConfig("upload.image.path"));
		return result;
	}
	
	/**
	 * 根据商品信息查询商品评论信息
	 * @param id
	 * @return
	 */
	private Map<String,Object> getAvgCore(String id){
		logger.info("查询商品评论信息入参：productId=" + id);
		//查询评论总数，好评总数，中评总数，差评总数，平均分
		Map<String,Object> avgCore = new HashMap<String,Object>();
		Map<String,Object> avg = new HashMap<String,Object>();
		try {
			avgCore = commentService.findCountGroupByProductId(id);
			if(null != avgCore){
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
				return avgCore;
			}else{
				avg.put("hp", "0");
				avg.put("cp", "0");
				avg.put("zp", "0");
				avg.put("scorePercent", "width:0%;");
				avg.put("hpRate", "0");
				avg.put("zongp", "0");
				avg.put("all","0");
			}
		} catch (Exception e) {
			logger.info("获取评论异常：" + e);
			avg.put("hp", "0");
			avg.put("cp", "0");
			avg.put("zp", "0");
			avg.put("scorePercent", "width:0%;");
			avg.put("hpRate", "0");
			avg.put("zongp", "0");
			avg.put("all","0");
		}
		return avg;
	}
	
	/**
	 * 初始化商品图文信息页面
	 */
	@RequestMapping("/initProductInfo")
	@ResponseBody
	public Map<String, Object> initProductInfo(String productId){
		logger.info("初始化商品图文信息页面入参productId=" + productId);
		Map<String, Object> result = new HashMap<String, Object>();
		
		try {
			//查询商品信息
			 String rankId = (String) UserSession.getSession(WechatConstant.RANK_ID);
			Product product = productServiceImpl.findById(productId,rankId);
			
			//查询商品规格
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("productId", productId);
			List<Map<String, Object>> paramlist = productServiceImpl.getParameters(map);
			List<Map<String, Object>> propList =productPropertyService.queryProductPropertyById(productId);
			result.put("product", product);
			result.put("paramlist", paramlist);
			result.put("propList", propList);
		} catch (Exception e) {
			logger.error("初始化商品图文信息页面异常",e);
		}
		return result;
	}
	
	/**
	 * 微信首页广告轮播图
	 */
	@ResponseBody
	@RequestMapping("/findAdvertisementImage")
	public ReturnMsg<List<Map<String, Object>>> findAdvertisementImage(@RequestParam String adPositionId) {
		ReturnMsg<List<Map<String, Object>>> result = new ReturnMsg<List<Map<String, Object>>>();
		if (Utils.isObjectsBlank(adPositionId)) {
			result.setStatus(false);
			result.setCode(ExceptionEnum.param_not_null.getIndex());
			return result;
		}
		try {
			result.setData(advertisementService.getAdvertisementImage(adPositionId));
		} catch (Exception e) {
			logger.error("初始化商品图文信息页面异常",e);
		}
		return result;
	}
	
	/**
	 * 根据产品id和规格id查询改该规格下能组合的规格和规格组信息
	 */
	@RequestMapping("/querySpecBySpecIdAndId")
	@ResponseBody
	public List<Map<String, Object>> querySpecBySpecIdAndId(String productId,String specId,HttpServletRequest request){
		logger.info("查询能组合的规格和规格组信息入参productId=" + productId + ",specId=" + specId);
		List<Map<String, Object>> specList = null;
		if (Utils.isObjectsBlank(productId,specId)) {
			return null;
		}
		try {
			Map<String, Object> parm = new HashMap<String, Object>();
			parm.put("productId", productId);
			parm.put("specId", specId);
			//查询商品规格组和规格信息
			specList = productServiceImpl.querySpecBySpecIdAndId(parm);
		} catch (Exception e) {
			logger.error("查询能组合的规格和规格组信息异常",e);
		}
		return specList;
	}
	
	/**
	 * 通过规格id和同类型的产品id查询当前规格的产品id
	 */
	@RequestMapping("/queryProductIdBySpecIds")
	@ResponseBody
	public Map<String,String> queryProductIdBySpecIds(String productId,String memberId,String[] specIds,HttpServletRequest request){
		logger.info("通过规格id和同类型的产品id得到查询规格的产品id入参productId=" + productId + ",specIds=" + Arrays.toString(specIds));
		if (Utils.isObjectsBlank(productId,specIds)) {
			logger.error("输入参数为空");
			return null;
		}
		Map<String,String> result = null;
		try {
		    String nowRankIdString="";
			String rid = productServiceImpl.queryProductIdByIdAndSpecIds(productId, specIds);
			result = new HashMap<String, String>();
			Product product=new Product();
			// 查询商品信息
			Map<String,Object> mapList= memMemberService.queryMemMember(null, memberId);
			if(mapList!=null){
				Object rankId =mapList.get("rankId");
				if (StringUtils.isNotEmpty(rankId.toString()) && !rankId.toString().equals("null") && !rankId.toString().equals(null)) {						
					logger.info("查询商品详情入参rankId=" + rankId);
					product = productServiceImpl.findById(rid, rankId.toString());
				}					
			}
			if(product!=null){	
				if(product.getId()==rid || rid.equals(product.getId())){					
					result.put("Stock", product.getStock().toString());
					result.put("allStock",product.getAllocatedStock().toString());
					result.put("wxIsMarketable", product.getWxIsMarketable());
					result.put("sellPrice", product.getSellPrice().toString());
					result.put("fullName",product.getFullName());
					result.put("sales",product.getSales().toString());
				}
			}
			result.put("productId", rid);
		} catch (Exception e) {
			logger.error("通过规格id和同类型的产品id得到查询规格的产品id异常",e);
	  }
		return result;
	}
	
}
