package com.uib.ptyt.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.uib.common.enums.ExceptionEnum;
import com.uib.mobile.dto.ReturnMsg;
import com.uib.ptyt.entity.MemRankDto;
import com.uib.ptyt.service.MemRankService;

@RequestMapping("/memRank")
@Controller
public class MemRankController {

	private Logger logger = LoggerFactory.getLogger(MemRankController.class);
	
	@Autowired
	private MemRankService memRankService;
	
	/**根据给定的贡献值返回相应的会员等级
	 * @param amount
	 * @return
	 */
	@RequestMapping("/queryMemRankName")
	@ResponseBody
	public ReturnMsg<String> queryMemRankName(Double amount){
		logger.info("微信后台：根据给定的贡献值返回相应的会员等级amount=" + amount);
		ReturnMsg<String> result = new ReturnMsg<String>();
		if(null==amount){
			result.setStatus(false);
			result.setCode(ExceptionEnum.param_not_null.getIndex());
			result.setMsg(ExceptionEnum.param_not_null.getValue());
			return result;
		}
		try {
			List<MemRankDto> list=memRankService.queryMemRank();
			//把所有的贡献值放到一个数组
			List<Double> amounts=new ArrayList<Double>();
			for(MemRankDto memRankDto:list){
				amounts.add(memRankDto.getAmount());
			}
			//给数组排序
			Comparator<Double> comparator = new Comparator<Double>(){  
				   public int compare(Double d1, Double d2) {  
				     return (int) (d1-d2);  
				   }  
	        };  
	        int count=0;
	        int count1=0;
	        Collections.sort(amounts,comparator);
	        for(int i=0;i<amounts.size();i++){
	        	if(amount<amounts.get(i)){
	        		count=i;
	        		break;
	        	}
	        	if(amount.equals(amounts.get(i))){
	        		count1=i;
	        		break;
	        	}
	        	if(amount>amounts.get(amounts.size()-1)){
	        		result.setStatus(true);
	    			result.setData("蓝钻会员");
	    			return result;
	        	}
	        }
	        switch (count) {
	            case 0:
	            	break;
	            case 1:
	            	result.setStatus(true);
	    			result.setData("普通会员");
	    			return result;
	            case 2:
	            	result.setStatus(true);
	    			result.setData("铜牌会员");
	    			return result;
	            case 3:
	            	result.setStatus(true);
	    			result.setData("银牌会员");
	    			return result;
	            case 4:
	            	result.setStatus(true);
	    			result.setData("金牌会员");
	    			return result;
	            case 5:
	            	result.setStatus(true);
	    			result.setData("钻石会员");
	    			return result;
	            default:
				break;
	        }
	        switch (count1) {
            case 0:
            	result.setStatus(true);
    			result.setData("普通会员");
    			return result;
            case 1:
            	result.setStatus(true);
    			result.setData("铜牌会员");
    			return result;
            case 2:
            	result.setStatus(true);
    			result.setData("银牌会员");
    			return result;
            case 3:
            	result.setStatus(true);
    			result.setData("金牌会员");
    			return result;
            case 4:
            	result.setStatus(true);
    			result.setData("钻石会员");
    			return result;
            case 5:
            	result.setStatus(true);
    			result.setData("蓝钻会员");
    			return result;
            default:
			break;
            }
		} catch (Exception e) {
			logger.error("微信后台：根据给定的贡献值返回相应的会员等级失败!", e);
			result.setStatus(false);
			result.setCode(ExceptionEnum.business_logic_exception.getIndex());
			result.setMsg(ExceptionEnum.business_logic_exception.getValue());
		}
		return result;
	}
}
