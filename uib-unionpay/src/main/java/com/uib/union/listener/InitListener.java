package com.uib.union.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.log4j.Logger;

import com.unionpay.acp.sdk.SDKConfig;

public class InitListener implements ServletContextListener {

	private static final Logger logger = Logger.getLogger(InitListener.class.getName());

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		SDKConfig.getConfig().loadPropertiesFromSrc();	
		logger.info("已加初使化加载银联配置文件");
		
	}
	
	
	
}
