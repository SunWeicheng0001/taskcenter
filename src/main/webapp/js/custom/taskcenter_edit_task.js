function editTaskModal() {
	$('#editTaskModal').modal('show');

	$('#editTaskModal').css('z-index', 100);

	$('#editTaskNameInput').val(globalRow.task_name);
	ueEdit.setContent(globalRow.task_detail);

	$('.modal-backdrop').css('z-index', 99);
}

function editTaskReset(){
	$('#editTNameInput').val(globalRow.task_name);
	ueEdit.setContent(globalRow.task_detail);
}
