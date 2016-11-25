<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:base>

<jsp:attribute name="head">
<title>EWIDE - Wiki</title>
</jsp:attribute>

    <jsp:body>
        <!-- Page Content -->
        <div class="container">
        <div class="col-md-8 col-xs-12 col-md-offset-2">

        <jsp:include page="../include/alert.jsp" />
        <h1 class="text-center">
            <strong>Wiki</strong>
        </h1>

        <div class="row">
            <div class="pull-right">
                <a class="btn btn-primary" href="<c:url value="/project/${projectId}/wiki/edit"/>">	<i class="material-icons md-18 valign">add</i>Edit Wiki</a>
            </div>
        </div>

        <hr>

        <div class="col-md-12">
            <p>
                ${wiki}
            </p>
        </div>

        </div>
        </div>
    </jsp:body>
</t:base>
