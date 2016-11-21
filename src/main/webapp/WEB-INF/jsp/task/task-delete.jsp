<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>


<t:base>

<jsp:attribute name="head">
<title>EWIDE - Delete task</title>    
</jsp:attribute>

<jsp:body>
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
					<a class="btn btn-lg btn-danger btn-block pix-btn-primary" href="#" onclick="window.history.back();">Cancel</a>
				</div>				
			</form:form>
		</div>
	</div>
</jsp:body>
</t:base>
