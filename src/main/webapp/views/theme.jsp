<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="custo" uri="/WEB-INF/custom-functions.tld" %>
<script type='text/javascript' charset='utf-8'>
         setActive('programme');
</script>

<fieldset class="span8">
    <legend>${theme.htmlValue}</legend>
    <table class="table  table-striped table-bordered table-condensed span6">
        <tr>
            <td><spring:message code="theme.talk.title" text="default text" /></td>
           <td class="span11">
                <c:forEach var="talk" items="${talks}">
                    <a href="/talk/${talk.id}.htm<c:if test="${hide}">?hide=true</c:if>">${talk.title}</a> - ${custo:getduree(talk.duree)}<br/>
              </c:forEach>
           </td>
        </tr>
    </table>
</fieldset>

<fieldset  class="span8 baspage" >
</fieldset>
