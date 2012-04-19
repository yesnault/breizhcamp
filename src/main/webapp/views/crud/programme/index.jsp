<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<script type='text/javascript' charset='utf-8'>
         setActive('programme');
</script>

<script type='text/javascript' charset='utf-8'>

    var talks = {
        <c:forEach var="duree" items="${allDurees}" varStatus="status">
            <c:if test="${not empty data.talksNotScheduled[duree.minute]}">
                ${duree.minute} : {
                    <c:forEach var="talk" items="${data.talksNotScheduled[duree.minute]}" varStatus="status">
                        ${talk.id} : '${talk.title}'<c:if test="${not status.last}">,</c:if>
                    </c:forEach>
                },
            </c:if>
        </c:forEach>
    };

    function addTalkToSelect(duree, idSelect) {
        // On vide le select
        while ($('#talk' + idSelect + '>option').length > 0) {
            $('#talk' + idSelect).get(0).remove();
        }
        $('#talk'+idSelect).append('<option value="-1">Pas de talk</option')
        for (id in talks[duree]) {
            titre = talks[duree][id];
            $('#talk'+idSelect).append('<option value="'+id+'">'+titre+'</option')
        }
    }

    function selectTalk(idSelect) {
        idSchedule = idSelect;
        idTalk = $('#talk' + idSelect + ' option:selected').val();
        $('#talk' + idSelect).attr('disabled', 'disabled');
        url = "/crud/programme/addTalk.htm?idSchedule=" + idSchedule
                + "&idTalk=" + idTalk;

        $.get(url, function(data) {
            alert(data);
        });
    }
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
                                <c:if test="${empty data.talksBySchedules[data.schedules[date][creneau][sansRoom]]}">
                                    <select id="talk${data.schedules[date][creneau][sansRoom].id}" class="span2"
                                                onChange="javascript:selectTalk(${data.schedules[date][creneau][sansRoom].id})">

                                    </select>
                                    <script type='text/javascript' charset='utf-8'>
                                        addTalkToSelect(
                                            ${data.schedules[date][creneau][sansRoom].duree},
                                            ${data.schedules[date][creneau][sansRoom].id});
                                    </script>
                                </c:if>
                            </td>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="room" items="${data.rooms}">
                                <c:if test="${not empty data.schedules[date][creneau][room.name].id}">
                                    <td id="${data.schedules[date][creneau][room.name].id}" style="text-align:center">
                                        <c:if test="${not empty data.talksBySchedules[data.schedules[date][creneau][room.name]]}">
                                            ${data.talksBySchedules[data.schedules[date][creneau][room.name]].title}
                                        </c:if>
                                        <c:if test="${empty data.talksBySchedules[data.schedules[date][creneau][room.name]]}">
                                            <select id="talk${data.schedules[date][creneau][room.name].id}" class="span2"
                                                onChange="javascript:selectTalk(${data.schedules[date][creneau][room.name].id})">
                                            </select>
                                            <script type='text/javascript' charset='utf-8'>
                                                addTalkToSelect(
                                                    ${data.schedules[date][creneau][room.name].duree},
                                                    ${data.schedules[date][creneau][room.name].id});
                                            </script>
                                        </c:if>
                                    </td>
                                </c:if>
                                <c:if test="${empty data.schedules[date][creneau][room.name].id}">
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

