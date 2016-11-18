//button for to confirm the new password
var btnConfirm=document.getElementById("confirm");

btnConfirm.onclick=function(){
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	$.ajax({
		type:"POST",
		url:"changepwd",
		data:"lastpwd="+document.getElementById("lastpwd").value+"&newpwd="+document.getElementById("newpwd").value,
	   beforeSend: function(xhr){
	       xhr.setRequestHeader(header, token);
	   },
		success:function(respond){
			console.lo(respond);
		}
	});
}