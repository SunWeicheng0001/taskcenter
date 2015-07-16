var globalRow = null;
var globalUser = null;
var globalTab = null;
var globalTempTaskname = null;
var globalTempTaskdetail = null;
$(window).load(function() {

	getMyUser();

//	$('#unprocessingTasks').on('show.bs.tab', showUnprocessingTasks);
//	$('#processingTasks').on('show.bs.tab', showProcessingTasks);
//	$('#processedTasks').on('show.bs.tab', showProcessedTasks);
//
//	$('#pickTaskButton').off('click').on('click', pickTask);
	
	$('#editTaskButton').off('click').on('click', editTask);
	$('#editTaskResetButton').off('click').on('click',editTaskReset);
	$('#publishTaskResetButton').off('click').on('click',publishTaskReset);
	$("#publishTaskTempCloseButton").off('click').on('click',publishTaskTempClose);
	$('#publishTaskModalButton').off('click').on('click', publishTaskModal);
	$('#publishTaskButton').off('click').on('click', publishTask);
//	$('#finishTaskButton').off('click').on('click', finishTask);

//	$('#finishStatusSelect').change(finishStatusSelectChange);

	showUnprocessingTasks();

});

function refreshTab(){
	switch(globalTab){
	case 'unprocessing':
		showUnprocessingTasks();
		break;
	case 'processing':
		showProcessingTasks();
		break;
	case 'processed':
		showProcessedTasks();
		break;
	}
}