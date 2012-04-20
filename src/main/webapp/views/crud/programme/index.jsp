<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<script type='text/javascript' charset='utf-8'>
         setActive('programme');
</script>

<script type='text/javascript' charset='utf-8'>
    function selectTalk(idSelect) {
        idSchedule = idSelect;
        idTalk = $('#talk' + idSelect + ' option:selected').val();
        url = "/crud/programme/addTalk.htm?idSchedule=" + idSchedule
                + "&idTalk=" + idTalk;

        $.get(url, function(data) {
            refreshJson();
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
                                <select id="talk${data.schedules[date][creneau][sansRoom].id}" class="span2 selectTalk"
                                            onChange="javascript:selectTalk(${data.schedules[date][creneau][sansRoom].id})">

                                </select>
                            </td>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="room" items="${data.rooms}">
                                <c:if test="${not empty data.schedules[date][creneau][room.name].id}">
                                    <td id="${data.schedules[date][creneau][room.name].id}" style="text-align:center">
                                        <select id="talk${data.schedules[date][creneau][room.name].id}" class="span2 selectTalk"
                                            onChange="javascript:selectTalk(${data.schedules[date][creneau][room.name].id})">
                                        </select>
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


<script type='text/javascript' charset='utf-8'>
    function refreshJson() {

        $.getJSON('/crud/programme/talks.json', function(data) {
            var talksBySchedules = data.talksBySchedules;
            var talksNotScheduled = data.talksNotScheduled;
            var dureeBySchedule = data.dureeBySchedule;

            $('.selectTalk').each(function(index) {
                idSelect = $(this).attr('id');
                idSchedule = $(this).attr('id').substring(4, 100);
                talkOfSchedule = talksBySchedules[idSchedule];
                // On vide le select
                while ($('#' + idSelect + '>option').length > 0) {
                    $('#' + idSelect).get(0).remove();
                }
                $('#'+idSelect).append('<option value="-1">Pas de talk</option')
                if (typeof(talksNotScheduled[dureeBySchedule[idSchedule]]) != 'undefined') {
                    $.each(talksNotScheduled[dureeBySchedule[idSchedule]], function(index, talk) {
                        $('#' + idSelect).append('<option value="'+talk.id+'">'+talk.title+'</option')
                    });
                }
                if (typeof(talkOfSchedule) != 'undefined') {
                    $('#' + idSelect).append('<option value="'+talkOfSchedule.id+'" selected="selected">'+talkOfSchedule.title+'</option')
                }
            });
        });
    }

    refreshJson();
</script>

