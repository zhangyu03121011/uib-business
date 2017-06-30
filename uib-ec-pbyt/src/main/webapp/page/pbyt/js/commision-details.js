/**
 * 津贴明细
 */
$(document).ready(function(){
    //一进入页面,加载数据
	AJAX.call(rootPath+"/mobile/Commission/queryCommDetail", "post", "json", "", false, function(result){
		$("#detail").empty();
		var array=[];
		if(result.status){
			var list=result.data;
		    if(isNotEmpty(list)){
		    	 $.each(list, function(n, value) {
		    		 var commission=formatPrice(value.commission);
		    		 var name=value.name;
		    		 if(name==null || name=="" || name==undefined){
		    			 name="平步云天";
		    		 }
		    		 appendHtml="<li>"+
	                                "<div class='head-portrait'><img src='"+value.avatar+"'/></div>"+
	                                "<div class='information'>"+
		                                "<p class='name'>"+name+"</p>"+
		                                "<p class='date'>"+value.createTime+"</p>"+
	                                "</div>"+
	                                "<span id='commission_"+value.id+"'>+"+commission.substring(1, commission.length)+"</span>"+
	                            "</li>";
		    		 $("#detail").append(appendHtml);
		    		 if(null==commission || ""==commission){
		    			 $("#commission_"+value.id).text("+0.00");
		    		 }
		    	 });
		    }else{
		    	array.push("<div class='me-wrap' id='no-data' style='display: black;'>");
				array.push("   <div class='no-data'>") ;
		        array.push(" 	 <div class='no-data-logo'>"); 
		        array.push("		<span class='iconfont icon-dingdan1'></span></div>");   
		        array.push(" 			<p class='no-data-title'>您还没有相关的明细记录</p>");       
		        array.push("    </div>");   
		        array.push(" </div>");   
		        if(array){
					array = array.join(" ");
				}
		        $("#detail").html(array);	
		    	var endli = "<p class='no-more-data'>没有更多数据</p>"; 
		    	$("#detail").append(endli);
		    }
		}else{
	        //查询失败
			Alert.disp_prompt(result.msg);
		}
    });
});