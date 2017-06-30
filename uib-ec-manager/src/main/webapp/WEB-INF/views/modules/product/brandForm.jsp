<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>品牌管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript" src="${pageContext.request.contextPath  }/resources/kindeditor-4.1.10/kindeditor.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath  }/resources/kindeditor-4.1.10/themes/default/default.css" />
	<script src="${pageContext.request.contextPath  }/resources/kindeditor-4.1.10/lang/zh_CN.js"></script>
	<script type="text/javascript">
		KindEditor.ready(function(K) {
		K.create('textarea[name="introduce"]', {
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
					imageUrl : K('#logo').val(),
					clickFn : function(url, title, width, height, border, align) {
						K('#logo').val(url);
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
		<li><a href="${ctx}/product/brand/">品牌列表</a></li>
		<li class="active"><a href="${ctx}/product/brand/form?id=${brand.id}"><shiro:hasPermission name="product:brand:edit">${not empty brand.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="product:brand:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="brand" action="${ctx}/product/brand/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="32" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">logo：</label>
			<div class="controls">
				<form:input path="logo" htmlEscape="false" maxlength="255" class="input-xlarge "/>
				<input id="file_select_button" type="button" value="选择图片"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">网址：</label>
			<div class="controls">
				<form:input path="url" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商品分类：</label>
			<div class="controls">
			<sys:treeselect id="productCategoryId" name="productCategoryId" value="${productCategory.id}" labelName="productCategory.name" labelValue="${productCategory.name}"
					title="商品分类" url="/product/productCategory/treeData" module="product" notAllowSelectRoot="false" cssClass="input-small"/>
			</div>
		</div>		
		<div class="control-group">
			<label class="control-label">排序：</label>
			<div class="controls">
				<form:input path="orders" htmlEscape="false" maxlength="11" class="input-xlarge  digits"/>
			</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">商户编号：</label>
			<div class="controls">
				<form:input path="merchantNo" htmlEscape="false" maxlength="32" class="input-xlarge "/>
			</div>
		</div> --%>
		<div class="control-group">
			<label class="control-label">品牌介绍：</label>
			<div class="controls">
				<form:textarea path="introduce" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="product:brand:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>