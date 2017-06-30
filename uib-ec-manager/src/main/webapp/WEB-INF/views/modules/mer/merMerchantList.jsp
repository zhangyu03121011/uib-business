<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>单表管理</title>
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
		<li class="active"><a href="${ctx}/mer/merMerchant/">单表列表</a></li>
		<shiro:hasPermission name="mer:merMerchant:edit"><li><a href="${ctx}/mer/merMerchant/form">单表添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="merMerchant" action="${ctx}/mer/merMerchant/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<%-- <li><label>商户号：</label>
				<form:input path="merchantNo" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li> --%>
			<li><label>商户名称：</label>
				<form:input path="merchantName" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li><label>入驻时间：</label>
				<input name="registerDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${merMerchant.registerDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>是否停用：</label>
				<form:select path="isCancel" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>有效期时间：</label>
				<input name="effectiveDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${merMerchant.effectiveDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>模板名称：</label>
				<form:input path="templateNum" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li><label>主页地址：</label>
				<form:input path="merchantPage" htmlEscape="false" maxlength="500" class="input-medium"/>
			</li>
			<li><label>联系人姓名：</label>
				<form:input path="contactName" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li><label>联系人邮箱：</label>
				<form:input path="email" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li><label>联系人电话：</label>
				<form:input path="phone" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<!-- <th>商户号</th> -->
				<th>商户名称</th>
				<th>入驻时间</th>
				<th>是否停用</th>
				<th>有效期时间</th>
				<th>模板名称</th>
				<th>主页地址</th>
				<th>联系人姓名</th>
				<th>联系人邮箱</th>
				<th>联系人电话</th>
				<shiro:hasPermission name="mer:merMerchant:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="merMerchant">
			<tr>
				<%-- <td><a href="${ctx}/mer/merMerchant/form?id=${merMerchant.id}">
					${merMerchant.merchantNo}
				</a></td> --%>
				<td>
					${merMerchant.merchantName}
				</td>
				<td>
					<fmt:formatDate value="${merMerchant.registerDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${fns:getDictLabel(merMerchant.isCancel, '', '')}
				</td>
				<td>
					<fmt:formatDate value="${merMerchant.effectiveDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${merMerchant.templateNum}
				</td>
				<td>
					${merMerchant.merchantPage}
				</td>
				<td>
					${merMerchant.contactName}
				</td>
				<td>
					${merMerchant.email}
				</td>
				<td>
					${merMerchant.phone}
				</td>
				<shiro:hasPermission name="mer:merMerchant:edit"><td>
    				<a href="${ctx}/mer/merMerchant/form?id=${merMerchant.id}">修改</a>
					<a href="${ctx}/mer/merMerchant/delete?id=${merMerchant.id}" onclick="return confirmx('确认要删除该单表吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>