<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>导航管理管理</title>
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
			$("#linkSelectUrl").change(function(){
				$("#navigation_url").val($("#linkSelectUrl").val());
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/navigation/">导航管理列表</a></li>
		<li class="active"><a href="${ctx}/navigation/form?id=${navigation.id}">导航管理<shiro:hasPermission name="navigation:navigation:edit">${not empty navigation.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="navigation:navigation:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="navigation" action="${ctx}/navigation/update" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">系统内容：</label>
			<div class="controls">
				<form:select path="" class="input-xlarge " id="linkSelectUrl">
					<form:option value="" label="------------"/>
					<form:option value="${base}/" label="首页"/>
				    <form:option value="${base}/product_category" label="商品分类"/>
					<form:option value="${base}/friend_link" label="友情链接"/>
					<form:option value="${base}/member/" label="会员中心"/>
					<c:forEach items="${productCateGoryList}" var="productCateGory" varStatus="status">
						<form:option value="${productCateGory.path}" label="${productCateGory.name}"/>
					</c:forEach>
					<c:forEach items="${cateGoryList}" var="cateGory">
						<form:option value="/f/list-${cateGory.id}${fns:getUrlSuffix()}" label="${cateGory.name}"/>
					</c:forEach>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">链接地址：</label>
			<div class="controls">
				<form:input path="url" id="navigation_url" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">位置：</label>
			<div class="controls">
				<form:select path="position" class="input-xlarge ">
					<form:option value="" label=""/>
					<c:forEach items="${positions}" var="position" varStatus="status">
						<form:option value="${position.index}" label="${position.name}"/>
					</c:forEach>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">所属标签：</label>
			<div class="controls">
				<form:select path="tag" class="input-xlarge ">
					<form:option value="" label="------------"/>
					<c:forEach items="${tags}" var="tag">
						<form:option value="${tag.index}" label="${tag.description}"/>
					</c:forEach>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否新窗口打开：</label>
			<div class="controls">
				<form:radiobutton path="isBlankTarget" value="1"/>是
				<form:radiobutton path="isBlankTarget" value="0"/>否
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">排序：</label>
			<div class="controls">
				<form:input path="orders" htmlEscape="false" maxlength="11" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="navigation:navigation:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>