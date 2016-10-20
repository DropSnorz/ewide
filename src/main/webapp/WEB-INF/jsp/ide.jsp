<%@ page language="java" contentType="text/html; charset=windows-1256"
    pageEncoding="windows-1256"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
	<title>EWIDE</title>
	<link rel="shortcut icon" href="resources/images/favicon.ico">
	<!-- CSS dependencies -->
	<link href="<c:url value="/resources/bootstrap/css/bootstrap.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/css/font-awesome.min.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/css/font-awesome.min.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/css/md-icons.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/css/main.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/css/component.css" />" rel="stylesheet">
</head>
<body>
	
<div id="st-container">
	<nav class="st-menu st-effect-4" id="menu-2">
		<a href="dashboard" class="menu-projects"><i class="material-icons">view_list</i> <span>
			List projets</span>
		</a>
		<h2 class="icon icon-stack">Menu Projet</h2>
		<ul>
			<li><a class="icon icon-data" href="#">Wiki</a></li>
			<li><a class="icon icon-location" href="#">Workspace</a></li>
			<li><a class="icon icon-study" href="#">Versioning</a></li>
			<li><a class="icon icon-photo" href="#">Utilisateurs</a></li>
			<li><a class="icon icon-wallet" href="#">Tâches en cours</a></li>
			<li><a class="icon icon-wallet" href="#">Tâches réalisées</a></li>
		</ul>
	</nav>
	<!-- Fixed navbar -->
	
	<div class="ewide st-pusher">
		<div id="st-trigger-effects" class="column">
								
								<button class="ewide-menu" data-effect="st-effect-4"><i class="material-icons">menu</i></button>
								
							</div>
		<nav class="navbar navbar-inverse ewide_no_margin">
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
						<li class=""><a href="<c:url value="/dashboard" />">Dashboard</a></li>
						<li class="active"><a href="#">Editor</a></li>
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
	
	<div class="ide-tools">
		<div class="container">
			<a href="#" class="tools-btn">
				<i class="material-icons">save</i> <span>Sauvegarder</span>
			</a><a href="#" class="tools-btn">
				<i class="material-icons">playlist_play</i> <span>Compiler</span>
			</a>
		</div>
	</div>


	<div class="container-fluid ide-body">
		<div class="row">
			<div class="col-md-2 ewide_no_margin ide-files-list">
				Files List
			</div>
			<div class="col-md-10 ewide_no_margin">
				<ul class="nav nav-tabs pix_settings_tab" id="myTab">
					<li class="active">
						<a href="#" data-target="#pix_style" data-toggle="tab">File 1 </a>
					</li>
					<li><a href="#" data-target="#pix_animation" data-toggle="tab">File 2</a></li>
					<li><a href="#" class="ide-add-page" data-target="#pix_style" data-toggle="tab"><i class="material-icons">add</i></a></li>
				</ul>
				<div class="tab-content">	
					<div class="tab-pane active" id="pix_style">
						<div id="the_code">
x
					</div>
				</div>
				<div class="tab-pane" id="pix_animation">
					<div id="the_code_2">
/* HelloWorld.java
 */

public class HelloWorld
{
	public static void main(String[] args) {
		System.out.println("Hello World!");
	}
}
</div>
				</div>
			</div>
				<div id="the_console">Console Output goes here...</div>
			</div>
		</div> <!-- /container -->
	</div>
	</div>
	</div>
	
	<!-- Javascripts -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script type="text/javascript" src="<c:url value="/resources/js/jquery-1.11.2.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/js/jquery-ui.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/bootstrap/js/bootstrap.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/js/velocity.min.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/js/velocity.ui.min.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/js/ace.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/js/ext-language_tools.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/js/jquery.scrollbar.js" />"></script>
	<script src="<c:url value="/resources/js/classie.js" />"></script>
	<script src="<c:url value="/resources/js/sidebarEffects.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/js/custom.js" />"></script>  
</body>
</html>