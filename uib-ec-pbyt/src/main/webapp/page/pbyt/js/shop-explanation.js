var obj = {};

function getParam(name){
    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
    var r = decodeURI(window.location.search).substr(1).match(reg);
    if(r!=null)
   	 	return unescape(r[2]); 
    return null;
}


obj.complete = function(){
	if($("#explain").val().length<=35){
		if(window.localStorage){
		    localStorage.id = obj.id;
			localStorage.logo=obj.logo;
			localStorage.name=obj.name;
			localStorage.explain=$("#explain").val();
		}
		location.href = "shop-inf.html";
	}else{
		Alert.disp_prompt("店铺说明最多输入35个字！");
	}
	
//	location.href = "shop-inf.html?name=" + obj.name + "&explain=" + $("#explain").val()+"&id="+obj.id;
}


$(function(){
	
//	obj.id = getParam("id");
//	obj.name = getParam("name");
//	obj.explain = getParam("explain");
	
	if(window.localStorage){
		obj.id=localStorage.id;
		obj.logo=localStorage.logo;
		obj.name=localStorage.name;
		obj.explain=(localStorage.explain)||"";
	}
	if(obj.name==null || obj.name==""){
		$("#pageTitle").text("我的店铺");
	}else{
		$("#pageTitle").text(obj.name);
	}
	
	$("#explain").text(obj.explain);	
	$("#complete").click(obj.complete);	
	
	//$("#explain").focus();
	
})