<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<script type='text/javascript' charset='utf-8'>
         setActive('rooms');
</script>
<div class="row">
<table class="table table-striped table-bordered table-condensed span3">
<tr>
<th class="span2"><spring:message code="room.add.salle.label"   /></th>
<th class="span1"><spring:message code="actions.title"   /></th>
</tr>
<c:if test="${empty rooms}">
<tr><td colspan="2"><spring:message code="room.index.no.salle"/></td></tr>
</c:if>
<c:forEach var="room" items="${rooms}">
    <tr>
      <td>${room.name}</td>
      <td>
        <a href="/crud/room/edit/${room.id}.htm"><spring:message code="edit.title"   /></a>
        <a href="/crud/room/delete/${room.id}.htm"><spring:message code="delete.title"   /></a>
      </td>
    </tr>
	</c:forEach>
</table>
<br/>
</div>
<div class="row">
<div class="span3">
<a href="/crud/room/add.htm" accesskey="a"><spring:message code="room.action.add"/></a>
</div>
</div>


