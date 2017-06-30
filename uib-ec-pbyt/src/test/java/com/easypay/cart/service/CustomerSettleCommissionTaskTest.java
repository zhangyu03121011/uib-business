package com.easypay.cart.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.uib.quartz.CustomerCommissionSettleTask;

public class CustomerSettleCommissionTaskTest extends BaseTest {

	@Autowired
	private CustomerCommissionSettleTask customerCommissionSettleTask;

	@Test
	public void testOk() {
		customerCommissionSettleTask.customerCommission();
	}
}
