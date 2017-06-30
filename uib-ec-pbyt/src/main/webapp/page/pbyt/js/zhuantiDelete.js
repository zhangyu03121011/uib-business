var isApend = true;
var isTop = false;
var notEnd0 = true;
var obj ={};

function deleteprod(){
	var bool =window.confirm('是否删除专题？');	
	var inputList=document.getElementsByTagName("input");
	var specialIds ="";
	for(var i=0;i<inputList.length;i++){
		if(inputList[i].type == "checkbox" && inputList[i].checked == true && inputList[i].id != "select-all-btn"){
			if(inputList[i].id != 'undefined'){
				specialIds += inputList[i].id + ",";
        	}
		}
	}
	if (specialIds !=""&&bool){
		AJAX.call(rootPath + "/ptyt/special/deleteMySpecials?specialIds="+specialIds, "post", "json", "", true, 
				  function(result){
					if(result.status){
						Alert.disp_prompt("删除我的专题成功！");
						$("#all-product-page").val("1");
						$("#all-product-ul").empty();
						 //加载我的专题信息
						getSpeciaList(rootPath+"/ptyt/special/findSpecialUserId?page=1",$("#all-product-ul"));
					}else{
						Alert.disp_prompt("删除我的专题失败：" + result.msg);
					}
				}, function(){});
	}else{
		if(bool)
			{
			Alert.disp_prompt("请选择专题！");
			}
	}
}
/**
 * 上下滑动分页
 */
function upDownTouch(){
		 var scrollImg = "<li class='scrimg' id ='scrimg'><p calss='name' align='center'>正在努力加载数据...</p></li>";
		  if($("#menu-index").val() == 0 && notEnd0){
	      		var page = Number($("#all-product-page").val()) + 1;
	  			$("#all-product-page").val(page);
	  			if(isApend){
	  				$("#all-product-ul").append(scrollImg);
	  				isApend = false;
	  				getSpeciaList(rootPath+"/ptyt/special/findSpecialUserId?page="+page,$("#all-product-ul"));
	  			}
	      }
}

/**
 * 查询我的专题信息
 * @param url
 * @param divshow
 */
function getSpeciaList(url, divshow){
	
	AJAX.call( url, "post", "json", "", true, function(result){
		var data = result.data;
		//删除等待图片
		$("li").remove(".scrimg");
		/*$("#scrimg").remove();
		$("#endli").remove();*/
		var appendHtml="";
		isApend = true;
		//当查询结果为空时，说明没有下一页了，设置结尾标识
		if(isNull(data)){
			var endli = "<li class='endli' id ='endli'><p calss='name' align='center'>没有更多数据</p></li>";
			if($("#menu-index").val() == 0 && notEnd0){
				notEnd0 = false;
				$("#all-product-ul").append(endli);
			};	
		}else{	
			for(var index in data){
				var itemID =data[index].id;
				appendHtml ="<li class='item'>"
			      		   +	"<div class='select-label app-checkbox'>"
			      		   +		"<input type='checkbox' name='' id="+itemID+" >"
			      		   +		"<label for="+itemID+" class='iconfont'></label>"
			      		   +	"</div>"
			      		   +	"<div class='pic'><img src='"+data[index].showImage+"'></div>"
			      		   +	"<div class='title display-flex'>"
			      		   +		"<div class='text flex-1'>"+data[index].specialTitle+"</div>"
			      		//   +		"<div class='more'>MORE>></div>"
			      		   +	"</div>"
			      		   +	"<p class='content'>"+data[index].specialArticle+"</p>"
			      		   +"</li>";
			    
				/*appendHtml ="<div class='detail-body'>"
						   +	"<a class='edit-label'>X</a>"
						   +	"<div class='pic'>"
						   +		"<img src='"+data[index].showImage+"'>"
						   +	"</div>"
					   	   +	"<div class='title'>"
					   	   +		"<h3 class='title-text'>"+data[index].specialTitle+"</h3>"
			   	   		   +		"<p class='more'>MORE>></p>"
			   	   		   +	"</div>"
			   	   		   +	"<div class='tips'>"+data[index].specialArticle+"</div>"
			   	   		   +"</div>";*/
				divshow.append(appendHtml);
			};
		};
	}, function(){});
}
$(function(){
	//obj.merchantNo = URL_PARAM.getParam("merchantNo");
	//obj.rankId = URL_PARAM.getParam("rankId");
	$("#all-product-page").val("1");
	$("#all-product-ul").empty();
	
	 var $allBtn= $('#select-all-btn');
	    $allBtn.on('change',function(){
	      if($allBtn.prop('checked')){
	        $('.item input').prop('checked',true);
	      }else{
	        $('.item input').prop('checked',false);
	      }
	    })

	 //加载我的专题信息
	getSpeciaList(rootPath+"/ptyt/special/findSpecialUserId?page=1",$("#all-product-ul"));
	$(document).on('scroll',function(){
		//分页加载
		upDownTouch();
	});
})

