<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>退货单管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	var tables = [ "base_info_tab", "order_returns_item_tab" ];
	$(document).ready(
			function() {
				//$("#name").focus();
				$("#inputForm")
						.validate(
								{
									submitHandler : function(form) {
										loading('正在提交，请稍等...');
										form.submit();
									},
									errorContainer : "#messageBox",
									errorPlacement : function(error, element) {
										$("#messageBox").text("输入有误，请先更正。");
										if (element.is(":checkbox")
												|| element.is(":radio")
												|| element.parent().is(
														".input-append")) {
											error.appendTo(element.parent()
													.parent());
										} else {
											error.insertAfter(element);
										}
									}
								});
			});
	function addRow(list, idx, tpl, row) {
		$(list).append(Mustache.render(tpl, {
			idx : idx,
			delBtn : true,
			row : row
		}));
		$(list + idx).find("select").each(function() {
			$(this).val($(this).attr("data-value"));
		});
		$(list + idx).find("input[type='checkbox'], input[type='radio']").each(
				function() {
					var ss = $(this).attr("data-value").split(',');
					for (var i = 0; i < ss.length; i++) {
						if ($(this).val() == ss[i]) {
							$(this).attr("checked", "checked");
						}
					}
				});
	}
	function delRow(obj, prefix) {
		var id = $(prefix + "_id");
		var delFlag = $(prefix + "_delFlag");
		if (id.val() == "") {
			$(obj).parent().parent().remove();
		} else if (delFlag.val() == "0") {
			delFlag.val("1");
			$(obj).html("&divide;").attr("title", "撤销删除");
			$(obj).parent().parent().addClass("error");
		} else if (delFlag.val() == "1") {
			delFlag.val("0");
			$(obj).html("&times;").attr("title", "删除");
			$(obj).parent().parent().removeClass("error");
		}
	}

	//切换菜单
	$(function() {
		$("#inputForm ul > li").click(function() {
			$(this).addClass("active").siblings().removeClass("active");
			var table_id = $(this).attr("id").replace("li", "tab");
			$("#" + table_id).attr("style", "display:table;width:100%");
			for (var i = 0; i < tables.length; i++) {
				if (tables[i] == table_id) {
					continue;
				}
				$("#" + tables[i]).attr("style", "display:none");
			}
		});
	});
</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/order/orderTableReturns/">退货单列表</a></li>
		<li class="active"><a
			href="${ctx}/order/orderTableReturns/form?id=${orderTableReturns.id}">退货单${orderTableReturns.operaTion eq 'edit'? '编辑':'详情'}</a></li>
	</ul>
	<br />
	<form:form id="inputForm" modelAttribute="orderTableReturns"
		action="${ctx}/order/orderTableReturns/updateStatus" method="post"
		class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>

		<div id="base_info_tab">
			<div class="control-group">
				<label class="control-label">退货单编号：</label>
				<div class="controls">
					<form:input path="returnNo" htmlEscape="false" maxlength="32"
						class="input-xlarge " readonly="true" />
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">订单编号：</label>
				<div class="controls">
					<form:input path="orderNo" htmlEscape="false" maxlength="32"
						class="input-xlarge required" readonly="true" />
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">用户名：</label>
				<div class="controls">
					<form:input path="userName" htmlEscape="false" maxlength="32"
						class="input-xlarge required" readonly="true" />
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">退货类型：</label>
				<div class="controls">
					<form:select path="returnType" class="input-small" disabled="true">
						<form:option value="1" label="退款" />
						<form:option value="2" label="退货" />
						<form:option value="3" label="换货" />
					</form:select>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">商品编号：</label>
				<div class="controls">
					<form:input path="productId" htmlEscape="false" maxlength="32"
						class="input-xlarge required" readonly="true" />
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">退货原因：</label>
				<div class="controls">
					<form:input path="returnReason" htmlEscape="false"
						class="input-xlarge " readonly="true" />
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">供应商名称：</label>
				<div class="controls">
					<form:input path="companyName" htmlEscape="false" maxlength="32"
						class="input-xlarge" readonly="true" />
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">退款金额：</label>
				<div class="controls">
					<form:input path="returnSum" htmlEscape="false" maxlength="32"
						class="input-xlarge required" readonly="true" />
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">退货时间：</label>
				<div class="controls">
					<fmt:formatDate value="${orderTableReturns.applyTime}"
						pattern="yyyy-MM-dd" />
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">退货说明：</label>
				<div class="controls">
					<form:input path="returnReason" htmlEscape="false" maxlength="32"
						class="input-xlarge required" readonly="true" />
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">绑定银行卡手机号：</label>
				<div class="controls">
					<form:input path="withdrawalApplyFor.applyPhone" htmlEscape="false" maxlength="32"
						class="input-xlarge required" readonly="true" />
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">提现银行：</label>
				<div class="controls">
					<form:input path="withdrawalApplyFor.bankName" htmlEscape="false" maxlength="32"
						class="input-xlarge required" readonly="true" />
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">所属省市：</label>
				<div class="controls">
					<form:input path="withdrawalApplyFor.provinceCity" htmlEscape="false" maxlength="32"
						class="input-xlarge required" readonly="true" />
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">所属分行：</label>
				<div class="controls">
					<form:input path="withdrawalApplyFor.branchBankName" htmlEscape="false" maxlength="32"
						class="input-xlarge required" readonly="true" />
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">绑定银行卡号：</label>
				<div class="controls">
					<form:input path="withdrawalApplyFor.cardNo" htmlEscape="false" maxlength="32"
						class="input-xlarge required" readonly="true" />
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">持卡人姓名：</label>
				<div class="controls">
					<form:input path="withdrawalApplyFor.cardPersonName" htmlEscape="false" maxlength="32"
						class="input-xlarge required" readonly="true" />
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">退货状态：</label>
				<div class="controls">
					<form:select path="returnStatus" class="input-small"
						disabled="${orderTableReturns.operaTion eq 'edit'? false:true}">
						<form:option value="1" label="已处理" />
						<form:option value="2" label="无法退货" />
						<form:option value="3" label="未处理" />
					</form:select>
				</div>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">图片凭证：</label>
			<div class="controls">
				<c:forEach items="${orderTableReturns.orderTableReturnsItemList}"
					var="item">
					<img src="${item.imgWebUrl}" width="80" height="80">
				</c:forEach>
			</div>
		</div>

		<div class="form-actions">
			<c:if test="${orderTableReturns.operaTion eq 'edit'}">
				<input id="btnCancel" class="btn" type="submit" value="确定" />
			</c:if>
			<c:if test="${orderTableReturns.operaTion ne 'edit'}">
				<input id="btnCancel" class="btn" type="button" value="返 回"
					onclick="history.go(-1)" />
			</c:if>
		</div>
	</form:form>
</body>
</html>