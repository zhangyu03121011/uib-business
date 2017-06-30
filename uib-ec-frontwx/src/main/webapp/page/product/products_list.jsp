<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>初云汇商城</title>
<link href="${pageContext.request.contextPath  }/static/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath  }/static/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath  }/static/js/hover.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath  }/static/js/photo-info.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath  }/static/js/select.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath  }/static/js/jquery.cookie.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath  }/static/js/product-scan.js"></script>
<link href="${pageContext.request.contextPath  }/static/jquery-jbox/2.3/Skins/Bootstrap/jbox.min.css" rel="stylesheet" />
<script src="${pageContext.request.contextPath  }/static/jquery-jbox/2.3/jquery.jBox-2.3.min.js" type="text/javascript"></script>
<script type="text/javascript">
var arrs = new Array("brand","specification","property","parameter");
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
$(function(){
	$(".list_photobox .p_btn .catbtn").click(function(){
		var $this = $(this);
		var specificationId = "";
		var productId = $this.parent().attr("data-pid");
		$.ajax({
			url:"${pageContext.request.contextPath}/f/cart/addCart",
			type: "GET",
			data:{"productId":productId,"quantity":1,"specificationId":specificationId},
			dataType: "json",
			success: function(message){
				if(message.cartItemVoList!=null){
					var $cart_menu_bd = $("#cart-menu-bd");
					$cart_menu_bd.empty();
					var cart_item_html = "<dl>";
					$("#font_of_cart_count").text(message.quantity);
					$.each(message.cartItemVoList,function(indec,cartItem){
						cart_item_html += "<dt><a href=\"#\"><img src=\"../showImage?filePath="+(cartItem.thumbnail==null?"123":cartItem.thumbnail)+"\" /></a>"+
						  "<p>"+
						  "<a class=\"tit\" href=\"../product/details?productId="+(cartItem.productId)+"\">"+ (cartItem.name)+
			              "</a> <span>"+(cartItem.price)+"x"+(cartItem.quantity)+"</span> <a class=\"del\" href=\"javascript:void(0);\" cartId=\""+cartItem.id+"\" data-id=\""+cartItem.productId+"\">删除</a>"+
	    				  "</p>"+
	  					  "</dt>";
					});
					cart_item_html += "<dd><em>购物车里还有"+(message.quantity)+"件商品</em> <a href=\"../cart/list\">去购物车并结算</a></dd>";
					cart_item_html += "</dl>";
					$cart_menu_bd.append(cart_item_html);
				}
				$.jBox.tip(message.content);
			}
		});
	});
	$("#select-condition ul").on("click","li a b",function(){
		var $this = $(this);
		var data_id = $this.parent().attr("data-id");
		var name_value = $this.prev().text();
		var id_type = data_id.split("_")[0];
		var id_name = id_type +"Id";
		var id_value = data_id.split("_")[1];
		if(id_name=='specificationId'){
			id_name +="s";
		}
		if(id_name!='brandId'){
			var old_value = $("#"+id_name).val();
			var old_name = $("#"+id_type+"Name").val();
			var reg = new RegExp(",?"+id_value+"","gmi")
			var new_value = old_value.replace(reg,'');
			reg = new RegExp(",?"+name_value);
			var new_name_value = old_name.replace(reg,'');
			$("#"+id_name).val(new_value);
			$("#"+id_type+"Name").val(new_name_value);
			if(new_name_value==''){
				$("#"+id_type+"ParentName").val("");
			}
		}else{
			$("#"+id_name).val("");
			$("#"+id_type+"Name").val("");
			$("#"+id_type+"ParentName").val("");
		}
		$this.parents("li").remove();
		//$("#"+prefix+"_list").parents(".porducts_filter_brand").show();
		//var parent_div = $("#"+prefix+"_list a[data-id='"+id_value+"']").parents(".a-values").show();
		var parent_div = $(".porducts_filter_brand a[data-id='"+data_id+"']").parents(".a-values").show();
		parent_div.parents(".porducts_filter_brand").show();
		parent_div.prev().show();
		query();
	});
	
	$("#all-revocation").click(function(){
		$("#select-condition ul").empty();
		$(".porducts_filter_brand").show();
		$(".porducts_filter_brand .a-values,.a-key.font14").show();
		$.each(arrs,function(n,value){
			var id_name = value +"Id";
			if(value=="specification"){
				id_name +="s";
			}
			$("#"+id_name).val("");
			$("#"+value+"Name").val("");
			$("#"+value+"ParentName").val("");
		});
		query();
	});
	
	$(".porducts_filter_brand .tabcon div a").bind("click",function(){
		var $this = $(this);
		var child_name = $this.text();
		var parent_name = $this.parents(".a-values").prev().text();
		var data_id = $this.attr("data-id");
		var id_type = data_id.split("_")[0];
		var id_name = id_type +"Id";
		var id_value = data_id.split("_")[1];
		if(id_name=='specificationId'){
			id_name +="s";
		}
		var old_value = $("#"+id_name).val();
		var old_name = $("#"+id_type+"Name").val();
		$("#"+id_name).val(id_value);
		$("#"+id_type+"Name").val(child_name);
		if(id_name!='brandId' && typeof(old_value)!='undefined' && old_value!=''){
			$("#"+id_name).val(old_value+","+id_value);
			$("#"+id_type+"Name").val(old_name+","+child_name);
		}
		$("#"+id_type+"ParentName").val(parent_name);
		var a_html = "<li><a href=\"javascript:void(0);\" data-id=\""+data_id+"\">"+parent_name+"<strong>"+child_name+"</strong><b></b></a></li>";
		$("#select-condition ul").append(a_html);
		$this.parents(".a-values").hide();
		$this.parents(".a-values").prev().hide();
		$this.parents(".porducts_filter_brand").hide();
		query();
	});
	$(".sort_box .order").on("click","a",function(){
		var $this = $(this);
		var class_ = $this.parent().attr("class");
		if(class_!=null && class_!='' && $this.parent().attr("id")=="price_sdx" && class_!='curr up'){
			$this.parent().attr("class","curr up");
			$this.attr("orderType","price_asc");
			$("#orderType").val($this.attr("orderType"));
		}else{
			if($this.parent().attr("id")=="price_sdx"){
				$this.attr("orderType","price_desc");
			}
			$this.parent().attr("class","curr down");
		}
		$this.parent().siblings("dd").removeAttr("class");
		query();
	});
	
	$(".sort_box .selected,.type").delegate("input","change",function(){
		if($(".sort_box .selected input").is(":checked")){
			$(".sort_box .selected input").val("true");
		}else{
			//$(".sort_box .selected input").val("false");
		}
		query();
	});
	appendProductScaned();
	var more_boolean = true;
	$("#attr-more").click(function(){
		var $this = $(this);
		if(more_boolean){
			$(".porducts_filter_brand:hidden").each(function(){
				if(typeof($(this).attr("hidden"))=='undefined'){
					$(this).show();
				}
			});
			$this.children().text("收起");
			more_boolean = false;
		}else{
			var i = 0;
			$(".porducts_filter_brand:visible").each(function(){
				if(i>=4){
					$(this).hide();
				}
				i++;
			});
			$this.children().text("更多选项");
			more_boolean = true;
		}
		
	});
});
function query(){
	var sdx = "price_asc";
	$(".sort_box .order a").each(function(){
		var class_ = $(this).parent().attr("class");
		if(class_!=null && class_!=''){
			sdx = $(this).attr("orderType");
		}
	});
	$("#query_form").submit();
}
function appendProductScaned(){
	var productListJson = $.cookie("productList");
	//console.info("json:"+productListJson);
	if(typeof(productListJson)=='undefined'){
		return;
	}
	var productList = JSON.parse(productListJson).data;
	var appendHtml = '';
	$.each(productList,function(index,item){
		if(index==productList.length-1){
			appendHtml += "<dl class=>";
		}else{
			appendHtml += "<dl class=\"line\">";
		}
		appendHtml += "<dd><a href=\"#\"><img src=\"${baseFilePath}"+item.imageUrl+"\" /></a></dd>"+
		"<dt><p class=\"name\"><a href=\"${pageContext.request.contextPath }/f/product/details?productId="+item.id+"\">"+item.name+"</a></p>"+
		"<p>销售价:<span class=\"red font14\">￥"+item.price+"</span></p></dt></dl>";
	});
	$("#product_scaned").append(appendHtml);
}
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
<%@ include file="../../include/product_top.jsp" %>

