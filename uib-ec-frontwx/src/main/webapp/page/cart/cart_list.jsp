<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page language="java" contentType="text/html;charset=UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>e-life.me</title>
<link href="${pageContext.request.contextPath  }/static/css/common.css"
	rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath  }/static/css/orders.css"
	rel="stylesheet" type="text/css" />
<script type="text/javascript"
	src="${pageContext.request.contextPath  }/static/js/jquery.js"></script>
	<link href="${pageContext.request.contextPath  }/static/jquery-jbox/2.3/Skins/Bootstrap/jbox.min.css" rel="stylesheet" />
<script src="${pageContext.request.contextPath  }/static/jquery-jbox/2.3/jquery.jBox-2.3.min.js" type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function() {
		var $product_count= $("#product_count");
		var $effective_price = $("#effective_price");
		$("#deleteOperation").click(function() {

		});
		$("#select_all1,#select_all2").click(function() {
			var checked_value = $(this).attr("checked");
			var count = 0;
			var account = 0;
			var sign_flag = "￥";
			$('.tbody_cell input[type="checkbox"]').each(function() {
				var $this = $(this);
				$this.attr("checked",checked_value);
				if($this.attr("checked")){
					var $tbody_cell = $this.parents(".tbody_cell");
					count += parseInt($tbody_cell.find(".tbody_quantity input").val());
					var span_value = $tbody_cell.find(".tbody_price .red").text();
					account += parseFloat(span_value.substring(1));
					sign_flag = span_value.substring(0,1);
				}
			});
			$product_count.text(count);
			$product_count.attr("value",count);
			$effective_price.text(sign_flag+account);
			$effective_price.attr("value",account);
		});
		
		$('.tbody_cell input[type="checkbox"]').click(function(){
			var oldCount = parseInt($product_count.attr("value"));
			var oldAccount = parseFloat($effective_price.attr("value"));
			var $this = $(this);
			var $tbody_cell = $this.parents(".tbody_cell");
			var unitCount = parseInt($tbody_cell.find(".tbody_quantity input").val());
			var unitAccount = parseFloat($tbody_cell.find(".tbody_price .red").text().substring(1));
			var sign_flag = $effective_price.text().substring(0,1);
			if($this.attr("checked")){
				$product_count.text(oldCount+unitCount);
				$product_count.attr("value",oldCount+unitCount);
				$effective_price.text(sign_flag+(unitAccount+oldAccount));
				$effective_price.attr("value",unitAccount+oldAccount);
			}else{
				$product_count.text(oldCount-unitCount);
				$product_count.attr("value",oldCount-unitCount);
				$effective_price.text(sign_flag+(oldAccount-unitAccount));
				$effective_price.attr("value",oldAccount-unitAccount);
			}
		});

		$(".tbody_cell .tbody_quantity").each(function(){
			var cartItemId = $(this).siblings(".tbody_operation").find("a").attr("value");
			var unit_check_box = $(this).siblings(".tbody_choice").find("input[type='checkbox']");
			$(this).find("a:first").click(function() {
				var $this = $(this);
				var oldCount = parseInt($product_count.attr("value"));
				var oldAccount = parseFloat($effective_price.attr("value"));
				var valueObj = $this.next();
				var value = valueObj.val();
				if (value < 2) {
				} else {
					$.ajax({
						url:"../cart/update_count?cartItemId="+cartItemId+"&quantity="+(value-1),
						type: "GET",
						dataType: "json",
						success: function(message){
							valueObj.val(--value);
							if (value < 2) {
								$this.attr("class", "tbody_cout_btn leson lesonoff")
							}
							var $subTotal = $this.parent().next().find("span");
							var sign_flag = $subTotal.text().trim().substring(0,1);
							var $price_node = $this.parent().prev();
							var unitPrice = parseFloat($price_node.text().substring(1));
							if(unit_check_box.attr("checked")){
								$product_count.text(oldCount-1);
								$product_count.attr("value",oldCount-1);
								$effective_price.text(sign_flag+(oldAccount-unitPrice));
								$effective_price.attr("value",oldAccount-unitPrice);
							}
							$subTotal.text(sign_flag+(unitPrice*value));
						}
					});
				}
			});
			$(this).find("a:last").click(function(){
				var $this = $(this);
				var oldCount = parseInt($product_count.attr("value"));
				var oldAccount = parseFloat($effective_price.attr("value"));
				var valueObj = $this.siblings("input");
				var value = valueObj.val();
				$.ajax({
					url:"../cart/update_count?productId="+productId+"&quantity="+(value+1),
					type: "GET",
					dataType: "json",
					success: function(message){
						valueObj.val(++value);
						if (value > 1) {
							$this.siblings("a:first").attr("class", "tbody_cout_btn leson leson")
						}
						var $subTotal = $this.parent().next().find("span");
						var sign_flag = $subTotal.text().trim().substring(0,1);
						var $price_node = $this.parent().prev();
						var unitPrice = parseFloat($price_node.text().substring(1));
						if(unit_check_box.attr("checked")){
							$product_count.text(oldCount+1);
							$product_count.attr("value",oldCount+1);
							$effective_price.text(sign_flag+(oldAccount+unitPrice));
							$effective_price.attr("value",oldAccount+unitPrice);
						}
						$subTotal.text(sign_flag+(unitPrice*value));
					}
				});
			});
		});
		$(".tbody_operation .blue").click(function(){
			var $this = $(this);
			var oldCount = parseInt($product_count.attr("value"));
			var oldAccount = parseFloat($effective_price.attr("value"));
			var id = $this.attr("value");
			var $tbody_cell = $this.parents(".tbody_cell");
			var productId = $tbody_cell.attr("value");
			var unitCount = parseInt($tbody_cell.find(".tbody_quantity input").val());
			var unitAccount = parseFloat($tbody_cell.find(".tbody_price .red").text().substring(1));
			var sign_flag = $effective_price.text().substring(0,1);
			$.ajax({
				url:"../cart/delete_by_Id?productId="+productId+"&id="+id,
				type: "GET",
				dataType: "json",
				success: function(message){
					$product_count.text(oldCount-unitCount);
					$product_count.attr("value",oldCount-unitCount);
					$effective_price.text(sign_flag+(oldAccount-unitAccount));
					$effective_price.attr("value",oldAccount-unitAccount);
					$this.parent().parent().remove();
					$.jBox.tip("删除商品成功");
				}
			});
		});
		
		$("#buy_btn").click(function(){
			var cartId = $(this).attr("cartId");
			var pids = '';
			$('.tbody_cell input:checked').each(function() {
				pids+=$(this).parents(".tbody_cell").attr("value")+",";
			});
			if(pids==''){
				alert("请选择商品!");
				return false;
			}
			pids = pids.substring(0,pids.length-1);
			window.location.href="../order/form?cartId="+cartId+"&pids="+pids;
		});
	});
	$(function(){
		$.ajax({
			url:"${pageContext.request.contextPath  }/f/cart/list_json",
			type: "GET",
			dataType: "json",
			success: function(cart){
				var $cart_menu_bd = $("#cart-menu-bd");
				var cart_item_html = "<dl>";
				if(cart.cartItems!=null){
					$("#font_of_cart_count").text(cart.quantity);
					$.each(cart.cartItems,function(indec,cartItem){
						cart_item_html += "<dt><a href=\"#\"><img src=\"${UPLOAD_IMAGE_PATH}"+(cartItem.product.image==null?"123":cartItem.product.image)+"\" /></a>"+
						  "<p>"+
						  "<a class=\"tit\" href=\"../product/details?productId="+(cartItem.product.id)+"\">"+ (cartItem.product.name)+
			              "</a> <span>¥"+(cartItem.product.price)+"</span>"+"x"+(cartItem.quantity)+" <a class=\"del\" href=\"../cart/delete_by_Id?productId="+(cartItem.product.id)+"\">删除</a>"+
	    				  "</p>"+
	  					  "</dt>";
					});
				}
				cart_item_html += "<dd><em>购物车里还有"+(cart.quantity)+"件商品</em> <a href=\"../cart/list\">去购物车并结算</a></dd>";
				cart_item_html += "</dl>";
				$cart_menu_bd.append(cart_item_html);
			}
		})
	});
