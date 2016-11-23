<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<t:base>

<jsp:attribute name="head">
	<title>File versions - EWIDE</title>    
</jsp:attribute>

<jsp:body>

    <div class="container">
		<div class="col-md-6 col-xs-12 col-md-offset-3">
			<c:if test="${versionRestored!=null}"><div>Successfully restored ${fileName} to version ${versionRestored}</div></c:if>
			<h1 class="text-center"><strong>File versions</strong></h1>
		       		
					<table class="table users_table">
						<thead>
							<tr>
								<th>Number #</th>
								<th class="text-center">Date</th>
								<th class="text-center">User</th>
								<th></th>
							</tr>
						</thead>
						<tbody>
							
								<c:forEach var="version" items="${versions_list}" >
									<tr data-toggle="collapse" data-target="#version_${version.versionID}">
										<td class="">
											<b>${version.versionID}</b>
										</td>
										<td  class="text-center">
											${version.date}
										</td>
										<td class="text-center">
											${version.user.username}
										</td>
										<td><i class="material-icons">add_box</i></td>
									</tr>
					    		</c:forEach>
						    
						</tbody>
					</table>
					<c:forEach var="version" items="${versions_list}" >
						<div id="version_${version.versionID}" class="collapse" style="margin-bottom:20px;">
							<div style="position:relative;">
								<h3 class="text-center">Version #${version.versionID} details: </h3>
								<button style="right:0; top:0; position:absolute;" class="btn btn-primary" data-toggle="collapse" data-target="#version_${version.versionID}"><i class="material-icons">clear</i></button>
								
							</div>
							<div style="position:relative;" >
								<b>Comment:</b><br/>
								<pre>${version.comment}</pre>
							</div>
							<div style="position:relative;" >
								<b>Content:</b><br/>
								<pre>${version.content}</pre>
							</div>
							<div class="text-center">
			
							<a href=<spring:url value="/project/${projectId}/versions/{fileName}/restore/${version.version}">
									<spring:param name="fileName" value="${fileName}" />
									</spring:url> >
									<button class="btn btn-success" style="margin-top:20px">Restore this version</button>
							</a>
							</div>
						</div>
					</c:forEach>
		  </div>
      </div>
	
</jsp:body>
</t:base>