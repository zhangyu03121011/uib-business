<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglib.jsp"%>
<html>
<head>
<title>e-life.me</title>
<link href="${pageContext.request.contextPath}/static/jquery-jbox/2.3/Skins/Bootstrap/jbox.min.css" rel="stylesheet" />
<script src="${pageContext.request.contextPath}/static/jquery-jbox/2.3/jquery.jBox-2.3.min.js" type="text/javascript"></script>
<script type="text/javascript">
	window.onload = function() {

		var oStar = document.getElementById("star");
		var aLi = oStar.getElementsByTagName("li");
		var oUl = oStar.getElementsByTagName("ul")[0];
		var oSpan = oStar.getElementsByTagName("span")[1];
		var oP = oStar.getElementsByTagName("p")[0];
		var i = iScore = iStar = 0;
		var aMsg = [ "很不满意|差得太离谱，与卖家描述的严重不符，非常不满", "不满意|部分有破损，与卖家描述的不符，不满意",
				"一般|质量一般，没有卖家描述的那么好", "满意|质量不错，与卖家描述的基本一致，还是挺满意的",
				"非常满意|质量非常好，与卖家描述的完全一致，非常满意" ]

		for (i = 1; i <= aLi.length; i++) {
			aLi[i - 1].index = i;

			//鼠标移过显示分数
			aLi[i - 1].onmouseover = function() {
				fnPoint(this.index);
				//浮动层显示
				oP.style.display = "block";
				//计算浮动层位置
				oP.style.left = oUl.offsetLeft + this.index * this.offsetWidth
						- 104 + "px";
				//匹配浮动层文字内容
				oP.innerHTML = "<em><b>" + this.index + "</b> 分 "
						+ aMsg[this.index - 1].match(/(.+)\|/)[1] + "</em>"
						+ aMsg[this.index - 1].match(/\|(.+)/)[1]
			};

			//鼠标离开后恢复上次评分
			aLi[i - 1].onmouseout = function() {
				fnPoint();
				//关闭浮动层
				oP.style.display = "none"
			};

			//点击后进行评分处理
			aLi[i - 1].onclick = function() {
				iStar = this.index;
				oP.style.display = "none";
				oSpan.innerHTML = "<strong>" + (this.index) + " 分</strong> ("
						+ aMsg[this.index - 1].match(/\|(.+)/)[1] + ")"
			}
		}

		//评分处理
		function fnPoint(iArg) {
			//分数赋值
			iScore = iArg || iStar;
			for (i = 0; i < aLi.length; i++)
				aLi[i].className = i < iScore ? "on" : "";
		}

		$("ul li").click(function() { //得到分数
			$("#score").val($(this).text());
		});
	};

	function comment(id, productId) {
		$("#" + id).append($("#comment").toggle());
		$("#orderTableItemId").val(id);
		$("#productId").val(productId);
	}

	function commitSubmit() {
		if ($("#score").val()< 1) {
			$.jBox.tip("亲:你还没评分!", "error");
			return;
		}
		if ($("#content").val().length < 6) {
			$.jBox.tip("评价至少填写6个字哦!", "error");
			return;
		}
		isGuests=$("#isGuests").attr("checked") == "checked"?1:0;
		$.post("${ctxBase}/order/saveComment",
				{ "orderTableItemId": $('#orderTableItemId').val(), 
				"productId": $('#productId').val() ,
				"content": $('#content').val(),
				"productId": $('#productId').val() ,
				"score": $('#score').val(),
				"isGuests":isGuests,
				"memberId":'${member.id}' },
				   function(data){
				 	location.reload();
		  });
	}
</script>
</head>
<body>
	<div class="myelife_content">
		<div class="my_order">
			<div class="my_balance line_none">
				<div class="balance_title font14">评价晒单</div>
			</div>
			<div class="my_couponscn">
				<table width="100%" cellspacing="0" cellpadding="0" border="0" class="tb_void">
					<colgroup>
						<col width="500px">
							<col>
					</colgroup>
					<thead>
						<tr class="my_couponscn_inof">
							<th>商品信息</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${list}" var="l">
							<tr>
								<td colspan="2" id="${l.id}">
									<div class="evaluation-list">
										<a href="${pageContext.request.contextPath}/f/product/details?productId=${l.goodsNo}" target="_blank"> <img src="${ctxStatic}/images/cpone.jpg"></a> <font class="evaluation-name">
											<p></p>
											<p>
												购买时间：
												<fmt:formatDate value="${l.createDate}" pattern="yyyy-MM-dd HH:mm:ss" />
											</p>
										</font>
									</div>
									<div class="eval-btn">
										 <c:if test="${l.cid!=null }">
											已评价
										</c:if> 
									 	<c:if test="${l.cid==null }">
										<a class="bluenone" href="javascript:void(0)" onclick="comment('${l.id}','${l.goodsNo}')">点击评价</a>
										</c:if> 
									</div>
								</td>
							</tr>
						</c:forEach>

						<div class="evaluation-box"   style="display: none;" id="comment">
							<!-- 三角箭头 -->
							<div class="eval-arwo">
								<img src="${ctxStatic}/images/arw-pj.png" />
							</div>
							<div class="item">
								<font class="label"><font class="red">*</font>评分：</font>
								<div class="fl">
									<div id="star">
										<ul>
											<li><a href="javascript:;">1</a></li>
											<li><a href="javascript:;">2</a></li>
											<li><a href="javascript:;">3</a></li>
											<li><a href="javascript:;">4</a></li>
											<li><a href="javascript:;">5</a></li>
										</ul>
										<span></span>
										<p></p>
									</div>
								</div>
							</div>
							<form action="${ctxBase}/order/saveComment" id="inputForm" method="post">
								<div class="item">
									<font class="label"><font class="red">*</font>评价：</font>
									<div class="fl">
										<textarea id="content" name="content"></textarea>
									</div>
								</div>
								<input type="hidden" value="" name="orderTableItemId" id="orderTableItemId" /> 
								<input type="hidden" value="0" name="score" id="score" /> 
								<input type="hidden" value="${member.id}"	name="memberId" id="memberId" /> 
								<input type="hidden" value="" name="productId" id="productId" />
								<div class="item">
									<font class="label">&nbsp;</font>
									<div class="fl">
										<a class="evaluation-btn" href="javascript:void(0)" onclick="commitSubmit()">发表评价</a> 
										<label class="label-chbox"> <input type="checkbox" id="isGuests" name="isGuests" /> 匿名评价
										</label>
									</div>
								</div>
							</form>
						</div>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</body>
</html>
