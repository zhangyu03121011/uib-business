/**
 * 提现页面
 */
//获取路径参--从个人中心传递过来
//总金额
var commission=0.00;
//提现中的金额
var withdraw_comm=0.00;
//点提现按钮将 可用余额传给下一个页面
var can_use_money=0.00;

$(document).ready(function(){
	commission=URL_PARAM.getParam("commission");
	withdraw_comm=URL_PARAM.getParam("withdraw_comm");
	
	//总金额
	show(commission,"all_money");
	
	//提现中金额
	show(withdraw_comm,"drawals_money");
	
	//可用余额
	can_use_money=Number(commission)-Number(withdraw_comm);
	show(can_use_money,"can_use_money");
});
//格式化显示 数字
function show(value,id){
	var value=Number(value);
	if(0.0==value||0.00==value||0==value){
		$("#"+id).text("￥0.00");
	}else{
		$("#"+id).text(formatPrice(value).toString());	
	}
}


//点提现按钮时判断是否是第一次提现,跳转到相应的页面
function drawals(){
	//判断可用余额是否大于100
	if(can_use_money<100){
		Alert.disp_prompt("可用余额不能低于100");
		return;
	}
	AJAX.call(rootPath+"/draRecord/isFirst", "post", "json", "", false, function(result){
		if(result.status){
			if(result.data){
				//第一次提现
				window.location.href="./bind-bank-page.html?balance="+can_use_money;
			}else{
				//不是第一次提现
				window.location.href="./withdrawals-bank.html?balance="+can_use_money;
			}
		}else{
			//失败
			Alert.disp_prompt(result.msg);
		}
	});
}