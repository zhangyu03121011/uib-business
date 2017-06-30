/**
 * Copyright &copy; 2014-2016  All rights reserved.
 *
 * Licensed under the 深圳嘉宝易汇通科技发展有限公司 License, Version 1.0 (the "License");
 * 
 */
package com.uib.mobile.web;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.easypay.common.utils.StringUtil;
import com.uib.ad.service.AdvertisementService;
import com.uib.base.BaseController;
import com.uib.cart.service.CartItemService;
import com.uib.common.enums.ExceptionEnum;
import com.uib.common.web.Global;
import com.uib.member.entity.MemMember;
import com.uib.member.service.MemMemberService;
import com.uib.mobile.dto.ReturnMsg;
import com.uib.mobile.service.CommentService;
import com.uib.product.entity.MemberFavoriteProduct;
import com.uib.product.entity.Product;
import com.uib.product.entity.ProductImageRef;
import com.uib.product.service.MemberFavoriteProductService;
import com.uib.product.service.ProductImageRefService;
import com.uib.product.service.ProductService;
import com.uib.product.service.ProductSpecificationService;
import com.uib.ptyt.constants.WechatConstant;
import com.uib.serviceUtils.Utils;
import com.uib.weixin.util.UserSession;

/**
 * @ClassName: ProductController
 * @Description:商品
 * @author sl
 * @date 2015年9月15日 上午10:02:02
 */
@Controller
@RequestMapping("/mobile/product")
public class MobileProductController extends BaseController {
	
	@Autowired
	private ProductService productServiceImpl;
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private MemMemberService memMemberService;
	
	@Autowired
	private MemberFavoriteProductService memberFavoriteProductService;
	
	@Autowired
	private AdvertisementService advertisementService;
	
	@Autowired
	private ProductImageRefService productImageRefService;
	
	@Autowired
	private ProductSpecificationService productSpecificationService;
	
	@Autowired
	private CartItemService cartItemService;

	@ModelAttribute
	public Product get(@RequestParam(required = false) String id) {
		Product entity = null;
		 String rankId = (String) UserSession.getSession(WechatConstant.RANK_ID);
		try {
			if (id != null) {
				entity = productServiceImpl.findById(id,rankId);
			}
			if (entity == null) {
				entity = new Product();
			}
		} catch (Exception e) {
			logger.error("根据id获取商品失败", e);
		}
		/*if(entity.getIntroduction()!=null){
			entity.setIntroduction(entity.getIntroduction().replaceAll("src=\"", "src=\"" + Global.getConfig("upload.image.path")));
		}*/
		if(entity.getImage()!=null){
			entity.setImage(Global.getConfig("upload.image.path")+entity.getImage());
		}
		return entity;
	}

	/**
	 * 根据商品编号查询商品
	 */
	@RequestMapping("/getProductList")
	public ReturnMsg<List<Product>> getProductList(@RequestParam Map<String, String> map) {
		ReturnMsg<List<Product>> returnMsg = new ReturnMsg<List<Product>>();
		if (StringUtil.isNotBlank(map.get("productNo"))) {
			returnMsg.setStatus(false);
			returnMsg.setCode(ExceptionEnum.param_not_null.getIndex());
			return returnMsg;
		}
		List<Product> list = productServiceImpl.getProductListByProductNo(map);
		returnMsg.setData(list);
		return returnMsg;
	}

	/**
	 * 查看手机端首页显示商品
	 */
	@RequestMapping("/getList")
	@ResponseBody
	public ReturnMsg<List<Map<String, Object>>> getList() {
		ReturnMsg<List<Map<String, Object>>> returnMsg = new ReturnMsg<List<Map<String, Object>>>();
		returnMsg.setData(productServiceImpl.findListMobile());
		return returnMsg;
	}

	/**
	 * 查询商品
	 */
	@RequestMapping("/getProduct")
	public String getProduct(String id,ModelMap modelMap) {
		logger.info("查询商品详情入参productId=" + id);
		Map<String,Object> parms = new HashMap<String,Object>();
		List<Map<String,Object>> commentList = null;
		try {
			parms.put("productId", id);
			commentList = commentService.queryLast5Comment(parms);
		} catch (Exception e) {
			logger.info("查询商品最新5笔评论异常：" + e);
		}
		Map<String,Object> avgCore = this.getAvgCore(id);
		modelMap.put("commentList", commentList);
		modelMap.put("avgCore", avgCore);
		return "/mobile/products";
	}

