<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
			<h1 class="text-center"><strong>Tasks</strong></h1>
			
			<div class="row">	
				<div class="pull-right">
					<a class="btn btn-primary" href="<c:url value="/project/${projectId}/task/create"/>">New task</a>
				</div>	
			</div>			
			<hr>
			<c:forEach var="task" items="${taskList}">
				<div class="bs-callout bs-callout-info">
					<div class="row">
						<small class="pull-right">On <fmt:formatDate value="${task.date}" pattern="yyyy-MM-dd" /></small>
						<p><strong>${task.text}</strong></p>
						<span>By ${task.user.username}</span>
					</div>
					<div class="row">
					<div class="pull-right">
						<a class="nav-link" href="<c:url value="/project/${projectId}/task/${task.taskID}/edit"/>"><i class="material-icons md-16">edit</i>Edit</a>
						<a class="nav-link" href="<c:url value="/project/${projectId}/task/${task.taskID}/resolve"/>"><i class="material-icons md-16">done</i>Resolve</a>
						<a class="nav-link" href="<c:url value="/project/${projectId}/task/${task.taskID}/delete"/>"><i class="material-icons md-16">delete</i>Delete</a>
					</div>
					</div>
				</div>
			</c:forEach>
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