</script>
</head>

<body>
	<!-- 顶部栏目开始 -->
	<div class="topWrap">
		<div class="site-nav w1200">
			<div class="tool_link">
				<c:choose>
					<c:when test="${not empty(member)}">
							${member.username},欢迎光临初云汇商城！   <a
							href="${pageContext.request.contextPath  }/f/logout">退出</a>
					</c:when>
					<c:otherwise>
							欢迎光临初云汇商城！&nbsp;<a
							href="${pageContext.request.contextPath  }/f/login">登录</a>&nbsp;或&nbsp;<a
							href="${pageContext.request.contextPath  }/f/reg/registerView">免费注册</a>
					</c:otherwise>
				</c:choose>
			</div>
			<ul class="quick-menu">
				<li class="user menu-item">
					<div class="menu">
						<span class="menu-hd"><s></s>我的账户<b></b></span>
						<div class="menu-bd">
							<div class="menu-bd-panel">
								<ul>
									<li><a
										href="${pageContext.request.contextPath}/f/member/myOrder">我的订单</a></li>
									<!-- <li><a href="#">地址管理</a></li> -->
								</ul>
							</div>
						</div>
					</div>
				</li>
				<li class="cart menu-item">
					<div class="menu">
						<span class="menu-hd"><s></s><a
							href="${pageContext.request.contextPath  }/f/cart/list">购物车(<font id="font_of_cart_count">0</font>)
						</a><b></b></span>
						<div class="menu-bd" id="cart-menu-bd">
						</div>
					</div>
				</li>

				<li class="language menu-item">
					<div class="menu">
						<a class="menu-hd" href="#">语言选择<b></b></a>
						<div class="menu-bd">
							<div class="menu-bd-panel">
								<a href="#">简体中文</a> <a href="#">繁體中文</a>
							</div>
						</div>
					</div>
				</li>
				<li class="services menu-item">
					<div class="menu">
						<a class="menu-hd" href="#">网站导航<b></b></a>
						<div class="menu-bd fontbox">
							<div class="menu-bd-panel">
								<dl>
									<dt>
										<a href="#">购物指南</a>
										<dd>
											<!-- <a href="#">购物流程</a>  -->
											<!-- <a href="#">会员等级</a>  -->
											<a href="${pageContext.request.contextPath  }/f/reg/registerView">新用户注册</a>
											<!-- <a href="#">预存款支付</a>  -->
											<!-- <a href="#">退换货政策</a> -->
										</dd>
								</dl>
								<!-- <dl>
									<dt>
										<a href="#">关于我们</a>
										<dd>
											<a href="#">公司简介</a> <a href="#">联系我们</a>
										</dd>
								</dl>
								<dl class="last">
									<dt>
										<a href="#">帮助中心</a>
										<dd>
											<a href="#">交易安全</a> <a href="#">维权中心</a>
										</dd>
								</dl> -->
							</div>

						</div>
					</div>
				</li>
			</UL>
		</div>
	</div>
	<!-- 顶部栏目结束 -->
	<!-- 搜索栏开始 -->
	<div class="logoWrap w980">
		<div class="life_logo">
			<img
				src="${pageContext.request.contextPath  }/static/images/life_logo.png" />
		</div>
		<div class="shop_titleright">
			<ul>
				<li class="lione white">查看购物车</li>
				<li class="litwo gray666">订单信息</li>
				<li class="litre gray666">完成订单</li>
			</ul>
		</div>
	</div>
	<!-- 搜索栏结束 -->
	<!-- 购物车 -->
	<div class="shop_cart">
		<div class="shop_content">
			<div class="cart_thead">
				<ul>
					<li class="thead_cell thead_c1"><input name="" type="checkbox"
						value="" id="select_all1" /> 全选</li>
					<li class="thead_cell thead_c2">商品</li>
					<li class="thead_cell thead_c3">原价</li>
					<li class="thead_cell thead_c3">现价</li>
					<li class="thead_cell thead_c4">数量</li>
					<li class="thead_cell thead_c3">小计</li>
					<li class="thead_cell thead_c3">操作</li>
				</ul>
			</div>
			<!-- 商品内容区begin -->
			<c:forEach items="${cart.cartItems }" var="cartItem">
				<div class="tbody_cell" value="${cartItem.product.id }">
					<div class="tbody_choice">
						<input name="" type="checkbox" value="${cartItem.id}" checked="true"/>
					</div>
					<div class="tbody_goods">
						<a href="#"><img src="${UPLOAD_IMAGE_PATH}${cartItem.product.image}" /></a>
						<p>
							<a herf="${UPLOAD_IMAGE_PATH}${cartItem.product.id}">${cartItem.product.name }</a>
						</p>
					</div>
					<div class="tbody_price">
						<font>¥${cartItem.product.price}</font>
					</div>
					<div class="tbody_price">¥${cartItem.product.price}</div>
					<div class="tbody_quantity">
						<c:choose>
							<c:when test="${cartItem.quantity>1 }">
								<a class="tbody_cout_btn leson leson" href="#"></a>
							</c:when>
							<c:otherwise>
								<a class="tbody_cout_btn leson lesonoff" href="#"></a>
							</c:otherwise>
						</c:choose>
						<input name="" type="text" value="${cartItem.quantity}" /> 
						<a class="tbody_cout_btn add " href="#"></a>
					</div>
					<div class="tbody_price">
						<span class="red">¥${cartItem.subtotal}</span>
					</div>
					<div class="tbody_operation">
						<a class="blue" href="javascript:void(0);" value="${cartItem.id}">删除</a>
					</div>
				</div>
			</c:forEach>
			<!-- 商品内容区off -->
			<div class="tbottom">
				<ul>
					<li class="tbottom_cell tbottom_c1"><input name=""
						type="checkbox" value="" id="select_all2" /> 全选</li>
					<li class="tbottom_delete"><a href="javascript:void(0);"
						id="deleteOperation">删除选中的商品</a></li>
					<li class="tbottom_return"><a
						href="${pageContext.request.contextPath }">返回继续购物</a></li>
					<li class="tbottom_details"><span>已经选<font class="red"
							id="product_count" value="${cart.quantity }">${cart.quantity }</font>件商品
					</span><span>运费：<font class="red">¥0.00</font></span><span>商品金额：<font
							class="red" id="effective_price" value="${cart.effectivePrice}">¥${cart.effectivePrice}</font></span></li>
				</ul>
			</div>
		</div>
		<div class="tbottom_btn">
			<a class="white" href="javascript:void(0);" id="buy_btn" cartId="${cart.id}">立即购买</a>
		</div>
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
				<a href=""><img src="${pageContext.request.contextPath  }/static/images/buy-security.jpg" /></a>
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
</body>
</html>
