package com.uib.mobile.web;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.uib.common.enums.ExceptionEnum;
import com.uib.common.utils.JacksonUtil;
import com.uib.member.entity.Area;
import com.uib.member.entity.MemMember;
import com.uib.member.entity.MemReceiver;
import com.uib.member.service.MemMemberService;
import com.uib.mobile.dto.AreaResponseDto;
import com.uib.mobile.dto.MemReceiverDto;
import com.uib.mobile.dto.ReturnMsg;
import com.uib.serviceUtils.Utils;

/**
 * 手机 地址管理
 * 
 * @author limy
 * 
 */
@Controller
@RequestMapping("/mobile/member/address")
public class MobileAddressController {
	private Logger logger = LoggerFactory.getLogger("rootLogger");
	@Autowired
	private MemMemberService memMemberService;

	/**
	 * 查询区域地址
	 * 
	 * @param parentId
	 * @return areaResponseList
	 */
	@RequestMapping("/listByParentId")
	@ResponseBody
	public ReturnMsg<List<AreaResponseDto>> listByParentId(String parentId, HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) {
		ReturnMsg<List<AreaResponseDto>> returnMsg = new ReturnMsg<List<AreaResponseDto>>();
		try {
			logger.info("=======begin 手机APP调用listByParentId方法接口====================");
			logger.info("=======传参 parentId=" + parentId + "=============");
			if (Utils.isBlank(parentId)) {
				parentId = "1";
			}
			List<Area> areaList = memMemberService.findAreasByParentId(parentId);
			List<AreaResponseDto> areaResponseList = new ArrayList<AreaResponseDto>();
			if(areaList.size() ==0){
				returnMsg.setCode(ExceptionEnum.list_area_null.getIndex());
				returnMsg.setMsg(ExceptionEnum.list_area_null.getValue());
				returnMsg.setData(null);
				returnMsg.setStatus(false);
				return returnMsg;
			}
			String[] ignore = new String[] { "memReceiverList" };
			for (Area area : areaList) {
				AreaResponseDto areaDto = new AreaResponseDto();
				BeanUtils.copyProperties(area, areaDto, ignore);
				areaResponseList.add(areaDto);
			}
			returnMsg.setData(areaResponseList);
		} catch (Exception e) {
			returnMsg.setMsg(ExceptionEnum.business_logic_exception.getValue() + e.getMessage());
			returnMsg.setCode(ExceptionEnum.business_logic_exception.getIndex());
			returnMsg.setStatus(false);
			logger.error("查询地区报错！", e);
		}
		logger.info("返回参数:" + JacksonUtil.writeValueAsString(returnMsg));
		logger.info("=======end 手机APP调用listByParentId方法接口====================");
		return returnMsg;
	}

	/**
	 * 查询所有收货地址
	 * 
	 * @param userName
	 * @return memReceiverDtoList
	 */

	@RequestMapping("/listAddress")
	@ResponseBody
	public ReturnMsg<List<MemReceiverDto>> listAddress(String sessionId) {
		ReturnMsg<List<MemReceiverDto>> returnMsg = new ReturnMsg<List<MemReceiverDto>>();
		try {
			logger.info("=======begin 手机APP调用listAddress方法接口====================");
			logger.info("=======传参(会员名) sessionId=" + sessionId + "=============");
			MemMember member = memMemberService.getMemMemberBySessionId(sessionId);
			if (null == member) {
				logger.info("该会员不存在");
				returnMsg.setMsg(ExceptionEnum.member_not_exist.getValue());
				returnMsg.setCode(ExceptionEnum.member_not_exist.getIndex());
				returnMsg.setStatus(false);
				return returnMsg;
			}
			// 1.查询当前用户所有的地址
			List<MemReceiver> list = memMemberService.getMemReceiverByAddress(member.getId());
			List<MemReceiverDto> memReceiverDtoList = new ArrayList<MemReceiverDto>();
			if(list.size()==0){
				returnMsg.setCode(ExceptionEnum.list_address_null.getIndex());
				returnMsg.setMsg(ExceptionEnum.list_address_null.getValue());
				returnMsg.setData(null);
				returnMsg.setStatus(false);
				return returnMsg;
			}
			for (MemReceiver memReceiver : list) {
				MemReceiverDto memReceiverDto = new MemReceiverDto();
				BeanUtils.copyProperties(memReceiver, memReceiverDto);
				memReceiverDto.setUserName(member.getUsername());
				memReceiverDtoList.add(memReceiverDto);
			}
			returnMsg.setData(memReceiverDtoList);
		} catch (Exception e) {
			returnMsg.setMsg(ExceptionEnum.business_logic_exception.getValue() + e.getMessage());
			returnMsg.setCode(ExceptionEnum.business_logic_exception.getIndex());
			returnMsg.setStatus(false);
			logger.error("查询地址报错！", e);
		}
		logger.info("返回参数:" + JacksonUtil.writeValueAsString(returnMsg));
		logger.info("=======end listAddress 手机APP调用listAddress方法接口====================");
		return returnMsg;
	}
	
