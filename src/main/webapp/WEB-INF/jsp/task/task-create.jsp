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
			<h1 class="text-center"><strong>New Task</strong></h1>
			<hr>
			
			<spring:hasBindErrors name="taskForm">
				<div class="alert alert-danger" role="alert">
					<i class="material-icons">error</i>
					<span class="sr-only">Error: </span>
					<c:forEach var="error" items="${errors.allErrors}">
							<b><spring:message message="${error}" /></b>		
					</c:forEach>
				</div>
			</spring:hasBindErrors>
			
			<form:form name="taskForm" method="post" action="create">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			
				<div class="col-md-12">
			
					<div class="form-group">
						<label for="taskText">Task label</label>
						<input type="text" class="form-control" name="taskText" id="taskText" placeholder="Task label" value="${taskText}">
					</div>
					
					<div class="form-group">
						<label for="taskDescription">Task Description</label>
						<textarea class="form-control" name="taskDescription" id="taskDescription" placeholder="Task Description">${taskDescription}</textarea>
					</div>
					
					<div class="form-group">
						<label for="taskType">Type</label>
						<select class="form-control" name="taskType" id="taskType">
							<option value="TODO" <c:if test="${taskType == 'TODO' }"> selected="selected" </c:if> >TODO</option>
							<option value="Bug" <c:if test="${taskType == 'Bug' }"> selected="selected" </c:if>>Bug</option>
						</select>
					</div>
					
					<div class="form-group">
						<label for="taskState">State</label>
					
						<select class="form-control" name="taskState" id="taskState">
							<option value="New" <c:if test="${taskState == 'New' }"> selected="selected" </c:if>>New</option>
							<option value="InProgress" <c:if test="${taskState == 'InProgress' }"> selected="selected" </c:if>>InProgress</option>
							<option value="Closed" <c:if test="${taskState == 'Closed' }"> selected="selected" </c:if>>Closed</option>
						</select>
					</div>
				</div>
				
				<div class="col-md-9">
					<button class="btn btn-lg btn-success btn-block pix-btn-primary" type="submit">Confirm</button>
				</div>
				<div class="col-md-3">
					<a class="btn btn-lg btn-danger btn-block pix-btn-primary" href="../task">Cancel</a>
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