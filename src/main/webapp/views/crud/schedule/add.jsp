<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<script type='text/javascript' charset='utf-8'>
         setActive('schedules');
</script>
<form action="/crud/schedule/add/submit.htm" method="post" class="form-horizontal span3">
<fieldset>
    <legend><spring:message code="schedule.add.title"   /></legend>
    <div class="control-group <c:if test='${not empty dateError}'>error</c:if>">
        <label class="control-label" for="date"><spring:message code="schedule.add.date.label"   /></label>
        <div class="controls">
            <script type='text/javascript' charset='utf-8'>
                $(function() {
                    setTimeout( function() { $('#date').focus() }, 0 );
                });
            </script>
            <select id="date" name="date" class="input-xlarge" tabindex="1">
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
        <label class="control-label" for="startTime"><spring:message code="schedule.add.heure.label"   /></label>
        <div class="controls">
            <input type="time" id="startTime" name="startTime" class="input-xlarge" tabindex="2"
                <c:if test='${not empty startTime}'>value="${startTime}"</c:if>/>
            <c:if test='${not empty startTimeError}'>
                <span class="help-inline">${startTimeError}</span>
            </c:if>
        </div>
    </div>
    <div class="control-group <c:if test='${not empty roomError}'>error</c:if>">
        <label class="control-label" for="theme"><spring:message code="schedule.add.salle.label"   /></label>
        <div class="controls">
            <select id="room" name="room" class="input-xlarge" tabindex="3">
                <option value="-1"><spring:message code="all.rooms"   /></option>
                <c:forEach var="aRoom" items="${allRooms}">
                    <option value="${aRoom.id}"
                        <c:if test="${aRoom.id == room}">selected="selected"</c:if>>${aRoom.name}</option>
                </c:forEach>
            </select>
            <c:if test="${not empty roomError}">
                <span class="help-inline">${roomError}</span>
            </c:if>
        </div>
    </div>
    <div class="form-actions">
        <button type="submit" class="btn btn-primary" tabindex="4"><spring:message code="action.submit"   /></button>
    </div>
</fieldset>
</form>



