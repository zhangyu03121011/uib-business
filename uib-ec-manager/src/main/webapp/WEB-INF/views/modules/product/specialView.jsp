<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>专题信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript" src="${pageContext.request.contextPath  }/resources/kindeditor-4.1.10/kindeditor.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath  }/resources/kindeditor-4.1.10/themes/default/default.css" />
	<script src="${pageContext.request.contextPath  }/resources/kindeditor-4.1.10/lang/zh_CN.js"></script>
	<script type="text/javascript">
	KindEditor.ready(function(K) {
		var editor = K.editor({
			allowFileManager : true
		});
		K('#image_select_button').click(function() {
			editor.loadPlugin('image', function() {
				editor.plugin.imageDialog({
					imageUrl : K('#showImage').val(),
					clickFn : function(url, title, width, height, border, align) {
						K('#showImage').val(url);
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
		<li><a href="${ctx}/product/special/">专题信息列表</a></li>
		<li class="active"><a href="${ctx}/product/special/specialView?id=${special.id}">专题信息查看</a></li>
	</ul><br/>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/product/special/selectProduct?specialId=${product.specialId}">专题商品查看</a></li>
	</ul>
	<form:form id="inputForm" modelAttribute="special" action="${ctx}/product/special/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">专题名称：</label>
			<div class="controls">
				<form:input path="specialTitle" htmlEscape="false" maxlength="255" class="input required" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">推荐内容：</label>
			<div class="controls">
				<form:textarea path="specialArticle" htmlEscape="false" maxlength="255" class="input required" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">展示图片：</label>
			<div class="controls">
				<form:input path="showImage" htmlEscape="false" maxlength="255" class="input required" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">排序：</label>
			<div class="controls">
				<form:input path="sort" htmlEscape="false" maxlength="32" class="input" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"> 开始日期：</label>
			<div class="controls">
				<input name="beginDate" type="text" readonly="readonly" maxlength="20" class="input Wdate  required"
					value="<fmt:formatDate value="${special.beginDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">结束日期：</label>
			<div class="controls">
				<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input Wdate  required"
					value="<fmt:formatDate value="${special.endDate}" pattern="yyyy-MM-dd HH:mm:ss "/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});" readonly="true"/>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>