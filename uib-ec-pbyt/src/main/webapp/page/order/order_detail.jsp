<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<title>初云汇商城</title>
	<link href="${pageContext.request.contextPath  }/static/css/common.css" rel="stylesheet" type="text/css" />
    <link href="${pageContext.request.contextPath  }/static/css/user.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${pageContext.request.contextPath  }/static/js/jquery-1.8.2.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath  }/static/js/hover.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath  }/static/js/photo-info.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath  }/static/js/select.js"></script>
<script type="text/javascript">
(function($){
	$.fn.hoverForIE6=function(option){
		var s=$.extend({current:"hover",delay:10},option||{});
		$.each(this,function(){
			var timer1=null,timer2=null,flag=false;
			$(this).bind("mouseover",function(){
				if (flag){
					clearTimeout(timer2);
				}else{
					var _this=$(this);
					timer1=setTimeout(function(){
						_this.addClass(s.current);
						flag=true;
					},s.delay);
				}
			}).bind("mouseout",function(){
				if (flag){
					var _this=$(this);timer2=setTimeout(function(){
						_this.removeClass(s.current);
						flag=false;
					},s.delay);
				}else{
					clearTimeout(timer1);
				}
			})
		})
	}
})(jQuery);
</script>
<script type="text/javascript">
//===========================点击展开关闭效果====================================
function openShutManager(oSourceObj,oTargetObj,shutAble,oOpenTip,oShutTip){
var sourceObj = typeof oSourceObj == "string" ? document.getElementById(oSourceObj) : oSourceObj;
var targetObj = typeof oTargetObj == "string" ? document.getElementById(oTargetObj) : oTargetObj;
var openTip = oOpenTip || "";
var shutTip = oShutTip || "";
if(targetObj.style.display!="none"){
   if(shutAble) return;
   targetObj.style.display="none";
   if(openTip && shutTip){
    sourceObj.innerHTML = shutTip; 
   }
} else {
   targetObj.style.display="block";
   if(openTip && shutTip){
    sourceObj.innerHTML = openTip; 
   }
}
}
</script>
</head>

<body>
<!-- 顶部栏目开始 -->
<%@include file="../member/include/top.jsp"%><!-- 菜单栏结束 -->

<!-- 我的elife内容区 -->
<div class="myelife_content">
<div class="myelife_title font14">首页 ><a href="my_elife.html"> 我的e-life</a> > <a href="${pageContext.request.contextPath  }/f/member/myProduct?orderStatus=4">我的订单</a> > 订单详情</div>

<div class="my_order" id="w978">
<!-- 我的余额 -->
<div class="my_balance">
<div class="balance_title font14"><font><a class="gray_btn return_btn" href=""${pageContext.request.contextPath  }/f/member/myProduct?orderStatus=4"">返回</a></font>订单概况</div>
<div class="complaints_con">
<div class="complaints_view">
<span>订单编号：${order.orderNo }</span><span>下单时间：<fmt:formatDate value="${order.createDate}" pattern="yyyy-MM-dd  HH:mm:ss" /> </span><span>订单总计：<font class="red font16">¥${order.amount }</font>（含运费${order.freight }）</span><span>订单状态：<font class="green">${order.orderStatusName }</font></span>
</div>
<c:choose>
	<c:when test="${order.orderStatus=='4' }">
		<p class="complaints_state">您的订单还未付款，点击<a href="${pageContext.request.contextPath}/f/order/toPayView?orderNo=${order.orderNo}&amount=${order.amount}">支付</a></p>
	</c:when>
	<c:when test="${order.orderStatus=='6' or order.orderStatus=='5'}">
		<p class="complaints_state">您的订单已经付款成功，我们会尽快安排发货，感谢您在初云汇商城购物，祝您生活愉快！</p>
	</c:when>
	<c:when test=""></c:when>
</c:choose>
</div>
</div>

