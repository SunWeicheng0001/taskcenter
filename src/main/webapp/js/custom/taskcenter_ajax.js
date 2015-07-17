function getTaskListFromServer(task_status, callback){
	var statusObj = new Object();
	if(task_status != null){
		statusObj.task_status = task_status;
	}
	$.ajax({
		url : ContextPath + '/task/listTask.ajax',
		type : 'post',
		dataType : 'json',
		contentType : 'application/json',
		data : JSON.stringify(statusObj),
		success : function(res) {
			if (res && res.success) {
				console.log(res.value);
				//mergeTaskDetail(res.value);
				callback(res.value);
			} else if (res) {
				alert(res.message);
			} else {
				alert('服务器异常！');
			}
		},
		error : function(err) {
			alert('未知错误');
		}
	});
}

function mergeTaskDetail(rows){
	if(!rows || !rows.length){
		return;
	}
	for(var i=0; i<rows.length; i++){
		var row = rows[i];
		if(row.task_detail_list && row.task_detail_list.length){
			var list = row.task_detail_list;
			row.task_detail = '';
			for(var j=0; j<list.length; j++){
				row.task_detail += list[j];
			}
		}
	}
}


//获取已登录的用户信息
function getMyUser() {
	$.ajax({
		url : ContextPath + '/task/myUser.ajax',
		type : 'post',
		dataType : 'json',
		async : false,
		contentType : 'application/json',
		data : JSON.stringify({}),
		success : function(res) {
			if (res && res.success) {
				globalUser = res.value;
				console.log(globalUser);
				$('#userSelectPublishTask').text(globalUser.username+" "+globalUser.usernickname);
				$('#editUserSelectPublishTask').text(globalUser.username+" "+globalUser.usernickname);
				$('#userSelect').text(globalUser.username+" "+globalUser.usernickname);
				$('#userSelect_finish').text(globalUser.username+" "+globalUser.usernickname);
			} else if (res) {
				alert(res.message);
			} else {
				alert('服务器异常！');
			}
		},
		error : function(err) {
			alert('未知错误');
		}
	});
}

//发布任务
function publishTask() {
	var task_name = $('#taskNameInput').val();
	var task_detail = ue.getContent();
	var min_name_len = 5;
	var min_detail_len = 10;
	if (!task_name || task_name.length < min_name_len) {
		alert('请输入至少' + min_name_len + '个字的任务名称。');
		return;
	}
	var plainContent = ue.getContentTxt();
	if (!plainContent || plainContent.length < min_detail_len) {
		alert('请输入至少' + min_detail_len + '个字的任务详细描述。');
		return;
	}

	$.ajax({
		url : ContextPath + '/task/publishTask.ajax',
		type : 'post',
		dataType : 'json',
		contentType : 'application/json',
		data : JSON.stringify({
			task_name : task_name,
			task_detail : task_detail,
		}),
		success : function(res) {
			if (res && res.success) {
				alert('发布成功！');
				$('#publishTaskModal').modal('hide');
				refreshTab();
			} else if (res) {
				alert(res.message);
			} else {
				alert('服务器异常！');
			}
		},
		error : function(err) {
			alert('未知错误');
		}
	});
}

function deleteTask(){
	var taskname = globalRow.task_name;
	var taskid = globalRow.task_id;
	var userid = globalUser.userid;
	$.ajax({
		url : ContextPath + '/task/deleteTask.ajax',
		type : 'post',
		dataType : 'json',
		contentType : 'application/json',
		data : JSON.stringify({
			task_name : taskname,
			task_id :taskid,
			userid : userid
		}),
		success : function(res) {
			if (res && res.success) {
				alert('删除成功！');
				refreshTab();
			} else if (res) {
				alert(res.message);
			} else {
				alert('服务器异常！');
			}
		},
		error : function(err) {
			alert('未知错误');
		}
	});
}

function editTask(){
	var task_name = $('#editTaskNameInput').val();
	var task_detail = ueEdit.getContent();
	var task_id = globalRow.task_id;
	var min_name_len = 5;
	var min_detail_len = 10;
	if (!task_name || task_name.length < min_name_len) {
		alert('请输入至少' + min_name_len + '个字的任务名称。');
		return;
	}
	var plainContent = ueEdit.getContentTxt();
	if (!plainContent || plainContent.length < min_detail_len) {
		alert('请输入至少' + min_detail_len + '个字的任务详细描述。');
		return;
	}

	$.ajax({
		url : ContextPath + '/task/editTask.ajax',
		type : 'post',
		dataType : 'json',
		contentType : 'application/json',
		data : JSON.stringify({
			task_id : task_id,
			task_name : task_name,
			task_detail : task_detail,
		}),
		success : function(res) {
			if (res && res.success) {
				alert('修改成功！');
				$('#editTaskModal').modal('hide');
				refreshTab();
			} else if (res) {
				alert(res.message);
			} else {
				alert('服务器异常！');
			}
		},
		error : function(err) {
			alert('未知错误');
		}
	});
}
function pickTask(){
	var userid = globalUser.userid;
	var task_id = globalRow.task_id;
	var selectTime = $("#durationSelect").val();
	$.ajax({
		url : ContextPath + '/task/pickTask.ajax',
		type : 'post',
		dataType : 'json',
		contentType : 'application/json',
		data : JSON.stringify({
			task_id : task_id,
			userid :userid,
			duration : selectTime
		}),
		success : function(res) {
			if (res && res.success) {
				alert('领取成功！');
				$('#processTheTaskModal').modal('hide');
				refreshTab();
			} else if (res) {
				alert(res.message);
			} else {
				alert('服务器异常！');
			}
		},
		error : function(err) {
			alert('未知错误');
		}
	});
}

function finishTask(){
	var finishTypeVal = $('#finishStatusSelect').val();
	if(finishTypeVal == -1){
		alert("请选择完成类型");
		return;
	}
	if(finishTypeVal == 1){//完成任务
		
	}
	else if(finishTypeVal == 2){//申请延期
		
	}
	else if(finishTypeVal == 3){//放弃任务
		
	}
}

function toFinishTask(){
	var userid = globalUser.userid;
	var task_id = globalRow.task_id;
	$.ajax({
		url : ContextPath + "/finishTask.ajax",
		type : "post",
		dataType : "json",
		contentType: "application/json",
		data : JSON.stringify({
			userid: userid,
			task_id: task_id,
			
		}),
		success : function(res){
			if(res && res.success){
				alert("任务完成");
				$('#finishTheTaskModal').modal('hide');
				refreshTab();
			}else if (res) {
				alert(res.message);
			} else {
				alert('服务器异常！');
			}
		},
		error: function(res){
			alert('未知错误');
		}
		
	});
}