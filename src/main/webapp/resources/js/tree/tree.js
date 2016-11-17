/**
 * EWIDE
 * Copyright 2016
 */

$(function () {
	var projectid = $("meta[name='_project_id']").attr("content");
	//localStorage[projectid]="";
	
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
//					var temp_files = [];
//					$(jQuery.parseJSON(JSON.stringify(respond))).each(function() {  
//						item = {};
//						item ["id"] = this.fileID;
//						item ["text"] = this.name+"."+this.type;
//						item ["icon"] = "jstree-file";
//				        temp_files.push(item);
//					});
//					
//					files = temp_files;
//					console.log(JSON.stringify(files));
					$('#tree')
							.jstree({ 'core' : {
							    'data' : [
							       {
							         'text' : 'Project Name',
							         'state' : {
							           'opened' : true,
							           'selected' : true
							         },
							         'children' : respond
							      }
							    ]
							},
							'force_text' : true,
							'themes' : {
								'responsive' : true,
								'variant' : 'small',
								'stripes' : true
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
													setTimeout(function () { inst.edit(new_node); },0);
												});
											}
										},
										"create_file" : {
											"label"				: "File",
											"action"			: function (data) {
												var inst = $.jstree.reference(data.reference),
													obj = inst.get_node(data.reference);
												inst.create_node(obj, { type : "file" }, "last", function (new_node) {
													setTimeout(function () { inst.edit(new_node); },0);
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
							'plugins' : ['state','dnd','sort','types','contextmenu','unique'] }
							)
							.on('changed.jstree', function (e, data) {
								var token = $("meta[name='_csrf']").attr("content");
					    		 var header = $("meta[name='_csrf_header']").attr("content");
					    		 var projectid = $("meta[name='_project_id']").attr("content");
					    		 
					    		 if(data.node){
					    			 $.ajax({
							    			type:"POST",
							    			url:"files",
							    			data:"file="+data.selected.join(':'),
							    		    beforeSend: function(xhr){
							    		        xhr.setRequestHeader(header, token);
							    		    },
							    			success:function(respond){
							    				var res = $.parseJSON(respond);
							    				if(res['contents']&&res['contents']!=""&&data.node.text){
							    					
							    					var id = $('#myTab').children().length;
												    var tabId = 'file_' + id;
												    //if($('#myTab a[fileid="'+data.selected.join(':')+'"]').length != 0){
												    //alert($('#myTab a[data-fileid="'+data.selected.join(':')+'"]'));
												    var new_path = data.selected.join(':');
												    
												    new_path = new_path.split('\\').join('-');
												    
												    if($('#myTab a[data-efileid="'+new_path+'"]').length != 0){
												    	$('#myTab a[data-efileid="'+new_path+'"]').click();
												    }else{
												    	$('#myTab').append('<li><a class="file_tab" id="tab_'+data.selected.join(':')+'" data-efileid="'+new_path+'" data-target="#'+tabId+'" data-toggle="tab" href="#">'+data.node.text+'</a></li>');
													    $('.tab-content').append('<div class="tab-pane" id="' + tabId + '"><div id="code_'+tabId+'"></div></div>');
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
														
														filecodeEditor.setValue(res['contents']);
														filecodeEditor.gotoLine(1);
														var tt =JSON.stringify($('#tree').jstree().get_json());
														$('#myTab a[data-efileid="'+new_path+'"]').click();
														
														if( localStorage.getItem(projectid) !== null && localStorage.getItem(projectid) !== "" ) {
															var pfiles =  JSON.parse(localStorage[projectid]);
															item = {};
															var exist = false;
															for (var i=0 ; i <pfiles.length ; i++){
																if(pfiles[i].id==data.selected.join(':')){
																	exist=true;
																	pfiles[i].content=res['contents'];
																}
															}
															if(!exist){
																item ["id"] = data.selected.join(':');
																item ["content"] = res['contents'];
																pfiles.push(item);
															}
															
															localStorage[projectid]=JSON.stringify(pfiles);															
														}else{
															item = {};
															item ["id"] = data.selected.join(':');
															item ["content"] = res['contents'];
															var pfiles = [];
															pfiles.push(item);
															localStorage[projectid]=JSON.stringify(pfiles);
														}
														//localStorage[projectid] = res['contents'];
														//console.log(tt);
												    }
												    
							    				}
							    			}
							    		});
					    		 }
								
								
								
							});
				}
			});
			
			$('#save_project').click(function(e){
				var files = "";
				var token = $("meta[name='_csrf']").attr("content");
	    		 var header = $("meta[name='_csrf_header']").attr("content");
				var projectid = $("meta[name='_project_id']").attr("content");
				files = localStorage[projectid];
				files = files.replace(/\\\\/g, '\\');
				alert(files);
				$.ajax({
	    			type:"POST",
	    			url:"save",
	    			data:"file="+files,
	    		    beforeSend: function(xhr){
	    		        xhr.setRequestHeader(header, token);
	    		    },
	    			success:function(respond){
						    alert(JSON.stringify(respond));
	    			}
	    		});
			});
			
			
//			$('#tree')
//				.jstree({ 'core' : {
//				    'data' : [
//				       {
//				         'text' : 'Project Name',
//				         'state' : {
//				           'opened' : true,
//				           'selected' : true
//				         },
//				         'children' : [
//				           { 'text' : 'Child 1' },
//				           'Child 2'
//				         ]
//				      }
//				    ]
//				},
//				'force_text' : true,
//				'themes' : {
//					'responsive' : true,
//					'variant' : 'small',
//					'stripes' : true
//				} });
		});