<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="custo" uri="/WEB-INF/custom-functions.tld" %>
<script type='text/javascript' charset='utf-8'>
         setActive('contact');
</script>
<h3>Talk</h3>

<table class="table  table-striped table-bordered table-condensed span6">
<tr><td>Titre : </td><td>${talk.title}</td> </tr>
<tr><td>R&eacute;sum&eacute;</td>      <td>${talk.abstract}</td></tr>
<tr><td>Date</td>      <td><fmt:formatDate value="${talk.start}" type="both" pattern="dd/MM/yyyy" /></td> </tr>
<tr><td>Heure</td>      <td>${custo:getdebut(talk.start)}</td>   </tr>
<tr><td>Dur&eacute;e :</td>      <td>${custo:getduree(talk.duree)}</td> </tr>
<tr><td>Th&egrave;me :</td>      <td>${talk.theme.htmlValue}</td>            </tr>
<tr><td>Salle :</td>      <td><c:if test="${empty talk.room}">Toutes Salles</c:if>${talk.room.name}</td> </tr>
<tr><td>Speakers :</td>      <td>
        <c:forEach var="speaker" items="${talk.speakers}">
            <a href="https://twitter.com/#!/${speaker.twitter}">${speaker.firstName} ${speaker.lastName}</a><br/>
        </c:forEach>
      </td>    </tr>
</table>