	/**
	 * 根据会员名查询默认地址
	 * 
	 * @param userName
	 * @return memReceiverDtoList
	 */
	@RequestMapping("/listDefaultAddress")
	@ResponseBody
	public ReturnMsg<MemReceiverDto> listDefaultAddress(String sessionId) {
		ReturnMsg<MemReceiverDto> returnMsg = new ReturnMsg<MemReceiverDto>();
		try {
			logger.info("=======begin 手机APP调用listDefaultAddress方法接口====================");
			logger.info("=======传参(会员名) userName=" + sessionId + "=============");
			MemMember member = memMemberService.getMemMemberBySessionId(sessionId);
			if (null == member) {
				logger.info("该会员不存在" );
				returnMsg.setMsg(ExceptionEnum.member_not_exist.getValue());
				returnMsg.setCode(ExceptionEnum.member_not_exist.getIndex());
				returnMsg.setStatus(false);
				return returnMsg;
			}
			MemReceiver defaultMemReceiver = memMemberService.getDefaultMemReceiverByMemberId(member.getId());
			
			if(null == defaultMemReceiver){
				defaultMemReceiver = memMemberService.getLastOrderReceiverByMemberId(member.getUsername());
			}
			MemReceiverDto memReceiverDto = new MemReceiverDto();
			BeanUtils.copyProperties(defaultMemReceiver, memReceiverDto);
			memReceiverDto.setUserName(member.getUsername());
			
			returnMsg.setData(memReceiverDto);
		} catch (Exception e) {
			returnMsg.setMsg(ExceptionEnum.business_logic_exception.getValue() + e.getMessage());
			returnMsg.setCode(ExceptionEnum.business_logic_exception.getIndex());
			returnMsg.setStatus(false);
			logger.error("查询默认地址报错！", e);
		}
		logger.info("返回参数:" + JacksonUtil.writeValueAsString(returnMsg));
		logger.info("=======end listDefaultAddress 手机APP调用listDefaultAddress方法接口====================");
		return returnMsg;
	}
	/**
	 * 修改收货地址
	 * 
	 * @param memReceiverDto
	 */
	@RequestMapping("/update")
	@ResponseBody
	public ReturnMsg<Object> update(String sessionId,MemReceiverDto memReceiverDto, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("=======begin 手机APP调用update方法接口====================");
		logger.info("=======传参 id=" + memReceiverDto.getId());
		logger.info("=======address=" + memReceiverDto.getAddress());
		logger.info("=======area=" + memReceiverDto.getArea());
		logger.info("=======areaName=" + memReceiverDto.getAreaName());
		logger.info("=======consignee=" + memReceiverDto.getConsignee());
		logger.info("=======sessionId=" + sessionId);
		logger.info("=======phone=" + memReceiverDto.getPhone());
		logger.info("=======isDefault=" + memReceiverDto.getIsDefault());
		ReturnMsg<Object> returnMsg = new ReturnMsg<Object>();
		MemReceiver memReceiver = new MemReceiver();
		BeanUtils.copyProperties(memReceiverDto, memReceiver);

		try {
			MemMember member = memMemberService.getMemMemberBySessionId(sessionId);
			if (null == memReceiverDto.getId()) {
				logger.info("该id不存在:" + memReceiverDto.getId());
				returnMsg.setMsg(ExceptionEnum.id_not_exist.getValue() + ":" + memReceiverDto.getId());
				returnMsg.setCode(ExceptionEnum.id_not_exist.getIndex());
				returnMsg.setStatus(false);
				return returnMsg;
			}
			if (null == member) {
				logger.info("该会员不存在");
				returnMsg.setMsg(ExceptionEnum.member_not_exist.getValue());
				returnMsg.setCode(ExceptionEnum.member_not_exist.getIndex());
				returnMsg.setStatus(false);
				return returnMsg;
			}
			if (null == memReceiverDto.getAddress()) {
				logger.info("该地址为空:" + memReceiverDto.getAddress());
				returnMsg.setMsg(ExceptionEnum.address_not_exist.getValue() + ":"
						+ memReceiverDto.getAddress());
				returnMsg.setCode(ExceptionEnum.address_not_exist.getIndex());
				returnMsg.setStatus(false);
				return returnMsg;
			}
			if (null == memReceiverDto.getArea()) {
				logger.info("该区域为空:" + memReceiverDto.getArea());
				returnMsg.setMsg(ExceptionEnum.area_not_exist.getValue() + ":" + memReceiverDto.getArea());
				returnMsg.setCode(ExceptionEnum.area_not_exist.getIndex());
				returnMsg.setStatus(false);
				return returnMsg;
			}
			if (null == memReceiverDto.getAreaName()) {
				logger.info("该区域名称为空:" + memReceiverDto.getAreaName());
				returnMsg.setMsg(ExceptionEnum.areaName_not_exist.getValue() + ":"
						+ memReceiverDto.getAreaName());
				returnMsg.setCode(ExceptionEnum.areaName_not_exist.getIndex());
				returnMsg.setStatus(false);
				return returnMsg;
			}
			if (null == memReceiverDto.getConsignee()) {
				logger.info("该联系人为空:" + memReceiverDto.getConsignee());
				returnMsg.setMsg(ExceptionEnum.consignee_not_exist.getValue() + ":"
						+ memReceiverDto.getConsignee());
				returnMsg.setCode(ExceptionEnum.consignee_not_exist.getIndex());
				returnMsg.setStatus(false);
				return returnMsg;
			}
			if (null == memReceiverDto.getPhone()) {
				logger.info("该手机号为空:" + memReceiverDto.getPhone());
				returnMsg.setMsg(ExceptionEnum.phone_not_exist.getValue() + ":"
						+ memReceiverDto.getPhone());
				returnMsg.setCode(ExceptionEnum.phone_not_exist.getIndex());
				returnMsg.setStatus(false);
				return returnMsg;
			}
			memReceiver.setMemMember(member);
			Timestamp d = new Timestamp(System.currentTimeMillis());
			memReceiver.setCreateDate(d);
			memReceiver.setUpdateDate(d);
			memReceiver.setDelFlag("0");
			memMemberService.update(memReceiver);
		} catch (Exception e) {
			returnMsg.setMsg(ExceptionEnum.business_logic_exception.getValue() + e.getMessage());
			returnMsg.setCode(ExceptionEnum.business_logic_exception.getIndex());
			returnMsg.setStatus(false);
			logger.error("修改地址报错！", e);
		}
		logger.info("=======end update 手机APP调用update方法接口====================");
		return returnMsg;
	}

