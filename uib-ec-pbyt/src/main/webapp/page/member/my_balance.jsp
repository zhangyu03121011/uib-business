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
	<!-- 我的elife内容区 -->
	<div class="myelife_content">
		
		

<!--以上复制-->
<div class="my_order">
<!-- 我的余额 -->
<div class="my_balance">
<div class="balance_title font14">我的账户余额</div>
<div class="balance_con">
<ul>
<li>
<p>账户余额：</p>
<p><font class="font18 red">${balance}</font> 元可用</p>
</li>
<li>
<p>冻结金额：</p>
<p><font class="font18 red">￥155.00</font> 不可用</p>
</li>
</ul>
<div class="rechargebox"><a class="blue_btn rechargebtn" href="../recharge_amount.html">充值</a><a class="ml20 bluenone" href="my_recharge_record.html">查看充值明细</a></div>
</div>
</div>
<div class="my_coupons mt20">
<ul>
<li id="one1" onclick="setTab('one',1,2)" class="hover">近三个月收支明细</li>
<li id="one2" onclick="setTab('one',2,2)">三个月前收支明细</li>
</ul>
</div>
<div class="my_couponscn top_line">
<!-- 近三个月 -->
<div id="con_one_1">
<table width="100%" cellspacing="0" cellpadding="0" border="0" class="tb_void">
<colgroup>
<col width="214px">
<col width="150px">
<col width="150px">
<col>
</colgroup>
<thead>
<tr class="my_couponscn_inof">
<th>时间</th>
<th>存入</th>
<th>支出</th>
<th>备注</th>
</tr>
</thead>
<c:forEach items="${list_ThreeMonth_before}" var="item">
<tbody>
<tr>
<td class="gray999">${item.createDate}</td>

<td><c:choose><c:when test="${empty item.credit}">-</c:when><c:otherwise>¥${item.credit}</c:otherwise></c:choose></td>
<td><c:choose><c:when test="${empty item.debit}">-</c:when><c:otherwise>¥${item.debit}</c:otherwise></c:choose></td>
<td><div class="textleft">${item.remarks}</div></td>
</tr>
</tbody>
</c:forEach>
<!-- <tbody>
<tr>
<td class="gray999">2014-03-06 21:10:05</td>
<td>¥10.00</td>
<td>-</td>
<td><div class="textleft">订单返余额(原返返现) 订单号：1167975482；退款理由：更换或添加新商品； 审核备注：订单退款系统审核通过！； 返还金额：243.000 退款途径：江苏快钱平台；相关财务单：839022022</div></td>
</tr>
</tbody> -->
</table>
</div>
<!-- 三个月前 -->
<div id="con_one_2" style="display:none">
<table width="100%" cellspacing="0" cellpadding="0" border="0" class="tb_void">
<colgroup>
<col width="214px">
<col width="150px">
<col width="150px">
<col>
</colgroup>
<thead>
<tr class="my_couponscn_inof">
<th>时间</th>
<th>存入</th>
<th>支出</th>
<th>备注</th>
</tr>
</thead>
<c:forEach items="${list_ThreeMonth_after}" var="item_after">
<tbody>
<tr>
<td class="gray999">${item_after.createDate}</td>
<td><c:choose><c:when test="${empty item_after.credit}">-</c:when><c:otherwise>¥${item_after.credit}</c:otherwise></c:choose></td>
<td><c:choose><c:when test="${empty item_after.debit}">-</c:when><c:otherwise>¥${item_after.debit}</c:otherwise></c:choose></td>
<td><div class="textleft">${item_after.remarks}</div></td></tr>
</tbody>
</c:forEach>


<!-- <tbody>
<tr>
<td class="gray999">2014-03-06 21:10:05</td>
<td>¥100.00</td>
<td>-</td>
<td><div class="textleft">订单<1167975486>的退款申请财务已受理完毕，正在向银行提交退款申请，请等待款项到账。</div></td>
</tr>
</tbody> -->
</table>
</div>

</div>
</div>
</div>

</body>
</html>
