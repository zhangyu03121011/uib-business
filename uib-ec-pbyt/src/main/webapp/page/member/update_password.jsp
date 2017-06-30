<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglib.jsp"%>
<html>
<head>
<title></title>
<link href="${pageContext.request.contextPath}/static/jquery-jbox/2.3/Skins/Bootstrap/jbox.min.css" rel="stylesheet" />
<script src="${pageContext.request.contextPath}/static/jquery-jbox/2.3/jquery.jBox-2.3.min.js" type="text/javascript"></script>
<script type="text/javascript">
	function goSubmit() {
		if ($("#oldPassword").val().length == 0) {
			$.jBox.tip("旧密码不能为空", "error");
			return;
		}
		if ($("#newPassword").val().length == 0) {
			$.jBox.tip("新密码不能为空", "error");
			return;
		}
		if ($("#reNewPassword").val().length == 0) {
			$.jBox.tip("确认密码不能为空", "error");
			return;
		}
		if ($("#newPassword").val()!=$("#reNewPassword").val()) {
			$.jBox.tip("新密码与确认密码不一致", "error");
			return;
		}
		$.post("${ctxBase}/member/updatePassword", {"password" : $("#reNewPassword").val() }, function(data) {
				if (data == 'ok') {
					$.jBox.tip("密码修改成功");
					$("input").val('');
					return;
				}
				$.jBox.tip('旧密码错误',"succee");
			});
	}
</script>
</head>
<!-- 订单信息 -->
<div class="my_balance">
	<div class="balance_title font14">修改密码</div>
</div>
<!-- 邮箱验证 -->
<div class="security_content">
	<div class="ctrl_group">
		<label class="ctrl_label">旧密码：</label>
		<div class="ctrl_main">
			<input type="password" name="oldPassword" id="oldPassword" />
		</div>
	</div>
	<div class="ctrl_group">
		<label class="ctrl_label">新密码：</label>
		<div class="ctrl_main">
			<input type="password" name="newPassword" id="newPassword" />
		</div>
	</div>
	<div class="ctrl_group">
		<label class="ctrl_label">确认密码：</label>
		<div class="ctrl_main">
			<input type="password" name="reNewPassword" id="reNewPassword" />
		</div>
	</div>
	<div class="ctrl_group">
		<a class="blue_btn next_btn" onclick="goSubmit()" href="javascript:void(0)">提交</a>
	</div>
</div>
</div>
</div>
</div>
</body>
</html>
