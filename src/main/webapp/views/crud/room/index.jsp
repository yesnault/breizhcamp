<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type='text/javascript' charset='utf-8'>
         setActive('rooms');
</script>
<div class="row">
<table class="table table-striped table-bordered table-condensed span3">
<tr>
<th class="span2">Nom de la salle</th>
<th class="span1">Action</th>
</tr>
<c:if test="${rooms.isEmpty()}">
<tr><td colspan="2">Aucunne salle pour l'instant</td></tr>
</c:if>
<c:forEach var="room" items="${rooms}">
    <tr>
      <td>${room.name}</td>
      <td>
        <a href="/crud/room/edit/${room.id}.htm">Editer</a>
        <a href="/crud/room/delete/${room.id}.htm">Supprimer</a>
      </td>
    </tr>
	</c:forEach>
</table>
</div>
<div class="row">
<div class="span3">
<a href="/crud/room/add.htm">Ajouter une salle</a>
</div>
</div>


