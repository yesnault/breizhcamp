<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type='text/javascript' charset='utf-8'>
         setActive('talks');
</script>
<script src="/static/js/jquery-1.7.1.min.js"></script>
<script src="/static/js/jquery-ui-1.8.18.custom.min.js"></script>
<form action="/crud/talk/add/submit.htm" method="post" class="form-horizontal span4">
<fieldset>
    <legend>Ajout d'un talk</legend>
    <div class="control-group <c:if test='${not empty titleError}'>error</c:if>">
        <label class="control-label" for="title">Titre du talk</label>
        <div class="controls">
            <input type="text" id="title" name="title" class="input-xlarge"
                <c:if test='${not empty title}'>value="${title}"</c:if>/>
            <c:if test='${not empty titleError}'>
                <span class="help-inline">${titleError}</span>
            </c:if>
        </div>
    </div>
    <div class="control-group <c:if test='${not empty resumeError}'>error</c:if>">
        <label class="control-label" for="resume">R&eacute;sum&eacute; du talk</label>
        <div class="controls">
            <textarea type="textarea" id="resume" name="resume" rows="4" class="input-xlarge"><c:if test='${not empty resume}'>${resume}</c:if></textarea>
            <c:if test='${not empty resumeError}'>
                <span class="help-inline">${resumeError}</span>
            </c:if>
        </div>
    </div>
    <div class="control-group <c:if test='${not empty dateError}'>error</c:if>">
        <script>
    	$(function() {
    		$( "#date" ).datepicker();
    	});
    	</script>
        <label class="control-label" for="date">Date du talk</label>
        <div class="controls">
            <input type="text" id="date" name="date" class="input-xlarge"
                <c:if test='${not empty date}'>value="${date}"</c:if>/>
            <c:if test='${not empty dateError}'>
                <span class="help-inline">${dateError}</span>
            </c:if>
        </div>
    </div>
    <div class="control-group <c:if test='${not empty startTimeError}'>error</c:if>">
        <label class="control-label" for="startTime">Heure de d&eacute;but du talk</label>
        <div class="controls">
            <input type="time" id="startTime" name="startTime" class="input-xlarge"
                <c:if test='${not empty startTime}'>value="${startTime}"</c:if>/>
            <c:if test='${not empty startTimeError}'>
                <span class="help-inline">${startTimeError}</span>
            </c:if>
        </div>
    </div>
    <div class="control-group <c:if test='${not empty endTimeError}'>error</c:if>">
        <label class="control-label" for="endTime">Heure de fin du talk</label>
        <div class="controls">
            <input type="time" id="endTime" name="endTime" class="input-xlarge"
                <c:if test='${not empty endTime}'>value="${endTime}"</c:if>/>
            <c:if test='${not empty endTimeError}'>
                <span class="help-inline">${endTimeError}</span>
            </c:if>
        </div>
    </div>
    <div class="control-group <c:if test='${not empty themeError}'>error</c:if>">
        <label class="control-label" for="theme">Th&egrave;me du talk</label>
        <div class="controls">
            <select id="theme" name="theme" class="input-xlarge">
                <c:forEach var="unTheme" items="${possibleThemes}">
                    <option id="${unTheme.name()}" name="${unTheme.name()}"
                        <c:if test="${unTheme.htmlValue == theme}">selected="selected"</c:if>>${unTheme.htmlValue}</option>
                </c:forEach>
            </select>
            <c:if test='${not empty themeError}'>
                <span class="help-inline">${themeError}</span>
            </c:if>
        </div>
    </div>
    <div class="form-actions">
        <button type="submit" class="btn btn-primary">Submit</button>
    </div>
</fieldset>
</form>



