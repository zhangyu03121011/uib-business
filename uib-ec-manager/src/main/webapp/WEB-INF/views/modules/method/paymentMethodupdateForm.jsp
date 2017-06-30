<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>支付方式管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript" src="${pageContext.request.contextPath  }/resources/kindeditor-4.1.10/kindeditor.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath  }/resources/kindeditor-4.1.10/themes/default/default.css" />
	<script src="${pageContext.request.contextPath  }/resources/kindeditor-4.1.10/lang/zh_CN.js"></script>
	<script type="text/javascript">
	KindEditor.ready(function(K) {
		K.create('textarea[name="content"]', {
			uploadJson : '${pageContext.request.contextPath  }/resources/kindeditor-4.1.10/jsp/upload_json.jsp',
		                fileManagerJson : '${pageContext.request.contextPath  }/resources/kindeditor-4.1.10/jsp/file_manager_json.jsp',
		                allowFileManager : true,
		                allowImageUpload : true, 
		    width : "100%",
			autoHeightMode : true,
			afterCreate : function() {this.loadPlugin('autoheight');},
			afterBlur : function(){ this.sync(); }  //Kindeditor下获取文本框信息
		});
		var editor = K.editor({
			allowFileManager : true
		});
		K('#file_select_button').click(function() {
			editor.loadPlugin('image', function() {
				editor.plugin.imageDialog({
					imageUrl : K('#icon').val(),
					clickFn : function(url, title, width, height, border, align) {
						K('#icon').val(url);
						editor.hideDialog();
					}
				});
			});
		});
	});
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
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/method/paymentMethod/">支付方式列表</a></li>
		<li class="active"><a href="${ctx}/method/paymentMethod/form?id=${paymentMethod.id}">支付方式<shiro:hasPermission name="method:paymentMethod:edit">${not empty paymentMethod.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="method:paymentMethod:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="paymentMethod" action="${ctx}/method/paymentMethod/update" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label"><font color="#ff6d6d" >*</font>名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="32" class="input "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">方式：</label>
			<div class="controls">
				<select id="method" name="method" data-value="{{row.orderShipping}}" class="input-small ">
					<option value="">请选择</option>
					<c:forEach items="${list}" var="payMethod">
							<c:choose>
								<c:when test="${paymentMethod.method == payMethod.key}">
									<option value="${payMethod.key}" selected="selected">
									${payMethod.value}</option>
								</c:when>
								<c:otherwise>
									<option value="${payMethod.key}">
									${payMethod.value}</option>
								</c:otherwise>
							</c:choose>
					</c:forEach>
				</select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">超时时间：</label>
			<div class="controls">
				<form:input path="timeout" htmlEscape="false" maxlength="32" class="input digits"/>
			</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">支持配送方式：</label>
			<div class="controls">
				<c:forEach items="${shippingMethods}" var="shippingMethod">
					<input type="checkbox" name="shippingMethodIds"   value="${shippingMethod.id}" />${shippingMethod.name}
				</c:forEach>
			</div>
		</div> --%>
		<div class="control-group">
			<label class="control-label">图标：</label>
			<div class="controls">
				<form:input path="icon" htmlEscape="false" maxlength="255" class="input "/>
				<input id="file_select_button" type="button" value="选择图片"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">介绍：</label>
			<div class="controls">
			<form:input path="description" htmlEscape="false" maxlength="64" class="input"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">排序：</label>
			<div class="controls">
				<form:input path="orders" htmlEscape="false" maxlength="64" class="input digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">内容：</label>
			<div class="controls">
				<form:textarea path="content" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="method:paymentMethod:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>