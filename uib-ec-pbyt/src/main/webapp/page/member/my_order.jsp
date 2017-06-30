<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>e-life.me</title>
<link href="${pageContext.request.contextPath  }/static/css/common.css"
	rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath  }/static/css/user.css"
	rel="stylesheet" type="text/css" />
<script type="text/javascript"
	src="${pageContext.request.contextPath  }/static/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath  }/static/js/hover.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath  }/static/js/photo-info.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath  }/static/js/select.js"></script>
	
<script language="javascript" type="text/javascript"> 
function dyniframesize(down) { 
	var pTar = null; 
	if (document.getElementById){ 
		pTar = document.getElementById(down); 
	} 
	else{ 
		eval('pTar = ' + down + ';'); 
	} 
	if (pTar && !window.opera){ 
		//begin resizing iframe 
			pTar.style.display="block"; 
		if (pTar.contentDocument && pTar.contentDocument.body.offsetHeight){ 
		//ns6 syntax 
			pTar.height = pTar.contentDocument.body.offsetHeight +20; 
			pTar.width = pTar.contentDocument.body.scrollWidth+20; 
		} 
		else if (pTar.Document && pTar.Document.body.scrollHeight){ 
	//ie5+ syntax 
		pTar.height = pTar.Document.body.scrollHeight; 
		pTar.width = pTar.Document.body.scrollWidth; 
		} 
	} 
	
} 



</script>
</head>

<body>

	<%@include file="include/top.jsp"%>
	<!-- 我的elife内容区 -->
	<div class="myelife_content">
		<div class="myelife_title font14">
			首页 > <a href="my_elife.html">我的e-life</a> > 我的订单
		</div>
		<!-- 左边菜单 -->
		<div class="myelife_left">
			<dl>
				<dt class="listone">
					<s></s><span>交易管理</span>
				</dt>
				<dd>
					<a class="m_hover" href="${pageContext.request.contextPath  }/f/member/myProduct?orderStatus=4" target="myFrame">我的订单</a>
				</dd>
				<dd>
					<a href="javascript:alert('敬请期待')" target="myFrame">我的保单</a>
				</dd>
				<dd>
					<a href="${pageContext.request.contextPath  }/f/member/myCoupon" target="myFrame">我的优惠券</a>
				</dd>
				<dd>
					<a href="${pageContext.request.contextPath  }/f/member/myIntegral" target="myFrame">我的积分</a>
				</dd>
				<dd>
					<a href="${pageContext.request.contextPath  }/f/member/myBalance" target="myFrame">我的账户余额</a>
				</dd>
				<dd>
					<a href="${pageContext.request.contextPath  }/f/member/myAdvisory" target="myFrame">商品咨询</a>
				</dd>
				<dd>
					<a href="${pageContext.request.contextPath  }/f/member/myArrivalNotice" target="myFrame">到货通知</a>
				</dd>
				<dt class="listtwo mt20">
					<s></s><span>服务中心</span>
				</dt>
				<!-- <dd>
					<a href="returned.html">返修/退换货</a>
				</dd> -->
				<dd>
					<a href="javascript:alert('敬请期待')">服务咨询/投诉</a>
				</dd>
				<!-- <dd>
					<a href="cancel_order.html">取消订单记录</a>
				</dd> -->
				<dt class="listtre mt20">
					<s></s><span>账户管理</span>
				</dt>
				<dd>
					 <a href="${pageContext.request.contextPath}/f/member/myInfo" target="myFrame">个人信息</a>
				</dd>
				<dd>
					<a href="javascript:alert('敬请期待')">安全设置</a>
				</dd>
				<dd>
					<a href="${pageContext.request.contextPath}/f/member/toUpdatePassword"	target="myFrame">修改密码</a>
				</dd> 
				<dd>
					<a href="${pageContext.request.contextPath}/f/member/toIdentityApprove"  target="myFrame">身份认证</a>
				</dd>
				<dd>
						<a href="javascript:alert('敬请期待')">短消息</a>
				</dd>
				</dd> 
				
				<dd>
					<a  href="${pageContext.request.contextPath  }/f/member/myAddress" target="myFrame">地址管理</a>
				</dd>
			</dl>
		</div>
		<div class="my_order" >
		<!-- 订单信息 -->
		<iframe src="${pageContext.request.contextPath  }/f/member/myProduct?orderStatus=4" frameborder="0" marginheight="0" marginwidth="0" frameborder="0" scrolling="auto"
		   width="100%" height="600"  id="my_order" name="myFrame"  ></iframe>
		  
</div>

	<!-- 五楼 -->
	<div class="footerWrap">
		<div class="promise_nav">
			<ul>
				<li class="promise_one"><s></s>品质保障<br /> <span>品质护航&nbsp;&nbsp;购物无忧</span></li>
				<li class="line-nav"></li>
				<li class="promise_two"><s></s>品质保障<br /> <span>正品行货&nbsp;&nbsp;精致服务</span></li>
				<li class="line-nav"></li>
				<li class="promise_tre"><s></s>专业配送<br /> <span>国际物流配送&nbsp;&nbsp;急速到货</span></li>
				<li class="line-nav"></li>
				<li class="promise_four"><s></s>帮助中心<br /> <span>您的购物指南</span></li>
			</ul>
		</div>
		<div class="sub_nav">
			<div class="sub_nav_left">
				<div class="sub_nav1">
					<s></s>
					<h2>购物指南</h2>
					<ul>
						<li><a href="">购物流程</a></li>
						<li><a href="">会员介绍</a></li>
						<li><a href="">常见问题</a></li>
						<li><a href="">联系客服</a></li>
					</ul>
				</div>
				<div class="sub_nav2">

					<s></s>
					<h2>配送方法</h2>
					<ul>
						<li><a href="">海外配送</a></li>
						<li><a href="">包邮配送</a></li>
						<li><a href="">配送服务查询</a></li>
						<li><a href="">配送费收取标准</a></li>
					</ul>
				</div>
				<div class="sub_nav3">
					<s></s>
					<h2>支付方式</h2>
					<ul>
						<li><a href="">货到付款</a></li>
						<li><a href="">在线支付</a></li>
					</ul>
				</div>
				<div class="sub_nav4">
					<s></s>
					<h2>售后服务</h2>
					<ul>
						<li><a href="">售后政策</a></li>
						<li><a href="">退款说明</a></li>
						<li><a href="">取消订单</a></li>
						<li><a href="">返修/退换货</a></li>
					</ul>
				</div>
			</div>
			<div class="sub_nav_right">
				<a href=""><img
					src="${pageContext.request.contextPath  }/static/images/buy-security.jpg" /></a>
			</div>
		</div>
		<div class="footer">
			<div class="footer_nav">
				<a href="">关于我们</a>| <a href="">联系我们</a>| <a href="">诚征英才</a>| <a
					href="">欢迎合作</a>| <a href="">知识产权</a>| <a href="">著作权与商标声明</a>| <a
					href="">法律声明</a>| <a href="">服务条款</a>| <a href="">隐私声明</a>| <a
					href="">网站地图</a>
			</div>
		</div>
	</div>
	</div>
</body>
</html>
