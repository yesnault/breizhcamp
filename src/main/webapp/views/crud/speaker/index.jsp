<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<script type='text/javascript' charset='utf-8'>
         setActive('speakers');
</script>
<div class="row">
<table class="table  table-striped table-bordered table-condensed span6">
<tr>
<th class="span1">Image</th>
<th class="span2">Nom</th>
<th class="span2">Pr&eacute;nom</th>
<th class="span2">Twitter</th>
<th class="span1">Actions</th>
</tr>
<c:if test="${empty speakers}">
<tr><td colspan="5">Aucun speaker pour l'instant</td></tr>
</c:if>
<c:forEach var="speaker" items="${speakers}">
    <tr>
      <td><img src="${speaker.picture}" width=40 height=40 /></td>
      <td>${speaker.lastName}</td>
      <td>${speaker.firstName}</td>
      <td>${speaker.twitter}</td>
      <td>
        <a href="/crud/speaker/edit/${speaker.id}.htm">Editer</a><br/>
        <a href="/crud/speaker/delete/${speaker.id}.htm">Supprimer</a>
      </td>
    </tr>
	</c:forEach>
</table>
<br/>
</div>
<div class="row">
<div class="span3">
<a href="/crud/speaker/add.htm" accesskey="a">Ajouter un speaker (ALT+a)</a>
</div>
</div>

