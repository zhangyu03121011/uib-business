<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>发货单管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	var tables = ["base_info_tab","order_shipping_item_tab"];
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
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/order/orderTableShipping/">发货单列表</a></li>
		<li class="active"><a href="${ctx}/order/orderTableShipping/form?id=${orderTableShipping.id}">发货单<shiro:hasPermission name="order:orderTableShipping:edit">${not empty orderTableShipping.id?'查看':'查看'}</shiro:hasPermission><shiro:lacksPermission name="order:orderTableShipping:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="orderTableShipping" action="${ctx}/order/orderTableShipping/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<ul class="nav nav-tabs" id="tab">
			<li class="active" id="base_info_li"><a>订单信息</a></li>
			<li id="order_shipping_item_li"><a>商品信息</a></li>
		</ul>
		
		<div id="base_info_tab">
		<div class="control-group">
			<label class="control-label">发货单编号：</label>
			<div class="controls">
				<%-- <form:input path="shippingNo" htmlEscape="false" maxlength="32" class="input-xlarge "/> --%>
				${orderTableShipping.shippingNo}
			</div>
		</div>
			<div class="control-group">
			<label class="control-label">订单编号：</label>
			<div class="controls">
				<%-- <form:input path="orderNo" htmlEscape="false" maxlength="32" class="input-xlarge "/> --%>
				${orderTableShipping.orderNo}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">地址：</label>
			<div class="controls">
				<%-- <form:input path="address" htmlEscape="false" maxlength="32" class="input-xlarge "/> --%>
				${orderTableShipping.address}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">地区：</label>
			<div class="controls">
				<%-- <form:input path="area" htmlEscape="false" maxlength="32" class="input-xlarge "/> --%>
				${orderTableShipping.shippingNo}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">收货人：</label>
			<div class="controls">
				<%-- <form:input path="consignee" htmlEscape="false" maxlength="32" class="input-xlarge "/> --%>
				${orderTableShipping.consignee}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">物流公司：</label>
			<div class="controls">
				<%-- <form:select path="deliveryCorp" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('order_delivery')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select> --%>
				${orderTableShipping.deliveryCorp}
				
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">物流公司代码：</label>
			<div class="controls">
				<%-- <form:input path="deliveryCorpCode" htmlEscape="false" maxlength="32" class="input-xlarge "/> --%>
				${orderTableShipping.deliveryCorpCode}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">物流公司网址：</label>
			<div class="controls">
				<%-- <form:select path="deliveryCorpUrl" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('order_delivery_corp_url')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select> --%>
				${fns:getDictLabel(orderTableShipping.deliveryCorpUrl, 'order_delivery_corp_url', '')}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">物流费用：</label>
			<div class="controls">
				<%-- <form:input path="freight" htmlEscape="false" class="input-xlarge "/> --%>
				${orderTableShipping.freight}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">操作员：</label>
			<div class="controls">
				<%-- <form:input path="operator" htmlEscape="false" maxlength="32" class="input-xlarge "/> --%>
				${orderTableShipping.operator}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">电话：</label>
			<div class="controls">
				<%-- <form:input path="phone" htmlEscape="false" maxlength="32" class="input-xlarge "/> --%>
				${orderTableShipping.phone}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">配送方式：</label>
			<div class="controls">
				<%-- <form:select path="shippingMethod" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('order_Shipping')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					
				</form:select> --%>
				${orderTableShipping.shippingMethod}
				<%-- ${fns:getDictLabel(orderTableShipping.shippingMethod, 'order_Shipping', '')} --%>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">运单号：</label>
			<div class="controls">
				<%-- <form:input path="trackingNo" htmlEscape="false" maxlength="32" class="input-xlarge "/> --%>
				${orderTableShipping.trackingNo}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">邮编：</label>
			<div class="controls">
			<%-- 	<form:input path="zipCode" htmlEscape="false" maxlength="32" class="input-xlarge "/> --%>
				${orderTableShipping.zipCode}
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<%-- <form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/> --%>
				${orderTableShipping.remarks}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">异常备注：</label>
			<div class="controls">
				<%-- <form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/> --%>
				${orderTableShipping.exceptionRemarks}
			</div>
		</div>
	</div>
		<!-- 退货项-->
		<table id="order_shipping_item_tab" style="display:none;width:100%;">
		<tr height="40" class="title" style="background-color: #f1f8ff">
			<td>商品编号</td>
			<td>商品名称</td>
			<td>数量</td>
		</tr>
		<c:forEach items="${orderTableShippingItem}" var="orderTableShippingItem">
			<tr height="40">
					<td class="ordertableshippingitem_${orderTableShippingItem.id} ">
						${orderTableShippingItem.productNo}
					</td>
					<td class="ordertableshippingitem_${orderTableShippingItem.id} ">
						${orderTableShippingItem.name}
					</td>
					<td class="ordertableshippingitem_${orderTableShippingItem.id} ">
						${orderTableShippingItem.quantity}
					</td>
			</tr>
			</c:forEach>
		</table>
			
		<div class="form-actions">
			<%-- <shiro:hasPermission name="order:orderTableShipping:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission> --%>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>