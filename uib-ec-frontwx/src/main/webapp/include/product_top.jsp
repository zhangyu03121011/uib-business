<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	function searchProduct(){
		document.getElementById("searchForm").submit();
	}
	$(function(){
		$.ajax({
			url:"${pageContext.request.contextPath  }/f/cart/list_json",
			type: "GET",
			dataType: "json",
			success: function(cart){
				var $cart_menu_bd = $("#cart-menu-bd");
				var cart_item_html = "<dl>";
				if(cart!=null&&cart.cartItems!=null){
					$("#font_of_cart_count").text(cart.quantity);
					$.each(cart.cartItems,function(indec,cartItem){
						cart_item_html += "<dt><a href=\"#\"><img src=\"${UPLOAD_IMAGE_PATH}"+(cartItem.product.image==null?"123":cartItem.product.image)+"\" /></a>"+
						  "<p>"+
						  "<a class=\"tit\" href=\"../product/details?productId="+(cartItem.product.id)+"\">"+ (cartItem.product.name)+
			              "</a> <span>¥"+(cartItem.product.price)+"</span>"+"x"+(cartItem.quantity)+" <a class=\"del\" href=\"javascript:void(0);\" cartItemId=\""+cartItem.id+"\" data-id=\""+cartItem.product.id+"\">删除</a>"+
	    				  "</p>"+
	  					  "</dt>";
					});
				}else{
					$("#font_of_cart_count").text(0);
				}
				cart_item_html += "<dd><em>购物车里还有"+(cart!=null?cart.quantity:0)+"件商品</em> <a href=\"../cart/list\">去购物车并结算</a></dd>";
				cart_item_html += "</dl>";
				$cart_menu_bd.append(cart_item_html);
			}
		});
	});
</script>
<script type="text/javascript"
	src="${pageContext.request.contextPath  }/static/js/jquery-1.8.2.min.js" ></script>
<script type="text/javascript">
var jq182 = jQuery.noConflict(true);
jq182(function(){
	jq182("#cart-menu-bd").delegate(".del","click",function(){
		var $this = jq182(this);
		var cartItemId = $this.attr("cartItemId");
		var dataId = $this.attr("data-id");
		jq182.ajax({
			url:'${pageContext.request.contextPath  }/f/cart/delete_by_Id?id='+cartItemId+'&productId='+dataId,
			type: "GET",
			dataType: "json",
			success: function(data){
				if(data.status=="success"){
					var $cart_menu_bd = jq182("#cart-menu-bd");
					$cart_menu_bd.empty();
					var cart_item_html = "<dl>";
					var cart = data.cart;
					if(cart!=null&&cart.cartItems!=null){
						jq182("#font_of_cart_count").text(cart.quantity);
						jq182.each(cart.cartItems,function(indec,cartItem){
							cart_item_html += "<dt><a href=\"#\"><img src=\"${UPLOAD_IMAGE_PATH}"+(cartItem.product.image==null?"123":cartItem.product.image)+"\" /></a>"+
							  "<p>"+
							  "<a class=\"tit\" href=\"../product/details?productId="+(cartItem.product.id)+"\">"+ (cartItem.product.name)+
				              "</a> <span>¥"+(cartItem.product.price)+"</span>"+"x"+(cartItem.quantity)+" <a class=\"del\" href=\"javascript:void(0);\" cartItemId=\""+cartItem.id+"\" data-id=\""+cartItem.product.id+"\">删除</a>"+
		    				  "</p>"+
		  					  "</dt>";
						});
					}else{
						$("#font_of_cart_count").text(0);
					}
					cart_item_html += "<dd><em>购物车里还有"+(cart!=null?cart.quantity:0)+"件商品</em> <a href=\"../cart/list\">去购物车并结算</a></dd>";
					cart_item_html += "</dl>";
					$cart_menu_bd.append(cart_item_html);
				}
				$.jBox.tip(data.message);
			}
		});
	});
});
</script>
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
									href="${pageContext.request.contextPath  }/f/member/myOrder">我的订单</a></li>
								<%-- <li><a href="${pageContext.request.contextPath  }/f/member/myAddress">地址管理</a></li> --%>
							</ul>
						</div>
					</div>
				</div>
			</li>
			<li class="cart menu-item">
				<div class="menu">
					<span class="menu-hd"><s></s><a href="${pageContext.request.contextPath  }/f/cart/list">购物车(<font id="font_of_cart_count">0</font>)</a><b></b></span>
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
<div class="logoWrap w1200">
	<div class="life_logo">
		<img src="${pageContext.request.contextPath  }/static/images/logo.jpg" />
	</div>
	<div class="life_search">
		<div class="life_searchbox">
			<ul>
					<form name="searchForm" id="searchForm" action="${pageContext.request.contextPath  }/f/product/search" method="post">
						<li><input class="life_searchinput" name="productName" type="text" /></li>
						<li><a class="lift_searchbtn" href="#" id="searchBtn" onclick="searchProduct();">搜索</a></li>
					</form>
			</ul>
			<div class="life_searchhot">
				热门搜索：<a href="#">图书</a> <a href="#">美素</a> <a href="#">美赞臣</a> <a
					href="#">衬衫</a>
			</div>
		</div>
	</div>
