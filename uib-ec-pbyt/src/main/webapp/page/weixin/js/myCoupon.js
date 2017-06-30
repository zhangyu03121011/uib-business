/**
 * 我的优惠券
 */
$(document).ready(function(){
	//未使用
	notUsed();	
	Used(1,"tab-group2","CouponCount");
	Used(2,"tab-group3","expiredCouponList");
	
	//未使用
	$("#notUsed").click(function(){
		notUsed(0);
	});
	
	//已使用
	$("#used").click(function(){
		Used(1,"tab-group2","CouponCount");
	});
	
	//已过期
	$("#expired").click(function(){
		Used(2,"tab-group3","expiredCouponList");
	});
	
	function notUsed(){
		$.get(rootPath + "/wechat/member/coupon/myCoupon",{states:0},function(result){
			var str = "";
			if(result.status){
				$("#uncouponCount").text("未使用（"+result.data.length+"）");
				for(var i = 0; i < result.data.length; i ++){
					var Coupon = result.data[i];
					str+= " <div class='pepper-w'>";
					str+="    	<div class='pepper pepper-blue'> ";
					str+="      <div class='pepper-l'> ";	
					str+="      <p class='pepper-l-num'> ";
					str+="         <i></i><span>"+formatPrice(Coupon.facePrice)+"</span>（"+Coupon.name+"） ";	
					str+="      </p>";
					str+="      <p class='pepper-l-con'>使用范围：通用</p>";
					str+="      </div>";
					str+="      <div class='pepper-r'>";
					str+="      	<span>有效期：</span>";
					str+="      	<div>"+Coupon.beginDate+"<p class='pepper-line'>~</p>"+Coupon.endDate+"</div>";
					str+="      </div> ";
					str+="   </div>";
					str+="  <div class='pepper-b pepper-blue'>";
					str+="    <div class='pb-con'></div>";
					str+="    <div class='pb-border'></div>";
					str+="   </div>";
				    str+="   </div>";
				}
				 $("#tab-group1").empty();
				$("#tab-group1").append(str);	
			}	
		});
	}
	
	
	function Used(state,id,countId){
		$.get(rootPath + "/wechat/member/coupon/myCoupon",{states:state},function(result){
			console.log(result);
			var str = "";
			if(countId=="CouponCount"){
				$("#"+countId).text("已使用（"+result.data.length+"）");
			}else{
				$("#"+countId).text("已过期（"+result.data.length+"）");
			}			
			if(result.status){
				for(var i = 0; i < result.data.length; i ++){
					var Coupon = result.data[i];
					str+= " <div class='pepper-w'>";
					str+="    	<div class='pepper pepper-gary'> ";
					str+="      <div class='pepper-l'> ";	
					str+="      <p class='pepper-l-num'> ";
					str+="         <i></i><span>"+formatPrice(Coupon.facePrice)+"</span>（"+Coupon.name+"） ";	
					str+="      </p>";
					str+="      <p class='pepper-l-con'>使用范围：通用</p>";
					str+="      </div>";
					str+="      <div class='pepper-r'>";
					str+="      	<span>有效期：</span>";
					str+="      	<div>"+Coupon.beginDate+"<p class='pepper-line'>~</p>"+Coupon.endDate+"</div>";
					str+="      </div> ";
					str+="   </div>";
					str+="  <div class='pepper-b pepper-gary'>";
					str+="    <div class='pb-con'></div>";
					str+="    <div class='pb-border'></div>";
					str+="   </div>";
				    str+="   </div>";
				}
				$("#"+id).empty();
				$("#"+id).append(str);	
			}	
		});
	}
	
});