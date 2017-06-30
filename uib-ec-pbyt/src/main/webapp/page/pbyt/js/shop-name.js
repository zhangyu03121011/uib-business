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

obj.complete = function(){
	if(window.localStorage){
	    localStorage.id = obj.id;
		localStorage.logo=obj.logo;
		localStorage.name=$("#name").val();
		localStorage.image=obj.image;
		localStorage.explain=obj.explain;
	}
	location.href = "shop-inf.html";
	//location.href = "shop-inf.html?id="+ obj.id +"&logo="+ obj.logo +"&name="+ $("#name").val() +"&explain="+ obj.explain;
}

$(function(){
//	obj.id = getParam("id");
//	obj.logo = getParam("logo");
//	obj.name = getParam("name");
//	obj.explain = getParam("explain");
	
	
	if(window.localStorage){
		obj.id=localStorage.id;
		obj.logo=localStorage.logo;
		obj.name=(localStorage.name)||"";
		obj.explain=localStorage.explain;
	}
	
	if(obj.name==null || obj.name==""){
		$("#pageTitle").text("我的店铺");
	}else{
		$("#pageTitle").text(obj.name);
	}
	
	if(!isNull(obj.name)){
		$("#name").val(obj.name);
	}
	$("#complete").click(obj.complete);	
})