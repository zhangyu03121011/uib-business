<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglib.jsp"%>
<html>
<head>
<title></title>
<script type="text/javascript" src="${ctxStatic}/js/hover.js"></script>
<script>
	function doSubmit() {
		if ($("#subbtn").text() == '确定') {
			$("#subbtn").text("修改");
			$("#name").hide();
			$("#nickname").show();
			$.post("${ctxBase}/member/updateMember", {
				"name" : $("#name").val(),
				"id" : "${member.id}"
			}, function(data) {
				alert('修改成功');
				location = '${ctxBase}/member/myInfo';
			});
		} else {
			$("#subbtn").text("确定");
			$("#name").show();
			$("#nickname").hide();
		}
	}
	
	
	
	
	
	
	
</script>
</head>
<body>
	<!-- 我的elife内容区 -->
	<div class="myelife_content">
		<!-- 左边菜单 -->
		<!-- 订单信息 -->
		<div class="my_order">
			<div class="my_coupons">
				<ul>
					<li id="one1" onclick="setTab('one',1,3)" class="hover">基本信息</li>
					<!-- <li id="one2" onclick="setTab('one',2,3)">头像修改</li> -->
				</ul>
			</div>
			<div id="con_one_1">
				<!-- 列表 -->
				<div class="my_inflist">
					<ul>
						<li>
							<dl>
								<%-- <dt>
									<div class="avatar_name">头像：</div>
								</dt>
								 <dd>
									<div class="my_avatar">
										<img src="${ctxStatic}/images/my_avatar.jpg" />
									</div>
								</dd>  --%>
							</dl>
							<dl>
								<dt>昵称：</dt>
								<dd>
									<input class="inf_input" name="name" id="name" type="text" value="${member.name}" style="display: none;" /> <span id="nickname">${member.name}</span>
								</dd>
							</dl>
						</li>
						<li>
							<p>用户名：${member.username}</p> <!-- <p>会员等级：普通会员</p> -->
						</li>
					</ul>
					<p>
						<a class="blue_btn confirm_btn" href="javascript:void(0)" onclick="doSubmit()" id="subbtn">修改</a>
					</p>
				</div>
			</div>
			<div id="con_one_2" style="display: none">
				<div class="my_inflist">
					<div class="my_avatarbox">
						<div class="avatar_left">
							<div class="avatar_btnbox">
								<input class="avatar_btn" type="file" name="" id="" />
							</div>
							<p>仅支持JPG、GIF、PNG、JPEG、BMP格式，文件小于4M</p>
							<div class="previewbox">
								<div class="imgbox">
									<img src="${ctxStatic}/images/my_avatar.jpg" />
								</div>
							</div>
							<p class="title_p">推荐头像</p>
							<ul class="ul_avatar">
								<li class="selected"><img src="${ctxStatic}/images/my_avatar.jpg" /><b></b></li>
								<li><img src="${ctxStatic}/images/my_avatar.jpg" /><b></b></li>
								<li><img src="${ctxStatic}/images/my_avatar.jpg" /><b></b></li>
								<li><img src="${ctxStatic}/images/my_avatar.jpg" /><b></b></li>
								<li><img src="${ctxStatic}/images/my_avatar.jpg" /><b></b></li>
								<li><img src="${ctxStatic}/images/my_avatar.jpg" /><b></b></li>
								<li><img src="${ctxStatic}/images/my_avatar.jpg" /><b></b></li>
								<li><img src="${ctxStatic}/images/my_avatar.jpg" /><b></b></li>
							</ul>
							<p>
								<a class="blue_btn confirm_btn" href="#">确认</a>
							</p>
						</div>
						<div class="avatar_right">
							<p class="title_p">效果预览</p>
							<p>你上传的图片会自动生成2种尺寸，请注意小尺寸的头像是否清晰</p>
							<div class="avatar_100">
								<img src="${ctxStatic}/images/my_avatar.jpg" />
							</div>
							<p>100*100像素</p>
							<div class="avatar_50">
								<img src="${ctxStatic}/images/my_avatar.jpg" />
							</div>
							<p>50*50像素</p>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
</body>
</html>