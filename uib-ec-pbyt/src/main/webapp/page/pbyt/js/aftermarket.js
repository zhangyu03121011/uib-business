/**
 * 订单详情---退换货
 */
var images=[];
var imageId=0;
$(document).ready(function(){
	$("#returnsForm").on("submit",function(e) {
		e.preventDefault();
	});
	//光标注焦
	$("#returnDesc").blur();
	$("#hiddenFocus").focus();
	/** 上传凭证开始**/
	var url = location.href.split('#')[0];
	$.get(rootPath + "/wechat/config/getWechatConfig?url=" + url,"",function(data){
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
		document.querySelector('#chooseImage').onclick = function () {
			wx.chooseImage({
				count: 1, // 可选择图片张数
				success: function (res) {
					//控制图片数量(不超过3张)
					imageId++;
					if(images.length==2){
						$("#chooseImage").css("display","none");
					}
					//$("#image").attr("src",res.localIds);
					var appendHtml="<li id='li_id_"+imageId+"'>"+
					                   "<img src='"+res.localIds+"' id='image_id_"+imageId+"'>"+
					                   "<span class='close-img'>"+
					                       "<a><i class='iconfont icon-error' onclick=del_image('"+imageId+"')></i></a>"+
					                   "</span>"+
					               "</li>";
					$("#imageDetail").before(appendHtml);
					setTimeout(function(){
						wx.uploadImage({
							localId: res.localIds.toString(),
							isShowProgressTips:1,// 默认为1，显示进度提示
							success : function(res){
								//alert("上传成功" + res.serverId);
								$.get("/f/wechat/config/downloadImage",{mediaId: res.serverId.toString(), folderName: "store"},function(result){
									if(result == ""){
										alert("下载失败:" + result);
									}else{
										//alert("下载成功" + result);
										images.push(result);
										$("#image_id_"+imageId).attr("src",result);
									}
								});
							},
							fail: function(res){
								alert("上传失败" + res.errMsg);
							}
						});
					},100);
				  }
			});
		};
	 });
	wx.error(function (res) {
		alert(res.errMsg);
	});
	
	/** 上传凭证结束**/
	//接收路径参
	if(window.localStorage){
		orderNo=localStorage.orderNo;
		goodNo=localStorage.goodNo;
		name=localStorage.name;
		name = decodeURI(name, "utf-8");
		quantity=localStorage.quantity;
		price=localStorage.price;
		after_cardNo=localStorage.cardNo;
		after_bankName=localStorage.bankName;
	    supplierId=localStorage.supplierId;
	}
	/**写入退款账户开始**/
	//1.判断是否传过来卡号、银行卡:若是 显示, 否则 去后台查
	if(null==after_cardNo || null==after_bankName){
		//从数据库查出默认的那条数据并且显示
		AJAX.call(rootPath+"/draRecord/queryDraDefault", "post", "json", "", false, function(result){
			if(result.status){
				var list=result.data;
			    if(isNotEmpty(list)){
	    	        cardNo=list.cardNo;
	    			cardNo=cardNo.substring(cardNo.length-4,cardNo.length);
				    $("#returnAccount").val(list.bankName+" (尾号"+cardNo+")"); 
			    }
			}else{
				//查询失败
				Alert.disp_prompt(result.msg);
			}
		});
	}else{
		after_cardNo=after_cardNo.substring(after_cardNo.length-4,after_cardNo.length);
		$("#returnAccount").val(after_bankName+" (尾号"+after_cardNo+")");
	}
	/**写入退款账户结束**/
	
	//往订单号,商品号的隐藏域写值
	$("#orderNo").val(orderNo);
	$("#productId").val(goodNo);
	//往商品数量的隐藏域写值
	$("#quantity").val(quantity);
	//往供应商id的隐藏域写值
	$("#supplierId").val(supplierId);
	
	//点击提交申请
	name = encodeURI(name); 
	name = encodeURI(name);
	$("#aftermarketFromBtn").click(function(){
		if(!validate("returnType","退款类型") || !validate("returnReson","退款原因") || !validate("returnSum","退款金额")|| !validate("returnAccount","退款账户") || !validate("returnDesc","退款说明")){
			return;
		}
		var returnSum=$("#returnSum").val();
		var allow_price=Number(price)*Number(quantity);
		if(!(/^[0-9]*[1-9][0-9]*$/.test(returnSum)) && !(/^\d+(\.\d+)?$/.test(returnSum))){
    		Alert.disp_prompt("您输入的退款金额不是纯数字");
    		return;
    	}else if(Number(returnSum)<0){
    		Alert.disp_prompt("您输入的退款金额不合法");
    		return;
    	}else if(Number(returnSum)>allow_price){
    		Alert.disp_prompt("退款金额不能超过商品价格");
    		return;
    	}
		if(images.length>0){
			if(images.length==1){
				$("#trackingNo").val(images[0]+",");
			}else{
				$("#trackingNo").val(images.join(","));
			}
		}
		
		
		//往后台插入数据
		if(!save(name)){
			return;
		}
		
		  //美工给的js
		  uib.init({
		        swipeBack: true //启用右滑关闭功能
		    });
		  //touch.on('#applicationBtn','tap',function(){
		      //var btnArray = ['取消', '确定'];
		      var btnArray = ['确定'];
		      uib.confirm('我们的客服会在1-2个工作日内与您联系', '退货申请提交成功', btnArray, function(e) {
		          if (e.index == 0) {
		              window.location.href = 'javascript:history.go(-1)';
		          }
		      });
		  //});
	});
    /**写入退款账户开始**/
   $("#return_account_div").click(function(){
        var returnAccount=$("#returnAccount").val();
        if(""==returnAccount){
        	//没有绑定过银行卡
        	window.location.href='./bind-bank-page.html';
        }else{
        	//绑定过银行卡
        	window.location.href='./select-bank.html';
        }		
    });
	/**写入退款账户结束**/
});

