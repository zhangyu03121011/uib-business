<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商品分类管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	<script type="text/javascript">
		$(document).ready(function() {
			var tpl = $("#treeTableTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
			var data = ${fns:toJson(list)}, ids = [], rootIds = [];
			for (var i=0; i<data.length; i++){
				ids.push(data[i].id);
			}
			ids = ',' + ids.join(',') + ',';
			for (var i=0; i<data.length; i++){
				if (ids.indexOf(','+data[i].parentId+',') == -1){
					if ((','+rootIds.join(',')+',').indexOf(','+data[i].parentId+',') == -1){
						rootIds.push(data[i].parentId);
					}
				}
			}
			for (var i=0; i<rootIds.length; i++){
				addRow("#treeTableList", tpl, data, rootIds[i], true);
			}
			$("#treeTable").treeTable({expandLevel : 5});
		});
		function addRow(list, tpl, data, pid, root){
			for (var i=0; i<data.length; i++){
				var row = data[i];
				if ((${fns:jsGetVal('row.parentId')}) == pid){
					$(list).append(Mustache.render(tpl, {
						dict: {
						blank123:0}, pid: (root?0:pid), row: row
					}));
					addRow(list, tpl, data, row.id);
				}
			}
		}
		function delConfirm(id,href){
			var result = false;
			$.ajax({
				url:"${ctx}/product/productCategory/checkProductCategory",
				type:"POST",
				dataType: "json",
				data:{"productCategoryId":id},
				async:false,
				success:function(data){
					if(!data.status){
						$.jBox("系统错误！");
					}else{
						var code = parseInt(data.code);
						if(code==0){
							$.jBox("该分类不是末级分类，请先删除子分类！");
						}
						if(code==1){
							$.jBox("该分类下有商品，请先删除商品！");
						}
						if(code==2){
							result = true;
						}
					}
				}	
			});
			return result;
			//confirmx('确认要删除该商品分类吗？', href);
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/product/productCategory/">商品分类列表</a></li>
		<shiro:hasPermission name="product:productCategory:edit"><li><a href="${ctx}/product/productCategory/form">商品分类添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="productCategory" action="${ctx}/product/productCategory/" method="post" class="breadcrumb form-search">
		<ul class="ul-form">
			<li><label>名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="treeTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>名称</th>
				<th>排序</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="product:productCategory:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody id="treeTableList"></tbody>
	</table>
	<script type="text/template" id="treeTableTpl">
		<tr id="{{row.id}}" pId="{{pid}}">
			<td><a href="${ctx}/product/productCategory/form?id={{row.id}}">
				{{row.name}}
			</a></td>
			<td>
				{{row.orders}}
			</td>
			<td>
				{{row.updateDate}}
			</td>
			<td>
				{{row.remarks}}
			</td>
			<shiro:hasPermission name="product:productCategory:edit"><td>
   				<a href="${ctx}/product/productCategory/form?id={{row.id}}">修改</a>
				<a href="${ctx}/product/productCategory/delete?id={{row.id}}" onclick="return delConfirm('{{row.id}}',this.href);">删除</a>
				<a href="${ctx}/product/productCategory/form?parent.id={{row.id}}">添加下级商品分类</a> 
			</td></shiro:hasPermission>
		</tr>
	</script>
</body>
</html>