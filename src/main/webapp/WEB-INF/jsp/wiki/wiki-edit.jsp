<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:base>

<jsp:attribute name="head">
    <title>EWIDE - Edit Wiki</title>
</jsp:attribute>
<jsp:attribute name="javascript">
    <script type="text/javascript" src="<c:url value="/resources/js/ckeditor/ckeditor.js" />"></script>
    <script type="text/javascript">
    window.onload = function(e){
        CKEDITOR.replace( 'wikiText');

    };
    </script>
</jsp:attribute>

    <jsp:body>
        <!-- Page Content -->
          <div class="container">
            <div class="col-md-8 col-xs-12 col-md-offset-2">
                <h1 class="text-center"><strong>Edit Wiki</strong></h1>
                <hr>

                <spring:hasBindErrors name="wikiForm">
                    <div class="alert alert-danger" role="alert">
                        <i class="material-icons">error</i>
                        <span class="sr-only">Error: </span>
                        <c:forEach var="error" items="${errors.allErrors}">
                            <b><spring:message message="${error}" /></b>
                        </c:forEach>
                    </div>
                </spring:hasBindErrors>


                 <form:form name="wikiForm" method="post" action="edit">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />

                    <div class="col-md-12">
                        <div class="form-group">
                            <textarea id="wikiText" name="wikiText">&lt;p&gt;${wikiText}${wiki}&lt;/p&gt;</textarea>
                        </div>
                       </div>

                    <div class="col-md-9">
                        <button class="btn btn-lg btn-success btn-block pix-btn-primary" type="submit">Confirm</button>
                    </div>
                    <div class="col-md-3">
                        <a class="btn btn-lg btn-danger btn-block pix-btn-primary" href="../wiki">Cancel</a>
                    </div>
                 </form:form>
            </div>
        </div>
    </jsp:body>
</t:base>