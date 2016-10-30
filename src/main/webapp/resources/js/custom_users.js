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


	/*
$( document ).ready(function() {
	//alert("test2");
	//$("form").live( "submit", function( event ) {
	$('.delete-user').click(function() {
		
		var option = document.createElement("option");
		option.text = "Removed";
		option.value = "null";
		$(this).closest( "tr" ).find('select')[0].appendChild(option);
		$(this).closest( "tr" ).find('select').val("null").change();
		$(this).closest( "tr" ).hide('100');
	});
	$('#ajaxtest').click(function() { 
//		$.post('ajaxtest', "test", function(response){
//			alert(response);
//		}, 'json');
		var data = {}
		data["producer"] = "test1";
		data["model"] = "test2";
		data["price"] = "test3";
	    var json = { "producer" : "test1", "model" : "test2", "price": "999"};
	    $.ajax({
	    	//type: "POST",
	    	url : 'user_delete',
	        data: data,
	        //data: json,
	        //dataType: 'json',
	        success : function(data) {
	            alert(data);
	        },
	        error: function(xhr, status, error) {
	        	  alert(status+" : "+error);
	        	}
	    });
	});
	
	$('#users_roles2').submit(function(event ) {
        event.preventDefault();
        
        var values = {};
        var temp_str = "";
        var theform = this;
        $.each($(theform).serializeArray(), function(i, field) {
            values[field.name] = field.value;
            temp_str += field.name + ": " + field.value + "\n";
             //alert(this.name);
        });
        alert(temp_str);
        return false;
//	$('select').each(function()
//	{
//		org_val = $(this).val();
//	    $(this).change(function() {
//			if($(this)!=org_val){
//				//alert("Value Changed!");
//				$('#users_submit').removeClass('disabled');
//				$('#users_submit').text('Save');
//			}
//		}
//	);
        
	});
	
	
	
});*/