 package com.uib.weixin.util;
 
 import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
 
 public class UserSession
 {
   public static void setSession(String key, Object obj)
   {
     HttpServletRequest request = ((ServletRequestAttributes)
       RequestContextHolder.getRequestAttributes()).getRequest();
     request.getSession().setAttribute(key, obj);
   }
 
   public static Object getSession(String key) {
     HttpServletRequest request = ((ServletRequestAttributes)
       RequestContextHolder.getRequestAttributes()).getRequest();
     return request.getSession().getAttribute(key);
   }
   
   public static String getLoginUserName(){
	   return (String) getSession("username");
   }
 }
