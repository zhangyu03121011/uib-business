package com.uib.weixin.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.uib.base.BaseController;
import com.uib.common.utils.StringUtils;
import com.uib.member.entity.MemMember;
import com.uib.member.service.MemMemberService;
import com.uib.weixin.constant.WxConstant;
import com.uib.weixin.service.MyCenterService;
import com.uib.weixin.util.CheckUtil;
import com.uib.weixin.util.MessageUtil;
import com.uib.weixin.util.UserSession;
import com.uib.weixin.util.WeixinUtil;

/**
 * 手机
 * 
 * @author kevin
 * 
 */
@Controller
@RequestMapping("/wechat")
public class MyCenterController extends BaseController {
	private Logger logger = LoggerFactory.getLogger(MyCenterController.class);

	@Autowired
	private MyCenterService myCenterService;
	
	@Autowired
	private MemMemberService memMemberService;
	
	/**
	 * 接入验证
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String signature = req.getParameter("signature");
		String timestamp = req.getParameter("timestamp");
		String nonce = req.getParameter("nonce");
		String echostr = req.getParameter("echostr");
		
		PrintWriter out = resp.getWriter();
		if(CheckUtil.checkSignature(signature, timestamp, nonce)){
			out.print(echostr);
		}
	}
	
	
	/**
	 * 消息的接收与响应
	 */
	@RequestMapping(value = "/index", method = RequestMethod.POST)
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.info("消息的接收与响应开始");
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		try {
			Map<String, String> map = MessageUtil.xmlToMap(req);
			String fromUserName = map.get("FromUserName");
			String toUserName = map.get("ToUserName");
			String msgType = map.get("MsgType");
			String content = map.get("Content");
			String message = null;
			if(MessageUtil.MESSAGE_TEXT.equals(msgType)){
				if("1".equals(content)){
					logger.info("消息的接收与响应信息======toUserName："+toUserName);
					logger.info("消息的接收与响应信息======fromUserName："+fromUserName);
					logger.info("消息的接收与响应信息======content："+MessageUtil.firstMenu());
					message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.firstMenu());
				}else if("2".equals(content)){
					logger.info("消息的接收与响应信息======toUserName："+toUserName);
					logger.info("消息的接收与响应信息======fromUserName："+fromUserName);
					logger.info("消息的接收与响应信息======content："+MessageUtil.secondMenu());
					message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.secondMenu());
				}else if("?".equals(content) || "？".equals(content)){
					message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
				}
			}else if(MessageUtil.MESSAGE_EVNET.equals(msgType)){
				String eventType = map.get("Event");
				//关注
				if(MessageUtil.MESSAGE_SUBSCRIBE.equals(eventType)){
					logger.info("关注操作======");
					logger.info("消息的接收与响应信息======toUserName："+toUserName);
					logger.info("消息的接收与响应信息======fromUserName："+fromUserName);
					logger.info("消息的接收与响应信息======content："+MessageUtil.secondMenu());
					message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
//					UserSession.setSession(WxConstant.wx_user_name, fromUserName);
					logger.info("关注操作结束======"+UserSession.getSession(WxConstant.wx_user_name));
					//插入当前用户信息
//					myCenterService.weixinRegister(fromUserName);
				} else if(MessageUtil.MESSAGE_UNSUBSCRIBE.equals(eventType)){ //取消关注
					logger.info("取消关注操作======");
					logger.info("消息的接收与响应信息======toUserName："+toUserName);
					logger.info("消息的接收与响应信息======fromUserName："+fromUserName);
					logger.info("消息的接收与响应信息======content："+MessageUtil.secondMenu());
					//插入当前用户信息
					myCenterService.unSubscribe(fromUserName);
				}
			}
			logger.info("消息的接收与响应结束======"+message);
			out.print(message);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			out.close();
		}
	}
	
	/**
	 * 根据用户名查询用户信息
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 */
	@RequestMapping(value = "/getMemMemberByUsername", method = RequestMethod.POST)
	public Map<String, Object> getMemMemberByUsername(HttpServletRequest req, HttpServletResponse resp) throws ServletException{
		HashMap<String, Object> returnMap = new HashMap<String, Object>();
		MemMember member = memMemberService.getMemMemberByUsername("kevin_001");
		returnMap.put("result", member);
		return returnMap;
	}
	
	/**
	 * 网页授权操作
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/get/oauth2", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView getTicketState(){
		return new ModelAndView("redirect:/page/weixin/order.html?openId=1");
	}
	
	@RequestMapping(value = "/callback",  produces="text/plain;charset=UTF-8" ,method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView oauth2authorize(@RequestParam(value = "code") String authcode,@RequestParam(value = "state") String state,HttpServletRequest request,HttpServletResponse response) throws Exception {
		logger.info("获取网页授权code回调开始======"+authcode);
		logger.info("获取网页授权state回调======"+state);
		String gotoPage = "/page/weixin/home.html";
		try {
//			String openId = WeixinUtil.queryWxuseridCallTX(authcode);
//			logger.info("获取网页授权openid回调"+openId);
//			Object userName = (String)UserSession.getSession(WxConstant.wx_user_name);
//			MemMember member = memMemberService.getMemMemberByUsername(openId);
//			if (StringUtils.isNotEmpty(openId) && !openId.equals(userName)) {
//				UserSession.setSession(WxConstant.wx_user_name, openId);
//			}
			String openId = WeixinUtil.queryWxuseridCallTX(authcode);
			logger.info("获取网页授权openid回调"+openId);
			if (StringUtils.isNotEmpty(openId)){
				UserSession.setSession("wxOpenId", openId);
			}
			
			if("1".equals(state)) {
				//商城首页
				gotoPage="/page/weixin/home.html";
			} else if("2".equals(state)) {
				//购物车
				gotoPage="/page/weixin/cart.html";
			} else if("3".equals(state)) {
				//账户管理
				gotoPage="/page/weixin/userCenter.html";
			} 
		} catch (Exception e) {
			logger.info("获取网页授权code回调异常======"+e);
		}
		
		return new ModelAndView("redirect:"+gotoPage);
	}
}
