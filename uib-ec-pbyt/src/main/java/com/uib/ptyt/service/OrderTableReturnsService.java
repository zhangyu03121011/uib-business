package com.uib.ptyt.service;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.easypay.common.utils.UUIDGenerator;
import com.uib.common.utils.StringUtils;
import com.uib.ptyt.constants.WechatConstant;
import com.uib.ptyt.dao.OrderTableReturnsDao;
import com.uib.ptyt.entity.OrderTableReturnsDto;
import com.uib.ptyt.entity.OrderTableReturnsItemDto;
import com.uib.serviceUtils.OrderNoGenerateUtil;
import com.uib.weixin.util.UserSession;

@Service
public class OrderTableReturnsService {
	private Logger logger = LoggerFactory.getLogger(OrderTableReturnsService.class);

	@Autowired
	private OrderTableReturnsDao orderTableReturnsDao;
	
	@Autowired
	private MemMemberService memMemberService;
	
	/**
	 * 查询退货单信息(单条)
	 * @param orderTableReturnsDto
	 * @return
	 * @throws Exception
	 */
	public OrderTableReturnsDto queryReturns(OrderTableReturnsDto orderTableReturnsDto) throws Exception{
		return orderTableReturnsDao.queryReturns(orderTableReturnsDto);
	}

	/**
	 * 插入退货信息
	 * @param orderTableReturnsDto
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public boolean insert(OrderTableReturnsDto orderTableReturnsDto) throws Exception{
		try{
			String productName=orderTableReturnsDto.getName();
			productName = URLDecoder.decode(productName, "utf-8");
			productName=productName.substring(0,productName.length()-1);
			//接收暂时存放在TrackingNo字段里的image
			String images=orderTableReturnsDto.getTrackingNo();
			orderTableReturnsDto.setTrackingNo("");
			logger.info("上传的图片为="+images);
			//插入退货表 
			String id=UUIDGenerator.getUUID();
			orderTableReturnsDto.setId(id);
			orderTableReturnsDto.setReturnNo(OrderNoGenerateUtil.getOrderNo());
			String memberId=(String) UserSession.getSession(WechatConstant.USER_ID);
			Map<String,Object> map=memMemberService.queryMemMember(null, memberId);
			//插入手机号
			orderTableReturnsDto.setPhone((String)map.get("phone"));
			String name=(String) map.get("name");
			if(null==name){
				name="";
			}
			orderTableReturnsDto.setCreateBy(name);
			orderTableReturnsDto.setCreateDate(new Date());
			orderTableReturnsDto.setDelFlag("0");
			orderTableReturnsDto.setReturnStatus("3");
			orderTableReturnsDto.setApplyTime(new Date());
			orderTableReturnsDto.setUserName(name);
			orderTableReturnsDto.setUpdateBy(name);
			orderTableReturnsDto.setUpdateDate(new Date());
			orderTableReturnsDao.insert(orderTableReturnsDto);
			
			//插入退货项表
			List<OrderTableReturnsItemDto> list=new ArrayList<OrderTableReturnsItemDto>();
			if(StringUtils.isNotEmpty(images) && images.indexOf(",") != -1) {
				String[] imagess=images.split(",");
				for (String image : imagess) {
					OrderTableReturnsItemDto orderTableReturnsItemDto=new OrderTableReturnsItemDto();
					orderTableReturnsItemDto.setId(UUIDGenerator.getUUID());
					orderTableReturnsItemDto.setOrderTableReturnsId(id);
					orderTableReturnsItemDto.setProductNo(orderTableReturnsDto.getProductId());
					orderTableReturnsItemDto.setName(productName);
					orderTableReturnsItemDto.setQuantity(orderTableReturnsDto.getQuantity());
					orderTableReturnsItemDto.setReturnNo(orderTableReturnsDto.getReturnNo());
					orderTableReturnsItemDto.setCreateBy(name);
					orderTableReturnsItemDto.setCreateDate(new Date());
					orderTableReturnsItemDto.setUpdateBy(name);
					orderTableReturnsItemDto.setUpdateDate(new Date());
					orderTableReturnsItemDto.setDelFlag("0");
					orderTableReturnsItemDto.setImage(image);
					list.add(orderTableReturnsItemDto);
				}
				orderTableReturnsDao.insertReturnsItem(list);
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.info("插入退货表失败",e);
			return false;
		}
		return true;
	}
}
