package com.uib.weixin.web;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.uib.base.BaseController;
import com.uib.cart.service.CartService;
import com.uib.common.enums.ExceptionEnum;
import com.uib.mobile.dto.ReturnMsg;
import com.uib.product.entity.MemberFavoriteProduct;
import com.uib.product.service.MemberFavoriteProductService;
import com.uib.serviceUtils.Utils;
import com.uib.weixin.constant.WxConstant;
import com.uib.weixin.util.UserSession;

/***
 * 会员商品收藏controller
 * 
 * @author zfan
 * 
 */
@Controller
@RequestMapping("/wechat/member/favorite")
public class WeChatFavoriteProductController extends BaseController {

	@Autowired
	private MemberFavoriteProductService favoriteProductService;
	
	@Autowired
	private CartService cartService;

	/***
	 * 我的收藏列表
	 * 
	 * @return
	 */
	@RequestMapping("/myFavorite")
	@ResponseBody
	public ReturnMsg<List<Map<String, Object>>> myFavorite() {
		logger.info("=======begin 手机微信调用myFavorite方法接口====================");

		ReturnMsg<List<Map<String, Object>>> returnMsg = new ReturnMsg<List<Map<String, Object>>>();

		try {
			List<Map<String, Object>> listMap = favoriteProductService
					.getMemberFavoriteProductsByMemberId(String
							.valueOf(UserSession
									.getSession(WxConstant.wx_user_id)));
			returnMsg.setData(listMap);
			returnMsg.setStatus(true);
		} catch (Exception e) {
			returnMsg.setStatus(false);
			returnMsg.setCode(ExceptionEnum.system_error.getIndex());
			returnMsg.setMsg(ExceptionEnum.system_error.getValue());

			logger.error("查询我的收藏列表失败，错误信息：{}", e.getMessage());
		}
		logger.info("=======end myFavorite 手机微信调用myFavorite方法接口====================");
		return returnMsg;
	}

	/***
	 * 删除商品收藏
	 * 
	 * @param sessionId
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public ReturnMsg<Object> delete(String[] ids) {
		logger.info("=======begin 手机微信调用delete方法接口====================");
		
		ReturnMsg<Object> msg = new ReturnMsg<Object>();
		// 非空判断
		if (Utils.isBlank(ids)) {
			msg.setCode(ExceptionEnum.param_not_null.getIndex());
			msg.setMsg(ExceptionEnum.param_not_null.getValue());
			msg.setStatus(false);
			return msg;
		}
		try {
			for (String id : ids) {
				favoriteProductService.deleteFavorite(id);
			}
			msg.setStatus(true);
		} catch (Exception e) {
			msg.setStatus(false);
			msg.setCode(ExceptionEnum.system_error.getIndex());
			msg.setMsg(ExceptionEnum.system_error.getValue());

			logger.error("删除收藏商品出错，错误信息：{}", e.getMessage());
		}
		logger.info("=======end delete 手机微信调用delete方法接口====================");
		return msg;
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
	public ReturnMsg<Object> favoriteProduct(String productId) {
		logger.info("=======begin 手机微信调用delete方法接口====================");

		ReturnMsg<Object> msg = new ReturnMsg<Object>();
		if (Utils.isBlank(productId)) {
			msg.setCode(ExceptionEnum.param_not_null.getIndex());
			msg.setMsg(ExceptionEnum.param_not_null.getValue() + "productId:" + productId);
			msg.setStatus(false);
			return msg;
		}
		String userId = String.valueOf(UserSession.getSession(WxConstant.wx_user_id));
		
		try {
			MemberFavoriteProduct favorite = favoriteProductService.getMemberFavoriteProducts(userId, productId);
			if(favorite != null){
				msg.setStatus(false);
				msg.setCode("021");
				msg.setMsg("该商品已收藏");
				return msg;
			}
			favorite = new MemberFavoriteProduct();
			favorite.setProductId(productId);
			favorite.setId(UUID.randomUUID().toString());
			favorite.setMemberId(userId);
			Timestamp date = new Timestamp(System.currentTimeMillis());
			favorite.setCreateDate(date);
			favorite.setDelFlag("0");
			favoriteProductService.saveFavorite(favorite);
			msg.setStatus(true);
		} catch (Exception e) {
			msg.setStatus(false);
			msg.setCode(ExceptionEnum.system_error.getIndex());
			msg.setMsg(ExceptionEnum.system_error.getValue());
			
			logger.error("收藏商品出错，错误信息：{}", e.getMessage());
		}
		logger.info("=======end favoriteProduct 手机APP调用favoriteProduct方法接口====================");
		return msg;
	}
	
	/***
	 * 添加到购物车
	 * @param productId
	 * @return
	 */
	@RequestMapping("/addCart")
	@ResponseBody
	public ReturnMsg<String> addCart(@RequestParam("productId") String productId) {
		logger.info("微信端加入购物车入参：productId=" + productId);
		
		ReturnMsg<String> result = new ReturnMsg<String>();
		result.setStatus(true);
		try {
			//获取当前用户userName
			String userName = (String)UserSession.getSession(WxConstant.wx_user_name);
			logger.info("通过session获取userName=" + userName);
			
			if (Utils.isObjectsBlank(productId, userName)) {
				result.setCode(ExceptionEnum.param_not_null.getIndex());
				result.setMsg(ExceptionEnum.param_not_null.getValue());
				result.setStatus(false);
				return result;
			}
			
			ReturnMsg<String> addCartResult = new ReturnMsg<String>();
			addCartResult = cartService.addCart(productId, 1, userName, "WX");
			
			result.setCode(addCartResult.getCode());
			result.setMsg(addCartResult.getMsg());
			result.setStatus(addCartResult.isStatus());
		} catch (Exception e) {
			logger.error("添加购物车失败!", e);
			result.setCode(ExceptionEnum.business_logic_exception.getIndex());
			result.setMsg(ExceptionEnum.business_logic_exception.getValue());
		}
		return result;
	}
	
	@RequestMapping("/myFavoriteCount")
	@ResponseBody
	public ReturnMsg<Integer> myFavoriteCount() {
		logger.info("=======begin 手机微信调用myFavoriteCount方法接口====================");

		ReturnMsg<Integer> returnMsg = new ReturnMsg<Integer>();

		try {
			Integer favoriteCount = favoriteProductService
					.getFavoriteCount(String
							.valueOf(UserSession
									.getSession(WxConstant.wx_user_id)));
			returnMsg.setData(favoriteCount);
			returnMsg.setStatus(true);
		} catch (Exception e) {
			returnMsg.setStatus(false);
			returnMsg.setCode(ExceptionEnum.system_error.getIndex());
			returnMsg.setMsg(ExceptionEnum.system_error.getValue());

			logger.error("查询我的收藏商品数量失败，错误信息：{}", e.getMessage());
		}
		logger.info("=======end myFavorite 手机微信调用myFavoriteCount方法接口====================");
		return returnMsg;
	}


}
