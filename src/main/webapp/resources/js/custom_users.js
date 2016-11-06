/**
 * EWIDE
 * 2016
 */


//-----------------------------------------------------------------
/**
 * add a user as a developer in the project
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
/**
 * autocomplete the search user 
 */
$("#adduser").autocomplete({
	source:function(request,response){
		if((document.getElementById("adduser").value).length>2){
			$.ajax({
				type:"GET",
				data:"username="+document.getElementById("adduser").value,
				url:"/ewide/project/users",
				success:function(respond){
		            response($.map(respond,function(object){
		                return{
		                    value:object.username,
		                    label:object.username
		                };
		            }));
				}
			});
		}
	},
    messages: {
        noResults: '',
        results: function() {}
    }
});

//---------------------------------------------------------------------
/**
 * remove a user that was in the project : need to be save
 */
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
/**
 * remove a user that wasn't in the project : need to be save
 */
function deleteonclick(username){
	var option = document.createElement("option");
	option.text = "Removed";
	option.value = "null";
	$("#"+username).find('select')[0].appendChild(option);
	$("#"+username).find('select').val("null").change();
	$("#"+username).hide('100');
}