<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<script type='text/javascript' charset='utf-8'>
         setActive('speakers');
</script>
<div class="row">
<div class="span3">
<a href="/crud/speaker/add.htm" accesskey="a"><spring:message code="speaker.action.add"/></a>
</div>
</div>
<br/>
<div class="row">
<table class="table  table-striped table-bordered table-condensed span6">
<tr>
<th class="span1"><spring:message code="image.title" /></th>
<th class="span2"><spring:message code="nom.title" /></th>
<th class="span2"><spring:message code="prenom.title" /></th>
<th class="span2"><spring:message code="twitter.title" /></th>
<th class="span1"><spring:message code="actions.title" /></th>
</tr>
<c:if test="${empty speakers}">
<tr><td colspan="5"><spring:message code="speaker.no.speaker" /></td></tr>
</c:if>
<c:forEach var="speaker" items="${speakers}">
    <tr>
      <td><img src="${speaker.picture}" width=40 height=40 /></td>
      <td>${speaker.lastName}</td>
      <td>${speaker.firstName}</td>
      <td>${speaker.twitter}</td>
      <td>
        <a href="/crud/speaker/edit/${speaker.id}.htm"><spring:message code="edit.title"   /></a><br/>
        <a href="/crud/speaker/delete/${speaker.id}.htm"><spring:message code="delete.title"   /></a>
      </td>
    </tr>
	</c:forEach>
</table>
<br/>
</div>
<div class="row">
<div class="span3">
<a href="/crud/speaker/add.htm" accesskey="a"><spring:message code="speaker.action.add"/></a>
</div>
</div>

