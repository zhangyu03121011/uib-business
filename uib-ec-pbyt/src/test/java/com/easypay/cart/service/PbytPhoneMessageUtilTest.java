package com.easypay.cart.service;

import org.junit.Test;

import com.uib.ptyt.common.PbytPhoneMessageUtil;
import com.uib.weixin.util.MD5Util;

public class PbytPhoneMessageUtilTest extends BaseTest {

	@Test
	public void testOk() throws Exception {
		String sign = MD5Util.MD5Encode("123456", "UTF-8").toUpperCase();
		System.out.println(sign);

		String phone = "13798568211";
		String messCode = "1234";
		String type = "1";
		PbytPhoneMessageUtil.sendMessCode(phone, messCode, type);
	}
}