<!-- 商品列表 -->
<div class="one_floor w1200">
<!-- 左边内容 -->
<div class="products_list_left">
<!-- 分类 -->
<c:if test="${not empty(subCategoryList)}">
	<c:forEach items="${subCategoryList}" var="item" varStatus="idx">
		<div class="sort_title font16"><a href="${pageContext.request.contextPath }/f/product/list?productCategoryId=${item.id}"></a>${item.text}</div>
		<c:if test="${not empty(item.children)}">
			<c:forEach items="${item.children }" var="childrenItem">
				<div class="sort_content">
					<div class="my_left_category">
    					<div class="h2_cat" onmouseover="this.className='h2_cat active_cat'" onmouseout="this.className='h2_cat'">
        				<h3><a href="${pageContext.request.contextPath }/f/product/list?productCategoryId=${childrenItem.id}">${childrenItem.text }</a></h3>
        				<c:if test="${not empty(childrenItem.children)}">
        				<div class="h3_cat">
            				<div class="shadow">
              					<div class="ht_cat_line"></div>
                				<div class="shadow_border">
                    				<ul>
                    					<c:forEach items="${childrenItem.children }" var="ccItem">	
                        				<li><a href="${pageContext.request.contextPath }/f/product/list?productCategoryId=${ccItem.id}">${ccItem.text }</a></li>
                        				</c:forEach>
                    				</ul>
                				</div>
            				</div>
        				</div>
        				</c:if>
    					</div>
					</div>
				</div>
			</c:forEach>
		</c:if>
	</c:forEach>
