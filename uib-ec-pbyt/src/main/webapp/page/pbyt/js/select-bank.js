/**
 * 提现---选择银行卡页面
 */
//路径参
var balance=URL_PARAM.getParam("balance");
$(document).ready(function(){
	//1.把所有储存的银行卡信息列出来-默认的得选上
	AJAX.call(rootPath+"/draRecord/querySaveDra", "post", "json", "", false, function(result){
		if(result.status){
			$("#detail").empty();
			var list=result.data;
		    if(isNotEmpty(list)){
		    	$.each(list, function(n, value) {
		    		var cardNo=value.cardNo;
		    		var bankname=value.bankName;
		    		var appendHtml="<li class='backcard-box'>"+
                                       "<span>"+bankname+"(尾号<span id='formatCardNo'>"+formatCardNo(cardNo)+"</span>)</span>"+
                                       "<div class='select-bank app-checkbox'>"+
                                           html(value.isDefault,cardNo,bankname)+
                                       "</div>"+
                                   "</li>";
		    		$("#detail").append(appendHtml);
		    	});
		    }
		}else{
			//查询失败
			Alert.disp_prompt(result.msg);
		}
	});
	
	//2.点确认按钮
	   //2.1 若选择的发生变化,就把原来的默认变成2
	        //当前选中的变成1
	$("#withdrawBtn").click(function(){
		var hiddenCardNo=$("#hiddenCardNo").val();
		var check_cardNo="";
		var check_bankname="";
		var chkObjs = document.getElementsByName("RadioGroup1");
        for(var i=0;i<chkObjs.length;i++){
            if(chkObjs[i].checked){
            	var value=$(chkObjs[i]).val();
            	var values=value.split(",");
            	check_cardNo=values[0];
            	check_bankname=values[1];
            }
        }
		if(hiddenCardNo==check_cardNo){
			//默认值还是原来那个没变
		}else{
			AJAX.call(rootPath+"/draRecord/updateSaveDra?cardNo="+check_cardNo+"&oldCardNo="+hiddenCardNo, "post", "json", "", false, function(result){
				if(result.status){
				}else{
					//查询失败
					Alert.disp_prompt(result.msg);
					return;
				}
			});
		}
		 //2.2 跳转页面  确认提现 把余额传过来
        //跳转到确认提现页面,并且传值过去
		if(window.localStorage){
			localStorage.balance = balance;
			localStorage.cardNo=check_cardNo;
			localStorage.bankName=check_bankname;
		}
		if(null==balance || ""==balance || undefined==balance || "null"==balance){
			//从申请售后页面进来的
			window.location.href="./aftermarket.html";
		}else{
			window.location.href="./withdrawals-bank.html";	
		}
	});
	$("#add").click(function(){
		window.location.href="./bind-bank-page.html?balance="+balance+"&type=1";
	});
	//点击返回按钮
	$("#back").click(function(){
		window.location.href="./withdrawals-bank.html?balance="+balance;
		return false;
	});
});
//根据是否默认返回相应的html
function html(isDefault,cardNo,bankname){
	if("1"==isDefault){
		$("#hiddenCardNo").val(cardNo);
		return "<input type='radio' id='RadioGroup1_0' name='RadioGroup1' value="+cardNo+","+bankname+" checked='checked'>"+
		       "<label for='RadioGroup1_0' class='iconfont'></label>";
	}else{
		return "<input type='radio' id='RadioGroup1_1' value="+cardNo+","+bankname+" name='RadioGroup1'>"+
		       "<label for='RadioGroup1_1' class='iconfont'></label>";
	}
}

//格式化银行卡号 (只保留后4位数字)
function formatCardNo(cardNo){
	return cardNo.substring(cardNo.length-4,cardNo.length);
}