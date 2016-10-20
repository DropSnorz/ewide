/**
 * EWIDE
 * Copyright 2016
 */

	
$( window ).load(function() {
	alert("test2");
	//$("form").live( "submit", function( event ) {
	$('#users_roles').submit(function(event ) {
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
	
	
	
});