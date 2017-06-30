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
<title>用户口碑</title>
<link href="${pageContext.request.contextPath}/page/mobile/css/public.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript">
function selectTag(showContent,selfObj){  
	// 操作标签  
	var tag = document.getElementById("tags").getElementsByTagName("span");  
	var taglength = tag.length;  
	for(i=0; i<taglength; i++){  
		tag[i].className = "";  
	}  
	selfObj.parentNode.className = "hover";  
	// 操作内容  
	for(i=0; j=document.getElementById("tagContent"+i); i++){  
		j.style.display = "none";  
	}  
	document.getElementById(showContent).style.display = "block";  
} 
</script>  
</head>
<body>
<!-- 提示区域 -->
<c:if test="${flag==0}">
<div class="dow-pop">
    <div class="close-box"><img src="${pageContext.request.contextPath}/static/images/close.png"/></div>
    <div class="title-box">华夏通移动商城注册送优惠券</div>
    <div class="dow-app"><a href="#">下载APP</a></div>
</div>
</c:if>

<!-- 头部 -->
<div class="sn-nav">
    <div class="sn-nav-back"><a class="sn-iconbtn" href="javascript:history.back(1)">返回</a></div>
    <div class="sn-nav-title">用户口碑</div>
</div>
<!-- 内容区域 -->
<div class="detail-wrapper">
    <!-- 评分 -->
    <div class="com-score">
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
    </div>
    <div class="tab">
        <ul class="tab-bar" id="tags">
            <li class="tab-bar-group">
            	<span class="tab-bar-item hover"><a onClick="selectTag('tagContent0',this)" href="javascript:void(0)">好评（${avgCore.hp}）</a></span>
           		<span class="tab-bar-item"><a onClick="selectTag('tagContent1',this)" href="javascript:void(0)">中评（${avgCore.zp}）</a></span>
                <span class="tab-bar-item"><a onClick="selectTag('tagContent2',this)" href="javascript:void(0)">差评（${avgCore.cp}）</a></span>
            </li>
        </ul>
        <div id="tagContent"> 
        	<!-- 好评 -->
            <div class="tab-group show" id="tagContent0">
	            <c:if test="${not empty(hpList)}">
		            <c:forEach items="${hpList}" var="hpItem" varStatus="idx">
		            	<!-- 评价文字 -->
		                <div class="com-eval">
		                    <!-- 时间 -->
		                    <div class="score-time">
		                    <span>${hpItem.createTime}</span>
		                    <font class="fl">${hpItem.userName}</font>
		                    <div class="starBar pr fl">
		                                <div class="starBarMask" style="${hpItem.scorePercent}"></div>
		                            </div></div>
		                    <!-- 评价内容 -->
		                    <div class="score-con">${hpItem.content}</div>
		                </div>
		            </c:forEach>
	            </c:if>
            </div>
            <!-- 中评 -->
            <div class="tab-group " id="tagContent1">
            	 <c:if test="${not empty(zpList)}">
		            <c:forEach items="${zpList}" var="zpItem" varStatus="idx">
		            	<!-- 评价文字 -->
		                <div class="com-eval">
		                    <!-- 时间 -->
		                    <div class="score-time">
		                    <span>${zpItem.createTime}</span>
		                    <font class="fl">${zpItem.userName}</font>
		                    <div class="starBar pr fl">
		                                <div class="starBarMask" style="${zpItem.scorePercent}"></div>
		                            </div></div>
		                    <!-- 评价内容 -->
		                    <div class="score-con">${zpItem.content}</div>
		                </div>
		            </c:forEach>
	            </c:if>
            </div>
            <!-- 差评 -->
            <div class="tab-group " id="tagContent2">
            	 <c:if test="${not empty(cpList)}">
		            <c:forEach items="${cpList}" var="cpItem" varStatus="idx">
		            	<!-- 评价文字 -->
		                <div class="com-eval">
		                    <!-- 时间 -->
		                    <div class="score-time">
		                    <span>${cpItem.createTime}</span>
		                    <font class="fl">${cpItem.userName}</font>
		                    <div class="starBar pr fl">
		                                <div class="starBarMask" style="${cpItem.scorePercent}"></div>
		                            </div></div>
		                    <!-- 评价内容 -->
		                    <div class="score-con">${cpItem.content}</div>
		                </div>
		            </c:forEach>
	            </c:if>
            </div>
        </div>
    </div>
</div>
</body>
</html>
