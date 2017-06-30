/**
 * 填写储蓄卡信息
 */
//路径参
var balance=URL_PARAM.getParam("balance");
var type=URL_PARAM.getParam("type");
$(document).ready(function(){
	  
		//美工写的js--地址
 		function chooseBoxCallBack(scroller,text,value){
 			var result ="" ;
 			for(var i=0 ;i<text.length ; i++){
 				result += (" "+text[i]);
 			} ;
 		

 			$('.data-input').html(result) ;
 		}
		var selectArea = new MobileSelectArea();
	    selectArea.init({trigger:'.select-item',level:2,value:$('.defaultValue').data('value'),data:rootPath+'/ptyt/addr/findAddressList',eventName:'click',callback:chooseBoxCallBack });
		//美工写的js---银行
		touch.on('.select-toggle','tap',function(){
            $('.select-part').toggleClass('open');
        });
        touch.on('.select-part li','tap',function(){
            //1.加样式
            $(this).addClass('active').siblings().removeClass('active');
            // 2.将值写入输入框
            $("#bankName").text($(this).text());
            //3.关闭弹窗
            $('.select-part').removeClass('open');
        });
        /*touch.on('.select-part .ok-btn','tap',function(){
        	//1.判断是否选中值    2.关闭对话框  
            var value=$("#bankName").text();
            if(""==value || null==value || undefined==value){
            	 Alert.disp_prompt("请选择银行");
            	 return;
            }
            $('.select-part').removeClass('open');
       });*/
});

//点击下一步按钮
function submit(){
	//验证姓名
	if(!checkName()){
		return;
	}
	if(!vaildate2("bankName","所属银行")){
		return;
	}
	//验证银行卡卡号
	if(!checkCard()){
		return;
	}
	if(!checkPhone()){
		return;
	}
	if(!vaildate2("provinceCity","所属省市")||!vaildate("branchBankName","所属分行")){
		return;
	}
	var bankName=$("#bankName").text();
	$("#bankName1").val(bankName);
	var provinceCity=$("#provinceCity").text();
	$("#provinceCity1").val(provinceCity);
	
	//判断是否要插入默认字段 若第一次点提现需要插入默认  确认提现页面点添加进来不需要插入默认
	if("1"==type){
		//在确认提现页面点添加按钮进入本页面
	}else{
		//第一次点提现进入本页面
		$("#isDefault").val("1");
	}
	
	//发请求,插入数据库
	var applyPhone=$("#applyPhone").val();
	AJAX.call(rootPath+"/draRecord/insert", "post", "json",$("#bindBankForm").serialize(), false, function(result){
		if (result.status) {
			if("1"==type){
				window.location.href="./select-bank.html?balance="+balance;
			}else{
				//页面传值
				if(window.localStorage){
					localStorage.balance = balance;
					localStorage.cardNo=$("#cardNo").val();
					localStorage.applyerPhone = applyPhone;
					localStorage.bankName=bankName;
				}
				window.location.href="./Authentication.html";
			}
        } else {
    	    Alert.disp_prompt(result.msg);
    	}
     });
}
//检验姓名
function checkName(){
	var flag=false;
	var name=$("#cardPersonName").val();
	var type=typeof name;
	if(vaildate("cardPersonName","持卡人姓名")){
		if(("string"==type)){
			if(name.length>0 && name.length<=30){
				flag=true;
			}else{
				Alert.disp_prompt("长度不能超过16个字符");
			}
		}else{
			Alert.disp_prompt("请输入汉字或字母");
		}   
	}
	return flag;
}

//检验银行卡卡号
function checkCard(){
	var flag=false;
	var cardNo=$("#cardNo").val();
	if(vaildate("cardNo","银行卡号")){
		if(!(/^\d{16,}$/.test(cardNo))){
			Alert.disp_prompt("卡号不合法");
		}else{
			flag=true;
		}
	}
	return flag;
}

//检验是否为空---input元素
function vaildate(id,message){
	var value=$("#"+id).val();
	if(null==value || ""==value || "undefined"==value){
		Alert.disp_prompt(message+"不能为空");
		return false;
	}
	return true;
}
//检验是否为空---非input元素
function vaildate2(id,message){
	var value=$("#"+id).text();
	if(null==value || ""==value || "undefined"==value){
		Alert.disp_prompt(message+"不能为空");
		return false;
	}
	return true;
}
//检验手机号
function checkPhone(){
	var phone=$("#applyPhone").val();
	if(""!=phone && null!=phone){
		 var valResult=VALIDATOR.isValidMobel(phone);
		 if(valResult){
		     return true;
		  }else{
			  Alert.disp_prompt("请输入合法的手机号");
			  return false;
		  }
	 }else{
         Alert.disp_prompt("手机号不能为空");
		 return false;
	 }
}