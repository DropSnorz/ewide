<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
  Hello
    
         <div>
	      <c:forEach var="role" items="${listUsers}" >
	        	${role.project}    
	        	${role.role }          
	       </c:forEach>
       </div>