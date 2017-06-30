<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>广告管理管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript" src="${pageContext.request.contextPath  }/resources/kindeditor-4.1.10/kindeditor.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath  }/resources/kindeditor-4.1.10/themes/default/default.css" />
	<script src="${pageContext.request.contextPath  }/resources/kindeditor-4.1.10/lang/zh_CN.js"></script>
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
			$("#ad_type").change(function(){
				if(parseInt(this.value)==0){
					$("#ad_content_div").show();
					$("#ad_path_div,#app_ad_url,#app_ad_path_div,#wx_ad_url,#wx_ad_path_div").hide();
				}else{
					$("#ad_content_div").hide();
					$("#ad_path_div,#app_ad_url,#app_ad_path_div,#wx_ad_url,#wx_ad_path_div").show();
				}
			});
		});
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
						imageUrl : K('#path').val(),
						clickFn : function(url, title, width, height, border, align) {
							K('#path').val(url);
							editor.hideDialog();
						}
					});
				});
			});
			K('#app_file_select_button').click(function() {
				editor.loadPlugin('image', function() {
					editor.plugin.imageDialog({
						imageUrl : K('#appPath').val(),
						clickFn : function(url, title, width, height, border, align) {
							K('#appPath').val(url);
							editor.hideDialog();
						}
					});
				});
			});
			K('#wx_file_select_button').click(function() {
				editor.loadPlugin('image', function() {
					editor.plugin.imageDialog({
						imageUrl : K('#wxPath').val(),
						clickFn : function(url, title, width, height, border, align) {
							K('#wxPath').val(url);
							editor.hideDialog();
						}
					});
				});
			});
			});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${pageContext.request.contextPath  }/a/ad/advertisement/">广告管理列表</a></li>
		<li class="active"><a href="${pageContext.request.contextPath  }/a/ad/advertisement/form?id=${advertisement.id}">广告管理<shiro:hasPermission name="ad:advertisement:edit">${not empty advertisement.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="ad:advertisement:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="advertisement" action="${pageContext.request.contextPath  }/a/ad/advertisement/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">标题：</label>
			<div class="controls">
				<form:input path="title" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">类型：</label>
			<div class="controls">
				<form:select path="type" class="input-xlarge " id="ad_type">
					<form:options items="${fns:getDictList('advertisement_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">广告位：</label>
			<div class="controls">
				<form:select path="adPositionId" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${positionList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group" id="ad_path_div" style="display:none">
			<label class="control-label">PC路径：</label>
			<div class="controls">
				<form:input path="path" htmlEscape="false" maxlength="255" class="input-xlarge "/>
				<input id="file_select_button" type="button" value="选择图片"/>
			</div>
		</div>
		<div class="control-group" id="ad_content_div">
			<label class="control-label">内容：</label>
			<div class="controls">
				<form:textarea path="content" htmlEscape="false" rows="4" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">链接地址：</label>
			<div class="controls">
				<form:input path="url" htmlEscape="false" maxlength="255" class="input-xlarge url"/>
			</div>
		</div>
		<div class="control-group" id="app_ad_path_div" style="display:none">
			<label class="control-label">APP路径：</label>
			<div class="controls">
				<form:input path="appPath" htmlEscape="false" maxlength="255" class="input-xlarge "/>
				<input id="app_file_select_button" type="button" value="选择图片"/>
			</div>
		</div>
		<div class="control-group" id="app_ad_url" style="display:none">
			<label class="control-label">APP链接地址：</label>
			<div class="controls">
				<form:input path="appUrl" htmlEscape="false" maxlength="255" class="input-xlarge url"/>
			</div>
		</div>
		<div class="control-group" id="wx_ad_path_div" style="display:none">
			<label class="control-label">微信路径：</label>
			<div class="controls">
				<form:input path="wxPath" htmlEscape="false" maxlength="255" class="input-xlarge "/>
				<input id="wx_file_select_button" type="button" value="选择图片"/>
			</div>
		</div>
		<div class="control-group" id="wx_ad_url" style="display:none">
			<label class="control-label">微信链接地址：</label>
			<div class="controls">
				<form:input path="wxUrl" htmlEscape="false" maxlength="255" class="input-xlarge url"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">排序：</label>
			<div class="controls">
				<form:input path="orders" htmlEscape="false" maxlength="11" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">起始日期：</label>
			<div class="controls">
				<input name="beginDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${advertisement.beginDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">结束日期：</label>
			<div class="controls">
				<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${advertisement.endDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="ad:advertisement:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>