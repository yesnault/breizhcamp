<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<b>Une erreur est survenue</b>     <br/>
<spring:message code="error.title"   />${exception.message}

