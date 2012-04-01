<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type='text/javascript' charset='utf-8'>
         setActive('speakers');
</script>
<form action="/crud/speaker/add/submit.htm" method="post" class="form-horizontal span3">
<fieldset>
    <legend>Ajout d'un speaker</legend>
    <div class="control-group <c:if test='${not empty lastNameError}'>error</c:if>">
        <label class="control-label" for="lastName">Nom du speaker</label>
        <div class="controls">
            <input type="text" id="lastName" name="lastName" class="input-large"
                <c:if test='${not empty lastName}'>value="${lastName}"</c:if>/>
            <c:if test='${not empty lastNameError}'>
                <span class="help-inline">${lastNameError}</span>
            </c:if>
        </div>
    </div>
    <div class="control-group <c:if test='${not empty firstNameError}'>error</c:if>">
        <label class="control-label" for="firstName">Pr&eacute;nom du speaker</label>
        <div class="controls">
            <input type="text" id="firstName" name="firstName" class="input-large"
                <c:if test='${not empty firstName}'>value="${firstName}"</c:if>/>
            <c:if test='${not empty firstNameError}'>
                <span class="help-inline">${firstNameError}</span>
            </c:if>
        </div>
    </div>
    <div class="control-group <c:if test='${not empty pictureError}'>error</c:if>">
        <label class="control-label" for="picture">URL de la photo du speaker</label>
        <div class="controls">
            <input type="text" id="picture" name="picture" class="input-large"
                <c:if test='${not empty picture}'>value="${picture}"</c:if>/>
            <c:if test='${not empty pictureError}'>
                <span class="help-inline">${pictureError}</span>
            </c:if>
        </div>
    </div>
    <div class="form-actions">
        <button type="submit" class="btn btn-primary">Submit</button>
    </div>
</fieldset>
</form>



