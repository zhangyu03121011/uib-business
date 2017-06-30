package com.easypay.cart.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.uib.cart.service.impl.CartPriceService;
import com.uib.order.service.OrderService;

public class CartPriceServiceTest extends BaseTest {
	
	@Autowired
	private CartPriceService cartPriceService;
	
	@Autowired
	private OrderService orderService;
	
	@Test
	public void batchUpdPriceTest(){
		String cartId = "f3aa70b464704a51b2a8a39d9ad619b4";
		String[] productIds = {"49f7030007734efda7f27e180693a070","edc7b8dfea554adab99cea827f335e69"};
		cartPriceService.batchUpdPrice(cartId, productIds);
	}
	
	@Test
	public void test1() throws Exception {
//		orderService.queryRecommendMeberIdByCartItemId("");
		orderService.updateProductSalesByOrderNO("2da2b48c1ebc4cb6a7a2264ae6e776b8");
	}
	
}
