/**
 * EWIDE
 * Copyright 2016
 */


$(function () {
	var projectid = $("meta[name='_project_id']").attr("content");
	var user_role = $("meta[name='_user_role']").attr("content");
	var base_path = "";
	var getid = "";
	localStorage.removeItem(projectid);
	var x = $(window).height();
	var y = $('.main_nav')[0].offsetHeight;
	y += $('.ide-tools')[0].offsetHeight;
	var z = x-y;
	var r = document.getElementById('myTab').offsetHeight;
	$('.ide-body').height(z);
	$('#tree').height(z);
	$('#tree').height(z);
	var code_height = z-r-120;
	$('.tab-content, .tab-pane').height(code_height);
	

			$(window).resize(function () {
				var h = Math.max($(window).height() - 0, 420);
				var h = z;
				//$('.ide-body').height(h);
				//$('#container, #data, #data .content').height(h).filter('.default').css('lineHeight', h + 'px');
			}).resize();

			codeArea = $('#code');
			if(codeArea.length > 0){
				var codeEditor = ace.edit("code");
			    codeEditor.setTheme("ace/theme/monokai");
			    codeEditor.getSession().setMode("ace/mode/java");
			    codeEditor.setOptions({
			        enableBasicAutocompletion: true,
			        enableSnippets: true,
			        showPrintMargin: false,
			        enableLiveAutocompletion: true
			    });
			    codeArea
				    .height(code_height)
				;	
			}
			
			var files = [];
			$.ajax({
				type:"GET",
				url:"files",
				success:function(respond){
				//console.log(respond);
				//var jsonObj = $.parseJSON(respond);
				//alert(respond.id);
				base_path = respond.id;
				getid = respond.li_attr.gitid;
					$('#tree')
							.jstree({ 'core' : {
							    'data' : [
							    	/*
							       {
							         'text' : 'Project Name',
							         'id'   : 'GitRepos/'+projectid,
							         //'parent' : '',      
							         'state' : {
							           'opened' : true,
							           'selected' : true
							         },
							         'children' : respond
							      }
							      */
							    	respond
							    ],
							    'check_callback' : function(o, n, p, i, m) {
									if(m && m.dnd && m.pos !== 'i') { return false; }
									if(o === "move_node" || o === "copy_node" || o === "create_node") {
									//if(o === "move_node" || o === "copy_node") {
										if(this.get_node(n).parent === this.get_node(p).id) { return false; }
									}
									return true;
								},
								'force_text' : true,
								'themes' : {
									'responsive' : true,
									'variant' : 'small',
									'stripes' : true
								}
							},
							'contextmenu' : {
								'items' : 
								function(node) {
									var tmp = $.jstree.defaults.contextmenu.items();
									delete tmp.create.action;
									tmp.create.label = "New";
									tmp.create.submenu = {
										"create_folder" : {
											"separator_after"	: true,
											"label"				: "Folder",
											"action"			: function (data) {
												var inst = $.jstree.reference(data.reference),
													obj = inst.get_node(data.reference);
												var real_path = inst.get_path(obj,false,false);
												real_path[0]= base_path;
												var parentpath = real_path.join("/");
												parentpath+='/';
												//var parentpath = inst.get_path(obj,'/',false)+"/";
												
												parentpath = parentpath.split('Project Name').join('GitRepos/'+projectid);
												inst.create_node(obj, { type : "default" }, "last", function (new_node) {
													inst.set_id(new_node, parentpath+new_node.text);
													updateLocal(projectid, new_node.id, "", "folder");
													//setTimeout(function () { inst.edit(new_node); },0);
												});
											}
										},
										"create_file" : {
											"label"				: "File",
											"action"			: function (data) {
												var inst = $.jstree.reference(data.reference),
													obj = inst.get_node(data.reference);
												var real_path = inst.get_path(obj,false,false);
												real_path[0]= base_path;
//												alert(real_path);
//												console.log(real_path);
												var parentpath = real_path.join("/");
												parentpath+='/';
												//var parentpath = inst.get_path(obj,'/',false)+"/";
												
												parentpath = parentpath.split('Project Name').join('GitRepos/'+projectid);
												inst.create_node(obj, { type : "file" }, "last", function (new_node) {
													while(idLocalExist(projectid,parentpath+new_node.text)){
														new_node.text = new_node.text + "2";
													}
													inst.set_id(new_node, parentpath+new_node.text);
													
													updateLocal(projectid, new_node.id, "");
												});
												
											}
										}
									};
									if(this.get_type(node) === "file") {
										delete tmp.create;
									}
									return tmp;
								}
								
								
							},
							'types' : {
								'default' : { 'icon' : 'folder' },
								'file' : { 'valid_children' : [], 'icon' : 'jstree-file' }
							},
							'unique' : {
								'duplicate' : function (name, counter) {
									return name + ' ' + counter;
								}
							},
							'plugins' : ['sort','types','contextmenu','unique'] 
							})
							.on('rename_node.jstree', function (e, data) {
								var inst = $.jstree.reference(data.reference);
								console.log(inst);
								updateLocalpath(projectid, data.node.id, data.node.parent+'/'+data.node.text);
								var new_path = data.node.id;
							    new_path = new_path.split('\\').join('-');
							    $('#myTab a[data-efileid="'+new_path+'"]').html(data.node.text);
							})
							.on('delete_node.jstree', function (e, data) {
								var new_path = data.node.id;
							    new_path = new_path.split('\\').join('-');
							    if($('#myTab a:not(.file_removed)').length != 1){
							    	$('#myTab a[data-efileid="'+new_path+'"]').addClass('file_removed');
							    	$('#myTab a[data-efileid="'+new_path+'"]').hide();
							    	$('#myTab a:not([data-efileid="'+new_path+'"])').click();
							    }else{
							    	$('#myTab a[data-efileid="'+new_path+'"]').addClass('file_removed');
							    	$('#myTab a[data-efileid="'+new_path+'"]').hide();
							    	$('#myTab a:not([data-efileid="'+new_path+'"])').click();
							    	$('.welcome_nav_tab').show();
							    	$('.welcome_nav_tab a,.welcome_nav_tab').click();
							    }
								
								data.node.type="deleted";
								updateLocal(projectid,data.node.id,"", "delete");
							})
							.on('create_node.jstree', function (e, data) {
							})
							.on('changed.jstree', function (e, data) {
								var token = $("meta[name='_csrf']").attr("content");
					    		 var header = $("meta[name='_csrf_header']").attr("content");
					    		 var projectid = $("meta[name='_project_id']").attr("content");
					    		 console.log(data.node);
					    		 if(data.node&&data.node.icon==="jstree-file" && data.node.type!="deleted"){
					    			 $.ajax({
							    			type:"POST",
							    			url:"files",
							    			data:"file="+data.selected.join(':'),
							    		    beforeSend: function(xhr){
							    		        xhr.setRequestHeader(header, token);
							    		    },
							    			success:function(respond){
							    				var res = $.parseJSON(respond);
							    				if(data.node.text){
							    					$('.welcome_nav_tab a').addClass('file_removed');
							    					$('.welcome_nav_tab').hide();
							    					var id = $('#myTab').children().length;
												    var tabId = 'file_' + id;
												    var new_path = data.selected.join(':');
												    
												    new_path = new_path.split('\\').join('-');
												    
												    if($('#myTab a[data-efileid="'+new_path+'"]').length != 0){
												    	$('#myTab a[data-efileid="'+new_path+'"]').click();
												    }else{
												    	$('#myTab').append('<li><a class="file_tab" id="tab_'+data.selected.join(':')+'" data-efileid="'+new_path+'" data-target="#'+tabId+'" data-toggle="tab" href="#">'+data.node.text+'</a></li>');
													    $('.tab-content').append('<div class="tab-pane" id="' + tabId + '"><div class="code_div" data-filecode="'+new_path+'" id="code_'+tabId+'"></div></div>');
													    $('#myTab a#tab_'+tabId+'').click();
													    var filecodeID = "code_"+tabId;
													    var filecodeArea = $('#'+filecodeID);
														if(codeArea.length > 0){
															var filecodeEditor = ace.edit(filecodeID);
														    filecodeEditor.setTheme("ace/theme/monokai");
														    filecodeEditor.getSession().setMode("ace/mode/java");
														    filecodeEditor.setOptions({
														        enableBasicAutocompletion: true,
														        enableSnippets: true,
														        showPrintMargin: false,
														        enableLiveAutocompletion: true
														    });
														    filecodeArea
															    .height(code_height)
															;	
														    if(user_role=="REPORTER"){
														    	filecodeEditor.setReadOnly(true);	
														    }
														    
														}
														if(res['contents']&&res['contents']!=""){
															filecodeEditor.setValue(""+res['contents']);
														}else{
															filecodeEditor.setValue("");
														}
															
														filecodeEditor.gotoLine(1);
														var tt =JSON.stringify($('#tree').jstree().get_json());
														$('#myTab a[data-efileid="'+new_path+'"]').click();
														updateLocal(projectid, data.selected.join(':'), res['contents']);
												    }
												    
							    				}
							    			}
							    		});
					    		 }
								
								
								
							});
					
				},
				error: function (xhr, ajaxOptions, thrownError) {
		          alert(xhr.status);
		          alert(thrownError);
		        }
			});
				
			
			$('#save_project').click(function(e){
				save(getid);
			});
			 $(document).bind('keydown', function(event) {
	                if (event.ctrlKey || event.metaKey) {
	                    switch (String.fromCharCode(event.which).toLowerCase()) {
	                    case 's':
	                        event.preventDefault();
	                        save(getid);
	                        return false;
	                        break;
	                    }
	                }
	           });
			 
		});

