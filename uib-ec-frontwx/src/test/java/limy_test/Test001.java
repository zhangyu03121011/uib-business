package limy_test;

import java.util.HashMap;

import com.uib.common.web.HttpCall;
import com.uib.common.web.HttpCallResult;

public class Test001 {
	public static HashMap<String,String> map = new HashMap<String,String> ();
	private static void myCouponTest(){
		map.put("userName", "kevin_001");
		map.put("states", "2");
		map.put("sessionId", "B9DF4C15E08289DCD73258CD367C37FD");
		HttpCallResult aa=	HttpCall.post("http://localhost:9988/ec-frontweb/f/mobile/member/coupon/myCoupon", map);
		System.out.println(aa.getContent());
	}
	private static void ifUsedCouponTest(){
		map.put("userName", "kevin_001");
		map.put("selectFlag", "2");
		map.put("orderAmount", "100");
		map.put("sessionId", "B9DF4C15E08289DCD73258CD367C37FD");
		HttpCallResult aa=	HttpCall.post("http://localhost:9988/ec-frontweb/f/mobile/member/coupon/ifUsedCoupon", map);
		System.out.println(aa.getContent());
	}
	private static void listByParentIdTest(){
		map.put("parentId", "1");
		map.put("sessionId", "B9DF4C15E08289DCD73258CD367C37FD");
		HttpCallResult aa=	HttpCall.post("http://localhost:9988/ec-frontweb/f/mobile/member/address/listByParentId", map);
		System.out.println(aa.getContent());
	}
	private static void listAddressTest(){
		map.put("userName", "kevin_001");
		map.put("sessionId", "B9DF4C15E08289DCD73258CD367C37FD");
		HttpCallResult aa=	HttpCall.post("http://localhost:9988/ec-frontweb/f/mobile/member/address/listAddress", map);
		System.out.println(aa.getContent());
	}
	private static void listDefaultAddressTest(){
		map.put("userName", "kevin_001");
		map.put("sessionId", "B9DF4C15E08289DCD73258CD367C37FD");
		HttpCallResult aa=	HttpCall.post("http://localhost:9988/ec-frontweb/f/mobile/member/address/listDefaultAddress", map);
		System.out.println(aa.getContent());
	}
	private static void updateTest(){
		map.put("id", "298a5e6a-f2e4-4168-8a64-f570227153f9");
		map.put("userName", "kevin_001");
		map.put("address", "科技园中区科技中三路8888888888");
		map.put("areaName", "广东省深圳市南山区");
		map.put("consignee", "王二");
		map.put("isDefault", "false");
		map.put("phone", "13973642021");
		map.put("area", "2222,55555,4444");
		map.put("sessionId", "B9DF4C15E08289DCD73258CD367C37FD");
		HttpCallResult aa=	HttpCall.post("http://localhost:9988/ec-frontweb/f/mobile/member/address/update", map);
		System.out.println(aa.getContent());
	}	
	private static void updateDefaultAddressTest(){
		map.put("id", "298a5e6a-f2e4-4168-8a64-f570227153f9");
		map.put("sessionId", "B9DF4C15E08289DCD73258CD367C37FD");
		HttpCallResult aa=	HttpCall.post("http://localhost:9988/ec-frontweb/f/mobile/member/address/updateDefaultAddress", map);
		System.out.println(aa.getContent());
	}
	private static void saveTest(){
		map.put("id", "298a5e6a-f2e4-4168-8a64-f570227153f9");
		map.put("userName", "kevin_001");
		map.put("address", "科技园中区科技中三路8888888888");
		map.put("areaName", "广东省深圳市南山区");
		map.put("consignee", "王二");
		map.put("isDefault", "false");
		map.put("phone", "13973642021");
		map.put("area", "2222,55555,4444");
		map.put("sessionId", "B9DF4C15E08289DCD73258CD367C37FD");
		HttpCallResult aa=	HttpCall.post("http://localhost:9988/ec-frontweb/f/mobile/member/address/save", map);
		System.out.println(aa.getContent());
	}
	private static void deleteTest(){
		map.put("id", "298a5e6a-f2e4-4168-8a64-f570227153f9");
		map.put("sessionId", "B9DF4C15E08289DCD73258CD367C37FD");
		HttpCallResult aa=	HttpCall.post("http://localhost:9988/ec-frontweb/f/mobile/member/address/delete", map);
		System.out.println(aa.getContent());
	}
	private static void queryOrderTest(){
		map.put("userName", "kevin001");
		map.put("orderNo", "2015102110114445440416");
//		map.put("userName", "sshine");
//		map.put("orderNo", "2015110316391013403493");
		map.put("sessionId", "B9DF4C15E08289DCD73258CD367C37FD");
		HttpCallResult aa=	HttpCall.post("http://localhost:9988/ec-frontweb/f/mobile/member/order/queryOrder", map);
		System.out.println(aa.getContent());
	}
	private static void paymentCuesTest(){
		map.put("userName", "xiaxia");
		map.put("orderNo", "2015102016101987612824");
		map.put("sessionId", "B9DF4C15E08289DCD73258CD367C37FD");
		HttpCallResult aa=	HttpCall.post("http://localhost:9988/ec-frontweb/f/mobile/member/order/paymentCues", map);
		System.out.println(aa.getContent());
	}
	private static void listCmsHelpTest(){
		map.put("cmsCategoryNo", "bangzhu");
		map.put("sessionId", "B9DF4C15E08289DCD73258CD367C37FD");
		HttpCallResult aa=	HttpCall.post("http://localhost:9988/ec-frontweb/f/mobile/member/CmsCategory/listCmsHelp", map);
		System.out.println(aa.getContent());
	}
	private static void listCmsRegisterTest(){
		map.put("cmsCategoryNo", "zhucexieyi");
		map.put("sessionId", "B9DF4C15E08289DCD73258CD367C37FD");
		HttpCallResult aa=	HttpCall.post("http://localhost:9988/ec-frontweb/f/mobile/member/CmsCategory/listCmsRegister", map);
		System.out.println(aa.getContent());
	}
	public static void main(String[] args) {
		//查询订单详情
		Test001.queryOrderTest();
//		//查询订单详情
//		Test001.paymentCuesTest();
//		//删除收货地址
//		Test001.deleteTest();
//		//查询是否可用优惠券
//		Test001.ifUsedCouponTest();
//		//查询所有收货地址
//		Test001.listAddressTest();
//		//查询地区
//		Test001.listByParentIdTest();
//		//查询默认地址
//		Test001.listDefaultAddressTest();
//		//查询所有优惠券
//		Test001.myCouponTest();
//		//保存收货地址
//		Test001.saveTest();
//		//修改默认收货地址
//		Test001.updateDefaultAddressTest();
//		//修改收货地址
//		Test001.updateTest();
//		//查询帮助内容
//		Test001.listCmsHelpTest();
		//查询注册协议内容
//		Test001.listCmsRegisterTest();
	}
}
