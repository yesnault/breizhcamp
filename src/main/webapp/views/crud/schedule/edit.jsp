<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="custo" uri="/WEB-INF/custom-functions.tld" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<script type='text/javascript' charset='utf-8'>
         setActive('schedules');
</script>
<form action="/crud/schedule/edit/submit.htm" method="post" class="form-horizontal span3">
<fieldset>
    <legend>Modification d'un cr&eacute;neau</legend>
    <input type="hidden" id="id" name="id" value="${schedule.id}"/>
    <div class="control-group <c:if test='${not empty dateError}'>error</c:if>">
        <label class="control-label" for="date"><spring:message code="schedule.add.date.label" text="default text" /></label>
        <div class="controls">
            <c:set var="pattern" value="dd/MM/yyyy"/>
            <c:set var="date" value="${custo:format(schedule.start, pattern)}"/>
            <select id="date" name="date" class="input-xlarge">
                <c:forEach var="uneDate" items="${possibleDates}">
                    <option value="${uneDate.sortieDate}" <c:if test="${date == uneDate.sortieDate}">selected="selected"</c:if>>${uneDate.sortieFr}</option>
                </c:forEach>
            </select>
            <c:if test='${not empty dateError}'>
                <span class="help-inline">${dateError}</span>
            </c:if>
        </div>
    </div>
    <div class="control-group <c:if test='${not empty startTimeError}'>error</c:if>">
        <label class="control-label" for="startTime"><spring:message code="schedule.add.heure.label" text="default text" /></label>
        <div class="controls">
            <c:set var="patternTime" value="HH:mm"/>
            <c:set var="start" value="${custo:format(schedule.start, patternTime)}"/>
            <input type="time" id="startTime" name="startTime" class="input-xlarge"
                value="${start}"/>
            <c:if test='${not empty startTimeError}'>
                <span class="help-inline">${startTimeError}</span>
            </c:if>
        </div>
    </div>
    <div class="control-group <c:if test='${not empty roomError}'>error</c:if>">
        <label class="control-label" for="theme"><spring:message code="schedule.add.salle.label" text="default text" /></label>
        <div class="controls">
            <select id="room" name="room" class="input-xlarge">
                <option value="-1" <c:if test="${empty schedule.room}">selected="selected"</c:if>><spring:message code="all.rooms" text="default text" /></option>
                <c:forEach var="aRoom" items="${allRooms}">
                    <option value="${aRoom.id}"
                        <c:if test="${aRoom.name == schedule.room.name}">selected="selected"</c:if>>${aRoom.name}</option>
                </c:forEach>
            </select>
            <c:if test='${not empty roomError}'>
                <span class="help-inline">${roomError}</span>
            </c:if>
        </div>
    </div>
    <div class="form-actions">
        <button type="submit" class="btn btn-primary"><spring:message code="action.submit" text="default text" /></button>
    </div>
</fieldset>
</form>



