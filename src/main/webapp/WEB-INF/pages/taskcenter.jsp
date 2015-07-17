<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="css/bootstrap.min.css" rel="stylesheet">
	<link href="css/custom/taskcenter.css" rel="stylesheet">
	<link href="plugins/bootstrap-table/bootstrap-table.css" rel="stylesheet">
	
	<!-- Custom js for this template -->
	<script src="js/jquery-2.1.4.min.js"></script>
	<script src="js/bootstrap.js"></script>
	<script src="plugins/bootstrap-table/bootstrap-table.js"></script>
	<script src="plugins/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
	<script src="js/custom/taskcenter.js"></script>
	<script src="js/custom/taskcenter_ajax.js"></script>
	<script src="js/custom/taskcenter_unprocessing.js"></script>
	<script src="js/custom/taskcenter_processing.js"></script>
	<script src="js/custom/taskcenter_publish_task.js"></script>
	<script src="js/custom/taskcenter_edit_task.js"></script>
	<script src="js/custom/util.js"></script>
	<script src="plugins/ueditor1_4_3-utf8-jsp/ueditor.config.js"></script>
	<script src="plugins/ueditor1_4_3-utf8-jsp/ueditor.all.js"></script>
<title>Task Center</title>
</head>
<body>
	<div class="container" style="padding-bottom: 100px">
		<div class="row" style="margin-top: 30px">
			<div class="col-md-4">
				<button id="publishTaskModalButton" class="btn btn-primary">发布任务</button>
			</div>
			<div class="col-md-1 pull-right">
				<a href="<%=request.getContextPath()%>/logout.htm">注销</a>
			</div>
			<div class="pull-right">
				<span>已登录：<security:authentication
						property="principal.username"></security:authentication></span>
			</div>
		</div>
		<div style="padding-top: 30px">
			<ul class="nav nav-tabs" id="myTab" role="tablist">
				<li id="unprocessingTasks" class="active"><a
					href="#showUnprocessingTasks" data-toggle="tab">未处理的任务</a></li>
				<li id="processingTasks"><a href="#showProcessingTasks"
					data-toggle="tab">正在进行中的任务</a></li>
				<li id="processedTasks"><a href="#showProcessedTasks"
					data-toggle="tab">已完成的任务</a></li>
				<li id="myTasks"><a href="#showMyTasks" data-toggle="tab">我的任务</a></li>
			</ul>
			<div class="tab-content">
				<div class="tab-pane active" id="showUnprocessingTasks">
					<table id="unprocessingTaskTable"></table>
				</div>
				<div class="tab-pane" id="showProcessingTasks">
					<table id="processingTaskTable"></table>
				</div>
				<div class="tab-pane" id="showProcessedTasks">
					<table id="processedTaskTable"></table>
				</div>
				<div class="tab-pane" id="showMyTasks">
					<table id="myTaskTable"></table>
				</div>
			</div>
		</div>
		<div class="modal fade" id="publishTaskModal">
			<div class="modal-dialog modal-lg">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title">发布任务</h4>
					</div>
					<div class="modal-body">
						<div class="fontsize_13 fontcolor_5">
							<div class="row margin_5">
								任务名称： <input type="text" id="taskNameInput" style="width: 80%">
							</div>
						</div>
						<div class="row margin_10">
							你的角色： <span id="userSelectPublishTask"> </span>
						</div>
						<div class="row margin_10 border_top padding_top_5">任务详细描述：</div>
						<div class="row margin_10">
							<script id="editor" type="text/plain" style="height: 400px;"></script>
							<script>
								var ue = UE.getEditor('editor');
							</script>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" id="publishTaskResetButton">重置</button>
						<button type="button" class="btn btn-default" id="publishTaskTempCloseButton" data-dismiss="modal">暂时关闭</button>
						<button type="button" class="btn btn-primary" id="publishTaskButton">发布任务</button>
					</div>
				</div>
			</div>
		</div>
		<div class="modal fade" id="editTaskModal">
			<div class="modal-dialog modal-lg">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title">编辑任务</h4>
					</div>
					<div class="modal-body">
						<div class="fontsize_13 fontcolor_5">
							<div class="row margin_5">
								任务名称： <input type="text" id="editTaskNameInput" style="width: 80%">
							</div>
						</div>
						<div class="row margin_10">
							你的角色： <span id="editUserSelectPublishTask"> </span>
						</div>
						<div class="row margin_10 border_top padding_top_5">任务详细描述：</div>
						<div class="row margin_10">
							<script id="editEditor" type="text/plain" style="height: 400px;"></script>
							<script>
								var ueEdit = UE.getEditor('editEditor');
							</script>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" id="editTaskResetButton">重置</button>
						<button type="button" class="btn btn-primary" id="editTaskButton">修改任务</button>
					</div>
				</div>
			</div>
		</div>
		<div class="modal fade" id="processTheTaskModal">
			<div class="modal-dialog modal-lg">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title">任务详情</h4>
					</div>
					<div class="modal-body">
						<div class="fontsize_11 fontcolor_6">
							<div class="row margin_5">
								<div class="col-md-4">
									任务ID：<span id="task_id"></span>
								</div>
								<div class="col-md-4">
									发布时间：<span id="task_addtime"></span>
								</div>
								<div class="col-md-4">
									修改时间：<span id="task_updatetime"></span>
								</div>
							</div>
							<div class="row margin_5">
								<div class="col-md-4">
									任务名称：<span id="task_name"></span>
								</div>
								<div class="col-md-4">
									任务状态：<span id="task_status"></span>
								</div>
								<div class="col-md-4">
									发布者：<span id="task_publisher"></span>
								</div>
							</div>
							<div class="fontsize_13 fontcolor_5">
							<div class="row margin_5 border_top">
								<div class="col-md-12">任务详情：</div>
							</div>
							<div class="row margin_5 border_bottom" style="margin:0px 0px">
								<div class="col-md-12 margin_left_10 margin_right_10"
									id="task_detail"></div>
							</div>
						</div>
						<div class="fontsize_13 fontcolor_5">
							<div class="row margin_5">
								<div class="col-md-5">
									你的角色：<span id="userSelect"> </span>
								</div>
							</div>
							<div class="row margin_5">
								<div class="col-md-7">
									请选择你的预计完成该任务的天数：
									<select id="durationSelect">
										<option value="1" selected="true">1</option>
										<option value="2">2</option>
										<option value="3">3</option>
										<option value="4">4</option>
										<option value="5">5</option>
										<option value="6">6</option>
										<option value="7">7</option>
										<option value="8">8</option>
										<option value="9">9</option>
										<option value="10">10</option>
										<option value="11">11</option>
										<option value="12">12</option>
										<option value="13">13</option>
										<option value="14">14</option>
										<option value="15">15</option>
									</select>
								</div>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						<button type="button" class="btn btn-primary" id="pickTaskButton">领取任务</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="finishTheTaskModal">
			<div class="modal-dialog modal-lg">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title">完成任务</h4>
					</div>
					<div class="modal-body">
						<div class="fontsize_11 fontcolor_6">
							<div class="row margin_5">
								<div class="col-md-4">
									任务ID：<span id="task_id_finish"></span>
								</div>
								<div class="col-md-4">
									发布时间：<span id="task_addtime_finish"></span>
								</div>
								<div class="col-md-4">
									修改时间：<span id="task_updatetime_finish"></span>
								</div>
							</div>
							<div class="row margin_5">
								<div class="col-md-4">
									发布者：<span id="task_publisher_finish"></span>
								</div>
								<div class="col-md-4">
									领取时间：<span id="task_startime_finish"></span>
								</div>
								<div class="col-md-4">
									预计完成时间：<span id="task_endtime_finish"></span>
								</div>
							</div>
							<div class="row margin_5">
								<div class="col-md-4">
									任务名称：<span id="task_name_finish"></span>
								</div>
								<div class="col-md-4">
									任务状态：<span id="task_status_finish"></span>
								</div>
							</div>
						</div>
						<div class="fontsize_13 fontcolor_5">
							<div class="row margin_5 border_top">
								<div class="col-md-12">任务详情：</div>
							</div>
							<div class="row margin_5 border_bottom" style="margin:0px 0px">
								<div class="col-md-12 margin_left_10 margin_right_10" style="padding:10px"
									id="task_detail_finish"></div>
							</div>
						</div>
						<div class="fontsize_13 fontcolor_5">
							<div class="row margin_5">
								<div class="col-md-5">
									你的角色：<span id="userSelect_finish" disabled> </span>
								</div>
							</div>
							<div class="row margin_5">
								<div class="col-md-7">
									结束状态：<select id="finishStatusSelect">
										<option value="-1">请选择</option>
										<option value="1">完成任务</option>
										<option value="2">申请延期</option>
										<option value="3">放弃任务</option>
									</select> <span id="finishStatusDurationSpan" style="display: none">输入延期天数：<input
										id="finishStatusDurationInput" type="text"></span>
								</div>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						<button type="button" class="btn btn-primary"
							id="finishTaskButton">结束任务</button>
					</div>
				</div>
			</div>
		</div>
</body>
</html>