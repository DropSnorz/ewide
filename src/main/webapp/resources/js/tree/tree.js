/**
 * EWIDE
 * Copyright 2016
 */

$(function () {
	var projectid = $("meta[name='_project_id']").attr("content");
	
	localStorage.removeItem(projectid);


			$(window).resize(function () {
				var h = Math.max($(window).height() - 0, 420);
				var h = 400;
				$('#container, #data, #tree, #data .content').height(h).filter('.default').css('lineHeight', h + 'px');
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
				    .height(400)
				;	
			}
			
			var files = [];
			$.ajax({
				type:"GET",
				url:"files",
				success:function(respond){
					$('#tree')
							.jstree({ 'core' : {
							    'data' : [
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
								'items' : function(node) {
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
												inst.create_node(obj, { type : "default" }, "last", function (new_node) {
													//setTimeout(function () { inst.edit(new_node); },0);
												});
											}
										},
										"create_file" : {
											"label"				: "File",
											"action"			: function (data) {
												var inst = $.jstree.reference(data.reference),
													obj = inst.get_node(data.reference);
												var parentpath = inst.get_path(obj,'/',true)+"/";
												inst.create_node(obj, { type : "file" }, "last", function (new_node) {
													inst.set_id(new_node, parentpath+new_node.text);
													
													updateLocal(projectid, new_node.id, "");
													
//													if( localStorage.getItem(projectid) !== null && localStorage.getItem(projectid) !== "" ) {
//														var pfiles =  JSON.parse(localStorage[projectid]);
//														item = {};
//														var exist = false;
//														for (var i=0 ; i <pfiles.length ; i++){
//															if(pfiles[i].id==new_node.id){
//																exist=true;
//																pfiles[i].content="";
//															}
//														}
//														if(!exist){
//															item ["id"] = new_node.id;
//															item ["content"] = "";
//															pfiles.push(item);
//														}
//														
//														localStorage[projectid]=JSON.stringify(pfiles);
//													}else{
//														item = {};
//														item ["id"] = new_node.id;
//														item ["content"] = "";
//														var pfiles = [];
//														pfiles.push(item);
//														localStorage[projectid]=JSON.stringify(pfiles);
//													}
													
													//setTimeout(function () { 
													//	inst.edit(new_node);
													//},0);
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
							'plugins' : ['dnd','sort','types','contextmenu','unique'] 
							})
							.on('rename_node.jstree', function (e, data) {
								var inst = $.jstree.reference(data.reference);
								var parentpath = inst.get_path($('#'+data.parent),'\\',true)+"\\";
								alert(parentpath);
								//console.log(data.original);
								alert(data.node.id);
								alert(data.text);
							})
							.on('delete_node.jstree', function (e, data) {
								//alert(projectid);
								//alert(data.node.id);
								data.node.type="deleted";
								//console.log(data.node);
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
															    .height(400)
															;	
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
//														if( localStorage.getItem(projectid) !== null && localStorage.getItem(projectid) !== "" ) {
//															var pfiles =  JSON.parse(localStorage[projectid]);
//															item = {};
//															var exist = false;
//															for (var i=0 ; i <pfiles.length ; i++){
//																if(pfiles[i].id==data.selected.join(':')){
//																	exist=true;
//																	pfiles[i].content=res['contents'];
//																}
//															}
//															if(!exist){
//																item ["id"] = data.selected.join(':');
//																item ["content"] = res['contents'];
//																pfiles.push(item);
//															}
//															
//															localStorage[projectid]=JSON.stringify(pfiles);															
//														}else{
//															item = {};
//															item ["id"] = data.selected.join(':');
//															item ["content"] = res['contents'];
//															var pfiles = [];
//															pfiles.push(item);
//															localStorage[projectid]=JSON.stringify(pfiles);
//														}
														//localStorage[projectid] = res['contents'];
														//console.log(tt);
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
		    			data:"file="+final_res,
		    		    beforeSend: function(xhr){
		    		        xhr.setRequestHeader(header, token);
		    		    },
		    			success:function(respond){
							    alert(JSON.stringify(respond));
		    			}
		    		});	
				}
				
			});
		});

function updateLocal(projectid, item_id, item_content, item_type = "filetext"){
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
			}
		}
		if(!exist){
			item["id"] = item_id;
			item["content"] = item_content;
			item["ftype"] = item_type;
//			item["change"] = "changetext";
			pfiles.push(item);
		}
		
		localStorage[projectid]=JSON.stringify(pfiles);
	}else{
		item = {};
		item ["id"] = item_id;
		item ["content"] = item_content;
		item["ftype"] = item_type;
//		item["change"] = "changetext";
		var pfiles = [];
		pfiles.push(item);
		localStorage[projectid]=JSON.stringify(pfiles);
	}
}