<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglib.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>初云汇商城</title>
<script type="text/javascript" src="${pageContext.request.contextPath  }/static/js/hover.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath  }/static/js/jqzoom.pack.1.0.1.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath  }/static/js/jquery.jcarousel.pack.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath  }/static/js/jquery.jmpopups-0.5.1.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath  }/static/js/jquery.cookie.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath  }/static/js/product-scan.js"></script>
<link href="${pageContext.request.contextPath  }/static/jquery-jbox/2.3/Skins/Bootstrap/jbox.min.css" rel="stylesheet" />
<script src="${pageContext.request.contextPath  }/static/jquery-jbox/2.3/jquery.jBox-2.3.min.js" type="text/javascript"></script>
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

$(function() {
	//console.info(jQuery.fn.jquery);
    // 放大镜
    var options =
    {
        zoomWidth: 400,
        zoomHeight: 400,
        showEffect:'show',
        hideEffect:'fadeout',
        fadeoutSpeed: 'slow',
        title :false
    }
    $(".jqzoom").jqzoom(options);

    // 图片左右滚动
    $('#image_list').jcarousel();

    // 点击小图更换大图
    $('#image_list img:only-child').mousemove(function(){
		for (var i=0; i < $('#image_list li').length; i++){
			$('#image_list li').removeClass('hover');
		}
        $('#current_img').attr('src', this.src);
        // 大图的命名方式为 小图 + 下划线
        $('#current_img').parent().attr('href', this.alt);
	
    });
	$('#image_list li').mousemove(function (){
		$(this).addClass('hover');
	});
	var productList = new ArrayList();
	var productListJson = $.cookie("productList");
	var contains = false;
	if(productListJson!="undefined"&&productListJson!=null&&productListJson!=''){
		var productArray = JSON.parse(productListJson).data;
		$.each(productArray,function(index,value){
			if(value.id=="${product.id}"){
				contains = true;
			}
			productList.add(value);
		});
	}
	if(!contains){
		productList.add(new scanProductInfo("${product.id}","${product.name}","${product.price}","${product.image}"));
	}
	if(productList.size()>5){
		productList.remove(0);
	}
	//console.info("productList:"+JSON.stringify(productList));
	$.cookie("productList",JSON.stringify(productList),{expires:365});
});
function selectSpecification(obj){
	var $this = $(obj);
	var $parent = $this.parent();
	if($parent.attr("class")=="item disabled"){
		$(".item&.selected").attr("class","item disabled");
		$parent.attr("class","item selected");
	}else{
		$parent.attr("class","item disabled");
	}
}
function deleteCount(){
	var value = parseInt($("#buy_count").val());
	if(value<2){
	}else{
		$("#buy_count").val(--value);
		if(value<2){
			$("#minus_btn").attr("class","tbody_cout_btn leson lesonoff")
		}
	}
}
function addCount(){
	var value = parseInt($("#buy_count").val());
	$("#buy_count").val(++value);
	if(value>1){
		$("#minus_btn").attr("class","tbody_cout_btn leson leson")
	}
}

