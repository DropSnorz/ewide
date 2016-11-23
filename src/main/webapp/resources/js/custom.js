/**
 * EWIDE
 * Copyright 2016
 */

	
$( window ).load(function() {

	htmlTextArea = $('#the_code');
	if(htmlTextArea.length > 0){
		var editor = ace.edit("the_code");
	    editor.setTheme("ace/theme/monokai");
	    editor.getSession().setMode("ace/mode/java");
	    editor.setOptions({
	        enableBasicAutocompletion: true,
	        enableSnippets: true,
	        showPrintMargin: false,
	        enableLiveAutocompletion: true
	    });
	    htmlTextArea
		    .height(0.65 * $(window).height())
		    .show()
		;	
	}
	htmlTextArea = $('#the_code_2');
	if(htmlTextArea.length > 0){
		var editor = ace.edit("the_code_2");
	    editor.setTheme("ace/theme/monokai");
	    editor.getSession().setMode("ace/mode/java");
	    editor.setOptions({
	        enableBasicAutocompletion: true,
	        enableSnippets: true,
	        showPrintMargin: false,
	        enableLiveAutocompletion: true
	    });
	    htmlTextArea
		    .height(0.65 * $(window).height())
		    .show()
		;
	}

	consoleArea = $('#the_console');
	if(htmlTextArea.length > 0){
		var editor2 = ace.edit("the_console");
	    editor2.setTheme("ace/theme/chaos");
	    editor2.getSession().setMode("ace/mode/text");
	    editor2.setReadOnly(true);
	    editor2.renderer.setShowGutter(false);
	    editor2.setOptions({
	    	highlightActiveLine: false,
	        enableBasicAutocompletion: true,
	        enableSnippets: true,
	        enableLiveAutocompletion: true,
	        showPrintMargin: false
	    });
	    consoleArea
		    //.height(0.16 * $(window).height())
	    	.height(120)
		    .show()
		;	
	}
	

	$(".pix_click, .pix_settings_tab li a, .pix_btn_click, .pix_md_btn").click(function(e) {
		$(".left_button").not(this).find('.ripple').remove();
		$(".pix_settings_tab li a").not(this).find('.ripple').remove();
		
		$(".pix_btn_click, .pix_md_btn").find('.ripple').remove();
			//$(".ripple").remove();
			if ($(this).find('.ripple').length!=0) {
		    return;
		}
		
	  	var posX = $(this).offset().left,
	      	posY = $(this).offset().top,
	      	buttonWidth = $(this).width(),
	      	buttonHeight = $(this).height();

	  	$(this).prepend("<span class='ripple'></span>");

		if (buttonWidth >= buttonHeight) {
			buttonHeight = buttonWidth;
		} else {
			buttonWidth = buttonHeight;
		}

		var x = e.pageX - posX - buttonWidth / 2;
		var y = e.pageY - posY - buttonHeight / 2;

		$(".ripple").css({
			width: buttonWidth,
			height: buttonHeight,
			top: y + 'px',
			left: x + 'px'
		}).addClass("rippleEffect");
	  
	});
});
		
function pix_ripple(e, elm) {
		//$(".ripple").remove();
		if ($(elm).find('.ripple').length!=0) {
	    $(elm).find('.ripple').remove();
	}
	
  	var posX = $(elm).offset().left,
      	posY = $(elm).offset().top,
      	buttonWidth = $(elm).width(),
      	buttonHeight = $(elm).height();

  	$(elm).prepend("<span class='ripple'></span>");

	if (buttonWidth >= buttonHeight) {
		buttonHeight = buttonWidth;
	} else {
		buttonWidth = buttonHeight;
	}

	var x = e.pageX - posX - buttonWidth / 2;
	var y = e.pageY - posY - buttonHeight / 2;

	$(elm).find('.ripple').css({
		width: buttonWidth,
		height: buttonHeight,
		top: y + 'px',
		left: x + 'px'
	}).addClass("rippleEffect");
  
};