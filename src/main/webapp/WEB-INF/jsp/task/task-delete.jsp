<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>EWIDE - Tasks</title>
</head>
<body>

	<!-- Header Template -->
	<jsp:include page="../header.jsp" />

	<!-- Page Content -->
	<div class="container">
		<div class="col-md-6 col-xs-12 col-md-offset-3">
			<p class="text-center">Do you want to delete task:</p>
			<p class="text-center">[${task.type}]<strong>${task.text}</strong></span>
			
			<div class="alert alert-info" role="alert">You will definitely delete the task. If you want to keep this task in history, <a class="alert-link" href="#">change the task state</a></div>
			
			<form:form name="taskForm" method="post" action="delete">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			
				<div class="col-md-offset-3 col-md-3">
					<button class="btn btn-lg btn-success btn-block pix-btn-primary" type="submit">Confirm</button>
				</div>
				<div class="col-md-3">
					<a class="btn btn-lg btn-danger btn-block pix-btn-primary" href="../../task">Cancel</a>
				</div>				
			</form:form>
		</div>
	</div>

	<!-- Javascripts -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script type="text/javascript"
		src="<c:url value="/resources/js/jquery-1.11.2.js" />" /></script>
	<script type="text/javascript"
		src="<c:url value="/resources/js/jquery-ui.js" />" /></script>
	<script type="text/javascript"
		src="<c:url value="/resources/bootstrap/js/bootstrap.js" />" /></script>
	<script type="text/javascript"
		src="<c:url value="/resources/js/custom_users.js" />" /></script>
</body>
</html>