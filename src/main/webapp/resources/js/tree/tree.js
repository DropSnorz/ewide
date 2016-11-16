/**
 * EWIDE
 * Copyright 2016
 */

$(function () {
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
					var temp_files = [];
					$(jQuery.parseJSON(JSON.stringify(respond))).each(function() {  
						item = {};
						item ["id"] = this.fileID;
						item ["text"] = this.name+"."+this.type;
						item ["icon"] = "jstree-file";
				        temp_files.push(item);
					});
					
					files = temp_files;
					console.log(JSON.stringify(files));
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
							} })
							.on('changed.jstree', function (e, data) {
								codeEditor.setValue(data.selected.join(':'));
								codeEditor.gotoLine(1);
							});
				}
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