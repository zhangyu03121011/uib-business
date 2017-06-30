<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>退货单管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(
			function() {
				$("#btnExport").click(
						function() {
							$("#searchForm").attr("action",
									"${ctx}/order/orderTableReturns/export")
									.submit();
						});
				$("#btnSubmit").click(
						function() {
							$("#searchForm").attr("action",
									"${ctx}/order/orderTableReturns/list")
									.submit();
						});
			});
	function page(n, s) {
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
		return false;
	}
</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/order/orderTableReturns/">退货单列表</a></li>
		<shiro:hasPermission name="order:orderTableReturns:save">
			<li><a href="${ctx}/order/orderTableReturns/form">退货单添加</a></li>
		</shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="orderTableReturns"
		action="${ctx}/order/orderTableReturns/updateStatus" method="post"
		class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
		<input id="pageSize" name="pageSize" type="hidden"
			value="${page.pageSize}" />
		<ul class="ul-form">
			<li><label>订单编号：</label> <form:input path="orderNo"
					htmlEscape="false" maxlength="32" class="input-medium" /></li>
		    <li>
		    	<label>供应商名称：</label> 
		    	<sys:treeselect id="supplier" name="supplierId" value="${supplier.id}" labelName="supplier.companyName" labelValue="${supplier.companyName}" title="供应商"
						url="/supplier/supplier/treeData" module="product" notAllowSelectRoot="false" cssClass="input-small required" /><span class="help-inline"><font color="red">*</font> </span>
<%-- 		    	<form:input path="companyName" htmlEscape="false" maxlength="32" class="input-medium" /></li> --%>
			<li><label>退货类型</label> <form:select path="returnType"
					class="input-small">
					<form:option value="0" label="请选择" />
					<form:option value="1" label="退款" />
					<form:option value="2" label="退货" />
					<form:option value="3" label="换货" />
				</form:select></li>
			<li><label>退货时间：</label> <input name="applyBginTime" type="text"
				readonly="readonly" maxlength="20" class="input-medium Wdate"
				value="<fmt:formatDate value="${orderTableReturns.applyBginTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
				onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});" />—<input
				name="applyEndTime" type="text" readonly="readonly" maxlength="20"
				class="input-medium Wdate"
				value="<fmt:formatDate value="${orderTableReturns.applyEndTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
				onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});" />
			</li>
			<li><label>退货状态</label> <form:select path="returnStatus"
					class="input-small">
					<form:option value="0" label="请选择" />
					<form:option value="1" label="已处理" />
					<form:option value="2" label="无法退货" />
					<form:option value="3" label="未处理" />
				</form:select></li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary"
				type="button" value="查询" /></li>
			<li class="btns"><input id="btnExport" class="btn btn-primary"
				type="button" value="导出" /></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}" />
	<table id="contentTable"
		class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>退货单编号</th>
				<th>订单编号</th>
				<th>用户名</th>
				<th>手机号</th>
				<th>退货类型</th>
				<th>供应商公司名称</th>
				<th>退货状态</th>
				<th>申请时间</th>
				<shiro:hasPermission name="order:orderTableReturns:edit">
					<th>操作</th>
				</shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="orderTableReturns">
				<tr>
					<td>${orderTableReturns.returnNo}</td>
					<td><a
						href="${ctx}/order/orderTable/form?id=${orderTableReturns.orderTableId}">${orderTableReturns.orderNo}</a></td>
					<td>${orderTableReturns.userName}</td>
					<td>${orderTableReturns.phone}</td>
					<td><c:if test="${orderTableReturns.returnType==1}">退款</c:if>
						<c:if test="${orderTableReturns.returnType==2}">退货</c:if> <c:if
							test="${orderTableReturns.returnType==3}">换货</c:if></td>
					<td>${orderTableReturns.companyName}</td>
					<td><c:if test="${orderTableReturns.returnStatus==1}">已处理</c:if>
						<c:if test="${orderTableReturns.returnStatus==2}">无法退货</c:if> <c:if
							test="${orderTableReturns.returnStatus==3}">未处理</c:if></td>
					<td><fmt:formatDate value="${orderTableReturns.applyTime}"
							pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<shiro:hasPermission name="order:orderTableReturns:edit">
						<td><a
							href="${ctx}/order/orderTableReturns/delete?id=${orderTableReturns.id}"
							onclick="return confirmx('确认要删除该退货单吗？', this.href)">删除</a> <a
							href="${ctx}/order/orderTableReturns/form?id=${orderTableReturns.id}&operaTion=detail">退货详情</a>
							<a
							href="${ctx}/order/orderTableReturns/form?id=${orderTableReturns.id}&operaTion=edit">退货编辑</a></td>
					</shiro:hasPermission>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>