</div>
<!-- 搜索栏结束 -->
<!-- 菜单栏开始 -->
<div class="navWrap">
	<div class="navsort w1200">
		<div class='allsort'>
			<div class='mt'>
				<s></s><font>全部分类</font>
				<div class='extra'>&nbsp;</div>
			</div>
			<div class='mc'>
				<c:if test="${not empty(categoryList)}">
					<c:forEach items="${categoryList }" var="item" varStatus="idx">
						<c:choose>
							<c:when test="${idx.index == 0 }">
								<div class='item fore'>
									<span><h3>
											<a
												href="${pageContext.request.contextPath }/f/product/list?productCategoryId=${item.id}">${item.text }</a>
										</h3>
										<s></s></span>
									<div class='i-mc'>
										<div class='close'
											onclick="$(this).parent().parent().removeClass('hover')"></div>
										<div class='subitem'>
											<c:if test="${not empty(item.children)}">
												<c:forEach items="${item.children }" var="childrenItem">
													<dl class='fore'>
														<dt>
															<a
																href="${pageContext.request.contextPath }/f/product/list?productCategoryId=${childrenItem.id}">${childrenItem.text}</a>
														</dt>
														<c:if test="${not empty(childrenItem.children)}">
															<dd>
																<c:forEach items="${childrenItem.children }"
																	var="ccItem">
																	<em><a
																		href='${pageContext.request.contextPath }/f/product/list?productCategoryId=${ccItem.id}'>${ccItem.text}</a></em>
																</c:forEach>
															</dd>
														</c:if>
													</dl>
												</c:forEach>
											</c:if>


										</div>

									</div>
								</div>
							</c:when>
							<c:otherwise>
								<div class='item'>
									<span><h3>
											<a href="${pageContext.request.contextPath }/f/product/list?productCategoryId=${item.id}">${item.text }</a>
										</h3>
										<s></s></span>
									<div class='i-mc'>
										<div class='close'
											onclick="$(this).parent().parent().removeClass('hover')"></div>
										<div class='subitem'>
											<c:if test="${not empty(item.children)}">
												<c:forEach items="${item.children }" var="childrenItem">
													<dl class='fore'>
														<dt>
															<a
																href="${pageContext.request.contextPath }/f/product/list?productCategoryId=${childrenItem.id}">${childrenItem.text}</a>
														</dt>
														<c:if test="${not empty(childrenItem.children)}">
															<dd>
																<c:forEach items="${childrenItem.children }"
																	var="ccItem">
																	<em><a
																		href='${pageContext.request.contextPath }/f/product/list?productCategoryId=${ccItem.id}'>${ccItem.text}</a></em>
																</c:forEach>
															</dd>
														</c:if>
													</dl>
												</c:forEach>
											</c:if>


										</div>

									</div>
								</div>
							</c:otherwise>
						</c:choose>

					</c:forEach>
				</c:if>
			</div>
		</div>
		<div class="commitment_box">
			<s></s>
			<p>正品保证</p>
			<b></b>
			<p>七天无理由</p>
		</div>
		<ul>
			<c:if test="${not empty(navigationList)}">
				 <c:forEach var="item" items="${navigationList}">
				 		<li>
					 		<c:choose>
					 			<c:when test="${item.name eq '首页' }">
					 				<a href="${pageContext.request.contextPath }/f/index">${item.name}</a>
					 			</c:when>
					 			<c:when test="${item.name eq '个护化妆' }">
					 				<a href="${pageContext.request.contextPath }${item.url }">${item.name }</a>
					 			</c:when>
					 			<c:otherwise>
					 				<!--<a href="${pageContext.request.contextPath }${item.url }">${item.name }</a>-->
					 				 <a href="javascript:alert('敬请期待');">${item.name}</a>
					 			</c:otherwise>
					 		</c:choose>
				 		</li>
				 </c:forEach>	
			</c:if>
		</ul>
	</div>
</div>
<script type="text/javascript">
	$(".allsort").hoverForIE6({
		current : "allsorthover",
		delay : 200
	});
	$(".allsort .item").hoverForIE6({
		delay : 150
	});
</script>
<!-- 菜单栏结束 -->