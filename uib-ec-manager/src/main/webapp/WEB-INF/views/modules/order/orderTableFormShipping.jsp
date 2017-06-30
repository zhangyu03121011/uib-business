<%@ page contentType="text/html;charset=UTF-8" import="com.uib.ecmanager.common.enums.*" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>订单管理</title>
	<meta name="decorator" content="default"/>
	<link href="../../../static/common/common.css" type="text/css" rel="stylesheet" />	
	<script type="text/javascript">
	var tables = ["base_info_tab","order_product_tab","order_shipping_tab"];
	var $confirmForam = $("#confirmForm");//确定表单
	var $completeForm = $("#completeForm");//完成表单
	var $cancelForm = $("#cancelForm");//取消表单
	var $shippingButton = $("#shippingButton");//发货按钮
	var $completeButton = $("#completeButton");//完成按钮
	var $refundsButton = $("#refundsButton");//退款按钮
	var $returnsButton = $("#returnsButton");//退货按钮
	var $cancelButton = $("#cancelButton");//取消按钮
	var isLocked = false;
	var $returnsUnpassButton = $("#returnsUnpassButton");
	var $returnsReturnedButton = $("#returnsReturnedButton");
	var $confirmButton = $("#confirmButton");//确定按钮
		
	$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
			if($("#area").val()!=null ){
				var shippingCodes = $("#area").val().split(',');
				getProvince(1);
				if(shippingCodes!=null){
					getCity(shippingCodes[0],true);
					getArea(shippingCodes[1],true);
				}
			}
			$("#selarea").change(function(){
				var provinceCode = $("#selprovince").val();
				var cityCode = $("#selcity").val();
				var areaCode = $("#selarea").val();
				$("#area").val(provinceCode+","+cityCode+","+areaCode);
				var provinceText = $("#selprovince").find("option:selected").text(); 
				var cityText = $("#selcity").find("option:selected").text(); 
				var areaText = $("#selarea").find("option:selected").text();
				$("#areaName").val(provinceText+cityText+areaText);
			});
			//发货
			$shippingButton.live("click",function(){
				$("#shippingDialogs" ).dialog("open");
			});
			$("#shippingDialogs").dialog({
				width:900,
				height:440,
				modal:true,
				autoOpen:false, //将此对话框设置为不自动弹出
				buttons: [
				  		{
				  			text: "确定",
				  			click: function() {
				  				var trackingNo=$("#trackingNo").val();
				  				var deliveryCorpId= $("#deliveryCorpId").val();
				  				if(deliveryCorpId==null || deliveryCorpId=="" || deliveryCorpId==undefined){
				  					$.jBox.tip('请选择物流公司!');
				  					return false;
				  				  }else if(trackingNo==null || trackingNo==undefined || trackingNo==""){
				  					$.jBox.tip('请填写运单号!');
				  					return false;
				  				  }else{
				  					$("#shippingForm").submit();
				  					$( this ).dialog( "close" );
				  				}
				  			}
				  		},
				  		{
				  			text: "取消",
				  			click: function() {
				  				$( this ).dialog( "close" );
				  			}
				  		}
				  	],
				//设置对话框效果  
				show:{
					effect:"blind",   
					duration:1000
				}
			});	
			
			//退款
			$refundsButton.live("click",function(){
				$("#refundsDialogs" ).dialog("open");
			});
			$("#refundsDialogs").dialog({
				width:900,
				height:390,
				modal:true,
				autoOpen:false, //将此对话框设置为不自动弹出
				buttons: [
				  		{
				  			text: "确定",
				  			click: function() {
				  					$("#refundsForm").submit();
				  					$( this ).dialog( "close" );
				  			}
				  		},
				  		{
				  			text: "取消",
				  			click: function() {
				  				$( this ).dialog( "close" );
				  			}
				  		}
				  	],
				//设置对话框效果  
				show:{
					effect:"blind",   
					duration:1000
				}
			});	
		});
	
		function addRow(list, idx, tpl, row){
			$(list).append(Mustache.render(tpl, {
				idx: idx, delBtn: true, row: row
			}));
			$(list+idx).find("select").each(function(){
				$(this).val($(this).attr("data-value"));
			});
			$(list+idx).find("input[type='checkbox'], input[type='radio']").each(function(){
				var ss = $(this).attr("data-value").split(',');
				for (var i=0; i<ss.length; i++){
					if($(this).val() == ss[i]){
						$(this).attr("checked","checked");
					}
				}
			});
		}
		function delRow(obj, prefix){
			var id = $(prefix+"_id");
			var delFlag = $(prefix+"_delFlag");
			if (id.val() == ""){
				$(obj).parent().parent().remove();
			}else if(delFlag.val() == "0"){
				delFlag.val("1");
				$(obj).html("&divide;").attr("title", "撤销删除");
				$(obj).parent().parent().addClass("error");
			}else if(delFlag.val() == "1"){
				delFlag.val("0");
				$(obj).html("&times;").attr("title", "删除");
				$(obj).parent().parent().removeClass("error");
			}
		}
		//切换菜单
		$(function(){
			$("#inputForm ul > li").click(function(){
		        $(this).addClass("active").siblings().removeClass("active");
				var table_id = $(this).attr("id").replace("li","tab");
				$("#"+table_id).attr("style","display:table;width:100%");
				for(var i = 0 ; i < tables.length ; i++){
					if(tables[i] == table_id){
						continue;
					}
					$("#"+tables[i]).attr("style","display:none");
				}
			});
		});
		function getProvince(Id) {
			$("option:gt(0)","#selcity").remove();
			$.ajax( {
				type :'post',
				url :'../../sys/area/listByParentId?parentId='+Id,
				dataType :'json',
				async: false,
				success : function(result) {
					var proviceCode = $("#area").val().split(',')[0];
					$.each(result, function(entryIndex, entry) {
						var selected = "";
						if(proviceCode==entry.id){
							selected = "selected=\"selected\"";
							var way2=$("#provicediv span").eq(0);
							way2.text(entry.name);
						}
						var html = "<option value='" + entry.id + "'"+selected+">"+ entry.name + "</option>";
						$("#selprovince").append(html);
					});
					
				}
			});
		}
		
		function getCity(Id,isFirst) {
			var way2=$("#citydiv span").eq(0);
			way2.text("-请选择城市-");
			$('#areadiv').css('display','none');
			$("option:gt(0)","#selcity").remove();
			$("option:gt(0)","#selarea").remove();
			
			$.ajax( {
				type :'post',
				url :'../../sys/area/listByParentId?parentId='+Id,
				dataType :'json',
				async: false,
				success : function(result) {
					$.each(result, function(entryIndex, entry) {
						var selected = "";
						var cityCode = $("#area").val().split(',')[1];
						if(cityCode==entry.id && isFirst!=null){
							selected = "selected=\"selected\""
							way2.text(entry.name);
						}
						var html = "<option value='" + entry.id+ "'"+selected+">"+ entry.name + "</option>";
						$("#selcity").append(html);
					});
					$('#citydiv').css('display','inline');
				}
			});
		}
	
		function getArea(Id,isFirst) {
			var way2=$(" #areadiv span").eq(0);
			way2.text("-请选择地区-");
			if ($('#areadiv').css('display') == "inline") {
				$('#citydiv').css('display','none');
			}
			if ($('#areadiv').css('display') == "none"
					&& $("#selcity").prev().val() != 0
					&& $("#selarea").prev().val() == 0)
					{
			}
			$("#selarea option[value!=0]").remove();
			$.ajax( {
				type :'post',
				url :'../../sys/area/listByParentId?parentId='+Id,
				dataType :'json',
				async: false,
				success : function(result) {
					if(result==""){
						$('#areadiv').css('display','none');
						var provinceCode = $("#selprovince").val();
						var cityCode = $("#selcity").val();
						var areaCode = $("#selarea").val();
						$("#area").val(provinceCode+","+cityCode);
						var provinceText = $("#selprovince").find("option:selected").text(); 
						var cityText = $("#selcity").find("option:selected").text(); 
						$("#areaName").val(provinceText+cityText);
						return;
					}else{
						$('#areadiv').css('display','inline');
						$.each(result, function(entryIndex, entry) {
							var selected = "";
							var areaCode = $("#area").val().split(',')[2];
							if(areaCode==entry.id && isFirst!=null){
								selected = "selected=\"selected\"";
								way2.text(entry.name);
							}
							var html = "<option value='" + entry.id + "'"+selected+">"+ entry.name + "</option>";
							$("#selarea").append(html);
						});
					}
					
				}
			});
		}
