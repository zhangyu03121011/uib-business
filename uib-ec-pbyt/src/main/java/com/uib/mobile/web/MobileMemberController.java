package com.uib.mobile.web;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.RequestContext;

import com.uib.common.enums.ExceptionEnum;
import com.uib.common.utils.DateUtil;
import com.uib.common.utils.JacksonUtil;
import com.uib.common.utils.StringUtils;
import com.uib.common.utils.UUIDGenerator;
import com.uib.member.dto.MemberDto;
import com.uib.member.dto.ValidateCodeInfo;
import com.uib.member.entity.MemMember;
import com.uib.member.service.MemMemberService;
import com.uib.mobile.constants.Constants;
import com.uib.mobile.dto.ComplaintApplicationDto;
import com.uib.mobile.dto.MemberIdCartDto;
import com.uib.mobile.dto.ReturnMsg;
import com.uib.mobile.dto.TbAttachment;
import com.uib.mobile.dto.UserComplaintAttrMap;
import com.uib.mobile.service.CommentService;
import com.uib.mobile.service.ComplainApplicationService;
import com.uib.mobile.service.TbAttachmentService;
import com.uib.mobile.service.UserComplaintAttrMapService;
import com.uib.serviceUtils.Utils;

@Controller
@RequestMapping("/mobile/member/user")
public class MobileMemberController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private MemMemberService memMemberService;

	@Autowired
	private ComplainApplicationService complainApplicationservice;
	
	@Autowired
	private UserComplaintAttrMapService userComplaintAttrMapService;
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private TbAttachmentService tbAttachmentService;

	@Value("${upload.image.path}")
	private String UPLOAD_IMAGE_PATH;

	/**
	 * 修改密码
	 * 
	 * @param userName
	 * @param password
	 * @param password1
	 * @param phone
	 * @param validateCode
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/updatePassword")
	@ResponseBody
	public ReturnMsg<Object> updatePassword(String password, String password1,
			String oldPassword, String sessionId, HttpServletRequest request) {
		ReturnMsg<Object> returnMsg = new ReturnMsg<Object>();
		try {
			// 从后台代码获取国际化信息
			RequestContext requestContext = new RequestContext(request);
			if (StringUtils.isEmpty(sessionId) || StringUtils.isEmpty(password)
					|| StringUtils.isEmpty(password1)
					|| StringUtils.isEmpty(oldPassword)) {
				returnMsg.setCode(ExceptionEnum.param_not_null.getIndex());
				returnMsg.setStatus(false);
				returnMsg.setMsg(ExceptionEnum.param_not_null.getValue());
				return returnMsg;
			}
			MemMember member = memMemberService.getMemMemberBySessionId(sessionId);
			if (!oldPassword.equalsIgnoreCase(member.getPassword())) {
				returnMsg.setCode(ExceptionEnum.old_password_error.getIndex());
				returnMsg.setStatus(false);
				returnMsg.setMsg(ExceptionEnum.old_password_error.getValue());
				return returnMsg;
			}
			/*if (password.equalsIgnoreCase(password1)) {
				returnMsg.setCode(ExceptionEnum.password_different.getIndex());
				returnMsg.setStatus(false);
				returnMsg.setMsg(ExceptionEnum.password_different.getValue());
				return returnMsg;
			}*/
			memMemberService.updatePassword(password,member.getUsername());
			returnMsg.setStatus(true);
			returnMsg.setMsg(requestContext
					.getMessage("user.updatepassword.ok"));
		} catch (Exception ex) {
			returnMsg.setCode(ExceptionEnum.system_error.getIndex());
			returnMsg.setStatus(false);
			returnMsg.setMsg(ExceptionEnum.system_error.getValue());
			logger.error("手机修改密码方法updatePassword出错", ex);
			return returnMsg;
		}
		return returnMsg;
	}

	/**
	 * 
	 * @Title: identityAuthentication
	 * @author sl 提交身份验证
	 */
	@ResponseBody
	@RequestMapping("/identityAuthentication")
	public ReturnMsg<String> identityAuthentication(
			@RequestParam Map<String, String> param, HttpServletRequest request) {
		logger.info("提交身份验证开始mobile" + param.toString());
		String idCard = param.get("idCard");
		ReturnMsg<String> returnMsg = new ReturnMsg<String>();
		if (Utils.isObjectsBlank(param.get("idCardPositive"),
				param.get("idCardOpposite"), param.get("idCardHand"),
				param.get("realName"), param.get("idCard"), param.get("sessionId"))) {
			returnMsg.setStatus(false);
			returnMsg.setCode(ExceptionEnum.param_not_null.getIndex());
			return returnMsg;
		}
		try{
			Map<String,Object> memberInfo = new HashMap<String,Object>();
			memberInfo = memMemberService.getMemberbyCardId(idCard);
			String approveFlag = memberInfo.get("approve_flag").toString();
			//审核中，审核通过不提交
			if(approveFlag.equals("0")||approveFlag.equals("1")){
				returnMsg.setStatus(false);
				returnMsg.setCode(ExceptionEnum.approve_passed.getIndex());
				returnMsg.setMsg("审核成功或审核中！");
				return returnMsg;
			}
			param.put("approveFlag", "0");
			memMemberService.updateById(param);
			returnMsg.setMsg(new RequestContext(request)
					.getMessage("submit.succeed"));
			return returnMsg;
		}catch(Exception ex){
			returnMsg.setStatus(false);
			returnMsg.setMsg("提交失败！");
			return returnMsg;
		}
		/*param.put("approveFlag", "0");
		memMemberService.updateById(param);
		returnMsg.setMsg(new RequestContext(request)
				.getMessage("submit.succeed"));
		return returnMsg;*/
	}

	/**
	 * 根据用户名查询身份认证信息
	 * 
	 * @param userName
	 * @return
	 */
	@RequestMapping("/getApproveByUserName")
	@ResponseBody
	public ReturnMsg<MemberIdCartDto> getApproveByUserName(
			@RequestParam String sessionId) {
		ReturnMsg<MemberIdCartDto> returnMsg = new ReturnMsg<MemberIdCartDto>();
		try {
			logger.info("============ begin 获取身分验证信息接口 getApproveByUserName=================");
			logger.info("获取身分验证信息接口 getApproveByUserName--sessionId参数= "
					+ sessionId);
			MemMember member = memMemberService
					.getMemMemberBySessionId(sessionId);
			if (null == member) {
				returnMsg.setStatus(false);
				returnMsg.setCode(ExceptionEnum.member_exist.getIndex());
				returnMsg.setMsg(ExceptionEnum.member_exist.getValue());
				return returnMsg;
			}
			//logger.info("member：" + JsonMapper.toJsonString(member));
			MemberIdCartDto memberIdCartDto = new MemberIdCartDto();
			memberIdCartDto.setIdCard(member.getIdCard());
			memberIdCartDto.setApproveFlag(member.getApproveFlag());
			memberIdCartDto.setAuditFailureDescription(member
					.getAuditFailureDescription());
			memberIdCartDto.setIdCardPositive(UPLOAD_IMAGE_PATH
					+ member.getIdCardPositive());
			memberIdCartDto.setIdCardOpposite(UPLOAD_IMAGE_PATH
					+ member.getIdCardOpposite());
			memberIdCartDto.setIdCardHand(UPLOAD_IMAGE_PATH
					+ member.getIdCardHand());

			memberIdCartDto.setUserName(member.getUsername());
			memberIdCartDto.setName(member.getRealName());
			memberIdCartDto.setId(member.getId());
			memberIdCartDto.setApproveDate(member.getApproveDate());
			//logger.info("memberIdCartDto：" + JsonMapper.toJsonString(memberIdCartDto));
			returnMsg.setStatus(true);
			returnMsg.setData(memberIdCartDto);
			logger.info("============ end 获取身分验证信息接口 getApproveByUserName=================");
		} catch (Exception ex) {
			ex.printStackTrace();
			returnMsg.setCode(ExceptionEnum.system_error.getIndex());
			returnMsg.setStatus(false);
			returnMsg.setMsg(ExceptionEnum.system_error.getValue());
			logger.error("手机查看身份认证方法getApproveByUserName出错", ex);
			return returnMsg;
		}
		return returnMsg;
	}
	
	
	/**
	 * 验证验证码是否填写正确
	 * 
	 * @param phone
	 * @param formCode
	 * @return
	 */
	private Map<String, Object> validateCode(String phone, String formCode) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		ValidateCodeInfo codeInfo = Constants.codeMap.get(phone);
		String fromPageValidateCode = formCode;
		ReturnMsg<Object> returnMsg = new ReturnMsg<Object>();
		if (null == codeInfo || null == codeInfo.getValidateCode() || null == fromPageValidateCode) {
			returnMsg.setCode(ExceptionEnum.validate_code_error.getIndex());
			returnMsg.setStatus(false);
			returnMsg.setMsg(ExceptionEnum.validate_code_error.getValue());
			map.put("errorFlag", true);
			map.put("returnMsg", returnMsg);
			return map;
		}
		long time = DateUtil.compareDateStr(codeInfo.getCreateDate(), DateUtil.dateToDateString(new Date()));
		if (time > 120000) {
			// 验证码超时请重新获取
			returnMsg.setCode(ExceptionEnum.validate_code_timeout.getIndex());
			returnMsg.setStatus(false);
			returnMsg.setMsg(ExceptionEnum.validate_code_timeout.getValue());
			map.put("errorFlag", true);
			map.put("returnMsg", returnMsg);
			return map;
		}

		if (!codeInfo.getValidateCode().equals(fromPageValidateCode)) {
			// 验证码验证不通过
			returnMsg.setCode(ExceptionEnum.validate_code_error.getIndex());
			returnMsg.setStatus(false);
			returnMsg.setMsg(ExceptionEnum.validate_code_error.getValue());
			map.put("errorFlag", true);
			map.put("returnMsg", returnMsg);
			return map;
		}
		map.put("errorFlag", false);
		return map;
	}
	
	/**
	 * 设置/忘记支付密码
	 * @param memberDto
	 * @param request
	 * @param response
	 * @param modelMap
	 * @param session
	 * @return
	 */
	@RequestMapping("/savePayPasswordByUserName")
	@ResponseBody
	public ReturnMsg<Object> savePayPasswordByUserName(@RequestParam String sessionId,@ModelAttribute MemberDto memberDto, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, HttpSession session){
		ReturnMsg<Object> returnMsg = new ReturnMsg<Object>();
		RequestContext requestContext = new RequestContext(request);
		try {
		String fromPageValidateCode = request.getParameter("validateCode");
		if (StringUtils.isEmpty( memberDto.getPhone()) || StringUtils.isEmpty(memberDto.getPayPassword()) ||StringUtils.isEmpty(fromPageValidateCode)) {
			returnMsg.setCode(ExceptionEnum.param_not_null.getIndex());
			returnMsg.setStatus(false);
			returnMsg.setMsg(ExceptionEnum.param_not_null.getValue());
			return returnMsg;
		}
		MemMember member = memMemberService.getMemMemberBySessionId(sessionId);
		if (null == member) {
			logger.info("该会员不存在");
			returnMsg.setMsg(ExceptionEnum.member_not_exist.getValue());
			returnMsg.setCode(ExceptionEnum.member_not_exist.getIndex());
			returnMsg.setStatus(false);
			return returnMsg;
		}
		Map<String, Object> map = validateCode(memberDto.getPhone(), fromPageValidateCode);
		Boolean errorFlag = (Boolean) map.get("errorFlag");
		if (errorFlag == true) {
			return (ReturnMsg<Object>) map.get("returnMsg");
		}		
		memMemberService.updatePayPassword(member.getId(), memberDto.getPayPassword());
		returnMsg.setMsg(requestContext.getMessage("user.updatepaypassword.ok"));
		returnMsg.setStatus(true);
		returnMsg.setData(member);	
		}catch (Exception ex) {	
			ex.printStackTrace();
			returnMsg.setStatus(false);
			returnMsg.setMsg(requestContext.getMessage("user.updatepaypassword.failregist"));
			return returnMsg;
		}
		return returnMsg;
		
	}
	
	/**
	 * 更新支付密码
	 * @param sessionId
	 * @param payPassword
	 * @param oldpayPassword
	 * @param request
	 * @param response
	 * @param modelMap
	 * @param session
	 * @return
	 */
	@RequestMapping("/updatePayPasswordByUserName")
	@ResponseBody
	public ReturnMsg<Object> updatePayPasswordByUserName(String sessionId,String payPassword,String oldpayPassword, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, HttpSession session){
		ReturnMsg<Object> returnMsg = new ReturnMsg<Object>();
		RequestContext requestContext = new RequestContext(request);
		try {
			if (StringUtils.isEmpty( payPassword) || StringUtils.isEmpty(oldpayPassword)) {
				returnMsg.setCode(ExceptionEnum.param_not_null.getIndex());
				returnMsg.setStatus(false);
				returnMsg.setMsg(ExceptionEnum.param_not_null.getValue());
				return returnMsg;
			}
			MemMember member = memMemberService.getMemMemberBySessionId(sessionId);
			if (null == member) {
				logger.info("该会员不存在");
				returnMsg.setMsg(ExceptionEnum.member_not_exist.getValue());
				returnMsg.setCode(ExceptionEnum.member_not_exist.getIndex());
				returnMsg.setStatus(false);
				return returnMsg;
			}
			if(!oldpayPassword.equals(member.getPayPassword())){
				logger.info("与原始密码不一致");
				returnMsg.setMsg(ExceptionEnum.old_password_error.getValue());
				returnMsg.setCode(ExceptionEnum.old_password_error.getIndex());
				returnMsg.setStatus(false);
				return returnMsg;
			}
			memMemberService.updatePayPassword(member.getId(), payPassword);
			returnMsg.setMsg(requestContext.getMessage("user.updatepaypassword.ok"));
			returnMsg.setStatus(true);
			returnMsg.setData(member);	
		
		}catch (Exception ex) {	
			ex.printStackTrace();
			returnMsg.setStatus(false);
			returnMsg.setMsg(requestContext.getMessage("user.updatepaypassword.failregist"));
			return returnMsg;
		}
		return returnMsg;
		
	}
	

