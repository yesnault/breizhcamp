<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="custo" uri="/WEB-INF/custom-functions.tld" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<script type='text/javascript' charset='utf-8'>
         setActive('talks');
</script>
<div class="row">
<table class="table  table-striped table-bordered table-condensed span12" id="talksTable">
<tr>
<th class="span1">Titre</th>
<th class="span2"><spring:message code="resume.title"   /></th>
<th class="span2"><spring:message code="date.title"   /></th>
<th class="span2"><spring:message code="heure.title"   /></th>
<th class="span2"><spring:message code="duree.title"   /></th>
<th class="span1"><spring:message code="theme.title"   /></th>
<th class="span1"><spring:message code="salle.title"   /></th>
<th class="span2"><spring:message code="speakers.title"   /></th>
<th class="span1"><spring:message code="actions.title"   /></th>
</tr>
<c:if test="${empty talks}">
<tr><td colspan="9"><spring:message code="talk.edit.no.talk"   /></td></tr>
</c:if>
<c:forEach var="talk" items="${talks}">
    <tr>
      <td>${talk.title}</td>
      <td>${talk.abstract}</td>
      <c:choose>
        <c:when test="${empty talk.schedule}">
            <td colspan="2"><spring:message code="non.planifie" /></td>
        </c:when>
        <c:otherwise>
            <td><fmt:formatDate value="${talk.start}" type="both" pattern="dd/MM/yyyy" /></td>
            <td>${custo:getdebut(talk.start)}</td>
        </c:otherwise>
      </c:choose>
      <td>${custo:getduree(talk.duree)}</td>
      <td>${talk.theme.htmlValue}</td>
      <c:choose>
        <c:when test="${empty talk.schedule}">
            <td ><spring:message code="non.planifie" /></td>
        </c:when>
        <c:when test="${empty talk.schedule.room}">
            <td><spring:message code="all.rooms"   /></td>
        </c:when>
        <c:otherwise>
            <td>${talk.schedule.room.name}</td>
        </c:otherwise>
      </c:choose>
      <td>
        <c:forEach var="speaker" items="${talk.speakers}">
            ${speaker.firstName} ${speaker.lastName}<br/>
        </c:forEach>
      </td>
      <td>
        <a href="/crud/talk/edit/${talk.id}.htm"><spring:message code="edit.title"   /></a><br/>
        <a href="/crud/talk/delete/${talk.id}.htm"><spring:message code="delete.title"   /></a>
      </td>
    </tr>
	</c:forEach>
</table>
<br/>
</div>
<div class="row">
<div class="span3">
<a href="/crud/talk/add.htm" accesskey="a"><spring:message code="talk.action.add"   /></a>
</div>
</div>


