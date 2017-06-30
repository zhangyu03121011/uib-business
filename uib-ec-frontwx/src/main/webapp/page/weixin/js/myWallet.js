/**
 * 我的钱包
 */

function initCommission(settleFlag){
	AJAX.call(rootPath +"/wechat/Commission/queryCommission?settleFlag="+settleFlag, "post", "json", "", true, function(result){
		var data = result.data;
		var appendHtml,appendVar;
		if(isNotEmpty(data)){
			if(null==data.accountAmt){
				data.accountAmt="";
			}
			appendHtml= "<span>佣金收入"
					  +    "<font>￥"+data.commAmt+"</font>"
					  +	"</span>账户余额"
					  + "<font class='red'>￥"+data.accountAmt+"</font>";
			}
		$("#CommissionInput").append(appendHtml);
		var CommList = data.doList;
		if(isNotEmpty(CommList)){
			//已结算
			if(settleFlag=="2"){
				 $.each(CommList, function(n, value) {
		            	var dateTime = value.createDate.substring(0,16);
		            	//佣金保留两位小数点
		            	var commission = formatPrice(parseFloat(value.commission));
		            	appendVar = "<dl>"
						          + 	"<dt>"
						          + 		"<div class='comm-select'>"
						          + 		    "<div class='checkboxFive'>"
						          +  		        "<input type='checkbox' value='1' id='" + value.id + "'/>"
						          +  		        "<label for="+value.id+"></label>"
						          +  		    "</div>"
						          +  		"</div>"
						          +  		"<div class='brokerage-list pl15'>"
						          +  			"<span>"+value.phone+"</span>"+value.username
						          +  		"</div>"
						          +  		"<div class='brokerage-list pl15'>"
						          +  			"<span class='red'>佣金:"+commission+"</span>"+dateTime
						          +  		"</div>"
						          + 	"</dt>"
				                  +"</dl>";
						$("#Settled").append(appendVar);
					}); 
			}
			//未结算
			if(settleFlag=="1"){
				 $.each(CommList, function(n, value) {
					 var  createDate= value.createDate.substring(0,16);
					 var notCommission = formatPrice(parseFloat(value.commission));
		            	appendVar = "<dl>"
						          +  	"<dt>"
						          +  		"<div class='brokerage-list'>"
						          +  			"<span>"+value.phone+"</span>"+value.username
						          +  		"</div>"
						          +  		"<div class='brokerage-list'>"
						          +  			"<span class='red'>佣金:"+notCommission+"</span>"+createDate
						          +  		"</div>"
						          +  	"</dt>"
						          +  "</dl>";
						$("#Notsettled").append(appendVar);
					}); 
			}
		}
	}, function(){});
}

//全选
function checkAll()
{
	var obj=document.getElementsByTagName("input");
    if(document.getElementById("checkAll").checked==true){
        for(var i=0;i<obj.length;i++){
            obj[i].checked=true;
        }
    }else{
        for(var i=0;i<obj.length;i++){
            obj[i].checked=false;
        }
    }
}

//批量删除
function del(){
	var bool =window.confirm('是否删除佣金？');	
	var inputList=document.getElementsByTagName("input");
	var CommissionIds="";
	for(var i=0;i<inputList.length;i++){
		if(inputList[i].type == "checkbox" && inputList[i].checked == true && inputList[i].id != "checkAll"){
			if(inputList[i].id != 'undefined'){
				CommissionIds += inputList[i].id + ",";
        	}
		}
	}
	if (CommissionIds !=""&&bool){
		AJAX.call(rootPath + "/wechat/Commission/deleteCommission?ids=" + CommissionIds, "post", "json", "", true, 
				  function(result){
					if(result.status){
//						Alert.disp_prompt("删除成功");
						location.reload(true);
					}else{
						Alert.disp_prompt("删除失败：" + result.msg);
					}
				}, function(){});
	}else{
		if(bool)
			{
			Alert.disp_prompt("请选择佣金!");
			}
	}
}

$(document).ready(function(){
	$(".brokerage-tab li").click(function(){
		$(".brokerage-tab li").removeClass("hover");
		$(this).addClass("hover");
		$(".brokerage-cen").hide();
		$(".brokerage-cen").eq($(this).index()).show();
		$(".select-all").hide();
		$(".select-all").eq($(this).index()).show();
		var curIndex = $(this).index();
		if(curIndex == 0){
			//查询已结算佣金
			$("#CommissionInput").empty();
			$("#Settled").empty();
			initCommission(2);
		}
		if(curIndex == 1){
			//查询未结算佣金
			$("#CommissionInput").empty();
			$("#Notsettled").empty();
			initCommission(1);
		}
	});
	//查询已结算佣金
	initCommission(2);
	
});
