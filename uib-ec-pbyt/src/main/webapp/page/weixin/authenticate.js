$(document).ready(function(){
	var approveFlag = URL_PARAM.getParam("approveFlag");
	
	//驳回点重新认证信息
	if(approveFlag && approveFlag == 2) {
		getApproveByUserName();
	}
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

	var idCardPositive = "";//个人信息所在面
	var idCardOpposite = "";//国徽图案面
	var idCardHand = "";//手持证件照
	wx.ready(function () {
		/**
		 * 选择图片
		 */
		document.querySelector('#idCardPositive').onclick = function () {
			selectImage("idCardPositive");
		};
		
		document.querySelector('#idCardOpposite').onclick = function () {
			selectImage("idCardOpposite");
		};
		
		document.querySelector('#idCardHand').onclick = function () {
			selectImage("idCardHand");
		};
		
		function selectImage(id){
			wx.chooseImage({
			  count: 1, // 可选择图片张数
			  success: function (res) {
				//alert("选择成功：" + res.localIds);
				$("#" + id +"Img").attr("src",res.localIds);
				$("#" + id +"FileDiv").css("display","none");
				$("#" + id +"ImgDiv").css("display","block");
				setTimeout(function(){ 
					wx.uploadImage({
						localId: res.localIds.toString(),
						isShowProgressTips:1,
						success : function(res){
							//alert("上传成功" + res.serverId);
							$.get(rootPath + "/wechat/member/user/uploadImgToService?mediaId=" + res.serverId.toString(),"",function(result){
								if(result == ""){
									alert("下载失败");
								}else{
									if(id == "idCardPositive"){
										idCardPositive = result;
									}else if(id == "idCardOpposite"){
										idCardOpposite = result;
									}else if(id == "idCardHand"){
										idCardHand = result;
									}
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
	
		/**
		 * 预览图片
		 */
		document.querySelector('#idCardPositiveImg').onclick = function () {
			if(idCardPositive != "")
				previewImage(idCardPositive);
		};
		
		document.querySelector('#idCardOppositeImg').onclick = function () {
			if(idCardOpposite != "")
				previewImage(idCardOpposite);
		};
		
		document.querySelector('#idCardHandImg').onclick = function () {
			if(idCardHand != "")
				previewImage(idCardHand);
		};
		function previewImage(imgUrl){
			wx.previewImage({
			    current: imgUrl, 
			    urls: [imgUrl]
			});
		}
		
		document.querySelector('#idCardPositiveClose').onclick = function () {
			idCardPositive = "";
			$("#idCardPositiveImg").attr("src","");
			$("#idCardPositiveFileDiv").css("display","block");
			$("#idCardPositiveImgDiv").css("display","none");
		};
		
		document.querySelector('#idCardOppositeClose').onclick = function () {
			idCardOpposite = "";
			$("#idCardOppositeImg").attr("src","");
			$("#idCardOppositeFileDiv").css("display","block");
			$("#idCardOppositeImgDiv").css("display","none");
		};

		document.querySelector('#idCardHandClose').onclick = function () {
			idCardHand = "";
			$("#idCardHandImg").attr("src","");
			$("#idCardHandFileDiv").css("display","block");
			$("#idCardHandImgDiv").css("display","none");
		};
		
		function queryMemberByIdCard(idCard){
			var flag = false;
			$.ajax({
		    	type : "get",
		    	url : rootPath + "/wechat/user/queryMemberByIdCard",  
		    	data : "idCard=" + idCard,  
		    	async : false,  
		    	success : function(data){  
		        	if(data == "1"){
		        		flag = true;
					}
		    	}  
		    }); 
			return flag;
		}
		
		/**
		 * 提交
		 */
		document.querySelector('#subBtn').onclick = function () {
			
			var reg=/^(\d{15}$|^\d{18}$|^\d{17}(\d|X|x))$/;
			var date = new Date();
			var dateStr = date.getFullYear() +""+ (date.getMonth()+1) +""+ date.getDate();
			var indate = $("#indate").val();
			if(!isNull(indate)){
				//indate = indate.replace("年","").replace("月","").replace("日","");
				indate = indate.replace(/-/g,"");
			}
			if(isNull($("#realName").val())){
				alert("请填写真实姓名");
				return false;
			}else if(isNull($("#idCard").val())){
				alert("请填写身份证号");
				return false;
			}else if(!reg.test($("#idCard").val())){
				alert("身份证号格式不正确");
				return false;
			}else if(queryMemberByIdCard($("#idCard").val())){
				alert("该身份证号已经验证");
				return false;
			}else if(isNull($("#indate").val())){
				alert("请填写有效期限");
				return false;
			}else if(parseInt(indate) <= parseInt(dateStr)){
				alert("有效期限不能小于当天");
				return false;
			}else if(isNull(idCardPositive) || isNull(idCardOpposite) || isNull(idCardHand)){
				alert("请上传身份证照片");
				return false;
			}
			
			
		  	$.post(rootPath + "/wechat/member/user/identityAuthentication",{
					realName:$("#realName").val(),//姓名
					idCard:$("#idCard").val(),//身份证号码
					indate:$("#indate").val(),//身份证有效期
					idCardPositive:idCardPositive,//身份证正面图片
					idCardOpposite:idCardOpposite,//国徽图案面
					idCardHand:idCardHand//手持身份证图片
				},function(result){
					if(result.status){
						alert("提交成功");
						location.href = "verify.html";
					}else{
						alert("提交失败");
					}
				}
			);
		};
		
		document.querySelector('#mapBtn').onclick = function () {
			wx.getLocation({
				success: function (res) {
					var longitude = res.longitude; // 纬度，浮点数，范围为90 ~ -90
			        var latitude = res.latitude; // 经度，浮点数，范围为180 ~ -180。
			        var speed = res.speed; // 速度，以米/每秒计
			        var accuracy = res.accuracy; // 位置精度
			        //alert("纬度:" + longitude +"---"+ "经度:" + latitude  +"---"+ "速度:" + speed  +"---"+ "位置:" + accuracy);
			        wx.openLocation({
			            latitude: latitude,
			            longitude: longitude,
			            name: '我的位置',
			            address: accuracy,
			            scale: speed,
			            infoUrl: 'http://weixin.qq.com'
			        });
				},
				cancel: function (res) {
					alert('取消获取地理位置');
				}
		    });
		};	
	});

	wx.error(function (res) {
		alert(res.errMsg);
	});
	
	/**
	 * 根据用户名查询身份认证信息
	 */
	function getApproveByUserName(){
		$.ajax({
	    	type : "get",
	    	url : rootPath + "/wechat/member/user/getApproveByUserName",  
	    	data : "",  
	    	async : true,  
	    	success : function(result){  
	        	if(result.status == true){
	        		var data = result.data;
	        		$("#realName").val(data.name);
	        		$("#idCard").val(data.idCard);
	        		$("#indate").val(data.idCardValid);
	        		idCardPositive = setValue("idCardPositive",data.idCardPositive);
	        		idCardOpposite = setValue("idCardOpposite",data.idCardOpposite);
	        		idCardHand = setValue("idCardHand",data.idCardHand);
				}
	    	}  
	    }); 
	}
	
	function setValue(id,value) {
		$("#" + id +"Img").attr("src",value);
		$("#" + id +"FileDiv").css("display","none");
		$("#" + id +"ImgDiv").css("display","block");
		return value;
	};
});