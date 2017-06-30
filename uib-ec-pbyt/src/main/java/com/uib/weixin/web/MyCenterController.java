package com.uib.weixin.web;

import java.io.IOException;
import java.io.PrintWriter;
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
import com.uib.ptyt.constants.WechatConstant;
import com.uib.ptyt.service.MemMemberService;
//import com.uib.member.service.MemMemberService;
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
	 * 
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
		if (CheckUtil.checkSignature(signature, timestamp, nonce)) {
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
			logger.info(" 消息的接收与响应信息 ={} ", msgType);
			if (MessageUtil.MESSAGE_TEXT.equals(msgType)) {
				if ("1".equals(content)) {
					logger.info("消息的接收与响应信息======toUserName：" + toUserName);
					logger.info("消息的接收与响应信息======fromUserName：" + fromUserName);
					logger.info("消息的接收与响应信息======content：" + MessageUtil.firstMenu());
					message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.firstMenu());
				} else if ("2".equals(content)) {
					logger.info("消息的接收与响应信息======toUserName：" + toUserName);
					logger.info("消息的接收与响应信息======fromUserName：" + fromUserName);
					logger.info("消息的接收与响应信息======content：" + MessageUtil.secondMenu());
					message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.secondMenu());
				} else if ("?".equals(content) || "？".equals(content)) {
					message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
				}
			} else if (MessageUtil.MESSAGE_EVNET.equals(msgType)) {
				String eventType = map.get("Event");
				logger.info(" eventType={}", eventType);
				// 关注
				if (MessageUtil.MESSAGE_SUBSCRIBE.equals(eventType)) {
					logger.info("关注操作======");
					logger.info("消息的接收与响应信息======toUserName：" + toUserName);
					logger.info("消息的接收与响应信息======fromUserName：" + fromUserName);
					logger.info("消息的接收与响应信息======content：" + MessageUtil.secondMenu());
					message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
					UserSession.setSession(WxConstant.wx_user_name, fromUserName);
					logger.info("关注操作结束======" + UserSession.getSession(WxConstant.wx_user_name));
					// 插入当前用户信息
					// myCenterService.weixinRegister(fromUserName);
				} else if (MessageUtil.MESSAGE_UNSUBSCRIBE.equals(eventType)) { // 取消关注
					logger.info("取消关注操作======");
					logger.info("消息的接收与响应信息======toUserName：" + toUserName);
					logger.info("消息的接收与响应信息======fromUserName：" + fromUserName);
					logger.info("消息的接收与响应信息======content：" + MessageUtil.secondMenu());
					// 插入当前用户信息
					myCenterService.unSubscribe(fromUserName);
				} else if (MessageUtil.MESSAGE_VIEW.equals(eventType)) {
					logger.info("关注操作======");
					logger.info("消息的接收与响应信息======toUserName：" + toUserName);
					logger.info("消息的接收与响应信息======fromUserName：" + fromUserName);
					logger.info("消息的接收与响应信息======content：" + MessageUtil.secondMenu());
					message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
					UserSession.setSession(WxConstant.wx_user_name, fromUserName);
					logger.info("关注操作结束======" + UserSession.getSession(WxConstant.wx_user_name));
				}
			}
			logger.info("消息的接收与响应结束======" + message);
			out.print(message);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
	}

	/**
	 * 根据用户名查询用户信息
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 */
	/*
	 * @RequestMapping(value = "/getMemMemberByUsername", method =
	 * RequestMethod.POST) public Map<String, Object>
	 * getMemMemberByUsername(HttpServletRequest req, HttpServletResponse resp)
	 * throws ServletException { HashMap<String, Object> returnMap = new
	 * HashMap<String, Object>(); MemMember member =
	 * memMemberService.getMemMemberByUsername("kevin_001");
	 * returnMap.put("result", member); return returnMap; }
	 */

	/**
	 * 网页授权操作
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/get/oauth2", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView getTicketState() {
		return new ModelAndView("redirect:/page/pbyt/index.html");
	}

	@RequestMapping(value = "/callback", produces = "text/plain;charset=UTF-8", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView oauth2authorize(@RequestParam(value = "code") String authcode, @RequestParam(value = "state") String state,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("获取网页授权code回调开始======" + authcode);
		logger.info("获取网页授权state回调======" + state);
		String gotoPage = "/page/pbyt/index.html";
		try {
			// String openId = WeixinUtil.queryWxuseridCallTX(authcode);
			// logger.info("获取网页授权openid回调"+openId);
			// Object userName =
			// (String)UserSession.getSession(WxConstant.wx_user_name);
			// MemMember member =
			// memMemberService.getMemMemberByUsername(openId);
			// if (StringUtils.isNotEmpty(openId) && !openId.equals(userName)) {
			// UserSession.setSession(WxConstant.wx_user_name, openId);
			// }
			String openId = WeixinUtil.queryWxuseridCallTX(authcode);
			logger.info("获取网页授权openid回调=" + openId);
			if (StringUtils.isNotEmpty(openId)) {
				UserSession.setSession(WechatConstant.OPEN_ID, openId);
				Map<String, Object> map = WeixinUtil.getWeChatInfo(openId);
				// 根据openId查询出当前用户
				Map<String,Object> member = memMemberService.getuserIdByopenId(openId);
				if (member==null) {
					// 微信用户不存在，直接生产一个用户
					memMemberService.createUser(openId);
					// 更新用户头像和性别
					memMemberService.updateUserHead(map);
					try{
						//更新用户昵称        ----微信昵称有特殊字符，必须要单独处理
						memMemberService.updateUserName(map);
					}catch(Exception e){
						logger.info("微信头像特殊字符处理开始======");
						int code = memMemberService.getCode();
						String strHao = liuShuiHao(code);  //流水号不齐六位补齐六位
						String userName = "PBYT"+strHao;
						logger.info("流水号strHao======" + strHao);
						logger.info("系统自动分配userName======" + userName);
						memMemberService.updateUserName2(openId,userName);
						memMemberService.updateCode(code+1);
						logger.info("微信头像特殊字符处理结束======");
					}
					
				} else {
					String userId = member.get("id").toString();
					String rankId = member.get("rankId").toString();
					logger.info("根据openId查询出当前userId=" + userId);
					logger.info("根据openId查询出当前rankId=" + rankId);
					UserSession.setSession(WechatConstant.USER_ID, userId);
					//用户等级
					UserSession.setSession(WechatConstant.RANK_ID, rankId);
					String merchartId = memMemberService.getmerchartIdByUserId(userId);
					logger.info("根据userId查询出当前merchartId" + merchartId);
					if (null != merchartId) {
						// 判断是否是商户，是商户把merchartId保存
						UserSession.setSession(WechatConstant.MERCHANT_ID, merchartId);
						logger.info("商户号merchartId===" + UserSession.getSession(WechatConstant.MERCHANT_ID));
						
					}
					// 更新用户头像和性别
					memMemberService.updateUserHead(map);
					
					String userName = member.get("userName").toString();
					UserSession.setSession(WechatConstant.USER_NAME, userName);
					/*
					if(null !=userName)
					{
						// 更新用户头像和性别
						memMemberService.updateUserHead(map);
						//更新用户昵称        ----微信昵称有特殊字符，必须要单独处理
						String nickname = map.get("nickname").toString();
						logger.info("微信昵称===" +nickname );
						logger.info("数据库用户名昵称===" +userName );
						if(!userName.equals(nickname)){
							memMemberService.updateUserName(map);
						}
					}*/
				}
			}

			if ("1".equals(state)) {
				// 商城首页
				gotoPage = "/page/pbyt/index.html";
			} else if ("2".equals(state)) {
				// 购物车
				gotoPage = "/page/pbyt/index.html";
			} else if ("3".equals(state)) {
				// 账户管理
				gotoPage = "/page/pbyt/index.html";
			}
		} catch (Exception e) {
			logger.error("获取网页授权code回调异常"+e);
			logger.info("获取网页授权code回调异常======" + e);
		}

		return new ModelAndView("redirect:" + gotoPage);
	}
	/*
	 * 流水号不齐六位补齐六位
	 */
	private String liuShuiHao(int code){
		String strHao =String.valueOf(code);
		while (strHao.length() < 6)
		{
			strHao = "0" + strHao;
		}
		return strHao;
	}
	
	/**
	 * 从滴滴快保商城跳转回调
	 * @param authcode
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/ddkb/callback",method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView oauth2authorize(@RequestParam(value = "openId") String openId,HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("获取网页授权openId回调开始======" + openId);
		String gotoPage = "/page/pbyt/index.html";
		try {
			if (StringUtils.isNotEmpty(openId)) {
				UserSession.setSession(WechatConstant.OPEN_ID, openId);
				// 根据openId查询出当前用户
				Map<String,Object> member = memMemberService.getuserIdByopenId(openId);
				if (member==null) {
					// 微信用户不存在，直接生产一个用户
					memMemberService.createUser(openId);
				} else {
					String userId = member.get("id").toString();
					String rankId = member.get("rankId").toString();
					logger.info("根据openId查询出当前userId=" + userId);
					logger.info("根据openId查询出当前rankId=" + rankId);
					UserSession.setSession(WechatConstant.USER_ID, userId);
					//用户等级
					UserSession.setSession(WechatConstant.RANK_ID, rankId);
					String merchartId = memMemberService.getmerchartIdByUserId(userId);
					logger.info("根据userId查询出当前merchartId" + merchartId);
					if (null != merchartId) {
						// 判断是否是商户，是商户把merchartId保存
						UserSession.setSession(WechatConstant.MERCHANT_ID, merchartId);
						logger.info("商户号merchartId===" + UserSession.getSession(WechatConstant.MERCHANT_ID));
						
					}
					String userName = (String)member.get("userName");
					UserSession.setSession(WechatConstant.USER_NAME, userName);
				}
			}
			gotoPage = "/page/pbyt/index.html";
			return new ModelAndView("redirect:" + gotoPage);
		} catch (Exception e) {
			logger.error("ddkb商城跳转回调异常"+e);
		}
		return new ModelAndView();
	}
	
}
