<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>EWIDE - Dashboard</title>
</head>
<body>

	<!-- Header Template -->
	<jsp:include page="header.jsp"/>
	
	<!-- Page Content -->	
     <div class="container">
		<div class="col-md-8 col-xs-12 col-md-offset-2">
			<h1 class="text-center"><strong>Dashboard</strong></h1>
			<hr>
			<table class="table users_table">
				<thead>
					<tr>
						<th class="">Project</th>
						<th class="text-center role_col">Settings</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="project" items="${projectList}" >
						<tr>
							<td><a href="project/${project.name}"> ${project.name }</a></td>
							<td class="text-center">
								<a href="project/${project.projectID}/users_manager">Edit Users</a>
							</td>
						</tr>
				    </c:forEach>
				</tbody>
			</table>
		</div>
	</div>

	<!-- Javascripts -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script type="text/javascript" src="resources/js/jquery-1.11.2.js"></script>
	<script type="text/javascript" src="resources/js/jquery-ui.js"></script>
	<script type="text/javascript" src="resources/bootstrap/js/bootstrap.js"></script>
</body>
</html>