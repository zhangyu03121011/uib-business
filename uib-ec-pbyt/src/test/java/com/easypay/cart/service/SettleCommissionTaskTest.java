package com.easypay.cart.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.uib.quartz.SettleCommissionTask;

public class SettleCommissionTaskTest extends BaseTest {

	@Autowired
	private SettleCommissionTask settleCommissionTaskTest;

	@Test
	public void testOk() {
		settleCommissionTaskTest.settleCommission();
	}
}
