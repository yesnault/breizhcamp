<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<h2>Speakers</h2>

<ul>
	<c:forEach var="speaker" items="${speakers}">
    <li>${speaker.firstName} ${speaker.lastName}</li>
	</c:forEach>
</ul>

