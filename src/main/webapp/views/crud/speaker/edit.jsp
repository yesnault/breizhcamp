<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type='text/javascript' charset='utf-8'>
         setActive('speakers');
</script>
<form action="/crud/speaker/edit/submit.htm" method="post" class="form-horizontal span3">
<fieldset>
    <legend>Modification d'un speaker</legend>
    <input type="hidden" id="id" name="id" value="${speaker.id}"/>
    <div class="control-group <c:if test='${not empty lastNameError}'>error</c:if>">
        <label class="control-label" for="lastName">Nom du speaker</label>
        <div class="controls">
            <input type="text" id="lastName" name="lastName" class="input-large" value="${speaker.lastName}"/>
            <c:if test='${not empty lastNameError}'>
                <span class="help-inline">${lastNameError}</span>
            </c:if>
        </div>
    </div>
    <div class="control-group <c:if test='${not empty firstNameError}'>error</c:if>">
        <label class="control-label" for="firstName">Pr&eacute;nom du speaker</label>
        <div class="controls">
            <input type="text" id="firstName" name="firstName" class="input-large"
                value="${speaker.firstName}"/>
            <c:if test='${not empty firstNameError}'>
                <span class="help-inline">${firstNameError}</span>
            </c:if>
        </div>
    </div>
    <div class="control-group <c:if test='${not empty pictureError}'>error</c:if>">
        <label class="control-label" for="picture">URL de la photo du speaker</label>
        <div class="controls">
            <input type="text" id="picture" name="picture" class="input-large"
                value="${speaker.picture}"/>
            <c:if test='${not empty pictureError}'>
                <span class="help-inline">${pictureError}</span>
            </c:if>
        </div>
    </div>
     <div class="control-group <c:if test='${not empty descriptionError}'>error</c:if>">
                <label class="control-label" for="resume">Pr&eacute;sentation du speaker</label>
                <div class="controls">
                    <textarea type="textarea" id="description" name="description" rows="4" class="input-xlarge">${speaker.description}</textarea>
                    <c:if test='${not empty descriptionError}'>
                        <span class="help-inline">${descriptionError}</span>
                    </c:if>
                </div>
     </div>
     <div class="control-group <c:if test='${not empty twitterError}'>error</c:if>">
                <label class="control-label" for="twitter">Twitter du speaker</label>
                <div class="controls">
                    <input type="text" id="twitter" name="twitter" class="input-large" value="${speaker.twitter}" />
                    <c:if test='${not empty twitterError}'>
                        <span class="help-inline">${twitterError}</span>
                    </c:if>
                </div>
      </div>
    <div class="form-actions">
        <button type="submit" class="btn btn-primary">Submit</button>
    </div>
</fieldset>
</form>



