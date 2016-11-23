//button for to confirm the new password
var btnConfirm=document.getElementById("confirm");

btnConfirm.onclick=function(){
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	if(document.getElementById("newpwd").value.length>5  ){
		if( document.getElementById("newpwd").value==document.getElementById("confirmpwd").value){
			$.ajax({
				type:"POST",
				url:"changepwd",
				data:"lastpwd="+document.getElementById("lastpwd").value+"&newpwd="+document.getElementById("newpwd").value,
				beforeSend: function(xhr){
			       xhr.setRequestHeader(header, token);
				},
				success:function(respond){
					if(respond['error']){
						document.getElementById("pwdErrors").style.display="block";
						document.getElementById("pwdValid").style.display="none";
						document.getElementById("pwdErrors").innerHTML=respond['error'];
					}
					if(respond['success']){
						document.getElementById("pwdErrors").style.display="none";
						document.getElementById("pwdValid").style.display="block";
						document.getElementById("pwdValid").innerHTML=respond['success'];

					}
				}
			});
		}
		else{
			console.log("ici");
			document.getElementById("pwdErrors").style.display="block";
			document.getElementById("pwdErrors").innerHTML="Passwords don't match";
			document.getElementById("pwdValid").innerHTML="none";
			document.getElementById("confirmpwd").innerHTML="";
			document.getElementById("newpwd").innerHTML="";
		}
	}
	else{
		console.log("icid");
		document.getElementById("pwdErrors").style.display="block";
		document.getElementById("pwdErrors").innerHTML="Minimum length of characters";
		document.getElementById("pwdValid").innerHTML="none";
		document.getElementById("confirmpwd").innerHTML="";
		document.getElementById("newpwd").innerHTML="";
	}

}