//	/**
//	 * 投诉申请
//	 * 
//	 * @param sessionId
//	 * @param feedbackType
//	 * @param describe
//	 * @param images
//	 * @return
//	 */
//	@RequestMapping("/complaintApplication")
//	@ResponseBody
//	public ReturnMsg<Object> complaintApplication(HttpServletRequest request, @RequestParam String sessionId, ComplaintApplicationDto complaintApplicationDto) {
//		ReturnMsg<Object> returnMsg = new ReturnMsg<Object>();
//		try {
//			logger.info("============ begin 投诉申请接口complaintApplication=================");
//			logger.info("============ complaintApplication--FeedbackType参数= "+ complaintApplicationDto.getFeedbackType() );
//			logger.info("============ complaintApplication--DescribeInfo参数= "+ complaintApplicationDto.getDescribeInfo() );
//			logger.info("============complaintApplication--image参数= "+ complaintApplicationDto.getImages() );
//			logger.info("============ complaintApplication--title参数= "+ complaintApplicationDto.getTitle() );
//
//			if ( StringUtils.isEmpty(sessionId)) {
//				returnMsg.setStatus(false);
//				returnMsg.setCode(ExceptionEnum.param_not_null.getIndex());
//				returnMsg.setMsg(ExceptionEnum.param_not_null.getValue());
//				return returnMsg;
//			}
//			MemMember memMerber = memMemberService
//					.getMemMemberBySessionId(sessionId);
//			if (memMerber == null) {
//				returnMsg.setStatus(false);
//				returnMsg.setCode(ExceptionEnum.member_not_exist.getIndex());
//				returnMsg.setMsg(ExceptionEnum.member_not_exist.getValue());
//				return returnMsg;
//			}
//			Timestamp d = new Timestamp(System.currentTimeMillis());
//			complaintApplicationDto.setId(UUID.randomUUID().toString());
//			complaintApplicationDto.setMemberId(memMerber.getId());
//			complaintApplicationDto.setCreateTime(d);
//			complaintApplicationDto.setModifyDate(d);
//			complaintApplicationDto.setDelFlag("0");
//			complainApplicationservice.save(complaintApplicationDto);
//			returnMsg.setStatus(true);
//		} catch (Exception ex) {
//			returnMsg.setCode(ExceptionEnum.system_error.getIndex());
//			returnMsg.setStatus(false);
//			returnMsg.setMsg(ExceptionEnum.system_error.getValue());
//			logger.error("添加投诉申请异常", ex);
//			return returnMsg;
//		}
//		logger.info("=======end save 手机APP调用complaintApplication方法接口====================");
//		return returnMsg;
//
//	}
	
	
	/**
	 * 投诉申请
	 * 
	 * @param sessionId
	 * @param feedbackType
	 * @param describe
	 * @param images
	 * @return
	 */
	@RequestMapping("/complaintApplication")
	@ResponseBody
	public ReturnMsg<Object> complaintApplication(HttpServletRequest request, @RequestParam String sessionId, ComplaintApplicationDto complaintApplicationDto) {
		ReturnMsg<Object> returnMsg = new ReturnMsg<Object>();
		try {
			logger.info("============ begin 投诉申请接口complaintApplication=================");
			logger.info("============ complaintApplication--FeedbackType参数= "+ complaintApplicationDto.getFeedbackType() );
			logger.info("============ complaintApplication--DescribeInfo参数= "+ complaintApplicationDto.getDescribeInfo() );
			logger.info("============ complaintApplication--title参数= "+ complaintApplicationDto.getTitle() );
			if ( StringUtils.isEmpty(sessionId)) {
				returnMsg.setStatus(false);
				returnMsg.setCode(ExceptionEnum.param_not_null.getIndex());
				returnMsg.setMsg(ExceptionEnum.param_not_null.getValue());
				return returnMsg;
			}
			MemMember memMerber = memMemberService
					.getMemMemberBySessionId(sessionId);
			if (null == memMerber) {
				returnMsg.setMsg(ExceptionEnum.member_not_exist.getValue());
				returnMsg.setCode(ExceptionEnum.member_not_exist.getIndex());
				returnMsg.setStatus(false);
				return returnMsg;
			}
			Timestamp d = new Timestamp(System.currentTimeMillis());
			String complaintId=UUIDGenerator.getUUID();
			complaintApplicationDto.setId(complaintId);
			complaintApplicationDto.setMemberId(memMerber.getId());
			complaintApplicationDto.setCreateTime(d);
			complaintApplicationDto.setModifyDate(d);
			complaintApplicationDto.setDelFlag("0");	
			complainApplicationservice.save(complaintApplicationDto);	
			
			List<TbAttachment> tbAttachmentList = complaintApplicationDto.getTbAttachment();	
			
			String filePath0= request.getParameter("tbAttachment.filePath0");
			String filePath1= request.getParameter("tbAttachment.filePath1");
			String filePath2= request.getParameter("tbAttachment.filePath2");
			logger.info("============ complaintApplication--filePath0参数= "+ filePath0 );
			logger.info("============ complaintApplication--filePath1参数= "+ filePath1 );
			logger.info("============ complaintApplication--filePath2参数= "+filePath2 );
			if(null!=filePath0 && !("").equals(filePath0)){
				String attachmentId0 = UUIDGenerator.getUUID();
				String fileName = filePath0.substring(filePath0.lastIndexOf("/")+1, filePath0.lastIndexOf("."));
				String filePath = filePath0;
				this.addTbAttachment(attachmentId0, complaintId, fileName, filePath, tbAttachmentList);					
			}
			if(null!=filePath1 && !("").equals(filePath1)){
				String attachmentId0 = UUIDGenerator.getUUID();
				String fileName = filePath1.substring(filePath1.lastIndexOf("/")+1, filePath1.lastIndexOf("."));
				String filePath = filePath1;
				this.addTbAttachment(attachmentId0, complaintId, fileName, filePath, tbAttachmentList);	
			}
			if(null!=filePath2 && !("").equals(filePath2)){
				String attachmentId0 = UUIDGenerator.getUUID();
				String fileName = filePath2.substring(filePath2.lastIndexOf("/")+1, filePath2.lastIndexOf("."));
				String filePath = filePath2;
				this.addTbAttachment(attachmentId0, complaintId, fileName, filePath, tbAttachmentList);		
			}		
			returnMsg.setStatus(true);
		} catch (Exception ex) {
			returnMsg.setCode(ExceptionEnum.system_error.getIndex());
			returnMsg.setStatus(false);
			returnMsg.setMsg(ExceptionEnum.system_error.getValue());
			logger.error("添加投诉申请异常", ex);
			return returnMsg;
		}
		logger.info("=======end save 手机APP调用complaintApplication方法接口====================");
		return returnMsg;

	}
	
	private void addTbAttachment(String id,String complaintId,String fileName,String filePath,List<TbAttachment> tbAttachmentList){
		try {
			TbAttachment tbAttachment =new TbAttachment();
			tbAttachment.setId(id);
			tbAttachment.setFileName(fileName);
			tbAttachment.setFilePath(filePath);
			tbAttachmentService.save(tbAttachment);		
			tbAttachmentList.add(tbAttachment);
	
			UserComplaintAttrMap userComplaintAttrMap =new UserComplaintAttrMap();
			userComplaintAttrMap.setId(UUIDGenerator.getUUID());
			userComplaintAttrMap.setComplaintId(complaintId);
			userComplaintAttrMap.setAttachmentId(id);
			userComplaintAttrMapService.save(userComplaintAttrMap);	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	}
	
	
	
	
	/**
	 * 查询投诉记录
	 * @param sessionId
	 * @return
	 */
	@RequestMapping("/complainRecords")
	@ResponseBody
	public ReturnMsg<List<ComplaintApplicationDto>> findcomplainRecordsByPage(HttpServletRequest request, @RequestParam String sessionId){
		ReturnMsg<List<ComplaintApplicationDto>> result = new ReturnMsg<List<ComplaintApplicationDto>>();
		try {
			logger.info("=======begin 手机微信调用complainRecords方法接口====================");
			if (StringUtils.isEmpty(sessionId)) {
				result.setStatus(false);
				result.setCode(ExceptionEnum.param_not_null.getIndex());
				result.setMsg(ExceptionEnum.param_not_null.getValue());
				return result;
			}
			MemMember memMerber = memMemberService
					.getMemMemberBySessionId(sessionId);
			if (memMerber == null) {
				result.setStatus(false);
				result.setCode(ExceptionEnum.member_not_exist.getIndex());
				result.setMsg(ExceptionEnum.member_not_exist.getValue());
				return result;
			}
			//查询当前用户的投诉记录
			List<ComplaintApplicationDto> list = complainApplicationservice.findcomplainRecords(memMerber.getId());
			//List<ComplaintApplicationDto> ComplaintApplicationDtoList = new ArrayList<ComplaintApplicationDto>();
			if(list.size()==0){
				result.setCode(ExceptionEnum.list_complaint_null.getIndex());
				result.setMsg(ExceptionEnum.list_complaint_null.getValue());
				result.setData(null);
				result.setStatus(false);
				return result;
			}
			for(ComplaintApplicationDto complaintApplication :list){	
				if(null!=complaintApplication.getCreateTime()){
					complaintApplication.setCreateTimeStr(new SimpleDateFormat("yyyy-MM-dd").format(complaintApplication.getCreateTime()));
				}
				if(null!=complaintApplication.getSolutionState()){
					complaintApplication.setSolutionTimeStr(new SimpleDateFormat("yyyy-MM-dd").format(complaintApplication.getSolutionTime()));
				}				
			}
			result.setData(list);
			result.setStatus(true);
			} catch (Exception e) {
				logger.error("微信后台：查询商品失败!", e);
				result.setStatus(false);
				result.setCode(ExceptionEnum.business_logic_exception.getIndex());
				result.setMsg(ExceptionEnum.business_logic_exception.getValue());
			}
			logger.info("返回参数:" + JacksonUtil.writeValueAsString(result));
			logger.info("=======end complainRecords 手机APP调用complainRecords方法接口====================");
			return result;
	}

	
	
	
	/**
	 * 查询一个投诉记录
	 * @param request
	 * @param sessionId
	 * @param id
	 * @return
	 */
	@RequestMapping("/queryOneComplaintApplication")
	@ResponseBody
	public ReturnMsg<ComplaintApplicationDto> queryOneComplaintApplication(HttpServletRequest request, String sessionId, String id){
		ReturnMsg<ComplaintApplicationDto> returnMsg = new ReturnMsg<ComplaintApplicationDto>();
		try {
			logger.info("=======begin 手机微信调用queryOneComplaintApplication方法接口====================");
			logger.info("=======begin 手机微信调用queryOneComplaintApplication方法接口====================");
			if (StringUtils.isEmpty(id)||StringUtils.isEmpty(sessionId)) {
				returnMsg.setStatus(false);
				returnMsg.setCode(ExceptionEnum.param_not_null.getIndex());
				returnMsg.setMsg(ExceptionEnum.param_not_null.getValue());
				return returnMsg;
			}
			MemMember memMerber = memMemberService.getMemMemberBySessionId(sessionId);
			if (memMerber == null) {
				returnMsg.setStatus(false);
				returnMsg.setCode(ExceptionEnum.member_not_exist.getIndex());
				returnMsg.setMsg(ExceptionEnum.member_not_exist.getValue());
				return returnMsg;
			}
			ComplaintApplicationDto complaintApplicationDto = complainApplicationservice.getComplaintApplicationById(id);
			returnMsg.setData(complaintApplicationDto);
		} catch (Exception e) {
			returnMsg.setMsg(ExceptionEnum.business_logic_exception.getValue() + e.getMessage());
			returnMsg.setCode(ExceptionEnum.business_logic_exception.getIndex());
			returnMsg.setStatus(false);
			logger.error("查询投诉记录报错！", e);
		}
		logger.info("返回参数:" + JacksonUtil.writeValueAsString(returnMsg));
		logger.info("=======end queryOneComplaintApplication 手机APP调用queryOneComplaintApplication方法接口====================");
		return returnMsg;
		
	}

	/**
	 * 删除投诉记录
	 * @param sessionId
	 * @return
	 */
	@RequestMapping("/deleteComplainRecord")
	@ResponseBody
	public ReturnMsg<Object> deleteComplainRecord(HttpServletRequest request,String sessionId, String id){
		logger.info("=======begin delete 手机APP调用deleteComplainRecord方法接口====================");
		logger.info("=======传参 id=" + id + "=============");
		ReturnMsg<Object> returnMsg = new ReturnMsg<Object>();
		try {
			if (StringUtils.isEmpty(id)||StringUtils.isEmpty(sessionId)) {
				returnMsg.setStatus(false);
				returnMsg.setCode(ExceptionEnum.param_not_null.getIndex());
				returnMsg.setMsg(ExceptionEnum.param_not_null.getValue());
				return returnMsg;
			}
			MemMember memMerber = memMemberService.getMemMemberBySessionId(sessionId);
			if (memMerber == null) {
				returnMsg.setStatus(false);
				returnMsg.setCode(ExceptionEnum.member_not_exist.getIndex());
				returnMsg.setMsg(ExceptionEnum.member_not_exist.getValue());
				return returnMsg;
			}
			ComplaintApplicationDto complaintApplicationDto = complainApplicationservice.getComplaintApplicationById(id);
			if (complaintApplicationDto == null) {
				logger.info("该id不存在:" + id);
				returnMsg.setMsg(ExceptionEnum.id_not_exist.getValue() + ":" + id);
				returnMsg.setCode(ExceptionEnum.id_not_exist.getIndex());
				returnMsg.setStatus(false);
				return returnMsg;
			}
			complainApplicationservice.deleteComplainRecord(id);
		}catch (Exception e) {
			returnMsg.setMsg(ExceptionEnum.business_logic_exception.getValue() + e.getMessage());
			returnMsg.setCode(ExceptionEnum.business_logic_exception.getIndex());
			returnMsg.setStatus(false);
			logger.error("根据id删除投诉记录失败!", e);
		}
		logger.info("=======end delete 手机APP调用updateDefaultAddress方法接口====================");
		return returnMsg;
		
	}
	
	

	/**
	 * 查询用户是否已预授权
	 * 
	 * @param userName
	 * @return
	 */
	@RequestMapping("/queryIsAuthByUserName")
	@ResponseBody
	public ReturnMsg<MemberIdCartDto> queryIsAuthByUserName(
			@RequestParam String sessionId, @RequestParam String authFlag) {
		ReturnMsg<MemberIdCartDto> returnMsg = new ReturnMsg<MemberIdCartDto>();
		try {
			logger.info("============ begin 查询用户是否已预授权接口 queryIsAuthByUserName=================");
			logger.info("获取身分验证信息接口 getApproveByUserName--userName参数= "
					+ sessionId);

			if (StringUtils.isEmpty(sessionId) || StringUtils.isEmpty(authFlag)) {
				returnMsg.setStatus(false);
				returnMsg.setCode(ExceptionEnum.param_not_null.getIndex());
				returnMsg.setMsg(ExceptionEnum.param_not_null.getValue());
				return returnMsg;
			}
			// 根据sessionId查询会员信息
			MemMember memMemberBySessionId = memMemberService
					.getMemMemberBySessionId(sessionId);
			MemMember member = null;
			if (null != memMemberBySessionId) {
				member = memMemberService.queryIsAuthByUserName(
						memMemberBySessionId.getId(), authFlag);
			}
			if (null == member) {
				returnMsg.setStatus(false);
				returnMsg.setCode(ExceptionEnum.member_not_pre_auth.getIndex());
				returnMsg.setMsg(ExceptionEnum.member_not_pre_auth.getValue());
				return returnMsg;
			}
			logger.info("============ end 查询用户是否已预授权接口 queryIsAuthByUserName=================");
		} catch (Exception ex) {
			returnMsg.setCode(ExceptionEnum.system_error.getIndex());
			returnMsg.setStatus(false);
			returnMsg.setMsg(ExceptionEnum.system_error.getValue());
			logger.error("查询用户是否已预授权异常", ex);
			return returnMsg;
		}
		return returnMsg;
	}
	
	/**
	 * 更新手机号码
	 * @param sessionId
	 * @param phone
	 * @param validateCode
	 * @param phoneFlag
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/updatePhoneByPhone")
	@ResponseBody
	public ReturnMsg<Object> updatePhoneByPhone(String sessionId, String phone, String validateCode,String phoneFlag, HttpServletRequest request) {
		ReturnMsg<Object> returnMsg = new ReturnMsg<Object>();
		try {
			if (StringUtils.isEmpty(sessionId) || StringUtils.isEmpty(phone) || StringUtils.isEmpty(validateCode) || StringUtils.isEmpty(phoneFlag)) {
				returnMsg.setCode(ExceptionEnum.param_not_null.getIndex());
				returnMsg.setStatus(false);
				returnMsg.setMsg(ExceptionEnum.param_not_null.getValue());
				return returnMsg;
			}
			
			Map<String, Object> map = validateCode(phone, validateCode);
			Boolean errorFlag = (Boolean) map.get("errorFlag");
			if (errorFlag == true) {
				returnMsg.setStatus(false);
				returnMsg.setMsg((String)map.get("returnMsg"));
				return returnMsg;
			}
			
			//登录手机号码
			HashMap<String, String> memMemberMap = new HashMap<String, String>();
			memMemberMap.put("sessionId", sessionId);
			if (Constants.UPDATE_LOGINPHONE_FLAG.equals(phoneFlag)) {
				memMemberMap.put("phone", phone);
				memMemberService.updateById(memMemberMap);
			//支付手机号码
			} else if (Constants.UPDATE_PAYPHONE_FLAG.equals(phoneFlag)) {
				memMemberMap.put("payPhone", phone);
				memMemberService.updateById(memMemberMap);
			}
			returnMsg.setStatus(true);
		} catch (Exception ex) {
			returnMsg.setCode(ExceptionEnum.member_updatePhone_error.getIndex());
			returnMsg.setStatus(false);
			returnMsg.setMsg(ExceptionEnum.member_updatePhone_error.getValue());
			logger.error("更新手机号码异常", ex);
			return returnMsg;
		}
		return returnMsg;
	}
	
	/**
	 * 验证码校验
	 * @param phone
	 * @param validateCode
	 * @param sessionId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateCodeByPhone")
	@ResponseBody
	private ReturnMsg<Object> validateCodeByPhone(String phone, String validateCode,String sessionId) throws Exception {
		ValidateCodeInfo codeInfo = Constants.codeMap.get(phone);
		String fromPageValidateCode = validateCode;
		ReturnMsg<Object> returnMsg = new ReturnMsg<Object>();
		if (null == codeInfo || null == codeInfo.getValidateCode() || null == fromPageValidateCode) {
			returnMsg.setCode(ExceptionEnum.validate_code_error.getIndex());
			returnMsg.setStatus(false);
			returnMsg.setMsg(ExceptionEnum.validate_code_error.getValue());
			return returnMsg;
		}
		long time = DateUtil.compareDateStr(codeInfo.getCreateDate(), DateUtil.dateToDateString(new Date()));
		if (time > 120000) {
			// 验证码超时请重新获取
			returnMsg.setCode(ExceptionEnum.validate_code_timeout.getIndex());
			returnMsg.setStatus(false);
			returnMsg.setMsg(ExceptionEnum.validate_code_timeout.getValue());
			return returnMsg;
		}

		if (!codeInfo.getValidateCode().equals(fromPageValidateCode)) {
			// 验证码验证不通过
			returnMsg.setCode(ExceptionEnum.validate_code_error.getIndex());
			returnMsg.setStatus(false);
			returnMsg.setMsg(ExceptionEnum.validate_code_error.getValue());
			return returnMsg;
		}
		returnMsg.setStatus(true);
		return returnMsg;
	}
	
	/**
	 * 
	 * @Title: 查看评价
	 * @author 程健
	 */
	@ResponseBody
	@RequestMapping(value = "/comment")
	public ReturnMsg<Object> comment(String productId, String sessionId,String orderItemId) {
		
		ReturnMsg<Object> returnMsg = new ReturnMsg<Object>();
		Map<String, Object> queryComment =new HashMap<String,Object>();
		Map<String, Object> params =new HashMap<String,Object>();
		MemMember memMember = new MemMember();
		try{
			//拿到会员表信息
			 memMember = memMemberService.getMemMemberBySessionId(sessionId);
			 returnMsg.setData(memMember.getUsername());
		}catch(Exception e){
			logger.info("会员数据异常：" + e);
			returnMsg.setStatus(false);
			returnMsg.setCode(ExceptionEnum.member_not_exist.getIndex());
			return returnMsg;
		}
		String memberId = memMember.getId();
		params.put("memberId", memberId);
		params.put("productId", productId);
		params.put("orderItemId", orderItemId);
		try{
			//拿到评价内容
			queryComment = commentService.queryComment(params);
			returnMsg.setStatus(true);
			String createDate=queryComment.get("createDate").toString();
			queryComment.put("createDate", createDate);
			returnMsg.setData(queryComment);
		}
		catch(Exception e){
			logger.info("评价数据异常：" + e);
			returnMsg.setStatus(false);
			returnMsg.setCode(ExceptionEnum.comment_not_exist.getIndex());
			return returnMsg;
		}
		
		return returnMsg;
	}
	
	/**
	 * 根据sessionId查询用户信息
	 * @return
	 */
	@RequestMapping("/getMemberBySessionId")
	@ResponseBody
	public ReturnMsg<Object> getMemberBySessionId(String sessionId) {
		ReturnMsg<Object> returnMsg = new ReturnMsg<Object>();
		MemMember member = null;
		try {
			if (StringUtils.isEmpty(sessionId)) {
				returnMsg.setCode(ExceptionEnum.param_not_null.getIndex());
				returnMsg.setStatus(false);
				returnMsg.setMsg(ExceptionEnum.param_not_null.getValue());
				return returnMsg;
			}
			member = memMemberService.getMemMemberBySessionId(sessionId);
		} catch (Exception e) {
			logger.info("根据sessionId查询用户信息异常：" + e);
			returnMsg.setStatus(false);
			returnMsg.setCode(ExceptionEnum.system_error.getIndex());
			return returnMsg;
		}
		returnMsg.setStatus(true);
		returnMsg.setData(member);
		return returnMsg;
	}
}
