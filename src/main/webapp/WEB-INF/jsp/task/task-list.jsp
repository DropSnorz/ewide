<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
		<div class="col-md-8 col-xs-12 col-md-offset-2">
			<jsp:include page="../include/alert.jsp" />
			<h1 class="text-center">
				<strong>Tasks</strong>
			</h1>

			<div class="row">
				<div class="pull-right">
					<a class="btn btn-primary"
						href="<c:url value="/project/${projectId}/task/create"/>">	<i class="material-icons md-18 valign">add</i>New task</a>
				</div>
			</div>
			<hr>
			<div class="col-md-12">
			<c:forEach var="task" items="${taskList}">
				<div class="row task-row">
					<div class="col-md-12">
					<div class="bs-callout bs-callout-info">
							<small class="pull-right">On <fmt:formatDate
									value="${task.date}" pattern="yyyy-MM-dd" /></small>
							<p>
								<c:choose>
								  <c:when test="${task.type.equalsIgnoreCase('TODO')}">
								  	<i class="material-icons md-18 valign" title="Todo">label</i>
								  </c:when>
								  <c:when test="${task.type.equalsIgnoreCase('BUG')}">
								  	<i class="material-icons md-18 valign" title="Bug">bug_report</i>
								  </c:when>
								  <c:otherwise>
								    <i class="material-icons md-18 valign" title="Custom task">date_range</i>
								  </c:otherwise>
								</c:choose>
								<strong>${task.text}</strong>
							</p>
							<span>By ${task.user.username}</span>
							<div class="pull-right">
								<a class="nav-link"
									href="<c:url value="/project/${projectId}/task/${task.taskID}/edit"/>"><i
									class="material-icons md-16 valign">edit</i>Edit</a> 
								<a class="nav-link"
									href="<c:url value="/project/${projectId}/task/${task.taskID}/resolve"/>"><i
									class="material-icons md-16 valign">done</i>Resolve</a> 
								<a class="nav-link"
									href="<c:url value="/project/${projectId}/task/${task.taskID}/delete"/>"><i
									class="material-icons md-16 valign">delete</i>Delete</a>
							</div>
						</div>
					</div>
				</div>
			</c:forEach>
			</div>
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
		src="<c:url value="/resources/js/custom.js" />" /></script>
</body>
</html>