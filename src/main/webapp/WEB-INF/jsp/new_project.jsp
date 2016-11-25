<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!doctype html>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
	<title>EWIDE - New Project</title>
</head>
<body>

	<!-- Header Template -->
	<jsp:include page="header.jsp"/>
	
	<!-- Page Content -->	
    <div class="container theme-showcase" role="main">
		<div class="col-md-6 col-xs-12 col-md-offset-3">
			<h1 class="text-center"><strong>New Project</strong></h1>
			<hr>
			<form:form post="/newproject" modelAttribute="Project">
				<div class="form-group">
					<label for="projectName">Project Name</label>
					<input type="text" class="form-control" name ="projectName" id="projectName" placeholder="Project Name" maxlength="80">
				</div>
				<div class="form-group">
					<label for="projectDesc">Description</label>
					<textarea class="form-control" rows="3" name ="projectDesc" id="projectDesc" placeholder="Project Description"></textarea>
				</div>
				<button class="btn btn-lg btn-success btn-block pix-btn-primary" type="submit">Confirm</button>
			</form:form>
		</div>
	</div> <!-- /container -->
	
	<!-- Javascripts -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script type="text/javascript" src="resources/js/jquery-1.11.2.js"></script>
	<script type="text/javascript" src="resources/js/jquery-ui.js"></script>
	<script type="text/javascript" src="resources/bootstrap/js/bootstrap.js"></script>
</body>
</html>