<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>会员卖家信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/mem/memMerchant/">会员卖家信息列表</a></li>
		<shiro:hasPermission name="mem:memMerchant:save"><li><a href="${ctx}/mem/memMerchant/form">会员卖家信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="memMerchant" action="${ctx}/mem/memMerchant/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<%-- <li><label>商户编号：</label>
				<form:input path="merchantNo" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li> --%>
			<li><label>商户名称：</label>
				<form:input path="merchantName" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>入驻时间：</label>
				<input name="registerDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${memMerchant.registerDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>有效期时间：</label>
				<input name="effectiveDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${memMerchant.effectiveDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>模板名称：</label>
				<form:input path="templateNum" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li><label>主页地址：</label>
				<form:input path="merchantPage" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li><label>联系人姓名：</label>
				<form:input path="contactName" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>联系人邮箱：</label>
				<form:input path="email" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li><label>手机号：</label>
				<form:input path="phone" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<!-- <th>商户编号</th> -->
				<th>商户名称</th>
				<th>入驻时间</th>
				<th>有效期时间</th>
				<th>主页地址</th>
				<th>联系人姓名</th>
				<th>联系人邮箱</th>
				<th>手机号</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="mem:memMerchant:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="memMerchant">
			<tr>
				<%-- <td><a href="${ctx}/mem/memMerchant/form?id=${memMerchant.id}">
					${memMerchant.merchantNo}
				</a></td> --%>
				<td>
					${memMerchant.merchantName}
				</td>
				<td>
					<fmt:formatDate value="${memMerchant.registerDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${memMerchant.effectiveDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${memMerchant.merchantPage}
				</td>
				<td>
					${memMerchant.contactName}
				</td>
				<td>
					${memMerchant.email}
				</td>
				<td>
					${memMerchant.phone}
				</td>
				<td>
					<fmt:formatDate value="${memMerchant.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${memMerchant.remarks}
				</td>
				<shiro:hasPermission name="mem:memMerchant:edit"><td>
    				<a href="${ctx}/mem/memMerchant/form?id=${memMerchant.id}">修改</a>
					<a href="${ctx}/mem/memMerchant/delete?id=${memMerchant.id}" onclick="return confirmx('确认要删除该会员卖家信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>