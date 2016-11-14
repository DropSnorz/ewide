<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<head>
    <meta HTTP-EQUIV="Refresh" CONTENT="5">

</head>

	<div class="messages">
		<c:set var="date" scope="request" value="${ null}"/>
		<c:forEach  var="message" items="${messagesList}" >
			<c:if test="${date!= message.date}">
				<c:set var="date" scope="request" value="${ message.date}"/>
				<span> ${ message.date}</span>
			</c:if>
			<c:if test="${message.user==user }">
				 <div class="bubble me right">
				 	${message.text}
				 </div>
			</c:if>
			<c:if test="${message.user!=user }">
				 <div class="bubble you">
				 		${message.username}: 
				 		${message.text}
				 </div>
			</c:if>
		</c:forEach>
	</div>	

