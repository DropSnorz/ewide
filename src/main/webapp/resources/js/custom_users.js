/**
 * EWIDE
 * 2016
 */

function adduser(){
	 var token = $("meta[name='_csrf']").attr("content");
	 var header = $("meta[name='_csrf_header']").attr("content");
	$.ajax({
		type:"POST",
		data:"username="+document.getElementById("adduser").value,
		url:"adduser",
	    beforeSend: function(xhr){
	        xhr.setRequestHeader(header, token);
	    },
		success:function(data){
			if(data){
				var newChild = document.createElement("tr");
				newChild.setAttribute("id", data.username);
				var usertd=document.createElement("td");
				var user=document.createTextNode(data.username);
				var roletd=document.createElement("td");
				roletd.setAttribute("id", "roleselect");
				var deletetd=document.createElement("td");
				deletetd.setAttribute("class","text-center");
				deletetd.setAttribute("id","dlt");
				usertd.appendChild(user);
				newChild.appendChild(usertd);
				newChild.appendChild(roletd);
				newChild.appendChild(deletetd);
				document.getElementById("userRoleTab").appendChild(newChild);
				document.getElementById("roleselect").innerHTML="<select class=\"userrole form-control\" name=\""+data.username+"\">"+
						"<option value=\"\"></option>"+
						"<option value=\"MANAGER\">MANAGER </option>"+
						"<option value=\"REPORTER\">REPORTER </option>"+
						"<option selected=\"selected\" value=\"DEVELOPER\">DEVELOPER </option>"+
						+"</select>";
				document.getElementById("dlt").innerHTML="<a href=\"#\" onclick=\"deleteonclick(\'"+data.username+"\')\" class=\"btn btn-danger btn-delete-user\"><i class=\"material-icons\">delete</i></a>";
				
				}
			}
		});

}
//-----------------------------------------------------------------
$("#adduser").autocomplete({
	source:function(request,response){
		if((document.getElementById("adduser").value).length>2){
			var users;
			$.ajax({
				type:"GET",
				data:"username="+document.getElementById("adduser").value,
				url:"/ewide/project/users",
				success:function(respond){
					console.log(respond);
		            response($.map(respond,function(object){
		                return{
		                    label:object.username,
		                    value:object.username
		                };
		            }));
				}
			});
		}
	}
});

//---------------------------------------------------------------------

$( document ).ready(function() {
	$('.delete-user').click(function() {
		var option = document.createElement("option");
		option.text = "Removed";
		option.value = "null";
		console.log("oui");
		$(this).closest( "tr" ).find('select')[0].appendChild(option);
		$(this).closest( "tr" ).find('select').val("null").change();
		$(this).closest( "tr" ).hide('100');
	});
});
//----------------------------------------------------------------------
function deleteonclick(username){
	var node=document.getElementById(username);
	var option = document.createElement("option");
	option.text = "Removed";
	option.value = "null";
	node.first.next.find('select')[0].appendChild(option);
	node.first.next.find('select').val("null").change();
	node.first.next.closest( "tr" ).hide('100');
}