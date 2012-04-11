<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<script type='text/javascript' charset='utf-8'>
         setActive('contact');
</script>
<h3><spring:message code="contact.title"   /></h3>

<spring:message code="contact.mail.label"   /> <a href="mailto:team@breizhcamp.org" >team@breizhcamp.org</a>    <br/>

<spring:message code="contact.twitter.label"   /> <a href="https://twitter.com/#!/breizhcamp" >@breizhcamp</a>
