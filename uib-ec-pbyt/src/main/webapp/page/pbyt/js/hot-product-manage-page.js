var obj = {};
var pageIndex = 0;
var pageSize = 6;
var isLoad = true;//是否需要加载(到最后一页时,禁止滚动加载)
var isRequest = false;//是否正在请求(正在请求时,禁止滚动加载)
var dids = new Array();//预删除id
var rids = new Array();//预推荐id

function isNull(value){
	if(value == "" || value == null)
		return true;
	return false;
}
String.prototype.format = function() {
	if (arguments.length == 0)
		return this;
	for (var s = this, i = 0; i < arguments.length; i++)
		s = s.replace(new RegExp("\\{" + i + "\\}", "g"), arguments[i]);
	return s;
};
function getParam(name){
    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
    var r = decodeURI(window.location.search).substr(1).match(reg);
    if(r!=null)
   	 	return unescape(r[2]); 
    return null;
}

obj.initData = function(pageIndex,pageSize){
	isRequest = true;
	dids = new Array();
	rids = new Array();
	$.get("/f/store/queryProduct/pageIndex/{0}/pageSize/{1}".format(pageIndex,pageSize), {storeId: obj.id,rankId:obj.rankId}, function(result){
		console.info(result);
		if(pageIndex == 0){
				$("#productData").empty();
			}
		if(result.length != 0){
			$("#no-data").hide();
			var str = "";
			for(var i=0; i<result.length; i++){
				str += "<div class='product-box'>";
				str += "    <a class='edit-label' onclick='obj.del(\""+result[i].id+"\")'>X</a>";
				str += "    <img src='"+result[i].image+"'>";
				str += "    <p class='title-p'>"+result[i].fullName+"</p>";
				str += "    <div class='price-part display-flex align-items-end'>";
				str += "        <div class='price'>¥"+result[i].price+"</div>";
				str += "        <div class='market-price'>¥"+result[i].marketPrice+"</div>";
				str += "        <div class='recommend flex-1 app-checkbox'>";
				if(result[i].isRecommend == 1){
					str += "        <input type='checkbox' id='"+result[i].id+"' onclick='obj.recommend(\""+result[i].id+"\")' checked='checked'>";
				}else{
					str += "        <input type='checkbox' id='"+result[i].id+"' onclick='obj.recommend(\""+result[i].id+"\")'>";
				}
				str += "            <label class='iconfont ' for='"+result[i].id+"'></label>";
				str += "            <span>推荐</span>";
				str += "        </div>";
				str += "    </div>";
				str += "</div>";
			}
			$("#productData").append(str);
			isRequest = false;
 			if(result.length < pageSize){
 				isLoad = false;
 			}
		}else{
			if($("#productData").length){
				if($("#productData").text()==""){
					$("#no-data").show();
				}
			}
			$(".scrimg").hide();
			//$(".endli").show();
		}
	})
}

obj.del = function(id){
	dids.push(id);
	AJAX.call(rootPath+"/store/delProduct", "post", "json", {ids: dids.join(","), storeId: obj.id}, false, function(result){
//	$.post("/f/store/delProduct",{ids: dids.join(","), storeId: obj.id},function(result){
		if(result == 1){
			Alert.disp_prompt("删除成功");
		}else{
			Alert.disp_prompt("删除失败");
		}
		$(window).scrollTop(0);
		$(document).height(0);
		isLoad = true;//是否需要加载(到最后一页时,禁止滚动加载)
		isRequest = false;//是否正在请求(正在请求时,禁止滚动加载)
		pageIndex = 0;
		pageSize = 6;
		obj.initData(0,6);
	}, function(){});
	
}

obj.recommend = function(id){
	rids.push(id);
	var isRecommend = 0;
	if($("#"+id).is(':checked')){
		isRecommend = 1;
	}
	$.post("/f/store/redProduct",{ids: rids.join(","), storeId: obj.id, isRecommend: isRecommend},function(result){
		if(isRecommend==1){
			if(result == 1){
				Alert.disp_prompt("推荐成功");
			}else{
				Alert.disp_prompt("推荐失败");
			}
		}else{
			if(result == 1){
				Alert.disp_prompt("取消推荐成功");
			}else{
				Alert.disp_prompt("取消推荐失败");
			}
		}
	})
}


$(function(){
	obj.id = getParam("id");
	obj.rankId = getParam("rankId");
//	obj.name = getParam("name");
//	
//	if(obj.name==null || obj.name==""){
//		$("#pageTitle").text("我的店铺");
//	}else{
//		$("#pageTitle").text(obj.name);
//	}
	
	obj.initData(pageIndex,pageSize);
	$("#batchMag").click(function(){
		location.href = "products-batch.html?id=" + obj.id+"&rankId="+obj.rankId;
	})
	$("#addProduct").click(function(){
		location.href = "products_add.html?storeId=" + obj.id+"&rankId="+obj.rankId;
	});
})

//滚动分页
$(window).scroll(function(){
	var scrollTop = $(this).scrollTop();
	var scrollHeight = $(document).height();
	var windowHeight = $(this).height();
	if(scrollTop/(scrollHeight-windowHeight) >= 0.95 && isRequest == false && isLoad == true){
		pageIndex += pageSize;
		obj.initData(pageIndex,pageSize);
	}
})