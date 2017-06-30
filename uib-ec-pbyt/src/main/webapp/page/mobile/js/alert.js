// JavaScript Document
var time=1; 
var stop;
var Alert={	  
		disp_prompt:function(message){
			if($('#alert_dialog_show_3s_box').length > 0) {
				return false;
			}
			else {
				Alert.show1sMsg(message);
			}
		},	
		show1sMsg:function(obj){
			var subhtml='<div id="alert_dialog_show_3s_box" style=" overflow:hidden;"><div id="alert_show_3" class="time1" style="height:2rem; width:50%; background-color:#000; color:#fff; opacity:0.8; border-radius:8px;font-size:.6rem; text-align:center;z-index: 2000; position:fixed;top:20%;left:25%;line-height:2rem"><p>'+obj+'</p></div></div>';
			$("body").append(subhtml);
			//计数器
			time=1;		
			stop=setInterval(closedShow3sMsg,1000);
		},
	
};
//3s关闭弹出框
function closedShow3sMsg(){
	time=time-1;
	if(time==0){
		$('#alert_dialog_show_3s_box').remove();
		clearInterval(stop);
	}
}
