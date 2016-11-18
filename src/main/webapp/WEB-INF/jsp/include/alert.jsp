<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="alert-container">
<c:if test="${SUCCESS_MESSAGE != null}">
  <div class="alert alert-success alert-status fade in">
	<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>${SUCCESS_MESSAGE}
  </div>
</c:if> 
<c:if test="${INFO_MESSAGE != null}">
  <div class="alert alert-info alert-status fade in"><a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>${INFO_MESSAGE}</div>
</c:if> 
<c:if test="${WARNING_MESSAGE != null}">
  <div class="alert alert-warning alert-status fade in"><a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>${WARNING_MESSAGE}</div>
</c:if> 
<c:if test="${ERROR_MESSAGE != null}">
  <div class="alert alert-danger alert-status fade in"><a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>${ERROR_MESSAGE}</div>
</c:if> 

</div>
