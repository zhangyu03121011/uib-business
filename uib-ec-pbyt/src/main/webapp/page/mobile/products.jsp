<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<title>商品详情</title>
<link href="${pageContext.request.contextPath}/page/mobile/css/public.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/swiper.css">
</head>

<body>
	<!-- 提示区域 -->
	<c:if test="${flag==0}">
		<div class="dow-pop">
			<div class="close-box">
				<img src="${pageContext.request.contextPath}/static/images/close.png" />
			</div>
			<div class="title-box">华夏通移动商城注册送优惠券</div>
			<div class="dow-app">
				<a href="#">下载APP</a>
			</div>
		</div>
	</c:if>

	<!-- 内容区 -->
	<div class="detail-wrapper">
		<!-- 商品图片 -->
		<div class="products-ad">
			<div class="swiper-container">
				<div class="swiper-wrapper">
					<div class="swiper-slide">
						<img src="${product.image}" height="300" width="300"/>
					</div>
				</div>
			</div>
		</div>
		<!-- 价格 -->
		<div class="com-price">
			￥${product.price }
			<del>￥${product.marketPrice}</del>
		</div>
		<!-- 名字 -->
		<div class="com-name">
			<c:if test="${flag==0}">
				<a href="${pageContext.request.contextPath}/f/mobile/product/productsDetailHintApp?id=${product.id}">
			</c:if>
			<c:if test="${flag!=0}">
				<a href="${pageContext.request.contextPath}/f/mobile/product/productsDetail?id=${product.id}">
			</c:if>
			<h2>${product.fullName}</h2>
			<p>${product.seoDescription}</p>
			</a>
		</div>
		<!-- 评分 -->
		<div class="com-score">
				<c:if test="${flag==0}">
					<a href="${pageContext.request.contextPath}/f/mobile/comment/shareUserEstimate?productId=${product.id}">
				</c:if>
				<c:if test="${flag!=0}">
					<a href="${pageContext.request.contextPath}/f/mobile/comment/userEstimate?productId=${product.id}">
				</c:if>
				<div class="left">${avgCore.zongp}</div>
				<div class="com">
					<p>好评率：${avgCore.hpRate}%</p>
					<p>${avgCore.all}人评价</p>
				</div>
				<div class="right">
					<div class="starBar pr fl">
						<div class="starBarMask" style="${avgCore.scorePercent}"></div>
					</div>
				</div>
			</a>
		</div>
		<!-- 评价文字 -->
        <div class="tab-group show" id="tagContent0">
          <c:if test="${not empty(commentList)}">
           <c:forEach items="${commentList}" var="plItem" varStatus="idx">
           	<!-- 评价文字 -->
               <div class="com-eval">
                   <!-- 时间 -->
                   <div class="score-time">
                   <span>${plItem.createTime}</span>
                   <font class="fl">${plItem.userName}</font>
                   <div class="starBar pr fl">
                               <div class="starBarMask" style="${plItem.scorePercent}"></div>
                           </div></div>
                   <!-- 评价内容 -->
                   <div class="score-con">${plItem.content}</div>
               </div>
           </c:forEach>
          </c:if>
        </div>
	</div>
	<c:if test="${flag==0}">
		<div class="buy-btn-box-two">
			<div class="reg-btn mb1">
				<div class="w50 fl">
					<form action="${pageContext.request.contextPath}/page/mobile/login.jsp"  method="post">
						<input type="hidden"  name="productId" value="${product.id}">
						<input type="hidden" name="rMemberId"	 value="${memberId}">
						<input type="submit" value="已注册用户验证">
					</form>
				</div>
				<div class="w50">
					<form action="${pageContext.request.contextPath}/page/mobile/registered.jsp"  method="post">
						<input type="hidden"  name="productId" value="${product.id}">
						<input type="hidden" name="rMemberId"	 value="${memberId}">
						<input type="submit" value="注册后立即购买">
					</form>
				</div>
			</div>
	</div>
	</c:if>
	<script src="${pageContext.request.contextPath}/static/js/swiper.js"></script>
	<script>
		var swiper = new Swiper(".swiper-container");
	</script>
</body>
</html>