<!-- <div class="my_balance line_none mt20"> -->
<%-- <div class="balance_title font14">进度跟踪</div>
</div>
<div class="my_couponscn info_box">
<table width="100%" cellspacing="0" cellpadding="0" border="0" class="tb_void">
<colgroup>
<col width="280px">
<col width="675px">
</colgroup>
<tbody>
<tr>
<td>
<div class="info_left">
<dl>
<dt>收货人信息</dt>
<dd>
<ul>
<li class="info_name">收&nbsp;货&nbsp;人：</li>
<li class="info_con">罗决心</li>
</ul>
<ul>
<li class="info_name">地&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;址：</li>
<li class="info_contwo">广东深圳市南山区海德三道天利名城C座1506</li>
</ul>
<ul>
<li class="info_name">手机号码：</li>
<li class="info_con">13510799469</li>
</ul>
</dd>
<dt>发票信息</dt>
<dd>
<ul>
<li class="info_name">发票类型：</li>
<li class="info_con">普通发票</li>
</ul>
<ul>
<li class="info_name">发票抬头：</li>
<li class="info_contwo">深圳嘉宝易汇通科技发展有限公司</li>
</ul>
<ul>
<li class="info_name">发票内容：</li>
<li class="info_con">办公用品</li>
</ul>
</dd>
<dt>支付信息</dt>
<dd>
<ul>
<li class="info_name">付款方式：</li>
<li class="info_con">网银支付-交通银行</li>
</ul>
<ul>
<li class="info_name">运&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;费：</li>
<li class="info_contwo">¥0.00</li>
</ul>
<ul>
<li class="info_name">优&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;惠：</li>
<li class="info_con">¥0.00</li>
</ul>
<ul>
<li class="info_name">订单金额：</li>
<li class="info_con">¥1999.00</li>
</ul>
</dd>
</dl>
</div>
</td>
<td>
<div class="info_right">
<div class="cancel_progress state_one state_two">
<span class="process_one"><p class="font14 bold">订单提交</p><p class="cancel_date">2014-10-24<br />12:03:32</p></span>
<span class="process_two"><p class="font14 bold">付款成功</p><p class="cancel_date">2014-10-24<br />12:03:32</p></span>
<span class="process_tre"><p class="font14 bold">商品出库</p><p class="cancel_date">2014-10-24<br />12:03:32</p></span>
<span class="process_four"><p class="font14 bold">收货完成</p><p class="cancel_date">2014-10-24<br />12:03:32</p></span>
</div>
<div class="progress_list">
<table width="100%" cellspacing="0" cellpadding="0" border="0" id="progress_list">
<colgroup>
<col width="150px">
<col width="400px">
<col>
</colgroup>
<thead>
<tr>
<th>处理时间</th>
<th>处理信息</th>
<th>操作人</th>
</tr>
</thead>
<tbody>
<tr>
<td><div class="gray999">2014-10-14 20:10:32</div></td>
<td>您提交了订单，请等待系统确认 </td>
<td>客户</td>
</tr>
</tbody>
<tbody>
<tr>
<td><div class="gray999">2014-10-14 20:10:32</div></td>
<td>第三方卖家已经开始拣货，订单不能修改第三方卖家已经开始拣货，订单不能修改第三方卖家已经开始拣货，订单不能修改第三方卖家已经开始拣货，订单不能修改 </td>
<td>客户</td>
</tr>
</tbody>
</table>
<div class="express_inq"><span>送货方式：普通快递</span><span>承运人：圆通快递</span><span>承运人电话：021-69777888</span><span>货运单号：V214489879</span><a class="bluenone" href="#">点击查询</a></div>
</div>
</div>
</td>
</tr>
</tbody>
</table>
</div> --%>
<div class="my_balance line_none mt10">
<div class="balance_title font14">商品清单</div>
</div>
<div class="my_couponscn">
<table width="100%" cellspacing="0" cellpadding="0" border="0" class="tb_void">
<colgroup>
<col width="120px">
<col width="100px">
<col width="330px">
<col width="100px">
<col width="80px">
<col width="80px">
<col>
</colgroup>
<thead>
<tr class="my_couponscn_inof">
<th>商品编号</th>
<th>商品图片</th>
<th>商品名称</th>
<th>价格</th>
<th>积分</th>
<th>商品数量</th>
<th>操作</th>
</tr>
</thead>
<tbody id="tbody_of_order">
<c:forEach items="${order.list_ordertable_item }" var="item">
<tr>
<td>${item.goodsNo }</td>
<td>
<div class="img-list">
<a href="${pageContext.request.contextPath}/f/product/details?productId=${item.goodsNo}" target="_blank"><img src="${baseFilePath }${item.thumbnail}"></a>
</div>
</td>
<td><div class="textleft"><a class="bluenone" href="${pageContext.request.contextPath}/f/product/details?productId=${item.goodsNo}">${item.fullName }</a></div></td>
<td><div class="red">¥${item.price }</div></td>
<td>+10</td>
<td>${item.quantity }</td>
<td class="operation_td">
	<a class="bluenone" href="#" value="${item.goodsNo}">取消订单</a>
</td>
</tr>
</c:forEach>
</tbody>
</table>
<script type="text/javascript">
	var row = $("#tbody_of_order tr").length;
	if(row>=2){
		var ceil = $("#tbody_of_order td").length/row;
		console.info(ceil);//rowspan="${item.quantity}"
		console.info("size:"+$("#tbody_of_order td").filter(".operation_td").size());
		$("#tbody_of_order td").filter(".operation_td").each(function(i,td){
			if(i==0){
				$(this).attr("rowspan",row)
				console.info($(this).html());
			}else{
				$(this).remove();
			}
		});
	}
</script>
<div class="order_total">
<p><span><font class="red">${order.productCount}</font>件商品，总商品金额：¥${order.price }</span><span>优惠券：-¥${order.couponDiscount }</span><span>运费：¥${order.freight }</span></p>
<p>应付总额：<font class="red font16">¥${order.amount}</font></p>
</div>
</div>
<!-- </div> -->
</div>

<!-- 五楼 -->
<div class="footerWrap">
<div class="promise_nav">
<ul>
<li class="promise_one"><s></s>品质保障<br/><span>品质护航&nbsp;&nbsp;购物无忧</span></li>
<li class="line-nav"></li>
<li class="promise_two"><s></s>品质保障<br/><span>正品行货&nbsp;&nbsp;精致服务</span></li>
<li class="line-nav"></li>
<li class="promise_tre"><s></s>专业配送<br/><span>国际物流配送&nbsp;&nbsp;急速到货</span></li>
<li class="line-nav"></li>
<li class="promise_four"><s></s>帮助中心<br/><span>您的购物指南</span></li>
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
        <div class="sub_nav_right"><a href=""><img src="../images/buy-security.jpg"/></a></div>
    </div>
    <div class="footer">
    	<div class="footer_nav">
        	<a href="">关于我们</a>|
            <a href="">联系我们</a>|
            <a href="">诚征英才</a>|
            <a href="">欢迎合作</a>|
            <a href="">知识产权</a>|
            <a href="">著作权与商标声明</a>|
            <a href="">法律声明</a>|
            <a href="">服务条款</a>|
            <a href="">隐私声明</a>|
            <a href="">网站地图</a>
        </div>
    </div>
    </div>
</body>
</html>
