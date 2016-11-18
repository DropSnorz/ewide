<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:default>

<jsp:attribute name="head">
	<title>EWIDE - Change password</title>    
	<meta name="_csrf" content="${_csrf.token}"/>
    <!-- default header name is X-CSRF-TOKEN -->
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
</jsp:attribute>


	<jsp:body>	
		<div class="container">
		<div class="col-md-6 col-xs-12 col-md-offset-3">
			<h2 class="text-center"><strong>Change password </strong></h2>
			<hr>
			<div id="pwdErrors">
			</div>
			<div id="pwdValid">
			</div>
			<div class="form-group">
				<label for="lastpwd">Actual password :</label>
				<input class="form-control" type="password" id="lastpwd" name="lastpwd" placeholder="last password" >
				<br>
				<label for="newpwd">New password :</label>
				<input class="form-control" type="password" id="newpwd" name="newpwd" placeholder="new password" >
				<label for="confirmpwd">Confirm password :</label>
				<input class="form-control" type="password" id="confirmpwd" name="confirmpwd" placeholder="new password" >
			</div>
			<button class="btn btn-lg btn-success btn-block pix-btn-primary" id="confirm" >Confirm</button>
		</div>
		</div>
 
	</jsp:body>
</t:default>