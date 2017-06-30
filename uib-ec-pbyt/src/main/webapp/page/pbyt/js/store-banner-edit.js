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

obj.initWechatConfig = function(){
	var url = location.href.split('#')[0];
	$.get("/f/wechat/config/getWechatConfig",{url: url},function(data){
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
					$("#image").attr("src",res.localIds);
					obj.image = res.localIds;
					setTimeout(function(){
						wx.uploadImage({
							localId: res.localIds.toString(),
							isShowProgressTips:1,// 默认为1，显示进度提示
							success : function(res){
								//alert("上传成功" + res.serverId);
								//AJAX.call("/f/wechat/config/downloadImage", "post", "json", {mediaId: res.serverId.toString(), folderName: "store"}, true, function(result){
								$.get("/f/wechat/config/downloadImage",{mediaId: res.serverId.toString(), folderName: "store"},function(result){
									if(result == ""){
										//alert("下载失败:" + result);
									}else{
										//alert("下载成功" + result);
										obj.image = result;
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
}

obj.complete = function(){
	//alert(obj.image);
	if(!isNull(obj.image)){
		$.post("/f/store/save",{id: obj.id, image: obj.image},function(result){
			if(result == 1){
				alert("保存成功");
				location.href = "myStore-page.html?flag=1";
			}else{
				alert("保存失败");
			}
		})
	}else{
		alert("请上传店铺图片");
	}
}

$(function(){
//	obj.id = getParam("id");
//	obj.image = getParam("image");		
	obj.initWechatConfig();
	
	if(window.localStorage){
		obj.id=localStorage.id;
		obj.logo=localStorage.logo;
		obj.name=localStorage.name;
		obj.image =localStorage.image;
		obj.explain=localStorage.explain;
	}
	
	if(obj.name==null || obj.name==""){
		$("#pageTitle").text("我的店铺");
	}else{
		$("#pageTitle").text(obj.name);
	}
	
	if(!isNull(obj.image)){
		$("#image").attr("src", obj.image);
	}
	$("#complete").click(obj.complete);
})