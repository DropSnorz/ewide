

$( ".task-resolve-link" ).click(processTaskResolve);


function processTaskResolve(event){
	event.preventDefault();
	
	var task = $(event.currentTarget).data('task');
	var card = $(".task-card[data-task="+ task +"]");
	var labelResolve = $(".task-card[data-task="+ task +"] .task-resolve-link .text-link");
	var iconResolve = $(".task-card[data-task="+ task +"] .task-resolve-link .icon-link");


	
	if($(card).data("state").toUpperCase() != "CLOSED"){
		
		$(card).data("state-undo", $(card).data("state").toUpperCase());
		$(card).data("state", "CLOSED");
		$(card).removeClass('bs-callout-info');
		$(card).removeClass('bs-callout-success');
		$(card).removeClass('bs-callout-danger');
		
		$(card).addClass('bs-callout-disable');
		
		$(labelResolve).text("Undo");
		$(iconResolve).text("undo");

		
		updateTaskState(task, "Closed");
	
	}
	else{
		var state = $(card).data("state").toUpperCase();
		var previousState = $(card).data("state-undo");
		$(card).data("state", previousState);
		
		$(card).removeClass('bs-callout-disable');
		$(labelResolve).text("Close");		
		$(iconResolve).text("done");


		switch(previousState.toUpperCase()){
		
		case "NEW":
			$(card).addClass('bs-callout-info');
			break;
		case "INPROGRESS":
			$(card).addClass('bs-callout-success');
			break;
		case "REJECTED":
			$(card).addClass('bs-callout-danger');
			break;

		}

		updateTaskState(task, previousState);

	}


}

function updateTaskState(taskId, state){
    		 var token = $("meta[name='_csrf']").attr("content");
    		 var header = $("meta[name='_csrf_header']").attr("content");
    		 
    		 
    		$.ajax({
    			type:"POST",
    			url:"task/edit/state",
    			data:{ taskId: taskId, state : state},
    		    beforeSend: function(xhr){
    		        xhr.setRequestHeader(header, token);
    		    },
    		});
    
}
