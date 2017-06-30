package com.uib.ptyt.web;

import hk.com.easypay.dict.service.AddressService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.uib.ad.service.AdvertisementService;
import com.uib.cart.service.CartItemService;
import com.uib.common.enums.ExceptionEnum;
import com.uib.common.web.Global;
import com.uib.mobile.dto.ReturnMsg;
import com.uib.product.entity.Product;
import com.uib.product.entity.ProductImageRef;
import com.uib.product.service.ProductCategoryService;
import com.uib.product.service.ProductImageRefService;
import com.uib.product.service.ProductService;
import com.uib.product.service.impl.ProductPropertyService;
import com.uib.ptyt.constants.WechatConstant;
import com.uib.ptyt.entity.StoreDto;
import com.uib.ptyt.service.MemMemberService;
import com.uib.ptyt.service.PbytProductService;
import com.uib.ptyt.service.StoreService;
import com.uib.ptyt.utils.CRequest;
import com.uib.serviceUtils.Utils;
import com.uib.weixin.constant.WxConstant;
import com.uib.weixin.util.UserSession;
import com.uib.weixin.util.WeixinUtil;

/***
 * 产品管理
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/pbyt/product")
public class PbytProductController {

	private Logger logger = LoggerFactory.getLogger(PbytProductController.class);

	@Autowired
	private ProductService productServiceImpl;

	@Autowired
	private AddressService addressService;

	@Autowired
	private ProductImageRefService productImageRefService;

	@Autowired
	private AdvertisementService advertisementService;

	@Autowired
	private CartItemService cartItemService;

	@Autowired
	private ProductCategoryService productCategoryService;

	@Autowired
	private ProductPropertyService productPropertyService;

	@Autowired
	private PbytProductService pbytProductService;

	@Autowired
	private StoreService storeService;

	@Autowired
	private MemMemberService memMemberService;

	/**
	 * 初始化商品图文信息页面
	 */
	@RequestMapping("/initProductInfo")
	@ResponseBody
	public Map<String, Object> initProductInfo(String productId, String memberId) {
		logger.info("初始化商品图文信息页面入参productId=" + productId);
		Map<String, Object> result = new HashMap<String, Object>();
		String nowRankIdString = "";
		Product product = null;
		try {
			Map<String,Object> mapList= memMemberService.queryMemMember(null, memberId);
			if(mapList!=null){
				Object rankId =mapList.get("rankId");
				if (StringUtils.isNotEmpty(rankId.toString()) && !rankId.toString().equals("null") && !rankId.toString().equals(null)) {						
					logger.info("查询商品详情入参rankId=" + rankId);
					product = productServiceImpl.findById(productId, rankId.toString());
				}					
			}
		

			// 查询商品规格
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("productId", productId);
			List<Map<String, Object>> paramlist = productServiceImpl.getParameters(map);
			List<Map<String, Object>> propList = productPropertyService.queryProductPropertyById(productId);
			result.put("product", product);
			result.put("paramlist", paramlist);
			result.put("propList", propList);
		} catch (Exception e) {
			logger.error("初始化商品图文信息页面异常", e);
		}
		return result;
	}

	@RequestMapping("/getProductDetail")
	@ResponseBody
	public Map<String, Object> getProductDetail(String productId, String memberId) {
		logger.info("查询商品详情入参productId=" + productId);
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> cartInfo = null;
		List<Map<String, Object>> specList = null;
		Product product = null;
		String nowRankIdString = "";
		
		try {
			if (StringUtils.isEmpty(productId)) {
				return null;
			} else {
				Map<String,Object> mapList= memMemberService.queryMemMember(null, memberId);
				if(mapList!=null){
					Object rankId =mapList.get("rankId");
					if (StringUtils.isNotEmpty(rankId.toString()) && !rankId.toString().equals("null") && !rankId.toString().equals(null)) {						
						logger.info("查询商品详情入参rankId=" + rankId);
						product = productServiceImpl.findById(productId, rankId.toString());
					}					
				}
			}
			List<ProductImageRef> productImageRefList = productImageRefService.queryLast3ImageById(productId);
			if (null != productImageRefList && !productImageRefList.isEmpty()) {
				product.setProductImageRefList(productImageRefList);
			}

			specList = productServiceImpl.querySpecAndGroupSpecInfo(productId);

			// 获取当前用户userName
			String userName = (String) UserSession.getSession(WxConstant.wx_user_name);
			// 查询用户购物车所有商品的数量
			if (Utils.isNotBlank(userName)) {
				cartInfo = cartItemService.queryProductNumberByUserName(userName);

			} else {
				cartInfo = new HashMap<String, Object>();
				cartInfo.put("count", 0);
			}
			cartInfo = new HashMap<String, Object>();
			cartInfo.put("count", 0);
		} catch (Exception e) {
			logger.error("查询商品详情异常", e);
		}
		result.put("product", product);
		result.put("cartInfo", cartInfo);
		result.put("specList", specList);
		result.put("imageDomain", Global.getConfig("upload.image.path"));
		return result;
	}

	/**
	 * 批量添加商品到我的店铺
	 * 
	 * @param productIds
	 * @param storeId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addStoreProduct")
	@ResponseBody
	public ReturnMsg<Object> addStoreProduct(String[] productIds, String storeId) {
		ReturnMsg<Object> returnMsg = new ReturnMsg<Object>();
		Map map = new HashMap();
		// storeId = "43c895d391fc46619a5e366c5c60d9a8";
		logger.info("批量加入我的店铺入参productIds=" + productIds);
		logger.info("批量加入我的店铺入参storeId=" + storeId);
		if (productIds.length == 0) {
			returnMsg.setStatus(false);
			returnMsg.setCode(ExceptionEnum.param_not_null.getIndex());
			returnMsg.setMsg(ExceptionEnum.param_not_null.getValue());
			return returnMsg;
		}
		try {
			if (StringUtils.isEmpty(storeId) || storeId.equals("null") || storeId == null || storeId.equals(null)) {
				String merchantId = (String) UserSession.getSession(WechatConstant.MERCHANT_ID);
				// map.put("memberId",
				// memberId);3307b67f68534795bce4029fdf53e266
				StoreDto sto = storeService.queryStore(merchantId);
				Boolean have = true;
				if (sto != null) {
					String storeid = sto.getId();
					Map pMap = new HashMap();
					pMap.put("storeId", storeid);
					List<Product> pList = storeService.queryProductList(pMap);
					if (pList.size() != 0 || null != pList) {
						for (Product product : pList) {
							for (int i = 0; i < productIds.length; i++) {
								if (productIds[i].equals(product.getId())) {
									returnMsg.setCode("0");
									have = false;
								}
							}
						}
					}
					if (have) {
						pbytProductService.addStoreProduct(productIds, storeid);
						returnMsg.setCode("1");
						logger.info("批量加入我的店铺入参storeid=" + storeid);
					}
				}
			} else {
				pbytProductService.addStoreProduct(productIds, storeId);
				returnMsg.setCode("2");
			}
			returnMsg.setStatus(true);
		} catch (Exception e) {
			returnMsg.setStatus(false);
			logger.error("批量添加商品到我的店铺失败，原因：{}", e.getMessage());
		}
		return returnMsg;

	}

	// @ResponseBody
	// @RequestMapping(value = "/findAddressList", produces =
	// "text/html;charset=UTF-8")
	// public String findAddressList() {
	// List<AddressDto> addressDto = new ArrayList<AddressDto>();
	// StringBuilder jsonData=new StringBuilder();
	// jsonData.append("{\"data\": [");
	//
	// try{
	// addressDto = addressService.getAddress();
	// for(int i=0; i< addressDto.size(); i++){
	// AddressDto addr = addressDto.get(i);
	// jsonData.append("{\"id\": \""+addr.getId()+"\",\"name\": \""+addr.getName()+"\",\"child\":"+addr.getChild()
	// + "}");
	// if(i < addressDto.size() - 1){
	// jsonData.append(",");
	// }
	// }
	// jsonData.append("]}");
	// }catch (ServiceException e) {
	// logger.info("查询省市区树结构异常"+e);
	// e.printStackTrace();
	// }
	//
	// return jsonData.toString();
	// }
	@RequestMapping({"/DetilCenter"})
	  public String DetilCenter(String id, String recommendMemberId, String userType, String link)
	  {
	    String appId = Global.getConfig("WECHAT.APPID");
	    String host = Global.getConfig("WECHAT.HOST");

	    this.logger.info(" DetilCenter  link = {}", link);
	    String urlString = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="
	    + appId + "&redirect_uri=" + "http%3a%2f%2f" + host + "%2ff%2fpbyt%2fproduct%2fcallBack?" + "link=" + link + "?id=" + id + "-recommendMemberId=" + recommendMemberId + "-userType=" + userType + "&response_type=code&scope=snsapi_userinfo&state=1" + "#wechat_redirect";
	    this.logger.info("urlString = {}", urlString);
	    return "redirect:" + urlString;
	  }

	  @RequestMapping(value={"/callBack"}, produces={"text/plain;charset=UTF-8"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
	  @ResponseBody
	  public ModelAndView oauth2authorize(@RequestParam("code") String authcode, @RequestParam("state") String state, @RequestParam("link") String link, HttpServletRequest request, HttpServletResponse response) throws Exception
	  {
	    this.logger.info("获取网页授权code回调开始======" + authcode);
	    this.logger.info("获取网页授权state回调======" + state);
	    this.logger.info("当前链接link====" + link);

	    Map mapRequest = CRequest.URLRequest(link);

	    String id = (String)mapRequest.get("id");
	    this.logger.info("当前productId===={}", id);

	    String recommendMemberId = (String)mapRequest.get("recommendmemberid");
	    this.logger.info("当前recommendMemberId===={}", recommendMemberId);

	    String userType = (String)mapRequest.get("usertype");
	    this.logger.info("当前userType===={}", userType);

	    String gotoPage = "";
	    try {
	      Map<String,Object> mapList = WeixinUtil.queryWxuseridCall(authcode);	    
	      String flag = "1";
	      Object openId=mapList.get("openid");
	      Object accessToken=mapList.get("accesstoken");
	      Object reshToken=mapList.get("refreshToken");
	      this.logger.info("获取网页授权openid回调=" + openId.toString());
	      this.logger.info("获取网页授权accesstoken回调=" + accessToken.toString());
	      this.logger.info("获取网页授权reshToken回调=" + reshToken.toString());
	      if (StringUtils.isNotEmpty(openId.toString())) {
	        UserSession.setSession("openId", openId);
	        
	        Map<String, Object> map = WeixinUtil.getWeChatUserInfo(openId.toString(),accessToken.toString(),reshToken.toString());
	        Map<String,Object> member = this.memMemberService.getuserIdByopenId(openId.toString());
	        if (member == null) {	          
	       // 微信用户不存在，直接生产一个用户
				memMemberService.createUser(openId.toString());
				if(map!=null){
					// 更新用户头像和性别
					memMemberService.updateUserHead(map);					
					try{
						//更新用户昵称        ----微信昵称有特殊字符，必须要单独处理
						memMemberService.updateUserName(map);
					}catch(Exception e){
						creaName(openId.toString());
					}
				}else{
					String host = Global.getConfig("WECHAT.HOST");
					String avatar=host+"/upload/images/person.png";
					map.put("avatar", avatar);
					map.put("gender", "1");
					creaName(openId.toString());
					logger.info("更新用户头像和性别入参avatar======"+avatar);	
					// 更新用户头像和性别
					memMemberService.updateUserHead(map);	
				}
	        } else {
	          String userId = member.get("id").toString();
	          String rankId = member.get("rankId").toString();
	          this.logger.info("根据openId查询出当前userId=" + userId);
	          this.logger.info("根据openId查询出当前rankId=" + rankId);
	          UserSession.setSession("userId", userId);

	          UserSession.setSession("rankId", rankId);
	          String merchartId = this.memMemberService.getmerchartIdByUserId(userId);
	          this.logger.info("根据userId查询出当前merchartId" + merchartId);
	          if (null != merchartId)
	          {
	            UserSession.setSession("merchantId", merchartId);
	            this.logger.info("商户号merchartId===" + UserSession.getSession("merchantId"));
	          }

	          if (member.get("userName") == null) {
	            this.logger.info("当前用户的用户姓名为空====");
	          } else {
	            String userName = member.get("userName").toString();
	            UserSession.setSession("userName", userName); 
	          }
	        }
	      }

	      gotoPage = CRequest.UrlPage(link);
	      if (userType =="2"){
	        gotoPage = gotoPage + "?id=" + id + "&recommendMemberId=" + recommendMemberId + "&userType=" + userType ;
	      }else {
	        gotoPage = gotoPage + "?id=" + id + "&recommendMemberId=" + recommendMemberId + "&userType=" + userType ;
	      }
	      this.logger.info(" gotoPage = {}", gotoPage);
	    } catch (Exception e) {
	      this.logger.error("获取网页授权code回调异常" + e);
	      this.logger.info("获取网页授权code回调异常======" + e);
	    }

	    return new ModelAndView("redirect:" + gotoPage);
	  }
	@RequestMapping(value = "/vilatAdress")
	@ResponseBody
	public ReturnMsg<Object> vilatAdress(String[] areaIds, String productId) {
		ReturnMsg<Object> returnMsg = new ReturnMsg<Object>();
		logger.info("获取商品区域入参areaId=" + areaIds);
		logger.info("获取商品区域入参productId=" + productId);
		try {
	       Boolean productAdress = pbytProductService.queryProductAdress(areaIds,productId);
	       returnMsg.setStatus(productAdress);
		} catch (Exception e) {
			 returnMsg.setStatus(false);
			logger.error("获取商品区域异常" + e.getMessage());
			logger.info("获取商品区域异常======" + e);
		}

		return returnMsg;
	}
	
	
	/*
	 * 流水号不齐六位补齐六位
	 */
	private String liuShuiHao(int code){
		String strHao =String.valueOf(code);
		while (strHao.length() < 6)
		{
			strHao = "0" + strHao;
		}
		return strHao;
	}
	
	
	private void creaName(String openId){
		logger.info("微信头像特殊字符处理开始======");	
		logger.info("微信头像特殊字符处理入参openId======"+openId);	
		int code;
		try {
			code = memMemberService.getCode();
			String strHao = liuShuiHao(code);  //流水号不齐六位补齐六位
			String userName = "PBYT"+strHao;
			logger.info("流水号strHao======" + strHao);
			logger.info("系统自动分配userName======" + userName);
			memMemberService.updateUserName2(openId,userName);
			memMemberService.updateCode(code+1);
			logger.info("微信头像特殊字符处理结束======");
		} catch (Exception e) {
			logger.info("微信头像特殊字符处理异常======"+ e);
		}
	}
	
}
