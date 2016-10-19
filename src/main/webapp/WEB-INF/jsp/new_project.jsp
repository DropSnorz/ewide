<%@ page language="java" contentType="text/html; charset=windows-1256"
    pageEncoding="windows-1256"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
	<title>EWIDE</title>
	<link rel="shortcut icon" href="resources/images/favicon.ico">
	<!-- CSS dependencies -->
	<link rel="stylesheet" type="text/css" href="resources/bootstrap/css/bootstrap.css" />
	<link rel="stylesheet" type="text/css" href="resources/css/font-awesome.min.css" />
	<link rel="stylesheet" href="resources/css/jquery.scrollbar.css">
	<link rel="stylesheet" type="text/css" href="resources/css/main.css"/>
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
            <li><a href="dashboard">Dashboard</a></li>
            <li><a href="project">Editor</a></li>
            <li class="active"><a href="newproject">Create Project</a></li>
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
		<div class="col-md-6 col-xs-12 col-md-offset-3">
			<h1 class="text-center"><strong>New Project</strong></h1>
			<hr>
			<form>
				<div class="form-group">
					<label for="projectName">Project Name</label>
					<input type="text" class="form-control" id="projectName" placeholder="Project Name">
				</div>
				<div class="form-group">
					<label for="projectDesc">Description</label>
					<textarea class="form-control" rows="3" id="projectDesc" placeholder="Project Description"></textarea>
				</div>
				<div class="form-group">
					<label for="projectUsers">Users</label>
					<input type="text" class="form-control" id="projectUsers" placeholder="Users">
				</div>
				<button class="btn btn-lg btn-success btn-block pix-btn-primary" type="submit">Confirm</button>
			</form>
		</div>


	</div> <!-- /container -->
	
	<!-- Javascripts -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script type="text/javascript" src="resources/js/jquery-1.11.2.js"></script>
	<script type="text/javascript" src="resources/js/jquery-ui.js"></script>
	<script type="text/javascript" src="resources/bootstrap/js/bootstrap.js"></script>
</body>
</html>