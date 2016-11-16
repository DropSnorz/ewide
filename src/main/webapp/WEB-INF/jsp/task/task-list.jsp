<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>


<t:base>

<jsp:attribute name="head">
<title>EWIDE - Tasks</title>    
</jsp:attribute>

<jsp:body>
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
</jsp:body>
	</t:base>