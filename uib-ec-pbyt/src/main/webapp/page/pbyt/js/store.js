var obj = {};
var pageIndex = 0;
var pageSize = 6;
var isLoad = true;//是否需要加载(到最后一页时,禁止滚动加载)
var isRequest = false;//是否正在请求(正在请求时,禁止滚动加载)
var isLogin = 0;
var longitude = 0;//用户当前坐标
var latitude = 0;//用户当前坐标

function formatDist(distance){
    if(distance == 0){
        return "0m";
    }else if(distance < 1000){
        return parseInt(distance) + "m";
    }else{
        return Math.round(distance/1000*10)/10  + "km";
    }
}

obj.initData = function() {
	isRequest = true;
	$.get("/wechat/store/queryStoreList/pageIndex/{0}/pageSize/{1}".format(pageIndex,pageSize),{longitude: longitude, latitude: latitude},function(result) {
		isLogin = result.isLogin;
		if(result.data.length != 0){
			var str = "";
			var concern = "+关注";
 			for(var i = 0; i<result.data.length; i++){
 				if(result.data[i].concern == 1)
 					concern = "已关注";
 				else
 					concern = "+关注";
 				str += "<li class='ui-col store'><div class='ui-tag-freelimit'><a href='store-view.html?id="+result.data[i].id+"'><img src='"+result.data[i].image+"' alt='丽人猫'/></a><button onClick='concernFun("+result.data[i].id+")' id='"+result.data[i].id+"' class='ui-btn ui-btn-danger sc'>"+concern+"</button></div>";
 				str += "    <div class='ui-whitespace w100'>";
 				str += "        <ul class='ui-list ui-list-text'>";
 				str += "            <li class='ui-border-t pno'>";
 				str += "                <div class='ui-list-info'>";
 				str += "                    <h5 class='ui-nowrap'>评分："+scoreHtml(result.data[i].serverScore)+"</h5>";
 				str += "                </div>";
 				if(undefined != result.data[i].distance){
 					str += "            <div class='ui-badge-muted'>"+formatDist(result.data[i].distance)+"</div>";
 				}
 				str += "            </li>";
 				str += "        </ul>";
 				str += "        <h5>店铺："+result.data[i].name+"</h5>";
 				str += "        <h5 onClick=\"navFun("+result.data[i].id+")\">地址："+result.data[i].address+result.data[i].addressDetail+"</h5>";
 				str += "    </div>";
 				str += "</li>";
 			}
 			
 			if(pageIndex == 0){
 				$("#list").empty();
 			}
 			$("#list").append(str);
 			
 			isRequest = false;
 			if(result.data.length < pageSize){
 				isLoad = false;
 				$("#loading").hide();
 			}
 		}
	})
};

function navFun(id){
	location.href = "store-view.html?id=" + id;
}

$(document).ready(function(){
//	longitude = 113.92343022144823;
//	latitude = 22.52883973451039;
//	obj.initData();
	if (window.navigator.geolocation) {
        window.navigator.geolocation.getCurrentPosition(
            function(position) {
                //使用定位
            	longitude = position.coords.longitude;
            	latitude = position.coords.latitude;
                obj.initData();
            }, 
            function(error) {
                //取消定位或定位失败
            	obj.initData();
            },
            {
                enableHighAcuracy : true
            }
        );
    }
});

function concernFun(id){
	if(isLogin != 0){
		var status = 0;
		if($("#"+id).html() == "+关注"){
			$("#"+id).html("已关注");
			status = 1;
		}else{
			$("#"+id).html("+关注");
			status = 2;
		}
		$.post("/wechat/store/concern",{id: id, status: status},function(result){})
	}else{
		window.location.href = "login.html";
	}
}

//滚动分页
$(window).scroll(function(){
	
	var scrollTop = $(this).scrollTop();
	var scrollHeight = $(document).height();
	var windowHeight = $(this).height();
	if(scrollTop/(scrollHeight-windowHeight) >= 0.95 && isRequest == false && isLoad == true){
		$("#loading").show();
		pageIndex += pageSize;
		obj.initData();
	}
});
