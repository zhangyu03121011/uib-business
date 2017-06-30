package com.uib.ptyt.web;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.config.BaseConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.uib.base.BaseController;
import com.uib.common.enums.OrderStatus;
import com.uib.member.entity.MemMember;
import com.uib.mobile.dto.AreaResponseDto;
import com.uib.mobile.dto.ReturnMsg;
import com.uib.ptyt.constants.WechatConstant;
import com.uib.ptyt.entity.GoodsDto;
import com.uib.ptyt.entity.RecommOrderDto;
import com.uib.ptyt.service.CommDetailService;
import com.uib.weixin.constant.WxConstant;
import com.uib.weixin.util.UserSession;

/**
 * 销售订单
 * 
 * @author xjp
 * 
 */
@Controller
@RequestMapping("/pbyt/recommend")
public class RecommendController  extends BaseController{
	@Autowired
	private CommDetailService commDetailService;
	@RequestMapping("/getUser")
	@ResponseBody
	public ReturnMsg getUser(HttpServletRequest request,
			HttpServletResponse response){
		//MemMember memberDto = (MemMember) UserSession.getSession(WxConstant.wx_member_info);
		//String memberId=memberDto.getId();//"2f7f481de28c4f97977fbb0e79199bd2";//memberDto.getId();
		String memberId=(String)UserSession.getSession(WechatConstant.USER_ID);
				return null;		
	}
	@RequestMapping("/init")
	@ResponseBody
	public ReturnMsg<List<RecommOrderDto>> init(HttpServletRequest request,
			HttpServletResponse response){
	ReturnMsg<List<RecommOrderDto>> returnMsg=new ReturnMsg<List<RecommOrderDto>>();
	logger.info("=============进入销售订单=========");
	RecommOrderDto recommOrderdto=new RecommOrderDto();
	try {
	
		String memberId=(String)UserSession.getSession(WechatConstant.USER_ID);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberId", memberId);
		List<RecommOrderDto> recommOrderDtos=commDetailService.querRecommendList(map);
		List<RecommOrderDto> recommOrderDtoList=new ArrayList<RecommOrderDto>();
		if(recommOrderDtos.get(0).getMemberId()==memberId ||recommOrderDtos.get(0).getMemberId().equals(memberId)){
			
			for (int i = 0; i < recommOrderDtos.size(); i++) {			
				double  commission=0;
				String orderNo="";
				double allPice=0;
				double allQuantity=0;
				if(recommOrderDtos.get(i).getCommission()!=null){
					commission=Double.valueOf(recommOrderDtos.get(i).getCommission());				
				}
				if(recommOrderDtos.get(i).getSellPice()==null){
					recommOrderDtos.get(i).setSellPice("0");
				}
				if(recommOrderDtos.get(i).getQuantity()==null){
					recommOrderDtos.get(i).setQuantity("0");
				}					
				allPice=allPice+Double.parseDouble(recommOrderDtos.get(i).getSellPice());
				allQuantity=allQuantity+Double.parseDouble(recommOrderDtos.get(i).getQuantity());										
				if(recommOrderDtos.get(i).getOrderNo()==null){
					recommOrderDtos.get(i).setOrderNo("2");
				}
				orderNo=recommOrderDtos.get(i).getOrderNo();
				GoodsDto goodsDto=new GoodsDto();
				List <GoodsDto> goodsList=new ArrayList<GoodsDto>();
				goodsDto.setOrderNo(recommOrderDtos.get(i).getOrderNo());
				goodsDto.setFullName(recommOrderDtos.get(i).getFullName());
				goodsDto.setSellPice(recommOrderDtos.get(i).getSellPice());
				goodsDto.setImage(recommOrderDtos.get(i).getImage());
				goodsDto.setQuantity(recommOrderDtos.get(i).getQuantity());	
				goodsList.add(goodsDto);	
				for (int j = i+1; j < recommOrderDtos.size(); j++) {	
					if(recommOrderDtos.get(j).getOrderNo()==null){
						recommOrderDtos.get(j).setOrderNo("2");
					}
					
					if (orderNo.equals(recommOrderDtos.get(j).getOrderNo()) || 
							orderNo.equals("2")  || orderNo.equals("1")){
						if(recommOrderDtos.get(j).getSellPice()==null){
							recommOrderDtos.get(j).setSellPice("0");
						}
						if(recommOrderDtos.get(j).getQuantity()==null){
							recommOrderDtos.get(j).setQuantity("0");
						}	
						if(recommOrderDtos.get(j).getCommission()!=null){
							commission=commission+Double.valueOf(recommOrderDtos.get(j).getCommission());
						}
						allPice=allPice+Double.parseDouble(recommOrderDtos.get(j).getSellPice());
						allQuantity=allQuantity+Double.parseDouble(recommOrderDtos.get(j).getQuantity());										
						
						GoodsDto goodsDtos=new GoodsDto();
						goodsDtos.setOrderNo(recommOrderDtos.get(j).getOrderNo());
						goodsDtos.setFullName(recommOrderDtos.get(j).getFullName());
						goodsDtos.setSellPice(recommOrderDtos.get(j).getSellPice());
						goodsDtos.setImage(recommOrderDtos.get(j).getImage());
						goodsDtos.setQuantity(recommOrderDtos.get(j).getQuantity());
						goodsList.add(goodsDtos);
						recommOrderDtos.remove(j);
					}
				}
				recommOrderDtos.get(i).setCommission(String.valueOf(commission));
				recommOrderDtos.get(i).setGoods(goodsList);
				recommOrderDtos.get(i).setAllPice(String.valueOf(allPice));
				int allq=(int)allQuantity;
				recommOrderDtos.get(i).setAllQuantity(String.valueOf(allq));
				recommOrderDtos.get(i).setOrderStatus("已完成");
			}
			returnMsg.setStatus(true);
			returnMsg.setData(recommOrderDtos);
		} else{
			returnMsg.setStatus(false);
		}
	} catch (Exception e) {
		logger.error("========进入销售订单错误===========",e);
		returnMsg.setStatus(false);
		returnMsg.setMsg("");
	}
		return returnMsg;		
	}
}
