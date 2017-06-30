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
<title>商品信息</title>
<link href="${pageContext.request.contextPath}/static/css/public.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript">
function selectTag(showContent,selfObj){
	if(showContent=='tagContent1'&&$("tbody tr").length==0){
		$.getJSON("${pageContext.request.contextPath}/f/mobile/product/getParameters?id=${product.id}", function(data){
			$.each(data, function(n, value) {
				$("tbody").append("<tr><td>"+value.name+"</td><td>"+value.parameterValue+"</td></tr>");
			    }); 
		});
	}
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

<!-- 内容区 -->
<div class="detail-wrapper">
    <!-- 切换 -->
    <div class="tab">
        <ul class="tab-bar" id="tags">
            <li class="tab-bar-group">
            	<span class="tab-bar-item hover"><a onClick="selectTag('tagContent0',this)" href="javascript:void(0)">图文详情</a></span>
           		<span class="tab-bar-item"><a onClick="selectTag('tagContent1',this)" href="javascript:void(0)">商品规格</a></span>
            </li>
        </ul>
        <div id="tagContent"> 
        	<!-- 图文 -->
            <div class="tab-group show" id="tagContent0">
         	  ${product.introduction}
            </div>
            <!-- 规格参数 -->
            <div class="tab-group " id="tagContent1">
            	<table border="0" cellpadding="0" cellspacing="1">
                	<colgroup>
                    <col width="30%">
                    </colgroup>
                	<thead>
                    	<tr><th colspan="3">基本参数</tr>
                    </thead>
                    <tbody>
                    
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
</body>
</html>
