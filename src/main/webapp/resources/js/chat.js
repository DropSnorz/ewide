

$( window ).load(function() {

	$('.scrollbar-macosx').scrollbar();
	loadmessage();

	var init_scroll = true;
	$(".open_chat, .close_chat").click(function(e) {
		$(".open_chat, .close_chat, .chat_content").slideToggle();
		if(init_scroll){
			var chat_end = $('.pix_items');
			var height = chat_end[0].scrollHeight;
			chat_end.scrollTop(height);
			$('.scrollbar-macosx').scrollTop(height);
			init_scroll=false;
		}
		loadmessage();
	});

	$('.scrollbar-macosx').scrollbar({
	    "onScroll": function(y, x){
	        if(y.scroll < 1){
	            $('.chat_content').removeClass('chat_begin');
	        }else{
	        	$('.chat_content').addClass('chat_begin');
	        }
	    }
	});
});

setInterval(function(){loadmessage();},7000);

/**
 * charge messages if the chat is opened
 */
function loadmessage(){
	//if(document.getElementById("chatdiv").style.display=="block"){
	if(document.getElementById("chatdiv")){
		$.ajax({
			type:"GET",
			url:"messages",
			success:function(respond){
				document.getElementById("chat").innerHTML=respond;
			}
		});
	}
}
/**
 * display or not the chat
 */
function openchat(){
	if(document.getElementById("chatdiv").style.display=="block"){
		document.getElementById("chatdiv").style.display="none";
	}
	else{
		document.getElementById("chatdiv").style.display="block";
		loadmessage();

	}
}

/**
 * on enter to the chat input -> send the message
 */
$('body').on('keypress','#chattext', function(e){
    if (e.keyCode == 13)
    {
    	if(e.shiftKey){
    		document.getElementById("chattext").innerHTML=document.getElementById("chattext").value+"\n";
    	}
    	else{
    		console.log("wut");
    		 var token = $("meta[name='_csrf']").attr("content");
    		 var header = $("meta[name='_csrf_header']").attr("content");
    		$.ajax({
    			type:"POST",
    			url:"message",
    			data:"message="+document.getElementById("chattext").value,
    		    beforeSend: function(xhr){
    		        xhr.setRequestHeader(header, token);
    		    },
    			success:function(respond){
    				document.getElementById("chat").innerHTML=respond;
    				document.getElementById("chattext").innerHTML="";
    				var chat_end2 = $('.pix_items');
    				var height2 = chat_end[0].scrollHeight;
    				chat_end2.scrollTop(height);
    				$('.scrollbar-macosx').scrollTop(height2);
    			}
    		});
    		
    	}
    }
});