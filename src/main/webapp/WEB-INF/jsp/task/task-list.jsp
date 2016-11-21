<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>



<c:set var="baseUrl" value="/project/${projectId}/task" />


<t:base>
<jsp:attribute name="head">
<title>EWIDE - Tasks</title>  
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>  
</jsp:attribute>

<jsp:attribute name="footer">
<div class="footer">
</div>
</jsp:attribute>

<jsp:attribute name="javascript">
	<script type="text/javascript" src="<c:url value="/resources/js/task.js" />"></script>
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
				<div class="dropdown col-md-3">
			  		<button class="btn btn-default dropdown-toggle" type="button" id="state-dropDown" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
			    		<c:choose>
							<c:when test="${param['state'].equalsIgnoreCase('inactive')}">
								Inactive
							</c:when>
							<c:otherwise>
								Active
							</c:otherwise>
						</c:choose>
			    		<span class="caret"></span>
  					</button>
					  <ul class="dropdown-menu" aria-labelledby="state-dropDown">
					    <li><a href="<c:url value="${baseUrl}"><c:param name="state" value="active"/><c:param name="owner" value="${param['owner']}" /></c:url>">Active</a></li>
					    <li><a href="<c:url value="${baseUrl}"><c:param name="state" value="inactive"/><c:param name="owner" value="${param['owner']}" /></c:url>">Inactive</a></li>
					  </ul>
				</div>
				<span class="pull-right">
					<c:choose>
						<c:when test="${param['owner'].equalsIgnoreCase('me')}">
							<a class="btn btn-default" href="<c:url value="${baseUrl}"></c:url>">	<i class="material-icons md-18 valign">visibility</i> Show all tasks</a>
						</c:when>
						<c:otherwise>
							<a class="btn btn-default" 
							href="<c:url value="${baseUrl}"><c:param name="state" value="${param['state']}"/><c:param name="owner" value="me" /></c:url>">	<i class="material-icons md-18 valign">visibility_off</i> Only my tasks</a>
						</c:otherwise>
					</c:choose>
					<a class="btn btn-primary" href="<c:url value="/project/${projectId}/task/create"/>">	<i class="material-icons md-18 valign">add</i>New task</a>					
				</span>
			</div>
			<hr>
			<div class="col-md-12">
			<c:forEach var="task" items="${taskList}">
				<div class="row task-row">
					<div class="col-md-12">
					<c:choose>
						<c:when test="${task.state.equalsIgnoreCase('New')}">
							<div class="bs-callout bs-callout-info task-card" data-task="${task.taskID}" data-state="${task.state}">
						</c:when>
						<c:when test="${task.state.equalsIgnoreCase('InProgress')}">
							<div class="bs-callout bs-callout-success task-card" data-task="${task.taskID}" data-state="${task.state}">
						</c:when>
	
						<c:when test="${task.state.equalsIgnoreCase('Closed')}">
							<div class="bs-callout bs-callout-disable task-card" data-task="${task.taskID}" data-state="${task.state}">
						</c:when>
						<c:when test="${task.state.equalsIgnoreCase('Rejected')}">
							<div class="bs-callout bs-callout-danger task-card" data-task="${task.taskID}" data-state="${task.state}">
						</c:when>
						<c:otherwise>
							<div class="bs-callout bs-callout-info task-card" data-task="${task.taskID}" data-state="${task.state}">
						</c:otherwise>
					</c:choose>
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
								<c:if test="${!task.state.equalsIgnoreCase('CLOSED') }">
									<a class="nav-link task-resolve-link"
										href="#" data-task="${task.taskID}"><i
										class="material-icons md-16 valign icon-link">done</i><span class="text-link">Close</span></a> 
								</c:if>
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
	
	