<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>发货单管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
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
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/order/orderTableShipping/">发货单列表</a></li>
		<li class="active"><a href="${ctx}/order/orderTableShipping/form?id=${orderTableShipping.id}">发货单<shiro:hasPermission name="order:orderTableShipping:edit">${not empty orderTableShipping.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="order:orderTableShipping:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="orderTableShipping" action="${ctx}/order/orderTableShipping/update" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">发货单编号：</label>
			<div class="controls">
				<form:input path="shippingNo" htmlEscape="false" maxlength="32" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">地址：</label>
			<div class="controls">
				<form:input path="address" htmlEscape="false" maxlength="32" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">地区：</label>
			<div class="controls">
				<form:input path="area" htmlEscape="false" maxlength="32" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">收货人：</label>
			<div class="controls">
				<form:input path="consignee" htmlEscape="false" maxlength="32" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">物流公司：</label>
			<div class="controls">
				<form:select path="deliveryCorp" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('order_delivery')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">物流公司代码：</label>
			<div class="controls">
				<form:input path="deliveryCorpCode" htmlEscape="false" maxlength="32" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">物流公司网址：</label>
			<div class="controls">
				<form:select path="deliveryCorpUrl" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('order_delivery_corp_url')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">物流费用：</label>
			<div class="controls">
				<form:input path="freight" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">操作员：</label>
			<div class="controls">
				<form:input path="operator" htmlEscape="false" maxlength="32" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">电话：</label>
			<div class="controls">
				<form:input path="phone" htmlEscape="false" maxlength="32" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">配送方式：</label>
			<div class="controls">
				<form:select path="shippingMethod" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('order_Shipping')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">运单号：</label>
			<div class="controls">
				<form:input path="trackingNo" htmlEscape="false" maxlength="32" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">邮编：</label>
			<div class="controls">
				<form:input path="zipCode" htmlEscape="false" maxlength="32" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">订单编号：</label>
			<div class="controls">
				<form:input path="orderNo" htmlEscape="false" maxlength="32" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
			<div class="control-group">
				<label class="control-label">订单发货关联表：</label>
				<div class="controls">
					<table id="contentTable" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th class="hide"></th>
								<th>订单主键ID</th>
								<shiro:hasPermission name="order:orderTableShipping:edit"><th width="10">&nbsp;</th></shiro:hasPermission>
							</tr>
						</thead>
						<tbody id="orderShippingRefList">
						</tbody>
						<shiro:hasPermission name="order:orderTableShipping:save"><tfoot>
							<tr><td colspan="3"><a href="javascript:" onclick="addRow('#orderShippingRefList', orderShippingRefRowIdx, orderShippingRefTpl);orderShippingRefRowIdx = orderShippingRefRowIdx + 1;" class="btn">新增</a></td></tr>
						</tfoot></shiro:hasPermission>
					</table>
					<script type="text/template" id="orderShippingRefTpl">//<!--
						<tr id="orderShippingRefList{{idx}}">
							<td class="hide">
								<input id="orderShippingRefList{{idx}}_id" name="orderShippingRefList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
								<input id="orderShippingRefList{{idx}}_delFlag" name="orderShippingRefList[{{idx}}].delFlag" type="hidden" value="0"/>
							</td>
							<td>
								<input id="orderShippingRefList{{idx}}_orderTabelId" name="orderShippingRefList[{{idx}}].orderTabelId" type="text" value="{{row.orderTabelId}}" maxlength="64" class="input-small required"/>
							</td>
							<shiro:hasPermission name="order:orderTableShipping:edit"><td class="text-center" width="10">
								{{#delBtn}}<span class="close" onclick="delRow(this, '#orderShippingRefList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
							</td></shiro:hasPermission>
						</tr>//-->
					</script>
					<script type="text/javascript">
						var orderShippingRefRowIdx = 0, orderShippingRefTpl = $("#orderShippingRefTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
						$(document).ready(function() {
							var data = ${fns:toJson(orderTableShipping.orderShippingRefList)};
							for (var i=0; i<data.length; i++){
								addRow('#orderShippingRefList', orderShippingRefRowIdx, orderShippingRefTpl, data[i]);
								orderShippingRefRowIdx = orderShippingRefRowIdx + 1;
							}
						});
					</script>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">发货项：</label>
				<div class="controls">
					<table id="contentTable" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th class="hide"></th>
								<th>商品名称</th>
								<th>商品数量</th>
								<th>发货单编号</th>
								<th>备注信息</th>
								<shiro:hasPermission name="order:orderTableShipping:edit"><th width="10">&nbsp;</th></shiro:hasPermission>
							</tr>
						</thead>
						<tbody id="orderTableShippingItemList">
						</tbody>
						<shiro:hasPermission name="order:orderTableShipping:save"><tfoot>
							<tr><td colspan="6"><a href="javascript:" onclick="addRow('#orderTableShippingItemList', orderTableShippingItemRowIdx, orderTableShippingItemTpl);orderTableShippingItemRowIdx = orderTableShippingItemRowIdx + 1;" class="btn">新增</a></td></tr>
						</tfoot></shiro:hasPermission>
					</table>
					<script type="text/template" id="orderTableShippingItemTpl">//<!--
						<tr id="orderTableShippingItemList{{idx}}">
							<td class="hide">
								<input id="orderTableShippingItemList{{idx}}_id" name="orderTableShippingItemList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
								<input id="orderTableShippingItemList{{idx}}_delFlag" name="orderTableShippingItemList[{{idx}}].delFlag" type="hidden" value="0"/>
							</td>
							<td>
								<input id="orderTableShippingItemList{{idx}}_name" name="orderTableShippingItemList[{{idx}}].name" type="text" value="{{row.name}}" maxlength="32" class="input-small "/>
							</td>
							<td>
								<input id="orderTableShippingItemList{{idx}}_quantity" name="orderTableShippingItemList[{{idx}}].quantity" type="text" value="{{row.quantity}}" maxlength="11" class="input-small "/>
							</td>
							<td>
								<input id="orderTableShippingItemList{{idx}}_shippingNo" name="orderTableShippingItemList[{{idx}}].shippingNo" type="text" value="{{row.shippingNo}}" maxlength="32" class="input-small "/>
							</td>
							<td>
								<textarea id="orderTableShippingItemList{{idx}}_remarks" name="orderTableShippingItemList[{{idx}}].remarks" rows="4" maxlength="255" class="input-small ">{{row.remarks}}</textarea>
							</td>
							<shiro:hasPermission name="order:orderTableShipping:edit"><td class="text-center" width="10">
								{{#delBtn}}<span class="close" onclick="delRow(this, '#orderTableShippingItemList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
							</td></shiro:hasPermission>
						</tr>//-->
					</script>
					<script type="text/javascript">
						var orderTableShippingItemRowIdx = 0, orderTableShippingItemTpl = $("#orderTableShippingItemTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
						$(document).ready(function() {
							var data = ${fns:toJson(orderTableShipping.orderTableShippingItemList)};
							for (var i=0; i<data.length; i++){
								addRow('#orderTableShippingItemList', orderTableShippingItemRowIdx, orderTableShippingItemTpl, data[i]);
								orderTableShippingItemRowIdx = orderTableShippingItemRowIdx + 1;
							}
						});
					</script>
				</div>
			</div>
		<div class="form-actions">
			<shiro:hasPermission name="order:orderTableShipping:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>