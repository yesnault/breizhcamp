<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="custo" uri="/WEB-INF/custom-functions.tld" %>
<script type='text/javascript' charset='utf-8'>
         setActive('speakers');
</script>

<fieldset class="span12">
    <legend>${speaker.firstName} ${speaker.lastName}</legend>
    <table class="table  table-striped table-bordered table-condensed span12">
        <tr >
                <td class="span2" ROWSPAN=4><a href="https://twitter.com/#!/${speaker.twitter}" imageanchor="1">
                      <img border="0" src="${speaker.picture}" style="height:128px;width:128px;" height="128px" width="128px"></a>
                </td>
        </tr>
        <tr>
           <td class="span6" COLSPAN=2><a href="https://twitter.com/#!/${speaker.twitter}" imageanchor="1">${speaker.twitter}</a></td>
        </tr>
        <tr>
            <td class="span6" COLSPAN=2>${speaker.description}</td>
        </tr>

        <tr>
            <td class="span1"><spring:message code="speaker.session.title" text="default text" /></td>
            <td class="span11">
                <c:forEach var="talk" items="${speaker.talks}">
                    <a href="/talk/${talk.id}.htm<c:if test="${hide}">?hide=true</c:if>">${talk.title}</a> - ${talk.theme.htmlValue} - ${custo:getduree(talk.duree)}<br/>
                </c:forEach>
            </td>
        </tr>
    </table>
</fieldset>

<fieldset  class="span8 baspage" >
</fieldset>
