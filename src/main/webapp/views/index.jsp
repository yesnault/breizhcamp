<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<script type='text/javascript' charset='utf-8'>
         setActive('programme');
</script>

<script type='text/javascript' charset='utf-8'>
    function coloriseTalks(id) {
        // Si le talk est une favori on change le style.
        isFavoriteTalk(id, function (transaction, results) {
            if (results.rows.length > 0) {
                $('#' + id).addClass("favorite");
            } else {
                $('#' + id).removeClass("favorite");
            }
        });
    }

    function changeFavorite(id) {
        // Si le talk est une favori on change le style.
        isFavoriteTalk(id, function (transaction, results) {
            if (results.rows.length > 0) {
                rmFavoriteTalk(id);
                $('#' + id).removeClass("favorite");
            } else {
                addFavoriteTalk(id);
                $('#' + id).addClass("favorite");
            }
        });
    }
</script>

<c:forEach var="date" items="${dates}">
    <fieldset class="span${fn:length(rooms)*2+2}">
        <legend><spring:message code="programme.title"   /> <fmt:formatDate value="${date}" type="both" pattern="dd/MM/yyyy" /></legend>
        <table class="table  table-striped table-bordered table-condensed span${fn:length(rooms)*2+2}">
            <tr>
                <th class="span2"><spring:message code="heure.title"   /></th>
                <c:forEach var="room" items="${rooms}">
                    <th class="span2">${room.name}</th>
                </c:forEach>
            </tr>
            <c:forEach var="creneau" items="${creneaux[date]}">
                <tr>
                    <td>${creneau}</td>
                    <c:choose>
                        <c:when test="${not empty talks[date][creneau][sansRoom]}">
                            <td id="${talks[date][creneau][sansRoom].id}" colspan="${fn:length(rooms)}" style="text-align:center" onClick="javascript:changeFavorite(${talks[date][creneau][sansRoom].id})">
                                <script type='text/javascript' charset='utf-8'>
                                    coloriseTalks(${talks[date][creneau][sansRoom].id});
                                </script>
                                <a href="/talk/${talks[date][creneau][sansRoom].id}.htm<c:if test="${hide}">?hide=true</c:if>">
                                    ${talks[date][creneau][sansRoom].title}
                                </a>
                                <p>${talks[date][creneau][sansRoom].theme.htmlValue}</p>
                                <p style="text-align:right;">
                                    <c:forEach var="speaker" items="${talks[date][creneau][sansRoom].speakers}">
                                         <a href="/speaker/${speaker.id}.htm<c:if test="${hide}">?hide=true</c:if>" imageanchor="1">
                                           <img src="${speaker.picture}" title="${speaker.firstName} ${speaker.lastName}" width=30 height=30 /></a>
                                    </c:forEach>
                                <p>
                            </td>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="room" items="${rooms}">
                                <c:if test="${not empty talks[date][creneau][room.name].id}">
                                    <td id="${talks[date][creneau][room.name].id}" style="text-align:center" onClick="javascript:changeFavorite(${talks[date][creneau][room.name].id})">
                                        <script type='text/javascript' charset='utf-8'>
                                            coloriseTalks(${talks[date][creneau][room.name].id});
                                        </script>
                                        <a href="/talk/${talks[date][creneau][room.name].id}.htm<c:if test="${hide}">?hide=true</c:if>">
                                            ${talks[date][creneau][room.name].title}
                                        </a>
                                        <p>${talks[date][creneau][room.name].theme.htmlValue}</p>
                                        <p style="text-align:right;">
                                            <c:forEach var="speaker" items="${talks[date][creneau][room.name].speakers}">
                                                <a href="/speaker/${speaker.id}.htm<c:if test="${hide}">?hide=true</c:if>" imageanchor="1">
                                                    <img src="${speaker.picture}" title="${speaker.firstName} ${speaker.lastName}" width=30 height=30 /></a>
                                            </c:forEach>
                                         <p>
                                    </td>
                                </c:if>
                                <c:if test="${empty talks[date][creneau][room.name].id}">
                                    <td/>
                                </c:if>
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

