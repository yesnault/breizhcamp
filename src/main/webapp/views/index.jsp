<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<script type='text/javascript' charset='utf-8'>
         setActive('programme');
</script>

<c:forEach var="date" items="${dates}">
    <fieldset class="span${fn:length(rooms)*2+2}">
        <legend>Programme du <fmt:formatDate value="${date}" type="both" pattern="dd/MM/yyyy" /></legend>
        <table class="table  table-striped table-bordered table-condensed span${fn:length(rooms)*2+2}">
            <tr>
                <th class="span2">Heure</th>
                <c:forEach var="room" items="${rooms}">
                    <th class="span2">${room.name}</th>
                </c:forEach>
            </tr>
            <c:forEach var="creneau" items="${creneaux[date]}">
                <tr>
                    <td>${creneau}</td>
                    <c:choose>
                        <c:when test="${not empty talks[date][creneau][sansRoom]}">
                            <td colspan="${fn:length(rooms)}" style="text-align:center">
                                <a href="/talk/${talks[date][creneau][sansRoom].id}.htm<c:if test="${hide}">?hide=true</c:if>">
                                    ${talks[date][creneau][sansRoom].title}
                                </a>
                            </td>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="room" items="${rooms}">
                                <td style="text-align:center">
                                    <a href="/talk/${talks[date][creneau][room.name].id}.htm<c:if test="${hide}">?hide=true</c:if>">
                                        ${talks[date][creneau][room.name].title}
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
<fieldset  class="span${fn:length(rooms)*2+2} baspage" >
</fieldset>

