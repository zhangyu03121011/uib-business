<%@ page contentType="text/html;charset=UTF-8" import="com.uib.ecmanager.common.enums.*" import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>

	<title>优惠券管理</title>
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
		/* //发货
		$shippingButton.live("click",function(){
			$("#shippingDialogs" ).dialog("open");
		});
		$("#shippingDialogs").dialog({
			width:900,
			height:440,
			modal:true,
			autoOpen:false, //将此对话框设置为不自动弹出
			buttons: [
			  		{
			  			text: "确定",
			  			click: function() {
			  				$("#shippingForm").submit();
			  				$( this ).dialog( "close" );
			  			}
			  		},
			  		{
			  			text: "取消",
			  			click: function() {
			  				$( this ).dialog( "close" );
			  			}
			  		}
			  	],
			//设置对话框效果  
			show:{
				effect:"blind",   
				duration:1000
			}
		});	 */
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/coupon/coupon/">优惠券列表</a></li>
		<shiro:hasPermission name="coupon:coupon:save"><li><a href="${ctx}/coupon/coupon/form">优惠券添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="coupon" action="${ctx}/coupon/coupon/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>起始日期：</label>
				<input name="beginDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${coupon.beginDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label>结束日期：</label>
				<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${coupon.endDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label>名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<c:set var="registerPresent" value="<%=PresentType.register_present%>"/>
	<c:set var="buyPresent" value="<%=PresentType.buy_present%>"/>
	<c:set var="activitiesPresent" value="<%=PresentType.activities_present%>"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				
				<th>名称</th>
				<th>前缀</th>
				<th>生成数量</th>
				<th>赠送数量</th>
				<th>赠送类型</th>
				<th>使用起始日期</th>
				<th>使用结束日期</th>
				<shiro:hasPermission name="coupon:coupon:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="coupon">
			<tr>
				<td>
					${coupon.name}
				</td>
				<td>
					${coupon.prefix}
					
				</td>
				<td>
					${coupon.sum}
				</td>
				<td>
					${coupon.presentSum}
				</td>
				<td>
					<c:choose>
						<c:when test="${coupon.presentType == registerPresent.index}">
								${registerPresent.description}
						</c:when>
						<c:otherwise>
							<c:choose>
							 
							 	<c:when test="${coupon.presentType == buyPresent.index}">
										${buyPresent.description}
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${coupon.presentType == activitiesPresent.index}">
												${activitiesPresent.description}
										</c:when>
										<c:otherwise>
												 无
										</c:otherwise>
									</c:choose>
								</c:otherwise>
							</c:choose>
						</c:otherwise>
					</c:choose>
				</td>
				<td>
					<fmt:formatDate value="${coupon.beginDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<fmt:formatDate value="${coupon.endDate}" pattern="yyyy-MM-dd"/>
				</td>
				
				<shiro:hasPermission name="coupon:coupon:edit"><td>
				<c:set var="nowDate">  
			    	<fmt:formatDate value="<%=new Date()%>" pattern="yyyy-MM-dd " type="date"/>  
				</c:set>  
				<c:set var="beginDate">  
				   <fmt:formatDate value="${coupon.beginDate}" pattern="yyyy-MM-dd" type="date"/>
				</c:set>
				<c:set var="endDate">  
				   <fmt:formatDate value="${coupon.endDate}" pattern="yyyy-MM-dd" type="date"/>
				</c:set>
    				<c:choose>
    					<c:when test="${coupon.sum > 0 && beginDate <= nowDate && endDate >= nowDate}">
    					<a href="${ctx}/coupon/coupon/couponBuild?id=${coupon.id}">[生成优惠码]</a>
    					</c:when>
    				</c:choose>
    				<a href="${ctx}/coupon/coupon/updateFormView?id=${coupon.id}">[修改]</a>
    				<a href="${ctx}/coupon/couponCode?id=${coupon.id}">[查看优惠码]</a>
					<a href="${ctx}/coupon/coupon/delete?id=${coupon.id}" onclick="return confirmx('确认要删除该优惠券吗？', this.href)">[删除]</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</div>
	<div class="pagination">${page}</div>
</body>
</html>