</c:if>
<!-- 热销商品 -->
<div class="hot_title font16 mt20">热销商品</div>
<div class="hot_content">
	<c:forEach items="${hotPoroductList}" var="hotProduct" varStatus="status">
		<c:choose>
			<c:when test="${status.last }">
				<dl>
			</c:when>
			<c:otherwise>
				<dl class="line">
			</c:otherwise>
		</c:choose>
			<dd>
				<a href="${pageContext.request.contextPath }/f/product/details?productId=${hotProduct.id}"><img src="${baseFilePath}${hotProduct.image}" /></a>
			</dd>
			<dt>
				<p class="name"><a href="${pageContext.request.contextPath }/f/product/details?productId=${hotProduct.id}">${hotProduct.name }</a></p>
				<p>销售价:<span class="red font14">￥${hotProduct.price }</span></p>
			</dt>
		</dl>
	</c:forEach>
</div>
<!-- 浏览记录 -->
<div class="hot_title font16 mt20">浏览记录</div>
<div class="hot_content" id="product_scaned">
<!--
<dl class="line">
<dd><a href="#"><img src="${pageContext.request.contextPath  }/static/images/cpfive.jpg" /></a></dd>
<dt>
<p class="name"><a href="#">绚丽7.9英寸显示屏绚丽7.9英寸显示屏</a></p>
<p>销售价:<span class="red font14">￥99.50</span></p>
</dt>
</dl>
<dl>
<dd><a href="#"><img src="${pageContext.request.contextPath  }/static/images/cpfive.jpg" /></a></dd>
<dt>
<p class="name"><a href="#">绚丽7.9英寸显示屏绚丽7.9英寸显示屏</a></p>
<p>销售价:<span class="red font14">￥99.50</span></p>
</dt>
</dl>
-->
</div>
</div>
<!-- 右边内容 -->
<form id="query_form" action="../product/query" method="get">
<input type="hidden" name="productCategoryId" value="${param.productCategoryId}"/>
<div class="products_list_right">
<div class="products_filter">
<div class="products_filter_title font16">商品筛选</div>
<div class="porducts_filter_con">
<!-- 已选条件 -->
<div class="selected-c">
<div class="attr">
<div class="a-key font14">已选条件：</div>
<div class="v-tabs">
<div class="v-fold" id="select-condition">
<ul class="f-list">
	<c:if test="${param.brandId!=null and param.brandId!='' }">
		<li><a href="#" data-id="brand_${param.brandId}">${param.brandParentName}<strong>${param.brandName }</strong><b></b></a></li>
	</c:if>
	<c:if test="${param.specificationIds!=null and param.specificationIds!='' }">
		<c:set value="${ fn:split(param.specificationIds, ',') }" var="specificationIds" />
		<c:set value="${ fn:split(param.specificationName, ',') }" var="specificationNames" />
		<c:forEach items="${specificationIds}" var="specificationId" varStatus="status">
			<li><a href="#" data-id="specification_${specificationId}">${param.specificationParentName }<strong>${specificationNames[status.index] }</strong><b></b></a></li>
		</c:forEach>
	</c:if>
	<c:if test="${param.propertyId!=null and param.propertyId!='' }">
		<c:set value="${ fn:split(param.propertyId, ',') }" var="propertyIds" />
		<c:set value="${ fn:split(param.propertyName, ',') }" var="propertyNames" />
		<c:forEach items="${propertyIds}" var="propertyId" varStatus="status">
			<li><a href="#" data-id="property_${propertyId}">${param.propertyParentName }<strong>${propertyNames[status.index] }</strong><b></b></a></li>
		</c:forEach>
	</c:if>
	<c:if test="${param.parameterId!=null and param.parameterId!='' }">
		<c:set value="${ fn:split(param.parameterId, ',') }" var="parameterIds" />
		<c:set value="${ fn:split(param.parameterName, ',') }" var="parameterNames" />
		<c:forEach items="${parameterIds }" var="parameterId" varStatus="status">
			<li><a href="#" data-id="parameter_${parameterId}">${param.parameterParentName }<strong>${parameterNames[statud.index] }</strong><b></b></a></li>
		</c:forEach>
	</c:if>
