<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!doctype html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="shortcut icon" href="<c:url value="/resources/images/favicon.ico" />">
	<!-- CSS dependencies -->
	<link href="<c:url value="/resources/bootstrap/css/bootstrap.css" />" rel="stylesheet">
	<link href="<c:url value="/resources/css/font-awesome.min.css" />" rel="stylesheet">
	<link href="<c:url value="/resources/css/font-awesome.min.css" />" rel="stylesheet">
	<link href="<c:url value="/resources/css/md-icons.css" />" rel="stylesheet">
	<link href="<c:url value="/resources/css/main.css" />" rel="stylesheet">
	<link href="<c:url value="/resources/css/component.css" />" rel="stylesheet">
	<link href="<c:url value="/resources/css/jquery-ui.css" />" rel="stylesheet">
	
</head>
<body>
	<!-- Fixed navbar -->
	<nav class="navbar navbar-inverse main_nav">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
					<span class="sr-only">Toggle navigation</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="<c:url value="/index"/>">EWIDE</a>
			</div>
			<div id="navbar" class="navbar-collapse collapse">
				<ul class="nav navbar-nav">
					<li><a href="<c:url value="/dashboard"/>">Dashboard</a></li>
					<c:if test="${fn:contains(pageContext.request.requestURL,\"dashboard\")}">
						<li><a href="<c:url value="/newproject"/>">Create Project</a></li>
					</c:if>
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<li>
						<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Moi <span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="#">Mot de passe</a></li>
							<li role="separator" class="divider"></li>
							<li><a href="<c:url value="/logout"/>">Sign Out</a></li>
						</ul>
					</li>
				</ul>
			</div><!--/.nav-collapse -->
		</div>
	</nav>
</body>
</html>