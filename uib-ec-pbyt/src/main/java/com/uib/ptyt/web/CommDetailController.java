package com.uib.ptyt.web;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.uib.ptyt.service.CommDetailService;



@RequestMapping("/commDetail")
@Controller
public class CommDetailController {

	private Logger logger = LoggerFactory.getLogger(CommDetailController.class);
	
	@Autowired
	private CommDetailService commDetailService;
	
	
}
