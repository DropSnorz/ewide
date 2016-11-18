<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>EWIDE</title>
	<link rel="shortcut icon" href="resources/images/favicon.ico">
	<!-- CSS dependencies -->
	<link href="<c:url value="/resources/bootstrap/css/bootstrap.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/css/font-awesome.min.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/css/font-awesome.min.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/css/md-icons.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/css/main.css" />" rel="stylesheet">
</head>
<body>

	<!-- Fixed navbar -->
    <nav class="navbar navbar-inverse">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="index">EWIDE</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
          <ul class="nav navbar-nav">
            <li><a href="#">Dashboard</a></li>
            <li><a href="project">Editor</a></li>
            <li><a href="newproject">Create Project</a></li>
            <li class="dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Dropdown <span class="caret"></span></a>
              <ul class="dropdown-menu">
                <li><a href="#">Action</a></li>
                <li><a href="#">Another action</a></li>
                <li><a href="#">Something else here</a></li>
                <li role="separator" class="divider"></li>
                <li class="dropdown-header">Nav header</li>
                <li><a href="#">Separated link</a></li>
                <li><a href="#">One more separated link</a></li>
              </ul>
            </li>
          </ul>
          <ul class="nav navbar-nav navbar-right">
             <li>
             	<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Moi <span class="caret"></span></a>
				<ul class="dropdown-menu">
					<li><a href="#">Mot de passe</a></li>
					<li role="separator" class="divider"></li>
					<li><a href="logout">Sign Out</a></li>
				</ul>
             </li>
           </ul>
        </div><!--/.nav-collapse -->
      </div>
    </nav>

	

    <div class="container theme-showcase" role="main">

      <!-- Main jumbotron for a primary marketing message or call to action -->
      <div class="jumbotron">
        <h1>File versions</h1>
        <p>
	        <table>
	        	<tr>
			       <th style="max-width:100px;overflow:hidden;">Version</th>
			       <th style="max-width:100px; padding-left:15px">Date</th>
			       <th style="max-width:100px; padding-left:15px">User</th>
			       <th>Message</th>
			       <th>Contenu</th>
			   </tr>
	        	<c:forEach var="version" items="${versions_list}" >
					<tr>
						<td style="max-width:100px;overflow:hidden;">
							${version.version}
						</td>
						<td  style="min-width:200px; padding-left:15px;">
							${version.date}
						</td>
						<td>${version.user.mail}</td>
						<td style="min-width:250px;">
							${version.comment}
						</td>
						<td>
							${version.content}
						</td>
					</tr>
			    </c:forEach>
		  </table>
	  </p>
      </div>
      <div>
	      <c:forEach var="project" items="${projectList}" >
	        	<a href="${project.name}"> ${project.name }</a>  <br />                 
	       </c:forEach>
       </div>

    </div> <!-- /container -->
	
	<!-- Javascripts -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script type="text/javascript" src="<c:url value="resources/js/jquery-1.11.2.js" />"></script>
	<script type="text/javascript" src="<c:url value="resources/js/jquery-ui.js" /> "></script>
	<script type="text/javascript" src="<c:url value="resources/bootstrap/js/bootstrap.js" /> "></script>
</body>
</html>