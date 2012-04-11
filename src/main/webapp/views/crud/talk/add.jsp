<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="custo" uri="/WEB-INF/custom-functions.tld" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<script type='text/javascript' charset='utf-8'>
         setActive('talks');
</script>
<form action="/crud/talk/add/submit.htm" method="post" class="form-horizontal span4">
<fieldset>
    <legend><spring:message code="talk.add.title"   /></legend>
    <div class="control-group <c:if test='${not empty titleError}'>error</c:if>">
        <label class="control-label" for="title"><spring:message code="talk.add.titre.label"   /></label>
        <div class="controls">
            <script type='text/javascript' charset='utf-8'>
                $(function() {
                    setTimeout( function() { $('#title').focus() }, 0 );
                });
            </script>
            <input type="text" id="title" name="title" class="input-xlarge" tabindex="1"
                <c:if test='${not empty title}'>value="${title}"</c:if>/>
            <c:if test='${not empty titleError}'>
                <span class="help-inline">${titleError}</span>
            </c:if>
        </div>
    </div>
    <div class="control-group <c:if test='${not empty resumeError}'>error</c:if>">
        <label class="control-label" for="resume"><spring:message code="talk.add.resume.label"   /></label>
        <div class="controls">
            <textarea type="textarea" id="resume" name="resume" rows="4" class="input-xlarge" tabindex="2"><c:if test='${not empty resume}'>${resume}</c:if></textarea>
            <c:if test='${not empty resumeError}'>
                <span class="help-inline">${resumeError}</span>
            </c:if>
        </div>
    </div>
    <div class="control-group <c:if test='${not empty dureeError}'>error</c:if>">
        <label class="control-label" for=" duree"><spring:message code="talk.add.duree.label"   /></label>
        <div class="controls">
            <select id="duree" name="duree" class="input-xlarge" tabindex="3">
                 <c:forEach var="uneDuree" items="${possibleDurees}">
                     <option id="${custo:getdureename(uneDuree)}" name="${custo:getdureename(uneDuree)}"
                         <c:if test="${uneDuree.minute == talk.duree}">selected="selected"</c:if> value="${uneDuree.minute}">${uneDuree.html}</option>
                 </c:forEach>
             </select>
            <c:if test='${not empty dureeError}'>
                <span class="help-inline">${dureeError}</span>
            </c:if>
        </div>
    </div>
    <div class="control-group <c:if test='${not empty themeError}'>error</c:if>">
        <label class="control-label" for="theme"><spring:message code="talk.add.theme.label"   /></label>
        <div class="controls">
            <select id="theme" name="theme" class="input-xlarge" tabindex="4">
                <c:forEach var="unTheme" items="${possibleThemes}">
                    <option id="${custo:getthemename(unTheme)}" name="${custo:getthemename(unTheme)}"
                        <c:if test="${unTheme.htmlValue == theme}">selected="selected"</c:if>>${unTheme.htmlValue}</option>
                </c:forEach>
            </select>
            <c:if test='${not empty themeError}'>
                <span class="help-inline">${themeError}</span>
            </c:if>
        </div>
    </div>
    <div class="control-group <c:if test='${not empty scheduleError}'>error</c:if>">
        <label class="control-label" for="schedule"><spring:message code="talk.add.creneau.label"   /></label>
        <div class="controls">
            <select id="schedule" name="schedule" class="input-xlarge" tabindex="5">
                <option value="-1"><spring:message code="talk.unknown"   /></option>
                <c:forEach var="unSchedule" items="${allSchedules}">
                    <option value="${unSchedule.id}"
                        <c:if test="${unSchedule.id == schedule}">selected="selected"</c:if>>
                        <c:if test="${empty unSchedule.room}"><spring:message code="all.rooms" /></c:if>
                        ${unSchedule.room.name} - <fmt:formatDate value="${unSchedule.start}" type="both" pattern="dd/MM/yyyy HH:mm"/>
                    </option>
                </c:forEach>
            </select>
            <c:if test='${not empty scheduleError}'>
                <span class="help-inline">${scheduleError}</span>
            </c:if>
        </div>
    </div>
    <div class="control-group <c:if test='${not empty speakersError}'>error</c:if>">
        <label class="control-label" for="speakers"><spring:message code="talk.add.speakers.label"   /></label>
        <div class="controls">
            <select id="speakers" name="speakers" class="input-xlarge" multiple="multiple" tabindex="6">
                <c:forEach var="speaker" items="${allSpeakers}">
                    <option value="${speaker.id}" <c:if test="${custo:contains(speakers,speaker.id)}" >selected="selected"</c:if>>${speaker.firstName} ${speaker.lastName}</option>
                </c:forEach>
            </select>
            <c:if test='${not empty speakersError}'>
                <span class="help-inline">${speakersError}</span>
            </c:if>
        </div>
    </div>
    <div class="form-actions">
        <button type="submit" class="btn btn-primary" tabindex="7"><spring:message code="action.submit"   /></button>
    </div>
</fieldset>
</form>



