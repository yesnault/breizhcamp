<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<h2>Programme du BreizhCamp !</h2>

<ul>
	<c:forEach var="talk" items="${talks}">
    <li>${talk.title}</li>
	</c:forEach>
</ul>

