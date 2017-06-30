<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head> 
	<title>专题信息管理</title>
	<meta name="decorator" content="default"/>
	<style type="text/css">
		.sortError{
			background: url("${ctxStatic}/images/unchecked.gif") no-repeat 0 0;
			padding-left: 18px;
			padding-bottom: 2px;
			font-weight: bold;
			color: #ea5200;
			margin-left: 10px;
		}
	</style>
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
						var sorterror = $("#sorterror").text();
						if(sorterror.length>0){
							$.jBox.tip("该排序已存在");
						}else{
						loading('正在提交，请稍等...');
						form.submit();
						}
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
		
		function getbysort(){
			var sort = $("#sort").val();
			$.ajax({
				type :'post',
				url :'${ctx}/product/special/getbysort?sort='+sort,
				success :function(date){
					if(date.status){
						$("#sorterror").removeAttr("class");
						$("#sorterror").text("")
					}else{
						$("#sorterror").text("该排序已存在")
						$("#sorterror").attr("class", "sortError");
					}
				}
			});
		}
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/product/special/">专题信息列表</a></li>
		<li class="active"><a href="${ctx}/product/special/form">专题信息添加</a></li>
	</ul><br/>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/product/special/formView?id=${product.specialId}">专题基本信息</a></li>
		<li><a href="${ctx}/product/special/selectProduct?specialId=${product.specialId}">专题商品查看</a></li>
		<c:choose>
			<c:when test="${not empty product.specialId}">
			<li><a href="${ctx}/product/special/productList?specialId=${product.specialId}">专题商品添加</a></li>
			</c:when>
			<c:otherwise>
			<li><a href ="#" name="submit" onclick="$('#inputForm').submit();return false">专题商品添加</a></li>
			</c:otherwise>
		</c:choose>
	</ul>
	<form:form id="inputForm" modelAttribute="special" action="${ctx}/product/special/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">专题名称：</label>
			<div class="controls">
				<form:input path="specialTitle" htmlEscape="false" maxlength="255" class="input required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">推荐内容：</label>
			<div class="controls">
				<form:textarea path="specialArticle" htmlEscape="false" maxlength="255" class="input required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">展示图片：</label>
			<div class="controls">
				<form:input path="showImage" htmlEscape="false" maxlength="255" class="input required"/>
				<input type="button" id="image_select_button" value="选择图片" />
				<!-- <font color="red">图片大小(330*330)</font> -->
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">排序：</label>
			<div class="controls">
				<form:input path="sort" htmlEscape="false" maxlength="32" class="input required number" onblur="getbysort()"/>
				<lable id= "sorterror"></lable>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"> 开始日期：</label>
			<div class="controls">
				<input name="beginDate" type="text" readonly="readonly" maxlength="20" class="input Wdate  required"
					value="<fmt:formatDate value="${special.beginDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">结束日期：</label>
			<div class="controls">
				<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input Wdate  required"
					value="<fmt:formatDate value="${special.endDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="product:special:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>