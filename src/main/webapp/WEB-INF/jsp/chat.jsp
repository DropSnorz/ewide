<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div id="chatdiv">
alllllllllllllo
	<div class="messages">
		<c:set var="date" scope="request" value="${ null}"/>
		<c:forEach  var="message" items="${messagesList}" >
			<c:if test="${date!= message.date}">
				<span> ${ message.date}</span>
			</c:if>
			<c:if test="${message.user==user }">
				 <div class="bubble me">
				 </div>
			</c:if>
			<c:if test="${message.user!=user }">
				 <div class="bubble you">
				 </div>
			</c:if>
		</c:forEach>
	</div>	
</div>