</ul>
</div>
<div class="v-option"><a href="javascript:void(0);"><span id="all-revocation">全部撤消</span></a></div>
</div>
</div>
</div>
<!-- 品牌 -->
<input type="hidden" id="brandParentName" name="brandParentName" value="${param.brandParentName }"/>
<input type="hidden" id="brandId" name="brandId" value="${param.brandId }"/>
<input type="hidden" id="brandName" name="brandName" value="${param.brandName }"/>
<c:if test="${not empty(brandList)}">
<c:choose>
	<c:when test="${param.brandId!=null and param.brandId!=''}">
		<div class="porducts_filter_brand" hidden>
	</c:when>
	<c:otherwise>
		<div class="porducts_filter_brand">
	</c:otherwise>
</c:choose>
<div class="attr">
<div class="a-key font14">品牌：</div>
<div class="a-values">
<div class="v-option"><span class="o-more"><b></b>更多</span><span class="o-moreover hide"><b></b>收起</span></div>
<div class="v-tabs">
<ul class="tab hide">
<li class="curr">
所有品牌
<b></b>
</li>
<li>A</li>
<li>B</li>
<li>C</li>
<li>D</li>
<li>E</li>
<li>F</li>
</ul>
<div class="tabcon" id="brand_list">
<c:forEach items="${brandList}" var="brand">
<div><a href="javascript:void(0);" data-id="brand_${brand.id}">${brand.name }</a></div> 
</c:forEach>
</div>
</div>
</div>
</div>
</div>
</c:if>
<!-- 规格 -->
<input type="hidden" id="specificationParentName" name="specificationParentName" value="${param.specificationParentName}"/>
<input type="hidden" id="specificationIds" name="specificationIds" value="${param.specificationIds }"/>
<input type="hidden" id="specificationName" name="specificationName" value="${param.specificationName }"/>
<c:if test="${not empty(specificationGroups)}">
<c:forEach items="${specificationGroups}" var="specificationGroup" varStatus="status">

<c:choose>
	<c:when test="${extendMap['hasSpecificationAll'] or specificationGroup.selected}">
		<div class="porducts_filter_brand" hidden>
	</c:when>
	<c:otherwise>
		<div class="porducts_filter_brand">
	</c:otherwise>
