<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<head>
    <meta HTTP-EQUIV="Refresh" CONTENT="5">

</head>

	<div class="messages">
		<c:set var="date" scope="request" value="${ null}"/>
		<c:forEach  var="message" items="${messagesList}" >
			<c:if test="${date!= message.date}">
				<c:set var="date" scope="request" value="${ message.date}"/>
				<div class="chat_date">${ message.date}</div>
			</c:if>
			<c:if test="${message.user==user }">
				 <div class="message_item me">
					<span class="m_username">Me</span>
					<div class="message_content">${message.text}</div>
				</div>
			</c:if>
			<c:if test="${message.user!=user }">
				<div class="message_item">
					<span class="m_username">${message.username}:</span>
					<div class="message_content">${message.text}</div>
				</div>
			</c:if>
		</c:forEach>
	</div>	

