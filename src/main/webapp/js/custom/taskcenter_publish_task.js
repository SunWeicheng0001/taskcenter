function publishTaskModal() {
	$('#publishTaskModal').modal('show');

	$('#publishTaskModal').css('z-index', 100);

	if(globalTempTaskname!=null)
		$('#taskNameInput').val(globalTempTaskname);
	else
		$('#taskNameInput').val('');
	if(globalTempTaskdetail==null)
		ue.setContent('');
	else
		ue.setContent(globalTempTaskdetail);

	$('.modal-backdrop').css('z-index', 99);
}

function publishTaskTempClose(){
	globalTempTaskname = $('#taskNameInput').val();
	globalTempTaskdetail = ue.getContent();
	$('#publishTaskModal').modal('hide');
}

function publishTaskReset(){
	$('#taskNameInput').val('');
	ue.setContent('');
}