</script>
</head>
<body>
	<form id="confirmForm"  action="${ctx}/order/orderTable/confirm" method="post">
		<input type="hidden" name="id" value="${orderTable.id}" />
	</form>
	<form id="completeForm"  action="${ctx}/order/orderTable/complete" method="post">
		<input type="hidden" name="id" value="${orderTable.id}" />
	</form>
	<form id="cancelForm"  action="${ctx}/order/orderTable/cancel" method="post">
		<input type="hidden" name="id" value="${orderTable.id}" />
	</form>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/order/orderTable/">订单列表</a></li>
		<li class="active"><a href="${ctx}/order/orderTable/form?id=${orderTable.id}">订单<shiro:hasPermission name="order:orderTable:edit">${not empty orderTable.id?'查看':'查看'}</shiro:hasPermission><shiro:lacksPermission name="order:orderTable:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="orderTable" action="${ctx}/order/orderTable/save" method="post" class="form-horizontal">
		<ul class="nav nav-tabs" id="tab">
			<li class="active" id="base_info_li"><a>订单信息</a></li>
			<li id="order_product_li"><a>商品信息</a></li>
			<li id="order_shipping_li"><a>发货信息</a></li>
		</ul>
		
		<div id="base_info_tab">
		<div class="control-group">
		<div class="controls">
		<table>
		<tr >
			<td>
				&nbsp;
			</td>
			<td>
				<%-- <c:set var="unconfirmed" value="<%=OrderStatus.unconfirmed %>"/>
				<c:choose>
					<c:when test="${orderTable.orderStatus != unconfirmed.index}">
						<input type="button" id="confirmButton" class="btn btn-primary" value="确认"  disabled="disabled"/>
					</c:when>
					<c:otherwise>
						<input type="button" id="confirmButton" class="btn btn-primary" value="确认"  />
					</c:otherwise>
				</c:choose> --%>
				<c:set var="paidShipped" value="<%=OrderStatus.paid_shipped %>"/>
				<c:set var="unshipped" value="<%=ShippingStatus.unshipped %>"/>
				<c:set var="paid" value="<%=PaymentStatus.paid %>"/>
				<c:choose>
					<c:when test="${orderTable.orderStatus != paidShipped.index }">
					<input type="button" id="shippingButton" class="btn btn-primary" value="发货" disabled="disabled"  />
					</c:when>
					<c:otherwise>
						<input type="button" id="shippingButton" class="btn btn-primary" value="发货" />
					</c:otherwise>
				</c:choose>
				<%-- <c:set var="paidShipped" value="<%=OrderStatus.paid_shipped %>"/>
				<c:choose>
					<c:when test="${orderTable.orderStatus != 1 || (orderTable.shippingStatus != 2 && orderTable.paymentStatus != 3)}">
					<input type="button" id="completeButton" class="btn btn-primary" value="完成" disabled="disabled"/>
				 	</c:when>
				 	<c:otherwise>
				 	<input type="button" id="completeButton" class="btn btn-primary" value="完成" />
				 	</c:otherwise>
				</c:choose> --%>
			</td>
			<td width="80">
			</td>
			<td>
			<%-- <c:choose>								
				<c:when test="${orderTable.orderStatus != 1 || (orderTable.paymentStatus != 2 && orderTable.paymentStatus != 1 && orderTable.shippingStatus != 1)}">
					<input type="button" id="refundsButton" class="btn btn-primary" value="退款" disabled="disabled" />
					</c:when>
				<c:otherwise>
					<input type="button" id="refundsButton" class="btn btn-primary" value="退款" />
				</c:otherwise>
				</c:choose>
				<c:choose>							
					<c:when test="${orderTable.orderStatus != 1 || (orderTable.shippingStatus != 2 && orderTable.shippingStatus != 1 && orderTable.shippingStatus != 3)}">
						<input type="button" id="returnsButton" class="btn btn-primary" value="退货" disabled="disabled"/>
					</c:when>
					<c:otherwise>
						<input type="button" id="returnsButton" class="btn btn-primary" value="退货"  />
					</c:otherwise>
				</c:choose>
				<c:choose>								
					<c:when test="${orderTable.orderStatus != 0 }">
					<input type="button" id="cancelButton" class="btn btn-primary" value="取消" disabled="disabled"  />
					</c:when>
					<c:otherwise>
					<input type="button" id="cancelButton" class="btn btn-primary" value="取消" />
					</c:otherwise>
				</c:choose> --%>
			</td>
		</tr>
		</table>
		</div>
		</div>
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">订单编号：</label>
			<div class="controls">
				${orderTable.orderNo}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">订单状态：</label>
			<div class="controls">
				${orderTable.orderStatusName}
				<%-- <c:choose>
					<c:when test="${orderTable.expired}">
					<span >(已过期)</span>
					</c:when>
				</c:choose> --%>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">B端分销商：</label>
			<div class="controls">
				${orderTable.distributorName}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">B端分销商手机号：</label>
			<div class="controls">
				${orderTable.distributorPhone}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">支付状态：</label>
			<div class="controls">
				${orderTable.paymentStatusName}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">配送状态：</label>
			<div class="controls">
				 ${orderTable.shippingStatusName}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">订单金额：</label>
			<div class="controls">
				<c:choose>
				 	<c:when test="${orderTable.amount != 0}">
				 	￥${orderTable.amount}
				 	</c:when>
				 	<c:otherwise>
				 		￥0.00
				 	</c:otherwise>
				 </c:choose>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">已付金额：</label>
			<div class="controls">
				<c:choose>
				 	<c:when test="${orderTable.amountPaid!=null && orderTable.amountPaid!= 0}">
				 	￥${orderTable.amountPaid}
				 	</c:when>
				 	<c:otherwise>
				 		￥0.00
				 	</c:otherwise>
				 </c:choose>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">订单来源：</label>
			<div class="controls">
				${orderTable.orderSourceName}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">地址：</label>
			<div class="controls">
				${orderTable.address}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">地区名称：</label>
			<div class="controls">
				${orderTable.areaName}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">收货人：</label>
			<div class="controls">
				${orderTable.consignee}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">优惠劵折扣：</label>
			<div class="controls">
				<c:choose>
				 	<c:when test="${orderTable.couponDiscount!=null && orderTable.couponDiscount!= 0}">
				 	￥${orderTable.couponDiscount}
				 	</c:when>
				 	<c:otherwise>
				 		￥0.00
				 	</c:otherwise>
				 </c:choose>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">支付手续费：</label>
			<div class="controls">
				<c:choose>
				 	<c:when test="${orderTable.fee!=null && orderTable.fee!= 0}">
				 	￥${orderTable.fee}
				 	</c:when>
				 	<c:otherwise>
				 		￥0.00
				 	</c:otherwise>
				 </c:choose>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">电话：</label>
			<div class="controls">
				${orderTable.phone}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">促销：</label>
			<div class="controls">
				<c:choose>
				 	<c:when test="${orderTable.promotion!=null}">
				 	${orderTable.promotion}
				 	</c:when>
				 	<c:otherwise>
				 		-
				 	</c:otherwise>
				 </c:choose>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">促销折扣：</label>
			<div class="controls">
				<c:choose>
				 	<c:when test="${orderTable.promotionDiscount!=null && orderTable.promotionDiscount!= 0}">
				 	￥${orderTable.promotionDiscount}
				 	</c:when>
				 	<c:otherwise>
				 		￥0.00
				 	</c:otherwise>
				 </c:choose>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">优惠码：</label>
			<div class="controls">
				<c:choose>
				 	<c:when test="${orderTable.couponCode!=null}">
				 	${orderTable.couponCode}
				 	</c:when>
				 	<c:otherwise>
				 		-
				 	</c:otherwise>
				 </c:choose>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">用户名：</label>
			<div class="controls">
				<c:choose>
				 	<c:when test="${orderTable.userName!=null}">
				 	${orderTable.userName}
				 	</c:when>
				 	<c:otherwise>
				 		-
				 	</c:otherwise>
				 </c:choose>
			</div>
		</div>	
		<%-- <div class="control-group">
			<label class="control-label">到期时间：</label>
			<div class="controls">
				<fmt:formatDate value="${orderTable.expire}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">锁定到期时间：</label>
			<div class="controls">
				<fmt:formatDate value="${orderTable.lockExpire}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div> --%>
		<div class="control-group">
			<label class="control-label">附言：</label>
			<div class="controls">
				<c:choose>
				 	<c:when test="${orderTable.memo!=null}">
				 	${orderTable.memo}
				 	</c:when>
				 	<c:otherwise>
				 		-
				 	</c:otherwise>
				 </c:choose>
			</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">调整金额：</label>
			<div class="controls">
				<c:choose>
				 	<c:when test="${orderTable.offsetAmount!=null && orderTable.offsetAmount!= 0}">
				 	￥${orderTable.offsetAmount}
				 	</c:when>
				 	<c:otherwise>
				 		￥0.00
				 	</c:otherwise>
				 </c:choose>
			</div>
		</div> --%>
		
		<div class="control-group">
			<label class="control-label">赠送积分：</label>
			<div class="controls">
				<c:choose>
				 	<c:when test="${orderTable.point!=null && orderTable.point!= 0}">
				 	${orderTable.point}
				 	</c:when>
				 	<c:otherwise>
				 		-
				 	</c:otherwise>
				 </c:choose>
			</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">税金：</label>
			<div class="controls">
				 <c:choose>
				 	<c:when test="${orderTable.tax!=null && orderTable.tax!= 0}">
				 	￥${orderTable.tax}
				 	</c:when>
				 	<c:otherwise>
				 		￥0.00
				 	</c:otherwise>
				 </c:choose>
			</div>
		</div> --%>
		<div class="control-group">
			<label class="control-label">邮编：</label>
			<div class="controls">
				<c:choose>
				 	<c:when test="${orderTable.zipCode!=null}">
				 	${orderTable.zipCode}
				 	</c:when>
				 	<c:otherwise>
				 		-
				 	</c:otherwise>
				 </c:choose>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">地区：</label>
			<div class="controls" >
				<c:choose>
				 	<c:when test="${orderTable.nameOfArea!=null}">
				 	${orderTable.nameOfArea}
				 	</c:when>
				 	<c:otherwise>
				 		-
				 	</c:otherwise>
				 </c:choose>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">支付方式：</label>
			<div class="controls">
				${paymentMethod.name}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">配送方式：</label>
			<div class="controls">
			${orderTable.shippingMethodName}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">异常备注：</label>
			<div class="controls">
				<c:choose>
				 	<c:when test="${orderTable.exceptionRemarks!=null}">
				 	<textarea rows="5" cols="8">${orderTable.exceptionRemarks}</textarea>
				 	</c:when>
				 	<c:otherwise>
				 		-
				 	</c:otherwise>
				 </c:choose>
			</div>
		</div>
	</div>
				
		<!-- 商品信息 -->
		<table id="order_product_tab" style="display:none;width:100%;">
		<tr height="40" class="title" style="background-color: #f1f8ff">
			<td>商品编号</td>
			<td>商品名称</td>
			<td>商品价格</td>
			<td>数量</td>
			<td>已发货数量</td>
			<td>已退货数量</td>
			<td>小计</td>
		</tr>
		 <c:forEach items="${orderTableItems}" var="orderTableItem">
			<tr  height="40">
				<td>
					${orderTableItem.product.id}
				</td>
				<td>
					${orderTableItem.product.name}
				</td>
				<td>
					<c:choose>
					 	<c:when test="${orderTableItem.price!=null  && orderTableItem.price!= 0}">
					 	￥${orderTableItem.price}
					 	</c:when>
					 	<c:otherwise>
					 		￥0.00
					 	</c:otherwise>
			 		</c:choose>
				</td>
				<td>
					${orderTableItem.quantity}
				</td>
				<td>
					${orderTableItem.shippedQuantity}
				</td>
				<td>
					${orderTableItem.returnQuantity}
				</td>
				<td>￥${orderTableItem.price*orderTableItem.quantity}</td>
			</tr>
			</c:forEach> 
		</table>
		
	
		<!-- 发货信息 -->
		<table id="order_shipping_tab" style="display:none;width:100%;">
		<tr height="40" class="title" style="background-color: #f1f8ff">
			<td>发货编号</td>
			<td>配送方式</td>
			<td>物流公司</td>
			<td>运单号</td>
			<td>收货人</td>
			<td>创建日期</td>
		</tr>
		<c:forEach items="${orderTableShippings}" var="orderTableShipping">
		<tr height="40">
				<td>
					${orderTableShipping.shippingNo}
				</td>
				<td>
					${orderTableShipping.shippingMethod}
				</td>
				<td>
					${orderTableShipping.deliveryCorp}
				</td>
				<td>
					${orderTableShipping.trackingNo}
				</td>
				<td>
					${orderTableShipping.consignee}
				</td>
				<td>
					<fmt:formatDate value="${orderTableShipping.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
		</tr>
		</c:forEach>
		</table>
		<div class="form-actions">
			<%-- <shiro:hasPermission name="order:orderTable:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission> --%>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
	
	<div class="shipping" id="shippingDialogs" title="发货">
		<form id="shippingForm"  action="${ctx}/order/orderTable/shipping" method="post" class="form-horizontal">
		
			<input type="hidden" name="orderId" value="${orderTable.id}" />
			<%-- <input type="hidden" id="shippingId" name="shippingId" value="${orderTableShipping.id}" /> --%>
			<div style="height: 330px; overflow-x: hidden; overflow-y: auto;">
				<table class="input" style="margin-bottom: 20px;">
					<tr >
						<th>订单编号:</th><td width="300">${orderTable.orderNo}</td>
						<th>创建日期:</th><td><fmt:formatDate value="${orderTable.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					</tr>
					<tr>
						<th>配送方式:</th><td>
								<select id="shippingMethodId" name="shippingMethodId" data-value="{{row.orderShipping}}" class="input-small ">
									<c:forEach items="${shippingMethods}" var="sm">
										<option value="${sm.id}">
										${sm.name}</option>
									</c:forEach>
								</select>
						</td>
						<th>物流公司:</th><td>
								<select id="deliveryCorpId" name="deliveryCorpId" data-value="{{row.orderDelivery}}" class="input-small ">
									<option value="">请选择</option>
									<c:forEach items="${deliveryCorps}" var="deliveryCorp">
										<c:choose>
											<c:when test="${deliveryCorp.id==shippingMethod.defaultdeliverycorp.id}">
											<option value="${deliveryCorp.id}" selected="selected">
											${deliveryCorp.name}</option>
											</c:when>
											<c:otherwise>
											<option value="${deliveryCorp.id}">
											${deliveryCorp.name}</option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</select>
							<span class="help-inline"><font color="red">*</font> </span>
						</td>
					</tr>
					<tr >
						<th>运单号:</th><td>
						<input name="trackingNo" id="trackingNo" type="text" class="text" />
						<span class="help-inline"><font color="red">*</font> </span>
						</td>
						<th>物流费用:</th><td>
						<input name="freight"  type="text" class="text" value="0" readonly="readonly"/>
						</td>
					</tr>
					<tr>
						<th>收货人:</th><td>
						<input name="consignee"  type="text" class="text" value="${orderTable.consignee}"/>
						<th>邮编:</th><td>
						<input name="zipCode"  type="text" class="text" value="${orderTable.zipCode}"/>
						</td>
					</tr>
					<tr>
						<th>地区:</th>
						<td>
							 <input id="areaName" name="areaName" type="text" class="text" value="${orderTable.areaName}"/>
						</td>
						<th>地址:</th><td>
						<input name="address"  type="text" class="text" value="${orderTable.address}"/>
						</td>
					</tr>
					<tr>
						<th>电话:</th><td>
						<input name="phone"  type="text" class="text" value="${orderTable.phone}"/>
						<th>备注:</th><td>
						<input name="memo"  type="text" class="text" />
						
						</td>
					</tr>
					
				</table>
				<table class="input">
					<tr class="title" style="background-color: #f1f8ff">
						<th>商品编号</th><th>商品名称</th><!-- <th>商品库存</th> --><th>购买数量</th><th>已发货数量</th><th>发货数量</th>
					</tr>
					<c:forEach items="${orderTableItems}" var="orderTableItem">
						<tr>
							<td>
								${orderTableItem.product.id}
							</td>
							<td>
								${orderTableItem.product.name}
							</td>
							<%-- <td>
								${orderTableItem.product.allocatedStock}
							</td> --%>
							<td>
								${orderTableItem.quantity}
							</td>
							<td>
								${orderTableItem.shippedQuantity}
							</td>
							<td>
								<c:choose>
								 	<c:when test="${orderTableItem.product!=null ||orderTableItem.product.stock != null}">
								 		<c:choose>
								 			<c:when test="${orderTableItem.product.stock<=0 || orderTableItem.quantity-orderTableItem.shippedQuantity<=0}">
								 				<input type="text"  class="text" value="0" style="width: 30px;" disabled="disabled" />
								 			</c:when>
								 			<c:otherwise>
								 				<c:choose>
								 					<c:when test="${orderTableItem.product.stock < orderTableItem.quantity-orderTableItem.shippedQuantity}">
								 						${orderTableItem.product.stock}"
								 					</c:when>
								 					<c:otherwise>
								 						${orderTableItem.quantity - orderTableItem.shippedQuantity}
								 					</c:otherwise>
								 				</c:choose>
								 			</c:otherwise>
								 		</c:choose>
								 	</c:when>
								 	<c:otherwise>
								 		<input type="text"  class="text" value="${orderTableItem.quantity - orderTableItem.shippedQuantity}" maxlength="9" style="width: 30px;" max="${orderTableItem.quantity - orderTableItem.shippedQuantity}" />
								 	</c:otherwise>
						 		</c:choose>
							</td>
						</tr>
						</c:forEach>
				</table>
			</div>
		</form>
	</div>
		
</body>
</html>