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
	<script src="plugins/bootstrap-table/bootstrap-table.js"></script>
	<script src="plugins/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
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
		</div>
	</div>
</body>
</html>