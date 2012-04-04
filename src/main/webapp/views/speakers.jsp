<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type='text/javascript' charset='utf-8'>
         setActive('speakers');
</script>
<h3>Speakers</h3>

<c:forEach var="speaker" items="${speakers}">
    <img src="${speaker.picture}" width="60px" height="60px" />
    <a href="https://twitter.com/#!/${speaker.twitter}" target="_blank">${speaker.firstName} ${speaker.lastName}</a><br/>
</c:forEach>
