//-------------------------------------------------------------------------
/**
 * close and open modal
 */

// Get the modal
var modalCompil = document.getElementById("paramCompil");
// Get the button that opens the modal
var btnConfigure = document.getElementById("btnConfigure");
// Get the <span> element that closes the modal
var closeConfigure = document.getElementById("closeConfigure");

// When the user clicks on the cancel button close the modal
closeConfigure.onclick = function() {
	modalCompil.style.display = "none";
};
// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
	if (event.target == modalCompil) {
		modalCompil.style.display = "none";
	}
};

//----------------------------------------------------------------------------
/**
 * if the user click on configure
 */
var configureCompiler=document.getElementById("configureCompiler");
configureCompiler.onclick=function(){
	var compilerString=null;
	$.ajax({
		type:"GET",
		url:"getcompiler",
		async:false,
		success:function(respond){
			if(respond.length>0){
				compilerString= respond;
			}
			else
				compilerString= null;
		}
	});
	if(compilerString!=null){
		var array=compilerString.split(' ');
		if(array[0]=="mvn"){
			document.getElementById("mvncommand").value=compilerString.replace(array[0], '');
			document.getElementById("maven").checked=true;
			document.getElementById("commandCompil").value="";
			document.getElementById("makecommand").value="";
		}
		else{
			if(array[0]=="make"){
				document.getElementById("maven").checked=true;
				document.getElementById("mvncommand").value="";
				document.getElementById("commandCompil").value="";
				document.getElementById("makecommand").value=compilerString.replace(array[0], '');

			}else{
				document.getElementById("mainfileCompil").checked=true;
				document.getElementById("language").value=array[0];
				document.getElementById("mvncommand").value="";
				document.getElementById("makecommand").value="";
				document.getElementById("commandCompil").value=compilerString.replace(array[0], '');
			}
		}
	}
	modalCompil.style.display = "block";
};


//----------------------------------------------------------------------------
/**
 * post the request to configure the compiler
 * don't compile the project
 */
var valideCompiler=document.getElementById("valideCompiler");
valideCompiler.onclick=function(){
	var compilo =$("input[name='compilo']:checked").val();
	var mainfile;
	if(compilo=="cmd"){
		compilo=$("#language").val();
		mainfile=document.getElementById("commandCompil").value;

	}
	else{
		if(compilo=="mvn"){
			mainfile=document.getElementById("mvncommand").value;
		}else{
			mainfile=document.getElementById("makecommand").value;
		}
	}
	if(mainfile.indexOf("|")!=-1 || mainfile.indexOf("\\ ")!=-1 ){
		mainfile="";
	}
	//put in base the compilation configuration
	if(compilo!=null){
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
		$.ajax({
			type:"POST",
			url:"setcompiler",
			data:"compiler="+compilo+"&mainfile="+mainfile,
			beforeSend: function(xhr){
				xhr.setRequestHeader(header, token);
			},
			success:function(respond){
				modalCompil.style.display = "none";
			}
		});
	}
	
};

//----------------------------------------------------------------------------
/**
 * compile
 */
var compilation=document.getElementById("compilation");
compilation.onclick=function(){
	document.getElementById("the_console").innerHTML="loading compilation ... ";
	$.ajax({
		type:"GET",
		url:"compile",
		success:function(respond){
			//si le compilateur n'est pas configurer
			if(respond==null){
				document.getElementById("the_console").innerHTML="no compiler : configure a compiler first";
			}else{
				document.getElementById("the_console").innerHTML=respond;
			}
		}
	});
};

//----------------------------------------------------------------------------
/**
 * download the executable file
 */
var resultCompilation=document.getElementById("resultCompilation");
resultCompilation.onclick=function(){
	$.ajax({
		type:"GET",
		url:"resultfiles",
		success:function(respond){
		}
	});
};

