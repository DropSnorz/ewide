
setInterval(function(){loadmessage();},7000);

/**
 * charge messages if the chat is opened
 */
function loadmessage(){
	if(document.getElementById("chatdiv").style.display=="block"){
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
    		console.log(document.getElementById("chattext").value);
    		document.getElementById("chattext").innerHTML=document.getElementById("chattext").value+"\n";
    	}
    	else{
    		
    	}
    }
});