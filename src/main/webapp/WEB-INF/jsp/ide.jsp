<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<!doctype html>
<html>
<head>
	<title>EWIDE - Editor</title>
	<link href="<c:url value="/resources/css/chat.css" />" rel="stylesheet">
	<link href="<c:url value="/resources/css/tree/style.css" />" rel="stylesheet">
	<link href="<c:url value="/resources/css/jquery.scrollbar.css" />" rel="stylesheet">
	<link href="<c:url value="/resources/css/compiler.css" />" rel="stylesheet">
	<meta name="_csrf" content="${_csrf.token}"/>
    <!-- default header name is X-CSRF-TOKEN -->
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <meta name="_project_id" content="${project.projectID}"/>
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
				<i class="material-icons">save</i> <span>Save</span>
			</a>
			<a href="#" class="tools-btn" id="configureCompiler">
				<i class="material-icons" id="btnConfigure" >build</i> <span>Configure</span>
			</a>
			<a href="#" class="tools-btn" id="compilation">
				<i class="material-icons">play_arrow</i> <span>Compiler</span>
			</a>
			<a href="#" class="tools-btn" id="resultCompilation">
				<i class="material-icons">get_app</i> <span>Download result</span>
			</a>
		</div>
	</div>


	<div class="container-fluid ide-body">
		<div class="row">
			<div class="col-md-2 ewide_no_margin ide-files-list">
				<div id="tree"></div>
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
						<div id="code"></div>
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
				<div id="the_console"></div>
			</div>
		</div> <!-- /container -->

	</div>
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


	<!-- -- -- --  -- Compiler Configuration pop-up -- -- -- -- -- -->
	<div id="paramCompil" class="modal-content modal">
		<div>
			<header class="modal-header">
				<h4>Configure the compiler :</h4>
			</header>
			<div class="modal-body">
				<div class="borderCompil">
					<input id="make" type="radio" name="compilo" value="make"> Makefile</br>
				</div>
				<div class="borderCompil">
					<input id="maven" type="radio" name="compilo" value="maven"> Maven<br/>
				</div>
				<div class="borderCompil">
					<input id="mainfileCompil" type="radio" name="compilo" value="cmd"> Command : 
					<select id="language">
						<option  value="javac"> javac </option>
						<option  value="gcc"> gcc</option> 
						<option  value="g++"> g++ </option>
						<option  value="python"> python</option> 
					</select>
					<input type="text" id="commandCompil" placeholder="command to execute">
					
				</div>
			</div>
			<footer class="modal-footer">
				<a href="#fermer" id="closeConfigure" class="btn modal-btn">Cancel</a>
				<a href="#validation" id="valideCompiler" class="btn modal-btn">Validate</a>
			</footer>
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
	<script type="text/javascript" src="<c:url value="/resources/js/classie.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/js/sidebarEffects.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/js/chat.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/js/custom.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/js/tree/jstree.min.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/js/tree/tree.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/js/compiler.js" />"></script>
</body>
</html>