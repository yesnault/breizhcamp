<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="custo" uri="/WEB-INF/custom-functions.tld" %>
<script type='text/javascript' charset='utf-8'>
         setActive('talks');
</script>
<div class="row">
<table class="table  table-striped table-bordered table-condensed span12" id="talksTable">
<tr>
<th class="span1">Titre</th>
<th class="span2">R&eacute;sum&eacute;</th>
<th class="span2">Date</th>
<th class="span2">Heure</th>
<th class="span2">Dur&eacute;e</th>
<th class="span1">Th&egrave;me</th>
<th class="span1">Salle</th>
<th class="span2">Speakers</th>
<th class="span1">Actions</th>
</tr>
<c:if test="${empty talks}">
<tr><td colspan="9">Aucun talk pour l'instant</td></tr>
</c:if>
<c:forEach var="talk" items="${talks}">
    <tr>
      <td>${talk.title}</td>
      <td>${talk.abstract}</td>
      <td><fmt:formatDate value="${talk.start}" type="both" pattern="dd/MM/yyyy" /></td>
      <td>${custo:getdebut(talk.start)}</td>
      <td>${custo:getduree(talk.duree)}</td>
      <td>${talk.theme.htmlValue}</td>
      <td><c:if test="${empty talk.room}">Toutes Salles</c:if>${talk.room.name}</td>
      <td>
        <c:forEach var="speaker" items="${talk.speakers}">
            ${speaker.firstName} ${speaker.lastName}<br/>
        </c:forEach>
      </td>
      <td>
        <a href="/crud/talk/edit/${talk.id}.htm">Editer</a><br/>
        <a href="/crud/talk/delete/${talk.id}.htm">Supprimer</a>
      </td>
    </tr>
	</c:forEach>
</table>
<br/>
</div>
<div class="row">
<div class="span3">
<a href="/crud/talk/add.htm" accesskey="a">Ajouter un talk (ALT+a)</a>
</div>
</div>


