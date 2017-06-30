var obj = {};

function isNull(value){
	if(value == "" || value == null)
		return true;
	return false;
}

function getParam(name){
    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
    var r = decodeURI(window.location.search).substr(1).match(reg);
    if(r!=null)
   	 	return unescape(r[2]); 
    return null;
}


obj.initData = function(){
	
//	obj.id = getParam("id");
//	obj.logo = getParam("logo");
//	obj.name = getParam("name");
//	obj.explain = getParam("explain");
	
	
	
	
	$("#logo").attr("src", obj.logo);
	$("#name").text(obj.name);
	$("#explain").text(obj.explain);
	
	
}

obj.initWechatConfig = function(){
	/** 上传凭证开始**/
	
//	wx.ready(function () {
//		alert(1);
//		document.querySelector('#chooseImage').onclick = function () {
//			alert(2);
//			wx.chooseImage({
//				count: 1, // 可选择图片张数
//				success: function (res) {
//					$("#logo").attr("src",res.localIds);
//					alert(3);
//					setTimeout(function(){
//						wx.uploadImage({
//							localId: res.localIds.toString(),
//							isShowProgressTips:1,// 默认为1，显示进度提示
//							success : function(res){
//								alert("上传成功" + res.serverId);
//								$.get("/f/wechat/config/downloadImage",{mediaId: res.serverId.toString(), folderName: "store"},function(result){
//									if(result == ""){
//										alert("下载失败:" + result);
//									}else{
//										alert("下载成功" + result);
//										obj.logo = result;
//										$("#image").attr("src", result);
//									}
//								});
//							},
//							fail: function(res){
//								alert("上传失败" + res.errMsg);
//							}
//						});
//					},100);
//				  }
//			});
//		};
//	});
//	wx.error(function (res) {
//		alert(res.errMsg);
//	});
}


obj.editName = function(){
	if(window.localStorage){
	    localStorage.id = obj.id;
		localStorage.logo=obj.logo;
		localStorage.name=obj.name;
		localStorage.image=obj.image;
		localStorage.explain=obj.explain;
	}
	location.href = "shop-name.html";
	//location.href = "shop-name.html?id="+ obj.id +"&logo="+ obj.logo +"&name="+ obj.name +"&explain="+ obj.explain;
}


obj.editExplain = function(){
	if(window.localStorage){
	    localStorage.id = obj.id;
		localStorage.logo=obj.logo;
		localStorage.name=obj.name;
		localStorage.image=obj.image;
		localStorage.explain=obj.explain;
	}
	location.href = "shop-explanation.html";
	//location.href = "shop-explanation.html?id="+ obj.id +"&logo="+ obj.logo +"&name="+ obj.name +"&explain="+ obj.explain;
}


obj.save = function(){
	//obj.logo = "/page/pbyt/images/index_3.png";
	if(isNull(obj.logo)){
		alert("店铺LOGO不能为空");
		return;
	}
	if(isNull(obj.name)){
		alert("店铺名称不能为空");
		return;
	}
	if(isNull(obj.explain)){
		alert("店铺说明不能为空");
		return;
	}
	$.post("/f/store/save",{id: obj.id, logo: obj.logo, name: obj.name, explain: obj.explain},function(result){
		if(result == 1){
			alert("保存成功");
			location.href = "myStore-page.html?flag=1";
		}else{
			alert("保存失败");
		}
	})
}

$(function(){
	//obj.initWechatConfig();
	
	if(window.localStorage){
		obj.id=localStorage.id;
		obj.logo=(localStorage.logo)||"";
		obj.name=(localStorage.name)||"";
		obj.explain=(localStorage.explain)||"";
	}
	if(obj.name==null || obj.name==""){
		$("#pageTitle").text("我的店铺");
	}else{
		$("#pageTitle").text(obj.name);
	}
	
	var url = location.href.split('#')[0];
//	alert(url);
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
					$("#logo").attr("src",res.localIds);
					setTimeout(function(){
						wx.uploadImage({
							localId: res.localIds.toString(),
							isShowProgressTips:1,// 默认为1，显示进度提示
							success : function(res){
								//alert("上传成功" + res.serverId);
								$.get("/f/wechat/config/downloadImage",{mediaId: res.serverId.toString(), folderName: "store"},function(result){
									if(result == ""){
										//alert("下载失败:" + result);
									}else{
										//alert("下载成功" + result);
										obj.logo = result;
										localStorage.logo =result;
										$("#image").attr("src", result);
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
		//alert(res.errMsg);
	});
	
	
	
	obj.initData();
	$("#editName").click(obj.editName);
	$("#editExplain").click(obj.editExplain);
	$("#save").click(obj.save);
})