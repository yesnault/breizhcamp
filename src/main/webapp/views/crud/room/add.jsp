<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type='text/javascript' charset='utf-8'>
         setActive('rooms');
</script>
<form action="/crud/room/add/submit.htm" method="post" class="form-horizontal span3">
<fieldset>
    <legend>Ajout d'une salle</legend>
    <div class="control-group <c:if test='${error}'>error</c:if>">
        <label class="control-label" for="name">Nom de la salle</label>
        <div class="controls">
            <script type='text/javascript' charset='utf-8'>
                $(function() {
                    setTimeout( function() { $('#name').focus() }, 0 );
                });
            </script>
            <input type="text" id="name" name="name" class="input-large" tabindex="1"/>
            <c:if test='${not empty nameError}'>
                <span class="help-inline">${nameError}</span>
            </c:if>
        </div>
    </div>
    <div class="form-actions">
        <button type="submit" class="btn btn-primary">Submit</button>
    </div>
</fieldset>
</form>



