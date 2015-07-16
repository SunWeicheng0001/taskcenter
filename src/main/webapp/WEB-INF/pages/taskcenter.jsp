<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="css/bootstrap.min.css" rel="stylesheet">
	<link href="plugins/bootstrap-table/bootstrap-table.css" rel="stylesheet">
	
	<!-- Custom js for this template -->
	<script src="js/jquery-2.1.4.min.js"></script>
	<script src="js/bootstrap.js"></script>
	<script src="plugins/bootstrap-table/bootstrap-table.js"></script>
	<script src="plugins/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
	<script src="js/custom/taskcenter.js"></script>
	<script src="js/custom/taskcenter_ajax.js"></script>
	<script src="js/custom/taskcenter_unprocessing.js"></script>
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
	
	</div>
</body>
</html>