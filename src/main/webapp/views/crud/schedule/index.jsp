<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<script type='text/javascript' charset='utf-8'>
         setActive('schedules');
</script>
<div class="row">
<table class="table table-striped table-bordered table-condensed span4">
<tr>
<th class="span1"><spring:message code="date.title"   /></th>
<th class="span1"><spring:message code="heure.title"   /></th>
<th class="span1"><spring:message code="salle.title"   /></th>
<th class="span1"><spring:message code="actions.title"   /></th>
</tr>
<c:if test="${empty schedules}">
<tr><td colspan="4">Aucun cr&eacute;neau pour l'instant</td></tr>
</c:if>
<c:forEach var="schedule" items="${schedules}">
    <tr>
      <td><fmt:formatDate value="${schedule.start}" type="both" pattern="dd/MM/yyyy" /></td>
      <td><fmt:formatDate value="${schedule.start}" type="both" pattern="HH:mm" /></td>
      <td><c:if test="${empty schedule.room}"><spring:message code="all.rooms"   /></c:if>${schedule.room.name}</td>
      <td>
        <a href="/crud/schedule/edit/${schedule.id}.htm"><spring:message code="edit.title"   /></a>
        <a href="/crud/schedule/delete/${schedule.id}.htm"><spring:message code="delete.title"   /></a>
      </td>
    </tr>
	</c:forEach>
</table>
<br/>
</div>
<div class="row">
<div class="span3">
<a href="/crud/schedule/add.htm" accesskey="a"><spring:message code="schedule.action.add" /></a>
</div>
</div>


