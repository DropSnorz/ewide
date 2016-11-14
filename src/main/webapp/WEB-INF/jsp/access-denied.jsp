<%@ page language="java" contentType="text/html; charset=windows-1256"
    pageEncoding="windows-1256"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page isELIgnored="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
	<title>EWIDE</title>
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
	<!-- Header Template -->
	<jsp:include page="header.jsp"/>


	<div class="container">
		<div class="col-md-12">
			<div class="alert alert-danger" role="alert">
				 <i class="material-icons">error_outline</i>
				 <strong>Access denied !</strong> You don't have permission to access this resource
			</div>
		</div>
	</div>
 <!-- /container -->
	
	<!-- Javascripts -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script type="text/javascript" src="resources/js/jquery-1.11.2.js"></script>
	<script type="text/javascript" src="resources/js/jquery-ui.js"></script>
	<script type="text/javascript" src="resources/bootstrap/js/bootstrap.js"></script>
</body>
</html>