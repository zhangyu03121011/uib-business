/**
 * 添加投诉申请 and 查看投诉记录
 */
var notEnd0 = true;
var isApend = true;
var count=0;
var imagPathObj = {};
$(document).ready(function(){
	var url = location.href.split('#')[0];
	$.get(rootPath + "/wechat/member/user/getWechatConfig?url=" + url,"",function(data){
		wx.config({
			debug: false,
			appId: data.appId,
			nonceStr: data.nonceStr,
			signature: data.signature,
			timestamp: parseInt(data.timestamp),
			jsApiList: [
	            'checkJsApi',
	            'onMenuShareTimeline',
	            'onMenuShareAppMessage',
	            'onMenuShareQQ',
	            'onMenuShareWeibo',
	            'hideMenuItems',
	            'showMenuItems',
	            'hideAllNonBaseMenuItem',
	            'showAllNonBaseMenuItem',
	            'translateVoice',
	            'startRecord',
	            'stopRecord',
	            'onRecordEnd',
	            'playVoice',
	            'pauseVoice',
	            'stopVoice',
	            'uploadVoice',
	            'downloadVoice',
	            'chooseImage',
	            'previewImage',
	            'uploadImage',
	            'downloadImage',
	            'getNetworkType',
	            'openLocation',
	            'getLocation',
	            'hideOptionMenu',
	            'showOptionMenu',
	            'closeWindow',
	            'scanQRCode',
	            'chooseWXPay',
	            'openProductSpecificView',
	            'addCard',
	            'chooseCard',
	            'openCard'
	        ]
		});
	});
	
	wx.ready(function () {
		/**
		 * 选择图片
		 */
		document.querySelector('#filePath').onclick = function () {		
			selectImage("filePath",count);
			count =count+1;
		};
		
		
		function selectImage(id,count){
			var localId = "filePath"+count;
			wx.chooseImage({
			  success: function (res) {
					var str="";
					str+="  <div class='img-item' >";
					str+=" <span><img src="+res.localIds+"  id='fileImg"+count+"' onclick=query(this)></span>";
					str+="<b onclick=closeImg(this,'"+localId+"')></b>";
					str+="</div>";
					$("#imgItem").prepend(str);
					if(count==2){
						$("#upload-btn-box").hide();
					}				
				setTimeout(function(){ 
					
					wx.uploadImage({
						localId: res.localIds.toString(),
						isShowProgressTips:1,
						success : function(res){
							$.get(rootPath + "/wechat/member/user/uploadImgToService?mediaId=" + res.serverId.toString(),"",function(result){
								if(result == ""){
									alert("下载失败");
								}else{
									imagPathObj[localId] = result;
									alert(JSON.stringify(imagPathObj));
								}
							});
							/*wx.downloadImage({
					  			serverId: res.serverId.toString(),
					  			isShowProgressTips: 1,
					  			success: function(res){
					  				//alert("下载成功：" + res.localId);
					  			}
					  		});*/
							
						},
						fail: function(res){
							alert("上传失败" + res.errMsg);
						}
					});
				},100);
			  }
			});
		}
		
		
	});
	
	//查询投诉记录
	$("#myComplaints").empty();
	complainRecords(rootPath+"/wechat/member/user/findcomplainRecordsByPage",$("#myComplaints"));
//	$(document).on("scrollstart",function(){
////		var scrollImg = "<table class='scrollImg' width='100%' border='0' cellspacing='0' cellpadding='0'><tr align='center'>正在努力加载数据...</tr></table>";
//				//此处是滚动条到底部时候触发的事件，在这里写要加载的数据，或者是拉动滚动条的操作
//	   	if($("#menu-index").val() == 1 && notEnd0){
//	   		var page = Number($("#all-product-page").val()) + 1;
//				$("#all-product-page").val(page);
////				if(isApend){                                                                               
////					$("#myComplaints").append(scrollImg);
////					isApend = false;
////				}
//			complainRecords(rootPath+"/wechat/member/user/findcomplainRecordsByPage?page="+page,$("#myComplaints"));
//	   	}   
//	   	
//	});
	//添加投诉申请
	$("#submit").click(function(){
		
		if(isNotEmpty(imagPathObj)) {
			$.each(imagPathObj,function(i,item) {
				$("#"+i).val(item);
			});
		}
		
		var complaintApplicationFrom = $("#complaintApplicationFrom").serialize();
		//alert(complaintApplicationFrom);
		
		
		complaintApplicationFrom = decodeURIComponent(complaintApplicationFrom,true);
//		complaintApplicationFrom ="tbAttachment.filePath0=https://www.baidu.com/usr/local/tomcat7/webapps/upload/5825260121.jpg&tbAttachment.filePath1=https://www.baidu.com/usr/local/tomcat7/webapps/upload/2111121.jpg&tbAttachment.filePath2=https://www.baidu.com/usr/local/tomcat7/webapps/upload/208955121.jpg&feedbackType=1&title=33333&describeInfo=244522"
		var flag=true;
		var complaintApplication = $("#complaintApplicationFrom input[type=text],textarea");
		$.each(complaintApplication,function(i,each) {
			var value = $(each).val();
			if(value == "" || value == undefined || value == null) {
				Alert.disp_prompt($(each).attr("errorMsg"));
				$(each).focus();
				flag=false;
				return flag;
			}
		});
		if(flag){
			AJAX.call(rootPath+"/wechat/member/user/complaintApplication", "post", "json", complaintApplicationFrom, false, function(result){
				if(result.status){
					Alert.disp_prompt("提交成功！");					
					$(".tab-bar-item").removeClass("hover");
					$("#compliaintRecord").addClass("hover");
					$(".complaints-tab").hide();
					$(".complaints-tab").eq($(this).index()).show();	
					$("#menu-index").val($(this).index());
					$("#myComplaints").empty();
					complainRecords(rootPath+"/wechat/member/user/findcomplainRecordsByPage",$("#myComplaints"));
				}else{
					Alert.disp_prompt("提交失败！");
				}
			});
		}
		
		
	});
	
	
	
	

	
	//查询投诉记录
	function complainRecords(url,divshow){
		AJAX.call(url, "post", "json", null, true, function(result){
			console.log(result);
			var str = "";
			var data = result.data;
			if(result.status){
//				//删除等待图片
//				$(".scrollImg").empty();
//				$(".scrollImg").remove();
				isApend = true;
				//当查询结果为空时，说明没有下一页了，设置结尾标识
				if(isNull(data)){
//					var endli = "<table width='100%' border='0' cellspacing='0' cellpadding='0'><tr>没有更多数据</tr></table>";
//					if($("#menu-index").val() == 1 && notEnd0){
//						notEnd0 = false;
//						divshow.append(endli);
//					};	
				}else{
					for(var i = 0; i < result.data.length; i ++){
						var complainRecord = result.data[i];
						var feedbackType;
						var solutionState;
						var solutionTimeStr="";
						if(complainRecord.feedbackType==1){
							feedbackType="商品";
						}else if(complainRecord.feedbackType==2){
							feedbackType="服务";
						}else if(complainRecord.feedbackType==3){
							feedbackType="其它";
						}
						
						if(complainRecord.solutionState==""||isNull(complainRecord.solutionState)){
							solutionState="待解决";
							solutionTimeStr="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
						}else{
							solutionState="已解决";
							solutionTimeStr=complainRecord.solutionTimeStr;
						}
						str +="<a href='/page/weixin/complaintApplicationDetail.html?id="+complainRecord.id+"' data-ajax='false'>";
						str +=" <div class='my-complaints' id='"+complainRecord.id+"'>";
						str += " <table width='100%' border='0' cellspacing='0' cellpadding='0'>";
						str +="     <tr>";
						str +="         <th colspan='4'>"+complainRecord.title+"<span><a href='javascript:void(0)' onclick=delComplainRecord('"+complainRecord.id+"')><img src='images/del.png'/></a></span></th>";
						str +="     </tr>";  
						str +="     <tr>" ;  					
						str +="         <td>"+feedbackType+"</td>";
//						str +="			<td>"
//						for(var j=0;j<complainRecord.tbAttachment.length;j++){
//							str +="			<img src='complainRecord.tbAttachment[j].filePath'>";
//						}
//						str +="			</td>"
						str +="         <td>"+complainRecord.createTimeStr+"</td>";    
						str +="         <td>"+solutionState+"</td>"; 
						
						str +="         <td>"+solutionTimeStr+"</td>";
						str +="     </tr>"; 
						str +=" </table>";	
						str +=" </div>";
						str +="</a>";
					}
					divshow.empty();
					divshow.append(str);
				}
//			
			}
			
		});
		
	}	
});

/**
 * 预览图片
 */
function query(obj){
	var imageSrc =$(obj).attr("src");
	previewImage(imageSrc);
}
function previewImage(imgUrl){
	wx.previewImage({
	    current: imgUrl, 
	    urls: [imgUrl]
	});
}

function closeImg(obj,locatinIds){
	//
	delete imagPathObj[locatinIds];
	$("#"+locatinIds).attr("value","");
	alert(JSON.stringify(imagPathObj));
	$(obj).parent(".img-item").remove();
	count--;	 
	if(count==2){
		$("#upload-btn-box").show();
	}
	 
}

/**
 * 删除
 */
function delComplainRecord(id) {
	var bool =window.confirm('是否删除投诉记录？');	
	if(isNotEmpty(id)&&bool) {
		AJAX.call(rootPath+"/wechat/member/user/deleteComplainRecord", "get", "json", "id="+id, true, function(result){
			if (result && result.code == null) {
				$("#"+id).remove();
			}
		}, function(){});
	}
}