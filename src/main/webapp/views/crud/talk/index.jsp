<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type='text/javascript' charset='utf-8'>
         setActive('talks');
</script>
<div class="row">
<table class="table  table-striped table-bordered table-condensed span9">
<tr>
<th class="span1">Titre</th>
<th class="span2">R&eacute;sum&eacute;</th>
<th class="span2">D&eacute;but</th>
<th class="span2">Fin</th>
<th class="span1">Th&egrave;me</th>
<th class="span1">Actions</th>
</tr>
<c:if test="${talks.isEmpty()}">
<tr><td colspan="6">Aucun talk pour l'instant</td></tr>
</c:if>
<c:forEach var="talk" items="${talks}">
    <tr>
      <td>${talk.title}</td>
      <td>${talk.abstract}</td>
      <td>${talk.start}</td>
      <td>${talk.end}</td>
      <td>${talk.theme}</td>
      <td>
        <a href="/crud/talk/edit/${talk.id}.htm">Editer</a><br/>
        <a href="/crud/talk/delete/${talk.id}.htm">Supprimer</a>
      </td>
    </tr>
	</c:forEach>
</table>
</div>
<div class="row">
<div class="span3">
<a href="/crud/talk/add.htm">Ajouter un talk</a>
</div>
</div>


