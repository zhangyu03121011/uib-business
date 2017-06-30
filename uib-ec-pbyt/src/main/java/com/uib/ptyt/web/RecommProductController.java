package com.uib.ptyt.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.easypay.common.utils.StringUtil;
import com.easypay.common.utils.UUIDGenerator;
import com.uib.base.BaseController;
import com.uib.member.entity.MemMember;
import com.uib.mobile.dto.ReturnMsg;
import com.uib.ptyt.constants.WechatConstant;
import com.uib.ptyt.entity.RecommProduct;
import com.uib.ptyt.service.RecommProductService;
import com.uib.weixin.constant.WxConstant;
import com.uib.weixin.util.UserSession;

/***
 * 推广记录Controller
 * @author zfan
 *
 */
@Controller
@RequestMapping("/ptyt/recommProduct")
public class RecommProductController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(RecommProductController.class);
	
	@Autowired
	private RecommProductService recommProductService;
	
	/***
	 * 新增推广记录
	 * @param recommProduct
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/insertRecommProduct")
	public ReturnMsg<Object> insertRecommProduct(String productId,String userType) throws Exception {
		ReturnMsg<Object> returnMsg = new ReturnMsg<Object>();
		RecommProduct recommProduct=new RecommProduct();
		recommProduct.setProductId(productId);
		recommProduct.setId(UUIDGenerator.getUUID());
		recommProduct.setCreateTime((new Date()).toString());
		recommProduct.setUserType(userType);
		recommProduct.setViewCount("1");
		//TODO
		//FIXME ZFAN 获取当前登录用户ID
		String userId = (String) UserSession.getSession(WechatConstant.USER_ID);
		//MemMember memberDto = (MemMember) UserSession.getSession(WxConstant.wx_member_info);
		//String userId=memberDto.getId();
		recommProduct.setRecommMemberId(userId);
		logger.info("新增推广记录入参productId="+productId);
		logger.info("新增推广记录入参userId="+userId);
		logger.info("新增推广记录入参userType="+userType);
		try{
			RecommProduct  recommproduct =recommProductService.getRecommProduct(recommProduct);
			if(null==recommproduct){
				logger.info("新增推广记录入参recommProduct="+recommProduct);
			    recommProductService.insert(recommProduct);
			}
			returnMsg.setStatus(true);
		}catch(Exception e){
			returnMsg.setStatus(false);
			logger.error("新增推广记录失败，原因：{}", e.getMessage());
		}
		return returnMsg;
	}
	
	/***
	 * 获取推广记录列表
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/queryRecommProductList")
	public ReturnMsg<List<RecommProduct>> queryRecommProductList(RecommProduct product){
		ReturnMsg<List<RecommProduct>> returnMsg = new ReturnMsg<List<RecommProduct>>();
		try{
			
			MemMember memberDto = (MemMember) UserSession.getSession(WxConstant.wx_member_info);
			String id=(String)UserSession.getSession(WechatConstant.USER_ID);
			Map<String, Object> map = new HashMap<String, Object>();
			logger.error("获取推广记录列表入参memberId="+id);
			map.put("memberId", id);
			int allNum=0;
			List<RecommProduct> recommProductList = recommProductService.queryRecommProduct(map);
			for(RecommProduct recommProduct:recommProductList){
				List<Map<String,Object>> mapNum=recommProductService.querRecommProductNum(recommProduct);
				if (mapNum.size()!=0   &&  mapNum!=null) {
					for(Map<String,Object> num:mapNum){
						Object numObj=num.get("num");					
						logger.error("获取推广记录列表交易量num="+numObj.toString());
						allNum=allNum+(int)numObj;
					}
					recommProduct.setNum(String.valueOf(allNum));					
				}else{
					recommProduct.setNum("0");
				}
			}
			returnMsg.setData(recommProductList);
			returnMsg.setStatus(true);
		}catch(Exception e){
			returnMsg.setStatus(false);
			logger.error("查询推广记录列表失败，原因：{}", e.getMessage());
		}
		return returnMsg;
	}
	
	/***
	 * 更新推广商品浏览量
	 * @param recommProduct
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/updateViewCount")
	public ReturnMsg<Object> updateViewCount(RecommProduct recommProduct) throws Exception {
		ReturnMsg<Object> returnMsg = new ReturnMsg<Object>();
		try{
			if(recommProduct.getRecommMemberId()!=null || 
					recommProduct.getRecommMemberId()!="null"){
				RecommProduct tempRecommProduct = new RecommProduct();
				tempRecommProduct.setProductId(recommProduct.getProductId());
				tempRecommProduct.setRecommMemberId(recommProduct.getRecommMemberId());
				
				recommProduct = recommProductService.getRecommProduct(tempRecommProduct);
				int put=Integer.valueOf(recommProduct.getViewCount());
				put=put+1;
				recommProduct.setViewCount(String.valueOf(put));
				recommProductService.updateViewCount(recommProduct);
				returnMsg.setStatus(true);				
			}else {
				returnMsg.setStatus(true);	
			}
		}catch(Exception e){
			returnMsg.setStatus(false);
			logger.error("更新推广记录访问量失败，原因：{}", e.getMessage());
		}
		return returnMsg;
	}
	
}

