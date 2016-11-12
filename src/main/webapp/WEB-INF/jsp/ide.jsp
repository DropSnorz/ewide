<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>EWIDE - Editor</title>
</head>
<body id="ide_page">
	
<div id="st-container">
	<nav class="st-menu st-effect-4" id="menu-2">
		<a href="<c:url value="/dashboard"/>" class="menu-projects"><i class="material-icons">view_list</i> <span>
			List projets</span>
		</a>
		<h2 class="icon icon-stack">Menu Projet</h2>
		<ul>
			<li><a class="icon icon-data" href="#">Wiki</a></li>
			<li><a class="icon icon-location" href="#">Workspace</a></li>
			<li><a class="icon icon-study" href="#">Versioning</a></li>
			<li><a class="icon icon-photo" href="<c:url value="/project/${project.projectID}/users_manager"/>">Utilisateurs</a></li>
			<li><a class="icon icon-wallet" href="<c:url value="/project/${project.projectID}/task"/>">Tâches en cours</a></li>
			<li><a class="icon icon-wallet" href="#">Tâches réalisées</a></li>
		</ul>
	</nav>
	<!-- Fixed navbar -->
	
	<div class="ewide st-pusher">
		<div id="st-trigger-effects" class="column">
								
			<button class="ewide-menu" data-effect="st-effect-4"><i class="material-icons md-36">menu</i></button>
								
		</div>
		<header>
		    <jsp:include page="header.jsp"/>
		</header>
	
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
	<iframe id="_chat" name="_chat" src="<%=request.getContextPath()%>/jsp/chat.jsp"  scrolling="auto" class="ifcontent"></iframe>
	        <form action='<c:url value="/project/${project.projectID}/messages"/>' method="get" target="_chat" class="chatForm">
	        	<input type="submit" value="ouvrir le chat"/>
	        </form>
	
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
	<script src="<c:url value="/resources/js/chat.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/js/custom.js" />"></script>  
</body>
</html>