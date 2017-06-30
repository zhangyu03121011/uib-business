package com.uib.common.filter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import com.uib.common.utils.StringUtil;

public class MyInvocationSecurityMetadataSource implements
		FilterInvocationSecurityMetadataSource {

	private UrlMatcher urlMatcher = new AntUrlPathMatcher();

	private static Map<String, Collection<ConfigAttribute>> resourceMap = null;
	
	

	public MyInvocationSecurityMetadataSource() {
		loadResourceDefine();
	}

	// 加载资源与角色的关系
	private void loadResourceDefine() {
		resourceMap  =   new  HashMap < String, Collection < ConfigAttribute >> ();
        Collection < ConfigAttribute >  atts  =   new  ArrayList < ConfigAttribute > ();
        ConfigAttribute ca  =   new  SecurityConfig( "member" );
        atts.add(ca);
        resourceMap.put( "/index.jsp" , atts);
		
	}

	// According to a URL, Find out permission configuration of this URL.
	public Collection<ConfigAttribute> getAttributes(Object object)
			throws IllegalArgumentException {
		// guess object is a URL.
		String url = ((FilterInvocation) object).getRequestUrl();
		Iterator<String> ite = resourceMap.keySet().iterator();
		while (ite.hasNext()) {
			String resURL = ite.next();
			if (!StringUtil.isNullOrEmpty(resURL)) {
				if (urlMatcher.pathMatchesUrl(resURL, url)) {
					return resourceMap.get(resURL);
				}
			}
		}
		return null;
	}

	public boolean supports(Class<?> clazz) {
		return true;
	}

	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

}
