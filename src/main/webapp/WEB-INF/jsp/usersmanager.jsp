<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>EWIDE - Users manager</title>
	<meta name="_csrf" content="${_csrf.token}"/>
    <!-- default header name is X-CSRF-TOKEN -->
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
</head>
<body>

	<!-- Header Template -->
	<jsp:include page="header.jsp"/>
	
	
	<!-- Page Content -->	
	<div class="container">
		<div class="col-md-6 col-xs-12 col-md-offset-3">
			<h1 class="text-center"><strong>Utilisateurs</strong></h1>
			<hr>
			<!-- adding a User  -->
			<c:if test="${userrole=='MANAGER'}">
				<c:if test="${not empty error}">
					<div class="alert alert-danger">${error}</div>
					<hr>
				</c:if>
				<c:if test="${not empty success}">
					<div class="alert alert-success">${success}</div>
					<hr>
				</c:if>
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				<div class="form-inline">
					<div class="form-group">
						<input type="text" style="width:400px" class="form-control"  id="adduser" placeholder="Search a user...">
					</div>
						<button class="btn btn-secondary"  onclick="adduser()" >Add</button>
				</div>
				<hr>
			</c:if>
			<!-- roles of users  -->
			<form name="formlogin" class="users_roles" id="users_roles" action="users_manager" method="POST">
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			<table class="table users_table">
				<thead>
					<tr>
						<th class="">Pseudo</th>
						<th class="text-center role_col">Role</th>
						<th class="text-center"></th>
					</tr>
				</thead>
				<tbody  id="userRoleTab">
					<c:forEach var="user" items="${listUsers}" >
						<tr>
							<td>${user.username}</td>
							<td class="text-center">
							<c:if test="${userrole=='MANAGER'}">
								<select class="userrole form-control" name="${user.username}">
									<c:forEach var="role" items="${roles}">
								        <option value="${role}" ${role == user.roles.get(0).role ? 'selected="selected"' : ''}>${role}</option>
								    </c:forEach>
								</select>
							</c:if>
							<c:if test="${userrole!='MANAGER'}">
								${user.roles.get(0).role}
							</c:if>
							</td>
							<td class="text-center">
								<c:if test="${userrole=='MANAGER'}">
									<a href="#" class="btn btn-danger btn-delete-user delete-user"><i class="material-icons">delete</i></a>
								</c:if>
							</td>
						</tr>
				     </c:forEach>
				</tbody>
			</table>
			<c:if test="${userrole=='MANAGER'}">
				<button class="btn btn-lg btn-success btn-block pix-btn-primary " id="users_submit" type="submit">Save</button>
			</c:if>
			</form>
			<br>
		</div>
	</div>
	
	<!-- Javascripts -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script type="text/javascript" src="<c:url value="/resources/js/jquery-1.11.2.js" />"/></script>
	<script type="text/javascript" src="<c:url value="/resources/js/jquery-ui.js" />"/></script>    
	<script type="text/javascript" src="<c:url value="/resources/bootstrap/js/bootstrap.js" />"/></script>    
	<script type="text/javascript" src="<c:url value="/resources/js/custom_users.js" />"/></script>    
</body>
</html>