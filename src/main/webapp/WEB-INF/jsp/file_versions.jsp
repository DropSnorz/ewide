<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>


<t:base>

<jsp:attribute name="head">
	<title>EWIDE - Create task</title>    
</jsp:attribute>

<jsp:body>

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
	
</jsp:body>
</t:base>