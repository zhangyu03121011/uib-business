package com.uib.product.web;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 保险商品详情
 * @author kevin
 *
 */
@Controller
@RequestMapping("/insurance")
public class InsuranceProductController {
	
	@Value("/safety-cpjs")
	private String safetyCpjsView;
	
	@Value("/safety-xfzrx")
	private String safetyXfzrxView;
	
	@Value("/safety-rsyw")
	private String rsywView;
	
	
	
	/**
	 * 国内旅游险
	 * @return
	 */
	@RequestMapping("/rsyw")
	private String safetyRsywView(){
		return rsywView;
	}
	
	/**
	 * 学生平安险
	 * @return
	 */
	@RequestMapping("/cpjs")
	private String safetyCpjsView(){
		return safetyCpjsView;
	}
	
	
	
	
	/**
	 * 校方责任险
	 * @return
	 */
	@RequestMapping("/xfzrx")
	private String safetyXfzrxView(){
		return safetyXfzrxView;
	}
}
