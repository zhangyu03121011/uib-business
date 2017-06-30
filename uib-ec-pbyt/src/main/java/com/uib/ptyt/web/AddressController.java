package com.uib.ptyt.web;

import hk.com.easypay.bdp.service.ServiceException;
import hk.com.easypay.dict.dto.AddressDto;
import hk.com.easypay.dict.service.AddressService;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/ptyt/addr")
public class AddressController {
	private Logger logger = LoggerFactory.getLogger(AddressController.class);
	@Autowired
	private AddressService addressService;

	@ResponseBody
	@RequestMapping(value = "/findAddressList", produces = "text/html;charset=UTF-8")
	public String findAddressList() {
		List<AddressDto> addressDto = new ArrayList<AddressDto>();
		StringBuilder jsonData=new StringBuilder();
		jsonData.append("{\"data\": [");
		
		try{
			addressDto = addressService.getAddress();
			for(int i=0; i< addressDto.size(); i++){
				AddressDto addr = addressDto.get(i);
				jsonData.append("{\"id\": \""+addr.getId()+"\",\"name\": \""+addr.getName()+"\",\"child\":"+addr.getChild() + "}");
				if(i < addressDto.size() - 1){
					jsonData.append(",");
				}
			}
			jsonData.append("]}");
		}catch (ServiceException e) {
			logger.info("查询省市区树结构异常"+e);
			e.printStackTrace();
		}
		
		return jsonData.toString();
	}

}
