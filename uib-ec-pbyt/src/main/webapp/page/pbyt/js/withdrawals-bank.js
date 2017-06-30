/**
 * 提现---确认提现页面bank_name
 */
//第二次提现时的余额
var two_balance=URL_PARAM.getParam("balance");
$(document).ready(function(){
	var last_cardNo="";
	var bankName="";
	//接受路径参--从身份验证页面传过来的参
	if(window.localStorage){
		balance=localStorage.balance;
		cardNo=localStorage.cardNo;
		bankName=localStorage.bankName;
		localStorage.balance=null;
		localStorage.cardNo=null;
		localStorage.bankName=null;
	}
	
	//1.写入最多提现的金额
	if(balance=="" || balance==null || balance=="null" || balance=="undefined"){
		balance=two_balance;
		$("#balance").text(two_balance);
	}else{
		$("#balance").text(balance);
	}
	
	
	//2.写入卡号
	if("null"==cardNo || undefined==cardNo || undefined==bankName || "null"==bankName){
		//从数据库查出默认的那条数据并且显示
		AJAX.call(rootPath+"/draRecord/queryDraDefault", "post", "json", "", false, function(result){
			if(result.status){
				var list=result.data;
			    if(isNotEmpty(list)){
			    	 last_cardNo=list.cardNo;
					 $("#cardNo").text(formatCardNo(list.cardNo)); 
					 $("#bank_name").text(list.bankName);
			    }
			}else{
				//查询失败
				Alert.disp_prompt(result.msg);
			}
		});
	}else{
		last_cardNo=cardNo;;
		$("#cardNo").text(formatCardNo(cardNo));
		$("#bank_name").text(bankName);
	}
	
	//美工给的js
	uib.init({
        swipeBack: true //启用右滑关闭功能
    });
    //document.getElementById("withdrawBtn").addEventListener('tap', function() {
    $("#withdrawBtn").click(function(){
    	//点提现按钮
    	var applyAmount=$("#applyAmount").val();
    	//1.100<=提现金额<=1000
    	if(null==applyAmount || ""==applyAmount){
    		Alert.disp_prompt("输入金额不能为空");
    		return;
    	}
    	if(!(/^[0-9]*[1-9][0-9]*$/.test(applyAmount)) && !(/^\d+(\.\d+)?$/.test(applyAmount))){
    		Alert.disp_prompt("您输入的金额不是纯数字");
    		return;
    	}else if(Number(applyAmount)>Number(balance)){
    		Alert.disp_prompt("申请额度不能超过可用余额");
    		return;
    	}else if(Number(applyAmount)<100){
    		Alert.disp_prompt("申请额度最低为100");
    		return;
    	}else if(Number(applyAmount)>1000){
    		Alert.disp_prompt("申请额度最高为1000");
    		return;
    	}
    	
    	//2.每月提现次数不超过5次
    	if(!countDrawals()){
    		return;
    	}
 
   	    //3.保存记录
    	if(!save(last_cardNo,applyAmount)){
    		return;
    	}
   	
    	
    	var btnArray = ['确定'];
        uib.confirm('提现金额会在一周内到达指定账户！', '提现成功！', btnArray, function(e) {
            if (e.index == 0) {
                window.location.href = './personal.html';
            } else {
            }
        })
    });
    $("#select_bank").click(function(){
    	//跳转到选择绑定银行卡页面,并传参
    	window.location.href="select-bank.html?balance="+balance;
    });
});

//保存记录
function save(last_cardNo,applyAmount){
	var flag=true;
	 AJAX.call(rootPath+"/draRecord/update?cardNo="+last_cardNo+"&applyAmount="+applyAmount, "post", "json", "", false, function(result){
			if(result.status){
			}else{
				//查询失败
				Alert.disp_prompt(result.msg);
				flag=false;
			}
	});
	return flag;
}



//每月提现次数不超过5次
function countDrawals(){
	var flag=true;
	AJAX.call(rootPath+"/draRecord/countAlreadyDra", "post", "json", "", false, function(result){
		if(result.status){
			var count=result.data;
		    if(count>4){
		    	Alert.disp_prompt("每月提现不能超过5次");
		    	flag=false;
		    }
		}else{
			//查询失败
			Alert.disp_prompt(result.msg);
			flag=false;
		}
	});
	return flag;
}


//格式化银行卡号 (只保留后4位数字)
function formatCardNo(cardNo){
	return cardNo.substring(cardNo.length-4,cardNo.length);
}