</c:choose>
<div class="attr" id="specification_list">
<div class="a-key font14">${specificationGroup.name}：</div>
<div class="a-values">
<div class="v-option"><span class="o-more"><b></b>更多</span><span class="o-moreover hide"><b></b>收起</span></div>
<div class="v-tabs">
<ul class="tab hide">
<li class="curr">
所有规格
<b></b>
</li>
<li>A</li>
<li>B</li>
<li>C</li>
<li>D</li>
<li>E</li>
<li>F</li>
</ul>
<div class="tabcon">
<c:forEach items="${specificationGroup.productSpecificationList}" var="specification">
<div class="specification_div">
	<a href="javascript:void(0);" data-id="specification_${specification.id}">${specification.name }</a>
</div>
</c:forEach>
</div>
</div>
</div>
</div>
</div>
</c:forEach>
</c:if>
<!-- 属性 -->
<input type="hidden" id="propertyParentName" name="propertyParentName" value="${param.propertyParentName }"/>
<input type="hidden" id="propertyId" name="propertyId" value="${param.propertyId }"/>
<input type="hidden" id="propertyName" name="propertyName" value="${param.propertyName }"/>
<c:if test="${not empty(propertyGroups)}">
<c:forEach items="${propertyGroups}" var="propertyGroup" varStatus="status">
<c:choose>
	<c:when test="${extendMap['hasPropertyAll'] or propertyGroup.selected}">
		<div class="porducts_filter_brand" hidden>
	</c:when>
	<c:otherwise>
		<div class="porducts_filter_brand">
	</c:otherwise>
</c:choose>
<div class="attr" id="property_list">
<div class="a-key font14">${propertyGroup.name }：</div>
<div class="a-values">
<div class="v-option"><span class="o-more"><b></b>更多</span><span class="o-moreover hide"><b></b>收起</span></div>
<div class="v-tabs">
<ul class="tab hide">
<li class="curr">
所有属性
<b></b>
</li>
<li>A</li>
<li>B</li>
<li>C</li>
<li>D</li>
<li>E</li>
<li>F</li>
</ul>
<div class="tabcon">
<c:forEach items="${propertyGroup.productPropertyList}" var="property">
<div>
	<a href="javascript:void(0);" data-id="property_${property.id }">${property.name }</a>
</div>
</c:forEach>
</div>
</div>
</div>
</div>
</div>
</c:forEach>
</c:if>
<!-- 参数 -->
<input type="hidden" id="parameterParentName" name="parameterParentName" value="${param.parameterParentName}"/>
<input type="hidden" id="parameterId" name="parameterId" value="${param.parameterId }"/>
<input type="hidden" id="parameterName" name="parameterName" value="${param.parameterName }"/>
<c:if test="${not empty(parameterGroups)}">
<c:forEach items="${parameterGroups}" var="parameterGroup" varStatus="status">
<c:choose>
	<c:when test="${extendMap['hasParameterAll'] or parameterGroup.selected}">
		<div class="porducts_filter_brand" hidden>
	</c:when>
	<c:otherwise>
		<div class="porducts_filter_brand">
	</c:otherwise>
</c:choose>
<div class="attr" id="parameter_list">
<div class="a-key font14">${parameterGroup.name }：</div>
<div class="a-values">
<div class="v-option"><span class="o-more"><b></b>更多</span><span class="o-moreover hide"><b></b>收起</span></div>
<div class="v-tabs">
<ul class="tab hide">
<li class="curr">
所有参数
<b></b>
</li>
<li>A</li>
<li>B</li>
<li>C</li>
<li>D</li>
<li>E</li>
<li>F</li>
</ul>
<div class="tabcon">
<c:forEach items="${parameterGroup.productParameterList}" var="parameter">
<div>
	<a href="javascript:void(0);" data-id="parameter_${parameter.id }">${parameter.name }</a>
</div>
</c:forEach>
</div>
</div>
</div>
</div>
</div>
</c:forEach>
</c:if>
<script type="text/javascript">
	var i = 0;
	//console.info("size:"+$(".porducts_filter_brand:visible").size());
	$(".porducts_filter_brand:visible").each(function(){
		if(i>=4){
			$(this).hide();
		}else{
			$(this).show();
		}
		i++;
	});
