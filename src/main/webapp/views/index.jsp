<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type='text/javascript' charset='utf-8'>
         setActive('programme');
</script>
<h3>Programme du BreizhCamp !</h3>

<c:forEach var="date" items="${dates}">
    <fieldset class="span${rooms.size()*2+2}">
        <legend>Programme du <fmt:formatDate value="${date}" type="both" pattern="dd/MM/yyyy" /></legend>
        <table class="table  table-striped table-bordered table-condensed span${rooms.size()*2+2}">
            <tr>
                <th class="span2">Heure</th>
                <c:forEach var="room" items="${rooms}">
                    <th class="span2">${room.name}</th>
                </c:forEach>
            </tr>
            <c:forEach var="creneau" items="${creneaux.get(date)}">
                <tr>
                    <td>${creneau}</td>
                    <c:choose>
                        <c:when test="${talks.get(date).get(creneau).containsKey(sansRoom)}">
                            <td colspan="${rooms.size()}" style="text-align:center">
                                <a href="/talk/${talks.get(date).get(creneau).get(sansRoom).id}.htm">
                                    ${talks.get(date).get(creneau).get(sansRoom).title}
                                </a>
                            </td>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="room" items="${rooms}">
                                <td style="text-align:center">
                                    <a href="/talk/${talks.get(date).get(creneau).get(room.name).id}.htm">
                                        ${talks.get(date).get(creneau).get(room.name).title}
                                    </a>
                                </td>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </tr>
            </c:forEach>
        </table>
    </fieldset>
</c:forEach>
<fieldset  class="span${rooms.size()*2+2} baspage" >
</fieldset>

