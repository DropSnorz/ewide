
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ attribute name="head" fragment="true"%>
<%@ attribute name="footer" fragment="true"%>
<%@ attribute name="javascript" fragment="true"%>
<%@ tag description="Base template tag" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!doctype html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="shortcut icon"
		href="<c:url value="resources/images/favicon.ico" />">
	<!-- CSS dependencies -->
	<link href="<c:url value="/resources/bootstrap/css/bootstrap.css" />" rel="stylesheet">
	<link href="<c:url value="/resources/css/font-awesome.min.css" />" rel="stylesheet">
	<link href="<c:url value="/resources/css/font-awesome.min.css" />" rel="stylesheet">
	<link href="<c:url value="/resources/css/md-icons.css" />" rel="stylesheet">
	<link href="<c:url value="/resources/css/main.css" />" rel="stylesheet">
	<link href="<c:url value="/resources/css/component.css" />" rel="stylesheet">
	<link href="<c:url value="/resources/css/jquery.scrollbar.css" />" rel="stylesheet">
	<link rel="stylesheet" href="http://code.jquery.com/ui/1.9.0/themes/smoothness/jquery-ui.css" />
	
	<jsp:invoke fragment="head" />
</head>
<body id="">
	<div id="st-container">
		<nav class="st-menu st-effect-4" id="menu-2"> 
			<a href="<c:url value="/dashboard"/>" class="menu-projects">
				<i class="material-icons">view_list</i> <span> List projets</span>
			</a>
			<h2 class="icon icon-stack">${project.name}</h2>
			<ul>
				<li><a class="icon icon-data" href="#">Wiki</a></li>
				<li><a class="icon icon-location" href="<c:url value="/project/${project.projectID}"/>">Workspace</a></li>
				<li><a class="icon icon-study" href="#">Versioning</a></li>
				<li><a class="icon icon-photo" href="<c:url value="/project/${project.projectID}/users_manager"/>">Members</a></li>
				<li><a class="icon icon-wallet" href="<c:url value="/project/${project.projectID}/task"/>">Tasks List</a></li>
			</ul>
		</nav>
		<!-- Fixed navbar -->

		<div class="ewide st-pusher">
			<div id="st-trigger-effects" class="column">
				<button class="ewide-menu" data-effect="st-effect-4">
					<i class="material-icons md-36">menu</i>
				</button>
			</div>
			<header> 
				<nav class="navbar navbar-inverse main_nav">
					<div class="container">
						<div class="navbar-header">
							<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
								<span class="sr-only">Toggle navigation</span> 
								<span class="icon-bar"></span> 
								<span class="icon-bar"></span> 
								<span class="icon-bar"></span>
							</button>
							<a class="navbar-brand" href="<c:url value="/index"/>">EWIDE</a>
						</div>
						<div id="navbar" class="navbar-collapse collapse">
							<ul class="nav navbar-nav">
								<li><a href="<c:url value="/dashboard"/>">Dashboard</a></li>
								<c:if test="${fn:contains(pageContext.request.requestURL,\"dashboard\")}">
									<li><a href="<c:url value="/newproject"/>">Create
											Project</a></li>
								</c:if>
							</ul>
							<ul class="nav navbar-nav navbar-right">
								<li><a href="#" class="dropdown-toggle"
									data-toggle="dropdown" role="button" aria-haspopup="true"
									aria-expanded="false">Moi <span class="caret"></span></a>
									<ul class="dropdown-menu">
										<li><a href="#">Mot de passe</a></li>
										<li role="separator" class="divider"></li>
										<li><a href="<c:url value="/logout"/>">Sign Out</a></li>
									</ul>
								</li>
							</ul>
						</div>
						<!--/.nav-collapse -->
					</div>
				</nav> 
			</header>
			<div class="container-fluid">
				<jsp:doBody />
				<div>
					<jsp:invoke fragment="footer" />
				</div>
			</div>
		</div>
	</div>

	<!-- Javascripts -->
	<!-- Placed at the end of the document so the pages load faster -->	
	<script type="text/javascript"
		src="<c:url value="/resources/js/jquery-1.11.2.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/js/jquery-ui.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/bootstrap/js/bootstrap.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/js/ace.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/js/jquery.scrollbar.js" />"></script>
	<script src="<c:url value="/resources/js/classie.js" />"></script>
	<script src="<c:url value="/resources/js/sidebarEffects.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/js/custom.js" />"></script>
		
			<jsp:invoke fragment="javascript" />
		
</body>
</html>