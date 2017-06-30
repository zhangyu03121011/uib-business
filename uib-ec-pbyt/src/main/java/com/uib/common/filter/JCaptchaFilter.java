package com.uib.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.octo.captcha.service.CaptchaServiceException;
import com.uib.common.service.CaptchaService;
import com.uib.common.utils.SpringContextHolder;

/**
 * 验证码验证过滤器
 * @author shuaiyz
 *
 */
public class JCaptchaFilter implements Filter {

    //默认值定义  
    public static final String DEFAULT_FILTER_PROCESSES_URL = "/j_spring_security_check";  
    public static final String DEFAULT_CAPTCHA_PARAMTER_NAME = "j_captcha";  
      
      
    private String filterProcessesUrl = DEFAULT_FILTER_PROCESSES_URL;  
    private String captchaParamterName = DEFAULT_CAPTCHA_PARAMTER_NAME;  
    public static final String PARAM_FAILURE_URL = "failureUrl";  
    
    private String failureUrl;  
    private String captchaServiceId = "captchaService"; 
    
	private CaptchaService captchaService;
	
      
    /** 
     * Filter回调初始化函数. 
     */  
    public void init(FilterConfig filterConfig) throws ServletException {  
        // TODO Auto-generated method stub  
        initParameters(filterConfig);  
        initCaptchaService(filterConfig);  
  
    }  
  
    public void doFilter(ServletRequest theRequest, ServletResponse theResponse,  
            FilterChain chain) throws IOException, ServletException {  
        HttpServletRequest request = (HttpServletRequest) theRequest;  
        HttpServletResponse response = (HttpServletResponse) theResponse;  
        String servletPath = request.getServletPath();  
        //符合filterProcessesUrl为验证处理请求,其余为生成验证图片请求.  
        if (StringUtils.startsWith(servletPath, filterProcessesUrl)) {  
            boolean validated = validateCaptchaChallenge(request);  
            if (validated) {  
                chain.doFilter(request, response);  
            }else {  
            	String userName = request.getParameter("j_username");
                redirectFailureUrl(request, response);  
            } 
        }
    }  
  
    /** 
     * Filter回调退出函数. 
     */  
    public void destroy() {  
        // TODO Auto-generated method stub  
  
    }  
      
    /** 
     * 初始化web.xml中定义的filter init-param. 
     */  
    protected void initParameters(final FilterConfig fConfig) {  
        if (StringUtils.isBlank(fConfig.getInitParameter(PARAM_FAILURE_URL))) {  
            throw new IllegalArgumentException("CaptchaFilter缺少failureUrl参数");  
        }  
  
        failureUrl = fConfig.getInitParameter(PARAM_FAILURE_URL);    
    }  
      
    /** 
     * 从ApplicatonContext获取CaptchaService实例. 
     */  
    protected void initCaptchaService(final FilterConfig fConfig) {
    	captchaService = (CaptchaService) SpringContextHolder.getApplicationContext().getBean(captchaServiceId);
    }  
//      
    /** 
     * 生成验证码图片. 
     */  
//    protected void genernateCaptchaImage(final HttpServletRequest request, final HttpServletResponse response)  
//            throws IOException {  
//  
//        setDisableCacheHeader(response);  
//        response.setContentType("image/jpeg");  
//  
//        ServletOutputStream out = response.getOutputStream();  
//        try {  
//            String captchaId = request.getSession(true).getId();  
//            BufferedImage challenge = (BufferedImage) captchaService.getChallengeForID(captchaId, request.getLocale());  
//            ImageIO.write(challenge, "jpg", out);  
//            out.flush();  
//        } catch (CaptchaServiceException e) {  
//        } finally {  
//            out.close();  
//        }  
//    }  
      
    /** 
     * 验证验证码. 
     */  
    protected boolean validateCaptchaChallenge(final HttpServletRequest request) {  
        try {  
            String captchaID = request.getParameter("captchaId");
            String challengeResponse = request.getParameter(captchaParamterName);  
            //自动通过值存在时,检验输入值是否等于自动通过值  
//            if (StringUtils.isNotBlank(autoPassValue) && autoPassValue.equals(challengeResponse)) {  
//                return true;  
//            }  
            boolean flag = captchaService.isValid(captchaID, challengeResponse);  
            return flag;  
        } catch (CaptchaServiceException e) {  
        	e.printStackTrace();
            return false;  
        }  
    }   
    
    /** 
     * 跳转到失败页面. 
     *  
     * 可在子类进行扩展, 比如在session中放入SpringSecurity的Exception. 
     */  
    protected void redirectFailureUrl(final HttpServletRequest request, final HttpServletResponse response)  
            throws IOException {  
        response.sendRedirect(request.getContextPath() + failureUrl);  
    }  

}