	/**
	 * 修改默认收货地址
	 * 
	 * @param id
	 */
	@RequestMapping("/updateDefaultAddress")
	@ResponseBody
	public ReturnMsg<Object> updateDefaultAddress(String id) {
		logger.info("=======begin  手机APP调用updateDefaultAddress方法接口====================");
		logger.info("=======传参 id=" + id + "=============");	
		ReturnMsg<Object> returnMsg = new ReturnMsg<Object>();
		try {
			if (Utils.isObjectsBlank(id)) {
				logger.info("id参数为空");
				returnMsg.setMsg(ExceptionEnum.param_not_null.getValue());
				returnMsg.setCode(ExceptionEnum.param_not_null.getIndex());
				returnMsg.setStatus(false);
				return returnMsg;
			}
			MemReceiver memReceiver = memMemberService.getMemReceiverById(id);
			List<MemReceiver> list = memMemberService.getMemReceiverByAddress(memReceiver.getMemMember().getId());
			for (MemReceiver receiver : list) {
				if (receiver.getIsDefault()) {
					receiver.setIsDefault(false);
					memMemberService.update(receiver);
				}
				memMemberService.updateIsDefault(memReceiver);
			}
		} catch (Exception e) {
			returnMsg.setMsg(ExceptionEnum.business_logic_exception.getValue() + e.getMessage());
			returnMsg.setCode(ExceptionEnum.business_logic_exception.getIndex());
			returnMsg.setStatus(false);
			logger.error("更新默认地址报错!", e);
		}
		logger.info("=======end updateDefaultAddress 手机APP调用updateDefaultAddress方法接口====================");
		return returnMsg;
	}

