package com.uib.ptyt.web;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.uib.common.enums.ExceptionEnum;
import com.uib.mobile.dto.ReturnMsg;
import com.uib.ptyt.entity.OrderTableReturnsDto;
import com.uib.ptyt.service.OrderTableReturnsService;

@RequestMapping("/orderReturns")
@Controller
public class OrderTableReturnsController {

	private Logger logger = LoggerFactory.getLogger(OrderTableReturnsController.class);
	
	@Autowired
	private OrderTableReturnsService orderTableReturnsService;
	
	/**
	 * 查询退货单信息(单条)
	 * @param orderTableReturnsDto
	 * @return
	 */
	@RequestMapping("/queryReturns")
	@ResponseBody
	public ReturnMsg<OrderTableReturnsDto> queryReturns(String goodNo,String orderNo){
		ReturnMsg<OrderTableReturnsDto> result = new ReturnMsg<OrderTableReturnsDto>();
		try{
			if(StringUtils.isEmpty(goodNo) || StringUtils.isEmpty(orderNo)){
				result.setStatus(false);
				result.setCode(ExceptionEnum.param_not_null.getIndex());
				result.setMsg(ExceptionEnum.param_not_null.getValue());
				return result;
			}
			OrderTableReturnsDto orderTableReturnsDto=new OrderTableReturnsDto();
			orderTableReturnsDto.setProductId(goodNo);
			orderTableReturnsDto.setOrderNo(orderNo);
			OrderTableReturnsDto orderTableReturnsDto2=orderTableReturnsService.queryReturns(orderTableReturnsDto);
			result.setStatus(true);
			result.setData(orderTableReturnsDto2);
		}catch (Exception e) {
			logger.error("微信后台： 查询退货单信息(单条)失败!", e);
			result.setStatus(false);
			result.setCode(ExceptionEnum.business_logic_exception.getIndex());
			result.setMsg(ExceptionEnum.business_logic_exception.getValue());
		}
		return result;
	}

	/**
	 * 插入退货信息
	 * @param orderTableReturnsDto
	 * @return
	 */
	@RequestMapping("/insert")
	@ResponseBody
	public ReturnMsg<Object> insert(OrderTableReturnsDto orderTableReturnsDto){
		ReturnMsg<Object> result = new ReturnMsg<Object>();
		try{
			boolean flag=orderTableReturnsService.insert(orderTableReturnsDto);
			if(flag){
				result.setStatus(true);
			}else{
				result.setStatus(false);
				result.setCode(ExceptionEnum.business_logic_exception.getIndex());
				result.setMsg(ExceptionEnum.business_logic_exception.getValue());
			}
		}catch (Exception e) {
			logger.error("微信后台： 插入退货信息失败!", e);
			result.setStatus(false);
			result.setCode(ExceptionEnum.business_logic_exception.getIndex());
			result.setMsg(ExceptionEnum.business_logic_exception.getValue());
		}
		return result;
	}
}
