var TICKET = {
	checkTicketState : function(){
		AJAX.call("/f/wechat/user/ticket/state/", 'get','json', '', false,
				  function(data){
					if(data == '0'){
						window.location.href="login.html?locationParm=jump";
						return;
					} 
				  }, function(){});
		
	}
};
