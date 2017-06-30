<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>华夏通商城</title>
<link href="${pageContext.request.contextPath  }/static/css/common.css" rel="stylesheet" type="text/css"/>
<link href="${pageContext.request.contextPath  }/static/css/shangou.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath  }/static/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath  }/static/js/banner.js"></script>
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

</head>

<body>

<%@include file="../../include/banner_top.jsp"%>

<!-- 品牌介绍区  -->
<div class="brand_pro_box">
<div class="brand_title"><font>品牌故事</font></div>
<div class="brand_pro_con">
<div class="font_con">
<p>${brand.introduce}</p>
<!-- <p>欧莱雅(LOREAL)在20世纪的发展史，可说是日化工业发展史的一部分代表。巴黎欧莱雅(LOREAL)拥有一流的药学试验室和皮肤学中心，还有遍布全球的研究测试中心，这骄人的产品研发背景使其不断推出适应全球消费者不同需要的优质产品，登陆中国市场后，欧莱雅(LOREAL)与苏州医学院合作成立了欧莱雅(LOREAL)护肤美容研究中心，专门致力于研发适合中国女性肌肤的产品。</p>
<p>作为全球最大的化妆品集团，欧莱雅(LOREAL)不遗余力地为满足世界各国人民对美的追求而奋斗。1997年，欧莱雅(LOREAL)正式来到中国，积极投身于全球瞩目的中国市场，奉献出高科技创新优质的护肤、护发、彩妆、香水等产品，为中国人民增添一份美的姿彩。</p> -->
</div>
<div class="brand_img"><img src="${pageContext.request.contextPath}/f/showImage?filePath=/brand_img.jpg"/></div>
</div>
</div>
<!-- 产品内容区  -->
<div class="flash_pro_box">
<div class="fore fore1">
                    <ul class="tabs">
                    <li class="curr"><a href="${pageContext.request.contextPath}/f/brand/select?id=${brand.id}">所有商品（${number}）</a></li>
                 <c:forEach items="#{list_productCategory}" var="item" >
               			  <li><a href="${pageContext.request.contextPath}/f/brand/select?id=${brand.id}&productcategoryid=${item.id}" cid="1455">${item.name }（${item.number_product }）</a></li>
                 </c:forEach>
                    
                    <!-- <li><a href="" cid="2580">钱包（4）</a></li>
                    <li><a href="" cid="2584">钱包/卡包（30）</a></li>
                    <li><a href="" cid="2589">拉杆箱/拉杆包（1）</a></li>
                    <li><a href="" cid="5262">男士手包（9）</a></li>
                    <li><a href="" cid="12029">男士腰带/礼盒（37）</a></li>
                    <li><a href="" cid="12030">女士腰带/礼盒（9）</a></li>
                    <li><a href="" cid="12071">双肩包（6）</a></li>
                    <li><a href="" cid="12072">单肩/斜挎包（10）</a></li>
                    <li><a href="" cid="12073">钥匙包（3）</a></li> -->
                    
                    </ul>
                    <div class="extra">
                        <a href="#" class="down">展开<b></b></a>
                    </div>
                </div>
        <div class="fore fore2">
            <ul id="filter">
                <li><input type="checkbox" name="" id="have" class="checkbox" style="display:none"/> <label for="have"> </label></li>
                <li>
                <c:if test="${flag=='desc'||flag==''}">
                <a class="down" id="sortPriceDesc" href="${pageContext.request.contextPath}/f/brand/select?id=${brand.id}&sort=desc">价格<b></b></a>
                </c:if>
                <c:if test="${flag=='asc'}">
                <a class="down" id="sortPriceAsc" href="${pageContext.request.contextPath}/f/brand/select?id=${brand.id}&sort=asc">价格<b></b></a>
                </c:if>
                </li>
                <li><a>折扣<b style="display:none;"></b></a></li>
            </ul>
        </div>
        <!-- 产品列表 -->
        <div class="fore3">
        <ul class="am-list">
        
        <c:forEach items="${list}" var="item">
        
        <li>
    <div class="brand-item">
        <div class="p-img">
            <a href="#" target="_blank">
                <img width="220" height="220" src="${baseFilePath }/${item.image}" alt="">
            </a>
        </div>
        <div class="p-name">
            <a href="#" target="_blank">${item.fullName}</a>
        </div>
        <div class="p-price">
            <strong>¥${item.price}</strong>
            <del>¥109</del>
			<span>5.5折</span>
        </div>
    </div>
	</li>
	</c:forEach>
	
	
	
    <!-- <li>
    <div class="brand-item">
        <div class="p-img">
            <a href="#" target="_blank">
                <img width="220" height="220" src="images/556c2560Neaab7641.jpg" alt="">
            </a>
        </div>
        <div class="p-name">
            <a href="#" target="_blank">珀颜（Baeanoe）轻盈嫩白防晒乳隔离防晒亮白嫩肤</a>
        </div>
        <div class="p-price">
            <strong>¥59</strong>
            <del>¥109</del>
			<span>5.5折</span>
        </div>
    </div>
	</li>
    <li>
    <div class="brand-item">
        <div class="p-img">
            <a href="#" target="_blank">
                <img width="220" height="220" src="images/556c2560Neaab7641.jpg" alt="">
            </a>
        </div>
        <div class="p-name">
            <a href="#" target="_blank">珀颜（Baeanoe）轻盈嫩白防晒乳隔离防晒亮白嫩肤</a>
        </div>
        <div class="p-price">
            <strong>¥59</strong>
            <del>¥109</del>
			<span>5.5折</span>
        </div>
    </div>
	</li>
    <li>
    <div class="brand-item">
        <div class="p-img">
            <a href="#" target="_blank">
                <img width="220" height="220" src="images/556c2560Neaab7641.jpg" alt="">
            </a>
        </div>
        <div class="p-name">
            <a href="#" target="_blank">珀颜（Baeanoe）轻盈嫩白防晒乳隔离防晒亮白嫩肤</a>
        </div>
        <div class="p-price">
            <strong>¥59</strong>
            <del>¥109</del>
			<span>5.5折</span>
        </div>
    </div>
	</li>
    <li>
    <div class="brand-item">
        <div class="p-img">
            <a href="#" target="_blank">
                <img width="220" height="220" src="images/556c2560Neaab7641.jpg" alt="">
            </a>
        </div>
        <div class="p-name">
            <a href="#" target="_blank">珀颜（Baeanoe）轻盈嫩白防晒乳隔离防晒亮白嫩肤</a>
        </div>
        <div class="p-price">
            <strong>¥59</strong>
            <del>¥109</del>
			<span>5.5折</span>
        </div>
    </div>
	</li>
    <li>
    <div class="brand-item">
        <div class="p-img">
            <a href="#" target="_blank">
                <img width="220" height="220" src="images/556c2560Neaab7641.jpg" alt="">
            </a>
        </div>
        <div class="p-name">
            <a href="#" target="_blank">珀颜（Baeanoe）轻盈嫩白防晒乳隔离防晒亮白嫩肤</a>
        </div>
        <div class="p-price">
            <strong>¥59</strong>
            <del>¥109</del>
			<span>5.5折</span>
        </div>
    </div>
	</li> -->
        </ul>
        <!-- 分页 -->
<div class="pagination">
<span class="specify">共24页，到第
<input class="inputbox" name="" type="text" /> 页
<input class="mod_page_go" name="" type="button" value="确定" />
</span>
<div class="page">
<span class="disabled"> < </span>
<span class="current">1</span>
<a href="#">2</a>
<a href="#">3</a>
<a href="#">4</a>
<a href="#">5</a>
<a href="#">6</a>
<a href="#">7</a>
...
<a href="#">199</a>
<a href="#">200</a>
<a href="#"> > </a></div>
</div>
        </div>
</div>
<!--  版权区域 -->
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
        <div class="sub_nav_right"><a href=""><img src="images/buy-security.jpg"/></a></div>
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
