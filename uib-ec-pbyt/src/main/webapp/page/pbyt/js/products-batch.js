var obj = {};
var pageIndex = 0;
var pageSize = 6;
var isLoad = true;//是否需要加载(到最后一页时,禁止滚动加载)
var isRequest = false;//是否正在请求(正在请求时,禁止滚动加载)
var rids = new Array();//预推荐id
var nids = new Array();//预取消推荐id
var rankId = URL_PARAM.getParam("rankId");
var flag =true;
var data="";
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
Array.prototype.indexOf = function(val) {
	for (var i = 0; i < this.length; i++) {
		if (this[i] == val)
			return i;
	}
	return -1;
};
Array.prototype.remove = function(val) {
	var index = this.indexOf(val);
	if (index > -1) {
		this.splice(index, 1);
	}
};

/**
 * 填充数据
 */
obj.initData = function(pageIndex,pageSize){
	isRequest = true;
	dids = new Array();
	rids = new Array();
	nids = new Array();
	$.get("/f/store/queryProduct/pageIndex/{0}/pageSize/{1}".format(pageIndex,pageSize), {storeId: obj.id,rankId:rankId}, function(result){
		if(pageIndex == 0){
				$("#productData").empty();
			}
		data = result.length;
		if(result.length != 0){
			var str = "";
			for(var i=0; i<result.length; i++){
				str +="<div class='flex-row cart'>";
				str +="    <div class='app-checkbox rdo'>";
				str +="        <input type='checkbox' name='single' id='single"+result[i].id+"' onclick='obj.checkSingle()'>";
				str +="        <label class='iconfont' for='set-default'></label>";
				str +="    </div>";
				str +="    <div class='cart-img'>";
				str +="        <img src='"+result[i].image+"'>";
				str +="    </div>";
				str +="    <div class='flex-item detail has-product-toggle'>";
				str +="        <div class='title'>"+result[i].fullName+"</div>";
				//str +="        <p class='capacity'>容量：30ml</p>";
				str +="        <div class='price'>";
				str +="            <span class='xianjia'>¥"+result[i].price+"</span>";
				str +="            <span class='yuanjia'>¥"+result[i].marketPrice+"</span>";
				str +="            <div class='app-checkbox tuijian'>";
				if(result[i].isRecommend == 1){
					rids.push(result[i].id);
					str +="            <input type='checkbox' id='"+result[i].id+"' name='recommend' onclick='obj.recommend(\""+result[i].id+"\")' checked='checked'>";
				}else{
					str +="            <input type='checkbox' id='"+result[i].id+"' name='recommend' onclick='obj.recommend(\""+result[i].id+"\")'>";
				}
				str +="                <label class='iconfont' for='tui-default'></label>";
				str +="                <span>推荐</span>";
				str +="            </div>";
				str +="        </div>";
				str +="    </div>";
				str +="</div>";
			}
			$("#productData").append(str);
			obj.checkSingle();
			isRequest = false;
 			if(result.length < pageSize){
 				isLoad = false;
 				$(".scrimg .load_tip").text("没有更多数据");
 			}
		}
	})
}

/**
 * 推荐单选
 */
obj.recommend = function(id){	
	if($("#"+id).is(':checked')){
		flag=true;
		var count = 0;
		var inputs = $('input[name="recommend"]');
		for(var i = 0; i < inputs.length; i++) {
			if(inputs[i].checked == true) {
				count ++;
			}
	    }
		if(count > 4){
			Alert.disp_prompt("每个店铺至多能推荐4件商品");
			$("#"+id).attr('checked',false);
		}else{
			if(rids.indexOf(id) == -1)
				rids.push(id);
			if(nids.indexOf(id) != -1)
				nids.remove(id);
		}
	}else{
		flag=false;
		if(rids.indexOf(id) != -1)
			rids.remove(id);
		if(nids.indexOf(id) == -1)
			nids.push(id);
	}
	
	AJAX.call(rootPath+"/store/redProduct", "post", "json", {ids: rids.join(","), storeId: obj.id, isRecommend: 1}, false, function(result){
		
	})
	AJAX.call(rootPath+"/store/redProduct", "post", "json", {ids: nids.join(","), storeId: obj.id, isRecommend: 0}, false, function(result){
	
	})
	
	$(window).scrollTop(0);
	$(document).height(0);
	isLoad = true;//是否需要加载(到最后一页时,禁止滚动加载)
	isRequest = false;//是否正在请求(正在请求时,禁止滚动加载)
	pageIndex = 0;
	pageSize = 6;
	obj.initData(0,6);
}

/**
 * 删除单选
 */
obj.checkSingle = function(){
	var count = 0;
    var inputs = $('input[name="single"]');
    for(var i = 0; i < inputs.length; i++) {
        if(inputs[i].checked == true) {
            count ++;
        }
    }
    var isCheck = "";
    if(count == inputs.length) {
    	isCheck = "checked";
    }
    var all = $('input[name="checkAll"]');
    for(var i = 0; i < all.length; i++) {
    	all[i].checked = isCheck;
    }
};

/**
 * 删除全选
 */
obj.checkAll = function(){
	var isCheck = "";
    if($("#set-default-all").is(':checked')){
        isCheck = "checked";
    }
    var inputs = $('input[name="single"]');
    for(var i = 0; i < inputs.length; i++) {
        inputs[i].checked = isCheck;
    }
}

/**
 * 完成
 */
obj.complete = function(){
//	AJAX.call(rootPath+"/store/redProduct", "post", "json", {ids: rids.join(","), storeId: obj.id, isRecommend: 1}, false, function(result){
//		
//	})
//	AJAX.call(rootPath+"/store/redProduct", "post", "json", {ids: nids.join(","), storeId: obj.id, isRecommend: 0}, false, function(result){
//	
//	})
//	//alert($('input[name="recommend"]').is(':checked'));
//	var status =$('input[name="recommend"]').is(':checked');
//	if(data!=0  && status){
//		if(flag){
//			Alert.disp_prompt("推荐成功");
//		}else{
//			Alert.disp_prompt("取消推荐成功");
//		}
//	}
	
	window.location.href = "/page/pbyt/myStore-page.html?id="+obj.id+"&rankId="+rankId+"&flag="+1;	
	
}

/**
 * 删除
 */
obj.del = function(){
	var dids = new Array();
	var inputs = $('input[name="single"]');
	for(var i = 0; i < inputs.length; i++) {
        if(inputs[i].checked == true) {
        	dids.push(inputs[i].id.replace("single", ""));
        }
    }
	AJAX.call(rootPath+"/store/delProduct", "post", "json", {ids: dids.join(","), storeId: obj.id}, false, function(result){
	//$.post("/f/store/delProduct",{ids: dids.join(","), storeId: obj.id},function(result){
		if(result == 1){
			Alert.disp_prompt("删除成功");
			$("#set-default-all").attr('checked',false);
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
	})
}


$(function(){
	obj.id = getParam("id");
	obj.initData(0,6);
	$("#complete").click(obj.complete);
	$("#set-default-all").click(obj.checkAll);
	$("#del").click(obj.del);
})

//滚动分页
$(window).scroll(function(){
	var scrollTop = $(this).scrollTop();
	var scrollHeight = $(document).height();
	var windowHeight = $(this).height();
	if(scrollTop/(scrollHeight-windowHeight) >= 0.95 && isRequest == false && isLoad == true){
		$(".scrimg").show();
		pageIndex += pageSize;
		obj.initData(pageIndex,pageSize);
	}
})