function addCart(){
	var quantity = $("#buy_count").val();
	var productId = $("#product_id").text();
	var specificationId = null;
	$(".item&.selected > a").each(function(){
		specificationId = $(this).attr("value");
	});
	if (/^\d*[1-9]\d*$/.test(quantity) && parseInt(quantity) > 0) {
		$.ajax({
			url:"${pageContext.request.contextPath}/f/cart/addCart",
			type: "GET",
			data:{"productId":productId,"quantity":quantity,"specificationId":specificationId},
			dataType: "json",
			async:false,
			success: function(message){
				if(message.cartItemVoList!=null){
					var $cart_menu_bd = $("#cart-menu-bd");
					$cart_menu_bd.empty();
					var cart_item_html = "<dl>";
					$("#font_of_cart_count").text(message.quantity);
					$.each(message.cartItemVoList,function(indec,cartItem){
						cart_item_html += "<dt><a href=\"#\"><img src=\"${UPLOAD_IMAGE_PATH}"+(cartItem.thumbnail==null?"123":cartItem.thumbnail)+"\" /></a>"+
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
	}else {
		$.jBox.tip("购买数量请输入整数");
	}
}
function appendProductScaned(){
	var productListJson = $.cookie("productList");
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

function findComment(v) {
	setTab('one',3,5);
	$.post("${pageContext.request.contextPath }/f/product/findComment", {"productId": "${product.id}"},
	 function(data){
	$("#productComment").empty();
	$.each(data, function(n, value) {
		$("#productComment").append('<div><dl><dd><div class="avatat"><img src="images/cpfive.jpg"/></div><p>'+n.username+'</p></dd><dt><div class="rating_title">'+
		'<span>商品总体评分：</span>	<span class="starbox s'+n.score+'"></span><font class="gray999">'+n.create_date+
		'</font></div><div class="experience">评论：'+n.content+'</div><div class="reply_content">'+
		'<p>&nbsp;&nbsp;回复&nbsp;&nbsp;：'+n.re_content+'</p><p class="gray999">'+n.update_date+'</p>	</div></dt></dl></div>');
	}); 
});
};
//差评
function badComment(v ) {
	
	
}
//中评
function badComment(v ) {
	
	
}




</script>
</head>

<body>
<%@ include file="../../include/product_top.jsp" %>
<!-- 商品列表 -->
<div class="one_floor w1200">
<!-- 商品展示 -->
<div class="products_detail">
<div class="title">
	<a href="#">首页</a> >
	<c:if test="${not empty(categoryCascadeList)}">
		<c:forEach items="${categoryCascadeList}" var="item" varStatus="status">
			<a href="${pageContext.request.contextPath }/f/product/list?productCategoryId=${item.id}">${item.text}</a>
			<c:if test="${!status.last}">
				 > 
			</c:if>
		</c:forEach>
	</c:if>
	<!-- <a href="#">首页</a> > 
	<a href="#">手机</a> > 
	<a href="#">华为（HUAWEI）</a> > 
	<a href="#">华为荣耀6</a>
	 -->
</div>
<div class="product_intro">
<!-- 商品名称 -->
<div class="name">
<h1>${product.name }</h1>
<c:if test="product.fullName!=null">
	<strong>${product.fullName }</strong>
</c:if>
</div>
<!-- 商品详情 -->
<div class="summary_box">
<ul class="summary">
<li>
<div class="dt">销&nbsp;&nbsp;售&nbsp;&nbsp;价：</div>
<div class="dd">
<strong class="p_price">￥${product.price }</strong>
<!-- <a class="blue" href="#">(降价通知)</a> -->
</div>
</li>
<li>
<div class="dt">商品编号：</div>
<div class="dd" id="product_id">${product.id}</div>
</li>
<c:forEach items="${product.specificationGroups }" var="specificationGoup">
<li class="choose-version">
<div class="dt">${specificationGoup.name}：</div>
<div class="dd">
<c:forEach items="${specificationGoup.productSpecificationList}" var="productSpecification">
	<div class="item disabled"><b></b><a href="javascript:void(0);" onclick="selectSpecification(this);" value="${productSpecification.id}">${productSpecification.name}</a></div>
</c:forEach>
</div>
</li>
</c:forEach>
<li class="choose-amount">
<div class="dt">购买数量：</div>
<div class="dd">
<div class="tbody_quantity" id="padding_none">
<a id="minus_btn" class="tbody_cout_btn leson lesonoff" href="javascript:void(0);" onclick="deleteCount()"></a>
<input id="buy_count" name="buyCount" type="text" value="1" />
<a class="tbody_cout_btn add " href="javascript:void(0);" onclick="addCount()"></a>
</div>
</div>
</li>
<li class="buy_btnbox">
<!-- a class="nowbuy" href="javascript:void(0);">立即购买</a -->
<a class="catbtn" href="javascript:void(0);" onclick="addCart();"><s></s>加入购物车</a>
</li>
</ul>
<!-- 商家信息 -->
<div class="business">
<div class="bus_title font14">卖家：<a class="blue" href="#">嘉宝易汇通</a><s></s></div>
<div class="bus_content">
<div class="rating">
<span>综合评分：</span>
<span class="starbox s5"></span>
<span class="orange font14">5.0分</span>
</div>
<dl class="seller_inf">
<dt>公司名称：</dt>
<dd>深圳嘉宝易汇通发展有限公司</dd>
</dl>
<dl class="seller_inf">
<dt>所在地：</dt>
<dd>广东省 深圳市</dd>
</dl>
<dl class="seller_inf">
<dt>联系电话：</dt>
<dd>400-888-8888</dd>
</dl>
<a class="ent_shop font16" href="#">进入店铺</a>
</div>
</div>
</div>
<!-- 商品展示 -->
<div class="preview">
<div id="pro_view">
    <div id="pro_infor">
        <div id="image_box">
        <a href="${baseFilePath }/${product.image}" class="jqzoom" title=""><img src="${baseFilePath }/${product.image}"  alt="" id="current_img"></a>
        </div>  
    </div>
        <ul id="image_list" class="jcarousel-skin-tango">
            <li class="hover"><img src="${baseFilePath }/${product.image}" alt="${baseFilePath }/${product.image}" /></li>
            <c:forEach items="${product.productImageRefList}" var="ProductImageRef">
            	<li><img src="${baseFilePath }/${ProductImageRef.thumbnail}" alt="${baseFilePath }/${ProductImageRef.filePath}" /></li>
            </c:forEach>
        </ul>
</div>
</div>
</div>
</div>
<!-- 左边内容 -->
<div class="products_list_left">
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
			<a href="${pageContext.request.contextPath }/f/product/details?productId=${hotProduct.id}"><img src="${baseFilePath }/${hotProduct.image}" /></a>
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
<script type="text/javascript">
	appendProductScaned();
</script>
</div>
</div>
<!-- 右边内容 -->
<div class="products_list_right mt20">
<div class="position_title">
<ul>
<li id="one1" onclick="setTab('one',1,5)" class="hover">商品介绍</li>
<li id="one2" onclick="setTab('one',2,5)">参数及属性</li>
<li id="one3" onclick="findComment('')">商品评价</li>
<li id="one4" onclick="setTab('one',4,5)">售后保障</li>
<li id="one5" onclick="setTab('one',5,5)">商品咨询</li>
<li id="catbtnbox">加入购物车</li>
</ul>
</div>
<!-- 悬浮在顶部 -->
<script type="text/javascript"> 
$.fn.smartFloat = function() {
	var position = function(element) {
		var top = element.position().top, pos = element.css("position");
		$(window).scroll(function() {
			var scrolls = $(this).scrollTop();
			if (scrolls > top) {
				if (window.XMLHttpRequest) {
					$("#catbtnbox").show();
					element.css({
						position: "fixed",
						top: 0
					});	
				} else {
					element.css({
						top: scrolls
					});	
				}
			}else {
				$("#catbtnbox").hide();
				element.css({
					position: pos,
					top: top
				});	
			}
		});
};
	return $(this).each(function() {
		position($(this));						 
	});
};
//绑定
$(".position_title").smartFloat();
</script>
<div class="commodity_box">
<div id="con_one_1">
<div class="products_introduction"><span>商品特性</span><i></i></div>
<div class="products_feature" >${product.introduction }</div>
</div>
<div id="con_one_2" style="display:none">
<div class="products_property">
<!-- 包装清单 -->
<table class="mb10" cellspacing="0" cellpadding="0">
<!--基本参数 -->
<c:forEach items="${product.parametersValue }" var="item">
<table class="mb10" cellspacing="0" cellpadding="0">
	<tbody>
		<tr>
			<th colspan="3">${item.key.name}</th>
		</tr>
		<c:forEach items="${item.value }" var="cItem">
		<tr>
			<td width="19%">${cItem.key.name }：</td>
			<td width="72%">${cItem.value}</td>
		</tr>
		</c:forEach>
</tbody>
</table>
</c:forEach>
<!-- 属性-->
<table class="mb10" cellspacing="0" cellpadding="0">
<tbody><tr><th colspan="3">商品属性</th></tr>
<c:forEach items="${product.propertyValuesMap }" var="item">
<tr>
<td width="19%">${item.key.name }：</td>
<td width="72%">${item.value.name }</td>
</tr>
</c:forEach>
</tbody>
</table>
</table>
</div>
</div>
<div id="con_one_3" style="display:none">
<div class="products_rating">
<span>商品总体评分：</span>
<span class="starbox s5"></span>
<span class="orange font14">5.0分</span>
</div>
<div class="products_evaluate">
<ul>
<li id="two1" onclick="setTab('two',1,4)" class="hover">全部（120）</li>
<li id="two2" onclick="setTab('two',2,4)">好评（100）</li>
<li id="two3" onclick="setTab('two',3,4)">中评（18）</li>
<li id="two4" onclick="setTab('two',4,4)">差评（2）</li>
</ul>
</div>

<div class="evaluate_content"  id="productComment">

</div>
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
<div id="con_one_4" style="display:none">
<div class="products_caption">
<p>本产品全国联保，享受三包服务，质保期为：一年质保<br/>如因质量问题或故障，凭厂商维修中心或特约维修点的质量检测证明，享受7日内退货，15日内换货，15日以上在质保期内享受免费保修等三包服务！</p>
<p class="font16">服务承诺:</p>
<p>网站所售产品均为厂商正品，如有任何问题可与我们客服人员联系，我们会在第一时间跟您沟通处理。我们将争取以最低的价格、最优的服务来满足您最大的需求。开箱验货：签收时在付款后与配送人员当面核对：商品及配件、应付金额、商品数量及发货清单、发票（如有）、赠品（如有）等；如存在包装破损、商品错误、商品短缺、商品存在质量问题等影响签收的因素，请您可以拒收全部或部分商品，相关的赠品，配件或捆绑商品应一起当场拒收；为了保护您的权益，建议您尽量不要委托他人代为签收；如由他人代为签收商品而没有在配送人员在场的情况下验货，则视为您所订购商品的包装无任何问题。</p>
<p class="font16">温馨提示:</p>
<p>由于部分商品包装更换较为频繁，因此您收到的货品有可能与图片不完全一致，请您以收到的商品实物为准，同时我们会尽量做到及时更新，由此给您带来不便多多谅解，谢谢！</p>
</div>
</div>
<div id="con_one_5" style="display:none">
<div class="products_rating">
<div class="evaluate_btnbox">
<span><a class="blue_btn evaluate_btn" href="#">发表咨询</a></span>
</div>
<span>提示:因厂家更改产品包装、产地或者更换随机附件等没有任何提前通知，且每位咨询者购买情况、提问时间等不同，为此以下回复信息仅供参考！</span>
</div>
<div class="products_evaluate">
<ul>
<li id="tre1" onclick="setTab('tre',1,4)" class="hover">全部咨询(1000)</li>
<li id="tre2" onclick="setTab('tre',2,4)">商品咨询(10)</li>
<li id="tre3" onclick="setTab('tre',3,4)">配送/支付(0)</li>
<li id="tre4" onclick="setTab('tre',4,4)">发票/保修(0)</li>
</ul>
</div>
<div class="evaluate_content">
<div id="con_tre_1">
<dl>
<dd>
<div class="avatat"><img src="images/cpfive.jpg"/></div>
<p>156*****31</p>
</dd>
<dt>
<div class="experience">
<h3>[商品咨询]</h3>
<p>使用心得：外形漂亮设计合理 目前看来就是接听声音有点小（我已调到最大） 性价比高 没等iphone6是正确滴！物流配送超快第一次在e-life买东西很满意，
下次还会继续光顾~~</p>
<p class="gray999">2014-09-01 13:44:09</p>
</div>
<div class="reply_content">
<p><b>e-life回复：</b>第一次在苏宁买东西。手机刚到手就发现降了100多，而且增的蓝牙耳机到现在也没收到，客服说给我反馈一下到现在也没有回 音。不知是否可以退货。当时就是相信苏宁自营的信誉。1999元钱买的.商家同款C199才1700多本来就是冲自营的信誉去的，郁闷晕看来苏宁除了商品价格高以外没有什么好的地方，以后还是。。。。。</p>
<p class="gray999">2014-09-01 13:44:09</p>
</div>
</dt>
</dl>
</div>
<div id="con_tre_2" style="display:none">
<dl>
<dd>
<div class="avatat"><img src="images/cpfive.jpg"/></div>
<p>156*****31</p>
</dd>
<dt>
<div class="experience">
<h3>[商品咨询]</h3>
<p>使用心得：外形漂亮设计合理 目前看来就是接听声音有点小（我已调到最大） 性价比高 没等iphone6是正确滴！物流配送超快第一次在e-life买东西很满意，
下次还会继续光顾~~</p>
<p class="gray999">2014-09-01 13:44:09</p>
</div>
<div class="reply_content">
<p><b>e-life回复：</b>第一次在苏宁买东西。手机刚到手就发现降了100多，而且增的蓝牙耳机到现在也没收到，客服说给我反馈一下到现在也没有回 音。不知是否可以退货。当时就是相信苏宁自营的信誉。1999元钱买的.商家同款C199才1700多本来就是冲自营的信誉去的，郁闷晕看来苏宁除了商品价格高以外没有什么好的地方，以后还是。。。。。</p>
<p class="gray999">2014-09-01 13:44:09</p>
</div>
</dt>
</dl>
</div>
<div id="con_tre_3" style="display:none">
<dl>
<dd>
<div class="avatat"><img src="images/cpfive.jpg"/></div>
<p>156*****31</p>
</dd>
<dt>
<div class="experience">
<h3>[商品咨询]</h3>
<p>使用心得：外形漂亮设计合理 目前看来就是接听声音有点小（我已调到最大） 性价比高 没等iphone6是正确滴！物流配送超快第一次在e-life买东西很满意，
下次还会继续光顾~~</p>
<p class="gray999">2014-09-01 13:44:09</p>
</div>
<div class="reply_content">
<p><b>e-life回复：</b>第一次在苏宁买东西。手机刚到手就发现降了100多，而且增的蓝牙耳机到现在也没收到，客服说给我反馈一下到现在也没有回 音。不知是否可以退货。当时就是相信苏宁自营的信誉。1999元钱买的.商家同款C199才1700多本来就是冲自营的信誉去的，郁闷晕看来苏宁除了商品价格高以外没有什么好的地方，以后还是。。。。。</p>
<p class="gray999">2014-09-01 13:44:09</p>
</div>
</dt>
</dl>
</div>
<div id="con_tre_4" style="display:none">
<dl>
<dd>
<div class="avatat"><img src="images/cpfive.jpg"/></div>
<p>156*****31</p>
</dd>
<dt>
<div class="experience">
<h3>[商品咨询]</h3>
<p>使用心得：外形漂亮设计合理 目前看来就是接听声音有点小（我已调到最大） 性价比高 没等iphone6是正确滴！物流配送超快第一次在e-life买东西很满意，
下次还会继续光顾~~</p>
<p class="gray999">2014-09-01 13:44:09</p>
</div>
<div class="reply_content">
<p><b>e-life回复：</b>第一次在苏宁买东西。手机刚到手就发现降了100多，而且增的蓝牙耳机到现在也没收到，客服说给我反馈一下到现在也没有回 音。不知是否可以退货。当时就是相信苏宁自营的信誉。1999元钱买的.商家同款C199才1700多本来就是冲自营的信誉去的，郁闷晕看来苏宁除了商品价格高以外没有什么好的地方，以后还是。。。。。</p>
<p class="gray999">2014-09-01 13:44:09</p>
</div>
</dt>
</dl>
</div>
</div>
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
</div>
</div>
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
