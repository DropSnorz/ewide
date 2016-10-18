<%@ page language="java" contentType="text/html; charset=windows-1256"
    pageEncoding="windows-1256"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
	<title>EWIDE</title>
	<link rel="shortcut icon" href="resources/images/favicon.ico">
	<link rel="shortcut icon" href="resources/images/favicon.ico"> 
	<!-- CSS dependencies -->
	<link rel="stylesheet" type="text/css" href="resources/bootstrap/css/bootstrap.css" />
	<link rel="stylesheet" type="text/css" href="resources/css/font-awesome.min.css" />
	<link rel="stylesheet" type="text/css" href="resources/css/md-icons.css" />
	<link rel="stylesheet" href="resources/css/jquery.scrollbar.css">
	<link rel="stylesheet" type="text/css" href="resources/css/main.css"/>
</head>
<body>
	<!-- Fixed navbar -->
	<nav class="navbar navbar-inverse ewide_no_margin">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
					<span class="sr-only">Toggle navigation</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">EWIDE</a>
			</div>
			<div id="navbar" class="navbar-collapse collapse">
				<ul class="nav navbar-nav">
            <li><a href="dashboard">Dashboard</a></li>
            <li><a href="ide">Editor</a></li>
            <li><a href="#contact">Contact</a></li>
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
	            <li><a href="login">Login</a></li>
	          </ul>
			</div><!--/.nav-collapse -->
		</div>
	</nav>
	<section class="pix-padding-v-85 pix-elegant-1">
		<div class="container">
			<div class="row">
				<div class="col-md-12 col-xs-12 col-sm-12 column ui-droppable">
					<div class="pix-content text-center">
						<div class="pix-margin-bottom-30">
							<img src="resources/images/elegant-logo.png" alt="">
						</div>
						<h1 class="pix-white text-center pix-no-margin-top secondary-font pix-small-width-text">
							<span class="pix_edit_test"><strong>PROJET TRANSVERSAL!</strong></span>
						</h1>
						<div><h5 class="pix-dark-green-2 text-center pix-margin-bottom-30 pix-md-width-text">
							L’EDI web offrira non seulement les fonctionnalités de base d’une
application classique, mais devra également implémenter des outils de
développement collaboratif, et la gestion de versions des projets créés par
son biais.
						</h5></div>
						<a href="login" class="btn orange-bg btn-xl pix-white btn-flat">
							<span class="pix_edit_test">
								<strong>Create a Free Account</strong>
							</span>
						</a>
					</div>
				</div>
			</div>
		</div>
	</section>
	
	
	
	<!-- Javascripts -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script type="text/javascript" src="resources/js/jquery-1.11.2.js"></script>
	<script type="text/javascript" src="resources/js/jquery-ui.js"></script>
	<script type="text/javascript" src="resources/bootstrap/js/bootstrap.js"></script>
</body>
</html>