</script>
<!-- 价格 
<div class="prop-attrs">
<div class="attr">
<div class="a-key font14">价格：</div>
<div class="v-tabs">
<div class="prop-attrs">
<div class="v-fold">
<ul class="f-list">
<li><a href="#">0-1999</a></li>
<li><a href="#">2000-2999</a></li>
<li><a href="#">3000-3999</a></li>
<li><a href="#">0-1999</a></li>
<li><a href="#">2000-2999</a></li>
<li><a href="#">3000-3999</a></li>
<li><a href="#">0-1999</a></li>
<li><a href="#">2000-2999</a></li>
<li><a href="#">3000-3999</a></li>
<li><a href="#">2000-2999</a></li>
<li><a href="#">3000-3999</a></li>
<li><a href="#">2000-2999</a></li>
<li><a href="#">3000-3999</a></li>
</ul>
</div>
<div class="v-option"><span class="o-more"><b></b>更多</span><span class="o-more hide"><b></b>收起</span></div>
</div>
</div>
</div>
</div>
-->
<!-- 点击查看更多 -->
<div class="mb">
<div class="attr-extra" id="attr-more">
<div>更多选项<b></b></div>
</div>
</div>
</div>
</div>
<!-- 排序信息 -->
<div class="sort_box">
<div class="fore1">
<dl class="order">
<dt>排序：</dt>
<input type="hidden" id="orderType" name="orderType" value="price_asc"/>
<dd><a href="javascript:void(0);" orderType="sales_desc">销量</a></dd>
<dd class="curr up" id="price_sdx" orderType="price_asc"><a href="javascript:void(0);">价格</a><b></b></dd>
<dd><a href="javascript:void(0);" orderType="comments_desc">评论数</a></dd>
</dl>
<div class="pagin">
<span class="text"><i>1</i>/51</span>
<span class="prev-disabled">上一页<b></b></span>
<a href="#" class="next">下一页<b></b></a>
</div>
<div class="total"><span>共<strong>3037</strong>个商品</span></div>
</div>
<div class="fore2">
<dl>
<dd>
<div class="selected">
<label><span class="c1">
	<c:choose>
		<c:when test="${param.hasStock!=null and param.hasStock!='' }">
			<input name="hasStock" type="checkbox" value="${param.hasStock }" checked/>
		</c:when>
		<c:otherwise>
			<input name="hasStock" type="checkbox" value="${param.hasStock }" />
		</c:otherwise>
	</c:choose>
	</span>仅显示有货</label></div>
</dd>
</dl>
<dl class="type">
<dt>商品类型：</dt>
<dd><label><span class="c1"><input type="radio" name="shippingType" value="all" id="RadioGroup1_0" /></span>全部</label></dd>
<dd><label><span class="c1"><input type="radio" name="shippingType" value="own_self" id="RadioGroup1_0" /></span>e-life配送</label></dd>
<dd><label><span class="c1"><input type="radio" name="shippingType" value="other" id="RadioGroup1_0" /></span>第三方配送</label></dd>
<script type="text/javascript">
	$(".sort_box .type input[value=${param.shippingType}]").attr("checked",true);
</script>
</dl>
</div>
</div>
<!-- 图文排列 -->
<div class="list_photo mt20">
	<ul>
	<c:forEach items="${productList}" var="product">
		<li class="li_hover">
			<div class="list_photobox">
				<div class="p_img"><a href="${pageContext.request.contextPath }/f/product/details?productId=${product.id}"><img src="${baseFilePath}${product.image}"/></a></div>
				<div class="p_name"><a href="${pageContext.request.contextPath }/f/product/details?productId=${product.id}">${product.name } 
					<c:forEach items="${product.specificationGroups }" var="specificationGroup">
						${specificationGroup.productSpecificationList[0].name}
					</c:forEach>
					</a></div>
				<div class="p_price">${product.price }<span class="comment"><a href="#"><font class="blue">
						${product.productCommentCount}
					</font>条评论</a></span></div>
				<div class="p_btn" data-pid="${product.id }">
					<a class="buybtn" href="${pageContext.request.contextPath }/f/product/details?productId=${product.id}">立即购买</a>
					<a class="catbtn" href="javascript:void(0);"><s></s>加入购物车</a>
				</div>
			</div>
		</li>
	</c:forEach>
	</ul>
</div>
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
</form>
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
        <div class="sub_nav_right"><a href=""><img src="${pageContext.request.contextPath  }/static/images/buy-security.jpg"/></a></div>
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
