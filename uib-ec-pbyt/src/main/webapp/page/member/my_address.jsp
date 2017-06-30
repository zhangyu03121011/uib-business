<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<style type="text/css">
	.address_pop_content .actionbox button{border-top-width: 0px;border-left-width: 0px;border-right-width: 0px;border-bottom-width: 0px;float: left;}
	.address_con .isDefault{margin: 0 0 0 10px;font-size: 12px;background: #ffaa45;padding: 2px;color: #fff;font-weight: 400;display: inline;text-decoration: none;}
	.address_con .Default{border-color: #fff;}
	.theme-popover-delete{z-index:999999;position:fixed;top:63%;left:63%;width:350px;height:160px;margin:-180px 0 0 -280px;border-radius:5px;border:solid 2px #666;background-color:#fff;display:none;box-shadow: 0 0 10px #666;}
	.theme-poptit-delete{border-bottom:1px solid #ddd;padding:10px;position: relative;background:#1c547d}
	.theme-poptit-delete h3{color:#fff}
	.theme-poptit-delete .close{float:right;color:#fff;padding:5px;margin:-2px -5px -5px;font:bold 14px/14px simsun;text-shadow:0 1px 0 #ddd}
	.theme-poptit-delete .close:hover{color:#fff;}
	.theme-popover-delete .address_pop_content .actionbox{padding:20px 0 10px 63px}
	.theme-popover-delete .address_pop_content .actionbox .confirm_btn{width:90px;height:35px;line-height:35px}
	.theme-popover-delete .address_pop_content .actionbox .cancel_btn{width:90px;height:35px;line-height:35px;margin-left:10px}
	</style>

	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link href="${pageContext.request.contextPath  }/static/css/common.css" rel="stylesheet" type="text/css" />
    <link href="${pageContext.request.contextPath  }/static/css/user.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${pageContext.request.contextPath  }/static/js/jquery-1.8.2.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath  }/static/js/hover.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath  }/static/js/photo-info.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath  }/static/js/select.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath  }/static/js/jquery.jmpopups-0.5.1.js"></script>
	<script type="text/javascript">
	var myid="";

	$(document).ready(function() {
		getProvinceAdd(1);
		$('.add_addressbtn').click(function(){
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
					$(".update #id").val(result.id);
					$(".update #consignee").val(result.consignee);
					$(".update #address").val(result.address);
					$(".update #area").val(result.area);
					$(".update #phone").val(result.phone);
					if(result.isDefault){
						$(".update #isDefault").val("1");
						$(".update #isDefault").prop("checked",true);
					}
					var codes = $(".update #area").val().split(',');
					getProvinceUpdate(1);
					if(codes!=null){
						getCityUpdate(codes[0],true);
						getAreaUpdate(codes[1],true);
					}
				}
			});
			
		});
		$('.delete_addressbtn').click(function(){
			$('.theme-popover-mask').fadeIn(100);
			$('.delete .theme-popover-delete').slideDown(200);
			myid=$(this).attr('myid');
		});
		$('.theme-popover-delete .close ').click(function(){
			$('.theme-popover-mask').fadeOut(100);
			$('.theme-popover-delete').slideUp(200);
		});
		$('.theme-popover-delete .confirm ').click(function(){
			$('.theme-popover-mask').fadeOut(100);
			$('.theme-popover-delete').slideUp(200);
			window.location.href="${pageContext.request.contextPath}/f/member/delete?id="+myid;
		});
		$('.theme-popover .close').click(function(){
			$('.theme-popover-mask').fadeOut(100);
			$('.theme-popover').slideUp(200);
		});

		$('.order_address_editor').find("a:last").click(function(){
			$('.theme-popover-mask').fadeIn(100);
			$('.delete .theme-popover-delete').slideDown(200);
			myid=$(this).attr('myid');
		});
		$(".order_address_editor").find("a:first").click(function(){
			var $this = $(this);
			selectDefualtAddress($this);
		});
		
		function selectDefualtAddress($this){
			$("#id_of_select_address .address_selected").find(".order_address_editor").find("a:first").text("设为默认");
			$("#id_of_select_address .address_selected").find(".order_address_editor").find("separator").text("|");
			$("#id_of_select_address .address_selected").find(".order_address_editor").find("a:first").attr("class", "Default");;
			$this.text("默认地址");
			$this.addClass("isDefault");
			updateDefaultAddressOperation($this.attr("myId"));
		}
		
		function updateDefaultAddressOperation(id){
			$.ajax({
				type :"GET",
				url:"${pageContext.request.contextPath}/f/member/update_default_address?id="+id,
				dataType:"json",
				success:function(data){
					$.jBox.tip(data.message);
					console.debug($.fn.jquery);
				}
			});
		}
		
	});
	function getProvinceUpdate(Id) {
		$("option:gt(0)",".update #selprovince").remove();
		$.ajax( {
			type :'post',
			url :'${pageContext.request.contextPath}/f/member/listByParentId?parentId='+Id,
			dataType :'json',
			async: false,
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
		var way2=$(".update #selcity span").eq(0);
		$(".update #areadiv").css("visibility","hidden");
		document.getElementById("areadiv").style.display = "none";
		$("option:gt(0)",".update #selcity").remove();
		$("option:gt(0)",".update #selarea").remove();
		$.ajax( {
			type :'post',
			url :'${pageContext.request.contextPath}/f/member/listByParentId?parentId='+Id,
			dataType :'json',
			async: false,
			success : function(result) {
					$.each(result, function(entryIndex, entry) {
						var selected="";
						var cityCode = $(".update #area").val().split(',')[1];
						if(cityCode==entry.id && isFirst!=null){
							selected = "selected=\"selected\"";
							way2.text(entry.name);
						}
						var html = "<option value='" + entry.id+ "'"+selected+">"+ entry.name + "</option>";
						$(".update #selcity").append(html);
					});
					 $(".update #citydiv").css("visibility","visible");
			}
		});
	}

	function getAreaUpdate(Id,isFirst) {
		var way2=$(".update #areadiv span").eq(0);
		$("option:gt(0)",".update #selarea").remove();
		$.ajax( {
			type :'post',
			url :'${pageContext.request.contextPath}/f/member/listByParentId?parentId='+Id,
			dataType :'json',
			async: false,
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
						if(areaCode==entry.id && isFirst!=null){
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
			function getProvinceAdd(Id) {
				$.ajax( {
					type :'post',
					url :'${pageContext.request.contextPath}/f/member/listByParentId?parentId='+Id,
					dataType :'json',
					success : function(result) {
						$.each(result, function(entryIndex, entry) {
							var html = "<option value='" + entry.id + "'>"+ entry.name + "</option>";
							$("#selprovince").append(html);
						});
					}
				});
			}
			
			function getCityAdd(Id) {
				document.getElementById("areadiv").style.display = "none";
				$("#selcity option[value!=0]").remove();
				$("#selarea option[value!=0]").remove();
				$.ajax( {
					type :'post',
					url :'${pageContext.request.contextPath}/f/member/listByParentId?parentId='+Id,
					dataType :'json',
						success : function(result) {
							$.each(result, function(entryIndex, entry) {
								var html = "<option value='" + entry.id + "'>"+ entry.name + "</option>";
								$("#selcity").append(html);
							});
							document.getElementById("citydiv").style.display ="inline";
					}
				});
			}
			
			function getAreaAdd(Id) {
				if (document.getElementById("areadiv").style.display == "inline") {
					document.getElementById("areadiv").style.display = "none";
				}
				if (document.getElementById("areadiv").style.display == "none"
						&& document.getElementById("selcity").value != 0
						&& document.getElementById("selarea").value == 0)
						{
				}
				$("#selarea option[value!=0]").remove();
				$.ajax( {
					type :'post',
					url :'${pageContext.request.contextPath}/f/member/listByParentId?parentId='+Id,
					dataType :'json',
					success : function(result) {
						if(result==""){
							document.getElementById("areadiv").style.display = "none";
							var provinceCode = $("#selprovince").val();
							var cityCode = $("#selcity").val();
							var provinceText = $("#selprovince").find("option:selected").text(); 
							var cityText = $("#selcity").find("option:selected").text();
							$("#areaName").val(provinceText+cityText);
							$("#area").val(provinceCode+","+cityCode);
							return;
						}else{
							document.getElementById("areadiv").style.display = "inline";
							$.each(result, function(entryIndex, entry) {
								var html = "<option value='" + entry.id + "'>"+ entry.name + "</option>";
								$("#selarea").append(html);
							});
						}
					}
					
				});
			}
			function getNotAreaAdd(Id){
				$("#btn").prop("disabled",false);
				var provinceCode = $("#selprovince").val();
				var cityCode = $("#selcity").val();
				var areaCode = $("#selarea").val();
				var provinceText = $("#selprovince").find("option:selected").text(); 
				var cityText = $("#selcity").find("option:selected").text(); 
				var areaText = $("#selarea").find("option:selected").text(); 
				$("#area").val(provinceCode+","+cityCode+","+areaCode);
				$("#areaName").val(provinceText+cityText+areaText);
			}
			//验证手机号码
			function isPhone(){
				$("#btn").prop("disabled",false);
				phone = $("#phone").val();
				   reg=/^((\d{3}-\d{8}|\d{4}-\d{7,8})|(1[3|5|7|8][0-9]{9}))$/;
				   if(!reg.test(phone)){
					   $("#phoneNotnull").html("请输入正确的电话号码");
					   return false;
				   }else{
					   $("#phoneNotnull").html("");
					   return true;
				   }
				}
			
			//验证收货人
			function isConsignee(){
				$("#btn").prop("disabled",false);
				   if($("#consignee").val()==""){
					   $("#consigneeNotnull").html("请输入收货人");
					   return false;
				   }else{
					   $("#consigneeNotnull").html("");
					   return true;
				   }
				}
			//验证地址
			function isAddress(){
				$("#btn").prop("disabled",false);
				   if($("#address").val()==""){
					   $("#addressNotnull").html("请输入地址");
					   return false;
				   }else{
					   $("#addressNotnull").html("");
					   return true;
				   }
				}
			//验证地区
			function isArea(){
				$("#btn").prop("disabled",false);
				var province = $("#selprovince").find("option:selected").val(); 
				var city = $("#selcity").find("option:selected").val(); 
				var area = $("#selarea").find("option:selected").val(); 
				var provincecity = province+","+city;
				var provincecityarea = province+","+city+","+area;
				if(provincecity=="0,0" || provincecityarea=="0,0,0"){
					   $("#areaNotnull").html("请选择地区");
					   return false;
				   }else{
					   $("#areaNotnull").html("");
					   return true;
				   }
			}
			//验证表单
			function validata(){
				isPhone();isConsignee();isAddress();isArea();
				if(isPhone() && isConsignee() && isAddress() && isArea()){
					return true;
				}else{	
					return false;
					$("#btn").prop("disabled",true);
				}
			}
			//验证修改手机号码
			function isPhoneUpdate(){
				$(".update #btn").prop("disabled",false);
				phone = $(".update #phone").val();
			    reg=/^((\d{3}-\d{8}|\d{4}-\d{7,8})|(1[3|5|7|8][0-9]{9}))$/;
				   if(!reg.test(phone)){
					   $(".update #phoneNotnull").html("请输入正确的电话号码");
					  return false;
				   }else{
					   $(".update #phoneNotnull").html("");
					   return true;
				   }
				  
				}
			//验证修改收货人
			function isConsigneeUpdate(){
				$(".update #btn").prop("disabled",false);
				   if($(".update #consignee").val()==""){
					   $(".update #consigneeNotnull").html("请输入收货人");
					   return false;
				   }else{
					   $(".update #consigneeNotnull").html("");
					   return true;
				   }
				}
			//验证修改地址
			function isAddressUpdate(){
				$(".update #btn").prop("disabled",false);
				   if($(".update #address").val()==""){
					   $(".update #addressNotnull").html("请输入地址");
					   return false;
				   }else{
					   $(".update #addressNotnull").html("");
					   return true;
				   }
				}
			//验证修改地区
			function isAreaUpdate(){
				$(".update #btn").prop("disabled",false);
				var province = $(".update #selprovince").find("option:selected").val(); 
				var city = $(".update #selcity").find("option:selected").val(); 
				var area = $(".update #selarea").find("option:selected").val(); 
				var provincecity = province+","+city;
				var provincecityarea = province+","+city+","+area;
				if((provincecityarea.substr(provincecityarea.length-1))!=0){
					var provinceCode = $(".update #selprovince").val();
					var cityCode = $(".update #selcity").val();
					var areaCode = $(".update #selarea").val();
					var provinceText = $(".update #selprovince").find("option:selected").text(); 
					var cityText = $(".update #selcity").find("option:selected").text(); 
					var areaText = $(".update #selarea").find("option:selected").text(); 
					$(".update #areaName").val(provinceText+cityText+areaText);
					$(".update #area").val(provinceCode+","+cityCode+","+areaCode);
				}
				if(provincecity=="0,0" || provincecityarea=="0,0,0"){
					   $(".update #areaNotnull").html("请选择地区");
					   return false;
				   }else{
					   $(".update #areaNotnull").html("");
					   return true;
				   }
			}
			//验证修改表单
			function validataUpdate(){
				isPhoneUpdate();isConsigneeUpdate();isAddressUpdate();isAreaUpdate();
				if(isPhoneUpdate() && isConsigneeUpdate() && isAddressUpdate() && isAreaUpdate()){
					return true;
				}else{	
					return false;
					$(".update #btn").prop("disabled",true);
				}
			}
			// 是否默认
			$("#isDefault").live("click",function() {
				if ($("#isDefault").prop("checked")) {
					$("#isDefault").val("1");
				} else {
					$("#isDefault").val("0");
				}
			});
			// 是否默认
			$(".update #isDefault").live("click",function() {
				if ($(".update #isDefault").prop("checked")) {
					$(".update #isDefault").val("1");
				} else {
					$(".update #isDefault").val("0");
				}
			});
			
	</script>
</head>
<body>

<!-- 我的地址 -->
<div class="my_order">
<div class="my_balance">
	<div class="balance_title font14">地址管理<span>（您已保存<b class="green">${address_count}</b>个地址，还能增加<b class="green">${20-address_count}</b>个新地址）</span></div>
	<div class="address_con">
		<ul  id="id_of_select_address">
			<c:forEach items="${list}" var="myAddress">
				<li  class="address_selected">
				<input type="hidden" name="receiverId" value="" id="receiver_id_input"/>
						<div class="order_address_editor">
							<c:choose>
								<c:when test="${myAddress.isDefault && myAddress.isDefault!=null }">
									<label>${myAddress.consignee}</label>
									<label>${myAddress.areaName} ${myAddress.address} ，${myAddress.phone}</label>
									<span><a href="javascript:void(0);" style="text-decoration: none;"><div class="isDefault" >默认地址</div></a></span>
								</c:when>
								<c:otherwise>
									<label>${myAddress.consignee}</label>
									<label>${myAddress.areaName} ${myAddress.address} ，${myAddress.phone}</label>
									<span><a href="javascript:void(0);" myid="${myAddress.id}">&nbsp;<label class="separator"></label>设为默认</a></span>
								</c:otherwise>
							</c:choose>
							<span><a href="javascript:void(0);" myid="${myAddress.id}">修改</a> <a href="#" myid="${myAddress.id}">|&nbsp;删除&nbsp;|</a></span>
						</div>
						</li>
					</c:forEach>
		</ul>
		<div class="add_address" ><s></s><a class="gray_btn add_addressbtn" href="javascript:;">添加新的收货地址</a></div>
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
			<form action="${pageContext.request.contextPath}/f/member/save"  method="post">
				<input type="hidden" name="${memberId}"/>
				<input type="hidden" name="areaName" id="areaName"/>
				<div class="address_pop_content">
					<dl>
						<dt>
							<span class="red">*</span>收货人：
						</dt>
						<dd>
							<input class="add_input" id="consignee" name="consignee" type="text"  onblur="isConsignee(this.value)" />
							<div id="consigneeNotnull" style="display: inline; color: #ffaa45"></div>
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
							  <div id="areaNotnull"  style="display: inline; color: #ffaa45"></div>
							  <input type="hidden" id="area" name="area"/>
						</dd>
					</dl>
					<dl>
						<dt>
							<span class="red">*</span>详细地址：
						</dt>
						<dd>
							<input class="add_inputtwo" id="address" name="address" type="text" onblur="isAddress()"/>
							<div id="addressNotnull"  style="display: inline; color: #ffaa45"></div>
						</dd>
					</dl>
					<dl>
						<dt>
							<span class="red">*</span>手机/电话：
						</dt>
						<dd>
							<input class="add_input" id="phone" name="phone" type="text"  onblur="isPhone()"/>
							<div id="phoneNotnull"  style="display: inline; color: #ffaa45"></div>
						</dd>
					</dl>
					<div class="defaultbox">
						<label><input id="isDefault" name="isDefault" type="checkbox" value="1" checked="checked"/> 设为默认地址</label>
					</div>
					<div class="actionbox">
						<button class="blue_btn confirm_btn" id = "btn" type="submit" sytle="border-width:0" onclick="return validata();" >确认收货地址</button>
						<a class="gray_btn cancel_btn close" href="javascript:;" >取消</a>
					</div>
				</div>
			</form>
		</div>
	</div>
	<!-- 修改弹出框 -->
	<div class= "update">
	<div class="theme-popover">
	     <div class="theme-poptit">
	          <a href="javascript:;" title="关闭" class="close">×</a>
	          <h3>修改收货人</h3>
	     </div>
			<form action="${pageContext.request.contextPath}/f/member/update"  method="post">
				<input type="hidden" id="id" name="id"  />
				<input type="hidden" name="areaName" id="areaName"/>
				<div class="address_pop_content">
					<dl>
						<dt>
							<span class="red">*</span>收货人：
						</dt>
						<dd>
							<input class="add_input" id="consignee"  name="consignee" type="text" onblur="isConsigneeUpdate()"/>
							<div id="consigneeNotnull" style="display: inline; color: #ffaa45"></div>
						</dd>
					</dl>
					<dl>
						<dt>
							<span class="red">*</span>地区：
						</dt>
						<dd>
							<div style="display: inline;" id="provicediv">
							  <select id="selprovince"  onchange="getCityUpdate(this.value,null)">
								<option value="0">-请选择省份-</option>
							  </select>
							</div>
							<div id="citydiv" style="display: inline;">
							  <select id="selcity"   onchange="getAreaUpdate(this.value,null)">
								<option value="0">-请选择城市-</option>
							  </select>
							 </div>
							 <div id="areadiv" style="display: inline-block;;">
								  <select id="selarea"  >
									<option value="0">-请选择地区-</option>
								  </select>
							  </div>
							  <div id="areaNotnull"  style="display: inline; color: #ffaa45"></div>
							  <input type="hidden" id="area" name="area" onblur="isAreaUpdate()"/>
						</dd>
					</dl>
					<dl>
						<dt>
							<span class="red">*</span>详细地址：
						</dt>
						<dd>
							<input class="add_inputtwo" id="address" name="address" type="text" onblur="isAddressUpdate()"/>
							<div id="addressNotnull"  style="display: inline; color: #ffaa45"></div>
						</dd>
					</dl>
					<dl>
						<dt>
							<span class="red">*</span>手机/电话：
						</dt>
						<dd>
							<input class="add_input" name="phone" id="phone" type="text" onblur="isPhoneUpdate()"/>
							<div id="phoneNotnull"  style="display: inline; color: #ffaa45"></div>
						</dd>
					</dl>
					<div class="defaultbox">
						<label><input name="isDefault" id="isDefault" type="checkbox" /> 设为默认地址</label>
					</div>
					<div class="actionbox">
						<button class="blue_btn confirm_btn" id = "btn" type="submit" sytle="border-width:0" onclick="return validataUpdate();">确认收货地址</button>
						<a class="gray_btn cancel_btn close" href="javascript:;">取消</a>
					</div>
				</div>
			</form>
		</div>
		</div>
		
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
<div class="theme-popover-mask"></div>
</body>	