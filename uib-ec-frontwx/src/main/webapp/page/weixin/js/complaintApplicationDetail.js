/**
 * 投诉申请详情
 */

$(document).ready(function(){
	var id = "";
	var url = location.href;
	if(url.indexOf("=") != -1){
		id = url.substring(url.indexOf("=") + 1, url.length);
	}
	AJAX.call(rootPath+"/wechat/member/user/queryOneComplaintApplication", "get", "json", "id="+id, true, function(result){
		console.log(result);
		if(result.status){
			var complaintsDetail=result.data;
			var feedbackType;
			var solutionState;
			var solutionTime="";
			if(complaintsDetail.feedbackType==1){
				feedbackType="商品"
			}else if(complaintsDetail.feedbackType==2){
				feedbackType="服务"
			}else if(complaintsDetail.feedbackType==3){
				feedbackType="其它"
			}
			
			if(null==complaintsDetail.reply){
				var reply="";
			}else{
				var reply=complaintsDetail.reply;
			}
			var str="";
				str+="<ul>";
				str+="  <li>反馈类型："+feedbackType+"</li>";
				str+="  <li>标题： "+complaintsDetail.title+"</li>";
				str+="  <li>详情描述： "+complaintsDetail.describeInfo+"</li>";
				str+="  <li>";
				str+="     <p>上传图片：</p>";
				str+="     <div class='complanints-img'>";
				for(var j=0;j<complaintsDetail.tbAttachment.length;j++){
				str+="    <img src="+complaintsDetail.tbAttachment[j].filePath+"/>";
				}
				str+="    </div>";
				str+="  </li>";				
				str+="  <li>提交时间:"+complaintsDetail.createTimeStr2+"</li>";
				if(complaintsDetail.solutionState==""||isNull(complaintsDetail.solutionState)){
					str+="  <li>解决状态：未解决</li>";
				}else{
					str+="  <li>解决状态：已解决</li>";
					str+="  <li>客服答复："+reply+"</li>";
					str+="  <li>解决时间："+complaintsDetail.solutionTimeStr2+"</li>";
				}		
				str+=" </ul>";
				
				$("#complaints-detail").empty();
				$("#complaints-detail").append(str);
			
		}	
		
	});
	
});