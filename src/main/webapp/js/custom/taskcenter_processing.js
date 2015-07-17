function showProcessingTasks() {
	globalTab = "processing";
	console.log("showprocess");
	$('#processingTaskTable').bootstrapTable('destroy');
	getTaskListFromServer('正在进行中', createProcessingTaskTable);
}

function createProcessingTaskTable(data) {
	processingFormat(data);
	console.log(data);
	$('#processingTaskTable').bootstrapTable('destroy').bootstrapTable({
		columns : [ {
			title : '编号',
			field : 'task_id',
			sortable : true,
			width : 20,
		}, {
			title : '任务名',
			field : 'task_name',
			width : 140,
		}, {
			title : '任务内容',
			field : 'task_detail',
		},{
			title : '发布者',
			field : 'publisher_name',
			sortable : true,
			width : 70,
		},{
			title : '执行者',
			field : 'executor_name',
			sortable : true,
			width : 70,
		}, {
			title : '开始时间',
			field : 'starttime_format',
			sortable : true,
			width : 120,
		}, {
			title : '预计完成时间',
			field : 'endtime_format',
			sortable : true,
			width : 120,
		}, {
			title : '操作',
			field : 'processTheTask',
			formatter : finishTheTaskIcon,
			width : 90,
			events : window.operateEvents = {
				'click .finish' : function(e, value, row, index) {
					globalRow = row;

					$('#finishTheTaskModal').modal('show');
					$('#task_id_finish').text(row.task_id);
					$('#task_addtime_finish').text(row.addtime_format);
					$('#task_updatetime_finish').text(row.updatetime_format);
					$('#task_name_finish').text(row.task_name);
					$('#task_status_finish').text(row.task_status);
					$('#task_publisher_finish').text(row.publisher_name);
					$('#task_detail_finish').html(row.task_detail);
					$('#task_startime_finish').text(row.starttime_format);
					$('#task_endtime_finish').text(row.endtime_format);

					$('#finishStatusDurationSpan').hide();
					$('#finishStatusSelect').val(-1);
				},
				'click .edit' : function(e, value, row, index){
					globalRow = row;
					editTaskModal();
				}
			}
		} ],
		sortName : 'starttime_format',
		sortOrder : 'desc',
		classes : tableClasses()
	}).bootstrapTable('load', data);
}


function processingFormat(rows) {
	for (var i = 0; i < rows.length; i++) {
		rows[i].addtime_format = new Date(rows[i].addtime).pattern("MM-dd hh:mm:ss");
		rows[i].updatetime_format = new Date(rows[i].updatetime).pattern("MM-dd hh:mm:ss");
		if (rows[i].processInfo) {
			rows[i].starttime_format = new Date(rows[i].processInfo.addtime).pattern("MM-dd hh:mm:ss");
			rows[i].executor_name = rows[i].processInfo.executor;//显示昵称
			var endTime = new Date(rows[i].processInfo.addtime);
			endTime.setDate(endTime.getDate()+ rows[i].processInfo.process_duration);
			rows[i].endtime_format = endTime.pattern("MM-dd hh:mm:ss");
		}
			rows[i].publisher_name=rows[i].usernickname;//显示昵称
	}
}

function finishTheTaskIcon(value, row) {
	if (row.processInfo.user_id == globalUser.userid) {
		return "<a class='finish' href='javascript:void(0)'style=''>完成任务</a><br>"
			+ "<a class='edit pull-right' href='javascript:void(0)'style='font-size:9px; color:#999;margin-top:10px'>编辑</a>";
	} else {
		return '';
	}
}

function finishStatusSelectChange() {
	if ($('#finishStatusSelect').val() == '2') { // 申请延期
		$('#finishStatusDurationSpan').show();
		$('#finishStatusDurationInput').val('');
	} else {
		$('#finishStatusDurationSpan').hide();
	}
}

function finishTask() {
	var finishStatus = $('#finishStatusSelect').val();
	switch (finishStatus) {
	case '1': // 正常结束
		endTask();
		break;
	case '2': // 申请延期
		var delay = $('#finishStatusDurationInput').val();
		if (!/^[0-9]*[1-9][0-9]*$/.test(delay)) {
			alert('请输入延期天数（正整数）');
			return;
		}
		delayTask(delay);
		break;
	case '3': // 撤销任务
		cancelTask();
		break;
	default:
		alert('请选择结束状态后再提交。');
	}
}