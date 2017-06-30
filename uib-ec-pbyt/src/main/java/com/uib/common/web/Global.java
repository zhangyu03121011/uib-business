package com.uib.common.web;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.stereotype.Component;

import com.easypay.common.utils.StringUtil;
import com.google.common.collect.Maps;
import com.uib.common.utils.PropertiesLoader;


@SuppressWarnings("serial")
@Component
public class Global extends HttpServlet{
	/**
	 * 当前对象实例
	 */
	private static Global global = new Global();
	
	/**
	 * 保存全局属性值
	 */
	private static Map<String, String> map = Maps.newHashMap();
	// action配置文件路径  
	public static final String ACTIONPATH = "/system.properties";  
	  
	private static PropertiesLoader loader = new PropertiesLoader("system.properties");


	@Override
	public void init() throws ServletException {
		
		String baseFilePath =getProperties().getProperty("upload.image.path");
		this.getServletContext().setAttribute("baseFilePath",baseFilePath);  
	}
	
	
	private Properties getProperties(){
		// 属性文件   
	    Properties prop = new Properties();  
		try{
			// 获取servlet上下文的绝对路径，如：C:\Program Files\Apache\Tomcat 6.0\webapps\fee\  
			//String path = getServletContext().getRealPath("\\");
			
			// 获取当前类加载的根目录，如：/C:/Program Files/Apache/Tomcat 6.0/webapps/fee/WEB-INF/classes/  
			String path = Global.class.getClassLoader().getResource("").toURI().getPath();    

			
			// 把文件读入文件输入流，存入内存中     
			FileInputStream fis = new FileInputStream(new File(path + ACTIONPATH));     
			//加载文件流的属性     
			prop.load(fis); 
		} catch (Exception ex){
			ex.printStackTrace();
		}
		return prop;
	}
	/**
	 * 获取配置
	 * @see ${fns:getConfig('adminPath')}
	 */
	public static String getConfig(String key) {
		String value = map.get(key);
		if (value == null){
			value = loader.getProperty(key);
			map.put(key, value != null ? value : StringUtil.EMPTY);
		}
		return value;
	}
	
	/**
	 * 获取管理端根路径
	 */
	public static String getAdminPath() {
		return getConfig("adminPath");
	}
	
	/**
	 * 获取前端根路径
	 */
	public static String getFrontPath() {
		return getConfig("frontPath");
	}
	
	/**
	 * 获取URL后缀
	 */
	public static String getUrlSuffix() {
		return getConfig("urlSuffix");
	}


	
    /**
     * 获取工程路径
     * @return
     */
    public static String getProjectPath(){
    	// 如果配置了工程路径，则直接返回，否则自动获取。
		String projectPath = Global.getConfig("projectPath");
		if (StringUtil.isNotBlank(projectPath)){
			return projectPath;
		}
		try {
			File file = new DefaultResourceLoader().getResource("").getFile();
			if (file != null){
				while(true){
					File f = new File(file.getPath() + File.separator + "src" + File.separator + "main");
					if (f == null || f.exists()){
						break;
					}
					if (file.getParentFile() != null){
						file = file.getParentFile();
					}else{
						break;
					}
				}
				projectPath = file.toString();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return projectPath;
    }
public static void main(String[] args) {
	System.out.println(getConfig("upload.image.path"));
}	
}
