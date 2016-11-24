
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:default>

<jsp:attribute name="head">
	<title>EWIDE - Dashboard</title>    
	<meta name="_csrf" content="${_csrf.token}"/>
    <!-- default header name is X-CSRF-TOKEN -->
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
</jsp:attribute>

	<jsp:body>	
	
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
							<td><a href="project/${project.projectID}/ide"> ${project.name }</a></td>
							<td class="text-center">
								<a href="project/${project.projectID}/users_manager">Edit Users</a>
							</td>
						</tr>
				    </c:forEach>
				</tbody>
			</table>
		</div>
	</div>

</jsp:body>
</t:default>
