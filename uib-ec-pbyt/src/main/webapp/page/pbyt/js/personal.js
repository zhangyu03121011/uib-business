/**
 * 个人中心首页
 *
 */
var approveFlag="";
var p_phone="";
//后台会员审核失败时,传给申请代理的参数
var proxy_username="";
var proxy_idCard="";
$(document).ready(function(){
	//美工给的js
	uib.init({
	    swipeBack: true //启用右滑关闭功能
	});
	
	//新增需求,若openId为空弹出弹出框 
	var openId_is_null=false;
	
	//1.写入总收入,贡献值
	var amount=0.0;
	var amount2=0.0;   //写入提现中可用余额为  元所用的佣金字段
	var userType="";//1.消费者 2.分销商
	//点设置所需要的参数
	var p_name="";
	var p_avatar="";
	var p_rankName="";
	
    AJAX.call( rootPath + "/memMember/queryMemMember", "post", "json", "", false, function(result){
	    if(result.status){
	    	var data=result.data;
	    	if(isNotEmpty(data)){
	    		//点设置所需要的参数赋值
	    		p_name=data.name;
	    		p_avatar=data.avatar;
	    		p_rankName=data.rankName;
	    		p_phone=data.phone;
	    		//后台会员深刻失败时传给申请代理的参数
	    		proxy_username=data.username;
	    		proxy_idCard=data.idCard;
	    		
	    		userType=data.userType;
	    		//写入微信昵称和头像
	    		if("2"==userType || "3"==userType){
	    			$("#authentication").css("display","none");
	    			$("#shezhi").css("display","block");
	    			$("#image_authentication").css("display","block");
	    			if("2"==userType){
	    				//分销商   图标为B端分销商
	    				$("#image_authentication").attr("src","images/b.png");
	    			}
	    		}
		    	$("#head_image").attr("src",data.avatar);
	    		$("#name").text(data.name);
		    	
	    		approveFlag=data.approveFlag;	 
	    		
	    		//写入会员等级
	    		$("#memberGrade").text(data.rankName);
	    		
	    		amount=data.commission;
	    		amount2=data.commission;
	    		sumamount=data.sumamount;
	    		amount=formatPrice(amount);
	    		sumamount=formatPrice(sumamount);
	    		if(0.00==data.commission || 0.0==data.commission || 0==data.commission){
	    			$("#allMoney").text("0.00");
	    		}else{
	    			$("#allMoney").text(amount.substring(1, amount.length));
	    		}
	    		if(0.00==data.sumamount || 0.0==data.sumamount || 0==data.sumamount){
		    		$("#amount").text("0.00");	
	    		}else{
		    		$("#amount").text(sumamount.substring(1, sumamount.length));
	    		}
	    	}
	    }else{
	    	if("3022"==result.code){
	    		//openId为空,弹出弹出框
	    		var btnArray = ['取消', '确定'];
		        uib.confirm('请打开微信关注平步云天公众账号，即可查看个人信息。申请成为代理还可轻松赚钱。', '温馨提示', btnArray, function(e) {
		        	if (e.index == 1) {
		                //window.location.href = './proxy.html';
		            } else {
		            }
		        	openId_is_null=true;
		        });
		        return;
	    	}
			//查询失败
			Alert.disp_prompt(result.msg);
		}
	});	
    
    //点设置跳页面 并传参
    $("#shezhi_href").click(function(){
    	// 用户名、头像、会员等级、手机号码
    	if(window.localStorage){
    	    localStorage.p_name = p_name;
    		localStorage.p_avatar=p_avatar;
    		localStorage.p_rankName=p_rankName;
    		localStorage.p_phone=p_phone;
    	}
    	window.location.href='./account-management.html';
    	return false;
    });
  
    
    //写入去提现,还有  元未提现  
    //提现中的金额
    var withdraw_comm=0.00;
	AJAX.call(rootPath+"/draRecord/queryDraRecord?applyStatus=0", "post", "json", "", false, function(result){
		if(result.status){
			var list=result.data;
		    if(isNotEmpty(list)){
		    	 $.each(list, function(n, value) {
		    		 withdraw_comm+=value.applyAmount;
		    	 });
		    }
		}else{
			//查询失败
			Alert.disp_prompt(result.msg);
		}
	});
	// 总收入-提现中的金额
	var balance=amount2-withdraw_comm;
    if(0.00==balance || 0.0==balance || 0==balance){
		$("#balance").text("去提现，还有0.00元未提现");
	}else{
		balance=formatPrice(balance);
		$("#balance").text("去提现，还有"+balance.substring(1, balance.length)+"元未提现");
	}
    
    //2.写入月收入
    AJAX.call( rootPath + "/mobile/Commission/countCommission", "post", "json", "", false, function(result){
	    if(result.status){
	    	var data=result.data;
	    	if(0.00==data || 0.0==data || 0==data){
    			$("#monthMoney").text("0.00");	
    		}
	    	if(isNotEmpty(data)){
	            data=formatPrice(data);
		        $("#monthMoney").text(data.substring(1, data.length));	
	    	}
	    }else{
			//查询失败
			Alert.disp_prompt(result.msg);
		}
	});		

    //暂未获得代理资格用户点下面图标给出提示
    $("#saleOrder").click(function(){
    	warn(userType,"./order_list.html");
    	return false;
    });  
    $("#myExpand").click(function(){
    	warn(userType,"./tuiguang.html");
    	return false;
    });
    $("#moneyRecord").click(function(){
    	warn(userType,"./withdrawals-record.html");
    	return false;
    });
    $("#amountDetail").click(function(){
    	warn(userType,"./commision-details.html");
    	return false;
    });
    $("#confirmBtn").click(function(){
    	warn(userType,"./myStore-page.html?flag=1");
    	return false;
    });
    //点击去提现,还有8000.00元未提现 跳转去提现页面
    $("#balance").click(function(){
    	//传路径参  总金额 提现中的金额 可用余额
    	warn(userType,"./withdraw.html?commission="+amount2+"&withdraw_comm="+withdraw_comm);
    });
    
    //我的订单单击事件,若openId为空,停留在当前页面,不跳转
    $("#myOrder").click(function(){
    	if(!openId_is_null){
    		window.location.href="./order_me.html";
    	}
    	return false;
    });
 });

    function warn(userType,href){
    	if("1"==userType || "3"==userType){
    		var confirmMsg='';
    		var confirmHref='./proxy.html';
    		//要申请成为代理
    		if("0"==approveFlag){
    			//正在审核
    			confirmMsg='后台正在审核您提交的资料，请耐心等待！';
    			confirmHref='#';
    		}else if("2"==approveFlag){
    			//审核失败
    			confirmMsg='您提交的资料审核未通过，请修改资料后重新申请!';
    		}else if(null==approveFlag || ""==approveFlag || undefined==approveFlag){
    			//需申请代理
    			confirmMsg='您暂未取得代理资格，无法使用该功能，赶快申请成为代理吧！轻轻松松获得额外收益！';
    		}
	        var btnArray = ['取消', '确定'];
	        uib.confirm(confirmMsg, '温馨提示', btnArray, function(e) {
	        	if (e.index == 1) {
	        		if(window.localStorage){
	        		    localStorage.proxy_phone = p_phone;
	        			localStorage.proxy_approveFlag=approveFlag;
	        			localStorage.proxy_username=proxy_username;
	        			localStorage.proxy_idCard=proxy_idCard;
	        		}
	                window.location.href = confirmHref;
	            } else {

	            }
	        });
    	}else if("2"==userType && "1"==approveFlag){
    		//已经是代理并且身份证审核通过
    		window.location.href = href;
    	}
    }