	/**
	 * 查询商品
	 */
	@RequestMapping("/productsDetail")
	public String productsDetail() {
		return "/mobile/productsDetail";
	}

	/**
	 * 查询商品 页面推荐下载App
	 */
	@RequestMapping("/productsDetailHintApp")
	public String productsDetailHintApp(Model model) {
		model.addAttribute("flag", 0); // flag=0 页面显示推荐app下载
		return "/mobile/productsDetail";
	}

	/**
	 * 查询商品 页面推荐下载App
	 */
	@RequestMapping("/getProductHintApp")
	public String getProductHintApp(String id,HttpServletRequest request) {
		//查询评论总数，好评总数，中评总数，差评总数，平均分
		Map<String,Object> avgCore = this.getAvgCore(id);
		request.setAttribute("avgCore", avgCore);
		request.setAttribute("memberId", request.getParameter("memberId"));
		request.setAttribute("flag", 0);// flag=0 页面显示推荐app下载
		
		logger.info("查询商品详情入参productId=" + id);
		Map<String,Object> parms = new HashMap<String,Object>();
		List<Map<String,Object>> commentList = null;
		try {
			parms.put("productId", id);
			commentList = commentService.queryLast5Comment(parms);
		} catch (Exception e) {
			logger.info("查询商品最新5笔评论异常：" + e);
		}
		request.setAttribute("commentList", commentList);
		
		return "/mobile/products";
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
			}
		} catch (NumberFormatException e) {
			logger.info("获取评论异常：" + e);
			avg.put("hp", "0");
			avg.put("cp", "0");
			avg.put("zp", "0");
			avg.put("scorePercent", "width:0%;");
			avg.put("hpRate", "0");
			avg.put("zongp", "0");
		}
		return avg;
	}

	/**
	 * 查询商品参数
	 */
	@RequestMapping("/getParameters")
	@ResponseBody
	public List<Map<String, Object>> getParameters(@RequestParam(required = false) String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("productId", id);
		List<Map<String, Object>> list = productServiceImpl.getParameters(map);
		return list;
	}

	/**
	 * 根据分类 查询商品
	 */
	@ResponseBody
	@RequestMapping("/findByCategory")
	public ReturnMsg<List<Map<String, Object>>> findByCategory(@RequestParam Map<String, Object> param) {
		logger.info("APP端根据商品分类查询商品入参：" + ToStringBuilder.reflectionToString(param));
		ReturnMsg<List<Map<String, Object>>> result = new ReturnMsg<List<Map<String, Object>>>();
		if (Utils.isObjectsBlank(param.get("categoryId"))) {
			result.setCode(ExceptionEnum.param_not_null.getIndex());
			result.setMsg(ExceptionEnum.param_not_null.getValue());
			result.setStatus(false);
			return result;
		}
		try {
			result.setData(productServiceImpl.findByCategory4Mobile(param));
		} catch (Exception e) {
			result.setCode(ExceptionEnum.system_error.getIndex());
			result.setMsg(ExceptionEnum.system_error.getValue());
			result.setStatus(false);
		}
		return result;
	}

	
	/**
	 * 收藏商品
	 * @param favoriteProduct
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/favoriteProduct")
	public ReturnMsg<Object> favoriteProduct(String sessionId, String productId) {
		ReturnMsg<Object> msg = new ReturnMsg<Object>();
		// 非空判断
		if (Utils.isBlank(sessionId) || Utils.isBlank(productId)) {
			msg.setCode(ExceptionEnum.param_not_null.getIndex());
			msg.setMsg(ExceptionEnum.param_not_null.getValue() + "sessionId:"
					+ sessionId + ",productId:" + productId);
			msg.setStatus(false);
			return msg;
		}
		
		try {
			MemMember member = memMemberService.getMemMemberBySessionId(sessionId);
			if(member == null){
				msg.setStatus(false);
				msg.setCode(ExceptionEnum.member_not_exist.getIndex());
				msg.setMsg(ExceptionEnum.member_not_exist.getValue());
				return msg;
			}
			MemberFavoriteProduct xmfp = memberFavoriteProductService.getMemberFavoriteProducts(member.getId(), productId);
			if(xmfp != null){
				msg.setStatus(false);
				msg.setCode("021");
				msg.setMsg("该商品已收藏");
				return msg;
			}
			xmfp = new MemberFavoriteProduct();
			xmfp.setProductId(productId);
			xmfp.setId(UUID.randomUUID().toString());
			xmfp.setMemberId(member.getId());
			Timestamp date = new Timestamp(System.currentTimeMillis());
			xmfp.setCreateDate(date);
			xmfp.setDelFlag("0");
			memberFavoriteProductService.saveFavorite(xmfp);
			msg.setStatus(true);
		} catch (Exception e) {
			msg.setStatus(false);
			msg.setCode(ExceptionEnum.system_error.getIndex());
			msg.setMsg(ExceptionEnum.system_error.getValue());
			
			logger.error("收藏商品出错", e);
		}
		return msg;
	}
	
	/**
	 * 
	 * @Title: 查询商品库存和是否下架
	 * @author 程健
	 * 时间：2015-11-17
	 */
	@ResponseBody
	@RequestMapping(value = "/queryProductStatusAndCount")
	public ReturnMsg<Object> queryProductStatusAndCount(String productId) {
		logger.info("APP端：查询商品库存和是否下架入参productId=" + productId);
		ReturnMsg<Object> returnMsg = new ReturnMsg<Object>();
		
		if (Utils.isBlank(productId)) {
			returnMsg.setCode(ExceptionEnum.param_not_null.getIndex());
			returnMsg.setMsg(ExceptionEnum.param_not_null.getValue());
			returnMsg.setStatus(false);
			return returnMsg;
		}
		
		try{
			Map<String, Object> result =productServiceImpl.queryProductIsMarketable4Mobile(productId);
			if(null!=result&result.containsKey("isMarketable"))
			{
				String isMarketable = result.get("isMarketable").toString();
				if(isMarketable.equalsIgnoreCase("0")){
					returnMsg.setStatus(true);
					returnMsg.setData(1);
					return returnMsg;
				}
			}
			if(null!=result & result.containsKey("stock"))
			{
				String stock = result.get("stock").toString();
				int istock = Integer.parseInt(stock);
				if(istock < 1){
					returnMsg.setStatus(true);
					returnMsg.setData(2);
					return returnMsg;
				}
			}
			
			returnMsg.setStatus(true);
			returnMsg.setData(0);
		}catch(Exception e){
			logger.info("产品库存数据异常：" + e);
			returnMsg.setStatus(false);
			returnMsg.setCode(ExceptionEnum.system_error.getIndex());
			returnMsg.setMsg(ExceptionEnum.system_error.getValue());
			return returnMsg;
		}
		return returnMsg;
	}
	
	/**
	 * app首页广告轮播图
	 */
	@ResponseBody
	@RequestMapping("/getAppAdvertisementImage")
	public ReturnMsg<List<Map<String, Object>>> getAppAdvertisementImage(@RequestParam String adPositionId,HttpServletRequest request) {
		ReturnMsg<List<Map<String, Object>>> result = new ReturnMsg<List<Map<String, Object>>>();
		if (Utils.isObjectsBlank(adPositionId)) {
			result.setStatus(false);
			result.setCode(ExceptionEnum.param_not_null.getIndex());
			return result;
		}
		try {
			result.setData(advertisementService.getAppAdvertisementImage(adPositionId));
		} catch (Exception e) {
			logger.error("初始化商品图文信息页面异常",e);
		}
		return result;
	}
	
	/**
	 * 查询详情
	 */
	@RequestMapping("/queryProductDetail")
	@ResponseBody
	public ReturnMsg<Map<String, Object>> queryProductDetail(String productId,String sessionId,HttpServletRequest request) {
		logger.info("【APP端】查询商品详情入参productId=" + productId + ",sessionId=" + sessionId);
		ReturnMsg<Map<String, Object>> result = new ReturnMsg<Map<String, Object>>();
		Map<String,Object> data = new HashMap<String,Object>();
		Map<String,Object> cartInfo = null;
		List<Map<String,Object>> specList = null;
		List<String> imageList = null;
		Map<String,Object> product = null;
		try {
			if(StringUtils.isEmpty(productId)){
				result.setStatus(false);
				result.setCode(ExceptionEnum.param_not_null.getIndex());
				result.setCode(ExceptionEnum.param_not_null.getValue());
				return result;
			}
			 String rankId = (String) UserSession.getSession(WechatConstant.RANK_ID);
			Product productInfo = productServiceImpl.findById(productId,rankId);
			if(null != productInfo){
				product = new HashMap<String,Object>();
				product.put("productId", productInfo.getId());
				product.put("name", productInfo.getName());
				product.put("fullName", productInfo.getFullName());
				product.put("price", productInfo.getPrice());
				product.put("marketPrice", productInfo.getMarketPrice());
				product.put("seoDescription", productInfo.getSeoDescription());
				product.put("productNo", productInfo.getProductNo());
				product.put("stock", productInfo.getAvailableStock());
				product.put("isMarketable", productInfo.getAppIsMarketable());
				product.put("image", Global.getConfig("upload.image.path") + productInfo.getImage());
				product.put("unit", productInfo.getUnit());
			}
			List<ProductImageRef> productImageRefList =productImageRefService.queryLast3ImageById(productId);
			if(null != productImageRefList && !productImageRefList.isEmpty()){
				imageList = new ArrayList<String>();
				for (ProductImageRef productImageRef : productImageRefList) {
					imageList.add(productImageRef.getSource());
				}
			}
			
			specList = productServiceImpl.querySpecAndGroupSpecInfo(productId);
			if(StringUtils.isNotEmpty(sessionId)){
				MemMember member = memMemberService.getMemMemberBySessionId(sessionId);
				String userName = "";
				if(null != member){
					userName = member.getUsername();
				}
				if(StringUtils.isNotEmpty(userName)){
//					cartInfo = cartItemService.queryProductNumberByIdAndUserName(productId, userName);
					cartInfo = cartItemService.queryProductNumberByUserName(userName);
				}
			}
			data.put("product", product);
			data.put("imageList", imageList);
			data.put("specList", specList);
			data.put("cartInfo", cartInfo);
			result.setData(data);
			
		} catch (Exception e) {
			logger.error("【APP端】查询商品详情异常",e);
			result.setStatus(false);
			result.setCode(ExceptionEnum.system_error.getIndex());
			result.setMsg(ExceptionEnum.system_error.getValue());
		}
		
		return result;
	}
	
	/**
	 * 根据产品id和规格id查询改该规格下能组合的规格和规格组信息
	 */
	@RequestMapping("/querySpecBySpecIdAndId")
	@ResponseBody
	public ReturnMsg<List<Map<String, Object>>> querySpecBySpecIdAndId(String productId,String specId,HttpServletRequest request){
		logger.info("查询能组合的规格和规格组信息入参productId=" + productId + ",specId=" + specId);
		ReturnMsg<List<Map<String, Object>>> result = new ReturnMsg<List<Map<String, Object>>>();
		List<Map<String, Object>> specList = null;
		if (Utils.isObjectsBlank(productId,specId)) {
			result.setStatus(false);
			result.setCode(ExceptionEnum.param_not_null.getIndex());
			result.setCode(ExceptionEnum.param_not_null.getValue());
			return result;
		}
		try {
			Map<String, Object> parm = new HashMap<String, Object>();
			parm.put("productId", productId);
			parm.put("specId", specId);
			//查询商品规格组和规格信息
			specList = productServiceImpl.querySpecBySpecIdAndId(parm);
			result.setStatus(true);
			result.setData(specList);
		} catch (Exception e) {
			logger.error("查询能组合的规格和规格组信息异常",e);
			result.setStatus(false);
			result.setCode(ExceptionEnum.system_error.getIndex());
			result.setCode(ExceptionEnum.system_error.getValue());
		}
		return result;
	}
	
	/**
	 * 通过规格id和同类型的产品id查询当前规格的产品id
	 */
	@RequestMapping("/queryProductIdBySpecIds")
	@ResponseBody
	public ReturnMsg<String> queryProductIdBySpecIds(String productId,String[] specIds,HttpServletRequest request){
		logger.info("通过规格id和同类型的产品id得到查询规格的产品id入参productId=" + productId + ",specIds=" + Arrays.toString(specIds));
		ReturnMsg<String> result = new ReturnMsg<String>();
		if (Utils.isObjectsBlank(productId,specIds)) {
			result.setStatus(false);
			result.setCode(ExceptionEnum.param_not_null.getIndex());
			result.setCode(ExceptionEnum.param_not_null.getValue());
			return result;
		}
		try {
			String returnId = productServiceImpl.queryProductIdByIdAndSpecIds(productId, specIds);
			if(StringUtils.isNotEmpty(returnId)){
				result.setData(returnId);
				result.setStatus(true);
			}else{
				result.setStatus(false);
			}
			
		} catch (Exception e) {
			logger.error("通过规格id和同类型的产品id得到查询规格的产品id异常",e);
			result.setStatus(false);
			result.setCode(ExceptionEnum.system_error.getIndex());
			result.setCode(ExceptionEnum.system_error.getValue());
		}
		return result;
	}
	
}
