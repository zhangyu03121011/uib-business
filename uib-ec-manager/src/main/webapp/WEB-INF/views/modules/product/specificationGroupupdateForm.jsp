<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商品规格管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript" src="${pageContext.request.contextPath  }/resources/kindeditor-4.1.10/kindeditor.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath  }/resources/kindeditor-4.1.10/themes/default/default.css" />
	<script src="${pageContext.request.contextPath  }/resources/kindeditor-4.1.10/lang/zh_CN.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					var productCategoryId = $("#productCategoryId").val();
					if(typeof(productCategoryId)=='undefined' || productCategoryId==''){
						$.jBox.tip("请选择商品分类!");
						return false;
					}
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
			KindEditor.ready(function(K) {
				var editor = K.editor({
					allowFileManager : true
				});
				$('#productSpecificationList').delegate("tr td input[type=\"button\"]","click",function() {
					console.info($(this).val()+","+$(this).prev().attr("id"));
					var url_id = $(this).prev().attr("id");
					editor.loadPlugin('image', function() {
						editor.plugin.imageDialog({
							imageUrl : K('#'+url_id).val(),
							clickFn : function(url, title, width, height, border, align) {
								K('#'+url_id).val(url);
								editor.hideDialog();
							}
						});
					});
				});
			});
			$("#specification_content_type").change(function(){
				if(parseInt($(this).val())==0){
					$("#productSpecificationList tr").each(function(index){
						$(this).find("td:eq(2) input").attr("disabled","disabled");
					});
				}else{
					$("#productSpecificationList tr").each(function(index){
						$(this).find("td:eq(2) input").removeAttr("disabled");
					});
				}
			});
		});
		function addRow(list, idx, tpl, row){
			$(list).append(Mustache.render(tpl, {
				idx: idx, delBtn: true, row: row
			}));
			$(list+idx).find("select").each(function(){
				$(this).val($(this).attr("data-value"));
			});
			$(list+idx).find("input[type='checkbox'], input[type='radio']").each(function(){
				var ss = $(this).attr("data-value").split(',');
				for (var i=0; i<ss.length; i++){
					if($(this).val() == ss[i]){
						$(this).attr("checked","checked");
					}
				}
			});
			if(parseInt($("#specification_content_type").val())==0){
				$("#productSpecificationList tr").each(function(index){
					$(this).find("td:eq(2) input").attr("disabled","disabled");
				});
			}else{
				$("#productSpecificationList tr").each(function(index){
					$(this).find("td:eq(2) input").removeAttr("disabled");
				});
			}
		}
		function delRow(obj, prefix){
			var id = $(prefix+"_id");
			var delFlag = $(prefix+"_delFlag");
			if (id.val() == ""){
				$(obj).parent().parent().remove();
			}else if(delFlag.val() == "0"){
				delFlag.val("1");
				$(obj).html("&divide;").attr("title", "撤销删除");
				$(obj).parent().parent().addClass("error");
			}else if(delFlag.val() == "1"){
				delFlag.val("0");
				$(obj).html("&times;").attr("title", "删除");
				$(obj).parent().parent().removeClass("error");
			}
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/product/specificationGroup/">规格列表</a></li>
		<li class="active"><a href="${ctx}/product/specificationGroup/form?id=${specificationGroup.id}"><shiro:hasPermission name="product:specificationGroup:edit">${not empty specificationGroup.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="product:specificationGroup:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="specificationGroup" action="${ctx}/product/specificationGroup/update" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="32"  class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">规格类型：</label>
			<div class="controls">
				<form:select path="contentType" class="input-xlarge " id="specification_content_type">
					<form:options items="${contentTypes}" itemLabel="description" itemValue="index" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">排序：</label>
			<div class="controls">
				<form:input path="orders" htmlEscape="false" maxlength="11" class="input-xlarge  digits"/>
			</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">商户号：</label>
			<div class="controls">
				<form:input path="merchantNo" htmlEscape="false" maxlength="32" class="input-xlarge "/>
			</div>
		</div> --%>
		<div class="control-group">
			<label class="control-label">商品分类：</label>
			<div class="controls">
			<sys:treeselect id="productCategory" name="productCategoryId" value="${productCategory.id}" labelName="productCategory.name" labelValue="${productCategory.name}"
					title="商品分类" url="/product/productCategory/treeData" module="product" notAllowSelectRoot="false" cssClass="" allowClear="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
			<div class="control-group">
				<label class="control-label">商品规格：</label>
				<div class="controls">
					<table id="contentTable" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th class="hide"></th>
								<th>规格名称</th>
								<th>文件路径</th>
								<th>备注信息</th>
								<th>排序</th>
								<shiro:hasPermission name="product:specificationGroup:edit"><th width="10">&nbsp;</th></shiro:hasPermission>
							</tr>
						</thead>
						<tbody id="productSpecificationList">
						</tbody>
						<shiro:hasPermission name="product:specificationGroup:save"><tfoot>
							<tr><td colspan="6"><a href="javascript:" onclick="addRow('#productSpecificationList', productSpecificationRowIdx, productSpecificationTpl);productSpecificationRowIdx = productSpecificationRowIdx + 1;" class="btn">新增</a></td></tr>
						</tfoot></shiro:hasPermission>
					</table>
					<script type="text/template" id="productSpecificationTpl">//<!--
						<tr id="productSpecificationList{{idx}}">
							<td class="hide">
								<input id="productSpecificationList{{idx}}_id" name="productSpecificationList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
								<input id="productSpecificationList{{idx}}_delFlag" name="productSpecificationList[{{idx}}].delFlag" type="hidden" value="0"/>
							</td>
							<td>
								<input id="productSpecificationList{{idx}}_name"   class="required"   name="productSpecificationList[{{idx}}].name" type="text" value="{{row.name}}" maxlength="32" class="input-small "/>
							</td>
							<td>
								<input id="productSpecificationList{{idx}}_filePath" type="text" name="productSpecificationList[{{idx}}].filePath" value="{{row.filePath}}" maxlength="255" class="input-small " disabled="disabled"/>
								<input id="productSpecificationList{{idx}}_button" type="button" value="选择图片" disabled="disabled"/>
							</td>
							<td>
								<textarea id="productSpecificationList{{idx}}_remarks" name="productSpecificationList[{{idx}}].remarks" rows="4" maxlength="255" class="input-small ">{{row.remarks}}</textarea>
							</td>
							<td>
								<input id="productSpecificationList{{idx}}_orders" name="productSpecificationList[{{idx}}].orders" type="text" value="{{row.orders}}" maxlength="11" class="input-small "/>
							</td>
							<shiro:hasPermission name="product:specificationGroup:edit"><td class="text-center" width="10">
								{{#delBtn}}<span class="close" onclick="delRow(this, '#productSpecificationList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
							</td></shiro:hasPermission>
						</tr>//-->
					</script>
					<script type="text/javascript">
						var productSpecificationRowIdx = 0, productSpecificationTpl = $("#productSpecificationTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
						$(document).ready(function() {
							var data = ${fns:toJson(specificationGroup.productSpecificationList)};
							for (var i=0; i<data.length; i++){
								addRow('#productSpecificationList', productSpecificationRowIdx, productSpecificationTpl, data[i]);
								productSpecificationRowIdx = productSpecificationRowIdx + 1;
							}
						});
					</script>
				</div>
			</div>
		<div class="form-actions">
			<shiro:hasPermission name="product:specificationGroup:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>