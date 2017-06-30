$(document).ready(function(){
	AJAX.call(rootPath +"/wechat/member/user/getMemberByUserName", "post", "json", "", true, function(result){
	/*AJAX.call(rootPath +"/wechat/member/user/getMemberByUserName", "post", "json", {"username" :  openId}, true, function(result){*/
		var order = $("#order");
		var verify = $("#verify");
		var personalInfo= $("#personalInfo");
		var reason= $("#reason");
		var verifyHtml,personalInfoHtml,reasonHtml;
		var status = "";
		var failureReason = "";
		var name = "";
		var idCard = "";
		var approveDate ="";
		if(!isNull(result.realName)){
			name=result.realName;
		}
		if(!isNull(result.idCard)){
			//idCard=result.idCard;
			var last =result.idCard.slice(-4);
			idCard=result.idCard.substring(0,3)+"***********"+last;
		}
		if(!isNull(result.approveDate)){
			approveDate=result.approveDate.substring(0,10);
			approveDate = approveDate.replace(/-/g,"/");
		}
		
		var orderHtml="";

		if(result.approveFlag==2){
			status = "未通过";
			failureReason = result.auditFailureDescription;
			verifyHtml="<div class='failure'>" +
						"<p>很遗憾</p>" +
						"<p>审核未通过</p>" +
						"</div>";
			orderHtml = "<a class='add-btn red-btn' href='./authenticate.html?approveFlag=2'>重新填写认证信息</a>";
			reasonHtml = "<p>审核未通过原因</p>" +
						 "<P class='resaon-box'>"+failureReason+"</P>";
			personalInfoHtml= "<p><span class='fr'>"+name+"</span>真实姓名</p>" +
							"<p><span class='fr'>"+idCard+"</span>身份证号</p>" +
							"<p><span class='fr'>" +status+ "</span>证件审核</p>";
		}
		else if(result.approveFlag==1){
			status = "已通过";
			verifyHtml="<div class='progress'>" +
			 			"审核通过..."+
						"</div>";
			reasonHtml ="";
			personalInfoHtml= "<p><span class='fr'>"+name+"</span>真实姓名</p>" +
			"<p><span class='fr'>"+idCard+"</span>身份证号</p>" +
			"<p><span class='fr'>" +status+ "</span>证件审核</p>" +
			"<p><span class='fr'>"+approveDate+"</span>认证通过时间</p>";
			//orderHtml = "<a class='add-btn red-btn' href='javascript:history.go(-1)'>返回上一页</a>";
		}
		else{
			status = "审核中";
			verifyHtml="<div class='progress'>" +
			 			"审核中..."+
						"</div>";
			reasonHtml ="";
			personalInfoHtml= "<p><span class='fr'>"+name+"</span>真实姓名</p>" +
			"<p><span class='fr'>"+idCard+"</span>身份证号</p>" +
			"<p><span class='fr'>" +status+ "</span>证件审核</p>";
		}
		verify.append(verifyHtml);
		personalInfo.append(personalInfoHtml);
		reason.append(reasonHtml);
		order.append(orderHtml);
	}, function(){});
});