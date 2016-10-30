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
			console.log(data);
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
		$(this).closest( "tr" ).find('select')[0].appendChild(option);
		$(this).closest( "tr" ).find('select').val("null").change();
		$(this).closest( "tr" ).hide('100');
	});
});