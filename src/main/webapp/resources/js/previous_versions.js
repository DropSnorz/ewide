$( "#previousVersions" ).click(function() {
	var path = $ ("#myTab li.active a").attr("data-efileid");
	if (path == undefined)
	alert( "Veuillez ouvrir d'abord un fichier pour en consulter les précédentes versions." );
	else {
		regx = /GitRepos-[^-]*-/;
		match = regx.exec(path);
		if (match == null){
			regx = /GitRepos\/[^\/]*\//;
			match = regx.exec(path);
		}
		path = path.replace(match,"");
		path = path.replace("-","%3F");
		path = path.replace("/","%3F");
		alert(path);
		
		window.location.href = "versions/"+path+"/display";
	}
});