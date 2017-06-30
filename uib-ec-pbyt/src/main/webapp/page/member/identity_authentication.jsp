<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglib.jsp"%>
<html>
<head>
<title>联保经纪</title>
<link href="${pageContext.request.contextPath}/static/jquery-jbox/2.3/Skins/Bootstrap/jbox.min.css" rel="stylesheet" />
<script src="${pageContext.request.contextPath}/static/jquery-jbox/2.3/jquery.jBox-2.3.min.js" type="text/javascript"></script>
<script type="text/javascript">
	function goSubmit() {
		if (!/^[\u4e00-\u9fa5]{2,30}$/.test($("#realName").val())) {
			$.jBox.tip("请正确输入姓名", "error");
			return;
		}
		if (!/(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/
				.test($("#idCard").val())) {
			$.jBox.tip("请正确输入身份证号码", "error");
			return;
		}
		var idCardPositive = document.getElementById("idCardPositive");
		if (!/.(gif|jpg|jpeg|png)$/.test(idCardPositive.value)) {
			$.jBox.tip("身份证正面图片类型必须是.gif,jpeg,jpg,png中的一种", "error");
			return;
		}
		if (idCardPositive.files[0].size > (1024 * 1024 * 1.5)) {
			$.jBox.tip("身份证正面图片不能大于1.5M", "error");
			return;
		}
		var idCardOpposite = document.getElementById("idCardOpposite");
		if (!/.(gif|jpg|jpeg|png)$/.test(idCardOpposite.value)) {
			$.jBox.tip("身份证反面图片类型必须是.gif,jpeg,jpg,png中的一种", "error");
			return;
		}
		if (idCardOpposite.files[0].size > (1024 * 1024 * 1.5)) {
			$.jBox.tip("身份证反面图片不能大于1.5M", "error");
			return;
		}
		var idCardHand = document.getElementById("idCardHand");
		if (!/.(gif|jpg|jpeg|png)$/.test(idCardHand.value)) {
			$.jBox.tip("手持身份证图片类型必须是.gif,jpeg,jpg,png中的一种", "error");
			return;
		}
		if (idCardHand.files[0].size > (1024 * 1024 * 1.5)) {
			$.jBox.tip("手持身份证图片不能大于1.5M", "error");
			return;
		}
		$("#inputForm").submit();
	}
</script>
</head>
<!-- 订单信息 -->
<div class="my_balance">
	<div class="balance_title font14">身份认证</div>
</div>
<!-- 邮箱验证 -->
<div class="security_content">
	<form action="${ctxBase}/member/identityAuthentication" method="post" id="inputForm" enctype="multipart/form-data">
		<div class="ctrl_group">
			<label class="ctrl_label">姓名：</label>
			<div class="ctrl_main">
				<c:if test="${map.approve_flag==null||map.approve_flag==''}">
					<input type="text" name="realName" id="realName" />
				</c:if>
				<c:if test="${map.approve_flag eq '0'}">
					<em style="color: green;">审核中</em>
				</c:if>
				<c:if test="${map.approve_flag eq '1'}">
					${map.real_name}
			</c:if>
				<c:if test="${map.approve_flag eq '2'}">
					<input type="text" name="realName" id="realName" value="${map.real_name}" />
					<em style="color: red">审核失败,请重新认证</em>
				</c:if>
			</div>
		</div>

		<div class="ctrl_group">
			<label class="ctrl_label">身份证号码：</label>
			<div class="ctrl_main">
				<c:if test="${map.approve_flag==null||map.approve_flag==''}">
					<input type="text" name="idCard" id="idCard" />
				</c:if>
				<c:if test="${map.approve_flag eq '0'}">
					<em style="color: green;">审核中</em>
				</c:if>
				<c:if test="${map.approve_flag eq '1'}">
					${map.id_card}
			</c:if>
				<c:if test="${map.approve_flag eq '2'}">
					<input type="text" name="idCard" id="idCard" value="${map.id_card}" /> 
					<em style="color: red">审核失败,请重新认证</em>
				</c:if>
			</div>
		</div>

		<div class="ctrl_group">
			<label class="ctrl_label">身份证正面：</label>
			<div class="ctrl_main">
				<c:if test="${map.approve_flag==null||map.approve_flag==''}">
					<input type="file" name="idCardPositive" id="idCardPositive" />
				</c:if>
				<c:if test="${map.approve_flag eq '0'}">
					<em style="color: green;">审核中</em>
				</c:if>
				<c:if test="${map.approve_flag eq '1'}">
					已认证
			</c:if>
				<c:if test="${map.approve_flag eq '2'}">
					<input type="file" name="idCardPositive" id="idCardPositive" />
					<em style="color: red">审核失败,请重新认证</em>
				</c:if>
			</div>
		</div>
		<div class="ctrl_group">
			<label class="ctrl_label">身份证反面：</label>
			<div class="ctrl_main">
				<c:if test="${map.approve_flag==null||map.approve_flag==''}">
					<input type="file" name="idCardOpposite" id="idCardOpposite" />
				</c:if>
				<c:if test="${map.approve_flag eq '0'}">
					<em style="color: green;">审核中</em>
				</c:if>
				<c:if test="${map.approve_flag eq '1'}">
					已认证
			</c:if>
				<c:if test="${map.approve_flag eq '2'}">
					<input type="file" name="idCardOpposite" id="idCardOpposite" />
					<em style="color: red">审核失败,请重新认证</em>
				</c:if>
			</div>
		</div>
		<div class="ctrl_group">
			<label class="ctrl_label">手持身份证：</label>
			<div class="ctrl_main">
				<c:if test="${map.approve_flag==null||map.approve_flag==''}">
					<input type="file" name="idCardHand" id="idCardHand" />
				</c:if>
				<c:if test="${map.approve_flag eq '0'}">
					<em style="color: green;">审核中</em>
				</c:if>
				<c:if test="${map.approve_flag eq '1'}">
					已认证
			</c:if>
				<c:if test="${map.approve_flag eq '2'}">
					<input type="file" name="idCardHand" id="idCardHand" />
					<em style="color: red">审核失败,请重新认证</em>
				</c:if>
			</div>
		</div>
		
		<div class="ctrl_group">
			<c:if test="${map.approve_flag==null||map.approve_flag==''||map.approve_flag==2}">
				<a class="blue_btn next_btn" onclick="goSubmit()" href="javascript:void(0)">提交</a>
			</c:if>
		</div>
</div>
</form>
</div>
</div>
</div>
</body>
</html>
