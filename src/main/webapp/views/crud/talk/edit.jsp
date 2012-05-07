<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="custo" uri="/WEB-INF/custom-functions.tld" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<script type='text/javascript' charset='utf-8'>
         setActive('talks');
         function doAjax() {
                 jQuery.getJSON("/speakers.json", {}, function (data) {
                        $('#speakers option').remove();
                         $.each(data, function(key, val) {
                                 $("").attr('value', val.id)
                                         .text(val.name)
                                         .appendTo('#speakers');
                           });
                       });
             }
             function popup() {

                window.open('/crud/speaker/add.htm?hide=true','windowname1','width=500, height=480');

             }
</script>
<form action="/crud/talk/edit/submit.htm" method="post" class="form-horizontal span4">
<fieldset>
    <legend>Modification d'un talk</legend>
    <input type="hidden" id="id" name="id" value="${talk.id}"/>
    <div class="control-group <c:if test='${not empty titleError}'>error</c:if>">
        <label class="control-label" for="title"><spring:message code="talk.add.titre.label" text="default text" /></label>
        <div class="controls">
            <input type="text" id="title" name="title" class="input-xlarge"
                value="${talk.title}"/>
            <c:if test='${not empty titleError}'>
                <span class="help-inline">${titleError}</span>
            </c:if>
        </div>
    </div>
    <div class="control-group <c:if test='${not empty resumeError}'>error</c:if>">
        <label class="control-label" for="resume"><spring:message code="talk.add.resume.label" text="default text" /></label>
        <div class="controls">
            <textarea type="textarea" id="resume" name="resume" rows="4" class="input-xlarge">${talk.description}</textarea>
            <c:if test='${not empty resumeError}'>
                <span class="help-inline">${resumeError}</span>
            </c:if>
        </div>
    </div>
     <div class="control-group <c:if test='${not empty dureeError}'>error</c:if>">
            <label class="control-label" for=" duree"><spring:message code="talk.add.duree.label" text="default text" /></label>
            <div class="controls">
                <select id="duree" name="duree" class="input-xlarge">
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
        <label class="control-label" for="theme"><spring:message code="talk.add.theme.label" text="default text" /></label>
        <div class="controls">
            <select id="theme" name="theme" class="input-xlarge">
                <c:forEach var="unTheme" items="${possibleThemes}">
                    <option id="${custo:getthemename(unTheme)}" name="${custo:getthemename(unTheme)}"
                        <c:if test="${unTheme == talk.theme}">selected="selected"</c:if>>${unTheme.htmlValue}</option>
                </c:forEach>
            </select>
            <c:if test='${not empty themeError}'>
                <span class="help-inline">${themeError}</span>
            </c:if>
        </div>
    </div>
    <div class="control-group <c:if test='${not empty scheduleError}'>error</c:if>">
        <label class="control-label" for="schedule"><spring:message code="talk.add.creneau.label" text="default text" /></label>
        <div class="controls">
            <select id="schedule" name="schedule" class="input-xlarge" tabindex="5">
                <option value="-1">Je sais pas</option>
                <c:forEach var="unSchedule" items="${allSchedules}">
                    <option value="${unSchedule.id}"
                        <c:if test="${unSchedule.id == talk.schedule.id}">selected="selected"</c:if>>
                        <c:if test="${empty unSchedule.room}">Toutes salles</c:if>
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
        <label class="control-label" for="speakers"><spring:message code="talk.add.speakers.label" text="default text" /></label>
        <div class="controls">
            <select id="speakers" name="speakers" class="input-xlarge" multiple="multiple">
                <c:forEach var="speaker" items="${allSpeakers}">
                    <option value="${speaker.id}" <c:if test="${custo:contains(talk.speakers,speaker)}" >selected="selected"</c:if>>${speaker.firstName} ${speaker.lastName}</option>
                </c:forEach>
            </select><a class="icon-refresh" href="javascript:doAjax()"></a>
            <c:if test='${not empty speakersError}'>
                <span class="help-inline">${speakersError}</span>
            </c:if> <br/> <br/>
            <a accesskey="a" href="javascript: void(0)"   onclick="popup();"><spring:message code="speaker.action.add"/></a>
        </div>
    </div>
    <div class="form-actions">
        <button type="submit" class="btn btn-primary"><spring:message code="action.submit" text="default text" /></button>
    </div>
</fieldset>
</form>