	/**
	 * 保存收货地址
	 * 
	 * @param memReceiverDto
	 */
	@RequestMapping("/save")
	@ResponseBody
	public ReturnMsg<Object> save(String sessionId,MemReceiverDto memReceiverDto, HttpServletRequest request,
			HttpServletResponse response) {
		ReturnMsg<Object> returnMsg = new ReturnMsg<Object>();
		try {
			logger.info("=======begin save 手机APP调用updateDefaultAddress方法接口====================");
			logger.info("=======begin 手机APP调用update方法接口====================");
			logger.info("=======传参address=" + memReceiverDto.getAddress());
			logger.info("=======area=" + memReceiverDto.getArea());
			logger.info("=======areaName=" + memReceiverDto.getAreaName());
			logger.info("=======consignee=" + memReceiverDto.getConsignee());
			logger.info("=======sessionId=" + sessionId);
			logger.info("=======phone=" + memReceiverDto.getPhone());
			logger.info("=======isDefault=" + memReceiverDto.getIsDefault());
			MemReceiver memReceiver = new MemReceiver();
			BeanUtils.copyProperties(memReceiverDto, memReceiver);
			MemMember member = memMemberService.getMemMemberBySessionId(sessionId);
			if (null == member) {
				logger.info("该会员不存在" );
				returnMsg.setMsg(ExceptionEnum.member_not_exist.getValue());
				returnMsg.setCode(ExceptionEnum.member_not_exist.getIndex());
				returnMsg.setStatus(false);
				return returnMsg;
			}
			if (null == memReceiverDto.getAddress()) {
				logger.info("该地址为空:" + memReceiverDto.getAddress());
				returnMsg.setMsg(ExceptionEnum.address_not_exist.getValue() + ":"
						+ memReceiverDto.getAddress());
				returnMsg.setCode(ExceptionEnum.address_not_exist.getIndex());
				returnMsg.setStatus(false);
				return returnMsg;
			}
			if (null == memReceiverDto.getArea()) {
				logger.info("该区域为空:" + memReceiverDto.getArea());
				returnMsg.setMsg(ExceptionEnum.area_not_exist.getValue() + ":" + memReceiverDto.getArea());
				returnMsg.setCode(ExceptionEnum.area_not_exist.getIndex());
				returnMsg.setStatus(false);
				return returnMsg;
			}
			if (null == memReceiverDto.getAreaName()) {
				logger.info("该区域名称为空:" + memReceiverDto.getAreaName());
				returnMsg.setMsg(ExceptionEnum.areaName_not_exist.getValue() + ":"
						+ memReceiverDto.getAreaName());
				returnMsg.setCode(ExceptionEnum.areaName_not_exist.getIndex());
				returnMsg.setStatus(false);
				return returnMsg;
			}
			if (null == memReceiverDto.getConsignee()) {
				logger.info("该联系人为空:" + memReceiverDto.getConsignee());
				returnMsg.setMsg(ExceptionEnum.consignee_not_exist.getValue() + ":"
						+ memReceiverDto.getConsignee());
				returnMsg.setCode(ExceptionEnum.consignee_not_exist.getIndex());
				returnMsg.setStatus(false);
				return returnMsg;
			}
			if (null == memReceiverDto.getPhone()) {
				logger.info("该手机号为空:" + memReceiverDto.getPhone());
				returnMsg.setMsg(ExceptionEnum.phone_not_exist.getValue() + ":"
						+ memReceiverDto.getPhone());
				returnMsg.setCode(ExceptionEnum.phone_not_exist.getIndex());
				returnMsg.setStatus(false);
				return returnMsg;
			}
			memReceiver.setMemMember(member);
			memReceiver.setId(UUID.randomUUID().toString());
			Timestamp d = new Timestamp(System.currentTimeMillis());
			memReceiver.setCreateDate(d);
			memReceiver.setUpdateDate(d);
			memReceiver.setDelFlag("0");
			List<MemReceiver> list = memMemberService.getMemReceiverByAddress(member.getId());
			if (list.size() != 0) {
				for (MemReceiver receiver : list) {
					if (receiver.getIsDefault() && memReceiver.getIsDefault()) {
						receiver.setIsDefault(false);
						memMemberService.update(receiver);
					}
				}
			}
			memMemberService.save(memReceiver);
		} catch (Exception e) {
			returnMsg.setMsg(ExceptionEnum.business_logic_exception.getValue() + e.getMessage());
			returnMsg.setCode(ExceptionEnum.business_logic_exception.getIndex());
			returnMsg.setStatus(false);
			logger.error("保存地址出错！", e);
		}
		logger.info("=======end save 手机APP调用updateDefaultAddress方法接口====================");
		return returnMsg;
	}

	/**
	 * 删除收货地址
	 * 
	 * @param id
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public ReturnMsg<Object> delete(HttpServletRequest request, String id) {
		logger.info("=======begin delete 手机APP调用updateDefaultAddress方法接口====================");
		logger.info("=======传参 id=" + id + "=============");
		ReturnMsg<Object> returnMsg = new ReturnMsg<Object>();
		try {
			MemReceiver memReceiver = memMemberService.getMemReceiverById(id);
			if (null == id || memReceiver == null) {
				logger.info("该id不存在:" + id);
				returnMsg.setMsg(ExceptionEnum.id_not_exist.getValue() + ":" + id);
				returnMsg.setCode(ExceptionEnum.id_not_exist.getIndex());
				returnMsg.setStatus(false);
				return returnMsg;
			}
			memMemberService.delete(id);
		} catch (Exception e) {
			returnMsg.setMsg(ExceptionEnum.business_logic_exception.getValue() + e.getMessage());
			returnMsg.setCode(ExceptionEnum.business_logic_exception.getIndex());
			returnMsg.setStatus(false);
			logger.error("根据id删除地址失败!", e);
		}
		logger.info("=======end delete 手机APP调用updateDefaultAddress方法接口====================");
		return returnMsg;
	}

}
