<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>e-life.me</title>
<link href="${pageContext.request.contextPath  }/static/css/common.css"
	rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath  }/static/css/orders.css"
	rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath  }/static/css/user.css" rel="stylesheet" type="text/css" />	
<script type="text/javascript" src="${pageContext.request.contextPath  }/static/js/jquery-1.8.2.min.js" ></script>
<link href="${pageContext.request.contextPath}/static/jquery-jbox/2.3/Skins/Bootstrap/jbox.min.css" rel="stylesheet" />
<script src="${pageContext.request.contextPath}/static/jquery-jbox/2.3/jquery.jBox-2.3.min.js" type="text/javascript"></script>
	<style type="text/css">
		.address_pop_content .actionbox button{
		border-top-width: 0px;
		border-left-width: 0px;
		border-right-width: 0px;
		border-bottom-width: 0px;
		float: left;
		}
	.address_con .isDefault{
	  margin: 0 0 0 10px;
	  font-size: 12px;
	  background: #ffaa45;
	  padding: 2px;
	  color: #fff;
	  font-weight: 400;
	  display: inline;
	  }
	.theme-popover-delete{z-index:999999;position:fixed;top:63%;left:63%;width:350px;height:160px;margin:-180px 0 0 -280px;border-radius:5px;border:solid 2px #666;background-color:#fff;display:none;box-shadow: 0 0 10px #666;}
	.theme-poptit-delete{border-bottom:1px solid #ddd;padding:10px;position: relative;background:#1c547d}
	.theme-poptit-delete h3{color:#fff}
	.theme-poptit-delete .close{float:right;color:#fff;padding:5px;margin:-2px -5px -5px;font:bold 14px/14px simsun;text-shadow:0 1px 0 #ddd}
	.theme-poptit-delete .close:hover{color:#fff;}
	.theme-popover-delete .address_pop_content .actionbox{padding:20px 0 10px 63px}
	.theme-popover-delete .address_pop_content .actionbox .confirm_btn{width:90px;height:35px;line-height:35px}
	.theme-popover-delete .address_pop_content .actionbox .cancel_btn{width:90px;height:35px;line-height:35px;margin-left:10px}
	</style>
<script type="text/javascript">
var areaTypeArray = new Array("PROVINCE","CITY","AREA");
var baseFilePath = "${baseFilePath }";
var default_check_box_update = false;
$(document).ready(function() {
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
					cart_item_html += "<dt><a href=\"#\"><img src=\""+baseFilePath+"\\"+(cartItem.product.image==null?"123":cartItem.product.image)+"\" /></a>"+
					  "<p>"+
					  "<a class=\"tit\" href=\"../product/details?productId="+(cartItem.product.id)+"\">"+ (cartItem.product.name)+
		              "</a> <span>¥"+(cartItem.product.price)+"</span>"+"x"+(cartItem.quantity)+" <a class=\"del\" href=\"javascript:void(0);\" cartId=\""+cartItem.id+"\" data-id=\""+cartItem.product.id+"\">删除</a>"+
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
		getProvinceAdd(1);
		$('#new_address_radio').click(function(){
			$('.theme-popover-mask').fadeIn(100);
			$('.add .theme-popover').slideDown(200);
		});
		$(".order_address_editor").find("a:contains('修改')").click(function(){
			$('.theme-popover-mask').fadeIn(100);
			$('.update .theme-popover').slideDown(200);
			$.ajax( {
				type :'post',
				url:'${pageContext.request.contextPath}/f/member/updateById?id='+ $(this).attr('myid'),
				dataType:'json',
				success : function(result) {
					console.info(result);
					$(".update #id").val(result.id);
					$(".update #consignee").val(result.consignee);
					$(".update #address").val(result.address);
					$(".update #area").val(result.area);
					$(".update #phone").val(result.phone);
					$(".update #isDefault").val(result.isDefault);
					default_check_box_update = result.isDefault;
					var codes = $(".update #area").val().split(',');
					getProvinceUpdate(1);
					if(codes!=null){
						getCityUpdate(codes[0],true);
						getAreaUpdate(codes[1],true);
					}
					$(".update #selarea").change(function(){
						var provinceCode = $(".update #selprovince").val();
						var cityCode = $(".update #selcity").val();
						var areaCode = $(".update #selarea").val();
						var provinceText = $(".update #selprovince").find("option:selected").text(); 
						var cityText = $(".update #selcity").find("option:selected").text(); 
						var areaText = $(".update #selarea").find("option:selected").text(); 
						$(".update #areaName").val(provinceText+cityText+areaText);
						$(".update #area").val(provinceCode+","+cityCode+","+areaCode);
					});
					
				}
			});
			
		});
		$('.order_address_editor').find("a:last").click(function(){
			$('.theme-popover-mask').fadeIn(100);
			$('.delete .theme-popover-delete').slideDown(200);
			myid=$(this).attr('myid');
		});
		$('.theme-popover .close').click(function(){
			$('.theme-popover-mask').fadeOut(100);
			$('.theme-popover').slideUp(200);
		});
		$("#receiver_id_input").val($("#id_of_select_address .address_selected input:radio:checked").parents(".order_address_info").attr("value"));
		$("#id_of_select_address .address_selected").find("input:radio").click(function(){
			var $this = $(this);
			selectRadio($this);
		});
		$(".order_address_editor").find("a:first").click(function(){
			var $this = $(this);
			selectDefualtAddress($this);
		});
		$("#new_address_radio").click(function(){
			selectRadio($(this));
		});
		
		$('.theme-popover-delete .confirm ').click(function(){
			$('.theme-popover-mask').fadeOut(100);
			$('.theme-popover-delete').slideUp(200);
			$.ajax({
				url:"${pageContext.request.contextPath}/f/member/delete_by_id?id="+myid,
				type:"GET",
				dataType : 'json',
				success : function(data) {
					var $delete_a = $(".order_address_editor").find("a:last").filter("a[myId='"+myid+"']");
					console.info($delete_a.text());
					if(data.status=="success"){
						$delete_a.parent().prev().remove();
						$delete_a.parent().remove();
					}
				}
			});
		});
		
		$("#new_default_radio").click(function(){
			var $this = $(this);
			$this.attr("checked","checked");
			$("#id_of_select_address .address_selected").find(".order_address_editor").find("a:first").text("设为默认");
		});
		
		$("#province_select").change(function(){
			var value = $(this).val();
			$("#area_code_string").val(value);
			ajaxForArea(value,areaTypeArray[1]);
		});
		
		$("#city_select").change(function(){
			var value = $(this).val();
			$("#area_code_string").val($("#area_code_string").val()+","+value);
			ajaxForArea(value,areaTypeArray[2]);
		});
		
		$("#area_select").change(function(){
			var value = $(this).val();
			if(value != '' && value != null){
				$("#area_code_string").val($("#area_code_string").val()+","+value);
			}
		});
		
		$("#payment_method_ul .address_selected").delegate("input:radio","click",function(){
			$("#payment_method_ul .address_selected").find("input:radio").each(function(){
				$(this).attr("checked",false);
				$(this).parent().siblings("label").find("span.c2").attr("name","");
			});
			$(this).attr("checked",true);
			$(this).parent().siblings("label").find("span.c2").attr("name","paymentMethodName");
		});
		
		$("#shipping_method_ul .address_selected").delegate("input:radio","click",function(){
			$("#shipping_method_ul .address_selected").find("input:radio").each(function(){
				$(this).attr("checked",false);
				$(this).parent().siblings("label").find("span.c2").attr("name","");
			});
			$(this).attr("checked",true);
			$(this).parent().siblings("label").find("span.c2").attr("name","shippingMethodName");
		});
		
		$("#add_form").click(function(){
			if($("input:radio[name='paymentMethod']:checked").size()==0){
				$.jBox.tip("请选择支付方式");
				return;
			};
			if($("input:radio[name='shippingMethod']:checked").size()==0){
				$.jBox.tip("请选择配送方式");
				return;
			};
		
			console.info($(this).text());
			$("#order_table_form").attr("action","${pageContext.request.contextPath}/f/order/save").submit();
		});
		var invoices_checked = false;
		$(".order_invoices input:radio").click(function(){
			var $this=$(this);
			if(invoices_checked){
				$this.attr("checked",false);
				$this.val("0");
				invoices_checked = false;
			}else{
				$this.attr("checked",true);
				$this.val("1");
				invoices_checked = true;
			}
			
		});
		$(".order_coupon_select.address_selected input:radio").click(function(){
			var $this = $(this);
			console.info($this.val());
			if($this.attr("checked")){
				var needConsumeBalance = $this.parent().siblings().find(".c2 .red").text().split("减")[0];
				var total = $(".order_total p font").text().substring(1);
				if(parseFloat(needConsumeBalance)>parseFloat(total)){
					$this.attr("checked",false);
					$(".order_coupon_add input:text").val("");
					alert("消费金额小于使用优惠券标准!");
				}else{
					$(".order_coupon_add input:text").val($this.val());
				}
			}
		});
		$("#cart-menu-bd").delegate(".del","click",function(){
			var $this = $(this);
			var cartId = $this.attr("cartId");
			var dataId = $this.attr("data-id");
			$.ajax({
				url:'${pageContext.request.contextPath  }/f/cart/delete_by_Id?id='+cartId+'&productId='+dataId,
				type: "GET",
				dataType: "json",
				success: function(data){
					if(data.status=="success"){
						var $cart_menu_bd = $("#cart-menu-bd");
						$cart_menu_bd.empty();
						var cart_item_html = "<dl>";
						var cart = data.cart;
						if(cart!=null&&cart.cartItems!=null){
							$("#font_of_cart_count").text(cart.quantity);
							$.each(cart.cartItems,function(indec,cartItem){
								cart_item_html += "<dt><a href=\"#\"><img src=\""+baseFilePath+"\\"+(cartItem.product.image==null?"123":cartItem.product.image)+"\" /></a>"+
								  "<p>"+
								  "<a class=\"tit\" href=\"../product/details?productId="+(cartItem.product.id)+"\">"+ (cartItem.product.name)+
					              "</a> <span>¥"+(cartItem.product.price)+"</span>"+"x"+(cartItem.quantity)+" <a class=\"del\" href=\"javascript:void(0);\" cartId=\""+cartItem.id+"\" data-id=\""+cartItem.product.id+"\">删除</a>"+
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
					alert(data.message);
				}
			});
		});
		
		$(".update #isDefault").click(function(){
			if(default_check_box_update){
				default_check_box_update=false;
			}else{
				default_check_box_update=true;
			}
			$(this).val(default_check_box_update);
		});
	});
	
	//验证手机号码
	var isphone = false;	
	function isPhone(obj){
		$("#btn").prop("disabled",false);
	   	reg=/^((\d{3}-\d{8}|\d{4}-\d{7,8})|(1[3|5|7|8][0-9]{9}))$/;
	   	if(!reg.test(obj)){
		   $("#phoneNotnull").html("<b>请输入正确的电话号码</b>");
		   isphone = false;
	   }else{
		   $("#phoneNotnull").html("");
		   isphone = true;
	   }
	}
//验证收货人
var isconsignee = false;
function isConsignee(obj){
	$("#btn").prop("disabled",false);
	   if($("#consignee").val()==""){
		   $("#consigneeNotnull").html("<b>请输入收货人</b>");
		   isconsignee = false;
	   }else{
		   $("#consigneeNotnull").html("");
		   isconsignee = true;
	   }
	}
//验证地址
var isaddress = false;
function isAddress(obj){
	$("#btn").prop("disabled",false);
	   if($("#address").val()==""){
		   $("#addressNotnull").html("<b>请输入地址</b>");
		   isaddress = false;
	   }else{
		   $("#addressNotnull").html("");
		   isaddress = true;
	   }
	}
	//验证表单
	function validata(){
		if(isphone && isconsignee && isaddress){
			$.ajax({
	            cache: true,
	            type: "POST",
	            url:"${pageContext.request.contextPath}/f/member/save_json",
	            data:$('#add_address_form').serialize(),// 你的formid
	            async: false,
	            error: function(request) {
	                console.error("Connection error");
	            },
	            success: function(data) {
	            	var $selectList = $("#id_of_select_address .address_selected");
	            	var html = "<div class=\"order_address_info\">"+
	            	"<span class=\"radio_wrap c1\"><input name=\"area\" type=\"radio\" value=\""+data.receiver.area+"\" /></span>"+
	            	"<label><span class=\"c2\">"+data.receiver.consignee+"</span><span class=\"c3\">"+data.receiver.areaName+" "+data.receiver.address+" ，"+data.receiver.phone+"</span></label>"+
	            	"</div>"+
	            	"<div class=\"order_address_editor\">";
	            	if(data.receiver.isDefault && data.receiver.isDefault!=null){
	            		html += "<a href=\"javascript:void(0);\"></a>";
	            	}else{
	            		html += "<a href=\"javascript:void(0);\" myid=\""+data.receiver.id+"\">设为默认</a>";
	            	}
	            	html += "<a href=\"javascript:void(0);\" myid=\""+data.receiver.id+"\">修改</a> <a href=\"#\" myid=\""+data.receiver.id+"\">删除</a>";
	            	$selectList.append(html);
	                return true;
	            }
	        });
		}else{	
			return false;
			$("#btn").prop("disabled",true);
		}
	}
	
	function updateAddress(){
		var id_value=$(".update #id").val();
		$.ajax({
            cache: true,
            type: "POST",
            url:"${pageContext.request.contextPath}/f/member/update_json",
            data:$('#update_address_form').serialize(),// 你的formid
            async: false,
            error: function(request) {
                console.error("Connection error");
                $.jBox.tip(data.message);
            },
            success: function(data) {
            	if(default_check_box_update){
            		$("#id_of_select_address .order_address_info").each(function(){
            			var $this = $(this);
            			var value = $this.attr("value");
            			if(value==id_value){
            				var $default_a = $this.next().find("a:first");
            				console.info($default_a.text());
                    		selectDefualtAddress($default_a);
            			}
            		});
            	}
            }
        });
	}
	
	function ajaxForArea(parentId,type){
		$.ajax({
			type : 'post',
			url : '${pageContext.request.contextPath}/f/member/listByParentId?parentId='
					+ parentId,
			dataType : 'json',
			success : function(result) {
				areaOptionsAdd(result,type);
			}
		});
	}
	
	function areaOptionsAdd(data,type){
		if(type==areaTypeArray[1]){
			$("#city_select option:not(:first)").remove();
			$("#area_select option:not(:first)").remove();
			$.each(data, function(entryIndex, entry) {
				var html = "<option value='" + entry.id + "'>"
						+ entry.name + "</option>";
				$("#city_select").append(html);
			});
			return;
		}
		if(type==areaTypeArray[2]){
			$("#area_select option:not(:first)").remove();
			if(data!=null && data!=''){
				$.each(data, function(entryIndex, entry) {
					var html = "<option value='" + entry.id + "'>"
							+ entry.name + "</option>";
					$("#area_select").append(html);
				});
				$("#area_select").show();
				$("#area_code_string")
			}else{
				$("#area_select").hide();
				$("#area_code_string")
			}
			return;
		}
	}
	
	function selectRadio($this){
		$("#id_of_select_address .order_address_info").find("input:first").attr("checked",false);
		$this.attr("checked","checked");
		$("#receiver_id_input").val($this.parents(".order_address_info").attr("value"));
	}
	
	function selectDefualtAddress($this){
		$("#id_of_select_address .address_selected").find(".order_address_editor").find("a:first").text("设为默认");
		$this.text('');
		$("#new_default_radio").removeAttr("checked");
		console.info($this.attr("myId"));
		updateDefaultAddressOperation($this.attr("myId"));
	}
	
	function updateDefaultAddressOperation(id){
		console.info(id);
		$.ajax({
			type :"GET",
			url:"${pageContext.request.contextPath}/f/member/update_default_address?id="+id,
			dataType:"json",
			success:function(data){
				$.jBox.tip(data.message);
				console.debug($.fn.jquery);
			}
		})
	}

	function getProvinceAdd(Id) {
		$.ajax({
					type : 'post',
					url : '${pageContext.request.contextPath}/f/member/listByParentId?parentId='
							+ Id,
					dataType : 'json',
					success : function(result) {
						$.each(result, function(entryIndex, entry) {
							var html = "<option value='" + entry.id + "'>"
									+ entry.name + "</option>";
							$("#selprovince").append(html);
							$("#province_select").append(html);
						});
					}
		});
	}

	function getCityAdd(Id) {
		document.getElementById("areadiv").style.display = "none";
		$("#selcity option[value!=0]").remove();
		$("#selarea option[value!=0]").remove();
		$.ajax({
					type : 'post',
					url : '${pageContext.request.contextPath}/f/member/listByParentId?parentId='
							+ Id,
					dataType : 'json',
					success : function(result) {
						$.each(result, function(entryIndex, entry) {
							var html = "<option value='" + entry.id + "'>"
									+ entry.name + "</option>";
							$("#selcity").append(html);
						});
						document.getElementById("citydiv").style.display = "inline";
					}
		});
	}

	function getAreaAdd(Id) {
		if (document.getElementById("areadiv").style.display == "inline") {
			document.getElementById("areadiv").style.display = "none";
		}
		if (document.getElementById("areadiv").style.display == "none"
				&& document.getElementById("selcity").value != 0
				&& document.getElementById("selarea").value == 0) {
		}
		$("#selarea option[value!=0]").remove();
		$.ajax({
					type : 'post',
					url : '${pageContext.request.contextPath}/f/member/listByParentId?parentId='
							+ Id,
					dataType : 'json',
					success : function(result) {
						if (result == "") {
							document.getElementById("areadiv").style.display = "none";
							var provinceCode = $("#selprovince").val();
							var cityCode = $("#selcity").val();
							var provinceText = $("#selprovince").find(
									"option:selected").text();
							var cityText = $("#selcity")
									.find("option:selected").text();
							$("#areaName").val(provinceText + cityText);
							$("#area").val(provinceCode + "," + cityCode);
							return;
						} else {
							document.getElementById("areadiv").style.display = "inline";
							$.each(result, function(entryIndex, entry) {
								var html = "<option value='" + entry.id + "'>"
										+ entry.name + "</option>";
								$("#selarea").append(html);
							});

						}
					}

		});
	}
	function getProvinceUpdate(Id) {
		$("option:gt(0)",".update #selprovince").remove();
		$.ajax( {
			type :'post',
			url :'${pageContext.request.contextPath}/f/member/listByParentId?parentId='+Id,
			dataType :'json',
			success : function(result) {
				var proviceCode =  $(".update #area").val().split(',')[0];
				$.each(result, function(entryIndex, entry) {
					var selected="";
					if(proviceCode==entry.id){
						selected = "selected=\"selected\"";
						var way2=$(".update #provicediv span").eq(0);
						way2.text(entry.name);
					}
					var html = "<option value='" + entry.id + "'"+selected+">"+ entry.name + "</option>";							
					$(".update #selprovince").append(html);
				});
			}
		});
	}
	
	function getCityUpdate(Id,isFirst) {
		$(".update #areadiv").css("visibility","hidden");
		document.getElementById("areadiv").style.display = "none";
		$("option:gt(0)",".update #selcity").remove();
		$("option:gt(0)",".update #selarea").remove();
		
		$.ajax( {
			type :'post',
			url :'${pageContext.request.contextPath}/f/member/listByParentId?parentId='+Id,
			dataType :'json',
			success : function(result) {
				if(result!=""){
					$.each(result, function(entryIndex, entry) {
						var selected="";
						var cityCode = $(".update #area").val().split(',')[1];
						if(cityCode==entry.id){
							var way2=$(".update #selcity span").eq(0);
							selected = "selected=\"selected\"";
							way2.text(entry.name);
						}
						var html = "<option value='" + entry.id+ "'"+selected+">"+ entry.name + "</option>";
						$(".update #selcity").append(html);
					});
					 $(".update #citydiv").css("visibility","visible");
				}else{
					$(".update #citydiv").css("visibility","hidden");
				}
				 
			}
		});
	}

	function getAreaUpdate(Id,isFirst) {
		$("option:gt(0)",".update #selarea").remove();
		$.ajax( {
			type :'post',
			url :'${pageContext.request.contextPath}/f/member/listByParentId?parentId='+Id,
			dataType :'json',
			success : function(result) {
				if(result==""){
					$(".update #areadiv").css("visibility","hidden");
					var provinceCode = $(".update #selprovince").val();
					var cityCode = $(".update #selcity").val();
					var provinceText = $(".update #selprovince").find("option:selected").text(); 
					var cityText = $(".update #selcity").find("option:selected").text(); 
					$(".update #areaName").val(provinceText+cityText);
					$(".update #area").val(provinceCode+","+cityCode);
					return;
				}else{
					$(".update #areadiv").css("visibility","visible");
					$.each(result, function(entryIndex, entry) {
						var selected="";
						var areaCode =$(".update #area").val().split(',')[2];
						if(areaCode==entry.id ){
							var way2=$(".update #areadiv span").eq(0);
							selected = "selected=\"selected\"";
							way2.text(entry.name);
						}
						var html = "<option value='" + entry.id + "'"+selected+">"+ entry.name + "</option>";
						$(".update #selarea").append(html);
					});
				    }
				}
			});
		}
	function getNotAreaAdd(Id){
		var provinceCode = $("#selprovince").val();
		var cityCode = $("#selcity").val();
		var areaCode = $("#selarea").val();
		var provinceText = $("#selprovince").find("option:selected").text(); 
		var cityText = $("#selcity").find("option:selected").text(); 
		var areaText = $("#selarea").find("option:selected").text(); 
		$("#area").val(provinceCode+","+cityCode+","+areaCode);
		$("#areaName").val(provinceText+cityText+areaText);
	}
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
		<div class="shop_titlerighttwo">
			<ul>
				<li class="lione gray666">查看购物车</li>
				<li class="litwo white">订单信息</li>
				<li class="litre gray666">完成订单</li>
			</ul>
		</div>
	</div>
	<!-- 搜索栏结束 -->
	<div id="errormsg" hidden></div>
	<!-- 删除 -->
	<div class= "delete">
	<div class="theme-popover-delete">
	     <div class="theme-poptit-delete">
	          <a href="javascript:;" title="关闭" class="close">×</a>
	          <h3>提示</h3>
	     </div>
	     <div class="address_pop_content">
		     	<div style="margin-left: 35%;"><h3>确定删除地址？</h2></div>
		     <div class="actionbox">
				<button class="blue_btn confirm_btn confirm" type="submit" sytle="border-width:0">确定</button>
				<a class="gray_btn cancel_btn close" href="javascript:;">取消</a>
			 </div>
		</div>
	</div>
	</div>
	<!-- 添加弹出框 -->
	<div class= "add">
	<div class="theme-popover">
	     <div class="theme-poptit">
	          <a href="javascript:;" title="关闭" class="close">×</a>
	          <h3>添加收货人</h3>
	     </div>
			<form id="add_address_form" method="post">
				<input type="hidden" name="memberId" value="${memberId}"/>
				<input type="hidden" name="areaName" id="areaName"/>
				<div class="address_pop_content">
					<dl>
						<dt>
							<span class="red">*</span>收货人：
						</dt>
						<dd>
							<input class="add_input" id="consignee" name="consignee" type="text"  onblur="isConsignee(this.value)" />
							<div id="consigneeNotnull" style="display: inline;"></div>
						</dd>
					</dl>
					<dl>
						<dt>
							<span class="red">*</span>地区：
						</dt>
						<dd>
							<div style="display: inline;" id="provicediv">
								 <select id="selprovince"  onchange="getCityAdd(this.value)">
									<option value="0">-请选择省份-</option>
								  </select>
							</div>
							<div id="citydiv"  style="display: inline;">
								  <select id="selcity"   onchange="getAreaAdd(this.value)">
									<option value="0">-请选择城市-</option>
								  </select>
							 </div>
							  <div id="areadiv" style="display: none;">
								  <select id="selarea"  onchange="getNotAreaAdd(this.value)">
									<option value="0">-请选择地区-</option>
								  </select>
							  </div>
							  <div style="display: none;">
								  <select id="notarea" >
									<option value="0">-请选择地区-</option>
								  </select>
							  </div>
							  <input type="hidden" id="area" name="area"/>
						</dd>
					</dl>
					<dl>
						<dt>
							<span class="red">*</span>详细地址：
						</dt>
						<dd>
							<input class="add_inputtwo" id="address" name="address" type="text" onblur="isAddress(this.value)"/>
							<div id="addressNotnull"  style="display: inline;"></div>
						</dd>
					</dl>
					<dl>
						<dt>
							<span class="red">*</span>手机/电话：
						</dt>
						<dd>
							<input class="add_input" id="phone" name="phone" type="text"  onblur="isPhone(this.value)"/>
							<div id="phoneNotnull"  style="display: inline;"></div>
						</dd>
					</dl>
					<div class="defaultbox">
						<label><input id="isDefault" name="isDefault" type="checkbox" value="1" checked="checked"/> 设为默认地址</label>
					</div>
					<div class="actionbox">
						<button class="blue_btn confirm_btn" id = "btn" sytle="border-width:0" onclick="return validata();" >确认收货地址</button>
						<a class="gray_btn cancel_btn close" href="javascript:;" >取消</a>
					</div>
				</div>
			</form>
		</div>
	</div>
	<!-- 修改弹出框 -->
						<div class="update">
							<div class="theme-popover">
								<div class="theme-poptit">
									<a href="javascript:;" title="关闭" class="close">×</a>
									<h3>修改收货人</h3>
								</div>
								<form method="post" id="update_address_form">
									<input type="hidden" id="id" name="id" /> <input
										type="hidden" name="areaName" id="areaName" />
									<div class="address_pop_content">
										<dl>
											<dt>
												<span class="red">*</span>收货人：
											</dt>
											<dd>
												<input class="add_input" id="consignee" name="consignee"
													type="text" />
											</dd>
										</dl>
										<dl>
											<dt>
												<span class="red">*</span>地区：
											</dt>
											<dd>
												<div style="display: inline;" id="provicediv">
													<select id="selprovince"
														onchange="getCityUpdate(this.value,null)">
														<option value="0">-请选择省份-</option>
													</select>
												</div>
												<div id="citydiv" style="display: inline;">
													<select id="selcity"
														onchange="getAreaUpdate(this.value,null)">
														<option value="0">-请选择城市-</option>
													</select>
												</div>
												<div id="areadiv" style="display: inline;">
													<select id="selarea">
														<option value="0">-请选择地区-</option>
													</select>
												</div>
												<input type="hidden" id="area" name="area" />
											</dd>
										</dl>
										<dl>
											<dt>
												<span class="red">*</span>详细地址：
											</dt>
											<dd>
												<input class="add_inputtwo" id="address" name="address"
													type="text" />
											</dd>
										</dl>
										<dl>
											<dt>
												<span class="red">*</span>手机/电话：
											</dt>
											<dd>
												<input class="add_input" name="phone" id="phone" type="text" />
											</dd>
										</dl>
										<div class="defaultbox">
											<label><input name="isDefault" id="isDefault"
												type="checkbox" /> 设为默认地址</label>
										</div>
										<div class="actionbox">
											<button class="blue_btn confirm_btn" type="submit"
												sytle="border-width:0" id="update_address_form_submit" onclick="return updateAddress();">确认收货地址</button>
											<a class="gray_btn cancel_btn close" href="javascript:;">取消</a>
										</div>
									</div>
								</form>
							</div>
						</div>
	<form id="order_table_form">
	<input type="hidden" name="cartId" value="${param.cartId }"/>
	<input type="hidden" name="pids" value="${param.pids}"/>
	<!-- 购物车 -->
	<div class="shop_cart">
		<!-- 收货信息 -->
		<div class="shop_content">
			<div class="order_thead font14">收货信息</div>
			<div class="order_address">
				<ul id="id_of_select_address">
					<li class="address_selected">
						<input type="hidden" name="receiverId" value="" id="receiver_id_input"/>
						<c:forEach items="${list}" var="myAddress">
						<div class="order_address_info" value="${myAddress.id}">
							<span class="radio_wrap c1">
							<c:choose>
								<c:when test="${myAddress.isDefault!=null && myAddress.isDefault}">
									<input name="area" type="radio" value="${myAddress.area }" checked/>
								</c:when>
								<c:otherwise>
									<input name="area" type="radio" value="${myAddress.area }"/>
								</c:otherwise>
							</c:choose>
							</span> <label><span class="c2">${myAddress.consignee}</span><span
								class="c3">${myAddress.areaName} ${myAddress.address} ，${myAddress.phone}</span></label>
						</div>
						<div class="order_address_editor">
						<c:choose>
								<c:when test="${myAddress.isDefault && myAddress.isDefault!=null }">
									<a href="javascript:void(0);"></a>
								</c:when>
								<c:otherwise>
									<a href="javascript:void(0);" myid="${myAddress.id}">设为默认</a>
								</c:otherwise>
							</c:choose>
							<a href="javascript:void(0);" myid="${myAddress.id}">修改</a> <a href="#" myid="${myAddress.id}">删除</a>
						</div>
						</c:forEach>
					</li>
					<li>
						<div class="order_address_info">
							<span class="radio_wrap c1"><input name="" type="radio"
								value="" id="new_address_radio"/></span> <label><span class="c2">使用新地址</span></label>
						</div>
					</li>
				</ul>
			</div>
		</div>
		<!-- 支付方式 -->
		<div class="shop_content mt20">
			<div class="order_thead font14">支付方式</div>
			<div class="order_address">
				<ul id="payment_method_ul">
					<c:forEach items="${paymentMethods}" var="item">
						<li class="address_selected">
							<div class="order_address_info">
								<span class="radio_wrap c1">
									<input name="paymentMethod" type="radio" value="${item.id }" />
								</span> 
								<label>
									<c:if test="${item.icon!=null and item.icon!=''}">
										<img src="${baseFilePath }/${item.icon}" class="c2"/>
									</c:if>
									<span class="c2" name="" value="${item.name}">${item.name}</span>
									<span class="c3">${item.description }</span> 
								</label>
							</div>
						</li>
					</c:forEach>
				</ul>
			</div>
		</div>
		<!-- 配送方式 -->
		<div class="shop_content mt20">
			<div class="order_thead font14">配送方式</div>
			<div class="order_address">
				<ul id="shipping_method_ul">
					<c:forEach items="${shippingMethods}" var="item">
						<li class="address_selected">
							<div class="order_address_info">
								<span class="radio_wrap c1">
									<input name="shippingMethod" type="radio" value="${item.id }" />
								</span> 
								<label>
									<c:if test="${item.icon!=null and icon!=''}">
										<img src="${baseFilePath }/${item.icon}" class="c2"/>
									</c:if>
									<span class="c2" name="" value="${item.name}">${item.name}</span>
									<span class="c3">${item.description }</span> 
								</label>
							</div>
						</li>
					</c:forEach>
				</ul>
			</div>
		</div>
		<!-- 发票信息 -->
		<div class="shop_content mt20">
			<div class="order_thead font14">发票信息</div>
			<div class="order_address">
				<!-- 
				<ul>

					<li>
						<div class="order_address_info">
							<span class="radio_wrap c1"><input name="" type="radio"
								value="" /></span> <label><span class="c2">普通发票</span><span
								class="c3">个人 张三 办公用品</span> </label>
						</div>
					</li>
					<li class="address_selected">
						<div class="order_address_info">
							<span class="radio_wrap c1"><input name="" type="radio"
								value="" /></span> <label><span class="c2">新增发票</span></label>
						</div>
					</li>
				</ul>
				 -->
				<ul class="order_invoices">
					<li>
						<div class="order_address_info">
							<span class="radio_wrap c1"><input name="isInvoice"
								type="radio" id="RadioGroup1_0" value="0" /></span>
							<label><span class="c2">普通发票</span></label>
						</div>
						<!-- 
						<div class="order_address_info">
							<span class="radio_wrap c1"><input type="radio"
								name="RadioGroup1" value="单选" id="RadioGroup1_1" /></span> <label><span
								class="c2">增值税发票</span></label>
						</div>
						 -->
					</li>
					<li>
						<!-- 普通发票 -->
						<div class="order_add_invoices">
							<div class="list">
								<span class="label"><em>*</em>发票抬头：</span>
								<div class="field">
									<input class="textbox" name="invoiceTitle" type="text" />
								</div>
							</div>
							<!-- 
							<div class="list">
								<span class="label"><em>*</em>发票抬头：</span>
								<div class="field">
									<select>
										<option>个人</option>
										<option>公司</option>
									</select><input class="textbox" name="" type="text" />
								</div>
							</div>
							<div class="list">
								<span class="label"><em>*</em>发票内容：</span>
								<div class="field">
									<select name="">
										<option>商品明细</option>
										<option>办公用品</option>
										<option>电脑配件</option>
										<option>耗材</option>
									</select>
								</div>
							</div>
							<div class="list">
								<div class="order_default">
									<a class="address_btn btn_confirm" href="#">确认发票信息</a><a
										class="address_btn btn_cancel" href="#">取消</a>
								</div>
							</div>
							 -->
						</div> 
						<!-- 增值税发票 -->
						<!-- 
						<div class="order_add_invoices">
							<div class="list">
								<span class="label"><em>*</em>公司名称：</span>
								<div class="field">
									<input class="textbox" name="" type="text" />
								</div>
							</div>
							<div class="list">
								<span class="label"><em>*</em>公司地址：</span>
								<div class="field">
									<input class="textbox" name="" type="text" />
								</div>
							</div>
							<div class="list">
								<span class="label"><em>*</em>公司电话：</span>
								<div class="field">
									<input type="text" class="textbox minitextbox" value="区号" /> -
									<input type="text" class="textbox" value="电话号码" />
								</div>
							</div>
							<div class="list">
								<span class="label"><em>*</em>税&nbsp;&nbsp;&nbsp;&nbsp;号：</span>
								<div class="field">
									<input class="textbox" name="" type="text" />
								</div>
							</div>
							<div class="list">
								<span class="label"><em>*</em>银行账号：</span>
								<div class="field">
									<input class="textbox" name="" type="text" />
								</div>
							</div>
							<div class="list">
								<span class="label"><em>*</em>开&nbsp;户&nbsp;行：</span>
								<div class="field">
									<input class="textbox" name="" type="text" />
								</div>
							</div>
							<div class="list">
								<span class="label"><em>*</em>发票抬头：</span>
								<div class="field">
									<input class="textbox" name="" type="text" />
								</div>
							</div>
							<div class="list">
								<div class="order_default">
									<a class="address_btn btn_confirm" href="#">确认发票信息</a><a
										class="address_btn btn_cancel" href="#">取消</a>
								</div>
							</div>
						</div> -->
					</li>
				</ul>
			</div>
		</div>
		<!-- 商品信息 -->
		<div class="shop_content mt20">
			<div class="order_thead font14">
				<span class="order_thead_return"><a class="bluenone" href="${pageContext.request.contextPath  }/f/cart/list">返回修改购物车商品清单</a></span>商品信息
			</div>
			<div class="order_goods" price="${cart.effectivePrice }">
				<div class="order_goods_title">
					<ul>
						<li class="title_cl">商品名称</li>
						<li class="title_c2">价格</li>
						<li class="title_c3">数量</li>
						<li class="title_c4">库存状态</li>
					</ul>
				</div>
				<c:forEach items="${cart.cartItems }" var="cartItem">
				
				<c:set var="goodsName" value="${ cartItem.product.name }"></c:set>
				<ul class="order_goods_details">
					<li><a class="img_wrap" href="#"><img
							src="${baseFilePath }/${cartItem.product.image}" /></a>
						<div class="img_detail">
							<a href="${pageContext.request.contextPath  }/f/product/details?productId=${cartItem.product.id}">${cartItem.product.name }</a>
						</div>
						<div class="detail_c2 red">¥${cartItem.product.price}</div>
						<div class="detail_c3">${cartItem.quantity}</div>
						<c:choose>
							<c:when test="${cartItem.product.availableStock>0}">
								<div class="detail_c4">有货</div>
							</c:when>
							<c:otherwise>
								<div class="detail_c4">无货</div>
							</c:otherwise>
						</c:choose>
						</li>
				</ul>
				</c:forEach>
				<input type="hidden" name="remarks" value="${goodsName}"/>
			</div>
		</div>
		<!-- 结算信息 -->
		<div class="shop_content mt20">
			<div class="order_thead font14">
				</span>结算信息
			</div>
			<div class="order_coupon">
				<div class="order_coupon_title">
					<s></s>使用优惠券抵消部分总额
				</div>
				<c:forEach items="${coupons}" var="item">
					<c:forEach items="${item.couponCodeList }" var="cItem">
					<div class="order_coupon_select address_selected">
						<span class="c1"><input name="codeId" type="radio" value="${cItem.code}" /></span> 
						<label>
							<span class="c2">
								<span class="red">${item.needConsumeBalance}减${item.facePrice}</span>元
							</span>
							<span class="c3">${item.couponSource }</span>
							<span class="c4">有效期至<fmt:formatDate value="${item.endDate }" pattern="yyyy-MM-dd" /></span>
						</label>
					</div>
					</c:forEach>
				</c:forEach>
				<!-- 
				<div class="order_coupon_select">
					<span class="c1"><input name="" type="radio" value="" /></span> <label><span
						class="c2"><span class="red">1000-50</span>元</span><span
						class="c3">全品类券</span><span class="c4">有效期至2014-09-30</span></label>
				</div> -->
				<div class="order_coupon_add">
					<span>请输入易购券号：</span> <span><input class="textbox" name="couponCode"
						type="text" /></span> <a class="order_coupon_addbtn" href="#">添加</a>
				</div>
				<div class="order_coupon_title">
					<s></s>若您对包裹有特殊要求，请在此留言
				</div>
				<div class="order_coupon_add">
					<span>备注信息：</span> <span><textarea name="memo"
							id="textarea" cols="45" rows="5"></textarea></span><!-- 选填，可告诉卖家您的特殊要求，限定100个字符以内。 -->
				</div>
			</div>
			<div class="order_total">
				<p>
					<span class="red">${cart.quantity }</span>件商品，总商品金额：¥${cart.effectivePrice }<span>优惠券：-¥0.00</span><span>税金：¥0.00</span><span>运费：¥0.00</span>
				</p>
				<c:forEach items="${list}" var="myAddress">
					<c:choose>
						<c:when test="${myAddress.isDefault!=null && myAddress.isDefault}">
							<p>收货地址：${myAddress.areaName} ${myAddress.address}</p>
							<p>收货人：${myAddress.consignee} ${myAddress.phone}</p>
						</c:when>
					</c:choose>
				</c:forEach>
				<p>
					应付总额：<font class="font16 red">￥${cart.effectivePrice }</font>
				</p>
				<p>
					<a class="order_total_btn font18" href="javascript:void(0);" id="add_form">提交订单</a>
					<!-- <input type="submit" class="order_total_btn font18" href="order_payment.html" value="提交订单"/> -->
				</p>
			</div>
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
	</form>
</body>
</html>