//点击图片上的叉
function del_image(id2){
	    var btnArray = ['取消', '确定'];
	    uib.confirm('请确认是否删除?', '提示', btnArray, function(e) {
	        if (e.index == 1) {
	            //点击确定按钮,+框显示   
	        	$("#chooseImage").css("display","block");
	        	
	        	 //从服务器上把它给删除
	        	var value=$("#image_id_"+id2).attr("src");
	        	/*alert("------我开始删除图片了");
                AJAX.call(rootPath+"/wechat/config/deleteFile?rootPath="+value, "post", "json","",false, function(result){
            		if (!result) {
            			Alert.disp_prompt("从服务器上删除图片失败!");
            	    } 
            		    
            	});*/
	        	
	        	//数组里给移除
                var tempt=[];
                for(var i=0;i<images.length;i++){
                	if(value==images[i]){
                		continue;
                	}
                	tempt.push(images[i]);
                }
                images=tempt;
                
	        	 //页面移除
				var removeId="li_id_"+id2;
	            var ul = document.getElementById('details');
                var lis= ul.getElementsByTagName("li");
                for(i=0;i<lis.length;i++){
                    if(lis[i].id==removeId){
                    	ul.removeChild(lis[i]);
                    }
                }
	        }
	    });
	//});	
}

function save(name){
	var flag=false;
	AJAX.call(rootPath+"/orderReturns/insert?name="+name, "post", "json",$("#returnsForm").serialize(), false, function(result){
		if (result.status) {
	       flag=true;
	    } else {
		    Alert.disp_prompt(result.msg);
		}
	});
	return flag; 
}

function validate(id,message){
	var value=$("#"+id).val();
	if(null==value || ""==value || undefined==value || "请选择退款类型"==value){
		Alert.disp_prompt(message+"不能为空");
		return false;
	}
	return true;
}