function save(getid){
	var files = "";
	var token = $("meta[name='_csrf']").attr("content");
	 var header = $("meta[name='_csrf_header']").attr("content");
	var projectid = $("meta[name='_project_id']").attr("content");
	files = localStorage[projectid];
	if(files!=null && files!=""){
		//files = files.replace(/\\\\/g, '\\');
		//alert(files);
		var json_files = $.parseJSON(files);
		for(var i = 0; i < json_files.length; i++) {
			if(json_files[i]){
				var new_path = json_files[i].id;
			    new_path = new_path.split('\\').join('-');
			    if($('.code_div[data-filecode="'+new_path+'"]').length != 0){
			    	var filecodeEditor = ace.edit($('.code_div[data-filecode="'+new_path+'"]')[0].id);
			    	//console.log(filecodeEditor.getValue());
			    	json_files[i].content = filecodeEditor.getValue();
			    }
			    var obj = json_files[i];
			    console.log(json_files[i].id);	
			}
		}
		var final_res = JSON.stringify(json_files);
		//final_res = final_res.replace(/\\\\/g, '\\');
		alert(final_res);
		
		$.ajax({
			type:"POST",
			url:"save",
			//data:"file="+final_res,
			data:{file: final_res, id: getid, confirm: "0"},
		    beforeSend: function(xhr){
		        xhr.setRequestHeader(header, token);
		    },
			success:function(respond){
				if(respond.type == 'error'){
					//TODO confirm save
					alert(JSON.stringify(respond));
				}else{
					alert(JSON.stringify(respond));
				}
			}
		});	
	}
}
function updateLocal(projectid, item_id, item_content, item_type = "filetext", newpath = ""){
	//alert(item_type);
	if( localStorage.getItem(projectid) !== null && localStorage.getItem(projectid) !== "" ) {
		var pfiles =  JSON.parse(localStorage[projectid]);
		item = {};
		var exist = false;
		for (var i=0 ; i <pfiles.length ; i++){
			if(pfiles[i].id==item_id){
				exist=true;
				pfiles[i].content = item_content;
				pfiles[i].ftype = item_type;
				pfiles[i].newpath = newpath;
			}
		}
		if(!exist){
			item["id"] = item_id;
			item["content"] = item_content;
			item["ftype"] = item_type;
			item["newpath"] = newpath;
			pfiles.push(item);
		}
		
		localStorage[projectid]=JSON.stringify(pfiles);
	}else{
		item = {};
		item ["id"] = item_id;
		item ["content"] = item_content;
		item["ftype"] = item_type;
		item["newpath"] = newpath;
		var pfiles = [];
		pfiles.push(item);
		localStorage[projectid]=JSON.stringify(pfiles);
	}
}

function updateLocalpath(projectid, item_id, newpath = ""){
	if( localStorage.getItem(projectid) !== null && localStorage.getItem(projectid) !== "" ) {
		var pfiles =  JSON.parse(localStorage[projectid]);
		item = {};
		for (var i=0 ; i <pfiles.length ; i++){
			if(pfiles[i].id==item_id){
				pfiles[i].newpath = newpath;
			}
		}
		localStorage[projectid]=JSON.stringify(pfiles);
	}
}
function idLocalExist(projectid, item_id){
	if( localStorage.getItem(projectid) !== null && localStorage.getItem(projectid) !== "" ) {
		var pfiles =  JSON.parse(localStorage[projectid]);
		item = {};
		for (var i=0 ; i <pfiles.length ; i++){
			if(pfiles[i].id==item_id){
				return true;
			}
		}
	}
	return false;
}