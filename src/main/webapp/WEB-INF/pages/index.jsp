<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <meta name="description" content="">
  <meta name="author" content="">

  <title>Signin Template for Bootstrap</title>

  <!-- Bootstrap core CSS -->
  <link href="css/bootstrap.min.css" rel="stylesheet">

  <!-- Custom styles for this template -->
  <link href="css/custom/signin.css" rel="stylesheet">
  <script src="js/jquery-2.1.4.min.js"></script>
  <script src="js/custom/signin.js"></script>

</head>

<body>
	<security:authorize access="hasRole('ROLE_USER')">
		<script>
		window.location.href = '<%=request.getContextPath()%>/taskcenter.htm';
 		</script>
	</security:authorize>
<div class="container">

  <form class="form-signin"
  action="<%=request.getContextPath()%>/j_spring_security_check"
  method="POST" accept-charset="utf-8"
  onsubmit="document.charset='utf-8';">
	<h2 class="form-signin-heading">Please sign in</h2>
	<label for="inputUsername" class="sr-only" id="usernameLabel">Username</label>
	<input type="text" name='j_username' id="inputUsername" class="form-control" placeholder="Username" required autofocus>
	<label for="inputPassword" class="sr-only" id="password">Password</label>
	<input type="password" name='j_password' id="inputPassword" class="form-control" placeholder="Password" required>
	<div class="checkbox">
	  <label>
		<input type="checkbox" value="remember-me"> Remember me
	  </label>
	</div>
	<button class="btn btn-lg btn-primary btn-block" type="submit" id="signinButton">Sign in</button>
  </form>

</div> <!-- /container -->
</body>
</html>