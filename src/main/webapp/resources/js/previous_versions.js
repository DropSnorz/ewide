$( "#previousVersions" ).click(function() {
	var path = $ ("#myTab li.active a").attr("data-efileid");
	if (path == undefined)
	alert( "Please open a file before trying to check for previous versions." );
	else {
		var regx = /GitRepos-[^-]*-/;
		var match = regx.exec(path);
		if (match == null){
			regx = /GitRepos\/[^\/]*\//;
			match = regx.exec(path);
		}
		regx = /\/home\/[^\/]*\//;
		var match2 = regx.exec(path);
		path = path.replace(match,"");
		path = path.replace(match2,"");
		path = path.replace("-","%3F");
		res = path.replace("/","%3F");
		while (res != path){
			path = res;
			res = path.replace("/","%3F");
		}
		alert(path);
		
		window.location.href = "versions/"+path+"/display";
	}
});