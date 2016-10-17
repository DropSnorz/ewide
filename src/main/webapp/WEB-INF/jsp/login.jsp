<%@ page language="java" contentType="text/html; charset=windows-1256"
    pageEncoding="windows-1256"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
	<title>EWIDE</title>
	<link rel="shortcut icon" href="resources/images/favicon.ico">
	<link rel="shortcut icon" href="resources/images/favicon.ico"> 
	<!-- CSS dependencies -->
	<link rel="stylesheet" type="text/css" href="resources/bootstrap/css/bootstrap.css" />
	<link rel="stylesheet" type="text/css" href="resources/css/font-awesome.min.css" />
	<link rel="stylesheet" type="text/css" href="resources/css/font-style.css" />
	<link rel="stylesheet" type="text/css" href="resources/css/md-icons.css" />
	<link rel="stylesheet" href="resources/css/jquery.scrollbar.css">
	<link rel="stylesheet" type="text/css" href="resources/css/main.css"/>
</head>
<body>
	<!-- Fixed navbar -->
	<nav class="navbar navbar-inverse">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
					<span class="sr-only">Toggle navigation</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="index">EWIDE</a>
			</div>
			<div id="navbar" class="navbar-collapse collapse">
				<ul class="nav navbar-nav">
					<li><a href="index">Home</a></li>
					<li><a href="#about">About</a></li>
					<li><a href="#contact">Contact</a></li>
					<li class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Dropdown <span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="#">Action</a></li>
							<li><a href="#">Another action</a></li>
							<li><a href="#">Something else here</a></li>
							<li role="separator" class="divider"></li>
							<li class="dropdown-header">Nav header</li>
							<li><a href="#">Separated link</a></li>
							<li><a href="#">One more separated link</a></li>
						</ul>
					</li>
				</ul>

			</div><!--/.nav-collapse -->
		</div>
	</nav>


	<div class="container">
		<div class="row">
			<!-- Logout message -->
			<c:if test="${param.logout!= null}">
				<div class="col-md-12">
					<div class="alert alert-success" role="alert">
						 <i class="material-icons">check</i>
						 You have successfully logged out!
					</div>
				</div>
			</c:if>
			<div class="col-md-6 col-border-right">
				<div class="ewide-form">
					<!-- Login error message -->
					<c:if test="${param.error != null}">
						<div class="alert alert-danger" role="alert">
							<i class="material-icons">error</i>
							<span class="sr-only">Error:</span>
							${sessionScope.SPRING_SECURITY_LAST_EXCEPTION.message}
						</div>
					</c:if>
					<form name="formlogin" class="form-signin" action="login" method="post">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
						<h2 class="form-signin-heading">Se connecter</h2>
						<label for="inputEmail" class="sr-only">Email address</label>
						<input name="email" type="email" id="email" class="form-control" placeholder="Email address" autofocus><br>
						<label for="inputPassword" class="sr-only">Password</label>
						<input name="password" type="password" id="password" class="form-control" placeholder="Password">
						<br>
						<button class="btn btn-lg btn-primary btn-block pix-btn-primary" name="submit" type="submit">Sign in</button>
					</form>
					<br><br>
				</div>
			</div>
			<div class="col-md-6">
				<div class="ewide-form">
					<form name="formSignUp" class="form-signup" action="Signup" method="post">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
						<h2 class="form-signup-heading">S'inscrire</h2>
						<label for="inputEmail" class="sr-only">Email address</label>
						<input name="email" type="email" id="email" class="form-control" placeholder="Email address" autofocus><br>
						<label for="inputPassword" class="sr-only">Password</label>
						<input name="password" type="password" id="password" class="form-control" placeholder="Password">
						<br>
						<button class="btn btn-lg btn-success btn-block pix-btn-primary" name="submit" type="submit">Sign Up</button>
					</form>
					<br><br>
				</div>
			</div>
		</div>
	</div> <!-- /container -->
	
	<!-- Javascripts -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script type="text/javascript" src="resources/js/jquery-1.11.2.js"></script>
	<script type="text/javascript" src="resources/js/jquery-ui.js"></script>
	<script type="text/javascript" src="resources/bootstrap/js/bootstrap.js"></script>
</body>
</html>