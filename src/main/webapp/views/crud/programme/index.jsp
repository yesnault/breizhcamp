<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<script type='text/javascript' charset='utf-8'>
         setActive('programme');
</script>

<c:forEach var="date" items="${data.datesOrdonnees}">
    <fieldset class="span${fn:length(data.rooms)*2+2}">
        <legend><spring:message code="programme.title"   /> <fmt:formatDate value="${date}" type="both" pattern="dd/MM/yyyy" /></legend>
        <table class="table  table-striped table-bordered table-condensed span${fn:length(data.rooms)*2+2}">
            <tr>
                <th class="span2"><spring:message code="heure.title"   /></th>
                <c:forEach var="room" items="${data.rooms}">
                    <th class="span2">${room.name}</th>
                </c:forEach>
            </tr>
            <c:forEach var="creneau" items="${data.creneaux[date]}">
                <tr>
                    <td>${creneau}</td>
                    <c:choose>
                        <c:when test="${not empty data.schedules[date][creneau][sansRoom]}">
                            <td id="${data.schedules[date][creneau][sansRoom].id}" colspan="${fn:length(data.rooms)}" style="text-align:center">
                                <c:if test="${not empty data.talksBySchedules[data.schedules[date][creneau][sansRoom]]}">
                                    ${data.talksBySchedules[data.schedules[date][creneau][sansRoom]].title}
                                </c:if>
                            </td>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="room" items="${data.rooms}">
                                <td id="${data.schedules[date][creneau][room.name].id}" style="text-align:center">
                                    <c:if test="${not empty data.talksBySchedules[data.schedules[date][creneau][room.name]]}">
                                        ${data.talksBySchedules[data.schedules[date][creneau][room.name]].title}
                                    </c:if>
                                </td>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </tr>
            </c:forEach>
        </table>
    </fieldset>
</c:forEach>
<fieldset  class="span${fn:length(rooms)*2+2} baspage" >
</fieldset>

