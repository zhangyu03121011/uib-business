package com.uib.quartz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.uib.common.utils.LuceneUtil;
import com.uib.product.dao.ProductServiceDao;
import com.uib.product.entity.Product;
import com.uib.product.service.ProductService;

//@Component
public class ProductLoadTask {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProductServiceDao productServiceDao;
	
	//@Autowired
	//private LuceneUtil luceneUtil;
	
    protected void execute()  {
    	/*try{
    		List<String> idList =	luceneUtil.searchAll("id");
        	List<Product> productList =	productServiceDao.getProductByNotInIds(idList);
        	if (null != productList && productList.size() > 0){
        		luceneUtil.create(productList);
        	}
    	} catch (Exception ex){
    		ex.printStackTrace();
    	}*/
    	
     /*   long ms = System.currentTimeMillis();  
        System.out.println("\t\t" + new Date(ms));  
        System.out.println("(" + counter++ + ")");  */
    } 
}
