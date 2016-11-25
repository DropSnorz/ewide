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
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
	<link rel="shortcut icon" href="<c:url value="/resources/images/favicon.ico" />">
	<!-- CSS dependencies -->
	<link href="<c:url value="/resources/css/chat.css" />" rel="stylesheet">
	<link href="<c:url value="/resources/bootstrap/css/bootstrap.css" />" rel="stylesheet">
	<link href="<c:url value="/resources/css/md-icons.css" />" rel="stylesheet">
	<link href="<c:url value="/resources/css/main.css" />" rel="stylesheet">
	<link href="<c:url value="/resources/css/component.css" />" rel="stylesheet">
	<link href="<c:url value="/resources/css/jquery.scrollbar.css" />" rel="stylesheet">
	<link href="<c:url value="/resources/css/jquery-ui.css" />" rel="stylesheet">
	<link href="<c:url value="/resources/css/font-awesome.min.css" />" rel="stylesheet">
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
				<li><a class="icon icon-location" href="<c:url value="/project/${project.projectID}/ide"/>">Workspace</a></li>
				<li><a class="icon icon-data" href="<c:url value="/project/${projectId}/wiki"/>">Wiki</a></li>
				<li><a class="icon icon-study" href="<c:url value="/project/${projectId}/versions/display"/>">Versioning</a></li>
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
									aria-expanded="false">Me <span class="caret"></span></a>
									<ul class="dropdown-menu">
										<li><a href="<c:url value="/changepwd"/>">Change password</a></li>
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
	
	<div id="loader">
		<span>Loading...</span>
		<div class="spinner">
			<div class="double-bounce1"></div>
			<div class="double-bounce2"></div>
		</div>
	</div>
	
	<!-- -- -- -- -- -- -- -- --  chat -- -- -- -- -- -- -->
<div class="chat_bar">
	<div class="open_chat">
		<a href="#">Open Chat</a>
	</div>
	<div class="close_chat">
		<a href="#">Close Chat</a>
	</div>
	<div class="chat_content">
		<div class="chat_messages scrollbar-macosx" id="chat_scrollbar">
			<div class="pix_items" id="chat_inner">
				<div id="chatdiv">
					<div id="chat"></div>
				</div>
			</div>
		</div>
		<textarea rows="1" id="chattext"></textarea>
	</div>
</div>
<script>
	var messageUrl = "<c:url value="/project/${project.projectID}/messages" />"
</script>	


	<!-- Javascripts -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script type="text/javascript" src="<c:url value="/resources/js/jquery-1.11.2.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/js/jquery-ui.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/bootstrap/js/bootstrap.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/js/ace.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/js/jquery.scrollbar.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/js/classie.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/js/sidebarEffects.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/js/custom.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/js/custom_users.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/js/chat.js" />"></script>
	
	<jsp:invoke fragment="javascript" />
	</body>
</html>